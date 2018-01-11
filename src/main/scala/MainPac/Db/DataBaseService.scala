package MainPac.Db
import MainPac.entities._
import io.getquill._

object DataBaseService  {

  def InsertCart(newCart:Carts,cartItems: List[Int]) {
    lazy val ctx = new H2JdbcContext(CamelCase, "Context")

    import ctx._
    def AddToCartsTable(_newCart: Carts) = quote {
      query[Carts].insert(lift(_newCart))
    }

    def AddToSoldItems(_cartItems: List[Int],_CartId:Int)= quote {
      liftQuery(_cartItems).foreach(ItemID => query[SoldItems].insert(new SoldItems(lift(_CartId),ItemID)))
    }
    ctx.run(AddToCartsTable(newCart))
    ctx.run(AddToSoldItems(cartItems,newCart.Id))
    ctx.close()
  }

  def FindCart(CartId:Int) :CombinedCart = {
    lazy val ctx = new H2JdbcContext(CamelCase, "Context")
    import ctx._

    def GetFromCartTable(_CartId:Int) = quote{
      query[Carts].filter(cart => cart.Id == lift(CartId))
    }

    def GetFromSoldItemsTable(_CartId: Int) = quote{
      query[SoldItems].filter(Item => Item.CartId == lift(_CartId))
    }
   var MinCart= ctx.run(GetFromCartTable(CartId))
    var MinCartItems=ctx.run(GetFromSoldItemsTable(CartId)).map(Item => Item.ItemId)
    ctx.close()
    if (MinCart.length ==1 )
      return new CombinedCart(MinCart(0).Id,MinCartItems,MinCart(0).TotalPrice)
    else
      CombinedCart(0,List(),0)

  }

  def DeleteCart(CartId:Int)= {
    lazy val ctx = new H2JdbcContext(CamelCase, "Context")

    import ctx._
    def DeleteFromCartTable(_CartId:Int) = quote{
      query[Carts].filter(cart => cart.Id == lift(CartId)).delete
    }

    def DeleteFormSoldItems(_CartId: Int) = quote{
      query[SoldItems].filter(Item => Item.CartId == lift(_CartId)).delete
    }
    ctx.run(DeleteFromCartTable(CartId))
    ctx.run(DeleteFormSoldItems(CartId))
    ctx.close()
  }

  def UpdateCart(newCart:Carts,cartItems:List[Int]) ={
    lazy val ctx = new H2JdbcContext(CamelCase, "Context")

    import ctx._
  def UpdateInCartTable(_newCart:Carts) = quote{
    query[Carts].filter(cart => cart.Id == lift(_newCart.Id)).update(lift(_newCart))

  }

  def UpdateInSoldItems(_CartId: Int,_CartItems:List[Int]) = {
   def p1= quote {
      query[SoldItems].filter(Item => Item.CartId == lift(_CartId)).delete
    }
    def p2=quote {
      liftQuery(_CartItems).foreach(ItemID => query[SoldItems].insert(new SoldItems(lift(_CartId), ItemID)))
    }
    ctx.run(p1)
    ctx.run(p2)

  }

  ctx.run(UpdateInCartTable(newCart))
  UpdateInSoldItems(newCart.Id,cartItems)
    ctx.close()
}

}
