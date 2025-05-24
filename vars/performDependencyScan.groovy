// vars/performDependencyScan.groovy
def call(String language) {
    script {
        echo "Performing dependency scanning for ${language}..."
        switch (language) {
            case 'python':
                sh 'pip install safety || true'
                sh 'safety check -r requirements.txt || true'
                break
            case 'node':
                sh 'npm audit || true'
                break
            case 'go':
                sh 'go install github.com/securego/gosec/cmd/gosec@latest || true'
                sh 'gosec ./... || true' // Basic Go security scan
                break
            case 'java':
                // Assumes OWASP Dependency-Check Maven Plugin is configured
                sh 'mvn org.owasp:dependency-check-maven:check || true'
                break
            case 'rust':
                sh 'cargo audit || true'
                break
            default:
                echo "⚠️ Language unknown. Skipping dependency scanning."
                break
        }
        echo "✅ Dependency scanning completed."
    }
}