// vars/sendEmailNotification.groovy
def call(String status, String recipients, String customMessage = "") {
    script {
        def subject
        def body

        switch (status) {
            case 'SUCCESS':
                subject = "✅ Build Passed: ${env.JOB_NAME} #${env.BUILD_NUMBER}"
                body = """
                    <p>Hello Team,</p>
                    <p>The Jenkins job <b>${env.JOB_NAME} #${env.BUILD_NUMBER}</b> has completed successfully.</p>
                    ${customMessage ? "<p>${customMessage}</p>" : ""}
                    <p>Link to build: <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
                """
                break
            case 'UNSTABLE':
                subject = "⚠️ UNSTABLE: Pipeline ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}"
                body = """
                    <p>Hello Team,</p>
                    <p>The Jenkins pipeline <b>${env.JOB_NAME} #${env.BUILD_NUMBER}</b> finished in an unstable state.</p>
                    <p>There might be some non-critical issues (e.g., test failures).</p>
                    ${customMessage ? "<p>${customMessage}</p>" : ""}
                    <p>Link to build: <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
                """
                break
            case 'ABORTED':
                subject = "🛑 ABORTED: Pipeline ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}"
                body = """
                    <p>Hello Team,</p>
                    <p>The Jenkins pipeline <b>${env.JOB_NAME} #${env.BUILD_NUMBER}</b> was aborted.</p>
                    ${customMessage ? "<p>${customMessage}</p>" : ""}
                    <p>Link to build: <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
                """
                break
            case 'Alert':
                subject = "🛑 Important: need your presence to continue the pipeline ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}"
                body = """
                    <p>Hello,</p>
                    <p>The Jenkins pipeline <b>${env.JOB_NAME} #${env.BUILD_NUMBER}</b> needs your approval to proceed to the next stage.</p>
                    ${customMessage ? "<p>${customMessage}</p>" : ""}
                    <p>Please log in to Jenkins and approve to continue.</p>
                    <p>Link to build: <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
                """
                break
            case 'FAILURE':
                subject = "❌ FAILED: Pipeline ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}"
                body = """
                    <p>Hello Team,</p>
                    <p>The Jenkins pipeline <b>${env.JOB_NAME} #${env.BUILD_NUMBER}</b> has failed.</p>
                    <p>Please check the build logs for details.</p>
                    ${customMessage ? "<p>${customMessage}</p>" : ""}
                    <p>Link to build: <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
                """
                break
            default:
                subject = "Jenkins Build Notification: ${env.JOB_NAME} #${env.BUILD_NUMBER} (${status})"
                body = """
                    <p>Status: ${status}</p>
                    ${customMessage ? "<p>${customMessage}</p>" : ""}
                    <p>Link: <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
                """
                break
        }

        emailext(
            to: recipients,
            subject: subject,
            body: body,
            mimeType: 'text/html'
        )
        echo "📧 Email notification sent for status: ${status} to ${recipients}"
    }
}
