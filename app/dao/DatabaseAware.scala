package dao

import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile

trait DatabaseAware {
  def dbConfig: DatabaseConfig[JdbcProfile]
}
