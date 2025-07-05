def call(String language) {
    script {
        echo "🚀 Performing chaos testing after deployment for ${language}..."

        if (language == 'python') {
            echo "💥 Would run: chaos run tests/chaos_test.json"
        } else if (language == 'java') {
            echo "💥 Would run: ./tests/chaos_test.sh (e.g., chaos-monkey-spring-boot script)"
        } else if (language == 'go') {
            echo "💥 Would run: go run tests/chaos_test.go"
        } else if (language == 'node') {
            echo "💥 Would run: node tests/chaos_test.js"
        } else if (language == 'rust') {
            echo "💥 Would run: cargo run --bin chaos_test"
        } else {
            echo "⚠️ Chaos testing is not defined for this language. Skipping."
        }

        echo "✅ Chaos testing (messages only) completed for ${language}."
    }
}
