
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


function swalError(errorText, callback) {
        Swal.fire({
            icon: 'error',
            html: "<p class='text-danger'><strong>" + errorText +"</strong></p>",
            focusConfirm: false,
            confirmButtonText: 'Ok',
            onClose: () => {
               if(callback != undefined) {
                   callback.$server.swalErrorAck();
               }
            }
        }
        );
}

function swalErrorParam(errorText, callback, param) {
    Swal.fire({
            icon: 'success',
            html: "<p class='text-danger'><strong>" + errorText +"</strong></p>",
            focusConfirm: false,
            confirmButtonText: 'Ok',
            onClose: () => {
            if(callback != undefined) {
        callback.$server.swalErrorAck(param);
    }
}
}
);
}

function swalInfoParam(infoText, callback,param) {

    Swal.fire({
            icon: 'success',
            html: infoText,
            focusConfirm: false,
            confirmButtonText: 'Ok',
            position:'bottom',
            onClose: () => {
            if(callback != undefined) {
        callback.$server.swalInfoAck(param);
    }
}
}
);
}

function swalInfoParam2(infoText, callback,param) {

    Swal.fire({
            icon: 'success',
            html: infoText,
            focusConfirm: false,
            confirmButtonText: 'Ok',
            onClose: () => {
            if(callback != undefined) {
        callback.$server.swalInfoParam2Ack(param);
    }
}
}
);
}


function triggerClick(elem) {

    //$(elem).trigger('click');
    window.open(elem ,'_blank');
}
function swalInfoTarget(infoText, target) {
    Swal.fire({
            icon: 'success',
            html: infoText,
            focusConfirm: false,
            confirmButtonText: 'Ok',
            target:target

}
);
}
function swalInfo(infoText, callback) {
     Swal.fire({
            icon: 'success',
            html: infoText,
            focusConfirm: false,
            confirmButtonText: 'Ok',
            onClose: () => {
               if(callback != undefined) {
                callback.$server.swalInfoAck();
               }
            }
        }
        );
}

function swalInfoBottom(infoText, callback) {
    Swal.fire({
            icon: 'success',
            html: infoText,
            position:"bottom",
            focusConfirm: false,
            confirmButtonText: 'Ok',
            onClose: () => {
            if(callback != undefined) {
        callback.$server.swalInfoAck();
    }
}
}
);
}

function swalErrorTop(errorText, callback) {

    window.parent.parent.scrollTo(0, 0);
    Swal.fire({

            icon: 'error',
            html: "<p class='text-danger'><strong>" + errorText +"</strong></p>",
            focusConfirm: false,
            position:'top',
            confirmButtonText: 'Ok',
            className:'swal-text',
            onClose: () => {
            if(callback != undefined) {
        callback.$server.swalErrorAck();
    }
}
}
);
}
function swalPaymentInfoConfirmation(infoText, callback) {
     Swal.fire({
            icon: 'success',
            html: infoText,
            focusConfirm: false,
            showCancelButton: true,
            cancelButtonText: 'Renunta',
            confirmButtonText: 'Confirma'
//            onClose: () => {
//               if(callback != undefined) {
//                callback.$server.swalPaymentInfoConfirmation();
//               }
//            }
        }).then((result) => {
            if (result.value) {
                callback.$server.swalPaymentInfoConfirmation();
            } else if (
              /* Read more about handling dismissals below */
              result.dismiss === Swal.DismissReason.cancel
            ) {
            }
          });
}

function hideUploadFileList() {
        var uploadElements = $('.upload-vaadin-no-file-list');
        for(var i = 0; i < uploadElements.length; ++i) {
            uploadElements[i].shadowRoot.childNodes[4].setAttribute("style", "display: none");
        }
//    var uploadElementShadowRoot = uploadElement.shadowRoot;

}

function initAutocompleteItems(phrases,callback) {

    var autocompleteElements = document.getElementsByClassName("search-text-autocomplete");
    for(var i=0;i<autocompleteElements.length;i++) {
        var autocompleteShadowRoot = autocompleteElements[i].shadowRoot;
        var element =  Array.from(autocompleteShadowRoot.childNodes)[2].childNodes[3].childNodes[3].childNodes[1];
        element.classList.add("autocomplete");


        phrases = JSON.parse(phrases);
        $(element).autocomplete({
            source: phrases,
            select: function (event, ui) {
                callback.$server.selectedItem(ui.item.value);
            }
        });
    }

}


function addThemeSmall(element) {
    Array.from(element.shadowRoot.childNodes)[2].setAttribute("theme","small");
}

function displayLoadingSpinner() {
    $('#loading-spinner').show();
}

function hideLoadingSpinner() {
    $('#loading-spinner').hide();
}


function toggleDisplayState(className, displayState){
    var elements = document.getElementsByClassName(className);
    for (var i = 0; i < elements.length; i++){
        elements[i].style.display = displayState;
    }
}
function initCarouselSliderFoto(callback) {
    $.fancybox.defaults.hash = false
    $('.foto-items, .video-items').slick({
        dots: false,
        infinite: false,
        speed: 300,
        slidesToShow: 4,
        slidesToScroll: 4,
        responsive: [
            {
                breakpoint: 1192,
                settings: {
                    slidesToShow: 3,
                    slidesToScroll: 3
                }
            },
            {
                breakpoint: 767,
                settings: {
                    slidesToShow: 2,
                    slidesToScroll: 2
                }
            },
            {
                breakpoint: 500,
                settings: {
                    slidesToShow: 1,
                    slidesToScroll: 1
                }
            }
        ]
    });

}
function initCarouselSliderBugetare(callback) {

// Slider - map (bugetare administrativa)
    $('.slider_map').slick({
        infinite: false,
        slidesToShow: 3,
        slidesToScroll: 3
    });

    $('.box_modal_map').on('shown.bs.modal', function (e) {
        $('.slider_map').slick('setPosition');
        $('.wrap-modal-slider').addClass('open');
    });

}
// Fancybox
$(".fancybox").fancybox({
    openEffect: "none",
    closeEffect: "none"
});
function setPositionSlick(callback) {

    $('.slider_map').slick('setPosition');
}

