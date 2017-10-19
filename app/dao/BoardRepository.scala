package dao

import scala.concurrent.Future

import domain.Board
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile

class BoardRepository(val dbConfig: DatabaseConfig[JdbcProfile]) {

  import dbConfig.profile.api._

  class Boards(tag: Tag) extends Table[Board](tag, "Boards") {
    def index = column[Long]("index", O.PrimaryKey, O.AutoInc)

    def title = column[String]("title")

    def context = column[String]("context")

    def writer = column[String]("writer")

    def * = (index, title, context, writer) <> (Board.tupled, Board.unapply _)
  }
  val boards: TableQuery[Boards] = TableQuery[Boards]

  def findAll(): Future[Seq[Board]] = dbConfig.db.run(boards.result)

  def findByIndex(index: Long): Future[Option[Board]] = dbConfig.db.run(boards.filter(_.index === index).result.headOption)

  def delete(index: Long): Future[Int] = dbConfig.db.run(boards.filter(_.index === index).delete)

  def update(board: Board): Future[Int] = {
    dbConfig.db.run(
      boards.filter(_.index === board.index)
        .map(b => (b.title, b.context, b.writer))
        .update((board.title, board.context, board.writer))
    )
  }

  def insert(board: Board): Future[Int] = {
    dbConfig.db.run(boards += board)
  }
}
