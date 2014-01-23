angular.module('uiApp').controller('LoginPanelController', function ($scope,$http,endpoints,authService) {



	//add to local storage if needed
	$scope.loggedIn = false;
	$scope.notLoggedMessage = "You are not logged in.";
	$scope.loggedMessage = "Hello, ";

	$scope.username;
	$scope.password;

	$scope.showMessage = function(message) {alert(message);}

	$scope.tryLogout = function() {
		authService.logout();
		$scope.loggedIn=authService.isAuthorized();
	}
	$scope.tryLogin = function() {
		authService.authorize($scope.username,$scope.password, function(data) {
			if(data.code=="UNAUTHORIZED") {
				$scope.showMessage("User "+$scope.username+" was not authorized.");
				
			}
			else if(data.code=="OK") {
				$scope.showMessage("Welcome back "+$scope.username+"!");
				
			}

			$scope.loggedIn=authService.isAuthorized();
		});
	};
});