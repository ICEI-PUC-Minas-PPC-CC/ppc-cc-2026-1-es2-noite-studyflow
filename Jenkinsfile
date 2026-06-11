pipeline {
    agent any

    stages {

        stage('Preparação') {
            
            steps {
                echo 'Iniciando pipeline'
            }
        }

        stage('Build') {
            steps {
                echo 'Compilando aplicação'
            }
        }

        stage('Testes') {
            steps {
                bat 'C:\\Python314\\python.exe -m pytest'
                echo 'Executando testes'
            }
        }

        stage('Finalização') {
            steps {
                echo 'Pipeline finalizado'
            }
        }
    }
}
