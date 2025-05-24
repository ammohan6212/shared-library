// vars/sendEmailNotification.groovy
// Requires the Email Extension Plugin configured in Jenkins
def call(String status, String recipients) {
    script {
        def subject
        def body

        switch (status) {
            case 'SUCCESS':
                subject = "✅ Build Passed: ${env.JOB_NAME} #${env.BUILD_NUMBER}"
                body = """
                    <p>Hello Team,</p>
                    <p>The Jenkins job <b>${env.JOB_NAME} #${env.BUILD_NUMBER}</b> has completed successfully.</p>
                    <p>Link to build: <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
                """
                break
            case 'UNSTABLE':
                subject = "⚠️ UNSTABLE: Pipeline ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}"
                body = """
                    <p>Hello Team,</p>
                    <p>The Jenkins pipeline <b>${env.JOB_NAME} #${env.BUILD_NUMBER}</b> finished in an unstable state.</p>
                    <p>There might be some non-critical issues (e.g., test failures).</p>
                    <p>Link to build: <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
                """
                break
            case 'ABORTED':
                subject = "🛑 ABORTED: Pipeline ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}"
                body = """
                    <p>Hello Team,</p>
                    <p>The Jenkins pipeline <b>${env.JOB_NAME} #${env.BUILD_NUMBER}</b> was aborted.</p>
                    <p>Link to build: <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
                """
                break
            case 'Alert':
                subject = "🛑 Important: need your presence  to continue the pipelie  ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}"
                body = """
                    <p>Hello ,</p>
                    <p>The Jenkins pipeline <b>${env.JOB_NAME} #${env.BUILD_NUMBER}</b> need your presence to continue to the next stage</p>
                    <p> please login to the jenkins and approve to go to the next stage</P>
                """
                break
            case 'FAILURE':
                subject = "❌ FAILED: Pipeline ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}"
                body = """
                    <p>Hello Team,</p>
                    <p>The Jenkins pipeline <b>${env.JOB_NAME} #${env.BUILD_NUMBER}</b> has failed.</p>
                    <p>Please check the build logs for details.</p>
                    <p>Link to build: <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
                """
                break
            default:
                subject = "Jenkins Build Notification: ${env.JOB_NAME} #${env.BUILD_NUMBER} (${status})"
                body = "Status: ${status}. Link: ${env.BUILD_URL}"
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