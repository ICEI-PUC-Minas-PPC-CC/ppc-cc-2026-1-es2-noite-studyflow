pipeline {
    agent any

    stages {

        stage('Preparação') {
            assert 2 + 2 == 4
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
