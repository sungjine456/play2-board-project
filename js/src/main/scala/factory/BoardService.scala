package factory

import scala.concurrent.Future
import scala.util.{ Failure, Success, Try }

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import scala.scalajs.js
import scala.scalajs.js.JSConverters._
import scala.scalajs.js.JSON
import scala.scalajs.js.annotation.JSExport

import org.scalajs.jquery.jQuery

import domain.Board
import prickle.Unpickle

import com.greencatsoft.angularjs
import com.greencatsoft.angularjs.core.{ HttpConfig, HttpService }
import com.greencatsoft.angularjs.{ Service, injectable }

@injectable("BoardService")
class BoardService(http: HttpService) extends Service {

  @JSExport
  def get(): Future[Seq[Board]] = flatten {
    http.get[js.Any]("/boards")
      .map(JSON.stringify(_))
      .map(Unpickle[Seq[Board]].fromString(_))
  }

  @JSExport
  def write(title: String, context: String) = {
    val data: Map[String, String] = Map("title" -> title, "context" -> context)

    http.put[js.Any]("/board", jQuery.param(data.toJSDictionary, traditional = true), HttpConfig.postHandler)
      .map(_.toString)
  }

  def flatten[T](future: Future[Try[T]]): Future[T] = {
    require(future != null, "Missing argument 'future'.")

    future flatMap {
      case Success(s) => Future.successful(s)
      case Failure(f) => Future.failed(f)
    }
  }
}

object BoardService {

  @injectable("BoardService")
  class Factory(http: HttpService) extends angularjs.Factory[BoardService] {

    override def apply(): BoardService = new BoardService(http)
  }
}
