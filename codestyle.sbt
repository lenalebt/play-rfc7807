scapegoatVersion in ThisBuild := "1.3.8"

scalafmtOnCompile in ThisBuild := true

scapegoatIgnoredFiles := Seq(".*/ReverseRoutes.scala",
  ".*/JavaScriptReverseRoutes.scala",
  ".*/Routes.scala")
