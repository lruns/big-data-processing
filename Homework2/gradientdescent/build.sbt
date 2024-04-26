val scalaVersionn = "2.13.13"
val sparkVersion = "3.5.1"
val breezeVersion = "2.1.0"

lazy val root = project
  .in(file("."))
  .settings(
    name := "GradientDescent",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scalaVersionn,

    libraryDependencies ++= Seq(
      "org.apache.spark" %% "spark-core" % sparkVersion,
      "org.apache.spark" %% "spark-sql" % sparkVersion,
      "org.apache.spark" %% "spark-mllib" % sparkVersion,
      "org.scalanlp" %% "breeze" % breezeVersion,
    )
  )
