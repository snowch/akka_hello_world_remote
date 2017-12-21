name := "hello"

organization in ThisBuild := "com.example"
scalaVersion in ThisBuild := "2.11.1"
version in ThisBuild      := "0.1.0-SNAPSHOT"

lazy val settings = Seq(
  publishTo := Some(Resolver.file("file",  new File(Path.userHome.absolutePath+"/.m2/repository"))),
)

lazy val global = project
  .in(file("."))
  .settings(settings)
  .aggregate(
    server,
    client
  )

lazy val messages = project
  .settings(
    name := "messages",
    libraryDependencies ++= commonDependencies,
    settings
  )

lazy val server = project
  .settings(
    name := "server",
    libraryDependencies ++= commonDependencies ++ Seq(
      "com.example"       %% "messages"    % "0.1.0-SNAPSHOT",
      "com.typesafe.akka" %% "akka-remote" % "2.5.8"
    ),
    mainClass in assembly := Some("com.akkademy.AkkademyDb")
    settings
  )
  .dependsOn(
    messages
  )

lazy val client = project
  .settings(
    name := "client",
    libraryDependencies ++= commonDependencies ++ Seq(
      "com.example"       %% "messages"    % "0.1.0-SNAPSHOT",
      "com.typesafe.akka" %% "akka-remote" % "2.5.8"
      ),
    mainClass in assembly := Some("akkademyclient.Client")
    settings
  )
  .dependsOn(
    messages
  )

lazy val commonDependencies = Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.5.8",
  "com.typesafe.akka" %% "akka-testkit" % "2.5.8" % "test",
  "org.scalatest"     %% "scalatest" % "2.1.6" % "test"
)

