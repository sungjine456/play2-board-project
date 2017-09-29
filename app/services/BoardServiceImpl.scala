package services

import javax.inject.{ Inject, Singleton }

import scala.concurrent.Future

import dao.{ BoardRepository, DatabaseSupport }
import domain.Board

@Singleton
class BoardServiceImpl @Inject() extends BoardService {
  private val repository: BoardRepository = new BoardRepository(new DatabaseSupport)

  override def findAll: Future[Seq[Board]] = repository.findAll()
}
