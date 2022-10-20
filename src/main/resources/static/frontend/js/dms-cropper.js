var cropper = null;


//XMLHttpRequest.prototype._originalSend = XMLHttpRequest.prototype.send;
//
//var sendWithCredentials = function(data) {
//    this.withCredentials = true;
//    this._originalSend(data);
//};
//
//XMLHttpRequest.prototype.send = sendWithCredentials;



function dmsCropper(elmnt, cropEndCallback, imageMoveResizeCallback) {
    if(elmnt == null)
        return;
    if(cropper != null) {
        cropper.destroy();
    }
    cropper = new Cropper(elmnt, {
                checkCrossOrigin: false,
                crossOrigin: "anonymous",
                dragMode: "move",
                autoCrop: false,
                background: false,
                crop(event) {
                    var cropBoxData = cropper.getCropBoxData();
                    if(cropBoxData.left == undefined) {
                        var canvasData = cropper.getCanvasData();
                        imageMoveResizeCallback.$server.onImageMoveOrResize(canvasData.left, canvasData.top,
                                canvasData.width, canvasData.height, canvasData.naturalWidth, canvasData.naturalHeight);
                    }

                },
                ready() {
                    var containerData = cropper.getContainerData();
                    cropper.zoomTo(.36, {
                        x: containerData.width / 2,
                        y: 0,
                    });
                },
                cropend() {
                    var cropBoxData = cropper.getCropBoxData();
                    if(cropBoxData.left != undefined) {
                        cropEndCallback.$server.onCropEnd(cropBoxData.left, cropBoxData.top, cropBoxData.width, cropBoxData.height);
                    }
                },
              });
}


function dmsAddComment(callback) {
    var cropBoxData = cropper.getCropBoxData();
    var canvasData = cropper.getCanvasData();
    callback.$server.onAddComment(cropBoxData.left, cropBoxData.top, cropBoxData.width, cropBoxData.height,
                    canvasData.left, canvasData.top, canvasData.width, canvasData.height, canvasData.naturalWidth, canvasData.naturalHeight);
    cropper.clear();
    cropper.setDragMode("move");
}

function dmsGetCropperData(callback) {

 var cropBoxData = cropper.getCropBoxData();

cropper.getCroppedCanvas({
  imageSmoothingEnabled: true,
  imageSmoothingQuality: 'high',
}).toBlob((blob) => {




// var formData = new FormData();
//
//  // Pass the image file name as the third parameter if necessary.
//  formData.append('croppedImage', blob/*, 'example.png' */);
//
//  // Use `jQuery.ajax` method for example
//  $.ajax({
//        type: 'POST',
//        enctype: 'multipart/form-data',
//        url: '/ocr-rc/ocr/imagedata',
//        data: formData,
//        processData: false,
//        contentType: false,
//        cache: false,
//        timeout: 600000,
//        success(data) {
//            callback.$server.onOcrOnTheFlyTextAvailable("Ocr On The Fly Test:\t"+data);
//    //      console.log('Upload success');
//        },
//        error(e) {
//          console.log('Upload error:\t', e.responseText);
//        },
//  });


var fileReader = new FileReader();

fileReader.readAsArrayBuffer(blob);

fileReader.onload = function(event) {
  var arrayBuffer = new Uint8Array(fileReader.result);
  callback.$server.onGetCropData(arrayBuffer);
  console.log("arrayBuffer", arrayBuffer);
};

//  var formData = new FormData();
//
//  // Pass the image file name as the third parameter if necessary.
//  formData.append('croppedImage', blob/*, 'example.png' */);
//var object  = {};
//formData.forEach(function(value, key){
//    object[key] = value;
//});

//console.log("object blob", object);
//console.log("object blob string", JSON.stringify(object));

//       var reader = new FileReader();
//    callBack.$server.onGetCropData(reader.readAsText(blob));
  // Use `jQuery.ajax` method for example
//  $.ajax('/path/to/upload', {
//    method: 'POST',
//    data: formData,
//    processData: false,
//    contentType: false,
//    success() {
//      console.log('Upload success');
//    },
//    error() {
//      console.log('Upload error');
//    },
//  });
}/*, 'image/png' */);

}