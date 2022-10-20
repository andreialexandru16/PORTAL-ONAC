var PAGE_NAME = "main-screen-operator-c-gn-detail.js";

var FileManager = {
    // pre-compiled mustache templates
    templates: {},

    id_contrib_gn: null,
    id_perioada_contabila: null,
    id_tert: null,
    wsAndUserInfo: {},
    formulareDetailData: {},
    documenteContributiiData: {},

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
                            that.loadFormulareDetailData();
                            that.loadDocumenteContributiiByZona().then(function(){
                                that.actionsDocumenteContributiiData();
                            });
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
        this.templates['tmpl_formulare_detail_list'] = $('#tmpl_formulare_detail_list').html();
        this.templates['tmpl_documente_contributii'] = $('#tmpl_documente_contributii').html();

        // parseaza toate template-urile
        $.each(this.templates, function (index, template) {
            Mustache.parse(template);
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

                var params = new URLSearchParams(window.location.href);
                if (params.has('id')){
                    that.id_contrib_gn = params.get('id');
                }
                if (params.has('idPerioada')){
                    FileManager.id_perioada_contabila = params.get('idPerioada');
                }
                if (params.has('idTert')){
                    FileManager.id_tert = params.get('idTert');
                }
                //-------------- initializam template-uri mustache ---------------------
                that.compileAllTemplates();
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

    loadFormulareDetailData: function(){
        var that = this;

        var req = {
            "id_contrib_gn": that.id_contrib_gn
        };

        var jReq = JSON.stringify(req);

        var defer = $.Deferred();
        $.ajax({
            url: that.wsAndUserInfo.wsUrl + '/anre_contributii_gn/' + that.wsAndUserInfo.userToken.token + '/getFormulareDetailData',
            type: 'post',
            data: jReq,
            headers: ObjTools.mergeObjects(AjaxTools.corsHeaders, {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }),
            success: function (data) {
                if (data.status == 'OK') {
                    that.formulareDetailData = data;
                    var currentObj;
                    if(that.formulareDetailData!=null && that.formulareDetailData.contribGnList.length!=0){
                        currentObj=that.formulareDetailData.contribGnList[0];
                    }
                    for(var i=0;i<data.contribGnList.length;i++){
                        for(var j=0;j<data.contribGnList[i].detailList.length;j++){

                            var current= data.contribGnList[i].detailList[j];
                            if(typeof current !='undefined' && typeof current.valoare_minima !='undefined'){
                                data.contribGnList[i].detailList[j].valoare_minima_view=current.valoare_minima.toLocaleString();

                            }
                            if(typeof current !='undefined' && typeof current.valoare_declarata !='undefined' && current.valoare_declarata!=null){
                                data.contribGnList[i].detailList[j].valoare_declarata_view=current.valoare_declarata.toLocaleString();

                            }
                        }

                    }

                    that.renderFormulareDetailData(data);
                    that.determineVisibleOrInvisibleButtons();
                    that.setPersoanaContactData(currentObj);
                    that.setReprezentantData(currentObj);
                    that.setOperatorData(currentObj);

                    $("#mentiuni").val(currentObj.mentiuni)

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
    setOperatorData: function(data){
    $("#operator").val(data.tert);
    $("#cui_operator").val(data.cuiTert);
    $("#localitate_operator").val(data.localitate);
},
    setPersoanaContactData: function(data){
      $("#nume_contact").val(data.numeContact);
      $("#telefon_contact").val(data.telefonContact);
      $("#functie_contact").val(data.functieContact);
      $("#mail_contact").val(data.mailContact);
    },
    setReprezentantData: function(data){
        $("#nume_rep").val(data.numeRep);
        $("#telefon_rep").val(data.telefonRep);
        $("#functie_rep").val(data.functieRep);
        $("#mail_rep").val(data.mailRep);
    },
    renderFormulareDetailData: function (data) {
        var that = this;

        $("#container_formular").empty();
        var html = that.renderTemplateNonAsync(that, "tmpl_formulare_detail_list", data);
        $("#container_formular").html(html);
        $("td").html(function (i, html) {
            return html.replace(/&nbsp;/g, ' ');
        });
        $(".input_valoare_declarata").number(true, 3, '.', ' ');
        $(".input_calculat_lei").number(true, 2, '.', ' ');
        $('#resize_iframe', window.parent.document).trigger('click');
    },

    determineVisibleOrInvisibleButtons: function(){
        var that = this;

        var statusMaster = null;
        try {
            statusMaster = that.formulareDetailData.contribGnList[0].status;
        } catch (e){}

        if (typeof statusMaster !== 'undefined' && statusMaster === 'Depusa'){
            $("#buton_depune_declaratie_finala").css('display', 'none');
            $("#mentiuni").attr('disabled','disabled');
            $("#nume_contact").attr('disabled','disabled');
            $("#functie_contact").attr('disabled','disabled');
            $("#mail_contact").attr('disabled','disabled');
            $("#telefon_contact").attr('disabled','disabled');
        } else {
            $("#buton_depune_declaratie_finala").css('display', 'initial');
        }
    },

    updateValoareCalculata: function(id,id_contrib_gn,coeficient,valoare_minima,element_id,calculated_element_id){
        var that = this;

        var valoare_declarata_float = null;
        var valoare_declarata_str = $("#" +element_id).val();

        if (valoare_declarata_str != null && valoare_declarata_str == ''){
            return;
        }

        try {
            valoare_declarata_float = parseFloat(valoare_declarata_str);
        } catch (e){}

        if (valoare_declarata_float == null || isNaN(valoare_declarata_float) || valoare_declarata_float < 0){
            Swal.fire({
                position: 'top',
                icon: 'error',
                html: "Specificati o valoare numerica pozitiva!<br/>",
                focusConfirm: false,
                confirmButtonText: 'Ok'
            });

            that.animateElementErr("#"+element_id);

            defer.reject("A aparut o eroare");
            return defer.promise();
        }

        var req = {
            "id": id,
            "id_contrib_gn": id_contrib_gn,
            "coeficient": coeficient,
            "valoare_minima" : valoare_minima,
            "valoare_declarata" : valoare_declarata_float
        };

        var jReq = JSON.stringify(req);

        var defer = $.Deferred();
        $.ajax({
            url: that.wsAndUserInfo.wsUrl + '/anre_contributii_gn/' + that.wsAndUserInfo.userToken.token + '/updateDeclaratieDetailValoareCalculata',
            type: 'post',
            data: jReq,
            headers: ObjTools.mergeObjects(AjaxTools.corsHeaders, {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }),
            success: function (data) {
                if (data.status == 'OK') {
                    that.updateValoareCalculataRand(id,id_contrib_gn,coeficient,valoare_minima,element_id,calculated_element_id,data.valoareCalculata);
                    defer.resolve();
                } else {
                    Swal.fire({
                        position: 'top',
                        icon: 'error',
                        html: "A aparut o eroare!<br/>",
                        focusConfirm: false,
                        confirmButtonText: 'Ok'
                    });

                    that.animateElementErr("#"+element_id);

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

                that.animateElementErr("#"+element_id);

                defer.reject("A aparut o eroare");
            }
        });

        return defer.promise();
    },

    updateReprezentant: function(element_id){
        var that = this;

        var nume_contact = $("#nume_rep").val();
        var telefon_contact = $("#telefon_rep").val();
        var functie_contact = $("#functie_rep").val();
        var mail_contact = $("#mail_rep").val();

        if($UTIL.validateEmail(mail_contact)){
            var req = {
                "id": that.id_contrib_gn,
                "numeRep": nume_contact,
                "telefonRep" : telefon_contact,
                "functieRep" : functie_contact,
                "mailRep" : mail_contact
            };

            var jReq = JSON.stringify(req);

            var defer = $.Deferred();
            $.ajax({
                url: that.wsAndUserInfo.wsUrl + '/anre_contributii_gn/' + that.wsAndUserInfo.userToken.token + '/updateReprezentant',
                type: 'post',
                data: jReq,
                headers: ObjTools.mergeObjects(AjaxTools.corsHeaders, {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }),
                success: function (data) {
                    if (data.status == 'OK') {
                        that.animateElementOk('#' + element_id);
                        defer.resolve();
                    } else {
                        Swal.fire({
                            position: 'top',
                            icon: 'error',
                            html: "A aparut o eroare!<br/>",
                            focusConfirm: false,
                            confirmButtonText: 'Ok'
                        });

                        that.animateElementErr("#"+element_id);

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

                    that.animateElementErr("#"+element_id);

                    defer.reject("A aparut o eroare");
                }
            });
            return defer.promise();

        }else{

            that.animateElementErr("#"+element_id);

        }


    },

updatePersoanaContact: function(element_id){
        var that = this;

        var nume_contact = $("#nume_contact").val();
        var telefon_contact = $("#telefon_contact").val();
        var functie_contact = $("#functie_contact").val();
        var mail_contact = $("#mail_contact").val();

        if($UTIL.validateEmail(mail_contact)){
            var req = {
                "id": that.id_contrib_gn,
                "numeContact": nume_contact,
                "telefonContact" : telefon_contact,
                "functieContact" : functie_contact,
                "mailContact" : mail_contact
            };

            var jReq = JSON.stringify(req);

            var defer = $.Deferred();
            $.ajax({
                url: that.wsAndUserInfo.wsUrl + '/anre_contributii_gn/' + that.wsAndUserInfo.userToken.token + '/updatePersoanaContact',
                type: 'post',
                data: jReq,
                headers: ObjTools.mergeObjects(AjaxTools.corsHeaders, {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }),
                success: function (data) {
                    if (data.status == 'OK') {
                        that.animateElementOk('#' + element_id);
                        defer.resolve();
                    } else {
                        Swal.fire({
                            position: 'top',
                            icon: 'error',
                            html: "A aparut o eroare!<br/>",
                            focusConfirm: false,
                            confirmButtonText: 'Ok'
                        });

                        that.animateElementErr("#"+element_id);

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

                    that.animateElementErr("#"+element_id);

                    defer.reject("A aparut o eroare");
                }
            });
            return defer.promise();

        }else{

            that.animateElementErr("#"+element_id);

        }


    },
    updateMentiuni: function(element_id){
        var that = this;

        var mentiuni = $("#mentiuni").val();

        var req = {
            "id": that.id_contrib_gn,
            "mentiuni": mentiuni
        };

        var jReq = JSON.stringify(req);

        var defer = $.Deferred();
        $.ajax({
            url: that.wsAndUserInfo.wsUrl + '/anre_contributii_gn/' + that.wsAndUserInfo.userToken.token + '/updateMentiuni',
            type: 'post',
            data: jReq,
            headers: ObjTools.mergeObjects(AjaxTools.corsHeaders, {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }),
            success: function (data) {
                if (data.status == 'OK') {
                    that.animateElementOk('#' + element_id);
                    defer.resolve();
                } else {
                    Swal.fire({
                        position: 'top',
                        icon: 'error',
                        html: "A aparut o eroare!<br/>",
                        focusConfirm: false,
                        confirmButtonText: 'Ok'
                    });

                    that.animateElementErr("#"+element_id);

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

                that.animateElementErr("#"+element_id);

                defer.reject("A aparut o eroare");
            }
        });

        return defer.promise();
    },

    validareDescarcare: function(){
        var that = this;
        var notFilled = false;

        $(".input_valoare_declarata").each(function(index) {
            var valoare = $(this).val();

            if (typeof valoare === 'undefined' || valoare === null || valoare.trim() === ''){
                notFilled = true;
            }
        });

        if (notFilled){
            Swal.fire({
                position: 'top',
                icon: 'error',
                html: "Completati toate valorile!<br/>",
                focusConfirm: false,
                confirmButtonText: 'Ok'
            });

            return false;
        }


        return true;
    },
    validareDepunere: function(){
        var that = this;
        var notFilled = false;

        $(".input_valoare_declarata").each(function(index) {
            var valoare = $(this).val();

            if (typeof valoare === 'undefined' || valoare === null || valoare.trim() === ''){
                notFilled = true;
            }
        });

        if (notFilled){
            Swal.fire({
                position: 'top',
                icon: 'error',
                html: "Completati toate valorile!<br/>",
                focusConfirm: false,
                confirmButtonText: 'Ok'
            });

            return false;
        }

        var toateObligatoriiDepuse = true;

        $(that.documenteContributiiData.tipuriDocumente).each(function(index, doc) {
            if (typeof doc.obligatoriu !== 'undefined' && doc.obligatoriu !== null && doc.obligatoriu
            && (typeof doc.documentList !== 'object' || doc.documentList === null || doc.documentList.length === 0)){
                toateObligatoriiDepuse = false;
            }
        });

        if (!toateObligatoriiDepuse){
            Swal.fire({
                position: 'top',
                icon: 'error',
                html: "Aveti documente obligatorii ne-incarcate!<br/>",
                focusConfirm: false,
                confirmButtonText: 'Ok'
            });

            return false;
        }


        return true;
    },
    descarcaDeclaratie: function(){
        var that = this;

        if (!that.validareDescarcare()){
            return;
        }


        var req = {
            "id_contrib_gn": that.id_contrib_gn
        };

        var jReq = JSON.stringify(req);

        var defer = $.Deferred();
        $.ajax({
            url: that.wsAndUserInfo.wsUrl + '/anre_contributii_gn/' + that.wsAndUserInfo.userToken.token + '/descarcaDeclaratie',
            type: 'post',
            data: jReq,
            headers: ObjTools.mergeObjects(AjaxTools.corsHeaders, {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }),
            success: function (data) {
                if (data.result == 'OK') {
                   /* window.open(
                        data.downloadLink,
                        '_blank' // <- This is what makes it open in a new window.
                    );*/
                    $UTIL.downloadFileByVaadin(data.nume,data.downloadLink);
                    defer.resolve();
                } else {
                    Swal.fire({
                        position: 'bottom',
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
                    position: 'bottom',
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
    depuneDeclaratieFinala: function(){
        var that = this;

        if (!that.validareDepunere()){
            return;
        }


        var req = {
            "id_contrib_gn": that.id_contrib_gn
        };

        var jReq = JSON.stringify(req);

        var defer = $.Deferred();
        $.ajax({
            url: that.wsAndUserInfo.wsUrl + '/anre_contributii_gn/' + that.wsAndUserInfo.userToken.token + '/salveazaSiDepuneDeclaratie',
            type: 'post',
            data: jReq,
            headers: ObjTools.mergeObjects(AjaxTools.corsHeaders, {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }),
            success: function (data) {
                if (data.status == 'OK') {
                    that.backToMaster();
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

    deleteAttachedFile: function(id_fisier, id_contrib_gn_fisiere){
        var that = this;

        var req = {
            "id_fisier": id_fisier,
            "id_contrib_gn_fisiere" : id_contrib_gn_fisiere
        };

        var jReq = JSON.stringify(req);

        var defer = $.Deferred();
        $.ajax({
            url: that.wsAndUserInfo.wsUrl + '/anre_contributii_gn/' + that.wsAndUserInfo.userToken.token + '/deleteAttachedFile',
            type: 'post',
            data: jReq,
            headers: ObjTools.mergeObjects(AjaxTools.corsHeaders, {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }),
            success: function (data) {
                if (data.status == 'OK') {
                    that.loadDocumenteContributiiByZona();
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

    saveAttachedFileName: function(id_fisier, id_contrib_gn_fisiere){
        var that = this;

        var newName = $("#file_name_" + id_contrib_gn_fisiere).val().trim();

        var req = {
            "id_fisier": id_fisier,
            "id_contrib_gn_fisiere" : id_contrib_gn_fisiere,
            "new_name" : newName
        };

        var jReq = JSON.stringify(req);

        var defer = $.Deferred();
        $.ajax({
            url: that.wsAndUserInfo.wsUrl + '/anre_contributii_gn/' + that.wsAndUserInfo.userToken.token + '/saveAttachedFileName',
            type: 'post',
            data: jReq,
            headers: ObjTools.mergeObjects(AjaxTools.corsHeaders, {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }),
            success: function (data) {
                if (data.status == 'OK') {
                    that.loadDocumenteContributiiByZona().then(function(){
                        that.animateElementOk('#file_name_' + id_contrib_gn_fisiere);
                    });
                    defer.resolve();
                } else {
                    that.loadDocumenteContributiiByZona().then(function(){
                        that.animateElementErr('#file_name_' + id_contrib_gn_fisiere);
                    });
                    defer.reject("A aparut o eroare");
                }
            },
            error: function(data){
                that.animateElementErr('#file_name_' + id_contrib_gn_fisiere);
                defer.reject("A aparut o eroare");
            }
        });

        return defer.promise();
    },

    startUploadFile: function(id_tip_doc){
        var that = this;
        that.actionsDocumenteContributiiData();
        $("#upload_file_"+id_tip_doc).trigger('click');
    },

    continueUploadFile: function(id_tip_doc){
        var that = this;
        var upload_file = $("#upload_file_" + id_tip_doc).val();

        if (typeof upload_file !== 'undefined' && upload_file !== null && upload_file !== ''){
            var fileData = $("#upload_file_" + id_tip_doc)[0];

            var url = that.wsAndUserInfo.uploadWsUrl + '/anre_contributii_gn/' + that.wsAndUserInfo.userToken.token + '/uploadDocumentContributii';

            var formData = new FormData();

            formData.append('file', fileData.files[0]);
            formData.append('nume', fileData.files[0].name);
            formData.append('id_document', id_tip_doc);
            formData.append('id_contrib_gn', that.id_contrib_gn);
            formData.append('zona', "CONTRIB_GN");

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

                    if (json.status == 'OK') {
                        that.loadDocumenteContributiiByZona();
                    }
                    else {
                        Swal.fire({
                            icon: 'error',
                            html: json.extendedStatus,
                            position:'bottom',
                            focusConfirm: false,
                            confirmButtonText: 'Ok'
                        });

                        that.loadDocumenteContributiiByZona();
                    }
                },
                error: function (err) {
                    var strErr = err;
                },
                complete: function (data) {
                    that.cleanBeforeAddFile();
                }
            });
        }
    },

    cleanBeforeAddFile: function(){
        var that = this;
        $(".upload_file_control").val('');
    },

    loadDocumenteContributiiByZona: function(){
        var that = this;

        var req = {
            "id_contrib_gn": that.id_contrib_gn,
            "zona": "CONTRIB_GN"
        };

        var jReq = JSON.stringify(req);

        var defer = $.Deferred();
        $.ajax({
            url: that.wsAndUserInfo.wsUrl + '/anre_contributii_gn/' + that.wsAndUserInfo.userToken.token + '/getDocumenteContributiiByZona',
            type: 'post',
            data: jReq,
            headers: ObjTools.mergeObjects(AjaxTools.corsHeaders, {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }),
            success: function (data) {
                if (data.status == 'OK') {
                    that.documenteContributiiData = data;
                    that.renderDocumenteContributiiData(data);
                    defer.resolve();
                } else {
                    defer.reject("A aparut o eroare");
                }
            },
            error: function(data){
                defer.reject("A aparut o eroare");
            }
        });

        return defer.promise();
    },

    renderDocumenteContributiiData: function (data) {
        var that = this;

        $("#container_documente").empty();
        var html = that.renderTemplateNonAsync(that, "tmpl_documente_contributii", data);
        $("#container_documente").html(html);
        that.actionsDocumenteContributiiData();

    },

    actionsDocumenteContributiiData: function () {
        var that = this;

        $(".upload_file_control").unbind('change').change(function(){
            var id = $(this).data('id');
            that.continueUploadFile(id);
        });
    },

    animateElementOk: function(elem){
        var that = this;
        var origColor = $(elem).css('color');
        $(elem).stop().animate({"background-color" : "#66DEAC", "color": "white"}, 350).animate({"background-color" : "white", "color": origColor}, 350);
    },

    animateElementErr: function(elem){
        var that = this;
        var origColor = $(elem).css('color');
        $(elem).stop().animate({"background-color" : "#de78a1", "color": "white"}, 350).animate({"background-color" : "white", "color": origColor}, 350);
    },

    updateValoareCalculataRand: function(id,id_contrib_gn,coeficient,valoare_minima,element_id,calculated_element_id,valoare_calculata){
        var that = this;
        $("#"+calculated_element_id).html(valoare_calculata);
        that.animateElementOk("#"+element_id);
        that.animateElementOk("#"+calculated_element_id);
        $("#"+calculated_element_id).number(true, 2, '.', ' ');
    },

    goBack: function () {
        window.history.back();
    },

    backToMaster: function(){


        var that=this;
        var url=  './main-screen-operator-c-gn.html';
        if (FileManager.id_perioada_contabila!=null && FileManager.id_perioada_contabila!=''){
            url+='?&idPerioada='+FileManager.id_perioada_contabila;
        }
        if (FileManager.id_tert!=null && FileManager.id_tert!=''){
            url+='?&idTert='+FileManager.id_tert;
        }
        window.location.href =url;
    }
};


$(document).ready(function () {
    FileManager.init();
});