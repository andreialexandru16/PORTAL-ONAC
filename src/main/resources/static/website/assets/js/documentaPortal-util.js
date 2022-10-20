/**
 * DOCUMENTA_PORTAL WS
 */
var Util = function () {

    this.userData = {};
    this.menuRights = {};

    this.STD_EXPIRE_CHECK_MINS = 5;
    this.idFluxAprobare=9909;
   this.LOG_LEVEL = "ALL"; // "OFF" ; "ERR"

    //ID REGISTRU DISPOZITII
    this.idRegistruDispozitii= "4488,5038";

    //ID REGISTRU Proiecte HCJ
    this.idRegistruProiecteHcj= 4485;

    //ID FOLDER COMISII
    this.idFolderComisii= 174488;

    //ID REGISTRU PROIECT DISPOZITII
    this.idRegistruProiectDispozitii= 4486;

    //ID GRUP CONSILIERI_JUDETENI
    this.idGrupConsilieriJudeteni=7589;

    //ID REGISTRU HCJ
    this.idRegistruHCJ= "4484,5036";

    //ID REGISTRU COMISIA1
    this.idRegistruC1=4723;

    //ID REGISTRU COMISIA2
    this.idRegistruC2=4816;

    //ID REGISTRU COMISIA3
    this.idRegistruC3=4817;

    //ID REGISTRU COMISIA4
    this.idRegistruC4=4818;

    //ID GRUP COMISIA1
    this.idGrupC1=7609;

    //ID GRUP COMISIA2
    this.idGrupC2=7610;

    //ID GRUP COMISIA3
    this.idGrupC3=7611;

    //ID GRUP COMISIA4
    this.idGrupC4=7612;



    this.log = function (page_name, function_name, text_param, is_err) {
        if ((this.LOG_LEVEL="ALL") || ((this.LOG_LEVEL="ERR") && (is_err="1"))) {
            console.log(page_name + "-" + function_name  + ':' + text_param);}
    };


    this.goToHomePage = function(){
        var link = 'index.html#pages/page-home.html';
        var paramPageHome = $UTIL.getSetting('PORTAL_DEFAULT_PAGE');

        if (typeof paramPageHome !== 'undefined' && paramPageHome !== null){
            link = 'index.html#pages/' + paramPageHome;
        }

        window.open(link, '_self');
    };

//     Dspring.profiles.active=ioio
//     Dshow.footer=true
//     Dshow.header=true
//     Dwordpress.url=
//     Ddmsws.login.url=login2
//     Ddmsws.url=http://localhost:8181/DMSWS/api/v1

    this.downloadFileByVaadin = function(filename,downloadlink){

        var url ="/dmsws/download?&downloadLink="+downloadlink;

        $.ajax({
            url: url,
            success: function (result) {
                if (result!= null) {
                    var binaryString = window.atob(result);
                    var binaryLen = binaryString.length;
                    var bytes = new Uint8Array(binaryLen);
                    for (var i = 0; i < binaryLen; i++) {
                        var ascii = binaryString.charCodeAt(i);
                        bytes[i] = ascii;
                    }
                    var blob = new Blob([bytes], {type: "application/pdf"});
                    var link=document.createElement('a');
                    link.target="_self";
                    link.href=window.URL.createObjectURL(blob);
                    link.download=filename;
                    link.click();

                }

            }
        });
    };
    this.exportXlsxByVaadin = function(filename,baseUrl,query,params,columns){
        debugger;
        var url ="/dmsws/anre/exportXlsx?&baseUrl="+baseUrl;

        var req = {
            "sql":query,
            "params":params,
            "columns":columns
        };

        var jReq=JSON.stringify(req);

        $.ajax({
            type: 'POST',
            url: url,
            data: jReq,
            contentType: "application/json",
            success: function (result) {
                if (result!= null) {
                    var binaryString = window.atob(result);
                    var binaryLen = binaryString.length;
                    var bytes = new Uint8Array(binaryLen);
                    for (var i = 0; i < binaryLen; i++) {
                        var ascii = binaryString.charCodeAt(i);
                        bytes[i] = ascii;
                    }
                    var blob = new Blob([bytes], {type: "application/octet-stream"});
                    var link=document.createElement('a');
                    link.target="_self";
                    link.href=window.URL.createObjectURL(blob);
                    link.download=filename;
                    link.click();

                }

            }, complete: function() {
                swal.close();
            }
        });
    };
    this.templates = {};

    this.corsHeaders = {
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Credentials': 'true',
        'Access-Control-Allow-Methods': 'POST, PUT, GET, OPTIONS, DELETE, HEAD',
        'Access-Control-Allow-Headers': 'Access-Control-Allow-Headers, Access-Control-Allow-Origin, Access-Control-Request-Method, Access-Control-Request-Headers, Access-Control-Allow-Credentials, Access-Control-Allow-Methods, Origin, Accept, X-Requested-With, Content-Type, X-PINGOTHER, Authorization'
    };

    this.isNotUndef = function (item) {
        return typeof item !== 'undefined';
    };

    this.encodeReqParam = function(pass){
        if(pass.includes('#')){
            pass=pass.replaceAll('#','%23');
        }
        if(pass.includes('^')){
            pass=pass.replaceAll('^','%5E');
        }
        if(pass.includes('=')){
            pass=pass.replaceAll('=','%3D');
        }
        if(pass.includes('&')){
            pass=pass.replaceAll('&','%26');
        }
        return pass;
    };

    this.isNotNull = function (item) {
        return this.isNotUndef(item) && item !== null;
    };

    this.isNotEmpty = function (item) {
        return this.isNotNull(item) && (typeof item !== 'string' || item !== '');
    };

    this.isUndef = function (item) {
        return typeof item === 'undefined';
    };

    this.isNull = function (item) {
        return this.isUndef(item) || item === null;
    };

    this.isEmpty = function (item) {
        return this.isNull(item) && typeof item === 'string' && item === '';
    };

    this.alert = function (msg, type) {
        alert(msg);
    };

    this.ajaxAction = function (options) {
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
                    if (respText.trim() === '')
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
            headers: options.headers,
            contentType: options.contentType,
            processData:options.processData
        });
    };

    this.goPost2 = function (url, dat, id, target) {
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

            if (key === 'req') {
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
    };
    this.saveUserData = function (idUser,token,prenume,expires) {
        var userData={userId: idUser, token:token,prenume:prenume,expires:expires};

        sessionStorage.setItem('userData', JSON.stringify(userData));
    };
    this.storeUserDataInSession = function () {
        sessionStorage.setItem('userData', JSON.stringify(this.userData));
    };

    this.deleteUserDataInSession = function () {
        sessionStorage.removeItem('userData');
        sessionStorage.removeItem('topMenuBar');
    };

    this.loadUserDataFromSession = function () {
        var userData = sessionStorage.getItem('userData');
        if (typeof userData === 'undefined' || userData === null || userData === '') {
            userData = "{}";
        }
        userData = JSON.parse(userData);
        this.userData = userData;

        return userData;
    };

    this.loadUserDataFromSecurityUtils = function () {

        var that = this;
        var defer = $.Deferred();

        $.ajax({
            type: 'GET',
            url: '/dmsws/utilizator/getFirstNameAndCompany',
            success: function (data1) {
                $UTIL.USER_FIRST_NAME=data1;
                $.ajax({
                    type: 'GET',
                    url: '/dmsws/utilizator/getUsername',
                    success: function (data) {
                        $UTIL.USERNAME=data;
                        defer.resolve(data1);
                    },
                    error: function (err) {
                        console.log(err);
                        defer.reject(err);
                    }
                });

            },
            error: function (err) {
                console.log(err);
                defer.reject(err);
            }
        });


        return defer.promise();

    };

    this.loadCaptchaKey = function () {

        var that = this;
        var defer = $.Deferred();

        $.ajax({
            type: 'GET',
            url: '/dmsws/api/getCaptchaKey',
            success: function (data) {
               defer.resolve(data);
                return defer.promise();
            },
            error: function (err) {
                console.log(err);
                defer.reject(err);
            }
        });


        return defer.promise();

    };
    this.getSetting = function(key){
        if (typeof key === 'undefined' || key === null
            || typeof this.userData === 'undefined' || this.userData === null
            || typeof this.userData.settings === 'undefined' || this.userData.settings === null
            || typeof this.userData.settings[key] === 'undefined' || this.userData.settings[key] === null){
            return null;
        }

        return this.userData.settings[key]
    };
    this.doLogin = function (username, password) {

        var that = this;
        var defer = $.Deferred();

        var req = {
            username: username,
            password: password
        };

        var jReq = JSON.stringify(req);

        this.ajaxAction({
            url: this.WS_URL + '/login/',
            method: 'put',
            headers: that.mergeObjects(that.corsHeaders, {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }),
            data: jReq,
            onSuccess: function (data) {
                if (typeof data !== 'undefined' && data
                    && typeof data.valid !== 'undefined' && data.valid) {

                    defer.resolve(data);
                } else {
                    $.ajax({
                        type: 'POST',
                        //url: this.WS_URL + '/utilizator/' + this.LINK_WS_TOKEN + '/updateLoginFailed/' + username + '/',
                        url: '/dmsws/utilizator/updateLoginFailed/' + username + '/',
                        success: function (data) {
                            if (data.result == 'OK') {
                                if(data.locked==1){
                                    Swal.fire({
                                        icon: 'error',
                                        html: "Contul a fost blocat din cauza depăşirii incercărilor permise!",
                                        focusConfirm: false,
                                        confirmButtonText: 'Ok'
                                    });
                                }else{
                                    Swal.fire({
                                        icon: 'error',
                                        html: "Username sau parola greșită!<br/> Încercări rămase:"+data.loginFailRemains,
                                        focusConfirm: false,
                                        confirmButtonText: 'Ok'
                                    });
                                }
                            }
                            else{
                                Swal.fire({
                                    icon: 'error',
                                    html: "Username sau parola greșită!<br/>",
                                    focusConfirm: false,
                                    confirmButtonText: 'Ok'
                                });
                            }

                        }
                    });

                }
            },
            onError: function (data) {
                var json = true;
                var respText = data['responseText'];
                try {
                    var respObj = JSON.parse(respText);
                    respText = respObj['error'];
                } catch (e) {
                    json = false;
                }
                if (!json) {
                    if (respText.trim() === '')
                        respText = "Server error";
                }

                defer.reject(respText);
            }
        });

        return defer.promise();
    };

    this.checkToken = function () {
        var that = this;
        var defer = $.Deferred();

        if (!that.userIsLoggedIn()) {
            that.redirectToLogin();
        } else if (!that.extendNeeded()) {
            defer.resolve();
        } else {
            that.extendToken().then(function (data) {
                    console.log('token extended');
                    if (typeof data !== null) {
                        console.log(JSON.stringify(data));
                    }
                    that.userData.token = data.token;
                    that.userData.expires = data.expires;
                    that.storeUserDataInSession();
                    defer.resolve();
                },
                function (err) {
                    defer.reject(err);
                });
        }

        return defer.promise();
    };

    this.checkTokenSync = function (onOk, onErr) {
        var that = this;

        if (!that.userIsLoggedIn()) {
            that.redirectToLogin();
        } else if (!that.extendNeeded()) {
            onOk();
        } else {
            that.extendTokenSync(function(data){
                console.log('token extended');
                if (typeof data !== null) {
                    console.log(JSON.stringify(data));
                }
                that.userData.token = data.token;
                that.userData.expires = data.expires;
                that.storeUserDataInSession();
                onOk();
            }, function(err){
                onErr(err);
            })
        }
    };

    this.extendToken = function () {
        var that = this;
        var defer = $.Deferred();

        this.ajaxAction({
            url: this.WS_URL + '/api/v1/login/extend/' + this.userData.token,
            method: 'put',
            headers: that.mergeObjects(that.corsHeaders, {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }),
            onSuccess: function (data) {
                if (typeof data !== 'undefined' && data
                    && typeof data.expires !== 'undefined' && data.expires) {

                    defer.resolve(data);
                } else {
                    var err = data.result_msg;
                    if (typeof err === 'undefined' || !err) {
                        err = 'Unable to extend token.'
                    }

                    defer.reject(err);
                }
            },
            onError: function (data) {
                var json = true;
                var respText = data['responseText'];
                try {
                    var respObj = JSON.parse(respText);
                    respText = respObj['error'];
                } catch (e) {
                    json = false;
                }
                if (!json) {
                    if (respText.trim() === '')
                        respText = "Server error";
                }

                defer.reject(respText);
            }
        });

        return defer.promise();
    };

    this.extendTokenSync = function (onOk, onErr) {
        var that = this;

        this.ajaxAction({
            url: this.WS_URL + '/api/v1/login/extend/' + this.userData.token,
            method: 'put',
            async: false,
            headers: that.mergeObjects(that.corsHeaders, {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }),
            onSuccess: function (data) {
                if (typeof data !== 'undefined' && data
                    && typeof data.expires !== 'undefined' && data.expires) {

                    onOk(data);
                } else {
                    var err = data.result_msg;
                    if (typeof err === 'undefined' || !err) {
                        err = 'Unable to extend token.'
                    }

                    onErr(err);
                }
            },
            onError: function (data) {
                var json = true;
                var respText = data['responseText'];
                try {
                    var respObj = JSON.parse(respText);
                    respText = respObj['error'];
                } catch (e) {
                    json = false;
                }
                if (!json) {
                    if (respText.trim() === '')
                        respText = "Server error";
                }

                onErr(respText);
            }
        });
    };

    this.extendNeeded = function(){
        return this.tokenExpiresInMinutes(this.STD_EXPIRE_CHECK_MINS) || this.tokenIsExpired();
    };

    this.tokenExpiresInMinutes = function(mins){
        if (typeof this.userData !== 'undefined' && this.userData !== null){
            var date = new Date(this.userData.expires);
            var now = new Date();

            return date.getTime() > now.getTime() && date.getTime() - now.getTime() <= mins * 1000 * 60;
        }

        return true;
    };

    this.tokenIsExpired = function(){
        if (typeof this.userData !== 'undefined' && this.userData !== null){
            var date = new Date(this.userData.expires);
            var now = new Date();

            return now.getTime() > date.getTime();
        }

        return true;
    };

    this.mergeObjects = function(object1, object2){
        var obj = {};

        function merge(obj1, obj2){
            $.each(obj2, function(key, value) {
                obj1[key] = value;
            });
        }

        if (typeof object1 !== 'undefined' && object1 !== null){
            merge(obj, object1);
        }

        if (typeof object2 !== 'undefined' && object2 !== null){
            merge(obj, object2);
        }

        return obj;
    };

    this.redirectTo = function(url){
        window.location.href = url;

    };
    this.redirectToTarget = function(url, target){
        //window.open (url,target);
	window.top.location.href=url;
    };
    this.redirectToLogin = function(){
        this.redirectTo('login.html');
    };

    this.redirectToIndex = function(){
        this.redirectTo('index.html');
    };

    this.captchaCheck = function(username,password,lang){

        this.login(username,password,lang)

        /*  var response ="true";    try{        response = grecaptcha.getResponse(0);    }catch (err){    }
         if(response !="") {
             this.login(username,password,lang)

         }else{
             //reCaptcha not verified
             Swal.fire({
                 icon: 'error',
                 html: "Captcha gresit!",
                 focusConfirm: false,
                 confirmButtonText: 'Ok',
             });

         }*/
    };

    this.login = function (username, password, lang) {
        var that = this;

            this.doLogin(username, password).then(function (data) {

                that.userData = data;
                that.userData.lang = lang;
                that.storeUserDataInSession();
                top.location.href=$UTIL.WORDPRESS_URL;
            }, function (err) {
                that.alert(err);

            });


    };
    this.checkBlockedUser = function (username, password, lang) {


         var response ="true";    try{        response = grecaptcha.getResponse(0);    }catch (err){    }
        if(response == "false") {

            //reCaptcha not verified
            Swal.fire({
                icon: 'error',
                html: "Captcha gresit!",
                focusConfirm: false,
                confirmButtonText: 'Ok',
            });
        } else {
            $.ajax({
               // url: this.WS_URL + '/utilizator/' + this.LINK_WS_TOKEN + '/getUserInfoByUsername/' + username + '/',
                url: '/dmsws/utilizator/getUserInfoByUsername/' + username ,
                type: 'GET',
                success: function (data) {
                    if (data.result == 'OK') {
                        if (data.locked == 1) {
                            Swal.fire({
                                icon: 'error',
                                html: "<p>Contul a fost blocat din cauza depăşirii incercărilor permise pentru o perioada de "+data.extendedInfo2+" zile.</p><p>După acest interval va fi deblocat automat. Pentru urgențe ne puteți contacta folosind informațiile din pagina de contact.</p> ",
                                focusConfirm: false,
                                confirmButtonText: 'Ok',
                            });
                        }
                        if (data.activ == 0) {
                            Swal.fire({
                                icon: 'error',
                                html: "Contul nu a fost activat !",
                                focusConfirm: false,
                                confirmButtonText: 'Ok',
                            });
                        }
                        if(data.parola=='expired'){
                            Swal.fire({
                                icon: 'error',
                                html: "Acest utilizator are parola expirată!",
                                focusConfirm: false,
                                confirmButtonText: 'Ok',
                                    onClose: () => {
                                window.location.href = 'resetare-parola.html';
                        }});


                        }
                        if ((data.locked == null||data.locked==0) && data.activ == 1 && data.parola!='expired') {
                            $UTIL.login(username, password, lang)
                        }
                    } else {
                        Swal.fire({
                            icon: 'error',
                            html: "Username sau parola greșită !",
                            focusConfirm: false,
                            confirmButtonText: 'Ok'
                        });
                    }
                },
                error: function (data) {
                    Swal.fire({
                        icon: 'error',
                        html: "Username sau parola greșită!",
                        focusConfirm: false,
                        confirmButtonText: 'Ok'
                    });
                }
            });
        }

    };

    this.logout = function () {

        var that = this;
        this.deleteUserDataInSession();
        top.location.href='/logout';
    };

    this.populateLoginFromUrl = function(url){
        var that = this;

        var obj = {};
        var params = new URLSearchParams(window.location.href);
        if (params.has('wsUrl')){
            obj.wsUrl = params.get('wsUrl');
        }

        if (params.has('wsToken')){
            obj.wsToken = params.get('wsToken');
        }

        this.ajaxAction({
            url: obj.wsUrl + '/api/v1/login/get_user_data_for_token/' + obj.wsToken,
            method: 'get',
            async: false,
            headers: that.mergeObjects(that.corsHeaders, {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }),
            onSuccess: function (data) {
                if (typeof data !== 'undefined' && data
                    && typeof data.expires !== 'undefined' && data.expires) {

                    that.userData = data;
                } else {
                    var err = data.result_msg;
                    if (typeof err === 'undefined' || !err) {
                        err = 'Unable to get token.'
                    }
                }
            },
            onError: function (data) {
                var json = true;
                var respText = data['responseText'];
                try {
                    var respObj = JSON.parse(respText);
                    respText = respObj['error'];
                } catch (e) {
                    json = false;
                }
                if (!json) {
                    if (respText.trim() === '')
                        respText = "Server error";
                    }
            }
        });
    };

    this.checkUserGroup = function(idGrup,wsToken){

        var that=this;
        var result;
        this.ajaxAction({
            url: $UTIL.WS_URL + '/utilizator/' + wsToken+'/checkUserGroupMember/'+idGrup,
            method: 'get',
            async: false,
            headers: that.mergeObjects(that.corsHeaders, {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }),
            onSuccess: function (data) {
                if (data.id!=null) {
                    result= true;
                } else {
                    var err = data.result_msg;
                    if (typeof err === 'undefined' || !err) {
                        err = 'Unable to get token.'
                    }
                    result= false;
                }
            },
            onError: function (data) {
                var json = true;
                var respText = data['responseText'];
                try {
                    var respObj = JSON.parse(respText);
                    respText = respObj['error'];
                } catch (e) {
                    json = false;
                }
                if (!json) {
                    if (respText.trim() === '')
                        respText = "Server error";
                }
                result= false;
            }
        });

        return result;

    };


    this.checkRegistruAccess = function(idRegistru,wsToken,drept){

        var that=this;
        var check;
        var defer = $.Deferred();
        var url=$UTIL.WS_URL + '/registratura_portal/' + wsToken+'/getDrepturiAcess/'+idRegistru;
        if(drept!==null &&drept!=undefined&&drept!=''){
            url=url+'?drept='+drept;
        }
        this.ajaxAction({
            url: url,
            async: false,

            onSuccess: function (data) {
                if (data.info==1) {
                    result= true;
                } else {
                    var err = data.result_msg;
                    if (typeof err === 'undefined' || !err) {
                        err = 'Unable to get token.'
                    }
                    result= false;
                }
            },
            onError: function (data) {
                var json = true;
                var respText = data['responseText'];
                try {
                    var respObj = JSON.parse(respText);
                    respText = respObj['error'];
                } catch (e) {
                    json = false;
                }
                if (!json) {
                    if (respText.trim() === '')
                        respText = "Server error";
                }
                result= false;
            }
        });

        return result;

    },
        this.getTipDocument = function(idDocument){

            var that=this;
            var url='/document/getDocumentById/'+idDocument+'/';
            var result=null;
            this.ajaxAction({
                url: url,
                async: false,

                onSuccess: function (data) {
                    if (data.result!="ERR") {
                        result= data;
                    }
                },
                onError: function (data) {
                    var json = true;
                    var respText = data['responseText'];
                    try {
                        var respObj = JSON.parse(respText);
                        respText = respObj['error'];
                    } catch (e) {
                        json = false;
                    }
                    if (!json) {
                        if (respText.trim() === '')
                            respText = "Server error";
                    }
                    result= false;
                }
            });

            return result;

        },

    this.sendSms = function(mesaj,telefon){

        var that=this;
        var sms={
            mesaj:mesaj,
            telefon:telefon
        };
        this.ajaxAction({
            url: $UTIL.WS_URL + '/sms/' + wsToken+'/addNewSms',
            method: 'POST',
            data:sms,
            headers: that.mergeObjects(that.corsHeaders, {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }),
            onSuccess: function (data) {
                if (data.id!=null) {
                    result= true;
                } else {
                    var err = data.result_msg;
                    if (typeof err === 'undefined' || !err) {
                        err = 'Unable to get token.'
                    }
                    result= false;
                }
            },
            onError: function (data) {
                var json = true;
                var respText = data['responseText'];
                try {
                    var respObj = JSON.parse(respText);
                    respText = respObj['error'];
                } catch (e) {
                    json = false;
                }
                if (!json) {
                    if (respText.trim() === '')
                        respText = "Server error";
                }
                result= false;
            }
        });

        return result;

    };


this.waitForLoading=function(){


    Swal.fire({
            title:"Va rugam asteptati",
            showConfirmButton:true,
            allowOutsideClick: false,

            onBeforeOpen: () => {
            Swal.showLoading()
},



});
}
    this.userIsLoggedIn = function(optionalUrl){
        var that = this;

        function check(){
            return typeof that.userData !== 'undefined' && that.userData !== null
                && typeof that.userData.token !== 'undefined' && that.userData.token !== null;
        }

        this.loadUserDataFromSession();

        if (check()){
            return true;
        }

        return false;
    };

    this.userIsLoggedInVaadin = function(optionalUrl){
        var that = this;
        var checkLogged = new Promise(function (resolve, reject) {
            checkLoggedVaadin(resolve);
        });
        Promise.all([checkLogged]).then(function (values) {

                return values[0];

        });


    };

    this.checkTokenAndExtend = function() {



        /*  var tokenExpiresInMinutesR = new Promise(function (resolve, reject) {
         tokenExpiresInMinutes(window.portal.STD_MIN_CHECK,resolve);
         });*/
        var tokenIsExpiredR = new Promise(function (resolve, reject) {
            tokenIsExpired(resolve);
        });
        Promise.all([tokenIsExpiredR]).then(function (values) {
            if (
                values[0] === true
            ) {
                Swal.fire({
                    icon: 'info',
                    html: "Din motive de securitate va rugam sa va relogati!",
                    focusConfirm: false,
                    confirmButtonText: 'Ok',
                    onClose: () => {  window.location.href = "/logout";
            }

            });
            }

        });
    }
     checkLoggedVaadin=function(resolve){
        $.ajax({

            type: 'GET',
            url: '/dmsws/utilizator/userIsLogged',
            success: function (data) {

                resolve((data!=null && data== 'true'));
            }

        });
    }
     tokenIsExpired=function(resolve){

        var that = this;
        this.userData= $UTIL.loadUserDataFromSession();
        var date = new Date(this.userData.expires);
        var now = new Date();
        var resp = now.getTime() > date.getTime();
        if(typeof resolve=='function'){
            resolve(resp);
        }


    }
    this.getMenuRights = function(){
        var that = this;
        var defer = $.Deferred();

        this.checkToken().then(function(){
            that.ajaxAction({
                url: $UTIL.WS_URL + '/api/v1/portal_e/' + that.userData.token +'/getPortalMenuRights',
                method: 'get',
                headers: that.mergeObjects(that.corsHeaders, {
                    'Accept': 'application/json'
                }),
                onSuccess: function (data) {
                    if(typeof data !== 'undefined' && data !== null){
                        defer.resolve(data);
                    } else {
                        defer.reject('Unknown error.');
                    }
                },
                onError: function (data) {
                    var json = true;
                    var respText = data['responseText'];
                    try {
                        var respObj = JSON.parse(respText);
                        respText = respObj['error'];
                    } catch (e) {
                        json = false;
                    }
                    if (!json) {
                        if (respText.trim() === '')
                            respText = "Server error";
                    }

                    defer.reject(respText);
                }
            });
        }, function(){
            defer.reject('cannot check or extend token!');
        });

        return defer.promise();
    };


    this.renderTemplate = function (templateName, data) {
        var defer = $.Deferred();
        var str = null;

        if (typeof this.templates[templateName] === 'undefined' || this.templates[templateName] === null){
            defer.reject('Template: ' + templateName + ' not loaded');
        } else {
            try {
                str = Mustache.render(this.templates[templateName], data);
                if (typeof str === 'undefined' || str === null){
                    defer.reject('Template: + ' + templateName + ' compiled to null / undefined.');
                }
            } catch (e) {
                console.log(e);
                defer.reject(e);
            }
        }

        defer.resolve(str);

        return defer.promise();
    };

    this.addTemplate = function (templateName, templateData){
        if (typeof this.templates[templateName] === 'undefined' || this.templates[templateName] === null){
            this.templates[templateName] = templateData;
            Mustache.parse(this.templates[templateName]);
        }
    };

    this.search = function(value){

        if (typeof this.searchFunction !== 'undefined' && this.searchFunction !== null){
            this.searchFunction.apply(null, [value]);
        }
    };

    this.highRowCheckboxSelection = function (checkbox) {
        $('tr.success').removeClass('success');$(this).closest('tr').addClass('success')
    }
    this.matchExpression = function(str) {

        var rgularExp = {
            contains_alphaNumeric: /^(?!-)(?!.*-)[a-zA-ZÀ-žșȘțȚ0-9-]+[^-]$/,
            containsNumber: /\d+/,
            containsAlphabet: /[a-zA-ZÀ-žșȘțȚ]/,

            onlyLetters: /^[.a-zA-ZÀ-žșȘțȚ ]*$/,
            onlyLettersNumbersSpaceUnderscore:  /^[_0-9a-zA-ZÀ-žșȘțȚ\s]*$/,
            onlyNumbers: /^[0-9]+$/,
            onlyMixOfAlphaNumeric: /^([0-9]+[a-zA-ZÀ-žșȘțȚ]+|[a-zA-ZÀ-žșȘțȚ]+[0-9]+)[0-9a-zA-ZÀ-žșȘțȚ]*$/,
            onlyLettersNumbersSlash: /^([0-9a-zA-ZÀ-žșȘțȚ\/\_]*)$/
        }

        var expMatch = {};
        expMatch.containsNumber = rgularExp.containsNumber.test(str);
        expMatch.containsAlphabet = rgularExp.containsAlphabet.test(str);
        expMatch.alphaNumeric = rgularExp.contains_alphaNumeric.test(str);

        expMatch.onlyNumbers = rgularExp.onlyNumbers.test(str);
        expMatch.onlyLetters = rgularExp.onlyLetters.test(str);
        expMatch.mixOfAlphaNumeric = rgularExp.onlyMixOfAlphaNumeric.test(str);

        return expMatch;
    }

    this.notValid = function(elem){

        var snack = "Corectați valorile celulor modificate";
        var alert = "Această celulă trebuie să conțină ";
        switch ($(elem).attr('data-validation')) {
            case 'alphaNumeric':
                alert += "doar caractere alfa-numerice.";
                break;
            case 'containsAlphabet':
                alert += "și caractere alfa.";
                break;
            case 'containsNumber':
                alert += "și caractere numerice.";
                break;
            case 'mixOfAlphaNumeric':
                break;
            case 'onlyLetters':
                alert += "doar caractere alfa.";
                break;
            case 'onlyLettersNumbersSlash':
                alert += "doar caractere alfa-numerice și /.";
                break;
            case 'onlyLettersNumbersSpaceUnderscore':
                alert += "doar caractere alfa-numerice, spatii și _ .";
                break;

            case 'onlyNumbers':
                alert += "doar caractere numerice.";
                break;
        }
        $(elem).closest('td').addClass('not-valid');
        $(elem).closest('td').attr('data-toggle', 'tooltip');
        $(elem).closest('td').attr('data-placement', 'top');
        $(elem).closest('td').attr('data-container', 'body');
        $(elem).closest('td').attr('title', alert);

        $(elem).closest('td').tooltip();
        setTimeout(function () {
            $(elem).closest('td').trigger('mouseover');
        }, 200)

        Swal.fire({
            icon: 'error',
            title: snack,
            text: alert
        });
    }

    this.isValid = function(elem){
        $(elem).closest('td').removeClass('not-valid');
        $(elem).closest('td').removeAttr('data-toggle');
        $(elem).closest('td').removeAttr('data-placement');
        $(elem).closest('td').removeAttr('data-container');
        $(elem).closest('td').removeAttr('title');
        $(elem).closest('td').tooltip("destroy");
    }
    this.validateEmail=function(email) {
        var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        return re.test(String(email).toLowerCase()) || email=='';
    }
    this.getMltipleRequest = function (requestsAsArray) {
        var that = this;
        var defer = $.Deferred();

        var allRequests = [];
        for(var i=0;i<requestsAsArray.length;i++){
           var req =  $.ajax({
                url: requestsAsArray[i],
                method: "GET",
                async: false,
                headers: that.mergeObjects(that.corsHeaders, {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                })
            });

            allRequests.push(req);
        }
        $.when(allRequests).done(function ( response ) {
            defer.resolve(response);
        });

        return defer.promise();
    }

    this.getTemplate = function (template) {
        var defer = $.Deferred();
        $.get(template, function( response ) {
            defer.resolve(response);
        });
        return defer.promise();
    }

    this.generateHead = function (data,template) {

        var ret_th = "";
        ret_th += "<th></th>";
        for(var i=0;i<data.length;i++){
            var render_th           = Mustache.render(template, data[i]);
            ret_th += render_th;
        }
        ret_th += "<th colspan='2'></th>"
        return "<tr>"+ret_th+"</tr>";
    }

    this.returnWhenGet = function (ret) {
        if(typeof ret === "object"){
            return ret[0];
        }else{
            return ret;
        }
    }
    
    this.generateBody = function (data, lovl, template_row) {
        var ret_opt="";
        for(var x=0;x<lovl.length;x++){
            var render_option  = Mustache.render("<option value='{{ value }}'>{{ label }}</option>", {
                value:lovl[x].id,
                label:lovl[x].value
            });
            ret_opt += render_option;
        }

        var ret_td = "";
        for(var i=0;i<data.length;i++){
            data[i].options = ret_opt;
            var render_td  = Mustache.render(template_row, data[i]);
            ret_td += render_td;
        }
        return ret_td;
    }

    this.generateRow = function (data, lovl, template_row) {
        var ret_opt="";
        for(var x=0;x<lovl.length;x++){
            var render_option  = Mustache.render("<option value='{{ value }}'>{{ label }}</option>", {
                value:lovl[x].id,
                label:lovl[x].value
            });
            ret_opt += render_option;
        }

        var ret_td = "";
        data[i].options = ret_opt;
        var render_td  = Mustache.render(template_row, data);
        ret_td += render_td;

        return ret_td;
    }

    this.generateLOV = function (lovl) {
        var ret_opt="<option value=''>&nbsp;</option>";
        for(var x=0;x<lovl.length;x++){
            var render_option  = Mustache.render("<option value='{{ value }}'>{{ label }}</option>", {
                value:lovl[x].id,
                label:lovl[x].value
            });
            ret_opt += render_option;
        }
        return ret_opt;
    }

    this.enableUpdate = function () {
        $("input, select").on("change",function () {
            $(this).closest("tr").find(".btn-update").prop("disabled",false);
            $(this).closest("tr").find("input.btnSelect").iCheck('check');
            var wahtTU = $(this).attr("data-update-row");
            var whereTU = $(this).val();
            $(this).closest("tr").attr(wahtTU,whereTU);

        })
    }

    this.updateRow = function (ws) {
        $(".btn-update").click(function () {
            var defer = $.Deferred();
            var row = $(this).closest("tr");
            var rowId  = $(row).data();
            $UTIL.ajaxAction({
                url: $UTIL.WS_URL + $UTIL.WS_API + ws + $UTIL.userData.token + "/edit",
                method: 'PUT',
                headers: $UTIL.mergeObjects($UTIL.corsHeaders, {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }),
                data: {
                    id: rowId
                },
                onSuccess: function (response) {
                    defer.resolve(response);
                },
                onError: function (error) {

                }
            });
            return defer.promise();
        });
    }

    this.updateConfirmDelete = function (id) {
        $("#for-delete-confirmation").val(id);
    }

    this.addRow = function (ws) {
        var defer = $.Deferred();
        $(".btn-save").click(function () {
            var form = $("#add_form")
            var model = {
                "id_punct_lucru": null,
                "id_unitate_logistica": null,
                "data": null,
                "nr_linii": null,
                "inchisa": false
            }

            var data = $UTIL.serializeForm(form,model);
            $UTIL.ajaxAction({
                url: $UTIL.WS_URL + $UTIL.WS_API + ws + $UTIL.userData.token + "/add",
                method: 'post',
                headers: $UTIL.mergeObjects($UTIL.corsHeaders, {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }),
                data: data,
                onSuccess: function (response) {
                    defer.resolve(response,data);
                },
                onError: function (error) {

                }
            })
        });
        return defer.promise();
    }

    this.deleteRow = function (ws) {
        $(".btn-confirm-delete").click(function () {
            var defer = $.Deferred();
            var idRow =  $("#for-delete-confirmation").val(id);
            $UTIL.ajaxAction({
                url: $UTIL.WS_URL + $UTIL.WS_API + ws + $UTIL.userData.token + "/delete",
                method: 'post',
                headers: $UTIL.mergeObjects($UTIL.corsHeaders, {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }),
                data: {
                    id: idRow
                },
                onSuccess: function (response) {
                    $("[data-id='idRow']").remove();
                    defer.resolve(response);
                },
                onError: function (error) {

                }
            });
            return defer.promise();
        });
    }

    this.serializeForm = function (form, model) {
        var data = {};
        $(form).serializeArray().map(function(x){data[x.name] = x.value;});
        for(var key in model){
            if(data[key] !== undefined && data[key] != "")
                model[key] = data[key];
        }
        return JSON.stringify(model);
    }

    this.readURL = function (input,image) {

        if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
                $(image).attr('src', e.target.result);
            };

            reader.readAsDataURL(input.files[0]);
        }
    }

    /**
     * Functie de dus la afisat edit fisier per id.
     *
     * @param id id fisier.
     */
    this.goToFileEdit = function(id) {
        var link = "index.html#pages/file-properties.html?&p=" + this.encB64Params({
                fileId: id
            });
        window.open(link, '_self');
    };

    /**
     * Functie de redirect la o pagina Dms data prin param folosind tagetPage..
     * Foloseste ws si token de redirect.
     * Alex M - 08.10.2019 - E62013 - adaugat acest link pt ca raja s-a pus pe 2 baze/ws diferite.
     */
    this.goToDmsLinkPageByTargetPage = function(targetPage, target){
        if (ObjTools.isNullyOrEmpty(target)){
            target = '_self';
        }

        var link = this.LINK_DMS_URL + "/go_get.jsp?ws_token=" + $UTIL.userData.token  + "&targetPage=" + targetPage;
        window.open(link, target);
    };

    /**
     * Functie de redirect la o pagina DmsLight data prin param folosind pageName.
     * Foloseste ws si token de redirect.
     * Alex M - 08.10.2019 - E62013 - adaugat acest link pt ca raja s-a pus pe 2 baze/ws diferite.
     */
    this.goToLightLinkPageByPageName = function(pageName){
        var link = this.LINK_DMSLIGHT_URL + "/" + pageName + "/menu/0/lang/RO/token/" + this.LINK_WS_TOKEN;
        window.open(link, '_self');
    };
    // GN - 27.11.2019 - adaugat aceasta metoda pentru redirectionare din Portal catre Registratura
    this.goToLightLinkPageByName = function(pageName,target){
        var link = this.LINK_DMSLIGHT_URL + "/" + pageName + "/menu/0/lang/RO/token/" +  $UTIL.userData.token;
        window.open(link, target);
    };
    /**
     * Functie de redirect la pagina de tichete.
     * Foloseste ws si token de redirect.
     * Alex M - 08.10.2019 - E62013 - adaugat acest link pt ca raja s-a pus pe 2 baze/ws diferite.
     */
    this.goToLinkPageKanban = function(target){
        this.goToDmsLinkPageByTargetPage('kanban.jsp', target);
    };

    /**
     * Functie de redirect la pagina de registratura.
     * Foloseste ws si token de redirect.
     * Alex M - 08.10.2019 - E62013 - adaugat acest link pt ca raja s-a pus pe 2 baze/ws diferite.
     */
    this.goToLinkPageRegistratura = function(target){
        this.goToLightLinkPageByName('registratura',target);
    };

    /**
     * Functie de redirect la pagina de audit.
     * Foloseste ws si token de redirect.
     * Alex M - 08.10.2019 - E62013 - adaugat acest link pt ca raja s-a pus pe 2 baze/ws diferite.
     */
    this.goToLinkPageAudit = function(target){
        this.goToDmsLinkPageByTargetPage('vizualizare_audit.jsp', target);
    };

    /**
     * Functie de redirect la pagina de monitorizare useri.
     * Foloseste ws si token de redirect.
     * Alex M - 08.10.2019 - E62013 - adaugat acest link pt ca raja s-a pus pe 2 baze/ws diferite.
     */
    this.goToLinkPageMonitUseri = function(target){
        this.goToDmsLinkPageByTargetPage('logged_in_users.jsp', target);
    };

    /**
     * Functie de redirect la pagina de acces ip.
     * Foloseste ws si token de redirect.
     * Alex M - 08.10.2019 - E62013 - adaugat acest link pt ca raja s-a pus pe 2 baze/ws diferite.
     */
    this.goToLinkPageAccesIp = function(target){
        this.goToDmsLinkPageByTargetPage('user_ip_secure.jsp', target);
    };

    /**
     * Functie de redirect la pagina de admin useri.
     * Foloseste ws si token de redirect.
     * Alex M - 08.10.2019 - E62013 - adaugat acest link pt ca raja s-a pus pe 2 baze/ws diferite.
     */
    this.goToLinkPageAdminUseri = function(target){
        this.goToDmsLinkPageByTargetPage('utilizatori.jsp', target);
    };

    /**
     * Functie de redirect la pagina de admin contracte.
     * Foloseste ws si token de redirect.
     * Alex M - 08.10.2019 - E62013 - adaugat acest link pt ca raja s-a pus pe 2 baze/ws diferite.
     */
    this.goToLinkPageAdminContracte = function(target){
        this.goToDmsLinkPageByTargetPage('contacte.jsp', target);
    };

    /**
     * Functie de redirect la pagina de consola.
     * Foloseste ws si token de redirect.
     * Alex M - 08.10.2019 - E62013 - adaugat acest link pt ca raja s-a pus pe 2 baze/ws diferite.
     */
    this.goToLinkPageConsola = function(target){
        this.goToDmsLinkPageByTargetPage('consola.jsp', target);
    };

    /**
     * Functie de redirect la pagina de calendar.
     * Foloseste ws si token de redirect.
     * Alex M - 08.10.2019 - E62013 - adaugat acest link pt ca raja s-a pus pe 2 baze/ws diferite.
     */

    this.goToLinkPageCalendar = function(target){
        this.goToDmsLinkPageByTargetPage('jqcalendar.jsp', target);
    };

    this.goToLinkPageInbox = function(target){
        //this.goToDmsLinkPageByTargetPage('fluxuri.jsp', target);

        var link = "portalFlow/index.html?&wsUrl=" + $UTIL.WS_URL + "&wsToken=" + $UTIL.userData.token;
        var sendFluxWindow = window.open(link, '_blank');
    };


};

(function ($) {
    $.fn.serializeFormJSON = function () {

        var o = {};
        var a = this.serializeArray();
        $.each(a, function () {
            if (o[this.name]) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return o;
    };
})(jQuery);


Number.prototype.round = function(places) {
    return +(Math.round(this + "e+" + places)  + "e-" + places);
};

$UTIL = new Util();
window.$UTIL = $UTIL;
jQuery(document).ready(function ($) {

    $.ajax({

        type: 'GET',
        url: '/dmsws/utilizator/getWorpresUrl',
        success: function (data) {
            debugger;
            $UTIL.WORDPRESS_URL=data;
            $("#createAccount").attr('href',$UTIL.WORDPRESS_URL+"inregistrare");
            $("#resetPpass").attr('href',$UTIL.WORDPRESS_URL+"resetare-parola");

        }

    });
    $.ajax({

        type: 'GET',
        url: '/dmsws/notifAmenzi/getNotifAmenzi',
        success: function (data) {
            $UTIL.NOTIF_AMENZI=data;
        }

    });
    //this.WS_URL="http://localhost:8181/DMSWS/api/v1";
    $.ajax({
        type: 'GET',
        url: '/dmsws/utilizator/getWsUrl',
        success: function (data) {

            $UTIL.WS_URL=data;
        }
    });

    $.ajax({
        type: 'GET',
        url: '/dmsws/utilizator/getPortalUrl',
        success: function (data) {
            $UTIL.PORTAL_URL=data;
            //$("#resetPpass").attr('href',$UTIL.PORTAL_URL==""?"/PORTAL/resetare-parola.html":$UTIL.PORTAL_URL+"website/resetare-parola.html");
            //$("#resetPpass").attr('href',$UTIL.WORDPRESS_URL+"resetare-parola");

        }
    });

    $.ajax({
        type: 'GET',
        url: '/dmsws/utilizator/getAnonymousToken',
        success: function (data) {
            $UTIL.LINK_WS_TOKEN=data;
        }
    });
    $('#resize_iframe', window.parent.document).trigger('click');


    setInterval($UTIL.checkTokenAndExtend, 1 * 10000 * 60 );
});
