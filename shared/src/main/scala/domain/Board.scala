package domain

import play.api.libs.json.Json

case class Board(index: Long, title: String, context: String, writer: String) {
  def json = Json.obj("index" -> index, "title" -> title, "context" -> context, "writer" -> writer)
}
