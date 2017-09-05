package controllers

import javax.inject._

import play.api.mvc._

import services.BoardService

@Singleton
class BoardController @Inject() (cc: ControllerComponents) extends AbstractController(cc) {
  def list = Action {
    Ok(views.html.board())
  }
}
