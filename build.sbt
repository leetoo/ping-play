lazy val commonSettings = Seq(
  organization := "com.ybrikman",
  version := "0.0.1",
  scalaVersion := "2.11.6",
  scalacOptions += "-feature",
  resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
)

// You must added these settings to your Play app to be able to use .scala.stream templates for BigPipe-style streaming
lazy val bigPipeSettings = Seq(
  play.twirl.sbt.Import.TwirlKeys.templateFormats ++= Map("stream" -> "com.ybrikman.ping.scalaapi.bigpipe.HtmlStreamFormat"),
  play.twirl.sbt.Import.TwirlKeys.templateImports ++= Vector("com.ybrikman.ping.scalaapi.bigpipe.HtmlStream", "com.ybrikman.ping.scalaapi.bigpipe.HtmlStream._")
)

// The BigPipe library
lazy val bigPipe = (project in file("big-pipe"))
  .settings(commonSettings)
  .enablePlugins(SbtTwirl)
  .settings(
    name := "ping-play-big-pipe",
    libraryDependencies ++= Seq(
      "com.typesafe.play" %% "play" % play.core.PlayVersion.current,
      "com.typesafe.play" %% "play-iteratees" % play.core.PlayVersion.current,
      specs2 % Test
    )
  )

// Some shared code for the sample apps
lazy val sampleAppCommon = (project in file("sample-app-common"))
  .settings(commonSettings)
  .settings(bigPipeSettings)
  .enablePlugins(SbtTwirl)
  .dependsOn(bigPipe)
  .settings(
    name := "ping-play-sample-app-common",
    libraryDependencies ++= Seq(
      "com.typesafe.play" %% "play" % play.core.PlayVersion.current,
      specs2 % Test
    )
  )

// The Scala sample app
lazy val sampleAppScala = (project in file("sample-app-scala"))
  .settings(commonSettings)
  .settings(bigPipeSettings)
  .enablePlugins(PlayScala)
  .dependsOn(bigPipe, sampleAppCommon)
  .settings(
    name := "ping-play-sample-app-scala",
    routesGenerator := InjectedRoutesGenerator,
    libraryDependencies ++= Seq(
      ws,
      specs2 % Test
    )
  )

// The Java sample app
lazy val sampleAppJava = (project in file("sample-app-java"))
  .settings(commonSettings)
  .settings(bigPipeSettings)
  .enablePlugins(PlayJava)
  .dependsOn(bigPipe, sampleAppCommon)
  .settings(
    name := "ping-play-sample-app-java",
    routesGenerator := InjectedRoutesGenerator,
    libraryDependencies ++= Seq(
      javaWs,
      "com.typesafe.play" %% "play-java" % play.core.PlayVersion.current,
      specs2 % Test
    )
  )









