package de.codepitbull.vertx.kubernetes;

import io.vertx.core.AbstractVerticle;

import java.io.InputStream;

public class MainVerticle extends AbstractVerticle {

    @Override
    public void start() throws Exception {

        System.out.println("I AM " + hashCode());

        vertx.createHttpServer().requestHandler(req -> {
              req.response()
                .putHeader("content-type", "text/plain")
                .end("Hello from Vert.x!");
            }).listen(8666);

        vertx.eventBus().consumer("hello", r -> {
            System.out.println("RECEIVED " + r.body());
        });

        vertx.setPeriodic(500, r -> {
           vertx.eventBus().publish("hello", ""+hashCode());
        });

        System.out.println("HTTP server started on port 8666!!");
    }
}
