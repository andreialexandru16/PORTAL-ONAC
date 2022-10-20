var ps4GMapAddr = {
    map: null,
    newPoi: null,
    markers: [],
    popup: null,
    smartForm: null,
    addrComponent: null
}, Popup;

function resizeMapToFitDialog(){
    var width = $("#map_addr").closest('.content_modal').width();
    var height = $("#map_addr").closest('.content_modal').height();

    height = '75vh';

    $("#map_addr").css('width', width);
    $("#map_addr").css('height', height);
}

function saveAddress(textboxAttrName, modalAttrName, address){
    ps4GMapAddr.smartForm.$server.saveAdresa(textboxAttrName, address);
    // $("#"+textboxAttrName).val(address);
    $("#"+modalAttrName).modal("hide");
}

function initMapAddr(textboxAttrName, modalAttrName, smartForm, addrComponent) {
    ps4GMapAddr.smartForm = smartForm;
    ps4GMapAddr.addrComponent = addrComponent;
    ps4GMapAddr.map = new google.maps.Map(document.getElementById('gmap_addr'), {
        //center: {lat: 44.379768, lng: 26.118118}, Centru PORTAL
        center: {lat: 47.7452009, lng: 26.6610744}, //Centru CJ Botosani
        zoom: 18
    });

    ps4GMapAddr.map.addListener('click', function(event) {
        var geocoder = new google.maps.Geocoder;
        geocoder.geocode({'location': event.latLng }, function(results, status) {
            if (status === 'OK') {
                if (results[0]) {
                    saveAddress(textboxAttrName, modalAttrName, results[0].formatted_address + '(' + event.latLng.lat() + ',' + event.latLng.lng() + ')');
                } else {
                   alert('No results found');
                }
            } else {
                alert('Geocoder failed due to: ' + status);
            }
        });
    });

    resizeMapToFitDialog();
}

function initBootstrapOnShowFunction(textboxAttrName, modalAttrName, smartForm, addrComponent){
    $("#" + modalAttrName).on('shown.bs.modal', function(){
        initMapAddr(textboxAttrName, modalAttrName, smartForm, addrComponent);
    });
}