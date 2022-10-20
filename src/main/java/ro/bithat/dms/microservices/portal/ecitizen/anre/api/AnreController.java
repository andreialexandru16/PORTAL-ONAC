package ro.bithat.dms.microservices.portal.ecitizen.anre.api;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ServerWebInputException;
import ro.bithat.dms.microservices.dmsws.file.DmswsFileService;
import ro.bithat.dms.microservices.dmsws.file.DocAttrLink;
import ro.bithat.dms.microservices.dmsws.file.DocAttrLinkList;
import ro.bithat.dms.microservices.dmsws.file.UserToken;
import ro.bithat.dms.microservices.dmsws.flow.StandardResponse;
import ro.bithat.dms.microservices.dmsws.metadata.DmswsMetadataService;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.CreateTipDocFileResponse;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.CreateTipDocFileResponseXml;
import ro.bithat.dms.microservices.portal.ecitizen.anre.backend.AnreService;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.api.DmswsAddUtilizatorController;
import ro.bithat.dms.security.SecurityUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Controller
public class AnreController {

    @Value("${dmsws.unitname:}")
    private String unitate;

    @Autowired
    private AnreService anreService;
    @Autowired
    private DmswsFileService dmswsFileService;
    @Autowired
    private DmswsMetadataService dmswsMetadataService;
    @PostMapping(value = "/dmsws/anre/processExcel", consumes = "multipart/form-data", produces = "application/json")
    public ResponseEntity<CreateTipDocFileResponse> processExcel(HttpServletRequest httpServletRequest) {
        DmswsAddUtilizatorController.FileUploadParamRequest paramRequest = getRequestForm(httpServletRequest);
        DmswsAddUtilizatorController.UploadFileDescription fileDescriptions = paramRequest.getUploadedFiles().iterator().next();
        String idDocType = paramRequest.getParamMap().get("idDocType");
        String idPerioada = paramRequest.getParamMap().get("idPerioada");
        String idTert = paramRequest.getParamMap().get("idTert");

        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentType(MediaType.APPLICATION_JSON);

        CreateTipDocFileResponse fileResponse = anreService.processExcel(SecurityUtils.getToken(), fileDescriptions.getFileName(), fileDescriptions.getFileData(), idDocType, idPerioada);

        //add atribute tert & perioada pentru fisierul creat
        DocAttrLinkList docAttrLinkList= new DocAttrLinkList();
        List<DocAttrLink> linkList= new ArrayList<>();
        DocAttrLink docAttrLink = new DocAttrLink();
        docAttrLink.setAttributeCode("DENUMIRE_OPERATOR");
        try{
            if(SecurityUtils.getContCurentPortalE().getTertParinteUserCurent().getIdTert()!=null){
                String value = idTert == null || idTert.equals("null") || idTert.equals("") ? SecurityUtils.getContCurentPortalE().getTertParinteUserCurent().getIdTert().toString() : idTert;
                docAttrLink.setValue(value);

            }else{
                String value = idTert == null || idTert.equals("null") ? SecurityUtils.getContCurentPortalE().getUserCurent().getIdTert().toString() : idTert;
                docAttrLink.setValue(value);

            }

        }catch (Exception e){

        }
        DocAttrLink docAttrLinkPerioada = new DocAttrLink();
        docAttrLinkPerioada.setAttributeCode("LUNA_MON_EE");
        try{
            docAttrLinkPerioada.setValue(idPerioada);


        }catch (Exception e){

        }

        linkList.add(docAttrLink);
        linkList.add(docAttrLinkPerioada);

        docAttrLinkList.setDocAttrLink(linkList);
        dmswsFileService.setDocAttributesEvenIfMissing(SecurityUtils.getToken(), Integer.valueOf(fileResponse.getFileId()),docAttrLinkList);

        return new ResponseEntity<>(
                fileResponse, respHeaders, HttpStatus.OK
        );
    }

    @PostMapping(value = "/dmsws/anre/processExcelET", consumes = "multipart/form-data", produces = "application/json")
    public ResponseEntity<CreateTipDocFileResponse> processExcelET(HttpServletRequest httpServletRequest) {
        DmswsAddUtilizatorController.FileUploadParamRequest paramRequest = getRequestForm(httpServletRequest);
        DmswsAddUtilizatorController.UploadFileDescription fileDescriptions = paramRequest.getUploadedFiles().iterator().next();
        String idDocType = paramRequest.getParamMap().get("idDocType");
        String idPerioada = paramRequest.getParamMap().get("idPerioada");
        String idTert = paramRequest.getParamMap().get("idTert");

        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentType(MediaType.APPLICATION_JSON);

        CreateTipDocFileResponse fileResponse = anreService.processExcel(SecurityUtils.getToken(), fileDescriptions.getFileName(), fileDescriptions.getFileData(), idDocType, idPerioada);

        //add atribute tert & perioada pentru fisierul creat
        DocAttrLinkList docAttrLinkList= new DocAttrLinkList();
        List<DocAttrLink> linkList= new ArrayList<>();
        DocAttrLink docAttrLink = new DocAttrLink();
        docAttrLink.setAttributeCode("DENUMIRE_OPERATOR");
        try{
            if(SecurityUtils.getContCurentPortalE().getTertParinteUserCurent().getIdTert()!=null){
                String value = idTert == null || idTert.equals("null") || idTert.equals("") ? SecurityUtils.getContCurentPortalE().getTertParinteUserCurent().getIdTert().toString() : idTert;
                docAttrLink.setValue(value);

            }else{
                String value = idTert == null || idTert.equals("null") ? SecurityUtils.getContCurentPortalE().getUserCurent().getIdTert().toString() : idTert;
                docAttrLink.setValue(value);

            }

        }catch (Exception e){

        }
        DocAttrLink docAttrLinkPerioada = new DocAttrLink();
        docAttrLinkPerioada.setAttributeCode("LUNA_MON_ET");
        try{
            docAttrLinkPerioada.setValue(idPerioada);


        }catch (Exception e){

        }

        linkList.add(docAttrLink);
        linkList.add(docAttrLinkPerioada);

        docAttrLinkList.setDocAttrLink(linkList);
        dmswsFileService.setDocAttributesEvenIfMissing(SecurityUtils.getToken(), Integer.valueOf(fileResponse.getFileId()),docAttrLinkList);

        return new ResponseEntity<>(
                fileResponse, respHeaders, HttpStatus.OK
        );
    }

    @PostMapping(value = "/dmsws/file/uploadFisierByIdTipDoc", consumes = "multipart/form-data", produces = "text/html")
    public ResponseEntity<CreateTipDocFileResponse> uploadFisierByIdTipDoc(HttpServletRequest httpServletRequest) {
        DmswsAddUtilizatorController.FileUploadParamRequest paramRequest = getRequestForm(httpServletRequest);
        DmswsAddUtilizatorController.UploadFileDescription fileDescriptions = paramRequest.getUploadedFiles().iterator().next();
        String idDocType = paramRequest.getParamMap().get("idDocType");
        String idPerioada = paramRequest.getParamMap().get("idPerioada");

        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<CreateTipDocFileResponse> resp =  new ResponseEntity<>(
                dmswsFileService.uploadFisierTipDocId(SecurityUtils.getToken(), Integer.valueOf(idDocType),SecurityUtils.getUserId(), fileDescriptions.getFileName(), fileDescriptions.getFileName(), fileDescriptions.getFileData(),Optional.empty()), respHeaders, HttpStatus.OK
        );
        //add atribute tert & perioada pentru fisierul creat
        DocAttrLinkList docAttrLinkList= new DocAttrLinkList();
        List<DocAttrLink> linkList= new ArrayList<>();
        DocAttrLink docAttrLink = new DocAttrLink();
        docAttrLink.setAttributeCode("DENUMIRE_OPERATOR");
        try{
            if(SecurityUtils.getContCurentPortalE().getTertParinteUserCurent().getIdTert()!=null){
                docAttrLink.setValue(SecurityUtils.getContCurentPortalE().getTertParinteUserCurent().getIdTert().toString());

            }else{
                docAttrLink.setValue(SecurityUtils.getContCurentPortalE().getUserCurent().getIdTert().toString());

            }

        }catch (Exception e){

        }
        DocAttrLink docAttrLinkPerioada = new DocAttrLink();
        docAttrLinkPerioada.setAttributeCode("LUNA_TEST_2");
        try{
            docAttrLinkPerioada.setValue(idPerioada);


        }catch (Exception e){

        }

        linkList.add(docAttrLink);
        linkList.add(docAttrLinkPerioada);

        docAttrLinkList.setDocAttrLink(linkList);
                dmswsFileService.setDocAttributesEvenIfMissing(SecurityUtils.getToken(), Integer.valueOf(resp.getBody().getFileId()),docAttrLinkList);
        return resp;
    }

    @PostMapping(value = "/dmsws/file/uploadFisierByIdTipDocXml", consumes = "multipart/form-data", produces = "application/json")
    public ResponseEntity<CreateTipDocFileResponseXml> uploadFisierByIdTipDocXml(HttpServletRequest httpServletRequest) {
        DmswsAddUtilizatorController.FileUploadParamRequest paramRequest = getRequestForm(httpServletRequest);
        DmswsAddUtilizatorController.UploadFileDescription fileDescriptions = paramRequest.getUploadedFiles().iterator().next();
        String idDocType = paramRequest.getParamMap().get("idDocType");
        String idPerioada = paramRequest.getParamMap().get("idPerioada");
        String idTert = paramRequest.getParamMap().get("idTert");
        Integer intIdTert = null;

        if (idTert != null && !idTert.trim().isEmpty()){
            intIdTert = Integer.valueOf(idTert);
        }

        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<CreateTipDocFileResponseXml> resp =  new ResponseEntity<>(
                dmswsFileService.uploadFisierTipDocIdXml(SecurityUtils.getToken(), Integer.valueOf(idDocType), SecurityUtils.getUserId(), intIdTert, Integer.valueOf(idPerioada), fileDescriptions.getFileName(), fileDescriptions.getFileName(), fileDescriptions.getFileData()), respHeaders, HttpStatus.OK
        );

        if (resp.getBody() != null && resp.getBody().isError()) {
            throw new ServerWebInputException(resp.getBody().getErrString()+"\t"+resp.getBody().getExtendedStatus()+"\t"+resp.getBody().getStatus());
        }

        //add atribute tert & perioada pentru fisierul creat
        DocAttrLinkList docAttrLinkList= new DocAttrLinkList();
        List<DocAttrLink> linkList= new ArrayList<>();
        DocAttrLink docAttrLink = new DocAttrLink();
        docAttrLink.setAttributeCode("DENUMIRE_OPERATOR");
        try{
            if(SecurityUtils.getContCurentPortalE().getTertParinteUserCurent().getIdTert()!=null){
                docAttrLink.setValue(SecurityUtils.getContCurentPortalE().getTertParinteUserCurent().getIdTert().toString());

            }else{
                docAttrLink.setValue(SecurityUtils.getContCurentPortalE().getUserCurent().getIdTert().toString());

            }

        }catch (Exception e){

        }
        DocAttrLink docAttrLinkPerioada = new DocAttrLink();
        docAttrLinkPerioada.setAttributeCode("LUNA_TEST_2");
        try{
            docAttrLinkPerioada.setValue(idPerioada);


        }catch (Exception e){

        }

        linkList.add(docAttrLink);
        linkList.add(docAttrLinkPerioada);

        docAttrLinkList.setDocAttrLink(linkList);
        dmswsFileService.setDocAttributesEvenIfMissing(SecurityUtils.getToken(), Integer.valueOf(resp.getBody().getFileId()),docAttrLinkList);
        return resp;
    }

    private DmswsAddUtilizatorController.FileUploadParamRequest getRequestForm(HttpServletRequest httpServletRequest) {
        boolean isMultipart = ServletFileUpload.isMultipartContent(httpServletRequest);

        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setRepository(
                new File(System.getProperty("java.io.tmpdir")));
        factory.setSizeThreshold(
                DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD);
        factory.setFileCleaningTracker(null);

        ServletFileUpload upload = new ServletFileUpload(factory);
        DmswsAddUtilizatorController.FileUploadParamRequest requestForm = new DmswsAddUtilizatorController.FileUploadParamRequest();

        if(isMultipart) {
            try {
                List items = upload.parseRequest(httpServletRequest);
                Iterator iter = items.iterator();
                while (iter.hasNext()) {
                    FileItem item = (FileItem) iter.next();

                    if (!item.isFormField()) {
                        InputStream uploadedStream = item.getInputStream();
                        requestForm.addUploadedFile(new DmswsAddUtilizatorController.UploadFileDescription(item.getName(), IOUtils.toByteArray(uploadedStream)));
                    } else  {
                        ((DiskFileItem) item).setDefaultCharset("UTF-8");
                        requestForm.putParam(item.getFieldName(), item.getString());
                    }
                }
            } catch (FileUploadException e) {
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return requestForm;
    }

    @PostMapping(value = "/dmsws/anre/uploadDocumentAutMediu", consumes = "multipart/form-data", produces = "application/json")
    public ResponseEntity<StandardResponse> uploadDocumentAutMediu(HttpServletRequest httpServletRequest) {
        DmswsAddUtilizatorController.FileUploadParamRequest paramRequest = getRequestForm(httpServletRequest);
        DmswsAddUtilizatorController.UploadFileDescription fileDescriptions = paramRequest.getUploadedFiles().iterator().next();
        String id = paramRequest.getParamMap().get("id");

        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<StandardResponse> resp =  new ResponseEntity<>(
                dmswsFileService.uploadDocumentAutMediu(SecurityUtils.getToken(), Integer.valueOf(id), fileDescriptions.getFileName(), fileDescriptions.getFileData()), respHeaders, HttpStatus.OK
        );

        return resp;
    }

    @PostMapping(value = "/dmsws/anre/uploadDocumentAutGospApelor", consumes = "multipart/form-data", produces = "application/json")
    public ResponseEntity<StandardResponse> uploadDocumentAutGospApelor(HttpServletRequest httpServletRequest) {
        DmswsAddUtilizatorController.FileUploadParamRequest paramRequest = getRequestForm(httpServletRequest);
        DmswsAddUtilizatorController.UploadFileDescription fileDescriptions = paramRequest.getUploadedFiles().iterator().next();
        String id = paramRequest.getParamMap().get("id");

        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<StandardResponse> resp =  new ResponseEntity<>(
                dmswsFileService.uploadDocumentAutGospApelor(SecurityUtils.getToken(), Integer.valueOf(id), fileDescriptions.getFileName(), fileDescriptions.getFileData()), respHeaders, HttpStatus.OK
        );

        return resp;
    }



    @PostMapping(value = "/dmsws/anre/uploadDocumentCont", consumes = "multipart/form-data", produces = "application/json")
    public ResponseEntity<CreateTipDocFileResponse> uploadDocumentCont(HttpServletRequest httpServletRequest) {
        DmswsAddUtilizatorController.FileUploadParamRequest paramRequest = getRequestForm(httpServletRequest);
        DmswsAddUtilizatorController.UploadFileDescription fileDescriptions = paramRequest.getUploadedFiles().iterator().next();
        String idTipDoc = paramRequest.getParamMap().get("id_document");
        String valabilDeLa = paramRequest.getParamMap().get("valabil_de_la");
        String valabilPanaLa = paramRequest.getParamMap().get("valabil_pana_la");
        Map<String, String> mapParam= paramRequest.getParamMap();


        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentType(MediaType.APPLICATION_JSON);
        if(unitate.equals("fga")) {
         String existaRap=paramRequest.getParamMap().get("rapG");
         if(existaRap.equalsIgnoreCase("1")){
             idTipDoc="23474";
             String idFisier=paramRequest.getParamMap().get("idFisier");
             ResponseEntity<CreateTipDocFileResponse> resp =  new ResponseEntity<>(
                     dmswsFileService.uploadDocumentCont(SecurityUtils.getToken(), Integer.valueOf(idTipDoc),SecurityUtils.getUserId(),
                             fileDescriptions.getFileName(), fileDescriptions.getFileName(), fileDescriptions.getFileData(),
                             Optional.ofNullable(valabilDeLa), Optional.ofNullable(valabilPanaLa),
                             Optional.ofNullable(mapParam.get("nr_anre")),Optional.ofNullable(mapParam.get("data_anre")),
                             Optional.ofNullable(idFisier),Optional.ofNullable(mapParam.get("data_document")),
                             Optional.ofNullable(mapParam.get("zona")))
                     , respHeaders, HttpStatus.OK
             );
             return resp;
         }else{
             ResponseEntity<CreateTipDocFileResponse> resp =  new ResponseEntity<>(
                     dmswsFileService.uploadDocumentCont(SecurityUtils.getToken(), Integer.valueOf(idTipDoc),SecurityUtils.getUserId(),
                             fileDescriptions.getFileName(), fileDescriptions.getFileName(), fileDescriptions.getFileData(),
                             Optional.ofNullable(valabilDeLa), Optional.ofNullable(valabilPanaLa),
                             Optional.ofNullable(mapParam.get("nr_anre")),Optional.ofNullable(mapParam.get("data_anre")),
                             Optional.ofNullable(mapParam.get("nr_document")),Optional.ofNullable(mapParam.get("data_document")),
                             Optional.ofNullable(mapParam.get("zona")))
                     , respHeaders, HttpStatus.OK
             );
             return resp;
         }
        }else {
            ResponseEntity<CreateTipDocFileResponse> resp = new ResponseEntity<>(
                    dmswsFileService.uploadDocumentCont(SecurityUtils.getToken(), Integer.valueOf(idTipDoc), SecurityUtils.getUserId(),
                            fileDescriptions.getFileName(), fileDescriptions.getFileName(), fileDescriptions.getFileData(),
                            Optional.ofNullable(valabilDeLa), Optional.ofNullable(valabilPanaLa),
                            Optional.ofNullable(mapParam.get("nr_anre")), Optional.ofNullable(mapParam.get("data_anre")),
                            Optional.ofNullable(mapParam.get("nr_document")), Optional.ofNullable(mapParam.get("data_document")),
                            Optional.ofNullable(mapParam.get("zona")))
                    , respHeaders, HttpStatus.OK
            );
            return resp;
        }

    }

    @PostMapping(value = "/dmsws/anre/uploadDocumentContEdit", consumes = "multipart/form-data", produces = "application/json")
    public ResponseEntity<CreateTipDocFileResponse> uploadDocumentContEdit(HttpServletRequest httpServletRequest) {
        DmswsAddUtilizatorController.FileUploadParamRequest paramRequest = getRequestForm(httpServletRequest);
        DmswsAddUtilizatorController.UploadFileDescription fileDescriptions = null;

        if(paramRequest.getUploadedFiles()!=null &&paramRequest.getUploadedFiles().size()!=0 ){
            fileDescriptions = paramRequest.getUploadedFiles().iterator().next();

        }
        String id = paramRequest.getParamMap().get("id");
        String idTipDoc = paramRequest.getParamMap().get("id_document");
        String valabilDeLa = paramRequest.getParamMap().get("valabil_de_la");
        String valabilPanaLa = paramRequest.getParamMap().get("valabil_pana_la");
        Map<String, String> mapParam= paramRequest.getParamMap();


        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentType(MediaType.APPLICATION_JSON);

            ResponseEntity<CreateTipDocFileResponse> resp =  new ResponseEntity<>(
                    dmswsFileService.uploadDocumentContEdit(SecurityUtils.getToken(), Integer.valueOf(idTipDoc),SecurityUtils.getUserId(),
                            fileDescriptions==null?null:fileDescriptions.getFileName(), fileDescriptions==null?null:fileDescriptions.getFileName(), fileDescriptions==null?null:fileDescriptions.getFileData(),
                            Optional.ofNullable(valabilDeLa), Optional.ofNullable(valabilPanaLa),
                            Optional.ofNullable(mapParam.get("nr_anre")),Optional.ofNullable(mapParam.get("data_anre")),
                            Optional.ofNullable(mapParam.get("nr_document")),Optional.ofNullable(mapParam.get("data_document")),
                            Optional.ofNullable(mapParam.get("zona")),id)
                    , respHeaders, HttpStatus.OK
            );



        return resp;
    }
}
