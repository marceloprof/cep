pipeline {
	agent any
	tools {
	    maven 'maven' 
        jdk 'jdk'
	}
    stages {
        stage('build') {
	        steps {
	            sh 'mvn clean compile'
	        }
        }
        stage('test') {
           steps {
               sh 'mvn verify'
           }
        }
    }
}