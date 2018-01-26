package backend.services
import akka.actor.Actor
import backend.db.DataBaseService
import backend.entities._

case class GetItems()
case class AddNewItem(newItem :WebsiteItems)
class ItemService extends Actor{


  override def receive: Receive = {
    case GetItems () =>
      val items :ItemsList = ItemsList(DataBaseService.getItemMethods.getAllItems())
      sender() !  items

    case AddNewItem(newItem :WebsiteItems) =>
      val id :Int = DataBaseService.getItemMethods.addItem(newItem)
      sender() ! id
  }
}
