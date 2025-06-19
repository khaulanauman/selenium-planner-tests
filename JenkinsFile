pipeline {
    agent {
        docker {
            image 'markhobson/maven-chrome'
        }
    }

    stages {
        stage('Clone Test Repo') {
            steps {
                git 'https://github.com/khaulanauman/selenium-planner-tests'
            }
        }

        stage('Run Tests') {
            steps {
                sh 'mvn test'
            }
        }
    }
}
