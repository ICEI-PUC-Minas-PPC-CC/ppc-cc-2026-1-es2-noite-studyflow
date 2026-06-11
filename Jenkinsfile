pipeline {
    agent any

    stages {

        stage('Preparação') {
            steps {
                echo 'Iniciando pipeline'
            }
        }

        stage('Testes') {
            steps {
                bat 'C:\\Python314\\python.exe -m pytest'
            }
        }

        stage('Finalização') {
            steps {
                echo 'Pipeline finalizado'
            }
        }
    }
}
