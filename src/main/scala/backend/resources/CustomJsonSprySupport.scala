package backend.resources

import backend.entities.{CombinedCart, Item, ItemsList}
import backend.services.UpdateCart
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json._

trait CustomJsonSprySupport  extends SprayJsonSupport with DefaultJsonProtocol{


  implicit val CartFormat = jsonFormat3(CombinedCart)
  implicit val CartListFormat = jsonFormat2(UpdateCart)
  implicit val ItemFormat = jsonFormat3(Item)
  implicit val ItemsFormat =jsonFormat1(ItemsList)

}
