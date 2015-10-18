name := "sangria-playground"
description := "An example of GraphQL server written with Play and Sangria."

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.7"

val tinkerVersion =  "3.0.1-incubating"
val titanVerson = "1.0.0"

libraryDependencies ++= Seq(
  "com.thinkaurelius.titan" % "titan-cassandra" % titanVerson,
  "com.thinkaurelius.titan" % "titan-core" % titanVerson,
  "org.apache.tinkerpop" % "gremlin-core" % tinkerVersion,
  "org.apache.tinkerpop" % "tinkergraph-gremlin" % tinkerVersion,
  "com.tinkerpop.gremlin" % "gremlin-scala" % "1.5",
  "org.sangria-graphql" %% "sangria" % "0.4.1",
  "org.webjars" % "jquery" % "2.1.4",
  "org.webjars" % "d3js" % "3.5.6"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

resolvers += "Apache public" at "https://repository.apache.org/content/groups/public/"

routesGenerator := InjectedRoutesGenerator

lazy val root = (project in file(".")).enablePlugins(PlayScala)

herokuAppName in Compile := "aqueous-hollows-6102"
herokuConfigVars in Compile := Map(
  "JAVA_OPTS" -> "-DgaCode=UA-65759630-2 -DdefaultGraphQLUrl=http://try.sangria-graphql.org/graphql"
)