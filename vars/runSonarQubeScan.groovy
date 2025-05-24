// vars/runSonarQubeScan.groovy
// Requires SonarQube Scanner to be configured in Jenkins global tools
// and SONAR_HOST_URL, SONAR_TOKEN to be set in environment variables or credentials
def call(String projectKey) {
    script {
        echo "Performing SonarQube scan..."
        def scannerHome = tool 'SonarQubeScanner' // Name of SonarQube Scanner tool configured in Jenkins
        withSonarQubeEnv('SonarQubeServer') { // Name of SonarQube server configured in Jenkins
            sh "${scannerHome}/bin/sonar-scanner -Dsonar.projectKey=${projectKey} -Dsonar.sources=./src"
        }
        // You might want to add a quality gate check here
        // timeout(time: 5, unit: 'MINUTES') {
        //     def qg = waitForQualityGate()
        //     if (qg.status != 'OK') {
        //         error "❌ SonarQube Quality Gate failed: ${qg.status}"
        //     }
        // }
        echo "✅ SonarQube scan completed."
    }
}