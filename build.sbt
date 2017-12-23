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
    pipelineStages := Seq(scalaJSProd),
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
      "com.github.benhutchison" %% "prickle" % "1.1.13",
      "org.scala-js" %% "scalajs-stubs" % scalaJSVersion,
      "org.webjars.bower" % "angular" % "1.5.7",
      "org.webjars.bower" % "jquery" % "2.2.4" force()))

lazy val client = playTestProject.js
  .enablePlugins(ScalaJSPlugin)
  .settings(
    scalaJSUseMainModuleInitializer := true,
    name := "playTestProject-client",
    libraryDependencies ++= Seq(
      "com.greencatsoft" %%%! "scalajs-angular" % "0.8-SNAPSHOT",
      "com.lihaoyi" %%%! "upickle" % "0.4.4",
      "com.github.benhutchison" %%%! "prickle" % "1.1.13",
      "be.doeraene" %%%! "scalajs-jquery" % "0.9.0"),
    jsDependencies ++= Seq(
      ("org.webjars.bower" % "jquery" % "2.2.4" force()) / "dist/jquery.js" minified s"dist/jquery.min.js",
      ("org.webjars.bower" % "angular" % "1.6.6" force()) / "angular.js" minified "angular.min.js" dependsOn "dist/jquery.js",
      "org.webjars.bower" % "angular-ui-router" % "1.0.8" / "angular-ui-router.js" minified "angular-ui-router.min.js" dependsOn "angular.js",
      "org.webjars.bower" % "angular-resource" % "1.6.6" / "angular-resource.js" minified "angular-resource.min.js" dependsOn "angular.js"),
    relativeSourceMaps := true,
    skip in packageJSDependencies := false)

lazy val playTestProject = (crossProject in file("."))
  .settings(
    name := "playTestProject-common",
    libraryDependencies ++= Seq(
      "com.typesafe.play" %%% "play-json" % "2.6.6"
    ),
    unmanagedSourceDirectories in Compile :=
      Seq((scalaSource in Compile).value) ++
        crossType.sharedSrcDir(baseDirectory.value, "main"),
    unmanagedSourceDirectories in Test :=
      Seq((scalaSource in Test).value) ++
        crossType.sharedSrcDir(baseDirectory.value, "test"),
    testOptions in Test := Seq(Tests.Filter(_.endsWith("Test"))))
