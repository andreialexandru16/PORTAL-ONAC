
$(document).ready(function () {
    PageManager.init();
});
var PAGE_NAME = "utilizator.js";

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
    userFirstName: null,
    userName: null,
    init: function () {
        var PROC_NAME = "PageManager.init";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);
        //-------------- se apeleaza obligatoriu la initializarea paginii---------------------
        this.mandatoryFunctions();

        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);

    },

    mandatoryFunctions: function () {
        var PROC_NAME = "PageManager.mandatoryFunctions";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);

        //-------------- preluam WS_URL si WS_TOKEN din documentaPortal-util.js ---------------------
        PageManager.wsUrl = $UTIL.WS_URL;
        var that=this;

        that.compileAllTemplates();

        $UTIL.loadUserDataFromSecurityUtils().then(function (data) {

            if($UTIL.USER_FIRST_NAME!=null && $UTIL.USERNAME!='nouser'){
                $('#iframe_utilizator').html('Bine ai venit, <span>'+$UTIL.USER_FIRST_NAME+'</span> !  ');
                //$('#iframe_inreg').css("display","none");
                $('#iframe_logout').removeClass("hide_logout");
                $('#iframe_contul_meu').removeClass("hide_contul_meu");
                $('#iframe_utilizator').removeClass("hide_utilizator");
                PageManager.loadListaParinti();
            }else
            {

                sessionStorage.removeItem('topMenuBar');
                $('#iframe_inreg').removeClass("hide_inreg");
                //$('#iframe_logout').css("display","none");
                //$('#iframe_contul_meu').css("display","none");
                //$('#iframe_utilizator').css("display", "none");
            }

       //     defer.resolve();
        });



        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },

    loadListaParinti: function(){

        var that=this;
        var url = '/dmsws/utilizator/getListaParinti';
        $.ajax({
            url:url,
            success: function (data) {

                if (data.status == 'OK') {

                    if(data.persoanaFizicaJuridicaList!=null && data.persoanaFizicaJuridicaList.length>=2){
                        $('#container_select_operator').removeClass("hide_utilizator");

                        that.renderTertRaportareDataSearch(data);

                    }
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
    actualizareDateContCurent: function(){

        var that=this;
        var url = '/dmsws/utilizator/resetDateContCurent';
        var idTert= $("#select_tert").val();
        if(idTert!=null && typeof idTert !='undefined'){
           url+='/'+idTert;

        }
        $.ajax({
            url:url,
            success: function (data) {

                if (data.result == 'OK') {
                    $UTIL.loadUserDataFromSecurityUtils().then(function (data) {

                        if($UTIL.USER_FIRST_NAME!=null && $UTIL.USERNAME!='nouser'){
                            $('#iframe_utilizator').html('Bine ai venit, <span>'+$UTIL.USER_FIRST_NAME+'</span> !  ');
                            $('#iframe_logout').removeClass("hide_logout");
                            $('#iframe_contul_meu').removeClass("hide_contul_meu");
                            $('#iframe_utilizator').removeClass("hide_utilizator");
                            $('#container_select_operator').removeClass("hide_utilizator");
                        }else
                        {

                            sessionStorage.removeItem('topMenuBar');
                            $('#iframe_inreg').removeClass("hide_inreg");
                        }

                    });
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
    renderTertRaportareDataSearch: function (data) {

        var that = this;

        var html = that.renderTemplateNonAsync(that, "tmpl_tert", data);
        $("#select_tert").html(html);
        $("#select_tert").trigger("chosen:updated");
        $("#select_tert").chosen({
            no_results_text: "Vom reîncărca lista conform textului căutat - momentan nu a fost găsit : "
        });
        $('#resize_iframe', window.parent.document).trigger('click');
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
    objectToArray: function (object) {
        var array = [];

        for (var property in object) {
            if (object.hasOwnProperty(property)) {
                array.push({key: property, value: object[property]});
            }
        }

        return array;
    },
    logout:function(){

        $UTIL.deleteUserDataInSession();

        $.ajax({

            type: 'GET',
            url: '/dmsws/utilizator/getLogoutUrl',
            success: function (data) {
                if(data!=null && data!='') {
                    window.top.location.href=data;

                }
            }

        });
        PageManager.mandatoryFunctions();
    },
    inregistrare:function(){
        $.ajax({
            type: 'GET',
            url: '/dmsws/utilizator/getCustomInregistrareUrl',
            success: function (data) {
                if(data!=null && data!='') {
                    window.top.location.href=data;
                } else {
                    PageManager.goToPage('/inregistrare');
                }
            }

        });
    },
    goToPage:function(page){

        window.top.location.href=$UTIL.WORDPRESS_URL+ page;
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

    compileAllTemplates: function () {
        this.templates['tmpl_tert'] = $('#tmpl_tert').html();

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
    }


};


