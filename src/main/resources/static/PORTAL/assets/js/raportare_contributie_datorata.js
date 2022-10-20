var PAGE_NAME = "raportare_contributie_datorata.js";

var FileManager = {
    parametersLoadedOk: false,

    MOD:null,
     uploadFile:null,
    uploadFileName:null,
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
    cotaGen:0,
    cotaViata:0,
    valContribGenCota:0,
    valContribViataCota:0,
    anteSPrima:0,
    ref_prime_brute1:0,
    ref_prime_brute2:0,
    ref_asg_gen_prime1:0,
    ref_asg_gen_prime2:0,
    ref_asg_viata_prime1:0,
    ref_asg_viata_prime2:0,
    ref_contrib_dat1:0,
    ref_contrib_dat2:0,
    ref_contrib_asg_gen1:0,
    ref_contrib_asg_gen2:0,
    ref_contrib_asg_viata1:0,
    ref_contrib_asg_viata2:0,
    ref_dobanzi1:0,
    ref_dobanzi2:0,
    ref_suma_virata_total1:0,
    ref_suma_virata_total2:0,
    ref_aferenta_contrib_dat1:0,
    ref_aferenta_contrib_dat2:0,
    ref_afr_contrib_asg_gen1:0,
    ref_afr_contrib_asg_gen2:0,
    ref_afr_contrib_asg_viata1:0,
    ref_afr_contrib_asg_viata2:0,
    ref_aferenta_dob_pen1:0,
    ref_aferenta_dob_pen2:0,
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
                    window.parent.parent.scrollTo(0, 0);
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
    salveazaCerereDraft: function (download) {

        var that=this;


                    //1. Recalculare Suma virata
                    FileManager.calculeazaSumaR6R7(true);

                    // 2. Salvare campuri
                    var saveInfoTabel;
                    $('table').each(function() {
                        saveInfoTabel=that.getSaveInfoTabel("#"+$(this).attr("id"));
                    });
                    //
                    if (saveInfoTabel != null && saveInfoTabel != 0) {
                        that.saveDoc(saveInfoTabel,true,download);

                    }
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
                    //1. Recalculare Suma virata
                    FileManager.calculeazaSumaR6R7(true);

                    // 2. Salvare campuri
                    var saveInfoTabel;
                    $('table').each(function() {
                        saveInfoTabel=that.getSaveInfoTabel("#"+$(this).attr("id"));
                    });
                    //
                    if (saveInfoTabel != null && saveInfoTabel != 0) {
                        that.saveDoc(saveInfoTabel,true,download,sendReport);

                    }
                }
            }, onError: function (data) {

                window.parent.parent.scrollTo(0, 0);

                    Swal.fire({
                        icon: 'error',
                        position: 'top',
                        html: "Eroare! Ne pare rau, cererea nu a putut fi salvata!",
                        focusConfirm: false,
                        confirmButtonText: 'Ok'
                    });
                    console.log(data);
                }
            });



    },
    salveazaCerere: function () {

        var that=this;
        window.parent.parent.scrollTo(0, 0);

        Swal.fire({
                title:"Va rugam asteptati",
                showConfirmButton:true,
                allowOutsideClick: false,
                position: 'top',
                onBeforeOpen: () => {
                Swal.showLoading();
    }
    });

            //1. Recalculare Suma virata
            FileManager.calculeazaSumaR6R7(true);

        // 2. Check completare campuri obligatorii
        var saveInfoTabel = FileManager.checkCampuriObligatorii();

        // 3. Check doc justificativ, upload & send cerere
        if (saveInfoTabel != null && saveInfoTabel != 0) {
            var upload_file = $("#input_file").val();
            if (typeof upload_file !== 'undefined' && upload_file !== null && upload_file !== '') {

                if(FileManager.idFisier!=null && typeof FileManager.idFisier!='undefined'){
                    /* -> salvare campuri cerere
                     -> rulareJasper
                     -> download*/
                    if(FileManager.MOD!=null &&  FileManager.MOD=='EDIT' ){
                        that.saveDoc(saveInfoTabel,true,false,false);

                    }else{
                        that.saveDoc(saveInfoTabel,true,false,true);

                    }

                }else{
                    /*-> create dummy file
                     -> salvare campuri cerere
                     -> rulareJasper
                     -> download*/
                    if(FileManager.MOD!=null &&  FileManager.MOD=='EDIT' ){
                        FileManager.salveazaCerereDraftWithDummy(false,false);

                    }else{
                        FileManager.salveazaCerereDraftWithDummy(false,true);

                    }
                }


            } else {
                window.parent.parent.scrollTo(0, 0);

                Swal.fire({
                    position: 'top',
                    icon: 'error',
                    html: "Incarcati documentul justificativ!",
                    focusConfirm: false,
                    confirmButtonText: 'Ok'
                });
            }
        }


    },
    rulareRaportareGarantare : function (idFisier, download) {

        var that=this;
        console.log("rulareRaportareGarantare");
        var url = that.wsAndUserInfo.wsUrl + '/jasper/' + that.wsAndUserInfo.userToken.token +'/rulareRaportareJasperByTipDoc/'+idFisier+'?&codtipdoc=RAPORTARE_GARANTARE';
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
    reseteazaValoriTabel: function () {

        var that=this;
        console.log("FileManager.reseteazaValoriTabel");
        //refresh zona nr_doc pentru valorile introduse
        $("#nr_doc_prime_brute").val("");
        $("#nr_doc_asg_gen_prm").val("");
        $("#nr_doc_plata_asg_viata_prime").val("");
        $("#nr_doc_cote1").val("");
        $("#nr_doc_asg_gen_cota").val("");
        $("#nr_doc_asg_viata_cota").val("");
        $("#nr_doc_contrib_dat").val("");
        $("#nr_doc_asg_gen_contrib").val("");
        $("#nr_doc_contrib_asg_viata").val("");
        $("#nr_doc_dobanzi").val("");
        $("#nr_doc_suma_virata").val("");
        $("#nr_doc_aferenta_contrib_dat").val("");
        $("#nr_doc_asg_gen_afr_contrb").val("");
        $("#nr_doc_afr_contr_asg_viata").val("");
        $("#nr_doc_af_dob_pen").val("");
        //refresh zona "din care luna pentru raportare"
        $("#prima_luna_rap_curenta").val(0);
        $("#asig_gen_prime_luna_raportare").val(0);
        $("#asig_viata_prime_luna_raportare").val(0);
        $("#prima_luna_rap_curenta_contrib").val(0);
        $("#asig_gen_prime_luna_raportare_contrib").val(0);
        $("#asig_viata_prime_luna_raportare_contrib").val(0);
        $("#dobanda_luna_curenta").val(0);
        $("#suma_virata_total_luna_curenta").val(0);
        $("#sum_vizata_total").val(0);
        $("#asig_gen_sm").val(0);
        $("#asig_viata_suma").val(0);
        $("#r7_luna_curenta").val(0);
        // refresh pe zona de total cumulat de la inceputul anului
        $("#prima_bruta_total").val(FileManager.ref_prime_brute1);
        $("#total_asig_gen_suma").val(FileManager.ref_asg_gen_prime1);
        $("#total_asig_viata_sum").val(FileManager.ref_asg_viata_prime1);
        $("#total_cota_sum").val(FileManager.ref_contrib_dat1);
        $("#total_asig_gen_cota").val(FileManager.ref_contrib_asg_gen1);
        $("#total_asig_viata_cot").val(FileManager.ref_contrib_asg_viata1);
        $("#dobanzi_pen").val(FileManager.ref_dobanzi1);
        $("#suma_virata").val(FileManager.ref_suma_virata_total1);
        $("#afer_contrib").val(FileManager.ref_aferenta_contrib_dat1);
        $("#asig_gen_af").val(FileManager.ref_afr_contrib_asg_gen1);
        $("#asig_viata_af").val(FileManager.ref_afr_contrib_asg_viata1);
        $("#af_dob_pen").val(FileManager.ref_aferenta_dob_pen1);

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
                    window.parent.parent.scrollTo(0, 0);

                    Swal.fire({
                            position: 'top',
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
                    that.uploadFileRap(FileManager.idFisier);
                    window.parent.parent.scrollTo(0, 0);

                    Swal.fire({
                            position: 'top',
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
                window.parent.parent.scrollTo(0, 0);

                var strErr = Util.getAjaxErrorMessage(err);
                Swal.fire({
                        position: 'top',
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
    checkCampuriObligatorii:function(){
        var that=this;
        var datastr;
        var saveInfoTabel;
        $('table').each(function() {
            saveInfoTabel=that.getSaveInfoTabel("#"+$(this).attr("id"));
            datastr=JSON.parse(saveInfoTabel);
        });

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
        }
        else if(datastr[1].val_luna_raportare===null || datastr[1].val_luna_raportare==="" || datastr[1].val_luna_raportare===undefined
            || datastr[2].val_luna_raportare===null || datastr[2].val_luna_raportare==="" || datastr[2].val_luna_raportare===undefined
            || datastr[7].val_luna_raportare===null || datastr[7].val_luna_raportare==="" || datastr[7].val_luna_raportare===undefined
            || datastr[8].val_luna_raportare===null || datastr[8].val_luna_raportare==="" || datastr[8].val_luna_raportare===undefined
            || datastr[9].val_luna_raportare===null || datastr[9].val_luna_raportare==="" || datastr[9].val_luna_raportare===undefined
            || datastr[12].val_luna_raportare===null || datastr[12].val_luna_raportare==="" || datastr[12].val_luna_raportare===undefined
            || datastr[13].val_luna_raportare===null || datastr[13].val_luna_raportare==="" || datastr[13].val_luna_raportare===undefined
            || datastr[14].val_luna_raportare===null || datastr[14].val_luna_raportare==="" || datastr[14].val_luna_raportare===undefined){
            window.parent.parent.scrollTo(0, 0);
            Swal.fire({
                position: 'top',
                icon: 'error',
                html: "Va rugam sa completati campurile cu '*'!<br/>",
                focusConfirm: false,
                confirmButtonText: 'Ok'
            });
        }else if(datastr[12].nr_data_doc_plata===null || datastr[12].nr_data_doc_plata==="" || datastr[12].nr_data_doc_plata===undefined
            || datastr[13].nr_data_doc_plata===null || datastr[13].nr_data_doc_plata==="" || datastr[13].nr_data_doc_plata===undefined
            || datastr[14].nr_data_doc_plata===null || datastr[14].nr_data_doc_plata==="" || datastr[14].nr_data_doc_plata===undefined) {
            window.parent.parent.scrollTo(0, 0);
            Swal.fire({
                position: 'top',
                icon: 'error',
                html: "Va rugam sa completati numarul si data documentului din zona 6,7!<br/>",
                focusConfirm: false,
                confirmButtonText: 'Ok'
            });
        }else{
            return saveInfoTabel;
        }
        return 0;
    },
    saveDoc:function(saveInfoTabel, genereazaJasper, download,sendRaport){

        var that=this;


                        var idFisier =FileManager.idFisier;
                        //call WS by ajaxAction salvare campuri cerere
                        var saveInfo = that.getSaveInfo($("#formular"));
                        var url = '/dmsws/formular_custom/adaugaFormular/' + that.mainTableName + '/' + idFisier + '/';

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

                                    $('table').each(function () {

                                        //call WS salvare tabel cerere
                                        //id tag table = nume tabel din baza de date

                                            var urlTabel = '/dmsws/formular_custom/adaugaTabel/' + $(this).attr("id") + '/' + idFisier + '/';
                                            that.ajaxAction({
                                                url: urlTabel,
                                                method: 'POST',
                                                headers: that.mergeObjects(that.corsHeaders, {
                                                    'Accept': 'application/json',
                                                    'Content-Type': 'application/json'
                                                }),
                                                data: saveInfoTabel,
                                                onSuccess: function (data) {
                                                    swal.close();
                                                    if (data.result == 'OK') {
                                                        if( genereazaJasper!=null && genereazaJasper==true){
                                                            FileManager.rulareRaportareGarantare(FileManager.idFisier,download);
                                                        }
                                                        if( sendRaport!=null && sendRaport==true){
                                                            that.sendRaport();
                                                        }
                                                        if(FileManager.MOD!=null &&  FileManager.MOD=='EDIT' ) {
                                                            window.parent.parent.scrollTo(0, 0);

                                                            Swal.fire({
                                                                    position: 'top',
                                                                    icon: "info",
                                                                    html: "Raportarea a fost salvata cu succes!",
                                                                    focusConfirm: false,
                                                                    confirmButtonText: "Ok",
                                                                    onClose: () => {
                                                                    window.history.back();

                                                        }
                                                        });
                                                        }


                                                        } else {
                                                        swal.close();
                                                        window.parent.parent.scrollTo(0, 0);

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
                    window.parent.parent.scrollTo(0, 0);

                    Swal.fire({
                        position: 'top',
                        icon: 'error',
                        html: "Ne pare rau,pdf eronat!",
                        focusConfirm: false,
                        confirmButtonText: 'Ok'
                    });
                }
            },
            onError: function (data) {

                //$.fancybox.close();
                window.parent.parent.scrollTo(0, 0);

                Swal.fire({
                    position: 'top',
                    icon: 'error',
                    html: "Eroare! Ne pare rau, pdf eronat!",
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
        $("#total_asig_gen_suma").val(parseFloat(FileManager.ref_asg_gen_prime1)+parseFloat(asigurariGenerale));
        $("#prima_bruta_total").val(parseFloat($("#total_asig_gen_suma").val())+parseFloat($("#total_asig_viata_sum").val()));
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
        $("#total_asig_viata_sum").val(parseFloat(FileManager.ref_asg_viata_prime1)+parseFloat(asigurariViata));
        $("#prima_bruta_total").val(parseFloat($("#total_asig_gen_suma").val())+parseFloat($("#total_asig_viata_sum").val()));

    },
    verificaCota1:function(){

        console.log("verificaCota1");
        var asigGenContrib=$("#asig_gen_prime_luna_raportare_contrib").val();
        var asigGenPrumeBrune=$("#asig_gen_prime_luna_raportare").val();
        if(asigGenContrib!=null && asigGenContrib!=undefined && asigGenContrib!=""){
            FileManager.valContribGenCota=asigGenContrib;
            var cotaCalculata=(parseFloat(asigGenPrumeBrune)*parseFloat(FileManager.cotaGen))/100;
            if(parseFloat(asigGenContrib)!=parseFloat(cotaCalculata)){

                Swal.fire({
                    icon: 'error',
                    html: "Valoarea pentru contributii zona asigurari generale nu este valida!",
                    focusConfirm: false,
                    confirmButtonText: 'Ok'
                });
            }
        }
    },

    changeValAsigGenContrib:function () {

        console.log("changeValAsigGenContrib");
        var that=this;
        var asigGenContrib=$("#asig_gen_prime_luna_raportare_contrib").val();
        var asigGenPrumeBrune=$("#asig_gen_prime_luna_raportare").val();
        that.calculZonaPrimeAsigGenContrib();
            // if($("#prima_luna_rap_curenta_contrib").val()==0){
            //     $("#asig_gen_prime_luna_raportare_contrib").val(0)
            // }else {
            //     $("#total_asig_gen_cota").val(0);
            //     $("#prima_luna_rap_curenta_contrib").val(parseFloat($("#prima_luna_rap_curenta_contrib").val()) - parseFloat(FileManager.valContribGenCota));
            //     $("#total_cota_sum").val(parseFloat($("#total_cota_sum").val()) - parseFloat(FileManager.valContribGenCota));
            // }




    },

    verificaCota2:function(){

        console.log("verificaCota2");
        var asigViataContrib=$("#asig_viata_prime_luna_raportare_contrib").val();
        var asigViataPrumeBrune=$("#asig_viata_prime_luna_raportare").val();
        if(asigViataContrib!=null && asigViataContrib!=undefined && asigViataContrib!="") {
            FileManager.valContribViataCota = asigViataContrib;
            var cotaCalculata = (parseFloat(asigViataPrumeBrune) * parseFloat(FileManager.cotaViata)) / 100;
            if (parseFloat(asigViataContrib) != parseFloat(cotaCalculata)) {
                Swal.fire({
                    icon: 'error',
                    html: "Valoarea pentru contributii zona asigurari de viata nu este valida!",
                    focusConfirm: false,
                    confirmButtonText: 'Ok'
                });
            }
        }
    },

    changeValAsigViataContrib:function () {

        console.log("changeValAsigViataContrib");
        var that=this;
        var asigViataContrib=$("#asig_viata_prime_luna_raportare_contrib").val();
        var asigViataPrumeBrune=$("#asig_viata_prime_luna_raportare").val();
        that.calculZonaPrimeAsigViataContrib();

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
      $("#total_asig_gen_cota").val(parseFloat(FileManager.ref_contrib_asg_gen1) + parseFloat(sumaCalculata));
        $("#total_cota_sum").val(parseFloat($("#total_asig_gen_cota").val()) + parseFloat($("#total_asig_viata_cot").val()));


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
        $("#total_asig_viata_cot").val(parseFloat(FileManager.ref_contrib_asg_viata1) + parseFloat(sumaCalculata));
        $("#total_cota_sum").val(parseFloat($("#total_asig_gen_cota").val()) + parseFloat($("#total_asig_viata_cot").val()));


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
        $("#asig_gen_af").val(parseFloat(FileManager.ref_afr_contrib_asg_gen1)+parseFloat(asigurariGenerale));
        $("#sum_vizata_total").val(parseFloat(asigurariGenerale)+parseFloat(asigurariViata));
        $("#afer_contrib").val(parseFloat($("#asig_gen_af").val()) + parseFloat($("#asig_viata_af").val()));


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
        $("#asig_viata_af").val(parseFloat(FileManager.ref_afr_contrib_asg_viata1)+parseFloat(asigurariViata));
        $("#sum_vizata_total").val(parseFloat(asigurariGenerale)+parseFloat(asigurariViata));
        $("#afer_contrib").val(parseFloat($("#asig_viata_af").val()) + parseFloat($("#asig_gen_af").val()));


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
            $("#af_dob_pen").val(parseFloat(FileManager.ref_aferenta_dob_pen1) + parseFloat(r7));
        }else {

            $("#af_dob_pen").val(parseFloat(FileManager.ref_aferenta_dob_pen1) + parseFloat(r7));
            FileManager.anteAfDob=r7;
        }
    },

    sumDobanda:function () {

        console.log("sumDobanda");
        var that=this;
       var dobanda_luna_curenta=$("#dobanda_luna_curenta").val();
        if(dobanda_luna_curenta==null || dobanda_luna_curenta==undefined || dobanda_luna_curenta==""){
            dobanda_luna_curenta=0;
            $("#dobanzi_pen").val(parseFloat(FileManager.ref_dobanzi1) + parseFloat(dobanda_luna_curenta));

        }else {

            $("#dobanzi_pen").val(parseFloat(FileManager.ref_dobanzi1) + parseFloat(dobanda_luna_curenta));
        }
    },

    calculeazaSumaR6R7:function (hideSwal) {

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


        if (!(typeof  hideSwal!='undefined' &&hideSwal==true)){
            window.parent.parent.scrollTo(0, 0);
            Swal.fire({
                position: 'top',
                icon: 'info',
                html: "Suma a fost recalculata!<br/>",
                focusConfirm: false,
                confirmButtonText: 'Ok'
            });
        }

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
        //-------------- preluam WS_URL si WS_TOKEN din documentaPortal-util.js ---------------------
        that.getWsAndUserInfo();
        FileManager.idTipDocument=new URLSearchParams(window.location.search).get("idTipDocument");
        FileManager.idPerioada=new URLSearchParams(window.location.search).get("idPerioada");
        var request=new URLSearchParams(window.location.search).get("request");
        if(request!=null  && typeof request!='undefined' && request!=''){
            FileManager.idFisier=new URLSearchParams(window.location.search).get("request");
            FileManager.MOD='EDIT';
        }
        FileManager.tipDocument=$UTIL.getTipDocument(FileManager.idTipDocument).id_tip_document;
        //functii load liste lov
        //id select, id container, cod lov
        FileManager.loadListaLov('#id_parinte_user_curent', '#container_select_id_parinte_user_curent', 'LOV_PERSOANA');
        FileManager.loadListaLov('#id_perioada_contabila', '#container_select_id_perioada_contabila', 'LOV_LUNA_RAPORTARE');
        FileManager.loadListaLov('#id_sediu_social', '#container_select_id_sediu_social', 'LOV_LOCALITATE');
       // FileManager.loadListaLov('#id_user_curent', '#container_select_id_user_curent', 'LOV_PERSOANA');

        //functii reload liste lov
        //timp reload after typing, id select, id container, cod lov

        FileManager.reloadListaLovAfterStopTyping(2000,'#id_parinte_user_curent', '#container_select_id_parinte_user_curent', 'LOV_PERSOANA');
        FileManager.reloadListaLovAfterStopTyping(2000,'#id_perioada_contabila', '#container_select_id_perioada_contabila', 'LOV_LUNA_RAPORTARE');
        FileManager.reloadListaLovAfterStopTyping(2000,'#id_sediu_social', '#container_select_id_sediu_social', 'LOV_LOCALITATE');
      //  FileManager.reloadListaLovAfterStopTyping(2000,'#id_user_curent', '#container_select_id_user_curent', 'LOV_PERSOANA');




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
                    if(data.existaRaportareLunaSelectata==-1){
                        $("#btn_next_step").css("display", "none");
                        window.parent.parent.scrollTo(0, 0);
                        Swal.fire({
                            position: 'top',
                            icon: 'error',
                            html: "Pentru raportarile initiale termenul este ultima zi lucratoare a lunii curente pentru luna anterioara!<br/>",
                            focusConfirm: false,
                            confirmButtonText: 'Ok'
                        });
                    }else if(data.existaRaportareLunaSelectata==2){
                        $("#btn_next_step").css("display", "none");
                        window.parent.parent.scrollTo(0, 0);
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
        var params="?&codTipDoc=RAP_CONTR_DAT_VIR_FGA_GAR";

        if(that.idPerioada!=null && typeof that.idPerioada!='undefined'){
            params+='&idPerioada='+that.idPerioada;
        }

        if(that.idFisier!=null && typeof that.idFisier!='undefined'){
            params+='&idFisier='+that.idFisier;
        }
        $.ajax({
            url: '/dmsws/formular_custom/getInfoTabelRaport'+params,
            success: function (data) {
                if (data.result == 'OK') {
                    if(data.areRaportare==0){


                        $("#prima_bruta_total").val(data.primeAsigBrute);
                        FileManager.ref_prime_brute1=data.primeAsigBrute;

                        $("#total_asig_gen_suma").val(data.asigurariGenerale);
                        FileManager.ref_asg_gen_prime1=data.asigurariGenerale;

                        $("#total_asig_viata_sum").val(data.asigurariViata);
                        FileManager.ref_asg_viata_prime1=data.asigurariViata;

                        $("#total_cota_sum").val(data.contribDatFga);
                        FileManager.ref_contrib_dat1=data.contribDatFga;

                        $("#total_asig_gen_cota").val(data.asigurareGeneralaContrib);
                        FileManager.ref_contrib_asg_gen1=data.asigurareGeneralaContrib;

                        $("#total_asig_viata_cot").val(data.asigurareViataContrib);
                        FileManager.ref_contrib_asg_viata1=data.asigurareViataContrib;

                        $("#dobanzi_pen").val(data.dobanziPenalitati);
                        FileManager.ref_dobanzi1=data.dobanziPenalitati;

                        $("#suma_virata").val(data.sumaVirataTotal);
                        FileManager.ref_suma_virata_total1=data.sumaVirataTotal;

                        $("#afer_contrib").val(data.afContrib);
                        FileManager.ref_aferenta_contrib_dat1=data.afContrib;

                        $("#asig_gen_af").val(data.asigGeneralAfc);
                        FileManager.ref_afr_contrib_asg_gen1=data.asigGeneralAfc;

                        $("#asig_viata_af").val(data.asigViataAfc);
                        FileManager.ref_afr_contrib_asg_viata1=data.asigViataAfc;

                        $("#af_dob_pen").val(data.aferentaDbPen);
                        FileManager.ref_aferenta_dob_pen1=data.aferentaDbPen;

                        $("#cota_asv").val(data.cotaViata);
                        $("#cota_asg").val(data.cotaGen);

                        if (that.MOD!=null && that.MOD=='EDIT'){
                            $("#subsemnatul").val(data.subsemnatul);
                            $("#in_calitate_de").val(data.inCalitateDe);

                            $("#prima_luna_rap_curenta").val(data.primeAsigBruteLuna);
                            $("#nr_doc_prime_brute").val(data.primeAsigBruteDoc);

                            $("#asig_gen_prime_luna_raportare").val(data.asigurariGeneraleLuna);
                            $("#nr_doc_asg_gen_prm").val(data.asigurariGeneraleDoc);

                            $("#asig_viata_prime_luna_raportare").val(data.asigurariViataLuna);
                            $("#nr_doc_plata_asg_viata_prime").val(data.asigurariViataDoc);

                            $("#prima_luna_rap_curenta_contrib").val(data.contribDatFgaLuna);
                            $("#nr_doc_contrib_dat").val(data.contribDatFgaDoc);

                            $("#asig_gen_prime_luna_raportare_contrib").val(data.asigurareGeneralaContribLuna);
                            $("#nr_doc_asg_gen_contrib").val(data.asigurareGeneralaContribDoc);

                            $("#asig_viata_prime_luna_raportare_contrib").val(data.asigurareViataContribLuna);
                            $("#nr_doc_contrib_asg_viata").val(data.asigurareViataContribDoc);

                            $("#dobanda_luna_curenta").val(data.dobanziPenalitatiLuna);
                            $("#nr_doc_dobanzi").val(data.dobanziPenalitatiDoc);

                            $("#suma_virata_total_luna_curenta").val(data.sumaVirataTotalLuna);
                            $("#nr_doc_suma_virata").val(data.sumaVirataTotalDoc);

                            $("#sum_vizata_total").val(data.afContribLuna);
                            $("#nr_doc_aferenta_contrib_dat").val(data.afContribDoc);

                            $("#asig_gen_sm").val(data.asigGeneralAfcLuna);
                            $("#nr_doc_asg_gen_afr_contrb").val(data.asigGeneralAfcDoc);

                            $("#asig_viata_suma").val(data.asigViataAfcLuna);
                            $("#nr_doc_afr_contr_asg_viata").val(data.asigViataAfcDoc);

                            $("#r7_luna_curenta").val(data.aferentaDbPenLuna);
                            $("#nr_doc_af_dob_pen").val(data.aferentaDbPenDoc);
                        }
                        FileManager.cotaGen=data.cotaGen;
                        FileManager.cotaViata=data.cotaViata;

                        FileManager.calculZonaPrimeAsigGen();
                        FileManager.changeValAsigGenContrib();
                        FileManager.changeValAsigViataContrib();
                        FileManager.sumDobanda();
                        FileManager.calculSumaVirataZonaGen();
                        FileManager.calculSumaVirataZonaViata();
                        FileManager.changeSumaVAf();
                    }else{
                        FileManager.cotaGen=data.cotaGen;
                        FileManager.cotaViata=data.cotaViata;
                        $("#cota_asv").val(data.cotaViata);
                        $("#cota_asg").val(data.cotaGen);
                        $("#prima_bruta_total").val(0);
                        $("#total_asig_gen_suma").val(0);
                        $("#total_asig_viata_sum").val(0);
                        $("#total_cota_sum").val(0);
                        $("#total_asig_gen_cota").val(0);
                        $("#total_asig_viata_cot").val(0);
                        $("#dobanzi_pen").val(0);
                        $("#suma_virata").val(0);
                        $("#afer_contrib").val(0);
                        $("#afer_contrib").val(0);
                        $("#asig_gen_af").val(0);
                        $("#asig_viata_af").val(0);
                        $("#af_dob_pen").val(0);
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
    uploadFileRap: function(idFisier){

        var that = this;
        var upload_file = $("#input_file").val();

        if (typeof upload_file !== 'undefined' && upload_file !== null && upload_file !== ''){

                var fileData = $("#input_file")[0];

                var url = '/dmsws/anre/uploadDocumentCont';

                var formData = new FormData();
                formData.append('file', fileData.files[0]);
                formData.append('nume', fileData.files[0].name);
                formData.append('rapG','1');
                formData.append('idFisier',idFisier);
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
                        }
                        else {
                            window.parent.parent.scrollTo(0, 0);

                            Swal.fire({
                                position: 'top',
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


        }else{
            $.fancybox.close();
            window.parent.parent.scrollTo(0, 0);

            Swal.fire({
                position: 'top',
                icon: 'error',
                html: "Incarcati fisierul!",
                focusConfirm: false,
                confirmButtonText: 'Ok'
            });
        }
    },
    uploadFile:function(input){



        console.log("FileManager.uploadFile input:" + input);
        var that=this;
        var reader=new FileReader();
        var file = input.files[0];
        reader.readAsDataURL(file);
        reader.onload =function () {

            FileManager.uploadFile=reader.result;
            FileManager.uploadFileName=$(input).val().split('\\').pop();

        };
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
                    if(!(FileManager.MOD!=null &&  FileManager.MOD=='EDIT' )){
                        $("#subsemnatul").val(data.userCurent.nume+' '+data.userCurent.prenume);

                    }
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