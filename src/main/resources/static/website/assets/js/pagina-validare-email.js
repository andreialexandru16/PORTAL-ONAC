var PAGE_NAME = "pagina-validare-email.js";

var PageManager = {

    wsUrl: null,
    wsToken: null,
    templates: {},
    listaInregistrari: {},
    nrPagina: 0,
    nrMaxPages: 4,
    numPerPage: 5,
    idRegistru: 5,
    init: function () {
debugger
        var PROC_NAME = "PageManager.init";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);
        //-------------- se apeleaza obligatoriu la initializarea paginii---------------------
        this.mandatoryFunctions();
        //this.sendFluxById();
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);


    },
    mandatoryFunctions: function () {
debugger
        var PROC_NAME = "PageManager.mandatoryFunctions";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);
        //-------------- preluam WS_URL si WS_TOKEN din documentaPortal-util.js ---------------------
        PageManager.wsUrl = $UTIL.WS_URL;
        PageManager.wsToken = $UTIL.LINK_WS_TOKEN;
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },
    sendFluxById: function () {


        $("#btn_confirma").prop('disabled','disabled');

        var PROC_NAME = "PageManager.sendFluxById";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);
        var that = this;
        var idFisier;
    var params = new URLSearchParams(window.location.href);
    if (params.has('idFisier')) {
        idFisier = params.get('idFisier');
        var etidFisier = idFisier;
        if (etidFisier.toString().includes("#")) {
            etidFisier = etidFisier.replace('#', '');
        }
     //   var url =this.wsUrl+"/portalflow/"+this.wsToken+"/sendFluxByIdFisier/"+etidFisier;


        this.ajaxAction({
            url: '/dmsws/cerericont/checkFileOnFlow?&idFisier='+etidFisier,
            headers: $UTIL.mergeObjects($UTIL.corsHeaders, {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }),

            onSuccess: function (json) {

                if (json.extendedInfo2 === '1') {
                    Swal.fire({
                        icon: "error",
                        html: "Contul a fost deja verificat iar cererea a fost trimisa catre operator.",
                        focusConfirm: false,
                        confirmButtonText: "Ok",
                          onClose: () =>{
                          "";
                         }
                    })
                    ;

                }
                else {

                    this.ajaxAction({
                        url: "/dmsws/cerericont/validareEmail/"+etidFisier,
                        method: 'POST',
                        headers: $UTIL.mergeObjects($UTIL.corsHeaders, {
                            'Accept': 'application/json',
                            'Content-Type': 'application/json'
                        }),

                        onSuccess: function (json) {

                            if (json.status !== 'OK') {
                                Swal.fire({
                                    icon: "error",
                                    html: "Ne pare rău, a intervenit o problemă.Informațiile nu au putut fi trimise.",
                                    focusConfirm: false,
                                    confirmButtonText: "Ok"
                                    /*  onClose: () = > {
                                     // window.location.reload();
                                     }*/
                                })
                                ;

                            }
                            else {

                                Swal.fire({
                                    icon: "info",
                                    html: "Informațiile au fost trimise.",
                                    focusConfirm: false,
                                    confirmButtonText: "Ok",
                                    onClose: () => {
                                    window.location.href = "";
                            }

                            })
                                ;
                            }
                        },
                        onError: function (err) {
                            ;
                            Swal.fire({
                                icon: "error",
                                html: "Ne pare rău, a intervenit o problemă.Informațiile nu au putut fi trimise.",
                                focusConfirm: false,
                                confirmButtonText: "Ok",
                                /*  onClose: () = > {
                                 //  window.location.reload();
                                 }*/


                            })
                        }
                    })
                }
            },
            onError: function (err) {
                ;
                Swal.fire({
                    icon: "error",
                    html: "Ne pare rău, a intervenit o problemă.Informațiile nu au putut fi trimise.",
                    focusConfirm: false,
                    confirmButtonText: "Ok",
                    /*  onClose: () = > {
                     //  window.location.reload();
                     }*/


                })

                $("#register").removeAttr('disabled');

            }
        })

        console.log("After Ajax;");
    }},


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


}

$(document).ready(function () {
    PageManager.init();
});



