def call(String branchName, String repoUrl) {
    try {
        echo "📦 Cloning repo: ${repoUrl} on branch: ${branchName}"

        // Clone the specified branch
        git branch: branchName, url: repoUrl

        // Fetch all tags
        sh 'git fetch --tags'

        // Get the latest tag
        def version = sh(
            script: "git describe --tags \$(git rev-list --tags --max-count=1)",
            returnStdout: true
        ).trim()

        env.version = version
        echo "✅ Version detected: ${env.version}"
    } catch (err) {
        echo "❌ Git clone or version fetch failed: ${err}"
        currentBuild.result = 'FAILURE'
        error("Stopping pipeline")
    }
}
