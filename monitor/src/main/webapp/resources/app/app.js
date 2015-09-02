/* global declarations for jslint*/
/*global MONITOR_CONFIG*/
define([
    'angular',
    'underscore',
    'angular-route'
], function (angular, _) {
    "use strict";
    var app = angular.module('tcMonitorApp', [
        'ngRoute'
    ]);
    app.config(function ($httpProvider) {
        $httpProvider.defaults.headers.post[MONITOR_CONFIG.csrfHeader] = MONITOR_CONFIG.csrf;
    });
    return app;
});
