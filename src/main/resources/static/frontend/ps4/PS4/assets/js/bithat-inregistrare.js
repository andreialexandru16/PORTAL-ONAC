
var getOptionListItemHtml = (id, city) => {
    return '<option value="' + id + '">' + city + '</option>';
}
    
$.ajax({
    url: "/dmsws/nomenclator/countries",
    success: function (result) {
        var countries = result.taraList;
        for (var i = 0; i < countries.length; i++) {
            $('#country').append(getOptionListItemHtml(countries[i].id, countries[i].denumire));
        }

        $('#country').val(1).trigger("chosen:updated");

        fetchRegions(1);
        fetchCities(1);
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

$('#country').on('change', function (e) {
    fetchRegions($(this).val());
    fetchCities($(this).val());
});



$.ajax({
    url: "/dmsws/nomenclator/countries",
    success: function (result) {
        var countries = result.taraList;
        for (var i = 0; i < countries.length; i++) {
            $('#country-pfa').append(getOptionListItemHtml(countries[i].id, countries[i].denumire));
        }

        $('#country-pfa').val(1).trigger("chosen:updated");

        fetchRegionsPfa(1);
        fetchCitiesPfa(1);
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
           $('#region').val(0).trigger("chosen:updated");
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
            $('#city').val(0).trigger("chosen:updated");
        },
        error: function (err) {
            console.log(err);
        }
    });
};


$('#country-pfa').on('change', function (e) {
    fetchRegionsPfa($(this).val());
    fetchCitiesPfa($(this).val());
});


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


$( '#register_pfa' )
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
    }else {
        let errorString = "<ul class='text-left'>";
        /* Check for required fields */
        $(this).find(".required").each(function () {
            if ($(this).val() === "") {
                errorString += "<li class='text-danger'>Câmpul: <strong>" + $(this).parent().find("label").text() + "</strong> este obligatoriu.</li>";
            }
        });
        /* Check for TOS */
        if ($("#tos-pfa:checked").length == 0) {
            errorString += "<li class='text-danger'>Vă rugăm să citiți și să bifați termenii și condițiile.</li>";
        }
        /* Passwords */
        if ($("#pwd1-pfa").val() !== $("#pwd2-pfa").val()) {
            errorString += "<li class='text-danger'>Parolele nu se potrivesc.</li>";
        }
        if ($("#pwd1-pfa").val().length < 9) {
            errorString += "<li class='text-danger'>Parola are mai putin de 8 caractere.</li>";
        }
        if (isNaN($("#cnp-pfa").val())) {
            errorString += "<li class='text-danger'>CNP nu este fromat din cifre.</li>";
        }

        /* Email */
        if (validateEmail($("#email-pfa").val()) == false) {
            errorString += "<li class='text-danger'>Adresa de email este invalidă.</li>";
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
            $.ajax({
                url: '/dmsws/utilizator/addPj',
                type: 'POST',
                data: new FormData(this),
                processData: false,
                contentType: false,
                success: function (resultData) {
                    Swal.fire({
                        icon: 'success',
                        html: "Utilizatorul a fost creat cu succes.",
                        focusConfirm: false,
                        confirmButtonText: 'Ok',
                    });

                    window.location.href = '/autentificare.html';
                },
                error: function (err) {
                    Swal.fire({
                        icon: 'error',
                        html: "<p class='text-danger'><strong>" + err.responseText + "</strong></p>",
                        focusConfirm: false,
                        confirmButtonText: 'Ok',
                    });
                }
            });

        }
    }
  e.preventDefault();
} );

$( '#register_pf' ).submit( function( e ) {
     var response ="true";    try{        response = grecaptcha.getResponse(0);    }catch (err){    }
    if(response == "") {
        e.preventDefault();
        //reCaptcha not verified
        Swal.fire({
            icon: 'error',
            html: "Captcha gresit!",
            focusConfirm: false,
            confirmButtonText: 'Ok',
        });
    }else {
        let errorString = "<ul class='text-left'>";
        /* Check for required fields */
        $(this).find(".required").each(function () {
            if ($(this).val() === "") {
                errorString += "<li class='text-danger'>Câmpul: <strong>" + $(this).parent().find("label").text() + "</strong> este obligatoriu.</li>";
            }
        });
        /* Check for TOS */
        if ($("#tos:checked").length == 0) {
            errorString += "<li class='text-danger'>Vă rugăm să citiți și să bifați termenii și condițiile.</li>";
        }
        /* Passwords */
        if ($("#pwd1").val() !== $("#pwd2").val()) {
            errorString += "<li class='text-danger'>Parolele nu se potrivesc.</li>";
        }
        /* Email */
        if (validateEmail($("#email").val()) == false) {
            errorString += "<li class='text-danger'>Adresa de email este invalidă.</li>";
        }
        /* Passwords */
        if ($("#pwd1").val().length < 9) {
            errorString += "<li class='text-danger'>Parola are mai putin de 8 caractere.</li>";
        }
        if (isNaN($("#cnp").val())) {
            errorString += "<li class='text-danger'>CNP nu este fromat din cifre.</li>";
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
            $.ajax({
                url: '/dmsws/utilizator/addPf',
                type: 'POST',
                data: new FormData(this),
                processData: false,
                contentType: false,
                success: function (resultData) {
                    Swal.fire({
                        icon: 'success',
                        html: "Utilizatorul a fost creat cu succes.",
                        focusConfirm: false,
                        confirmButtonText: 'Ok',
                    });

                    window.location.href = '/autentificare.html';
                },
                error: function (err) {
//    		    TODO: treat html tag for client side servers with err.responseJSON.status and err.responseJSON.message
                    Swal.fire({
                        icon: 'error',
                        html: "<p class='text-danger'><strong>" + err.responseText + "</strong></p>",
                        focusConfirm: false,
                        confirmButtonText: 'Ok',
                    });
                }
            });

        }
    }
  e.preventDefault();
} );

