name := "ShoppingCartV2"

version := "0.1"

scalaVersion := "2.12.4"

resolvers ++= Seq("Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
  Resolver.bintrayRepo("hseeberger", "maven"))

libraryDependencies ++= {
  val AkkaHttpVersion = "10.0.10"
  val QuillVersion= "2.3.2"
  val h2Version ="1.4.192"
  val SlickVer ="3.2.1"
  val OrgVer ="1.6.4"
  Seq(
    "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
    "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion,
    "ch.megard" %% "akka-http-cors" % "0.2.2",
    "de.heikoseeberger" %% "akka-http-json4s" % "1.16.0",
    "com.h2database" % "h2" % h2Version,
    "io.getquill" %% "quill-jdbc" % QuillVersion,
    "com.typesafe.slick" %% "slick" %  SlickVer,
    "org.slf4j" % "slf4j-nop" % OrgVer,
    "com.typesafe.slick" %% "slick-hikaricp" % SlickVer
  )
}
