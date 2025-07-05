def call(String language) {
    script {
        echo "📦 Performing regression testing for ${language}..."

        switch (language.toLowerCase()) {
            case 'python':
                echo "🧪 Would run pytest regression tests (tests/regression_test.py)."
                break
            case 'java':
                echo "🧪 Would run Maven regression tests (mvn test -Dgroups=regression)."
                break
            case 'go':
                echo "🧪 Would run Go regression tests (tests/regression_test.go)."
                break
            case 'node':
                echo "🧪 Would run Jest regression tests (tests/regression_test.js)."
                break
            case 'rust':
                echo "🧪 Would run Rust regression tests (cargo test --test regression_test)."
                break
            default:
                echo "⚠️ Language not recognized. Skipping regression tests."
                break
        }

        echo "✅ Regression testing (messages only) completed for ${language}."
    }
}
