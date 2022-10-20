

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

        if(window.sessionStorage && window.sessionStorage.getItem("lastUserId")){
            PageManager.idUser=window.sessionStorage.getItem("lastUserId");
        }

        // if(window.sessionStorage && window.sessionStorage.getItem("lastCalendarDate")){
        //     PageManager.lastDate=window.sessionStorage.getItem("lastCalendarDate");
        // }

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


                   // $('#calendar').val(PageManager.lastDate);
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

    onChangeFunction: function () {
        var PROC_NAME = "PageManager.onChangeFunction";

        var idUserNow = $("#user_audienta").val();
        var lunaNow = $("#calendar").val().split("/")[0];
        var anNow = $("#calendar").val().split("/")[2];
        var ziNow = $("#calendar").val().split("/")[1];
       // var dataNow = $("#calendar").val();

        window.sessionStorage.setItem("lastUserId", idUserNow);
       // window.sessionStorage.setItem("lastCalendarDate", dataNow);

        if(anNow != PageManager.anCalendar ||lunaNow != PageManager.lunaCalendar || idUserNow != PageManager.idUser) {
            PageManager.anCalendar = anNow;
            PageManager.lunaCalendar = lunaNow;
            PageManager.idUser = idUserNow;
            PageManager.initCalendar(PageManager.idUser,PageManager.lunaCalendar,PageManager.anCalendar);
        }

        PageManager.renderOre(ziNow);




        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },

    renderOre: function (zi) {

        var PROC_NAME = "PageManager.renderOre";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);
        var audiente = PageManager.listaInregistrari;
        $(".ore_custom").html(' ');
        var container = $(".ore_custom");
        //container.html('<div>');
        //container.append("<ul>");
        for (i=0;i<audiente.length;i++){
            if(audiente[i].ziua.split("-")[2] == zi) {


                // container.append("<li>" +
                //     "<div>" +
                //     "<label class='custom-control-label'  id='"+ audiente[i].id +"'> <input type='checkbox' class='' id='input_" + audiente[i].id +"' />" + audiente[i].ora + "</label>" +
                //     "</div>" +
                //     "</li>" );

                container.append("<li id='li_" + audiente[i].id + "'>" +
                    "<div>" +
                    "<label class='custom-control-label'  id='label_"+ audiente[i].id +"'> <input type='checkbox' class='' id='" + audiente[i].id +"' />" + audiente[i].ora + "</label>" +
                    "</div>" +
                    "</li>" );

                // container.append("<li>" +
                //     "<div class='custom-control custom-checkbox'>" +
                //     "<input type='checkbox' class='custom-control-input' id='input_" + audiente[i].id +"' />" +
                //     "<label class='custom-control-label'  id='"+ audiente[i].id +"'>" + audiente[i].ora + "</label>" +
                //     "</div>" +
                //     "</li>" );

                // container.append("<li id='li_" + audiente[i].id + "'>" +
                //     audiente[i].ora +
                //     "</li>" );

                    if(audiente[i].aprobat == 1){
                        var li = "li_" + audiente[i].id;
                        $("#" + audiente[i].id).prop("checked", true);
                        jQuery("#" + li).addClass("busyDay");
                    }

            }
        }


       // container.append("</ul>");


        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },


    initCalendar: function (idUser,luna,an) {

        var PROC_NAME = "PageManager.initCalendar";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);

        //construim obiectul cu campurile necesare
        var object = PageManager.buildObjectForInit(idUser,luna,an);
        var jReq = JSON.stringify(object);

        //apelam ws de generare calendar
        PageManager.callGetCalendar(jReq);

        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },

    lockDays: function (audiente) {

        var PROC_NAME = "PageManager.lockDays";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);

        var response = {};
        var days = [];
        var exists;
        var defer = $.Deferred();

        //var audiente = PageManager.listaInregistrari;
        for (i=0;i<audiente.length;i++){
            exists=false;


                for (j = 0; j < days.length; j++) {
                    if (days[j] == audiente[i].ziua.split("-")[2]) {
                        exists = true;
                    }
                }

                    if (exists == false) {
                        days.push(audiente[i].ziua.split("-")[2]);
                    }

        }

        for (x=0;x<audiente.length;x++){
        if(audiente[x].aprobat == 0) {
            for (y = 0; y < days.length; y++) {
                if (days[y] == audiente[x].ziua.split("-")[2]) {
                    exists = true;
                    if (exists == true) {
                        days.splice(y,1);
                    }
                }
            }
        }

        }

        for (z=0;z<days.length;z++)
        {
            var data = PageManager.lunaCalendar + "/" + days[z] + "/" + PageManager.anCalendar;

            response[ new Date( data )] = new Date( data );
        }

        PageManager.daysToBusy = response;
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);

        defer.resolve();
        return defer.promise();

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
        PageManager.lunaCalendar = $("#calendar").val().split("/")[0];
        PageManager.anCalendar = $("#calendar").val().split("/")[2];

        //--------initializam listele de valori-----------

        PageManager.prepareListUseriAuditie().then(function(){
            PageManager.initCalendar(PageManager.idUser,PageManager.lunaCalendar,PageManager.anCalendar);

        });

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
        }

    };


$(document).ready(function () {
    PageManager.init();
});




