package dao

import play.api.db.slick.DatabaseConfigProvider

import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile

trait DatabaseSupport extends DatabaseAware {
  def dbConfigProvider: DatabaseConfigProvider

  override def dbConfig: DatabaseConfig[JdbcProfile] = dbConfigProvider.get[JdbcProfile] ensuring (_ != null)
}
