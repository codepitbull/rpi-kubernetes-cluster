import sbt.{Def, _}
import sbtassembly.AssemblyPlugin.autoImport.assembly
import sbtdocker.DockerPlugin
import sbtdocker.DockerPlugin.autoImport.{Dockerfile, docker, dockerfile}

object Docker extends AutoPlugin {

  object autoImport {
    lazy val exposedPorts = SettingKey[Seq[Int]]("exposed-ports", "A list of awesome operating systems")
    lazy val useArm = SettingKey[Boolean]("use-arm", "Build for arm")
  }

  import autoImport._

  override def trigger = allRequirements

  override def requires: Plugins = DockerPlugin

  override def projectSettings: Seq[Def.Setting[_]] =
    Vector(
      exposedPorts := Seq(8666),
      useArm := false,
      dockerfile in docker := {
        // The assembly task generates a fat JAR file
        val artifact: File = assembly.value
        val artifactTargetPath = s"/app/${artifact.name}"
        if (!useArm.value) {
          new Dockerfile {
            from("frolvlad/alpine-oraclejdk8:slim")
            add(artifact, artifactTargetPath)
            entryPoint("java", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap" ,"-XX:MaxRAMFraction=1", "-jar", artifactTargetPath, "-cluster", "-cluster-port", "15701")
            expose(exposedPorts.value: _*)
          }
        }
        else {
          new Dockerfile {
            from("hypriot/rpi-java:jre-1.8.111")
            add(artifact, artifactTargetPath)
            entryPoint("java", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap" ,"-XX:MaxRAMFraction=1", "-jar", artifactTargetPath, "-cluster", "-cluster-port", "15701")
            expose(exposedPorts.value: _*)
          }
        }
      }
    )
}
