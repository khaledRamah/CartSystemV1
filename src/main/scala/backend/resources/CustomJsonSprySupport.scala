package backend.resources

import backend.entities._
import backend.services.UpdateCart
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json._

trait CustomJsonSprySupport  extends SprayJsonSupport with DefaultJsonProtocol{

  implicit val CartListFormat = jsonFormat3(Carts)
  implicit val WebsiteItemsFormat = jsonFormat4(WebsiteItems)
  implicit val ItemsFormat =jsonFormat1(ItemsList)
  implicit val UsersFormat =jsonFormat4(Users)
  implicit val SoldItemsFormat =jsonFormat3(SoldItems)
  implicit val SoldItemDetailsFormat=jsonFormat2(SoldItemDetails)
  implicit val SoldItemDetailsListFormat = jsonFormat1(SoldItemDetailsList)
  implicit val FullCartFormat =jsonFormat2(FullCart)


}
