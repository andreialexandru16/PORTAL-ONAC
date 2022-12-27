var FileManager = {
    parametersLoadedOk: false,
    wsAndUserInfo: {},

    // pre-compiled mustache templates
    templates: {},

    /*
     Initialization function.
     */
    init: function () {
        var that = this;

        // luam info dmsws si user
        that.getWsAndUserInfo().then(
            function(){
                that.compileAllTemplates();
                that.loadSubconturiData();
            }
        );
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
    loadSubconturiData: function () {
        debugger
        var that = this;

        var defer = $.Deferred();
        $.ajax({
          url: '/dmsws/cerericont/getSubconturi',
            success: function (data) {
                if (data.result == 'OK') {

                    that.renderSubconturiData(data);
                  //  FileManager.loadListaLov('#id_rol', '#container_select_id_rol', 'LOV_GRUP_PORTAL');
                    //functii reload liste lov
                    //timp reload after typing, id select, id container, cod lov

                   // FileManager.reloadListaLovAfterStopTyping(2000,'#id_rol', '#container_select_id_rol', 'LOV_GRUP_PORTAL');
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






    prepareToAddSubcont: function () {

        var username = $("#username").val();

        if (username == '' || username == null ) {

            Swal.fire({
                position: 'top',
                icon: "error",
                html: "Completati username-ul utilizatorului pe care doriti sa il asociati.",
                focusConfirm: false,
                confirmButtonText: "Ok"

            });

        } else {

            FileManager.insertSubcont(username);

        }
    },
    prepareToDelGrup: function () {

        var id_rol = $("#id_rol").val();
        var id_user = $("#id_user").val();

        if (id_rol == '' || id_rol == null ) {
            $.fancybox.close();

            Swal.fire({
                position: 'top',
                icon: "error",
                html: "Selectati grupul pe care doriti sa il retrageti.",
                focusConfirm: false,
                confirmButtonText: "Ok"

            });

        } else {

            FileManager.stergereGrupToUser(id_rol,id_user);

        }
    },
    prepareToAddGrup: function () {

        var id_rol = $("#id_rol").val();
        var id_user = $("#id_user").val();

        if (id_rol == '' || id_rol == null ) {
            $.fancybox.close();

            Swal.fire({
                position: 'top',
                icon: "error",
                html: "Selectati grupul pe care doriti sa il asociati.",
                focusConfirm: false,
                confirmButtonText: "Ok"

            });

        } else {

            FileManager.asociereGrupToUser(id_rol,id_user);

        }
    },
    openRoluri: function (id_user,nume) {

        $("#id_user").val(id_user);
        $("#label_id_rol").html("Selectati grupul pentru a fi adaugat / sters utilizatorul "+nume);

    },
    asociereGrupToUser:function(id_rol,id_user){

        var that=this;
        var rolUser={idRol:id_rol, idUser:id_user};
        var jReq=JSON.stringify(rolUser);

        $.ajax({
            url: that.wsAndUserInfo.wsUrl + '/utilizator/' + that.wsAndUserInfo.userToken.token + '/asociereGrupToUser',
            type: 'post',
            data: jReq,
            headers: ObjTools.mergeObjects(AjaxTools.corsHeaders, {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }),
            success: function (data) {
                if (data.result == 'OK') {
                    that.loadSubconturiData();

                    $.fancybox.close();
                    Swal.fire({
                        position: 'top',
                        icon: 'info',
                        html: "Grupul a fost asociat!<br/>",
                        focusConfirm: false,
                        confirmButtonText: 'Ok'
                    });
                } else {
                    $.fancybox.close();

                    Swal.fire({
                        position: 'top',
                        icon: 'error',
                        html: data.info,
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
    },
    stergereGrupToUser:function(id_rol,id_user){

        var that=this;
        var rolUser={idRol:id_rol, idUser:id_user};
        var jReq=JSON.stringify(rolUser);

        $.ajax({
            url: that.wsAndUserInfo.wsUrl + '/utilizator/' + that.wsAndUserInfo.userToken.token + '/stergereGrupToUser',
            type: 'post',
            data: jReq,
            headers: ObjTools.mergeObjects(AjaxTools.corsHeaders, {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }),
            success: function (data) {
                if (data.result == 'OK') {
                    that.loadSubconturiData();

                    $.fancybox.close();
                    Swal.fire({
                        position: 'top',
                        icon: 'info',
                        html: "Utilizatorul a fost eliminat din grup!<br/>",
                        focusConfirm: false,
                        confirmButtonText: 'Ok'
                    });
                } else {
                    $.fancybox.close();

                    Swal.fire({
                        position: 'top',
                        icon: 'error',
                        html: data.info,
                        focusConfirm: false,
                        confirmButtonText: 'Ok'
                    });

                }
            },
            error: function(data){
                $.fancybox.close();

                Swal.fire({
                    position: 'top',
                    icon: 'error',
                    html: "A aparut o eroare!<br/>",
                    focusConfirm: false,
                    confirmButtonText: 'Ok'
                });

            }
        });
    },

    insertSubcont:function(username){

        var utilizator={};
        var jReq=JSON.stringify(utilizator);

        $.ajax({
            url: "/dmsws/users/insertSubcont/"+username,
            type: 'POST',
            contentType: 'application/json',
            accept:'application/json',
            success: function (data) {

                if(data.result=='OK'){

                    if(data.info=='NOT_FOUND'){
                        $.fancybox.close();
                        Swal.fire({
                            position: 'top',
                                icon: "info",
                                html: "Nu exista niciun utilizator activ cu username-ul: "+username +".",
                                focusConfirm: false,
                                confirmButtonText: "Ok"


                    });
                    }
                    else  if(data.info=='ALREADY_EXIST'){
                        $.fancybox.close();
                        Swal.fire({
                            position: 'top',
                                icon: "info",
                                html: "Utilizatorul cu username-ul: "+username +" este deja asociat.",
                                focusConfirm: false,
                                confirmButtonText: "Ok"

                    });
                    }
                    else  if(data.info=='NOT_PERS_FIZ'){
                        $.fancybox.close();
                        Swal.fire({
                            position: 'top',
                            icon: "info",
                            html: "Utilizatorul cu username-ul: "+username +" nu are cont de persoana fizica.",
                            focusConfirm: false,
                            confirmButtonText: "Ok"

                        });
                    }
                    else{
                        $.fancybox.close();
                        Swal.fire({
                                position: 'top',
                                icon: "info",
                                html: "Inregistrare efectuata cu succes.",
                                focusConfirm: false,
                                confirmButtonText: "Ok",
                                onClose: () => {
                                window.location.reload();
                    }

                    });
                    }

                }

            },
            error: function (err) {

                $.fancybox.close();
                Swal.fire({
                        position: 'top',
                        icon: "error",
                        html: "Eroare server. Subcontul nu a putut fi adaugat.",
                        focusConfirm: false,
                        confirmButtonText: "Ok"

            });
            }
        });
    },

    renderSubconturiData: function (subcontData) {
        var that = this;

        var html = $UTIL.renderTemplateNonAsync(that, "tmpl_subconturi", subcontData);
        $("#cl_tickets").html(html);


    },
    goBack: function () {
       window.history.back();
    },
    inactivareCont: function (id,username) {
        Swal.fire({
                position: 'top',
                html:"Sunteti pe cale sa inactivati contul: "+username+". Sunteti sigur ca doriti sa il inactivati?",
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

                    $.ajax({
                        url: "/dmsws/cerericont/inactivareCont/"+id,
                        type: 'POST',
                        contentType: 'application/json',
                        accept:'application/json',
                        success: function (data) {

                            if(data.result=='OK'){
                                if(data.extendedInfo3 == -1){
                                    $.fancybox.close();
                                    Swal.fire({
                                            position: 'top',
                                            icon: "info",
                                            html: "Utilizatorul este deja inactivat.",
                                            focusConfirm: false,
                                            confirmButtonText: "Ok",
                                            onClose: () => {
                                            window.location.reload();
                                }

                                });
                                }else{  $.fancybox.close();
                                    Swal.fire({
                                            position: 'top',
                                            icon: "info",
                                            html: "Utilizatorul a fost inactivat cu succes.",
                                            focusConfirm: false,
                                            confirmButtonText: "Ok",
                                            onClose: () => {
                                            window.location.reload();
                                }

                                });}


                            }

                        },
                        error: function (err) {

                            $.fancybox.close();
                            Swal.fire({
                                position: 'top',
                                icon: "error",
                                html: "Eroare server. Utilizatorul nu a putut fi inactivat.",
                                focusConfirm: false,
                                confirmButtonText: "Ok"

                            });
                        }
                    });

                });
    },
        allowOutsideClick: () => !Swal.isLoading()
    });



    },
    activareCont: function (id,username) {
        Swal.fire({
                position: 'top',
                html:"Sunteti pe cale sa activati contul: "+username+". Sunteti sigur ca doriti sa il activati?",
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

                    $.ajax({
                        url: "/dmsws/cerericont/activareCont/"+id,
                        type: 'POST',
                        contentType: 'application/json',
                        accept:'application/json',
                        success: function (data) {

                            if(data.result=='OK'){
                                if(data.extendedInfo3 == -1){
                                    $.fancybox.close();
                                    Swal.fire({
                                            position: 'top',
                                            icon: "info",
                                            html: "Utilizatorul este deja activat.",
                                            focusConfirm: false,
                                            confirmButtonText: "Ok",
                                            onClose: () => {
                                            window.location.reload();
                                }

                                });
                                }else{  $.fancybox.close();
                                    Swal.fire({
                                            position: 'top',
                                            icon: "info",
                                            html: "Utilizatorul a fost activat cu succes.",
                                            focusConfirm: false,
                                            confirmButtonText: "Ok",
                                            onClose: () => {
                                            window.location.reload();
                                }

                                });}


                            }

                        },
                        error: function (err) {

                            $.fancybox.close();
                            Swal.fire({
                                position: 'top',
                                icon: "error",
                                html: "Eroare server. Utilizatorul nu a putut fi activat.",
                                focusConfirm: false,
                                confirmButtonText: "Ok"

                            });
                        }
                    });

                });
    },
        allowOutsideClick: () => !Swal.isLoading()
    });



    },
    loadListaLov:function(select_id, container_id,lov_code){
        var that=this;
        $.ajax({
            url: '/dmsws/formular_custom/lov/get_values_by_code/'+lov_code,
            success: function (data) {
                if (data.result == 'OK') {

                    that.renderLovData(data,select_id);

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
    },

    renderLovData: function (data,select_id) {
        var that = this;

        var html = that.renderTemplateNonAsync(that, "tmpl_lov", data);
        $(select_id).html(html);
        $(select_id).chosen({
            no_results_text: "Vom reîncărca lista conform textului căutat - momentan nu a fost găsit : "
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

    reloadListaLovAfterStopTyping: function (time,select_id, container_id,lov_code){


        var that=this;
        //setup before functions
        var typingTimer;                //timer identifier
        var doneTypingInterval = time;  //time in ms, 5 second for example

//user is "finished typing," do something
        function doneTyping () {

            var $text = $(container_id+ ' input').val();


            function write_text(input) {
                input.val($text);
            }

            function ajax_load(input) {
                var url = '/dmsws/formular_custom/lov/get_values_by_code_search/'+lov_code;
                url+="?&searchStr="+ $text;
                $.ajax({
                    url:url,
                    success: function (data) {
                        if (data.result == 'OK') {

                            that.renderLovDataSearch(data,input,$text,select_id,container_id );
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
        $(container_id).on('keydown','input', function(){
            clearTimeout(typingTimer);

        });
        $(container_id).on('keyup','input', function(){
            clearTimeout(typingTimer);
            typingTimer = setTimeout(doneTyping, doneTypingInterval);

        });
        //on keydown, clear the countdown
        $(select_id).on('change',  function(){
            clearTimeout(typingTimer);

        });

    },
    renderLovDataSearch: function (data,input,text,select_id,container_id) {
        var that = this;

        var html = that.renderTemplateNonAsync(that, "tmpl_lov", data);
        $(select_id).html(html);
        $(select_id).trigger("chosen:updated");
        $(select_id).chosen({
            no_results_text: "Vom reîncărca lista conform textului căutat - momentan nu a fost găsit : "
        });
        $(container_id+" input").val(text)

    },

    /*
     Function to compile all known mustache templates.
     */
    compileAllTemplates: function () {
        this.templates['tmpl_subconturi'] = $('#tmpl_subconturi').html();
        this.templates['tmpl_lov'] = $('#tmpl_lov').html();

        // parseaza toate template-urile
        $.each(this.templates, function (index, template) {
            Mustache.parse(template);
        });
    }
};

$( '#register_ct' ).submit( function( e ) {
    debugger
    $("#btn_add_contact").prop('disabled','disabled');
    var nume_c = $("#nume_c").val();
    var prenume_c = $("#prenume_c").val();
    var email_c = $("#email_c").val();
    var telefon_c = $("#telefon_c").val();
    var contact_file = $("#contact_file").val();

            var HTML_Width = $("#register_ct").width();
            var HTML_Height = $("#register_ct").height();
            var top_left_margin = 15;
            var PDF_Width = HTML_Width+(top_left_margin*2);
            var PDF_Height = (PDF_Width*1.5)+(top_left_margin*2);
            var canvas_image_width = HTML_Width;
            var canvas_image_height = HTML_Height;

            var totalPDFPages = Math.ceil(HTML_Height/PDF_Height)-1;

            $(window).scrollTop(0);
            $("#register_ct").css("background","white");
            var formData = new FormData( document.querySelector("#register_ct"));
                    $.ajax( {
                        url: '/dmsws/cerericont/addCt',
                        type: 'POST',
                        data: formData,
                        processData: false,
                        contentType: false,
                        beforeSend: function() {
                            $UTIL.waitForLoading();

                        },
                        success: function (resultData) {
                            swal.close();
                            if(resultData == "OK"){
                                Swal.fire({
                                    icon: 'error',
                                    html: "",
                                    focusConfirm: false,
                                    confirmButtonText: 'Ok',
                                });
                            }else if(resultData == "NRER"){
                                Swal.fire({
                                    icon: 'error',
                                    html: "Persoana de contact nu a fost adaugata.Numar maxim de utilizatori activi asociati a fost depasit!",
                                    focusConfirm: false,
                                    confirmButtonText: 'Ok',
                                });
                            }
                            $("#btn_add_contact").removeAttr('disabled');
                            e.preventDefault();

                        },
                        error: function(err) {
//    		    TODO: treat html tag for client side servers with err.responseJSON.status and err.responseJSON.message
                            swal.close();
                            Swal.fire({
                                icon: 'error',
                                html: "<p class='text-danger'><strong>" + err.responseText +"</strong></p>",
                                focusConfirm: false,
                                confirmButtonText: 'Ok',
                            });
                            $("#btn_add_contact").removeAttr('disabled');

                        } } );
    e.preventDefault();

} );

$(document).ready(function () {
    /* Upload Files */
    $(document).find(".file-control").each(function () {
        let currentFile = $(this);
        let uploadText = $(this).attr("upload-text");
        currentFile.after("<div class='cbnp_upload_file form-control'><span>" + uploadText + "</span><i class='fas fa-upload'></i></div>");
    });
    $(document).on("click", ".cbnp_upload_file", function () {
        $(this).prev().trigger("click");
    });
    $(document).on("change", ".file-control", function () {
        let fileName = $(this).val().split("\\");
        let uploadFileName = fileName[fileName.length - 1];
        if ( uploadFileName.length > 20 ) {
            uploadFileName = uploadFileName.substr(0, 20) + "...";
        }
        $(this).next().find("span").text(uploadFileName);
    });
    FileManager.init();
});