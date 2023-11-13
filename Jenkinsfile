pipeline {
    agent any

    environment {
        PATH = "/usr/share/maven/bin:$PATH"
        JAVA_HOME = "/usr/lib/jvm/java-17-openjdk-amd64"
    }
    stages {
        stage('Build') {
            steps {
                echo 'Building..'
                sh "mvn clean install"
            }
        }
        stage('SonarQube analysis') {
            steps {
                echo 'Testing..'
                withSonarQubeEnv('sonarqube') {
                    sh "mvn sonar:sonar"
                }
            }
        }
    }
}
