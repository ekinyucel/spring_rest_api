#!/usr/bin/env groovy

node {
    dir('spring-jenkins-pipeline') {
        stage("System configurations") {
            sh "java -version"
            sh "mvn -version"
        }
        stage('Checkout') {
            git 'https://github.com/Broke116/spring_rest_api'
        }
        stage("Build") {
            sh "mvn clean -DskipTests -X"
        }
    }
}