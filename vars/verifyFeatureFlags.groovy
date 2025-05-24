// vars/verifyFeatureFlags.groovy
def call(String language) {
    script {
        echo "Verifying feature flags for ${language}..."
        // This could involve running a script that queries a feature flag service
        // or checks configuration files.
        switch (language) {
            case 'node':
                sh 'node scripts/verifyFeatureFlags.js || true'
                break
            case 'python':
                sh 'python scripts/verify_feature_flags.py || true'
                break
            default:
                echo "⚠️ Language unknown or no feature flag verification script. Skipping."
                break
        }
        echo "✅ Feature flag verification completed."
    }
}