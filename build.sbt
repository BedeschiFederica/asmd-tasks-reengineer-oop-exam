ThisBuild / version := "0.1.0-SNAPSHOT"
lazy val root = (project in file("."))
  .settings(
    name := "asmd-tasks-reengineer-oop-exam",
    libraryDependencies ++= Seq(
        "net.aichler" % "jupiter-interface" % JupiterKeys.jupiterVersion.value % Test,
        "io.cucumber" % "cucumber-java" % "6.1.1" % Test,
        "org.mockito" % "mockito-core" % "3.+" % Test
    )
)
