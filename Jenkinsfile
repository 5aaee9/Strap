pipeline {
  agent {
    docker {
      image 'gradle:4.10.2-jdk11'
    }

  }
  stages {
    stage('error') {
      steps {
        sh '''

gradle build'''
        archiveArtifacts(artifacts: 'build/libs/*.jar', fingerprint: true)
      }
    }
  }
}