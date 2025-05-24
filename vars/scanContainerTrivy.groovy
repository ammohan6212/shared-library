// vars/scanContainerTrivy.groovy
def call(String dockerImageTag) {
    script {
        echo "Performing container vulnerability scan with Trivy for ${dockerImageTag}..."
        sh "trivy image --exit-code 1 --severity HIGH,CRITICAL ${dockerImageTag} || true"
        echo "✅ Trivy scan completed."
    }
}