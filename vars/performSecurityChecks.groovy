// vars/performSecurityChecks.groovy
def call(String language) {
    script {
        echo "🛡️ Performing general security checks (DAST, etc.) for ${language}..."

        switch (language.toLowerCase()) {
            case 'python':
                echo "🔎 Would run OWASP ZAP or Bandit for Python (example: zap-cli quick-scan -r http://your-app-url/)."
                break

            case 'java':
                echo "🔎 Would run OWASP ZAP, SonarQube, or FindSecBugs for Java."
                break

            case 'go':
                echo "🔎 Would run gosec or ZAP scan for Go application endpoints."
                break

            case 'node':
                echo "🔎 Would run npm audit, OWASP ZAP, or snyk test for Node.js."
                break

            case 'rust':
                echo "🔎 Would run cargo audit and optionally ZAP or manual DAST for Rust web apps."
                break

            default:
                echo "⚠️ Language '${language}' unknown or no security check script configured. Skipping."
                break
        }

        echo "✅ Security checks (messages only) completed for ${language}."
    }
}
