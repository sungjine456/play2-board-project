angular.module('boardApp', [])
    .controller('BoardListController', function ($scope, $http) {
        $http({
            method: 'GET',
            url: '/board-titles'
        }).then(function successCallback(response) {
            $scope.titles = response.data
        }, function errorCallback(response) {
            alert(response.status);
        });
    });