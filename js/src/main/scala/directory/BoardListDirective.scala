package directory

import scope.BoardListScope

import com.greencatsoft.angularjs.{ ElementDirective, TemplateSourceDirective, injectable }

@injectable("boardList")
class BoardListDirective extends ElementDirective with TemplateSourceDirective {
  override type ScopeType = BoardListScope

  override val template = "<li ng-repeat='board in boards'> <div class='row'>" +
    "<div class='col-sm-1 text-center'>{{$index + 1}}</div>" +
    "<div class='col-sm-8'>{{board.title}}</div>" +
    "<div class='col-sm-3 text-center'>{{board.writer}}</div>" +
    "</div> </li>"
}
