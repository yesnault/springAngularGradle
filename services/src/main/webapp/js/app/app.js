/* app.js */

'use strict';
var app = angular.module('app', [ 'ngRoute', 'ngResource']);

app.config(function ($routeProvider) {
    $routeProvider
        .when('/home', {  controller: HomeCtrl, templateUrl: 'partials/home/home.html' })
        .when('/login', {  controller: LoginCtrl, templateUrl: 'partials/login/login.html' })

        // Administration
        .when('/admin/logs', {  controller: LogsCtrl, templateUrl: 'partials/admin/logs/list.html' })
        .when('/admin/metrics', {  controller: MetricsCtrl, templateUrl: 'partials/admin/metrics/metrics.html' })

        .when('/admin/users/', { controller: UsersCtrl, templateUrl: 'partials/admin/users/list.html'})
        .when('/admin/users/new/', {controller: UserNewCtrl, templateUrl: 'partials/admin/users/detail.html'})
        .when('/admin/users/:userId', {controller: UserEditCtrl, templateUrl: 'partials/admin/users/detail.html'})

        .otherwise({
            redirectTo: '/login'
        });
});
