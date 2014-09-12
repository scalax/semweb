import bintray.Plugin.bintraySettings
import sbt.Keys._
import sbt._

lazy val root = project.in(file("."))

lazy val js = project.in(file("js"))

lazy val sesame = project.in(file("sesame")).dependsOn(root)

Build.sharedSettings

version := Build.currentVersion

unmanagedSourceDirectories in Compile <+= baseDirectory(_ / "shared" / "main" / "scala")

unmanagedSourceDirectories in Test <+= baseDirectory(_ / "shared" / "test" / "scala")

//test in Test <<= (test in Test) dependsOn (test in (sesame, Test)) //run sesame tests when rdfs are tested


libraryDependencies ++= Seq(
  "com.lihaoyi" %% "utest" % "0.2.3" % "test"
)

testFrameworks += new TestFramework("utest.runner.JvmFramework")

libraryDependencies += "org.scalajs" %% "scalajs-pickling-play-json" % "0.3.1"

resolvers += "bintray-alexander_myltsev" at "http://dl.bintray.com/alexander-myltsev/maven/"

libraryDependencies += "org.parboiled" %%% "parboiled" % "2.0.1"

autoCompilerPlugins := true

bintraySettings

Build.publishSettings
