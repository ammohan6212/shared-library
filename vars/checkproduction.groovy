import groovy.json.JsonSlurper

def call(List services, String namespace = "default") {
    services.each { svc ->
        def svcName = svc.name
        def svcPort = svc.port
        def expectedCode = svc.expectedCode ?: "200"
        def path = svc.path ?: "/"

        echo "🔎 Checking deployment rollout for: ${svcName}"

        // Check rollout status
        def rolloutStatus = sh(
            script: "kubectl rollout status deployment/${svcName} -n ${namespace} --timeout=180s",
            returnStatus: true
        )

        if (rolloutStatus != 0) {
            echo "❌ Rollout failed for deployment ${svcName}!"

            // Ask for manager approval before rollback
            def userInput = input(
                id: 'ManagerApproval', message: "Rollout failed for ${svcName}. Do you want to rollback?", parameters: [
                    [$class: 'BooleanParameterDefinition', defaultValue: true, description: 'Approve rollback?', name: 'approveRollback']
                ]
            )

            if (userInput == true) {
                echo "✅ Manager approved rollback. Rolling back deployment ${svcName}..."

                // Perform rollback
                sh "kubectl rollout undo deployment/${svcName} -n ${namespace}"

                // Wait for rollback rollout
                sh "kubectl rollout status deployment/${svcName} -n ${namespace} --timeout=180s"

                echo "⏳ Waiting 30 seconds after rollback for stabilization..."
                sleep 30

                // Check rollback service health
                echo "✅ Verifying service after rollback..."

                // Start port-forward
                sh """
                    kubectl port-forward svc/${svcName} ${svcPort}:${svcPort} -n ${namespace} &
                    sleep 5
                """

                def rollbackResponseCode = sh(script: "curl -s -o /dev/null -w \"%{http_code}\" http://localhost:${svcPort}${path}", returnStdout: true).trim()

                // Stop port-forward
                sh "pkill -f 'kubectl port-forward svc/${svcName}' || true"

                if (rollbackResponseCode != expectedCode) {
                    error "❌ Rollback service ${svcName} also did not return HTTP ${expectedCode}! Got: ${rollbackResponseCode}"
                } else {
                    echo "✅ Rollback service ${svcName} returned HTTP ${expectedCode}. Rollback is successful and healthy."
                }
            } else {
                error "⚠️ Rollout failed and rollback was not approved. Manual intervention required!"
            }
        } else {
            echo "✅ Rollout succeeded for ${svcName}. Proceeding to service health check..."

            // Start port-forward
            sh """
                kubectl port-forward svc/${svcName} ${svcPort}:${svcPort} -n ${namespace} &
                sleep 5
            """

            def responseCode = sh(script: "curl -s -o /dev/null -w \"%{http_code}\" http://localhost:${svcPort}${path}", returnStdout: true).trim()

            // Stop port-forward
            sh "pkill -f 'kubectl port-forward svc/${svcName}' || true"

            if (responseCode != expectedCode) {
                error "❌ Service ${svcName} did not return HTTP ${expectedCode}! Got: ${responseCode}"
            } else {
                echo "✅ Service ${svcName} returned HTTP ${expectedCode} successfully!"
            }
        }
    }
}
