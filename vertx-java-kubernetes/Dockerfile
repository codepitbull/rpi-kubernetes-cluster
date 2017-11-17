FROM frolvlad/alpine-oraclejdk8:slim
ADD target/vertx-java-kubernetes-1.0.0-SNAPSHOT-fat.jar /opt/vertx-java-fat.jar
ENTRYPOINT ["java","-XX:+UnlockExperimentalVMOptions","-XX:+UseCGroupMemoryLimitForHeap","-XX:MaxRAMFraction=1","-jar","/opt/vertx-java-fat.jar","-cluster","-cluster-port", "15701"]
EXPOSE 8666
