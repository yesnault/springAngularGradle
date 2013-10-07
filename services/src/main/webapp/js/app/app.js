/* app.js */

'use strict';
var app = angular.module('protoApp', [ 'ngRoute', 'ngResource']);

app.config(function ($routeProvider) {
    $routeProvider
        .when('/home', {  controller: HomeCtrl, templateUrl: 'partials/home/home.html' })
        .when('/login', {  controller: LoginCtrl, templateUrl: 'partials/login/login.html' })

        // Administration
        .when('/admin/logs', {  controller: LogsCtrl, templateUrl: 'partials/admin/logs/list.html' })

        .when('/users/', { controller: UsersCtrl, templateUrl: 'partials/admin/users/list.html'})
        .when('/users/new/', {controller: UserNewCtrl, templateUrl: 'partials/admin/users/detail.html'})
        .when('/users/:userId', {controller: UserEditCtrl, templateUrl: 'partials/admin/users/detail.html'})

        .otherwise({
            redirectTo: '/login'
        });
});
