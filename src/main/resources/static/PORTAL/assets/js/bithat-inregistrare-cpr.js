var PAGE_NAME="bithat-inregistrare-cpr.js";
var psihologExistent = false;



var getOptionListItemHtml = (id, city) => {
    return '<option value="' + id + '">' + city + '</option>';
}

//FILIALE
$.ajax({
    url: "/dmsws/nomenclator/filiala",
    success: function (result) {

        var filiale = result.filialaList;
        for (var i = 0; i < filiale.length; i++) {
            $('#filiala').append(getOptionListItemHtml(filiale[i].id, filiale[i].denumire));
        }

        $('#filiala').val(0).trigger("chosen:updated");

        //     $('#country').val(1).trigger("chosen:updated");

      //  $('#filiala').val(1);

    },
    error: function (err) {
        console.log(err);
    }
});
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
// DOMICILIU
$.ajax({
    url: "/dmsws/nomenclator/countries",
    success: function (result) {
        var countries = result.taraList;
        for (var i = 0; i < countries.length; i++) {
            $('#country').append(getOptionListItemHtml(countries[i].id, countries[i].denumire));
        }

   //     $('#country').val(1).trigger("chosen:updated");

        $('#country').val(1);
        fetchRegions(1);
        fetchRegionsFiliala(1);
        fetchRegionsMunca(1);

        //  fetchCities(1);
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

// var fetchCities = (countryId) => {
//
//     $.ajax({
//         url: "/dmsws/nomenclator/country/" + countryId + "/cities",
//         beforeSend: function () { $('#city').empty(); },
//         success: function (result) {
//             cities = result.localitateList;
//             console.log('cities');
//             console.log(cities);
//             for (var i = 0; i < cities.length; i++) {
//                 $('#city').append(getOptionListItemHtml(cities[i].id, cities[i].denumire.trim()));
//             }
//
//             $('#city').val(0).trigger("chosen:updated");
//         },
//         error: function (err) {
//             console.log(err);
//         }
//     });
// };

$('#country').on('change', function (e) {
    fetchRegions($(this).val());
   // fetchCities($(this).val());
});

$('#region').on('change', function (e) {

    if($('#region').val()!=null){
        $.ajax({

            url: "/dmsws/nomenclator/country/" + $('#region').val() + "/cities_by_region",
            beforeSend: function () { $('#city').empty(); },
            success: function (result) {
                cities = result.localitateList;
                for (var i = 0; i < cities.length; i++) {
                    $('#city').append(getOptionListItemHtml(cities[i].id, cities[i].denumire.trim()));
                }
                    $('#city').val(0).trigger("chosen:updated");

            },
            error: function (err) {
                console.log(err);
            }
        });
    }

});

//FILIALA
// $.ajax({
//     url: "/dmsws/nomenclator/countries",
//     success: function (result) {
//         var countries = result.taraList;
//         for (var i = 0; i < countries.length; i++) {
//             $('#country').append(getOptionListItemHtml(countries[i].id, countries[i].denumire));
//         }
//
//         fetchRegionsFiliala(1);
//     },
//     error: function (err) {
//         console.log(err);
//     }
// });


var fetchRegionsFiliala = (countryId) => {
    $.ajax({
        url: "/dmsws/nomenclator/country/" + countryId + "/regions",
        beforeSend: function () { $('#region_filiala').empty(); },
        success: function (result) {
            regions = result.judetList;
            console.log('regions')
            console.log(regions)
            for (var i = 0; i < regions.length; i++) {
                $('#region_filiala').append(getOptionListItemHtml(regions[i].id, regions[i].denumire));
            }

            $('#region_filiala').val(0).trigger("chosen:updated");
        },
        error: function (err) {
            console.log(err);
        }
    });
};

$('#region_filiala').on('change', function (e) {


    if($('#region_filiala').val()!=null){
        $.ajax({
            url: "/dmsws/nomenclator/country/" + $('#region_filiala').val() + "/cities_by_region",
            beforeSend: function () { $('#city_filiala').empty(); },
            success: function (result) {
                cities = result.localitateList;
                for (var i = 0; i < cities.length; i++) {
                    $('#city_filiala').append(getOptionListItemHtml(cities[i].id, cities[i].denumire.trim()));
                }
                $('#city_filiala').val(0).trigger("chosen:updated");

            },
            error: function (err) {
                console.log(err);
            }
        });
    }

});

//MUNCA

var fetchRegionsMunca = (countryId) => {
    $.ajax({
        url: "/dmsws/nomenclator/country/" + countryId + "/regions",
        beforeSend: function () { $('#region_munca').empty(); },
        success: function (result) {
            regions = result.judetList;
            console.log('regions');
            console.log(regions);
            for (var i = 0; i < regions.length; i++) {
                $('#region_munca').append(getOptionListItemHtml(regions[i].id, regions[i].denumire));
            }

            $('#region_munca').val(0).trigger("chosen:updated");
        },
        error: function (err) {
            console.log(err);
        }
    });
};

$('#region_munca').on('change', function (e) {


    if($('#region_munca').val()!=null){
        $.ajax({
            url: "/dmsws/nomenclator/country/" + $('#region_munca').val() + "/cities_by_region",
            beforeSend: function () { $('#city_munca').empty(); },
            success: function (result) {
                cities = result.localitateList;
                for (var i = 0; i < cities.length; i++) {
                    $('#city_munca').append(getOptionListItemHtml(cities[i].id, cities[i].denumire.trim()));
                }
                    $('#city_munca').val(0).trigger("chosen:updated");

            },
            error: function (err) {
                console.log(err);
            }
        });
    }

});

//END

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
            if( $('#city-pfa').val()==null){
                $('#city-pfa').val(0).trigger("chosen:updated");

            }
        },
        error: function (err) {
            console.log(err);
        }
    });
};


var fetchCitiesFiliala = (countryId) => {
    $.ajax({
        url: "/dmsws/nomenclator/country/" + countryId + "/cities",
        beforeSend: function () { $('#city-pfa').empty(); },
        success: function (result) {
            cities = result.localitateList;
            for (var i = 0; i < cities.length; i++) {
                $('#city_filiala').append(getOptionListItemHtml(cities[i].id, cities[i].denumire.trim()));
            }
            if( $('#city_filiala').val()==null){
                $('#city_filiala').val(0).trigger("chosen:updated");

            }
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

function fetchInfo() {

    var PROC_NAME = "fetchInfo";
    console.log(PROC_NAME);

    var cnp = $("#cnp").val();
    var codRup = $("#cod_rup").val();

    $.ajax({
        url: "/dmsws/utilizator/getInfoPsiholog/" + codRup + "/" + cnp,
        success: function (result) {
            var exists = result.exists;

            if(exists==true){
                var psiholog = result.psihologList[0];

                $("#region_filiala").val(psiholog.judetFiliala).change();
                $('#region_filiala').trigger("chosen:updated");
                $("#region_munca").val(psiholog.judetMunca).change();
                $('#region_munca').trigger("chosen:updated");

                $("#region").val(psiholog.judet).change();
                $('#region').trigger("chosen:updated");
                $('#cnp').attr('readonly', true);
                $('#cod_rup').attr('readonly', true);
                $("#register").show();


                var numePrenume = psiholog.nume;
                var nume = numePrenume.split(" ")[0];
                var prenume = "";
                for(i=1; i<numePrenume.split(" ").length; i++){
                    var prenume = prenume + numePrenume.split(" ")[i] + " ";
                }

                $("#nume").val(nume);
                $("#prenume").val(prenume);
                $("#tel").val(psiholog.telefon);
                $("#email").val(psiholog.email);
                $("#den_munca").val(psiholog.locMunca);

                $("#street").val(psiholog.strada);
                $("#nr_street").val(psiholog.nr);
                $("#bloc").val(psiholog.bloc);
                $("#scara").val(psiholog.scara);
                $("#etaj").val(psiholog.etaj);
                $("#apartament").val(psiholog.apartament);
                $("#street_munca").val(psiholog.stradaMunca);
                $("#nr_street_munca").val(psiholog.nrMunca);
                $("#bloc_munca").val(psiholog.blocMunca);
                $("#scara_munca").val(psiholog.scaraMunca);
                $("#etaj_munca").val(psiholog.etajMunca);
                $("#apartament_munca").val(psiholog.apartamentMunca);
                $("#universitate_licenta").val(psiholog.universitateLicenta);
                $("#facultatea_licenta").val(psiholog.facultateaLicenta);
                $("#specializare_licenta").val(psiholog.specializareLicenta);
                $("#an_obtinere_licenta").val(psiholog.anObtinereLicenta);
                $("#universitate_master").val(psiholog.universitateMaster);
                $("#facultatea_master").val(psiholog.facultateaMaster);
                $("#denumire_master").val(psiholog.denumireMaster);
                $("#an_obtinere_disertatie").val(psiholog.anObtinereDisertatie);
                $("#furnizor_formare").val(psiholog.furnizorFormare);
                $("#specialitate_formare").val(psiholog.specialitateFormare);
                $("#specializare_formare").val(psiholog.specializareFormare);
                $("#serie_formare").val(psiholog.serieFormare);
                $("#data_emitere_formare").val(psiholog.dataEmitereFormare);
                $("#treapta_formare").val(psiholog.treaptaFormare);
                $("#regim_formare").val(psiholog.regimFormare);
                $("#universitate_doctorat").val(psiholog.universitateDoctorat);
                $("#domeniu_doctorat").val(psiholog.domeniuDoctorat);
                $("#denumire_tema_doctorat").val(psiholog.denumireTemaDoctorat);
                $("#an_obtinere_doctorat").val(psiholog.anObtinereDoctorat);

                $("#filiala").val(psiholog.filiala).change();
                $('#filiala').trigger("chosen:updated");



                $("#city_filiala").val(psiholog.localitateFiliala).change();
                $('#city_filiala').trigger("chosen:updated");

                $("#city").val(psiholog.localitate).change();
                $('#city').trigger("chosen:updated");


                $("#city_munca").val(psiholog.localitateMunca).change();
                $('#city_munca').trigger("chosen:updated");
                Swal.fire({
                    icon: 'success',
                    html: "Datele au fost incarcate cu succes! Va rugam sa le actualizati acolo unde este cazul pentru a finaliza procesul de inrolare.",
                    focusConfirm: false,
                    confirmButtonText: 'Ok',
                    onClose: () => {
                        psihologExistent = true;
            }
                });

            } else {
                Swal.fire({
                    icon: 'error',
                    html: "Datele introduse nu corespund niciunui psiholog. ",
                    focusConfirm: false,
                    confirmButtonText: 'Ok'
                });

            }

        },
        error: function (err) {
            console.log(err);
        }
    });
}

function saveUserInfoExtended() {

    var PROC_NAME = "saveUserInfoExtended";
    console.log(PROC_NAME);

    var cnp = $("#cnp").val();
    var codRup = $("#cod_rup").val();
    var email = $("#email").val();
    var idFiliala = $("#filiala").val();
    var idJudetFiliala = $("#region_filiala").val();
    var idLocalitateFiliala = $("#city_filiala").val();
    var locMunca = $("#den_munca").val();
    var idJudetMunca = $("#region_munca").val();
    var idLocalitateMunca = $("#city_munca").val();
    var stradaMunca = $("#street_munca").val();
    var nrMunca = $("#nr_street_munca").val();
    var blocMunca = $("#bloc_munca").val();
    var scaraMunca = $("#scara_munca").val();
    var etajMunca = $("#etaj_munca").val();
    var apartamentMunca = $("#apartament_munca").val();
    var strada = $("#street").val();
    var nr = $("#nr_street").val();
    var bloc = $("#bloc").val();
    var scara = $("#scara").val();
    var etaj = $("#etaj").val();
    var apartament = $("#apartament").val();
    var universitateLicenta = $("#universitate_licenta").val();
    var facultateaLicenta = $("#facultatea_licenta").val();
    var specializareLicenta = $("#specializare_licenta").val();
    var anObtinereLicenta = $("#an_obtinere_licenta").val();
    var universitateMaster = $("#universitate_master").val();
    var facultateaMaster = $("#facultatea_master").val();
    var denumireMaster = $("#denumire_master").val();
    var anObtinereDisertatie = $("#an_obtinere_disertatie").val();
    var furnizorFormare = $("#furnizor_formare").val();
    var specialitateFormare = $("#specialitate_formare").val();
    var specializareFormare = $("#specializare_formare").val();
    var serieFormare = $("#serie_formare").val();
    var dataEmitereFormare = $("#data_emitere_formare").val();
    var treaptaFormare = $("#treapta_formare").val();
    var regimFormare = $("#regim_formare").val();
    var universitateDoctorat = $("#universitate_doctorat").val();
    var domeniuDoctorat = $("#domeniu_doctorat").val();
    var denumireTemaDoctorat = $("#denumire_tema_doctorat").val();
    var anObtinereDoctorat = $("#an_obtinere_doctorat").val();


    var psiholog = {
        id: "",
        codRup: codRup,
        cnp: cnp,
        email: email,
        idFiliala: idFiliala,
        idLocalitateFiliala: idLocalitateFiliala,
        idJudetFiliala: idJudetFiliala,
        locMunca: locMunca,
        idJudetMunca: idJudetMunca,
        idLocalitateMunca: idLocalitateMunca,
        stradaMunca: stradaMunca,
        nrMunca: nrMunca,
        blocMunca: blocMunca,
        scaraMunca: scaraMunca,
        etajMunca: etajMunca,
        apartamentMunca: apartamentMunca,
        strada: strada,
        nr: nr,
        bloc: bloc,
        scara: scara,
        etaj: etaj,
        universitateLicenta: universitateLicenta,
        facultateaLicenta: facultateaLicenta,
        specializareLicenta: specializareLicenta,
        anObtinereLicenta: anObtinereLicenta,
        universitateMaster: universitateMaster,
        facultateaMaster: facultateaMaster,
        denumireMaster: denumireMaster,
        anObtinereDisertatie: anObtinereDisertatie,
        furnizorFormare: furnizorFormare,
        specialitateFormare: specialitateFormare,
        specializareFormare: specializareFormare,
        serieFormare: serieFormare,
        dataEmitereFormare: dataEmitereFormare,
        treaptaFormare: treaptaFormare,
        regimFormare: regimFormare,
        universitateDoctorat: universitateDoctorat,
        domeniuDoctorat: domeniuDoctorat,
        denumireTemaDoctorat: denumireTemaDoctorat,
        anObtinereDoctorat: anObtinereDoctorat




    };

    var jReq= JSON.stringify(psiholog);

    $.ajax({
        type: 'POST',
        url: "/dmsws/utilizator/addPsihologExtension",
        data:jReq,
        datatype : "application/json",
        contentType: "application/json",
        success: function (result) {


            var resp = result.result;

            if(resp=="OK"){

                Swal.fire({
                    icon: 'success',
                    html: "Solicitarea dumneavoastră a fost transmisă! În 24 de ore datele dumneavoastră vor fi validate și contul va fi activat. Vă rugăm să monitorizați contul de email introdus. Vă mulțumim!",
                    focusConfirm: false,
                    confirmButtonText: 'Ok',
                    onClose: () => {
                    window.location.href = '/index';
            }

            });

            } else {
                Swal.fire({
                    icon: 'error',
                    html: "Contul nu s-a putut crea X.",
                    focusConfirm: false,
                    confirmButtonText: 'Ok'
                });

            }

        },
        error: function (err) {
            console.log(err);
            Swal.fire({
                icon: 'error',
                html: "<p class='text-danger'><strong>" + "Contul nu a putut fi creat" +"</strong></p>",
                focusConfirm: false,
                confirmButtonText: 'Ok',
            });
        }
    });
}




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


$( '#register_pfa' )
.submit( function( e ) {
	/*var response = grecaptcha.getResponse(1);
	if(response.length == 0) { 
		e.preventDefault();
	    //reCaptcha not verified
		Swal.fire({
	            icon: 'error',
	            html: "Captcha gresit!",
	            focusConfirm: false,
	            confirmButtonText: 'Ok',
	        }); 
	  }else{*/
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
    if ($("#pwd1-pfa").val().length < 9){
    	errorString += "<li class='text-danger'>Parola are mai putin de 8 caractere.</li>";
    }
    
    
//    if (isNaN($("#cnp-pfa").val())){
//    	errorString += "<li class='text-danger'>CNP nu este fromat din cifre.</li>";
//    }
    
    /* Cnp */
    if (validateCnp($("#cnp-pfa").val()) == false) {
        errorString += "<li class='text-danger'>CNP-ul este invalid.</li>";
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
                    saveUserInfoExtended();
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

$( '#register_pf' ).submit( function( e ) {
	/* var response ="true";    try{        response = grecaptcha.getResponse(0);    }catch (err){    }
	if(response.length == 0) { 
		e.preventDefault();
	    //reCaptcha not verified
		Swal.fire({
	            icon: 'error',
	            html: "Captcha gresit!",
	            focusConfirm: false,
	            confirmButtonText: 'Ok',
	        }); 
	  }else{*/
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
    if ($("#pwd1").val().length < 8){
    	errorString += "<li class='text-danger'>Parola are mai putin de 8 caractere.</li>";
    }
    
//    if (isNaN($("#cnp").val())){
//    	errorString += "<li class='text-danger'>CNP nu este fromat din cifre.</li>";
//    }

    /* Cnp */
    if (validateCnp($("#cnp").val()) == false) {
        errorString += "<li class='text-danger'>CNP-ul este invalid.</li>";
    }
    
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
                    saveUserInfoExtended();
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
//inregistrare persoana fizica
$( '#register_pf2' ).submit( function( e ) {


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
    }else{
        let errorString = "<ul class='text-left'>";
        /* Check for required fields */
        $(this).find(".required").each(function () {
            if ($(this).val() === "") {
                errorString += "<li class='text-danger'>Câmpul: <strong>" + $(this).parent().find("label").text() + "</strong> este obligatoriu.</li>";
            }
        });
        /* Check for TOS */
        if ($("#tos-pf2:checked").length == 0) {
            errorString += "<li class='text-danger'>Vă rugăm să citiți și să bifați termenii și condițiile.</li>";
        }
        /* Passwords */
        if ($("#pwd1-pf2").val() !== $("#pwd2-pf2").val()) {
            errorString += "<li class='text-danger'>Parolele nu se potrivesc.</li>";
        }

        /* Passwords */
        if (CheckPassword($("#pwd1-pf2").val())==false ){
            errorString += "<li class='text-danger'>Parola trebuie să conțină: cel puțin 8 caractere; cel puțin un caracter numeric; cel puțin o literă mare; cel puțin o literă mică; cel puțin un caracter special (=, ^, !, @, #, $, &, *)</li>";
        }
        // if (checkCaptcha()==false){
        //     errorString += "<li class='text-danger'>Va rugam sa completati captcha.</li>";
        // }

//    if (isNaN($("#cnp").val())){
//    	errorString += "<li class='text-danger'>CNP nu este fromat din cifre.</li>";
//    }

        /* Cnp */
        // if (validateCnp($("#cnp").val()) == false) {
        //     errorString += "<li class='text-danger'>CNP-ul este invalid.</li>";
        // }

        /* Email */
        if (validateEmail($("#email-pf2").val()) == false) {
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



            var HTML_Width = $("#register_pf2").width();
            var HTML_Height = $("#register_pf2").height();
            var top_left_margin = 15;
            var PDF_Width = HTML_Width+(top_left_margin*2);
            var PDF_Height = (PDF_Width*1.5)+(top_left_margin*2);
            var canvas_image_width = HTML_Width;
            var canvas_image_height = HTML_Height;

            var totalPDFPages = Math.ceil(HTML_Height/PDF_Height)-1;

            $(window).scrollTop(0);
            $("#register_pf2").css("background","white");

            domtoimage.toJpeg(document.querySelector("#register_pf2"), { quality: 0.95 })
                .then(function (dataUrl) {
                    var formData = new FormData( document.querySelector("#register_pf2"));

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
                        success: function (resultData) {
                            Swal.fire({
                                icon: 'success',
                                html: "Solicitarea dumneavoastră a fost transmisă! Un link de confirmare a adresei de email v-a fost transmis. Vă rugăm să confirmați contul de email pentru a trimite spre aprobare solicitarea dumneavoastră. Vă mulțumim!",
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
