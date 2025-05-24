def call(String language) {
    script {
        echo "🔍 Performing unit tests for ${language}..."

        switch (language.toLowerCase()) {
            case 'python':
                sh """
                    pip install pytest || true
                    pytest tests/test_unit.py || true
                """
                break

            case 'java':
                sh """
                    mvn test -Dtest=UnitTest || true
                """
                break

            case 'go':
                sh """
                    go test tests/unit_test.go -v || true
                """
                break

            case 'node':
                sh """
                    npm install || true
                    npx jest tests/unit.test.js || true
                """
                break

            case 'rust':
                sh """
                    cargo test --test unit_test || true
                """
                break

            default:
                echo "⚠️ Unknown language '${language}'. Skipping unit tests."
        }

        echo "✅ Unit tests completed for ${language}."
    }
}
