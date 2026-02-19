lazy val pekkoHttpVersion = "1.3.0"
lazy val pekkoVersion    = "1.4.0"

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization    := "com.example",
      scalaVersion    := "2.13.18"
    )),
    name := "scala-server",
    libraryDependencies ++= Seq(
      "org.apache.pekko" %% "pekko-http"                % pekkoHttpVersion,
      "org.apache.pekko" %% "pekko-http-spray-json"     % pekkoHttpVersion,
      "org.apache.pekko" %% "pekko-actor-typed"         % pekkoVersion,
      "org.apache.pekko" %% "pekko-stream"              % pekkoVersion,
      "ch.qos.logback"    % "logback-classic"           % "1.3.9",

      "org.apache.pekko" %% "pekko-http-testkit"        % pekkoHttpVersion % Test,
      "org.apache.pekko" %% "pekko-actor-testkit-typed" % pekkoVersion     % Test,
      "org.scalatest"     %% "scalatest"                % "3.0.8"          % Test
    )
  )
