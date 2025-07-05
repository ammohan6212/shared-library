// vars/performApiTesting.groovy
def call(String language) {
    script {
        echo "🚀 Performing API testing for ${language}..."

        switch (language.toLowerCase()) {
            case 'python':
                echo "🧪 Would run pytest for API tests (tests/test_api.py)."
                break

            case 'java':
                echo "🧪 Would run Maven API tests (e.g., mvn test -Dtest=ApiTest)."
                break

            case 'go':
                echo "🧪 Would run Go API tests (tests/api_test.go)."
                break

            case 'node':
                echo "🧪 Would run Jest API tests (tests/api.test.js)."
                break

            case 'rust':
                echo "🧪 Would run Rust API tests (cargo test --test api_test)."
                break

            default:
                echo "⚠️ Language '${language}' not recognized for API testing."
                echo "👉 You may run: newman run tests/my_api_collection.json or another fallback."
                break
        }

        echo "✅ API testing (messages only) completed for ${language}."
    }
}
