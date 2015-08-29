/* global declarations for jslint*/
/*global NETSUITE_CONFIG*/
define([
    'angular',
    'underscore',
    'angular-route'
], function (angular, _) {
    "use strict";
    var app = angular.module('netSuiteApp', [
        'ngRoute'
    ]);
    app.config(function ($httpProvider) {
        $httpProvider.defaults.headers.post[NETSUITE_CONFIG.csrfHeader] = NETSUITE_CONFIG.csrf;
    });
    return app;
});
