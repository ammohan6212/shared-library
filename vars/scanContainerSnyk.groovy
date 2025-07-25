def call(String dockerImageTag, String dockerfilePath = 'Dockerfile') {
    withCredentials([string(credentialsId: 'SNYK_API_TOKEN', variable: 'SNYK_TOKEN')]) {
        sh 'snyk auth $SNYK_TOKEN'
        sh "snyk container test ${dockerImageTag} --file=${dockerfilePath} --exclude-base-image-vulns --severity-threshold=high --json-file-output=snyk-report.json --sarif-file-output=snyk-report.sarif || true"
        echo "Docker scan is completed — please check snyk-report.json and snyk-report.sarif for details."
    }
}
