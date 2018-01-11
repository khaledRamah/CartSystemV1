package MainPac.entities

final case class CombinedCart (var Id:Int,var ItemsList : List[Int],var TotalPrice :Int)

case class Item(var Name:String ,var Price :Int ,var Description:String /*,var Amount :Int */)

case class MyInt(Id:Int)

final case class ItemsList(items: Map[String,Item] ) // msh naf3 23mlo int we item


// DataBase tables

case class Carts( Id:Int, TotalPrice :Int)

case class SoldItems ( CartId:Int,ItemId:Int)
