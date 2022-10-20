
$('#reset').click(function(e) {


    var response ="true";
    try{

        response = grecaptcha.getResponse(0);
    }catch (err){

    }
	if(response.length == 0) { 
		e.preventDefault();
	    //reCaptcha not verified
		Swal.fire({
	            icon: 'error',
	            html: "Captcha gresit!",
	            focusConfirm: false,
	            confirmButtonText: 'Ok',
	        }); 
	  }else{
	
	var currentURL = window.location.href;
	var parts = currentURL.split("?");
        var token= null;
        try{
            token= sessionStorage.tokenParola;
        }catch (err){

        }
        if(token==null || typeof token=='undefined'){
            token = parts[parts.length - 1];
        }

        if(token==null || typeof token=='undefined'){
            Swal.fire({
                icon: "error",
                html: 'Ne pare rau! Identitatea nu a putut fi verificata!',
                focusConfirm: false,
                confirmButtonText: "Ok", onClose: () => {
                window.location.href = $UTIL.WORDPRESS_URL;

        }

        });
        }

    var newPass = $('#password').val();
        var confirma_parola=$('#password_2').val();
        if(newPass==null || newPass=='' || confirma_parola==null || confirma_parola==''){
            Swal.fire({
                icon: 'error',
                html: "Completati toate campurile!",
                focusConfirm: false,
                confirmButtonText: 'Ok',
            });
            e.preventDefault();
        } else
        if (newPass !== confirma_parola) {
            Swal.fire({
                icon: 'error',
                html: "Parolele nu se potrivesc.",
                focusConfirm: false,
                confirmButtonText: 'Ok',
            });
            e.preventDefault();
        }
        token = token.replaceAll('\"','');
        var data={newPassword: newPass};
        var jReq = JSON.stringify(data);
    var urll = "/dmsws/utilizator/resetPasswordBody?&token="+token;
    /* Email */
    let errorString = "<ul class='text-left'>";
    /* Passwords */
    if ($("#password").val() !== $("#password_2").val()) {
        errorString += "<li class='text-danger'>Parolele nu se potrivesc.</li>";
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
	        type: 'POST',
	        datatype : "application/json",
	        contentType: "application/json",
	        url: urll,
            data: jReq,
		    success: function (resultData) {

                    Swal.fire({
                        icon: 'success',
                        html: "Parola a fost resetata cu succes!",
                        focusConfirm: false,
                        confirmButtonText: 'Ok',
                        onClose: () => {
                        window.top.location.href=$UTIL.WORDPRESS_URL;
                }
                });
		        
		    },
		    error: function(err) {
		    	/*var eroare = err.responseJSON.message;
		    	if (eroare=='Request successful') eroare =err.responseJSON.error; */
		        Swal.fire({
		            icon: 'error',
		            html: "<p class='text-danger'><strong>Parola nu este destul de complexa. Respectati toate criteriile specificate!</strong></p>",
		            focusConfirm: false,
		            confirmButtonText: 'Ok',
		        });    		        
		  }
	    });
    }
	  }
    e.preventDefault();
});

$(document).ready(function () {


    //afisare captcha - cu cheie preluata din application.properites: google.recaptcha.secretkey
    $UTIL.loadCaptchaKey().then(function (data) {
        if(data!=null && data!=''){
            $("#container_captcha").html( '  <div class="g-recaptcha" data-sitekey="'+data+'"></div>');

        }

    });



});

