var PAGE_NAME = "main-screen-operator-et.js";

var FileManager = {
    parametersLoadedOk: false,


    // user info object
    idClasaDoc: null,
    userInfo: null,
    wsAndUserInfo: {},

    // pre-compiled mustache templates
    templates: {},

    /*
     Initialization function.
     */
    init: function () {
        var that = this;
        //CHECK IF LOGGED IN
        $.ajax({

            type: 'GET',
            url: '/dmsws/utilizator/userIsLogged',
            success: function (data) {

                var isLogged=data!=null && data== 'true';
                if(isLogged){
                    that.mandatoryFunctions().then(
                        function(){
                            that.checkRightData();
                            that.loadPerioadaData();
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
                        onClose: () => {
                        window.top.location.href=$UTIL.WORDPRESS_URL+ '/';
                    // window.open($UTIL.WORDPRESS_URL+ '/autentificare', '_self');

                }
                });
                }
            }

        });



    },
    openFormular: function (idTipDoc,idClasaDoc, request) {


            if(  request!='false'){
                var url='/PISC/solicitare-noua?document='+idTipDoc+'&tipDocument='+idClasaDoc+'&fromMainScreen=ET'+request;
                var idPerioada= $("#select_perioada").val();
                if(idPerioada!=null && typeof idPerioada !='undefined' && idPerioada!=''){
                    url+='&idPerioada='+idPerioada;

                }

                var idTert= $("#select_tert").val();
                if(idTert!=null && typeof idTert !='undefined' && idTert!=''){
                    url+='&idTert='+idTert;

                }
                $("#a_open_formular").attr('href',url);
                window.location.href=url;
            }
    },
    viewFormular: function (idTipDoc,idClasaDoc, request) {


        if(  request!='false'){
            var url='/PISC/solicitare-revizie-finala?document='+idTipDoc+'&tipDocument='+idClasaDoc+'&fromMainScreen=ET-VIEW'+request;
            var idPerioada= $("#select_perioada").val();
            if(idPerioada!=null && typeof idPerioada !='undefined' && idPerioada!=''){
                url+='&idPerioada='+idPerioada;

            }

            var idTert= $("#select_tert").val();
            if(idTert!=null && typeof idTert !='undefined' && idTert!=''){
                url+='&idTert='+idTert;

            }
            $("#a_view_formular").attr('href',url);
            window.location.href=url;
        }
    },
    downloadLink: function (downloadLink) {

         window.top.location.href=downloadLink;

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
                var url = '/dmsws/raportare/getTertRaportareList?&idClasaDoc='+FileManager.idClasaDoc;
                url+="&searchStr="+ $text;
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
    mandatoryFunctions: function () {

        var that=this;
        var defer = $.Deferred();
        var PROC_NAME = "FileManager.mandatoryFunctions";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);
        //-------------- preluam WS_URL si WS_TOKEN din documentaPortal-util.js ---------------------
        // luam info dmsws si user
        that.getWsAndUserInfo().then(
            function(){
                //-------------- initializam template-uri mustache ---------------------
                FileManager.compileAllTemplates();
                 FileManager.idClasaDoc=new URLSearchParams(window.location.search).get("idClasaDoc");
                FileManager.reloadListaTertAfterStopTyping(2000);



                defer.resolve();
            },
            function(){
                defer.reject();
            }
        );

        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);

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
    /*
     Function to read basic project data from ws.
     */
    checkRightData: function () {
        var that = this;

        var defer = $.Deferred();
        $.ajax({
          url: '/dmsws/raportare/checkUserGroupMemberByCode?&codGrup=ADMIN_MONITORIZARE',
            success: function (data) {
                if (data.result == 'OK') {

                   if(data.info=='true'){


                        $("#container_select_operator").css('display','block');
                       $.ajax({
                           url: '/dmsws/raportare/getTertRaportareList?&idClasaDoc='+FileManager.idClasaDoc,
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

loadPerioadaData: function () {
    var that = this;

    var req = {
        "zona": "MONITORIZARE_SR"
    };

    var jReq = JSON.stringify(req);

    var defer = $.Deferred();
    $.ajax({
        url: that.wsAndUserInfo.wsUrl + '/anre_contributii/' + that.wsAndUserInfo.userToken.token + '/getPerioadaContabilaByZona',
        type: 'post',
        data: jReq,
        headers: ObjTools.mergeObjects(AjaxTools.corsHeaders, {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }),
        success: function (data) {
            if (data.status == 'OK') {

                that.renderPerioadaData(data);
                that.loadFormulareData();
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
        $("#select_tert").chosen({
            no_results_text: "Vom reîncărca lista conform textului căutat - momentan nu a fost găsit : "
        });

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

    },

renderPerioadaData: function (data) {
        var that = this;

        var html = that.renderTemplateNonAsync(that, "tmpl_perioada", data);
        $("#container_perioada").html(html);
        $("#select_perioada").chosen({
            no_results_text: "Nu a fost găsit : "
        });


        $(".idPerioadaHidden").val( $("#select_perioada").val());
    },

    triggerClickBtn: function (selectorBtn) {

        $(selectorBtn).click();

    },

    triggerClickBtnPerioada: function (selectorBtn) {

        $(selectorBtn).click();
        $(".idPerioadaHidden").val($("#select_perioada").val());

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

    valideazaDate: function () {
        var that = this;

        // start popup loading
        Swal.fire({position: 'top',
            title: "Validăm datele...",
            text: "Vă rugăm să așteptați",
            icon: "question",
            showConfirmButton: true,
            allowOutsideClick: false

        });
        // 1. Preluat id fisiere completate
        var idsFiles='';
        $( ".id_fisier" ).each(function( index ) {
            var idFisier=$( this ).val();
            if(idFisier!=''){
                idsFiles+=idFisier+',';
            }

        });
        // 2. Apelare ws


        if(idsFiles!=''){
            idsFiles=idsFiles.substr(0,idsFiles.length-1);
            var url= '/dmsws/raportare/validareInterFisiere?&idsFiles='+idsFiles+'&transmitereDate=false';
            $.ajax({
                url: url,
                success: function (data) {
                    // 3. Afisare mesaj/ inchis popup loading

                    if (data.result == 'OK') {
                        if(data.validTotal){
                            Swal.fire({
                                position: 'top',
                                icon: 'info',
                                html: "Formularele au fost validate. Datele sunt valide!",
                                focusConfirm: false,
                                confirmButtonText: 'Ok'
                            });
                        }
                        else{
                            Swal.fire({
                                position: 'top',
                                icon: 'error',
                                html: data.mesajFinal,
                                focusConfirm: false,
                                confirmButtonText: 'Ok'
                            });
                        }

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
        }else{
            Swal.fire({
                position: 'top',
                icon: 'error',
                html: "Nu exista niciun formular incarcat pentru trimitere spre validare!<br/>",
                focusConfirm: false,
                confirmButtonText: 'Ok'
            });
        }

},
    valideazaDateSiTrimite: function () {
        var that = this;

        // start popup loading
        Swal.fire({
            position: 'top',
            title: "Validăm și transmitem datele...",
            text: "Vă rugăm să așteptați",
            icon: "question",
            showConfirmButton: true,
            allowOutsideClick: false

        });
        // 1. Preluat id fisiere completate
        var idsFiles='';
        $( ".id_fisier" ).each(function( index ) {
            var idFisier=$( this ).val();
            if(idFisier!=''){
                idsFiles+=idFisier+',';
            }

        });
        // 2. Apelare ws


        if(idsFiles!=''){
            idsFiles=idsFiles.substr(0,idsFiles.length-1);
            var url= '/dmsws/raportare/validareInterFisiere?&idsFiles='+idsFiles+'&transmitereDate=true&transmiteValid=false';
            $.ajax({
                url: url,
                success: function (data) {
                    // 3. Afisare mesaj/ inchis popup loading

                    if (data.result == 'OK') {

                        if(data.validTotal){
                            Swal.fire({
                                position: 'top',
                                icon: 'info',
                                html: data.mesajFinal,
                                    onClose: () => {
                                   FileManager.loadFormulareData();
                        },
                                focusConfirm: false,
                                confirmButtonText: 'Ok'
                            });
                        }
                        else{
                            Swal.fire({
                                position: 'top',
                                icon: 'error',
                                html: data.mesajFinal,
                                    onClose: () => {
                                    FileManager.loadFormulareData();
                        },
                                focusConfirm: false,
                                confirmButtonText: 'Ok'
                            });
                        }

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
        }else{
            Swal.fire({
                position: 'top',
                icon: 'error',
                html: "Nu exista niciun formular incarcat pentru trimitere spre validare!<br/>",
                focusConfirm: false,
                confirmButtonText: 'Ok'
            });
        }

    },
loadFormulareData: function () {
    Swal.fire({
        position: 'top',
        title: "Încărcăm datele...",
        text: "Vă rugăm să așteptați",
        icon: "question",
        showConfirmButton: true,
        allowOutsideClick: false

    });
        var that = this;

        var defer = $.Deferred();
        var url= '/dmsws/raportare/getDocumentListByTertPerioada?&idClasaDoc='+that.idClasaDoc;
        var idPerioada= $("#select_perioada").val();
        url+='&idPerioada='+idPerioada;
        var idTert= $("#select_tert").val();
        if(idTert!=null && typeof idTert !='undefined'){
            url+='&idTert='+idTert;

        }
    if(idPerioada!=null && typeof idPerioada !='undefined' && idPerioada!='' ){

        $.ajax({
            url: url,
            success: function (data) {
                if (data.result == 'OK') {
                    $("#container_stare_luna").empty();

                    //check luna inchisa/deschisa
                    if(data.isPerioadaInchisa=='0'){
                        $("#container_stare_luna").append(" <div class='message-box success'>" +
                            "<p>Luna este deschisa raportarii! <i class='fas fa-lock-open'></i></p>" +
                            "</div>");
                    }else {
                        $("#container_stare_luna").append(" <div class='message-box error'>" +
                            "<p>Luna este inchisa raportarii! <i class='fas fa-lock'></i></p>" +
                            "</div>");
                    }
                    that.renderFormulareData(data);

                    swal.close();
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
    }else{
        swal.close();
    }


        return defer.promise();
    },


    renderFormulareData: function (data) {
        var that = this;

        $("#container_formular").empty();
        var html = that.renderTemplateNonAsync(that, "tmpl_formulare_list", data);
        $("#container_formular").html(html);
        $('#resize_iframe', window.parent.document).trigger('click');


    },

    goBack: function () {
       window.history.back();
    },

    renderResponsabilData: function () {
        var that = this;

        ;
        var html = Util.renderTemplateNonAsync(that, "tmpl_responsabil_ticket", that.responsabiliData);
        $("#lista_responsabili").html(html);
    },


    loadResponsabiliData: function () {
        var that = this;
        ;
        var defer = $.Deferred();
        var projectIdClear = FileManager.projectId;
        if (projectIdClear.toString().includes("#")) {
            projectIdClear = projectIdClear.replace('#', '');
        }
        AjaxTools.ajaxAction({
            url: '/dmsws/project/getListaResponsabili/' + projectIdClear,
            headers: ObjTools.mergeObjects(AjaxTools.corsHeaders, {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }),
            method: 'get',
            onSuccess: function (json) {
                if (json.result !== 'OK') {

                    defer.reject(json.extendedInfo);
                    return;
                }
                else {
                    that.responsabiliData = json;
                }

                defer.resolve(json);
            },
            onError: function (err) {
                var strErr = Util.getAjaxErrorMessage(err);
                defer.reject(strErr);
            }
        });

        return defer.promise();
    },

    /*
     Function to compile all known mustache templates.
     */
    compileAllTemplates: function () {
        this.templates['tmpl_formulare_list'] = $('#tmpl_formulare_list').html();
        this.templates['tmpl_perioada'] = $('#tmpl_perioada').html();
        this.templates['tmpl_tert_raportare'] = $('#tmpl_tert_raportare').html();

        // parseaza toate template-urile
        $.each(this.templates, function (index, template) {
            Mustache.parse(template);
        });
    },
    uploadFileChange: function (id) {
        $(".idPerioadaHidden").val( $("#select_perioada").val());
        $(".idTertHidden").val( $("#select_tert").val());

        if( $("#select_perioada").val() === '' ){
            Swal.fire({
                icon: 'error',
                html:  'Nu a fost selectata perioada',
                focusConfirm: false,
                confirmButtonText: 'Ok'
            }).then(
                function () {FileManager.loadFormulareData()}
            );
            return;
        }


        var fileData = $("#file"+id)[0];


        var formData = new FormData();
        formData.append('file', fileData.files[0]);
        formData.append('nume', fileData.files[0].name);
        formData.append('idPerioada', $("#select_perioada").val());
        formData.append('idTert', $("#select_tert").val());
        formData.append('idDocType', id);

        //$("#submit_file_"+id).click();
        $("#register_pfa" + id).submit(function (e){
            e.preventDefault();
            e.stopImmediatePropagation();
            $.ajax({
                url: '/dmsws/anre/processExcelET',
                method: 'POST',
                type:'POST',
                data: formData,
                enctype: 'multipart/form-data',
                processData: false,
                contentType: false,
                dataType: "json",
                headers: ObjTools.mergeObjects(AjaxTools.corsHeaders, {
                    'Accept': 'application/json'
                }),
                success: function (data) {
                    if(data.errString == null){
                        Swal.fire({
                            icon: 'success',
                            html: "Datele au fost încărcate cu succes",
                            focusConfirm: false,
                            confirmButtonText: 'Ok'
                        }).then(
                            function () {FileManager.loadFormulareData()}
                        );
                    } else {
                        Swal.fire({
                            icon: 'error',
                            html:  'Fișierul nu a putut fi procesat din urmatorul motiv:\n' + data.errString,
                            focusConfirm: false,
                            confirmButtonText: 'Ok'
                        }).then(
                            function () {FileManager.loadFormulareData()}
                        );
                    }

                },
                error: function (data) {

                    Swal.fire({
                        icon: 'error',
                        html: "Eroare! Ne pare rau, cererea nu a putut fi salvata!",
                        focusConfirm: false,
                        confirmButtonText: 'Ok'
                    }).then(
                        function () {FileManager.loadFormulareData()}
                    );
                    console.log(data);
                }
            })
        });
        $("#submit_file_"+id).click();
        Swal.fire({
            position: 'top',
            title: "Se procesează fisierul...",
            text: "Vă rugăm să așteptați",
            icon: "info",
            showConfirmButton: false,
            allowOutsideClick: false

        });
    }
};


$(document).ready(function () {
    FileManager.init();
});