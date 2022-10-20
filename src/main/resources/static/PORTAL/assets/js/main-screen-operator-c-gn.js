var PAGE_NAME = "main-screen-operator-c-gn.js";

var FileManager = {
    // pre-compiled mustache templates
    templates: {},

    id_perioada_contabila: null,
    id_tert: null,
    wsAndUserInfo: {},
    formulareData: {},
    perioadaData: {},
    isAdmin: false,

    userInfo: null,

    /*
     Initialization function.
     */
    init: function () {
        var that = this;

        // CHECK IF LOGGED IN
        $.ajax({
            type: 'GET',
            url: '/dmsws/utilizator/userIsLogged',
            success: function (data) {

                var isLogged=data!=null && data== 'true';
                if(isLogged){
                    that.mandatoryFunctions().then(
                        function(){
                            that.checkRightData();
                            that.loadPerioadaData().then(
                                function(){
                                    that.loadFormulareData()
                                }
                            );
                        }
                    );
                }
                else{
                    Swal.fire({
                        position:'top',
                        icon: "info",
                        html: "Vă rugăm să vă autentificați.",
                        focusConfirm: false,
                        confirmButtonText: "Ok",
                        onClose: function(){
                                window.top.location.href=$UTIL.WORDPRESS_URL+ '/';
                        }
                    });
                }
            }
        });
    },

    compileAllTemplates: function () {
        this.templates['tmpl_formulare_list'] = $('#tmpl_formulare_list').html();
        this.templates['tmpl_perioada'] = $('#tmpl_perioada').html();
        this.templates['tmpl_tert_raportare'] = $('#tmpl_tert_raportare').html();

        // parseaza toate template-urile
        $.each(this.templates, function (index, template) {
            Mustache.parse(template);
        });
    },
    checkRightData: function () {
        var that = this;

        var defer = $.Deferred();
        $.ajax({
            url: '/dmsws/raportare/checkUserGroupMemberByCode?&codGrup=ADMIN_CONTRIBUTII',
            success: function (data) {
                if (data.result == 'OK') {

                    if(data.info=='true'){
                        that.isAdmin=true;
                        $("#container_select_operator").css('display','block');
                        var url =  '/dmsws/raportare/getTertRaportareListShort';

                        var saved_last_tert_contrib = sessionStorage.getItem('saved_last_tert_contrib');
                        if (typeof saved_last_tert_contrib !== 'undefined' && saved_last_tert_contrib !== null){
                            url+="?&saved_last_tert_id="+ saved_last_tert_contrib;
                        }
                        $.ajax({
                            url: url,
                            success: function (data) {
                                if (data.result == 'OK') {

                                    that.renderTertRaportareData(data);

                                    defer.resolve();
                                }
                                else {
                                    Swal.fire({
                                        position: 'top',
                                        icon: 'error',
                                        html: "A aparut o eroare!<br/>",
                                        focusConfirm: false,
                                        confirmButtonText: 'Ok'
                                    });

                                    defer.reject("A aparut o eroare");
                                }

                            }
                        });

                    }

                    defer.resolve();
                }
                else {
                    Swal.fire({
                        position: 'top',
                        icon: 'error',
                        html: "A aparut o eroare!<br/>",
                        focusConfirm: false,
                        confirmButtonText: 'Ok'
                    });

                    defer.reject("A aparut o eroare");
                }

            }
        });


        return defer.promise();
    },
    reloadListaTertAfterStopTyping: function(time){


        var that=this;
        //setup before functions
        var typingTimer;                //timer identifier
        var doneTypingInterval = time;  //time in ms, 5 second for example

//user is "finished typing," do something
        function doneTyping () {

            var $text = $('#container_select_operator input').val();


            function write_text(input) {
                input.val($text);
            }

            function ajax_load(input) {
                var url = '/dmsws/raportare/getTertRaportareListShort?&searchStr='+ $text;
                $.ajax({
                    url:url,
                    success: function (data) {
                        if (data.result == 'OK') {

                            that.renderTertRaportareDataSearch(data,input,$text);
                        }
                        else {
                            Swal.fire({
                                position: 'top',
                                icon: 'error',
                                html: "A aparut o eroare!<br/>",
                                focusConfirm: false,
                                confirmButtonText: 'Ok'
                            });


                        }

                    }
                });
            }

            ajax_load($(this));
        }
        //on keydown, clear the countdown
        $('#container_select_operator').on('keydown','input', function(){
            clearTimeout(typingTimer);

        });
        $('#container_select_operator').on('keyup','input', function(){
            clearTimeout(typingTimer);
            typingTimer = setTimeout(doneTyping, doneTypingInterval);

        });
        //on keydown, clear the countdown
        $('#select_tert').on('change',  function(){
            clearTimeout(typingTimer);

        });

    },
    renderTemplateNonAsync: function (manager, templateName, data) {
        var that = this;
        var str = null;

        try {
            str = Mustache.render(manager.templates[templateName], data);
        } catch (e) {
            that.alert(e,"ERROR");
        }

        return str;
    },

    mandatoryFunctions: function () {
        var that=this;
        var defer = $.Deferred();
        var PROC_NAME = "FileManager.mandatoryFunctions";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);

        // luam info dmsws si user
        that.getWsAndUserInfo().then(
            function(){
                //-------------- initializam template-uri mustache ---------------------
                that.compileAllTemplates();
                var params = new URLSearchParams(window.location.href);

                if (params.has('idPerioada')){
                    that.id_perioada_contabila = params.get('idPerioada');
                }
                if (params.has('idTert')){
                    FileManager.id_tert = params.get('idTert');
                }
                FileManager.reloadListaTertAfterStopTyping(2000);
                $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
                defer.resolve();
            },
            function(){
                defer.reject();
            }
        );


        return defer.promise();
    },

    getWsAndUserInfo: function(){
        var that = this;
        var defer = $.Deferred();

        $.ajax({
            url: '/dmsws/utilizator/getWsAndUserInfo',
            success: function (data) {
                that.wsAndUserInfo = data;
                defer.resolve();
            },
            error: function (data) {
                Swal.fire({
                    position: 'top',
                    icon: 'error',
                    html: "A aparut o eroare!<br/>",
                    focusConfirm: false,
                    confirmButtonText: 'Ok'
                });
                defer.reject();
            }
        });

        return defer.promise();
    },

    loadPerioadaData: function () {
        var that = this;

        var req = {
            "zona": "CONTRIB_GN"
        };

        var jReq = JSON.stringify(req);

        var defer = $.Deferred();
        $.ajax({
            url: that.wsAndUserInfo.wsUrl + '/anre_contributii_gn/' + that.wsAndUserInfo.userToken.token + '/getPerioadaContabilaByZona',
            type: 'post',
            data: jReq,
            headers: ObjTools.mergeObjects(AjaxTools.corsHeaders, {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }),
            success: function (data) {
                if (data.status == 'OK') {
                    that.perioadaData = data;
                    that.renderPerioadaData(data);
                    defer.resolve();
                } else {
                    Swal.fire({
                        position: 'top',
                        icon: 'error',
                        html: "A aparut o eroare!<br/>",
                        focusConfirm: false,
                        confirmButtonText: 'Ok'
                    });

                    defer.reject("A aparut o eroare");
                }
            },
            error: function(data){
                Swal.fire({
                    position: 'top',
                    icon: 'error',
                    html: "A aparut o eroare!<br/>",
                    focusConfirm: false,
                    confirmButtonText: 'Ok'
                });

                defer.reject("A aparut o eroare");
            }
        });


        return defer.promise();
    },
    renderTertRaportareData: function (data) {
        var that = this;

        var html = that.renderTemplateNonAsync(that, "tmpl_tert_raportare", data);
        $("#select_tert").html(html);
        var saved_last_tert_contrib = sessionStorage.getItem('saved_last_tert_contrib');
        if (typeof saved_last_tert_contrib !== 'undefined' && saved_last_tert_contrib !== null){
            $("#select_tert").val(saved_last_tert_contrib);
            // $("#select_tert").trigger("chosen:updated");
            FileManager.triggerClickBtn('#btn_actualizare')
        }
        $("#select_tert").chosen({
            no_results_text: "Vom reîncărca lista conform textului căutat - momentan nu a fost găsit : "
        });

        $("#select_tert").change(function(){
            sessionStorage.setItem('saved_last_tert_contrib', $("#select_tert").val());
        })
        $('#resize_iframe', window.parent.document).trigger('click');
    },
    renderTertRaportareDataSearch: function (data,input,text) {
        var that = this;

        var html = that.renderTemplateNonAsync(that, "tmpl_tert_raportare", data);
        $("#select_tert").html(html);
        $("#select_tert").trigger("chosen:updated");
        $("#select_tert").chosen({
            no_results_text: "Vom reîncărca lista conform textului căutat - momentan nu a fost găsit : "
        });
        $("#container_select_operator input").val(text)
        $('#resize_iframe', window.parent.document).trigger('click');
    },
    renderPerioadaData: function (data) {
        var that = this;

        var html = that.renderTemplateNonAsync(that, "tmpl_perioada", data);
        $("#container_perioada").html(html);
        $("#select_perioada").chosen({
            no_results_text: "Nu a fost găsit : "
        });
        if(that.id_perioada_contabila==null  || that.id_perioada_contabila==''){
            that.id_perioada_contabila = $("#select_perioada").val();
        }else{
            $("#select_perioada").val(that.id_perioada_contabila).change();
            $('#select_perioada').trigger("chosen:updated");
        }

        var descriere = $('option:selected', $("#select_perioada")).attr('descriere');
        if(descriere==null || descriere==''){
            $("#temei_perioada").html("");

        }else{
            $("#temei_perioada").html("Temei legal: "+descriere);

        }

        that.renderStareLuna();
        $('#resize_iframe', window.parent.document).trigger('click');
    },

    renderStareLuna: function () {
        var that = this;
        $("#container_stare_luna").empty();

        $(this.perioadaData.perioadaList).each(function(index, obj) {
            if (obj.id == that.id_perioada_contabila){
                //check luna inchisa/deschisa


                if (typeof obj.dataLimitaDepunereStr !== 'undefined' && obj.dataLimitaDepunereStr !== null){
                    $("#container_stare_data_limita").html('');
                    $("#container_stare_data_limita").append(" <div class='message-box error'>" +
                        "<p>Data limita depunere: <span style=\"font-weight:bold\">"+obj.dataLimitaDepunereStr+"</style></p>" +
                        "</div>");
                } else {
                    $("#container_stare_data_limita").html('');
                }
            }
        });
    },

    triggerClickBtnPerioada: function (selectorBtn) {
        var that = this;
        $(selectorBtn).click();
        that.id_perioada_contabila = $("#select_perioada").val();
        var descriere = $('option:selected', $("#select_perioada")).attr('descriere');
        if(descriere==null || descriere==''){
            $("#temei_perioada").html("");

        }else{
            $("#temei_perioada").html("Temei legal: "+descriere);

        }
        that.renderStareLuna();
    },
    triggerClickBtn: function (selectorBtn) {
        var that=this;
         var idTert= $("#select_tert").val();
        if(idTert!=null && typeof idTert !='undefined'){
            that.wsAndUserInfo.tert.id=idTert;

        }
        $(selectorBtn).click();

    },
    loadFormulareData: function(){
        var that = this;

        that.id_perioada_contabila = $("#select_perioada").val();

        var req = {
            "id_tert": that.wsAndUserInfo.tert.id,
            "id_perioada_contabila": that.id_perioada_contabila,
            "zona": "CONTRIB_GN"
        };

        var jReq = JSON.stringify(req);

        var defer = $.Deferred();
        $.ajax({
            url: that.wsAndUserInfo.wsUrl + '/anre_contributii_gn/' + that.wsAndUserInfo.userToken.token + '/getFormulareData',
            type: 'post',
            data: jReq,
            headers: ObjTools.mergeObjects(AjaxTools.corsHeaders, {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }),
            success: function (data) {
                if (data.status == 'OK') {
                    that.formulareData = data;
                    that.renderFormulareData(data);
                    $("#container_stare_luna").empty();


                    that.determineVisibleOrInvisibleButtons();
                    //check luna inchisa/deschisa
                    if(data.isPerioadaInchisa=='0'){
                        $("#container_stare_luna").append(" <div class='message-box success'>" +
                            "<p>Perioada este deschisa raportarii! <i class='fas fa-lock-open'></i></p>" +
                            "</div>");
                    }else {
                        $("#container_stare_luna").append(" <div class='message-box error'>" +
                            "<p>Perioada este inchisa raportarii! <i class='fas fa-lock'></i></p>" +
                            "</div>");
                        $("#buton_depune_declaratie").hide();
                        $("#buton_depune_rectificativa").hide();
                        $(".open_formular").hide();
                    }
                    defer.resolve();
                } else {
                    Swal.fire({
                        position: 'top',
                        icon: 'error',
                        html: "A aparut o eroare!<br/>",
                        focusConfirm: false,
                        confirmButtonText: 'Ok'
                    });

                    defer.reject("A aparut o eroare");
                }
            },
            error: function(data){
                Swal.fire({
                    position: 'top',
                    icon: 'error',
                    html: "A aparut o eroare!<br/>",
                    focusConfirm: false,
                    confirmButtonText: 'Ok'
                });

                defer.reject("A aparut o eroare");
            }
        });

        return defer.promise();
    },

    renderFormulareData: function (data) {
        var that = this;

        $("#container_formular").empty();
        var html = that.renderTemplateNonAsync(that, "tmpl_formulare_list", data);
        $("#container_formular").html(html);
        $('#resize_iframe', window.parent.document).trigger('click');
        if(that.isAdmin ){
            $(".sterge_declaratie").each( function() {
                var item = $( this );
                item.css("display","block");
                item.removeAttr("hidden");
            } );
        }else{

            for(var i=0; i<data.contribGnList.length;i++){
                if(data.contribGnList[i].status.toLowerCase()=='in lucru'){
                    var item = $( "#sterge_declaratie_"+ data.contribGnList[i].id );
                    item.css("display","block");
                    item.removeAttr("hidden");
                }
            }
        }
    },

    determineVisibleOrInvisibleButtons: function(){
        var that = this;

        var lengthMaster = null;
        try {
            lengthMaster = that.formulareData.contribGnList.length;
        } catch (e){}

        if (typeof lengthMaster !== 'undefined' && lengthMaster > 0){
            $("#buton_depune_declaratie").css('display', 'none');
            $("#buton_depune_rectificativa").css('display', 'inline-block');
        } else {
            $("#buton_depune_declaratie").css('display', 'inline-block');
            $("#buton_depune_rectificativa").css('display', 'none');
        }
    },

    depuneDeclaratie: function(tip){
        var that = this;


        if (that.wsAndUserInfo!=null && that.wsAndUserInfo.tert.id!=null&&that.wsAndUserInfo.tert.id!=''){

            var req = {
            "id_tert": that.wsAndUserInfo.tert.id,
            "id_perioada_contabila": that.id_perioada_contabila,
            "zona": "CONTRIB_GN",
            "tip":tip
        };

        var jReq = JSON.stringify(req);

        var defer = $.Deferred();
        $.ajax({
            url: that.wsAndUserInfo.wsUrl + '/anre_contributii_gn/' + that.wsAndUserInfo.userToken.token + '/depuneDeclaratie',
            type: 'post',
            data: jReq,
            headers: ObjTools.mergeObjects(AjaxTools.corsHeaders, {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }),
            success: function (data) {
                if (data.status == 'OK') {
                    that.loadFormulareData();
                    if (typeof data.idDeclaratie !== 'undefined' && data.idDeclaratie !== null){
                        that.editDeclaratie(data.idDeclaratie);
                    }
                    defer.resolve();
                } else {
                    if (typeof data.errString !== 'undefined' && data.errString !== null){
                        Swal.fire({
                            position: 'top',
                            icon: 'error',
                            html: data.errString,
                            focusConfirm: false,
                            confirmButtonText: 'Ok'
                        });
                    } else if (typeof data.extendedStatus !== 'undefined' && data.extendedStatus !== null){
                        Swal.fire({
                            position: 'top',
                            icon: 'info',
                            html: data.extendedStatus,
                            focusConfirm: false,
                            confirmButtonText: 'Ok'
                        });
                    } else {
                        Swal.fire({
                            position: 'top',
                            icon: 'info',
                            html: 'A aparut o eroare',
                            focusConfirm: false,
                            confirmButtonText: 'Ok'
                        });
                    }

                    defer.reject("A aparut o eroare");
                }
            },
            error: function(data){
                Swal.fire({
                    position: 'top',
                    icon: 'error',
                    html: "A aparut o eroare!<br/>",
                    focusConfirm: false,
                    confirmButtonText: 'Ok'
                });

                defer.reject("A aparut o eroare");
            }
        });
    }else{
        Swal.fire({
        position:'top',
        icon: "error",
        html: "Selectati un operator pentru a putea depune o declaratie.",
        focusConfirm: false,
        confirmButtonText: "Ok"
    });
defer.resolve();
}
        return defer.promise();
    },

    goBack: function () {
        window.history.back();
    },

    editDeclaratie: function (id) {

        var that = this;
        if (that.wsAndUserInfo!=null && that.wsAndUserInfo.tert.id!=null&&that.wsAndUserInfo.tert.id!=''){
            //perioada selectata
            that.id_perioada_contabila= $("#select_perioada").val();
            var url= './main-screen-operator-c-gn-detail.html?&id='+id;
            if (that.id_perioada_contabila!=null && that.id_perioada_contabila!=''){
                url+='&idPerioada='+that.id_perioada_contabila;
            }

            window.location.href =url;
        }else{
            Swal.fire({
                position:'top',
                icon: "error",
                html: "Selectati un operator pentru a putea depune o declaratie.",
                focusConfirm: false,
                confirmButtonText: "Ok"
            });
        }

    },
    deleteDeclaratie: function(id){
        var that = this;

        Swal.fire({
                html:"Sunteti pe cale sa stergeti o declaratie. Sunteti sigur ca doriti sa o stergeti?",
                onOpen: function() {
                },
                inputAttributes: {
                    autocapitalize: 'off'
                },
                showCancelButton: true,
                confirmButtonText: 'Da',
                showLoaderOnConfirm: true,
                preConfirm: () => {
                return new Promise(function(resolve, reject) {

                    var req = {
                        "id": id
                    };

                    var jReq = JSON.stringify(req);

                    $.ajax({
                        url: that.wsAndUserInfo.wsUrl + '/anre_contributii_gn/' + that.wsAndUserInfo.userToken.token + '/deleteContrib',
                        type: 'post',
                        data: jReq,
                        headers: ObjTools.mergeObjects(AjaxTools.corsHeaders, {
                            'Accept': 'application/json',
                            'Content-Type': 'application/json'
                        }),
                        success: function (data) {
                            if (data.status == 'OK') {
                                Swal.fire({
                                    position:'top',
                                    icon: "info",
                                    html: data.extendedStatus,
                                    focusConfirm: false,
                                    confirmButtonText: "Ok",
                                    onClose: function(){
                                        that.loadFormulareData();

                                    }
                                });

                            } else {
                                Swal.fire({
                                    position: 'top',
                                    icon: 'error',
                                    html: "A aparut o eroare!<br/>",
                                    focusConfirm: false,
                                    confirmButtonText: 'Ok'
                                });

                            }
                        },
                        error: function(data){
                            Swal.fire({
                                position: 'top',
                                icon: 'error',
                                html: "A aparut o eroare!<br/>",
                                focusConfirm: false,
                                confirmButtonText: 'Ok'
                            });

                        }
                    });


                });
    },
        allowOutsideClick: () => !Swal.isLoading()
    });


}
};


$(document).ready(function () {
    FileManager.init();
});