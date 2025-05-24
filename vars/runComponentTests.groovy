def call(String language) {
    script {
        echo "🔧 Performing component testing for ${language}..."

        switch (language.toLowerCase()) {
            case 'python':
                echo "Running Python component tests..."
                sh 'pytest tests/component_test.py || true'
                break

            case 'java':
                echo "Running Java component tests..."
                sh 'mvn test -Dtest=ComponentTest || true'
                break

            case 'go':
                echo "Running Go component tests..."
                sh 'go test tests/component_test.go || true'
                break

            case 'node':
                echo "Running Node.js component tests..."
                sh 'npx jest tests/component.test.js || true'
                break

            case 'rust':
                echo "Running Rust component tests..."
                sh 'cargo test --test component_test || true'
                break

            default:
                echo "⚠️ Language unknown or unsupported for component testing. Skipping."
                break
        }

        echo "✅ Component testing completed for ${language}."
    }
}
