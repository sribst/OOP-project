val slick = Seq(
  "com.typesafe.slick" %% "slick" % "3.1.1",
  "org.slf4j" % "slf4j-nop" % "1.6.4"
)

val database_dep = slick

lazy val commonSettings = Seq(
  organization := "pocalooza",
  version := "0.0.0",
  scalaVersion := "2.11.8"
)

lazy val root = (project in file (".")).
  settings(commonSettings: _*).
  settings(
    name := "poca-2016"
  )

lazy val database = (project in file("database")).
  settings(commonSettings: _*).
  settings(
    name := "database",
    version := "0.0.0",
    libraryDependencies ++= database_dep
  )