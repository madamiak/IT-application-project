'use strict';


angular.module('uiApp').controller('MainController', function ($scope,$http) {

  		$scope.endpoint='http://localhost:9000';
		
		//add to local storage if needed
		$scope.loggedIn = false;
		$scope.notLoggedMessage = "You are not logged in.";
		$scope.loggedMessage = "Hello, ";

  		$scope.username;
  		$scope.password;
  		
  		$scope.showMessage = function(message) {alert(message);}

  		$scope.tryLogout = function() {
  			$scope.loggedIn=false;
  		}
  		$scope.tryLogin = function() {
  			console.log($scope.username+" "+$scope.password);
  			var reqUrl = $scope.endpoint+"/user/authenticate";
  			$http({method: 'POST', url: reqUrl,data: {username: $scope.username,password: $scope.password}}).
			  success(function(data, status, headers, config) {
			  	console.log(data);
			  	if(data.code=="UNAUTHORIZED") {
			  		$scope.showMessage("User "+$scope.username+" was not authorized.");
			  		$scope.loggedIn=false;
			  	}
			  	else if(data.code=="OK") {
			  		$scope.showMessage("Welcome back "+$scope.username+"!");
			  		$scope.loggedIn=true;
			  		
			  	}
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