'use strict';


angular.module('uiApp').controller('MainController', function ($scope) {
		$scope.searchMessage='Type your destinations!';
		$scope.explore = function($scope) {
			alert("loading explore page...");
		}
	});

angular.module('uiApp').directive('searchForm', function() {
	return {
		templateUrl: 'views/search-form.html',
		controller: 'SearchController'
	};
});

