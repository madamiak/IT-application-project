
angular.module('uiApp').controller('SearchController', function ($scope,$http) {

	$scope.place = undefined;	
	
	//modifiy it to be invoked on every keystroke user makes
	
	$scope.places=[];

	var endpoint='http://localhost:9000/places/destinations/War';

	$http({method: 'GET', url: endpoint}).
	  success(function(data, status, headers, config) {
		$scope.places=[];
		for (var each in data.destinations) {
			$scope.places.push(data.destinations[each].value);
		}
	}).
	error(function(data, status, headers, config) {
		alert("error connecting to the endpoint. is the backend server running on port :9000?");
	});

	$scope.getTrip = function() {
		$scope.searchText = 'Calculating the trip from '+$scope.from+" to "+$scope.to;
	};

});