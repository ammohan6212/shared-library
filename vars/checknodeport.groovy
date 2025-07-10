import groovy.json.JsonSlurper

def call(List services, String namespace = "default") {
    services.each { svc ->
        def svcName = svc.name
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

        echo "🌐 Checking NodePort HTTP response for ${svcName}"

        // Get node external IP
        def nodeIp = sh(
            script: "kubectl get nodes -o jsonpath='{.items[0].status.addresses[?(@.type==\"ExternalIP\")].address}'",
            returnStdout: true
        ).trim()

        // Get NodePort
        def nodePort = sh(
            script: "kubectl get svc ${svcName} -n ${namespace} -o jsonpath='{.spec.ports[0].nodePort}'",
            returnStdout: true
        ).trim()

        echo "🌍 Testing URL: http://${nodeIp}:${nodePort}${path}"

        // Check HTTP code
        def responseCode = sh(
            script: "curl -s -o /dev/null -w \"%{http_code}\" http://${nodeIp}:${nodePort}${path}",
            returnStdout: true
        ).trim()

        if (responseCode != expectedCode) {
            error "❌ Service ${svcName} did not return HTTP ${expectedCode}! Got: ${responseCode}"
        } else {
            echo "✅ Service ${svcName} returned HTTP ${expectedCode} successfully!"
        }
    }
}
