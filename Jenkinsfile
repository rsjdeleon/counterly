pipeline {
    agent any

    tools {
        maven 'Maven 3.9'  // Make sure you configured this in Jenkins → Global Tool Configuration
    }

    environment {
        INVENTORY_IMAGE = "rsjdeleon/inventory-service:latest"
        ORDER_IMAGE = "rsjdeleon/order-service:latest"
    }

    stages {
        stage('Check Prerequisites') {
            steps {
                echo '🔎 Checking for minikube and kubectl...'
                sh '''
                    if ! [ -x "/opt/homebrew/bin/minikube" ]; then
                        echo "❌ ERROR: minikube not found at /opt/homebrew/bin/minikube"
                        echo "Please ensure minikube is installed and accessible to the Jenkins agent at this path."
                        exit 1
                    fi
                    if ! [ -x "/opt/homebrew/bin/kubectl" ]; then
                        echo "❌ ERROR: kubectl not found at /opt/homebrew/bin/kubectl"
                        echo "Please ensure kubectl is installed and accessible to the Jenkins agent at this path."
                        exit 1
                    fi
                '''
            }
        }

        stage('Checkout') {
            steps {
                echo '📦 Cloning repository...'
                checkout scm
            }
        }

        stage('Build Maven Project') {
            steps {
                echo '🔧 Running mvn install...'
                sh 'mvn clean install -B'
            }
        }

        stage('Build and Push Docker Images to Minikube') {
            steps {
                echo '🐳 Building and pushing Docker images to Minikube...'
                // Point shell to minikube's docker-daemon
                sh 'eval $(/opt/homebrew/bin/minikube -p minikube docker-env)'

                // Build and push inventory-service image
                sh "docker build -t ${env.INVENTORY_IMAGE} -f inventory/src/main/docker/Dockerfile --build-arg JAR_FILE=target/inventory-1.0-SNAPSHOT.jar inventory"

                // Build and push order-service image
                sh "docker build -t ${env.ORDER_IMAGE} -f order/src/main/docker/Dockerfile --build-arg JAR_FILE=target/counterly.order-1.0-SNAPSHOT.jar order"
            }
        }

        stage('Deploy to Minikube') {
            steps {
                echo '🚀 Deploying to Minikube...'
                sh '/opt/homebrew/bin/kubectl apply -f k8s/'
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
