def call(String dockerImageTag, String usernameCredentialId, String passwordCredentialId) {
    script {
        withCredentials([
            string(credentialsId: usernameCredentialId, variable: 'DOCKER_USERNAME'),
            string(credentialsId: passwordCredentialId, variable: 'DOCKER_PASSWORD')
        ]) {
            echo "🔑 Logging in to Docker registry ${registryUrl} before running Docker Scout..."

            // Perform docker login
            sh "echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin"

            echo "✅ Login successful. Running Docker Scout scans..."

            // Run Scout commands
            sh "docker scout quickview ${dockerImageTag}"
            sh "docker scout cves ${dockerImageTag}"
            sh "docker scout recommendations ${dockerImageTag}"

            // Optional: logout after scan
            sh "docker logout ${registryUrl}"

            echo "🚀 Docker Scout scan completed for ${dockerImageTag}."
        }
    }
}
