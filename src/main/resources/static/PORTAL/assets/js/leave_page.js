var hasChanges = false;

function setChanges() {
    hasChanges = true;
}

function resetChanges() {
    hasChanges = false;
}

$(document).ready(function () {
    try {
        $('#resize_iframe', window.parent.document).trigger('click');
        window.parent.parent.scrollTo(0, 0);
    } catch (e) {
    }

    window.addEventListener('beforeunload', function (e) {
        if (hasChanges) {
            // Cancel the event
            e.preventDefault();
            // Chrome requires returnValue to be set
            e.returnValue = 'There are changes afoot';
        }
    });
});
