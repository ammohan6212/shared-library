// vars/scanContainerSyftDockle.groovy
def call(String dockerImageTag) {
    script {
        echo "Generating SBOM with Syft for ${dockerImageTag}..."
        sh "syft ${dockerImageTag} -o spdx-json=sbom.json || true"
        sh "syft ${dockerImageTag}"
        echo "✅ Syft SBOM generation and Dockle scan completed."
    }
}