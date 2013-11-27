import sbt._
import sbt.Keys._

object BuildSettings {
  val buildSettings = Defaults.defaultSettings ++ Seq(
    organization := "me.laiseca",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.10.3",
    scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")
  )
}

object RestcaleBuild extends Build {
  import BuildSettings._

  val snakeYaml = "org.yaml" % "snakeyaml" % "1.13"

  val scalatest = "org.scalatest" % "scalatest_2.10" % "2.0.M6" % "test"
  val mockito = "org.mockito" % "mockito-core" % "1.9.5" % "test"

  lazy val coreDeps = Seq(
    snakeYaml,
    scalatest,
    mockito
  )

  lazy val root: Project = Project(
    "root",
    file("."),
    settings = buildSettings
  ) aggregate(core)

  lazy val core: Project = Project(
    "core",
    file("core"),
    settings = buildSettings ++ Seq( libraryDependencies ++= coreDeps )
  )
}
