

var getOptionListItemHtml = (id, city) => {
    return '<option value="' + id + '">' + city + '</option>';
}

function checkCaptcha(){
    if($('#status').val()=='Corect!')
        return true;
    else
        return false;
}
function CheckPassword(inputtxt)
{
    var decimal=  /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\s).{8,20}$/;
    if(inputtxt.match(decimal))
    {
        return true;
    }
    else
    {
        return false;
    }
};

function isNumberKey(evt){
    var charCode = (evt.which) ? evt.which : evt.keyCode
    if (charCode > 31 && (charCode < 48 || charCode > 57))
        return false;
    return true;
}

// POPULARE LISTE VALORI PERS FIZICA

$.ajax({
    url: "/dmsws/nomenclator/countries",
    success: function (result) {
        var countries = result.taraList;
        for (var i = 0; i < countries.length; i++) {
            $('#country').append(getOptionListItemHtml(countries[i].id, countries[i].denumire));

            if(countries[i].cod == 'RO') {
                $('#country').val(countries[i].id).trigger("chosen:updated");
                fetchRegions(countries[i].id);
            }

        }

        // $('#country').val(1).trigger("chosen:updated");
        // fetchRegions(1);
        // fetchCities(1);

    },
    error: function (err) {
        console.log(err);
    }
});

var fetchRegions = (countryId) => {
    $.ajax({
        url: "/dmsws/nomenclator/country/" + countryId + "/regions",
        beforeSend: function () { $('#region').empty(); },
        success: function (result) {
            regions = result.judetList;
            console.log('regions')
            console.log(regions)
            for (var i = 0; i < regions.length; i++) {
                $('#region').append(getOptionListItemHtml(regions[i].id, regions[i].denumire));
            }

            $('#region').val(0).trigger("chosen:updated");
        },
        error: function (err) {
            console.log(err);
        }
    });
};

var fetchCities = (countryId) => {
    $.ajax({
        url: "/dmsws/nomenclator/country/" + countryId + "/cities",
        beforeSend: function () { $('#city').empty(); },
        success: function (result) {
            cities = result.localitateList;
            console.log('cities')
            console.log(cities)
            for (var i = 0; i < cities.length; i++) {
                $('#city').append(getOptionListItemHtml(cities[i].id, cities[i].denumire.trim()));
            }

            $('#city').val(0).trigger("chosen:updated");
        },
        error: function (err) {
            console.log(err);
        }
    });
};

var resizeContainer = () => {

    $('#resize_iframe', window.parent.document).trigger('click');

};
$('#country').on('change', function (e) {
    fetchRegions($(this).val());
    // fetchCities($(this).val());
});

$('#region').on('change', function (e) {

    $.ajax({
        url: "/dmsws/nomenclator/country/" + $('#region').val() + "/cities_by_region",
        beforeSend: function () { $('#city').empty(); },
        success: function (result) {
            cities = result.localitateList;
            for (var i = 0; i < cities.length; i++) {
                $('#city').append('<option value="' + cities[i].id + '">' + cities[i].denumire.trim() + '</option>');
            }
            $('#city').val(0).trigger("chosen:updated");

        },
        error: function (err) {
            console.log(err);
        }
    });
});
$('#region-pfa').on('change', function (e) {

    $.ajax({
        url: "/dmsws/nomenclator/country/" + $('#region-pfa').val() + "/cities_by_region",
        beforeSend: function () { $('#city-pfa').empty(); },
        success: function (result) {
            cities = result.localitateList;
            for (var i = 0; i < cities.length; i++) {
                $('#city-pfa').append(getOptionListItemHtml(cities[i].id, cities[i].denumire.trim()));
            }
            $('#city-pfa').val(0).trigger("chosen:updated");
        },
        error: function (err) {
            console.log(err);
        }
    });
});
$('#region-pfa1').on('change', function (e) {

    $.ajax({
        url: "/dmsws/nomenclator/country/" + $('#region-pfa1').val() + "/cities_by_region",
        beforeSend: function () { $('#city-pfa1').empty(); },
        success: function (result) {
            cities = result.localitateList;
            for (var i = 0; i < cities.length; i++) {
                $('#city-pfa1').append(getOptionListItemHtml(cities[i].id, cities[i].denumire.trim()));
            }
            $('#city-pfa1').val(0).trigger("chosen:updated");
        },
        error: function (err) {
            console.log(err);
        }
    });
});
$('#tos').on('change', function (e) {
    fetchChecker(1);
});

var fetchChecker = (countryId) => {
    $.ajax({
        url: "/dmsws/utilizator/addChecker",
        type: 'POST',
        success: function (result) {
            $('#checker').val(result);
            console.log('checker');
            console.log(result);
        },
        error: function (err) {
            console.log(err);
        }
    });
};

//POPULARE LISTE DE VALORI PERS JURIDICA

$.ajax({
    url: "/dmsws/nomenclator/countries",
    success: function (result) {

        var countries = result.taraList;
        for (var i = 0; i < countries.length; i++) {
            $('#country-pfa').append(getOptionListItemHtml(countries[i].id, countries[i].denumire));

            if(countries[i].cod == 'RO') {
                $('#country-pfa').val(countries[i].id).trigger("chosen:updated");
                fetchRegionsPfa(countries[i].id);
            }
        }


        // fetchRegionsPfa(1);
        // fetchCitiesPfa(1);
    },
    error: function (err) {
        console.log(err);
    }
});

var fetchRegionsPfa = (countryId) => {
    $.ajax({
        url: "/dmsws/nomenclator/country/" + countryId + "/regions",
        beforeSend: function () { $('#region-pfa').empty(); },
        success: function (result) {
            regions = result.judetList;
            for (var i = 0; i < regions.length; i++) {
                $('#region-pfa').append(getOptionListItemHtml(regions[i].id, regions[i].denumire));
            }
            $('#region-pfa').val(0).trigger("chosen:updated");
        },
        error: function (err) {
            console.log(err);
        }
    });
};

var fetchCitiesPfa = (countryId) => {
    $.ajax({
        url: "/dmsws/nomenclator/country/" + countryId + "/cities",
        beforeSend: function () { $('#city-pfa').empty(); },
        success: function (result) {
            cities = result.localitateList;
            for (var i = 0; i < cities.length; i++) {
                $('#city-pfa').append(getOptionListItemHtml(cities[i].id, cities[i].denumire.trim()));
            }
            $('#city-pfa').val(0).trigger("chosen:updated");
        },
        error: function (err) {
            console.log(err);
        }
    });
};

$('#country-pfa').on('change', function (e) {
    fetchRegionsPfa($(this).val());
    // fetchCitiesPfa($(this).val());
});

$('#tos-pfa').on('change', function (e) {
    fetchCheckerPfa(1);
});

var fetchCheckerPfa = (countryId) => {
    $.ajax({
        url: "/dmsws/utilizator/addChecker",
        type: 'POST',
        success: function (result) {
            $('#checker-pfa').val(result);
            console.log('checker-pfa');
            console.log(result);
        },
        error: function (err) {
            console.log(err);
        }
    });
};

//POPULARE LISTA DE VALORI INSTITUT PUBLIC

$.ajax({
    url: "/dmsws/nomenclator/countries",
    success: function (result) {
        var countries = result.taraList;
        for (var i = 0; i < countries.length; i++) {
            $('#country-pfa1').append(getOptionListItemHtml(countries[i].id, countries[i].denumire));
        }

        $('#country-pfa1').val(1).trigger("chosen:updated");

        fetchRegionsPfa1(1);
        // fetchCitiesPfa1(1);
    },
    error: function (err) {
        console.log(err);
    }
});

var fetchRegionsPfa1 = (countryId) => {
    $.ajax({
        url: "/dmsws/nomenclator/country/" + countryId + "/regions",
        beforeSend: function () { $('#region-pfa1').empty(); },
        success: function (result) {
            regions = result.judetList;
            for (var i = 0; i < regions.length; i++) {
                $('#region-pfa1').append(getOptionListItemHtml(regions[i].id, regions[i].denumire));
            }
            $('#region-pfa1').val(0).trigger("chosen:updated");
        },
        error: function (err) {
            console.log(err);
        }
    });
};

var fetchCitiesPfa1 = (countryId) => {
    $.ajax({
        url: "/dmsws/nomenclator/country/" + countryId + "/cities",
        beforeSend: function () { $('#city-pfa1').empty(); },
        success: function (result) {
            cities = result.localitateList;
            for (var i = 0; i < cities.length; i++) {
                $('#city-pfa1').append(getOptionListItemHtml(cities[i].id, cities[i].denumire.trim()));
            }
            $('#city-pfa1').val(0).trigger("chosen:updated");
        },
        error: function (err) {
            console.log(err);
        }
    });
};

$('#country-pfa1').on('change', function (e) {
    fetchRegionsPfa1($(this).val());
    // fetchCitiesPfa1($(this).val());
});

$('#tos-pfa1').on('change', function (e) {
    fetchCheckerPfa1(1);
});

var fetchCheckerPfa1 = (countryId) => {
    $.ajax({
        url: "/dmsws/utilizator/addChecker",
        type: 'POST',
        success: function (result) {
            $('#checker-pfa1').val(result);
            console.log('checker-pfa1');
            console.log(result);
        },
        error: function (err) {
            console.log(err);
        }
    });
};

//POPULARE LISTA DE VALORI SUBORDONATE

$.ajax({
    url: "/dmsws/nomenclator/countries",
    success: function (result) {
        var countries = result.taraList;
        for (var i = 0; i < countries.length; i++) {
            $('#country-pfa2').append(getOptionListItemHtml(countries[i].id, countries[i].denumire));
        }

        $('#country-pfa2').val(1).trigger("chosen:updated");

        fetchRegionsPfa2(1);
        fetchCitiesPfa2(1);
    },
    error: function (err) {
        console.log(err);
    }
});

var fetchRegionsPfa2 = (countryId) => {
    $.ajax({
        url: "/dmsws/nomenclator/country/" + countryId + "/regions",
        beforeSend: function () { $('#region-pfa2').empty(); },
        success: function (result) {
            regions = result.judetList;
            for (var i = 0; i < regions.length; i++) {
                $('#region-pfa2').append(getOptionListItemHtml(regions[i].id, regions[i].denumire));
            }
            $('#region-pfa2').val(0).trigger("chosen:updated");
        },
        error: function (err) {
            console.log(err);
        }
    });
};

var fetchCitiesPfa2 = (countryId) => {
    $.ajax({
        url: "/dmsws/nomenclator/country/" + countryId + "/cities",
        beforeSend: function () { $('#city-pfa2').empty(); },
        success: function (result) {
            cities = result.localitateList;
            for (var i = 0; i < cities.length; i++) {
                $('#city-pfa2').append(getOptionListItemHtml(cities[i].id, cities[i].denumire.trim()));
            }
            $('#city-pfa2').val(0).trigger("chosen:updated");
        },
        error: function (err) {
            console.log(err);
        }
    });
};

$('#country-pfa2').on('change', function (e) {
    fetchRegionsPfa2($(this).val());
    fetchCitiesPfa2($(this).val());
});

$('#tos-pfa2').on('change', function (e) {
    fetchCheckerPfa2(1);
});

var fetchCheckerPfa2 = (countryId) => {
    $.ajax({
        url: "/dmsws/utilizator/addChecker",
        type: 'POST',
        success: function (result) {
            $('#checker-pfa2').val(result);
            console.log('checker-pfa2');
            console.log(result);
        },
        error: function (err) {
            console.log(err);
        }
    });
};


var validateEmail = (email) => {
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(String(email).toLowerCase());
}

var validatePwd = (pwd) => {
    let checkPwd = false;
    if (pwd === "123456") { //wtf?
        checkPwd = true;
    }
    return checkPwd;
}

/******************************************************************************/
/**
 * Validate CNP ( valid for 1800-2099 )
 *
 * @param string $p_cnp
 * @return boolean
 */
var validateCnp = (p_cnp) => {
    var i=0 , year=0 , hashResult=0 , cnpp=[] , hashTable=[2,7,9,1,4,6,3,5,8,2,7,9];
    if( p_cnp.length !== 13 ) { return false; }
    for( i=0 ; i<13 ; i++ ) {
        cnpp[i] = parseInt( p_cnp.charAt(i) , 10 );
        if( isNaN( cnpp[i] ) ) { return false; }
        if( i < 12 ) { hashResult = hashResult + ( cnpp[i] * hashTable[i] ); }
    }
    hashResult = hashResult % 11;
    if( hashResult === 10 ) { hashResult = 1; }
    year = (cnpp[1]*10)+cnpp[2];
    switch( cnpp[0] ) {
        case 1  : case 2 : { year += 1900; } break;
        case 3  : case 4 : { year += 1800; } break;
        case 5  : case 6 : { year += 2000; } break;
        case 7  : case 8 : case 9 : { year += 2000; if( year > ( parseInt( new Date().getYear() , 10 ) - 14 ) ) { year -= 100; } } break;
        default : { return false; }
    }
    if( year < 1800 || year > 2099 ) { return false; }
    return ( cnpp[12] === hashResult );
}

//inregistrare persoana juridica
$( '#register_pfa' )
    .submit( function( e ) {

        $("#register-pfa").prop('disabled','disabled');

        var response ="true";
        try{

            response = grecaptcha.getResponse(0);
        }catch (err){

        }
        if(response == "FALSE") {
            e.preventDefault();
            //reCaptcha not verified
            Swal.fire({
                icon: 'error',
                html: "Captcha gresit!",
                focusConfirm: false,
                confirmButtonText: 'Ok',
            });
            $("#register-pfa").removeAttr('disabled');

        }else{
            let errorString = "<ul class='text-left'>";
            /* Check for required fields */
            $(this).find(".required").each(function () {
                if ($(this).val() === "") {
                    errorString += "<li class='text-danger'>C??mpul: <strong>" + $(this).parent().find("label").text() + "</strong> este obligatoriu.</li>";
                }
            });
            /* Check for TOS */
            if ($("#tos-pfa:checked").length == 0) {
                errorString += "<li class='text-danger'>V?? rug??m s?? citi??i ??i s?? bifa??i termenii ??i condi??iile.</li>";
            }
            /* Passwords */
            if ($("#pwd1-pfa").val() !== $("#pwd2-pfa").val()) {
                errorString += "<li class='text-danger'>Parolele nu se potrivesc.</li>";
            }
            if (CheckPassword($("#pwd1-pfa").val())==false){
                errorString += "<li class='text-danger'>Parola trebuie s?? con??in??: cel pu??in 8 caractere; cel pu??in un caracter numeric; cel pu??in o liter?? mare; cel pu??in o liter?? mic??; cel pu??in un caracter special (=, ^, !, @, #, $, &, *).</li>";
            }
            // if (checkCaptcha()==false){
            //     errorString += "<li class='text-danger'>Va rugam sa completati captcha.</li>";
            // }

   if ($("#cnp-pfa").val() != '' && isNaN($("#cnp-pfa").val())){
   	errorString += "<li class='text-danger'>CNP nu este fromat din cifre.</li>";
   }

            /* Cnp */
            if ($("#cnp-pfa").val() != '' && validateCnp($("#cnp-pfa").val()) == false) {
                errorString += "<li class='text-danger'>CNP-ul este invalid.</li>";
            }

            /* Email */
            if (validateEmail($("#email-pfa").val()) == false) {
                errorString += "<li class='text-danger'>Adresa de email este invalid??.</li>";
            }

            if (!($('#domeniu_ee').prop('checked')) && !($('#domeniu_gn').prop('checked')) && !($('#domeniu_et').prop('checked'))){
                errorString += "<li class='text-danger'>Selectati domeniile in cadrul carora intentionati sa depuneti solicitari.</li>";
            }


            errorString += "</ul>";
            if (errorString !== "<ul class='text-left'></ul>") {
                Swal.fire({
                    icon: 'error',
                    html: errorString,
                    focusConfirm: false,
                    confirmButtonText: 'Ok',
                });
                $("#register-pfa").removeAttr('disabled');

            } else {

                var HTML_Width = $("#register_pfa").width();
                var HTML_Height = $("#register_pfa").height();
                var top_left_margin = 15;
                var PDF_Width = HTML_Width+(top_left_margin*2);
                var PDF_Height = (PDF_Width*1.5)+(top_left_margin*2);
                var canvas_image_width = HTML_Width;
                var canvas_image_height = HTML_Height;

                var totalPDFPages = Math.ceil(HTML_Height/PDF_Height)-1;
                $(window).scrollTop(0);
                $("#register_pfa").css("background","white");

                domtoimage.toJpeg(document.querySelector("#register_pfa"), { quality: 0.95 })
                    .then(function (dataUrl) {
                        var formData = new FormData( document.querySelector("#register_pfa"));
                        var imgData = dataUrl;
                var pdf = new jsPDF('p', 'pt',  [PDF_Width, PDF_Height]);
                pdf.addImage(imgData, 'JPEG', top_left_margin, top_left_margin,canvas_image_width,canvas_image_height);

                for (var i = 1; i <= totalPDFPages; i++) {
                    pdf.addPage(PDF_Width, PDF_Height);
                    pdf.addImage(imgData, 'JPEG', top_left_margin, -(PDF_Height*i)+(top_left_margin*4),canvas_image_width,canvas_image_height);
                }
                formData.append("request_form_pdf", btoa(pdf.output())); ///pdf.output());
                formData.append("domeniu_ee", $('#domeniu_ee').prop('checked'));
                formData.append("domeniu_gn", $('#domeniu_gn').prop('checked'));
                formData.append("domeniu_et", $('#domeniu_et').prop('checked'));

                $.ajax( {
                    url: '/dmsws/utilizator/addPj',
                    type: 'POST',
                    data: formData,
                    processData: false,
                    contentType: false,
                    beforeSend: function() {
                        $UTIL.waitForLoading();
                    },
                    success: function (resultData) {
                        swal.close();
                        Swal.fire({
                            icon: 'success',
                            html: "Solicitarea dumneavoastr?? a fost transmis??! Un link de confirmare a adresei de email v-a fost transmis. V?? rug??m s?? confirma??i contul de email pentru a trimite spre aprobare solicitarea dumneavoastr??. V?? mul??umim!",
                            focusConfirm: false,
                            confirmButtonText: 'Ok'
                    });
                    },
                    error: function(err) {
//    		    TODO: treat html tag for client side servers with err.responseJSON.status and err.responseJSON.message
                        swal.close();
                        Swal.fire({
                            icon: 'error',
                            html: "<p class='text-danger'><strong>" + err.responseText +"</strong></p>",
                            focusConfirm: false,
                            confirmButtonText: 'Ok',
                        });
                        $("#register-pfa").removeAttr('disabled');

                    }} );

            });
            }
        }
        e.preventDefault();
    } );

//inregistrare persoana fizica
$( '#register_pf' ).submit( function( e ) {

    $("#register").prop('disabled','disabled');
    var response ="true";
    try{

        response = grecaptcha.getResponse(0);
    }catch (err){

    }
    if(response == "FALSE") {
        e.preventDefault();
        //reCaptcha not verified
        Swal.fire({
            icon: 'error',
            html: "Captcha gresit!",
            focusConfirm: false,
            confirmButtonText: 'Ok',
        });
        $("#register").removeAttr('disabled');
    }else{
        let errorString = "<ul class='text-left'>";
        /* Check for required fields */
        $(this).find(".required").each(function () {
            if ($(this).val() === "") {
                errorString += "<li class='text-danger'>C??mpul: <strong>" + $(this).parent().find("label").text() + "</strong> este obligatoriu.</li>";
            }
        });
        /* Check for TOS */
        if ($("#tos:checked").length == 0) {
            errorString += "<li class='text-danger'>V?? rug??m s?? citi??i ??i s?? bifa??i termenii ??i condi??iile.</li>";
        }
        /* Passwords */
        if ($("#pwd1").val() !== $("#pwd2").val()) {
            errorString += "<li class='text-danger'>Parolele nu se potrivesc.</li>";
        }

        /* Passwords */
        if (CheckPassword($("#pwd1").val())==false ){
            errorString += "<li class='text-danger'>Parola trebuie s?? con??in??: cel pu??in 8 caractere; cel pu??in un caracter numeric; cel pu??in o liter?? mare; cel pu??in o liter?? mic??; cel pu??in un caracter special (=, ^, !, @, #, $, &, *)</li>";
        }
        // if (checkCaptcha()==false){
        //     errorString += "<li class='text-danger'>Va rugam sa completati captcha.</li>";
        // }

   if (isNaN($("#cnp").val())){
   	errorString += "<li class='text-danger'>CNP nu este fromat din cifre.</li>";
   }

        /* Cnp */
        if (validateCnp($("#cnp").val()) == false) {
            errorString += "<li class='text-danger'>CNP-ul este invalid.</li>";
        }

        /* Email */
        if (validateEmail($("#email").val()) == false) {
            errorString += "<li class='text-danger'>Adresa de email este invalid??.</li>";
        }



        errorString += "</ul>";
        if (errorString !== "<ul class='text-left'></ul>") {

            Swal.fire({
                icon: 'error',
                html: errorString,
                focusConfirm: false,
                confirmButtonText: 'Ok',
            });
            $("#register").removeAttr('disabled');

        } else {



            var HTML_Width = $("#register_pf").width();
            var HTML_Height = $("#register_pf").height();
            var top_left_margin = 15;
            var PDF_Width = HTML_Width+(top_left_margin*2);
            var PDF_Height = (PDF_Width*1.5)+(top_left_margin*2);
            var canvas_image_width = HTML_Width;
            var canvas_image_height = HTML_Height;

            var totalPDFPages = Math.ceil(HTML_Height/PDF_Height)-1;

            $(window).scrollTop(0);
            $("#register_pf").css("background","white");

            domtoimage.toJpeg(document.querySelector("#register_pf"), { quality: 0.95 })
                .then(function (dataUrl) {
                    var formData = new FormData( document.querySelector("#register_pf"));

                    var imgData=dataUrl;
            var pdf = new jsPDF('p', 'pt',  [PDF_Width, PDF_Height]);
            pdf.addImage(imgData, 'JPEG', top_left_margin, top_left_margin,canvas_image_width,canvas_image_height);

            for (var i = 1; i <= totalPDFPages; i++) {
                pdf.addPage(PDF_Width, PDF_Height);
                pdf.addImage(imgData, 'JPEG', top_left_margin, -(PDF_Height*i)+(top_left_margin*4),canvas_image_width,canvas_image_height);
            }
            formData.append("request_form_pdf", btoa(pdf.output())); ///pdf.output());




            $.ajax( {
                url: '/dmsws/utilizator/addPf',
                type: 'POST',
                data: formData,
                processData: false,
                contentType: false,
                beforeSend: function() {
                    $UTIL.waitForLoading();
                },
                success: function (resultData) {
                    swal.close();
                    Swal.fire({
                        icon: 'success',
                        html: "Solicitarea dumneavoastr?? a fost transmis??! Un link de confirmare a adresei de email v-a fost transmis. V?? rug??m s?? confirma??i contul de email pentru a trimite spre aprobare solicitarea dumneavoastr??. V?? mul??umim!",
                        focusConfirm: false,
                        confirmButtonText: 'Ok'
                });
                },
                error: function(err) {
//    		    TODO: treat html tag for client side servers with err.responseJSON.status and err.responseJSON.message
                    swal.close();
                    Swal.fire({
                        icon: 'error',
                        html: "<p class='text-danger'><strong>" + err.responseText +"</strong></p>",
                        focusConfirm: false,
                        confirmButtonText: 'Ok',
                    });
                    $("#register").removeAttr('disabled');

                } } );

        });

        }
    }
    e.preventDefault();
} );

//inregistrare institutie publica
$( '#register_insitutii_publice' )
    .submit( function( e ) {

        var response ="true";
        try{

            response = grecaptcha.getResponse(0);
        }catch (err){

        }
        if(response == "") {
            e.preventDefault();
            //reCaptcha not verified
            Swal.fire({
                icon: 'error',
                html: "Captcha gresit!",
                focusConfirm: false,
                confirmButtonText: 'Ok',
            });
        }else{
            let errorString = "<ul class='text-left'>";
            /* Check for required fields */
            $(this).find(".required").each(function () {
                if ($(this).val() === "") {
                    errorString += "<li class='text-danger'>C??mpul: <strong>" + $(this).parent().find("label").text() + "</strong> este obligatoriu.</li>";
                }
            });
            /* Check for TOS */
            if ($("#tos-pfa1:checked").length == 0) {
                errorString += "<li class='text-danger'>V?? rug??m s?? citi??i ??i s?? bifa??i termenii ??i condi??iile.</li>";
            }
            /* Passwords */
            if ($("#pwd1-pfa1").val() !== $("#pwd2-pfa1").val()) {
                errorString += "<li class='text-danger'>Parolele nu se potrivesc.</li>";
            }
            if (CheckPassword($("#pwd1-pfa1").val())==false){
                errorString += "<li class='text-danger'>Parola trebuie s?? con??in??: cel pu??in 8 caractere; cel pu??in un caracter numeric; cel pu??in o liter?? mare; cel pu??in o liter?? mic??; cel pu??in un caracter special (=, ^, !, @, #, $, &, *)</li>";
            }
            // if (checkCaptcha()==false){
            //     errorString += "<li class='text-danger'>Va rugam sa completati captcha.</li>";
            // }


//    if (isNaN($("#cnp-pfa").val())){
//    	errorString += "<li class='text-danger'>CNP nu este fromat din cifre.</li>";
//    }

            /* Cnp */
            // if (validateCnp($("#cnp-pfa").val()) == false) {
            //     errorString += "<li class='text-danger'>CNP-ul este invalid.</li>";
            // }

            /* Email */
            if (validateEmail($("#email-pfa1").val()) == false) {
                errorString += "<li class='text-danger'>Adresa de email este invalid??.</li>";
            }


            errorString += "</ul>";
            if (errorString !== "<ul class='text-left'></ul>") {
                Swal.fire({
                    icon: 'error',
                    html: errorString,
                    focusConfirm: false,
                    confirmButtonText: 'Ok',
                });
            } else {

                var HTML_Width = $("#register_insitutii_publice").width();
                var HTML_Height = $("#register_insitutii_publice").height();
                var top_left_margin = 15;
                var PDF_Width = HTML_Width+(top_left_margin*2);
                var PDF_Height = (PDF_Width*1.5)+(top_left_margin*2);
                var canvas_image_width = HTML_Width;
                var canvas_image_height = HTML_Height;

                var totalPDFPages = Math.ceil(HTML_Height/PDF_Height)-1;
                $(window).scrollTop(0);

                $("#register_insitutii_publice").css("background","white");

                domtoimage.toJpeg(document.querySelector("#register_insitutii_publice"), { quality: 0.95 })
                    .then(function (dataUrl) {
                        var formData = new FormData( document.querySelector("#register_insitutii_publice"));
                        var imgData = dataUrl;

                        var pdf = new jsPDF('p', 'pt',  [PDF_Width, PDF_Height]);
                pdf.addImage(imgData, 'JPEG', top_left_margin, top_left_margin,canvas_image_width,canvas_image_height);

                for (var i = 1; i <= totalPDFPages; i++) {
                    pdf.addPage(PDF_Width, PDF_Height);
                    pdf.addImage(imgData, 'JPEG', top_left_margin, -(PDF_Height*i)+(top_left_margin*4),canvas_image_width,canvas_image_height);
                }
                formData.append("request_form_pdf", btoa(pdf.output())); ///pdf.output());

                $.ajax( {
                    url: '/dmsws/utilizator/addIp',
                    type: 'POST',
                    data: formData,
                    processData: false,
                    contentType: false,
                    success: function (resultData) {
                        Swal.fire({
                            icon: 'success',
                            html: "Solicitarea dumneavoastr?? a fost transmis??! Un link de confirmare a adresei de email v-a fost transmis. V?? rug??m s?? confirma??i contul de email pentru a trimite spre aprobare solicitarea dumneavoastr??. V?? mul??umim!",
                            focusConfirm: false,
                            confirmButtonText: 'Ok',
                            onClose: () => {
                            top.location.href = $UTIL.WORDPRESS_URL;
                    }

                    });
                    },
                    error: function(err) {
//    		    TODO: treat html tag for client side servers with err.responseJSON.status and err.responseJSON.message
                        Swal.fire({
                            icon: 'error',
                            html: "<p class='text-danger'><strong>" + err.responseText +"</strong></p>",
                            focusConfirm: false,
                            confirmButtonText: 'Ok',
                        });
                    }} );

            });
            }
        }
        e.preventDefault();
    } );

//inregistrare subordonate
$( '#register_subordonate' )
    .submit( function( e ) {

        // if(checkCaptcha() == false) {
        //     e.preventDefault();
        //     //reCaptcha not verified
        //     Swal.fire({
        //         icon: 'error',
        //         html: "Captcha gresit!",
        //         focusConfirm: false,
        //         confirmButtonText: 'Ok',
        //     });
        // }else{
        let errorString = "<ul class='text-left'>";
        /* Check for required fields */
        $(this).find(".required").each(function () {
            if ($(this).val() === "") {
                errorString += "<li class='text-danger'>C??mpul: <strong>" + $(this).parent().find("label").text() + "</strong> este obligatoriu.</li>";
            }
        });
        /* Check for TOS */
        if ($("#tos-pfa2:checked").length == 0) {
            errorString += "<li class='text-danger'>V?? rug??m s?? citi??i ??i s?? bifa??i termenii ??i condi??iile.</li>";
        }
        /* Passwords */
        if ($("#pwd1-pfa2").val() !== $("#pwd2-pfa2").val()) {
            errorString += "<li class='text-danger'>Parolele nu se potrivesc.</li>";
        }
        if (CheckPassword($("#pwd1-pfa2").val())==false){
            errorString += "<li class='text-danger'>Parola trebuie s?? con??in??: cel pu??in 8 caractere; cel pu??in un caracter numeric; cel pu??in o liter?? mare; cel pu??in o liter?? mic??; cel pu??in un caracter special (=, ^, !, @, #, $, &, *)</li>";
        }
        // if (checkCaptcha()==false){
        //     errorString += "<li class='text-danger'>Va rugam sa completati captcha.</li>";
        // }



        /* Email */
        if (validateEmail($("#email-pfa2").val()) == false) {
            errorString += "<li class='text-danger'>Adresa de email este invalid??.</li>";
        }


        errorString += "</ul>";
        if (errorString !== "<ul class='text-left'></ul>") {
            Swal.fire({
                icon: 'error',
                html: errorString,
                focusConfirm: false,
                confirmButtonText: 'Ok',
            });
        } else {

            var HTML_Width = $("#register_subordonate").width();
            var HTML_Height = $("#register_subordonate").height();
            var top_left_margin = 15;
            var PDF_Width = HTML_Width+(top_left_margin*2);
            var PDF_Height = (PDF_Width*1.5)+(top_left_margin*2);
            var canvas_image_width = HTML_Width;
            var canvas_image_height = HTML_Height;

            var totalPDFPages = Math.ceil(HTML_Height/PDF_Height)-1;
            $(window).scrollTop(0);
            $("#register_subordonate").css("background","white");

            domtoimage.toJpeg(document.querySelector("#register_subordonate"), { quality: 0.95 })
                .then(function (dataUrl) {
                    var formData = new FormData( document.querySelector("#register_subordonate"));
                    var imgData = dataUrl;
            var pdf = new jsPDF('p', 'pt',  [PDF_Width, PDF_Height]);
            pdf.addImage(imgData, 'JPEG', top_left_margin, top_left_margin,canvas_image_width,canvas_image_height);

            for (var i = 1; i <= totalPDFPages; i++) {
                pdf.addPage(PDF_Width, PDF_Height);
                pdf.addImage(imgData, 'JPEG', top_left_margin, -(PDF_Height*i)+(top_left_margin*4),canvas_image_width,canvas_image_height);
            }
            formData.append("request_form_pdf", btoa(pdf.output())); ///pdf.output());

            $.ajax( {
                url: '/dmsws/utilizator/addSub',
                type: 'POST',
                data: formData,
                processData: false,
                contentType: false,
                success: function (resultData) {
                    Swal.fire({
                        icon: 'success',
                        html: "Solicitarea dumneavoastr?? a fost transmis??! Un link de confirmare a adresei de email v-a fost transmis. V?? rug??m s?? confirma??i contul de email pentru a trimite spre aprobare solicitarea dumneavoastr??. V?? mul??umim!",
                        focusConfirm: false,
                        confirmButtonText: 'Ok',
                        onClose: () => {
                        top.location.href = $UTIL.WORDPRESS_URL;
                }

                });
                },
                error: function(err) {
//    		    TODO: treat html tag for client side servers with err.responseJSON.status and err.responseJSON.message
                    Swal.fire({
                        icon: 'error',
                        html: "<p class='text-danger'><strong>" + err.responseText +"</strong></p>",
                        focusConfirm: false,
                        confirmButtonText: 'Ok',
                    });
                }} );

        });
        }
        // }
        e.preventDefault();
    } );
$( document ).ready(function() {


    Util.validationFunctions("#register");
//afisare captcha - cu cheie preluata din application.properites: google.recaptcha.secretkey
    $UTIL.loadCaptchaKey().then(function (data) {
        if(data!=null && data!=''){
            $("#container_captcha").html( '  <div class="g-recaptcha" data-sitekey="'+data+'"></div>');
            $("#container_captcha2").html( '  <div class="g-recaptcha" data-sitekey="'+data+'"></div>');

        }

    });
});