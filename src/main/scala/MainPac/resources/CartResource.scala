package MainPac.resources


import akka.actor.{ActorSystem, Props}
import akka.pattern.ask

import scala.concurrent.duration._
import akka.util.Timeout
import MainPac.entities._
import MainPac.services._
import akka.http.scaladsl.marshalling.ToResponseMarshallable
import akka.http.scaladsl.model.{HttpMethods, HttpResponse, StatusCodes}
import akka.http.scaladsl.server.{Directives, RejectionHandler, Route}
import ch.megard.akka.http.cors.scaladsl.settings.CorsSettings
import ch.megard.akka.http.cors.scaladsl.CorsDirectives._
import akka.http.scaladsl.model.HttpMethods._
import MainPac.services.Failed

import scala.util._
import scala.collection._
import scala.concurrent.Future

trait CartResource  extends Directives with CustomJsonSprySupport{


  val cartServiceActor = ActorSystem("CartSystem").actorOf(Props[CartService], "cartServiceActor")

  val settings = CorsSettings.defaultSettings.copy(allowedMethods =
    immutable.Seq(GET, POST, HEAD, OPTIONS,HttpMethods.PUT,HttpMethods.DELETE))

  println(settings)

  def cartRoutes: Route = cors(settings){

    implicit val timeout = Timeout(5.seconds)
    pathPrefix("cart"){
       {
        post {
          entity(as[CombinedCart]) { ShoppingCart =>
            println(ShoppingCart)
         /*   val test =(cartServiceActor ? CreateCart(ShoppingCart))
            onComplete(cartServiceActor ? CreateCart(ShoppingCart)) {
              case Success(id :Int ) => complete(ToResponseMarshallable (new MyInt(id)))
              case Failure(ex) => complete("Failure")
            }
        */
           onSuccess(cartServiceActor ? CreateCart(ShoppingCart)) {
              case id : Int =>
                complete(ToResponseMarshallable (new MyInt(id)))
              case _ =>
                complete(StatusCodes.InternalServerError)
            }
          }
        }

      } ~ // ma3naha eh ?
        path(Segment) {
          Id =>
            get {
              onSuccess(cartServiceActor ? GetCart(Id.toInt)) {
                case wantedCart: CombinedCart =>
                  complete(ToResponseMarshallable(wantedCart))
                case Failed =>
                  complete("This cart was not found");
                case ex:Exception=>
                  complete(StatusCodes.InternalServerError,ex)
              }
            }~
            delete{
                onSuccess(cartServiceActor ? DeleteCart(Id.toInt)) {
                  case Done =>
                    complete(StatusCodes.OK)
                  case Failed =>
                    complete("This cart was not found");
                  case ex:Exception=>
                    complete(StatusCodes.InternalServerError,ex)
                }
              } ~
              put {
                entity(as[CombinedCart]) { updatedCart =>
                  println(updatedCart)
                  onSuccess(cartServiceActor ? UpdateCart(Id.toInt ,updatedCart)) {
                    case Done =>
                      complete(StatusCodes.OK)
                    case Failed =>
                      complete("This cart was not found");
                    case ex:Exception=>
                      complete(StatusCodes.InternalServerError,ex)
                  }

                }
              }
        }
    } ~
      pathPrefix("Items") {
        pathEnd {
          get {

            onSuccess(cartServiceActor ? GetWebSitItems()) {
              case allItems : ItemsList =>
                complete(ToResponseMarshallable (allItems))
              case _ =>
                complete(StatusCodes.InternalServerError)
            }


          }
        }

      }
  }
}

