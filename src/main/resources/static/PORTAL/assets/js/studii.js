var PAGE_NAME = "studii.js";
var MOD_STUDII = null;
var ID_STUDII = null;
var idFisierCerere = new URLSearchParams(window.location.search).get('idFisierCerere');
var fisiereIncarcate = [];
var PageManager = {
    myDrop: null,
    wsUrl: null,
    wsToken: null,
    templates: {},
    listaInregistrari: {},
    listaCautare: {},
    nrPagina: 0,
    nrMaxPages: 4,
    numPerPage: 10,
    COD_LOV_DEPARTAMENT: "LOV_DEPARTAMENTE_CONTACTE",
    COD_LOV_NIVEL: "LOV_NIVEL",
    COD_LOV_UNIVERSITATE: "LOV_UNIVERSITATE",
    COD_LOV_LOC_UNIVERSITATE: "LOV_LOC_UNIVERSITATE",
    COD_LOV_FACULTATE: "LOV_FACULTATE",
    COD_LOV_SPECIALITATE: "LOV_SPECIALITATE",
    COD_LOV_PROGRAMA: "LOV_PROGRAMA",
    COD_FIRMA_AUTORIZATA: "LOV_FIRMA_AUTORIZATA",
    COD_LOV_GRAD: "LOV_GRAD_TARIF_GN_ALL",
    COD_LOV_ID_GRAD: "LOV_GRADE_VECHIME",
    COD_LOV_TIP_CURS: "LOV_TIP_CURS",
    cleanBeforeAddFile: function () {
        $("#doc_file").val("");

    },
    trimiteCompletari:function(){
        var that = this;

        Swal.fire({
            icon: 'info',
            html: "Sunteti sigur ca doriti sa trimiteti completarile?",
            position:'bottom',
            focusConfirm: false,
            showCancelButton: true,
            cancelButtonText: 'Nu',

            confirmButtonText: 'Da, continua'

        }).then((result) => {
            /* Read more about isConfirmed, isDenied below */
            if (result.value) {
            that.confirmTrimiteCompletari()
        } else if(result.dismiss=='cancel') {
            swal.close();
        }

    });

    },

    confirmTrimiteCompletari: function(){
        var that = this;
        var req = {
            idFisierCerere: idFisierCerere,
            fisiereIncarcate:fisiereIncarcate
        };
        var url = "/dmsws/anre/trimiteCompletari/";
        var jReq=JSON.stringify(req);
        console.log("Ajax Call Start:" + url);

        $.ajax({
            type: 'POST',
            url: url,
            data: jReq,
            contentType: "application/json",
            success: function (data) {
                if (data.result == 'OK') {
                    debugger;
                    Swal.fire({
                        position:'top',
                        icon: "info",
                        html: "Completarile au fost trimise!",
                        focusConfirm: false,
                        confirmButtonText: "Ok",
                        onClose: () => {
                        window.top.location.href=$UTIL.WORDPRESS_URL+ '/';
                    // window.open($UTIL.WORDPRESS_URL+ '/autentificare', '_self');

                }
                });
                }else{
                    Swal.fire({
                        position:'top',
                        icon: "info",
                        html: data.extendedInfo,
                        focusConfirm: false,
                        confirmButtonText: "Ok"

                });
                }

            }
        });
    },

    redirectCereri : function(){
        var that=this;
        var url = '/dmsws/utilizator/getSysParam/REDIRECT_CERERI_PORTAL';
        $.ajax({
            url: url,
            success: function (data) {
                if (data.result == 'OK') {

                    window.location.replace(data.descriere);
                }
            }
        });
    },
    init: function () {

        var PROC_NAME = "PageManager.init";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);
        //-------------- se apeleaza obligatoriu la initializarea paginii---------------------
        if(idFisierCerere){
            $("#alte_documente_tab").css("display","block");
        }
        this.mandatoryFunctions();

        //apelam reconstructie paginare & afisare rezultate
        PageManager.afiseazaInregistrari();
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);


    },

    cautare: function () {

        var PROC_NAME = "PageManager.cautare";
        var searchStr = $('#search_box').val();
        PageManager.buildPaginationCautare(searchStr).then(function () {
            PageManager.getListaInregistrariCautare(searchStr);
        });


        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },
    adaugaContact: function () {
        debugger;
        var that = this;
        var PROC_NAME = "PageManager.adaugaStudii";
        var err="";
        var id_nivel = $("#container_select_nivel").val();
        var nr_serie_data = $("#nr_serie_data").val();
        var universitate = $("#container_select_universitate").val();
        var loc_universitate = $("#container_select_loc_universitate").val();
        var specialitate = $("#container_select_specialitate").val();
        var programa = $("#container_select_programa").val();

        var formData = new FormData();
        var fileData = $('#doc_file')[0];
        formData.append('file', fileData.files[0]);
        formData.append('id', ID_STUDII);
        formData.append('id_nivel', id_nivel);
        formData.append('nr_serie_data', nr_serie_data);
        formData.append('universitate', universitate);
        formData.append('loc_universitate', loc_universitate);
        formData.append('specialitate', specialitate);
        formData.append('programa', programa);
        if (id_nivel == null || id_nivel == '' || id_nivel == undefined) {
            err=err+"<br>Va rugam selectati nivelul de studii!";
        }
        if (specialitate == null || specialitate == '' || specialitate == undefined) {
            err=err+"<br>Va rugam selectati specialitatea!";
        }

        //TODO de preluat valorile dinamic
        if (specialitate != '4' && specialitate != '12'){
            if ($('#doc_file')[0].files.length == 0) {
                err=err+"<br>Va rugam incarcati un fisier!";
            }
        }


        if(err!=""){
            Swal.fire({
                icon: 'error',
                html: err,
                focusConfirm: false,
                confirmButtonText: 'Ok',

            })
        }else {
            $UTIL.waitForLoading();

            var url = "/dmsws/anre/uploadFileToStudii/";


            $.ajax({
                url: url,
                type: 'POST',
                data: formData,
                enctype: 'multipart/form-data',
                processData: false,
                contentType: false,
                success: function (json) {
                    swal.close();
                    $.fancybox.close();

                    if (json == 'OK') {
                        if(idFisierCerere){
                            fisiereIncarcate.push($("#doc_file")[0].files[0].name);
                        }
                        Swal.fire({
                            icon: 'success',
                            html: "Studiile a fost incarcate!",
                            focusConfirm: false,
                            confirmButtonText: 'Ok'


                        }).then(function () {
                            PageManager.afiseazaInregistrari();
                        });
                    }
                    else {
                        Swal.fire({
                            icon: 'error',
                            html: "Ne pare rau! A intervenit o problema. Fisierul nu a putut fi salvat!",
                            focusConfirm: false,
                            confirmButtonText: 'Ok'

                        }).then(function () {
                            PageManager.afiseazaInregistrari();
                        });
                    }


                },
                error: function (err) {
                    swal.close();
                    $.fancybox.close();

                    var strErr = Util.getAjaxErrorMessage(err);
                    Util.alert(strErr, "ERROR");
                },
                complete: function (data) {
                    PageManager.cleanBeforeAddFile();

                }
            });


        }
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },
    adaugaCurs: function () {

        var that = this;
        var PROC_NAME = "PageManager.adaugaStudii";
        var err="";
        var firma = $("#firma").val();
        var grad = $("#container_select_grad").val();
        var data_certificat_curs = $("#data_certificat_curs").val();
        var numar_certificat_curs = $("#numar_certificat_curs").val();
        var tip_curs = $("#tip_curs").val();

        var formData = new FormData();
        var fileData = $('#doc_curs')[0];
        formData.append('file', fileData.files[0]);
        formData.append('firma', firma);
        formData.append('id_grad', grad);
        formData.append('data_certificat_curs', data_certificat_curs);
        formData.append('numar_certificat_curs', numar_certificat_curs);
        formData.append('tip_curs', tip_curs);

        if ($('#doc_curs')[0].files.length == 0) {
            err=err+"<br>Va rugam incarcati un fisier!";
        }
        if (grad == null || grad == '' || grad == undefined) {
            err=err+"<br>Va rugam selectati gradul!"
        }
        if (tip_curs == null || tip_curs == '' || tip_curs == undefined) {
            err=err+"<br>Va rugam selectati tipul cursului!";
        }
        if(err!=""){
            Swal.fire({
                icon: 'error',
                html: err,
                focusConfirm: false,
                confirmButtonText: 'Ok',

            })
        }
        else {
            $UTIL.waitForLoading();

            var url = "/dmsws/anre/uploadFileToCursuri/";


            $.ajax({
                url: url,
                type: 'POST',
                data: formData,
                enctype: 'multipart/form-data',
                processData: false,
                contentType: false,
                success: function (json) {
                    $.fancybox.close();
                    swal.close();
                    if (json == 'OK') {
                        if(idFisierCerere){
                            fisiereIncarcate.push($("#doc_curs")[0].files[0].name);
                        }
                        Swal.fire({
                            icon: 'success',
                            html: "Fisierul a fost incarcat!",
                            focusConfirm: false,
                            confirmButtonText: 'Ok',


                        }).then(function () {
                            PageManager.afiseazaInregistrari();
                        });
                    }
                    else {
                        Swal.fire({
                            icon: 'error',
                            html: "Ne pare rau! A intervenit o problema. Fisierul nu a putut fi salvat!",
                            focusConfirm: false,
                            confirmButtonText: 'Ok',

                        }).then(function () {
                            PageManager.afiseazaInregistrari();
                        });
                    }


                },
                error: function (err) {
                    $.fancybox.close();
                    swal.close();
                    var strErr = Util.getAjaxErrorMessage(err);
                    Util.alert(strErr, "ERROR");
                },
                complete: function (data) {
                    PageManager.cleanBeforeAddFile();

                }
            });
            // $.ajax({
            //     url: url,
            //     method:'POST',
            //     dataType: 'json',
            //     contentType: "application/json; charset=utf-8",
            //     data:jReq,
            //     success: function (data) {
            //         if (data.result == 'OK') {
            //             $.fancybox.close();
            //
            //
            //             Swal.fire({
            //                 icon: "info",
            //                 html: "Au fost actualizate studiile.",
            //                 focusConfirm: false,
            //                 confirmButtonText: "Ok"
            //             });
            //             PageManager.afiseazaInregistrari();
            //
            //         } else   if (data.result == 'ERR') {
            //             Swal.fire({
            //                 icon: "error",
            //                 html: data.info,
            //                 focusConfirm: false,
            //                 confirmButtonText: "Ok"
            //             });
            //
            //         }
            //     }
            // });

        }
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },
    adaugaAdeverinta: function () {

        $UTIL.waitForLoading();
        var that = this;
        var PROC_NAME = "PageManager.adaugaAdeverinta";
        var err="";
        var numar_adeverinte = $("#numar_adeverinte").val();
        var data_adeverinta = $("#data_adeverinta").val();
        var id_grad_vechime = $("#container_select_grad_vechime").val();
        var id_tip_adeverinta = $("#container_select_tip_adeverinta").val();
        var data_inceput = $("#data_inceput").val();
        var data_sfarsit = $("#data_sfarsit").val();
        var id_firma = $("#id_firma").val();

        var formData = new FormData();
        var fileData = $('#doc_adeverinta')[0];
        formData.append('file', fileData.files[0]);
        formData.append('numar_adeverinte', numar_adeverinte);
        formData.append('data_adeverinta', data_adeverinta);
        formData.append('id_grad_vechime', id_grad_vechime);
        formData.append('id_tip_adeverinta', id_tip_adeverinta);
        formData.append('data_inceput', data_inceput);
        formData.append('data_sfarsit', data_sfarsit);
        formData.append('id_firma', id_firma);


        var datePartsPanaLa = data_sfarsit.split(".");
        var datePartsDeLa = data_inceput.split(".");

// month is 0-based, that's why we need dataParts[1] - 1
        var dateObjectPanaLa = new Date(+datePartsPanaLa[2], datePartsPanaLa[1] - 1, +datePartsPanaLa[0]);
        var dateObjectDeLa = new Date(+datePartsDeLa[2], datePartsDeLa[1] - 1, +datePartsDeLa[0]);


        if (data_inceput == null || data_inceput == '' || data_inceput == undefined) {
            err=err+"<br>Va rugam completati data de inceput!";

        }
        if (data_sfarsit == null || data_sfarsit == '' || data_sfarsit == undefined) {
            err=err+"<br>Va rugam completati data de sfarsit!";

        }
        if(dateObjectPanaLa<dateObjectDeLa){
            err=err+"<br>Data de inceput trebuie sa fie mai mica decat data de sfarsit";
        }
        if ($('#doc_adeverinta')[0].files.length == 0) {
            err=err+"<br>Va rugam sa incarcati un fisier!";
        }

        if (id_firma == null || id_firma == '' || id_firma == undefined) {
            err=err+"<br>Va rugam selectati firma!";

        }
        if(err!=""){
            Swal.fire({
                icon: 'error',
                html: err,
                focusConfirm: false,
                confirmButtonText: 'Ok',

            })
        }else {

            var url = "/dmsws/anre/uploadFileToAdeverinte/";


            $.ajax({
                url: url,
                type: 'POST',
                data: formData,
                enctype: 'multipart/form-data',
                processData: false,
                contentType: false,
                success: function (json) {
                    swal.close();
                    $.fancybox.close();
                    if (json == 'OK') {
                        if(idFisierCerere){
                            fisiereIncarcate.push($("#doc_adeverinta")[0].files[0].name);
                        }
                        Swal.fire({
                            icon: 'success',
                            html: "Fisierul a fost incarcat!",
                            focusConfirm: false,
                            confirmButtonText: 'Ok',


                        }).then(function () {
                            PageManager.afiseazaInregistrari();
                        });
                    }
                    else {
                        Swal.fire({
                            icon: 'error',
                            html: "Ne pare rau! A intervenit o problema. Fisierul nu a putut fi salvat!",
                            focusConfirm: false,
                            confirmButtonText: 'Ok',

                        }).then(function () {
                            PageManager.afiseazaInregistrari();
                        });
                    }


                },
                error: function (err) {
                    swal.close();
                    $.fancybox.close();

                    var strErr = Util.getAjaxErrorMessage(err);
                    Util.alert(strErr, "ERROR");
                },
                complete: function (data) {
                    PageManager.cleanBeforeAddFile();

                }
            });


        }
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },

    getInfoRandNou: function () {

        var that = this;
        var PROC_NAME = "PageManager.getInfoRandNou";

        var id_nivel = $("#container_select_nivel").val();
        var nr_serie_data = $("#nr_serie_data").val();
        var universitate = $("#container_select_universitate").val();
        var loc_universitate = $("#container_select_loc_universitate").val();
        var specialitate = $("#container_select_specialitate").val();
        var programa = $("#container_select_programa").val();


        var objRand = {
            id_nivel: id_nivel,
            nr_serie_data: nr_serie_data,
            universitate: universitate,
            loc_universitate: loc_universitate,
            programa: programa,
            specialitate: specialitate

        };
        return objRand;

        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },
    prepareOpenRandNou: function () {

        $('#resize_iframe', window.parent.document).trigger('click');
        var that = this;
        var PROC_NAME = "PageManager.prepareOpenRandNou";


        var url = "/dmsws/lov/values_by_code/" + PageManager.COD_LOV_DEPARTAMENT;
        $.ajax({
            url: url,
            success: function (data) {
                if (data.result == 'OK') {

                    if (data.lov != null && data.lov.length != 0) {

                        //apelam render template pentru a popula tabelul cu rezultatele obtinute
                        that.renderTemplate('tmpl_departament', {data: data.lov}).then(function (html) {
                            var tblHolder = $('#container_select_departament');
                            tblHolder.html(html);

                            $("#container_select_departament").trigger("chosen:updated");

                        }, function () {
                            that.alert('Unable to render template', 'ERROR');
                        });


                    }
                }
            }
        });

        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },
    afiseazaInregistrari: function () {
        var PROC_NAME = "PageManager.afiseazaInregistrari";


        //apelam reconstructie paginare & afisare rezultate
        PageManager.getListaInregistrari();

        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },
    resetDocumenteCerere: function(){
        $(".btn_upload_doc").html('<span>Încarcă documentul</span><i class="icn_upload"></i>');
    },
    adaugaDocAnexat:function(){
        $UTIL.waitForLoading();
        var that = this;
        var PROC_NAME = "PageManager.adaugaDocAnexat";
        var err="";

        var formData = new FormData();
        var fileData = $('#doc_anexat')[0];
        formData.append('file', fileData.files[0]);
        formData.append('idFisierCerere', idFisierCerere);


        if ($('#doc_anexat')[0].files.length == 0) {
            err=err+"<br>Va rugam sa incarcati un fisier!";
        }

        if(err!=""){
            Swal.fire({
                icon: 'error',
                html: err,
                focusConfirm: false,
                confirmButtonText: 'Ok',

            })
        }else {

            var url = "/dmsws/anre/uploadFileToDocAnexat/";


            $.ajax({
                url: url,
                type: 'POST',
                data: formData,
                enctype: 'multipart/form-data',
                processData: false,
                contentType: false,
                success: function (json) {
                    swal.close();
                    $.fancybox.close();
                    if (json == 'OK') {
                        if(idFisierCerere){
                            fisiereIncarcate.push($("#doc_anexat")[0].files[0].name);
                        }
                        Swal.fire({
                            icon: 'success',
                            html: "Fisierul a fost incarcat!",
                            focusConfirm: false,
                            confirmButtonText: 'Ok',


                        }).then(function () {
                            PageManager.afiseazaInregistrari();
                        });
                    }
                    else {
                        Swal.fire({
                            icon: 'error',
                            html: "Ne pare rau! A intervenit o problema. Fisierul nu a putut fi salvat!",
                            focusConfirm: false,
                            confirmButtonText: 'Ok',

                        }).then(function () {
                            PageManager.afiseazaInregistrari();
                        });
                    }


                },
                error: function (err) {
                    swal.close();
                    $.fancybox.close();

                    var strErr = Util.getAjaxErrorMessage(err);
                    Util.alert(strErr, "ERROR");
                },
                complete: function (data) {
                    PageManager.cleanBeforeAddFile();

                }
            });


        }
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },
    getListaInregistrari: function (jReq) {

        var that = this;
        var PROC_NAME = "PageManager.getListaInregistrari";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);

        var url = "/dmsws/anre/getInregistrariStudii";
        var urlAdeverinte = "/dmsws/anre/getInregistrariAdeverinte";
        var urlCursuri = "/dmsws/anre/getInregistrariCursuri";
        if(idFisierCerere){
            var urlDocumente = "/dmsws/anre/getInregistrariDocumenteCerere/"+idFisierCerere;
            $.ajax({
                url: urlDocumente,

                success: function (data) {
                    if (data.result == 'OK') {
                        that.listaInregistrari = data.documentList;

                        //apelam render template pentru a popula tabelul cu rezultatele obtinute
                        that.renderTemplate('tmpl_inregistrari_list_alte_documente', {data: data.documentList}).then(function (html) {
                            var tblHolder = $('.table_inregistrari_alte_documente');
                            tblHolder.html('');
                            tblHolder.html(html);


                            $('#documente_cerere_table').DataTable({
                                "searching": true,
                                language: {
                                    info: "Se afișează _START_ - _END_ din _TOTAL_ intrări",
                                    infoEmpty: "Se afișează 0 - 0 din 0 intrări",
                                    lengthMenu: "Se afișează _MENU_ intrări",
                                    zeroRecords: "Nu au fost găsite înregistrări care să se potrivească",
                                    paginate: {
                                        first: "Prima",
                                        previous: "Inapoi",
                                        next: "Inainte",
                                        last: "Ultima"
                                    },
                                    search: "Cauta in tabel ..."
                                }
                            });
                            if(idFisierCerere){
                                $(".trimite_completari").css("display","block");
                            }
                        }, function () {
                            that.alert('Unable to render template', 'ERROR');
                        });
                        // }

                    }

                }
            });
        }
        console.log("Ajax Call Start:" + url);

        $.ajax({
            url: url,

            success: function (data) {
                if (data.result == 'OK') {
                    that.listaInregistrari = data.studiiList;


                    /* if(data.persoaneContactList==null||data.persoaneContactList.length==0  ){
                     Swal.fire({
                     icon: "info",
                     html: "Nu exista persoane de contact",
                     focusConfirm: false,
                     confirmButtonText: "Ok"
                     });
                     }
                     else{*/
                    //apelam render template pentru a popula tabelul cu rezultatele obtinute
                    that.renderTemplate('tmpl_inregistrari_list', {data: data.studiiList}).then(function (html) {
                        var tblHolder = $('.table_inregistrari');
                        tblHolder.html('');
                        tblHolder.html(html);


                        $('#studii_table').DataTable({
                            "searching": true,
                            language: {
                                info: "Se afișează _START_ - _END_ din _TOTAL_ intrări",
                                infoEmpty: "Se afișează 0 - 0 din 0 intrări",
                                lengthMenu: "Se afișează _MENU_ intrări",
                                zeroRecords: "Nu au fost găsite înregistrări care să se potrivească",
                                paginate: {
                                    first: "Prima",
                                    previous: "Inapoi",
                                    next: "Inainte",
                                    last: "Ultima"
                                },
                                search: "Cauta in tabel ..."
                            }
                        });
                        if(idFisierCerere){
                            $(".trimite_completari").css("display","block");
                        }
                    }, function () {
                        that.alert('Unable to render template', 'ERROR');
                    });
                    // }

                }

            }
        });
        $.ajax({
            url: urlAdeverinte,

            success: function (data) {
                if (data.result == 'OK') {
                    that.listaInregistrari = data.adeverinteList;


                    /* if(data.persoaneContactList==null||data.persoaneContactList.length==0  ){
                     Swal.fire({
                     icon: "info",
                     html: "Nu exista persoane de contact",
                     focusConfirm: false,
                     confirmButtonText: "Ok"
                     });
                     }
                     else{*/
                    //apelam render template pentru a popula tabelul cu rezultatele obtinute
                    that.renderTemplate('tmpl_inregistrari_list_adeverinte', {data: data.adeverinteList}).then(function (html) {
                        var tblHolder = $('.table_inregistrari_adeverinte');
                        tblHolder.html('');
                        tblHolder.html(html);


                        $('#adeverinte_table').DataTable({
                            "searching": true,
                            language: {
                                info: "Se afișează _START_ - _END_ din _TOTAL_ intrări",
                                infoEmpty: "Se afișează 0 - 0 din 0 intrări",
                                lengthMenu: "Se afișează _MENU_ intrări",
                                zeroRecords: "Nu au fost găsite înregistrări care să se potrivească",
                                paginate: {
                                    first: "Prima",
                                    previous: "Inapoi",
                                    next: "Inainte",
                                    last: "Ultima"
                                },
                                search: "Cauta in tabel ..."
                            }
                        });
                        if(idFisierCerere){
                            $(".trimite_completari").css("display","block");
                        }
                    }, function () {
                        that.alert('Unable to render template', 'ERROR');
                    });
                    // }

                }

            }
        });
        $.ajax({
            url: urlCursuri,

            success: function (data) {
                if (data.result == 'OK') {
                    that.listaInregistrari = data.cursuriList;


                    /* if(data.persoaneContactList==null||data.persoaneContactList.length==0  ){
                     Swal.fire({
                     icon: "info",
                     html: "Nu exista persoane de contact",
                     focusConfirm: false,
                     confirmButtonText: "Ok"
                     });
                     }
                     else{*/
                    //apelam render template pentru a popula tabelul cu rezultatele obtinute
                    that.renderTemplate('tmpl_inregistrari_list_cursuri', {data: data.cursuriList}).then(function (html) {
                        var tblHolder = $('.table_inregistrari_cursuri');
                        tblHolder.html('');
                        tblHolder.html(html);


                        $('#cursuri_table').DataTable({
                            "searching": true,
                            language: {
                                info: "Se afișează _START_ - _END_ din _TOTAL_ intrări",
                                infoEmpty: "Se afișează 0 - 0 din 0 intrări",
                                lengthMenu: "Se afișează _MENU_ intrări",
                                zeroRecords: "Nu au fost găsite înregistrări care să se potrivească",
                                paginate: {
                                    first: "Prima",
                                    previous: "Inapoi",
                                    next: "Inainte",
                                    last: "Ultima"
                                },
                                search: "Cauta in tabel ..."
                            }
                        });
                    }, function () {
                        that.alert('Unable to render template', 'ERROR');
                    });
                    // }
                    if(idFisierCerere){
                        $(".trimite_completari").css("display","block");
                    }
                }

            }
        });
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },
    onChangeSpecialitate: function (value){
        //TODO de preluat dinamic
        if (value == '4' || value == '12'){
            $('#label_doc_file>span').css('display','none');
            $('#label_container_select_tip_document>span').css('display','none')
        } else {
            $('#label_doc_file>span').css('display','inline-block');
            $('#label_container_select_tip_document>span').css('display','inline-block')
        }
    },
    resetExperienta: function () {
        MOD_STUDII = "ADD";

        $('#resize_iframe', window.parent.document).trigger('click');
        var that = this;
        var PROC_NAME = "PageManager.prepareOpenRandNou";
        $("#nr_serie_data").val(null);


        var urlNivel = "/dmsws/lov/values_by_code/" + PageManager.COD_LOV_NIVEL;
        $.ajax({
            url: urlNivel,
            success: function (data) {
                if (data.result == 'OK') {

                    if (data.lov != null && data.lov.length != 0) {

                        //apelam render template pentru a popula tabelul cu rezultatele obtinute
                        that.renderTemplate('tmpl_id_nivel', {data: data.lov}).then(function (html) {
                            var tblHolder = $('#container_select_nivel');
                            tblHolder.html(html);

                            $("#container_select_nivel").trigger("chosen:updated");

                        }, function () {
                            that.alert('Unable to render template', 'ERROR');
                        });


                    }
                }
            }
        });
        var urlUniversitate = "/dmsws/lov/values_by_code/" + PageManager.COD_LOV_UNIVERSITATE;
        $.ajax({
            url: urlUniversitate,
            success: function (data) {
                if (data.result == 'OK') {

                    if (data.lov != null && data.lov.length != 0) {

                        //apelam render template pentru a popula tabelul cu rezultatele obtinute
                        that.renderTemplate('tmpl_universitate', {data: data.lov}).then(function (html) {
                            var tblHolder = $('#container_select_universitate');
                            tblHolder.html(html);

                            $("#container_select_universitate").trigger("chosen:updated");

                        }, function () {
                            that.alert('Unable to render template', 'ERROR');
                        });


                    }
                }
            }
        });
        var urlLocUniversitate = "/dmsws/lov/values_by_code/" + PageManager.COD_LOV_LOC_UNIVERSITATE;
        $.ajax({
            url: urlLocUniversitate,
            success: function (data) {
                if (data.result == 'OK') {

                    if (data.lov != null && data.lov.length != 0) {

                        //apelam render template pentru a popula tabelul cu rezultatele obtinute
                        that.renderTemplate('tmpl_loc_universitate', {data: data.lov}).then(function (html) {
                            var tblHolder = $('#container_select_loc_universitate');
                            tblHolder.html(html);

                            $("#container_select_loc_universitate").trigger("chosen:updated");

                        }, function () {
                            that.alert('Unable to render template', 'ERROR');
                        });


                    }
                }
            }
        });

        var urlPrograma = "/dmsws/lov/values_by_code/" + PageManager.COD_LOV_PROGRAMA;
        $.ajax({
            url: urlPrograma,
            success: function (data) {
                if (data.result == 'OK') {

                    if (data.lov != null && data.lov.length != 0) {

                        //apelam render template pentru a popula tabelul cu rezultatele obtinute
                        that.renderTemplate('tmpl_programa', {data: data.lov}).then(function (html) {
                            var tblHolder = $('#container_select_programa');
                            tblHolder.html(html);

                            $("#container_select_programa").trigger("chosen:updated");

                        }, function () {
                            that.alert('Unable to render template', 'ERROR');
                        });


                    }
                }
            }
        });
        var urlSpecialitate = "/dmsws/lov/values_by_code/" + PageManager.COD_LOV_SPECIALITATE;

        $.ajax({
            url: urlSpecialitate,
            success: function (data) {
                if (data.result == 'OK') {

                    if (data.lov != null && data.lov.length != 0) {

                        //apelam render template pentru a popula tabelul cu rezultatele obtinute
                        that.renderTemplate('tmpl_specialitate', {data: data.lov}).then(function (html) {
                            var tblHolder = $('#container_select_specialitate');
                            tblHolder.html(html);

                            $("#container_select_specialitate").trigger("chosen:updated");

                        }, function () {
                            that.alert('Unable to render template', 'ERROR');
                        });


                    }
                }
            }
        });

    },
    resetCursuri: function () {


        $('#resize_iframe', window.parent.document).trigger('click');
        var that = this;
        var PROC_NAME = "PageManager.prepareOpenRandNou";
        $("#nr_serie_data").val(null);


        var urlGrad = "/dmsws/lov/values_by_code/" + PageManager.COD_LOV_GRAD;
        $.ajax({
            url: urlGrad,
            success: function (data) {
                if (data.result == 'OK') {

                    if (data.lov != null && data.lov.length != 0) {

                        //apelam render template pentru a popula tabelul cu rezultatele obtinute
                        that.renderTemplate('tmpl_id_nivel', {data: data.lov}).then(function (html) {
                            var tblHolder = $('#container_select_grad');
                            tblHolder.html(html);

                            $("#container_select_grad").trigger("chosen:updated");

                        }, function () {
                            that.alert('Unable to render template', 'ERROR');
                        });


                    }
                }
            }
        });
        var urlTipCurs = "/dmsws/lov/values_by_code/" + PageManager.COD_LOV_TIP_CURS;
        $.ajax({
            url: urlTipCurs,
            success: function (data) {
                if (data.result == 'OK') {

                    if (data.lov != null && data.lov.length != 0) {

                        //apelam render template pentru a popula tabelul cu rezultatele obtinute
                        that.renderTemplate('tmpl_facultate', {data: data.lov}).then(function (html) {
                            var tblHolder = $('#tip_curs');
                            tblHolder.html(html);

                            $("#tip_curs").trigger("chosen:updated");

                        }, function () {
                            that.alert('Unable to render template', 'ERROR');
                        });


                    }
                }
            }
        });


    },
    resetAdeverinte: function () {


        $('#resize_iframe', window.parent.document).trigger('click');
        var that = this;
        var PROC_NAME = "PageManager.prepareOpenRandNou";
        $("#numar_adeverinte").val(null);
        $("#data_adeverinta").val(null);
        $("#data_inceput").val(null);
        $("#data_sfarsit").val(null);

        $("#id_firma").siblings().each(function(e){
            debugger;
            if(this.classList.contains("text")){
                $(this).html("");
            }

        });
        $("#container_select_tip_adeverinta").siblings().each(function(e){
            if(this.classList.contains("text")){
                $(this).html("");
            }

        });
        $("#container_select_tip_document_adeverinta").siblings().each(function(e){
            if(this.classList.contains("text")){
                $(this).html("");
            }

        });
        $("#container_select_grad_vechime").siblings().each(function(e){
            if(this.classList.contains("text")){
                $(this).html("");
            }

        });
        var urlGrad = "/dmsws/lov/values_by_code/" + PageManager.COD_LOV_ID_GRAD;
        $.ajax({
            url: urlGrad,
            success: function (data) {
                if (data.result == 'OK') {

                    if (data.lov != null && data.lov.length != 0) {

                        //apelam render template pentru a popula tabelul cu rezultatele obtinute
                        that.renderTemplate('tmpl_id_nivel', {data: data.lov}).then(function (html) {
                            var tblHolder = $('#container_select_grad_vechime');
                            tblHolder.html(html);

                            $("#container_select_grad_vechime").trigger("chosen:updated");

                        }, function () {
                            that.alert('Unable to render template', 'ERROR');
                        });


                    }
                }
            }
        });


        var urlFirma = "/dmsws/lov/values_by_code/" + PageManager.COD_FIRMA_AUTORIZATA;

        $.ajax({
            url: urlFirma,
            success: function (data) {
                if (data.result == 'OK') {

                    if (data.lov != null && data.lov.length != 0) {

                        //apelam render template pentru a popula tabelul cu rezultatele obtinute
                        that.renderTemplate('tmpl_id_nivel', {data: data.lov}).then(function (html) {
                            var tblHolder = $('#id_firma');
                            tblHolder.html(html);
                            $('#id_firma').html(html);
                            $("#id_firma").trigger("chosen:updated");

                        }, function () {
                            that.alert('Unable to render template', 'ERROR');
                        });


                    }
                }
            }
        });


    },

    resetExperienta: function () {
        MOD_STUDII = "ADD";

        $('#resize_iframe', window.parent.document).trigger('click');
        var that = this;
        var PROC_NAME = "PageManager.prepareOpenRandNou";
        $("#nr_serie_data").val(null);


        var urlNivel = "/dmsws/lov/values_by_code/" + PageManager.COD_LOV_NIVEL;
        $.ajax({
            url: urlNivel,
            success: function (data) {
                if (data.result == 'OK') {

                    if (data.lov != null && data.lov.length != 0) {

                        //apelam render template pentru a popula tabelul cu rezultatele obtinute
                        that.renderTemplate('tmpl_id_nivel', {data: data.lov}).then(function (html) {
                            var tblHolder = $('#container_select_nivel');
                            tblHolder.html(html);

                            $("#container_select_nivel").trigger("chosen:updated");

                        }, function () {
                            that.alert('Unable to render template', 'ERROR');
                        });


                    }
                }
            }
        });
        var urlUniversitate = "/dmsws/lov/values_by_code/" + PageManager.COD_LOV_UNIVERSITATE;
        $.ajax({
            url: urlUniversitate,
            success: function (data) {
                if (data.result == 'OK') {

                    if (data.lov != null && data.lov.length != 0) {

                        //apelam render template pentru a popula tabelul cu rezultatele obtinute
                        that.renderTemplate('tmpl_universitate', {data: data.lov}).then(function (html) {
                            var tblHolder = $('#container_select_universitate');
                            tblHolder.html(html);

                            $("#container_select_universitate").trigger("chosen:updated");

                        }, function () {
                            that.alert('Unable to render template', 'ERROR');
                        });


                    }
                }
            }
        });
        var urlLocUniversitate = "/dmsws/lov/values_by_code/" + PageManager.COD_LOV_LOC_UNIVERSITATE;
        $.ajax({
            url: urlLocUniversitate,
            success: function (data) {
                if (data.result == 'OK') {

                    if (data.lov != null && data.lov.length != 0) {

                        //apelam render template pentru a popula tabelul cu rezultatele obtinute
                        that.renderTemplate('tmpl_loc_universitate', {data: data.lov}).then(function (html) {
                            var tblHolder = $('#container_select_loc_universitate');
                            tblHolder.html(html);

                            $("#container_select_loc_universitate").trigger("chosen:updated");

                        }, function () {
                            that.alert('Unable to render template', 'ERROR');
                        });


                    }
                }
            }
        });

        var urlPrograma = "/dmsws/lov/values_by_code/" + PageManager.COD_LOV_PROGRAMA;
        $.ajax({
            url: urlPrograma,
            success: function (data) {
                if (data.result == 'OK') {

                    if (data.lov != null && data.lov.length != 0) {

                        //apelam render template pentru a popula tabelul cu rezultatele obtinute
                        that.renderTemplate('tmpl_programa', {data: data.lov}).then(function (html) {
                            var tblHolder = $('#container_select_programa');
                            tblHolder.html(html);

                            $("#container_select_programa").trigger("chosen:updated");

                        }, function () {
                            that.alert('Unable to render template', 'ERROR');
                        });


                    }
                }
            }
        });
        var urlSpecialitate = "/dmsws/lov/values_by_code/" + PageManager.COD_LOV_SPECIALITATE;

        $.ajax({
            url: urlSpecialitate,
            success: function (data) {
                if (data.result == 'OK') {

                    if (data.lov != null && data.lov.length != 0) {

                        //apelam render template pentru a popula tabelul cu rezultatele obtinute
                        that.renderTemplate('tmpl_specialitate', {data: data.lov}).then(function (html) {
                            var tblHolder = $('#container_select_specialitate');
                            tblHolder.html(html);

                            $("#container_select_specialitate").trigger("chosen:updated");

                        }, function () {
                            that.alert('Unable to render template', 'ERROR');
                        });


                    }
                }
            }
        });

    },
    getExperienta: function (id) {
        var url = "/dmsws/anre/getStudii/" + id;
        MOD_STUDII = "EDIT";

        console.log("Ajax Call Start:" + url);
        $.ajax({
            url: url,

            success: function (data) {
                if (data.studiiList[0].id !== null && data.studiiList[0].id != undefined) {
                    $('#container_select_nivel').val(data.studiiList[0].id_nivel).trigger("change");
                    $('#container_select_universitate').val(data.studiiList[0].universitate).trigger("change");
                    $('#container_select_loc_universitate').val(data.studiiList[0].loc_universitate).trigger("change");
                    $('#container_select_facultate').val(data.studiiList[0].facultate).trigger("change");
                    $('#container_select_specialitate').val(data.studiiList[0].specialitate).trigger("change");
                    $('#container_select_programa').val(data.studiiList[0].programa).trigger("change");
                    $('#doc_file').val(data.studiiList[0].fisier);

                    $("#nr_serie_data").val(data.studiiList[0].nr_serie_data);


                    ID_STUDII = data.studiiList[0].id;
                }
            }


        });
    },
    deleteExperienta: function (id) {

        var that = this;
        var PROC_NAME = "PageManager.deleteExperienta";



        var url = "/dmsws/anre/stergeStudii/" + id;

        $.ajax({
            url: url,
            method: 'GET',
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                if (data.result == 'OK') {
                    $.fancybox.close();


                    Swal.fire({
                        icon: "info",
                        html: "Au fost sterse studiile.",
                        focusConfirm: false,
                        confirmButtonText: "Ok"
                    });
                    PageManager.afiseazaInregistrari();

                } else if (data.result == 'ERR') {
                    Swal.fire({
                        icon: "error",
                        html: data.info,
                        focusConfirm: false,
                        confirmButtonText: "Ok"
                    });

                }
            }
        });


        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },
    deleteCursuri: function (id) {

        var that = this;
        var PROC_NAME = "PageManager.deleteExperienta";



        var url = "/dmsws/anre/stergeCursuri/" + id;

        $.ajax({
            url: url,
            method: 'GET',
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                if (data.result == 'OK') {
                    $.fancybox.close();


                    Swal.fire({
                        icon: "info",
                        html: "Au fost sterse cursurile.",
                        focusConfirm: false,
                        confirmButtonText: "Ok"
                    });
                    PageManager.afiseazaInregistrari();

                } else if (data.result == 'ERR') {
                    Swal.fire({
                        icon: "error",
                        html: data.info,
                        focusConfirm: false,
                        confirmButtonText: "Ok"
                    });

                }
            }
        });


        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },
    deleteAdeverinte: function (id) {

        var that = this;
        var PROC_NAME = "PageManager.deleteExperienta";



        var url = "/dmsws/anre/stergeAdeverinte/" + id;

        $.ajax({
            url: url,
            method: 'GET',
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                if (data.result == 'OK') {
                    $.fancybox.close();


                    Swal.fire({
                        icon: "info",
                        html: "Au fost sterse adeverintele.",
                        focusConfirm: false,
                        confirmButtonText: "Ok"
                    });
                    PageManager.afiseazaInregistrari();

                } else if (data.result == 'ERR') {
                    Swal.fire({
                        icon: "error",
                        html: data.info,
                        focusConfirm: false,
                        confirmButtonText: "Ok"
                    });

                }
            }
        });


        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },

    mandatoryFunctions: function () {
        var that = this;

        var PROC_NAME = "FileManager.mandatoryFunctions";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);

        that.compileAllTemplates();


        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
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
    compileAllTemplates: function () {
        this.templates['tmpl_inregistrari_list'] = $('#tmpl_inregistrari_list').html();
        this.templates['tmpl_id_nivel'] = $('#tmpl_id_nivel').html();
        this.templates['tmpl_universitate'] = $('#tmpl_universitate').html();
        this.templates['tmpl_facultate'] = $('#tmpl_facultate').html();
        this.templates['tmpl_loc_universitate'] = $('#tmpl_loc_universitate').html();
        this.templates['tmpl_programa'] = $('#tmpl_programa').html();
        this.templates['tmpl_specialitate'] = $('#tmpl_specialitate').html();
        this.templates['tmpl_document'] = $('#tmpl_document').html();
        this.templates['tmpl_inregistrari_list_adeverinte'] = $('#tmpl_inregistrari_list_adeverinte').html();
        this.templates['tmpl_inregistrari_list_cursuri'] = $('#tmpl_inregistrari_list_cursuri').html();
        this.templates['tmpl_inregistrari_list_alte_documente'] = $('#tmpl_inregistrari_list_alte_documente').html();


        // parseaza toate template-urile
        $.each(this.templates, function (index, template) {
            Mustache.parse(template);
        });
    },


    objectToArray: function (object) {
        var array = [];

        for (var property in object) {
            if (object.hasOwnProperty(property)) {
                array.push({key: property, value: object[property]});
            }
        }

        return array;
    },


    /*
     Render a mustache template.
     */
    renderTemplate: function (templateName, data) {
        var defer = $.Deferred();
        var str = null;

        try {
            str = Mustache.render(this.templates[templateName], data);
        } catch (e) {
            console.log(e);
            defer.reject(e);
        }

        if (typeof str === 'undefined' || str === null) {
            defer.reject('value null');
        }

        defer.resolve(str);

        return defer.promise();
    },


    /**
     * Intoarce daca un item nu e undefined.
     *
     * @param item item
     * @returns {boolean}
     */
    isNotUndef: function (item) {
        return typeof item !== 'undefined';
    },

    /**
     * Intoarce daca un item nu e null.
     *
     * @param item item
     * @returns {boolean}
     */
    isNotNull: function (item) {
        return this.isNotUndef(item) && item != null;
    },

    /**
     * Intoarce daca un item nu e empty.
     *
     * @param item item
     * @returns {*|boolean}
     */
    isNotEmpty: function (item) {
        return this.isNotNull(item) && (typeof item != 'string' || item != '');
    },

    /**
     * Intoarce daca un item e undefined.
     *
     * @param item item
     * @returns {boolean}
     */
    isUndef: function (item) {
        return typeof item === 'undefined';
    },

    /**
     * Intoarce daca un item e null.
     *
     * @param item item
     * @returns {boolean}
     */
    isNull: function (item) {
        return this.isUndef(item) || item == null;
    },

    /**
     * Intoarce daca un item e empty.
     *
     * @param item item
     * @returns {*|boolean}
     */
    isEmpty: function (item) {
        return this.isNull(item) && typeof item === 'string' && item == '';
    },

    /**
     * Arunca o alerta catre utilizator.
     *
     * @param msg mesaj
     * @param type tip in ('INFO', 'ERROR')
     */
    alert: function (msg, type) {
        swal(msg);
    },

    /**
     * Actiune ajax pt servlet-ul kanban_actions.
     *
     * @param options optiuni
     */
    ajaxAction: function (options) {
        var that = this;

        if (this.isNull(options)) {
            this.alert('Specify options.', 'ERROR');
        }

        if (this.isNull(options.url)) {
            this.alert('Specify url.', 'ERROR');
        }

        if (this.isNull(options.onSuccess)) {
            options.onSuccess = function () {
            };
        }

        if (this.isNull(options.onComplete)) {
            options.onComplete = function () {
            };
        }

        if (this.isNull(options.onError)) {
            options.onError = function (data) {
                var json = true;
                var respText = data['responseText'];
                try {
                    var respObj = JSON.parse(respText);
                    that.alert(respObj['error'], "ERROR");
                } catch (e) {
                    json = false;
                }
                if (!json) {
                    if (respText.trim() == '')
                        respText = "Server error";

                    that.alert(respText, "ERROR");
                }
            };
        }

        if (this.isNull(options.method)) {
            options.method = 'get';
        }

        if (this.isNull(options.async)) {
            options.async = true;
        }

        if (this.isNull(options.data)) {
            options.data = {};
        }

        if (this.isNull(options.headers)) {
            options.headers = {}
        }

        options.headers['Access-Control-Allow-Origin'] = '*';
        options.headers['Access-Control-Allow-Credentials'] = 'true';
        options.headers['Access-Control-Allow-Methods'] = 'POST, PUT, GET, OPTIONS, DELETE, HEAD';
        options.headers['Access-Control-Allow-Headers'] = 'Access-Control-Allow-Headers, Access-Control-Allow-Origin, Access-Control-Request-Method, Access-Control-Request-Headers, Access-Control-Allow-Credentials, Access-Control-Allow-Methods, Origin, Accept, X-Requested-With, Content-Type, X-PINGOTHER, Authorization';

        if (this.isNull(options.data)) {
            options.data = null;
        }

        $.ajax({
            context: this,
            type: options.method,
            data: options.data,
            url: options.url,
            async: options.async,
            complete: options.onComplete,
            success: options.onSuccess,
            error: options.onError,
            headers: options.headers
        });
    },

    /*
     Form request with POST.
     */
    goPost2: function (url, dat, id, target) {
        // daca nu are id, e math.random
        id = typeof id !== 'undefined' ? id : Math.floor(Math.random() * 3571);

        // default intra pe self
        target = typeof target !== 'undefined' ? target : "_self";

        var form_id = "post_form_" + id;

        $('#' + form_id).remove();

        var form_string = '<form action="' + url + '" method="post" target = "' + target + '" id="' + form_id + '" name="' + form_id + '">';

        for (var key in dat) {
            var val = dat[key];

            form_string += '<input type="hidden" name="' + key + '" id="' + key + '_' + id + '" value="' + val + '" />';

            if (key == 'req') {
                var secObj = dat[key];

                for (var secKey in secObj) {
                    var secVal = secObj[secKey];
                    form_string += '<input type="hidden" name="' + secKey + '" id="' + secKey + '_' + id + '" value="' + secVal + '" />';
                }
            }
        }

        form_string += '</form>';
        var form = $(form_string);
        $('body').append(form);
        $(form).submit();
    }


};

$(document).ready(function () {
    PageManager.init();
    $('#form_adauga')
        .submit(function (e) {
            e.preventDefault();
        });

});

$("#container_select_grad_vechime").change(function () {
    var val = $('#container_select_grad_vechime').val();
    if (val != null && val != '' && val != undefined) {
        var url = "/dmsws/anre/getTipAdeverintaByIdGrad/" + val;
        $.ajax({
            url: url,
            success: function (data) {

                if (data.result == 'OK') {

                    if (data.lov != null && data.lov.length != 0) {

                        //apelam render template pentru a popula tabelul cu rezultatele obtinute
                        PageManager.renderTemplate('tmpl_id_nivel', {data: data.lov}).then(function (html) {
                            var tblHolder = $('#container_select_tip_adeverinta');
                            tblHolder.html(html);

                            $("#container_select_tip_adeverinta").trigger("chosen:updated");

                        }, function () {
                            PageManager.alert('Unable to render template', 'ERROR');
                        });


                    }
                }
            }
        });
    }
});


function validateEmail(email) {
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(String(email).toLowerCase()) || email == '';
}




