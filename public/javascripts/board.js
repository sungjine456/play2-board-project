boardApp
    .factory('boardService', function ($resource) {
        return $resource('/boards', {}, {
            query: {method: 'GET', isArray: false}
        });
    })
    .controller('boardController', function ($scope, boardService) {
        boardService.get(function (data) {
            $scope.boards = data.boards;
        });
    })
    .directive('boardList', function () {
        return {
            restrict: 'E',
            require: false,
            template: '<li ng-repeat="board in boards"> <div class="row">' +
            '<div class="col-sm-1 text-center">{{$index + 1}}</div>' +
            '<div class="col-sm-8">{{board.title}}</div>' +
            '<div class="col-sm-3 text-center">{{board.writer}}</div>' +
            '</div> </li>'
        }
    });
