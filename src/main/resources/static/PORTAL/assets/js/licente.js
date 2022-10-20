
var PAGE_NAME = "licente.js";
var idDomeniu = new URLSearchParams(window.location.search).get('domeniu');

var PageManager = {
    // pre-compiled mustache templates
    templates: {},
    licentaList: [],
    userInfo: null,
    wsUrl: null,
    wsToken: null,
    qu: null,
    params: [],
    columns: [],
    idRomaniaLovTari: 642,
    COD_LOV_SOCIETATE: "LOV_LP_SOCIETATE_LICENTE",
    COD_LOV_STARE: "LOV_LP_STARE_LICENTA",
    COD_LOV_TIP_LICENTA: "LOV_LP_TIP_LICENTA",
    COD_LOV_TIP_ACTIVITATE: "LOV_LP_TIP_ACTIVITATE_LICENTA",
    /*
     Initialization function.
     */
    init: function () {
        var that = this;

        that.mandatoryFunctions();
        that.initLovSocietate();
        that.initLovJudet();
        that.initLovStare();
        that.initLovTipLicenta();
        that.initLovTipActivitate();

        that.afiseazaInregistrari();

    },

    compileAllTemplates: function () {
        this.templates['tmpl_inregistrari_list'] = $('#tmpl_inregistrari_list').html();

        // parseaza toate template-urile
        $.each(this.templates, function (index, template) {
            Mustache.parse(template);
        });
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

    getOptionListItemHtml: function (id, denumire){
    return '<option value="' + id + '">' + denumire + '</option>';
    },

    initLovSocietate: function() {
        var PROC_NAME = "PageManager.initLovJudet";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);
        debugger;
        var that = this;
        var defer = $.Deferred();
        var url = "/dmsws/lov/values_by_code/" + PageManager.COD_LOV_SOCIETATE;
        $.ajax({
            url: url,
            success: function (data) {
                if (data.result == 'OK') {

                    if (data.lov != null && data.lov.length != 0) {
                            var lov = data.lov;
                            $('#societate').append(that.getOptionListItemHtml('0', 'Selecteaza societate'));
                            for(i=0;i<lov.length;i++){
                                $('#societate').append(that.getOptionListItemHtml(lov[i].id, lov[i].valoare));
                            }
                    }
                }

                defer.resolve();

            }
        });

        return defer.promise();
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },

    initLovJudet: function () {
        debugger;
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

    initLovStare: function() {
        var PROC_NAME = "PageManager.initLovStare";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);
        debugger;
        var that = this;
        var defer = $.Deferred();
        var url = "/dmsws/lov/values_by_code/" + PageManager.COD_LOV_STARE;
        $.ajax({
            url: url,
            success: function (data) {
                if (data.result == 'OK') {

                    if (data.lov != null && data.lov.length != 0) {
                        var lov = data.lov;
                            $('#stare').append(that.getOptionListItemHtml('0', 'Selecteaza stare'));
                        for(i=0;i<lov.length;i++){
                            $('#stare').append(that.getOptionListItemHtml(lov[i].id, lov[i].valoare));
                        }
                    }
                }

                defer.resolve();

            }
        });

        return defer.promise();
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },

    initLovTipLicenta: function() {
        var PROC_NAME = "PageManager.initLovTipLicenta";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);
        debugger;
        var that = this;
        var defer = $.Deferred();
        var url = "/dmsws/lov/values_by_code/" + PageManager.COD_LOV_TIP_LICENTA;
        $.ajax({
            url: url,
            success: function (data) {
                if (data.result == 'OK') {

                    if (data.lov != null && data.lov.length != 0) {
                        var lov = data.lov;
                        $('#tip_licenta').append(that.getOptionListItemHtml('0', 'Selecteaza tip licenta'));
                        for(i=0;i<lov.length;i++){
                            $('#tip_licenta').append(that.getOptionListItemHtml(lov[i].id, lov[i].valoare));
                        }
                    }
                }

                defer.resolve();

            }
        });

        return defer.promise();
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },

    initLovTipActivitate: function() {
        var PROC_NAME = "PageManager.initLovTipActivitate";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);
        debugger;
        var that = this;
        var defer = $.Deferred();
        var url = "/dmsws/lov/values_by_code/" + PageManager.COD_LOV_TIP_ACTIVITATE;
        $.ajax({
            url: url,
            success: function (data) {
                if (data.result == 'OK') {

                    if (data.lov != null && data.lov.length != 0) {
                        var lov = data.lov;
                        $('#tip_activitate').append(that.getOptionListItemHtml('0', 'Selecteaza tip activitate'));
                        for(i=0;i<lov.length;i++){
                            $('#tip_activitate').append(that.getOptionListItemHtml(lov[i].id, lov[i].valoare));
                        }
                    }
                }

                defer.resolve();

            }
        });

        return defer.promise();
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },

    mandatoryFunctions: function () {
        var that = this;

        var PROC_NAME = "FileManager.mandatoryFunctions";
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


        if(idDomeniu==1){
            $("#titlu_licente").html("Licente Energie Electrica")
        }else if(idDomeniu==2){
            $("#titlu_licente").html("Licente Gaze Naturale")

        }else if(idDomeniu==3){
            $("#titlu_licente").html("Licente Energie Termica")

        }
        that.compileAllTemplates();



    },
    exportXlsx: function () {
        $UTIL.waitForLoading();
        var PROC_NAME = "PageManager.exportXlsx";
        debugger;
        var baseUrl = this.wsUrl + "/anre/" + this.wsToken + "/exportXlsx";
        var query = this.qu;
        var params = this.params;
        var columns = this.columns;


        $UTIL.exportXlsxByVaadin("export.xlsx",baseUrl,query,params,columns);

        // window.open(url, '_blank');

        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },
    getInfoFilters: function(){

        var societate=$("#societate").val();
        var sediu=$("#sediu").val();
        var localitate=$("#localitate").val();
        var judet=$("#judet").val();
        var nr_licenta=$("#nr_licenta").val();
        var stare=$("#stare").val();
        var tip_licenta=$("#tip_licenta").val();
        var tip_activitate=$("#tip_activitate").val();
        var nr_decizie=$("#nr_decizie").val();

        var legitimatie={
            societate:societate,
            sediu:sediu,
            localitate:localitate,
            judet:judet,
            nr_licenta:nr_licenta,
            stare:stare,
            tip_licenta:tip_licenta,
            tip_activitate:tip_activitate,
            nr_decizie:nr_decizie

        }
        return JSON.stringify(legitimatie);
    },

    loadData: function () {
        var that = this;

        var req = that.getInfoFilters();
        if (PageManager.nrPagina == null) {
            PageManager.nrPagina = 0;
        }

        var url='/dmsws/anre_licente/getLicenteFiltered/' + idDomeniu;
        //calcul index start si idnex end in functie de nr Pagina si nr item uri afisate per pagina
        var indexStart = PageManager.nrPagina * PageManager.numPerPage + 1;
        var indexEnd = (PageManager.nrPagina + 1) * PageManager.numPerPage + 1;
        url += "?&indexStart=" + indexStart + '&indexEnd=' + indexEnd;

        var defer = $.Deferred();
        $.ajax({
            url: url,
            method:'POST',
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            data: req,
            success: function (data) {
                if (data.status == 'OK') {
                    that.qu = data.query;
                    that.params = data.params;
                    that.licentaList = data.licentaList;
                    that.renderData({licentaList: data.licentaList});
                    $('#resize_iframe', window.parent.document).trigger('click');
                    $('.table_inregistrari div div table thead tr th').each(function(index) {
                        var elementId = $(this).attr('id');
                        if (elementId != null){
                            PageManager.columns.push(elementId.toUpperCase());
                        }
                    });
                    defer.resolve();
                } else {
                    Swal.fire({
                        position: 'top',
                        icon: 'error',
                        html: "A aparut o eroare!<br/>",
                        focusConfirm: false,
                        confirmButtonText: 'Ok'
                    });

                    defer.reject("A aparut o eroare");
                }
            },
            error: function(data){
                Swal.fire({
                    position: 'top',
                    icon: 'error',
                    html: "A aparut o eroare!<br/>",
                    focusConfirm: false,
                    confirmButtonText: 'Ok'
                });

                defer.reject("A aparut o eroare");
            }
        });


        return defer.promise();
    },

    renderData: function (data) {
        var that = this;
        var html = that.renderTemplateNonAsync(that, "tmpl_inregistrari_list", data);

        $('.table_inregistrari').html('');
        $('.table_inregistrari').html(html);
        $('.dataTable_nosearch').DataTable( {
            "searching": false,
            "paging": false,
            "info": false,
            "ordering":false
        });
        $('#resize_iframe', window.parent.document).trigger('click');
    },



    myDrop:null,
    templates: {},
    listaInregistrari: {},
    listaCautare:{},
    nrPagina: 0,
    nrMaxPages: 4,
    numPerPage: 10,
    idRegistru: 3306,

    cautare: function(){

        var PROC_NAME = "PageManager.cautare";
        var searchStr=$('#search_box').val();
        PageManager.buildPaginationCautare(searchStr).then(function () {
            PageManager.getListaInregistrariCautare(searchStr);
        });


        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },

    afiseazaInregistrari: function () {
        var PROC_NAME = "PageManager.afiseazaInregistrari";


        //apelam reconstructie paginare & afisare rezultate
        PageManager.buildPagination().then(function () {
            PageManager.loadData();
        });
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },


    buildPagination: function () {
        var PROC_NAME = "PageManager.buildPagination";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);


        var that = this;
        var defer = $.Deferred();
        var nrTotalInregistrari = 0;

        var req = that.getInfoFilters();
        var url='/dmsws/anre_licente/getLicenteFilteredCount/' + idDomeniu;

        $.ajax({
            url: url,    method:'POST',
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            data: req,
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
    buildListOfPagesCautare: function (nrTotalInregistrari) {
        var PROC_NAME = "PageManager.buildListOfPagesCautare";
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
            pagContainerUl.append(that.buildPagerCautare(i, i.toString() == PageManager.nrPagina ? "active" : "", ""));
        }
        if (PageManager.nrPagina == 0) {
            pagContainerUl.append(that.buildPagerCautare(PageManager.nrPagina, "disabled", "fas fa-chevron-left"));
            pagContainerUl.append(that.buildPagerCautare(PageManager.nrPagina, "page-item", "fas fa-chevron-right"));
        }
        else if (PageManager.nrPagina < nrTotalPages - 1) {
            pagContainerUl.append(that.buildPagerCautare(PageManager.nrPagina, "page-item", "fas fa-chevron-left"));
            pagContainerUl.append(that.buildPagerCautare(PageManager.nrPagina, "page-item", "fas fa-chevron-right"));
        }
        else if (PageManager.nrPagina = nrTotalPages - 1) {
            pagContainerUl.append(that.buildPagerCautare(PageManager.nrPagina, "page-item", "fas fa-chevron-left"));
            pagContainerUl.append(that.buildPagerCautare(PageManager.nrPagina, "disabled", "fas fa-chevron-right"));
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
    buildPagerCautare: function (page, classType, icon, nrTotalPages) {
        var PROC_NAME = "PageManager.buildPagerCautare";
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
                item.append("<a class='page-link " + classType + "' href='#' onclick='PageManager.setPageNumberAndGoCautare(" + paginaToGo + ")'><i class='" + icon + "'></i></a>");

            }

        }
        else if (classType != null && classType != '') {
            item.addClass('page-item ');
            item.addClass(classType);
            item.append("<a class='page-link " + classType + "' href='#' >"+page+"</a>");

        }
        else {
            item.addClass('page-item ');
            item.append("<a class='page-link'  href='#' onclick='PageManager.setPageNumberAndGoCautare(" + paginaToGo + ")'>" + page + "</a>");
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

    setPageNumberAndGoCautare: function (page) {
        var PROC_NAME = "PageManager.setPageNumberAndGoCautare";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);
        //setam numarul paginii selectate
        PageManager.nrPagina = page;

        //apelam functia de afisare rezultate
        PageManager.cautare();

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




