
$(document).ready(function () {
    PageManager.init();
});
var PAGE_NAME = "go-get.js";

var PageManager = {
    wsUrl: null,
    wsToken: null,
    idUser: null,
    targetPage: null,
    checkAuth: null,
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
        var href = window.location.search;
        href = window.location.search.replaceAll("&amp;","&");
        var params = new URLSearchParams(href);


        if (params.has('token')){
            this.wsToken = params.get('token');
        }

        if (params.has('targetPage')){
            this.targetPage = params.get('targetPage');
        }
        if (params.has('auth')){
            this.checkAuth = params.get('auth');
        }
        if (   typeof this.wsToken !== 'undefined' && this.wsToken != null ||
            typeof this.targetPage !== 'undefined' && this.targetPage != null
        ){
            this.parametersLoadedOk = true;
            defer.resolve('redir');
        }

        return defer.promise();
    },
    mandatoryFunctions: function () {
        var PROC_NAME = "PageManager.mandatoryFunctions";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);
        //-------------- preluam WS_URL si WS_TOKEN din documentaPortal-util.js ---------------------
        PageManager.wsUrl = $UTIL.WS_URL;
        var that=this;


        Swal.fire({
            title: "Verificãm identitatea...",
            text: "Vã rugãm aşteptaţi",
            icon: "http://bestanimations.com/Science/Gears/loadinggears/loading-gears-animation-10.gif#.XeTgww__Q9M.link",
            showConfirmButton: true,
            allowOutsideClick: false

        });


        this.readInputParameters().then(function(res){

            if(typeof $UTIL.PORTAL_URL =='undefined'){
                $.ajax({
                    type: 'GET',
                    url: '/dmsws/utilizator/getPortalUrl',
                    success: function (data) {
                        $UTIL.PORTAL_URL=data;
                        if (that.isNotUndef(res) && that.isNotEmpty(res) && res === "redir") {
                            if(that.isNotUndef(that.checkAuth) && that.isNotEmpty(that.checkAuth) && that.checkAuth === "true"){
                                //CHECK IF LOGGED IN
                                if($UTIL.userIsLoggedIn()){
                                    //-------------- se apeleaza obligatoriu la initializarea paginii---------------------
                                    sessionStorage.setItem('tokenParola', JSON.stringify(that.wsToken));

                                    if(that.targetPage!=null && that.targetPage.includes('schimbare-parola')  ){
                                        window.location.href = that.targetPage;

                                    } else if(that.targetPage!=null && that.targetPage.includes('setare-parola-noua') ){
                                        window.location.href = $UTIL.WORDPRESS_URL + "/"+that.targetPage;

                                    }
                                    else{
                                        if(!that.targetPage.includes('?')){
                                            that.targetPage+='?';
                                        }
                                        window.location.href = $UTIL.PORTAL_URL+that.targetPage+'&idUser='+$UTIL.loadUserDataFromSession().userId+'&token='+$UTIL.loadUserDataFromSession().token;

                                    }
                                }
                                else{
                                    Swal.fire({
                                            icon: "info",
                                            html: "Vă rugăm să vă autentificați.",
                                            focusConfirm: false,
                                            confirmButtonText: "Ok",
                                            onClose: () => {
                                            window.open($UTIL.WORDPRESS_URL+ '/autentificare', '_self');

                                }
                                });
                                }
                            }
                            else{
                                sessionStorage.setItem('tokenParola', JSON.stringify(that.wsToken));

                                if(that.targetPage!=null && that.targetPage.includes('schimbare-parola') ){
                                    window.location.href = $UTIL.PORTAL_URL+'PORTAL/'+that.targetPage;

                                } else if(that.targetPage!=null && that.targetPage.includes('setare-parola-noua') ){
                                    window.location.href = $UTIL.WORDPRESS_URL + "/"+that.targetPage;

                                }
                                else{
                                    window.location.href = $UTIL.PORTAL_URL+that.targetPage+'&idUser='+$UTIL.loadUserDataFromSession().userId+'&token='+$UTIL.loadUserDataFromSession().token;

                                }
                            }


                            //'schimbare-parola.html';

                        }
                    }
                });
            }
            else if (that.isNotUndef(res) && that.isNotEmpty(res) && res === "redir") {
                if(that.isNotUndef(that.checkAuth) && that.isNotEmpty(that.checkAuth) && that.checkAuth === "true"){
                    //CHECK IF LOGGED IN
                    if($UTIL.userIsLoggedIn()){
                        //-------------- se apeleaza obligatoriu la initializarea paginii---------------------
                        sessionStorage.setItem('tokenParola', JSON.stringify(that.wsToken));

                        if(that.targetPage!=null && that.targetPage.includes('schimbare-parola')){
                            window.location.href = that.targetPage;

                        }
                        else{
                            if(!that.targetPage.includes('?')){
                                that.targetPage+='?';
                            }
                            window.location.href = $UTIL.PORTAL_URL+that.targetPage+'&idUser='+$UTIL.loadUserDataFromSession().userId+'&token='+$UTIL.loadUserDataFromSession().token;

                        }
                    }
                    else{
                        Swal.fire({
                                icon: "info",
                                html: "Vă rugăm să vă autentificați.",
                                focusConfirm: false,
                                confirmButtonText: "Ok",
                                onClose: () => {
                                window.open($UTIL.WORDPRESS_URL+ '/autentificare', '_self');

                    }
                    });
                    }
                }
                else{
                    sessionStorage.setItem('tokenParola', JSON.stringify(that.wsToken));

                    if(that.targetPage.includes('schimbare-parola')){
                        window.location.href = $UTIL.PORTAL_URL+'PORTAL/'+that.targetPage;

                    }
                    else{
                        window.location.href = $UTIL.PORTAL_URL+that.targetPage+'&idUser='+$UTIL.loadUserDataFromSession().userId+'&token='+$UTIL.loadUserDataFromSession().token;

                    }
                }


                    //'schimbare-parola.html';

            }
        });

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


