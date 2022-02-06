ThisBuild / scalaVersion     := "2.13.7"
ThisBuild / organization     := "io.github.kitlangton"
ThisBuild / organizationName := "kitlangton"
ThisBuild / description      := "Automatically parallelize your for comprehensions at compile time."
ThisBuild / homepage         := Some(url("https://github.com/kitlangton/parallel-for"))

val sharedSettings = Seq(
  licenses := Seq("APL2" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt")),
  developers := List(
    Developer(
      id = "kitlangton",
      name = "Kit Langton",
      email = "kit.langton@gmail.com",
      url = url("https://github.com/kitlangton")
    )
  ),
  testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework"),
  libraryDependencies ++= {
    CrossVersion.partialVersion(scalaVersion.value) match {
      case Some((2, n)) if n <= 12 =>
        List(compilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full))
      case _ =>
        List()
    }
  },
  Compile / scalacOptions ++= {
    CrossVersion.partialVersion(scalaVersion.value) match {
      case Some((2, n)) if n <= 12 => List("-Ypartial-unification")
      case _                       => List("-Ymacro-annotations", "-Ywarn-unused", "-Wmacros:after")
    }
  }
)

val zioVersion = "2.0.0-RC2"

lazy val root = (project in file("."))
  .settings(
    name := "parallel-for",
    libraryDependencies ++= Seq(
      "dev.zio"       %% "zio"               % zioVersion,
      "dev.zio"       %% "zio-test"          % zioVersion % Test,
      "dev.zio"       %% "zio-test-magnolia" % zioVersion % Test,
      "dev.zio"       %% "zio-test-sbt"      % zioVersion % Test,
      "org.scala-lang" % "scala-reflect"     % scalaVersion.value,
      "org.scala-lang" % "scala-compiler"    % scalaVersion.value
    ),
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework"),
    sharedSettings
  )

testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
