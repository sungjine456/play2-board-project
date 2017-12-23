package controller

import scala.util.{ Failure, Success }

import scala.scalajs.js.JSConverters.JSRichGenTraversableOnce
import scala.scalajs.js.annotation.JSExportTopLevel

import factory.BoardService
import scope.BoardScope

import com.greencatsoft.angularjs.core.{ ExceptionHandler, Location, Timeout }
import com.greencatsoft.angularjs.{ AbstractController, AngularExecutionContextProvider, injectable }

@JSExportTopLevel("controller.BoardController")
@injectable("boardController")
class BoardController(
  scope: BoardScope,
  location: Location,
  service: BoardService,
  val timeout: Timeout,
  val exceptionHandler: ExceptionHandler)
  extends AbstractController[BoardScope](scope)
    with AngularExecutionContextProvider {

  service.get() onComplete {
    case Success(boards) => scope.boards = boards.toJSArray
    case Failure(t) => println(t.getMessage)
  }
}
