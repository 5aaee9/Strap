pipeline {
  agent {
    docker {
      image 'openjdk:11-jdk'
    }

  }
  stages {
    stage('error') {
      steps {
        sh '''

ls && chmod +x ./gradlew && ./gradlew build'''
        archiveArtifacts(artifacts: 'build/libs/*.jar', fingerprint: true)
      }
    }
  }
}