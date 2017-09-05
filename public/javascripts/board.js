angular.module('boardApp', [])
    .controller('BoardListController', function ($scope, $http) {
        $http({
            method: 'GET',
            url: '/getBoard'
        }).then(function successCallback(response) {
            $scope.titles = response.data
        }, function errorCallback(response) {
            alert(response.status);
        });
    });