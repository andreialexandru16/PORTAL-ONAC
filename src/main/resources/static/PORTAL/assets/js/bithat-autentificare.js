$(window).on('load', function(){
    // your logic here`enter code here`
    var currentURL = window.location.href;
    var parts = currentURL.split("?");
    var err = parts[parts.length - 1];


    if(err.includes('error')){
        Swal.fire({
            icon: 'error',
            html: "User sau parola gresite",
            focusConfirm: false,
            confirmButtonText: 'Ok',
        });
        var username= $("#username");
        $.ajax({
            // type: 'POST',
            // url: $UTIL.WS_URL + '/utilizator/' + $UTIL.LINK_WS_TOKEN + '/updateLoginFailed/' + username + '/',
            type: 'POST',
            datatype : "application/json",
            contentType: "application/json",
            url: "/dmsws/utilizator/updateLoginFailed/"+username+"/",
            success: function (data) {
                if (data.result == 'OK') {
                    if(data.locked==1){
                        Swal.fire({
                            icon: 'error',
                            html: "Contul a fost blocat din cauza depăşirii incercărilor permise!",
                            focusConfirm: false,
                            confirmButtonText: 'Ok'
                        });
                    }else{
                        if(data.loginFailRemains){
                            Swal.fire({
                                icon: 'error',
                                html: "Username sau parola greșită!<br/> Încercări rămase:"+data.loginFailRemains,
                                focusConfirm: false,
                                confirmButtonText: 'Ok'
                            });
                        }else{
                            Swal.fire({
                                icon: 'error',
                                html: "Username sau parola greșită!<br/>",
                                focusConfirm: false,
                                confirmButtonText: 'Ok'
                            });
                        }
                    }
                }
                else{
                    Swal.fire({
                        icon: 'error',
                        html: "Username sau parola greșită!<br/>",
                        focusConfirm: false,
                        confirmButtonText: 'Ok'
                    });
                }

            }
        });
    }


});

function checkCaptcha(){
    if($('#status').val()=='Corect!')
        return true;
    else
        return false;
}
//TODO just on prod
$( '#login_form' ).submit( function( e ) {
    debugger;
    var response ="true";
    try{

        response = grecaptcha.getResponse(0);
    }catch (err){

    }
	var username=$('#username').val();
    var password=($('#password').val());
    if(password==null || password==''){
        Swal.fire({
            icon: 'error',
            html: "Introduceti parola!",
            focusConfirm: false,
            confirmButtonText: 'Ok',
        });
        e.preventDefault();
    } else if(username==null || username==''){
        Swal.fire({
            icon: 'error',
            html: "Introduceti username-ul!",
            focusConfirm: false,
            confirmButtonText: 'Ok',
        });
        e.preventDefault();
    }
    else{
        if(response == "false") {
            e.preventDefault();
            //reCaptcha not verified
            Swal.fire({
                icon: 'error',
                html: "Captcha gresit!",
                focusConfirm: false,
                confirmButtonText: 'Ok',
            });
        }
        else{

            e.preventDefault();
            //apelare checkedblockedUser (dar varianta care sa nu faca si login)
            $.ajax({
                // url: $UTIL.WS_URL + '/utilizator/' + $UTIL.LINK_WS_TOKEN + '/getUserInfoByUsername/' + username + '/',
                type: 'GET',
                datatype : "application/json",
                contentType: "application/json",
                url: "/dmsws/utilizator/getUserInfoByUsername/"+username+"/",
                // type: 'POST',
                success: function (data) {
                    if (data.result == 'OK') {
                        if (data.locked == 1) {
                            Swal.fire({
                                icon: 'error',
                                html: "<p>Contul a fost blocat din cauza depăşirii incercărilor permise pentru o perioada de "+data.extendedInfo2+" zile.</p><p>După acest interval va fi deblocat automat. Pentru urgențe ne puteți contacta folosind informațiile din pagina de contact.</p> ",
                                focusConfirm: false,
                                confirmButtonText: 'Ok',
                            });
                            e.preventDefault();
                        }
                        if (data.activ == 0) {
                            Swal.fire({
                                icon: 'error',
                                html: "Contul nu a fost activat !",
                                focusConfirm: false,
                                confirmButtonText: 'Ok',
                            });
                            e.preventDefault();
                        }
                        if(data.parola=='expired'){
                            Swal.fire({
                                icon: 'error',
                                html: "Acest utilizator are parola expirată!",
                                focusConfirm: false,
                                confirmButtonText: 'Ok',
                                onClose: () => {
                                window.location.href = 'resetare-parola.html';
                        }});
                            e.preventDefault();


                        }
                        if ((data.locked == null||data.locked==0) && data.activ == 1 && data.parola!='expired') {
                            var login={
                                username:username,
                                password:password
                            };
                            var jReq= JSON.stringify(login);
                            //se preia login url de apelat din application.properites: dmsws.login.url  (default= /login2)
                            $.ajax({

                                type: 'GET',
                                url: '/dmsws/utilizator/getLoginUrl',
                                success: function (data) {
                                    if(data!=null && data!=''){
                                        $.ajax({
                                            type: 'POST',
                                            url: data,
                                            data:jReq,
                                            datatype : "application/json",
                                            contentType: "application/json",
                                            success: function (resultDataStr) {

                                                var resultData= JSON.parse(resultDataStr);
                                                if(resultData.status==false){
                                                    $.ajax({
                                                        // type: 'POST',
                                                        // url: $UTIL.WS_URL + '/utilizator/' + $UTIL.LINK_WS_TOKEN + '/updateLoginFailed/' + username + '/',
                                                        type: 'POST',
                                                        datatype : "application/json",
                                                        contentType: "application/json",
                                                        url: "/dmsws/utilizator/updateLoginFailed/"+username+"/",
                                                        success: function (data) {
                                                            if (data.result == 'OK') {
                                                                if(data.locked==1){
                                                                    Swal.fire({
                                                                        icon: 'error',
                                                                        html: "Contul a fost blocat din cauza depăşirii incercărilor permise!",
                                                                        focusConfirm: false,
                                                                        confirmButtonText: 'Ok'
                                                                    });
                                                                    e.preventDefault();
                                                                }else{
                                                                    if(data.loginFailRemains){
                                                                        Swal.fire({
                                                                            icon: 'error',
                                                                            html: "Username sau parola greșită!<br/> Încercări rămase:"+data.loginFailRemains,
                                                                            focusConfirm: false,
                                                                            confirmButtonText: 'Ok'
                                                                        });
                                                                    }else{
                                                                        Swal.fire({
                                                                            icon: 'error',
                                                                            html: "Username sau parola greșită!<br/>",
                                                                            focusConfirm: false,
                                                                            confirmButtonText: 'Ok'
                                                                        });
                                                                    }
                                                                    e.preventDefault();
                                                                }
                                                            }
                                                            else{
                                                                Swal.fire({
                                                                    icon: 'error',
                                                                    html: "Username sau parola greșită!<br/>",
                                                                    focusConfirm: false,
                                                                    confirmButtonText: 'Ok'
                                                                });
                                                                e.preventDefault();
                                                            }

                                                        }
                                                    });
                                                }
                                                else {
                                                    window.sessionStorage.clear();

                                                    $UTIL.saveUserData(resultData.id, resultData.token, resultData.prenume,resultData.expires);
                                                    if($UTIL.NOTIF_AMENZI){
                                                        $.ajax({
                                                            type: 'GET',
                                                            datatype : "application/json",
                                                            contentType: "application/json",
                                                            url: "/dmsws/notifAmenzi/getAmenzi",
                                                            success: function (data) {
                                                                if (data.result == 'OK') {

                                                                    if(data.Amenda!=null &&data.Amenda!=undefined &&data.Amenda.length>0){
                                                                        var html='';
                                                                        for(i=0;i<data.Amenda.length;i++){
                                                                            html=html + (i+1)+'. '+ data.Amenda[i].denumire +' suma: '+data.Amenda[i].suma +' din anul '+data.Amenda[i].an+'</br>';
                                                                        }

                                                                        Swal.fire({
                                                                            icon: 'info',
                                                                            html: 'Au fost indentificate in baza de date amenzile: </br>' + html,
                                                                            focusConfirm: false,
                                                                            confirmButtonText: 'Ok',
                                                                                onClose: () => {
                                                                                $UTIL.redirectToTarget($UTIL.WORDPRESS_URL);
                                                                        e.preventDefault();
                                                                            }
                                                                        });
                                                                    }else{
                                                                        $UTIL.redirectToTarget($UTIL.WORDPRESS_URL);
                                                                        e.preventDefault();
                                                                    }
                                                                }else{
                                                                    $UTIL.redirectToTarget($UTIL.WORDPRESS_URL);
                                                                    e.preventDefault();
                                                                }


                                                            },
                                                            error: function(err) {
                                                                $UTIL.redirectToTarget($UTIL.WORDPRESS_URL);
                                                                e.preventDefault();
                                                            }
                                                        });
                                                    }else{
                                                    $UTIL.redirectToTarget($UTIL.WORDPRESS_URL);
                                                    e.preventDefault();
                                                    }
                                                }
                                            },
                                            error: function(err) {
                                                $.ajax({
                                                    // type: 'POST',
                                                    // url: $UTIL.WS_URL + '/utilizator/' + $UTIL.LINK_WS_TOKEN + '/updateLoginFailed/' + username + '/',
                                                    type: 'POST',
                                                    datatype : "application/json",
                                                    contentType: "application/json",
                                                    url: "/dmsws/utilizator/updateLoginFailed/"+username+"/",
                                                    success: function (data) {
                                                        if (data.result == 'OK') {
                                                            if(data.locked==1){
                                                                Swal.fire({
                                                                    icon: 'error',
                                                                    html: "Contul a fost blocat din cauza depăşirii incercărilor permise!",
                                                                    focusConfirm: false,
                                                                    confirmButtonText: 'Ok'
                                                                });
                                                                e.preventDefault();
                                                            }else{
                                                                if(data.loginFailRemains){
                                                                    Swal.fire({
                                                                        icon: 'error',
                                                                        html: "Username sau parola greșită!<br/> Încercări rămase:"+data.loginFailRemains,
                                                                        focusConfirm: false,
                                                                        confirmButtonText: 'Ok'
                                                                    });
                                                                }else{
                                                                    Swal.fire({
                                                                        icon: 'error',
                                                                        html: "Username sau parola greșită!<br/>",
                                                                        focusConfirm: false,
                                                                        confirmButtonText: 'Ok'
                                                                    });
                                                                }
                                                                e.preventDefault();
                                                            }
                                                        }
                                                        else{
                                                            Swal.fire({
                                                                icon: 'error',
                                                                html: "Username sau parola greșită!<br/>",
                                                                focusConfirm: false,
                                                                confirmButtonText: 'Ok'
                                                            });
                                                            e.preventDefault();
                                                        }

                                                    }
                                                });
                                            }
                                        });
                                    }
                                }

                            });
                            e.preventDefault();
                        }
                    } else {
                        Swal.fire({
                            icon: 'error',
                            html: "Username sau parola greșită !",
                            focusConfirm: false,
                            confirmButtonText: 'Ok'
                        });
                        e.preventDefault();
                    }
                },
                error: function (data) {
                    Swal.fire({
                        icon: 'error',
                        html: "Username sau parola greșită!",
                        focusConfirm: false,
                        confirmButtonText: 'Ok'
                    });
                    e.preventDefault();
                }
            });
            //intorci cele 3 campuri: locked,activ,parola
            //faci aici verificare si daca e ok apelezi /login

        }
    }

    status.innerText = "";

} );

$(document).ready(function () {


            //afisare captcha - cu cheie preluata din application.properites: google.recaptcha.secretkey
            $UTIL.loadCaptchaKey().then(function (data) {
                if(data!=null && data!=''){
                    $("#container_captcha").html( '  <div class="g-recaptcha" data-sitekey="'+data+'"></div>');

                }

            });



});

