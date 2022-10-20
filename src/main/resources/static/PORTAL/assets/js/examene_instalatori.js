
var PAGE_NAME = "registru_decizii.js";

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
    idRegistru: 3302,
    idRomaniaLovTari: 642,
    qu: null,
    params: [],
    columns: [],
    codTipAutorizatie: "LOV_GRAD_GN_LP",
    codStatusDosar : "LOV_STATUS_INSTALATORI_GN_LP",
    codSesiune: "LOV_SESIUNE_GN_LP",
    codCentru: "LOV_CENTRU_GN_LP",
    exportXlsx: function () {
        $UTIL.waitForLoading();

        var PROC_NAME = "PageManager.exportXlsx";
        debugger;
        var baseUrl = this.wsUrl + "/anre/" + this.wsToken + "/exportXlsx";
        var query = this.qu;
        var params = this.params;
        var columns = this.columns;

        $UTIL.exportXlsxByVaadin("export.xlsx",baseUrl,query,params,columns);

        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },
    initLovSocietate: function () {

        var PROC_NAME = "PageManager.initLovSocietate";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);


        var that = this;
        var defer = $.Deferred();

        var url ="/dmsws/nomenclator/operatori_economici";

        $.ajax({
            url: url,
            success: function (result) {
                if (result && result.lov != null) {
                    var lov = result.lov;
                    $('#societate').append(that.getOptionListItemHtml('-99999999', 'Selecteaza societate'));
                    for(i=0;i<lov.length;i++){
                        $('#societate').append(that.getOptionListItemHtml(lov[i].id, lov[i].valoare));
                    }
                }

                defer.resolve();

            }
        });


        return defer.promise();
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);

    },
    loadLovTipAutorizatie: function() {
        var that = this;
        var url = "/dmsws/lov/values_by_code/" + PageManager.codTipAutorizatie;
        $.ajax({
            url: url,
            success: function (data) {
                if (data.result == 'OK') {
                    $('#grad').html('');
                    if (data.lov != null && data.lov.length != 0) {
                        var lov = data.lov;
                        $('#grad').append(that.getOptionListItemHtml('0', 'Selecteaza tip autorizatie'));
                        for(i=0;i<lov.length;i++){
                            $('#grad').append(that.getOptionListItemHtml(lov[i].id, lov[i].valoare));
                        }
                    }
                }
            }
        });
    },
    getOptionListItemHtml: function (id, denumire){
        return '<option value="' + id + '">' + denumire + '</option>';
    },
    loadLovStatusDosar: function() {
        var that = this;
        var url = "/dmsws/lov/values_by_code/" + PageManager.codStatusDosar;
        $.ajax({
            url: url,
            success: function (data) {
                if (data.result == 'OK') {
                    $('#stare_dosar').html('');
                    if (data.lov != null && data.lov.length != 0) {
                        var lov = data.lov;
                        $('#stare_dosar').append(that.getOptionListItemHtml('0', 'Selecteaza status dosar'));
                        for(i=0;i<lov.length;i++){
                            $('#stare_dosar').append(that.getOptionListItemHtml(lov[i].id, lov[i].valoare));
                        }
                    }
                }
            }
        });
    },
    loadLovSesiune: function() {
        var that = this;
        var url = "/dmsws/lov/values_by_code/" + PageManager.codSesiune;
        $.ajax({
            url: url,
            success: function (data) {
                if (data.result == 'OK') {
                    $('#sesiune').html('');
                    if (data.lov != null && data.lov.length != 0) {
                        var lov = data.lov;
                        $('#sesiune').append(that.getOptionListItemHtml('0', 'Selecteaza sesiune'));
                        for(i=0;i<lov.length;i++){
                            $('#sesiune').append(that.getOptionListItemHtml(lov[i].id, lov[i].valoare));
                        }
                    }
                }
            }
        });
    },
    loadLovCentru: function() {
        var that = this;
        var url = "/dmsws/lov/values_by_code/" + PageManager.codCentru;
        $.ajax({
            url: url,
            success: function (data) {
                if (data.result == 'OK') {
                    $('#centru').html('');
                    if (data.lov != null && data.lov.length != 0) {
                        var lov = data.lov;
                        $('#centru').append(that.getOptionListItemHtml('0', 'Selecteaza centru'));
                        for(i=0;i<lov.length;i++){
                            $('#centru').append(that.getOptionListItemHtml(lov[i].id, lov[i].valoare));
                        }
                    }
                }
            }
        });
    },
    initLovJudet: function () {
        var PROC_NAME = "PageManager.initLovJudet";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);


        var that = this;
        var defer = $.Deferred();

        var url ="/dmsws/nomenclator/regions/"+that.idRomaniaLovTari;

        $.ajax({
            url: url,
            success: function (result) {
                if (result && result.lov != null) {
                    var lov = result.lov;
                    $('#judet').append(that.getOptionListItemHtml('0', 'Selecteaza judet'));
                    for(i=0;i<lov.length;i++){
                        $('#judet').append(that.getOptionListItemHtml(lov[i].id, lov[i].valoare));
                    }
                }

                defer.resolve();

            }
        });


        return defer.promise();
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);

    },

    init: function () {

        var PROC_NAME = "PageManager.init";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);
        //-------------- se apeleaza obligatoriu la initializarea paginii---------------------
        this.mandatoryFunctions();
        this.loadLovTipAutorizatie();
        this.initLovJudet();
        this.initLovSocietate();
        this.loadLovCentru();
        this.loadLovSesiune();
        this.loadLovStatusDosar();
        //apelam reconstructie paginare & afisare rezultate
        PageManager.afiseazaInregistrari();
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);


    },


    afiseazaInregistrari: function () {
        var PROC_NAME = "PageManager.afiseazaInregistrari";


        //apelam reconstructie paginare & afisare rezultate
        PageManager.buildPagination().then(function () {
            PageManager.getListaInregistrari();
        });
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },
    getInfoFilters: function(){

        var nume_complet=$("#nume_complet").val();
        var grad=$("#grad").val();
        var localitate=$("#localitate").val();
        var judet=$("#judet").val();
        var societate=$("#societate").val();
        var stare_dosar=$("#stare_dosar").val();
        var sesiune=$("#sesiune").val();
        var centru=$("#centru").val();
        var legitimatie={
            nume_complet:nume_complet,
            grad:grad,
            localitate:localitate,
            judet:judet,
            societate:societate,
            stare_dosar:stare_dosar,
            sesiune:sesiune,
            centru:centru
        }
        return JSON.stringify(legitimatie);
    },
    getListaInregistrari: function (jReq) {
        $UTIL.waitForLoading();
        var that = this;
        var PROC_NAME = "PageManager.getListaInregistrari";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);

        var url ="/dmsws/anre/getExameneInstalatori";

        if (PageManager.nrPagina == null) {
            PageManager.nrPagina = 0;
        }


        //calcul index start si idnex end in functie de nr Pagina si nr item uri afisate per pagina
        var indexStart = PageManager.nrPagina * PageManager.numPerPage + 1;
        var indexEnd = (PageManager.nrPagina + 1) * PageManager.numPerPage + 1;
        url += "?&indexStart=" + indexStart + '&indexEnd=' + indexEnd;

        var legitimatie=that.getInfoFilters();
        console.log("Ajax Call Start:" + url);

        $.ajax({
            url: url,
            contentType: "application/json",
            type:'POST',
            data: legitimatie,
            success: function (data) {
                if (data.result == 'OK') {
                    that.listaInregistrari = data.legitimatieList;
                    that.qu = data.query;
                    that.params = data.params;
                    //apelam render template pentru a popula tabelul cu rezultatele obtinute
                    that.renderTemplate('tmpl_inregistrari_list', {data: data.legitimatieList}).then(function (html) {
                        var tblHolder = $('.table_inregistrari');
                        tblHolder.html('');
                        tblHolder.html(html);
                        $('.dataTable_nosearch').DataTable( {
                            "searching": false,
                            "paging": false,
                            "info": false,
                            "ordering":false
                        });
                        $('#resize_iframe', window.parent.document).trigger('click');
                        $('.table_inregistrari div div table thead tr th').each(function(index) {
                            var elementId = $(this).attr('id');
                            if (elementId != null){
                                PageManager.columns.push(elementId.toUpperCase());
                            }
                        });
                    }, function () {
                        that.alert('Unable to render template', 'ERROR');
                    });
                }
                swal.close();

            }
        });
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },



    buildPagination: function () {
        var PROC_NAME = "PageManager.buildPagination";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);


        var that = this;
        var defer = $.Deferred();
        var nrTotalInregistrari = 0;
        var legitimatie = that.getInfoFilters();

        var url ="/dmsws/anre/getExameneInstalatoriCount/";

        $.ajax({
            url: url,
            contentType: "application/json",
            type:'POST',
            data: legitimatie,
            success: function (result) {
                if (result && result.info != null) {
                    nrTotalInregistrari = parseInt(result.info);
                }
                that.buildListOfPages(nrTotalInregistrari);
                defer.resolve();


            }
        });


        return defer.promise();
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);

    },



    buildListOfPages: function (nrTotalInregistrari) {
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
            pagContainerUl.append(that.buildPager(i, i.toString() == PageManager.nrPagina ? "active" : "", ""));
        }
        if (PageManager.nrPagina == 0) {
            pagContainerUl.append(that.buildPager(PageManager.nrPagina, "disabled", "fas fa-chevron-left"));
            pagContainerUl.append(that.buildPager(PageManager.nrPagina, "page-item", "fas fa-chevron-right"));
        }
        else if (PageManager.nrPagina < nrTotalPages - 1) {
            pagContainerUl.append(that.buildPager(PageManager.nrPagina, "page-item", "fas fa-chevron-left"));
            pagContainerUl.append(that.buildPager(PageManager.nrPagina, "page-item", "fas fa-chevron-right"));
        }
        else if (PageManager.nrPagina = nrTotalPages - 1) {
            pagContainerUl.append(that.buildPager(PageManager.nrPagina, "page-item", "fas fa-chevron-left"));
            pagContainerUl.append(that.buildPager(PageManager.nrPagina, "disabled", "fas fa-chevron-right"));
        }
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },


    buildPager: function (page, classType, icon, nrTotalPages) {
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
                item.append("<a class='page-link " + classType + "' href='#' onclick='PageManager.setPageNumberAndGo(" + paginaToGo + ")'><i class='" + icon + "'></i></a>");

            }

        }
        else if (classType != null && classType != '') {
            item.addClass('page-item ');
            item.addClass(classType);
            item.append("<a class='page-link " + classType + "' href='#' >"+page+"</a>");

        }
        else {
            item.addClass('page-item ');
            item.append("<a class='page-link'  href='#' onclick='PageManager.setPageNumberAndGo(" + paginaToGo + ")'>" + page + "</a>");
            $(item).attr('id', 'li_page_' + paginaToGo);
        }

        return item;
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);

    },


    setPageNumberAndGo: function (page) {
        var PROC_NAME = "PageManager.setPageNumberAndGo";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);
        //setam numarul paginii selectate
        PageManager.nrPagina = page;

        //apelam functia de afisare rezultate
        PageManager.afiseazaInregistrari();

        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);

    },



    mandatoryFunctions: function () {
        var PROC_NAME = "PageManager.mandatoryFunctions";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);
        //-------------- preluam WS_URL si WS_TOKEN din documentaPortal-util.js ---------------------
        if(typeof $UTIL.WS_URL =='undefined'){
            $.ajax({
                type: 'GET',
                url: '/dmsws/utilizator/getWsUrl',
                success: function (data) {
                    debugger
                    $UTIL.WS_URL=data;
                    PageManager.wsUrl = $UTIL.WS_URL;
                }
            });
        }else{
            PageManager.wsUrl = $UTIL.WS_URL;
        }
        if(typeof $UTIL.LINK_WS_TOKEN =='undefined'){
            $.ajax({
                type: 'GET',
                url: '/dmsws/utilizator/getAnonymousToken',
                success: function (data) {
                    $UTIL.LINK_WS_TOKEN=data;
                    PageManager.wsToken = $UTIL.LINK_WS_TOKEN;
                }
            });
        }else{
            PageManager.wsToken = $UTIL.LINK_WS_TOKEN;
        }

        //-------------- initializam template-uri mustache ---------------------
        PageManager.compileAllTemplates();
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
    }


};
$('#judet').on('change', function (e) {

    $.ajax({
        url: "/dmsws/nomenclator/country/" + $('#judet').val() + "/cities_by_region",
        beforeSend: function () { $('#localitate').empty(); },
        success: function (result) {
            cities = result.localitateList;
            $('#localitate').append(PageManager.getOptionListItemHtml('0', 'Selecteaza localitate'));
            for (var i = 0; i < cities.length; i++) {
                $('#localitate').append(PageManager.getOptionListItemHtml(cities[i].id, cities[i].denumire.trim()));
            }
            // $('#localitate').val(0).trigger("chosen:updated");

        },
        error: function (err) {
            console.log(err);
        }
    });
});

$(document).ready(function () {
    PageManager.init();
});




