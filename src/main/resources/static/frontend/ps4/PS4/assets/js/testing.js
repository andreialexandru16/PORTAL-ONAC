jQuery(document).ready(function ($) {
    "user strict";
    /* Autocomplete example */
    var phrases = [
        'Autorizație de construcție / desființare', 
        'Aviz de săpătură', 
        'Aviz de urbanism', 
        'Certificat de urbanism', 
        'Eliberare de adeverință privind datele din registrul agricol'];

    $(".autocomplete").autocomplete({
        source: phrases
    });
    /* Sweet Alert */
    function validateEmail(email) {
        var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        return re.test(String(email).toLowerCase());
    }
    function validatePwd(pwd) {
        let checkPwd = false;
        if ( pwd === "123456" ) {
            checkPwd = true;
        }
        return checkPwd;
    }
    /* Authentication Form */
    $("#login_form").on("submit", function ( event ) {
        event.preventDefault();

        let errorString = "<ul class='text-left'>";
        let currentEmail = $(this).find("#username").val();
        let currentPwd = $(this).find("#password").val();

        if ( validateEmail(currentEmail) === false ) {
            errorString += "<li class='text-danger'>Adresa de email este invalidă.</li>";
        } else {
            if ( currentEmail !== "admin@admin.com" ) {
                errorString += "<li class='text-danger'>Nu există niciun utilizator cu această adresă de email.</li>";
            }
        }
        if ( validatePwd(currentPwd) == false ) {
            errorString += "<li class='text-danger'>Parolă incorectă.</li>";
        }

        errorString += "</ul>";

        if ( errorString !== "<ul class='text-left'></ul>" ) {
            Swal.fire({
                icon: 'error',
                html: errorString,
                focusConfirm: false,
                confirmButtonText: 'Ok',
            });
        } else {
            Swal.fire({
                icon: 'success',
                html: "Userul și parola au fost acceptate.",
                focusConfirm: false,
                confirmButtonText: 'Ok',
            });
        }
    });
    /* Registration Form -> moved to bithat-inregistrare.js for customization */
    // $("#register_pf").on("submit", function (event) {
    //     event.preventDefault();

    //     let errorString = "<ul class='text-left'>";
    //     /* Check for required fields */
    //     $(this).find(".required").each(function () {
    //         if ( $(this).val() === "" ) {
    //             errorString += "<li class='text-danger'>Câmpul: <strong>" + $(this).parent().find("label").text()  + "</strong> este obligatoriu.</li>";
    //         }
    //     });
    //     /* Check for TOS */
    //     if ( $("#tos:checked").length == 0 ) {
    //         errorString += "<li class='text-danger'>Vă rugăm să citiți și să bifați termenii și condițiile.</li>";
    //     }
    //     /* Passwords */
    //     if ( $("#pwd1").val() !== $("#pwd2").val() ) {
    //         errorString += "<li class='text-danger'>Parolele nu se potrivesc.</li>";
    //     }
    //     /* Email */
    //     if ( validateEmail($("#email").val()) == false ) {
    //         errorString += "<li class='text-danger'>Adresa de email este invalidă.</li>";
    //     }
    //     errorString += "</ul>";
    //     if ( errorString !== "<ul class='text-left'></ul>" ) {
    //         Swal.fire({
    //             icon: 'error',
    //             html: errorString,
    //             focusConfirm: false,
    //             confirmButtonText: 'Ok',
    //         });
    //     } else {
    //         Swal.fire({
    //             icon: 'success',
    //             html: "Formularul a fost completat cu succes.",
    //             focusConfirm: false,
    //             confirmButtonText: 'Ok',
    //         });
    //     }
    // });
});