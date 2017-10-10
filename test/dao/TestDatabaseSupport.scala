package dao

import play.api.Configuration
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.{ DBApi, Database }
import play.api.inject.guice.GuiceApplicationBuilder

import com.typesafe.config.ConfigFactory
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile

trait TestDatabaseSupport extends DatabaseAware {
  val config = ConfigFactory.load("testApplication.conf")
  val appBuilder = new GuiceApplicationBuilder().configure(Configuration(config))

  def dbConfigProvider: DatabaseConfigProvider = appBuilder.injector().instanceOf[DatabaseConfigProvider]

  override def dbConfig: DatabaseConfig[JdbcProfile] = dbConfigProvider.get[JdbcProfile] ensuring (_ != null)

  def database: Database = appBuilder.injector().instanceOf[DBApi].database("default")
}
