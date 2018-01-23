package backend.entities

case class ItemsList(items: List[WebsiteItems])

case  class SoldItemDetails(id:Int,myItem :WebsiteItems)

case class SoldItemDetailsList(allSoldItemDetails:List [SoldItemDetails])

case class  FullCart(id:Int, itemsList :SoldItemDetailsList, totalPrice :Int)

// DataBase tables

case class Carts(id:Int, totalPrice :Int)

case class SoldItems (id:Int ,cartId:Int ,itemId:Int)

case class WebsiteItems(id :Int ,name:String, price :Int, description:String)

case class Users(id:Int, name:String, email:String, password:String)
