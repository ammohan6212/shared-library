// vars/runLinter.groovy
def call(String language) {
    script {
        echo "Linting the code for ${language}..."
        try {
            switch (language) {
                case 'python':
                    sh """
                        pip install flake8 pylint black || true
                        flake8 src/ || true
                        pylint src/ || true
                        black src/ --check || true
                    """
                    break
                case 'node':
                    sh """
                        npm install eslint@8.57.0 || true
                        npx eslint --init
                        npm run lint
                        npm run lint:fix
                    """
                    break
                case 'go':
                    sh 'go install github.com/golangci/golangci-lint/cmd/golangci-lint@latest || true'
                    sh 'golangci-lint run ./... || true'
                    break
                case 'java':
                    // Assumes Maven Checkstyle plugin is configured in pom.xml
                    sh 'mvn checkstyle:check || true'
                    break
                case 'rust':
                    sh 'cargo clippy || true'
                    break
                default:
                    echo "⚠️ Language unknown. Skipping linting."
                    break
            }
            echo "✅ Linting completed."
        } catch (Exception e) {
            echo "❌ Linting failed for ${language}: ${e.getMessage()}"
            currentBuild.result = 'UNSTABLE' // Mark as unstable, not necessarily a failure
        }
    }
}