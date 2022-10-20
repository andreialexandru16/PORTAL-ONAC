//this need fabric.js in route


var holoCans = [];

function initHolographicAttributeLinkComponent(hc, imageData , callback) {
    var canvas = new fabric.Canvas(hc, {
        isDrawingMode: true,
        timeoutReference:0,
        width: $('#'+hc).width()
    });
    
    canvas.on('mouse:up', function(evt){

            for(var i=0;i<holoCans.length; ++i ) {
                if(holoCans[i].id == hc) {
                    holoCans[i].hasDrawn = true;
                }
            }
        canvas=canvas.loadFromJSON(canvas.toJSON());
        var image = copyImage(canvas);


        //apelam functia care salveaza semnatura
        setTimeout(applyHoloCanvas(hc,callback , image), 1000)
    });

    canvas.on('mouse:drag', function(evt){

            for(var i=0;i<holoCans.length; ++i ) {
                if(holoCans[i].id == hc) {
                    holoCans[i].hasDrawn = true;
                }
            }
        var image = copyImage(canvas);

        //apelam functia care salveaza semnatura
        setTimeout(applyHoloCanvas(hc,callback , image), 1000)
    });

    canvas.on('touch:drag', function(evt){

            for(var i=0;i<holoCans.length; ++i ) {
                if(holoCans[i].id == hc) {
                    holoCans[i].hasDrawn = true;
                }
            }
        var image = copyImage(canvas);
        //apelam functia care salveaza semnatura
        setTimeout(applyHoloCanvas(hc,callback , image), 1000)

    });

    if(imageData.length > 0) {
        imageData = JSON.parse(imageData);
        canvas.loadFromDatalessJSON(imageData);
        holoCans.push({
            id: hc,
            hasDrawn: true,
            canvas: canvas
        });
    } else {
       holoCans.push({
           id: hc,
           hasDrawn: false,
           canvas: canvas
       });
    }
//// Declaring the variables
//    var isMouseDown = false;
//
//    canvas.on('mouse:move', function(event) {
//        // Defining the procedure
//
//        if (!isMouseDown) {
//            return;
//        }
//
//    });
//
//    canvas.on('mouse:up', function() {
//        // alert("mouse up!");
//        isMouseDown = false;
//        // freeDrawing=false; // **Disables line drawing
//        callback.$server.afterDrawing(canvas.toDatalessJSON());
//    });
}


function setHoloCanvasReadOnly(hc, readOnly) {
    for(var i=0;i<holoCans.length; ++i ) {
        if(holoCans[i].id == hc) {
            holoCans[i].canvas.isDrawingMode = !readOnly;
        }
    }   
}


function applyHoloCanvas(hc, callback, image) {
    for(var i=0;i<holoCans.length; ++i ) {
        if(holoCans[i].id == hc && holoCans[i].hasDrawn) {
            callback.$server.afterDraw(holoCans[i].canvas.toDatalessJSON(),image);
        }
    }
}

function clearHoloCanvas(hc) {
    for(var i=0;i<holoCans.length; ++i ) {
        if(holoCans[i].id == hc) {
        	holoCans[i].hasDrawn = false;
            holoCans[i].canvas.clear();
        }
    }

}
function copyImage(canvas){
    return canvas.toDataURL();
}

fabric.Image.prototype.toObject = (function (toObject) {

    return function () {
        var image = this;

        var getData = function () {
            var canvas = document.createElement("canvas");
            canvas.width = image.width;
            canvas.height = image.height;

            context = canvas.getContext('2d');

            context.drawImage(image.getElement(), 0, 0);

            return canvas.toDataURL('image/png').replace(/^data:image\/png;base64,/, '');
        };

        return fabric.util.object.extend(toObject.call(this), {
            dataURL: getData(),
        });
    };
})(fabric.Image.prototype.toObject);
