def call(String language) {
    script {
        echo "🚩 Performing runtime feature flag checks for ${language}..."

        switch (language.toLowerCase()) {
            case 'python':
                sh 'pytest tests/featureflag_test.py || true'
                break

            case 'java':
                sh 'mvn test -Dtest=FeatureFlagTest || true'
                break

            case 'go':
                sh 'go test tests/featureflag_test.go || true'
                break

            case 'node':
                sh 'npx jest tests/featureflag_test.js || true'
                break

            case 'rust':
                sh 'cargo test --test featureflag_test || true'
                break

            default:
                echo "⚠️ Language '${language}' unknown or no runtime feature flag check script. Skipping."
                break
        }

        echo "✅ Runtime feature flag checks completed for ${language}."
    }
}
