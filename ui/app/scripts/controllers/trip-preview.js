

angular.module('uiApp').controller('TripPreviewController', function ($scope,$http,endpoints,authService) {
	
	$scope.mapIsVisible = false;
	$scope.routeData=[];
	$scope.map=null;
	$scope.markers=[];
	$scope.directions=[];
	$scope.poiNumber=0;
	$scope.pois=[];

	var mapOptions = {
		center: new google.maps.LatLng(50.,20.),
		zoom: 4
	};


	clearFields = function() {
		$scope.directions=[];
	},

	$scope.getIconFor = function(type) {
		if(type=="Hotel") return 'images/pin_4.png';
		else if(type=="Destination") return 'images/pin_3.png';
		else if(type=="POI") return 'images/pin_5.png';
		else return 'images/pin_3.png';
	}

	$scope.prepareTooltipFor = function(marker,point) {
		var popupContent;
		if(point.type=="Hotel") popupContent='<strong><i class="icon-home"></i> Hotel</strong><br>'+point.name;
		else if(point.type=="Destination") popupContent='<strong><i class="icon-road"></i> Destination</strong><br/>'+point.name;

		google.maps.event.addListener(marker, 'click', function() {
			infowindow.open($scope.map,marker);
		});
		var infowindow = new google.maps.InfoWindow({
			content: popupContent
		});
	}
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
       	var marker=new google.maps.Marker({
       		position:new google.maps.LatLng(parseFloat(scope.routeData.points[i].lat), parseFloat(scope.routeData.points[i].lng)),
       		map: scope.map,
       		icon: scope.getIconFor(scope.routeData.points[i].type),
       		title:scope.routeData.points[i].name
       	});

       	scope.prepareTooltipFor(marker,scope.routeData.points[i]);

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


       $scope.getPointIds = function() {
       	var ids=[];
       		$scope.routeData.points.forEach(function(point) {
       			ids.push({id: point.id});
       		});
       		return ids;
       }
       $scope.saveRoute = function() {
       	
       	if(!authService.isAuthorized()) {
       	
       		$scope.addAlert({ type: 'danger', msg: 'Cannot save the route! You must be signed in to save the route' });
       	}
       	else {
   
       			var routeObj={};
       			$http.put(endpoints.be+"/user/trip",{login: authService.getUserName(),route: { name: "Trip", start_at: "2014-03-12 20:23:21", budget: parseFloat($scope.budget), points: $scope.getPointIds()}}).
					success(function(response, status, headers, config) {
						$scope.addAlert({ type: 'success', msg: 'Your route has been added!' });			
				}).
			error(function(data, status, headers, config) {
				console.log(data+status+headers+config);
			});
       	}
       }


       $scope.getHotelDetails = function(step) {
       	$http.get(endpoints.be+"/places/hotel/"+step.id).success(function(data, status, headers, config) {
       		step.details=data;
       	}).
       	error(function(data, status, headers, config) {
       		console.log("There was an error connecting to the endpoint. is the backend server running on port :9000?");
       	});
       }

       $scope.toggleDetails = function(step) {
       	step.detailsVisible=!(step.detailsVisible);
       	$scope.getHotelDetails(step);

       }

       $scope.humanize = function(text) {
       	if(text) return text.split("|");
       }

       $scope.showPois=0;
       $scope.loadPoiMarkers = function() {
       	$scope.routeData.pois.forEach(function(poi) {

       		$http.get(endpoints.be+"/places/poi/"+poi.id).success(function(data, status, headers, config) {
       			$scope.pois.push($scope.getPoiMarker(data));
       			console.log($scope.pois);

       		}).
       		error(function(data, status, headers, config) {
       			console.log("There was an error connecting to the endpoint. is the backend server running on port :9000?");
       		});
       	});
       }

       $scope.togglePois = function() {
       	$scope.routeData.pois.forEach(function(poi) {

       		$http.get(endpoints.be+"/places/poi/"+poi.id).success(function(data, status, headers, config) {
       			$scope.showPoiMarker(data);
       			console.log(data);
       		}).
       		error(function(data, status, headers, config) {
       			console.log("There was an error connecting to the endpoint. is the backend server running on port :9000?");
       		});
       	});
       }

       $scope.showPoiMarker = function(poi) {

       	var marker = new google.maps.Marker({
       		map:$scope.map,
       		animation: google.maps.Animation.DROP,
       		position: new google.maps.LatLng(parseFloat(poi.latt), parseFloat(poi.longn)),
       		icon: 'images/pin_5.png'
       	});
       	google.maps.event.addListener(marker, 'click', function() {
       		infowindow.open($scope.map,marker);
       	});
       	var infowindow = new google.maps.InfoWindow({
       		content: '<i class="icon-star"></i><strong> POI</strong><br/>'+poi.value
       	});
       }
	// alerts
	$scope.alerts = [

	];

	$scope.addAlert = function(alert) {
		$scope.alerts.push(alert);
	};

	$scope.closeAlert = function(index) {
		$scope.alerts.splice(index, 1);
	};

	$scope.budget=0;
	$scope.getOverallCost = function(meters) {
		$scope.budget=$scope.niceFloat(meters/100*0.357)
		return $scope.budget;
	}

	$scope.getOverallCostPerPerson = function(meters,people) {
		return $scope.niceFloat($scope.getOverallCost(meters)/people);
	}

	$scope.niceFloat = function(value) {
		return parseFloat(Math.round(value * 100) / 100).toFixed(2);
	}
});