package com.akkademy

import akka.actor.Props
import akka.actor.ActorSystem
import akka.event.Logging
import com.akkademy.messages.{ SetRequest, GetRequest }
import scala.collection.mutable.HashMap

import akka.actor.Actor

class AkkademyDb extends Actor {

  val map = new HashMap[String, Object]
  val log = Logging(context.system, this)

  override def receive = {
    case SetRequest(key, value) => {
      log.info("Received set request: key [{}] value [{}] ", key, value)
      map.put(key, value)
    }
    case GetRequest(key) => {
      log.info("Received get request: key [{}] ", key)
      sender() ! map.get(key)
    }
    case o => log.info("Unknown message [{}]", o)
  }
}

object AkkademyDb extends App {
  val system = ActorSystem("akkademy")
  system.actorOf(Props[AkkademyDb], name = "akkademy-db")
}
