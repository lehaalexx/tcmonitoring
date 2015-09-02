define(['app',
        'components/home/homeController'
        ],
function (tcApp) {
  "use strict";
    tcApp.config(['$routeProvider',  function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'resources/app/components/home/homeView.html',
        controller: 'homeController'
      })
      .otherwise({
        redirectTo: '/'
      });
  }]);
});
