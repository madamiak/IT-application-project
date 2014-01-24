'use strict';


angular.module('uiApp').controller('MainController', function ($scope,$http) {

});

angular.module('uiApp').directive('loginPanel', function() {
	return {
		templateUrl: 'views/login-panel.html',
		controller: 'LoginPanelController'
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

angular.module('uiApp').directive('exploreTrips', function() {
	return {
		templateUrl: 'views/explore-trips.html',
		controller: 'ExploreTripsController'
	};
});

angular.module('uiApp').factory('authService',['$http','endpoints',function($http,endpoints) {
	var _isAuthorized = false;
	var _userName=undefined;
	var _userId=undefined;
	return {
		isAuthorized: function() {
			return _isAuthorized;
		},
		authorize: function(user,pass,callback) {
			var reqUrl = endpoints.be+"/user/authenticate";
			$http.post(reqUrl,{username: user,password: pass}).
			success(function(response, status, headers, config) {
				_isAuthorized=response.code=="OK";
				if(_isAuthorized) {
				
					_userName=response.data.login;
					_userId=response.data.userId;
				}
				else { _userName=undefined; _userId=undefined;}
				callback(response);
			}).
			error(function(data, status, headers, config) {
				console.log("There was an error authorising user "+user);

			});
		},
		logout: function() {
			_isAuthorized=false;
		},
		getUserName: function() {
			return _userName;
		},
		getUserId: function() {
			return _userId;
		}

	}
}]);

angular.module('uiApp').factory('endpoints',function() {
	return {
		be: 'http://localhost:9000'
	}
});