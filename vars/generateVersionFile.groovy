def call(String cloud = 'aws', String bucket, String credentialsId) {
    script {
        echo "📦 Generating version file..."

        def version     = sh(script: "git describe --tags --abbrev=0 || git rev-parse --short HEAD", returnStdout: true).trim()
        def commit      = sh(script: "git rev-parse HEAD", returnStdout: true).trim()
        def branch      = env.BRANCH_NAME ?: 'unknown'
        def buildNumber = env.BUILD_NUMBER
        def buildDate   = sh(script: "date -u +'%Y-%m-%dT%H:%M:%SZ'", returnStdout: true).trim()

        def versionFileName = "version-${branch}-${version}.txt"
        def versionContent = """
            VERSION=${version}
            BUILD_NUMBER=${buildNumber}
            BUILD_DATE=${buildDate}
            GIT_COMMIT=${commit}
            GIT_BRANCH=${branch}
            BUILD_BY=jenkins
            JENKINS_JOB=${env.JOB_NAME}
            JENKINS_BUILD_URL=${env.BUILD_URL}
        """.stripIndent()

        writeFile file: versionFileName, text: versionContent
        echo "✅ Created: ${versionFileName}\n${versionContent}"

        if (cloud == 'aws') {
            withCredentials([[ 
                $class: 'AmazonWebServicesCredentialsBinding',
                credentialsId: credentialsId
            ]]) {
                sh "aws s3 cp ${versionFileName} s3://${bucket}/${versionFileName}"
            }
            echo "✅ Uploaded to AWS S3: s3://${bucket}/${versionFileName}"
        }
        else if (cloud == 'gcp') {
            withCredentials([file(credentialsId: credentialsId, variable: 'GOOGLE_APPLICATION_CREDENTIALS')]) {
                sh "gcloud auth activate-service-account --key-file=$GOOGLE_APPLICATION_CREDENTIALS"
                sh "gsutil cp ${versionFileName} gs://${bucket}/${versionFileName}"
            }
            echo "✅ Uploaded to GCP GCS: gs://${bucket}/${versionFileName}"
        }
        else {
            error "❌ Unsupported cloud provider: ${cloud}"
        }
    }
}
