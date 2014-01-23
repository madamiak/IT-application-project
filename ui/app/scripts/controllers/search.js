
angular.module('uiApp').controller('SearchController', function ($scope,$http,endpoints,authService) {
	
	// create scope middle points

	$scope.middlePoints=[];
	$scope.middlePoints.push({place: {id:-1,value: ""}, placeholder:"From..."});
	$scope.middlePoints.push({place: {id: -1,value: ""}, placeholder:"To..."});
	$scope.additionalPrefferences={numberOfPeople:3, startDate:"2013-12-111:10", endDate:"2013-12-1212:12", budget:250.0,kmPerDay:420,suggest:"true"};

	$scope.places=[];
	$scope.detailedListToShow=[];

	$scope.isRouteCalculated=false;

	$scope.getTrip = function() {
		$scope.isRouteCalculated=true;

		$scope.detailedListToShow=[];
		for(var i=0;i<$scope.middlePoints.length;i++) 
		{
			var item = $scope.middlePoints[i].place;
			$scope.detailedListToShow.push({id:item.id});
		}
		
		
		var reqUrl=endpoints.be+'/schedule?ids='+JSON.stringify({ids:$scope.detailedListToShow})+'&prefs='+JSON.stringify($scope.additionalPrefferences);
		console.log(reqUrl);
		$http.get(reqUrl).success(function(data, status, headers, config) {
			$scope.$emit('showMap',data);
			$scope.searchText = '';
			$scope.isRouteCalculated=false;
		}).
		error(function(data, status, headers, config) {
			console.log("There was an error connecting to the endpoint. is the backend server running on port :9000?");
			$scope.searchText = 'The path could not be calculated.';
			$scope.isRouteCalculated=false;
		});

		// get route scheduled from rest
		console.log($scope.detailedListToShow);
		
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

	
	$scope.updateAutosugestion = function(searchedPhrase) {
		if(searchedPhrase != undefined && searchedPhrase.length<0);
		else
		{
			$http.get(endpoints.be+'/places/destinations/'+searchedPhrase).success(function(data, status, headers, config) {
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


	$scope.showLoading = function() {
		return $scope.isRouteCalculated;
	};
});