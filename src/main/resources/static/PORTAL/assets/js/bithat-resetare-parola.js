var validateEmail = (email) => {
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(String(email).toLowerCase());
}


$('#reset').click(function(e) {
/*	var response = grecaptcha.getResponse();
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
    var email = $('#email').val();
    var urll = "/dmsws/utilizator/resetPassByEmail/"+email+"/";

    /* Email */
    let errorString = "<ul class='text-left'>";
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
	    $.ajax({
	        type: 'POST',
	        datatype : "application/json",
	        contentType: "application/json",
	        url: urll,
		    success: function (resultData) {
		    	Swal.fire({
		            icon: 'success',
		            html: "Un email cu linkul de resetare parola va fi trimis daca exista un cont asociat.",
		            focusConfirm: false,
		            confirmButtonText: 'Ok',
		            onClose: () => {
	                	window.location.href = '/index';
		             }
		        });
	//	    	alert(resultData);
		        
		    },
		    error: function(err) {
		    	var eroare = err.responseJSON.message;
		    	if (eroare=='Request successful') eroare =err.responseJSON.error; 
		        Swal.fire({
		            icon: 'error',
		            html: "<p class='text-danger'><strong>"+eroare+"</strong></p>",
		            focusConfirm: false,
		            confirmButtonText: 'Ok',
		        });      		        
		  }
	    });
    }
	//  }
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