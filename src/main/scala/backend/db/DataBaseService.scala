package backend.db

import backend.entities._
import io.getquill._

trait AllDbServices{

  val ctx = new H2JdbcContext(CamelCase, "Context")
  import ctx._

  class CartMethods{

      def insertCart(newCart: Carts) {
        def addToCartsTable(myNewCart: Carts) = quote {
          query[Carts].insert(lift(myNewCart))
        }
        ctx.run(addToCartsTable(newCart))
      }

      def findCart(CartId: Int): FullCart = {


        def getFromCartTable(cartId: Int) = quote {
          query[Carts].filter(cart => cart.id == lift(CartId))
        }

        def getFromSoldItemsTable(cartId: Int) = quote {
          query[SoldItems].filter(Item => Item.cartId == lift(cartId)).map(x => SoldItems(x.id,x.cartId,x.itemId))
        }

        def getFullSoldItems (ids :List[SoldItems]) = {
          def getOne (id:Int)= quote {
             query[WebsiteItems].filter(item => lift(id) == item.id)
          }
          ids.flatMap(sItem => ctx.run(getOne(sItem.itemId)))
        }

        val soldCartItems= ctx.run(getFromSoldItemsTable(CartId))
        val fullCartItems = getFullSoldItems(soldCartItems)
        val cart = ctx.run(getFromCartTable(CartId))
        val mergeIndexWithValue =(soldCartItems.map(item =>item.id) zip fullCartItems).map(z => SoldItemDetails(z._1,z._2))
        if (cart.nonEmpty)
          FullCart(cart.head, SoldItemDetailsList(mergeIndexWithValue))
        else
          FullCart(Carts(0,0,0), SoldItemDetailsList(List()))

      }

      def deleteCart(CartId: Int): Unit = {
        def deleteFromCartTable(cartId: Int) = quote {
          query[Carts].filter(cart => cart.id == lift(CartId)).delete
        }

        def deleteFormSoldItems(cartId: Int) = quote {
          query[SoldItems].filter(Item => Item.cartId == lift(cartId)).delete
        }

        ctx.run(deleteFromCartTable(CartId))
        ctx.run(deleteFormSoldItems(CartId))

      }

      def updateCart(updatedCart: FullCart): Unit = {
        def updateInCartTable(myNewCart: Carts) = quote {
          query[Carts].filter(cart => cart.id == lift(myNewCart.id)).update(lift(myNewCart))
        }

        ctx.run(updateInCartTable(updatedCart.userCart))
        val idsList=updatedCart.itemsList.allSoldItemDetails.map(oneItem => oneItem.myItem.id)
        soldItemsInstance.addList(idsList,updatedCart.userCart.id)
      }
  }
  class ItemsMethods{
    def getAllItems() : List[WebsiteItems]={
      ctx.run(query[WebsiteItems])
    }
    def addItem(item :WebsiteItems): Int ={
      def insert (item :WebsiteItems) =quote{
        query[WebsiteItems].insert(lift(item)).returning(_.id)
      }
      ctx.run(insert(item))
    }
  }
  class UserMethods{
    def registerUser(newUser:Users): Int ={
      def insert(newUser:Users) = quote {
        query[Users].insert(lift(newUser)).returning(_.id)
      }
      ctx.run(insert(newUser))
    }
    def findUser(myUser:Users) : Users = {
      def find(myUser:Users) =quote{
        query[Users].filter(oneUser =>
          oneUser.email == lift(myUser.email) && oneUser.password ==lift(myUser.password))
      }
      val res = ctx.run(find(myUser))
      if( res.nonEmpty) res.head else Users(0,"","","")
    }
  }
  class SoldItemsMethods{

    def addItem(soldItem :SoldItems): Unit = {

      def insert(soldItem: SoldItems) = quote {
          query[SoldItems].insert(lift(soldItem)).returning(_.id)
        }
        ctx.run(insert(soldItem))
    }
    def deleteItem(id :Int): Unit ={

      def delete(id :Int) = quote {
        query[SoldItems].filter(sItem => sItem.id == lift(id)).delete
      }
      ctx.run(delete(id))
    }
    def addList(itemsIds:List[Int],cartId:Int): Unit = {

      def insert(myItemsIds:List[Int],myCartId:Int) = quote {
        liftQuery(itemsIds).foreach(itemId => query[SoldItems].insert(SoldItems(0,lift(myCartId),itemId)).returning(_.id))
      }
      ctx.run(insert(itemsIds,cartId))
    }

  }

  val cartInstance :CartMethods = new CartMethods()
  val itemInstance :ItemsMethods = new ItemsMethods()
  val userInstance :UserMethods = new UserMethods()
  val soldItemsInstance :SoldItemsMethods = new SoldItemsMethods()

}
object DataBaseService extends AllDbServices
{
  def getCartObject : CartMethods = cartInstance
  def getItemMethods : ItemsMethods = itemInstance
  def getUserMethods : UserMethods = userInstance
  def getSoldItemMethods : SoldItemsMethods = soldItemsInstance
}

