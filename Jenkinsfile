pipeline {
  agent {
    docker {
      image 'openjdk:11-jdk'
    }

  }
  stages {
    stage('') {
      steps {
        sh './gradlew build'
        archiveArtifacts(artifacts: 'build/libs/*.jar', fingerprint: true)
      }
    }
  }
}