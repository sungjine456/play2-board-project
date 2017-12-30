import scala.scalajs.js.annotation.{ JSExport, JSExportTopLevel }

import controller.{ BoardController, BoardWriteController }
import directory.BoardListDirective
import factory.BoardService

import com.greencatsoft.angularjs.extensions.{ State, StateProvider, UrlRouterProvider }
import com.greencatsoft.angularjs.{ Angular, Config }

class Client(stateProvider: StateProvider, routerProvider: UrlRouterProvider) extends Config {

  override def initialize() = {
    configRoutes()
  }

  protected def configRoutes() = {
    stateProvider
      .state("boards", State(url = "/", templateUrl = "/board"))
      .state("write", State(url = "/write", templateUrl = "/boardWrite"))

    routerProvider.otherwise("/")
  }
}

@JSExportTopLevel("Client")
object Client {
  @JSExport
  def main(args: Array[String]): Unit = {
    Angular.module("boardApp", Seq("ngResource", "ui.router"))
      .config[Client]

      .controller[BoardController]
      .controller[BoardWriteController]

      .directive[BoardListDirective]

      .factory[BoardService.Factory]
  }
}
