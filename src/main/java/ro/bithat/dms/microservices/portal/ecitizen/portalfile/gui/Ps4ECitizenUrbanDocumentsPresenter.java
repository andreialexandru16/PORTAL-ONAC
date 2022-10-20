package ro.bithat.dms.microservices.portal.ecitizen.portalfile.gui;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import ro.bithat.dms.microservices.dmsws.file.DmswsFileService;
import ro.bithat.dms.microservices.dmsws.poi.CategoriePOIList;
import ro.bithat.dms.microservices.dmsws.poi.ProjectInfo;
import ro.bithat.dms.microservices.dmsws.poi.ProjectsList;
import ro.bithat.dms.microservices.dmsws.poi.StatusProiectList;
import ro.bithat.dms.microservices.dmsws.ps4.PS4Service;
import ro.bithat.dms.microservices.portal.ecitizen.participatorybudgeting.backend.DmswsParticipatoryBudgetingService;
import ro.bithat.dms.microservices.portal.ecitizen.project.backend.ProjectService;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.DmswsUtilizatorService;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat.PersoanaFizicaJuridica;
import ro.bithat.dms.passiveview.QueryParameterUtil;
import ro.bithat.dms.passiveview.component.presenter.PrepareModelFlowPresenter;
import ro.bithat.dms.security.SecurityUtils;

import java.util.List;
import java.util.Optional;

public class Ps4ECitizenUrbanDocumentsPresenter extends PrepareModelFlowPresenter<Ps4ECitizenUrbanDocumentsView> {

    @Value("${google.maps.api.key2}")
    private String googleMapsApiKey;

    @Value("${poi.project.type.doc.urbanism.id}")
    private String projectTypeDUId;

    @Value("${poi.document.type.id:22220}")
    private Integer poiDocumentTypeId;

    @Value("${poi.attachment.files.type.code}")
    private String poiAttachmentTypeCode;

    @Value("${poi.attachment.photos.type.code}")
    private String poiAttachmentPhotosTypeCode;

    @Value("${poi.attachment.videos.type.code}")
    private String poiAttachmentVideosTypeCode;
    @Autowired
    private DmswsUtilizatorService dmswsUtilizatorService;

    @Autowired
    private DmswsFileService fileService;

    private Optional<PersoanaFizicaJuridica> pfj = Optional.empty();

    private Optional<ProjectInfo> poiInfo = Optional.empty();

    private Optional<CategoriePOIList> poiCateories = Optional.empty();

    private Optional<StatusProiectList> poiStages = Optional.empty();
    private Optional<ProjectsList> projectsInfoList= Optional.empty();

    private Boolean markOnMap = false;

    private Boolean fromDepunere = false;

    private Integer poiId = null;

    private Integer documentId = null;

    private ParticipatoryBudgetingActionForm participatoryBudgetingActionForm = ParticipatoryBudgetingActionForm.BUDGETING;

    @Autowired
    private PS4Service ps4Service;
    @Autowired
    private ProjectService projectService;


    @Autowired
    private DmswsParticipatoryBudgetingService participatoryBudgetingService;

    public void setPoiId(Integer poiId) {
        this.poiId = poiId;
        poiInfo = Optional.ofNullable(participatoryBudgetingService.getInfoPOI(SecurityUtils.getToken(), poiId));

    }

    public String getGoogleMapsApiKey() {
        return googleMapsApiKey;
    }

    public void setParticipatoryBudgetingActionForm(ParticipatoryBudgetingActionForm participatoryBudgetingActionForm) {
        this.participatoryBudgetingActionForm = participatoryBudgetingActionForm;
        loadBusinessPois();
    }

    public ParticipatoryBudgetingActionForm getParticipatoryBudgetingActionForm() {
        return participatoryBudgetingActionForm;
    }

    @Override
    public void prepareModel(String state) {
        fromDepunere = QueryParameterUtil.getQueryParameter("fromDepunere", Boolean.class).get();
        documentId = QueryParameterUtil.getQueryParameter("document", Integer.class).get();

        if (hasAuthenticateUser()) {
                poiCateories = Optional.ofNullable(participatoryBudgetingService.getCategoriiPOI(SecurityUtils.getToken()));

                getView().displayBudgetaryTab();

        }

    }

    public void serviceAdminForm() {
        UI.getCurrent().getPage().executeJs("clearMapMarker();");
    }

    enum ParticipatoryBudgetingActionForm {
        CONSULTATION, BUDGETING, ADMIN
    }

    public void afterLoadingGmaps() {
        loadBusinessPois();

    }

    private void loadBusinessPois() {
        String token = SecurityUtils.getToken();
        UI.getCurrent().getPage().executeJs("clearMapMarker();");
        markOnMap=false;
        loadAllPois(token);

    }



    private void loadAllPois(String token) {
        List<ProjectInfo> infoPois = participatoryBudgetingService.getPOIs(token, Optional.empty(), Optional.empty(), Optional.empty(), Optional.of(projectTypeDUId), Optional.empty()).getProjectInfoList();
        try {
            UI.getCurrent().getPage().executeJs("initBudgetMap($0, $1);", new ObjectMapper().writeValueAsString(infoPois), getView().getElement());
            getView().setProjectsBudgetary(infoPois, documentId);

        } catch (JsonProcessingException e) {
            getLogger().error("error serialize infoPois list size:\t" + infoPois.size(), e);
        }
    }

//    @DomEventPresenterMethod(viewProperty = "chunkUpload", eventType = SucceededEvent.class)
//    public void onFileUpload(SucceededEvent  succeededEvent) {
//        getLogger().info("upload file:\t");
//        uploadedFiles = getView().getMultiFileMemoryBuffer().getFiles();
//    }





    public Boolean hasAuthenticateUser() {
        return !SecurityUtils.getUsername().equalsIgnoreCase("nouser");
    }



}
