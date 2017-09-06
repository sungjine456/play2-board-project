package controllers

import javax.inject.{ Inject, Singleton }

import play.api.mvc.{ AbstractController, Action, AnyContent, ControllerComponents }

import services.BoardService

@Singleton
class BoardController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  import play.api.libs.json.Json

  def board: Action[AnyContent] = Action {
    val titles: Seq[String] = BoardService.findAll.map(_.title)
    Ok(Json.toJson(titles))
  }
}
