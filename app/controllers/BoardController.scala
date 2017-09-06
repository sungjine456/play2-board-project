package controllers

import javax.inject._

import domain.Board
import play.api.mvc._
import services.BoardService

@Singleton
class BoardController @Inject() (cc: ControllerComponents) extends AbstractController(cc) {
  import play.api.libs.json._

  def board = Action {
    val boardList: Seq[Board] = BoardService.findAll
    val titles: Seq[String] = boardList.map(_.title)
    Ok(Json.toJson(titles))
  }
}
