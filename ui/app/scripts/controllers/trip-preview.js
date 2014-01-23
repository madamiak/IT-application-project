

angular.module('uiApp').controller('TripPreviewController', function ($scope,$http,endpoints,authService) {
	
	$scope.mapIsVisible = false;
	$scope.routeData=[];
	$scope.map=null;
	$scope.markers=[];
	$scope.directions=[];
	$scope.poiNumber=0;

	var mapOptions = {
          center: new google.maps.LatLng(50.,20.),
          zoom: 4
       };


    clearFields = function() {
    	$scope.directions=[];
    },

	$scope.mapShownEvent = function(event,routeData) {
		clearFields();
		var scope = angular.element($("#trip-preview")).scope();
		scope.mapIsVisible=true;

		scope.routeData=routeData;
		console.log(routeData);
        scope.map = new google.maps.Map($("#test-map")[0],
            mapOptions);
       
       // draw points

        for(var i=0;i<scope.routeData.points.length;i++) {
        	new google.maps.Marker({
    			position:new google.maps.LatLng(parseFloat(scope.routeData.points[i].lat), parseFloat(scope.routeData.points[i].lng)),
   		 		map: scope.map,
    			title:scope.routeData.points[i].name
			});
	
        }

        // draw routes

         for(var i=0;i<scope.routeData.routes.length;i++) {
         	var points=google.maps.geometry.encoding.decodePath(scope.routeData.routes[i].polyline);
         	var lonlats=[];
         	for(var j=0;j<points.length;j++) {
         		lonlats.push(new google.maps.LatLng(points[j].lat(),points[j].lng()));
         	}
         	
        	var polyline = new google.maps.Polyline({
        		path: lonlats,
        		strokeColor: "#0000FF",
        		strokeOpacity: 0.3,
        		strokeWeight: 3
   			 });
    		polyline.setMap(scope.map);

        }      
       
       // prepare directions information
       var iterator=1;
       for(var i=0;i<scope.routeData.points.length-1;i++) {

       		scope.directions.push({
       			"type": "Destination",
       			"step": (iterator++),
       			"from": scope.routeData.points[i].name,
       			"to": scope.routeData.points[i+1].name,
       			"distance": scope.routeData.routes[i].distance.text,
       			"duration": scope.routeData.routes[i].duration.text,

       		});
       		if(scope.routeData.points[i+1].type=="Hotel") {
       			scope.directions.push({
       			"id": scope.routeData.points[i+1].id,
       			"type": scope.routeData.points[i+1].type,
       		    "step": (iterator++),
       		    "name": scope.routeData.points[i+1].name,
       		    "detailsVisible": true
       			});
       		}
       	}

       	// update POI information and display
       	scope.poiNumber=scope.routeData.pois.length;
       	console.log(scope.directions);
	};

	$scope.$on('showMap', $scope.mapShownEvent);

	$scope.saveRoute = function() {
		console.log(authService.isAuthorized()?"Authorized to save":"Not authorized to save");
	}

	$scope.toggleDetails = function(step) {
		step.detailsVisible=!(step.detailsVisible);
		$http.get(endpoints.be+"/places/hotel/"+step.id).success(function(data, status, headers, config) {
			step.details=data;
			console.log(data);
		}).
		error(function(data, status, headers, config) {
			console.log("There was an error connecting to the endpoint. is the backend server running on port :9000?");
		});
	}
});