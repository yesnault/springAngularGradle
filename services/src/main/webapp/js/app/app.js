/* app.js */

'use strict';
var protoApp = angular.module('protoApp', [ 'ngRoute', 'ngResource']);

protoApp.config(function ($routeProvider) {
    $routeProvider
        .when('/home', {  controller: HomeCtrl, templateUrl: 'partials/home/home.html' })
        .when('/login', {  controller: LoginCtrl, templateUrl: 'partials/login/login.html' })

        .when('/users/', { controller: UsersCtrl, templateUrl: 'partials/users/list.html'})
        .when('/users/new/', {controller: UserNewCtrl, templateUrl: 'partials/users/detail.html'})
        .when('/users/:userId', {controller: UserEditCtrl, templateUrl: 'partials/users/detail.html'})

        .otherwise({
            redirectTo: '/login'
        });
});
