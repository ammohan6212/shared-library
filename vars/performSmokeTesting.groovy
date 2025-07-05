// vars/performSmokeTesting.groovy
def call(String language) {
    script {
        echo "🚀 Performing smoke testing for ${language}..."

        switch (language.toLowerCase()) {
            case 'python':
                echo "💨 Would run pytest smoke test (tests/smoke_test.py)."
                break

            case 'java':
                echo "💨 Would run Maven smoke test (SmokeTest class)."
                break

            case 'go':
                echo "💨 Would run Go smoke test (tests/smoke_test.go)."
                break

            case 'node':
                echo "💨 Would run Jest smoke test (tests/smoke.test.js)."
                break

            case 'rust':
                echo "💨 Would run Rust smoke test (cargo test --test smoke)."
                break

            default:
                echo "⚠️ Language not recognized. Would run fallback smoke test using curl on health endpoint."
                break
        }

        echo "✅ Smoke testing (messages only) completed for ${language}."
    }
}
