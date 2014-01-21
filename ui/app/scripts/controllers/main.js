'use strict';


angular.module('uiApp').controller('MainController', function ($scope) {

		$scope.explore = function($scope) {};
		$scope.loginStatus = "You are not logged in.";
  		$scope.username="";
  		$scope.password="";
  		$scope.tryLogin = function() {
  			console.log($scope.username+" "+$scope.password);
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