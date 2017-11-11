package de.codepitbull.vertx.kubernetes

import io.vertx.lang.scala.ScalaVerticle
import io.vertx.scala.ext.web.Router

import scala.concurrent.Future
import scala.util.{Failure, Success}

class HttpVerticle extends ScalaVerticle {


  override def startFuture(): Future[_] = {
    val publisher = vertx.eventBus().publisher[String]("ping")

    //Create a router to answer GET-requests to "/hello" with "world"
    val router = Router.router(vertx)
    router
      .get("/hello")
      .handler(req => {
              publisher.write("ole!!!!")
              req.response().end(s"world!!")})

    vertx.deployVerticle(ScalaVerticle.nameForVerticle[BusVerticle])

    vertx
      .createHttpServer()
      .requestHandler(router.accept _)
      .listenFuture(8666, "0.0.0.0")
  }
}
