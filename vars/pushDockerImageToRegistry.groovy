def call(String registryUrl, String usernameCredentialId, String passwordCredentialId, def dockerImage) {
    script {
        echo "Preparing to push Docker image ${dockerImage} to ${registryUrl}..."

        withCredentials([
            string(credentialsId: usernameCredentialId, variable: 'DOCKER_USERNAME'),
            string(credentialsId: passwordCredentialId, variable: 'DOCKER_PASSWORD')
        ]) {
            // Perform docker login manually
            sh "echo $DOCKER_PASSWORD | docker login  -u $DOCKER_USERNAME --password-stdin"

            // Push the image
            sh "docker push ${dockerImage}"

            // Optional: logout after push
            sh "docker logout ${registryUrl}"

            echo "✅ Docker image pushed to ${registryUrl}."
        }
    }
}
