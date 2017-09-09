package controllers

import javax.inject.{ Inject, Singleton }

import scala.concurrent.ExecutionContext.Implicits.global

import play.api.mvc.{ AbstractController, Action, AnyContent, ControllerComponents }

import services.BoardServiceImpl

@Singleton
class BoardController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  import play.api.libs.json.Json

  def boards: Action[AnyContent] = Action.async {
    val boardService = new BoardServiceImpl
    boardService.findAll.map { boards =>
      Ok(
        Json.toJsObject(
          Map(
            "boards" -> boards.map(board =>
              Map(
                "title" -> Json.toJson(board.title),
                "writer" -> Json.toJson(board.writer)
              )
            )
          )
        )
      )
    }
  }
}
