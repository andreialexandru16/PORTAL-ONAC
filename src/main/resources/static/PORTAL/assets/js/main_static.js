jQuery(document).ready(function ($) {
    //add css by unit name
    // Create new link Element

    var path='/PORTAL/assets/css/';
    var link = document.createElement('link');

    // set the attributes for link element
    link.rel = 'stylesheet';

    link.type = 'text/css';

    link.href = path+'main.css';

    $.ajax({

        type: 'GET',
        url: '/dmsws/utilizator/getUnitName',
        success: function (data) {
            if(data!=null && data!=''){
                link.href = path+'main_'+data+'.css';

            }
            if( !document.getElementsByTagName('HEAD')[0].innerHTML.includes('PORTAL/assets/css/main_anre.css') ){
                // Get HTML head element to append
                // link element to it
                document.getElementsByTagName('HEAD')[0].appendChild(link);
                var oldLink= link;
                oldLink.href=".."+path+'main_'+data+'.css';
                document.getElementsByTagName('HEAD')[0].appendChild(oldLink);
            }
        }

    });



    "use strict";
    /* Initialize Homepage Banner */
    $(document).find("#home-banner .main_slide").slick({
        dots: true,
        draggable: false,
        infinite: false
    });

         /* Galerie foto / video */
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
    
    /* Accordion */
    if ( $(".cbnp_accordion").length ) {
        $(".cbnp_accordion").find(".btn").on("click", function () {
            /* Remove open class from all accordion elements */
            $(".cbnp_accordion").find(".card-header").each(function () {
                $(this).removeClass("open");
            });
            /* Add class open to the collapsed elements */
            if ( $(this).attr("aria-expanded") === "false" ) {
                console.log("Open says me!");
                $(this).parent().addClass("open");
            }
        });
    }
    /* Map Tabs */
    if ( $(".map_tab").length ) {
        $(document).on("click", ".map_tab", function () {
            let currentBtn = $(this);
            if ( $(this).attr("status") === "false" ) {
                /* Hide all tabs */
                $(".map_container .panel_tab_content").each(function () {
                    $(this).removeClass("show");
                });
                $(".map_container .map_content ").removeClass("show");
                /* Disable all buttons */
                $(".map_container button.map_tab").each(function () {
                    $(this).removeClass("active");
                    $(this).attr("status", "false");
                });
                /* Enable current tab and button */
                currentBtn.addClass("active");
                currentBtn.attr("status", "active");
                $("#" + $(this).attr("map-tab")).addClass("show");
                if ( $(this).attr("map-tab") === "consultare" ) {
                    $(".map_container .map_content ").addClass("show");
                }
            }
        });
    }
    if ( $(document).find(".close_btn").length ) {
        $(document).on("click", ".close_btn", function () {
            $(this).parent().remove();
        });
    }
    /* Custom Select */
    if ( $(".chosen-select").length ) {
        $(".chosen-select").chosen({
            disable_search_threshold: 10,
            no_results_text: "Nu a fost găsit: "
        });
    }
    /* Upload Files */
    $(document).find(".file-control").each(function () {
        let currentFile = $(this);
        let uploadText = $(this).attr("upload-text");
        currentFile.after("<div class='cbnp_upload_file form-control'><span>" + uploadText + "</span><i class='fas fa-upload'></i></div>");
    });
    $(document).on("click", ".cbnp_upload_file", function () {
        $(this).prev().trigger("click");
    });
    $(document).on("change", ".file-control", function () {
        let fileName = $(this).val().split("\\");
        let uploadFileName = fileName[fileName.length - 1];
        if ( uploadFileName.length > 20 ) {
            uploadFileName = uploadFileName.substr(0, 20) + "...";
        }
        $(this).next().find("span").text(uploadFileName);
    });
    /* Date Picker */
    $(".datepicker").datepicker({
        beforeShow: function (input, inst) {
            var calendar = inst.dpDiv;
            setTimeout(function() {
                calendar.position({
                    my: 'right top',
                    at: 'right bottom',
                    collision: 'none',
                    of: input
                });
            }, 1);
        }
    });

    // script input / textarea - show label
    function checkForInput(element) {
      // element is passed to the function ^

      const $label = $(element).parent('.div_label');

      if ($(element).val().length > 0) {
        $label.addClass('show_label');
      } else {
        $label.removeClass('show_label');
      }
    }

    // The lines below are executed on page load
    $('.input input').each(function() {
      checkForInput(this);
    });
    $('.textarea textarea').each(function() {
      checkForInput(this);
    });

    // The lines below (inside) are executed on change & keyup
    $('.input input').on('change keyup', function() {
      checkForInput(this);
    });
    $('.textarea textarea').on('change keyup', function() {
      checkForInput(this);
    });
    
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

    // Fancybox
    $(".fancybox").fancybox({
        openEffect: "none",
        closeEffect: "none"
    });

});