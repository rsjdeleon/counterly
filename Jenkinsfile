pipeline {
    agent any

    tools {
        maven 'Maven 3.9'  // Make sure you configured this in Jenkins → Global Tool Configuration
    }

    stages {
        stage('Checkout') {
            steps {
                echo '📦 Cloning repository...'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo '🔧 Running mvn install...'
                sh 'mvn clean install -B'
            }
        }
    }

    post {
        success {
            echo '✅ Build completed successfully.'
        }
        failure {
            echo '❌ Build failed.'
        }
    }
}
