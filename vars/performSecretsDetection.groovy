// vars/performSecretsDetection.groovy
def call(String scanPath = '.') {
    script {
        echo "Performing secrets detection on ${scanPath}..."
        // Tools like Gitleaks, Trufflehog, detect-secrets
        sh """
            cd ~
            # Gitleaks (requires Gitleaks executable)
            ~/gitleaks/gitleaks detect --source=${scanPath} --verbose --redact || true

            # Trufflehog (requires trufflehog executable)
            trufflehog filesystem ${scanPath} --no-update || true
        """
        echo "✅ Secrets detection completed."
    }
}