package ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui;
//13.07.2021 - NG - ANRE - resetare contor schimbari facute pentru a nu afisa dialog de confirmare iesire din pagina

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.upload.SucceededEvent;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import ro.bithat.dms.microservices.dmsws.file.DmswsFileService;
import ro.bithat.dms.microservices.dmsws.file.PortalFile;
import ro.bithat.dms.microservices.dmsws.ps4.documents.DocObligatoriuExtra;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.SolicitareService;
import ro.bithat.dms.passiveview.QueryParameterUtil;
import ro.bithat.dms.passiveview.VaadinClientUrlUtil;
import ro.bithat.dms.passiveview.boot.I18NProviderStatic;
import ro.bithat.dms.passiveview.component.html.ClickNotifierAnchor;
import ro.bithat.dms.security.SecurityUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Ps4ECitizenServiceAttacheMissingFilePresenter extends DocumentTypePresenter<Ps4ECitizenServiceAttacheMissingFileView> {

    @Autowired
    private DmswsFileService fileService;
    @Autowired
    private SolicitareService solictiareService;
    @Value("${ps4.ecitizen.workflow.status.request.completed}")
    private Integer idWorkflowStatusRequestCompleted;
    @Override
    public void afterPrepareModel(String state) {
        if(!getFromMainScreen().isPresent() || getFromMainScreen().get()==null){
            getView().buildBreadcrumbsWizard();

        }
        long documentId = new Long(getDocumentId().get());
        if(isPortalFileEditable()||portalFileNeedChanges()) {
            getView().setServiceNameAndRegisterPreviousStep(getDocument().get().getDenumire());
            List<DocObligatoriuExtra> listaDoc= getPs4Service().getDocumenteLipsaServiciu(documentId, getFileId());
                getView().setNeededDocumentsTable(listaDoc);


        } else {
            VaadinClientUrlUtil
                    .setLocation(QueryParameterUtil.getRelativePathWithQueryParameters(getPortalFileParamsMap(),
                            Ps4ECitizenServiceRequestReviewRoute.class));
        }
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

        }        VaadinClientUrlUtil.setLocation(QueryParameterUtil
                .getRelativePathWithQueryParameters(filterPageParameters, Ps4ECitizenServiceNewRequestRoute.class));
    }

    public void onNextBtnAction(ClickEvent<ClickNotifierAnchor> clickEvent) {
        //13.07.2021 - NG - ANRE - resetare contor schimbari facute pentru a nu afisa dialog de confirmare iesire din pagina
        if(getPs4Service().getDocumenteLipsaServiciu(new Long(getDocumentId().get()), getFileId())
                .stream().filter(docObligatoriuExtra -> docObligatoriuExtra.getDocObligatoriu().getObligatoriu()
                        && (!Optional.ofNullable(docObligatoriuExtra.getDocObligatoriu().getLinkFisierAnexat()).isPresent()
                        || docObligatoriuExtra.getDocObligatoriu().getLinkFisierAnexat().isEmpty())).count() != 0) {
            UI.getCurrent().getPage().executeJs("swalError($0);", "Vă rugăm să încărcați fișierele obligatorii!");

        } else {
            if (portalFileNeedChanges()){
                if(getFromMainScreen().isPresent() &&getFromMainScreen().get()!=null){

                    UI.getCurrent().getPage().executeJs("swalInfoParam2($0, $1,$2);", I18NProviderStatic.getTranslation("request.saved") ,getView(),"/PISC/PORTAL/main-screen-operator.html?&idClasaDoc="+getDocumentType());


                }
                else{
                    if(idWorkflowStatusRequestCompleted!=null){
                        solictiareService.setWorflowStatus(Long.valueOf(getFileId().get()) ,idWorkflowStatusRequestCompleted);
                    }
                    UI.getCurrent().getPage().executeJs("swalInfoBottomThenClose($0, $1);", I18NProviderStatic.getTranslation("cerere_completata"), getView());

                }

            }
        }


    }

    public void uploadFile(SucceededEvent event, MemoryBuffer fileBuffer, DocObligatoriuExtra docObligatoriuExtra) {
        try {
            if(Optional.ofNullable(docObligatoriuExtra.getDocObligatoriu().getLinkFisierAnexat()).isPresent() &&
                        !docObligatoriuExtra.getDocObligatoriu().getLinkFisierAnexat().isEmpty()) {
               fileService.uploadToReplaceExistingFile(SecurityUtils.getToken(), Long.valueOf(docObligatoriuExtra.getDocObligatoriu().getIdFisierAnexat()),
                       event.getFileName(), IOUtils.toByteArray(fileBuffer.getInputStream()));
            } else {
                getPs4Service().uploadFileAndAttach(Long.valueOf(getFileId().get()), docObligatoriuExtra, event.getFileName(), IOUtils.toByteArray(fileBuffer.getInputStream()));
            }
            //UI.getCurrent().getPage().executeJs("swalInfo($0);", "Fisierul a fost incarcat cu succes!");
            getLogger().info("uploadFile filename:\t", event.getFileName());
        } catch (Throwable t) {
            /*String message = t.getMessage();
            if (message == null){
                if (t.getCause() != null){
                    message = t.getCause().getMessage();
                }
            }
            if (t.getMessage() == null){
                message = t.toString();
            }

            StringBuilder ste = new StringBuilder();
            for (StackTraceElement stee: t.getStackTrace()){
                ste.append(stee.toString()).append("\n");
            }

            getLogger().error("ERR UPLOAD SITUATION: " + message + "\n" + ste.toString());*/
            getLogger().error("uploadFile error get file data", t.getStackTrace());
            UI.getCurrent().getPage().executeJs("swalError($0);", "Fisierul nu a putut fi incarcat, eroare DMSWS! Va rugam reincercati!");
        }
        //TODO sweet alert
        long documentId = new Long(getDocumentId().get());
        getView().setNeededDocumentsTable(getPs4Service().getDocumenteLipsaServiciu(documentId, getFileId()));
        UI.getCurrent().getPage().executeJs("$('#resize_iframe', window.parent.document).trigger('click')");

//        Map<String, Object> filterPageParameters = new HashMap<>();
//        filterPageParameters.put("tipDocument", getDocumentType());
//        filterPageParameters.put("document", getDocumentId().get());
//        filterPageParameters.put("request", getFileId().get());

//        UI.getCurrent().getPage().setLocation(QueryParameterUtil
//                .getRelativePathWithQueryParameters(filterPageParameters, Ps4ECitizenServiceAttacheFileRoute.class));

    }


    public void deleteFile(ClickEvent event, DocObligatoriuExtra docObligatoriuExtra) {
        getLogger().info("delete file");
        //TODO   check Octavian
        fileService.deleteAttachmentOnFlow(SecurityUtils.getToken(), Integer.valueOf(docObligatoriuExtra.getDocObligatoriu().getIdFisierAnexat()), getFileId().get());
        long documentId = new Long(getDocumentId().get());
        //UI.getCurrent().getPage().executeJs("swalInfo($0);", "Fisierul a fost sters!");
        getView().setNeededDocumentsTable(getPs4Service().getDocumenteLipsaServiciu(documentId, getFileId()));
//        Map<String, Object> filterPageParameters = new HashMap<>();
//        filterPageParameters.put("tipDocument", getDocumentType());
//        filterPageParameters.put("document", getDocumentId().get());
//        filterPageParameters.put("request", getFileId().get());
//
//        UI.getCurrent().getPage().setLocation(QueryParameterUtil
//                .getRelativePathWithQueryParameters(filterPageParameters, Ps4ECitizenServiceAttacheFileRoute.class));

    }


    public void saveDescriptionFile(ClickEvent event, DocObligatoriuExtra docObligatoriuExtra) {
        getLogger().info("saveDescriptionFile");
        //TODO   check Octavian
        PortalFile file = new PortalFile();
        String idTextArea= docObligatoriuExtra.getIdTextareDescription();
        String description  = null;
        try{
            description = getView().getValueFileDescription(idTextArea);
        }
        catch (Exception e){
            description=null;
        }
        if(description!=null){
            file.setDescriere(description);
            file.setId(Integer.valueOf(docObligatoriuExtra.getDocObligatoriu().getIdFisierAnexat()));
            fileService.saveDescriptionFile(SecurityUtils.getToken(),file);

            UI.getCurrent().getPage().executeJs("swalInfo($0);", "Descrierea a fost salvată!");
        }else{
            UI.getCurrent().getPage().executeJs("swalInfo($0);", "Descrierea nu a putut fi preluată!");

        }

        //getView().setNeededDocumentsTable(getPs4Service().getDocumenteObligatoriiServiciu(documentId, getFileId()));

    }
}
