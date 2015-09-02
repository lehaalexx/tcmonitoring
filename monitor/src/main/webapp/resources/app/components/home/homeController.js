/* global declarations for jslint*/
/*global MONITOR_CONFIG*/
define(['app'], function(tcApp) {
    'use strict';

    tcApp.controller('homeController', ['$scope', '$http', '$location', function ($scope, $http) {

        $scope.i18n = MONITOR_CONFIG.localization;

    }]);
});
