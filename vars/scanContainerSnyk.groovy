// vars/scanContainerSnyk.groovy
// Requires SNYK_TOKEN to be set as a secret in Jenkins or environment
def call(String dockerImageTag, String dockerfilePath = 'Dockerfile') {
    sh "snyk auth 9d262b22-1f2c-4069-adb9-696793789926"
    sh "snyk container test ${dockerImageTag} --file=Dockerfile --json-file-output=snyk-report.json --sarif-file-output=snyk-report.sarif"
    echo "docker scan is completed please check the logs for details"

}