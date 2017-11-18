package de.codepitbull.vertx.kubernetes;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;

import java.io.InputStream;

public class MainVerticle extends AbstractVerticle {

    @Override
    public void start() throws Exception {

        System.out.println("I AM " + hashCode());

        Router router = Router.router(vertx);
        router.get("/hello").handler(r -> {
            r.response().end("Hello World");
        });

        vertx.createHttpServer().requestHandler(router::accept).listen(8666);

        vertx.eventBus().consumer("hello", r -> {
            System.out.println("RECEIVED " + r.body());
        });

        vertx.setPeriodic(500, r -> {
           vertx.eventBus().publish("hello", ""+hashCode());
        });

        System.out.println("HTTP server started on port 8666!!");
    }
}
