
organization := "de.lenabrueder"
scalaVersion := "2.12.2"

crossScalaVersions := Seq(scalaVersion.value, "2.11.8")

name := "play-rfc7807"

//Dependencies
libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play" % "2.6.0" % "provided",
  "com.typesafe.play" %% "play-json" % "2.6.0" % "provided",
  "com.google.inject" % "guice" % "4.1.0" % "provided"
)

//Test dependencies
libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.1"
) map (_ % "test")

scmInfo := Some(
  ScmInfo(
    url("https://github.com/lenalebt/play-rfc7807"),
    "scm:git@github.com:lenalebt/play-rfc7807.git"
  )
)

developers := List(
  Developer(
    id    = "lbrueder",
    name  = "Lena Brueder",
    email = "oss@lena-brueder.de",
    url   = url("http://github.com/lenalebt")
  )
)

homepage := Some(url("https://github.com/lenalebt/play-rfc7807"))

publishMavenStyle := true

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

publishArtifact in Test := false

licenses += ("MIT", url("https://opensource.org/licenses/MIT"))

scapegoatVersion := "1.3.1"

useGpg := true
