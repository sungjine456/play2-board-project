package services

import scala.concurrent.duration.DurationInt

import scala.concurrent.Await

import dao.BoardRepository
import domain.Board

object BoardService {
  def findAll: Seq[Board] = {
    Await.result(BoardRepository.findAll(), 1 seconds)
  }
}
