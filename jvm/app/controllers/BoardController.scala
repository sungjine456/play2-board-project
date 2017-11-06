package controllers

import javax.inject.{ Inject, Singleton }

import scala.concurrent.ExecutionContext.Implicits.global

import play.api.mvc.{ AbstractController, ControllerComponents }

import services.BoardService

@Singleton
class BoardController @Inject()(boardService: BoardService, cc: ControllerComponents) extends AbstractController(cc) {

  import play.api.libs.json.Json

  def boards = Action.async {
    boardService.findAll.map { boards =>
      Ok(Json.obj("boards" -> boards.map(board => board.json)))
    }
  }
}
