pipeline {
    agent any

    environment {
        AWS_ACCESS_KEY_ID     = credentials('AWS_ACCESS_KEY_ID')
        AWS_SECRET_ACCESS_KEY = credentials('AWS_SECRET_ACCESS_KEY')
        AWS_REGION            = 'af-south-1'
        ECR_REGISTRY          = '591120834795.dkr.ecr.af-south-1.amazonaws.com'
        ECR_REPO              = 'todo-api'
        IMAGE_TAG             = 'latest'
        EC2_HOST              = '51.21.128.14'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Test') {
            steps {
                dir('todo-api') {
                    sh 'mvn test'
                }
            }
        }

        stage('Build JAR') {
            steps {
                dir('todo-api') {
                    sh 'mvn -B package -DskipTests'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                dir('todo-api') {
                    sh "docker build -t $ECR_REGISTRY/$ECR_REPO:$IMAGE_TAG ."
                }
            }
        }

        stage('Push to ECR') {
            steps {
                sh """
                    aws ecr get-login-password --region $AWS_REGION | \
                    docker login --username AWS --password-stdin $ECR_REGISTRY
                    docker push $ECR_REGISTRY/$ECR_REPO:$IMAGE_TAG
                """
            }
        }

        stage('Deploy to EC2') {
            steps {
                sshagent(['EC2_SSH_KEY']) {
                    sh """
                        ssh -o StrictHostKeyChecking=no ubuntu@$EC2_HOST '
                            aws ecr get-login-password --region af-south-1 | \
                            docker login --username AWS --password-stdin $ECR_REGISTRY
                            docker stop todo-api || true
                            docker rm todo-api || true
                            docker pull $ECR_REGISTRY/$ECR_REPO:$IMAGE_TAG
                            docker run -d --name todo-api -p 8080:8080 $ECR_REGISTRY/$ECR_REPO:$IMAGE_TAG
                        '
                    """
                }
            }
        }
    }

    post {
        always {
            junit '**/target/surefire-reports/*.xml'
        }
    }
}