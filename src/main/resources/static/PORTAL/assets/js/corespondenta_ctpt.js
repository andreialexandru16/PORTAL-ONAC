var PAGE_NAME = "corespondenta_ctpt.js";
var ID_RASPUNS=null;
var ID_RASPUNS2=null;

var PageManager = {
    zona: null,
    dateInLucru: [],
    dateIstoric: [],

    // pre-compiled mustache templates
    templates: {},

    init: function () {
        var that = this;

        that.mandatoryFunctions().then(
            function () {

            }
        );
    },

    mandatoryFunctions: function () {
        var that = this;
        var defer = $.Deferred();

        // luam info dmsws si user
        that.getWsAndUserInfo().then(
            function () {
                //-------------- initializam template-uri mustache ---------------------
                that.compileAllTemplates();

                // salvam zona
                that.zona = new URLSearchParams(window.location.search).get("zona");

                that.loadDateInLucru();
                that.loadDateIstoric();

                defer.resolve();
            },
            function () {
                defer.reject();
            }
        );

        return defer.promise();
    },

    compileAllTemplates: function () {
        this.templates['tmpl_date_in_lucru'] = $('#tmpl_date_in_lucru').html();
        this.templates['tmpl_date_istoric'] = $('#tmpl_date_istoric').html();

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

    getWsAndUserInfo: function () {
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

    loadDateInLucru: function () {
        var that = this;

        var req = {
            "zona": that.zona,
            "idUtilizator": that.wsAndUserInfo.userToken.userId
        };

        var jReq = JSON.stringify(req);

        var defer = $.Deferred();
        $.ajax({
            url: that.wsAndUserInfo.wsUrl + '/pet_ctr/' + that.wsAndUserInfo.userToken.token + '/getCorespondentaPetCtr',
            type: 'post',
            data: jReq,
            headers: ObjTools.mergeObjects(AjaxTools.corsHeaders, {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }),
            success: function (data) {
                if (data.result == 'OK') {
                    that.dateInLucru = data.corespondentaPetCtrList;
                    that.renderDateInLucru({data: data.corespondentaPetCtrList});
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

    renderDateInLucru: function (data) {
        var that = this;
        var html = that.renderTemplateNonAsync(that, "tmpl_date_in_lucru", data);

        var tblHolder = $('.table_inregistrari_date_in_lucru');
        tblHolder.html('');
        tblHolder.html(html);

        // Setup - add a text input to each footer cell
        $('.dataTable_nosearch_date_in_lucru tfoot th').each( function () {

            var title = $(this).text();
            if (title.toLowerCase() == "petent" || title.toLowerCase() == "nr. anre" ||
                title.toLowerCase() == "data anre" || title.toLowerCase() == "nr. inregistrare operator economic" ||
                title.toLowerCase() == "data inregistrare operator economic") {
                $(this).html('<input type="text" placeholder="" />');
            } else {
                $(this).text("");
            }
            // $(this).html( '<input type="text" placeholder="Search '+title+'" />' );
        } );

        $('.dataTable_nosearch_date_in_lucru').DataTable( {
            "searching": true,
            "pageLength": 5,
            "lengthMenu": [ 5, 25, 50, 100 ],
            'stateSave': true,
            initComplete: function () {
                // Apply the search
                this.api().columns().every( function () {
                    var that = this;

                    $( 'input', this.footer() ).on( 'keyup change clear', function () {
                        if ( that.search() !== this.value ) {
                            that
                                .search( this.value )
                                .draw();
                        }
                    } );
                } );

                    var r = $('.dataTable_nosearch_date_in_lucru tfoot tr');
                    r.find('th').each(function(){
                        $(this).css('padding', 8);
                    });
                    $('.dataTable_nosearch_date_in_lucru thead').append(r);
                    $('#search_0').css('text-align', 'center');
            },
            // initComplete: function () {
            //     var r = $('.dataTable_nosearch_date_in_lucru tfoot tr');
            //     r.find('th').each(function(){
            //         $(this).css('padding', 8);
            //     });
            //     $('.dataTable_nosearch_date_in_lucru thead').append(r);
            //     $('#search_0').css('text-align', 'center');
            // },
            language: {
                info:           "Se afișează _START_ - _END_ din _TOTAL_ intrări",
                infoEmpty:      "Se afișează 0 - 0 din 0 intrări",
                lengthMenu:     "Se afișează _MENU_ intrări",
                zeroRecords:    "Nu au fost găsite înregistrări care să se potrivească",
                infoFiltered:   "(filtrate dintr-un total de _MAX_)",
                paginate: {
                    first:      "Prima",
                    previous:   "Inapoi",
                    next:       "Inainte",
                    last:       "Ultima"
                },
                search:"Cauta in tabel ..."
            }
        });

        $('.dataTable_nosearch_date_in_lucru').on('draw.dt', function() {
            that.loadPageActions();
        });

        that.loadPageActions();
    },

    loadPageActions: function(){
        var that = this;

        $(".upload_file_control_principal").unbind('change').change(function(){
            var id = $(this).data('id');
            that.continueUploadFilePrincipal(id);
          //  Swal.showLoading();

        });

        $(".upload_file_control_secundar").unbind('change').change(function(){
            var id = $(this).data('id');
            that.continueUploadFileSecundar(id);
           // Swal.showLoading();
            waitForLoading();
        });
    },

    loadDateIstoric: function () {
        var that = this;

        var req = {
            "zona": that.zona,
            "idUtilizator": that.wsAndUserInfo.userToken.userId
        };

        var jReq = JSON.stringify(req);

        var defer = $.Deferred();
        $.ajax({
            url: that.wsAndUserInfo.wsUrl + '/pet_ctr/' + that.wsAndUserInfo.userToken.token + '/getCorespondentaPetCtrIstoric',
            type: 'post',
            data: jReq,
            headers: ObjTools.mergeObjects(AjaxTools.corsHeaders, {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }),
            success: function (data) {
                if (data.result == 'OK') {
                    that.dateIstoric = data.corespondentaPetCtrList;
                    that.renderDateIstoric({data: data.corespondentaPetCtrList});
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

    renderDateIstoric: function (data) {
        var that = this;
        var html = that.renderTemplateNonAsync(that, "tmpl_date_istoric", data);

        var tblHolder = $('.table_inregistrari_date_istoric');
        tblHolder.html('');
        tblHolder.html(html);

        // Setup - add a text input to each footer cell
        $('.dataTable_nosearch_date_istoric tfoot th').each( function () {

            var title = $(this).text();
            if (title.toLowerCase() == "petent" || title.toLowerCase() == "nr. anre" ||
                title.toLowerCase() == "data anre" || title.toLowerCase() == "nr. inregistrare operator economic" ||
                title.toLowerCase() == "data inregistrare operator economic" || title.toLowerCase() == "data" || title.toLowerCase() == "nr") {
                $(this).html('<input type="text" placeholder="" />');
            } else {
                $(this).text("");
            }
            // $(this).html( '<input type="text" placeholder="Search '+title+'" />' );
        } );

        $('.dataTable_nosearch_date_istoric').DataTable( {
            "searching": true,
            "pageLength": 5,
            "lengthMenu": [ 5, 25, 50, 100 ],
            'stateSave': true,
            initComplete: function () {
                // Apply the search
                this.api().columns().every( function () {
                    var that = this;

                    $( 'input', this.footer() ).on( 'keyup change clear', function () {
                        if ( that.search() !== this.value ) {
                            that
                                .search( this.value )
                                .draw();
                        }
                    } );
                } );

                var r = $('.dataTable_nosearch_date_istoric tfoot tr');
                r.find('th').each(function(){
                    $(this).css('padding', 8);
                });
                $('.dataTable_nosearch_date_istoric thead').append(r);
                $('#search_0').css('text-align', 'center');
            },
            language: {
                info:           "Se afișează _START_ - _END_ din _TOTAL_ intrări",
                infoEmpty:      "Se afișează 0 - 0 din 0 intrări",
                lengthMenu:     "Se afișează _MENU_ intrări",
                zeroRecords:    "Nu au fost găsite înregistrări care să se potrivească",
                infoFiltered:   "(filtrate dintr-un total de _MAX_)",
                paginate: {
                    first:      "Prima",
                    previous:   "Inapoi",
                    next:       "Inainte",
                    last:       "Ultima"
                },
                search:"Cauta in tabel ..."
            }
        });
    },

    startUploadFilePrincipal: function(id){
        var that = this;
        $("#upload_fisier_principal_"+id).trigger('click');
    },

    continueUploadFilePrincipal: function(id){

        var that = this;
        var upload_file = $("#upload_fisier_principal_" + id).val();

            if (typeof upload_file !== 'undefined' && upload_file !== null && upload_file !== ''){
                var fileData = $("#upload_fisier_principal_" + id)[0];
                var fileName =fileData.files[0].name;
                var fileExtension = "";
                if (fileName.lastIndexOf(".") > 0) {
                    fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length);
                }
                if(fileExtension.toLowerCase()=='pdf' || fileExtension.toLowerCase()=='doc' || fileExtension.toLowerCase()=='docx' ) {
                    waitForLoading();

                    var url = that.wsAndUserInfo.uploadWsUrl + '/pet_ctr/' + that.wsAndUserInfo.userToken.token + '/uploadDocumentPrincipal';

                    var formData = new FormData();

                    formData.append('file', fileData.files[0]);
                    formData.append('nume', fileData.files[0].name);
                    formData.append('zona', that.zona);
                    formData.append('id', id);

                    $.ajax({
                        url: url,
                        method: 'POST',
                        type: 'POST',
                        data: formData,
                        enctype: 'multipart/form-data',
                        processData: false,
                        contentType: false,
                        dataType: "json",
                        headers: ObjTools.mergeObjects(AjaxTools.corsHeaders, {
                            'Accept': 'application/json'
                        }),
                        success: function (json) {
                            if (json.result == 'OK') {
                                that.loadDateInLucru();
                            }
                            else {
                                Swal.fire({
                                    icon: 'error',
                                    html: "Ne pare rau! A intervenit o problema. Fisierul nu a putut fi salvat!",
                                    focusConfirm: false,
                                    confirmButtonText: 'Ok'
                                });

                                that.loadDateInLucru();
                            }
                        },
                        error: function (err) {
                            var strErr = err;
                        },
                        complete: function (data) {
                            Swal.close();
                            that.cleanBeforeAddFilePrincipal();
                        }
                    });
                }
                else{
                    Swal.fire({
                        icon: 'info',
                        html: "Extensie neacceptata! Va rugam sa incarcati un fisier care sa aiba una dintre urmatoarele extensii: pdf, doc sau docx.",
                        focusConfirm: false,
                        confirmButtonText: 'Ok'
                    });
                }
            }

    },

    cleanBeforeAddFilePrincipal: function(){
        var that = this;
        $(".upload_file_control_principal").val('');
    },


    deleteFilePrincipal: function (id) {
        var that = this;

        var req = {
            "idRaspunsLinie": id
        };

        var jReq = JSON.stringify(req);

        var defer = $.Deferred();
        $.ajax({
            url: that.wsAndUserInfo.wsUrl + '/pet_ctr/' + that.wsAndUserInfo.userToken.token + '/deleteDocumentPrincipal',
            type: 'post',
            data: jReq,
            headers: ObjTools.mergeObjects(AjaxTools.corsHeaders, {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }),
            success: function (data) {
                if (data.result == 'OK') {
                    that.loadDateInLucru();
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

    startUploadFileSecundar: function(id){
        var that = this;
        $("#upload_fisier_secundar_"+id).trigger('click');
    },

    continueUploadFileSecundar: function(id){
        var that = this;
        var upload_file = $("#upload_fisier_secundar_" + id).val();

        if (typeof upload_file !== 'undefined' && upload_file !== null && upload_file !== ''){
            var fileData = $("#upload_fisier_secundar_" + id)[0];

            var url = that.wsAndUserInfo.uploadWsUrl + '/pet_ctr/' + that.wsAndUserInfo.userToken.token + '/uploadDocumentSecundar';

            var formData = new FormData();

            formData.append('file', fileData.files[0]);
            formData.append('nume', fileData.files[0].name);
            formData.append('zona', that.zona);
            formData.append('id', id);

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
                    if (json.result == 'OK') {
                        that.loadDateInLucru();
                    }
                    else {
                        Swal.fire({
                            icon: 'error',
                            html: "Ne pare rau! A intervenit o problema. Fisierul nu a putut fi salvat!",
                            focusConfirm: false,
                            confirmButtonText: 'Ok'
                        });

                        that.loadDateInLucru();
                    }
                },
                error: function (err) {
                    var strErr = err;
                },
                complete: function (data) {
                    Swal.close();
                    that.cleanBeforeAddFileSecundar();
                }
            });
        }
    },

    cleanBeforeAddFileSecundar: function(){
        var that = this;
        $(".upload_file_control_secundar").val('');
    },

    deleteFileSecundar: function (id) {
        var that = this;

        var req = {
            "idRaspunsLinie": id
        };

        var jReq = JSON.stringify(req);

        var defer = $.Deferred();
        $.ajax({
            url: that.wsAndUserInfo.wsUrl + '/pet_ctr/' + that.wsAndUserInfo.userToken.token + '/deleteDocumentSecundar',
            type: 'post',
            data: jReq,
            headers: ObjTools.mergeObjects(AjaxTools.corsHeaders, {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }),
            success: function (data) {
                if (data.result == 'OK') {
                    that.loadDateInLucru();
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

    prepareFinish: function (id) {
        var that = this;

        ID_RASPUNS = id;
        that.cleanNrDataInregistrare();
    },

    prepareSaveNrData: function (id) {
        var that = this;

        ID_RASPUNS2 = id;
        that.cleanNrDataInregistrare2();
    },

    prepareEditNrData: function (id) {
        var that = this;
        that.cleanNrDataInregistrare2();
        ID_RASPUNS2 = id;

        for (var i=0; i < that.dateInLucru.length; i++){
            if(that.dateInLucru[i].id==id){
                $('#nr_inreg_operator2').val(that.dateInLucru[i].nrDocExtern2);
                $('#data_inreg_operator2').val(that.dateInLucru[i].dataDocExtern2);
                break;
            }
        }
    },

    cleanNrDataInregistrare: function () {
        $('#nr_inreg_operator').val('');
        $('#data_inreg_operator').val('');
     },

    cleanNrDataInregistrare2: function () {
        $('#nr_inreg_operator2').val('');
        $('#data_inreg_operator2').val('');
    },

    finish: function () {

        var that = this;

        var id = ID_RASPUNS;

        if (id !== null && typeof id !== 'undefined') {
        waitForLoading();

        var nr_inregistrare = $('#nr_inreg_operator').val();
        var data_inregistrare = $('#data_inreg_operator').val();

        var req = {
            "id": id,
            "nrDocExtern": nr_inregistrare,
            "dataDocExtern": data_inregistrare
        };

        var jReq = JSON.stringify(req);

        var defer = $.Deferred();
        $.ajax({
            url: that.wsAndUserInfo.wsUrl + '/pet_ctr/' + that.wsAndUserInfo.userToken.token + '/finalizeazaRaspuns',
            type: 'post',
            data: jReq,
            headers: ObjTools.mergeObjects(AjaxTools.corsHeaders, {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }),
            success: function (data) {
                $.fancybox.close();
                if (data.result == 'OK') {
                    that.loadDateInLucru();
                    that.loadDateIstoric();
                    that.cleanNrDataInregistrare();
                    ID_RASPUNS = null;

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
                $.fancybox.close();
                Swal.fire({
                    position: 'top',
                    icon: 'error',
                    html: "A aparut o eroare!<br/>",
                    focusConfirm: false,
                    confirmButtonText: 'Ok'
                });

                defer.reject("A aparut o eroare");
            },
            complete: function(data){
                Swal.close();
            }
        });

        return defer.promise();
    }
   },

    saveNrData: function () {

        var that = this;

        var id = ID_RASPUNS2;

        if (id !== null && typeof id !== 'undefined') {


            var nr_inregistrare = $('#nr_inreg_operator2').val();
            var data_inregistrare = $('#data_inreg_operator2').val();

            var req = {
                "id": id,
                "nrDocExtern": nr_inregistrare,
                "dataDocExtern": data_inregistrare
            };

            var jReq = JSON.stringify(req);

            var defer = $.Deferred();
            $.ajax({
                url: that.wsAndUserInfo.wsUrl + '/pet_ctr/' + that.wsAndUserInfo.userToken.token + '/saveNrData',
                type: 'post',
                data: jReq,
                headers: ObjTools.mergeObjects(AjaxTools.corsHeaders, {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }),
                success: function (data) {
                    $.fancybox.close();
                    if (data.result == 'OK') {
                        that.loadDateInLucru();
                        that.loadDateIstoric();
                        that.cleanNrDataInregistrare2();
                        ID_RASPUNS2 = null;

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
                    $.fancybox.close();
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
    }

};


$(document).ready(function () {
    PageManager.init();
});

function waitForLoading(){

    Swal.fire({
            title:"Va rugam asteptati",
            showConfirmButton:true,
            allowOutsideClick: false,
            position:'top',
            onBeforeOpen: () => {
            Swal.showLoading()

},

});
};


