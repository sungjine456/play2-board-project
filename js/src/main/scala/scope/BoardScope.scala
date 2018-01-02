package scope

import scala.scalajs.js

import com.greencatsoft.angularjs.core.Scope

@js.native
trait BoardScope extends Scope {
  var title: String = js.native
  var context: String = js.native
}
