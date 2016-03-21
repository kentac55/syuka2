import com.typesafe.config.ConfigFactory

name := """syuka2"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
//  jdbcとslickを同時に有効化するとバグるらしい
//  jdbc,
//  cache,
//  ws,
  "com.h2database" % "h2" % "1.4.191",
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.0-RC1" % Test,
  "com.typesafe.play" %% "play-slick" % "2.0.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "2.0.0",
  "com.typesafe.slick" %% "slick-codegen" % "3.1.1"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Slick code generator
// パクリ元
// http://laughingman7743.hatenablog.com/entry/2015/07/11/144335
// `sbt slickCodeGen` で勝手にmodels.Tablesにdbモデルを出力してくれる神
slickCodeGen <<= slickCodeGenTask
// コンパイル時に自動でmodels.Tablesを生成してくれる
// リロードする度に起動するので基本コメントアウト
//(compile in Compile) <<= (compile in Compile) dependsOn slickCodeGenTask
lazy val config = ConfigFactory.parseFile(new File("./conf/application.conf"))
lazy val slickCodeGen = taskKey[Seq[File]]("slick-codegen")
lazy val slickCodeGenTask = (sourceManaged, dependencyClasspath in Compile, runner in Compile, streams) map { (dir, cp, r, s) =>
  val slickDriver = config.getString("slick.dbs.default.driver").init
  val jdbcDriver = config.getString("slick.dbs.default.db.driver")
  val url = config.getString("slick.dbs.default.db.url")
  val user = config.getString("slick.dbs.default.db.user")
  val password = config.getString("slick.dbs.default.db.password")
  val outputDir = "app/"
  val pkg = "models"
  toError(r.run("slick.codegen.SourceCodeGenerator", cp.files, Array(slickDriver, jdbcDriver, url, outputDir, pkg, user, password), s.log))
  val fname = outputDir + "/Tables.scala"
  Seq(file(fname))
}