public class Jenkinsfile {

    pipeline {
        agent any

        tools {
            maven 'Maven 3.8.6'
            jdk 'JDK 21'
        }

        stages {
            stage('Checkout') {
                steps {
                    git 'https://github.com/salarcovargas/calcular-salario.git'
                }
            }

            stage('Build') {
                steps {
                    sh 'mvn clean compile'
                }
            }

            stage('Run Tests') {
                steps {
                    sh 'mvn test'
                }
            }
        }
    }
}
