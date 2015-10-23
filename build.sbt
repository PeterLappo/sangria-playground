name := "sangria-playground"
description := "An example of GraphQL server written with Play and Sangria."

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.7"

val tinkerVersion =  "3.0.1-incubating"
val titanVerson = "1.0.0"

// for webjars see http://www.webjars.org

libraryDependencies ++= Seq(
  "com.thinkaurelius.titan" % "titan-cassandra" % titanVerson,
  "com.thinkaurelius.titan" % "titan-core" % titanVerson,
  "org.apache.tinkerpop" % "gremlin-core" % tinkerVersion,
  "org.apache.tinkerpop" % "tinkergraph-gremlin" % tinkerVersion,
  "com.tinkerpop.gremlin" % "gremlin-scala" % "1.5",
  "org.sangria-graphql" %% "sangria" % "0.4.1",
  "org.webjars" % "jquery" % "2.1.4",
  "org.webjars" % "d3js" % "3.5.6",
  "org.webjars" % "angularjs"            % "1.4.7",
  "org.webjars" % "angular-ui-bootstrap" % "0.14.0",
  "org.webjars" % "angular-ui-router"    % "0.2.15",
  "org.webjars" % "marked"               % "0.3.2",
  "org.webjars" % "angular-marked"       % "0.0.12",
  "org.webjars" % "angularjs-toaster"    % "0.4.8",
  "org.webjars" % "angularjs-nvd3-directives" % "0.0.7-1",
  "org.webjars" % "lodash" % "3.10.1"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

resolvers += "Apache public" at "https://repository.apache.org/content/groups/public/"

routesGenerator := InjectedRoutesGenerator

lazy val root = (project in file(".")).enablePlugins(PlayScala)

herokuAppName in Compile := "aqueous-hollows-6102"
herokuConfigVars in Compile := Map(
  "JAVA_OPTS" -> "-DgaCode=UA-65759630-2 -DdefaultGraphQLUrl=http://try.sangria-graphql.org/graphql"
)