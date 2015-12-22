(function() {
  var app = angular.module('travelApp', ['airport-search', 'travel-dt', 'flight-service']);

  app.controller('FlightSearchCtrl', [
    '$scope', '$http', 'flightService',
    function($scope, $http, $filter, flightService) {
      $scope.flight = flightService;

      var paginationOptions = {
        pageNumber: 1,
        pageSize: 25,
        sort: null
      };

      $scope.gridOptions = {
        paginationPageSizes: [25, 50, 75],
        paginationPageSize: 25,
        useExternalPagination: true,
        useExternalSorting: false,
        columnDefs: [{
          name: 'Airline'
        }, {
          name: 'Flight'
        }, {
          name: 'Departure'
        }, {
          name: 'From'
        }, {
          name: 'To'
        }, {
          name: 'Aircraft'
        }],
        onRegisterApi: function(gridApi) {
          $scope.gridApi = gridApi;
          //        $scope.gridApi.core.on.sortChanged($scope, function(grid, sortColumns) {
          //          if (sortColumns.length == 0) {
          //            paginationOptions.sort = null;
          //          } else {
          //            paginationOptions.sort = sortColumns[0].sort.direction;
          //          }
          //          getPage();
          //        });
          gridApi.pagination.on.paginationChanged($scope, function(newPage, pageSize) {
            paginationOptions.pageNumber = newPage;
            paginationOptions.pageSize = pageSize;

            searchFlights();
          });
        }
      };

      $scope.searchFlights = function() {
        $http.get('flights', {
          params: {
            src: flight.srcAirport.faaCode,
            dest: flight.destAirport.faaCode,
            date: $filter('date')(flight.departureDt, 'yyyy-MM-dd'),
            pageSize: paginationOptions.pageSize,
            pageNum: paginationOptions.pageNumber
          }
        }).success(function(results) {
          $scope.gridOptions.totalItems = results.totalElements;
          $scope.gridOptions.data = results.data;
        });
      };
    }
  ]);
})();