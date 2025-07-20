pipeline {
    agent any

    environment {
        MAVEN_OPTS = "-Dmaven.repo.local=.m2/repository"
    }

    tools {
        // Optional: define this only if "Maven 3.9" is configured in Jenkins tools
        maven 'Maven 3.9'
    }

    stages {
        stage('Checkout') {
            steps {
                echo '📥 Checking out code...'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo '⚙️ Running mvn clean install...'
                sh 'mvn clean install -B -U'
            }
        }

        stage('Test') {
            steps {
                echo '🧪 Running unit tests...'
                sh 'mvn test -B'
            }
        }

        // Optional: Include this if you plan to deploy
        // stage('Deploy') {
        //     when {
        //         branch 'main'
        //     }
        //     steps {
        //         echo '🚀 Deploying artifact...'
        //         // Example: sh './deploy.sh' or Docker push, etc.
        //     }
        // }
    }

    post {
        success {
            echo '✅ Build completed successfully!'
        }
        failure {
            echo '❌ Build failed!'
        }
    }
}
