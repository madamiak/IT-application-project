angular.module('uiApp').controller('ExploreTripsController', function ($scope,$http,endpoints,authService) {
	
	$scope.trips=[];
	$scope.tripLoading=false;
	$scope.auth=authService;
	$scope.errorMessage="You are not authorized. Please login to see your trips"
	$scope.getTrips=function() {
		console.log("ASAS");
		$scope.tripLoading=true;
		$http.get(endpoints.be+"/user/"+authService.getUserId()+"/trips").success(function(response, status, headers, config){
			$scope.trips=response.routes;
			console.log($scope.trips);
			$scope.tripLoading=false;
		}).error(function(data, status, headers, config) {
			alert("There was an error when trying to load the trip");	
			$scope.tripLoading=false;
		});
	}

	$scope.refreshTrips=function() {
		if(authService.isAuthorized()) $scope.getTrips();
		else $scope.errorMessage="You are not authorized. Please login to see your trips";
	}

	$scope.previewTrip=function(trip) {
		$scope.$emit('showMap',trip);
		console.log(trip);
	}
});