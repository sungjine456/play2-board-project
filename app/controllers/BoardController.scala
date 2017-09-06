package controllers

import javax.inject.{ Inject, Singleton }

import scala.concurrent.ExecutionContext.Implicits.global

import play.api.mvc.{ AbstractController, Action, AnyContent, ControllerComponents }

import services.BoardServiceImpl

@Singleton
class BoardController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  import play.api.libs.json.Json

  def board: Action[AnyContent] = Action.async {
    val boardService = new BoardServiceImpl
    boardService.findAll.map { boards =>
      Ok(Json.toJson(boards.map(_.title)))
    }
  }
}
