package services

import javax.inject.{ Inject, Singleton }

import scala.concurrent.Future

import play.api.db.slick.DatabaseConfigProvider

import dao.{ BoardRepository, DatabaseSupport }
import domain.Board

@Singleton
class BoardServiceImpl @Inject()(val dbConfigProvider: DatabaseConfigProvider) extends BoardService with DatabaseSupport {
  private val repository = new BoardRepository(dbConfig)

  override def findAll: Future[Seq[Board]] = repository.findAll()

  override def write(title: String, context: String): Future[Int] = {
    repository.insert(Board(1,title, context))
  }
}
