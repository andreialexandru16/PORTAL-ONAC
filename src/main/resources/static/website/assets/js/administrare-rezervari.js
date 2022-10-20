var PAGE_NAME = "administrare-calendar.js";

var PageManager = {
    wsUrl: null,
    wsToken: null,
    templates: {},
    listaInregistrari: {},
    nrPagina: 0,
    nrMaxPages: 4,
    numPerPage: 5,
    idRegistru: 5,
    idUser: null,
    lastDate: null,
    lunaCalendar: null,
    anCalendar: null,
    daysToBusy: [],
    init: function () {

        var PROC_NAME = "PageManager.init";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);

        if(window.sessionStorage && window.sessionStorage.getItem("lastUserId")){
            PageManager.idUser=window.sessionStorage.getItem("lastUserId");
        }

        // if(window.sessionStorage && window.sessionStorage.getItem("lastCalendarDate")){
        //     PageManager.lastDate=window.sessionStorage.getItem("lastCalendarDate");
        // }

        //-------------- se apeleaza obligatoriu la initializarea paginii---------------------
        this.mandatoryFunctions();
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);


    },




    prepareListUseriAuditie: function () {

        var that = this;
        var defer = $.Deferred();

        var PROC_NAME = "PageManager.prepareListUseriAuditie";
        var url = this.wsUrl + '/programare/' + this.wsToken + '/getUseriAudienta';
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);
        //apelare serviciu web
        $.ajax({
            url: url,
            method: 'GET',
            headers: $UTIL.mergeObjects($UTIL.corsHeaders, {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }),
            success: function (result) {
                if (result.result == 'OK') {
                    var useri = result.listaUtilizator;
                    //parcurgere lista useri
                    for (var i = 0; i < useri.length; i++) {
                        //incarcare judet in lista
                        $('#user_audienta').append('<option value="' + useri[i].id + '">' + useri[i].nume + '</option>');
                    }


                    if((PageManager.idUser == null || PageManager.idUser == '') && useri[0].id != null && useri[0].id != '' && typeof(useri[0].id) != undefined) {

                        PageManager.idUser = useri[0].id;
                    }


                    // $('#calendar').val(PageManager.lastDate);
                    $('#user_audienta').val(PageManager.idUser).trigger("change");

                    defer.resolve();

                }

            },
            error: function (err) {
                console.log(err);
            }

        });

        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
        return defer.promise();



    },

    generareCalendar: function () {
        var PROC_NAME = "PageManager.afiseazaRezultateCautare";

        //construim obiectul cu campurile necesare
        var object = PageManager.buildObject();
        var jReq = JSON.stringify(object);

        //apelam ws de generare calendar
        PageManager.callWs(jReq);

        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },
    buildObjectForInit: function (idUser) {

        var that = this;
        var PROC_NAME = "PageManager.buildObjectForInit";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);

      //  var idUser = $("#user_audienta").val();
        // var calendar = $("#calendar").val();


        var calendar = {
            idUser:idUser

        };

        return calendar;
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);


    },

    onChangeDropdown: function () {

        var PROC_NAME = "PageManager.onChangeDropdown";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);

        var idUser = $("#user_audienta").val();
        PageManager.idUser = idUser;

        PageManager.initCalendar(idUser);

        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },



    initCalendar: function (idUser) {

        var PROC_NAME = "PageManager.initCalendar";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);

        //construim obiectul cu campurile necesare
        var object = PageManager.buildObjectForInit(idUser);
        var jReq = JSON.stringify(object);

        //apelam ws de generare calendar
        PageManager.buildPagination(jReq).then(function () {
            PageManager.callGetCalendar(jReq);
        });



        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },



    callUpdateWs: function (object) {

        var that = this;
        var PROC_NAME = "PageManager.callUpdateWs";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);

        var url = this.wsUrl + '/programare/' + this.wsToken + '/updateCalendar';
        var jReq = JSON.stringify(object);


        console.log("Ajax Call Start:" + url);

        $.ajax({
            url: url,
            method: 'POST',
            data: jReq,
            contentType: 'application/json',
            accept: 'application/json',
            success: function (data) {
                if (data.result == 'OK') {
                    window.location.reload();
                }


            },
            error: function (err) {
                console.log(err);
            }
        });
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },

    aprobareRezervare:function(id){

        var that = this;
        var PROC_NAME = "PageManager.aprobaRezervare";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);
        var url = this.wsUrl + '/programare/' + this.wsToken + '/aprobareRezervare/'+id;
        $.ajax({
            url: url,
            type: 'POST',
            headers: $UTIL.mergeObjects($UTIL.corsHeaders, {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }),
            success: function (result) {
                if (result.result == 'OK') {
                    Swal.fire({
                        icon: 'success',
                        html: "Rezervare aprobata.",
                        focusConfirm: false,
                        confirmButtonText: 'Ok',
                    });
                    PageManager.initCalendar(PageManager.idUser);
                }},
            error: function (err) {
                Swal.fire({
                    icon: 'error',
                    html: "Nu s-a putut aproba rezervarea.",
                    focusConfirm: false,
                    confirmButtonText: 'Ok',
                });
                PageManager.initCalendar(PageManager.idUser);
                console.log(err);
            }
        });
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);

    },

    respingeRezervare:function(id){

        var that = this;
        var PROC_NAME = "PageManager.respingeRezervare";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);
        var url = this.wsUrl + '/programare/' + this.wsToken + '/respingereRezervare/'+id;
        $.ajax({
            url: url,
            method: 'POST',
            headers: $UTIL.mergeObjects($UTIL.corsHeaders, {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }),
            success: function (result) {
                if (result.result == 'OK') {
                    Swal.fire({
                        icon: 'success',
                        html: "Rezervare respinsa.",
                        focusConfirm: false,
                        confirmButtonText: 'Ok',
                    });
                    PageManager.initCalendar(PageManager.idUser);
                }},
            error: function (err) {
                Swal.fire({
                    icon: 'error',
                    html: "Nu s-a putut respinge rezervarea.",
                    focusConfirm: false,
                    confirmButtonText: 'Ok',
                });
                PageManager.initCalendar(PageManager.idUser);
                console.log(err);
            }
        });
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);

    },



    callGetCalendar: function (jReq) {

        var that = this;
        var PROC_NAME = "PageManager.callGetCalendar";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);

        var url = this.wsUrl + '/programare/' + this.wsToken + '/getRezervari';
        if (PageManager.nrPagina == null) {
            PageManager.nrPagina = 0;
        }
        var indexStart = PageManager.nrPagina * PageManager.numPerPage + 1;
        var indexEnd = (PageManager.nrPagina + 1) * PageManager.numPerPage + 1;
        url += "?&indexStart=" + indexStart + '&indexEnd=' + indexEnd;


        console.log("Ajax Call Start:" + url);

        $.ajax({
            url: url,
            method: 'POST',
            data: jReq,
            headers: $UTIL.mergeObjects($UTIL.corsHeaders, {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }),
            success: function (result) {
                if (result.result == 'OK') {
                    that.listaInregistrari = result.audientaList;

                    that.renderTemplate('tmpl_inregistrari_list', {data: result.audientaList}).then(function (html) {
                        var tblHolder = $('.table_inregistrari');
                        tblHolder.html('');
                        tblHolder.html(html);
                    }, function () {
                    that.alert('Unable to render template', 'ERROR');
                });

            }},
            error: function (err) {
                console.log(err);
            }
        });
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },



    mandatoryFunctions: function () {

        var PROC_NAME = "PageManager.mandatoryFunctions";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);
        //-------------- preluam WS_URL si WS_TOKEN din documentaPortal-util.js ---------------------
        PageManager.wsUrl = $UTIL.WS_URL;
        PageManager.wsToken = $UTIL.LINK_WS_TOKEN;

        //--------initializam listele de valori-----------
        PageManager.compileAllTemplates();
        PageManager.prepareListUseriAuditie().then(function(){
            PageManager.initCalendar(PageManager.idUser);

        });

        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);

    },


    buildPagination: function (jReq) {

        var PROC_NAME = "PageManager.buildPagination";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);


        var that = this;
        var defer = $.Deferred();
        var nrTotalInregistrari = 0;
        var url =this.wsUrl+"/programare/"+this.wsToken+"/getRezervariCount";
        $.ajax({
            url: url,
            data:jReq,
            method: 'POST',
            headers: $UTIL.mergeObjects($UTIL.corsHeaders, {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }),
            success: function (result) {
                if (result && result.info != null) {
                    nrTotalInregistrari = parseInt(result.info);
                }
                that.buildListOfPages(nrTotalInregistrari,jReq);
                defer.resolve();


            }
        });


        return defer.promise();
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);

    },

    buildListOfPages: function (nrTotalInregistrari,jReq) {
        var PROC_NAME = "PageManager.buildListOfPages";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);

        var that = this;
        var pagContainerUl = $(".pagination_ul_container ul");
        pagContainerUl.empty();

        var nrTotalPages = parseInt(nrTotalInregistrari / PageManager.numPerPage) + 1;
        $(".pagination_ul_container.info p").html("Pagina " + (parseInt(PageManager.nrPagina) + 1) + " din " + nrTotalPages);

        var midShow = PageManager.nrMaxPages / 2;
        var nrPageStart = PageManager.nrPagina - midShow;
        var nrMaxPagesShow = PageManager.nrPagina + midShow;
        var endModifiedByStart = false;
        if (PageManager.nrPagina - midShow < 0) {
            nrPageStart = 0;
            nrMaxPagesShow = PageManager.nrMaxPages;
            endModifiedByStart = true;
        }
        if (nrMaxPagesShow > nrTotalPages) {
            nrMaxPagesShow = nrTotalPages;
            if (!endModifiedByStart) {
                nrPageStart = nrTotalPages - PageManager.nrMaxPages;
                if (nrPageStart < 0) {
                    nrPageStart = 0;
                }
            }
        }
        for (var i = nrPageStart; i < nrMaxPagesShow; i++) {
            pagContainerUl.append(that.buildPager(i, i.toString() == PageManager.nrPagina ? "active" : "", "",jReq));
        }
        if (PageManager.nrPagina == 0) {
            pagContainerUl.append(that.buildPager(PageManager.nrPagina, "disabled", "fas fa-chevron-left",jReq));
            pagContainerUl.append(that.buildPager(PageManager.nrPagina, "page-item", "fas fa-chevron-right",jReq));
        }
        else if (PageManager.nrPagina < nrTotalPages - 1) {
            pagContainerUl.append(that.buildPager(PageManager.nrPagina, "page-item", "fas fa-chevron-left",jReq));
            pagContainerUl.append(that.buildPager(PageManager.nrPagina, "page-item", "fas fa-chevron-right",jReq));
        }
        else if (PageManager.nrPagina = nrTotalPages - 1) {
            pagContainerUl.append(that.buildPager(PageManager.nrPagina, "page-item", "fas fa-chevron-left",jReq));
            pagContainerUl.append(that.buildPager(PageManager.nrPagina, "disabled", "fas fa-chevron-right",jReq));
        }
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },

    buildPager: function (page, classType, icon,jReq) {
        var PROC_NAME = "PageManager.buildPager";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);

        var that = this;
        var paginaToGo = 0;
        var item = $("<li></li>");



        if (icon == 'prev' || icon=='fas fa-chevron-left') {
            paginaToGo = parseInt(page) - 1;
        }

        else if (icon == 'next' || icon=='fas fa-chevron-right') {
            paginaToGo = parseInt(page) + 1;
        }

        else {
            paginaToGo = page;
        }

        page++;
        if (classType != null && classType != '' && icon != null && icon != '') {

            item.addClass(classType);
            if (classType == 'disabled') {
                item.append("<a class='page-link " + classType + "' href='#' ><i class='" + icon + "'></i></a>");

            }
            else {
                item.append("<a class='page-link " + classType + "' href='#' onclick='PageManager.setPageNumberAndGo(" + paginaToGo + ","+jReq+")'><i class='" + icon + "'></i></a>");

            }

        }
        else if (classType != null && classType != '') {
            item.addClass('page-item ');
            item.addClass(classType);
            item.append("<a class='page-link " + classType + "' href='#' >"+page+"</a>");

        }
        else {
            item.addClass('page-item ');
            item.append("<a class='page-link'  href='#' onclick='PageManager.setPageNumberAndGo(" + paginaToGo + ","+jReq+")'>" + page + "</a>");
            $(item).attr('id', 'li_page_' + paginaToGo);
        }

        return item;
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);

    },

    setPageNumberAndGo: function (page,jReq) {
        var PROC_NAME = "PageManager.setPageNumberAndGo";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);
        //setam numarul paginii selectate
        PageManager.nrPagina = page;

        //apelam functia de afisare rezultate
        PageManager.initCalendar(jReq.idUser);

        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);

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
    },





};


$(document).ready(function () {
    PageManager.init();
});