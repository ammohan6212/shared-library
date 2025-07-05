def call(String language) {
    script {
        echo "🚦 Performing sanity testing for ${language}..."

        switch (language.toLowerCase()) {
            case 'python':
                echo "💡 Would run Python sanity tests using pytest (tests/sanity_test.py)."
                break

            case 'java':
                echo "💡 Would run Java sanity tests using Maven (SanityTest class)."
                break

            case 'go':
                echo "💡 Would run Go sanity tests (tests/sanity_test.go, TestSanity)."
                break

            case 'node':
                echo "💡 Would run Node.js sanity tests using Jest (tests/sanity.test.js)."
                break

            case 'rust':
                echo "💡 Would run Rust sanity tests (tests/sanity_test.rs)."
                break

            default:
                echo "⚠️ Language '${language}' not supported for sanity testing. Skipping..."
                break
        }

        echo "✅ Sanity testing (messages only) completed for ${language}."
    }
}
