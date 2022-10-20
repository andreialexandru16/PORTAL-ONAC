var Util = {
    getAjaxErrorMessage: function (err) {
        if (!ObjTools.isNullyOrEmpty(err)) {
            if (!ObjTools.isNullyOrEmpty(err.responseJSON) && !ObjTools.isNullyOrEmpty(err.responseJSON.message)) {
                return err.responseJSON.message;
            } else if (!ObjTools.isNullyOrEmpty(err.responseJSON) && !ObjTools.isNullyOrEmpty(err.responseJSON.error)) {
                return err.responseJSON.error;
            }
        }

        return err;
    },

    alert: function (msg, type) {
        var that = this;
        that.log(msg, type);

        if (!ObjTools.isNullyOrEmpty(msg)) {
            if (ObjTools.isNullyOrEmpty(type)) {
                type = 'INFO';
            }

            switch (type) {
                case 'INFO':
                    Swal.fire({
                        icon: 'info',
                        title: 'Info',
                        text: msg
                    });
                    break;
                case 'ERROR':
                    Swal.fire({
                        icon: 'error',
                        title: 'Eroare',
                        text: msg
                    });
                    break;
                default:
                    console.trace(msg);
            }
        }
    },

    log: function (msg, type) {
        var that = this;

        if (!ObjTools.isNullyOrEmpty(msg)) {
            if (ObjTools.isNullyOrEmpty(type)) {
                type = 'INFO';
            }

            switch (type) {
                case 'INFO':
                    console.log(msg);
                    break;
                case 'ERROR':
                    console.error(msg);
                    break;
                default:
                    console.trace(msg);
            }
        }
    },

    /*
     Render a mustache template.
     */
    renderTemplate: function (manager, templateName, data) {
        var that = this;
        var defer = $.Deferred();
        var str = null;

        try {
            str = Mustache.render(manager.templates[templateName], data);
        } catch (e) {
            that.log(e,"ERROR");
            defer.reject(e);
        }

        if (typeof str === 'undefined' || str === null) {
            defer.reject('value null');
        }

        defer.resolve(str);

        return defer.promise();
    },

    /*
     Render a mustache template.
     */
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

    openDmsPage: function(targetPage, target){
        if (target==null || target==''){
            target = '_self';
        }
        $.ajax({
            url: '/util/open/dms/'+targetPage,
            method: 'get',
            success: function (json) {
                window.open(json, target);

            },
            error: function (err) {

                window.open('#', target);

            }
        });

    }
};