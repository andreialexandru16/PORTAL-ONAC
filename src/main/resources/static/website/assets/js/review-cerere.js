
var PAGE_NAME = "review-cerere.js";

var PageManager = {
    wsUrl: null,
    wsToken: null,
    idFisier:null,
    templates: {},
    listaInregistrari: {},
    nrPagina: 0,
    nrMaxPages: 4,
    numPerPage: 5,
    idRegistru: 5,
    init: function () {

        var PROC_NAME = "PageManager.init";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);
        //-------------- se apeleaza obligatoriu la initializarea paginii---------------------

        this.mandatoryFunctions().then( PageManager.afiseazaInregistrari());
        //apelam reconstructie paginare & afisare rezultate
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);


    },

    salveazaNote:function(id){
        var PROC_NAME = "PageManager.saveazaNote";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);
        var elementNota1 = 'nota1_'+id;
        var elementNota2 = 'nota2_'+id;
        var nota1=document.getElementById(elementNota1).value;
        var nota2=document.getElementById(elementNota2).value;
        var reviewCerere={
            nota1:nota1,
            nota2:nota2,
            idFisierAnexat:id,
            id_document_p:PageManager.idFisier
        };

        var url = this.wsUrl + "/document/"+ this.wsToken +"/addReview";
        var jReq=JSON.stringify(reviewCerere);

        console.log("Ajax Call Start:" + url);

        $.ajax({
            url: url,
            method:'POST',
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            data:jReq,
            success: function (data) {
                if(data.result=='OK'){
                Swal.fire({
                    icon: 'info',
                    html: "Note adaugate cu success!",
                    focusConfirm: false,
                    confirmButtonText: 'Ok'
                });
                }else {
                    Swal.fire({
                        icon: 'error',
                        html: "Ne pare rau, notele nu au fost adaugate!",
                        focusConfirm: false,
                        confirmButtonText: 'Ok'
                    });
                }


                },
            error: function (err) {
                Swal.fire({
                    icon: 'error',
                    html: "Eroare! Notele nu au fost adaugate!",
                    focusConfirm: false,
                    confirmButtonText: 'Ok'
                });
                console.log(err);
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
    getListaInregistrari: function () {

        var that = this;
        var PROC_NAME = "PageManager.getListaInregistrari";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);

        var url = this.wsUrl + "/document/"+ this.wsToken +"/getDocObligatorii?&parentFileId=" + that.idFisier;


        console.log("Ajax Call Start:" + url);

        $.ajax({
            url: url,
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                if (data.result == 'OK') {
                    that.listaInregistrari = data.docObligatoriuList;

                    //apelam render template pentru a popula tabelul cu rezultatele obtinute
                    that.renderTemplate('tmpl_inregistrari_list', {data: data.docObligatoriuList}).then(function (html) {
                        var tblHolder = $('.table_inregistrari');
                        tblHolder.html('');
                        tblHolder.html(html);

                    }, function () {
                        that.alert('Unable to render template', 'ERROR');
                    });
                }

            }
        });
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },
    generareScrisoare: function(){
        var PROC_NAME = "PageManager.generareScrisoare";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);
        var url = this.wsUrl + "/document/"+ this.wsToken +"/generareScrisoareLipsuri";
        if(this.idFisier==null || this.idFisier==undefined){
            Swal.fire({
                icon: 'error',
                html: "Ne pare rau, fisierul nu a fost regasit!",
                focusConfirm: false,
                confirmButtonText: 'Ok'
            });
        }else{
        var jReq=JSON.stringify(PageManager.idFisier);

        console.log("Ajax Call Start:" + url);

        $.ajax({
            url: url,
            method:'POST',
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            data:PageManager.idFisier,
            success: function (data) {
                if(data.result=='OK'){
                    Swal.fire({
                        icon: 'info',
                        html: "Note adaugate cu success!",
                        focusConfirm: false,
                        confirmButtonText: 'Ok'
                    });
                }else {
                    Swal.fire({
                        icon: 'error',
                        html: "Ne pare rau, notele nu au fost adaugate!",
                        focusConfirm: false,
                        confirmButtonText: 'Ok'
                    });
                }


            },
            error: function (err) {
                Swal.fire({
                    icon: 'error',
                    html: "Eroare! Notele nu au fost adaugate!",
                    focusConfirm: false,
                    confirmButtonText: 'Ok'
                });
                console.log(err);
            }


        });
        }
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },


    mandatoryFunctions: function () {

        var defer = $.Deferred();
        var PROC_NAME = "PageManager.mandatoryFunctions";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);
        //-------------- preluam WS_URL si WS_TOKEN din documentaPortal-util.js ---------------------
        PageManager.wsUrl = $UTIL.WS_URL;
        PageManager.wsToken = $UTIL.LINK_WS_TOKEN;
       /* PageManager.wsUrl ="http://192.168.12.104:8080/DMSWS/api/v1";
        PageManager.wsToken = "c38645c3aae2809e7411197ac3924c2278c38ac2bcc3afc2bfc5bdc398c2bc36540030e280ba7854c5a0cb867f042cc2a7";
      */  if (typeof PageManager.wsToken =='undefined'){
            $.ajax({
                type: 'GET',
                url: '/dmsws/utilizator/getAnonymousToken',
                success: function (data) {
                    $UTIL.LINK_WS_TOKEN=data;
                    PageManager.wsToken=data;
                    defer.resolve();
                }
            });
        }
        if (typeof PageManager.wsUrl =='undefined'){
            $.ajax({
                type: 'GET',
                url: '/dmsws/utilizator/getWsUrl',
                success: function (data) {
                    $UTIL.WS_URL=data;
                    PageManager.wsUrl=data;
                    defer.resolve();
                }
            });
        }
        PageManager.idFisier=new URLSearchParams(window.location.search).get("idFisier");

        //-------------- initializam template-uri mustache ---------------------
        PageManager.compileAllTemplates();
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);

        return defer.promise();

    },
    compileAllTemplates: function () {
        this.templates['tmpl_inregistrari_list'] = $('#tmpl_inregistrari_list').html();

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
});




