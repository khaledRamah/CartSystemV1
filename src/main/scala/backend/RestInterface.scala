package backend

import scala.concurrent.ExecutionContext
import akka.http.scaladsl.server.Route
import  backend.resources.{CartResource}


trait RestInterface extends Resources {

  val routes: Route = cartRoutes

}

trait Resources extends CartResource

