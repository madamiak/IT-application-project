
angular.module('uiApp').controller('SearchController', function ($scope,$http) {

	// create scope middle points

	$scope.middlePoints=[];
	$scope.middlePoints.push({place: {id:-1,value: ""}, placeholder:"From..."});
	$scope.middlePoints.push({place: {id: -1,value: ""}, placeholder:"To..."});
	

	$scope.places=[];


	$scope.getTrip = function() {
		$scope.searchText = 'Calculating the trip going throught places ';

		for(var each in $scope.middlePoints) {
			var item = $scope.middlePoints[each].place;
			$scope.searchText+='-> '+item.value+' ('+ item.id+') ';
		}
		// perform a call to the service here

		//invoke action on outer controller
		$scope.$emit('showMap',$scope.middlePoints);
	};

	$scope.addDirection = function() {
		if($scope.directionCanBeAdded())
		{
			$scope.middlePoints.push({place: {id: -1,value: ""}, placeholder:"To..."});
		}	
	};

	$scope.getAddDirectionMessage = function() {
		return $scope.directionCanBeAdded() ? 'Add direction' : 'Fill in last direction to add a new one!';
	};

	
	$scope.removeDirection = function() {
		if($scope.directionCanBeRemoved()) {
			$scope.middlePoints.splice($scope.middlePoints.length-1, 1);
		}
	};

	$scope.getRemoveDirectionMessage = function() {
		return $scope.directionCanBeRemoved() ? 'Remove direction': undefined;
	};

	// FIXME move to the properties file
	$scope.endpoint='http://localhost:9000/places/destinations/';

	$scope.updateAutosugestion = function(searchedPhrase) {

		if(searchedPhrase != undefined && searchedPhrase.length<2);
		else
		{
		
			$http({method: 'GET', url: $scope.endpoint+searchedPhrase}).success(function(data, status, headers, config) {
				$scope.places=[];
				for (var each in data.destinations) {
					$scope.places.push(data.destinations[each]);
				}
			}).
			error(function(data, status, headers, config) {
				console.log("There was an error connecting to the endpoint. is the backend server running on port :9000?");
			});
		}
	};

	$scope.directionCanBeAdded = function() {
		var item=$scope.middlePoints[$scope.middlePoints.length-1].place;
		 return item!=undefined && item.value!=undefined && item.value.length>0;
	};

	$scope.directionCanBeRemoved = function() {
		return $scope.middlePoints.length>2;
	};
});