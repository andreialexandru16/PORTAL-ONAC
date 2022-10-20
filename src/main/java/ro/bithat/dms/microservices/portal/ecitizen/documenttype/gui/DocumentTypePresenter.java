package ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui;

import com.vaadin.flow.component.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import ro.bithat.dms.microservices.dmsws.file.DmswsFileService;
import ro.bithat.dms.microservices.dmsws.file.PortalFile;
import ro.bithat.dms.microservices.dmsws.ps4.PS4Service;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.Document;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.TipDocument;
import ro.bithat.dms.microservices.portal.ecitizen.portalfile.backend.Ps4ECitizenPortalFileService;
import ro.bithat.dms.microservices.portal.ecitizen.gui.ContentContainerView;
import ro.bithat.dms.passiveview.QueryParameterUtil;
import ro.bithat.dms.passiveview.boot.I18NProviderStatic;
import ro.bithat.dms.passiveview.component.presenter.PrepareModelFlowPresenter;
import ro.bithat.dms.passiveview.mvp.observer.FlowPresenterAfterPrepareModelObserver;
import ro.bithat.dms.security.SecurityUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class DocumentTypePresenter<V extends ContentContainerView> extends PrepareModelFlowPresenter<V> implements FlowPresenterAfterPrepareModelObserver {

    @Value("${ps4.ecitizen.workflow.status.changes.required}")
    private Integer idWorkflowStatusChangesRequired;

    @Autowired
    private PS4Service ps4Service;

    @Autowired
    private Ps4ECitizenPortalFileService portalFileService;

    @Autowired
    private DmswsFileService fileService;

    private Integer documentType;

    private Optional<Document> document = Optional.empty();

    private Optional<Integer> documentId = Optional.empty();

    private Optional<TipDocument> documentClass = Optional.empty();

    private Optional<Integer> fileId = Optional.empty();
    private Optional<String> fromMainScreen = Optional.empty();
    private Optional<Integer> idPerioada = Optional.empty();
    private Optional<Integer> idTert = Optional.empty();


    private Optional<Integer> docRaspunsId = Optional.empty();

    private Optional<PortalFile> portalFile = Optional.empty();

    @Override
    public void prepareModel(String state) {
        idPerioada = QueryParameterUtil.getQueryParameter("idPerioada", Integer.class);
        idTert = QueryParameterUtil.getQueryParameter("idTert", Integer.class);
        fromMainScreen = QueryParameterUtil.getQueryParameter("fromMainScreen", String.class);
        documentType = QueryParameterUtil.getQueryParameter("tipDocument", Integer.class).get();
        documentId = QueryParameterUtil.getQueryParameter("document", Integer.class);
        fileId = QueryParameterUtil.getQueryParameter("request", Integer.class);
        docRaspunsId = QueryParameterUtil.getQueryParameter("docRaspunsId", Integer.class);
        if(documentId.isPresent()) {
            document = ps4Service.getDocumentByIdAndClasa(documentType,documentId.get());
        }
        if(fileId.isPresent()) {
            try {
                fileService.checkSecurityByFileId(SecurityUtils.getToken(), fileId.get());
            } catch (Throwable t) {
                getLogger().error("User access PORTAL file  id:\t"+getFileId().get()
                        +"\t system user id:\t"+SecurityUtils.getUserId()
                        + "\r\n\tbackend exception:\t" + t.getMessage(), t.getStackTrace());
                UI.getCurrent().getPage().executeJs("swalError($0, $1)",
                        "Resursa se afla intr-o zona protejata sau nu va apartine!", getView());
                return;
            }
            portalFile = Optional.ofNullable(portalFileService.getPortalFileByFileId(fileId.get()));
        }
        if(DocumentTypeView.class.isAssignableFrom(getView().getClass())) {
            ((DocumentTypeView) getView()).setContentPageTile(getDocumentClass().get(), document);
        }
    }

    public PS4Service getPs4Service() {
        return ps4Service;
    }

    public Optional<Integer> getDocRaspunsId() {
        return docRaspunsId;
    }

    public void setDocRaspunsId(Optional<Integer> docRaspunsId) {
        this.docRaspunsId = docRaspunsId;
    }

    public void setPs4Service(PS4Service ps4Service) {
        this.ps4Service = ps4Service;
    }

    public Integer getDocumentType() {
        return documentType;
    }

    public Optional<Integer> getDocumentId() {
        return documentId;
    }

    public Optional<Integer> getFileId() {
        return fileId;
    }

    public void setFileId(Optional<Integer> fileId) {
        this.fileId = fileId;
    }

    public Optional<TipDocument> getDocumentClass() {
        if(!documentClass.isPresent()){
            documentClass= ps4Service.getDocumentType(documentType);
        }
        return documentClass;
    }

    public Optional<Document> getDocument() {
        return document;
    }

    public void setDocument(Optional<Document> document) {
        this.document = document;
    }

    public Optional<PortalFile> getPortalFile() {
        return portalFile;
    }

    public boolean isPortalFileEditable() {
        return (portalFile.isPresent() && portalFile.get().getIdWorkflowStatus() == null);
    }
    
    public boolean portalFileNeedChanges() {
        return (portalFile.isPresent() && 
        		portalFile.get().getIdWorkflowStatus() != null &&
        		portalFile.get().getIdWorkflowStatus().equals(idWorkflowStatusChangesRequired));
    }
    
    public String getPortalFileTitle() {
        String portalTitle="";

        if(getFromMainScreen().isPresent() && getFromMainScreen().get()!=null){
            portalTitle=I18NProviderStatic.getTranslation("ps4.ecetatean.request.type.title.raportare");
        }else{
            portalTitle=portalFile.isPresent() && portalFile.get().getDenumireWorkflowStatus() != null ?
                    I18NProviderStatic.getTranslation("ps4.ecetatean.request.type.title") + portalFile.get().getDenumireWorkflowStatus() : I18NProviderStatic.getTranslation("ps4.ecetatean.newrequest.type.title")
            ;
        }

        return portalTitle;
    }

    public Map<String, Object> getPortalFileParamsMap(){
        Map<String, Object> filterPageParameters = new HashMap<>();
        filterPageParameters.put("tipDocument", getDocumentType());
        filterPageParameters.put("document", getDocumentId().get());
        filterPageParameters.put("request", getFileId().get());
        if(getFromMainScreen().isPresent()){
            filterPageParameters.put("fromMainScreen", getFromMainScreen().get());

        }
        if(getIdPerioada().isPresent()){
            filterPageParameters.put("idPerioada", getIdPerioada().get());

        }
        if(getIdTert().isPresent()){
            filterPageParameters.put("idTert", getIdTert().get());

        }
        return filterPageParameters;
    }

    public String getWorkflowStateTitle() {
        return portalFile.isPresent() && portalFile.get().getDenumireWorkflowStatus() != null ?
                portalFile.get().getDenumireWorkflowStatus() :
                "";
    }

    public Optional<String> getFromMainScreen() {
        return fromMainScreen;
    }

    public void setFromMainScreen(Optional<String> fromMainScreen) {
        this.fromMainScreen = fromMainScreen;
    }

    public Optional<Integer> getIdPerioada() {
        return idPerioada;
    }

    public Optional<Integer> getIdTert() {
        return idTert;
    }
}
