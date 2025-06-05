// vars/runSonarQubeScan.groovy
// Requires SonarQube Scanner to be configured in Jenkins global tools
// and SONAR_HOST_URL, SONAR_TOKEN to be set in environment variables or credentials
def call(String projectKey) {
    script {
        echo "Performing SonarQube scan..."
        sh "/opt/sonar-scanner/bin/sonar-scanner"
        echo "✅ SonarQube scan completed."
    }
}