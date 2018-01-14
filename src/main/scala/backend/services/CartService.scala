package backend.services

import akka.actor.Actor
import backend.db.DataBaseService
import backend.entities._

case class CreateCart(cart: CombinedCart)
case class GetCart(id:Int)
case class GetWebSitItems()
case class DeleteCart(id:Int)
case class UpdateCart(id :Int ,newCart: CombinedCart)
case object Done
case object Failed

class CartService extends Actor  {

  def checkIfValid(Id:Int): Boolean = if(DataBaseService.getCartObject.findCart(Id).id !=0) true else false

  override def receive: Receive = {

    case CreateCart(newCart: CombinedCart) =>
      DataBaseService.getCartObject.insertCart( Carts(newCart.id,newCart.totalPrice),newCart.itemsList)
      sender() ! Done


    case GetCart(id:Int) => sender() ! DataBaseService.getCartObject.findCart(id)


    case DeleteCart(id:Int) =>
      if (checkIfValid(id) )
      {
        DataBaseService.getCartObject.deleteCart(id)
        sender() ! Done
      }
      else  sender() ! Failed


    case UpdateCart(id :Int ,newCart: CombinedCart) =>
      DataBaseService.getCartObject.updateCart(Carts(id,newCart.totalPrice),newCart.itemsList)
      sender() ! Done


    case GetWebSitItems() =>
      var Items :Map[String,Item] = Map()
     (1 until 16 ) foreach (elementNumber => {
       Items += (elementNumber.toString->   Item("Item " + elementNumber.toString, elementNumber, "Description " + elementNumber.toString))
     })
       sender() !  ItemsList(Items)

  }
}

