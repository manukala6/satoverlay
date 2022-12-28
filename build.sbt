name := "geotrellis-spark-job"
version := "0.1.0"

scalaVersion := "2.12.13"

libraryDependencies ++= Seq(
  "com.monovore" %% "decline" % "1.2.0",
  "org.locationtech.geotrellis" %% "geotrellis-spark" % "3.6.0",
  "org.locationtech.geotrellis" %% "geotrellis-s3" % "3.6.0",
  "org.locationtech.geotrellis" %% "geotrellis-gdal" % "3.6.0",
  "org.apache.spark" %% "spark-core" % "3.1.3",
  "org.apache.spark" %% "spark-sql" % "3.1.3",
  "org.apache.spark" %% "spark-hive" % "3.1.3",
  "org.apache.hadoop" % "hadoop-client" % "3.1.3"
)

run / fork := true