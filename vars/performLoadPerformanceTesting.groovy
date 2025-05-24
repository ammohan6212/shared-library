def call(String language) {
    script {
        echo "🚀 Performing load/performance testing for ${language}..."

        if (language == 'python') {
            // Assumes locust script is at tests/load_test.py
            sh 'locust -f tests/load_test.py --headless -u 50 -r 5 --run-time 1m --host=http://localhost:8000 || true'
        
        } else if (language == 'java') {
            // Assume JMeter script at tests/load_test.jmx
            sh 'jmeter -n -t tests/load_test.jmx -l result.jtl || true'
        
        } else if (language == 'go') {
            // Assume K6 test at tests/load_test.js
            sh 'k6 run tests/load_test.js || go test -bench=. || true'
        
        } else if (language == 'node') {
            // Assume artillery test at tests/load_test.yml
            sh 'artillery run tests/load_test.yml || true'
        
        } else if (language == 'rust') {
            // Assume wrk is installed; target endpoint must be running
            sh 'wrk -t12 -c400 -d30s http://localhost:8000/api/hello || true'
        
        } else {
            echo "⚠️ Unsupported language or no load test available."
        }

        echo "✅ Load/Performance testing completed."
    }
}
