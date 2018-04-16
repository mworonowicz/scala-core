import sbt.Keys._
import sbt._

addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.6")

val circeVersion    = "0.9.3"
val akkaHttpVersion = "10.1.1"
val effVersion      = "5.1.0"

val typesafeConfig = "com.typesafe" % "config" % "1.3.3"

val monixCore = "io.monix" %% "monix" % "3.0.0-M3"
val monixEval = "io.monix" %% "monix-eval" % "3.0.0-M3"
val monix     = Seq(monixCore, monixEval)

val circeCore    = "io.circe" %% "circe-core" % circeVersion
val circeGeneric = "io.circe" %% "circe-generic" % circeVersion
val circeJava8   = "io.circe" %% "circe-java8" % circeVersion
val circeAkka    = "de.heikoseeberger" %% "akka-http-circe" % "1.20.1"
val circe        = Seq(circeCore, circeGeneric, circeJava8, circeAkka)

val effCore  = "org.atnos" %% "eff" % effVersion
val effMonix = "org.atnos" %% "eff-monix" % effVersion
val eff      = Seq(effCore, effMonix)

val catsCore   = "org.typelevel" %% "cats-core" % "1.1.0"
val catsEffect = "org.typelevel" %% "cats-effect" % "0.10"
val cats       = Seq(catsCore, catsEffect)

val akkaHttpCore = "com.typesafe.akka" %% "akka-http" % akkaHttpVersion
//val swagger      = "com.github.swagger-akka-http" %% "swagger-akka-http" % "0.11.0"

lazy val commonSettings = Seq(organization := "com.afterhourz",
                              version := "0.0.1-SNAPSHOT",
                              scalaVersion := "2.12.5",
                              scalacOptions ++= Seq("-unchecked", "-deprecation", "-Ypartial-unification"),
                              scalafmtOnCompile := true)

lazy val scalaCore = (project in file("."))
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= Seq(akkaHttpCore, typesafeConfig) ++ cats ++ monix ++ eff ++ cats ++ circe)

SettingKey[Unit]("scalafmtGenerateConfig") :=
  IO.write( // writes to file once when build is loaded
           file(".scalafmt.conf"),
           """style = IntelliJ
      |# Your configuration here
      |align = true    # For pretty alignment.
      |maxColumn = 140 # For my wide 30" display.
    """.stripMargin.getBytes("UTF-8"))
