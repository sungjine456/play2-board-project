package controllers

import javax.inject._

import play.api.mvc._

class BoardPageController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def list = Action {
    Ok(views.html.board())
  }

  def main = Action {
    Ok(views.html.boardMain())
  }
}
