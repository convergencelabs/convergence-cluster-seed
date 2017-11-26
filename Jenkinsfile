node {
  ansiColor('xterm') {
    notifyFor() {
      deleteDir()
      withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'NexusRepo', usernameVariable: 'NEXUS_USER', passwordVariable: 'NEXUS_PASSWORD']]) {
  
        stage 'Checkout'
        checkout scm
  
        gitlabCommitStatus {
          docker.withRegistry('https://nexus.convergencelabs.tech:18443/', 'NexusRepo') {
            def sbtTools = docker.image('sbt-tools:0.6')
            sbtTools.pull()
  
            docker.image(sbtTools.imageName()).inside("-e nexus_realm='Sonatype Nexus Repository Manager' -e nexus_host=nexus.convergencelabs.tech -e nexus_user=$NEXUS_USER -e nexus_password=$NEXUS_PASSWORD") {
              stage 'Build'
              sh 'sbt compile stage'
  
            }
          }
  
          stage 'Docker Build'
          echo "Current build number is ${env.BUILD_NUMBER}"
  
          sh '''
            echo "Creating docker target directory"
            mkdir target/docker
            cp -a target/universal/stage target/docker
            cp -a Dockerfile target/docker
  
            echo "Logging in to docker"
            docker login -u $NEXUS_USER -p $NEXUS_PASSWORD nexus.convergencelabs.tech:18444
  
            echo "Building the container"
            docker build -t nexus.convergencelabs.tech:18444/convergence-akka-cluster-seed target/docker 
  
            echo "Publishing the container"
            docker push nexus.convergencelabs.tech:18444/convergence-akka-cluster-seed
          '''
         }
       }
       deleteDir()
     }
   }
 }
