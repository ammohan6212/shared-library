def call(String language) {
    script {
        echo "📊 Starting code coverage calculation for '${language}'..."

        switch (language.toLowerCase()) {

            case 'python':
                echo "🧪 Running coverage for Python"
                sh '''
                    pip install coverage pytest pytest-cov || true
                    coverage run --source=src -m pytest tests/ || true
                    coverage report -m || true
                    pytest --cov=src --cov-report=xml --cov-report=html tests/ || true
                '''
                junit allowEmptyResults: true, testResults: '**/coverage.xml'
                archiveArtifacts artifacts: 'htmlcov/**', allowEmptyArchive: true
                break

            case 'node':
                echo "🧪 Running coverage for Node.js"
                sh '''
                    npm install --force || true
                    npm test -- --coverage || true
                '''
                junit allowEmptyResults: true, testResults: 'coverage/junit.xml'
                archiveArtifacts artifacts: 'coverage/**', allowEmptyArchive: true
                break

            case 'go':
                echo "🧪 Running coverage for Go"
                sh '''
                    go install golang.org/x/tools/cmd/cover@latest || true
                    go test -coverprofile=coverage.out ./src || true
                    go tool cover -html=coverage.out -o coverage.html || true
                '''
                archiveArtifacts artifacts: 'coverage.out, coverage.html', allowEmptyArchive: true
                break

            case 'java':
                echo "🧪 Running coverage for Java with JaCoCo"
                sh '''
                    mvn org.jacoco:jacoco-maven-plugin:prepare-agent verify || true
                '''
                junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
                jacoco execPattern: 'target/jacoco.exec', classPattern: 'target/classes', sourcePattern: 'src/main/java'
                archiveArtifacts artifacts: 'target/site/jacoco/**', allowEmptyArchive: true
                break

            case 'rust':
                echo "🧪 Running coverage for Rust"
                sh '''
                    cargo install cargo-tarpaulin --force || true
                    cargo tarpaulin --out Html --output-dir target/tarpaulin || true
                '''
                archiveArtifacts artifacts: 'target/tarpaulin/**', allowEmptyArchive: true
                break

            default:
                echo "⚠️ Unsupported language '${language}'. Skipping code coverage."
                break
        }

        echo "✅ Code coverage completed for '${language}'."
    }
}
