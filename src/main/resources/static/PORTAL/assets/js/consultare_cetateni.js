$(document).ready(function () {

  buildProjectContainer(false,false);
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
        url: "/dmsws/project/extern_projects/false",
        success: function (result) {
            $("#box_proiecte").empty();
            console.log("result_Madhur", result);
            if(result.result=='OK') {
                if (result.extendedInfo == 'true') {
                    $("#btn_depune_proiect").text("Administrare proiecte propuse");
                    $("#btn_depune_proiect").attr("role","POI_ADMIN");
                    $("#btn_vizualizare_proiecte").hide();

                }
                else if (result.extendedInfo2 == 'true') {
                    $("#btn_depune_proiect").text("Depune proiect pentru prezentarea cetatenilor");
                    $("#btn_depune_proiect").attr("role","CONSULTARE_CETATENI");

                    $("#btn_vizualizare_proiecte").hide();
                }
                else{
                    $("#btn_depune_proiect").hide();
                    //$("#btn_vizualizare_proiecte").hide();
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
  window.sessionStorage &&
    window.sessionStorage.setItem("activeProject", projectId);
  var projectNume = event.currentTarget.getAttribute("data-projectNume");
   window.sessionStorage &&
   window.sessionStorage.setItem("activeProjectNume", projectNume);
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
                    .attr("href", $UTIL.PORTAL_URL +"detaliu-proiect?fromBugetare=false&idProiect=" + project.id);
            }
        });
    }else{
        projectButton
            .children("a")
            .attr("href", $UTIL.PORTAL_URL +"detaliu-proiect?fromBugetare=false&idProiect=" + project.id);
    }


    $(item).find('.data_proiect').html("<div><span>Data publicare: "+project.publicatLa+"</span></div>");
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
                var link=$UTIL.PORTAL_URL +"consultare-cetateni?fromDepunere=false";
                //window.top.location.href=link;
                window.open(link,'_self');
            }
        });
    }else{
        var link=$UTIL.PORTAL_URL +"consultare-cetateni?fromDepunere=false";
        //window.top.location.href=link;
        window.open(link,'_self');
    }

}
function openDepunereProiect(roleCode) {

    var authenticate=false;
    $.ajax({
        url: "/dmsws/project/check_role/"+roleCode,
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


                var roleCode= $("#btn_depune_proiect").attr("role");
                if(roleCode=='POI_ADMIN' ||roleCode=='CONSULTARE_CETATENI' ){
                    if(typeof $UTIL.PORTAL_URL =='undefined') {
                        $.ajax({
                            type: 'GET',
                            url: '/dmsws/utilizator/getPortalUrl',
                            success: function (data) {
                                $UTIL.PORTAL_URL = data;
                                var link=$UTIL.PORTAL_URL + "consultare-cetateni?fromDepunere=true";
                                //window.top.location.href=link;
                                window.open(link,'_self');
                            }
                        });
                    }else{
                        var link=$UTIL.PORTAL_URL +"consultare-cetateni?fromDepunere=true";
                        //window.top.location.href=link;
                        window.open(link,'_self');
                    }

                }else{
                    Swal.fire({
                        icon: 'info',
                        html: "Nu aveți rolul necesar pentru a depune proiecte!",
                        focusConfirm: false,
                        confirmButtonText: 'Ok',
                    });

                }
            }
        },
        error: function (err) {

            console.log(err);
        }
    })


}
