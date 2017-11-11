package de.codepitbull.vertx.kubernetes

import io.vertx.lang.scala.ScalaVerticle

import scala.concurrent.Future

class BusVerticle extends ScalaVerticle {

  override def startFuture(): Future[_] = {
    vertx
      .eventBus()
      .consumer[String]("testAddress")
      .handler(_.reply("Hello World!"))
      .completionFuture()

    vertx
      .eventBus()
      .consumer[String]("ping")
      .handler(r => {
        println(s"Received by ${hashCode()}")
      })
      .completionFuture()


  }
}
