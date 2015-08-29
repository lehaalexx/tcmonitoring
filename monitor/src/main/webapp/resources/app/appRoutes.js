define(['app',
        'components/home/homeController',
        'components/requestorcontacts/requestorContactsController',
        'components/customercontacts/customerContactsController',
        'components/technicalinformation/technicalInformationController',
        'components/sitelocations/siteLocationsController',
        'components/summary/summaryController',
        'components/checkstatus/checkStatusController'
        ],
function (scancenterApp) {
  "use strict";
  scancenterApp.config(['$routeProvider',  function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'resources/app/components/home/homeView.html',
        controller: 'homeController'
      })
      /*
      .when('/requestorcontacts/:parent', {
      */
      .when('/requestorcontacts/Sales_Partner', {
        templateUrl: 'resources/app/components/requestorcontacts/requestorContactsView.html',
        controller: 'requestorContactsController'
      })
      .when('/customercontacts', {
          templateUrl: 'resources/app/components/customercontacts/customerContactsView.html',
          controller: 'customerContactsController'
      })
      .when('/technicalinformation', {
          templateUrl: 'resources/app/components/technicalinformation/technicalInformationView.html',
          controller: 'technicalInformationController'
      })
      .when('/sitelocations', {
          templateUrl: 'resources/app/components/sitelocations/siteLocationsView.html',
          controller: 'siteLocationsController'
      })
      .when('/summary', {
          templateUrl: 'resources/app/components/summary/summaryView.html',
          controller: 'summaryController'
      })
      .when('/checkstatus', {
            templateUrl: 'resources/app/components/checkstatus/checkStatusView.html',
            controller: 'checkStatusController'
      })
      .otherwise({
        redirectTo: '/'
      });
  }]);
});
