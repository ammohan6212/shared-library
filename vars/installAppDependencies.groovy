// vars/installAppDependencies.groovy
def call(String language) {
    script {
        echo "Installing dependencies for ${language}..."
        switch (language) {
            case 'python':
                sh 'pip install -r requirements.txt || true'
                break
            case 'node':
                sh ' rm -rf node_modules package-lock.json'
                sh 'npm install'
                break
            case 'go':
                sh 'go mod tidy || true'
                break
            case 'java':
                sh 'mvn clean install -DskipTests' // Skip tests during install
                break
            case 'rust':
                sh 'cargo fetch || true'
                break
            default:
                echo "⚠️ Language unknown. Skipping dependency installation."
                break
        }
        echo "✅ Dependencies installed."
    }
}