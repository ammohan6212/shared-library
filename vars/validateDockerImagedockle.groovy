// vars/validateDockerImage.groovy
def call(String dockerImageTag) {
    script {
        echo "Validating Docker image ${dockerImageTag}..."
        
        sh "dockle ${dockerImageTag}"

        // Optional: Dive for image analysis
        // sh "docker run --rm -v /var/run/docker.sock:/var/run/docker.sock \
        //     wagoodman/dive:latest ${dockerImageTag} || true"

        // Optional: Dockle for security best practices
        // sh "docker run --rm -v /var/run/docker.sock:/var/run/docker.sock \
        //     ghcr.io/goodwithtech/dockle:latest -ak --exit-code 1 ${dockerImageTag} || true"

        echo "✅ Docker image validation completed."
    }
}