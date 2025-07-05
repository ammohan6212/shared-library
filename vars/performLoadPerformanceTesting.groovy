def call(String language) {
    script {
        echo "🚀 Performing load/performance testing for ${language}..."

        switch (language.toLowerCase()) {
            case 'python':
                echo "💥 Would run: locust -f tests/load_test.py --headless -u 50 -r 5 --run-time 1m --host=http://localhost:8000"
                break

            case 'java':
                echo "💥 Would run: jmeter -n -t tests/load_test.jmx -l result.jtl"
                break

            case 'go':
                echo "💥 Would run: k6 run tests/load_test.js or go test -bench=."
                break

            case 'node':
                echo "💥 Would run: artillery run tests/load_test.yml"
                break

            case 'rust':
                echo "💥 Would run: wrk -t12 -c400 -d30s http://localhost:8000/api/hello"
                break

            default:
                echo "⚠️ Unsupported language or no load test available."
                break
        }

        echo "✅ Load/Performance testing (messages only) completed for ${language}."
    }
}
