def call(String branchName, String repoUrl) {
    try {
        echo "📦 Cloning repo: ${repoUrl} on branch: ${branchName}"

        // Clone full repo
        git branch: branchName, url: repoUrl, changelog: false, poll: false

        // Fetch all tags, ensure full history
        sh 'git fetch --tags --unshallow || git fetch --tags'

        // Check if tags exist
        def version = sh(
            script: """
                git describe --tags \$(git rev-list --tags --max-count=1) || echo "v0.0.0"
            """,
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
