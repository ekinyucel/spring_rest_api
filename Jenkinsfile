#!/usr/bin/env groovy

node {
    stage 'Clone the project'
    git 'https://github.com/Broke116/spring_rest_api'

    dir('spring-jenkins-pipeline') {
        stage("System configurations") {
            sh "java -version"
            sh "mvn -version"
        }
        stage("Build and analysis") {
            sh "mvn package spring-boot:repackage"
        }
    }
}