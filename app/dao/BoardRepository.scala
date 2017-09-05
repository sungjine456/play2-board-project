package dao

import scala.concurrent.Future

import domain.Board
import slick.jdbc.PostgresProfile.api._

object BoardRepository {
  class boards(tag: Tag) extends Table[Board](tag, "boards") {
    def idx = column[Long]("idx", O.PrimaryKey)
    def title = column[String]("title")
    def context = column[String]("context")
    def writer = column[String]("writer")
    def * = (idx, title, context, writer) <> (Board.tupled, Board.unapply _)
  }

  val db = Database.forConfig("mydb")
  val boards = TableQuery[boards]

  def findAll(): Future[Seq[Board]] = db.run(boards.result)
}
