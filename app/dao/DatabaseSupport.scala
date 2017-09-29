package dao

import com.typesafe.config.{ Config, ConfigFactory }
import slick.jdbc.{ JdbcProfile, PostgresProfile }

class DatabaseSupport(val config: Config, val profile: JdbcProfile) {
  def this() = this(ConfigFactory.load("application.conf"), PostgresProfile)

  import profile.api._

  val database: Database = Database.forConfig("slick.dbs.default.db", config)
}
