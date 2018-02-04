package backend.services
import akka.actor.Actor
import backend.db.SlickDBService
import backend.entities.Users

import scala.concurrent.Future


case class RegisterUser(newUser: Users)
case class GetUser(userName :String)
class UserService extends Actor with SlickDBService{

  override def receive: Receive = {

    case RegisterUser(newUser: Users) =>
      val newUserWithId: Future[Users] = SlickUserMethods.registerUser(newUser)
      sender() ! newUserWithId

    case GetUser(email: String) =>
      val userData: Future[Seq[Users]] = SlickUserMethods.findUser(email)
      sender() ! userData
  }
}
