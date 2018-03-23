lazy val akka_actor = Seq (
  "com.typesafe.akka" %% "akka-actor" 	% "2.4.12",
  "com.typesafe.akka" %% "akka-slf4j"   % "2.4.12",
  "com.typesafe.akka" %% "akka-remote"  % "2.4.12",
  "com.typesafe.akka" %% "akka-agent"   % "2.4.12",
  "com.typesafe.akka" %% "akka-testkit" % "2.4.12"	% "test"
)

lazy val akka = Seq (
 "com.typesafe.akka" %% "akka-actor" % "2.4.12",
 "com.typesafe.akka" %% "akka-agent" % "2.4.12",
 "com.typesafe.akka" %% "akka-camel" % "2.4.12",
 "com.typesafe.akka" %% "akka-cluster" % "2.4.12",
 "com.typesafe.akka" %% "akka-cluster-metrics" % "2.4.12",
 "com.typesafe.akka" %% "akka-cluster-sharding" % "2.4.12",
 "com.typesafe.akka" %% "akka-cluster-tools" % "2.4.12",
 "com.typesafe.akka" %% "akka-contrib" % "2.4.12",
 "com.typesafe.akka" %% "akka-multi-node-testkit" % "2.4.12",
 "com.typesafe.akka" %% "akka-osgi" % "2.4.12",
 "com.typesafe.akka" %% "akka-persistence" % "2.4.12",
 "com.typesafe.akka" %% "akka-persistence-tck" % "2.4.12",
 "com.typesafe.akka" %% "akka-remote" % "2.4.12",
 "com.typesafe.akka" %% "akka-slf4j" % "2.4.12",
 "com.typesafe.akka" %% "akka-stream" % "2.4.12",
 "com.typesafe.akka" %% "akka-stream-testkit" % "2.4.12",
 "com.typesafe.akka" %% "akka-testkit" % "2.4.12",
 "com.typesafe.akka" %% "akka-distributed-data-experimental" % "2.4.12",
 "com.typesafe.akka" %% "akka-typed-experimental" % "2.4.12",
 "com.typesafe.akka" %% "akka-persistence-query-experimental" % "2.4.12"
)

lazy val parsing = Seq (
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.4"
)

lazy val slick = Seq(
  "com.typesafe.slick" %% "slick" % "3.1.1",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "com.h2database" % "h2" % "1.4.187"
)

lazy val scalatest = Seq(
  "org.scalatest" %% "scalatest" % "3.0.0" % "test"
)

lazy val commonSettings = Seq(
  organization := "pocalooza",
  version := "0.0.0",
  scalaVersion := "2.11.8",
  libraryDependencies ++= slick ++ scalatest ++ parsing ++ akka_actor,
  connectInput in run := true
)

lazy val root = (project in file (".")).
  settings(commonSettings: _*).
  settings(
    name := "poca",
    mainClass in Compile := None
  )

fork in run := true
