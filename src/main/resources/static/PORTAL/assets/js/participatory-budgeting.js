var baseImage='PORTAL/assets/images/map/';
function initBudgetMap(infoPois, callback) {

    clearMapMarkers();
    infoPois = JSON.parse(infoPois);
    ps4GMap.map.addListener('click', function(event) {
        clearMapPopup();
        callback.$server.hideMapPopup();
        // Reverse geo code using latLng
        var geocoder = new google.maps.Geocoder;
        geocoder.geocode({'location': event.latLng }, function(results, status) {

            if (status === 'OK') {
                if (results[0]) {
//                        $('#search_new_places').val( results[0].formatted_address );
                    callback.$server.clickMap({
                        address: results[0].formatted_address,
                        lat: event.latLng.lat(),
                        lng: event.latLng.lng()
                    });

                } else {
//                        window.alert('No results found');
                }

            } else {
//                    window.alert('Geocoder failed due to: ' + status);
            }

        });

    });

    for(var i= 0; i < infoPois.length; i++) {

        var marker = new google.maps.Marker({position: {
            lat: infoPois[i].latitudine,
            lng: infoPois[i].longitudine
        },
            icon: baseImage+ infoPois[i].codCategoriePoi.toLowerCase()+"_blue.png",
            size: new google.maps.Size(220,220),
            scaledSize: new google.maps.Size(32,32),
            map: ps4GMap.map});
        marker.addListener('click', function(event) {
            for(var i= 0; i < ps4GMap.markers.length; ++i) {
                ps4GMap.markers[i].marker.setIcon(baseImage+infoPois[i].codCategoriePoi.toLowerCase()+"_blue.png" );
                if(ps4GMap.markers[i].marker.getPosition().lat() == event.latLng.lat()
                    && ps4GMap.markers[i].marker.getPosition().lng() == event.latLng.lng()) {
                    console.log(ps4GMap.markers[i].marker.getPosition().lat() + ", " + ps4GMap.markers[i].marker.getPosition().lng());
                    console.log(event.latLng.lat() + ", " + event.latLng.lng());
                    callback.$server.clickMarker(ps4GMap.markers[i].infoPoi);
                    ps4GMap.markers[i].marker.setIcon(baseImage+infoPois[i].codCategoriePoi.toLowerCase()+"_green.png");
//                    ps4GMap.markers[i].marker.icon.url = "http://maps.google.com/mapfiles/ms/icons/green-dot.png";
                }
            }
        });
        ps4GMap.markers.push( { marker: marker, infoPoi: infoPois[i]});
    }
}
function triggerClickMarker(idPoi,callback){

//V3 version is:
    for(var i= 0; i < ps4GMap.markers.length; ++i) {
        ps4GMap.markers[i].marker.setIcon(baseImage+ps4GMap.markers[i].infoPoi.codCategoriePoi.toLowerCase()+"_blue.png" );

        if(ps4GMap.markers[i].infoPoi.id== idPoi){
            callback.$server.clickMarker(ps4GMap.markers[i].infoPoi);
            ps4GMap.markers[i].marker.setIcon(baseImage+ps4GMap.markers[i].infoPoi.codCategoriePoi.toLowerCase()+"_green.png");
            //google.maps.event.trigger(ps4GMap.markers[i].marker, 'click');

        }
    }
}
function clearMapMarkers() {
    for(var i=0; i < ps4GMap.markers.length; ++i) {
        ps4GMap.markers[i].marker.setMap(null);
    }
    ps4GMap.markers = [];
}

function addMapMarker(lat, lng,imageLinkYellow) {

    clearMapMarker();
    ps4GMap.newPoi = new google.maps.Marker({position: {
        lat: lat,
        lng: lng
    },
        icon:baseImage+imageLinkYellow,
        size: new google.maps.Size(220,220),
        scaledSize: new google.maps.Size(32,32),
        map: ps4GMap.map});
}

function clearMapMarker() {
    if(ps4GMap.newPoi != null) {
        ps4GMap.newPoi.setMap(null);
        ps4GMap.newPoi = null;
    }
}

function dismissMapPopup() {
    if(ps4GMap.popup != null ) {
        ps4GMap.popup.setMap(null);
        ps4GMap.popup = null;
    }
}

function clearMapPopup() {
    if(ps4GMap.popup != null ) {
        ps4GMap.popup.setMap(null);
        ps4GMap.popup = null;
    }
   /* for(var i= 0; i < ps4GMap.markers.length; ++i) {
        ps4GMap.markers[i].marker.setIcon({ url:  "http://maps.google.com/mapfiles/ms/icons/blue-dot.png" });
    }*/
}


function showPopupOnMap(infoPoi, popupElement) {
    dismissMapPopup();
    ps4GMap.popup = new Popup(
        new google.maps.LatLng(infoPoi.latitudine, infoPoi.longitudine),
        popupElement);
    ps4GMap.popup.setMap(ps4GMap.map);
}
