def checkNodePort(String svcName, String namespace = "default", String path = "/", String expectedCode = "200") {
    echo "🌐 Checking NodePort service: ${svcName}"

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

    echo "🔎 Testing http://${nodeIp}:${nodePort}${path}"

    // Check HTTP code
    def responseCode = sh(
        script: "curl -s -o /dev/null -w \"%{http_code}\" http://${nodeIp}:${nodePort}${path}",
        returnStdout: true
    ).trim()

    if (responseCode != expectedCode) {
        error "❌ NodePort service ${svcName} did not return HTTP ${expectedCode}! Got: ${responseCode}"
    } else {
        echo "✅ NodePort service ${svcName} returned HTTP ${expectedCode} successfully!"
    }
}
