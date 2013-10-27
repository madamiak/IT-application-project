function initialize() {
	var mapOptions = {
		center: new google.maps.LatLng(52.187405, 19.398193),
		zoom: 6,
		mapTypeId: google.maps.MapTypeId.ROADMAP
	};
	var map = new google.maps.Map(document.getElementById("map-canvas"),
	mapOptions);
}
google.maps.event.addDomListener(window, 'load', initialize);
