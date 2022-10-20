$(document).ready(function(){

    // script Selectmenu
    $('.ui.dropdown').dropdown({
        allowAdditions: false
    });


    // Upload Files 
    $(document).find(".file-control").each(function () {
        let currentFile = $(this);
        let uploadText = $(this).attr("upload-text");
        currentFile.after("<div class='btn_upload_doc'><span>" + uploadText + "</span><i class='icn_upload'></i></div>");
    });
    $(document).on("click", ".btn_upload_doc", function () {
        $(this).prev().trigger("click");
    });
    $(document).on("change", ".file-control", function () {
        let fileName = $(this).val().split("\\");
        let uploadFileName = fileName[fileName.length - 1];
        if ( uploadFileName.length > 40 ) {
            uploadFileName = uploadFileName.substr(0, 40) + "...";
        }
        $(this).next().find("span").text(uploadFileName);
    });

    // Data Table 
    $('.dataTable').DataTable( {
        language: {
            search: "Cauta:",
            info:           "Se afișează _START_ - _END_ din _TOTAL_ intrări",
            infoEmpty:      "Se afișează 0 - 0 din 0 intrări",
            lengthMenu:     "Se afișează _MENU_ intrări",
            zeroRecords:    "Nu au fost găsite înregistrări care să se potrivească",
            paginate: {
                first:      "Prima",
                previous:   "Inapoi",
                next:       "Inainte",
                last:       "Ultima"
            }
        }
    });

    $('.dataTable_nosearch').DataTable( {
        "searching": false,
        language: {
            info:           "Se afișează _START_ - _END_ din _TOTAL_ intrări",
            infoEmpty:      "Se afișează 0 - 0 din 0 intrări",
            lengthMenu:     "Se afișează _MENU_ intrări",
            zeroRecords:    "Nu au fost găsite înregistrări care să se potrivească",
            paginate: {
                first:      "Prima",
                previous:   "Inapoi",
                next:       "Inainte",
                last:       "Ultima"
            }
        }
    });

    $('.dataTable_nopag').DataTable( {
        "searching": false,
        "paging": false,
        "info": false
    });

    $("select").css("background-image","none");
    // Tabs 
    $( ".tabs" ).tabs({
        activate: function( event, ui ) {
            try {
                $('#resize_iframe', window.parent.document).trigger('click');
                window.parent.parent.scrollTo(0, 0);
            } catch (e) {
            }
        }
    });

    // Datepicker
    $( ".datepicker" ).datepicker({
        dateFormat: 'dd.mm.yy',
        //constrainInput: false,
        showOtherMonths: true,
        firstDay: 1,
        dayNamesMin: ['SUN', 'MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT']
    });

    // Yearpicker
    $(".yearpicker").yearpicker({
        // year: 2020,
        startYear: 1900,
        endYear: (new Date).getFullYear()
    });

    tippy('.element', {
        content: "I'm a Tippy tooltip!",
        placement: 'right'
    });

});