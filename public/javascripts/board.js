angular.module('boardApp', ['ngResource'])
    .controller('BoardListController', function ($scope, $resource) {
        $scope.titles = $resource('/board-titles').query();
    });