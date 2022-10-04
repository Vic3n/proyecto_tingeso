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
                bat 'docker build -t gigavice/cal_app .'
            }
        }
        stage('Push docker image'){
            steps {
                withCredentials([string(credentialsId: 'dockerhubpassword', variable: 'docker_pass')]) {
                    bat 'docker login -u gigavice -p ${docker_pass}'
                }
                bat 'docker push gigavice/cal_app'
            }
        }
    }
    post {
        always {
            bat 'docker logout'
        }
    }
}
