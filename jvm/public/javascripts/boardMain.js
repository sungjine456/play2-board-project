var boardApp = angular.module('boardApp', ['ngResource', 'ui.router'])
    .config(function ($stateProvider) {
        $stateProvider
            .state('boards', {
                url: '',
                templateUrl: '/board'
            });
    });
