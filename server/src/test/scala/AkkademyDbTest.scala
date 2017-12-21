package com.akkademy

import akka.actor.ActorSystem
import akka.testkit.TestActorRef
import com.akkademy.AkkademyDb
import com.akkademy.messages.{ SetRequest, GetRequest }
import org.scalatest.{ BeforeAndAfterEach, Matchers }
import org.scalatest.path.FunSpecLike

import scala.concurrent.Await
import scala.concurrent.duration._
import akka.util.Timeout
import akka.pattern.ask


class AkkademyDbSpec extends FunSpecLike with Matchers {

  implicit val timeout = Timeout(5 seconds)
  implicit val system = ActorSystem()

  describe("akkademyDb") {
    describe("given SetRequest") {
      it("should place key/value into map") {
        val actorRef = TestActorRef(new AkkademyDb)
        actorRef ! SetRequest("key", "value")
        val AkkademyDb = actorRef.underlyingActor
        AkkademyDb.map.get("key") should equal(Some("value"))
      }
    }
    describe("given GetRequest") {
      it("should retrieve value from map") {
        val actorRef = TestActorRef(new AkkademyDb)
        actorRef ! SetRequest("key", "value")
        val future = actorRef ? GetRequest("key")

        val result = Await.result(future, timeout.duration)
        result should equal(Some("value"))
      }
    }
  }
}

