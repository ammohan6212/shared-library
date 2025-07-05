def call(String language) {
    script {
        echo "📦 Performing integration testing for ${language}..."

        switch (language.toLowerCase()) {
            case 'node':
                echo "🧪 Would run Jest integration tests (tests/integration_test.js)."
                break

            case 'python':
                echo "🧪 Would run pytest integration tests (tests/integration_test.py)."
                break

            case 'java':
                echo "🧪 Would run Maven integration tests (mvn test -Dtest=IntegrationTest)."
                echo "ℹ️ Alternatively: mvn failsafe:integration-test."
                break

            case 'go':
                echo "🧪 Would run Go integration tests (tests/integration_test.go)."
                break

            case 'rust':
                echo "🧪 Would run Rust integration tests (cargo test --test integration_test)."
                break

            default:
                echo "⚠️ Language unknown. Skipping integration tests."
        }

        echo "✅ Integration testing (messages only) completed for ${language}."
    }
}
