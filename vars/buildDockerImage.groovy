def call(String imageName, String version, String dockerfileDir = '.') {
    script {
        def fullTag = "${imageName}:${version}"
        echo "🚀 Building Docker image ${fullTag} from ${dockerfileDir}..."
        def dockerImage = docker.build(fullTag, dockerfileDir)
        echo "✅ Docker image ${fullTag} built successfully."
        return dockerImage
    }
}
