// vars/pushDockerImageToRegistry.groovy
def call(String registryUrl, String credentialsId, String dockerImageTag) {
    script {
        echo "Pushing Docker image ${dockerImageTag} to ${registryUrl}..."
        docker.withRegistry(registryUrl, credentialsId) {
            dockerImageTag.push()
        }
        echo "✅ Docker image pushed to registry."
    }
}