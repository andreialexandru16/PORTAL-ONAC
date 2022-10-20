$('#reset').click(function() {
    var email = $('#email').val();

    $.ajax({
        type: 'POST',
        datatype : "application/json",
        contentType: "application/json",
        url: "/dmsws/utilizator/resetPassword",
        success: function (resultData) {
            window.location.href = '/autentificare.html';
        },
        error: function(err) {
            console.log(err);
            window.location.href = '/autentificare.html';
        }
    });

});
$(document).ready(function () {


    //afisare captcha - cu cheie preluata din application.properites: google.recaptcha.secretkey
    $UTIL.loadCaptchaKey().then(function (data) {
        if(data!=null && data!=''){
            $("#container_captcha").html( '  <div class="g-recaptcha" data-sitekey="'+data+'"></div>');

        }

    });



});