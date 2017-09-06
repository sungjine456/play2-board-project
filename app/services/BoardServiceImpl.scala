package services

import scala.concurrent.Future

import dao.BoardRepository
import domain.Board

class BoardServiceImpl extends BoardService {
  override def findAll: Future[Seq[Board]] = BoardRepository.findAll()
}