package services

import scala.concurrent.Future

import com.google.inject.ImplementedBy
import domain.Board

@ImplementedBy(classOf[BoardServiceImpl])
trait BoardService {
  def findAll: Future[Seq[Board]]
}
