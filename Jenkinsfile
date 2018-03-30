sbtPod { label ->
  def containerName = "convergence-server-cluster-seed"
  runInNode(label) {
  
    stage('SBT Build') {
      injectIvyCredentials()
            
      container('sbt') {
        sh 'sbt compile stage'
      }
    }
    
    stage('Docker Prep') { 
      sh '''
      mkdir target/docker
      cp -a target/universal/stage target/docker
      cp -a Dockerfile target/docker
      '''
    }
    
    stage('Docker Build') {
      container('docker') {
        dir('target/docker') {
          dockerBuild(containerName)
        }
      }
    }

    stage('Docker Push') {
      container('docker') {
        dockerPush(containerName, ["latest", env.GIT_COMMIT])
      }
    }
  }
}
