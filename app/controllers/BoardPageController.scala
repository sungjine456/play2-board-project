package controllers

import javax.inject._

import play.api.mvc._

@Singleton
class BoardPageController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def list = Action {
    Ok(views.html.board())
  }
}
