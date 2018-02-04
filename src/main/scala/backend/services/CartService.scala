package backend.services

import akka.actor.Actor
import akka.http.javadsl.server.RequestContext
import akka.http.scaladsl.server.RouteResult
import backend.db.{DataBaseService, SlickDBService}
import backend.entities._

import scala.concurrent.Future


case class CreateCart(cart: Carts)
case class GetCart(id:Int)
case class GetWebSitItems()
case class DeleteCart(id:Int)
case class UpdateCart(newCart: Carts)
case class AddItemCart(cartId :Int,itemId :Int)
case class ConfirmItems(cartObj :FullCart)
case object Done
case object Failed

class CartService extends Actor with SlickDBService{

  def checkIfValid(Id:Int): Boolean = true //if(DataBaseService.getCartObject.findCart(Id).userCart.id !=0) true else false

  override def receive: Receive = {

    case CreateCart(newCart: Carts) =>
      val cart: Future[Carts]=SlickCartMethods.insertCart(newCart)
      sender() ! cart

    case GetCart(id:Int) =>
      val foundCarts : Future[FullCart]= SlickCartMethods.findFullCart(id)
      sender() !  foundCarts

    case ConfirmItems(cartObj :FullCart) =>
      val fullCart: Future[FullCart] = SlickCartMethods.updateCart(cartObj)
      sender() ! fullCart

  }
}

