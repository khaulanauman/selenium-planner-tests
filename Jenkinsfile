pipeline {
    agent any

    stages {
        stage('Clone Code') {
            steps {
                git 'https://github.com/khaulanauman/selenium-planner-tests'
            }
        }

        stage('Run Selenium Tests') {
            steps {
                script {
                    docker.image('markhobson/maven-chrome').inside {
                        sh 'mvn test'
                    }
                }
            }
        }
    }
}
