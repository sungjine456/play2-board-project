package services

import scala.concurrent.Future

import domain.Board

trait BoardService {
  def findAll: Future[Seq[Board]]
}