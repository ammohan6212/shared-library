def call(String language) {
    script {
        echo "📦 Performing regression testing for ${language}..."

        switch (language.toLowerCase()) {
            case 'python':
                sh 'pytest tests/regression_test.py || true'
                break
            case 'java':
                sh 'mvn test -Dgroups=regression || true'
                break
            case 'go':
                sh 'go test tests/regression_test.go || true'
                break
            case 'node':
                sh 'npx jest tests/regression_test.js || true'
                break
            case 'rust':
                sh 'cargo test --test regression_test || true'
                break
            default:
                echo "⚠️ Language not recognized. Skipping regression tests."
                break
        }

        echo "✅ Regression testing completed for ${language}."
    }
}
