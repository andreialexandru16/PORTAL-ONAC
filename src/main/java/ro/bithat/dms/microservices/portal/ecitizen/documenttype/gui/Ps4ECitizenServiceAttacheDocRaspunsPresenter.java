package ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui;
//13.07.2021 - NG - ANRE - resetare contor schimbari facute pentru a nu afisa dialog de confirmare iesire din pagina

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.upload.SucceededEvent;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.RouteConfiguration;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.server.ServerWebInputException;
import ro.bithat.dms.microservices.dmsws.file.BaseModel;
import ro.bithat.dms.microservices.dmsws.file.DmswsFileService;
import ro.bithat.dms.microservices.dmsws.file.FileData;
import ro.bithat.dms.microservices.dmsws.file.PortalFile;
import ro.bithat.dms.microservices.dmsws.ps4.documents.DocObligatoriuExtra;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.DocObligatoriiResp;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.EmailHelperService;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.SolicitareService;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.gui.Ps4ECitizenMyRequestsRoute;
import ro.bithat.dms.microservices.portal.ecitizen.website.models.RegistraturaComplete;
import ro.bithat.dms.passiveview.QueryParameterUtil;
import ro.bithat.dms.passiveview.VaadinClientUrlUtil;
import ro.bithat.dms.passiveview.component.html.ClickNotifierAnchor;
import ro.bithat.dms.security.SecurityUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Ps4ECitizenServiceAttacheDocRaspunsPresenter extends DocumentTypePresenter<Ps4ECitizenServiceAttacheDocRaspunsView> {

    @Autowired
    private DmswsFileService fileService;
    @Value("${ps4.ecitizen.workflow.status.request.completed}")
    private Integer idWorkflowStatusRequestCompleted;

    @Autowired
    private SolicitareService solictiareService;
    @Autowired
    private EmailHelperService emailService;
    Optional<String> nrAdresaRspuns;
    String idAdresaRspunsCurenta;
    String idTipDocAdresaRspunsCurenta;
    Optional<String>  userToInfoEmail; //email al persoanei catre care trebuie sa se trimita info ca a fost incarcat raspuns
    Optional<String>  infoFisierParinte; //nume,nr,data fisier parinte
    @Override
    public void afterPrepareModel(String state) {
        UI.getCurrent().getPage().executeJavaScript("window.parent.parent.scrollTo(0,0);");

        long documentId = new Long(getDocumentId().get());
        if(!getFromMainScreen().isPresent() || getFromMainScreen().get()==null){
            getView().buildBreadcrumbsWizard();

        }
            getView().setServiceNameAndRegisterPreviousStep(getDocument().get().getDenumire());
        DocObligatoriiResp docObligatoriiResp= getPs4Service().getCriteriiScrisoareLipsuri(documentId,getDocRaspunsId());
        idTipDocAdresaRspunsCurenta=docObligatoriiResp.getExtendedInfo2();
        nrAdresaRspuns= Optional.ofNullable(docObligatoriiResp.getNrInregRaspuns());
        userToInfoEmail= Optional.ofNullable(docObligatoriiResp.getUserToInfoEmail());
        infoFisierParinte= Optional.ofNullable(docObligatoriiResp.getInfoFisierParinte());

        getView().setNeededDocumentsTable(docObligatoriiResp.getDocObligatoriuList().stream().map(d -> new DocObligatoriuExtra(d)).collect(Collectors.toList()));
        UI.getCurrent().getPage().executeJs("$('#resize_iframe', window.parent.document).trigger('click')");

    }

    public void onPreviousBtnAction(ClickEvent<ClickNotifierAnchor> clickEvent) {
        //13.07.2021 - NG - ANRE - resetare contor schimbari facute pentru a nu afisa dialog de confirmare iesire din pagina
        UI.getCurrent().getPage().executeJavaScript("resetChanges();");
        getLogger().info("filter previous button");
        Map<String, Object> filterPageParameters = new HashMap<>();
        filterPageParameters.put("tipDocument", getDocumentType());
        filterPageParameters.put("document", getDocumentId().get());
        filterPageParameters.put("request", getFileId().get());
        if(getFromMainScreen().isPresent()){
            filterPageParameters.put("fromMainScreen", getFromMainScreen().get());

        }
        VaadinClientUrlUtil.setLocation(QueryParameterUtil
                .getRelativePathWithQueryParameters(filterPageParameters, Ps4ECitizenServiceRequestReviewRoute.class));
    }

    public void onNextBtnAction(ClickEvent<ClickNotifierAnchor> clickEvent) {
        //13.07.2021 - NG - ANRE - resetare contor schimbari facute pentru a nu afisa dialog de confirmare iesire din pagina
        UI.getCurrent().getPage().executeJavaScript("resetChanges();");
        getLogger().info("on lanseaza comanda");
        Map<String, Object> filterPageParameters = new HashMap<>();
        filterPageParameters.put("tipDocument", getDocumentType());
        filterPageParameters.put("document", getDocumentId().get());
        filterPageParameters.put("request", getFileId().get());
        if(getFromMainScreen().isPresent()){
            filterPageParameters.put("fromMainScreen", getFromMainScreen().get());

        }

        if(getPs4Service().getCriteriiScrisoareLipsuri(new Long(getDocumentId().get()), getDocRaspunsId()).getDocObligatoriuList()
                .stream().map(d -> new DocObligatoriuExtra(d)).collect(Collectors.toList())
                .stream().filter(docObligatoriuExtra -> docObligatoriuExtra.getDocObligatoriu().getObligatoriu()
                && (!Optional.ofNullable(docObligatoriuExtra.getDocObligatoriu().getLinkFisierAnexat()).isPresent()
                || docObligatoriuExtra.getDocObligatoriu().getLinkFisierAnexat().isEmpty())).count() != 0) {
            UI.getCurrent().getPage().executeJs("swalError($0);", "Vă rugăm să încărcați fișierele obligatorii!");

        } else {
            if(idWorkflowStatusRequestCompleted!=null){
                solictiareService.setWorflowStatus(Long.valueOf(getDocRaspunsId().get().toString()),idWorkflowStatusRequestCompleted);
            }
            if(userToInfoEmail.isPresent() && !userToInfoEmail.get().isEmpty()){
                String template=emailService.getHtmlTemplate("NOTIFICARE_ADRESA_RASPUNS_INCARCATA").getExtendedInfo2();
                template=template.replaceAll("TERT",SecurityUtils.getFullName());
                template=template.replaceAll("NR_DATA_INREG",nrAdresaRspuns.get());
                template=template.replaceAll("INFO_CERERE",infoFisierParinte.get());
                emailService.sendEmailTemplateBySystem(userToInfoEmail.get(),"Adresa de raspuns incarcata",template);
            }
            String infoNrAdresa=nrAdresaRspuns.isPresent()?" Adresa de raspuns " +
                    " a fost inregistrata cu numarul: "+ nrAdresaRspuns.get():"";
            UI.getCurrent().getPage().executeJs("swalInfoParam($0,$1,$2);", "Documentele au fost salvate cu succes!"
                    +infoNrAdresa,getView(),RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenMyRequestsRoute.class));

                    }
    }

    public void uploadFile(SucceededEvent event, MemoryBuffer fileBuffer, DocObligatoriuExtra docObligatoriuExtra) {
        try {
            //	1. Este doc de raspuns la scrisoare?
            if(docObligatoriuExtra.getDocObligatoriu().getDocumentRaspuns()!=null && docObligatoriuExtra.getDocObligatoriu().getDocumentRaspuns()==1) {
               //DA-> Am adresa de raspuns incarcata in pagina curenta?
                if(idAdresaRspunsCurenta!=null ){
                    //DA-> fac replace
                   fileService.uploadToReplaceExistingFileAndUpdateName(SecurityUtils.getToken(), Long.valueOf(docObligatoriuExtra.getDocObligatoriu().getIdFisierAnexat()),
                            event.getFileName(), IOUtils.toByteArray(fileBuffer.getInputStream()));

                }else{
                    //NU-> fac upload, atasare la scrisoare si salvez ca id curent doc adresa raspuns
                    idAdresaRspunsCurenta = getPs4Service().uploadFileAndAttachLaDocRaspuns(getFileId().get(), Long.valueOf(getDocRaspunsId().get()), docObligatoriuExtra, event.getFileName(), IOUtils.toByteArray(fileBuffer.getInputStream())).getUploadedFileId().toString();

                }
            } else {
                //1. Exista  deja pe cerere?
                String idFisierCurent;
                if(Optional.ofNullable(docObligatoriuExtra.getDocObligatoriu().getLinkFisierAnexat()).isPresent() &&
                        !docObligatoriuExtra.getDocObligatoriu().getLinkFisierAnexat().isEmpty() ) {
                    //DA-> fac vers noua pe cerere
                    idFisierCurent=docObligatoriuExtra.getDocObligatoriu().getIdFisierAnexat();
                    fileService.uploadToReplaceExistingFileAndUpdateName(SecurityUtils.getToken(), Long.valueOf(docObligatoriuExtra.getDocObligatoriu().getIdFisierAnexat()),
                            event.getFileName(), IOUtils.toByteArray(fileBuffer.getInputStream()));

                        }else{
                   // NU-> incarc si atasez la cerere
                    idFisierCurent = getPs4Service().uploadFileAndAttach(Long.valueOf(getFileId().get()),  docObligatoriuExtra, event.getFileName(), IOUtils.toByteArray(fileBuffer.getInputStream())).getUploadedFileId().toString();
                }
                //2. Am adresa de raspuns incarcata in pagina curenta?
                if(idAdresaRspunsCurenta!=null){
                    //DA-> atasez la ea
                    getPs4Service().attachFileToParent(SecurityUtils.getToken(), Integer.parseInt(idFisierCurent),Integer.parseInt(idAdresaRspunsCurenta));
                }else{
                    //	NU-> fac dummy file adresa raspuns, atasez la scrisoare si ii iau id ul => ATASEZ FISIER LA ID DUMMY
                    FileData fileData = new FileData();
                    if(idTipDocAdresaRspunsCurenta!=null){
                        fileData.setId_document(Integer.valueOf(idTipDocAdresaRspunsCurenta));
                        fileData.setNume("Adresa generata de sistem-");
                        //creare dummy si setare ca adresa curenta
                        idAdresaRspunsCurenta = fileService.createDummyFileSolicitareRegister(SecurityUtils.getToken(),fileData).getId().toString();
                        //atasare  adresa la scrisoare
                        getPs4Service().attachFileToParentDocRaspuns(SecurityUtils.getToken(), Integer.valueOf(idAdresaRspunsCurenta), Integer.valueOf(getDocRaspunsId().get()),getFileId().get());

                    }


                }

                }
            //UI.getCurrent().getPage().executeJs("swalInfo($0);", "Fisierul a fost incarcat cu succes!");
            getLogger().info("uploadFile filename:\t", event.getFileName());
        } catch (Throwable t) {

            getLogger().error("uploadFile error get file data", t.getStackTrace());
            UI.getCurrent().getPage().executeJs("swalError($0);", "Fisierul nu a putut fi incarcat, eroare DMSWS! Va rugam reincercati!");
        }

        long documentId = new Long(getDocumentId().get());
        DocObligatoriiResp docObligatoriiResp= getPs4Service().getCriteriiScrisoareLipsuri(documentId, getDocRaspunsId());
        nrAdresaRspuns= Optional.ofNullable(docObligatoriiResp.getNrInregRaspuns());
        getView().setNeededDocumentsTable(docObligatoriiResp.getDocObligatoriuList().stream().map(d -> new DocObligatoriuExtra(d)).collect(Collectors.toList()));

    }

    public void alertaAdresaGenerataSistem(ClickEvent event, DocObligatoriuExtra docObligatoriuExtra) {
        getLogger().info("alertaAdresaGenerataSistem file");
        UI.getCurrent().getPage().executeJs("swalInfo($0);", "Acesta este un fisier gol generat automat de sistem. ");


    }

    public void deleteFile(ClickEvent event, DocObligatoriuExtra docObligatoriuExtra) {
        getLogger().info("delete file");
        BaseModel response= new BaseModel();
        Boolean err=false;

        try{
            response=  fileService.deleteAttachment(SecurityUtils.getToken(), Integer.valueOf(docObligatoriuExtra.getDocObligatoriu().getIdFisierAnexat()), getDocRaspunsId().get());
            if(docObligatoriuExtra.getDocObligatoriu().getDocumentRaspuns()==1){
                idAdresaRspunsCurenta=null;
            }
            long documentId = new Long(getDocumentId().get());
            //UI.getCurrent().getPage().executeJs("swalInfo($0);", "Fisierul a fost sters!");
            DocObligatoriiResp docObligatoriiResp= getPs4Service().getCriteriiScrisoareLipsuri(documentId, getDocRaspunsId());
            nrAdresaRspuns= Optional.ofNullable(docObligatoriiResp.getNrInregRaspuns());
            getView().setNeededDocumentsTable(docObligatoriiResp.getDocObligatoriuList().stream().map(d -> new DocObligatoriuExtra(d)).collect(Collectors.toList()));

        }
        catch (Throwable t) {

            getLogger().error("uploadFile error get file data", t.getStackTrace());
            response.setExtendedInfo(((ServerWebInputException) t).getReason());
            err=true;

        }
        if(err){
            UI.getCurrent().getPage().executeJs("swalError($0);", response.getExtendedInfo());

        }




    }

}
