package controllers

import javax.inject.{ Inject, Singleton }

import scala.concurrent.ExecutionContext.Implicits.global

import play.api.http.Writeable
import play.api.mvc.{ AbstractController, Codec, ControllerComponents }

import prickle.{ Pickle, Pickler }
import services.BoardService

@Singleton
class BoardController @Inject()(boardService: BoardService, cc: ControllerComponents) extends AbstractController(cc) {

  def boards() = Action async {
    boardService.findAll.map {
      Ok(_)
    }
  }

  implicit def writeableModelSeq[A](implicit pickler: Pickler[A], codec: Codec): Writeable[Seq[A]] = {
    Writeable(data => codec.encode(Pickle.intoString(data)), Some(JSON))
  }
}
