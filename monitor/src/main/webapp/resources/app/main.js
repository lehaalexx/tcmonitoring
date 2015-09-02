/* global declarations for jslint*/
/*global document*/
require.config({
  baseUrl: MONITOR_CONFIG.context + '/resources/app/',
    urlArgs: MONITOR_CONFIG.version,
  paths: {
    'jQuery': 'assets/libs/jquery.min',
    'underscore': 'assets/libs/underscore',
    'angular': 'assets/libs/angular',
    'angular-route': 'assets/libs/angular-route',
    'spin': 'assets/libs/spin'
  },
  shim: {
    'angular': { 'exports': 'angular', deps: ['jQuery'] },
    'angular-resource': { deps: ['angular'] },
    'angular-animate': { deps: ['angular'] },
    'angular-route': { deps: ['angular'] },
    'jQuery': { 'exports': 'jQuery' }
  }
});
var deps = ['jQuery', 'angular', 'app', 'appRoutes'];
require(deps, function ($, angular) {
  "use strict";
  $(function () { // using jQuery because it will run this even if DOM load already happened
      angular.bootstrap(document, ['tcMonitorApp']);
  });
});
