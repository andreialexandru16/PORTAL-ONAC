

var getOptionListItemHtml = (id, city) => {
    return '<option value="' + id + '">' + city + '</option>';
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
// POPULARE LISTE VALORI PERS FIZICA
    
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
    fetchCitiesPfa($(this).val());
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
        fetchCitiesPfa1(1);
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
    fetchCitiesPfa1($(this).val());
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

//inregistrare persoana juridica
$( '#register_pfa' )
.submit( function( e ) {
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
	  }else{
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
    if (CheckPassword($("#pwd1-pfa").val())==false){
    	errorString += "<li class='text-danger'>Parola trebuie să conțină: cel puțin 8 caractere; cel puțin un caracter numeric; cel puțin o literă mare; cel puțin o literă mică; cel puțin un caracter special (#,*,^ ...).</li>";
    }
    
    
//    if (isNaN($("#cnp-pfa").val())){
//    	errorString += "<li class='text-danger'>CNP nu este fromat din cifre.</li>";
//    }
    
    /* Cnp */
    // if (validateCnp($("#cnp-pfa").val()) == false) {
    //     errorString += "<li class='text-danger'>CNP-ul este invalid.</li>";
    // }

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

    	  $.ajax( {
    		    url: '/dmsws/utilizator/addPj',
    		    type: 'POST',
    		    data: formData,
    		    processData: false,
    		    contentType: false,
    		    success: function (resultData) {
    		    	Swal.fire({
    		            icon: 'success',
    		            html: "Solicitarea dumneavoastră a fost transmisă! În 24 de ore datele dumneavoastră vor fi validate și contul va fi activat. Vă rugăm să monitorizați contul de email introdus. Vă mulțumim!",
    		            focusConfirm: false,
    		            confirmButtonText: 'Ok',
    		            onClose: () => {
    	                	window.location.href = '/index';
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

//inregistrare persoana fizica
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
	  }else{
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
    
    /* Passwords */
    if (CheckPassword($("#pwd1").val())==false ){
    	errorString += "<li class='text-danger'>Parola trebuie să conțină: cel puțin 8 caractere; cel puțin un caracter numeric; cel puțin o literă mare; cel puțin o literă mică; cel puțin un caracter special (#,*,^ ...)</li>";
    }
    
//    if (isNaN($("#cnp").val())){
//    	errorString += "<li class='text-danger'>CNP nu este fromat din cifre.</li>";
//    }

    /* Cnp */
    // if (validateCnp($("#cnp").val()) == false) {
    //     errorString += "<li class='text-danger'>CNP-ul este invalid.</li>";
    // }
    
    /* Email */
    if (validateEmail($("#email").val()) == false) {
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
                var imgData = dataUrl;

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
    		    success: function (resultData) {
    		    	Swal.fire({
    		            icon: 'success',
    		            html: "Solicitarea dumneavoastră a fost transmisă! În 24 de ore datele dumneavoastră vor fi validate și contul va fi activat. Vă rugăm să monitorizați contul de email introdus. Vă mulțumim!",
    		            focusConfirm: false,
    		            confirmButtonText: 'Ok',
    		            onClose: () => {
    		            	window.location.href = '/PORTAL/autentificare.html';
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

//inregistrare institutie publica
$( '#register_insitutii_publice' )
    .submit( function( e ) {

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
         }else{
        let errorString = "<ul class='text-left'>";
        /* Check for required fields */
        $(this).find(".required").each(function () {
            if ($(this).val() === "") {
                errorString += "<li class='text-danger'>Câmpul: <strong>" + $(this).parent().find("label").text() + "</strong> este obligatoriu.</li>";
            }
        });
        /* Check for TOS */
        if ($("#tos-pfa1:checked").length == 0) {
            errorString += "<li class='text-danger'>Vă rugăm să citiți și să bifați termenii și condițiile.</li>";
        }
        /* Passwords */
        if ($("#pwd1-pfa1").val() !== $("#pwd2-pfa1").val()) {
            errorString += "<li class='text-danger'>Parolele nu se potrivesc.</li>";
        }
        if (CheckPassword($("#pwd1-pfa1").val())==false){
            errorString += "<li class='text-danger'>Parola trebuie să conțină: cel puțin 8 caractere; cel puțin un caracter numeric; cel puțin o literă mare; cel puțin o literă mică; cel puțin un caracter special (#,*,^ ...)</li>";
        }


//    if (isNaN($("#cnp-pfa").val())){
//    	errorString += "<li class='text-danger'>CNP nu este fromat din cifre.</li>";
//    }

        /* Cnp */
        // if (validateCnp($("#cnp-pfa").val()) == false) {
        //     errorString += "<li class='text-danger'>CNP-ul este invalid.</li>";
        // }

        /* Email */
        if (validateEmail($("#email-pfa1").val()) == false) {
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
                        html: "Solicitarea dumneavoastră a fost transmisă! În 24 de ore datele dumneavoastră vor fi validate și contul va fi activat. Vă rugăm să monitorizați contul de email introdus. Vă mulțumim!",
                        focusConfirm: false,
                        confirmButtonText: 'Ok',
                        onClose: () => {
                        window.location.href = '/index';
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
        }else{
        let errorString = "<ul class='text-left'>";
        /* Check for required fields */
        $(this).find(".required").each(function () {
            if ($(this).val() === "") {
                errorString += "<li class='text-danger'>Câmpul: <strong>" + $(this).parent().find("label").text() + "</strong> este obligatoriu.</li>";
            }
        });
        /* Check for TOS */
        if ($("#tos-pfa2:checked").length == 0) {
            errorString += "<li class='text-danger'>Vă rugăm să citiți și să bifați termenii și condițiile.</li>";
        }
        /* Passwords */
        if ($("#pwd1-pfa2").val() !== $("#pwd2-pfa2").val()) {
            errorString += "<li class='text-danger'>Parolele nu se potrivesc.</li>";
        }
        if (CheckPassword($("#pwd1-pfa2").val())==false){
            errorString += "<li class='text-danger'>Parola trebuie să conțină: cel puțin 8 caractere; cel puțin un caracter numeric; cel puțin o literă mare; cel puțin o literă mică; cel puțin un caracter special (#,*,^ ...)</li>";
        }


        /* Email */
        if (validateEmail($("#email-pfa2").val()) == false) {
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
                        html: "Solicitarea dumneavoastră a fost transmisă! În 24 de ore datele dumneavoastră vor fi validate și contul va fi activat. Vă rugăm să monitorizați contul de email introdus. Vă mulțumim!",
                        focusConfirm: false,
                        confirmButtonText: 'Ok',
                        onClose: () => {
                        window.location.href = '/index';
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


