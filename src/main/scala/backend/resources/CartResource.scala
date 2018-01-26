package backend.resources


import akka.actor.{ActorRef, ActorSystem, Props}
import akka.pattern.ask

import scala.concurrent.duration._
import akka.util.Timeout
import backend.entities._
import backend.services._
import akka.http.scaladsl.marshalling.ToResponseMarshallable
import akka.http.scaladsl.model.{HttpMethods, StatusCodes}
import akka.http.scaladsl.server.{Directives, Route}
import ch.megard.akka.http.cors.scaladsl.settings.CorsSettings
import ch.megard.akka.http.cors.scaladsl.CorsDirectives._
import akka.http.scaladsl.model.HttpMethods._
import backend.services.Failed

import scala.collection._

import spray.json._
trait CartResource  extends Directives with CustomJsonSprySupport{


  val cartServiceActor:ActorRef = ActorSystem("CartSystem").actorOf(Props[CartService], "cartServiceActor")
  val itemServiceActor :ActorRef = ActorSystem("CartSystem").actorOf(Props[ItemService], "itemServiceActor")
  val userServiceActor :ActorRef = ActorSystem("CartSystem").actorOf(Props[UserService], "userServiceActor")

  val settings = CorsSettings.defaultSettings.copy(allowedMethods =
    immutable.Seq(GET, POST, HEAD, OPTIONS,HttpMethods.PUT,HttpMethods.DELETE))

  def cartRoutes: Route = cors(settings){

    implicit val timeout = Timeout(5.seconds)
    pathPrefix("cart"){
      pathEnd {
        post {
          entity(as[Carts]) { minCart =>
            println(minCart)
           onSuccess(cartServiceActor ? CreateCart(minCart)) {
              case Done =>
                complete(StatusCodes.OK)
              case _ =>
                complete(StatusCodes.InternalServerError)
            }
          }
        }
      } ~
      path(Segment) {
          Id =>
            get {
              onSuccess(cartServiceActor ? GetCart(Id.toInt)) {
                case wantedCart: FullCart =>
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
                entity(as[Carts]) { updatedCart =>
                  onSuccess(cartServiceActor ? UpdateCart(updatedCart)) {
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
    pathPrefix("item") {
        pathEnd {
          post{
            entity(as[WebsiteItems]) { newItem =>
              onSuccess(itemServiceActor ? AddNewItem(newItem )) {
                case id:Int => complete(StatusCodes.OK)
                case _ => complete(StatusCodes.InternalServerError)
              }
            }
          }~
          get {
            onSuccess(itemServiceActor ? GetItems ()) {
              case allItems : ItemsList =>
                complete(ToResponseMarshallable (allItems))
              case _ =>
                complete("there was error")
            }
          }
        }
      }~
    pathPrefix("user") {
     pathEnd{
       post{
         entity(as[Users]) { user =>
           if (user.id == -1)
           onSuccess( userServiceActor ? RegisterUser(user)){
             case newUser :Users => complete(ToResponseMarshallable(newUser))
             case _ =>  complete(StatusCodes.InternalServerError)
           }
           else
             onSuccess( userServiceActor ? GetUser(user)){
               case userData :Users => complete(ToResponseMarshallable( userData))
               case _ =>  complete(StatusCodes.InternalServerError)
             }
         }
       }
      }
    }~
    pathPrefix("fullCart") {
      pathEnd {
        post {
          entity(as[FullCart]) { shoppingCart =>
            println(shoppingCart)
            onSuccess(cartServiceActor ? ConfirmItems(shoppingCart)) {
              case Done =>
                complete(StatusCodes.OK)
              case _ =>
                complete(StatusCodes.InternalServerError)
            }
          }
        }
      }
    }
  }
}

