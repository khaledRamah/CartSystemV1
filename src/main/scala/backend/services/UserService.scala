package backend.services
import akka.actor.Actor
import backend.db.DataBaseService
import backend.entities.Users


case class RegisterUser(newUser: Users)
case class GetUser(loginData :Users)
class UserService extends Actor{

  override def receive: Receive = {

    case RegisterUser(newUser: Users) =>
      def userId:Int = DataBaseService.getUserMethods.registerUser(newUser)
      sender() ! Users(userId,newUser.name,newUser.email,newUser.password)

    case GetUser(loginData:Users) =>
      def userData :Users =DataBaseService.getUserMethods.findUser(loginData)
      sender() ! userData
  }
}
