pipeline {
    agent any

    environment {
        // Define environment variables
        AWS_DEFAULT_REGION = 'us-east-1'
        ECR_REPO_NAME = 'urlshortener'
        DOCKER_IMAGE = "${ECR_REPO_NAME}:${env.BUILD_ID}"
    }

    stages {
        stage('Checkout') {
            steps {
                // Checkout source code from version control
                git 'git@github.com:bliu1972/URL-shortening.git'
            }
        }

        stage('Build') {
            steps {
                // Build the project using Maven
                sh 'mvn clean package'
            }
        }

        stage('Docker Build') {
            steps {
                // Build Docker image
                script {
                    dockerImage = docker.build(DOCKER_IMAGE)
                }
            }
        }

        stage('Docker Push') {
            steps {
                // Authenticate with AWS ECR
                script {
                    sh 'aws ecr get-login-password --region $AWS_DEFAULT_REGION | docker login --username AWS --password-stdin 058264367850.dkr.ecr.$AWS_DEFAULT_REGION.amazonaws.com'
                }
                // Push Docker image to ECR
                script {
                    dockerImage.push()
                }
            }
        }

        stage('Deploy to AWS') {
            steps {
                // Deploy the Docker image to AWS ECS or Elastic Beanstalk
                script {
                    // For ECS
                    sh """
                    // aws ecs update-service --cluster your-cluster-name --service your-service-name --force-new-deployment
                    sh 'eb deploy Shipping-env'
                    """
                    // For Elastic Beanstalk
                    // sh 'eb deploy your-environment-name'
                }
            }
        }
    }

    post {
        always {
            // Clean up workspace
            cleanWs()
        }
    }
}

