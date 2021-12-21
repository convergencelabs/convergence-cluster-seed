organization := "com.convergencelabs"
organizationName := "Convergence Labs, Inc."
organizationHomepage := Some(url("http://convergencelabs.com"))

name := "convergence-cluster-seed"
version := "1.0.0-rc.10"

description := "Convergence Cluster Seed"
homepage := Some(url("https://convergence.io"))
maintainer := "info@convergencelabs.com"

licenses += "GPLv3" -> url("https://www.gnu.org/licenses/gpl-3.0.html")

scmInfo := Some(ScmInfo(
  url("https://github.com/convergencelabs/convergence-cluster-seed"),
  "https://github.com/convergencelabs/convergence-cluster-seed.git"))

scalaVersion := "2.13.5"

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

val akka = "2.6.15"
val log4j = "2.17.0"
val jackson = "2.11.4"

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
  ,"com.fasterxml.jackson.core" % "jackson-databind"     % jackson     // Apache 2.0
)

enablePlugins(JavaAppPackaging, UniversalDeployPlugin)

Compile / mainClass := Some("com.convergencelabs.convergence.clusterseed.ConvergenceClusterSeed")
Compile / discoveredMainClasses := Seq()
