package MainPac.services

import MainPac.Db.DataBaseService
import akka.actor.Actor
import MainPac.entities._

case class CreateCart(cart: CombinedCart)
case class GetCart(id:Int)
case class GetWebSitItems()
case class DeleteCart(id:Int)
case class UpdateCart(id :Int ,newCart: CombinedCart)
case object Done
case object Failed

class CartService extends Actor {

  var TotalCarts = 0


  def checkifvaild(Id:Int): Boolean = if(DataBaseService.FindCart(Id).Id !=0) true else false// Carts exists (cartobj => cartobj.Id == Id )

  override def receive: Receive = {

    case CreateCart(newCart: CombinedCart) => {


      DataBaseService.InsertCart(new Carts(newCart.Id,newCart.TotalPrice),newCart.ItemsList)
        sender() ! newCart.Id
      }

    case GetCart(id:Int) => {
          sender() ! DataBaseService.FindCart(id)
      }

    case DeleteCart(id:Int) => {
      if (checkifvaild(id) )
      {
        DataBaseService.DeleteCart(id)
        sender() ! Done
      }
      else  sender() ! Failed
    }

    case UpdateCart(id :Int ,newCart: CombinedCart) => {

      DataBaseService.UpdateCart(new Carts(newCart.Id,newCart.TotalPrice),newCart.ItemsList)
      sender() ! Done
      }

    case GetWebSitItems() =>{
      var Items :Map[String,Item] = Map()
     (1 until (16) ) foreach (Elementnumber => {
       Items += (Elementnumber.toString() ->  new Item("Item " + Elementnumber.toString(), Elementnumber, "Description " + Elementnumber.toString()))
     })
       sender() ! new ItemsList(Items)
    }
  }
}

