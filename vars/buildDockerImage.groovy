// vars/buildDockerImage.groovy
def call(String imageName, String version, String dockerfileDir = '.') {
    script {
        echo "Building Docker image ${imageName}:${version} from ${dockerfileDir}..."
        sh "docker build -t ${imageName}:${version} ${dockerfileDir}"
        echo "✅ Docker image built."
    }
}