var MOD_CONDUCERE=null;
var MOD_SEDIU=null;
var ID_CONDUCERE=null;
var MOD_SUCURSALA=null;
var MOD_AUT_MEDIU=null;
var MOD_AUT_GOSP_APELOR=null;
var ID_SUCURSALA=null;
var ID_AUT_MEDIU=null;
var ID_AUT_GOSP_APELOR=null;
var PAGE_NAME = "persoane_contact.js";

var PageManager = {
    idForUpt:null,
    idForUptAC:null,
    idForUptSD:null,
    myDrop:null,
    wsUrl: null,
    wsToken: null,
    templates: {},
    listaInregistrari: {},
    listaInregistrariSucursala: {},
    listaInregistrariAutMediu: {},
    listaInregistrariAutGospApelor: {},
    listaActionari: {},
    listaSedii: {},
    infoTert: null,
    listaCautare:{},
    nrPagina: 0,
    nrMaxPages: 4,
    numPerPage: 10,
    COD_LOV_DEPARTAMENT: "LOV_DEPARTAMENTE_CONTACTE",
    COD_LOV_TIP_CAPITAL_A: "LOV_TIP_CAPITAL_A",
    COD_LOV_MONEDA_A: "LOV_MONEDE_A",
    COD_LOV_JUDET: "LOV_JUDET",
    COD_LOV_STAREA_FIRMEI: "LOV_STARE_FIRMA",
    COD_LOV_COD_CAEN: "LOV_COD_CAEN",
    COD_LOV_LOCALITATE: "LOV_LOCALITATE",
    COD_LOV_SIRUTA: "LOV_SIRUTA",
    COD_LOV_LOCALITATE_APARTINATOARE: "LOV_LOCALITATE_APARTINATOARE",
    COD_LOV_DOMENIU: "LOV_TBX_DOMENIU",
     init: function () {
        var PROC_NAME = "PageManager.init";
       // //$UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);
        //-------------- se apeleaza obligatoriu la initializarea paginii---------------------
        this.mandatoryFunctions();

        //apelam reconstructie paginare & afisare rezultate
        PageManager.afiseazaInregistrari();
        PageManager.afiseazaInregistrariConducere();
        PageManager.afiseazaInregistrariActionari();
        PageManager.afiseazaInregistrariSucursala();
        PageManager.afiseazaInregistrariAutMediu();
        PageManager.afiseazaInregistrariAutGospApelor();
        PageManager.afiseazaInregistrariSedii();
       //$UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);


    },
    cautare: function(){
        var PROC_NAME = "PageManager.cautare";
        var searchStr=$('#search_box').val();
        PageManager.buildPaginationCautare(searchStr).then(function () {
            PageManager.getListaInregistrariCautare(searchStr);
        });





       // //$UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },
    adaugaContact: function(){
        var that=this;
        var PROC_NAME = "PageManager.adaugaContact";

        var jReq= PageManager.getInfoRandNou();

        var url = "/dmsws/anre/adauga_persoana_contact/";
        if(jReq!=null && typeof jReq!='undefined'){
            $.ajax({
                url: url,
                method:'POST',
                dataType: 'json',
                contentType: "application/json; charset=utf-8",
                data:jReq,
                success: function (data) {
                    if (data.result == 'OK') {
                        $.fancybox.close();


                        Swal.fire({
                            icon: "info",
                            html: "A fost adaugata persoana de contact.",
                            focusConfirm: false,
                            confirmButtonText: "Ok"
                        });
                        PageManager.afiseazaInregistrari();

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

        }

        //$UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },

    adaugaActionar: function(){
        var that=this;
        var PROC_NAME = "PageManager.adaugaActionar";

        var req = PageManager.getInfoActionarNou();
        var jReq = JSON.stringify(req);


        var ok = true;
        // if (typeof req.nr_actiuni === 'undefined' || req.nr_actiuni === null || req.nr_actiuni.trim() === '') {
        //     that.animateElementErr("#add_nr_actiuni");
        //     ok = false;
        // }
        // if (typeof req.valoare === 'undefined' || req.valoare === null || req.valoare.trim() === '') {
        //     that.animateElementErr("#add_valoare");
        //     ok = false;
        // }
        if (typeof req.actionar === 'undefined' || req.actionar === null || req.actionar.trim() === '') {
            that.animateElementErr("#add_actionar");
            ok = false;
        }

        if (!ok) {
            Swal.fire({
                icon: "error",
                html: 'Completati coloanele obligatorii',
                focusConfirm: false,
                confirmButtonText: "Ok",
                customClass: {
                    container: 'high-swal'
                }
            });
            return;
        }

        var url = "/dmsws/anre/adaugaActionar/";
       if(jReq!=null && typeof jReq!='undefined'){
            $.ajax({
                url: url,
                method:'POST',
                dataType: 'json',
                contentType: "application/json; charset=utf-8",
                data:jReq,
                success: function (data) {
                    if (data.result == 'OK') {
                        $.fancybox.close();


                        Swal.fire({
                            icon: "info",
                            html: "A fost adaugat actionarul.",
                            focusConfirm: false,
                            confirmButtonText: "Ok"
                        });
                        PageManager.afiseazaInregistrariActionari();

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

        }

        //$UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },
    stergeContact: function(id){
        var that=this;
        var PROC_NAME = "PageManager.stergeContact";



        var url = "/dmsws/anre/stergeContact/"+id;

        $.ajax({
            url: url,
            method:'GET',
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                if (data.result == 'OK') {
                    $.fancybox.close();


                    Swal.fire({
                        icon: "info",
                        html: "A fost sters contactul.",
                        focusConfirm: false,
                        confirmButtonText: "Ok",
                            preConfirm: () => {
                           that.doRefresh();
                        }

                    });


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



        //$UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },
    stergeActionar: function(id){
        var that=this;
        var PROC_NAME = "PageManager.stergeActionar";



        var url = "/dmsws/anre/stergeActionar/"+id;

        $.ajax({
            url: url,
            method:'GET',
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                if (data.result == 'OK') {
                    $.fancybox.close();


                    Swal.fire({
                        icon: "info",
                        html: "Actionarul a fost sters cu succes.",
                        focusConfirm: false,
                        confirmButtonText: "Ok"
                    });
                    PageManager.afiseazaInregistrariActionari();


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



        //$UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },

    stergeSediu: function(id){
        var that=this;
        var PROC_NAME = "PageManager.stergeSediu";



        var url = "/dmsws/anre/stergeSediu/"+id;

        $.ajax({
            url: url,
            method:'GET',
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                if (data.result == 'OK') {
                    $.fancybox.close();


                    Swal.fire({
                        icon: "info",
                        html: "Sediul a fost sters cu succes.",
                        focusConfirm: false,
                        confirmButtonText: "Ok"
                    });
                    PageManager.afiseazaInregistrariSedii();


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



        //$UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },

    doRefresh: function(){
        window.location.reload();
        },

    getInfoRandNou: function(){
        var that=this;
        var PROC_NAME = "PageManager.getInfoRandNou";

        var numeStr= $("#add_nume").val();
        var descriere= $("#add_descriere").val();
        var email= $("#add_email").val();
        var telefon= $("#add_telefon").val();
        var fax= $("#add_fax").val();
        var functie= $("#add_functie").val();

        var departament= $("#add_departament").val();
        var reprezentant= $("#add_reprezentant").prop('checked');
        var imputernicit= $("#add_imputernicit").prop('checked');


        if(reprezentant!=null && typeof reprezentant!='undefined' && reprezentant==true){
            reprezentant=1;
        }else{
            reprezentant=0;
        }

        if(imputernicit!=null && typeof imputernicit!='undefined' && imputernicit==true){
            imputernicit=1;
        }else{
            imputernicit=0;
        }

        var emailValid= validateEmail(email);
         if( numeStr!=null && numeStr!='' && emailValid ){
            var objRand= {
                nume: numeStr,
                descriere: descriere,
                email: email,
                telefon:telefon,
                fax:fax,
                functie:functie,
                departament:departament,
                reprezentant:reprezentant,
                imputernicit:imputernicit
            };
            return JSON.stringify(objRand);
        }
        //$UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },


    getInfoActionarNou: function(){
        var that=this;
        var PROC_NAME = "PageManager.getInfoActionarNou";

        var add_actionar= $("#add_actionar").val();
        var add_tip_Capital= $("#container_select_tip_capital").val();
        var add_procente= $("#add_procente").val();
        var add_nr_actiuni= $("#add_nr_actiuni").val();
        var add_valoare= $("#add_valoare").val();
        var add_moneda= $("#container_select_moneda").val();

            var objRand= {
                actionar: add_actionar,
                id_tip_capital: add_tip_Capital,
                procente: add_procente,
                nr_actiuni:add_nr_actiuni,
                valoare:add_valoare,
                id_moneda:add_moneda
            };
            return objRand;
        //$UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },
    getInfoCertConstatator: function(){
        var that=this;
        var PROC_NAME = "PageManager.getInfoCertConstatator";

        var numarCertificatConstatator= $("#nr_cert_constatator").val();
        var dataEmitereCertificatConstatator= $("#data_emitere").val();
        var idJudetCertificatConstatator= $("#select_judet").val();
        var durataDeConstituire= $("#durata_de_constituire").val();
        var capitalulSocial= $("#capitalul_social").val();
        var idStareaFirmei= $("#select_starea_firmei").val();
        var codCaenStr= $("#select_cod_caen").val().toString();
        var info= $("#info").val();

            var obj = {
                numarCertificatConstatator: numarCertificatConstatator,
                dataEmitereCertificatConstatator: dataEmitereCertificatConstatator,
                idJudetCertificatConstatator: idJudetCertificatConstatator,
                durataDeConstituire: durataDeConstituire,
                capitalulSocial: capitalulSocial,
                idStareaFirmei: idStareaFirmei,
                codCaenStr: codCaenStr,
                info:info
            };
            return JSON.stringify(obj);

        //$UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },

    prepareOpenRandNou: function(){
        $('#resize_iframe', window.parent.document).trigger('click');
        var that=this;
        var PROC_NAME = "PageManager.prepareOpenRandNou";

        var urlMonAc="/dmsws/lov/values_by_code/"+ PageManager.COD_LOV_MONEDA_A;
        $.ajax({
            url: urlMonAc,
            success: function (data) {
                if (data.result == 'OK') {

                    if (data.lov != null && data.lov.length != 0) {

                        //apelam render template pentru a popula tabelul cu rezultatele obtinute
                        that.renderTemplate('tmpl_moneda_actionar', {data: data.lov}).then(function (html) {

                            var tblHolder = $('#container_select_moneda');
                            tblHolder.html(html);

                            $("#container_select_moneda").trigger("chosen:updated");

                        }, function () {
                            that.alert('Unable to render template', 'ERROR');
                        });


                    }
                }
            }
        });










        var urlAc= "/dmsws/lov/values_by_code/"+ PageManager.COD_LOV_TIP_CAPITAL_A;
        $.ajax({
            url: urlAc,
            success: function (data) {
                if (data.result == 'OK') {

                    if (data.lov != null && data.lov.length != 0) {

                        //apelam render template pentru a popula tabelul cu rezultatele obtinute
                        that.renderTemplate('tmpl_tip_capital', {data: data.lov}).then(function (html) {

                            var tblHolder = $('#container_select_tip_capital');
                            tblHolder.html(html);

                            $("#container_select_tip_capital").trigger("chosen:updated");

                        }, function () {
                            that.alert('Unable to render template', 'ERROR');
                        });


                    }
                }
            }
        });







        var url = "/dmsws/lov/values_by_code/"+ PageManager.COD_LOV_DEPARTAMENT;
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

       //$UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },



    setLovJudetCertConst: function(idJudet){
        // $('#resize_iframe', window.parent.document).trigger('click');
        var that=this;
        var PROC_NAME = "PageManager.prepareOpenTabCertConstatator";

        var url = "/dmsws/lov/values_by_code/"+ PageManager.COD_LOV_JUDET;
        $.ajax({
            url: url,
            success: function (data) {
                if (data.result == 'OK') {

                    if (data.lov != null && data.lov.length != 0) {

                        //apelam render template pentru a popula tabelul cu rezultatele obtinute
                        that.renderTemplate('tmpl_judet', {data: data.lov}).then(function (html) {
                            var tblHolder = $('#select_judet');
                            tblHolder.html(html);

                         //   $("#select_judet").trigger("chosen:updated");
                            $('#select_judet').val(idJudet).trigger("chosen:updated");

                      //      PageManager.getCertificatConstatator();

                        }, function () {
                            that.alert('Unable to render template', 'ERROR');
                        });


                    }
                }
            }
        });

        //$UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },


    setLovStareaFirmeiCertConst: function(idStareaFirmei){
        var that=this;

        var url = "/dmsws/lov/values_by_code/"+ PageManager.COD_LOV_STAREA_FIRMEI;
        $.ajax({
            url: url,
            success: function (data) {
                if (data.result == 'OK') {

                    if (data.lov != null && data.lov.length != 0) {

                        //apelam render template pentru a popula tabelul cu rezultatele obtinute
                        that.renderTemplate('tmpl_starea_firmei', {data: data.lov}).then(function (html) {
                            var tblHolder = $('#select_starea_firmei');
                            tblHolder.html(html);

                            $('#select_starea_firmei').val(idStareaFirmei).trigger("chosen:updated");

                        }, function () {
                            that.alert('Unable to render template', 'ERROR');
                        });


                    }
                }
            }
        });

        //$UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },

    setLovCodCaenCertConstatator: function(cod_caen_str){
        var that=this;

        var url = "/dmsws/lov/values_by_code/"+ PageManager.COD_LOV_COD_CAEN;
        $.ajax({
            url: url,
            success: function (data) {
                if (data.result == 'OK') {

                    if (data.lov != null && data.lov.length != 0) {

                        //apelam render template pentru a popula tabelul cu rezultatele obtinute
                        that.renderTemplate('tmpl_cod_caen', {data: data.lov}).then(function (html) {
                            var tblHolder = $('#select_cod_caen');
                            tblHolder.html(html);

                            var codCaen = [];
                            if (typeof cod_caen_str !== 'undefined' && cod_caen_str !== null){
                                codCaen = cod_caen_str.split(',');
                            }

                            $('#select_cod_caen').val(codCaen).trigger("chosen:updated");

                        }, function () {
                            that.alert('Unable to render template', 'ERROR');
                        });


                    }
                }
            }
        });

        //$UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },

    getLovLocalitate: function(){
        // $('#resize_iframe', window.parent.document).trigger('click');
        var that=this;
        var PROC_NAME = "PageManager.getLovLocalitate";

        var url = "/dmsws/lov/values_by_code/"+ PageManager.COD_LOV_LOCALITATE;
        $.ajax({
            url: url,
            success: function (data) {
                if (data.result == 'OK') {

                    if (data.lov != null && data.lov.length != 0) {

                        //apelam render template pentru a popula tabelul cu rezultatele obtinute
                        that.renderTemplate('tmpl_localitate', {data: data.lov}).then(function (html) {
                            var tblHolder = $('#select_localitate');
                            tblHolder.html(html);

                            $("#select_localitate").trigger("chosen:updated");


                            tblHolder = $('#aga_select_localitate_principala');
                            tblHolder.html(html);

                            $("#aga_select_localitate_principala").trigger("chosen:updated");


                            tblHolder = $('#aga_select_localitate_apartinatoare');
                            tblHolder.html(html);

                            $("#aga_select_localitate_apartinatoare").trigger("chosen:updated");



                            tblHolder = $('#am_select_localitate_principala');
                            tblHolder.html(html);

                            $("#am_select_localitate_principala").trigger("chosen:updated");



                            tblHolder = $('#am_select_localitate_apartinatoare');
                            tblHolder.html(html);

                            $("#am_select_localitate_apartinatoare").trigger("chosen:updated");


                            tblHolder = $('#sed_select_localitate_mare');
                            tblHolder.html(html);
                            $("#sed_select_localitate_mare").trigger("chosen:updated");


                            tblHolder = $('#sed_select_localitate_mica');
                            tblHolder.html(html);
                            $("#sed_select_localitate_mica").trigger("chosen:updated");



                            // $('#select_judet').val(idJudet).trigger("chosen:updated");

                            //      PageManager.getCertificatConstatator();

                        }, function () {
                            that.alert('Unable to render template', 'ERROR');
                        });


                    }
                }
            }
        });

        //$UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },

    getLovLocalitatePrincipalaAm: function(cb){
        var that=this;

        var data = {};

        var JUDET_SIRUTA = $("#am_select_judet").val();
        if (typeof JUDET_SIRUTA === 'undefined'){
            JUDET_SIRUTA = null;
        }
        data["JUDET_SIRUTA"] = JUDET_SIRUTA;

        var preValue = $("#am_select_localitate_principala").val();

        var jData = JSON.stringify(data);

        var url = "/dmsws/lov/values_by_code_with_dep/"+ PageManager.COD_LOV_SIRUTA;
        $.ajax({
            url: url,
            data: jData,
            method: 'post',
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                if (data.result == 'OK') {

                    if (data.lov != null && data.lov.length != 0) {

                        //apelam render template pentru a popula tabelul cu rezultatele obtinute
                        that.renderTemplate('tmpl_localitate', {data: data.lov}).then(function (html) {
                            tblHolder = $('#am_select_localitate_principala');
                            tblHolder.html(html);

                            if (typeof preValue !== 'undefined' && preValue !== null){
                                $("#am_select_localitate_principala").val(preValue);
                            }

                            $("#am_select_localitate_principala").trigger("chosen:updated");

                            if (typeof cb === 'function'){
                                cb()
                            }
                        }, function () {
                            that.alert('Unable to render template', 'ERROR');
                        });
                    }
                }
            }
        });
    },

    getLovLocalitatePrincipalaAga: function(cb){
        var that=this;

        var data = {};

        var JUDET_SIRUTA = $("#aga_select_judet").val();
        if (typeof JUDET_SIRUTA === 'undefined'){
            JUDET_SIRUTA = null;
        }
        data["JUDET_SIRUTA"] = JUDET_SIRUTA;

        var preValue = $("#aga_select_localitate_principala").val();

        var jData = JSON.stringify(data);

        var url = "/dmsws/lov/values_by_code_with_dep/"+ PageManager.COD_LOV_SIRUTA;
        $.ajax({
            url: url,
            data: jData,
            method: 'post',
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                if (data.result == 'OK') {

                    if (data.lov != null && data.lov.length != 0) {

                        //apelam render template pentru a popula tabelul cu rezultatele obtinute
                        that.renderTemplate('tmpl_localitate', {data: data.lov}).then(function (html) {
                            tblHolder = $('#aga_select_localitate_principala');
                            tblHolder.html(html);


                            if (typeof preValue !== 'undefined' && preValue !== null){
                                $("#aga_select_localitate_principala").val(preValue);
                            }

                            $("#aga_select_localitate_principala").trigger("chosen:updated");

                            if (typeof cb === 'function'){
                                cb()
                            }
                        }, function () {
                            that.alert('Unable to render template', 'ERROR');
                        });
                    }
                }
            }
        });
    },


    getLovLocalitateApartinatoareAm: function(cb){
        var that=this;

        var data = {};

        var LOCALITATE_PRINCIPALA = $("#am_select_localitate_principala").val();
        if (typeof LOCALITATE_PRINCIPALA === 'undefined'){
            LOCALITATE_PRINCIPALA = null;
        }
        data["LOCALITATE_PRINCIPALA"] = LOCALITATE_PRINCIPALA;


        var preValue = $("#am_select_localitate_apartinatoare").val();

        var jData = JSON.stringify(data);

        var url = "/dmsws/lov/values_by_code_with_dep/"+ PageManager.COD_LOV_LOCALITATE_APARTINATOARE;
        $.ajax({
            url: url,
            data: jData,
            method: 'post',
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                if (data.result == 'OK') {

                    if (data.lov != null && data.lov.length != 0) {

                        //apelam render template pentru a popula tabelul cu rezultatele obtinute
                        that.renderTemplate('tmpl_localitate', {data: data.lov}).then(function (html) {
                            tblHolder = $('#am_select_localitate_apartinatoare');
                            tblHolder.html(html);

                            if (typeof preValue !== 'undefined' && preValue !== null){
                                $("#am_select_localitate_apartinatoare").val(preValue);
                            }

                            $("#am_select_localitate_apartinatoare").trigger("chosen:updated");

                            if (typeof cb === 'function'){
                                cb()
                            }
                        }, function () {
                            that.alert('Unable to render template', 'ERROR');
                        });
                    }
                }
            }
        });
    },

    getLovLocalitateApartinatoareAga: function(cb){
        var that=this;

        var data = {};

        var LOCALITATE_PRINCIPALA = $("#aga_select_localitate_principala").val();
        if (typeof LOCALITATE_PRINCIPALA === 'undefined'){
            LOCALITATE_PRINCIPALA = null;
        }
        data["LOCALITATE_PRINCIPALA"] = LOCALITATE_PRINCIPALA;

        var preValue = $("#aga_select_localitate_apartinatoare").val();

        var jData = JSON.stringify(data);

        var url = "/dmsws/lov/values_by_code_with_dep/"+ PageManager.COD_LOV_LOCALITATE_APARTINATOARE;
        $.ajax({
            url: url,
            data: jData,
            method: 'post',
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                if (data.result == 'OK') {

                    if (data.lov != null && data.lov.length != 0) {

                        //apelam render template pentru a popula tabelul cu rezultatele obtinute
                        that.renderTemplate('tmpl_localitate', {data: data.lov}).then(function (html) {
                            tblHolder = $('#aga_select_localitate_apartinatoare');
                            tblHolder.html(html);

                            if (typeof preValue !== 'undefined' && preValue !== null){
                                $("#aga_select_localitate_apartinatoare").val(preValue);
                            }

                            $("#aga_select_localitate_apartinatoare").trigger("chosen:updated");

                            if (typeof cb === 'function'){
                                cb()
                            }
                        }, function () {
                            that.alert('Unable to render template', 'ERROR');
                        });
                    }
                }
            }
        });
    },


    getLovDomeniu: function(){
        // $('#resize_iframe', window.parent.document).trigger('click');
        var that=this;
        var PROC_NAME = "PageManager.getLovDomeniu";

        var url = "/dmsws/lov/values_by_code/"+ PageManager.COD_LOV_DOMENIU;
        $.ajax({
            url: url,
            success: function (data) {
                if (data.result == 'OK') {

                    if (data.lov != null && data.lov.length != 0) {

                        //apelam render template pentru a popula tabelul cu rezultatele obtinute
                        that.renderTemplate('tmpl_domeniu', {data: data.lov}).then(function (html) {
                            var tblHolder = $('#select_domeniu');
                            tblHolder.html(html);

                            $("#select_domeniu").trigger("chosen:updated");

                        }, function () {
                            that.alert('Unable to render template', 'ERROR');
                        });
                    }
                }
            }
        });

        //$UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },

    getLovJudet: function(){
        // $('#resize_iframe', window.parent.document).trigger('click');
        var that=this;
        var PROC_NAME = "PageManager.getLovJudet";

        var url = "/dmsws/lov/values_by_code/"+ PageManager.COD_LOV_JUDET;
        $.ajax({
            url: url,
            success: function (data) {
                if (data.result == 'OK') {

                    if (data.lov != null && data.lov.length != 0) {

                        //apelam render template pentru a popula tabelul cu rezultatele obtinute
                        that.renderTemplate('tmpl_judet', {data: data.lov}).then(function (html) {

                            tblHolder = $('#am_select_judet');
                            tblHolder.html(html);

                            $("#am_select_judet").trigger("chosen:updated");


                            tblHolder = $('#aga_select_judet');
                            tblHolder.html(html);

                            $("#aga_select_judet").trigger("chosen:updated");


                            tblHolder = $('#sed_select_judet');
                            tblHolder.html(html);
                            $("#sed_select_judet").trigger("chosen:updated");


                        }, function () {
                            that.alert('Unable to render template', 'ERROR');
                        });
                    }
                }
            }
        });

        //$UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },


    getLovStareaFirmei: function(){
        // $('#resize_iframe', window.parent.document).trigger('click');
        var that=this;
        var PROC_NAME = "PageManager.getLovStareaFirmei";

        var url = "/dmsws/lov/values_by_code/"+ PageManager.COD_LOV_STAREA_FIRMEI;
        $.ajax({
            url: url,
            success: function (data) {
                if (data.result == 'OK') {

                    if (data.lov != null && data.lov.length != 0) {

                        //apelam render template pentru a popula tabelul cu rezultatele obtinute
                        that.renderTemplate('tmpl_starea_firmei', {data: data.lov}).then(function (html) {

                            var tblHolder = $('#select_starea_firmei');
                            tblHolder.html(html);

                            $("#select_starea_firmei").trigger("chosen:updated");

                        }, function () {
                            that.alert('Unable to render template', 'ERROR');
                        });
                    }
                }
            }
        });

        //$UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },


    getLovCodCaen: function(){

        // $('#resize_iframe', window.parent.document).trigger('click');
        var that=this;
        var PROC_NAME = "PageManager.getLovCodCaen";

        var url = "/dmsws/lov/values_by_code/"+ PageManager.COD_LOV_COD_CAEN;
        $.ajax({
            url: url,
            success: function (data) {
                if (data.result == 'OK') {

                    if (data.lov != null && data.lov.length != 0) {

                        //apelam render template pentru a popula tabelul cu rezultatele obtinute
                        that.renderTemplate('tmpl_cod_caen', {data: data.lov}).then(function (html) {

                            tblHolder = $('#sed_select_cod_caen');
                            tblHolder.html(html);
                            $("#sed_select_cod_caen").trigger("chosen:updated");

                            tblHolder = $('#select_cod_caen');
                            tblHolder.html(html);
                            $("#select_cod_caen").trigger("chosen:updated");

                        }, function () {
                            that.alert('Unable to render template', 'ERROR');
                        });
                    }
                }
            }
        });
    },

    prepareOpenTabCertConstatator: function (jReq) {
        var that = this;
        var PROC_NAME = "PageManager.getCertificatConstatator";
        //$UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);

        var url ="/dmsws/anre/getTertInfoById";

        console.log("Ajax Call Start:" + url);

        $.ajax({
            url: url,

            success: function (data) {
                if (data.result == 'OK') {
                    that.infoTert = data;

                    $('#nr_cert_constatator').val(that.infoTert.numarCertificatConstatator);
                    $('#data_emitere').val(that.infoTert.dataEmitereCertificatConstatator);
                    $('#capitalul_social').val(that.infoTert.capitalulSocial);
                    $('#durata_de_constituire').val(that.infoTert.durataDeConstituire);
                    $('#info').val(that.infoTert.info);


                    PageManager.setLovJudetCertConst(that.infoTert.idJudetCertificatConstatator);
                    PageManager.setLovStareaFirmeiCertConst(that.infoTert.idStareaFirmei);
                    PageManager.setLovCodCaenCertConstatator(that.infoTert.codCaenStr);
               //     $('#select_judet').val(infoTert.idJudetCertificatConstatator).trigger("chosen:updated");

                }

            }
        });
      //  //$UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },
    saveCertificatConstatator: function(){
        var that=this;
        var PROC_NAME = "PageManager.adaugaContact";

        var jReq= PageManager.getInfoCertConstatator();
        var url = "/dmsws/anre/updateCertificatConstatator/";
        if(jReq!=null && typeof jReq!='undefined'){
            $.ajax({
                url: url,
                method:'POST',
                dataType: 'json',
                contentType: "application/json; charset=utf-8",
                data:jReq,
                success: function (data) {
                    if (data.result == 'OK') {
                        $.fancybox.close();


                        Swal.fire({
                            icon: "info",
                            html: "Certificatul constatator a fost salvat.",
                            focusConfirm: false,
                            confirmButtonText: "Ok"
                        });
                        PageManager.afiseazaInregistrari();

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

        }

      //  //$UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },
    afiseazaInregistrari: function () {
        var PROC_NAME = "PageManager.afiseazaInregistrari";


        //apelam reconstructie paginare & afisare rezultate
        PageManager.getListaInregistrari();

        //$UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },

    getListaInregistrari: function (jReq) {

        var that = this;
        var PROC_NAME = "PageManager.getListaInregistrari";
        //$UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);

        var url ="/dmsws/anre/getListaPersoaneContact";

        console.log("Ajax Call Start:" + url);

        $.ajax({
            url: url,

            success: function (data) {
                if (data.result == 'OK') {
                    that.listaInregistrari = data.persoaneContactList;


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
                        that.renderTemplate('tmpl_inregistrari_list', {data: data.persoaneContactList}).then(function (html) {
                            var tblHolder = $('.table_inregistrari');
                            tblHolder.html('');
                            tblHolder.html(html);

                            //tratare bife reprezentant si imputernicit
                             for (var i = 0; i < data.persoaneContactList.length; i++) {
                                if (data.persoaneContactList[i].reprezentant != null && data.persoaneContactList[i].reprezentant == 1) {
                                    document.getElementById("reprezentant_"+data.persoaneContactList[i].id).checked=true;

                                }
                                 if (data.persoaneContactList[i].imputernicit != null && data.persoaneContactList[i].imputernicit == 1) {
                                     document.getElementById("imputernicit_"+data.persoaneContactList[i].id).checked=true;


                                 }


                            }
                            $('.dataTable_nosearch').DataTable( {
                                 "searching": true,
                                language: {
                                    info:           "Se afișează _START_ - _END_ din _TOTAL_ intrări",
                                    infoEmpty:      "Se afișează 0 - 0 din 0 intrări",
                                    lengthMenu:     "Se afișează _MENU_ intrări",
                                    zeroRecords:    "Nu au fost găsite înregistrări care să se potrivească",
                                    paginate: {
                                        first:      "Prima",
                                        previous:   "Inapoi",
                                        next:       "Inainte",
                                        last:       "Ultima"
                                    },
                                   search:"Cauta in tabel ..."
                                }
                            });
                        }, function () {
                            that.alert('Unable to render template', 'ERROR');
                        });
                   // }

                }

            }
        });
        //$UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },

    getListaActionari: function (jReq) {

        var that = this;
        var PROC_NAME = "PageManager.getListaActionari";
        //$UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);

        var url ="/dmsws/anre/getListaActionari";

        console.log("Ajax Call Start:" + url);

        $.ajax({
            url: url,

            success: function (data) {
                if (data.result == 'OK') {
                    that.listaActionari = data.actionariList;


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
                    that.renderTemplate('tmpl_actionari_list', {data: data.actionariList}).then(function (html) {

                        var tblHolder = $('.table_inregistrari_actionari');
                        tblHolder.html('');
                        tblHolder.html(html);


                        $('.dataTable_nosearch2').DataTable( {
                            "searching": true,
                            language: {
                                info:           "Se afișează _START_ - _END_ din _TOTAL_ intrări",
                                infoEmpty:      "Se afișează 0 - 0 din 0 intrări",
                                lengthMenu:     "Se afișează _MENU_ intrări",
                                zeroRecords:    "Nu au fost găsite înregistrări care să se potrivească",
                                paginate: {
                                    first:      "Prima",
                                    previous:   "Inapoi",
                                    next:       "Inainte",
                                    last:       "Ultima"
                                },
                                search:"Cauta in tabel ..."
                            }
                        });
                    }, function () {
                        that.alert('Unable to render template', 'ERROR');
                    });
                    // }

                }

            }
        });
        //$UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },

    getInregistrariSedii: function () {
        var that = this;

        var url ="/dmsws/anre/getInregistrariSedii";

        console.log("Ajax Call Start:" + url);

        $.ajax({
            url: url,

            success: function (data) {
                if (data.result == 'OK') {
                    that.listaSedii = data.sediuList;

                    that.renderTemplate('tmpl_sedii_list', {data: data.sediuList}).then(function (html) {

                        var tblHolder = $('.table_inregistrari_sedii');
                        tblHolder.html('');
                        tblHolder.html(html);

                        $('.dataTable_nosearch3').DataTable( {
                            "searching": true,
                            language: {
                                info:           "Se afișează _START_ - _END_ din _TOTAL_ intrări",
                                infoEmpty:      "Se afișează 0 - 0 din 0 intrări",
                                lengthMenu:     "Se afișează _MENU_ intrări",
                                zeroRecords:    "Nu au fost găsite înregistrări care să se potrivească",
                                paginate: {
                                    first:      "Prima",
                                    previous:   "Inapoi",
                                    next:       "Inainte",
                                    last:       "Ultima"
                                },
                                search:"Cauta in tabel ..."
                            }
                        });
                    }, function () {
                        that.alert('Unable to render template', 'ERROR');
                    });
                }
            }
        });
    },
    getContactInfo: function (id) {


        var that = this;
        var PROC_NAME = "PageManager.getContactInfo";
        //$UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);
        PageManager.idForUpt=id;
        var url ="/dmsws/anre/getPersoanaContactById/"+id;

        console.log("Ajax Call Start:" + url);

        $.ajax({
            url: url,

            success: function (data) {
                if (data.result == 'OK') {

                    that.listaInregistrari = data.persoaneContactList;


                    $("#editeaza_nume").val(that.listaInregistrari[0].nume);
                    $("#editeaza_descriere").val(that.listaInregistrari[0].descriere);
                    $("#editeaza_email").val(that.listaInregistrari[0].email);
                    $("#editeaza_telefon").val(that.listaInregistrari[0].telefon);
                    $("#editeaza_fax").val(that.listaInregistrari[0].fax);
                    $("#editeaza_functie").val(that.listaInregistrari[0].functie);
                    var idDep=that.listaInregistrari[0].departament;
                    // $("#editeaza_reprezentant").val(that.listaInregistrari[0].reprezentant);
                    // $("#editeaza_imputernicit").val(that.listaInregistrari[0].imputernicit);
                    if (that.listaInregistrari[0].reprezentant != null && that.listaInregistrari[0].reprezentant == 1) {
                        document.getElementById("editeaza_reprezentant").checked=true;

                    }
                    if (that.listaInregistrari[0].imputernicit != null && that.listaInregistrari[0].imputernicit == 1) {
                        document.getElementById("editeaza_imputernicit").checked=true;
                    }

                    $("#editeaza_departament").val(that.listaInregistrari[0].departament);
                }
            }
        });
        //$UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },
     getActionarInfo: function (id) {


        var that = this;
        var PROC_NAME = "PageManager.getContactInfo";
        //$UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);
        PageManager.idForUptAC=id;
        var url ="/dmsws/anre/getActionarById/"+id;

        console.log("Ajax Call Start:" + url);

        $.ajax({
            url: url,

            success: function (data) {
                if (data.result == 'OK') {
                    that.listaActionari = data.actionariList;


                    $("#editeaza_actionar").val(that.listaActionari[0].actionar);
                    $("#editeaza_procente").val(that.listaActionari[0].procente);
                    $("#editeaza_nr_actiuni").val(that.listaActionari[0].nr_actiuni);
                    $("#editeaza_valoare").val(that.listaActionari[0].valoare);

                    // $("#editeaza_moneda").val(that.listaActionari[0].moneda);
                    // $("#editeaza_tip_Capital").val(that.listaActionari[0].tip_capital);

                    var id_moneda=that.listaActionari[0].id_moneda;
                    var urlMonAc="/dmsws/lov/values_by_code/"+ PageManager.COD_LOV_MONEDA_A;
                    $.ajax({
                        url: urlMonAc,
                        success: function (data) {
                            if (data.result == 'OK') {

                                if (data.lov != null && data.lov.length != 0) {

                                    //apelam render template pentru a popula tabelul cu rezultatele obtinute
                                    that.renderTemplate('tmpl_moneda_actionar', {data: data.lov}).then(function (html) {

                                        var tblHolder = $('#container_select_editeaza_moneda');
                                        tblHolder.html(html);
                                    if(id_moneda!=null && id_moneda!=undefined && id_moneda!="") {
                                        $("#container_select_editeaza_moneda").val(id_moneda).trigger("chosen:updated");
                                    }else{
                                        $("#container_select_editeaza_moneda").trigger("chosen:updated");

                                    }
                                    }, function () {
                                        that.alert('Unable to render template', 'ERROR');
                                    });


                                }
                            }
                        }
                    });
                    var id_tip_capital=that.listaActionari[0].id_tip_capital;
                    var urlAc= "/dmsws/lov/values_by_code/"+ PageManager.COD_LOV_TIP_CAPITAL_A;
                    $.ajax({
                        url: urlAc,
                        success: function (data) {
                            if (data.result == 'OK') {

                                if (data.lov != null && data.lov.length != 0) {

                                    //apelam render template pentru a popula tabelul cu rezultatele obtinute
                                    that.renderTemplate('tmpl_tip_capital', {data: data.lov}).then(function (html) {

                                        var tblHolder = $('#container_select_editeaza_tip_capital');
                                        tblHolder.html(html);
                                        if(id_tip_capital!=null && id_tip_capital!="" && id_tip_capital!=undefined) {
                                            $("#container_select_editeaza_tip_capital").val(id_tip_capital).trigger("chosen:updated");
                                        }else{
                                            $("#container_select_editeaza_tip_capital").trigger("chosen:updated");

                                        }
                                    }, function () {
                                        that.alert('Unable to render template', 'ERROR');
                                    });


                                }
                            }
                        }
                    });


                }

            }
        });
        //$UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },

    prepareOpenSediuNou: function(){
        var that = this;

        $("#sed_nume").val('');
        $("#sed_select_cod_caen").val([]);
        $("#sed_nr_reg_com").val('');
        $("#sed_euid").val('');
        $("#sed_adresa").val('');
        $("#sed_cod_postal").val('');
        $("#sed_select_judet").val('');
        $("#sed_select_localitate_mica").val('');
        $("#sed_select_localitate_mare").val('');


        $('#sed_select_cod_caen').trigger("chosen:updated");
        $('#sed_select_judet').trigger("chosen:updated");
        $('#sed_select_localitate_mica').trigger("chosen:updated");
        $('#sed_select_localitate_mare').trigger("chosen:updated");

        that.MOD_SEDIU = 'ADD';
    },

    getSediuInfo: function (id) {
        var that = this;

        that.MOD_SEDIU = 'EDIT';

        PageManager.idForUptSD = id;
        var url ="/dmsws/anre/getSediuInfo/"+id;

        console.log("Ajax Call Start:" + url);

        $.ajax({
            url: url,

            success: function (data) {
                if (data.result == 'OK') {
                    that.listaSedii = data.sediuList;
                    if (data.sediuList[0].id !== null && data.sediuList[0].id!=undefined) {
                        $("#sed_nume").val(that.listaSedii[0].denumire);
                        $("#sed_select_cod_caen").val(that.strToArr(that.listaSedii[0].cod_caen));
                        $("#sed_nr_reg_com").val(that.listaSedii[0].nr_reg_com);
                        $("#sed_euid").val(that.listaSedii[0].euid);
                        $("#sed_adresa").val(that.listaSedii[0].adresa);
                        $("#sed_cod_postal").val(that.listaSedii[0].cod_postal);
                        $("#sed_select_judet").val(that.listaSedii[0].id_judet);
                        $("#sed_select_localitate_mica").val(that.listaSedii[0].id_localitate_mica);
                        $("#sed_select_localitate_mare").val(that.listaSedii[0].id_localitate_mare);



                        $('#sed_select_cod_caen').trigger("chosen:updated");
                        $('#sed_select_judet').trigger("chosen:updated");
                        $('#sed_select_localitate_mica').trigger("chosen:updated");
                        $('#sed_select_localitate_mare').trigger("chosen:updated");
                    }
                }
            }
        });
    },

    salveazaModificarilePrepare: function(){
        var that = this;
        var PROC_NAME = "PageManager.salveazaModificarile";


        var nume=$("#editeaza_nume").val();
        var descriere=$("#editeaza_descriere").val();
        var email=$("#editeaza_email").val();
        var telefon=$("#editeaza_telefon").val();
        var fax=$("#editeaza_fax").val();
        var functie=$("#editeaza_functie").val();
        var departament=$("#editeaza_departament").val();
        var reprezentant=$("#editeaza_reprezentant").prop('checked');
        var imputernicit=$("#editeaza_imputernicit").prop('checked');
        var reprezentantC;
        var imputernicitC;

        if(reprezentant!=null && typeof reprezentant!='undefined' && reprezentant==true){
            reprezentantC=1;
        }else{
            reprezentantC=0;
        }

        if(imputernicit!=null && typeof imputernicit!='undefined' && imputernicit==true){
            imputernicitC=1;
        }else{
            imputernicitC=0;
        }
        var obj = {
            id:PageManager.idForUpt,
            nume: nume,
            descriere: descriere,
            email: email,
            telefon:telefon,
            fax:fax,
            functie:functie,
            departament:departament,
            reprezentant:reprezentantC,
            imputernicit:imputernicitC
        };
        return JSON.stringify(obj)




    },
    salveazaModificarileActionarPrepare: function(){
        var that = this;
        var PROC_NAME = "PageManager.salveazaModificarileActionarPrepare";


        var editeaza_actionar=$("#editeaza_actionar").val();
        var editeaza_tip_Capital=$("#container_select_editeaza_tip_capital").val();
        var editeaza_procente=$("#editeaza_procente").val();
        var editeaza_nr_actiuni=$("#editeaza_nr_actiuni").val();
        var editeaza_valoare=$("#editeaza_valoare").val();
        var editeaza_moneda=$("#container_select_editeaza_moneda").val();



        var obj = {
            id:PageManager.idForUptAC,
            actionar: editeaza_actionar,
            id_tip_capital: editeaza_tip_Capital,
            procente: editeaza_procente,
            nr_actiuni:editeaza_nr_actiuni,
            valoare:editeaza_valoare,
            id_moneda:editeaza_moneda
        };
        return obj;




    },
    salveazaModificarileSediuPrepare: function(){
        var that = this;

        var sed_nume=$("#sed_nume").val();
        var sed_select_cod_caen=$("#sed_select_cod_caen").val().toString();
        var sed_nr_reg_com=$("#sed_nr_reg_com").val();
        var sed_euid=$("#sed_euid").val();
        var sed_adresa=$("#sed_adresa").val();
        var sed_select_judet=$("#sed_select_judet").val();
        var sed_select_localitate_mica=$("#sed_select_localitate_mica").val();
        var sed_select_localitate_mare=$("#sed_select_localitate_mare").val();
        var sed_cod_postal=$("#sed_cod_postal").val();

        var obj = {
            id : PageManager.idForUptSD,
            id_tert: that.id_tert,
            denumire: sed_nume,
            cod_caen: sed_select_cod_caen,
            nr_reg_com: sed_nr_reg_com,
            euid: sed_euid,
            adresa: sed_adresa,
            id_judet: sed_select_judet,
            id_localitate_mica: sed_select_localitate_mica,
            id_localitate_mare: sed_select_localitate_mare,
            cod_postal: sed_cod_postal
        };
        return JSON.stringify(obj)
    },
    editeazaContact: function(){
        var that=this;
        var PROC_NAME = "PageManager.editeazaContact";

        var jReq= PageManager.salveazaModificarilePrepare();
        var url = "/dmsws/anre/updateInfoContact";
        if(jReq!=null && typeof jReq!='undefined'){
            $.ajax({
                url: url,
                method:'POST',
                dataType: 'json',
                contentType: "application/json; charset=utf-8",
                data:jReq,
                success: function (data) {
                    if (data.result == 'OK') {
                        PageManager.afiseazaInregistrari();

                        $.fancybox.close();
                        Swal.fire({
                            icon: "info",
                            html: "Modificarile au fost salvate cu succes.",
                            focusConfirm: false,
                            confirmButtonText: "Ok"
                        });

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

        }

        //$UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },


    editeazaActionar: function(){
        var that=this;
        var PROC_NAME = "PageManager.editeazaActionar";

        var req = PageManager.salveazaModificarileActionarPrepare();
        var jReq = JSON.stringify(req);

        var ok = true;
        if (typeof req.nr_actiuni === 'undefined' || req.nr_actiuni === null || req.nr_actiuni.trim() === '') {
            that.animateElementErr("#editeaza_actiuni");
            ok = false;
        }
        if (typeof req.valoare === 'undefined' || req.valoare === null || req.valoare.trim() === '') {
            that.animateElementErr("#editeaza_valoare");
            ok = false;
        }
        if (typeof req.actionar === 'undefined' || req.actionar === null || req.actionar.trim() === '') {
            that.animateElementErr("#editeaza_actionar");
            ok = false;
        }

        if (!ok) {
            Swal.fire({
                icon: "error",
                html: 'Completati coloanele obligatorii',
                focusConfirm: false,
                confirmButtonText: "Ok",
                customClass: {
                    container: 'high-swal'
                }
            });
            return;
        }

        var url = "/dmsws/anre/updateInfoActionar";
        if(jReq!=null && typeof jReq!='undefined'){
            $.ajax({
                url: url,
                method:'POST',
                dataType: 'json',
                contentType: "application/json; charset=utf-8",
                data:jReq,
                success: function (data) {
                    if (data.result == 'OK') {
                        $.fancybox.close();
                        Swal.fire({
                            icon: "info",
                            html: "Modificarile au fost salvate cu succes.",
                            focusConfirm: false,
                            confirmButtonText: "Ok"
                        });
                        PageManager.afiseazaInregistrariActionari();

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

        }

        //$UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },

    editeazaSediu: function(){
        var that=this;

        var jReq = PageManager.salveazaModificarileSediuPrepare();

        var url = null;
        if (that.MOD_SEDIU == 'EDIT') {
            url = "/dmsws/anre/editeazaSediu";
        } else {
            url = "/dmsws/anre/adaugaSediu";
        }

        if(jReq!=null && typeof jReq!='undefined'){
            $.ajax({
                url: url,
                method:'POST',
                dataType: 'json',
                contentType: "application/json; charset=utf-8",
                data:jReq,
                success: function (data) {
                    if (data.result == 'OK') {

                        $.fancybox.close();
                        Swal.fire({
                            icon: "info",
                            html: "Modificarile au fost salvate cu succes.",
                            focusConfirm: false,
                            confirmButtonText: "Ok"
                        });

                        that.getInregistrariSedii();

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
        }
    },
    mandatoryFunctions: function () {
        var PROC_NAME = "PageManager.mandatoryFunctions";
        //.log(PAGE_NAME, PROC_NAME, 'Enter', 0);
        //-------------- preluam WS_URL si WS_TOKEN din documentaPortal-util.js ---------------------


        //-------------- initializam template-uri mustache ---------------------
        PageManager.compileAllTemplates();

        //-------------- initializam lov-uri ---------------------
        PageManager.getLovLocalitate();
        PageManager.getLovJudet();
        PageManager.getLovLocalitate();
        PageManager.getLovStareaFirmei();
        PageManager.getLovCodCaen();
        PageManager.prepareOpenTabCertConstatator();
        PageManager.getLovDomeniu();

        //$UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },
    compileAllTemplates: function () {
        this.templates['tmpl_inregistrari_list'] = $('#tmpl_inregistrari_list').html();
        this.templates['tmpl_actionari_list'] = $('#tmpl_actionari_list').html();
        this.templates['tmpl_sedii_list'] = $('#tmpl_sedii_list').html();
        this.templates['tmpl_departament'] = $('#tmpl_departament').html();
        this.templates['tmpl_tip_capital'] = $('#tmpl_tip_capital').html();
        this.templates['tmpl_moneda_actionar'] = $('#tmpl_moneda_actionar').html();
        this.templates['tmpl_inregistrari_list_conducere'] = $('#tmpl_inregistrari_list_conducere').html();
        this.templates['tmpl_inregistrari_list_sucursala'] = $('#tmpl_inregistrari_list_sucursala').html();
        this.templates['tmpl_inregistrari_list_aut_mediu'] = $('#tmpl_inregistrari_list_aut_mediu').html();
        this.templates['tmpl_inregistrari_list_aut_gosp_apelor'] = $('#tmpl_inregistrari_list_aut_gosp_apelor').html();


        this.templates['tmpl_judet'] = $('#tmpl_judet').html();
        this.templates['tmpl_starea_firmei'] = $('#tmpl_starea_firmei').html();
        this.templates['tmpl_cod_caen'] = $('#tmpl_cod_caen').html();
        this.templates['tmpl_localitate'] = $('#tmpl_localitate').html();
        this.templates['tmpl_domeniu'] = $('#tmpl_domeniu').html();

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
    },
    adaugaConducere: function(){
        var that=this;
        var PROC_NAME = "PageManager.adaugaConducere";

        var jReq= PageManager.getInfoConducere();

        if(MOD_CONDUCERE=="ADD"){
            var url = "/dmsws/anre/adauga_conducere/";
        }else{
            var url = "/dmsws/anre/editeaza_conducere/"+ID_CONDUCERE;
        }
        if(jReq!=null && typeof jReq!='undefined'){
            $.ajax({
                url: url,
                method:'POST',
                dataType: 'json',
                contentType: "application/json; charset=utf-8",
                data:jReq,
                success: function (data) {
                    if (data.result == 'OK') {
                        $.fancybox.close();


                        Swal.fire({
                            icon: "info",
                            html: "A fost actualizata persoana din conducere.",
                            focusConfirm: false,
                            confirmButtonText: "Ok"
                        });
                        PageManager.afiseazaInregistrariConducere();

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

        }

       //$UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },
    adaugaSucursala: function(){
        var that=this;
        var PROC_NAME = "PageManager.adaugaSucursala";

        var jReq= PageManager.prepareObjectSucursala();
        if(MOD_SUCURSALA=="ADD"){
            var url = "/dmsws/anre/adaugaSucursala/";
        }else{
            var url = "/dmsws/anre/editeazaSucursala/"+ID_SUCURSALA;
        }
        if(jReq!=null && typeof jReq!='undefined'){
            $.ajax({
                url: url,
                method:'POST',
                dataType: 'json',
                contentType: "application/json; charset=utf-8",
                data:jReq,
                success: function (data) {
                    if (data.result == 'OK') {
                        $.fancybox.close();


                        Swal.fire({
                            icon: "info",
                            html: "Sucursalele au fost actualizate.",
                            focusConfirm: false,
                            confirmButtonText: "Ok"
                        });
                        PageManager.afiseazaInregistrariSucursala();

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

        }

        //$UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },
    adaugaAutMediu: function(){
        var that=this;
        var PROC_NAME = "PageManager.adaugaAutMediu";

        var req = that.prepareObjectAutMediu();
        var jReq = JSON.stringify(req);

        var ok = true;
        if (typeof req.aut_emitent === 'undefined' || req.aut_emitent === null || req.aut_emitent.trim() === '') {
            that.animateElementErr("#am_aut_emitent");
            ok = false;
        }
        // if (typeof req.nr_inreg_emitent === 'undefined' || req.nr_inreg_emitent === null || req.nr_inreg_emitent.trim() === '') {
        //     that.animateElementErr("#am_nr_inreg_emitent");
        //     ok = false;
        // }
        // if (typeof req.data_inreg_emitent === 'undefined' || req.data_inreg_emitent === null || req.data_inreg_emitent.trim() === '') {
        //     that.animateElementErr("#am_data_inreg_emitent");
        //     ok = false;
        // }

        if (!ok) {
            Swal.fire({
                icon: "error",
                html: 'Completati coloanele obligatorii',
                focusConfirm: false,
                confirmButtonText: "Ok",
                customClass: {
                    container: 'high-swal'
                }
            });
            return;
        }


        if (typeof req.aut_emitent === 'undefined' || req.aut_emitent === null || req.aut_emitent.trim() === ''){
            that.animateElementErr("#am_aut_emitent");

            Swal.fire({
                icon: "error",
                html: 'Completati emitentul',
                focusConfirm: false,
                confirmButtonText: "Ok",
                customClass: {
                    container: 'high-swal'
                }
            });

            return;
        }

        if(MOD_AUT_MEDIU=="ADD"){
            var url = "/dmsws/anre/adaugaAutMediu/";
        }else{
            var url = "/dmsws/anre/editeazaAutMediu/"+ID_AUT_MEDIU;
        }
        if(jReq!=null && typeof jReq!='undefined'){
            $.ajax({
                url: url,
                method:'POST',
                dataType: 'json',
                contentType: "application/json; charset=utf-8",
                data:jReq,
                success: function (data) {
                    if (data.result == 'OK') {
                        $.fancybox.close();

                        Swal.fire({
                            icon: "info",
                            html: "Autorizatiile mediu au fost actualizate.",
                            focusConfirm: false,
                            confirmButtonText: "Ok"
                        });
                        that.afiseazaInregistrariAutMediu();

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
        }

        //$UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },
    adaugaAutGospApelor: function(){
        var that=this;
        var PROC_NAME = "PageManager.adaugaAutGospApelor";

        var req = that.prepareObjectAutGospApelor();
        var jReq = JSON.stringify(req);

        if (typeof req.emitent === 'undefined' || req.emitent === null || req.emitent.trim() === ''){
            that.animateElementErr("#aga_emitent");

            Swal.fire({
                icon: "error",
                html: 'Completati emitentul',
                focusConfirm: false,
                confirmButtonText: "Ok",
                customClass: {
                    container: 'high-swal'
                }
            });

            return;
        }

        if(MOD_AUT_GOSP_APELOR=="ADD"){
            var url = "/dmsws/anre/adaugaAutGospApelor/";
        }else{
            var url = "/dmsws/anre/editeazaAutGospApelor/"+ID_AUT_GOSP_APELOR;
        }
        if(jReq!=null && typeof jReq!='undefined'){
            $.ajax({
                url: url,
                method:'POST',
                dataType: 'json',
                contentType: "application/json; charset=utf-8",
                data:jReq,
                success: function (data) {
                    if (data.result == 'OK') {
                        $.fancybox.close();

                        Swal.fire({
                            icon: "info",
                            html: "Autorizatiile de gospodarirea apelor au fost actualizate.",
                            focusConfirm: false,
                            confirmButtonText: "Ok"
                        });
                        that.afiseazaInregistrariAutGospApelor();

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
        }

        //$UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },
    getInfoConducere: function(){
        var that=this;
        var PROC_NAME = "PageManager.getInfoConducere";

        var nume= $("#conducere_nume").val();
        var prenume= $("#conducere_prenume").val();
        var functie= $("#conducere_functie").val();
        var reprez_legal= $("#conducere_reprez_legal").prop('checked');
        var manager= $("#conducere_manager").prop('checked');
        var resp_tehnic= $("#conducere_resp_tehnic").prop('checked');

        var an_absolvire= $("#conducere_an_absolvire").val();
        var termen_expirare_mandat= $("#conducere_termen_expirare_mandat").val();
        var vechime= $("#conducere_vechime").val();
        var nr_adresa_cv= $("#conducere_nr_adresa_cv").val();
        var data_adresa_cv= $("#conducere_data_adresa_cv").val();
        var studii= $("#conducere_studii").val();

        if(reprez_legal!=null && typeof reprez_legal!='undefined' && reprez_legal==true){
            reprez_legal=1;
        }else{
            reprez_legal=0;
        }

        if(manager!=null && typeof manager!='undefined' && manager==true){
            manager=1;
        }else{
            manager=0;
        }
        if(resp_tehnic!=null && typeof resp_tehnic!='undefined' && resp_tehnic==true){
            resp_tehnic=1;
        }else{
            resp_tehnic=0;
        }

        var objRand= {
            nume: nume,
            prenume: prenume,
            functie: functie,
            reprez_legal:reprez_legal,
            manager:manager,
            resp_tehnic:resp_tehnic,
            an_absolvire:an_absolvire,
            termen_expirare_mandat:termen_expirare_mandat,
            vechime:vechime,
            nr_adresa_cv:nr_adresa_cv,
            data_adresa_cv:data_adresa_cv,
            studii:studii
        };
        return JSON.stringify(objRand);

       //$UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },
    prepareObjectSucursala: function(){
        var that=this;
        var PROC_NAME = "PageManager.prepareObjectSucursala";

        var nume= $("#sucursala_nume").val();
        var id_localitate= $("#select_localitate").val();
        var adresa= $("#sucursala_adresa").val();
        var adresa_corespondenta= $("#sucursala_adresa_corespondenta").val();
        var id_domeniu = $("#select_domeniu").val();

        var objRand= {
            nume: nume,
            id_localitate: id_localitate,
            adresa: adresa,
            adresa_corespondenta:adresa_corespondenta,
            id_domeniu: id_domeniu
        };
        return JSON.stringify(objRand);

        //$UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },
    prepareObjectAutMediu: function(){
        var that=this;
        var PROC_NAME = "PageManager.prepareObjectAutMediu";

        var am_nr_inreg_emitent = $("#am_nr_inreg_emitent").val();
        var am_data_inreg_emitent = $("#am_data_inreg_emitent").val();
        var am_aut_emitent = $("#am_aut_emitent").val();
        var am_aut_numar = $("#am_aut_numar").val();
        var am_aut_data_emiterii = $("#am_aut_data_emiterii").val();
        var am_aut_data_expirarii = $("#am_aut_data_expirarii").val();
        var am_select_judet = $("#am_select_judet").val();
        var am_select_localitate_principala = $("#am_select_localitate_principala").val();
        var am_select_localitate_apartinatoare = $("#am_select_localitate_apartinatoare").val();
        var am_adresa = $("#am_adresa").val();
        var am_denumire = $("#am_denumire").val();
        var am_dec_emitent = $("#am_dec_emitent").val();
        var am_dec_numar = $("#am_dec_numar").val();
        var am_dec_data_emiterii = $("#am_dec_data_emiterii").val();
        var am_dec_data_expirarii = $("#am_dec_data_expirarii").val();
        var am_perioada_de_valabilitate = $("#am_perioada_de_valabilitate").val();

        var objRand= {
            nr_inreg_emitent: am_nr_inreg_emitent,
            data_inreg_emitent: am_data_inreg_emitent,
            aut_emitent: am_aut_emitent,
            aut_numar: am_aut_numar,
            aut_data_emiterii: am_aut_data_emiterii,
            aut_data_expirarii: am_aut_data_expirarii,
            judet: am_select_judet,
            localitate_principala: am_select_localitate_principala,
            localitate_apartinatoare: am_select_localitate_apartinatoare,
            adresa: am_adresa,
            denumire: am_denumire,
            dec_emitent: am_dec_emitent,
            dec_numar: am_dec_numar,
            dec_data_emiterii: am_dec_data_emiterii,
            dec_data_expirarii: am_dec_data_expirarii,
            perioada_de_valabilitate: am_perioada_de_valabilitate
        };

        return objRand;

        //$UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },
    prepareObjectAutGospApelor: function(){
        var that=this;
        var PROC_NAME = "PageManager.prepareObjectAutGospApelor";


        var aga_nr_inreg_emitent = $("#aga_nr_inreg_emitent").val();
        var aga_data_inreg_emitent = $("#aga_data_inreg_emitent").val();
        var aga_emitent = $("#aga_emitent").val();
        var aga_numar = $("#aga_numar").val();
        var aga_data_emiterii = $("#aga_data_emiterii").val();
        var aga_data_expirarii = $("#aga_data_expirarii").val();
        var aga_select_judet = $("#aga_select_judet").val();
        var aga_select_localitate_principala = $("#aga_select_localitate_principala").val();
        var aga_select_localitate_apartinatoare = $("#aga_select_localitate_apartinatoare").val();
        var aga_adresa = $("#aga_adresa").val();
        var aga_obiectul_autorizatiei = $("#aga_obiectul_autorizatiei").val();

        var objRand= {
            nr_inreg_emitent: aga_nr_inreg_emitent,
            data_inreg_emitent: aga_data_inreg_emitent,
            emitent: aga_emitent,
            numar: aga_numar,
            data_emiterii: aga_data_emiterii,
            data_expirarii: aga_data_expirarii,
            judet: aga_select_judet,
            localitate_principala: aga_select_localitate_principala,
            localitate_apartinatoare: aga_select_localitate_apartinatoare,
            adresa: aga_adresa,
            obiectul_autorizatiei: aga_obiectul_autorizatiei
        };

        return objRand;
        //$UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },
    openExpertiza: function(id_pers){
        location.href='experienta_manageriala.html?id_pers='+id_pers;
    },
    resetConducere:function(){
        MOD_CONDUCERE="ADD";
        $("#conducere_nume").val(null);
        $("#conducere_prenume").val(null);
        $("#conducere_functie").val(null);
        document.getElementById("conducere_reprez_legal").checked=false;
        document.getElementById("conducere_manager").checked=false;
        document.getElementById("conducere_resp_tehnic").checked=false;
        $("#conducere_an_absolvire").val(null);
        $("#conducere_termen_expirare_mandat").val(null);
        $("#conducere_vechime").val(null);
        $("#conducere_nr_adresa_cv").val(null);
        $("#conducere_data_adresa_cv").val(null);
        $("#conducere_studii").val(null);
    },
    resetSucursala:function(){
        MOD_SUCURSALA="ADD";
        $("#sucursala_nume").val(null);
        $("#select_localitate").val(null);
        $("#sucursala_adresa").val(null);
        $("#sucursala_adresa_corespondenta").val(null);
    },
    resetAutMediu:function(){
        MOD_AUT_MEDIU="ADD";
        $("#am_nr_inreg_emitent").val(null);
        $("#am_data_inreg_emitent").val(null);
        $("#am_aut_emitent").val(null);
        $("#am_aut_numar").val(null);
        $("#am_aut_data_emiterii").val(null);
        $("#am_aut_data_expirarii").val(null);
        $("#am_select_judet").val(null);
        $("#am_select_localitate_principala").val(null);
        $("#am_select_localitate_apartinatoare").val(null);
        $("#am_adresa").val(null);
        $("#am_denumire").val(null);
        $("#am_dec_emitent").val(null);
        $("#am_dec_numar").val(null);
        $("#am_dec_data_emiterii").val(null);
        $("#am_dec_data_expirarii").val(null);
        $("#am_perioada_de_valabilitate").val(null);

        $('#am_select_judet').trigger("chosen:updated");
        $('#am_select_localitate_principala').trigger("chosen:updated");
        $('#am_select_localitate_apartinatoare').trigger("chosen:updated");

    },
    resetAutGospApelor:function(){
        MOD_AUT_GOSP_APELOR="ADD";
        $("#aga_nr_inreg_emitent").val(null);
        $("#aga_data_inreg_emitent").val(null);
        $("#aga_emitent").val(null);
        $("#aga_numar").val(null);
        $("#aga_data_emiterii").val(null);
        $("#aga_data_expirarii").val(null);
        $("#aga_select_judet").val(null);
        $("#aga_select_localitate_principala").val(null);
        $("#aga_select_localitate_apartinatoare").val(null);
        $("#aga_adresa").val(null);
        $("#aga_obiectul_autorizatiei").val(null);


        $('#aga_select_judet').trigger("chosen:updated");
        $('#aga_select_localitate_principala').trigger("chosen:updated");
        $('#aga_select_localitate_apartinatoare').trigger("chosen:updated");
    },
    getConducere: function(id){
        var url ="/dmsws/anre/getConducere/"+id;
        MOD_CONDUCERE="EDIT";

        console.log("Ajax Call Start:" + url);
        $.ajax({
            url: url,

            success: function (data) {
                if (data.conducereList[0].id !== null && data.conducereList[0].id!=undefined) {
                    $("#conducere_nume").val(data.conducereList[0].nume);
                    $("#conducere_prenume").val(data.conducereList[0].prenume);
                    $("#conducere_functie").val(data.conducereList[0].functie);
                    if(data.conducereList[0].reprez_legal!=null && data.conducereList[0].reprez_legal=='1'){
                        document.getElementById("conducere_reprez_legal").checked=true;
                    }
                    if(data.conducereList[0].manager!=null && data.conducereList[0].manager=='1') {
                        document.getElementById("conducere_manager").checked=true;

                    }
                    if(data.conducereList[0].resp_tehnic!=null && data.conducereList[0].resp_tehnic=='1') {
                        document.getElementById("conducere_resp_tehnic").checked=true;

                    }
                    $("#conducere_an_absolvire").val(data.conducereList[0].an_absolvire);
                    $("#conducere_termen_expirare_mandat").val(data.conducereList[0].termen_expirare_mandat);
                    $("#conducere_vechime").val(data.conducereList[0].vechime);
                    $("#conducere_nr_adresa_cv").val(data.conducereList[0].nr_adresa_cv);
                    $("#conducere_data_adresa_cv").val(data.conducereList[0].data_adresa_cv);
                    $("#conducere_studii").val(data.conducereList[0].studii);
                    ID_CONDUCERE=data.conducereList[0].id;
                }}



        });
    },
    getSucursala: function(id){
        var url ="/dmsws/anre/getSucursala/"+id;
        MOD_SUCURSALA="EDIT";

        console.log("Ajax Call Start:" + url);
        $.ajax({
            url: url,

            success: function (data) {
                if (data.sucursalaList[0].id !== null && data.sucursalaList[0].id!=undefined) {
                    $("#sucursala_nume").val(data.sucursalaList[0].nume);
                    $('#select_localitate').val(data.sucursalaList[0].id_localitate).trigger("change");
                    $('#select_domeniu').val(data.sucursalaList[0].id_domeniu).trigger("change");
                    $("#sucursala_adresa").val(data.sucursalaList[0].adresa);
                    $("#sucursala_adresa_corespondenta").val(data.sucursalaList[0].adresa_corespondenta);
                    ID_SUCURSALA=data.sucursalaList[0].id;
                }}



        });
    },
    getAutMediu: function(id){
        var that = this;

        var url ="/dmsws/anre/getAutMediu/"+id;
        MOD_AUT_MEDIU="EDIT";

        console.log("Ajax Call Start:" + url);
        $.ajax({
            url: url,

            success: function (data) {
                if (data.autMediuList[0].id !== null && data.autMediuList[0].id!=undefined) {
                    $("#am_nr_inreg_emitent").val(data.autMediuList[0].nr_inreg_emitent);
                    $("#am_data_inreg_emitent").val(data.autMediuList[0].data_inreg_emitent);
                    $("#am_aut_emitent").val(data.autMediuList[0].aut_emitent);
                    $("#am_aut_numar").val(data.autMediuList[0].aut_numar);
                    $("#am_aut_data_emiterii").val(data.autMediuList[0].aut_data_emiterii);
                    $("#am_aut_data_expirarii").val(data.autMediuList[0].aut_data_expirarii);
                    $("#am_adresa").val(data.autMediuList[0].adresa);
                    $("#am_denumire").val(data.autMediuList[0].denumire);
                    $("#am_dec_emitent").val(data.autMediuList[0].dec_emitent);
                    $("#am_dec_numar").val(data.autMediuList[0].dec_numar);
                    $("#am_dec_data_emiterii").val(data.autMediuList[0].dec_data_emiterii);
                    $("#am_dec_data_expirarii").val(data.autMediuList[0].dec_data_expirarii);
                    $("#am_perioada_de_valabilitate").val(data.autMediuList[0].perioada_de_valabilitate);

                    $("#am_select_judet").val(data.autMediuList[0].judet);
                    $('#am_select_judet').trigger("chosen:updated");


                    that.getLovLocalitatePrincipalaAm(function(){
                        $("#am_select_localitate_principala").val(data.autMediuList[0].localitate_principala);
                        $('#am_select_localitate_principala').trigger("chosen:updated");

                        that.getLovLocalitateApartinatoareAm(function(){
                            $("#am_select_localitate_apartinatoare").val(data.autMediuList[0].localitate_apartinatoare);
                            $('#am_select_localitate_apartinatoare').trigger("chosen:updated");
                        })
                    });


                    ID_AUT_MEDIU=data.autMediuList[0].id;
                }}



        });
    },
    getAutGospApelor: function(id){
        var that = this;
        var url ="/dmsws/anre/getAutGospApelor/"+id;
        MOD_AUT_GOSP_APELOR="EDIT";

        console.log("Ajax Call Start:" + url);
        $.ajax({
            url: url,

            success: function (data) {
                if (data.autGospApelorList[0].id !== null && data.autGospApelorList[0].id!=undefined) {

                    $("#aga_nr_inreg_emitent").val(data.autGospApelorList[0].nr_inreg_emitent);
                    $("#aga_data_inreg_emitent").val(data.autGospApelorList[0].data_inreg_emitent);
                    $("#aga_emitent").val(data.autGospApelorList[0].emitent);
                    $("#aga_numar").val(data.autGospApelorList[0].numar);
                    $("#aga_data_emiterii").val(data.autGospApelorList[0].data_emiterii);
                    $("#aga_data_expirarii").val(data.autGospApelorList[0].data_expirarii);
                    $("#aga_adresa").val(data.autGospApelorList[0].adresa);
                    $("#aga_obiectul_autorizatiei").val(data.autGospApelorList[0].obiectul_autorizatiei);

                    $("#aga_select_judet").val(data.autGospApelorList[0].judet);
                    $('#aga_select_judet').trigger("chosen:updated");

                    that.getLovLocalitatePrincipalaAga(function(){
                        $("#aga_select_localitate_principala").val(data.autGospApelorList[0].localitate_principala);
                        $('#aga_select_localitate_principala').trigger("chosen:updated");

                        that.getLovLocalitateApartinatoareAga(function(){
                            $("#aga_select_localitate_apartinatoare").val(data.autGospApelorList[0].localitate_apartinatoare);
                            $('#aga_select_localitate_apartinatoare').trigger("chosen:updated");
                        })
                    });

                    ID_AUT_GOSP_APELOR=data.autGospApelorList[0].id;
                }}
        });
    },
    deleteConducere: function(id_pers){
        var that=this;
        var PROC_NAME = "PageManager.deleteExpertiza";



        var url = "/dmsws/anre/stergeConducere/"+id_pers;

        $.ajax({
            url: url,
            method:'GET',
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                if (data.result == 'OK') {
                    $.fancybox.close();


                    Swal.fire({
                        icon: "info",
                        html: "A fost stearsa persoana din conducere.",
                        focusConfirm: false,
                        confirmButtonText: "Ok"
                    });
                    PageManager.afiseazaInregistrariConducere();

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



     //   //$UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },
    deleteSucursala: function(id){
        var that=this;
        var PROC_NAME = "PageManager.deleteExpertiza";


        var url = "/dmsws/anre/stergeSucursala/"+id;

        $.ajax({
            url: url,
            method:'GET',
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                if (data.result == 'OK') {
                    $.fancybox.close();


                    Swal.fire({
                        icon: "info",
                        html: "Sucursala a fost stearsa cu succes.",
                        focusConfirm: false,
                        confirmButtonText: "Ok"
                    });
                    PageManager.afiseazaInregistrariSucursala();

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



        //   //$UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },


    deleteAutMediu: function(id){
        var that=this;
        var PROC_NAME = "PageManager.deleteAutMediu";


        var url = "/dmsws/anre/stergeAutMediu/"+id;

        $.ajax({
            url: url,
            method:'GET',
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                if (data.result == 'OK') {
                    $.fancybox.close();


                    Swal.fire({
                        icon: "info",
                        html: "Autorizatia a fost stearsa cu succes.",
                        focusConfirm: false,
                        confirmButtonText: "Ok"
                    });
                    PageManager.afiseazaInregistrariAutMediu();

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



        //   //$UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },

    deleteAutGospApelor: function(id) {
        var that = this;
        var PROC_NAME = "PageManager.deleteAutGospApelor";


        var url = "/dmsws/anre/stergeAutGospApelor/" + id;

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
                        html: "Autorizatia a fost stearsa cu succes.",
                        focusConfirm: false,
                        confirmButtonText: "Ok"
                    });
                    PageManager.afiseazaInregistrariAutGospApelor();

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

        //   //$UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },

    deleteAttachedFileAutMediu: function (id) {
        var that = this;
        var PROC_NAME = "PageManager.deleteAttachedFileAutMediu";

        var url = "/dmsws/anre/stergeFisierAutMediu/" + id;

        $.ajax({
            url: url,
            method: 'GET',
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                if (data.result == 'OK') {
                    $.fancybox.close();

                    PageManager.afiseazaInregistrariAutMediu();

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
    },

    deleteAttachedFileAutGospApelor: function (id) {
        var that = this;
        var PROC_NAME = "PageManager.deleteAttachedFileAutGospApelor";


        var url = "/dmsws/anre/stergeFisierAutGospApelor/" + id;

        $.ajax({
            url: url,
            method: 'GET',
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                if (data.result == 'OK') {
                    $.fancybox.close();

                    PageManager.afiseazaInregistrariAutGospApelor();

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
    },


    saveAttachedFileNameAutMediu: function (id) {
        var that = this;
        var PROC_NAME = "PageManager.saveAttachedFileNameAutMediu";

        var url = "/dmsws/anre/updateFisierAutMediu/" + id;

        var name = $("#file_name_aut_mediu_" + id).val();
        if (typeof name === 'undefined' || name === null || name.trim() === '') {
            PageManager.afiseazaInregistrariAutMediu(function () {
                that.animateElementErr("#file_name_aut_mediu_" + id);
            });
        } else {
            var obj = {
                nume: name
            };

            $.ajax({
                url: url,
                method: 'POST',
                dataType: 'json',
                data: JSON.stringify(obj),
                contentType: "application/json; charset=utf-8",
                success: function (data) {
                    if (data.result == 'OK') {
                        $.fancybox.close();

                        PageManager.afiseazaInregistrariAutMediu(function () {
                            that.animateElementOk("#file_name_aut_mediu_" + id);
                        });

                    } else if (data.result == 'ERR') {
                        PageManager.afiseazaInregistrariAutMediu(function () {
                            that.animateElementErr("#file_name_aut_mediu_" + id);
                        });

                        Swal.fire({
                            icon: "error",
                            html: data.info,
                            focusConfirm: false,
                            confirmButtonText: "Ok"
                        });
                    }
                }
            });
        }
    },

    saveAttachedFileNameAutGospApelor: function (id) {
        var that = this;
        var PROC_NAME = "PageManager.saveAttachedFileNameAutGospApelor";

        var url = "/dmsws/anre/updateFisierAutGospApelor/" + id;

        var name = $("#file_name_aut_gosp_apelor_" + id).val();
        if (typeof name === 'undefined' || name === null || name.trim() === '') {
            PageManager.afiseazaInregistrariAutGospApelor(function () {
                that.animateElementErr("#file_name_aut_gosp_apelor_" + id);
            });
        } else {
            var obj = {
                nume: name
            };

            $.ajax({
                url: url,
                method: 'POST',
                dataType: 'json',
                data: JSON.stringify(obj),
                contentType: "application/json; charset=utf-8",
                success: function (data) {
                    if (data.result == 'OK') {
                        $.fancybox.close();

                        PageManager.afiseazaInregistrariAutGospApelor(function () {
                            that.animateElementOk("#file_name_aut_gosp_apelor_" + id);
                        });

                    } else if (data.result == 'ERR') {
                        PageManager.afiseazaInregistrariAutGospApelor(function () {
                            that.animateElementErr("#file_name_aut_gosp_apelor_" + id);
                        });

                        Swal.fire({
                            icon: "error",
                            html: data.info,
                            focusConfirm: false,
                            confirmButtonText: "Ok"
                        });
                    }
                }
            });
        }
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

    afiseazaInregistrariConducere: function () {
        var PROC_NAME = "PageManager.afiseazaInregistrariConducere";


        //apelam reconstructie paginare & afisare rezultate
        PageManager.getListaInregistrariConducere();

        //$UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },
    afiseazaInregistrariSucursala: function () {
        var PROC_NAME = "PageManager.getListaInregistrariSucursala";

        //apelam reconstructie paginare & afisare rezultate
        PageManager.getListaInregistrariSucursala();

        //$UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },
    afiseazaInregistrariAutMediu: function (cb) {
        var PROC_NAME = "PageManager.getListaInregistrariAutMediu";

        //apelam reconstructie paginare & afisare rezultate
        PageManager.getListaInregistrariAutMediu(cb);

        //$UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },
    afiseazaInregistrariAutGospApelor: function (cb) {
        var PROC_NAME = "PageManager.getListaInregistrariAutGospApelor";

        //apelam reconstructie paginare & afisare rezultate
        PageManager.getListaInregistrariAutGospApelor(cb);

        //$UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },
    afiseazaInregistrariActionari: function () {
        var PROC_NAME = "PageManager.afiseazaInregistrariActionari";


        //apelam reconstructie paginare & afisare rezultate
        PageManager.getListaActionari();

        //$UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },
    afiseazaInregistrariSedii: function () {
        var PROC_NAME = "PageManager.afiseazaInregistrariSedii";


        //apelam reconstructie paginare & afisare rezultate
        PageManager.getInregistrariSedii();

        //$UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },
    getListaInregistrariConducere: function (jReq) {

        var that = this;
        var PROC_NAME = "PageManager.getListaInregistrariConducere";
        //$UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);

        var url ="/dmsws/anre/getInregistrariConducere";

        console.log("Ajax Call Start:" + url);

        $.ajax({
            url: url,

            success: function (data) {
                if (data.result == 'OK') {
                    that.listaInregistrariConducere = data.conducereList;



                    //apelam render template pentru a popula tabelul cu rezultatele obtinute
                    that.renderTemplate('tmpl_inregistrari_list_conducere', {data: data.conducereList}).then(function (html) {
                        var tblHolder = $('.table_inregistrari_conducere');
                        tblHolder.html('');
                        tblHolder.html(html);

                        //tratare bife
                        for (var i = 0; i < data.conducereList.length; i++) {
                            if (data.conducereList[i].manager != null && data.conducereList[i].manager == 1) {
                                document.getElementById("manager_"+ data.conducereList[i].id).checked=true;
                            }
                            if (data.conducereList[i].resp_tehnic != null && data.conducereList[i].resp_tehnic == 1) {
                                document.getElementById("resp_tehnic_"+ data.conducereList[i].id).checked=true;
                            }
                            if (data.conducereList[i].reprez_legal != null && data.conducereList[i].reprez_legal == 1) {
                                document.getElementById("reprez_legal_"+ data.conducereList[i].id).checked=true;

                            }



                        }
                        $('.dataTable_nosearch1').DataTable( {
                            "searching": true,
                            language: {
                                info:           "Se afișează _START_ - _END_ din _TOTAL_ intrări",
                                infoEmpty:      "Se afișează 0 - 0 din 0 intrări",
                                lengthMenu:     "Se afișează _MENU_ intrări",
                                zeroRecords:    "Nu au fost găsite înregistrări care să se potrivească",
                                paginate: {
                                    first:      "Prima",
                                    previous:   "Inapoi",
                                    next:       "Inainte",
                                    last:       "Ultima"
                                },
                                search:"Cauta in tabel ..."
                            }
                        });
                    }, function () {
                        that.alert('Unable to render template', 'ERROR');
                    });
                    // }

                }

            }
        });
       //$UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },
    getListaInregistrariSucursala: function (jReq) {

        var that = this;
        var PROC_NAME = "PageManager.getListaInregistrariSucursala";
        //$UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);

        var url ="/dmsws/anre/getInregistrariSucursala";

        console.log("Ajax Call Start:" + url);

        $.ajax({
            url: url,

            success: function (data) {
                if (data.result == 'OK') {
                    that.listaInregistrariSucursala = data.sucursalaList;

                    //apelam render template pentru a popula tabelul cu rezultatele obtinute
                    that.renderTemplate('tmpl_inregistrari_list_sucursala', {data: data.sucursalaList}).then(function (html) {
                        var tblHolder = $('.table_inregistrari_sucursala');
                        tblHolder.html('');
                        tblHolder.html(html);

                        //tratare bife
                        // for (var i = 0; i < data.conducereList.length; i++) {
                        //     if (data.conducereList[i].manager != null && data.conducereList[i].manager == 1) {
                        //         document.getElementById("manager_"+ data.conducereList[i].id).checked=true;
                        //     }
                        //     if (data.conducereList[i].resp_tehnic != null && data.conducereList[i].resp_tehnic == 1) {
                        //         document.getElementById("resp_tehnic_"+ data.conducereList[i].id).checked=true;
                        //     }
                        //     if (data.conducereList[i].reprez_legal != null && data.conducereList[i].reprez_legal == 1) {
                        //         document.getElementById("reprez_legal_"+ data.conducereList[i].id).checked=true;
                        //
                        //     }
                        //
                        // }


                        $('.dataTable_nosearch_sucursala').DataTable( {
                            "searching": true,
                            language: {
                                info:           "Se afișează _START_ - _END_ din _TOTAL_ intrări",
                                infoEmpty:      "Se afișează 0 - 0 din 0 intrări",
                                lengthMenu:     "Se afișează _MENU_ intrări",
                                zeroRecords:    "Nu au fost găsite înregistrări care să se potrivească",
                                paginate: {
                                    first:      "Prima",
                                    previous:   "Inapoi",
                                    next:       "Inainte",
                                    last:       "Ultima"
                                },
                                search:"Cauta in tabel ..."
                            }
                        });
                    }, function () {
                        that.alert('Unable to render template', 'ERROR');
                    });
                    // }

                }

            }
        });
        //$UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },
    getListaInregistrariAutMediu: function (cb) {

        var that = this;
        var PROC_NAME = "PageManager.getListaInregistrariAutMediu";
        //$UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);

        var url ="/dmsws/anre/getInregistrariAutMediu";

        console.log("Ajax Call Start:" + url);

        $.ajax({
            url: url,

            success: function (data) {
                if (data.result == 'OK') {
                    that.listaInregistrariAutMediu = data.autMediuList;

                    //apelam render template pentru a popula tabelul cu rezultatele obtinute
                    that.renderTemplate('tmpl_inregistrari_list_aut_mediu', {data: data.autMediuList}).then(function (html) {
                        var tblHolder = $('.table_inregistrari_aut_mediu');
                        tblHolder.html('');
                        tblHolder.html(html);

                        //tratare bife
                        // for (var i = 0; i < data.conducereList.length; i++) {
                        //     if (data.conducereList[i].manager != null && data.conducereList[i].manager == 1) {
                        //         document.getElementById("manager_"+ data.conducereList[i].id).checked=true;
                        //     }
                        //     if (data.conducereList[i].resp_tehnic != null && data.conducereList[i].resp_tehnic == 1) {
                        //         document.getElementById("resp_tehnic_"+ data.conducereList[i].id).checked=true;
                        //     }
                        //     if (data.conducereList[i].reprez_legal != null && data.conducereList[i].reprez_legal == 1) {
                        //         document.getElementById("reprez_legal_"+ data.conducereList[i].id).checked=true;
                        //
                        //     }
                        //
                        // }


                        $('.dataTable_nosearch_aut_mediu').DataTable( {
                            "searching": true,
                            language: {
                                info:           "Se afișează _START_ - _END_ din _TOTAL_ intrări",
                                infoEmpty:      "Se afișează 0 - 0 din 0 intrări",
                                lengthMenu:     "Se afișează _MENU_ intrări",
                                zeroRecords:    "Nu au fost găsite înregistrări care să se potrivească",
                                paginate: {
                                    first:      "Prima",
                                    previous:   "Inapoi",
                                    next:       "Inainte",
                                    last:       "Ultima"
                                },
                                search:"Cauta in tabel ..."
                            }
                        });

                        if (typeof cb === 'function'){
                            cb();
                        }

                        $(".upload_file_control_aut_mediu").change(function(){
                            var id = $(this).data('id');
                            PageManager.continueUploadFileAutMediu(id);
                        });
                    }, function () {
                        that.alert('Unable to render template', 'ERROR');
                    });
                    // }

                }

            }
        });
        //$UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },
    getListaInregistrariAutGospApelor: function (cb) {

        var that = this;
        var PROC_NAME = "PageManager.getListaInregistrariAutGospApelor";
        //$UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);

        var url ="/dmsws/anre/getInregistrariAutGospApelor";

        console.log("Ajax Call Start:" + url);

        $.ajax({
            url: url,

            success: function (data) {
                if (data.result == 'OK') {
                    that.listaInregistrariAutGospApelor = data.autGospApelorList;

                    //apelam render template pentru a popula tabelul cu rezultatele obtinute
                    that.renderTemplate('tmpl_inregistrari_list_aut_gosp_apelor', {data: data.autGospApelorList}).then(function (html) {
                        var tblHolder = $('.table_inregistrari_aut_gosp_apelor');
                        tblHolder.html('');
                        tblHolder.html(html);

                        //tratare bife
                        // for (var i = 0; i < data.conducereList.length; i++) {
                        //     if (data.conducereList[i].manager != null && data.conducereList[i].manager == 1) {
                        //         document.getElementById("manager_"+ data.conducereList[i].id).checked=true;
                        //     }
                        //     if (data.conducereList[i].resp_tehnic != null && data.conducereList[i].resp_tehnic == 1) {
                        //         document.getElementById("resp_tehnic_"+ data.conducereList[i].id).checked=true;
                        //     }
                        //     if (data.conducereList[i].reprez_legal != null && data.conducereList[i].reprez_legal == 1) {
                        //         document.getElementById("reprez_legal_"+ data.conducereList[i].id).checked=true;
                        //
                        //     }
                        //
                        // }


                        $('.dataTable_nosearch_aut_gosp_apelor').DataTable( {
                            "searching": true,
                            language: {
                                info:           "Se afișează _START_ - _END_ din _TOTAL_ intrări",
                                infoEmpty:      "Se afișează 0 - 0 din 0 intrări",
                                lengthMenu:     "Se afișează _MENU_ intrări",
                                zeroRecords:    "Nu au fost găsite înregistrări care să se potrivească",
                                paginate: {
                                    first:      "Prima",
                                    previous:   "Inapoi",
                                    next:       "Inainte",
                                    last:       "Ultima"
                                },
                                search:"Cauta in tabel ..."
                            }
                        });

                        if (typeof cb === 'function'){
                            cb();
                        }

                        $(".upload_file_control_aut_gosp_apelor").change(function(){
                            var id = $(this).data('id');
                            PageManager.continueUploadFileAutGospApelor(id);
                        });
                    }, function () {
                        that.alert('Unable to render template', 'ERROR');
                    });
                    // }

                }

            }
        });
        //$UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },


    startUploadFileAutMediu: function(id){
        var that = this;
        $("#upload_file_aut_mediu_"+id).trigger('click');
    },

    continueUploadFileAutMediu: function(id){

        var that = this;
        var upload_file = $("#upload_file_aut_mediu_" + id).val();

        if (typeof upload_file !== 'undefined' && upload_file !== null && upload_file !== ''){
            var fileData = $("#upload_file_aut_mediu_" + id)[0];

            var url = '/dmsws/anre/uploadDocumentAutMediu';

            var formData = new FormData();

            formData.append('file', fileData.files[0]);
            formData.append('nume', fileData.files[0].name);
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
                    if (json.status == 'OK') {
                        that.getListaInregistrariAutMediu();
                    }
                    else {
                        Swal.fire({
                            icon: 'error',
                            html: "Ne pare rau! A intervenit o problema. Fisierul nu a putut fi salvat!",
                            focusConfirm: false,
                            confirmButtonText: 'Ok'
                        });

                        that.getListaInregistrariAutMediu();
                    }
                },
                error: function (err) {
                    var strErr = err;
                },
                complete: function (data) {
                    that.cleanBeforeAddFileAutMediu();
                }
            });
        }
    },

    cleanBeforeAddFileAutMediu: function(){
        var that = this;
        $(".upload_file_control_aut_mediu").val('');
    },



    startUploadFileAutGospApelor: function(id){
        var that = this;
        $("#upload_file_aut_gosp_apelor_"+id).trigger('click');
    },

    continueUploadFileAutGospApelor: function(id){

        var that = this;
        var upload_file = $("#upload_file_aut_gosp_apelor_" + id).val();

        if (typeof upload_file !== 'undefined' && upload_file !== null && upload_file !== ''){
            var fileData = $("#upload_file_aut_gosp_apelor_" + id)[0];

            var url = '/dmsws/anre/uploadDocumentAutGospApelor';

            var formData = new FormData();

            formData.append('file', fileData.files[0]);
            formData.append('nume', fileData.files[0].name);
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
                    if (json.status == 'OK') {
                        that.getListaInregistrariAutGospApelor();
                    }
                    else {
                        Swal.fire({
                            icon: 'error',
                            html: "Ne pare rau! A intervenit o problema. Fisierul nu a putut fi salvat!",
                            focusConfirm: false,
                            confirmButtonText: 'Ok'
                        });

                        that.getListaInregistrariAutGospApelor();
                    }
                },
                error: function (err) {
                    var strErr = err;
                },
                complete: function (data) {
                    that.cleanBeforeAddFileAutGospApelor();
                }
            });
        }
    },

    cleanBeforeAddFileAutGospApelor: function(){
        var that = this;
        $(".upload_file_control_aut_gosp_apelor").val('');
    },

    strToArr: function(str){
        if (typeof str === 'undefined' && str === null){
            return [];
        } else {
            return str.split(',');
        }
    }
};


$(document).ready(function () {
    PageManager.init();

    $('#form_adauga')
        .submit( function( e ) {
            e.preventDefault();
        });

    $('#form_editeaza')
        .submit( function( e ) {
            e.preventDefault();
        });

    $('#form_editeaza_sediu')
        .submit( function( e ) {
            e.preventDefault();
        });

    $('#form_adauga_actionar')
        .submit( function( e ) {
            e.preventDefault();
        });

    $('#form_editeaza_actionar')
        .submit( function( e ) {
            e.preventDefault();
        });

    $('#form_editeaza_aut_gosp_apelor')
        .submit( function( e ) {
            e.preventDefault();
        });

    $('#form_editeaza_aut_mediu')
        .submit( function( e ) {
            e.preventDefault();
        });


    $("#am_select_judet").change(function(){
        PageManager.getLovLocalitatePrincipalaAm();
    });

    $("#aga_select_judet").change(function(){
        PageManager.getLovLocalitatePrincipalaAga();
    });

    $("#am_select_localitate_principala").change(function(){
        PageManager.getLovLocalitateApartinatoareAm();
    });

    $("#aga_select_localitate_principala").change(function(){
        PageManager.getLovLocalitateApartinatoareAga();
    });

});

function validateEmail(email) {
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(String(email).toLowerCase()) || email=='';
}






