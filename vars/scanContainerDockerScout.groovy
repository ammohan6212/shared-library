// vars/scanContainerDockerScout.groovy
// Requires Docker Desktop with Docker Scout enabled and possibly login
def call(String dockerImageTag) {
    script {
        echo "Performing container analysis with Docker Scout for ${dockerImageTag}..."
        sh "docker scout scan ${dockerImageTag} --format json --output docker-scout-report.json || true"
        // Process the report or publish as an artifact
        echo "✅ Docker Scout scan completed."
    }
}