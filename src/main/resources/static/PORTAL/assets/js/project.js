
var PageManager = {
    wsUrl: null,
    wsToken: null,
    templates: {},
    listaInregistrari: {},
    nrPagina: 0,
    nrMaxPages: 4,
    numPerPage: 8,
    init: function () {

        //-------------- se apeleaza obligatoriu la initializarea paginii---------------------

        //apelam reconstructie paginare & afisare rezultate
        PageManager.afiseazaInregistrari();


    },
    afiseazaInregistrari: function () {

        //apelam reconstructie paginare & afisare rezultate
        PageManager.buildPagination().then(function () {
            PageManager.getListaInregistrari(false,false);

        });
    },
    buildPagination: function () {

        var that = this;
        var defer = $.Deferred();
        var nrTotalInregistrari = 0;


        var url ='/dmsws/project/extern_projects_short_pag_count/true';

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

    },

    buildListOfPages: function (nrTotalInregistrari) {

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

    },

    buildPager: function (page, classType, icon, nrTotalPages) {

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


    },

    setPageNumberAndGo: function (page) {
        //setam numarul paginii selectate
        PageManager.nrPagina = page;

        //apelam functia de afisare rezultate
        PageManager.afiseazaInregistrari();

    },
    getListaInregistrari: function ( proiecteChecked,zoneChecked) {

        var that = this;

        var url ='/dmsws/project/extern_projects_short_pag/true';

        if (PageManager.nrPagina == null) {
            PageManager.nrPagina = 0;
        }


        //calcul index start si idnex end in functie de nr Pagina si nr item uri afisate per pagina
        var indexStart = PageManager.nrPagina * PageManager.numPerPage + 1;
        var indexEnd = (PageManager.nrPagina + 1) * PageManager.numPerPage + 1;
        url += "?&indexStart=" + indexStart + '&indexEnd=' + indexEnd;


        console.log("Ajax Call Start:" + url);
        var projectContainer = $("#box_proiecte");
        projectContainer.append("<div id='progressbar'></div>");
        $("#progressbar").progressbar({ value: false });
        $.ajax({
            url: url,

            success: function (result) {
                $("#box_proiecte").empty();
                if (result.result == 'OK') {
                    if (result.extendedInfo == 'true') {
                        $("#btn_depune_proiect").text("Administrare proiecte propuse");
                        $("#btn_vizualizare_proiecte").hide();
                    }

                    if (result && result.projects && result.projects.length > 0) {
                        projectsList = result.projects;
                        for (var i = 0; i < result.projects.length; i++) {
                            var currentItem = result.projects[i];

                            if (proiecteChecked == 0 && zoneChecked == 1) {
                                if (currentItem.zonaColaborativa == true) {
                                    projectContainer.append(appendProjectCard(currentItem));
                                }
                            }
                            else if (proiecteChecked == 1 && zoneChecked == 0) {
                                if (currentItem.zonaColaborativa == false) {
                                    projectContainer.append(appendProjectCard(currentItem));
                                }
                            }
                            else {
                                projectContainer.append(appendProjectCard(currentItem));
                            }
                        }


                        //daca are rol de POI_ADMIN


                    }
                }

            },
            error: function (err) {
                projectContainer.append("<div id='error'>" + err.status + "</div>");
                $("#box_proiecte").empty();
                console.log(err);
            }
        });
    }
}
$(document).ready(function () {

    PageManager.init();
});
var projectsList=null;

function buildProjectContainerFiltered(){

    var projectContainer = $("#box_proiecte");
    projectContainer.append("<div id='progressbar'></div>");
    $("#progressbar").progressbar({ value: false });
    $("#box_proiecte").empty();
    var proiecteChecked= $('#checkbox_proiecte').is(':checked');
    var zoneChecked= $('#checkbox_zone').is(':checked');

    if(projectsList!=null) {
        for (var i = 0; i < projectsList.length; i++) {
            var currentItem = projectsList[i];

            if (proiecteChecked == 0 && zoneChecked == 1) {
                if (currentItem.zonaColaborativa == true) {
                    projectContainer.append(appendProjectCard(currentItem));
                }
            }
            else if (proiecteChecked == 1 && zoneChecked == 0) {
                if (currentItem.zonaColaborativa == false) {
                    projectContainer.append(appendProjectCard(currentItem));
                }
            }
            else {
                projectContainer.append(appendProjectCard(currentItem));
            }
        }
    }else{
        buildProjectContainer(proiecteChecked,zoneChecked);
    }
}
function buildProjectContainer(proiecteChecked,zoneChecked){
    var projectContainer = $("#box_proiecte");
    projectContainer.append("<div id='progressbar'></div>");
    $("#progressbar").progressbar({ value: false });

    $.ajax({
        url: "/dmsws/project/extern_projects_short/true",
        success: function (result) {
            $("#box_proiecte").empty();
            console.log("result_Madhur", result);
            if(result.result=='OK') {
                if (result.extendedInfo == 'true') {
                    $("#btn_depune_proiect").text("Administrare proiecte propuse");
                    $("#btn_vizualizare_proiecte").hide();
                }

                if (result && result.projects && result.projects.length > 0) {
                    projectsList = result.projects;
                    for (var i = 0; i < result.projects.length; i++) {
                        var currentItem = result.projects[i];

                        if (proiecteChecked == 0 && zoneChecked == 1) {
                            if (currentItem.zonaColaborativa == true) {
                                projectContainer.append(appendProjectCard(currentItem));
                            }
                        }
                        else if (proiecteChecked == 1 && zoneChecked == 0) {
                            if (currentItem.zonaColaborativa == false) {
                                projectContainer.append(appendProjectCard(currentItem));
                            }
                        }
                        else {
                            projectContainer.append(appendProjectCard(currentItem));
                        }
                    }


                    //daca are rol de POI_ADMIN


                }
            }
        },
        error: function (err) {
            projectContainer.append("<div id='error'>" + err.status + "</div>");
            $("#box_proiecte").empty();
            console.log(err);
        }
    });
}
function openProjectCLickHandler(event) {
    var projectId = event.currentTarget.getAttribute("data-projectId");
    window.localStorage &&
    window.localStorage.setItem("activeProject", projectId);
    var projectNume = event.currentTarget.getAttribute("data-projectNume");
    window.localStorage &&
    window.localStorage.setItem("activeProjectNume", projectNume);
}

function appendProjectCard(project) {
    var template = $("#hidden_project").html();
    var item = $(template).clone();

    if( project.imageLink == null){
        project.imageLink='https://i.imgur.com/DNXtdDQ.jpg'
    }
    $(item).find(".link_proiect").attr("href", "#");
    if(project.base64Image !=null ){
        $(item)
            .find(".img_proiect")
            .children("img")
            .attr("src", "data:image/png;base64,"+project.base64Image);

    }
    else{
        $(item)
            .find(".img_proiect")
            .children("img")
            .attr("src", "assets/images/home-banner-3.jpeg");

    }


    $(item)
        .find(".title_proiect")
        .html("<div><span>" + project.nume + "</span></div>");
    $(item)
        .find(".categorie_proiect")
        .html("<div><span>" + project.categoriePoi + "</span></div>");

    var projectButton = $(item).find(".btns_proiect");
    projectButton.attr("data-projectId", project.id);
    projectButton.attr("data-projectNume", project.nume);
    projectButton.on("click", openProjectCLickHandler);
    if(typeof $UTIL.PORTAL_URL =='undefined') {
        $.ajax({
            type: 'GET',
            url: '/dmsws/utilizator/getPortalUrl',
            success: function (data) {
                $UTIL.PORTAL_URL = data;
                projectButton
                    .children("a")
                    .attr("href", $UTIL.PORTAL_URL +"detaliu-proiect?fromBugetare=true&idProiect=" + project.id);
            }
        });
    }else{
        projectButton
            .children("a")
            .attr("href", $UTIL.PORTAL_URL +"detaliu-proiect?fromBugetare=true&idProiect=" + project.id);
    }



    $(item).find('.data_proiect').html("<div><span>Dată publicare: "+project.publicatLa+"</span></div>");
    $(item).find('.mesaje_proiect').html("<div><span>Like(s): "+project.nrVoturiPro+"</span></div>");
    $(item).find('.checked_proiect').html("<div><span>Dislike(s): "+project.nrVoturiContra+"</span></div>");
    return item;
}


function openHartaProiect() {

    if(typeof $UTIL.PORTAL_URL =='undefined') {
        $.ajax({
            type: 'GET',
            url: '/dmsws/utilizator/getPortalUrl',
            success: function (data) {
                $UTIL.PORTAL_URL = data;
                var link=$UTIL.PORTAL_URL +"bugetare-participativa?fromDepunere=false";
                //window.top.location.href=link;
                window.open(link,'_self');
            }
        });
    }else{
        var link=$UTIL.PORTAL_URL +"bugetare-participativa?fromDepunere=false";
        //window.top.location.href=link;
        window.open(link,'_self');
    }

}
function openDepunereProiect() {

    var authenticate=false;
    $.ajax({
        url: "/dmsws/project/getPerioadaDepunere",
        success: function (result) {
            console.log("result_Madhur", result);
            if(result.result==null){

                Swal.fire({
                    icon: 'info',
                    html: "Pentru a depune un proiect trebuie sa fii autentificat!",
                    focusConfirm: false,
                    confirmButtonText: 'Ok',
                    onClose: () => {
                    window.location.href = '/logout';
            }
            });

            }
            if (result.result=='OK') {

                if(result.info =='true'){
                    if(typeof $UTIL.PORTAL_URL =='undefined') {
                        $.ajax({
                            type: 'GET',
                            url: '/dmsws/utilizator/getPortalUrl',
                            success: function (data) {
                                $UTIL.PORTAL_URL = data;
                                var link=$UTIL.PORTAL_URL +"bugetare-participativa?fromDepunere=true";
                                //window.top.location.href=link;
                                window.open(link,'_self');
                            }
                        });
                    }else{
                        var link=$UTIL.PORTAL_URL +"bugetare-participativa?fromDepunere=true";
                        //window.top.location.href=link;
                        window.open(link,'_self');
                    }

                }else{
                    if(result.extendedInfo=='POI_ADMIN') {

                        if(typeof $UTIL.PORTAL_URL =='undefined') {
                            $.ajax({
                                type: 'GET',
                                url: '/dmsws/utilizator/getPortalUrl',
                                success: function (data) {
                                    $UTIL.PORTAL_URL = data;
                                    var link=$UTIL.PORTAL_URL +"bugetare-participativa?fromDepunere=true";
                                    //window.top.location.href=link;
                                    window.open(link,'_self');
                                }
                            });
                        }else{
                            var link=$UTIL.PORTAL_URL +"bugetare-participativa?fromDepunere=true";
                            //window.top.location.href=link;
                            window.open(link,'_self');
                        }

                    }else{
                        Swal.fire({
                            icon: 'info',
                            html: "Ne aflam in afara perioadei de depunere a proiectelor.",
                            focusConfirm: false,
                            confirmButtonText: 'Ok',
                        });
                    }

                }
            }
        },
        error: function (err) {

            console.log(err);
        }
    })


}
