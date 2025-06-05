// vars/scanContainerGrype.groovy
def call(String dockerImageTag) {
    script {
        echo "Performing container vulnerability scan with Grype for ${dockerImageTag}..."
        sh "grype ${dockerImageTag} --scope all-layers --output json > grype-report.json || true"
        sh "grype ${dockerImageTag}"
        sh "grype ${dockerImageTag} -o table"
        sh "grype ${dockerImageTag} -o json"
        echo "✅ Grype scan completed."
    }
}