package backend.db

import slick.jdbc.H2Profile.api._
import slick.sql.FixedSqlAction
import backend.entities._
import slick.dbio.Effect
import slick.jdbc.H2Profile

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait SlickDBService {
  lazy val db = Database.forConfig("SlickContext")
  val slickUsersObj = TableQuery[SlickUsers]
  val slickCartObj= TableQuery[SlickCarts]
  val slickItemsObj= TableQuery[SlickWebsiteItems]
  val slickSoldItemsObj= TableQuery[SlickSoldItems]

  object SlickCartMethods {
    def insertCart(newCart: Carts): Future[Carts] = {

      val InsertQuery = slickCartObj returning slickCartObj.map(_.id) into ((Carts, id) => Carts.copy(id = id))
      val queryAction: FixedSqlAction[Carts, NoStream, Effect.Write] = InsertQuery += newCart
      db.run(queryAction)
    }
    def findFullCart(cartId: Int): Future[FullCart] = {
      val getQuery = slickCartObj.filter(x => x.id === cartId)
      val myCart = db.run(getQuery.result)
      myCart.flatMap(cart => {
        SlickSoldItemsMethods.getSoldItems(cartId).flatMap(items => {
          val soldItems:List[SoldItemDetails] = items.map(oneSoldItem => SoldItemDetails(oneSoldItem._1, oneSoldItem._2)).toList
          Future {FullCart(cart.head,SoldItemDetailsList(soldItems))}
        })
      })
    }
    def updateCart(userFullCart: FullCart): Future[FullCart] ={
      val query= slickCartObj.filter(c => c.id === userFullCart.userCart.userId)
      val queryAction =query.update(userFullCart.userCart)
      val userItems: List[Int] =userFullCart.itemsList.allSoldItemDetails.map(item => item.myItem.id)
      db.run(queryAction).flatMap(u => SlickSoldItemsMethods.insertSoldItems(userItems,userFullCart.userCart.id)).flatMap(items => findFullCart(userFullCart.userCart.userId))
    }
  }

  object SlickUserMethods {
    def registerUser(newUser: Users): Future[Users] = {
      val userInsertQuery = slickUsersObj returning slickUsersObj.map(_.id) into ((Users, id) => Users.copy(id = id)) += newUser
      db.run(userInsertQuery)
    }
    def findUser(email: String): Future[Seq[Users]] = {
      val result = slickUsersObj.filter(oneUser => oneUser.email === email).result
      db.run(result)
    }
  }

  object SlickItemsMethods{
    def getAllItems : Future[Seq[WebsiteItems]] ={
      val query = slickItemsObj
      val action= query.result
      db.run(action)
    }
    def addItem(newItem :WebsiteItems) :Future[WebsiteItems] ={
      val insertQuery = slickItemsObj returning slickItemsObj.map(_.id) into ((item, id) => item.copy(id = id))
      val queryAction = insertQuery += newItem
      db.run(queryAction)
    }
  }

  object SlickSoldItemsMethods{
    def insertSoldItems(userSoldItems: List[Int], cartId: Int): (Future[Seq[SoldItems]]) forSome {type query >: _root_.slick.jdbc.H2Profile.IntoInsertActionComposer[SoldItems, SoldItems] <: _root_.slick.jdbc.H2Profile.IntoInsertActionComposer[SoldItems, SoldItems]} ={
      val query = slickSoldItemsObj returning slickSoldItemsObj.map(_.id) into ((Item, id) => Item.copy(id = id))
      val actionQuery =query ++=userSoldItems map (itemId => SoldItems(0,cartId,itemId))
      db.run(actionQuery)
    }
    def getSoldItems(cartId: Int): Future[Seq[(Int, WebsiteItems)]] ={
      val innerJoin = for {
        (sold, item) <- slickSoldItemsObj join slickItemsObj on (_.itemId === _.id )
        if sold.cartId === cartId
      } yield (sold.id, item)
      db.run(innerJoin.result)
    }
  }

  def defineDB(allSchema : List[H2Profile.SchemaDescription ]): Unit = {
    val dbSession = db.createSession()
    allSchema.foreach(oneSchema =>{
      for(s <- oneSchema.drop.statements ++ oneSchema.create.statements) {
        try {
          dbSession.withPreparedStatement(s)(_.execute)
        } catch {
          case e: Throwable =>  println(e)
        }
      }
    })
  }

  defineDB(List(slickUsersObj.schema,slickCartObj.schema,slickItemsObj.schema,slickSoldItemsObj.schema))
}
