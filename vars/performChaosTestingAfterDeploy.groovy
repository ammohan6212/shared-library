def call(String language) {
    script {
        echo "🚀 Performing chaos testing after deployment for ${language}..."

        if (language == 'python') {
            sh 'chaos run tests/chaos_test.json'
        } else if (language == 'java') {
            // Example: chaos-monkey-spring-boot or shell script based chaos test
            sh './tests/chaos_test.sh' // make sure the file exists and is executable
        } else if (language == 'go') {
            // Kill/restart simulation or use a Go-based chaos test
            sh 'go run tests/chaos_test.go'
        } else if (language == 'node') {
            // Chaos logic via custom Node.js script
            sh 'node tests/chaos_test.js'
        } else if (language == 'rust') {
            sh 'cargo run --bin chaos_test' // or a separate binary or integration test
        } else {
            echo "⚠️ Chaos testing is not defined for this language."
        }

        echo "✅ Chaos testing after deployment completed."
    }
}
