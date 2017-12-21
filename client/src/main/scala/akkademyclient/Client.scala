package akkademyclient

import akka.actor.ActorSystem
import com.akkademy.messages.{ SetRequest, GetRequest }

import scala.concurrent.Await
import scala.concurrent.duration._
import akka.util.Timeout
import akka.pattern.ask

import scala.concurrent.ExecutionContext.Implicits.global


object Client {

  val remoteAddress = "127.0.0.1:2552"

  implicit val timeout = Timeout(2 seconds)
  implicit val system = ActorSystem("LocalSystem")

  val url = s"akka.tcp://akkademy@$remoteAddress/user/akkademy-db"
  println(url)

  val remoteDb = system.actorSelection(url)

  def set(key: String, value: Object) = {
    remoteDb ? SetRequest(key, value)
  }

  /*
   // Example usage:

   val future = Client.get(key)
   val result = Await.result(future, 10 seconds)
   println(result)
  */
  def get(key: String) = {
    remoteDb ? GetRequest(key)
  }
}
