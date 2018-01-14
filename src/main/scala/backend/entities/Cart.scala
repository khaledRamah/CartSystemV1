package backend.entities

case class CombinedCart (id:Int, itemsList : List[Int], totalPrice :Int)

case class Item(name:String, price :Int, description:String /*,var Amount :Int */)

case class ItemsList(items: Map[String,Item] )


// DataBase tables

case class Carts(id:Int, totalPrice :Int)

case class SoldItems (cartId:Int, itemId:Int)
