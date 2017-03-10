name := """gameapi"""

version := "1.0"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

doc in Compile <<= target.map(_ / "none")

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws,
  "mysql" % "mysql-connector-java" % "5.1.18",
  "com.jason-goodwin" %% "authentikat-jwt" % "0.4.1"
)


fork in run := true