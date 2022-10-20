var PAGE_NAME = "main-screen-operator.js";

var FileManager = {
    parametersLoadedOk: false,
    // user info object
    tagClasaDoc: null,
    // pre-compiled mustache templates
    templates: {},

    /*
     Initialization function.
     */
    init: function () {
        var that = this;

        this.mandatoryFunctions().then(
        function(){
            that.loadInfoData();
        }

        );

    },

    mandatoryFunctions: function () {

        var defer = $.Deferred();
        var PROC_NAME = "PageManager.mandatoryFunctions";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);
        //-------------- preluam WS_URL si WS_TOKEN din documentaPortal-util.js ---------------------

        FileManager.tagClasaDoc=new URLSearchParams(window.location.search).get("tag");

        //-------------- initializam template-uri mustache ---------------------
        FileManager.compileAllTemplates();
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
          defer.resolve();

        return defer.promise();

    },
    /*
     Function to read basic project data from ws.
     */

    loadInfoData: function () {
    var that = this;

    var defer = $.Deferred();
    $.ajax({
        url: '/dmsws/document/getListaClasaDocByTag?&tagStr='+that.tagClasaDoc,
        success: function (data) {

            if (data.result == 'OK') {

                if(data.tipDocumentList==null || data.tipDocumentList.length==0){
                    data.tipDocumentList.push({"denumire":"Ne pare rău. Nu aveți drepturi pe niciun document din această categorie."});
                    that.renderInfoData(data);

                }
                else{
                    that.renderInfoData(data);

                }

                defer.resolve();
            }
            else {
                Swal.fire({
                    position: 'top',
                    icon: 'error',
                    html: "A aparut o eroare!<br/>",
                    focusConfirm: false,
                    confirmButtonText: 'Ok'
                });

                defer.reject("A aparut o eroare");
            }

        }
    });


    return defer.promise();
},
    showPopupInfo: function (codClasaDoc,linkUrl) {

        $('#resize_iframe', window.parent.document).trigger('click');

        if(codClasaDoc=='RAP_COMP'  ){
            Swal.fire({
                position: 'top',
                icon: 'info',
                html: "<p style='font-size: 24px'>Vă rugăm să consultați ghidul de utilizare și secțiunea de întrebări frecvente înainte de a începe raportarea.</p>",
                focusConfirm: false,
                confirmButtonText: 'Ok',
                    onClose: () => {
                    if(linkUrl.startsWith("/" )  ){
                window.location= linkUrl;
            }else{
                window.top.location.href= linkUrl;
            }

        },
            });

        }
        else if ( codClasaDoc=='RAP_CTR_BIL' ){
            if(linkUrl.startsWith("/" )  ){
                window.location= linkUrl;
            }else{
                window.top.location.href= linkUrl;
            }

        }
        else{
                window.location= linkUrl;

        }
        $('#resize_iframe', window.parent.document).trigger('click');

    },
    renderInfoData: function (data) {
        var that = this;

        $("#container_categorii").empty();
        var html = that.renderTemplateNonAsync(that, "tmpl_doc", data);
        $("#container_categorii").html(html);


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

    goBack: function () {
       window.history.back();
    },


    /*
     Function to compile all known mustache templates.
     */
    compileAllTemplates: function () {
        this.templates['tmpl_doc'] = $('#tmpl_doc').html();

        // parseaza toate template-urile
        $.each(this.templates, function (index, template) {
            Mustache.parse(template);
        });
    }
};


$(document).ready(function () {
    FileManager.init();
});