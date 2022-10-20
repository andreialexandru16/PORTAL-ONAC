var PAGE_NAME = "documente_cont.js";

var FileManager = {
    parametersLoadedOk: false,
    userInfo: null,
    zonaDocumente: '',
    wsAndUserInfo: {},

    // pre-compiled mustache templates
    templates: {},

    /*
     Initialization function.
     */
    resizeIframe:function(){
        $('#resize_iframe', window.parent.document).trigger('click');

    },
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
                            that.loadFormulareData();
                            //that.loadTipDocData();
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
    downloadLink: function (downloadLink) {
        window.top.location.href=downloadLink;
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

                var listaRoluri= that.wsAndUserInfo.listaRoluri;
                if(listaRoluri!=null && listaRoluri!=''){
                    if(!listaRoluri.includes('PORTAL_EXTERN_GN')){
                        $("#tab-gn").hide();
                    }else{
                        $("#tab-gn").show();
                        $("#tab-gn").css("display","block");
                    }
                    if(!listaRoluri.includes('PORTAL_EXTERN_EE')){
                        $("#tab-ee").hide();
                    }else{
                        $("#tab-ee").show();
                        $("#tab-ee").css("display","block");
                    }
                    if(!listaRoluri.includes('PORTAL_EXTERN_ET')){
                        $("#tab-et").hide();
                    }else{
                        $("#tab-et").show();
                        $("#tab-et").css("display","block");
                    }
                }

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
    rulareRaportareGarantare : function (idFisier) {

    var that=this;
    console.log("rulareRaportareGarantare");
        var url = that.wsAndUserInfo.wsUrl + '/jasper/' + that.wsAndUserInfo.userToken.token +'/rulareRaportareJasperByTipDoc/'+idFisier+'?&codtipdoc=RAPORTARE_RECTIFICARE_GRN';

    $.ajax({
        url: url,
        method: 'PUT',
        contentType: 'application/json',
        accept:'application/json',
        success: function (json) {
            if (json.result == "OK") {
                if (json.info != null) {
                    window.open(json.info, '_blank');
                } else {
                    console.log('Raportul nu a putut fi generat!');
                }

            }
        },
        error: function (err) {
            console.log(err);
        }
    });
},

    loadFormulareData: function () {

        var that = this;

        var defer = $.Deferred();
        var url= that.wsAndUserInfo.wsUrl + '/fga/' + that.wsAndUserInfo.userToken.token + '/getInfoRectificativeGarantareByIdUser';
        $.ajax({
            url: url,
            type: 'GET',
            headers: ObjTools.mergeObjects(AjaxTools.corsHeaders, {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }),
            success: function (data) {
                if (data.status == 'OK') {

                    that.renderFormulareData(data);
                    $('.dataTable_generale').DataTable( {
                        "bFilter": true,
                        "dom": 'rtip',
                        "bInfo": false,
                        "searching": false,
                        "pageLength": 10,
                        // language: {
                        //     // info:           "Se afișează _START_ - _END_ din _TOTAL_ intrări",
                        //     // infoEmpty:      "Se afișează 0 - 0 din 0 intrări",
                        //     // lengthMenu:     "Se afișează _MENU_ intrări",
                        //     // zeroRecords:    "Nu au fost găsite înregistrări care să se potrivească",
                        //     // paginate: {
                        //     //     first:      "Prima",
                        //     //     previous:   "Inapoi",
                        //     //     next:       "Inainte",
                        //     //     last:       "Ultima"
                        //     // },
                        //     // search:"Cauta in tabel ..."
                        // }
                    });
                    that.resizeIframe();
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
    deleteDocument: function(id){
        var that=this;
        var PROC_NAME = "FileManager.deleteDocument";

        Swal.fire({
                html:"Sunteti pe cale sa stergeti un document. Sunteti sigur ca doriti sa il stergeti?",
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

                    var url= that.wsAndUserInfo.wsUrl + '/document/' + that.wsAndUserInfo.userToken.token + '/deleteDocumentCont/'+id;


                    $.ajax({
                        url: url,
                        method:'GET',
                        dataType: 'json',
                        contentType: "application/json; charset=utf-8",
                        success: function (data) {
                            if (data.result == 'OK') {

                                Swal.fire({
                                    icon: "info",
                                    html: "A fost sters documentul din cont.",
                                    focusConfirm: false,
                                    confirmButtonText: "Ok"
                                });
                                FileManager.loadFormulareData();

                            } else   if (data.result == 'ERR') {
                                Swal.fire({
                                    icon: "error",
                                    html: data.info,
                                    focusConfirm: false,
                                    confirmButtonText: "Ok"
                                });

                            }
                        }
                    });
                });
    },
        allowOutsideClick: () => !Swal.isLoading()
    });


    },

    loadTipDocData: function () {

        var that = this;

        var defer = $.Deferred();
        var url= that.wsAndUserInfo.wsUrl + '/document/' + that.wsAndUserInfo.userToken.token + '/getTipDocumentListAnre';
        $.ajax({
            url: url,
            type: 'GET',
            headers: ObjTools.mergeObjects(AjaxTools.corsHeaders, {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }),
            success: function (data) {
                if (data.result == 'OK') {

                    that.renderTemplateData(data,"#container_select_tip_document","tmpl_tip_document");
                    that.renderTemplateData(data,"#container_select_tip_document_zona","tmpl_tip_document");
                    $("#container_select_tip_document").trigger("chosen:updated");
                    $("#container_select_tip_document_zona").trigger("chosen:updated");

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
    setZona: function (zona) {
        var that = this;

        that.zonaDocumente=zona;
        $('#resize_iframe', window.parent.document).trigger('click');

    },

    renderFormulareData: function (data) {
        var that = this;

        $("#container_doc_generale").empty();
        var html = that.renderTemplateNonAsync(that, "tmpl_formulare_list", data);
        $("#container_doc_generale").html(html);
    },
    renderTemplateData: function (data,container,template) {
        var that = this;

        $(container).empty();
        var html = that.renderTemplateNonAsync(that, template, data);
        $(container).html(html);


    },
    goBack: function () {
        window.history.back();
    },

    continueUploadFile: function(){

        var that = this;
        var upload_file = $("#input_file").val();

        if (typeof upload_file !== 'undefined' && upload_file !== null && upload_file !== ''){


            var idDocument=$("#container_select_tip_document").val();
            if(idDocument==null || typeof idDocument=='undefined' || idDocument==''){
                $.fancybox.close();
                that.cleanBeforeAddFile();

                Swal.fire({
                    icon: 'error',
                    html: "Selectati tipul de document!",
                    focusConfirm: false,
                    confirmButtonText: 'Ok'
                });
            }
            else{
                var fileData = $("#input_file")[0];

                var url = '/dmsws/anre/uploadDocumentCont';

                var formData = new FormData();
                formData.append('file', fileData.files[0]);
                formData.append('nume', fileData.files[0].name);
                formData.append('valabil_de_la', $("#valabil_de_la").val());
                formData.append('valabil_pana_la', $("#valabil_pana_la").val());
                formData.append('id_document', $("#container_select_tip_document").val());

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
                            that.loadFormulareData();
                            $.fancybox.close();
                            Swal.fire({
                                icon: 'info',
                                html: json.extendedStatus,
                                focusConfirm: false,
                                confirmButtonText: 'Ok'
                            });
                        }
                        else {
                            Swal.fire({
                                icon: 'error',
                                html: "Ne pare rau! A intervenit o problema. Fisierul nu a putut fi salvat!",
                                focusConfirm: false,
                                confirmButtonText: 'Ok'
                            });
                            $.fancybox.close();
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

        }else{
            $.fancybox.close();
            Swal.fire({
                icon: 'error',
                html: "Incarcati fisierul!",
                focusConfirm: false,
                confirmButtonText: 'Ok'
            });
        }
    },
    continueUploadFileZona: function(){


        var that = this;
        var selector="#input_file";
        if(that.zonaDocumente!=null && that.zonaDocumente!=''){
            selector+="_zona";
        }
        var upload_file = $(selector).val();

        if (typeof upload_file !== 'undefined' && upload_file !== null && upload_file !== ''){


            var idDocument=$("#container_select_tip_document_zona").val();
            if(idDocument==null || typeof idDocument=='undefined' || idDocument==''){
                $.fancybox.close();
                that.cleanBeforeAddFile();

                Swal.fire({
                    icon: 'error',
                    html: "Selectati tipul de document!",
                    focusConfirm: false,
                    confirmButtonText: 'Ok'
                });
            }
            else{
                var fileData = $(selector)[0];

                var url = '/dmsws/anre/uploadDocumentCont';

                var formData = new FormData();
                formData.append('file', fileData.files[0]);
                formData.append('nume', fileData.files[0].name);
                if(that.zonaDocumente!=null && that.zonaDocumente!=''){

                    formData.append('valabil_de_la', $("#valabil_de_la_zona").val());
                    formData.append('valabil_pana_la', $("#valabil_pana_la_zona").val());
                    formData.append('nr_anre', $("#nr_anre_zona").val());
                    formData.append('data_anre', $("#data_anre_zona").val());
                    formData.append('nr_document', $("#nr_doc_zona").val());
                    formData.append('data_document', $("#data_doc_zona").val());
                    formData.append('id_document', $("#container_select_tip_document_zona").val());
                    formData.append('zona', that.zonaDocumente);
                }else{
                    formData.append('valabil_de_la', $("#valabil_de_la").val());
                    formData.append('valabil_pana_la', $("#valabil_pana_la").val());
                    formData.append('id_document', $("#container_select_tip_document").val());
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

                        if (json.status == 'OK') {
                            that.loadFormulareData();
                            $.fancybox.close();
                            Swal.fire({
                                icon: 'info',
                                html: json.extendedStatus,
                                focusConfirm: false,
                                confirmButtonText: 'Ok'
                            });
                        }
                        else {
                            Swal.fire({
                                icon: 'error',
                                html: "Ne pare rau! A intervenit o problema. Fisierul nu a putut fi salvat!",
                                focusConfirm: false,
                                confirmButtonText: 'Ok'
                            });
                            $.fancybox.close();
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

        }else{
            $.fancybox.close();
            Swal.fire({
                icon: 'error',
                html: "Incarcati fisierul!",
                focusConfirm: false,
                confirmButtonText: 'Ok'
            });
        }
    },

    continueUploadFileEdit: function(){

        var that = this;
        var upload_file = $("#input_file_edit").val();

        var idDocument=$("#id_tip_document_hidden").val();
        if(idDocument==null || typeof idDocument=='undefined' || idDocument==''){
            $.fancybox.close();
            that.cleanBeforeAddFileEdit();

            Swal.fire({
                icon: 'error',
                html: "Nu exista tip de document!",
                focusConfirm: false,
                confirmButtonText: 'Ok'
            });
        }
        else{
            var fileData = $("#input_file_edit")[0];

            var url = '/dmsws/anre/uploadDocumentContEdit';

            var formData = new FormData();
            if (typeof upload_file !== 'undefined' && upload_file !== null && upload_file !== '') {
                formData.append('file', fileData.files[0]);
                formData.append('nume', fileData.files[0].name);
            }
            formData.append('valabil_de_la', $("#valabil_de_la_edit").val());
            formData.append('valabil_pana_la', $("#valabil_pana_la_edit").val());
            formData.append('id_document', $("#id_tip_document_hidden").val());
            formData.append('id', $("#id_document_cont_hidden").val());

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
                        that.loadFormulareData();
                        $.fancybox.close();
                        Swal.fire({
                            icon: 'info',
                            html: json.extendedStatus,
                            focusConfirm: false,
                            confirmButtonText: 'Ok'
                        });
                    }
                    else {
                        Swal.fire({
                            icon: 'error',
                            html: "Ne pare rau! A intervenit o problema. Fisierul nu a putut fi salvat!",
                            focusConfirm: false,
                            confirmButtonText: 'Ok'
                        });
                        $.fancybox.close();
                    }
                },
                error: function (err) {
                    var strErr = err;
                },
                complete: function (data) {

                    that.cleanBeforeAddFileEdit();
                }
            });
        }

    },
    continueUploadFileEditZona: function(){

        var that = this;
        var upload_file = $("#input_file_edit_zona").val();

        var idDocument=$("#id_tip_document_hidden_zona").val();
        if(idDocument==null || typeof idDocument=='undefined' || idDocument==''){
            $.fancybox.close();
            that.cleanBeforeAddFileEdit();

            Swal.fire({
                icon: 'error',
                html: "Nu exista tip de document!",
                focusConfirm: false,
                confirmButtonText: 'Ok'
            });
        }
        else{
            var fileData = $("#input_file_edit_zona")[0];

            var url = '/dmsws/anre/uploadDocumentContEdit';

            var formData = new FormData();
            if (typeof upload_file !== 'undefined' && upload_file !== null && upload_file !== '') {
                formData.append('file', fileData.files[0]);
                formData.append('nume', fileData.files[0].name);
            }
            formData.append('valabil_de_la', $("#valabil_de_la_edit_zona").val());
            formData.append('valabil_pana_la', $("#valabil_pana_la_edit_zona").val());
            formData.append('id_document', $("#id_tip_document_hidden_zona").val());
            formData.append('id', $("#id_document_cont_hidden_zona").val());
            formData.append('nr_anre', $("#nr_anre_edit_zona").val());
            formData.append('data_anre', $("#data_anre_edit_zona").val());
            formData.append('nr_document', $("#nr_doc_edit_zona").val());
            formData.append('data_document', $("#data_doc_edit_zona").val());
            formData.append('zona', that.zonaDocumente);
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
                        that.loadFormulareData();
                        $.fancybox.close();
                        Swal.fire({
                            icon: 'info',
                            html: json.extendedStatus,
                            focusConfirm: false,
                            confirmButtonText: 'Ok'
                        });
                    }
                    else {
                        Swal.fire({
                            icon: 'error',
                            html: "Ne pare rau! A intervenit o problema. Fisierul nu a putut fi salvat!",
                            focusConfirm: false,
                            confirmButtonText: 'Ok'
                        });
                        $.fancybox.close();
                    }
                },
                error: function (err) {
                    var strErr = err;
                },
                complete: function (data) {

                    that.cleanBeforeAddFileEdit();
                }
            });
        }

    },
    getDocumentInfo: function (id) {



        var that = this;
        var PROC_NAME = "PageManager.getDocumentInfo";
        //$UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);
        var url= that.wsAndUserInfo.wsUrl + '/document/' + that.wsAndUserInfo.userToken.token + '/getDocumentContById/'+id;

        console.log("Ajax Call Start:" + url);

        $.ajax({
            url: url,
            headers: ObjTools.mergeObjects(AjaxTools.corsHeaders, {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }),
            success: function (data) {
                if (data.result == 'OK') {

                    if(data.zona!=null && data.zona!=''){
                        $("#valabil_de_la_edit_zona").val(data.valabilDeLa);
                        $("#valabil_pana_la_edit_zona").val(data.valabilPanaLa);
                        $("#id_tip_document_hidden_zona").val(data.idDocument);
                        $("#id_document_cont_hidden_zona").val(data.id);
                        $("#tip_document_zona").val(data.document);
                        $("#nr_anre_edit_zona").val(data.nrAnre);
                        $("#data_anre_edit_zona").val(data.dataAnre);
                        $("#nr_doc_edit_zona").val(data.nrDoc);
                        $("#data_doc_edit_zona").val(data.dataDoc);

                    }else{
                        $("#valabil_de_la_edit").val(data.valabilDeLa);
                        $("#valabil_pana_la_edit").val(data.valabilPanaLa);
                        $("#id_tip_document_hidden").val(data.idDocument);
                        $("#id_document_cont_hidden").val(data.id);
                        $("#tip_document").val(data.document);
                    }

                }

            }
        });
        //$UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },
    cleanBeforeAddFile: function(){
        var that = this;
        $("#input_file").val('');
        $("#input_file_zona").val('');
    },
    cleanBeforeAddFileEdit: function(){
        var that = this;
        $("#input_file_edit").val('');
        $("#input_file_edit_zona").val('');
    },
    /*
     Function to compile all known mustache templates.
     */
    compileAllTemplates: function () {
        this.templates['tmpl_formulare_list'] = $('#tmpl_formulare_list').html();
        // this.templates['tmpl_formulare_list_gn'] = $('#tmpl_formulare_list_gn').html();
        // this.templates['tmpl_formulare_list_ee'] = $('#tmpl_formulare_list_ee').html();
        // this.templates['tmpl_formulare_list_et'] = $('#tmpl_formulare_list_et').html();
        // this.templates['tmpl_tip_document'] = $('#tmpl_tip_document').html();

        // parseaza toate template-urile
        $.each(this.templates, function (index, template) {
            Mustache.parse(template);
        });
    },
    uploadFileChange: function (id) {

        $("#submit_file_"+id).click();
    }
};


$(document).ready(function () {
    FileManager.init();
});