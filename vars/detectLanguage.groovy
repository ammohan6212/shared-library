// vars/detectLanguage.groovy
def call() {
    script {
        echo "🔍 Detecting language based on dependency files in src/..."
        if (fileExists('requirements.txt')) {
            env.DETECTED_LANG = 'python'
        } else if (fileExists('package.json')) {
            env.DETECTED_LANG = 'node'
        } else if (fileExists('go.mod')) {
            env.DETECTED_LANG = 'go'
        } else if (fileExists('pom.xml') || fileExists('src/build.gradle')) {
            env.DETECTED_LANG = 'java'
        } else if (fileExists('Cargo.toml')) {
            env.DETECTED_LANG = 'rust'
        } else {
            env.DETECTED_LANG = 'unknown'
        }
        echo "✅ Detected Language: ${env.DETECTED_LANG}"
    }
}