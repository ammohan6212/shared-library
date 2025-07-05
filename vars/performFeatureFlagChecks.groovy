def call(String language) {
    script {
        echo "🚩 Performing runtime feature flag checks for ${language}..."

        switch (language.toLowerCase()) {
            case 'python':
                echo "🧪 Would run: pytest tests/featureflag_test.py"
                break

            case 'java':
                echo "🧪 Would run: mvn test -Dtest=FeatureFlagTest"
                break

            case 'go':
                echo "🧪 Would run: go test tests/featureflag_test.go"
                break

            case 'node':
                echo "🧪 Would run: npx jest tests/featureflag_test.js"
                break

            case 'rust':
                echo "🧪 Would run: cargo test --test featureflag_test"
                break

            default:
                echo "⚠️ Language '${language}' unknown or no runtime feature flag check script. Skipping."
                break
        }

        echo "✅ Runtime feature flag checks (messages only) completed for ${language}."
    }
}
