// Jenkinsfile (Declarative Pipeline)
// This file is stored in SCM and defines the entire CI/CD process.

pipeline {
    // Defines where the pipeline runs (e.g., on the Jenkins agent)
    agent any

    // 1. Tool Definitions: Define the versions globally for use in stages
    tools {
        // These names must match the configuration in Manage Jenkins > Tools
        jdk 'Java_21_Home'
        maven 'Bundled (Maven 3)'
    }

    // 2. Environment: Define variables used throughout the pipeline.
    environment {
        DOCKER_HUB_USERNAME = 'rishabh720'
        DOCKER_CREDENTIAL_ID = 'docker-hub-credentials-id' // Jenkins ID for Docker Hub credentials
        GIT_REPO_URL = 'https://github.com/Rishabh-Dixit04/SciCalc.git'
        GIT_BRANCH = 'master'

        // Email Recipient for the notification
        EMAIL_RECIPIENT = 'rishabhdixit.in@gmail.com'
        // ----------------------------------

        IMAGE_NAME = 'scientific-calculator'
        IMAGE_TAG = "${env.BUILD_NUMBER}"
    }

    // 3. Stages: The main sequence of steps in the CI process.
    stages {
        // Stage 1: Checkout/SCM (Pull GitHub repo)
        stage('Pull GitHub Repo') {
            steps {
                echo '1. Pulling latest code from GitHub...'
                // Use script block for robust SCM checkout via variables
                script {
                    checkout([
                        $class: 'GitSCM',
                        branches: [[name: "${env.GIT_BRANCH}"]],
                        userRemoteConfigs: [[url: "${env.GIT_REPO_URL}"]]
                    ])
                }
            }
        }

        // Stage 2: Build and Test (Run test case)
        stage('Run Test and Build') {
            steps {
                echo '2. Running JUnit tests and building with Maven...'

                // CRITICAL FIX: Explicitly set tool environment variables for the shell session (Required for stability)
                script {
                    def mavenHome = tool 'Bundled (Maven 3)'
                    def javaHome = tool 'Java_21_Home'

                    withEnv(["MAVEN_HOME=${mavenHome}", "JAVA_HOME=${javaHome}"]) {
                        // mvn clean install runs compilation and test cases
                        sh 'mvn clean install'
                    }
                }
            }
        }

        // Stage 3: Containerize (Build image)
        stage('Build Docker Image') {
            steps {
                echo "3. Building Docker image: ${env.DOCKER_HUB_USERNAME}/${env.IMAGE_NAME}:${env.IMAGE_TAG}"
                sh "docker build -t ${env.DOCKER_HUB_USERNAME}/${env.IMAGE_NAME}:${env.IMAGE_TAG} -f Dockerfile ."
            }
        }

        // Stage 4: Docker Push
        stage('Push to Docker Hub') {
            steps {
                echo '4. Pushing image to Docker Hub...'
                withCredentials([usernamePassword(credentialsId: env.DOCKER_CREDENTIAL_ID, passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USER')]) {
                    sh "docker login -u ${env.DOCKER_USER} -p ${env.DOCKER_PASSWORD}"

                    // Push unique tag
                    sh "docker push ${env.DOCKER_HUB_USERNAME}/${env.IMAGE_NAME}:${env.IMAGE_TAG}"

                    // Push 'latest' tag
                    sh "docker tag ${env.DOCKER_HUB_USERNAME}/${env.IMAGE_NAME}:${env.IMAGE_TAG} ${env.DOCKER_HUB_USERNAME}/${env.IMAGE_NAME}:latest"
                    sh "docker push ${env.DOCKER_HUB_USERNAME}/${env.IMAGE_NAME}:latest"
                }
            }
        }
    }

    // Send email about pipeline success/failure
    post {
        // Runs regardless of success/failure
        always {
            echo 'Pipeline execution complete.'
        }
        // Runs only upon pipeline failure
        failure {
            // Requires Jenkins Email Extension Plugin configuration
            mail to: "${env.EMAIL_RECIPIENT}",
                 subject: "FAILURE: Jenkins Build #${env.BUILD_NUMBER} for ${env.JOB_NAME}",
                 body: "Build failed! Check console output at: ${env.BUILD_URL}"
        }
        // Runs only upon pipeline success
        success {
             mail to: "${env.EMAIL_RECIPIENT}",
                 subject: "SUCCESS: Jenkins Build #${env.BUILD_NUMBER} for ${env.JOB_NAME}",
                 body: "Build successful! Docker image pushed."
        }
    }
}

