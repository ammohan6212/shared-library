// vars/runUiComponentTests.groovy
def call(String language) {
    script {
        echo "Performing UI/Component testing for ${language}..."
        switch (language) {
            case 'node':
                // Example for Storybook or Cypress component tests
                sh 'npm run test:ui-components || true'
                break
            default:
                echo "⚠️ Language unknown or no specific UI component testing tool. Skipping."
                break
        }
        echo "✅ UI/Component testing completed."
    }
}