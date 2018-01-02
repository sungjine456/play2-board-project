package controllers

import javax.inject.{ Inject, Singleton }

import scala.concurrent.ExecutionContext.Implicits.global

import play.api.Environment
import play.api.data._
import play.api.data.Forms._
import play.api.http.Writeable
import play.api.mvc.{ AbstractController, Codec, ControllerComponents }

import prickle.{ Pickle, Pickler }
import services.BoardService

@Singleton
class BoardWriteController @Inject()(boardService: BoardService, cc: ControllerComponents, env: Environment) extends AbstractController(cc) {

  import BoardWriteController._

  def write() = Action.async(parse.form(writeBoardForm)) {
    implicit request =>
      boardService.write(request.body.title, request.body.context).map {
        Ok(_)
      }
  }

  implicit def modelWriteable[A](implicit pickler: Pickler[A], codec: Codec): Writeable[A] = {
    Writeable(data => codec.encode(Pickle.intoString(data)), Some(JSON))
  }
}

object BoardWriteController {
  case class WriteBoardForm(title: String, context: String)

  val writeBoardForm = Form(
    mapping("title" -> text, "context" -> text)(WriteBoardForm.apply)(WriteBoardForm.unapply)
  )
}
