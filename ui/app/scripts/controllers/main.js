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

angular.module('uiApp').factory('authService',['$http','endpoints',function($http,endpoints) {
	var _isAuthorized = false;

	return {
		isAuthorized: function() {
			return _isAuthorized;
		},
		authorize: function(user,pass,callback) {
			var reqUrl = endpoints.be+"/user/authenticate";
			$http.post(reqUrl,{username: user,password: pass}).
			success(function(response, status, headers, config) {
				_isAuthorized=response.code=="OK";
				callback(response);
			}).
			error(function(data, status, headers, config) {
				console.log("There was an error authorising user "+user);
				console.log(data+status+headers+config);
			});
		},
		logout: function() {
			_isAuthorized=false;
		}
	}
}]);

angular.module('uiApp').factory('endpoints',function() {
	return {
		be: 'http://localhost:9000'
	}
});