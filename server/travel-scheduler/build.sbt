import de.johoop.jacoco4sbt._
import JacocoPlugin._

name := "travel-scheduler"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache
)     

jacoco.settings

parallelExecution in jacoco.Config := false

play.Project.playJavaSettings
