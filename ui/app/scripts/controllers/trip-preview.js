

angular.module('uiApp').controller('TripPreviewController', function ($scope) {
	
	$scope.mapIsVisible = false;
	$scope.points=[];
	$scope.map=null;

	angular.extend($scope, {
		center: {
			latitude: 0, // initial map center latitude
			longitude: 0, // initial map center longitude
		},
		markers: [], // an array of markers,
		zoom: 2 // the zoom level
	});	

	$scope.mapShownEvent = function(event,points) {
		var scope = angular.element($("#trip-preview")).scope();
		scope.mapIsVisible=true;
		scope.points=points;
		console.log(points);
		 var mapOptions = {
          center: new google.maps.LatLng(-34.397, 150.644),
          zoom: 8
        };
        scope.map = new google.maps.Map($("#test-map")[0],
            mapOptions);
       
	
      var marker1 = new google.maps.Marker({
    	position:new google.maps.LatLng(-34.397, 150.644),
   		 map: scope.map,
    	title:scope.points[0].place.value
		});
          var marker2 = new google.maps.Marker({
    	position:new google.maps.LatLng(-32.397, 149.644),
   		 map: scope.map,
    	title:scope.points[1].place.value
		});

          var routePoints=[new google.maps.LatLng(-34.397, 150.644),new google.maps.LatLng(-32.397, 149.644)];
			var routeOnMap=new google.maps.Polyline({
				path: routePoints,
				geodesic:true,
				strkoeColor: '#FF1100',
				strokeOpacity: 1.0,
				strokeWeight: 3

			});
			routeOnMap.setMap(scope.map);
	};

	$scope.$on('showMap', $scope.mapShownEvent);
});