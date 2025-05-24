// vars/scanContainerSnyk.groovy
// Requires SNYK_TOKEN to be set as a secret in Jenkins or environment
def call(String dockerImageTag, String dockerfilePath = 'Dockerfile') {
    script {
        echo "Performing container vulnerability scan with Snyk for ${dockerImageTag}..."
        withCredentials([string(credentialsId: 'snyk-token', variable: 'SNYK_TOKEN')]) { // Replace 'snyk-token' with your credential ID
            sh "snyk container test ${dockerImageTag} --file=${dockerfilePath} --org=your-snyk-org-id || true" // Replace with your Snyk org ID
        }
        echo "✅ Snyk scan completed."
    }
}