// vars/validateDockerImage.groovy
def call(String dockerImageTag, String dockerfilePath = 'Dockerfile') {
    script {
        echo "Validating Docker image ${dockerImageTag} and Dockerfile ${dockerfilePath}..."
        // Hadolint for Dockerfile linting
        sh "docker pull hadolint/hadolint"
        sh "docker run --rm -i hadolint/hadolint < ${dockerfilePath} || true"
        sh "dockle ${dockerfilePath}"

        // Optional: Dive for image analysis
        // sh "docker run --rm -v /var/run/docker.sock:/var/run/docker.sock \
        //     wagoodman/dive:latest ${dockerImageTag} || true"

        // Optional: Dockle for security best practices
        // sh "docker run --rm -v /var/run/docker.sock:/var/run/docker.sock \
        //     ghcr.io/goodwithtech/dockle:latest -ak --exit-code 1 ${dockerImageTag} || true"

        echo "✅ Docker image validation completed."
    }
}