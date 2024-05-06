val _scalaVersion = "2.12.12"
val sparkVersion = "3.3.2"
val breezeVersion = "1.3"
val scalaTestVersion = "3.2.2"

lazy val root = project
  .in(file("."))
  .settings(
    name := "LinearRegression",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := _scalaVersion,

    libraryDependencies ++= Seq(
      "org.apache.spark" %% "spark-core" % sparkVersion withSources(),
      "org.apache.spark" %% "spark-sql" % sparkVersion withSources(),
      "org.apache.spark" %% "spark-mllib" % sparkVersion withSources(),
      "org.scalanlp" %% "breeze" % breezeVersion withSources(),
      "org.scalatest" %% "scalatest" % scalaTestVersion % "test" withSources()
    )
  )
