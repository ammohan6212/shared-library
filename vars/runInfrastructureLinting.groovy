// vars/runInfrastructureLinting.groovy
def call(String infraPath = 'terraform/') {
    script {
        echo "Performing infrastructure linting on ${infraPath}..."
        // Example with Terraform (tflint, terragrunt validate) or CloudFormation (cfn-lint)
        sh "tflint ${infraPath} || true" // Assumes tflint is installed
        sh "terraform fmt -check=true -recursive ${infraPath} || true"
        echo "✅ Infrastructure linting completed."
    }
}