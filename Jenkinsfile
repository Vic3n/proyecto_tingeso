pipeline {
    agent any
    tools{
        maven 'maven'
    }
    stages {
        stage('Build JAR File'){
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/Vic3n/proyecto_tingeso']]])
                bat 'mvn clean install -DskipTests'
            }
        }
        stage('Build Docker Image'){
            steps {
                bat 'docker build -t gigavice/calculos_app .'
            }
        }
        stage('Push Docker image'){
            steps {
                withCredentials([string(credentialsId: 'docker_hub_password_id', variable: 'docker_hub_pass')]) {
                    bat 'docker login -u gigavice -p %docker_hub_pass%'
                }
                bat 'docker push gigavice/calculos_app'
            }
        }
    }
    post {
        always {
            bat 'docker logout'
        }
    }
}
