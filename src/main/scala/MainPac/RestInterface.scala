package MainPac

import scala.concurrent.ExecutionContext
import akka.http.scaladsl.server.Route
import  MainPac.resources.{CartResource}


trait RestInterface extends Resources {

  implicit def executionContext: ExecutionContext

  //lazy val CartService = new CartService

  val routes: Route = cartRoutes

}

trait Resources extends CartResource

