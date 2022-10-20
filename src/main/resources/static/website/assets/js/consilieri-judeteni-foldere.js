var PAGE_NAME = "consilieri-judeteni-foldere.js";
var FileManager = {
    parametersLoadedOk: false,

    // project info object
    projectInfo: null,

    // user info object
    userInfo: null,

    // project id
    projectId: null,

    // initial directory loaded on startup
    startingDirId: null,

    // last dir id loaded
    lastDirId: null,

    documentList : null,

    startingSearchVal: null,

    lastSearchVal: null,

    // pre-compiled mustache templates
    templates: {},

    /*
     Initialization function.
     */
    init: function () {

        var that = this;
        var PROC_NAME = "PageManager.init";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);
//-------------- se apeleaza obligatoriu la initializarea paginii---------------------
        this.mandatoryFunctions();
        /*
         Read input parameters.
         */
        that.compileAllTemplates();
         that.loadDir(that.startingDirId, that.startingSearchVal);


    },
    mandatoryFunctions: function () {

        var PROC_NAME = "FileManager.mandatoryFunctions";
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Enter', 0);
        //-------------- preluam WS_URL si WS_TOKEN din documentaPortal-util.js ---------------------
        FileManager.startingDirId = $UTIL.idFolderComisii;
        FileManager.wsUrl = $UTIL.WS_URL;
        FileManager.wsToken = $UTIL.LINK_WS_TOKEN;
        $UTIL.log(PAGE_NAME, PROC_NAME, 'Exit', 0);
    },
    renderUsedSpace: function(){
        var that = this;

        var spaceObj = that.projectInfo;
        spaceObj.dimensiunePercentGb = Math.round(spaceObj.dimensiuneActualaGb * 100 / spaceObj.dimensiuneMaximaGb);
        var tmpl_space = {tmpl_space: [spaceObj]};

        var html = Util.renderTemplateNonAsync(that,"tmpl_space", tmpl_space);
        $("#pm_tmpl_space").html(html);
    },

    renderProjectName: function(){
        var that = this;

        $("#pm_nume_proiect").html(that.projectInfo.nume);
    },

    /*
     Function to read basic project data from ws.
     */



    /*
     Loads the specified directory.
     */
    loadNewDir: function (dirId) {
        var that = this;
        that.clearSearchValue();
        that.loadDir(dirId);
    },

    clearSearchValue: function(){
        var that = this;
        that.lastSearchVal = '';
        $("#pm_search").val('');
    },

    search: function(value){
        var that = this;
        if (!ObjTools.isNully(value)) {
            that.lastSearchVal = value;
            that.loadDir(that.lastDirId, that.lastSearchVal);
        }
    },

    /*
     Loads the specified directory.
     */
    loadDir: function (dirId, searchVal) {

        var that = this;
        that.loadFilesFolders(dirId, searchVal);
    },

    loadFilesFolders: function (dirId, searchVal) {

        var that = this;

        that.loadFilesFoldersData(dirId, searchVal).then(function (json) {
            that.lastDirId = dirId;
            that.renderFilesFolders(json);
        }, function (err) {
            Util.alert(err,"ERROR");
        });
    },



    renderFilesFolders: function(json){

        var that = this;

        var filesFoldersObj = json;
        var tmpl_files_folders = {tmpl_files_folders: filesFoldersObj};

        var html = Util.renderTemplateNonAsync(that,"tmpl_files_folders", tmpl_files_folders);
        $("#pm_tmpl_files_folders").html(html);
    },

    loadFilesFoldersData: function (dirId, searchVal) {

        var that = this;

        var defer = $.Deferred();
        var url =this.wsUrl+"/dir/"+this.wsToken+"/get_subfolders3_and_link_by_id2/"+dirId;
        if (!ObjTools.isNullyOrEmpty(searchVal)){
            url += '?searchVal='+searchVal;
        }

        $.ajax({
            url: url,

            success: function (json) {
                if (json.result !== 'OK') {
                    defer.reject(json.info);
                    return;
                }

                defer.resolve(json);
            },
            error: function (err) {
                var strErr = Util.getAjaxErrorMessage(err);
                defer.reject(strErr);
            }
        });
        return defer.promise();


    },

    loadBreadcrumbs: function (dirId) {
        var that = this;

        that.loadBreadcrumbsData(dirId).then(function (json) {
            that.renderBreadcrumbs(json);
        }, function (err) {
            Util.alert(err, "ERROR");
        });
    },

    renderBreadcrumbs: function(json){
        var that = this;

        var breadCrumbsObj = json.dirLink;
        var tmpl_breadcrumbs = {tmpl_breadcrumbs: breadCrumbsObj};

        var html = Util.renderTemplateNonAsync(that,"tmpl_breadcrumbs", tmpl_breadcrumbs);
        $("#pm_tmpl_breadcrumbs").html(html);
    },

    loadBreadcrumbsData: function (dirId) {
        var that = this;

        var defer = $.Deferred();

        var url =this.wsUrl+"/dir/"+this.wsToken+"/get_breadcrumbs_start_stop_dll/"+that.startingDirId+'/'+dirId;

        $.ajax({
            url: url,
            onSuccess: function (json) {
                if (json.result !== 'OK') {
                    defer.reject(json.info);
                    return;
                }

                defer.resolve(json);
            },
            onError: function (err) {
                var strErr = Util.getAjaxErrorMessage(err);
                defer.reject(strErr);
            }
        });

        return defer.promise();
    },

    /*
     Function to compile all known mustache templates.
     */
    compileAllTemplates: function () {

        this.templates['tmpl_space'] = $('#tmpl_space').html();
        this.templates['tmpl_breadcrumbs'] = $('#tmpl_breadcrumbs').html();
        this.templates['tmpl_files_folders'] = $('#tmpl_files_folders').html();
        this.templates['tmpl_documents'] = $('#tmpl_documents').html();

        // parseaza toate template-urile
        $.each(this.templates, function (index, template) {
            Mustache.parse(template);
        });
    },

    /*
     Function to read basic input parameters.
     */
    readInputParameters: function () {

        var defer = $.Deferred();

        var params = new URLSearchParams(window.location.href);
        if (params.has('id')) {
            this.projectId = params.get('id');
        }
        else {
            ;
            this.projectId =
                window.sessionStorage && window.sessionStorage.getItem("activeProject");
        }

        if (typeof this.projectId !== 'undefined' && this.projectId != null) {
            this.parametersLoadedOk = true;
            defer.resolve();
        } else {
            defer.reject();
        }

        return defer.promise();
    }
};

$(document).ready(function () {
    FileManager.init();
});