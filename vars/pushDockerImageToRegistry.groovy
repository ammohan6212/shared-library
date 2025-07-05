def call(String registryUrl, String credentialsId, def dockerImage) {
    script {
        echo "Pushing Docker image ${dockerImage.imageName()} to ${registryUrl}..."
        docker.withRegistry(registryUrl, credentialsId) {
            dockerImage.push()
        }
        echo "✅ Docker image pushed to registry."
    }
}
