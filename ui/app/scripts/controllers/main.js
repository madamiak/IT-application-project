'use strict';


angular.module('uiApp').controller('MainController', function ($scope,$http) {

		$scope.explore = function($scope) {};
		$scope.loginStatus = "You are not logged in.";
  		$scope.username="";
  		$scope.password="";
  		$scope.endpoint='http://localhost:9000';

  		$scope.tryLogin = function() {
  			console.log($scope.username+" "+$scope.password);
  			var reqUrl = $scope.endpoint+"/user/authenticate";
  			$http({method: 'POST', url: reqUrl}).
			  success(function(data, status, headers, config) {
			  console.log(data);
			  }).
			  error(function(data, status, headers, config) {
			   console.log(data);
			   });
  		};
		
	
});

angular.module('uiApp').directive('searchForm', function() {
	return {
		templateUrl: 'views/search-form.html',
		controller: 'SearchController'
	};
});

angular.module('uiApp').directive('tripPreview', function() {
	return {
		templateUrl: 'views/trip-preview.html',
		controller: 'TripPreviewController'
	};
});