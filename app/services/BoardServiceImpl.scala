package services

import javax.inject.{ Inject, Singleton }

import scala.concurrent.Future

import dao.BoardRepository
import domain.Board

@Singleton
class BoardServiceImpl @Inject() extends BoardService {
  override def findAll: Future[Seq[Board]] = BoardRepository.findAll()
}
