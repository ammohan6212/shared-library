import groovy.json.JsonSlurper

def call(List services, String namespace = "default", String recipients) {
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

            // Send email about rollout failure
            sendEmailNotification("FAILURE", recipients, "Rollout failed for ${svcName} in namespace ${namespace}. Manager approval will be requested.")

            // Ask for manager approval before rollback
            def userInput = input(
                id: 'ManagerApproval', 
                message: "Rollout failed for ${svcName}. Do you want to rollback?", 
                parameters: [
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

                // Check pods readiness after rollback
                def notReadyPods = sh(
                    script: "kubectl get pods -l app=${svcName} -n ${namespace} --no-headers | awk '{print \$2}' | grep -v '1/1' || true",
                    returnStdout: true
                ).trim()
                if (notReadyPods) {
                    sendEmailNotification("FAILURE", recipients, "Rollback completed for ${svcName}, but some pods are not ready: ${notReadyPods}. Immediate investigation required.")
                    error "❌ Some pods for ${svcName} are not ready after rollback: ${notReadyPods}"
                } else {
                    echo "✅ All pods for ${svcName} are ready after rollback."
                }

                echo "✅ Verifying service HTTP response after rollback..."

                // Start port-forward with PID capture
                sh """
                    kubectl port-forward svc/${svcName} ${svcPort}:${svcPort} -n ${namespace} &
                    echo \$! > portforward.pid
                    sleep 5
                """

                def maxRetries = 3
                def success = false
                for (int i = 0; i < maxRetries; i++) {
                    def rollbackResponseCode = sh(script: "curl -s -o /dev/null -w \"%{http_code}\" http://localhost:${svcPort}${path}", returnStdout: true).trim()
                    if (rollbackResponseCode == expectedCode) {
                        success = true
                        break
                    } else {
                        echo "⚠️ Attempt ${i + 1}: Got ${rollbackResponseCode}, expected ${expectedCode}. Retrying in 5 seconds..."
                        sleep 5
                    }
                }

                // Stop port-forward
                sh "kill \$(cat portforward.pid) || true"
                sh "rm -f portforward.pid"

                if (!success) {
                    sendEmailNotification("FAILURE", recipients, "Rollback service for ${svcName} did not return HTTP ${expectedCode} after retries. Manual intervention needed!")
                    error "❌ Rollback service ${svcName} did not return HTTP ${expectedCode} after retries!"
                } else {
                    echo "✅ Rollback service ${svcName} returned HTTP ${expectedCode} successfully!"
                    sendEmailNotification("SUCCESS", recipients, "Rollback for ${svcName} succeeded and service is healthy in namespace ${namespace}.")
                }
            } else {
                sendEmailNotification("Alert", recipients, "Rollout failed for ${svcName} and rollback was not approved. Manual intervention required immediately!")
                error "⚠️ Rollout failed and rollback was not approved. Manual intervention required!"
            }
        } else {
            echo "✅ Rollout succeeded for ${svcName}. Proceeding to service health checks..."

            // Check pods readiness explicitly
            def notReadyPods = sh(
                script: "kubectl get pods -l app=${svcName} -n ${namespace} --no-headers | awk '{print \$2}' | grep -v '1/1' || true",
                returnStdout: true
            ).trim()
            if (notReadyPods) {
                sendEmailNotification("FAILURE", recipients, "Rollout succeeded for ${svcName}, but some pods are not ready: ${notReadyPods}. Please check.")
                error "❌ Some pods for ${svcName} are not ready: ${notReadyPods}"
            } else {
                echo "✅ All pods for ${svcName} are ready."
            }

            // Start port-forward with PID capture
            sh """
                kubectl port-forward svc/${svcName} ${svcPort}:${svcPort} -n ${namespace} &
                echo \$! > portforward.pid
                sleep 5
            """

            def maxRetries = 3
            def success = false
            for (int i = 0; i < maxRetries; i++) {
                def responseCode = sh(script: "curl -s -o /dev/null -w \"%{http_code}\" http://localhost:${svcPort}${path}", returnStdout: true).trim()
                if (responseCode == expectedCode) {
                    success = true
                    break
                } else {
                    echo "⚠️ Attempt ${i + 1}: Got ${responseCode}, expected ${expectedCode}. Retrying in 5 seconds..."
                    sleep 5
                }
            }

            // Stop port-forward
            sh "kill \$(cat portforward.pid) || true"
            sh "rm -f portforward.pid"

            if (!success) {
                sendEmailNotification("FAILURE", recipients, "Service ${svcName} did not return expected HTTP ${expectedCode} after rollout. Please investigate urgently.")
                error "❌ Service ${svcName} did not return HTTP ${expectedCode} after retries!"
            } else {
                echo "✅ Service ${svcName} returned HTTP ${expectedCode} successfully!"
                sendEmailNotification("SUCCESS", recipients, "Rollout and service check succeeded for ${svcName} in namespace ${namespace}. All healthy!")
            }
        }
    }
}
