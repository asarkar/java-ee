(function() {
  var app = angular.module('airport-search', ['ui.bootstrap', 'flight-service']);

  app.directive('airportSearch', ['flightService', function(flightService) {
    var airportSearchCtrl = ['$http', '$scope', '$log',
      function($http, $scope, $log) {
        $scope.flight = flightService;

        $scope.findAirports = function(val) {
          return $http.get('airports', {
            params: {
              searchTerm: val
            }
          }).then(function(searchResults) {
            return searchResults.data.map(function(airport) {
              return airport;
            });
          }).catch(function(searchResults) {
            $log.error(searchResults);
          });
        };
      }];

    return {
      restrict: 'E',
      templateUrl: 'pages/airport-search.html',
      scope: {
        flight.srcAirport: '=',
        flight.destAirport: '='
      },
      controller: airportSearchCtrl
    };
  }]);
})();