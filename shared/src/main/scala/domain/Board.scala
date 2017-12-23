package domain

import scala.scalajs.js.annotation.JSExportAll

import play.api.libs.json.Json

@JSExportAll
case class Board(index: Long, title: String, context: String, writer: String) {
  def json = Json.obj("index" -> index, "title" -> title.trim, "context" -> context.trim, "writer" -> writer.trim)
}
