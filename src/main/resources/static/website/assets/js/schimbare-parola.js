function CheckPassword(inputtxt)
{
    var decimal=  /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\s).{8,20}$/;
    if(inputtxt.match(decimal))
    {
        return true;
    }
    else
    {
        return false;
    }
};
$('#schimba').click(function(e) {


    /*    var currentURL = window.location.href;
     var parts = currentURL.split("?");
     var token = parts[parts.length - 1];
     token=token.replace('token=','');*/
     var response ="true";    try{        response = grecaptcha.getResponse(0);    }catch (err){    }
    if(response == "") {
        e.preventDefault();
        //reCaptcha not verified
        Swal.fire({
            icon: 'error',
            html: "Captcha gresit!",
            focusConfirm: false,
            confirmButtonText: 'Ok',
        });
    }else{
    if (CheckPassword($("#password").val())==false){
        Swal.fire({
            icon: 'info',
            html: "Parola trebuie să conțină: cel puțin 8 caractere; cel puțin un caracter numeric; cel puțin o literă mare; cel puțin o literă mică; cel puțin un caracter special (#,*,^ ...).",
            focusConfirm: false,
            confirmButtonText: 'Ok',

    });
        e.preventDefault();}
    else{
        var token=PageManager.wsToken.replaceAll("\"",'');

        var newPass = $('#password').val();
        var url = $UTIL.WS_URL + '/utilizator/' + token + '/resetPassword?newPassword='+newPass;

        /* Email */
        var errorString = "<ul class='text-left'>";
        /* Passwords */
        if ($("#password").val() !== $("#password_2").val()) {
            errorString += "<li class='text-danger'>Parolele nu se potrivesc.</li>";
        }
        errorString += "</ul>";
        if (errorString !== "<ul class='text-left'></ul>") {
            Swal.fire({
                icon: 'error',
                html: errorString,
                focusConfirm: false,
                confirmButtonText: 'Ok',
            });
        } else {
            $.ajax({
                type: 'POST',
                headers: $UTIL.mergeObjects($UTIL.corsHeaders, {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }),
                url: url,
                success: function (resultData) {
                    if(resultData.extendetInfo2=='f'){
                        Swal.fire({
                            icon: 'success',
                            html: "Parola este asemănătoare cu una dintre ultimele parole ale acestui cont!!",
                            focusConfirm: false,
                            confirmButtonText: 'Ok',})
                    }
                    else{
                    Swal.fire({
                        icon: 'success',
                        html: "Parola a  fost schimbata cu succes!",
                        focusConfirm: false,
                        confirmButtonText: 'Ok',
                        onClose: () => {
                        top.location.href = $UTIL.WORDPRESS_URL;
                }
                });
                }


                },
                error: function(err) {
                    var eroare = err.responseJSON.info;

                    Swal.fire({
                        icon: 'error',
                        html: "<p class='text-danger'><strong>"+eroare+"</strong></p>",
                        focusConfirm: false,
                        confirmButtonText: 'Ok',
                    });
                }
            });
        }
        e.preventDefault();
    }}


});
$(document).ready(function () {
    PageManager.init();
   // $("#go_home").attr("href", $UTIL.WORDPRESS_URL);
});
var PAGE_NAME = "bithat-schimbare-parola.js";

var PageManager = {
    wsUrl: null,
    wsToken: null,
    templates: {},
    listaInregistrari: {},
    nrPagina: 0,
    nrMaxPages: 4,
    numPerPage: 5,
    init: function () {
        var PROC_NAME = "PageManager.init";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);
        //-------------- se apeleaza obligatoriu la initializarea paginii---------------------

        this.mandatoryFunctions();

        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);

    },
    readInputParameters: function(){

        var defer = $.Deferred();

        var params = new URLSearchParams(window.location.search);

        if (params.has('token')){
            this.wsToken = params.get('token');
        }


        if (
            typeof this.wsToken !== 'undefined' && this.wsToken != null ){
            this.parametersLoadedOk = true;
            defer.resolve('redir');
        } else {
            defer.resolve('ok');
        }

        return defer.promise();
    },
    mandatoryFunctions: function () {
        var PROC_NAME = "PageManager.mandatoryFunctions";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);
        //-------------- preluam WS_URL si WS_TOKEN din documentaPortal-util.js ---------------------
        PageManager.wsUrl = $UTIL.WS_URL;
        var that=this;
        that.wsToken= sessionStorage.getItem('tokenParola');
        if(that.wsToken==null || typeof that.wsToken=='undefined'){
            Swal.fire({
                icon: "error",
                html: 'Ne pare rau! Identitatea nu a putut fi verificata!',
                focusConfirm: false,
                confirmButtonText: "Ok", onClose: () => {
                window.location.href = $UTIL.WORDPRESS_URL;

        }

        });
        }

        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
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


