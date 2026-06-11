pipeline {
    agent any

    stages {

        stage('Preparação') {
            steps {
                echo 'Iniciando pipeline'
            }
        }

        stage('Diagnostico') {
            steps {
                bat 'whoami'
                bat 'C:\\Python314\\python.exe -m pip list'
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
