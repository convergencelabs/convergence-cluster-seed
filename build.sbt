organization := "com.convergencelabs"
organizationName := "Convergence Labs, Inc."
organizationHomepage := Some(url("http://convergencelabs.com"))

name := "convergence-cluster-seed"
version := "1.0.0-SNAPSHOT"

description := "Convergence Cluster Seed"
homepage := Some(url("https://convergence.io"))
maintainer := "info@convergencelabs.com"

licenses += "GPLv3" -> url("https://www.gnu.org/licenses/gpl-3.0.html")

scmInfo := Some(ScmInfo(
  url("https://github.com/convergencelabs/convergence-cluster-seed"),
  "https://github.com/convergencelabs/convergence-cluster-seed.git"))

scalaVersion := "2.13.2"

// These options will be used for *all* versions.
scalacOptions ++= Seq(
  "-deprecation"
  ,"-unchecked"
  ,"-encoding", "UTF-8"
  ,"-Xlint"
  ,"-Xverify"
  ,"-feature"
  ,"-language:postfixOps"
)

val akka = "2.6.6"
val log4j = "2.13.1"

/* dependencies */
libraryDependencies ++= Seq (
  // -- Akka --
   "com.typesafe.akka"          %% "akka-actor-typed"    % akka        // Apache 2.0
  ,"com.typesafe.akka"          %% "akka-cluster-typed"  % akka        // Apache 2.0
  ,"com.typesafe.akka"          %% "akka-slf4j"          % akka        // Apache 2.0

  // -- Logging --
  ,"org.apache.logging.log4j"   % "log4j-slf4j-impl"     % log4j       // Apache 2.0
  ,"org.apache.logging.log4j"   % "log4j-api"            % log4j       // Apache 2.0
  ,"org.apache.logging.log4j"   % "log4j-core"           % log4j       // Apache 2.0
  ,"org.apache.logging.log4j"   % "log4j-jul"            % log4j       // Apache 2.0
  ,"org.clapper"                %% "grizzled-slf4j"      % "1.3.4"     // BSD
)

enablePlugins(JavaAppPackaging, UniversalDeployPlugin)

mainClass in Compile := Some("com.convergencelabs.convergence.clusterseed.ConvergenceClusterSeed")
discoveredMainClasses in Compile := Seq()
