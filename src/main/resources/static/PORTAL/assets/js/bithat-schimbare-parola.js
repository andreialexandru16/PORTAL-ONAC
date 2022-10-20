
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
	var token = parts[parts.length - 1];
    var newPass = $('#password').val();
    var urll = "/dmsws/utilizator/resetPassword?"+token+"&newPass="+newPass;
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
		    success: function (resultData) {

                    Swal.fire({
                        icon: 'success',
                        html: "Parola a fost resetata cu succes!",
                        focusConfirm: false,
                        confirmButtonText: 'Ok',
                        onClose: () => {
                        window.location.href = '/PORTAL/autentificare.html';
                }
                });
		        
		    },
		    error: function(err) {
		    	/*var eroare = err.responseJSON.message;
		    	if (eroare=='Request successful') eroare =err.responseJSON.error; */
		        Swal.fire({
		            icon: 'error',
		            html: "<p class='text-danger'><strong>"+err.responseText +"</strong></p>",
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

