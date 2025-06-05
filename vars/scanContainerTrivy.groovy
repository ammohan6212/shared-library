// vars/scanContainerTrivy.groovy
def call(String dockerImageTag) {
    script {
        echo "Performing container vulnerability scan with Trivy for ${dockerImageTag}..."
        sh "trivy image --exit-code 1 --severity HIGH,CRITICAL ${dockerImageTag} || true"
        sh " trivy image --format table ${dockerImageTag}"      
        sh "trivy image --format json frontend:${dockerImageTag}    # JSON format"
        sh "trivy image --scanners secret --format json --output trivy-secrets.json ${dockerImageTag}"
        echo "✅ Trivy scan completed."
    }
}