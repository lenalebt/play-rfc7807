organization := "de.lenabrueder"
scalaVersion := "2.12.2"
version      := "0.1.0-SNAPSHOT"

name := "play-rfc7807"

//Dependencies
libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play" % "2.6.0" % "provided",
  "com.typesafe.play" %% "play-json" % "2.6.0" % "provided"
)

//Test dependencies
libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.1"
) map (_ % "test")
