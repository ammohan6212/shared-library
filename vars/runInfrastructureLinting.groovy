// vars/runInfrastructureLinting.groovy
def call(String infraPath = 'terraform/') {
    script {
        echo "Performing infrastructure linting on ${infraPath}..."
        echo "here infrastrcutre lintint happens here"
    }
}