package controllers

import javax.inject._
import play.api.mvc._

@Singleton
class AngularTestController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def test = Action {
    Ok(views.html.test())
  }
}
