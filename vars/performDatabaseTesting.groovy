def call(String language) {
    script {
        echo "🔍 Starting database testing for language: ${language}"

        switch(language.toLowerCase()) {
            case 'python':
                echo "🧪 Would run pytest database tests (tests/database_test.py)."
                break
            case 'java':
                echo "🧪 Would run Maven database tests (mvn test -Dtest=DatabaseTest)."
                break
            case 'go':
                echo "🧪 Would run Go database tests (tests/database_test.go)."
                break
            case 'node':
            case 'javascript':
                echo "🧪 Would run Jest database tests (tests/database_test.js)."
                break
            case 'rust':
                echo "🧪 Would run Rust database tests (cargo test --test database_test)."
                break
            default:
                echo "⚠️ Unsupported or unknown language: '${language}'. Skipping database test."
        }

        echo "✅ Database testing (messages only) completed for ${language}"
    }
}
