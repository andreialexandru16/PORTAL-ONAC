var refreshTimer = null;

//var callbackRegistry = [];

function passiveViewClientUpdate(callback, refreshInterval) {

//    addCallbackCallable("clientUpdate", callback);

     if(refreshTimer != null) {
        clearTimeout(refreshTimer);
        refreshTimer = null;
     }

    refreshTimer = setTimeout(function() {
        callback.$server.clientUpdate();
        console.log("callback din client");
        passiveViewClientUpdate(callback, refreshInterval);
     }, refreshInterval);

}


//function addCallbackCallable(callable, callback) {
//    callbackRegistry.push({
//        callable: callable,
//        callback: callback
//    });
//}
//
//function getServerCallback(callable) {
//
//    for(var i = 0; i < callbackRegistry.length; ++i) {
//        if(callbackRegistry[i].callable == callable) {
//            return callbackRegistry[i].callback;
//        }
//    }
//
//    return null;
//}