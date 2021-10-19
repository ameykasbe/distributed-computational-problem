name := "MapReduce"

version := "0.1"

scalaVersion := "3.0.1"

libraryDependencies += "org.apache.hadoop" % "hadoop-common" % "2.10.0"
libraryDependencies += "org.apache.hadoop" % "hadoop-core" % "1.2.1"


assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}
