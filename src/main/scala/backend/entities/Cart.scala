package backend.entities

case class CombinedCart ( Id:Int, ItemsList : List[Int], TotalPrice :Int)

case class Item( Name:String , Price :Int , Description:String /*,var Amount :Int */)

case class ItemsList(items: Map[String,Item] )


// DataBase tables

case class Carts( Id:Int, TotalPrice :Int)

case class SoldItems ( CartId:Int,ItemId:Int)
