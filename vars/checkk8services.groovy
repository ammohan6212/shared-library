import groovy.json.JsonSlurper

def call(List services, String namespace = "default") {
    services.each { svc ->
        def svcName = svc.name
        def svcPort = svc.port
        def expectedCode = svc.expectedCode ?: "200"
        def path = svc.path ?: "/"

        echo "🔎 Checking deployment and service status for: ${svcName}"

        // Check rollout status
        sh "kubectl rollout status deployment/${svcName} -n ${namespace} --timeout=120s"

        // Check endpoints
        def endpointsReady = sh(
            script: "kubectl get endpoints ${svcName} -n ${namespace} -o jsonpath='{.subsets}' | grep -q 'addresses'",
            returnStatus: true
        )
        if (endpointsReady != 0) {
            error "❌ Service ${svcName} has no ready endpoints!"
        } else {
            echo "✅ Service ${svcName} has ready endpoints."
        }

        echo "⚡ Starting port-forward and checking HTTP response"

        // Start port-forward
        sh """
            kubectl port-forward svc/${svcName} ${svcPort}:${svcPort} -n ${namespace} &
            echo \$! > portforward_${svcName}_pid
            sleep 5
        """

        // Check HTTP code
        def responseCode = sh(script: "curl -s -o /dev/null -w \"%{http_code}\" http://localhost:${svcPort}${path}", returnStdout: true).trim()

        if (responseCode != expectedCode) {
            sh "kill \$(cat portforward_${svcName}_pid)"
            error "❌ Service ${svcName} did not return HTTP ${expectedCode}! Got: ${responseCode}"
        } else {
            echo "✅ Service ${svcName} returned HTTP ${expectedCode} successfully!"
        }

        // Kill port-forward
        sh "kill \$(cat portforward_${svcName}_pid)"
    }
}
