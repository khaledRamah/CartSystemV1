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

import scala.concurrent.Future
import scala.util.{Failure, Success}
trait CartResource  extends Directives with CustomJsonSprySupport{


  val cartServiceActor:ActorRef = ActorSystem("CartSystem").actorOf(Props[CartService], "cartServiceActor")
  val itemServiceActor :ActorRef = ActorSystem("CartSystem").actorOf(Props[ItemService], "itemServiceActor")
  val userServiceActor :ActorRef = ActorSystem("CartSystem").actorOf(Props[UserService], "userServiceActor")

  val settings = CorsSettings.defaultSettings.copy(allowedMethods =
    immutable.Seq(GET, POST, HEAD, OPTIONS,HttpMethods.PUT,HttpMethods.DELETE))

  def cartRoutes: Route = cors(settings){

    implicit val timeout = Timeout(5.seconds)
    pathPrefix("user") {
      pathEnd{
        post{
          entity(as[Users]) { user =>

            val actorRes = userServiceActor ? RegisterUser(user)
            onSuccess(actorRes) {
              case fNewUser : Future[Users] => complete(ToResponseMarshallable(fNewUser))
              case _ => complete(StatusCodes.InternalServerError)
            }
            /*  else
                onSuccess( userServiceActor ? GetUser(user)){
                  case userData :Users => complete(ToResponseMarshallable( userData))
                  case _ =>  complete(StatusCodes.InternalServerError)
                }*/
          }
        }
      }~
        path(Segment){
          userName =>
            get{
              val userData= userServiceActor ? GetUser(userName)
              onSuccess(userData) {
                case value : Future[Seq[Users]] => complete(ToResponseMarshallable(value))
                case _ => complete(StatusCodes.InternalServerError)
              }
            }
        }
    }~
    pathPrefix("item") {
        pathEnd {
          post{
            entity(as[WebsiteItems]) { newItem =>
              val insertedItem= itemServiceActor ? AddNewItem(newItem )
              onSuccess(insertedItem) {
                case item:Future[WebsiteItems] => complete(ToResponseMarshallable(item))
                case _ => complete(StatusCodes.InternalServerError)
              }
            }
          }~
          get {
            val itemsList= itemServiceActor ? GetItems ()
              onSuccess(itemsList) {
                case allItems : Future[Seq[WebsiteItems]] =>
                  complete(ToResponseMarshallable (allItems))
                case _ =>
                  complete("there was error")
              }
            }
        }
      }~
    pathPrefix("cart") {
      pathEnd {
        post {
          entity(as[Carts]) { minCart =>
            val insertedCart= cartServiceActor ? CreateCart(minCart)
           onSuccess(insertedCart) {
             case newCart: Future[Carts]=>
                complete(ToResponseMarshallable(newCart))
             case _ =>
                complete(StatusCodes.InternalServerError)
            }
          }
        }
      }~
      path(Segment) {
          Id =>
            get {
              val wantedCart= cartServiceActor ? GetCart(Id.toInt)
              onSuccess(wantedCart) {
                case userFullCart: Future[FullCart] =>
                  complete(ToResponseMarshallable(userFullCart))
                case _=>
                  complete(StatusCodes.InternalServerError)
              }
            }
        }
    }~
    pathPrefix("fullCart") {
      pathEnd {
        post {
          entity(as[FullCart]) { shoppingCart =>
            val userFullCart= cartServiceActor ? ConfirmItems(shoppingCart)
            onSuccess(userFullCart) {
              case userCart:Future[FullCart] =>
                complete(ToResponseMarshallable(userCart))
              case _ =>
                complete(StatusCodes.InternalServerError)
            }
          }
        }
      }
    }
  }
}

