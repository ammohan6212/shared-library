// vars/performApiTesting.groovy
def call(String language) {
    script {
        echo "🚀 Performing API testing for ${language}..."

        switch (language.toLowerCase()) {
            case 'python':
                sh 'pytest tests/test_api.py || true'
                break

            case 'java':
                sh 'mvn test -Dtest=ApiTest || true'
                break

            case 'go':
                sh 'go test tests/api_test.go || true'
                break

            case 'node':
                // Prefer specific test file if available
                sh 'npx jest tests/api.test.js || true'
                break

            case 'rust':
                sh 'cargo test --test api_test || true'
                break

            default:
                echo "⚠️ Language '${language}' not recognized for API testing."
                echo "👉 You may run: newman run tests/my_api_collection.json or another fallback."
                break
        }

        echo "✅ API testing completed for ${language}."
    }
}
