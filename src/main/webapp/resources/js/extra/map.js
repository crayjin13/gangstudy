/**
 * 
 */
var mapEl = document.getElementById('map'),
	sButton = document.getElementById('sButton'),
	sLat = document.getElementById('sLat'), 
	sLng = document.getElementById('sLng'), 
	eButton = document.getElementById('eButton'), 
	eLat = document.getElementById('eLat'), 
	eLng = document.getElementById('eLng'),
	map, sMarker, eMarker, sSvg, eSvg
	
var radios = document.querySelectorAll('input[type=radio][name="marker"]');
var direction = document.getElementById('direction');

function initMap() {
	var init = { lat: 37.5642135 ,lng: 127.0016985 };
	map = new google.maps.Map(mapEl, {
			zoom: 12,
			center: init
		})
		
	map.addListener('click', function(event) {
		if(radios[0].checked) {
			if(sMarker != null) { sMarker.setMap(null) }
			sLat.value = event.latLng.lat()
			sLng.value = event.latLng.lng()
			sMarker = placeMarker(event.latLng, sSvg)
		} else {
			if(eMarker != null) { eMarker.setMap(null) }
			eLat.value = event.latLng.lat()
			eLng.value = event.latLng.lng()
			eMarker = placeMarker(event.latLng, eSvg)
		}
	})
	
	sSvg = {
		path:
		  "M10.453 14.016l6.563-6.609-1.406-1.406-5.156 5.203-2.063-2.109-1.406 1.406zM12 2.016q2.906 0 4.945 2.039t2.039 4.945q0 1.453-0.727 3.328t-1.758 3.516-2.039 3.070-1.711 2.273l-0.75 0.797q-0.281-0.328-0.75-0.867t-1.688-2.156-2.133-3.141-1.664-3.445-0.75-3.375q0-2.906 2.039-4.945t4.945-2.039z",
		fillColor: "blue",
		fillOpacity: 0.6,
		strokeWeight: 0,
		rotation: 0,
		scale: 2,
		anchor: new google.maps.Point(15, 30),
	}
	eSvg = {
		path:
		  "M10.453 14.016l6.563-6.609-1.406-1.406-5.156 5.203-2.063-2.109-1.406 1.406zM12 2.016q2.906 0 4.945 2.039t2.039 4.945q0 1.453-0.727 3.328t-1.758 3.516-2.039 3.070-1.711 2.273l-0.75 0.797q-0.281-0.328-0.75-0.867t-1.688-2.156-2.133-3.141-1.664-3.445-0.75-3.375q0-2.906 2.039-4.945t4.945-2.039z",
		fillColor: "red",
		fillOpacity: 0.6,
		strokeWeight: 0,
		rotation: 0,
		scale: 2,
		anchor: new google.maps.Point(15, 30),
	}
}

// Creates a marker on the map.
function placeMarker(latlng, svg) {
	var marker = new google.maps.Marker({
    	position: latlng,
    	map: map,
    	icon: svg
    })
	window.map.panTo(latlng)
	display()
	return marker
}

function onClick(target, lat, lng, svg) {
	var lat = parseFloat(lat.value),
		lng = parseFloat(lng.value)
	var latlng = new google.maps.LatLng(lat, lng);
	
	if(target != null) { target.setMap(null) }
	return placeMarker(latlng, svg)
}

function getDistance(lat1,lng1,lat2,lng2) {
	function deg2rad(deg) {
		return deg * (Math.PI/180)
	}
	var R = 6371; // Radius of the earth in km
	
	var dLat = deg2rad(lat2-lat1); // deg2rad below
	var dLon = deg2rad(lng2-lng1);
	var a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.sin(dLon/2) * Math.sin(dLon/2);
	var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	var d = R * c; // Distance in km
	return d;
}

function getAngle(x1, y1, x2, y2){
	var dx = x2 - x1;
	var dy = y2 - y1;
	var rad = Math.atan2(dx, dy);
	var a = (rad*180)/Math.PI ;
	return a;
}

function display() {
	if(sLat.value != "" && sLng.value != "" && eLat.value != "" && eLng.value != "") {
		var distance = getDistance(sLat.value, sLng.value, eLat.value, eLng.value),
			angle = getAngle(sLat.value, sLng.value, eLat.value, eLng.value)
		direction.textContent = "방향은 " + angle.toFixed(1) + "도 거리는 " + distance.toFixed(2) + "km"
	}
}

sButton.addEventListener("click", function() { sMarker = onClick(sMarker, sLat, sLng, sSvg) })
eButton.addEventListener("click", function() { eMarker = onClick(eMarker, eLat, eLng, eSvg) })



