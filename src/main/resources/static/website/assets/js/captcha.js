var captcha;
var alphabets = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz";
console.log(alphabets.length);
var status = document.getElementById('status');
status.innerText = "Captcha Generator";

 function generate() {
// console.log(status)
    var first = alphabets[Math.floor(Math.random() * alphabets.length)];
    var second = Math.floor(Math.random() * 10);
    var third = Math.floor(Math.random() * 10);
    var fourth = alphabets[Math.floor(Math.random() * alphabets.length)];
    var fifth = alphabets[Math.floor(Math.random() * alphabets.length)];
    var sixth = Math.floor(Math.random() * 10);
    captcha = first.toString()+second.toString()+third.toString()+fourth.toString()+fifth.toString()+sixth.toString();
    console.log(captcha);
    document.getElementById('generated-captcha').value = captcha;
    document.getElementById("entered-captcha").value = '';
     $("#status").val("");
}

function check () {
// console.log(status)
    var userValue = document.getElementById("entered-captcha").value;

    if(userValue == captcha){
        $("#status").val("Correct!!");
        $("#checkCaptcha").prop('checked', true);
        $("#captchaModal").modal('hide');
    }else{
        $("#status").val("Try again!!!");
        document.getElementById("entered-captcha").value = '';
    }
}

function captchaShow(){
     Swal.fire({
         html:'<div><input type="text" readonly id="status"></div>'+
         '<div >'+
         '<input type="text" readonly id="generated-captcha">'+
             '</div>'+
             '<div>'+
             '<input type="text" id="entered-captcha" placeholder="Enter the captcha..">'+
         '</div>'+
         '<button type="button" onclick="check()">'+
         'Verifica'+
         '</button>'+
         '<button type="button" onclick="generate()" id="gen">'+
         'Genereaza din nou'+
         '</button>'


     });
     generate();

}