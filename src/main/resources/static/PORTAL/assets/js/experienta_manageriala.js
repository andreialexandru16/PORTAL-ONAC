var PAGE_NAME = "experienta_manageriala.js";
var MOD_EXPERIENTA=null;
var ID_EXPERIENTA=null;

var PageManager = {
    myDrop:null,
    wsUrl: null,
    wsToken: null,
    templates: {},
    listaInregistrari: {},
    listaCautare:{},
    nrPagina: 0,
    nrMaxPages: 4,
    numPerPage: 10,
    COD_LOV_DEPARTAMENT: "LOV_DEPARTAMENTE_CONTACTE",
     init: function () {

        var PROC_NAME = "PageManager.init";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);
        //-------------- se apeleaza obligatoriu la initializarea paginii---------------------
        this.mandatoryFunctions();

        //apelam reconstructie paginare & afisare rezultate
        PageManager.afiseazaInregistrari();
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);


    },

    cautare: function(){

        var PROC_NAME = "PageManager.cautare";
        var searchStr=$('#search_box').val();
        PageManager.buildPaginationCautare(searchStr).then(function () {
            PageManager.getListaInregistrariCautare(searchStr);
        });





        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },
    adaugaContact: function(){

        var that=this;
        var PROC_NAME = "PageManager.adaugaContact";

        var jReq= PageManager.getInfoRandNou();

        if(MOD_EXPERIENTA=="ADD"){
        var id_pers = new URLSearchParams(window.location.search).get('id_pers');
            var url = "/dmsws/anre/adaugaExperientaManageriala/"+id_pers;
        }else{
            var url = "/dmsws/anre/editeaza_experienta/"+ID_EXPERIENTA;
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
                            html: "A fost actualizata experienta manageriala.",
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

        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },
    getInfoRandNou: function(){

        var that=this;
        var PROC_NAME = "PageManager.getInfoRandNou";

        var firma= $("#firma").val();
        var functie= $("#functie").val();
        var activitati_desfasurate= $("#activitati_desfasurate").val();
        var de_la= $("#de_la").val();
        var pana_la= $("#pana_la").val();
        var prezent= $("#prezent").prop('checked');

        if(prezent!=null && typeof prezent!='undefined' && prezent==true){
            prezent=1;
        }else{
            prezent=0;
        }

            var objRand= {
                firma: firma,
                functie: functie,
                activitati_desfasurate: activitati_desfasurate,
                de_la:de_la,
                pana_la:pana_la,
                prezent:prezent

            };
            return JSON.stringify(objRand);

        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },
    prepareOpenRandNou: function(){

        $('#resize_iframe', window.parent.document).trigger('click');
        var that=this;
        var PROC_NAME = "PageManager.prepareOpenRandNou";


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

        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },
    afiseazaInregistrari: function () {
        var PROC_NAME = "PageManager.afiseazaInregistrari";


        //apelam reconstructie paginare & afisare rezultate
        PageManager.getListaInregistrari();

        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },
    getListaInregistrari: function (jReq) {

        var that = this;
        var PROC_NAME = "PageManager.getListaInregistrari";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);
        var id_pers = new URLSearchParams(window.location.search).get('id_pers');

        var url ="/dmsws/anre/getExperientaManageriala/"+id_pers;

        console.log("Ajax Call Start:" + url);

        $.ajax({
            url: url,

            success: function (data) {
                if (data.result == 'OK') {
                    that.listaInregistrari = data.experientaManagerialaList;


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
                        that.renderTemplate('tmpl_inregistrari_list', {data: data.experientaManagerialaList}).then(function (html) {
                            var tblHolder = $('.table_inregistrari');
                            tblHolder.html('');
                            tblHolder.html(html);


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
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },
    resetExperienta:function(){
        MOD_EXPERIENTA="ADD";
        $("#firma").val(null);
        $("#functie").val(null);
        $("#activitati_desfasurate").val(null);
        document.getElementById("prezent").checked=false;
        $("#de_la").val(null);
        $("#pana_la").val(null);

    },
    getExperienta: function(id){
        var url ="/dmsws/anre/getExperienta/"+id;
        MOD_EXPERIENTA="EDIT";

        console.log("Ajax Call Start:" + url);
        $.ajax({
            url: url,

            success: function (data) {
                if (data.experientaManagerialaList[0].id !== null && data.experientaManagerialaList[0].id!=undefined) {
                    $("#firma").val(data.experientaManagerialaList[0].firma);
                    $("#functie").val(data.experientaManagerialaList[0].functie);
                    $("#activitati_desfasurate").val(data.experientaManagerialaList[0].activitati_desfasurate);
                    $("#de_la").val(data.experientaManagerialaList[0].de_la);
                    $("#pana_la").val(data.experientaManagerialaList[0].pana_la);

                    if(data.experientaManagerialaList[0].prezent!=null && data.experientaManagerialaList[0].prezent=='1') {
                        document.getElementById("prezent").checked=true;
                    }

                    ID_EXPERIENTA=data.experientaManagerialaList[0].id;
                }}



        });
    },
    deleteExperienta: function(id){

        var that=this;
        var PROC_NAME = "PageManager.deleteExperienta";



        var url = "/dmsws/anre/stergeExperienta/"+id;

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
                        html: "A fost stearsa experienta.",
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



        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },

    mandatoryFunctions: function () {
        var PROC_NAME = "PageManager.mandatoryFunctions";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);
        //-------------- preluam WS_URL si WS_TOKEN din documentaPortal-util.js ---------------------


        //-------------- initializam template-uri mustache ---------------------
        PageManager.compileAllTemplates();
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },
    compileAllTemplates: function () {
        this.templates['tmpl_inregistrari_list'] = $('#tmpl_inregistrari_list').html();
        this.templates['tmpl_departament'] = $('#tmpl_departament').html();



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
        .submit( function( e ) {
            e.preventDefault();
        });

});

function validateEmail(email) {
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(String(email).toLowerCase()) || email=='';
}




