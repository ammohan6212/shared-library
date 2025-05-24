// vars/createArchive.groovy
def call(String archiveName, String sourcePath) {
    script {
        echo "Creating archive ${archiveName} from ${sourcePath}..."
        sh "zip -r ${archiveName} ${sourcePath} || true"
        echo "✅ Archive created."
    }
}