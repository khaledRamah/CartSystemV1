package backend

import akka.actor._
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer

object Main extends  RestInterface with App  {

  val host = "localHost"//config.getString("http.host")
  val port = 8080 //config.getInt("http.port")

  implicit val system = ActorSystem("CartSystem")
  implicit val materializer = ActorMaterializer() // dah leh ?
  implicit val executionContext = system.dispatcher

  Http().bindAndHandle(routes, host, port) map { binding =>
    println(s"REST interface bound to ${binding.localAddress}") } recover { case ex =>
    println(s"REST interface could not bind to $host:$port", ex.getMessage)
  }
 // DataBaseService.CloseConn()
}
