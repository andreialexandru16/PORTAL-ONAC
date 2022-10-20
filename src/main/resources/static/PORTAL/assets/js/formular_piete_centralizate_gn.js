var PAGE_NAME = "formular_piete_centralizate_gn.js";

var FileManager = {
    parametersLoadedOk: false,


    // user info object
    mainTableName:'RAPORTARE_TRANZACTII_PIETE_CENTRALIZATE_GN',
    idTipDocument: null,
    nrCrtTabelA: 1,
    nrCrtTabelB: 1,
    nrCrtTabelC: 1,
    nrCrtTabelD: 1,
    userInfo: null,
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
    salveazaCerere: function () {
        var that=this;
        //build url WS


         that.ajaxAction({
            url: '/dmsws/formular_custom/create_dummy_file/'+that.idTipDocument,
            method: 'POST',
            headers: that.mergeObjects(that.corsHeaders, {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }),
            onSuccess: function (data) {
                 if (data.result == 'OK') {

                    var idFisier = data.id;
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
                                      if(saveInfoTabel!=''){
                                          var urlTabel = '/dmsws/formular_custom/adaugaTabel/'+$(this).attr("id")+'/'+idFisier+'/';
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
                                                      alert('Cerere salvata');


                                                  } else {
                                                      Swal.fire({
                                                          icon: 'error',
                                                          html: "Ne pare rau, tabelul nu a putut fi salvat!",
                                                          focusConfirm: false,
                                                          confirmButtonText: 'Ok'
                                                      });
                                                  }
                                              },
                                              onError: function (data) {

                                                  Swal.fire({
                                                      icon: 'error',
                                                      html: "Eroare! Ne pare rau, tabelul nu a putut fi salvat!",
                                                      focusConfirm: false,
                                                      confirmButtonText: 'Ok'
                                                  });
                                                  console.log(data);
                                              }
                                          });
                                      }

                                  });


                             } else {
                                 Swal.fire({
                                     icon: 'error',
                                     html: "Ne pare rau, cererea nu a putut fi salvata!",
                                     focusConfirm: false,
                                     confirmButtonText: 'Ok'
                                 });
                             }
                         },
                         onError: function (data) {

                             Swal.fire({
                                 icon: 'error',
                                 html: "Eroare! Ne pare rau, cererea nu a putut fi salvata!",
                                 focusConfirm: false,
                                 confirmButtonText: 'Ok'
                             });
                             console.log(data);
                         }
                     });



                 } else {
                    Swal.fire({
                        icon: 'error',
                        html: "Ne pare rau, cererea nu a putut fi salvata!",
                        focusConfirm: false,
                        confirmButtonText: 'Ok'
                    });
                }
            },
            onError: function (data) {
 

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
        if(saveDataTabel!=''){
            saveDataTabel+="]";
        }

        return saveDataTabel;
    },
    mandatoryFunctions: function () {

        var that=this;
        var defer = $.Deferred();
        var PROC_NAME = "FileManager.mandatoryFunctions";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);
        //-------------- preluam WS_URL si WS_TOKEN din documentaPortal-util.js ---------------------

        FileManager.idTipDocument=new URLSearchParams(window.location.search).get("idTipDocument");

        //functii load liste lov
        //id select, id container, cod lov
        FileManager.loadListaLov('#id_parinte_user_curent', '#container_select_id_parinte_user_curent', 'LOV_OPERATOR_ECONOMIC');
        FileManager.loadListaLov('#id_perioada_contabila', '#container_select_id_perioada_contabila', 'LOV_LUNA_SQL');
        FileManager.loadListaLov('#id_sediu_social', '#container_select_id_sediu_social', 'LOV_LOCALITATE');
        FileManager.loadListaLov('#id_user_curent', '#container_select_id_user_curent', 'LOV_PERS_FIZ');

        //functii reload liste lov
        //timp reload after typing, id select, id container, cod lov

        FileManager.reloadListaLovAfterStopTyping(2000,'#id_parinte_user_curent', '#container_select_id_parinte_user_curent', 'LOV_OPERATOR_ECONOMIC');
        FileManager.reloadListaLovAfterStopTyping(2000,'#id_perioada_contabila', '#container_select_id_perioada_contabila', 'LOV_LUNA_SQL');
        FileManager.reloadListaLovAfterStopTyping(2000,'#id_sediu_social', '#container_select_id_sediu_social', 'LOV_LOCALITATE');
        FileManager.reloadListaLovAfterStopTyping(2000,'#id_user_curent', '#container_select_id_user_curent', 'LOV_PERS_FIZ');




        //-------------- initializam template-uri mustache ---------------------
        FileManager.compileAllTemplates();
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
          defer.resolve();

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
    loadListaLovTabel:function(select_class, row,lov_code){
        var that=this;
        $.ajax({
            url: '/dmsws/formular_custom/lov/get_values_by_code/'+lov_code,
            success: function (data) {
                if (data.result == 'OK') {

                    that.renderLovDataTabel(data,select_class,row);

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
    renderLovDataTabel: function (data,select_class,row) {

        var that = this;

        var html = that.renderTemplateNonAsync(that, "tmpl_lov", data);
        row.find("[name='"+select_class+"']").html(html);
        row.find("[name='"+select_class+"']").chosen({
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

    renderRowFromTemplate: function (nume_template,id_tabel) {

        var that = this;

        var data={data: {nrCrt:that.nrCrtTabelD}};
        var html = that.renderTemplateNonAsync(that, nume_template, data);
        var emptyRow = $(id_tabel).find(".empty_row_to_add")
        emptyRow.html(html);
        emptyRow.removeClass("empty_row_to_add");
        $("<tr class='empty_row_to_add'></tr>").insertAfter(emptyRow);
         that.nrCrtTabelD++;

        FileManager.loadListaLovTabel('id_platforma',emptyRow, 'LOV_OPERATOR_ECONOMIC');
        FileManager.loadListaLovTabel('id_produs',emptyRow, 'LOV_OPERATOR_ECONOMIC');
        FileManager.loadListaLovTabel('id_tip_tranzactie',emptyRow, 'LOV_OPERATOR_ECONOMIC');
        FileManager.loadListaLovTabel('id_cumparator',emptyRow, 'LOV_OPERATOR_ECONOMIC');
        FileManager.loadListaLovTabel('id_initiator',emptyRow, 'LOV_OPERATOR_ECONOMIC');


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
        this.templates['tmpl_row_tab_formulare_produse_nest'] = $('#tmpl_row_tab_formulare_produse_nest').html();


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