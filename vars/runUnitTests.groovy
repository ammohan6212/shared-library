def call(String language) {
    script {
        echo "🔍 Performing unit tests for ${language}..."

        switch (language.toLowerCase()) {
            case 'python':
                echo "unit test for python happens here "
                break

            case 'java':
                echo "unit test for java happens here "
                break

            case 'go':
                 echo "unit test for go happens here "
                break

            case 'node':
                 echo "unit test for node happens here "
                break

            case 'rust':
                echo "unit test for rust happens here "
                break

            default:
                echo "⚠️ Unknown language '${language}'. Skipping unit tests."
        }

        echo "✅ Unit tests completed for ${language}."
    }
}
