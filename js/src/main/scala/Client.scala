import scala.scalajs.js.annotation.{ JSExport, JSExportTopLevel }

import controller.BoardController
import directory.BoardListDirective
import factory.BoardService

import com.greencatsoft.angularjs.extensions.{ State, StateProvider }
import com.greencatsoft.angularjs.{ Angular, Config }

class Client(stateProvider: StateProvider) extends Config {

  override def initialize() = {
    configRoutes()
  }

  protected def configRoutes() = {
    stateProvider.state("boards", State(url = "", templateUrl = "/board"))
  }
}

@JSExportTopLevel("Client")
object Client {
  @JSExport
  def main(args: Array[String]): Unit = {
    Angular.module("boardApp", Seq("ngResource", "ui.router"))
      .config[Client]

      .controller[BoardController]

      .directive[BoardListDirective]

      .factory[BoardService.Factory]
  }
}
