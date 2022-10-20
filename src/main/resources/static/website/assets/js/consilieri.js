
var PAGE_NAME = "consilieri.js";

var PageManager = {
    extensiiRestrictionate:['exe','msi'],
    wsUrl: null,
    wsToken: null,
    templates: {},
    listaInregistrari: {},
    nrPagina: 0,
    nrMaxPages: 4,
    numPerPage: 5,
    idRegistru: 5,
    idInregistrare:0,
    init: function () {

        var PROC_NAME = "PageManager.init";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);
        //CHECK IF LOGGED IN
        if($UTIL.userIsLoggedIn()){

            //-------------- se apeleaza obligatoriu la initializarea paginii---------------------
            this.mandatoryFunctions().then(function () {
                if($UTIL.checkUserGroup($UTIL.idGrupConsilieriJudeteni,$UTIL.userData.token)){
                    //apelam reconstructie paginare & afisare rezultate
                    PageManager.setIdRegistru();}
            });



        }
        else{
            Swal.fire({
                    icon: "info",
                    html: "Vă rugăm să vă autentificați.",
                    focusConfirm: false,
                    confirmButtonText: "Ok",
                    onClose: () => {
                    window.location.replace($UTIL.WORDPRESS_URL+ '/autentificare', '_blank');

            // window.location.href=$UTIL.WORDPRESS_URL+ '/autentificare';
        }
        });
        }

        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);


    },

    showModal:function(id,creat_de){


        if($UTIL.checkRegistruAccess(PageManager.idRegistru,$UTIL.userData.token,"edit_full")==1){
            PageManager.loadData(id);
            $("#modalAdauga").modal();
        }else if($UTIL.checkRegistruAccess(PageManager.idRegistru,$UTIL.userData.token,"edit_my")==1 && creat_de==$UTIL.userData.username)
        { PageManager.loadData(id);
            $("#modalAdauga").modal();}
        else{
            Swal.fire({
                icon: "info",
                html: "Nu aveți dreptul de editare pentru acest registru.",
                focusConfirm: false,
                confirmButtonText: "Ok"


            });

        }
    },

    loadData:function(id){
        var PROC_NAME = "PageManager.afiseazaInregistrare";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);
        PageManager.idInregistrare=id;
        $("#btnAddEditInreg").html("Editeaza<i class='fas fa-arrow-alt-circle-right'></i>");

        var url =$UTIL.WS_URL+"/registratura_portal/"+$UTIL.userData.token+"/getInregistrare/"+PageManager.idInregistrare;
        $.ajax({
            url: url,
            success: function (data) {
                if (data.result == 'OK') {
                    $('#nr_crt').val(data.nr_generat);
                    $('#data').val(data.data);
                    $('#documentul').val(data.obs);
                    $('#rezultat_analiza').val(data.info4);
                    $('#nume_fisier').val(data.fisierDownload);

                }

            }
        });

    },

    setIdRegistru:function(){
        var PROC_NAME = "PageManager.setIdRegistru";


        PageManager.idRegistru=$('#registru').find(":selected").val();

        if($UTIL.checkRegistruAccess(PageManager.idRegistru,$UTIL.userData.token,"view_full")==1) {
            PageManager.afiseazaInregistrari();
        }else{
            $('.table_inregistrari').html('');
            $(".pagination_ul_container ul").empty();
            Swal.fire({
                icon: "info",
                html: "Nu aveți drept de vizualizare pe acest registru!.",
                focusConfirm: false,
                confirmButtonText: "Ok",
            });
        }


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
    getListaInregistrari: function (jReq) {

        var that = this;
        var PROC_NAME = "PageManager.getListaInregistrari";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);
        var url =$UTIL.WS_URL+"/registratura_portal/"+$UTIL.userData.token+"/getInregistrariList/"+that.idRegistru;
        if (PageManager.nrPagina == null) {
            PageManager.nrPagina = 0;
        }


        //calcul index start si idnex end in functie de nr Pagina si nr item uri afisate per pagina
        var indexStart = PageManager.nrPagina * PageManager.numPerPage + 1;
        var indexEnd = (PageManager.nrPagina + 1) * PageManager.numPerPage + 1;
        url += "?&indexStart=" + indexStart + '&indexEnd=' + indexEnd;


        console.log("Ajax Call Start:" + url);

        $.ajax({
            url: url,

            success: function (data) {
                if (data.result == 'OK') {
                    that.listaInregistrari = data.registraturaCompleteList;

                    //apelam render template pentru a popula tabelul cu rezultatele obtinute
                    that.renderTemplate('tmpl_inregistrari_list', {data: data.registraturaCompleteList}).then(function (html) {
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
    resetIdInregistrare:function(){
        if($UTIL.checkRegistruAccess(PageManager.idRegistru,$UTIL.userData.token,"allow_in")==1){
            PageManager.idInregistrare=0;
            $("#btnAddEditInreg").html("Adauga<i class='fas fa-arrow-alt-circle-right'></i>");

            $('#nr_crt').val("");
            $('#data').val("");
            $('#documentul').val("");
            $('#rezultat_analiza').val("");
            $('#nume_fisier').val("");
            $("#modalAdauga").modal();
        } else{
            Swal.fire({
                icon: "info",
                html: "Nu aveți drept de adaugare pe acest registru!.",
                focusConfirm: false,
                confirmButtonText: "Ok",
            });


        }},

    attachEvents: function(){
        var that = this;

        $("#btnAddEditInreg").click(function(){

            if(PageManager.idInregistrare==0)
                var url =PageManager.wsUrl+"/registratura_portal/"+$UTIL.userData.token+"/insertRegistruFisier";
            else
                var url=PageManager.wsUrl+"/registratura_portal/"+$UTIL.userData.token+"/updateRegistruFisier";
            var fileData = $("#pm_file")[0];
            if($("#pm_file")[0].files[0]!=null && PageManager.extensiiRestrictionate.includes($("#pm_file")[0].files[0].name.split('.').pop())){
                swalError('Extensia fișierului nu este acceptată');
                return;
            }
            if ($("#nume_fisier").val()==="") {
                $("#pm_file").val("");
                $("#nume_fisier").val("");
                //  $.fancybox.close();
                swalError('Selectați un fișier');
                return;
            }
            var formData = new FormData();
            var nrGenerat=$('#nr_crt').val();
            var data=$('#data').val();
            if($('#documentul').val().length>=4000)
                var documentul=$('#documentul').val().toString().substring(0,4000);
            else
                var documentul=$('#documentul').val();
            if(($('#rezultat_analiza').val().length>=4000))
                var rezultatAnaliza=$('#rezultat_analiza').val().toString().substring(0,4000);
            else
                var rezultatAnaliza=$('#rezultat_analiza').val();
            var numeFisier = $("#nume_fisier").val();
            var registru={
                id:PageManager.idInregistrare,
                id_registru:PageManager.idRegistru,
                nr_generat:nrGenerat,
                obs:documentul,
                info4:rezultatAnaliza,
                data:data
            };
            formData.append('file', fileData.files[0]);
            formData.append('nume', numeFisier);
            formData.append('id', PageManager.idInregistrare);
            formData.append('id_registru', PageManager.idRegistru);
            formData.append('nr_generat', nrGenerat);
            formData.append('obs', documentul);
            formData.append('info4', rezultatAnaliza);
            formData.append('data', data);

            $.ajax({
                url: url,
                method: 'POST',
                type:'POST',
                data: formData,
                enctype: 'multipart/form-data',
                processData: false,
                contentType: false,
                dataType: "json",
                header:{'Accept': 'application/json'},
                success: function (json) {

                    if (json.status == 'OK') {
                        Swal.fire({
                            icon: 'success',
                            html: "Fisierul a fost incarcat!",
                            focusConfirm: false,
                            confirmButtonText: 'Ok',


                        }).then(function(){location.reload();});
                    }
                    else {
                        Swal.fire({
                            icon: 'error',
                            html: "Ne pare rau! A intervenit o problema. Fisierul nu a putut fi salvat!",
                            focusConfirm: false,
                            confirmButtonText: 'Ok',

                        }).then(function(){location.reload();});
                    }


                },
                error: function (err) {
                    var strErr = err;
                },
                complete: function (data) {
                    PageManager.cleanBeforeAddFile();

                }
            });


        });

        $("#pm_file").change(function(){

            var fileData = $("#pm_file")[0];
            var numeFisier= fileData.files[0].name;
            numeFisier= numeFisier.substr(0,numeFisier.lastIndexOf("."));
            $('#nume_fisier').val(numeFisier);


        });

        $("#pm_add_file").click(function(){
            document.getElementById('pm_file').click();
        });

    },

    cleanBeforeAddFile : function(){
        $("#pm_file").val("");
    },
    insertRegistru:function(){

        var PROC_NAME = "PageManager.buildPagination";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);
        //se preiau datele introduse
        var nrGenerat=$('#nr_crt').val();
        var data=$('#data').val();
        if($('#documentul').val().length>=4000)
            var documentul=$('#documentul').val().toString().substring(0,4000);
        else
            var documentul=$('#documentul').val();
        if(($('#rezultat_analiza').val().length>=4000))
            var rezultatAnaliza=$('#rezultat_analiza').val().toString().substring(0,4000);
        else
            var rezultatAnaliza=$('#rezultat_analiza').val();

        var registru={
            id:PageManager.idInregistrare,
            id_registru:PageManager.idRegistru,
            nr_generat:nrGenerat,
            obs:documentul,
            info4:rezultatAnaliza,
            data:data
        };
        if(PageManager.idInregistrare==0)
            var url =$UTIL.WS_URL+"/registratura_portal/"+this.wsToken+"/insertRegistru";
        else
            var url=$UTIL.WS_URL+"/registratura_portal/"+this.wsToken+"/updateRegistru/";


        var jReq=JSON.stringify(registru);

        $.ajax({
            url: url,
            method: 'POST',
            data:jReq,
            contentType: 'application/json',
            accept:'application/json',
            success: function (json) {

                Swal.fire({
                        icon: "info",
                        html: "Inregistrare efectuata cu succes.",
                        focusConfirm: false,
                        confirmButtonText: "Ok",
                        onClose: () => {
                        window.location.reload();
            }
            });

            }

            ,
            error: function (err) {
                ;
                Swal.fire({
                        icon: "error",
                        html: "Eroare server.Inregistrarea nu a putut fi adaugata.",
                        focusConfirm: false,
                        confirmButtonText: "Ok",
                        onClose: () => {
                        window.location.reload();
            }
            });
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
        var url =$UTIL.WS_URL+"/registratura_portal/"+$UTIL.userData.token+"/getInregistrariListCount/"+that.idRegistru;
        $.ajax({
            url: url,
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
    getListaInregistrariCautare: function () {

        var that = this;
        var PROC_NAME = "PageManager.getListaInregistrariCautare";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);

        var url ="/dmsws/regsistratura/getInregistrariListCautareP/"+that.idRegistru;

        if (PageManager.nrPagina == null) {
            PageManager.nrPagina = 0;
        }


        //calcul index start si idnex end in functie de nr Pagina si nr item uri afisate per pagina
        var indexStart = PageManager.nrPagina * PageManager.numPerPage + 1;
        var indexEnd = (PageManager.nrPagina + 1) * PageManager.numPerPage + 1;
        url += "?&indexStart=" + indexStart + '&indexEnd=' + indexEnd ;

            url+= '&an='+$('#an').val();


        console.log("Ajax Call Start:" + url);

        var obj=[];
        obj.push("DATA");
        var jReq= JSON.stringify(obj);
        $.ajax({
            type: 'POST',
            url: url,
            data: jReq,
            contentType: "application/json",
            success: function (data) {
                if (data.result == 'OK') {
                    that.listaInregistrari = data.registraturaCompleteList;

                    //apelam render template pentru a popula tabelul cu rezultatele obtinute
                    that.renderTemplate('tmpl_inregistrari_list', {data: data.registraturaCompleteList}).then(function (html) {
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

        var defer = $.Deferred();
        var PROC_NAME = "PageManager.mandatoryFunctions";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);
        //-------------- preluam WS_URL si WS_TOKEN din documentaPortal-util.js ---------------------
        if(typeof $UTIL.WS_URL =='undefined'){
            $.ajax({
                type: 'GET',
                url: '/dmsws/utilizator/getWsUrl',
                success: function (data) {

                    $UTIL.WS_URL=data;
                    PageManager.wsUrl = $UTIL.WS_URL;
                }
            });
        }else{
            PageManager.wsUrl = $UTIL.WS_URL;
        }
        PageManager.wsToken = $UTIL.LINK_WS_TOKEN;
        PageManager.idRegistru = $UTIL.idRegistruProiecteHcj;
        PageManager.attachEvents();
        //-------------- initializam template-uri mustache ---------------------
        PageManager.compileAllTemplates();
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
        defer.resolve();
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




