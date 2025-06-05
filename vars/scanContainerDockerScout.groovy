// vars/scanContainerDockerScout.groovy
// Requires Docker Desktop with Docker Scout enabled and possibly login
def call(String dockerImageTag) {
    script {
        sh "docker scout quickview ${dockerImageTag}"
        sh "docker scout cves ${dockerImageTag}"
        sh "docker scout recommendations  ${dockerImageTag}"   
    }
}