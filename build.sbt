name := "convergence-server-cluster-seed"
organization := "com.convergencelabs.server"
version := "0.1"

/* scala versions and options */
scalaVersion := "2.12.6"

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

val akka = "2.5.13"
val log4j = "2.10.0"

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
  ,"org.clapper"              %% "grizzled-slf4j" % "1.3.2"      // BSD

  // -- json --
  ,"org.json4s" %% "json4s-jackson" % "3.5.4"

  // -- config --
  ,"com.typesafe" % "config" % "1.3.3"
)

enablePlugins(JavaAppPackaging)
