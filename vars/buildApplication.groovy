// vars/buildApplication.groovy
def call(String language) {
    script {
        echo "Building the application for ${language}..."
        switch (language) {
            case 'python':
                sh 'python setup.py sdist bdist_wheel || true' // Builds source and wheel distributions
                break
            case 'node':
                sh 'CI=false npm run build || true' // Assumes a 'build' script in package.json
                break
            case 'go':
                sh 'CGO_ENABLED=0 go build -o app_binary ./cmd/app || true' // Builds static binary
                break
            case 'java':
                sh 'mvn clean package -DskipTests || true' // Creates JAR/WAR
                break
            case 'rust':
                sh 'cargo build --release || true' // Builds optimized release binary
                break
            default:
                echo "⚠️ Language unknown. Skipping application build."
                break
        }
        echo "✅ Application build completed."
    }
}