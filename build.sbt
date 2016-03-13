lazy val root = (project in file(".")).
    settings(
        name := "kmeans-iris",
        version := "0.0.1",
        scalaVersion := "2.11.7",
        scalacOptions ++= Seq(
            "-feature",
            "-deprecation"
        ),
        mainClass in Compile := Some("KMeansIris"),
        libraryDependencies ++= Seq(
            "org.apache.spark" %% "spark-core" % "1.6.1",
            "org.apache.spark" %% "spark-mllib" % "1.6.1"
        )
    )
