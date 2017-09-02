package dao

import scala.concurrent.ExecutionContext.Implicits.global
import slick.jdbc.PostgresProfile.api._

object UserRepository {
  class users(tag: Tag) extends Table[(String, String, String)](tag, "users") {
    def id = column[String]("id", O.PrimaryKey)
    def password = column[String]("password")
    def name = column[String]("name")
    def * = (id, password, name)
  }
  val url = "jdbc:postgresql://localhost:7002/boarddb"
  val user = "boarduser"
  val password = "password"
  val driver = "org.postgresql.Driver"

  val db = Database.forURL(url, user = user, password = password, driver = driver)
  val users = TableQuery[users]

  def findAll = {
    db.run(users.result).map(_.foreach {
      case (user) =>
        println("  " + user)
    })
  }
}