package backend.db

import backend.entities._
import io.getquill._

trait AllDbServices{

    class cartMethods{
      val ctx = new H2JdbcContext(CamelCase, "Context")

      import ctx._

      def insertCart(newCart: Carts, cartItems: List[Int]) {
        def addToCartsTable(myNewCart: Carts) = quote {
          query[Carts].insert(lift(myNewCart))
        }

        def addToSoldItems(myItems: List[Int], cartId: Int) = quote {
          liftQuery(myItems).foreach(ItemID => query[SoldItems].insert(SoldItems(lift(cartId), ItemID)))
        }

        ctx.run(addToCartsTable(newCart))
        ctx.run(addToSoldItems(cartItems, newCart.Id))
      }

      def findCart(CartId: Int): CombinedCart = {


        def getFromCartTable(cartId: Int) = quote {
          query[Carts].filter(cart => cart.Id == lift(CartId))
        }

        def getFromSoldItemsTable(cartId: Int) = quote {
          query[SoldItems].filter(Item => Item.CartId == lift(cartId))
        }

        val MinCart = ctx.run(getFromCartTable(CartId))
        val MinCartItems = ctx.run(getFromSoldItemsTable(CartId)).map(Item => Item.ItemId)

        //  println(MinCart)
        if (MinCart.nonEmpty)
          CombinedCart(MinCart.head.Id, MinCartItems, MinCart.head.TotalPrice)
        else
          CombinedCart(0, List(), 0)

      }

      def deleteCart(CartId: Int): Unit = {
        def deleteFromCartTable(cartId: Int) = quote {
          query[Carts].filter(cart => cart.Id == lift(CartId)).delete
        }

        def deleteFormSoldItems(cartId: Int) = quote {
          query[SoldItems].filter(Item => Item.CartId == lift(cartId)).delete
        }

        ctx.run(deleteFromCartTable(CartId))
        ctx.run(deleteFormSoldItems(CartId))

      }

      def updateCart(newCart: Carts, cartItems: List[Int]): Unit = {

        def updateInCartTable(myNewCart: Carts) = quote {
          query[Carts].filter(cart => cart.Id == lift(myNewCart.Id)).update(lift(myNewCart))

        }

        def updateInSoldItems(cartId: Int, myCartItems: List[Int]): Unit = {
          def deleteOld() = quote {
            query[SoldItems].filter(Item => Item.CartId == lift(cartId)).delete
          }

          def insertNew() = quote {
            liftQuery(myCartItems).foreach(ItemID => query[SoldItems].insert(SoldItems(lift(cartId), ItemID)))
          }

          ctx.run(deleteOld())
          ctx.run(insertNew())

        }

        ctx.run(updateInCartTable(newCart))
        updateInSoldItems(newCart.Id, cartItems)

      }


  }

  val CartInstance :cartMethods= new cartMethods()

}
object DataBaseService extends AllDbServices
{
  def getCartObject : cartMethods= CartInstance
}

