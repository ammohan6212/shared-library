def call(String filePath, String gcsBucketPath, String credentialsId = null) {
    script {
        echo "Pushing artifact ${filePath} to ${gcsBucketPath}..."

        if (credentialsId) {
            // Use service account credentials
            withCredentials([file(credentialsId: credentialsId, variable: 'GCP_KEY_FILE')]) {
                sh '''
                    echo "Activating GCP service account..."
                    gcloud auth activate-service-account --key-file=$GCP_KEY_FILE
                '''
                sh "gsutil cp ${filePath} ${gcsBucketPath}/"
            }
        } else {
            // Push without explicit auth (works only if the agent already has gcloud configured)
            sh "gsutil cp ${filePath} ${gcsBucketPath}/"
        }

        echo "✅ Artifact pushed to GCS successfully."
    }
}
