pipeline {
    agent any

    stages {

        stage('Preparação') {
            
            steps {
                assert 2 + 2 == 5
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
