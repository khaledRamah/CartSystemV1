package backend.entities
import  slick.jdbc.H2Profile.api._

case class ItemsList(items: List[WebsiteItems])

case  class SoldItemDetails(id:Int, myItem :WebsiteItems)

case class SoldItemDetailsList(allSoldItemDetails:List [SoldItemDetails])

case class  FullCart(userCart:Carts, itemsList :SoldItemDetailsList)

// DataBase tables

case class Carts(id:Int, userId :Int, totalPrice:Int)

case class SoldItems (id:Int, cartId:Int, itemId:Int)

case class WebsiteItems(id:Int, name:String, price:Int, description:String)

case class Users(id:Int, name:String, email:String, password:String)


// slick

class SlickCarts(tag: Tag) extends Table[Carts](tag, "carts") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def userId = column[Int]("userId")
  def totalPrice = column[Int]("totalPrice" ,O.Default(0))
  def * = (id,userId ,totalPrice) <> (Carts.tupled, Carts.unapply)
}

class SlickUsers(tag: Tag) extends Table[Users](tag,"users") {
  def id = column[Int] ("id", O.PrimaryKey, O.AutoInc)
  def name = column[String] ("name")
  def email = column[String] ("email")
  def password = column[String] ("password")
  def * = (id, name, email, password) <> (Users.tupled, Users.unapply)
}

class SlickWebsiteItems(tag: Tag) extends Table[WebsiteItems] (tag ,"websiteItems") {
  def id = column[Int] ("id", O.PrimaryKey, O.AutoInc)
  def name = column[String] ("name")
  def price = column[Int] ("price")
  def description = column[String] ("description")
  def * = (id, name, price, description) <> (WebsiteItems.tupled ,WebsiteItems.unapply)
}

class SlickSoldItems(tag: Tag) extends Table[SoldItems] (tag ,"soldItems"){
  def id = column[Int] ("id", O.PrimaryKey, O.AutoInc)
  def cartId = column[Int] ("cartId")
  def itemId = column[Int] ("itemId")
  def * = (id, cartId, itemId) <> (SoldItems.tupled ,SoldItems.unapply)
}