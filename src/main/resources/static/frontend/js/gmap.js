var ps4GMap = {

    map: null,
    newPoi: null,
    markers: [],
    popup: null
}, Popup;

function loadAfterMapElement(mapElementView) {
    mapElementView.$server.loadGmapApi();
}

function initMap() {

 ps4GMap.map = new google.maps.Map(document.getElementById('gmap'), {
                         //center: {lat: 44.379768, lng: 26.118118}, Centru PORTAL
                         center: {lat: 46.9245, lng: 26.3703}, //Centru Piatra Neamt
                         zoom: 15,
     mapTypeControlOptions: {
         mapTypeIds: ['roadmap', 'satellite',"hybrid", "terrain",'styled_map','styled_map_retro']
     }
                       });


   /* perimeter = new google.maps.Polygon({

        path: google.maps.geometry.encoding.decodePath("}r~mG_vt~CwRrmAmIng@g[jmBeErMkDlFmHjJ}`@|b@eMrN_DjAeTdGmJxCgBhD_AtFi@vMaCtKwA~@}HrEgApCW`B]fGYxHMv@u@AWl@qAlAnApB`@f@|@Bd@YpBRl@v@VdA`@f@\\`@t@h@xL|FfCtAxHfD~CjGM`GCfAbC@t@XhDe@vEMrAAtD?~@iCjAoEb@wArEpA`AgAj@o@v@nAl@@`FfDzE_GfBdAn@m@i@wEnDqCzCnIbLyIdCwDpC_Ft@s@xAhB\\gBvG?lCsAvAaAdCgAjExLlCsChD|F`F~GbBeBoEgJjBcN~HzDlLhJbObEbRpFzBpAlBXnEfBlO[`CYnEtBzCjEfHbIgEnDxJGaDkWWqAtF~@zBgDbQxCv^tG_AnN|WwHJsA|QgLvd@sVbsAgx@uf@sm@db@_~@o@{Ct_AunBoCg[}@wGgAmNa@mFk@aGk@gGeT{cAm~A`cAwoA~}@kIsFmBlGuZoSya@{XpAwEc^uVo@bCcI`@mJsKes@ad@U"),

        strokeColor: "#1908cc",

        strokeOpacity: 1,

        strokeWeight: 2,

        fillColor: "#08bada",

        fillOpacity: 0.075,

        editable: false,
        clickable:false

    });
    perimeter.setMap(ps4GMap.map);*/


    const styledMapType = new google.maps.StyledMapType(

        [
            {
                "featureType": "all",
                "elementType": "labels.icon",
                "stylers": [
                    {
                        "color": "#eeeeec"
                    },{
                        "visibility": "simplified"
                    },
                    {
                        "weight": 1
                    }
                ]
            }

        ],
        { name: "Indicatori proiecte" }
);
    const hartaRetro=  new google.maps.StyledMapType(

        [
            {
                "elementType": "geometry",
                "stylers": [
                    {
                        "color": "#ebe3cd"
                    }
                ]
            },
    {
        "elementType": "labels.text.fill",
        "stylers": [
        {
            "color": "#523735"
        }
    ]
    },
    {
        "elementType": "labels.text.stroke",
        "stylers": [
        {
            "color": "#f5f1e6"
        }
    ]
    },
    {
        "featureType": "administrative",
        "elementType": "geometry.stroke",
        "stylers": [
        {
            "color": "#c9b2a6"
        }
    ]
    },
    {
        "featureType": "administrative.land_parcel",
        "elementType": "geometry.stroke",
        "stylers": [
        {
            "color": "#dcd2be"
        }
    ]
    },
    {
        "featureType": "administrative.land_parcel",
        "elementType": "labels.text.fill",
        "stylers": [
        {
            "color": "#ae9e90"
        }
    ]
    },
    {
        "featureType": "landscape.natural",
        "elementType": "geometry",
        "stylers": [
        {
            "color": "#dfd2ae"
        }
    ]
    },
    {
        "featureType": "poi",
        "elementType": "geometry",
        "stylers": [
        {
            "color": "#dfd2ae"
        }
    ]
    },
    {
        "featureType": "poi",
        "elementType": "labels.text.fill",
        "stylers": [
        {
            "color": "#93817c"
        }
    ]
    },
    {
        "featureType": "poi.business",
        "stylers": [
        {
            "visibility": "off"
        }
    ]
    },
    {
        "featureType": "poi.park",
        "elementType": "geometry.fill",
        "stylers": [
        {
            "color": "#a5b076"
        }
    ]
    },
    {
        "featureType": "poi.park",
        "elementType": "labels.text",
        "stylers": [
        {
            "visibility": "off"
        }
    ]
    },
    {
        "featureType": "poi.park",
        "elementType": "labels.text.fill",
        "stylers": [
        {
            "color": "#447530"
        }
    ]
    },
    {
        "featureType": "road",
        "elementType": "geometry",
        "stylers": [
        {
            "color": "#f5f1e6"
        }
    ]
    },
    {
        "featureType": "road.arterial",
        "elementType": "geometry",
        "stylers": [
        {
            "color": "#fdfcf8"
        }
    ]
    },
    {
        "featureType": "road.arterial",
        "elementType": "labels",
        "stylers": [
        {
            "visibility": "off"
        }
    ]
    },
    {
        "featureType": "road.highway",
        "elementType": "geometry",
        "stylers": [
        {
            "color": "#f8c967"
        }
    ]
    },
    {
        "featureType": "road.highway",
        "elementType": "geometry.stroke",
        "stylers": [
        {
            "color": "#e9bc62"
        }
    ]
    },
    {
        "featureType": "road.highway",
        "elementType": "labels",
        "stylers": [
        {
            "visibility": "off"
        }
    ]
    },
    {
        "featureType": "road.highway.controlled_access",
        "elementType": "geometry",
        "stylers": [
        {
            "color": "#e98d58"
        }
    ]
    },
    {
        "featureType": "road.highway.controlled_access",
        "elementType": "geometry.stroke",
        "stylers": [
        {
            "color": "#db8555"
        }
    ]
    },
    {
        "featureType": "road.local",
        "stylers": [
        {
            "visibility": "off"
        }
    ]
    },
    {
        "featureType": "road.local",
        "elementType": "labels.text.fill",
        "stylers": [
        {
            "color": "#806b63"
        }
    ]
    },
    {
        "featureType": "transit.line",
        "elementType": "geometry",
        "stylers": [
        {
            "color": "#dfd2ae"
        }
    ]
    },
    {
        "featureType": "transit.line",
        "elementType": "labels.text.fill",
        "stylers": [
        {
            "color": "#8f7d77"
        }
    ]
    },
    {
        "featureType": "transit.line",
        "elementType": "labels.text.stroke",
        "stylers": [
        {
            "color": "#ebe3cd"
        }
    ]
    },
    {
        "featureType": "transit.station",
        "elementType": "geometry",
        "stylers": [
        {
            "color": "#dfd2ae"
        }
    ]
    },
    {
        "featureType": "water",
        "elementType": "geometry.fill",
        "stylers": [
        {
            "color": "#b9d3c2"
        }
    ]
    },
    {
        "featureType": "water",
        "elementType": "labels.text.fill",
        "stylers": [
        {
            "color": "#92998d"
        }
    ]
    }
],
        { name: "Harta Retro" });

    ps4GMap.map.mapTypes.set("styled_map", styledMapType);
    ps4GMap.map.mapTypes.set("styled_map_retro", hartaRetro);
    ps4GMap.map.setMapTypeId("satellite");
    Popup = createPopupClass();
}

function decodeLevels(encodedLevelsString) {
    var decodedLevels = [];

    for (var i = 0; i < encodedLevelsString.length; ++i) {
        var level = encodedLevelsString.charCodeAt(i) - 63;
        decodedLevels.push(level);
    }
    return decodedLevels;
}


/**
 * Returns the Popup class.
 *
 * Unfortunately, the Popup class can only be defined after
 * google.maps.OverlayView is defined, when the Maps API is loaded.
 * This function should be called by initMap.
 */
function createPopupClass() {
  /**
   * A customized popup on the map.
   * @param {!google.maps.LatLng} position
   * @param {!Element} content The bubble div.
   * @constructor
   * @extends {google.maps.OverlayView}
   */
  function Popup(position, content) {
    this.position = position;

    content.classList.add('popup-bubble');

    // This zero-height div is positioned at the bottom of the bubble.
    var bubbleAnchor = document.createElement('div');
    bubbleAnchor.classList.add('popup-bubble-anchor');
    bubbleAnchor.appendChild(content);

    // This zero-height div is positioned at the bottom of the tip.
    this.containerDiv = document.createElement('div');
    this.containerDiv.classList.add('popup-container');
    this.containerDiv.appendChild(bubbleAnchor);

    // Optionally stop clicks, etc., from bubbling up to the map.
    google.maps.OverlayView.preventMapHitsAndGesturesFrom(this.containerDiv);
  }
  // ES5 magic to extend google.maps.OverlayView.
  Popup.prototype = Object.create(google.maps.OverlayView.prototype);

  /** Called when the popup is added to the map. */
  Popup.prototype.onAdd = function() {
    this.getPanes().floatPane.appendChild(this.containerDiv);
  };

  /** Called when the popup is removed from the map. */
  Popup.prototype.onRemove = function() {
    if (this.containerDiv.parentElement) {
      this.containerDiv.parentElement.removeChild(this.containerDiv);
    }
  };

  /** Called each frame when the popup needs to draw itself. */
  Popup.prototype.draw = function() {
    var divPosition = this.getProjection().fromLatLngToDivPixel(this.position);

    // Hide the popup when it is far out of view.
    var display =
        Math.abs(divPosition.x) < 4000 && Math.abs(divPosition.y) < 4000 ?
        'block' :
        'none';

    if (display === 'block') {
      this.containerDiv.style.left = divPosition.x + 'px';
      this.containerDiv.style.top = divPosition.y + 'px';
        $('.slider_map').slick('setPosition');
    }
    if (this.containerDiv.style.display !== display) {
      this.containerDiv.style.display = display;
    }
  };

  return Popup;
}