name := "convergence-akka-cluster-seed"
organization := "com.convergencelabs"
version := "0.1"

/* scala versions and options */
scalaVersion := "2.11.8"

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

val akka = "2.5.7"
val log4j = "2.4.1"

/* dependencies */
libraryDependencies ++= Seq (

  // -- Akka --
  "com.typesafe.akka" %% "akka-testkit" % akka % "test"
  ,"com.typesafe.akka" %% "akka-actor" % akka
  ,"com.typesafe.akka" %% "akka-slf4j" % akka
  ,"com.typesafe.akka" %% "akka-cluster" % akka

  // -- Logging --
  ,"org.apache.logging.log4j" % "log4j-slf4j-impl" % log4j       // Apache 2.0
  ,"org.apache.logging.log4j" % "log4j-api" % log4j              // Apache 2.0
  ,"org.apache.logging.log4j" % "log4j-core" % log4j             // Apache 2.0
  ,"org.apache.logging.log4j" % "log4j-jul" % log4j              // Apache 2.0
  ,"org.clapper"              % "grizzled-slf4j_2.11" % "1.0.2"  // BSD
  
  // -- json --
  ,"org.json4s" %% "json4s-jackson" % "3.2.10"
  
  // -- config --
  ,"com.typesafe" % "config" % "1.2.0"
)

enablePlugins(JavaAppPackaging)