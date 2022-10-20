var SpeechRecognition = SpeechRecognition || webkitSpeechRecognition;
var SpeechGrammarList = SpeechGrammarList || webkitSpeechGrammarList;


var grammar = '#JSGF V1.0;'

var recognition = new SpeechRecognition();
var speechRecognitionList = new SpeechGrammarList();
speechRecognitionList.addFromString(grammar, 1);
recognition.grammars = speechRecognitionList;
recognition.lang = 'ro-RO';
recognition.interimResults = false;

var callbackC = null;


recognition.onresult = function(event) {
    var last = event.results.length - 1;
    var textResult = event.results[last][0].transcript;
    callbackC.$server.afterSpeachToText(textResult);
};

recognition.onspeechend = function() {
    recognition.stop();
};

recognition.onerror = function(event) {
    if (event.error == 'no-speech') {
        swal.error('Nu am indentificat niciun discurs !')
    }
    if (event.error == 'audio-capture') {
        swal.error('A aparatut o eroare la captarea discursului !')
    }
    if (event.error == 'not-allowed') {
        if (event.timeStamp - start_timestamp < 100) {
            swal.error('Acest site are accessul blocat la microfonul dumneavoastra!');
        } else {
            swal.error('Acest site are accessul interzis la microfonul dumneavoastra!');
        }
    }
}


function startMic(callback){
    callbackC = callback;
    recognition.start();
}
