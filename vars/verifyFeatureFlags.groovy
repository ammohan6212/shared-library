// vars/verifyFeatureFlags.groovy
def call(String language) {
    script {
        echo "🚩 Verifying feature flags for ${language}..."

        switch (language.toLowerCase()) {
            case 'node':
                echo "🔎 Would run: node scripts/verifyFeatureFlags.js"
                break

            case 'python':
                echo "🔎 Would run: python scripts/verify_feature_flags.py"
                break

            default:
                echo "⚠️ Language '${language}' unknown or no feature flag verification script configured. Skipping."
                break
        }

        echo "✅ Feature flag verification (messages only) completed for ${language}."
    }
}
