package dao

import scala.concurrent.Future

import domain.Board
import slick.jdbc.PostgresProfile.api._

object BoardRepository {
  class Boards(tag: Tag) extends Table[Board](tag, "Boards") {
    def index = column[Long]("index", O.PrimaryKey)

    def title = column[String]("title")

    def context = column[String]("context")

    def writer = column[String]("writer")

    def * = (index, title, context, writer) <> (Board.tupled, Board.unapply _)
  }

  val db = Database.forConfig("slick.dbs.default.db")
  val boards = TableQuery[Boards]

  def findAll(): Future[Seq[Board]] = db.run(boards.result)
}
