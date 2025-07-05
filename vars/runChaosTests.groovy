// vars/runChaosTests.groovy
def call(String language) {
    script {
        echo "🌪️ Starting chaos tests for ${language}..."

        switch (language.toLowerCase()) {
            case 'python':
                echo "⚡ Would run Python-based chaos tests (e.g., using Chaos Toolkit)."
                break

            case 'node':
                echo "⚡ Would run Node.js chaos tests (e.g., Gremlin SDK or custom scripts)."
                break

            case 'java':
                echo "⚡ Would run Java chaos tests (e.g., Chaos Monkey for Spring Boot)."
                break

            case 'go':
                echo "⚡ Would run Go chaos tests (e.g., using LitmusChaos or custom fault injection tools)."
                break

            case 'rust':
                echo "⚡ Would run Rust chaos tests (often via infrastructure-level chaos tools like Gremlin or Litmus)."
                break

            default:
                echo "⚠️ Language '${language}' unknown or no chaos test setup configured. Skipping."
                break
        }

        echo "✅ Chaos tests (messages only) completed for ${language}."
    }
}
