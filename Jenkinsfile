pipeline {
	agent any
	tools {
	    maven 'Maven 3.6.3' 
        jdk 'jdk8'
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