name := "playTestProject"

version := "1.0"

lazy val `playtestproject` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"

scalaVersion := "2.12.2"

libraryDependencies ++= Seq(ehcache, ws, specs2 % Test, guice,
  "com.typesafe.slick" %% "slick" % "3.2.1",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.2.1",
  "org.postgresql" % "postgresql" % "42.1.4",
  "com.typesafe.play" %% "play-json" % "2.6.3",
  "com.typesafe.play" %% "play-slick-evolutions" % "3.0.1"
)

includeFilter in(Assets, LessKeys.less) := "board.less"

unmanagedResourceDirectories in Test <+= baseDirectory(_ / "target/web/public/test")
