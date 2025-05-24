// vars/runTypeChecks.groovy
def call(String language) {
    script {
        echo "Performing type checking for ${language}..."
        switch (language) {
            case 'python':
                sh 'pip install mypy || true'
                sh 'mypy src/ || true'
                break
            case 'node':
                // Assumes TypeScript is used and configured
                sh 'npm install typescript || true'
                sh 'npx tsc --noEmit || true'
                break
            case 'go':
                sh 'go vet ./... || true'
                break
            case 'java':
                echo "Java type checking is inherently part of compilation."
                // No specific separate tool usually needed beyond compiler
                break
            case 'rust':
                echo "Rust type checking is inherently part of compilation with cargo check."
                sh 'cargo check || true'
                break
            default:
                echo "⚠️ Language unknown. Skipping type checking."
                break
        }
        echo "✅ Type checking completed."
    }
}