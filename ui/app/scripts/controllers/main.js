'use strict';


angular.module('uiApp').controller('MainController', function ($scope) {
		$scope.searchMessage='Type your destinations!';
		
		$scope.explore = function($scope) {}

		$scope.$on('showMap', function(event,points) {
			console.log("event happened.");
			console.log(points);
		
		});
	
});

angular.module('uiApp').directive('searchForm', function() {
	return {
		templateUrl: 'views/search-form.html',
		controller: 'SearchController'
	};
});

