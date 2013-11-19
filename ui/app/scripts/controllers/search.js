
angular.module('uiApp').controller('SearchController', function ($scope,$http) {

	$scope.middlePoints=[];
	$scope.middlePoints.push({id: -1,name: "",placeholder:"From..."});
	$scope.middlePoints.push({id: -1, name: "",placeholder:"To..."});

	$scope.place = undefined;	
	
	//modifiy it to be invoked on every keystroke user makes
	
	$scope.places=[];

	$scope.endpoint='http://localhost:9000/places/destinations/';

	

	$scope.getTrip = function() {
		$scope.searchText = 'Calculating the trip going throught places ';
		for(var each in $scope.middlePoints) {
			$scope.searchText+=$scope.middlePoints[each].name+", ";
		}
		
	};

	$scope.addDirection = function() {
		$scope.middlePoints.push({id:-1,name:"",placeholder:"To..."});
	};

	$scope.removeDirection = function(toRemove) {
		alert(toRemove);
	};

	$scope.updateAutosugestion = function(searchedPhrase) {
		if(searchedPhrase.length<2) return false;
		$http({method: 'GET', url: $scope.endpoint+searchedPhrase}).
	  success(function(data, status, headers, config) {
		$scope.places=[];
		for (var each in data.destinations) {
			$scope.places.push(data.destinations[each].value);
		}
	}).
	error(function(data, status, headers, config) {
		console.log("There was an error connecting to the endpoint. is the backend server running on port :9000?");
	});
	};
});