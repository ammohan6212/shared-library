def call(String language) {
    script {
        echo "📸 Performing snapshot testing for ${language}..."

        switch (language.toLowerCase()) {
            case 'python':
                echo "🧪 Would run pytest snapshot update (pytest-snapshot) for Python (tests/snapshot_test.py)."
                break

            case 'java':
                echo "ℹ️ Snapshot testing in Java usually uses ApprovalTests or AssertJ."
                echo "➡️ Please ensure snapshot tests are in `tests/snapshot_test.java`."
                break

            case 'go':
                echo "🧪 Would run Go snapshot test (tests/snapshot_test.go)."
                break

            case 'node':
                echo "🧪 Would run Jest snapshot update (npx jest --updateSnapshot) for Node.js."
                break

            case 'rust':
                echo "🧪 Would run cargo insta snapshot test for Rust."
                break

            default:
                echo "⚠️ Language '${language}' not supported for snapshot testing or not configured."
                break
        }

        echo "✅ Snapshot testing (messages only) completed for ${language}."
    }
}
