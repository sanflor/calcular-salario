pipeline {
    agent any

    tools {
        maven 'Maven 3.8.6'
        jdk 'JDK 21'
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/sanflor/calcular-salario.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
    }

    post {
        always {
            junit '**/target/surefire-reports/*.xml'
        }
    }
}
