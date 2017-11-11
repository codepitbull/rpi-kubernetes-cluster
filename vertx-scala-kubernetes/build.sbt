import sbt.Package._
import sbt._
import Docker.autoImport._
import sbtdocker.ImageName

scalaVersion := "2.12.4"

val buildForArm = false

enablePlugins(DockerPlugin)
exposedPorts := Seq(8666)
useArm := buildForArm
organization := s"codepitbull"

val rpiRegistry = "192.168.2.51:5000/"
val localRegistry = ""
val tag = "latest"

val taggedDockerImage = if(buildForArm)
                          s"${rpiRegistry}codepitbull/vertx-scala-kubernetes:${tag}"
                        else
                          s"${localRegistry}codepitbull/vertx-scala-kubernetes:${tag}"

imageNames in docker := Seq(ImageName(taggedDockerImage))
libraryDependencies ++= Vector (
  Library.vertx_lang_scala,
  Library.vertx_web,
  Library.scalaTest       % "test",
  // Uncomment for clustering
  Library.vertx_hazelcast,
  Library.k8s_hazelcast,

  //required to get rid of some warnings emitted by the scala-compile
  Library.vertx_codegen
)

packageOptions += ManifestAttributes(
  ("Main-Verticle", "scala:de.codepitbull.vertx.kubernetes.HttpVerticle"))

