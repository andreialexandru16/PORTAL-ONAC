jQuery(document).ready(function ($) {
    "use strict";
    /* Initialize Homepage Banner */
    /* Custom Select */
    if ( $(".chosen-select").length ) {
        $(".chosen-select").chosen({
            disable_search_threshold: 10,
            no_results_text: "Nu a fost găsit: "
        });
    }
});

//var slick = null;
//
//function initCarouselSlider() {
////    if(slick != null ) {
//    $("#home-banner .main_slide .slick-initialized").slick('unslick');
////    }
//    $("#home-banner .main_slide")
////    .not('.slick-initialized')
//    .slick({
//        dots: true,
//        draggable: false,
//        infinite: false
//    }).on('beforeChange', function (event, slick, currentSlide, nextSlide) {
//            getServerCallback("onCarouselSlideBeforeChange").$server.onCarouselSlideBeforeChange(nextSlide);
//      });
//
//}


function initCarouselSlider(callback) {
//    if(slick != null ) {
//    }
    $("#home-banner .main_slide")
//    .not('.slick-initialized')
    .slick({
        dots: true,
        draggable: false,
        infinite: false
    }).on('beforeChange', function (event, slick, currentSlide, nextSlide) {
            callback.$server.onCarouselSlideBeforeChange(nextSlide);
      });



}


function initAutocompleteItems(phrases) {

    var autocompleteElements =   document.getElementsByClassName("search-text-autocomplete");
    for(var i=0;i<autocompleteElements.length;i++){
        var autocompleteShadowRoot = autocompleteElements[i].shadowRoot;
        var element =  Array.from(autocompleteShadowRoot.childNodes)[2].childNodes[3].childNodes[3].childNodes[1];
        element.classList.add("autocomplete");

        phrases = JSON.parse(phrases);
        $(element).autocomplete({
            source: phrases
        });
    }



}