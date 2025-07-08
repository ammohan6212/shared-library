// vars/performDependencyScan.groovy
def call(String language) {
    script {
        echo "🔎 Performing dependency scanning for ${language}..."

        withCredentials([string(credentialsId: 'SNYK_API_TOKEN', variable: 'SNYK_TOKEN')]) {
            switch (language) {
                case 'python':
                    sh 'pip install safety || true'
                    sh 'safety check -r requirements.txt || true'
                    sh 'snyk auth $SNYK_TOKEN'
                    sh 'snyk test --file=requirements.txt'
                    break

                case 'node':
                    sh 'npm audit || true'
                    sh 'npm audit fix --force'
                    sh 'snyk auth $SNYK_TOKEN'
                    sh 'snyk test --file=package.json'
                    break

                case 'go':
                    sh 'go install github.com/securego/gosec/cmd/gosec@latest || true'
                    sh 'gosec ./... || true'
                    sh 'snyk auth $SNYK_TOKEN'
                    sh 'snyk test --file=go.mod'
                    break

                case 'java':
                    sh 'mvn org.owasp:dependency-check-maven:check || true'
                    sh 'snyk auth $SNYK_TOKEN'
                    sh 'snyk test --file=pom.xml'
                    sh 'dependency-check.sh --project "MyJavaApp" --scan . --format HTML'
                    break

                case 'rust':
                    sh 'ls -l'
                    sh 'cargo audit || true'
                    sh 'snyk auth $SNYK_TOKEN'
                    sh 'export SNYK_RUST_PROJECT_SUPPORT=1'
                    sh 'snyk test --file=Cargo.toml --package-manager=cargo'
                    break

                default:
                    echo "⚠️ Language unknown. Skipping dependency scanning."
                    break
            }
        }

        echo "✅ Dependency scanning completed."
    }
}
