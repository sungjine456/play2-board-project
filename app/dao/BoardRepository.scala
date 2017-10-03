package dao

import scala.concurrent.Future

import domain.Board

class BoardRepository(val databaseSupport: DatabaseSupport) {

  import databaseSupport.profile.api._

  class Boards(tag: Tag) extends Table[Board](tag, "Boards") {
    def index = column[Long]("index", O.PrimaryKey)

    def title = column[String]("title")

    def context = column[String]("context")

    def writer = column[String]("writer")

    def * = (index, title, context, writer) <> (Board.tupled, Board.unapply _)
  }
  val boards: TableQuery[Boards] = TableQuery[Boards]

  def findAll(): Future[Seq[Board]] = databaseSupport.database.run(boards.result)

  def delete(index: Long): Future[Int] = databaseSupport.database.run(boards.filter(_.index === index).delete)

  def update(board: Board): Future[Int] = {
    databaseSupport.database.run(
      boards.filter(_.index === board.index)
        .map(b => (b.title, b.context, b.writer))
        .update((board.title, board.context, board.writer))
    )
  }
}
