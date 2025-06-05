def call(String dockerImageTag) {
    script {
        sh '''
            for img in $(grep "^FROM" Dockerfile | awk '{print $2}' | sort -u); do
                echo "Scanning $img..."
                safe_name=$(echo $img | tr '/:' '_')
                tern report -i "$img" -o "tern_report_${safe_name}.txt"
            done
        '''
    }
}
