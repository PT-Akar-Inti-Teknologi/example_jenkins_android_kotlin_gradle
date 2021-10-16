pipeline {
  agent any

  stages {
    stage('Build & Test') {
      agent {
        dockerfile {
          filename 'Dockerfile.jenkins'
          additionalBuildArgs '--build-arg JDK_VERSION=8 --build-arg TARGET_SDK=30 --build-arg GRADLE_VERSION=6.5'
          args '-u root'
          reuseNode true
        }
      }

      steps {
        sh 'pwd'
        sh 'echo $HOME'
        sh 'gradle testDevelopmentDebugUnitTestCoverage'
      }
    }
  }

  post {
    always {
      cleanWs()
    }
  }
}