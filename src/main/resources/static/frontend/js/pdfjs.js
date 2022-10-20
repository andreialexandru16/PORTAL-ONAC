function getSmartFormPdf(toPdfElement, callback) {

  var t0 = performance.now();
 var HTML_Width = $(toPdfElement).width();
 var HTML_Height = $(toPdfElement).height();
 var top_left_margin = 15;
 var PDF_Width = HTML_Width+(top_left_margin*2);
 var PDF_Height = (PDF_Width*1.5)+(top_left_margin*2);
 var canvas_image_width = HTML_Width;
 var canvas_image_height = HTML_Height;

 var totalPDFPages = Math.ceil(HTML_Height/PDF_Height)-1;
 $(window).scrollTop(0);
    $(toPdfElement).css("background","white");
    domtoimage.toJpeg(toPdfElement, { quality: 0.95 })
        .then(function (imgData) {

//    setTimeout(function() {
//        canvas.toBlob((blob) => {
//            var fileReader = new FileReader();
//
//            fileReader.readAsArrayBuffer(blob);
//
//            fileReader.onload = function(event) {
//              var arrayBuffer = new Uint8Array(fileReader.result);
//              callback.$server.afterPrintSmartForm(arrayBuffer);
//              console.log("arrayBuffer", arrayBuffer);
//            }
//
//        });
//    }, 3000);


    var pdf = new jsPDF('p', 'pt',  [PDF_Width, PDF_Height]);
    pdf.addImage(imgData, 'JPEG', top_left_margin, top_left_margin,canvas_image_width,canvas_image_height);

    for (var i = 1; i <= totalPDFPages; i++) {
        pdf.addPage(PDF_Width, PDF_Height);
        pdf.addImage(imgData, 'JPEG', top_left_margin, -(PDF_Height*i)+(top_left_margin*4),canvas_image_width,canvas_image_height);
    }

//    var arrayBuffer = new Uint8Array(pdf.output('arraybuffer'));


//    var fileReader = new FileReader();
//
//    fileReader.readAsArrayBuffer(pdf.output('blob'));
//
//    fileReader.onload = function(event) {
//      var arrayBuffer = new Uint8Array(fileReader.result);
//        callback.$server.afterPrintSmartForm(arrayBuffer);
//      console.log("arrayBuffer", arrayBuffer);
//    };

//    setTimeout(function(){
//        var arrayBuffer = new Uint8Array(pdf.output('arraybuffer'));
      var t1 = performance.now();
        callback.$server.afterPrintSmartFormToPdf(btoa(pdf.output()), t1-t0);
//    }, 2000);


 }).catch(function (error) {
      var t1 = performance.now();
        callback.$server.afterPrintSmartFormToPdf("ERROR:\t"+error, t1 - t0);
 });


}


function printSmartFormPdf(toPdfElement, callback, fileName) {

 var HTML_Width = $(toPdfElement).width();
 var HTML_Height = $(toPdfElement).height();
 var top_left_margin = 15;
 var PDF_Width = HTML_Width+(top_left_margin*2);
 var PDF_Height = (PDF_Width*1.5)+(top_left_margin*2);
 var canvas_image_width = HTML_Width;
 var canvas_image_height = HTML_Height;

 var totalPDFPages = Math.ceil(HTML_Height/PDF_Height)-1;
 $(window).scrollTop(0);
    $(toPdfElement).css("background","white");
    domtoimage.toJpeg(toPdfElement, { quality: 0.95 })
        .then(function (imgData) {

    var pdf = new jsPDF('p', 'pt',  [PDF_Width, PDF_Height]);
    pdf.addImage(imgData, 'JPG', top_left_margin, top_left_margin,canvas_image_width,canvas_image_height);

    for (var i = 1; i <= totalPDFPages; i++) {
        pdf.addPage(PDF_Width, PDF_Height);
        pdf.addImage(imgData, 'JPG', top_left_margin, -(PDF_Height*i)+(top_left_margin*4),canvas_image_width,canvas_image_height);
    }
    pdf.save(fileName);
    callback.$server.afterPrintSmartForm({});
 }).catch(function (error) {
        callback.$server.afterPrintSmartForm({
            error: error
        });
 });

}

function printSmartFormPdfAndSave(toPdfElement, callback, fileName) {
    var t0 = performance.now();

    var HTML_Width = $(toPdfElement).width();
    var HTML_Height = $(toPdfElement).height();
    var top_left_margin = 15;
    var PDF_Width = HTML_Width+(top_left_margin*2);
    var PDF_Height = (PDF_Width*1.5)+(top_left_margin*2);
    var canvas_image_width = HTML_Width;
    var canvas_image_height = HTML_Height;

    var totalPDFPages = Math.ceil(HTML_Height/PDF_Height)-1;
    $(window).scrollTop(0);
    $(toPdfElement).css("background","white");
    domtoimage.toJpeg(toPdfElement, { quality: 0.95 })
        .then(function (imgData) {

            var pdf = new jsPDF('p', 'pt',  [PDF_Width, PDF_Height]);
            pdf.addImage(imgData, 'JPG', top_left_margin, top_left_margin,canvas_image_width,canvas_image_height);

            for (var i = 1; i <= totalPDFPages; i++) {
                pdf.addPage(PDF_Width, PDF_Height);
                pdf.addImage(imgData, 'JPG', top_left_margin, -(PDF_Height*i)+(top_left_margin*4),canvas_image_width,canvas_image_height);
            }
            var t1 = performance.now();

            callback.$server.afterPrintSmartFormToPdf(btoa(pdf.output()), t1-t0);
        }).catch(function (error) {
        var t1 = performance.now();
        callback.$server.afterPrintSmartFormToPdf("ERROR:\t"+error, t1 - t0);
    });

}



function print(hc) {
	printJS({
    printable: hc,
    type: 'html',
    targetStyles: ['*']
 })
}

function getPDF(toPdfElement){

 var HTML_Width = $(toPdfElement).width();
 var HTML_Height = $(toPdfElement).height();
 var top_left_margin = 15;
 var PDF_Width = HTML_Width+(top_left_margin*2);
 var PDF_Height = (PDF_Width*1.5)+(top_left_margin*2);
 var canvas_image_width = HTML_Width;
 var canvas_image_height = HTML_Height;

 var totalPDFPages = Math.ceil(HTML_Height/PDF_Height)-1;


//    var newRef = toPdfElement.nativeElement.cloneNode(true);
//    this.renderer.setStyle(newRef, 'filter', 'opacity(0)');
//    this.renderer.appendChild(document.body, newRef);
    $(toPdfElement).css("background","white");
    domtoimage.toJpeg(toPdfElement, { quality: 0.95 })
        .then(function (imgData) {

 var link = document.createElement('a');
   link.download = 'test-smart-form.png';
   link.href = imgData;
   link.click();
 var pdf = new jsPDF('p', 'pt',  [PDF_Width, PDF_Height]);
     pdf.addImage(imgData, 'JPEG', top_left_margin, top_left_margin,canvas_image_width,canvas_image_height);


 for (var i = 1; i <= totalPDFPages; i++) {
 pdf.addPage(PDF_Width, PDF_Height);
 pdf.addImage(imgData, 'JPEG', top_left_margin, -(PDF_Height*i)+(top_left_margin*4),canvas_image_width,canvas_image_height);
 }
//    this.renderer.removeChild(document.body, newRef);
//setTimeout(function(){
pdf.save('formular.pdf');
//},4000);

//     pdf.save("HTML-Document.pdf");
        }).catch(function (error) {
				        	/* This is fired when the promise executes without the DOM */
				    	});
 }
function getSmartFormPdfWithoutCallback(toPdfElement) {

    var defer = $.Deferred();
    var t0 = performance.now();
    var HTML_Width = $(toPdfElement).width();
    var HTML_Height = $(toPdfElement).height();
    var top_left_margin = 15;
    var PDF_Width = HTML_Width+(top_left_margin*2);
    var PDF_Height = (PDF_Width*1.5)+(top_left_margin*2);
    var canvas_image_width = HTML_Width;
    var canvas_image_height = HTML_Height;

    var totalPDFPages = Math.ceil(HTML_Height/PDF_Height)-1;
    $(window).scrollTop(0);
    $(toPdfElement).css("background","white");
    domtoimage.toJpeg(toPdfElement, { quality: 0.20 })
        .then(function (imgData) {

//    setTimeout(function() {
//        canvas.toBlob((blob) => {
//            var fileReader = new FileReader();
//
//            fileReader.readAsArrayBuffer(blob);
//
//            fileReader.onload = function(event) {
//              var arrayBuffer = new Uint8Array(fileReader.result);
//              callback.$server.afterPrintSmartForm(arrayBuffer);
//              console.log("arrayBuffer", arrayBuffer);
//            }
//
//        });
//    }, 3000);


            var pdf = new jsPDF('p', 'pt', [PDF_Width, PDF_Height]);
            pdf.addImage(imgData, 'JPEG', top_left_margin, top_left_margin, canvas_image_width, canvas_image_height);

            for (var i = 1; i <= totalPDFPages; i++) {
                pdf.addPage(PDF_Width, PDF_Height);
                pdf.addImage(imgData, 'JPEG', top_left_margin, -(PDF_Height * i) + (top_left_margin * 4), canvas_image_width, canvas_image_height);
            }

//    var arrayBuffer = new Uint8Array(pdf.output('arraybuffer'));


//    var fileReader = new FileReader();
//
//    fileReader.readAsArrayBuffer(pdf.output('blob'));
//
//    fileReader.onload = function(event) {
//      var arrayBuffer = new Uint8Array(fileReader.result);
//        callback.$server.afterPrintSmartForm(arrayBuffer);
//      console.log("arrayBuffer", arrayBuffer);
//    };

//    setTimeout(function(){
//        var arrayBuffer = new Uint8Array(pdf.output('arraybuffer'));
            var t1 = performance.now();
            defer.resolve(btoa(pdf.output()))
        })
    return defer.promise();
        }