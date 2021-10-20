name := "MapReduce"

version := "0.1"

scalaVersion := "3.0.1"

val typesafeConfigVersion = "1.4.1"
val scalacticVersion = "3.2.9"
val logbackVersion = "1.3.0-alpha10"
val sfl4sVersion = "2.0.0-alpha5"

libraryDependencies += "org.apache.hadoop" % "hadoop-common" % "2.10.0"
libraryDependencies += "org.apache.hadoop" % "hadoop-core" % "1.2.1"
libraryDependencies += "com.github.nscala-time" %% "nscala-time" % "2.30.0"
libraryDependencies += "joda-time" % "joda-time" % "2.10.12"
libraryDependencies += "com.typesafe" % "config" % typesafeConfigVersion
libraryDependencies += "org.scalactic" %% "scalactic" % scalacticVersion
libraryDependencies += "org.scalatest" %% "scalatest" % scalacticVersion % Test
libraryDependencies += "org.scalatest" %% "scalatest-featurespec" % scalacticVersion % Test

libraryDependencies += "ch.qos.logback" % "logback-core" % logbackVersion
libraryDependencies += "ch.qos.logback" % "logback-classic" % logbackVersion
libraryDependencies += "org.slf4j" % "slf4j-api" % sfl4sVersion

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}
