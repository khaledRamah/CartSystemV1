package backend.services
import akka.actor.Actor
import backend.db.DataBaseService.itemInstance
import backend.entities.{Item, ItemsList}

case class GetItems()
class ItemService extends Actor{


  override def receive: Receive = {
    case GetItems () =>
      val items :ItemsList = ItemsList(itemInstance.getAllItems())
      sender() !  items
  }
}
