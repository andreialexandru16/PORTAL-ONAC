var PAGE_NAME = "dateHourPicker.js";

var wsUrl = null;
var wsToken = null;
var lunaCalendar = null;
var anCalendar = null;
var idUserInitial = null;
var idUser = null;
var idUserTemp = null; //DACA E NULL  UTILIZEAZA VALOAREA DE PE PARAM DE SISTEM ID_USER_GENERIC
var listaInregistrari = {};
var daysToBusy = [];
var greenDays = [];
var idComponentGlobal = null;
var idModalGlobal = null;
var smartFormGlobal = null;
var idComponentPersAudientaGlobal = null;
var addrComponentGlobal = null;
var usersMap = new Map();


// var ps4GMapAddr = {
//     map: null,
//     newPoi: null,
//     markers: [],
//     popup: null,
//     smartForm: null,
//     addrComponent: null
// }, Popup;


// function datepickerInit (idCompoment, idModal, smartForm, addrComponent, eventDates) {
function datepickerInit (eventDates,eventDates2) {

    var PROC_NAME = "datepickerInit";
    $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);


    $(".datepicker").datepicker({

        monthNames: ['Ianuarie', 'Februarie', 'Martie', 'Aprilie', 'Mai', 'Iunie', 'Iulie', 'August', 'Septembrie', 'Octombrie', 'Noiembrie', 'Decembrie'],
        dayNamesMin: ['Du', 'Lu', 'Ma', 'Mi', 'Jo', 'Vi', 'Sa'],
        minDate: 0,
        beforeShow: function (input, inst) {
            var calendar = inst.dpDiv;
            setTimeout(function() {
                calendar.position({
                    my: 'right top',
                    at: 'right bottom',
                    collision: 'none',
                    of: input
                });
            }, 1);
        },
        beforeShowDay: function( date ) {

            // var string = jQuery.datepicker.formatDate('yy-mm-dd', date);
            // return [$.inArray(string, array) == -1];


            var highlight = eventDates[date];
            var highlight2 = eventDates2[date];
            if( highlight ) {
                return [true, "busyDay", ''];
            } else if ( highlight2 ){
                return [true, 'greenDay', ''];
            } else {
                return [true, '', ''];
            }
            // var string = jQuery.datepicker.formatDate('yy-mm-dd', date);
            // return [$.inArray(string, array) == -1];
            },
        onChangeMonthYear: function(year, month) {

            if(month<10){
                var luna = "0" + month;
            } else {
                var luna =  month;
            }

            if (anCalendar != year || lunaCalendar != luna) {
                anCalendar = year;
                lunaCalendar = luna;

                initUserCalendar(idUser, lunaCalendar, anCalendar, true);
            }
        },
        useCurrent: false

    });
    $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);

}


function datepickerReinit (eventDates,eventDates2) {

    var PROC_NAME = "datepickerInit";
    $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);

  //  $(".datepicker").datepicker('destroy');
  //  var dateToday = new Date();


    // var lunaNow = $("#calendar_audienta").val().split("/")[0];
    // var anNow = $("#calendar_audienta").val().split("/")[2];
    var ziNow = $("#calendar_audienta").val().split("/")[1];

    var date = new Date(anCalendar, lunaCalendar - 1 , 1);

    //
    // $("#calendar_audienta").datepicker().on('changeDate', function (e) {
    //     var pickedMonth = new Date(e.date).getMonth() + 1;
    //     var pickedYear = new Date(e.date).getFullYear();
    //     alert('CLICK');
    // });

    $(".datepicker").datepicker('destroy').datepicker({
        monthNames: ['Ianuarie', 'Februarie', 'Martie', 'Aprilie', 'Mai', 'Iunie', 'Iulie', 'August', 'Septembrie', 'Octombrie', 'Noiembrie', 'Decembrie'],
        dayNamesMin: ['Du', 'Lu', 'Ma', 'Mi', 'Jo', 'Vi', 'Sa'],
        minDate: 0,
        beforeShow: function (input, inst) {
            var calendar = inst.dpDiv;
            setTimeout(function() {
                calendar.position({
                    my: 'right top',
                    at: 'right bottom',
                    collision: 'none',
                    of: input
                });
            }, 1);
        },
        beforeShowDay: function( date ) {
            var highlight = eventDates[date];
            var highlight2 = eventDates2[date];
            if( highlight ) {
                return [true, "busyDay", ''];
            } else if ( highlight2 ){
                return [true, 'greenDay', ''];
            } else {
                return [true, '', ''];
            }
        },
        onChangeMonthYear: function(year, month) {

            if(month<10){
                var luna = "0" + month;
            } else {
                var luna =  month;
            }        if (anCalendar != year || lunaCalendar != luna) {
          anCalendar = year;
          lunaCalendar = luna;

          initUserCalendar(idUser, lunaCalendar, anCalendar, true);
        }

        }

    });
    // var date = new Date();
    // date.setDate(date.getDate() - 2);
    $(".datepicker").datepicker('setDate',date);
    $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);

}


function mandatoryFunctions () {


    var PROC_NAME = "mandatoryFunctions";
    $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);
    wsUrl = $UTIL.WS_URL;
    wsToken = $UTIL.LINK_WS_TOKEN;
    // lunaCalendar = $("#calendar_audienta").val().split("/")[0];
    // anCalendar = $("#calendar_audienta").val().split("/")[2];


    var today = new Date();
    lunaCalendar = today.getMonth() + 1;
    if(lunaCalendar < 10 && lunaCalendar > 0) {
        lunaCalendar = '0' +lunaCalendar.toString();
    }
    anCalendar = today.getFullYear();


    //--------initializam lov si calendar-----------


    prepareListUseriAuditie().then(function(){
        initUserCalendar(idUser,lunaCalendar,anCalendar,false);

    });


    $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);

}

function initUserCalendar (idUser,luna,an,reinit) {

    var PROC_NAME = "initCalendar";
    $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);

    //construim obiectul cu campurile necesare
    var object = buildObjectForInit(idUser,luna,an);
    var jReq = JSON.stringify(object);

    //apelam ws de generare calendar
    callGetCalendar(jReq,reinit);

    $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
}

function buildObjectForInit(idUser,luna,an) {

    var that = this;
    var PROC_NAME = "buildObjectForInit";
    $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);


    var calendar = {
        idUser:idUser,
        luna:luna,
        an:an
    };

    return calendar;
    $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
}

function callGetCalendar (jReq,reinit) {


    var that = this;
    var PROC_NAME = "PageManager.callGetCalendar";
    $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);

    var url = this.wsUrl + '/programare/' + this.wsToken + '/getCalendar';

    console.log("Ajax Call Start:" + url);

    $.ajax({
        url: url,
        method: 'POST',
        headers: $UTIL.mergeObjects($UTIL.corsHeaders, {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }),
        data: jReq,
        success: function (result) {
            if (result.result == 'OK') {
                listaInregistrari = result.audientaList;

                 renderOre($("#calendar_audienta").val().split("/")[1]);


                 lockDays(result.audientaList).then(function () {
                     if(reinit === false) {
                         datepickerInit(daysToBusy, greenDays);
                     } else {
                         datepickerReinit(daysToBusy, greenDays);
                     }
                });
            }

        },
        error: function (err) {
            console.log(err);
        }
    });
    $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);

}

function renderOre (zi) {

    var PROC_NAME = "renderOre";
    $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);
    var audiente = listaInregistrari;
    var dataPlusOra = null;
    var parametru = null;

    $(".ore_custom").html(' ');
    var container = $(".ore_custom");
    //container.html('<div>');
    //container.append("<ul>");
    for (i=0;i<audiente.length;i++){
        if(audiente[i].ziua.split("-")[2] == zi) {

            // container.append("<li id='li_" + audiente[i].id + "'>" +
            //     "<div>" +
            //     "<label class='custom-control-label'  id='label_"+ audiente[i].id +"'> <input type='checkbox' class='' id='" + audiente[i].id +"' />" + audiente[i].ora + "</label>" +
            //     "</div>" +
            //     "</li>" );

            dataPlusOra = audiente[i].ziua + ' ' + audiente[i].ora;
            // parametru = "'" + idComponentGlobal + "','" + idModalGlobal + "','" + dataPlusOra + "'"
            //
            // container.append("<li id='li_" + audiente[i].id + "' onClick='onHourClick(" + parametru + ")'>" +
            //     audiente[i].ora +
            //     "</li>" );


      /*      container.append("<li id='li_" + audiente[i].id + "'" + "onClick='onHourClick(\"" + idComponentGlobal + ",'" + idModalGlobal + "','" + dataPlusOra + "')\">" +
                audiente[i].ora +
                "</li>" );
*/
            container.append('<li id="li_' + audiente[i].id  +'"' + 'onClick="onHourClick(\''+ idComponentGlobal + '\' ,\'' + idModalGlobal + '\',\'' + dataPlusOra + '\',\'' + idComponentPersAudientaGlobal + '\',\'' + idUser + '\',\'' + usersMap.get(idUser) + '\')"> '+
                audiente[i].ora +
                '</li>' );

            if(audiente[i].aprobat == 1){
                var li = "li_" + audiente[i].id;
              //  $("#" + audiente[i].id).prop("checked", true);
                jQuery("#" + li).addClass("busy");
            }

        }
    }


    // container.append("</ul>");


    $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
}

function lockDays(audiente){

    var PROC_NAME = "lockDays";
    $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);

    greenDays= [];
    daysToBusy = [];

    var response = {};
    var response2 = {};
    var days = [];
    var fullDays = [];
    var exists;
    var defer = $.Deferred();

    for (i=0;i<audiente.length;i++){
        exists=false;


        for (j = 0; j < days.length; j++) {
            if (days[j] == audiente[i].ziua.split("-")[2]) {
                exists = true;
            }
        }

        if (exists == false) {
            days.push(audiente[i].ziua.split("-")[2]);
            fullDays.push(audiente[i].ziua);
        }

    }


    for (x=0;x<audiente.length;x++){
        if(audiente[x].aprobat == 0) {
            for (y = 0; y < days.length; y++) {
                if (days[y] == audiente[x].ziua.split("-")[2]) {
                    exists = true;
                    if (exists == true) {
                        var data = lunaCalendar + "/" + days[y] + "/" + anCalendar;
                        response2[ new Date( data )] = new Date( data );

                        days.splice(y,1);
                    }
                }
            }
        }

    }

    greenDays = response2;

    for (z=0;z<days.length;z++)
    {
        var data = lunaCalendar + "/" + days[z] + "/" + anCalendar;

        response[ new Date( data )] = new Date( data );
    }

    daysToBusy = response;
    $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);

    defer.resolve();
    return defer.promise();

    $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
}




function onChangeDate() {

    var PROC_NAME = "PageManager.onChangeDate";
    $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);


    // var lunaNow = $("#calendar_audienta").val().split("/")[0];
    // var anNow = $("#calendar_audienta").val().split("/")[2];
    var ziNow = $("#calendar_audienta").val().split("/")[1];
    // var dataNow = $("#calendar").val();

    // if(anNow != anCalendar ||lunaNow != lunaCalendar ) {
    //
    //     anCalendar = anNow;
    //     lunaCalendar = lunaNow;
    //     initUserCalendar(idUser,lunaCalendar,anCalendar,true);
    // }

    renderOre(ziNow);

    $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
}



function onChangePersoana() {

    var PROC_NAME = "PageManager.onChangePersoana";
    $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);

    idUser = $('#user_audienta').val();

    // var today = new Date();
    // lunaCalendar = today.getMonth() + 1;
    // if(lunaCalendar < 10 && lunaCalendar > 0) {
    //     lunaCalendar = '0' +lunaCalendar.toString();
    // }
    // anCalendar = today.getFullYear();

    // var lunaNow = $("#calendar_audienta").val().split("/")[0];
    // var anNow = $("#calendar_audienta").val().split("/")[2];
    // var ziNow = $("#calendar_audienta").val().split("/")[1];


    initUserCalendar(idUser,lunaCalendar,anCalendar,true);

    $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
}

// function removeOptions(selectElement) {
//     var i, L = selectElement.options.length - 1;
//     for(i = L; i >= 0; i--) {
//         selectElement.remove(i);
//     }
// }

function prepareListUseriAuditie () {

    var that = this;
    var defer = $.Deferred();
    $('#user_audienta').empty();
    var PROC_NAME = "PageManager.prepareListUseriAuditie";
    //$('#user_audienta').val('');
   // removeOptions($('#user_audienta'));
    var url = this.wsUrl + '/programare/' + this.wsToken + '/getUseriAudienta';
    $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);
    //apelare serviciu web
    $.ajax({
        url: url,
        method: 'GET',
        headers: $UTIL.mergeObjects($UTIL.corsHeaders, {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }),
        success: function (result) {
            if (result.result == 'OK') {
                var useri = result.listaUtilizator;
                //parcurgere lista useri
                for (var i = 0; i < useri.length; i++) {
                    usersMap.set(useri[i].id,useri[i].nume);
                    //incarcare judet in lista
                    $('#user_audienta').append('<option value="' + useri[i].id + '">' + useri[i].nume + '</option>');
                }


                if((idUser == null || idUser == '') && useri[0].id != null && useri[0].id != '' && typeof(useri[0].id) != undefined) {

                    idUser = useri[0].id;
                }


                // $('#calendar').val(PageManager.lastDate);
                $('#user_audienta').val(idUser).trigger("change");

                defer.resolve();

            }

        },
        error: function (err) {
            console.log(err);
        }

    });

    $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    return defer.promise();



}

function onHourClick(idComponent, idModal, dateHour, idComponentLov, idLov, valLov){
    var PROC_NAME = "onHourClick";
    $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);
   // smartFormGlobal.$server.saveAdresa(idComponent, dateHour);
     smartFormGlobal.$server.saveAudienta(idComponent, dateHour, idComponentLov, idLov,valLov);
    //$("#"+idComponent).val(dateHour);
    $("#"+idModal).modal("hide");

    $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
}

function jsInit (idComponent, idModal, smartForm, addrComponent, idComponentPersAudienta) {

    console.log('------------jsiNIT 1 ------------');
    $("#" + idModal).on('shown.bs.modal', function(){
        console.log('------------jsiNIT 2------------');
        var eventDates = {};
        idComponentPersAudientaGlobal = idComponentPersAudienta;
        idComponentGlobal = idComponent;
        idModalGlobal = idModal;
        smartFormGlobal = smartForm;
        addrComponentGlobal = addrComponent;
        mandatoryFunctions();
        //datepickerInit(eventDates);
    });
}