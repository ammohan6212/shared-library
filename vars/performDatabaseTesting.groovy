def call(String lang = '') {
    script {
        echo "🔍 Starting database testing for language: ${lang}"

        switch(lang.toLowerCase()) {
            case 'python':
                sh 'pytest tests/database_test.py'
                break
            case 'java':
                sh 'mvn test -Dtest=DatabaseTest'
                break
            case 'go':
                sh 'go test tests/database_test.go'
                break
            case 'node':
            case 'javascript':
                sh 'npx jest tests/database_test.js'
                break
            case 'rust':
                sh 'cargo test --test database_test'
                break
            default:
                echo "⚠️ Unsupported or unknown language: '${lang}'. Skipping database test."
        }

        echo "✅ Database testing completed for ${lang}"
    }
}
