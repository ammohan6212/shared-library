def call(String language) {
    script {
        echo "🔧 Performing component testing for ${language}..."

        switch (language.toLowerCase()) {
            case 'python':
                echo "🧪 Would run Python component tests (tests/component_test.py)."
                break

            case 'java':
                echo "🧪 Would run Java component tests with Maven (ComponentTest)."
                break

            case 'go':
                echo "🧪 Would run Go component tests (tests/component_test.go)."
                break

            case 'node':
                echo "🧪 Would run Node.js component tests with Jest (tests/component.test.js)."
                break

            case 'rust':
                echo "🧪 Would run Rust component tests (cargo test --test component_test)."
                break

            default:
                echo "⚠️ Language unknown or unsupported for component testing. Skipping."
                break
        }

        echo "✅ Component testing (messages only) completed for ${language}."
    }
}
