var captcha;
var alphabets = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz";
console.log(alphabets.length);
var status = document.getElementById('status');


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

}

function check () {
// console.log(status)
    var userValue = document.getElementById("entered-captcha").value;
    console.log(captcha);
    console.log(userValue);
    if(userValue == captcha){
        $('#status').val('Corect!');
        $('#captchaModal').modal('hide');

    }else{
        $('#status').val('Incorect!');
        document.getElementById("entered-captcha").value = '';
    }
}