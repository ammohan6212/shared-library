def call(String filePath, String gcsBucketPath, String credentialsId = null) {
    script {
        echo "Preparing to push artifact: ${filePath} to ${gcsBucketPath}..."

        // Get current date in a nice format
        def now = new Date()
        def formattedDate = now.format("yyyyMMdd-HHmmss")

        // Extract original filename (without path)
        def file = new File(filePath)
        def originalFileName = file.getName()

        // Create new filename with date
        def updatedFileName = "${originalFileName}-${formattedDate}"
        def updatedFilePath = updatedFileName

        // Copy original file to a new file with date in name
        sh "cp ${filePath} ${updatedFilePath}"

        echo "Uploading file as: ${updatedFileName}"

        if (credentialsId) {
            withCredentials([file(credentialsId: credentialsId, variable: 'GCP_KEY_FILE')]) {
                sh '''
                    echo "Activating GCP service account..."
                    gcloud auth activate-service-account --key-file=$GCP_KEY_FILE
                '''
                sh "gsutil cp ${updatedFilePath} ${gcsBucketPath}/${updatedFileName}"
            }
        } else {
            sh "gsutil cp ${updatedFilePath} ${gcsBucketPath}/${updatedFileName}"
        }

        echo "✅ Artifact pushed to GCS successfully as ${updatedFileName}."
    }
}
