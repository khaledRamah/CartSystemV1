name := "ShoppingCartV2"

version := "0.1"

scalaVersion := "2.12.4"

resolvers ++= Seq("Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
  Resolver.bintrayRepo("hseeberger", "maven"))

libraryDependencies ++= {
  val AkkaVersion = "2.5.8"
  val AkkaHttpVersion = "10.0.10"
  val Json4sVersion = "3.5.2"
  val QuillVersion= "2.3.2"
  val h2Version ="1.4.192"
  Seq(
    "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
    "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion,
    "ch.megard" %% "akka-http-cors" % "0.2.2",
    "de.heikoseeberger" %% "akka-http-json4s" % "1.16.0",
    "com.h2database" % "h2" % h2Version,
    "io.getquill" %% "quill-jdbc" % QuillVersion
  )
}
