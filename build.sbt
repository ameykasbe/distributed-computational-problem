name := "MapReduce"

version := "0.1"

scalaVersion := "3.0.1"

val typesafeConfigVersion = "1.4.1"

libraryDependencies += "org.apache.hadoop" % "hadoop-common" % "2.10.0"
libraryDependencies += "org.apache.hadoop" % "hadoop-core" % "1.2.1"
libraryDependencies += "com.github.nscala-time" %% "nscala-time" % "2.30.0"
libraryDependencies += "joda-time" % "joda-time" % "2.10.12"
libraryDependencies += "com.typesafe" % "config" % typesafeConfigVersion

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}
