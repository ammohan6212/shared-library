// vars/scanContainerSyftDockle.groovy
def call(String dockerImageTag) {
    script {
        echo "Generating SBOM with Syft for ${dockerImageTag}..."
        sh "syft ${dockerImageTag} -o spdx-json=sbom.json || true"
        echo "Performing security best practices check with Dockle for ${dockerImageTag}..."
        sh "docker run --rm -v /var/run/docker.sock:/var/run/docker.sock \
            ghcr.io/goodwithtech/dockle:latest -ak --exit-code 1 ${dockerImageTag} || true"
        echo "✅ Syft SBOM generation and Dockle scan completed."
    }
}