

angular.module('uiApp').controller('TripPreviewController', function ($scope) {
	
	$scope.mapIsVisible = false;
	$scope.detailedPoints=[];
	$scope.map=null;
	$scope.markers=[];

	var mapOptions = {
          center: new google.maps.LatLng(50.,20.),
          zoom: 4
       };


	$scope.mapShownEvent = function(event,detailedPoints) {
		var scope = angular.element($("#trip-preview")).scope();
		scope.mapIsVisible=true;

		scope.detailedPoints=detailedPoints;

        scope.map = new google.maps.Map($("#test-map")[0],
            mapOptions);
       
       // draw points

        for(var i=0;i<scope.detailedPoints.length;i++) {
        	new google.maps.Marker({
    			position:new google.maps.LatLng(parseFloat(scope.detailedPoints[i].latt), parseFloat(scope.detailedPoints[i].longn)),
   		 		map: scope.map,
    			title:scope.detailedPoints[i].value
			});
	
        }
        // draw lines between points

           for(var i=0;i<scope.detailedPoints.length-1;i++) {
        		var routePoints=[new google.maps.LatLng(parseFloat(scope.detailedPoints[i].latt),  parseFloat(scope.detailedPoints[i].longn)),new google.maps.LatLng(parseFloat(scope.detailedPoints[i+1].latt),  parseFloat(scope.detailedPoints[i+1].longn))];
				
				var routeOnMap=new google.maps.Polyline({
					path: routePoints,
					geodesic:true,
					strkoeColor: '#FF1100',
					strokeOpacity: 0.5,
					strokeWeight: 3

				});
			routeOnMap.setMap(scope.map);
			
        }

	};

	$scope.$on('showMap', $scope.mapShownEvent);
});