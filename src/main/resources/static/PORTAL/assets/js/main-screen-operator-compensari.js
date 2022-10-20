var PAGE_NAME = "main-screen-operator-compensari.js";

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
            var url='/PISC/solicitare-noua?document='+idTipDoc+'&tipDocument='+idClasaDoc+'&fromMainScreen=COMP'+request;
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
            var url='/PISC/solicitare-noua?document='+idTipDoc+'&tipDocument='+idClasaDoc+'&fromMainScreen=COMP-VIEW'+request;
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

                        var url = '/dmsws/raportare/getTertRaportareList?&idClasaDoc='+FileManager.idClasaDoc;

                        var saved_last_tert = sessionStorage.getItem('saved_last_tert');
                        if (typeof saved_last_tert !== 'undefined' && saved_last_tert !== null){
                            url+="&saved_last_tert_id="+ saved_last_tert;
                        }

                        $("#container_select_operator").css('display','block');
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

    loadPerioadaData: function () {
        var that = this;

        var req = {
            "zona": "COMPENSARI"
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

        var saved_last_tert = sessionStorage.getItem('saved_last_tert');
        if (typeof saved_last_tert !== 'undefined' && saved_last_tert !== null){
            $("#select_tert").val(saved_last_tert);
            FileManager.triggerClickBtn('#btn_actualizare')
        }

        $("#select_tert").chosen({
            no_results_text: "Vom reîncărca lista conform textului căutat - momentan nu a fost găsit : "
        });
        $('#resize_iframe', window.parent.document).trigger('click');


        $("#select_tert").change(function(){
            sessionStorage.setItem('saved_last_tert', $("#select_tert").val());
        })
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

        var saved_last_perioada = sessionStorage.getItem('saved_last_perioada');
        if (typeof saved_last_perioada !== 'undefined' && saved_last_perioada !== null){
            $("#select_perioada").val(saved_last_perioada);
        }

        $("#select_perioada").chosen({
            no_results_text: "Nu a fost găsit : "
        });

        $('#resize_iframe', window.parent.document).trigger('click');
        $(".idPerioadaHidden").val( $("#select_perioada").val());

        $("#select_perioada").change(function(){
            sessionStorage.setItem('saved_last_perioada', $("#select_perioada").val());
        });
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
        Swal.fire({
                html:"Va atragem atentia ca modificarea ulterioara a datelor incarcate va genera prelungirea termenului de raspuns cu 30 zile de la data ultimei modificari." +
                "Sunteti sigur ca ati completat corect datele aferente perioadei de raportare?",
                onOpen: function() {
                },
                inputAttributes: {
                    autocapitalize: 'off'
                },
                showCancelButton: true,
                confirmButtonText: 'Da, trimite',
                cancelButtonText: 'Nu, renunta',
                showLoaderOnConfirm: true,
                preConfirm: () => {
                return new Promise(function(resolve, reject) {

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
                        var url= '/dmsws/raportare/validareInterFisiere?&idsFiles='+idsFiles+'&transmitereDate=true';
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
                                                icon: 'info',
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
                                        icon: 'info',
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

                });
    },
        allowOutsideClick: () => !Swal.isLoading()
    });




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
                        that.formulareData = data;
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

        $("#submit_file_"+id).click();
        setTimeout( function(){
            FileManager.loadFormulareData()
        }  , 3000 );
    },
    uploadFileChangeXml: function (id) {
        var that = this;

        var idDocType = id;
        var idPerioada = $("#select_perioada").val();
        var idTert = $("#select_tert").val();

        if (typeof idTert === 'undefined' || idTert === null) {
            if (typeof that.wsAndUserInfo !== 'undefined' && that.wsAndUserInfo !== null
                && typeof that.wsAndUserInfo.tert !== 'undefined' && that.wsAndUserInfo.tert !== null
                && typeof that.wsAndUserInfo.tert.id !== 'undefined' && that.wsAndUserInfo.tert.id !== null
            ){
                idTert = that.wsAndUserInfo.tert.id;
            }
        }

        var fileData = $("#file_"+id)[0];

        var url = '/dmsws/file/uploadFisierByIdTipDocXml';

        var formData = new FormData();
        formData.append('file', fileData.files[0]);
        formData.append('idDocType', idDocType);
        formData.append('idPerioada', idPerioada);

        if (typeof idTert !== 'undefined' && idTert !== null) {
            formData.append('idTert', idTert);
        }

        $.ajax({
            url: url,
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
            success: function (json) {
                $("#file_"+id).val('');

                if (json.status == 'OK') {
                    if (typeof json.warnings !== 'undefined' && json.warnings !== null && json.warnings.trim() !== ''){
                        Swal.fire({
                            icon: 'info',
                            customClass: 'swal-wide',
                            html: 'Fisierul xml este valid si se conformeaza cu template-ul xsd. Au fost insa urmatoarele atentionari la validare: <br/><br/>' + json.warnings,
                            focusConfirm: false,
                            confirmButtonText: 'Ok'
                        }).then(function(result) {
                            FileManager.loadFormulareData()
                        });
                    } else if (typeof json.errors !== 'undefined' && json.errors !== null && json.errors.trim() !== ''){
                        Swal.fire({
                            icon: 'error',
                            customClass: 'swal-wide',
                            html: 'Fisierul xml nu este valid conform template-ului xsd. Acestea sunt erorile descoperite la validare: <br/><br/>' + json.errors,
                            focusConfirm: false,
                            confirmButtonText: 'Ok'
                        }).then(function(result) {
                            FileManager.loadFormulareData()
                        });
                    } else {
                        Swal.fire({
                            icon: 'info',
                            html: 'Fisierul xml este valid, se conformeaza cu template-ul xsd si a fost salvat.',
                            focusConfirm: false,
                            confirmButtonText: 'Ok'
                        }).then(function(result) {
                            FileManager.loadFormulareData()
                        });
                    }
                }
                else {
                    Swal.fire({
                        icon: 'error',
                        html: 'Au fost erori la incarcare: <br/><br/>' + json.errString,
                        focusConfirm: false,
                        confirmButtonText: 'Ok'
                    });
                }
            },
            error: function (err) {
                $("#file_"+id).val('');

                Swal.fire({
                    position: 'top',
                    icon: 'error',
                    customClass: 'swal-wide',
                    html: 'Au fost erori la incarcare: <br/><br/>' + err.responseJSON.message,
                    focusConfirm: false,
                    confirmButtonText: 'Ok'
                });
            }
        });
    },

    veziErori: function(id){
        var that = this;

        if (typeof that.formulareData !== 'undefined' && that.formulareData !== null && typeof that.formulareData.formularRaportareList !== 'undefined' && that.formulareData.formularRaportareList !== null){
            var dt = null;

            $.each(that.formulareData.formularRaportareList, function (index, fd) {
                if (typeof fd !== 'undefined' && fd !== null && typeof fd.id !== 'undefined' && fd.id !== null && fd.id == id){
                    dt = fd;
                }
            });

            if (dt !== null){
                Swal.fire({
                    icon: 'error',
                    customClass: 'swal-wide',
                    html: 'Fisierul xml nu este valid conform template-ului xsd. Acestea sunt erorile descoperite la validare: <br/><br/>' + dt.errors,
                    focusConfirm: false,
                    confirmButtonText: 'Ok'
                });
            }
        }
    },

    veziAtentionari: function(id){
        var that = this;

        if (typeof that.formulareData !== 'undefined' && that.formulareData !== null && typeof that.formulareData.formularRaportareList !== 'undefined' && that.formulareData.formularRaportareList !== null){
            var dt = null;

            $.each(that.formulareData.formularRaportareList, function (index, fd) {
                if (typeof fd !== 'undefined' && fd !== null && typeof fd.id !== 'undefined' && fd.id !== null && fd.id == id){
                    dt = fd;
                }
            });

            if (dt !== null){
                Swal.fire({
                    icon: 'info',
                    customClass: 'swal-wide',
                    html: 'Fisierul xml este valid si se conformeaza cu template-ul xsd. Au fost insa urmatoarele atentionari la validare: <br/><br/>' + dt.warnings,
                    focusConfirm: false,
                    confirmButtonText: 'Ok'
                });
            }
        }
    },

    downloadTemplateXml: function(templateFileXmlToken, downloadLinkTemplateFileXml){
        var that = this;

        var req = {
            "fileToken": templateFileXmlToken
        };

        var jReq = JSON.stringify(req);

        var defer = $.Deferred();
        $.ajax({
            url: that.wsAndUserInfo.wsUrl + '/download/' + that.wsAndUserInfo.userToken.token + '/checkXsdToXml',
            type: 'post',
            data: jReq,
            headers: ObjTools.mergeObjects(AjaxTools.corsHeaders, {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }),
            success: function (data) {
                if (data.status == 'OK') {
                    window.open(downloadLinkTemplateFileXml, '_blank');
                    defer.resolve();
                } else {
                    Swal.fire({
                        position: 'top',
                        icon: 'error',
                        html: data.errString,
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
    }

};


$(document).ready(function () {
    FileManager.init();
});