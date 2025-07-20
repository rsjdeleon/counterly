pipeline {
    agent any

    tools {
        // Ensure this is configured under "Manage Jenkins > Global Tool Configuration"
        jdk 'jdk-21'
    }

    environment {
        GRADLE_OPTS = "-Dorg.gradle.daemon=false"
    }

    stages {
        stage('Checkout') {
            steps {
                // Clone source from GitHub
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo '🛠️ Building the application...'
                sh './gradlew clean build --no-daemon'
            }
        }

        stage('Test') {
            steps {
                echo '🧪 Running unit tests...'
                sh './gradlew test --no-daemon'
            }
        }

        stage('Quality Check') {
            steps {
                echo '🧹 Running code checks (optional)...'
                // Optional: integrate tools like Checkstyle, Detekt, etc.
            }
        }

        stage('Deploy') {
            when {
                anyOf {
                    branch 'main'
                    branch 'develop'
                }
            }
            steps {
                echo "🚀 Deploying for branch: ${env.BRANCH_NAME}"
                // Example deploy: Docker image build & push, SSH, Kubernetes, etc.
                // sh './deploy.sh' or 'docker build/push'
            }
        }
    }

    post {
        success {
            echo '✅ Build succeeded!'
        }
        failure {
            echo '❌ Build failed!'
        }
    }
}
