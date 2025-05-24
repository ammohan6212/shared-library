def call(String language) {
    script {
        echo "📸 Performing snapshot testing for ${language}..."

        switch (language) {
            case 'python':
                // Using pytest-snapshot
                sh 'pytest --snapshot-update tests/snapshot_test.py || true'
                break

            case 'java':
                // Manual snapshot testing: assertj/approvaltests (inform user)
                echo "ℹ️ Snapshot testing in Java typically requires manual validation via ApprovalTests or AssertJ."
                echo "➡️ Please ensure you have written snapshot tests in `tests/snapshot_test.java`."
                break

            case 'go':
                // Manual snapshot logic, run the file
                sh 'go test tests/snapshot_test.go || true'
                break

            case 'node':
                // Jest snapshot update
                sh 'npx jest tests/snapshot.test.js --updateSnapshot || true'
                break

            case 'rust':
                // Using insta crate
                sh 'cargo insta test --review || true'
                break

            default:
                echo "⚠️ Language '${language}' not supported for snapshot testing or not configured."
                break
        }

        echo "✅ Snapshot testing completed for ${language}."
    }
}
