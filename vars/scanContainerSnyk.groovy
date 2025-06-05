// vars/scanContainerSnyk.groovy
// Requires SNYK_TOKEN to be set as a secret in Jenkins or environment
def call(String dockerImageTag, String dockerfilePath = 'Dockerfile') {
    sh "snyk auth 9d262b22-1f2c-4069-adb9-696793789926"
    sh "snyk container test ${dockerImageTag}"
    sh "snyk container test ${dockerImageTag} --file=Dockerfile"
    sh "snyk container test ${dockerImageTag} --exclude-base-image-vulns"
    sh "snyk container test ${dockerImageTag} --json > report.json"
    sh "snyk container test ${dockerImageTag} --sarif > report.sarif"
    sh "snyk container test ${dockerImageTag} --json-file-output=output.json"
    echo "docker scan is completed please check the logs for details"

}