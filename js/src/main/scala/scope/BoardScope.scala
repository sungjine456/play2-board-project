package scope

import scala.scalajs.js

import domain.Board

import com.greencatsoft.angularjs.core.Scope

@js.native
trait BoardScope extends Scope {
  var boards: js.Array[Board] = js.native
}
