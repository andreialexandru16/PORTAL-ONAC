
var PAGE_NAME = "administrare-calendar.js";

var PageManager = {
    wsUrl: null,
    wsToken: null,
    templates: {},
    listaInregistrari: {},
    nrPagina: 0,
    nrMaxPages: 4,
    numPerPage: 5,
    idRegistru: 5,
    idUser: null,
    lastDate: null,
    lunaCalendar: null,
    anCalendar: null,
    daysToBusy: [],
    init: function () {

        var PROC_NAME = "PageManager.init";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);

        //-------------- se apeleaza obligatoriu la initializarea paginii---------------------
        this.mandatoryFunctions();
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);


    },

    prepareListUseriAuditie: function () {

        var that = this;
        var defer = $.Deferred();

        var PROC_NAME = "PageManager.prepareListUseriAuditie";
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
                        //incarcare judet in lista
                        $('#user_audienta').append('<option value="' + useri[i].id + '">' + useri[i].nume + '</option>');
                    }


                    if((PageManager.idUser == null || PageManager.idUser == '') && useri[0].id != null && useri[0].id != '' && typeof(useri[0].id) != undefined) {

                        PageManager.idUser = useri[0].id;
                    }


                    $('#user_audienta').val(PageManager.idUser).trigger("change");

                    defer.resolve();

                }


            },
            error: function (err) {
                console.log(err);
            }

    });

        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
        return defer.promise();



    },

    generareCalendar: function () {
        var PROC_NAME = "PageManager.afiseazaRezultateCautare";

        //construim obiectul cu campurile necesare
        var object = PageManager.buildObject();
        var jReq = JSON.stringify(object);

        //apelam ws de generare calendar
        PageManager.callWs(jReq);

        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },

    deleteCalendar: function () {
        var PROC_NAME = "PageManager.deleteCalendar";

        //construim obiectul cu campurile necesare
        var object = PageManager.buildObject();
        var jReq = JSON.stringify(object);

        //apelam ws de generare calendar
        PageManager.callWsDelete(jReq);

        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },


    buildObject: function () {

        var that = this;
        var PROC_NAME = "PageManager.prepareGenerareCalendar";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);

        var idUser = $("#user_audienta").val();
        var deLa = $("#data_start").val();
        var panaLa = $("#data_end").val();
        var oraStart = $("#ora_start").val();
        var oraEnd = $("#ora_end").val();
        var pas = $("#pas").val();

        if (deLa == '' || deLa == null || panaLa == '' || panaLa == null || idUser == '' || idUser == null  || oraStart == '' || oraStart == null  || oraEnd == '' || oraEnd == null  || pas == '' || pas == null){
            Swal.fire({
                icon: 'error',
                html: "Completati campurile obligatorii pentru a genera calendarul!",
                focusConfirm: false,
                confirmButtonText: 'Ok'
            });
            return;
        }

        var calendar = {
            idUser:idUser,
            deLa: deLa,
            panaLa: panaLa,
            oraStart: oraStart,
            oraEnd: oraEnd,
            pas: pas
        };

        return calendar;
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);


    },

    callUpdateWs: function (object) {

        var that = this;
        var PROC_NAME = "PageManager.callUpdateWs";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);

        var url = this.wsUrl + '/programare/' + this.wsToken + '/updateCalendar';
        var jReq = JSON.stringify(object);


        console.log("Ajax Call Start:" + url);

        $.ajax({
            url: url,
            method: 'POST',
            data: jReq,
            contentType: 'application/json',
            accept: 'application/json',
            success: function (data) {
                if (data.result == 'OK') {
                    window.location.reload();
                }



            },
            error: function (err) {
                console.log(err);
            }
        });
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },

    callWs: function (jReq) {

        var that = this;
        var PROC_NAME = "PageManager.callWs";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);

        var url = this.wsUrl + '/programare/' + this.wsToken + '/genCalendar';


console.log("Ajax Call Start:" + url);

$.ajax({
    url: url,
    method: 'POST',
    headers: $UTIL.mergeObjects($UTIL.corsHeaders, {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
    }),
    data: jReq,
    success: function (data) {
        if (data.result == 'OK') {
            Swal.fire({
                icon: 'info',
                html: "Calendarul a fost generat cu succes.",
                focusConfirm: false,
                confirmButtonText: 'Ok',
                    onClose: () => {
                    window.location.reload();
        }
            });

        }
        else{
            Swal.fire({
                icon: 'error',
                html: "Ne pare rau! Calendarul nu a putut fi generat. Verificati formatul campurilor orare.",
                focusConfirm: false,
                confirmButtonText: 'Ok'
            });
        }

    },
    error: function (err) {
        console.log(err);
    }
});
$UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
},

    callWsDelete: function (jReq) {

        var that = this;
        var PROC_NAME = "PageManager.callWsDelete";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);

        var url = this.wsUrl + '/programare/' + this.wsToken + '/deleteCalendar';


        console.log("Ajax Call Start:" + url);

        $.ajax({
            url: url,
            method: 'POST',
            headers: $UTIL.mergeObjects($UTIL.corsHeaders, {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }),
            data: jReq,
            success: function (data) {
                if (data.result == 'OK') {

                    window.location.reload();
                }

            },
            error: function (err) {
                console.log(err);
            }
        });
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },

    callGetCalendar: function (jReq) {

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
                        PageManager.listaInregistrari = result.audientaList;

                        PageManager.renderOre($("#calendar").val().split("/")[1]);

                        PageManager.lockDays(result.audientaList).then(function () {
                            PageManager.beforeShowDay();
                        });
                    }

            },
            error: function (err) {
                console.log(err);
            }
        });
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },

    beforeShowDay: function( date ) {
        var PROC_NAME = "PageManager.beforeShowDay";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);
        var highlight = PageManager.daysToBusy[date];
        if( highlight ) {
            return [true, "busyDay", ''];
        } else {
            return [true, '', ''];
        }
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);

    },

    buildObjectForInit: function (idUser,luna,an) {

        var that = this;
        var PROC_NAME = "PageManager.buildObjectForInit";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);


       // var calendar = $("#calendar").val();


        var calendar = {
            idUser:idUser,
            luna:luna,
            an:an
        };

        return calendar;
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);


    },

    updateCalendar: function () {

        var that = this;
        var PROC_NAME = "PageManager.updateCalendar";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);
        var response = [];

        var dayToUpdate = $("#calendar").val().split("/")[1]

        var audiente = PageManager.listaInregistrari;
        for (i=0;i<audiente.length;i++){
            if(audiente[i].ziua.split("-")[2] == dayToUpdate) {
                if( $("#" + audiente[i].id).prop('checked') == true){
                    audiente[i].aprobat=1;
                } else if ( $("#" + audiente[i].id).prop('checked') == false) {
                    audiente[i].aprobat=0;
                }

                response.push(audiente[i]);

            }

        }

        PageManager.callUpdateWs(response);
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);


    },


    mandatoryFunctions: function () {

        var PROC_NAME = "PageManager.mandatoryFunctions";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);
        //-------------- preluam WS_URL si WS_TOKEN din documentaPortal-util.js ---------------------
        PageManager.wsUrl = $UTIL.WS_URL;
        PageManager.wsToken = $UTIL.LINK_WS_TOKEN;
        PageManager.idRegistru = $UTIL.idRegistruProiecteHcj;


        //--------initializam listele de valori-----------

        PageManager.prepareListUseriAuditie();

        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);

    },



        busyDays: function () {

            var PROC_NAME = "PageManager.busyDays";
            $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);

            var eventDates = {};
            eventDates[ new Date( '03/16/2021' )] = new Date( '03/16/2021' );
            eventDates[ new Date( '03/10/2021' )] = new Date( '03/10/2021' );

            $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    return eventDates;
        },

    prepareToDelete: function () {

        var that = this;
        var deLa = $("#data_start").val();
        var panaLa = $("#data_end").val();
        var idUser = $("#user_audienta").val();

        var url = this.wsUrl + '/programare/' + this.wsToken + '/deleteCalendar';

        if (deLa == '' || deLa == null || panaLa == '' || panaLa == null || idUser == '' || idUser == null ){
            Swal.fire({
                icon: 'error',
                html: "Completati campurile obligatorii pentru a sterge calendarul!",
                focusConfirm: false,
                confirmButtonText: 'Ok'
            });
            return;
        }

        Swal.fire({
                html:"Sunteti pe cale sa stergeti calendarul de la " + deLa + " pana la " + panaLa + " impreuna cu rezervarile aferente. Sunteti sigur ca doriti sa stergeti?",
                onOpen: function() {
                },

                inputAttributes: {
                    autocapitalize: 'off'
                },
                showCancelButton: true,
                confirmButtonText: 'Sterge',
                showLoaderOnConfirm: true,
                preConfirm: () => {
                return new Promise(function(resolve, reject) {


                   var calendar = {
                       deLa: deLa,
                       panaLa: panaLa,
                       idUser: idUser
                   };
                    var jReq = JSON.stringify(calendar);


                //    var url = this.wsUrl + '/programare/' + this.wsToken + '/deleteCalendar';


                    console.log("Ajax Call Start:" + url);

                    $.ajax({
                        url: url,
                        method: 'POST',
                        headers: $UTIL.mergeObjects($UTIL.corsHeaders, {
                            'Accept': 'application/json',
                            'Content-Type': 'application/json'
                        }),
                        data: jReq,
                        success: function (data) {
                            if (data.result == 'OK') {

                                window.location.reload();
                            }

                        },
                        error: function (err) {
                            console.log(err);
                        }
                    });

                });
    },
        allowOutsideClick: () => !Swal.isLoading()
    }).then((result) => {
            if (result.value) {
            Swal.fire({
                    title: 'Calendarul a fost sters cu succes!',
                    onClose: () => {
                    window.location.reload();
        }
        })

        }
    });
    }

    };


$(document).ready(function () {
    PageManager.init();
});




