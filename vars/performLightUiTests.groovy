// vars/performLightUiTests.groovy
def call(String language) {
    script {
        echo "✨ Performing light UI tests for ${language}..."

        switch (language.toLowerCase()) {
            case 'node':
                echo "🧪 Would run Cypress smoke test (cypress/e2e/smoke.cy.js)."
                echo "💡 Alternatively: would run Playwright smoke tests (e.g., @smoke tag)."
                break

            default:
                echo "⚠️ Language unknown or no specific light UI testing tool configured. Skipping."
                break
        }

        echo "✅ Light UI testing (messages only) completed."
    }
}
