'use strict';


angular.module('uiApp').controller('MainController', function ($scope) {

		$scope.explore = function($scope) {}

		
	
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