var refreshTimer = null;

function dmsDocumentGridRefreshOnHazaleCast(callBack, refreshInterval) {

     if(refreshTimer != null) {
        clearTimeout(refreshTimer);
        refreshTimer = null;
     }

    refreshTimer = setTimeout(function() {
        callBack.$server.refreshOnHazaleCast();
        console.log("callback din client");
        dmsDocumentGridRefreshOnHazaleCast(callBack, refreshInterval);
     }, refreshInterval);

}