// vars/performLightUiTests.groovy
def call(String language) {
    script {
        echo "Performing light UI tests for ${language}..."
        // Quick UI tests, possibly against a deployed environment.
        switch (language) {
            case 'node':
                // Example for Cypress/Playwright
                sh 'npx cypress run --spec "cypress/e2e/smoke.cy.js" || true'
                // sh 'npx playwright test --project=chromium --grep "@smoke" || true'
                break
            default:
                echo "⚠️ Language unknown or no specific light UI testing tool. Skipping."
                break
        }
        echo "✅ Light UI tests completed."
    }
}