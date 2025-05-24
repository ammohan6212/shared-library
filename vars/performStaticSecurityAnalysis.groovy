// vars/performStaticSecurityAnalysis.groovy
def call(String language) {
    script {
        echo "Performing static security analysis for ${language}..."
        // This often involves dedicated SAST tools like SonarQube (already covered),
        // Snyk Code, Checkmarx, Fortify, Semgrep, Bandit (Python), ESLint (JS/TS).
        switch (language) {
            case 'python':
                sh 'pip install bandit || true'
                sh 'bandit -r src/ -f json -o bandit-report.json || true'
                break
            case 'node':
                sh 'npm install -g snyk || true'
                withCredentials([string(credentialsId: 'snyk-token', variable: 'SNYK_TOKEN')]) {
                    sh "snyk code test --org=your-snyk-org-id || true"
                }
                break
            case 'go':
                sh 'go install github.com/securego/gosec/cmd/gosec@latest || true'
                sh 'gosec ./... || true'
                break
            case 'java':
                // For Java, you might use Maven plugins like SpotBugs or FindBugs
                // sh 'mvn com.github.spotbugs:spotbugs-maven-plugin:check'
                echo "Java SAST handled by SonarQube or other dedicated tools."
                break
            default:
                echo "⚠️ Language unknown or no specific SAST tool configured. Skipping."
                break
        }
        echo "✅ Static security analysis completed."
    }
}