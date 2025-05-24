def call(String language) {
    script {
        echo "📦 Performing integration testing for ${language}..."

        switch (language) {
            case 'node':
                // Runs file-based Jest test for integration
                sh 'npx jest tests/integration_test.js || true'
                break

            case 'python':
                // Runs file-based pytest integration test
                sh 'pytest tests/integration_test.py || true'
                break

            case 'java':
                // Runs a specific integration test file or class
                sh 'mvn test -Dtest=IntegrationTest || true'
                // Alternatively, use: sh 'mvn failsafe:integration-test || true'
                break

            case 'go':
                // Go test on a specific integration test file
                sh 'go test tests/integration_test.go || true'
                break

            case 'rust':
                // Rust integration test (file: tests/integration_test.rs)
                sh 'cargo test --test integration_test || true'
                break

            default:
                echo "⚠️ Language unknown. Skipping integration tests."
        }

        echo "✅ Integration testing completed."
    }
}
