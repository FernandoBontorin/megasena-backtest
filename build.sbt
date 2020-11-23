name := "mega-sena"
version := "0.1.0-SNAPSHOT"
scalaVersion := "2.11.12"
organization := "io.fernandobontorin"
organizationName := "fernandobontorin"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.2" % "test"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.4.7"
libraryDependencies += "com.databricks" %% "spark-xml" % "0.10.0"
libraryDependencies += "commons-io" % "commons-io" % "2.8.0"

