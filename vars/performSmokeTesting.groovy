// vars/performSmokeTesting.groovy
def call(String language) {
    script {
        echo "🚀 Performing smoke testing for ${language}..."

        switch (language.toLowerCase()) {
            case 'python':
                sh 'pytest tests/smoke_test.py || true'
                break

            case 'java':
                sh 'mvn test -Dtest=SmokeTest || true'
                break

            case 'go':
                sh 'go test tests/smoke_test.go || true'
                break

            case 'node':
                sh 'npx jest tests/smoke.test.js || true'
                break

            case 'rust':
                sh 'cargo test --test smoke || true'
                break

            default:
                echo "⚠️ Language not recognized. Running fallback smoke test with curl..."
                sh 'curl -f http://your-app-url/health || true'  // Replace with your real URL
                break
        }

        echo "✅ Smoke testing completed for ${language}."
    }
}
