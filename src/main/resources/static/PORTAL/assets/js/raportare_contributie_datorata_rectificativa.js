var PAGE_NAME = "raportare_contributie_datorata.js";

var FileManager = {
    parametersLoadedOk: false,


    // user info object
    wsAndUserInfo: {},
    mainTableName:'RAPORTARE_CONTRIBUTIE_DATORATA',
    idTipDocument: null,
    idPerioada: null,
    tipDocument:null,
    userInfo: null,
    idFisier:null,
    anteSGen:0,
    anteSGenContrb:0,
    anteSGenAF:0,
    anteAfDob:0,
    dobLunaCurenta:0,
    anteSViataAF:0,
    anteSGenViata:0,
    anteSViata:0,
    anteSPrima:0,
    idPerioada:0,
    idLocalitateParinte:null,

    // pre-compiled mustache templates
    templates: {},
    corsHeaders: {
    'Access-Control-Allow-Origin': '*',
    'Access-Control-Allow-Credentials': 'true',
    'Access-Control-Allow-Methods': 'POST, PUT, GET, OPTIONS, DELETE, HEAD',
    'Access-Control-Allow-Headers': 'Access-Control-Allow-Headers, Access-Control-Allow-Origin, Access-Control-Request-Method, Access-Control-Request-Headers, Access-Control-Allow-Credentials, Access-Control-Allow-Methods, Origin, Accept, X-Requested-With, Content-Type, X-PINGOTHER, Authorization'
},


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

                           // that.checkRightData();
                            that.getInfoTabelRaport();
                            that.getInfoUtilizator();
                            $("[numberCell]").number(true, 2, '.', ',');

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
    downloadPdf:function(data){

        var that=this;
        var fileData={base64File:data};
        that.ajaxAction({
            url: '/dmsws/formular_custom/create_dummy_file_base64/'+that.idTipDocument,
            method: 'POST',
            data:JSON.stringify(fileData),
            headers: that.mergeObjects(that.corsHeaders, {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }),
            onSuccess: function (data) {
                if (data.result == 'OK') {

                    swal.close();
                    window.open(data.downloadLinkFile,'_blank');

                } else {
                    Swal.fire({
                        icon: 'error',
                        html: "Ne pare rau,pdf eronat!",
                        focusConfirm: false,
                        confirmButtonText: 'Ok'
                    });
                }
            },
            onError: function (data) {

                //$.fancybox.close();

                Swal.fire({
                    icon: 'error',
                    html: "Eroare! Ne pare rau, pdf eronat!",
                    focusConfirm: false,
                    confirmButtonText: 'Ok'
                });
                console.log(data);
            }
        });

    },
    print: function () {

        var that=this;
        var idPerioada=$("#id_perioada_contabila").val();
        if(idPerioada===null || idPerioada==="" || idPerioada===undefined) {
            window.parent.parent.scrollTo(0, 0);
            Swal.fire({
                position: 'top',
                icon: 'error',
                html: "Va rugam sa selectati luna de raportare!<br/>",
                focusConfirm: false,
                confirmButtonText: 'Ok'
            });
        }else{
            $UTIL.waitForLoading();
            if(FileManager.idFisier!=null && typeof FileManager.idFisier!='undefined'){
                /* -> salvare campuri cerere
                 -> rulareJasper
                 -> download*/
                FileManager.salveazaCerereDraft(true);

            }else{
                /*-> create dummy file
                 -> salvare campuri cerere
                 -> rulareJasper
                 -> download*/
                FileManager.salveazaCerereDraftWithDummy(true,false);
            }
        }
    },
    salveazaCerereDraft: function (download) {

        var that=this;

        // 2. Salvare campuri
            that.saveDoc(true,download);


    },

    salveazaCerereDraftWithDummy: function (download,sendReport) {

        var that=this;
        //0. Creare dummy file

        that.ajaxAction({
            url: '/dmsws/formular_custom/create_dummy_file/'+that.idTipDocument,
            method: 'POST',
            headers: that.mergeObjects(that.corsHeaders, {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }),
            onSuccess: function (data) {
                if (data.result == 'OK') {
                    FileManager.idFisier = data.id;
                    // 2. Salvare campuri
                        that.saveDoc(true,download,sendReport);


                }
            }, onError: function (data) {


                Swal.fire({
                    icon: 'error',
                    html: "Eroare! Ne pare rau, cererea nu a putut fi salvata!",
                    focusConfirm: false,
                    confirmButtonText: 'Ok'
                });
                console.log(data);
            }
        });



    },
    salveazaCerere: function () {
        var idPerioada=$("#id_perioada_contabila").val();
        var subsemnatul=$("#subsemnatul").val();
        var inCalitateDe=$("#in_calitate_de").val();
        if(idPerioada===null || idPerioada==="" || idPerioada===undefined) {
            window.parent.parent.scrollTo(0, 0);
            Swal.fire({
                position: 'top',
                icon: 'error',
                html: "Va rugam sa selectati luna de raportare!<br/>",
                focusConfirm: false,
                confirmButtonText: 'Ok'
            });
        } else  if(subsemnatul===null || subsemnatul.trim()==="" || subsemnatul===undefined) {
            window.parent.parent.scrollTo(0, 0);
            Swal.fire({
                position: 'top',
                icon: 'error',
                html: "Va rugam sa completati campul: Subsemnatul.<br/>",
                focusConfirm: false,
                confirmButtonText: 'Ok'
            });
        } else  if(inCalitateDe===null || inCalitateDe.trim()==="" || inCalitateDe===undefined) {
            window.parent.parent.scrollTo(0, 0);
            Swal.fire({
                position: 'top',
                icon: 'error',
                html: "Va rugam sa completati campul: In calitate de.<br/>",
                focusConfirm: false,
                confirmButtonText: 'Ok'
            });
        } else{
            window.parent.parent.scrollTo(0, 0);

            var that=this;
            Swal.fire({
                    position: 'top',
                    title:"Va rugam asteptati",
                    showConfirmButton:true,
                    allowOutsideClick: false,

                    onBeforeOpen: () => {
                    Swal.showLoading();
        }
        });
            if(FileManager.idFisier!=null && typeof FileManager.idFisier!='undefined'){
                /* -> salvare campuri cerere
                 -> rulareJasper
                 -> download*/
                that.saveDoc(true,false,true);

            }else{
                /*-> create dummy file
                 -> salvare campuri cerere
                 -> rulareJasper
                 -> download*/
                FileManager.salveazaCerereDraftWithDummy(false,true);
            }
        }



    },
    rulareRaportareGarantare : function (idFisier, download) {

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

                        if(download!=null && typeof download!='undefined' && download==true){
                            window.open(json.info, '_blank');
                        }

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
    getInfoUtilizator:function(){

        var that=this;
        $.ajax({
            url: '/dmsws/formular_custom/getInfoUtilizator',
            success: function (data) {
                if (data.result == 'OK') {
                    $("#denumireSocietate").val(data.tertParinteUserCurent.numeComplet);
                    $("#strada").val(data.tertParinteUserCurent.strada);
                    $("#nr_strada").val(data.tertParinteUserCurent.nrStrada);
                    $("#bl_adresa").val(data.tertParinteUserCurent.bloc);
                    $("#sc_adresa").val(data.tertParinteUserCurent.scara);
                    $("#et_adresa").val(data.tertParinteUserCurent.etaj);
                    $("#ap_adresa").val(data.tertParinteUserCurent.apartament);
                    $("#persoana_contact").val(data.tertParinteUserCurent.persoanaContact);
                    $("#telefon_fax").val(data.tertParinteUserCurent.telefon);
                    $("#email").val(data.tertParinteUserCurent.email);
                    $("#subsemnatul").val(data.userCurent.nume+' '+data.userCurent.prenume);

                    if(data.tertParinteUserCurent.idLocalitate!=0 && data.tertParinteUserCurent.idLocalitate!='0' && data.tertParinteUserCurent.idLocalitate!=undefined && data.tertParinteUserCurent.idLocalitate!=null) {
                        that.idLocalitateParinte=data.tertParinteUserCurent.idLocalitate;
                        $("#id_sediu_social").val(data.tertParinteUserCurent.idLocalitate).trigger("chosen:updated");
                    }
                }
                else {
                    window.parent.parent.scrollTo(0, 0);
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


    sendRaport: function () {

        var that=this;
        //build url WS
        console.log("sendRaport");

        var url = '/dmsws/formular_custom/sendRaportare/'+FileManager.idFisier;
        $.ajax({
            url: url,
            method: 'POST',
            contentType: 'application/json',
            accept:'application/json',
            success: function (json) {
                ;
                if (json.result !== 'OK') {
                    Swal.fire({
                            icon: "error",
                            html: "Ne pare rau, a intervenit o problema. Raportarea nu a putut fi trimisa.",
                            focusConfirm: false,
                            confirmButtonText: "Ok",
                            onClose: () => {
                            window.location.reload();
                }
                });

                }
                else{
                    ;
                    Swal.fire({
                        icon: "info",
                        html: "Raportarea a fost trimisa cu succes!",
                        focusConfirm: false,
                        confirmButtonText: "Ok",
                        onClose: () => {
                            window.history.back();

                }
                });
                }
            },
            error: function (err) {
                ;
                var strErr = Util.getAjaxErrorMessage(err);
                Swal.fire({
                        icon: "error",
                        html: "Ne pare rau, a intervenit o problema. Raportarea nu s-a trimis.",
                        focusConfirm: false,
                        confirmButtonText: "Ok",
                        onClose: () => {
                        window.location.reload();
            }
            });
            }
        });

    },
    saveDoc:function(genereazaJasper, download,sendRaport){

        var that=this;
        var idFisier =FileManager.idFisier;
                    //call WS by ajaxAction salvare campuri cerere
                    var saveInfo=that.getSaveInfo($("#formular"));
                    var url = '/dmsws/formular_custom/adaugaFormular/'+that.mainTableName+'/'+idFisier+'/';

                    that.ajaxAction({
                        url: url,
                        method: 'POST',
                        headers: that.mergeObjects(that.corsHeaders, {
                            'Accept': 'application/json',
                            'Content-Type': 'application/json'
                        }),
                        data: saveInfo,
                        onSuccess: function (data) {
                            if (data.result == 'OK') {

                                $('table').each(function() {

                                    //call WS salvare tabel cerere
                                    //id tag table = nume tabel din baza de date
                                    var saveInfoTabel=that.getSaveInfoTabel("#"+$(this).attr("id"));
                                    var urlTabel = '/dmsws/formular_custom/adaugaTabelRectificare/'+$(this).attr("id")+'/'+idFisier+'/' +FileManager.idPerioada+
                                        '?&codTipDocInitial=RAP_CONTR_DAT_VIR_FGA_GAR';
                                    that.ajaxAction({
                                        url: urlTabel,
                                        method: 'POST',
                                        headers: that.mergeObjects(that.corsHeaders, {
                                            'Accept': 'application/json',
                                            'Content-Type': 'application/json'
                                        }),
                                        data: saveInfoTabel,
                                        onSuccess: function (data) {
                                            if (data.result == 'OK') {
                                                swal.close();
                                                if( genereazaJasper!=null && genereazaJasper==true){
                                                    FileManager.rulareRaportareGarantare(FileManager.idFisier,download);
                                                }
                                                if( sendRaport!=null && sendRaport==true){
                                                    that.sendRaport();
                                                }

                                            } else {
                                                window.parent.parent.scrollTo(0, 0);

                                                swal.close();
                                                Swal.fire({
                                                    position: 'top',
                                                    icon: 'error',
                                                    html: "Ne pare rau, tabelul nu a putut fi salvat!",
                                                    focusConfirm: false,
                                                    confirmButtonText: 'Ok'
                                                });
                                            }
                                        },
                                        onError: function (data) {
                                            window.parent.parent.scrollTo(0, 0);

                                            Swal.fire({
                                                position: 'top',
                                                icon: 'error',
                                                html: "Eroare! Ne pare rau, tabelul nu a putut fi salvat!",
                                                focusConfirm: false,
                                                confirmButtonText: 'Ok'
                                            });
                                            console.log(data);
                                        }
                                    });
                                });


                            } else {
                                window.parent.parent.scrollTo(0, 0);
                                Swal.fire({
                                    position: 'top',
                                    icon: 'error',
                                    html: "Ne pare rau, cererea nu a putut fi salvata!",
                                    focusConfirm: false,
                                    confirmButtonText: 'Ok'
                                });
                            }
                        },
                        onError: function (data) {
                            window.parent.parent.scrollTo(0, 0);

                            Swal.fire({
                                position: 'top',
                                icon: 'error',
                                html: "Eroare! Ne pare rau, cererea nu a putut fi salvata!",
                                focusConfirm: false,
                                confirmButtonText: 'Ok'
                            });
                            console.log(data);
                        }
                    });

    },
    getSaveInfo: function ($div) {

    var fields = $div.find("[saveable]");
    // console.log("fields",fields);
    var saveData = "{";
    fields.each(function() {
        var field = $(this);
        var key = field.attr("id");
        if($(this).attr("type")==='checkbox') {
            var value = field.prop("checked")?1:0;
            if(value!==field.attr("value")) {
                saveData+="\""+key+"\":\""+value+"\","
            }
        } else {

                saveData+="\""+key+"\":\""+field.val()+"\","

        }
    });

    saveData=saveData.substr(0,saveData.length-1);
    saveData+="}";
    return saveData;
},

    calculZonaPrimeAsigGen:function () {

      console.log("Start actualizare calcule Asig Gen zona prime");
        var that=this;
        var primaTotalAnte=$("#prima_bruta_total").val();

      var asigurariGenerale=$("#asig_gen_prime_luna_raportare").val();
      if(asigurariGenerale==null || asigurariGenerale==undefined || asigurariGenerale==""){
          asigurariGenerale=0;
          primaTotalAnte=parseFloat(primaTotalAnte-FileManager.anteSGen);
          $("#total_asig_gen_suma").val(parseFloat($("#total_asig_gen_suma").val())-parseFloat(FileManager.anteSGen));

      }
      FileManager.anteSGen=asigurariGenerale;
      var sumaCalculata=parseFloat(asigurariGenerale);
        if(primaTotalAnte==null || primaTotalAnte==undefined || primaTotalAnte==""){
            primaTotalAnte=0;
        }
        var asigurariViata=$("#asig_viata_prime_luna_raportare").val();
        if(asigurariViata==null || asigurariViata==undefined || asigurariViata==""){
            asigurariViata=0;
        }
        $("#prima_luna_rap_curenta").val(parseFloat(asigurariGenerale)+parseFloat(asigurariViata));
      $("#prima_bruta_total").val(parseFloat(primaTotalAnte) + parseFloat(sumaCalculata));
        $("#total_asig_gen_suma").val(parseFloat($("#total_asig_gen_suma").val())+parseFloat(asigurariGenerale));

    },


    calculZonaPrimeAsiViata:function () {

        console.log("Start actualizare calcule Asig Viata zona prime");
        var that=this;
        var primaTotalAnte=$("#prima_bruta_total").val();
        var asigurariViata=$("#asig_viata_prime_luna_raportare").val();
        if(asigurariViata==null || asigurariViata==undefined || asigurariViata==""){
            asigurariViata=0;
            primaTotalAnte=parseFloat(primaTotalAnte-FileManager.anteSViata);
            $("#total_asig_viata_sum").val(parseFloat($("#total_asig_viata_sum").val())-parseFloat(FileManager.anteSViata));

        }
        FileManager.anteSViata=asigurariViata;
        var sumaCalculata= parseFloat(asigurariViata);
        if(primaTotalAnte==null || primaTotalAnte==undefined || primaTotalAnte==""){
            primaTotalAnte=0;
        }
        $("#prima_bruta_total").val(parseFloat(primaTotalAnte) + parseFloat(sumaCalculata));
        var asigurariGenerale=$("#asig_gen_prime_luna_raportare").val();
        if(asigurariGenerale==null || asigurariGenerale==undefined || asigurariGenerale==""){
            asigurariGenerale=0;
        }
        $("#prima_luna_rap_curenta").val(parseFloat(asigurariGenerale)+parseFloat(asigurariViata));
        $("#total_asig_viata_sum").val(parseFloat($("#total_asig_viata_sum").val())+parseFloat(asigurariViata));

    },


    calculZonaPrimeAsigGenContrib:function () {

      console.log("Start actualizare calcule Asig Gen Contrib zona prime");
        var that=this;
        var primaTotalAnte=$("#total_cota_sum").val();
      var asigurariGenerale=$("#asig_gen_prime_luna_raportare_contrib").val();
      if(asigurariGenerale==null || asigurariGenerale==undefined || asigurariGenerale==""){
          asigurariGenerale=0;
          primaTotalAnte=parseFloat(primaTotalAnte-FileManager.anteSGenContrb);
          $("#total_asig_gen_cota").val(parseFloat($("#total_asig_gen_cota").val())-parseFloat(FileManager.anteSGenContrb));

      }
      FileManager.anteSGenContrb=asigurariGenerale;
      var sumaCalculata=parseFloat(asigurariGenerale);
        if(primaTotalAnte==null || primaTotalAnte==undefined || primaTotalAnte==""){
            primaTotalAnte=0;
        }
        var asigurariViata=$("#asig_viata_prime_luna_raportare_contrib").val();
        if(asigurariViata==null || asigurariViata==undefined || asigurariViata==""){
            asigurariViata=0;
        }
        $("#prima_luna_rap_curenta_contrib").val(parseFloat(asigurariGenerale)+parseFloat(asigurariViata));
      $("#total_cota_sum").val(parseFloat(primaTotalAnte) + parseFloat(sumaCalculata));
      $("#total_asig_gen_cota").val(parseFloat($("#total_asig_gen_cota").val()) + parseFloat(sumaCalculata));


    },

    calculZonaPrimeAsigViataContrib:function () {

      console.log("Start actualizare calcule Asig Viata Contrib zona prime");
        var that=this;
        var primaTotalAnte=$("#total_cota_sum").val();
      var asigurariViata=$("#asig_viata_prime_luna_raportare_contrib").val();
      if(asigurariViata==null || asigurariViata==undefined || asigurariViata==""){
          asigurariViata=0;
          primaTotalAnte=parseFloat(primaTotalAnte-FileManager.anteSGenViata);
          $("#total_asig_viata_cot").val(parseFloat($("#total_asig_viata_cot").val())-parseFloat(FileManager.anteSGenViata));

      }
      FileManager.anteSGenViata=asigurariViata;
      var sumaCalculata=parseFloat(asigurariViata);
        if(primaTotalAnte==null || primaTotalAnte==undefined || primaTotalAnte==""){
            primaTotalAnte=0;
        }
        var asigurariGenerale=$("#asig_gen_prime_luna_raportare_contrib").val();
        if(asigurariGenerale==null || asigurariGenerale==undefined || asigurariGenerale==""){
            asigurariGenerale=0;
        }
        $("#prima_luna_rap_curenta_contrib").val(parseFloat(asigurariGenerale)+parseFloat(asigurariViata));
      $("#total_cota_sum").val(parseFloat(primaTotalAnte) + parseFloat(sumaCalculata));
      $("#total_asig_viata_cot").val(parseFloat($("#total_asig_viata_cot").val()) + parseFloat(sumaCalculata));


    },

    calculSumaVirataZonaGen:function () {

        console.log("Start actualizare calcule Asig Gen Suma Avizata");
        var that=this;
        var primaTotalAnte=$("#afer_contrib").val();
        var asigurariGenerale=$("#asig_gen_sm").val();
        if(asigurariGenerale==null || asigurariGenerale==undefined || asigurariGenerale==""){
            asigurariGenerale=0;
            primaTotalAnte=parseFloat(primaTotalAnte-FileManager.anteSGenAF);
            $("#asig_gen_af").val(parseFloat($("#asig_gen_af").val())-parseFloat(FileManager.anteSGenAF));
            $("#afer_contrib").val(parseFloat($("#afer_contrib").val())-parseFloat(FileManager.anteSGenAF));

        }
        FileManager.anteSGenAF=asigurariGenerale;
        var sumaCalculata=parseFloat(asigurariGenerale);
        if(primaTotalAnte==null || primaTotalAnte==undefined || primaTotalAnte==""){
            primaTotalAnte=0;
        }
        var asigurariViata=$("#asig_viata_suma").val();
        if(asigurariViata==null || asigurariViata==undefined || asigurariViata==""){
            asigurariViata=0;
        }
        $("#asig_gen_af").val(parseFloat($("#asig_gen_af").val())+parseFloat(asigurariGenerale));
        $("#sum_vizata_total").val(parseFloat(asigurariGenerale)+parseFloat(asigurariViata));
        $("#afer_contrib").val(parseFloat($("#afer_contrib").val()) + parseFloat(sumaCalculata));


    },

    calculSumaVirataZonaViata:function () {

        console.log("Start actualizare calcule Asig Viata Suma Avizata");
        var that=this;
        var primaTotalAnte=$("#afer_contrib").val();
        var asigurariViata=$("#asig_viata_suma").val();
        if(asigurariViata==null || asigurariViata==undefined || asigurariViata==""){
            asigurariViata=0;
            primaTotalAnte=parseFloat(primaTotalAnte-FileManager.anteSViataAF);
            $("#asig_viata_af").val(parseFloat($("#asig_viata_af").val())-parseFloat(FileManager.anteSViataAF));
            $("#afer_contrib").val(parseFloat($("#afer_contrib").val())-parseFloat(FileManager.anteSViataAF));

        }
        FileManager.anteSViataAF=asigurariViata;
        var sumaCalculata=parseFloat(asigurariViata);
        if(primaTotalAnte==null || primaTotalAnte==undefined || primaTotalAnte==""){
            primaTotalAnte=0;
        }
        var asigurariGenerale=$("#asig_gen_sm").val();
        if(asigurariGenerale==null || asigurariGenerale==undefined || asigurariGenerale==""){
            asigurariGenerale=0;
        }
        $("#asig_viata_af").val(parseFloat($("#asig_viata_af").val())+parseFloat(asigurariViata));
        $("#sum_vizata_total").val(parseFloat(asigurariGenerale)+parseFloat(asigurariViata));
        $("#afer_contrib").val(parseFloat($("#afer_contrib").val()) + parseFloat(sumaCalculata));


    },
    changeSumaViratata:function () {

        console.log("changeSumaViratata");
        var that=this;
       var r7=$("#r7_luna_curenta").val();
       var r6=$("#sum_vizata_total").val();
       var af_old=$("#afer_contrib").val();
       var af_dob_pen_old=$("#af_dob_pen").val();
        if(r7==null || r7==undefined || r7==""){
            r7=0;
        }
        if(r6==null || r6==undefined || r6==""){
            r6=0;
        }
        if(af_old==null || af_old==undefined || af_old==""){
            af_old=0;
        }
        if(af_dob_pen==null || af_dob_pen==undefined || af_dob_pen==""){
            af_dob_pen=0;
        }
       $("#suma_virata_total_luna_curenta").val(parseFloat(r7)+parseFloat(r6));
       $("#suma_virata").val( parseFloat(af_old)+parseFloat(af_dob_pen_old));




    },
    changeSumaVAf:function () {

        console.log("changeSumaVAf");
        var that=this;
       var r7=$("#r7_luna_curenta").val();
        if(r7==null || r7==undefined || r7==""){
            r7=0;
            $("#af_dob_pen").val(parseFloat($("#af_dob_pen").val())-parseFloat(FileManager.anteAfDob));
        }else {

            $("#af_dob_pen").val(parseFloat($("#af_dob_pen").val()) + parseFloat(r7));
            FileManager.anteAfDob=r7;
        }
    },

    sumDobanda:function () {

        console.log("sumDobanda");
        var that=this;
       var dobanda_luna_curenta=$("#dobanda_luna_curenta").val();
        if(dobanda_luna_curenta==null || dobanda_luna_curenta==undefined || dobanda_luna_curenta==""){
            dobanda_luna_curenta=0;
            $("#dobanzi_pen").val(parseFloat($("#dobanzi_pen").val())-parseFloat(FileManager.dobLunaCurenta));
        }else {

            $("#dobanzi_pen").val(parseFloat($("#dobanzi_pen").val()) + parseFloat(dobanda_luna_curenta));
            FileManager.dobLunaCurenta=dobanda_luna_curenta;
        }
    },

    calculeazaSumaR6R7:function () {

        console.log("calculeazaSumaR6R7");
        var that=this;
      $("#sum_zone").css({"background-color": "green"});
      var r6=$("#sum_vizata_total").val();
      var r7=$("#r7_luna_curenta").val();

      var r6_old=$("#afer_contrib").val();
      var r7_old=$("#af_dob_pen").val();

        if(r7==null || r7==undefined || r7==""){
            r7=0;
        }
        if(r6==null || r6==undefined || r6==""){
            r6=0;
        }
        if(r7_old==null || r7_old==undefined || r7_old==""){
            r7_old=0;
        }
        if(r6_old==null || r6_old==undefined || r6_old==""){
            r6_old=0;
        }

        $("#suma_virata").val(parseFloat(r7_old)+parseFloat(r6_old));
        $("#suma_virata_total_luna_curenta").val(parseFloat(r7)+parseFloat(r6));

    },

    changePrimaButa:function () {

        console.log("changePrimaButa");
        var that=this;
      var prima_bruta_old=$("#si_prima_bruta").val();
      var prima_bruta_new=$("#sr_prima_bruta").val();
      if(prima_bruta_new=="" || prima_bruta_new==null || prima_bruta_new==undefined){
          prima_bruta_new=0;
          var dif=$("#prima_dif").val();
          if(dif<0){
              $("#si_prima_bruta").val(parseFloat($("#si_prima_bruta").val())-parseFloat(dif));
          }else{
              $("#si_prima_bruta").val(parseFloat($("#si_prima_bruta").val())-parseFloat(dif));

          }
          $("#prima_dif").val(0);


      }else{
          $("#prima_dif").val(parseFloat(prima_bruta_new)-parseFloat(prima_bruta_old));
          $("#si_prima_bruta").val(prima_bruta_new);
      }

    },

    changePrimaButAsigGen:function () {

        console.log("changePrimaButAsigGen");
        var that=this;
        var prima_bruta_old=$("#si_asig_gen_prima").val();
        var prima_bruta_new=$("#sr_asig_gen_prima").val();
        if(prima_bruta_new=="" || prima_bruta_new==null || prima_bruta_new==undefined){
            prima_bruta_new=0;
            var dif=$("#dif_asig_gen_prima").val();
            if(dif<0){
                $("#si_asig_gen_prima").val(parseFloat($("#si_asig_gen_prima").val())-parseFloat(dif));
            }else{
                $("#si_asig_gen_prima").val(parseFloat($("#si_asig_gen_prima").val())-parseFloat(dif));

            }
            $("#dif_asig_gen_prima").val(0);


        }else{
            $("#dif_asig_gen_prima").val(parseFloat(prima_bruta_new)-parseFloat(prima_bruta_old));
            $("#si_asig_gen_prima").val(prima_bruta_new);
        }
        $("#si_prima_bruta").val(parseFloat($("#si_asig_gen_prima").val()) + parseFloat($("#si_asig_viata_prima").val()));

    },

    changePrimaButAsigViata:function () {

        console.log("changePrimaButAsigViata");
        var that=this;
        var prima_bruta_old=$("#si_asig_viata_prima").val();
        var prima_bruta_new=$("#sr_asig_gen_viata_prima").val();
        if(prima_bruta_new=="" || prima_bruta_new==null || prima_bruta_new==undefined){
            prima_bruta_new=0;
            var dif=$("#diferentaasig_viata_prima").val();
            if(dif<0){
                $("#si_asig_viata_prima").val(parseFloat($("#si_asig_viata_prima").val())-parseFloat(dif));
            }else{
                $("#si_asig_viata_prima").val(parseFloat($("#si_asig_viata_prima").val())-parseFloat(dif));

            }
            $("#diferentaasig_viata_prima").val(0);


        }else{
            $("#diferentaasig_viata_prima").val(parseFloat(prima_bruta_new)-parseFloat(prima_bruta_old));
            $("#si_asig_viata_prima").val(prima_bruta_new);
        }
        $("#si_prima_bruta").val(parseFloat($("#si_asig_gen_prima").val()) + parseFloat($("#si_asig_viata_prima").val()));

    },

    changeCotaGen:function () {

        console.log("changeCotaGen");
        var that=this;
        var prima_bruta_old=$("#si_cota_asig_gen").val();
        var prima_bruta_new=$("#sr_cota_asig_gen").val();
        if(prima_bruta_new=="" || prima_bruta_new==null || prima_bruta_new==undefined){
            prima_bruta_new=0;
            var dif=$("#dif_asig_gen_cota").val();
            if(dif<0){
                $("#si_cota_asig_gen").val(parseFloat($("#si_cota_asig_gen").val())-parseFloat(dif));
            }else{
                $("#si_cota_asig_gen").val(parseFloat($("#si_cota_asig_gen").val())-parseFloat(dif));

            }
            $("#dif_asig_gen_cota").val(0);


        }else{
            $("#dif_asig_gen_cota").val(parseFloat(prima_bruta_new)-parseFloat(prima_bruta_old));
            $("#si_cota_asig_gen").val(prima_bruta_new);
        }

    },

    changeCotaViata:function () {

        console.log("changeCotaViata");
        var that=this;
        var prima_bruta_old=$("#si_asig_viata_cota").val();
        var prima_bruta_new=$("#sr_asig_viata_cota").val();
        if(prima_bruta_new=="" || prima_bruta_new==null || prima_bruta_new==undefined){
            prima_bruta_new=0;
            var dif=$("#dif_cota_viata").val();
            if(dif<0){
                $("#si_asig_viata_cota").val(parseFloat($("#si_asig_viata_cota").val())-parseFloat(dif));
            }else{
                $("#si_asig_viata_cota").val(parseFloat($("#si_asig_viata_cota").val())-parseFloat(dif));

            }
            $("#dif_cota_viata").val(0);


        }else{
            $("#dif_cota_viata").val(parseFloat(prima_bruta_new)-parseFloat(prima_bruta_old));
            $("#si_asig_viata_cota").val(prima_bruta_new);
        }

    },

    changeContribDat:function () {

        console.log("changeContribDat");
        var that=this;
        var prima_bruta_old=$("#si_contrib_dat").val();
        var prima_bruta_new=$("#sr_prim_contrib_dat").val();
        if(prima_bruta_new=="" || prima_bruta_new==null || prima_bruta_new==undefined){
            prima_bruta_new=0;
            var dif=$("#dif_contrib_dat").val();
            if(dif<0){
                $("#si_contrib_dat").val(parseFloat($("#si_contrib_dat").val())-parseFloat(dif));
            }else{
                $("#si_contrib_dat").val(parseFloat($("#si_contrib_dat").val())-parseFloat(dif));

            }
            $("#dif_contrib_dat").val(0);


        }else{
            $("#dif_contrib_dat").val(parseFloat(prima_bruta_new)-parseFloat(prima_bruta_old));
            $("#si_contrib_dat").val(prima_bruta_new);
        }

    },

    changeAsigGenContribDat:function () {

        console.log("changeAsigGenContribDat");
        var that=this;
        var prima_bruta_old=$("#si_aig_gen_contrib").val();
        var prima_bruta_new=$("#sr_asig_gen_contrb_dat").val();
        if(prima_bruta_new=="" || prima_bruta_new==null || prima_bruta_new==undefined){
            prima_bruta_new=0;
            var dif=$("#dif_asig_gen_contrib").val();
            if(dif<0){
                $("#si_aig_gen_contrib").val(parseFloat($("#si_aig_gen_contrib").val())-parseFloat(dif));
            }else{
                $("#si_aig_gen_contrib").val(parseFloat($("#si_aig_gen_contrib").val())-parseFloat(dif));

            }
            $("#dif_asig_gen_contrib").val(0);


        }else{
            $("#dif_asig_gen_contrib").val(parseFloat(prima_bruta_new)-parseFloat(prima_bruta_old));
            $("#si_aig_gen_contrib").val(prima_bruta_new);
        }
        $("#si_contrib_dat").val(parseFloat($("#si_asig_viata_contrib").val())+parseFloat($("#si_aig_gen_contrib").val()));

    },

    changeAsigViataContribData:function () {

        console.log("changeAsigViataContribData");
        var that=this;
        var prima_bruta_old=$("#si_asig_viata_contrib").val();
        var prima_bruta_new=$("#sr_asig_viata_contrib_data").val();
        if(prima_bruta_new=="" || prima_bruta_new==null || prima_bruta_new==undefined){
            prima_bruta_new=0;
            var dif=$("#diferenta_asig_viata_cota").val();
            if(dif<0){
                $("#si_asig_viata_contrib").val(parseFloat($("#si_asig_viata_contrib").val())-parseFloat(dif));
            }else{
                $("#si_asig_viata_contrib").val(parseFloat($("#si_asig_viata_contrib").val())-parseFloat(dif));

            }
            $("#diferenta_asig_viata_cota").val(0);


        }else{
            $("#diferenta_asig_viata_cota").val(parseFloat(prima_bruta_new)-parseFloat(prima_bruta_old));
            $("#si_asig_viata_contrib").val(prima_bruta_new);
        }
        $("#si_contrib_dat").val(parseFloat($("#si_asig_viata_contrib").val())+parseFloat($("#si_aig_gen_contrib").val()));

    },

    changeDobanada:function () {

        console.log("changeDobanada");
        var that=this;
        var prima_bruta_old=$("#si_dobanzi_pen").val();
        var prima_bruta_new=$("#sr_dobanda_pen").val();
        if(prima_bruta_new=="" || prima_bruta_new==null || prima_bruta_new==undefined){
            prima_bruta_new=0;
            var dif=$("#dif_dob_pen").val();
            if(dif<0){
                $("#si_dobanzi_pen").val(parseFloat($("#si_dobanzi_pen").val())-parseFloat(dif));
            }else{
                $("#si_dobanzi_pen").val(parseFloat($("#si_dobanzi_pen").val())-parseFloat(dif));

            }
            $("#dif_dob_pen").val(0);


        }else{
            $("#dif_dob_pen").val(parseFloat(prima_bruta_new)-parseFloat(prima_bruta_old));
            $("#si_dobanzi_pen").val(prima_bruta_new);
        }

    },

    changeSuma:function () {

        console.log("changeSuma");
        var that=this;
        var prima_bruta_old=$("#si_sum_vir").val();
        var prima_bruta_new=$("#sr_suma_virata").val();
        if(prima_bruta_new=="" || prima_bruta_new==null || prima_bruta_new==undefined){
            prima_bruta_new=0;
            var dif=$("#dif_suma_virata").val();
            if(dif<0){
                $("#si_sum_vir").val(parseFloat($("#si_sum_vir").val())-parseFloat(dif));
            }else{
                $("#si_sum_vir").val(parseFloat($("#si_sum_vir").val())-parseFloat(dif));

            }
            $("#dif_suma_virata").val(0);


        }else{
            $("#dif_suma_virata").val(parseFloat(prima_bruta_new)-parseFloat(prima_bruta_old));
            $("#si_sum_vir").val(prima_bruta_new);
        }

    },

    changeSumaAfc:function () {

        console.log("changeSumaAfc");
        var that=this;
        var prima_bruta_old=$("#si_afer_contrib").val();
        var prima_bruta_new=$("#sr_afc_contrib").val();
        if(prima_bruta_new=="" || prima_bruta_new==null || prima_bruta_new==undefined){
            prima_bruta_new=0;
            var dif=$("#dif_afc_contrib").val();
            if(dif<0){
                $("#si_afer_contrib").val(parseFloat($("#si_afer_contrib").val())-parseFloat(dif));
            }else{
                $("#si_afer_contrib").val(parseFloat($("#si_afer_contrib").val())-parseFloat(dif));

            }
            $("#dif_afc_contrib").val(0);


        }else{
            $("#dif_afc_contrib").val(parseFloat(prima_bruta_new)-parseFloat(prima_bruta_old));
            $("#si_afer_contrib").val(prima_bruta_new);
        }

    },

    changeAfcAsigGen:function () {

        console.log("changeAfcAsigGen");
        var that=this;
        var prima_bruta_old=$("#si_asig_gen_af").val();
        var prima_bruta_new=$("#sr_asig_gen_afc").val();
        if(prima_bruta_new=="" || prima_bruta_new==null || prima_bruta_new==undefined){
            prima_bruta_new=0;
            var dif=$("#dif_asig_gen").val();
            if(dif<0){
                $("#si_asig_gen_af").val(parseFloat($("#si_asig_gen_af").val())-parseFloat(dif));
            }else{
                $("#si_asig_gen_af").val(parseFloat($("#si_asig_gen_af").val())-parseFloat(dif));

            }
            $("#dif_asig_gen").val(0);


        }else{
            $("#dif_asig_gen").val(parseFloat(prima_bruta_new)-parseFloat(prima_bruta_old));
            $("#si_asig_gen_af").val(prima_bruta_new);
        }
        $("#si_afer_contrib").val(parseFloat($("#si_asig_viata_af").val())+parseFloat($("#si_asig_gen_af").val()));
        $("#si_sum_vir").val(parseFloat($("#si_afer_contrib").val())+parseFloat($("#si_af_dob_pen").val()));

    },
    changeAfcAsigViata:function () {

        console.log("changeAfcAsigViata");
        var that=this;
        var prima_bruta_old=$("#si_asig_viata_af").val();
        var prima_bruta_new=$("#sr_asig_viata_afc").val();
        if(prima_bruta_new=="" || prima_bruta_new==null || prima_bruta_new==undefined){
            prima_bruta_new=0;
            var dif=$("#dif_asig_viata_afc").val();
            if(dif<0){
                $("#si_asig_viata_af").val(parseFloat($("#si_asig_viata_af").val())-parseFloat(dif));
            }else{
                $("#si_asig_viata_af").val(parseFloat($("#si_asig_viata_af").val())-parseFloat(dif));

            }
            $("#dif_asig_viata_afc").val(0);


        }else{
            $("#dif_asig_viata_afc").val(parseFloat(prima_bruta_new)-parseFloat(prima_bruta_old));
            $("#si_asig_viata_af").val(prima_bruta_new);
        }
        $("#si_afer_contrib").val(parseFloat($("#si_asig_viata_af").val())+parseFloat($("#si_asig_gen_af").val()));
        $("#si_sum_vir").val(parseFloat($("#si_afer_contrib").val())+parseFloat($("#si_af_dob_pen").val()));
    },

    changeAfcDobPen:function () {

        console.log("changeAfcDobPen");
        var that=this;
        var prima_bruta_old=$("#si_af_dob_pen").val();
        var prima_bruta_new=$("#sr_afc_dob_pen").val();
        if(prima_bruta_new=="" || prima_bruta_new==null || prima_bruta_new==undefined){
            prima_bruta_new=0;
            var dif=$("#dif_afc_dob_pen").val();
            if(dif<0){
                $("#si_af_dob_pen").val(parseFloat($("#si_af_dob_pen").val())-parseFloat(dif));
            }else{
                $("#si_af_dob_pen").val(parseFloat($("#si_af_dob_pen").val())-parseFloat(dif));

            }
            $("#dif_afc_dob_pen").val(0);


        }else{
            $("#dif_afc_dob_pen").val(parseFloat(prima_bruta_new)-parseFloat(prima_bruta_old));
            $("#si_af_dob_pen").val(prima_bruta_new);
        }
        $("#si_sum_vir").val(parseFloat($("#si_afer_contrib").val())+parseFloat($("#si_af_dob_pen").val()));
    },
    getSaveInfoTabel: function (tabel) {

         // console.log("fields",fields);
        var saveDataTabel='[';
        $(tabel + ' > tbody >tr').each(function() {
            var fields = $(this).find("[saveableTd]");

            if(fields.length!=0){
                var saveData = "{";

                fields.each(function() {
                    var field = $(this);
                    //attr name td = nume coloana din baza de date
                    var key = field.attr("name");
                    if($(this).attr("type")==='checkbox') {
                        var value = field.prop("checked")?1:0;
                        if(value!==field.attr("value")) {
                            saveData+="\""+key+"\":\""+value+"\","
                        }
                    } else {

                        saveData+="\""+key+"\":\""+field.val()+"\","

                    }
                });
                saveData=saveData.substr(0,saveData.length-1);
                saveData+="},";
                saveDataTabel+=saveData;
            }

        });
        saveDataTabel=saveDataTabel.substr(0,saveDataTabel.length-1);
        saveDataTabel+="]";

        return saveDataTabel;
    },
    mandatoryFunctions: function () {

        var that=this;
        var defer = $.Deferred();
        var PROC_NAME = "FileManager.mandatoryFunctions";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);
        that.getWsAndUserInfo();
        //-------------- preluam WS_URL si WS_TOKEN din documentaPortal-util.js ---------------------

        FileManager.idTipDocument=new URLSearchParams(window.location.search).get("idTipDocument");
        FileManager.idPerioada=new URLSearchParams(window.location.search).get("idPerioada");
        FileManager.tipDocument=$UTIL.getTipDocument(FileManager.idTipDocument).id_tip_document;
        //functii load liste lov
        //id select, id container, cod lov
        FileManager.loadListaLov('#id_referinta_rectificativa', '#container_select_id_raportare_rectificata', 'LOV_RAPORTARI_SOC');
        FileManager.loadListaLov('#id_parinte_user_curent', '#container_select_id_parinte_user_curent', 'LOV_PERSOANA');
        FileManager.loadListaLov('#id_perioada_contabila', '#container_select_id_perioada_contabila', 'LOV_LUNA_RAPORTARE');
        FileManager.loadListaLov('#id_sediu_social', '#container_select_id_sediu_social', 'LOV_LOCALITATE');
        //FileManager.loadListaLov('#id_user_curent', '#container_select_id_user_curent', 'LOV_PERSOANA');

        //functii reload liste lov
        //timp reload after typing, id select, id container, cod lov
        FileManager.reloadListaLovAfterStopTyping(2000,'#id_referinta_rectificativa', '#container_select_id_raportare_rectificata', 'LOV_RAPORTARI_SOC');
        FileManager.reloadListaLovAfterStopTyping(2000,'#id_parinte_user_curent', '#container_select_id_parinte_user_curent', 'LOV_PERSOANA');
        FileManager.reloadListaLovAfterStopTyping(2000,'#id_perioada_contabila', '#container_select_id_perioada_contabila', 'LOV_LUNA_RAPORTARE');
        FileManager.reloadListaLovAfterStopTyping(2000,'#id_sediu_social', '#container_select_id_sediu_social', 'LOV_LOCALITATE');
       // FileManager.reloadListaLovAfterStopTyping(2000,'#id_user_curent', '#container_select_id_user_curent', 'LOV_PERSOANA');




        //-------------- initializam template-uri mustache ---------------------
        FileManager.compileAllTemplates();
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
          defer.resolve();

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
                window.parent.parent.scrollTo(0, 0);
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
    loadListaLov:function(select_id, container_id,lov_code){

        var that=this;
    $.ajax({
        url: '/dmsws/formular_custom/lov/get_values_by_code/'+lov_code,
        success: function (data) {

            if (data.result == 'OK') {

                that.renderLovData(data,select_id);
                if(lov_code=='LOV_LOCALITATE' && that.idLocalitateParinte!=null){
                    $(select_id).val(that.idLocalitateParinte).trigger("chosen:updated");

                }
                if(lov_code=='LOV_LUNA_RAPORTARE' && that.idPerioada!=null){
                    $(select_id).val(that.idPerioada).trigger("chosen:updated");

                }
             }
            else {
                window.parent.parent.scrollTo(0, 0);
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

    checkPerioada:function(){

        var that=this;
        var PROC_NAME = "FileManager.checkPerioada";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);
        var idPerioada=$("#id_perioada_contabila").val();
        if(idPerioada=="" || idPerioada==null){
            idPerioada="-1";
        }
            $.ajax({
            url: '/dmsws/formular_custom/checkPerioadaRaportare/'+idPerioada,
            success: function (data) {
                if (data.result == 'OK') {
                    if(data.existaRaportareLunaSelectata==1){
                        $("#btn_next_step").css("display", "none");
                        window.parent.parent.scrollTo(0, 0);
                        Swal.fire({
                            position: 'top',
                            icon: 'error',
                            html: "Exista deja o raportare pentru perioada selectata!<br/>",
                            focusConfirm: false,
                            confirmButtonText: 'Ok'
                        });
                    }else if(data.existaRaportareLunaSelectata==2){
                        window.parent.parent.scrollTo(0, 0);
                        $("#btn_next_step").css("display", "none");
                        Swal.fire({
                            position: 'top',
                            icon: 'error',
                            html: "Va rugam selectati o perioada!<br/>",
                            focusConfirm: false,
                            confirmButtonText: 'Ok'
                        });

                    }else{
                        $("#btn_next_step").css("display","block");
                    }

                }
                else {
                    window.parent.parent.scrollTo(0, 0);
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



    getInfoTabelRaport:function(){

        var that=this;
        if(that.idPerioada=="" || that.idPerioada==undefined || that.idPerioada==null){
            that.idPerioada=-1;
        }
        $.ajax({
            url: '/dmsws/formular_custom/getInfoRaportRectificare/' + that.idPerioada,
            success: function (data) {
                if (data.result == 'OK') {
                    if (data.areRaportare == 0) {

                        $("#si_prima_bruta").val(data.primeAsigBrute);
                        $("#si_asig_gen_prima").val(data.asigurariGenerale);
                        $("#si_asig_viata_prima").val(data.asigurariViata);
                        $("#si_asig_viata_prima").val(data.asigurariViata);
                        $("#si_cota_asig_gen").val(data.cotaGen);
                        $("#si_asig_viata_cota").val(data.cotaViata);
                        $("#si_contrib_dat").val(data.contribDatFga);
                        $("#si_aig_gen_contrib").val(data.asigurareGeneralaContrib);
                        $("#si_asig_viata_contrib").val(data.asigurareViataContrib);
                        $("#si_dobanzi_pen").val(data.dobanziPenalitati);
                        $("#si_sum_vir").val(data.sumaVirataTotal);
                        $("#si_afer_contrib").val(data.afContrib);
                        $("#si_asig_gen_af").val(data.asigGeneralAfc);
                        $("#si_asig_viata_af").val(data.asigViataAfc);
                        $("#si_af_dob_pen").val(data.aferentaDbPen);
                        $("#btn_next_step").css("display","block");
                        $("#sr_cota_asig_gen").val(data.cotaGen);
                        $("#sr_asig_viata_cota").val(data.cotaViata);
                    }else if(data.areRaportare==-1){
                        $("#btn_next_step").css("display", "none");

                    }else if(data.areRaportare==3){
                        $("#btn_next_step").css("display", "none");
                        window.parent.parent.scrollTo(0, 0);
                        Swal.fire({
                            position: 'top',
                            icon: 'error',
                            html: "Pentru luna selectata nu este facuta nici o raportare!<br/>",
                            focusConfirm: false,
                            confirmButtonText: 'Ok',
                                onClose: () => {
                                window.history.back();

                            }
                        });
                        $("#sr_cota_asig_gen").val(data.cotaGen);
                        $("#sr_asig_viata_cota").val(data.cotaViata);
                    }else if(data.areRaportare==4){
                        $("#btn_next_step").css("display", "none");
                        window.parent.parent.scrollTo(0, 0);
                        Swal.fire({
                            position: 'top',
                            icon: 'error',
                            html: "Pentru luna selectata a fost deja trimisa rectificativa!<br/>",
                            focusConfirm: false,
                            confirmButtonText: 'Ok',
                                onClose: () => {
                                window.history.back();

                            }
                        });
                        $("#sr_cota_asig_gen").val(data.cotaGen);
                        $("#sr_asig_viata_cota").val(data.cotaViata);
                    }
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
                            window.parent.parent.scrollTo(0, 0);
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



    goBack: function () {
       window.history.back();
    },


    /*
     Function to compile all known mustache templates.
     */
    compileAllTemplates: function () {
        this.templates['tmpl_lov'] = $('#tmpl_lov').html();


        // parseaza toate template-urile
        $.each(this.templates, function (index, template) {
            Mustache.parse(template);
        });
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
   mergeObjects : function(object1, object2){
    var obj = {};

    function merge(obj1, obj2){
        $.each(obj2, function(key, value) {
            obj1[key] = value;
        });
    }

    if (typeof object1 !== 'undefined' && object1 !== null){
        merge(obj, object1);
    }

    if (typeof object2 !== 'undefined' && object2 !== null){
        merge(obj, object2);
    }

    return obj;
}
};


$(document).ready(function () {
    FileManager.init();
});