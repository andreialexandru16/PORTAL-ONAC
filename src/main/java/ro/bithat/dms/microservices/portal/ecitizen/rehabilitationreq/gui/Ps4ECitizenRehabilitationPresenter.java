package ro.bithat.dms.microservices.portal.ecitizen.rehabilitationreq.gui;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.NativeButton;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import ro.bithat.dms.microservices.dmsws.file.DmswsFileService;
import ro.bithat.dms.microservices.dmsws.file.FileData;
import ro.bithat.dms.microservices.dmsws.poi.*;
import ro.bithat.dms.microservices.dmsws.ps4.PS4Service;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.CreateTipDocFileResponse;
import ro.bithat.dms.microservices.portal.ecitizen.participatorybudgeting.backend.DmswsParticipatoryBudgetingService;
import ro.bithat.dms.microservices.portal.ecitizen.rehabilitationreq.backend.DmswsRehabilitationService;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.DmswsUtilizatorService;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat.PersoanaFizicaJuridica;
import ro.bithat.dms.passiveview.ClickEventPresenterMethod;
import ro.bithat.dms.passiveview.QueryParameterUtil;
import ro.bithat.dms.passiveview.component.html.ClickNotifierAnchor;
import ro.bithat.dms.passiveview.component.presenter.PrepareModelFlowPresenter;
import ro.bithat.dms.security.SecurityUtils;

import java.util.List;
import java.util.Optional;

public class Ps4ECitizenRehabilitationPresenter extends PrepareModelFlowPresenter<Ps4ECitizenRehabilitationView> {


    @Value("${poi.project.type.reabilitare.id}")
    private String projectTypeLUId;

    @Value("${google.maps.api.key2}")
    private String googleMapsApiKey;

    @Value("${poi.document.type.id:22220}")
    private Integer poiDocumentTypeId;

    @Value("${poi.attachment.type.id:22221}")
    private Integer poiAttachmentTypeId;

    @Autowired
    private DmswsUtilizatorService dmswsUtilizatorService;

    @Autowired
    private DmswsFileService fileService;

    private Optional<PersoanaFizicaJuridica> pfj = Optional.empty();

    private Optional<ProjectInfo> poiInfo = Optional.empty();

    private Optional<CategoriePOIList> poiCateories = Optional.empty();

    private Optional<DurataProiectList> durataProiectList = Optional.empty();

    private Optional<StatusProiectList> poiStages = Optional.empty();

    private Boolean markOnMap = false;

    private Boolean fromDepunere = false;

    private Integer poiId = null;

    private ParticipatoryBudgetingActionForm participatoryBudgetingActionForm = ParticipatoryBudgetingActionForm.BUDGETING;

    @Autowired
    private PS4Service ps4Service;

    @Autowired
    private DmswsRehabilitationService rehabilitationService;

    @Autowired
    private DmswsParticipatoryBudgetingService participatoryBudgetingService;

    public void setPoiId(Integer poiId) {
        this.poiId = poiId;
        poiInfo = Optional.ofNullable(rehabilitationService.getInfoPOI(SecurityUtils.getToken(), poiId));
        if (participatoryBudgetingActionForm == ParticipatoryBudgetingActionForm.BUDGETING
                && rehabilitationService.checkForUserVote(SecurityUtils.getToken(), poiId).getInfo().equals("true")) {
            getView().hideVote();
        }
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

        if (hasAuthenticateUser()) {
            if(fromDepunere){
                pfj = Optional.ofNullable(dmswsUtilizatorService.getPersoanaFizicaJuridica(SecurityUtils.getToken(), SecurityUtils.getUserId().intValue()));
                if (pfj.isPresent()) {
                    getView().setSurname(pfj.get().getNume());
                    getView().setName(pfj.get().getPrenume());
                    getView().setEmail(pfj.get().getEmail());
                    if (pfj.get().getTelefon() != null) {
                        getView().setPhone(pfj.get().getTelefon());

                    }
                }
                poiCateories = Optional.ofNullable(rehabilitationService.getCategoriiPOI(SecurityUtils.getToken()));
                getView().setCategories(poiCateories.get().getCategoriePOIList());

                durataProiectList = Optional.ofNullable(rehabilitationService.getDurataProiectList(SecurityUtils.getToken()));
                getView().setDurataProiectList(durataProiectList.get().getDurataProiectList());

                if (rehabilitationService.checkIfHasRole(SecurityUtils.getToken(), "POI_ADMIN").getInfo().equals("true")) {
                    poiStages = Optional.ofNullable(rehabilitationService.getListaStatusProiect(SecurityUtils.getToken()));
                    getView().setStates(poiStages.get().getStatusProiectList());
                    getView().displayPoiAdmin();
                }
                getView().displayBudgetaryTab();
                getView().hideBudgetary();
                getView().displayConsultationTab();
                getView().configNewConsultationForm(pfj);

            }
            else{
                pfj = Optional.ofNullable(dmswsUtilizatorService.getPersoanaFizicaJuridica(SecurityUtils.getToken(), SecurityUtils.getUserId().intValue()));
                if (pfj.isPresent()) {
                    getView().setSurname(pfj.get().getNume());
                    getView().setName(pfj.get().getPrenume());
                    getView().setEmail(pfj.get().getEmail());
                    if (pfj.get().getTelefon() != null) {
                        getView().setPhone(pfj.get().getTelefon());

                    }
                }
                poiCateories = Optional.ofNullable(rehabilitationService.getCategoriiPOI(SecurityUtils.getToken()));
                getView().setCategories(poiCateories.get().getCategoriePOIList());

                durataProiectList = Optional.ofNullable(rehabilitationService.getDurataProiectList(SecurityUtils.getToken()));
                getView().setDurataProiectList(durataProiectList.get().getDurataProiectList());

                if (rehabilitationService.checkIfHasRole(SecurityUtils.getToken(), "POI_ADMIN").getInfo().equals("true")) {
                    poiStages = Optional.ofNullable(rehabilitationService.getListaStatusProiect(SecurityUtils.getToken()));
                    getView().setStates(poiStages.get().getStatusProiectList());
                    getView().displayPoiAdmin();
                }
                getView().displayConsultationTab();

                getView().displayBudgetaryTab();
                getView().hideConsultation();
                //getView().configNewConsultationForm(pfj);
            }


        }

    }

    public void serviceAdminForm() {
        UI.getCurrent().getPage().executeJs("clearMapMarker();");
        getView().configAdminForm(poiInfo.get(), poiCateories.get().getCategoriePOIList(),durataProiectList.get().getDurataProiectList(), poiStages.get().getStatusProiectList());
    }

    enum ParticipatoryBudgetingActionForm {
        CONSULTATION, BUDGETING, ADMIN
    }

    public void afterLoadingGmaps() {
        loadBusinessPois();

    }

    private void loadBusinessPois() {
        String token = SecurityUtils.getToken();

        /*
        if (participatoryBudgetingActionForm == ParticipatoryBudgetingActionForm.BUDGETING) {
            loadApprovedPois(token);
        }
        if (participatoryBudgetingActionForm == ParticipatoryBudgetingActionForm.CONSULTATION
                || participatoryBudgetingActionForm == ParticipatoryBudgetingActionForm.ADMIN) {
            loadAllPois(token);
        }
        */

        loadAllPoisWithType(token);
    }

    private void loadDraftPois(String token) {
        List<ProjectInfo> infoPois = participatoryBudgetingService.getPOIs(token, Optional.empty(), Optional.empty(), Optional.empty(), Optional.of(projectTypeLUId), Optional.empty()).getProjectInfoList();
        try {
            UI.getCurrent().getPage().executeJs("initBudgetMap($0, $1);", new ObjectMapper().writeValueAsString(infoPois), getView().getElement());
        } catch (JsonProcessingException e) {
            getLogger().error("error serialize infoPois list size:\t" + infoPois.size(), e);
        }
    }

    private void loadApprovedPois(String token) {
        List<ProjectInfo> infoPois = participatoryBudgetingService.getPOIs(token, Optional.empty(), Optional.empty(), Optional.empty(), Optional.of(projectTypeLUId), Optional.empty()).getProjectInfoList();
        try {
            UI.getCurrent().getPage().executeJs("initBudgetMap($0, $1);", new ObjectMapper().writeValueAsString(infoPois), getView().getElement());
        } catch (JsonProcessingException e) {
            getLogger().error("error serialize infoPois list size:\t" + infoPois.size(), e);
        }
    }

    private void loadAllPois(String token) {
        List<ProjectInfo> infoPois =participatoryBudgetingService.getPOIs(token, Optional.empty(), Optional.empty(), Optional.empty(), Optional.of(projectTypeLUId), Optional.empty()).getProjectInfoList();
        try {
            UI.getCurrent().getPage().executeJs("initBudgetMap($0, $1);", new ObjectMapper().writeValueAsString(infoPois), getView().getElement());
        } catch (JsonProcessingException e) {
            getLogger().error("error serialize infoPois list size:\t" + infoPois.size(), e);
        }
    }

    private void loadAllPoisWithType(String token){
        List<ProjectInfo> infoPois = participatoryBudgetingService.getPOIs(token, Optional.empty(), Optional.empty(), Optional.empty(), Optional.of(projectTypeLUId), Optional.empty()).getProjectInfoList();
        try {
            UI.getCurrent().getPage().executeJs("initBudgetMap($0, $1);", new ObjectMapper().writeValueAsString(infoPois), getView().getElement());
        } catch (JsonProcessingException e) {
            getLogger().error("error serialize infoPois list size:\t" + infoPois.size(), e);
        }
    }

//    @DomEventPresenterMethod(viewProperty = "chunkUpload", eventType = SucceededEvent.class)
//    public void onFileUpload(SucceededEvent  succeededEvent) {
//        getLogger().info("upload file:\t");
//        uploadedFiles = getView().getMultiFileMemoryBuffer().getFiles();
//    }

    @ClickEventPresenterMethod(viewProperty = "consultationSendBtn")
    public void onConsultationFormSend(ClickEvent<ClickNotifierAnchor> clickEvent) {
        getLogger().info("send consultation form");
        if (!poiInfo.isPresent()) {
            UI.getCurrent().getPage().executeJs("swalError($0);", "Nu ati selectat punct pe harta!");
            return;
        }
        if (getView().getSurname().trim().isEmpty()) {
            UI.getCurrent().getPage().executeJs("swalError($0);", "Nu ati completat numele!");
            return;
        }
        poiInfo.get().setPropusDeNume(getView().getSurname());
        if (getView().getName().trim().isEmpty()) {
            UI.getCurrent().getPage().executeJs("swalError($0);", "Nu ati completat prenumele!");
            return;
        }
        poiInfo.get().setPropusDePrenume(getView().getName());
        if (getView().getEmail().trim().isEmpty()) {
            UI.getCurrent().getPage().executeJs("swalError($0);", "Nu ati completat adresa de email!");
            return;
        }
        poiInfo.get().setPropusDeEmail(getView().getEmail());
        if (getView().getPhone().trim().isEmpty()) {
            UI.getCurrent().getPage().executeJs("swalError($0);", "Nu ati completat numarul de telefon!");
            return;
        }
        poiInfo.get().setPropusDeTelefon(getView().getPhone());
        if (!getView().getCategory().isPresent()) {
            UI.getCurrent().getPage().executeJs("swalError($0);", "Nu ati completat selectat categoria!");
            return;
        }
        poiInfo.get().setIdCategoriePoi(getView().getCategory().get().getId());
        poiInfo.get().setCategoriePoi(getView().getCategory().get().getDenumire());

        if (getView().getAddress().trim().isEmpty()) {
            UI.getCurrent().getPage().executeJs("swalError($0);", "Nu ati completat adresa!");
            return;
        }
        poiInfo.get().setAddress(getView().getAddress());
        if (getView().getMessage().trim().isEmpty()) {
            UI.getCurrent().getPage().executeJs("swalError($0);", "Nu ati completat mesajul!");
            return;
        }
        poiInfo.get().setDescriere(getView().getMessage());
        if (!getView().hasTermsAgreement()) {
            UI.getCurrent().getPage().executeJs("swalError($0);", "Acceptati termenii si conditiile!");
            return;
        }

        poiInfo.get().setBeneficii(getView().getBeneficii());

        poiInfo.get().setBeneficiari(getView().getBeneficiari());

        if (participatoryBudgetingActionForm == ParticipatoryBudgetingActionForm.ADMIN) {
            if (!getView().getState().isPresent()) {
                UI.getCurrent().getPage().executeJs("swalError($0);", "Nu ati completat selectat stadiul!");
                return;
            }
            StatusProiect statusProiect = getView().getState().get();
            poiInfo.get().setIdStatusProiect(statusProiect.getId());
            poiInfo.get().setStatusProiect(statusProiect.getDenumire());
            poiInfo.get().setDataStart(getView().getStartDate());
            poiInfo.get().setDataEnd(getView().getDueDate());
            poiInfo.get().setPublicat(getView().getPublicat());
            if (getView().getValue().trim().isEmpty()) {
                UI.getCurrent().getPage().executeJs("swalError($0);", "Nu ati completat valoarea!");
                return;
            }
        }
        poiInfo.get().setValoare(Double.valueOf(getView().getValue()));

        poiInfo.get().setIdJudet(121);
        poiInfo.get().setIdLocalitate(444);
        poiInfo.get().setDenumire(getView().getMessage().substring(0, getView().getMessage().length() > 25 ? 25 : getView().getMessage().length()));
        if (poiId != null) { // update
            poiInfo.get().setId(poiId);
            poiInfo = Optional.ofNullable(rehabilitationService.updatePOI(SecurityUtils.getToken(), poiInfo.get(), poiId));
        } else {
            poiId = Optional.ofNullable(rehabilitationService.addNewPOI(SecurityUtils.getToken(), poiInfo.get())).get().getId();
        }
        if (!poiInfo.isPresent() || !poiInfo.get().getResult().equals("OK")) {
//            rehabilitationService.
            UI.getCurrent().getPage().executeJs("swalError($0);", "Eroare salvare DMSWS!");
            return;
        } else {
            UI.getCurrent().getPage().executeJs("swalInfo($0);", "Propunerea a fost incarcata cu succes!");
        }
        try {
            if (getView().getMultiFileMemoryBuffer().getFiles().size() > 0) {
                getLogger().info("consultation form has:\t" + getView().getMultiFileMemoryBuffer().getFiles().size() + " uploaded files");
                FileData fileData = new FileData();
                fileData.setId_document(poiDocumentTypeId);
                Integer fileId = ps4Service.createDummyFileIncepereSolicitare(fileData);
                getView().getMultiFileMemoryBuffer().getFiles().forEach(fileName -> uploadAttachment(fileId, fileName, poiId));
            }
        } catch (Throwable t) {
            getLogger().error("Eroare la upload fisier!", t);
        }
        markOnMap = false;
        UI.getCurrent().getPage().executeJs("clearMapMarker();");
        loadBusinessPois();
        getView().configNewMarkerPlacementConsultationForm();
        poiInfo = Optional.empty();
        poiId = null;
    }

    private void uploadAttachment(Integer parentFileId, String fileName, Integer poiId) {
        try {
            String token = SecurityUtils.getToken();
            CreateTipDocFileResponse response = fileService.uploadFisierTipDocId(token, poiAttachmentTypeId, SecurityUtils.getUserId(), fileName, fileName,
                    IOUtils.toByteArray(getView().getMultiFileMemoryBuffer().getInputStream(fileName)),Optional.empty());
            fileService.attachFile(token, parentFileId, Integer.parseInt(response.getFileId()));
            ProjectFile poiFile = rehabilitationService.addPOIFile(token, poiId, Integer.parseInt(response.getFileId()));
            if (poiFile.getResult().equals("OK")) {
                getLogger().info("Fisierul cu id-ul:\t" + Integer.parseInt(response.getFileId()) + "\ta fost atribuit la poiId:\t" + poiId);
            } else {
                getLogger().error("Fisierul cu id-ul:\t" + Integer.parseInt(response.getFileId()) + "\tnu a fost atribuit la poiId:\t" + poiId + "\t!!!");
            }
        } catch (Throwable e) {
            getLogger().error("Eroare la upload fisier!", e);
        }
    }

    @ClickEventPresenterMethod(viewProperty = "consultationMarkerBtn")
    public void onConsultationFormMarked(ClickEvent<ClickNotifierAnchor> clickEvent) {
        getLogger().info("mark on consultation form");
        markOnMap = true;
        getView().configNewConsultationForm(Optional.ofNullable(dmswsUtilizatorService.getPersoanaFizicaJuridica(SecurityUtils.getToken(), SecurityUtils.getUserId().intValue())));
    }

    @ClickEventPresenterMethod(viewProperty = "consultationCancelBtn")
    public void onConsultationFormCanceled(ClickEvent<ClickNotifierAnchor> clickEvent) {
        getLogger().info("cancel consultation form");
        UI.getCurrent().getPage().executeJs("clearMapMarker();");
        markOnMap = false;
        getView().configNewMarkerPlacementConsultationForm();
        poiInfo = Optional.empty();
    }

    @ClickEventPresenterMethod(viewProperty = "upVoteProjectBtn")
    public void onUpVoteProject(ClickEvent<NativeButton> clickEvent) {
        getLogger().info("upvote project poiId:\t" + poiId);
        if (!hasAuthenticateUser()) {
            getLogger().error("Security alert! No logged user wants to vote");
            return;
        }
        rehabilitationService.POIVote(SecurityUtils.getToken(), poiId, 1);
        getView().configNewBudgetaryTab();
        UI.getCurrent().getPage().executeJs("clearMapPopup(); swalInfo($0);", "Votul a fost inregistrat!");
    }

    @ClickEventPresenterMethod(viewProperty = "downVoteProjectBtn")
    public void onDownVoteProject(ClickEvent<NativeButton> clickEvent) {
        getLogger().info("downvote project poiId:\t" + poiId);
        if (!hasAuthenticateUser()) {
            getLogger().error("Security alert! No logged user wants to vote");
            return;
        }
        rehabilitationService.POIVote(SecurityUtils.getToken(), poiId, 0);
        getView().configNewBudgetaryTab();
        UI.getCurrent().getPage().executeJs("clearMapPopup(); swalInfo($0);", "Votul a fost inregistrat!");
    }

    public void onMapLocation(String address, Double lat, Double lng) {
        if (markOnMap && (participatoryBudgetingActionForm == ParticipatoryBudgetingActionForm.CONSULTATION
                || participatoryBudgetingActionForm == ParticipatoryBudgetingActionForm.ADMIN)) {
            poiId = null;
            poiInfo = Optional.of(new ProjectInfo());
            poiInfo.get().setLatitudine(lat);
            poiInfo.get().setLongitudine(lng);
            getView().setAddress(address);
            getView().configNewConsultationForm(pfj);
            UI.getCurrent().getPage().executeJs("addMapMarker($0, $1);", lat, lng);
        }
    }

    @ClickEventPresenterMethod(viewProperty = "consultationFormApproved")
    public void onConsultationFormApprovedClick(ClickEvent<ClickNotifierAnchor> clickEvent) {
        getLogger().info("approved clicked");
        if (getView().getConsultationFormApprovedValue() && getView().getConsultationFormDraftValue()) {
            loadAllPois(SecurityUtils.getToken());
        } else if (getView().getConsultationFormApprovedValue() && !getView().getConsultationFormDraftValue()) {
            loadApprovedPois(SecurityUtils.getToken());

        } else if (!getView().getConsultationFormApprovedValue() && getView().getConsultationFormDraftValue()) {
            loadDraftPois(SecurityUtils.getToken());
        } else {
            loadAllPois(SecurityUtils.getToken());
        }
    }

    @ClickEventPresenterMethod(viewProperty = "consultationFormDraft")
    public void onConsultationFormDraftClick(ClickEvent<ClickNotifierAnchor> clickEvent) {
        getLogger().info("draft clicked");
        if (getView().getConsultationFormApprovedValue() && getView().getConsultationFormDraftValue()) {
            loadAllPois(SecurityUtils.getToken());
        } else if (getView().getConsultationFormApprovedValue() && !getView().getConsultationFormDraftValue()) {
            loadApprovedPois(SecurityUtils.getToken());

        } else if (!getView().getConsultationFormApprovedValue() && getView().getConsultationFormDraftValue()) {
            loadDraftPois(SecurityUtils.getToken());
        } else {
            loadAllPois(SecurityUtils.getToken());
        }
    }

    public Boolean hasAuthenticateUser() {
        return !SecurityUtils.getUsername().equalsIgnoreCase("nouser");
    }


}
