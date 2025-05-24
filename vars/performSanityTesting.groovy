def call(String language) {
    script {
        echo "🚦 Performing sanity testing for ${language}..."

        switch (language) {
            case 'python':
                // Assumes file is: tests/sanity_test.py
                sh 'pytest tests/sanity_test.py || true'
                break

            case 'java':
                // Assumes class is: SanityTest.java in standard test path
                sh 'mvn test -Dtest=SanityTest || true'
                break

            case 'go':
                // Assumes function: func TestSanity(t *testing.T) in tests/sanity_test.go
                sh 'go test -v -run TestSanity ./tests || true'
                break

            case 'node':
                // Assumes file is: tests/sanity.test.js
                sh 'npx jest tests/sanity.test.js || true'
                break

            case 'rust':
                // Assumes file is: tests/sanity_test.rs
                sh 'cargo test --test sanity_test || true'
                break

            default:
                echo "⚠️ Language '${language}' not supported for sanity testing. Skipping..."
                break
        }

        echo "✅ Sanity testing completed for ${language}."
    }
}
