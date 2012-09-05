import sbt._
import Keys._

object MinimalBuild extends Build {
  
  lazy val buildVersion =  "2.1-08292012"
  
  lazy val typesafeSnapshot = "Typesafe Snapshots Repository" at "http://repo.typesafe.com/typesafe/snapshots/"
  lazy val typesafe = "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
  lazy val repo = if (buildVersion.endsWith("SNAPSHOT")) typesafeSnapshot else typesafe  
  
  lazy val play =  "play" % "play_2.9.2" % buildVersion 

  lazy val root = Project(id = "play-mini", base = file("."), settings = Project.defaultSettings).settings(
    version := buildVersion,
    publishTo <<= (version) { version: String =>
                val nexus = "https://typesafe.artifactoryonline.com/typesafe/"
                if (version.trim.endsWith("SNAPSHOT")) Some("snapshots" at nexus + "maven-snapshots/") 
                else                                   Some("releases"  at nexus + "maven-releases/")
    },
    scalaVersion := "2.9.2",
    organization := "com.typesafe",
    resolvers += repo,
    libraryDependencies += play,
    mainClass in (Compile, run) := Some("play.core.server.NettyServer")
  )
}
