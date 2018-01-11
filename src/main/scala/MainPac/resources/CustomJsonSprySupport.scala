package MainPac.resources

import MainPac.entities.{CombinedCart, Item, ItemsList, MyInt}
import MainPac.services.UpdateCart
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json._

trait CustomJsonSprySupport  extends SprayJsonSupport with DefaultJsonProtocol{


  implicit val CartFormat = jsonFormat3(CombinedCart)
  implicit val CartListFormat = jsonFormat2(UpdateCart)
  implicit val ItemFormat = jsonFormat3(Item)
  implicit val ItemsFormat =jsonFormat1(ItemsList)
  implicit val IntFormat =jsonFormat1(MyInt)
}
