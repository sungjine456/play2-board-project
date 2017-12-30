package controller

import scala.scalajs.js.annotation.{ JSExport, JSExportTopLevel }

import factory.BoardService
import scope.BoardScope

import com.greencatsoft.angularjs.core.Location
import com.greencatsoft.angularjs.{ AbstractController, injectable }

@JSExportTopLevel("controller.BoardWriteController")
@injectable("boardWriteController")
class BoardWriteController(
  scope: BoardScope,
  location: Location,
  service: BoardService)
  extends AbstractController[BoardScope](scope) {

  @JSExport
  def boardWrite() {
    service.write(scope.title, scope.content)

    location.url("/")
  }
}
