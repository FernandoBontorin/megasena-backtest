name := "mega-sena"
version := "0.1.0-SNAPSHOT"
scalaVersion := "2.11.12"
organization := "io.fernandobontorin"
organizationName := "fernandobontorin"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.0" % "test"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.4.7" % "provided"
libraryDependencies += "com.databricks" %% "spark-xml" % "0.10.0"

