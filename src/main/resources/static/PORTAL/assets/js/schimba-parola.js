var PAGE_NAME = "page-model.js";

var PageManager = {
    wsUrl: null,
    wsToken: null,
    templates: {},
    listaInregistrari: {},
    nrPagina: 0,
    nrMaxPages: 4,
    numPerPage: 5,
    wsAndUserInfo: {},
    init: function () {
        var PROC_NAME = "PageManager.init";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);
        //-------------- se apeleaza obligatoriu la initializarea paginii---------------------
        this.mandatoryFunctions();

        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);

    },
    mandatoryFunctions: function () {
        var PROC_NAME = "PageManager.mandatoryFunctions";
        var that=this;
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);
        //-------------- preluam WS_URL si WS_TOKEN din documentaPortal-util.js ---------------------
        // luam info dmsws si user
        that.getWsAndUserInfo();

        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
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
    goBack: function () {
        window.history.back();
    },

    reset:function(){
        var that = this;
        var url = that.wsAndUserInfo.wsUrl  + '/utilizator/'+that.wsAndUserInfo.userToken.token+'/change_password2/';

			/* Email */
            var errorString = "<ul class='text-left'>";
		/* Passwords */

        var parola_noua=$('#parola_noua').val();
        var confirma_parola=$('#confirma_parola').val();
        var parola_veche=$('#parola_veche').val();
        if(parola_noua==null || parola_noua=='' || confirma_parola==null || confirma_parola=='' || parola_veche==null || parola_veche==''){
            Swal.fire({
                icon: 'error',
                html: "Completati toate campurile!",
                focusConfirm: false,
                confirmButtonText: 'Ok',
            });
            e.preventDefault();
        } else
        if (parola_noua !== confirma_parola) {
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

                var data = {
                    oldPassword:parola_veche,
                    newPassword:parola_noua,
                    newPassword2:confirma_parola}

				;
                var jReq = JSON.stringify(data);
                $.ajax({
                        url: url,
					type: 'post',
                        data: jReq,
                        headers: ObjTools.mergeObjects(AjaxTools.corsHeaders, {
                            'Accept': 'application/json',
                            'Content-Type': 'application/json'
                        }),
                        success: function (data) {

                        if (data.result == 'OK') {
                            Swal.fire({
                                icon: 'success',
                                html: "Parola a fost schimbata.",
                                focusConfirm: false,
                                confirmButtonText: 'Ok'
                            });
                            //      event.preventDefault()
                        }else{
                            Swal.fire({
                                icon: 'error',
                                html: "<p class='text-danger'><strong>Parola veche nu coincide</strong></p>",
                                focusConfirm: false,
                                confirmButtonText: 'Ok',
                            });
						}

                    },
                    error:function(err){
                        Swal.fire({
                            icon: 'error',
                            html: "<p class='text-danger'><strong>Serviciul nu aputut fi apelat</strong></p>",
                            focusConfirm: false,
                            confirmButtonText: 'Ok',
                        });
                        //  event.preventDefault()
                    }
                });

            }
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

    //afisare captcha - cu cheie preluata din application.properites: google.recaptcha.secretkey
    $UTIL.loadCaptchaKey().then(function (data) {
        if(data!=null && data!=''){
            $("#container_captcha").html( '  <div class="g-recaptcha" data-sitekey="'+data+'"></div>');

        }

    });

});






var validateEmail = function (email)  {
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(String(email).toLowerCase());
}

