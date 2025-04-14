pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/sanflor/calcular-salario.git'
            }
        }

        stage('Build') {
            steps {
                sh './mvnw clean compile'
            }
        }

        stage('Test') {
            steps {
                sh './mvnw test'
            }
        }
    }

    post {
        always {
            junit '**/target/surefire-reports/*.xml'
        }
    }
}
