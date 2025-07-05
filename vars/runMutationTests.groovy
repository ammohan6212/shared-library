def call(String language) {
    script {
        echo "🔬 Starting mutation testing for language: ${language}..."

        switch (language.toLowerCase()) {
            case 'python':
                echo "🧬 Would run mutmut for Python (src/, tests/)."
                break

            case 'java':
                echo "🧬 Would run Pitest for Java using Maven."
                break

            case 'go':
                echo "🧬 Would run go-mutesting for Go (src, tests)."
                break

            case 'node':
                echo "🧬 Would run Stryker Mutator for Node.js."
                break

            case 'rust':
                echo "🧬 Would run cargo-mutagen for Rust (tests)."
                break

            default:
                echo "⚠️ Unknown language '${language}'. Mutation testing skipped."
                break
        }

        echo "✅ Mutation testing (messages only) completed for ${language}."
    }
}
