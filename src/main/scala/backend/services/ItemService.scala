package backend.services
import akka.actor.Actor
import backend.db.{DataBaseService, SlickDBService}
import backend.entities._

import scala.concurrent.Future

case class GetItems()
case class AddNewItem(newItem: WebsiteItems)
class ItemService extends Actor with SlickDBService {


  override def receive: Receive = {
    case GetItems () =>
      val allItems: Future[Seq[WebsiteItems]] = SlickItemsMethods.getAllItems
      sender() ! allItems

    case AddNewItem(newItem :WebsiteItems) =>
      val item = SlickItemsMethods.addItem(newItem)
      sender() ! item
  }
}
