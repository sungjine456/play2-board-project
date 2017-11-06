import com.typesafe.sbt.web.Import.WebKeys.assets

version in ThisBuild := "1.0"

scalaVersion in ThisBuild := "2.12.2"

scalacOptions in ThisBuild ++= Seq("-deprecation", "-unchecked", "-feature")

resolvers in ThisBuild ++= Seq(
  "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases",
  "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/",
  Resolver.sonatypeRepo("snapshots")
)

lazy val crossType = CrossType.Full

lazy val root = project.in(file("."))
  .aggregate(client, server)
  .settings(
    name := "playTestProject",
    run in Compile := (run in Compile in server).evaluated)

lazy val server = playTestProject.jvm
  .enablePlugins(PlayScala)
  .settings(
    name := "playTestProject-server",
    routesGenerator := InjectedRoutesGenerator,
    scalaJSProjects := Seq(playTestProject.js),
    stage := { stage dependsOn(assets, fullOptJS in(client, Compile)) }.value,
    routesGenerator := InjectedRoutesGenerator,
    includeFilter in(Assets, LessKeys.less) := "board.less",
    libraryDependencies ++= Seq(ehcache, ws, specs2 % Test, guice,
      "com.h2database" % "h2" % "1.4.196" % "test",
      "org.scalatest" %% "scalatest" % "3.0.4" % "test",
      "com.typesafe.slick" %% "slick" % "3.2.1",
      "com.typesafe.slick" %% "slick-hikaricp" % "3.2.1",
      "org.postgresql" % "postgresql" % "42.1.4",
      "com.typesafe.play" %% "play-slick-evolutions" % "3.0.1",
      "org.scala-js" %% "scalajs-stubs" % scalaJSVersion))

lazy val client = playTestProject.js
  .enablePlugins(ScalaJSPlugin)
  .settings(
    name := "playTestProject-client",
    libraryDependencies ++= Seq(
      "com.greencatsoft" %%%! "scalajs-angular" % "0.8-SNAPSHOT"),
    relativeSourceMaps := true)

lazy val playTestProject = (crossProject in file("."))
  .settings(
    name := "playTestProject-common",
    libraryDependencies ++= Seq(
      "com.typesafe.play" %%% "play-json" % "2.6.6"
    ))

ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) }
