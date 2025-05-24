def call(String language) {
    script {
        echo "🔬 Starting mutation testing for language: ${language}..."

        switch (language.toLowerCase()) {
            case 'python':
                sh '''
                    echo "Installing and running mutmut..."
                    mutmut run --paths-to-mutate src/ --tests-dir tests/ --test-command "pytest tests/mutation_test.py"
                '''
                break

            case 'java':
                sh '''
                    echo "Running Pitest for Java..."
                    mvn org.pitest:pitest-maven:mutationCoverage
                '''
                break

            case 'go':
                sh '''
                    echo "Running go-mutesting for Go..."
                    go install github.com/zimmski/go-mutesting/cmd/go-mutesting@latest || true
                    go-mutesting --testdir tests ./src
                '''
                break

            case 'node':
                sh '''
                    echo "Running Stryker Mutator for Node.js..."
                    npx stryker run

                '''
                break

            case 'rust':
                sh '''
                    echo "Running cargo-mutagen for Rust..."
                    cargo install mutagen || true
                    cargo mutagen -- --test tests/mutation_test.rs
                '''
                break

            default:
                echo "⚠️ Unknown language '${language}'. Mutation testing skipped."
                break
        }

        echo "✅ Mutation testing completed for ${language}."
    }
}
