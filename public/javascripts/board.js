angular.module('boardApp', ['ngResource'])
    .factory('boardService', function($resource) {
        return $resource('/boards',{},{
            query:{method:'GET', isArray:false}
        });
    })
    .controller('BoardListController', function ($scope, boardService) {
        boardService.get(function(data) {
            $scope.boards = data.boards;
        });
    });
