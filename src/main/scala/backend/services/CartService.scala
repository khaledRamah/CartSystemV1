package backend.services

import akka.actor.Actor
import backend.db.DataBaseService
import backend.entities._

case class CreateCart(cart: Carts)
case class GetCart(id:Int)
case class GetWebSitItems()
case class DeleteCart(id:Int)
case class UpdateCart(newCart: Carts)
case class AddItemCart(cartId :Int,itemId :Int)
case class ConfirmItems(cartObj :FullCart)
case object Done
case object Failed

class CartService extends Actor  {

  def checkIfValid(Id:Int): Boolean = if(DataBaseService.getCartObject.findCart(Id).id !=0) true else false

  override def receive: Receive = {

    case CreateCart(newCart: Carts) =>
      DataBaseService.getCartObject.insertCart( Carts(newCart.id,newCart.totalPrice))
      sender() ! Done


    case GetCart(id:Int) => sender() ! DataBaseService.getCartObject.findCart(id)


    case DeleteCart(id:Int) =>
      if (checkIfValid(id) )
      {
        DataBaseService.getCartObject.deleteCart(id)
        sender() ! Done
      }
      else  sender() ! Failed


    case ConfirmItems(cartObj :FullCart) =>
      DataBaseService.getCartObject.updateCart(cartObj)
      sender() ! Done

  }
}

