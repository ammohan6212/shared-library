// vars/performDependencyScan.groovy
def call(String language) {
    script {
        echo "Performing dependency scanning for ${language}..."
        switch (language) {
            case 'python':
                sh 'pip install safety || true'
                sh 'safety check -r requirements.txt || true'
                sh 'snyk auth 9d262b22-1f2c-4069-adb9-696793789926'
                sh 'snyk test --file=requirements.txt'
                break
            case 'node':
                sh 'npm audit || true'
                sh 'snyk auth 9d262b22-1f2c-4069-adb9-696793789926'
                sh 'snyk test --file=package.json'
                break
            case 'go':
                sh 'go install github.com/securego/gosec/cmd/gosec@latest || true'
                sh 'gosec ./... || true' 
                sh 'snyk auth 9d262b22-1f2c-4069-adb9-696793789926'
                sh 'snyk test --file=go.mod'// Basic Go security scan
                break
            case 'java':
                // Assumes OWASP Dependency-Check Maven Plugin is configured
                sh 'mvn org.owasp:dependency-check-maven:check || true'
                sh 'snyk auth 9d262b22-1f2c-4069-adb9-696793789926'
                sh 'snyk test --file=pom.xml'
                sh 'dependency-check.sh --project "MyJavaApp" --scan . --format HTML'
                break
            case 'rust':
                sh 'cargo audit || true'
                sh 'snyk auth 9d262b22-1f2c-4069-adb9-696793789926'
                sh 'snyk test --file=Cargo.toml'
                break
            default:
                echo "⚠️ Language unknown. Skipping dependency scanning."
                break
        }
        echo "✅ Dependency scanning completed."
    }
}