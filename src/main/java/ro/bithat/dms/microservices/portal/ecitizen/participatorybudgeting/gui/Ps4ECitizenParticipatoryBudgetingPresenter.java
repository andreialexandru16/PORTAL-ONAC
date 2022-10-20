package ro.bithat.dms.microservices.portal.ecitizen.participatorybudgeting.gui;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.NativeButton;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import ro.bithat.dms.microservices.dmsws.file.BaseModel;
import ro.bithat.dms.microservices.dmsws.file.DmswsFileService;
import ro.bithat.dms.microservices.dmsws.file.FileData;
import ro.bithat.dms.microservices.dmsws.poi.*;
import ro.bithat.dms.microservices.dmsws.ps4.PS4Service;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.CreateTipDocFileResponse;
import ro.bithat.dms.microservices.portal.ecitizen.participatorybudgeting.backend.DmswsParticipatoryBudgetingService;
import ro.bithat.dms.microservices.portal.ecitizen.project.backend.ProjectService;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.DmswsUtilizatorService;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat.PersoanaFizicaJuridica;
import ro.bithat.dms.passiveview.ClickEventPresenterMethod;
import ro.bithat.dms.passiveview.QueryParameterUtil;
import ro.bithat.dms.passiveview.component.html.ClickNotifierAnchor;
import ro.bithat.dms.passiveview.component.presenter.PrepareModelFlowPresenter;
import ro.bithat.dms.security.SecurityUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class Ps4ECitizenParticipatoryBudgetingPresenter extends PrepareModelFlowPresenter<Ps4ECitizenParticipatoryBudgetingView> {

    @Value("${google.maps.api.key2}")
    private String googleMapsApiKey;

    @Value("${poi.project.type.bp.id}")
    private String projectTypeBPId;

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
       /* if (participatoryBudgetingActionForm == ParticipatoryBudgetingActionForm.BUDGETING
                && participatoryBudgetingService.checkForUserVote(SecurityUtils.getToken(), poiId).getInfo().equals("true")) {
            getView().hideVote();
        }*/
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
                poiCateories = Optional.ofNullable(participatoryBudgetingService.getCategoriiPOI(SecurityUtils.getToken()));
                getView().setCategories(poiCateories.get().getCategoriePOIList());


                if (participatoryBudgetingService.checkIfHasRole(SecurityUtils.getToken(), "POI_ADMIN").getInfo().equals("true")) {
                    poiStages = Optional.ofNullable(participatoryBudgetingService.getListaStatusProiect(SecurityUtils.getToken()));
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
                poiCateories = Optional.ofNullable(participatoryBudgetingService.getCategoriiPOI(SecurityUtils.getToken()));
                getView().setCategories(poiCateories.get().getCategoriePOIList());


                if (participatoryBudgetingService.checkIfHasRole(SecurityUtils.getToken(), "POI_ADMIN").getInfo().equals("true")) {
                    poiStages = Optional.ofNullable(participatoryBudgetingService.getListaStatusProiect(SecurityUtils.getToken()));
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
        getView().configAdminForm(poiInfo.get(), poiCateories.get().getCategoriePOIList(),poiStages.get().getStatusProiectList());
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

        if (participatoryBudgetingActionForm == ParticipatoryBudgetingActionForm.BUDGETING) {
            loadApprovedPois(token);
        }
        else if (participatoryBudgetingActionForm == ParticipatoryBudgetingActionForm.CONSULTATION){
            loadAllPois(token);
        }
        else if( participatoryBudgetingActionForm == ParticipatoryBudgetingActionForm.ADMIN) {
            loadDraftPois(token);
        }
    }

    public void loadDraftPois(String token) {
        List<ProjectInfo> infoPois = participatoryBudgetingService.getPOIs(token, Optional.empty(), Optional.empty(), Optional.of("NEPUBLICAT"), Optional.of(projectTypeBPId), Optional.empty()).getProjectInfoList();
        try {
            UI.getCurrent().getPage().executeJs("initBudgetMap($0, $1);", new ObjectMapper().writeValueAsString(infoPois), getView().getElement());
            if (participatoryBudgetingActionForm == ParticipatoryBudgetingActionForm.BUDGETING) {
                getView().setProjectsBudgetary(infoPois);
            }
            else if (participatoryBudgetingActionForm == ParticipatoryBudgetingActionForm.CONSULTATION){
                getView().setProjectsConsultation(infoPois);
            }
            else {
                getView().setProjectsBudgetary(infoPois);
                getView().setProjectsConsultation(infoPois);

            }
        } catch (JsonProcessingException e) {
            getLogger().error("error serialize infoPois list size:\t" + infoPois.size(), e);
        }
    }

    private void loadApprovedPois(String token) {
        List<ProjectInfo> infoPois = participatoryBudgetingService.getPOIs(token, Optional.empty(), Optional.empty(), Optional.of("PUBLICAT"), Optional.of(projectTypeBPId), Optional.empty()).getProjectInfoList();
        try {
            UI.getCurrent().getPage().executeJs("initBudgetMap($0, $1);", new ObjectMapper().writeValueAsString(infoPois), getView().getElement());
            if (participatoryBudgetingActionForm == ParticipatoryBudgetingActionForm.BUDGETING) {
                getView().setProjectsBudgetary(infoPois);
            }
            else if (participatoryBudgetingActionForm == ParticipatoryBudgetingActionForm.CONSULTATION){
                getView().setProjectsConsultation(infoPois);
            }
            else {
                getView().setProjectsBudgetary(infoPois);
                getView().setProjectsConsultation(infoPois);

            }
        } catch (JsonProcessingException e) {
            getLogger().error("error serialize infoPois list size:\t" + infoPois.size(), e);
        }
    }

    private void loadAllPois(String token) {
        List<ProjectInfo> infoPois = participatoryBudgetingService.getPOIs(token, Optional.empty(), Optional.empty(), Optional.empty(), Optional.of(projectTypeBPId), Optional.empty()).getProjectInfoList();
        try {
            UI.getCurrent().getPage().executeJs("initBudgetMap($0, $1);", new ObjectMapper().writeValueAsString(infoPois), getView().getElement());
            if (participatoryBudgetingActionForm == ParticipatoryBudgetingActionForm.BUDGETING) {
                getView().setProjectsBudgetary(infoPois);
            }
            else if (participatoryBudgetingActionForm == ParticipatoryBudgetingActionForm.CONSULTATION){
                getView().setProjectsConsultation(infoPois);
            }
            else {
                getView().setProjectsBudgetary(infoPois);
                getView().setProjectsConsultation(infoPois);

            }
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
            UI.getCurrent().getPage().executeJs("swalError($0);", "Nu ati selectat categoria!");
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
            UI.getCurrent().getPage().executeJs("swalError($0);", "Nu ati completat denumirea proiectului!");
            return;
        }
        if(getView().getDescription().length()>4000){
            UI.getCurrent().getPage().executeJs("swalError($0);", "Textul descrierii depaseste limita de caractere.");
            return;
        }
        if(getView().getBeneficii().length()>4000){
            UI.getCurrent().getPage().executeJs("swalError($0);", "Textul de beneficii depaseste limita de caractere.");
            return;
        }
        if(getView().getBeneficiari().length()>4000){
            UI.getCurrent().getPage().executeJs("swalError($0);", "Textul de beneficiari depaseste limita de caractere.");
            return;
        }
        poiInfo.get().setDescriere(getView().getDescription());
        if (!getView().hasTermsAgreement()) {
            UI.getCurrent().getPage().executeJs("swalError($0);", "Acceptati termenii si conditiile!");
            return;
        }

        poiInfo.get().setBeneficii(getView().getBeneficii());

        poiInfo.get().setBeneficiari(getView().getBeneficiari());
        poiInfo.get().setTipProiect("BP");
/*
        if (participatoryBudgetingActionForm == ParticipatoryBudgetingActionForm.CONSULTATION) {
            poiInfo.get().setTipProiect("BP");
        }else if (participatoryBudgetingActionForm == ParticipatoryBudgetingActionForm.BUDGETING){
            poiInfo.get().setTipProiect("CC");
        }*/
        if (participatoryBudgetingActionForm == ParticipatoryBudgetingActionForm.ADMIN) {
            /*if (!getView().getState().isPresent()) {
                UI.getCurrent().getPage().executeJs("swalError($0);", "Nu ati selectat statusul!");
                return;
            }*/
            try{
                poiInfo.get().setDataStartVote(getView().getStartDate());

            }catch (Exception e){
                UI.getCurrent().getPage().executeJs("swalError($0);", "Nu ati completat data inceperii votului!");
                return;
            }
            try{
                poiInfo.get().setDataEndVote(getView().getDueDate());

            }catch (Exception e){
                UI.getCurrent().getPage().executeJs("swalError($0);", "Nu ati completat data incheierii votului!");
                return;
            }

            //StatusProiect statusProiect = getView().getState().get();
            //poiInfo.get().setIdStatusProiect(statusProiect.getId());
            //poiInfo.get().setStatusProiect(statusProiect.getDenumire());

            poiInfo.get().setPublicat(getView().getPublicat());
            if(getView().getPosition()!=null && !getView().getPosition().trim().isEmpty()) {
                try {
                    Double valuePosition = Double.valueOf(getView().getPosition());
                    poiInfo.get().setInfo1(valuePosition.toString());
                } catch (Exception e) {
                    UI.getCurrent().getPage().executeJs("swalError($0);", "Introduceti o valoare numerica pentru pozitia de afisare.");
                    return;
                }
            }
            if(getView().getValue()!=null && !getView().getValue().trim().isEmpty()){
                try {
                    Double valueValue= Double.valueOf(getView().getValue());
                    poiInfo.get().setValoare(valueValue);
                }catch (Exception e){
                    UI.getCurrent().getPage().executeJs("swalError($0);", "Introduceti o valoare numerica pentru valoarea proiectului.");
                    return;
                }
            }

        }

        try{
            poiInfo.get().setPropusDurataProiect(getView().getDurata());

        }catch (Exception e){

        }
        poiInfo.get().setIdJudet(121);
        poiInfo.get().setIdLocalitate(444);
        poiInfo.get().setDenumire(getView().getMessage());
        Integer fileId =null;
        //.substring(0, getView().getMessage().length() > 25 ? 25 : getView().getMessage().length()));
        if (poiId != null) { // update
            poiInfo.get().setId(poiId);
            fileId= poiInfo.get().getIdFisier();
            poiInfo = Optional.ofNullable(participatoryBudgetingService.updatePOI(SecurityUtils.getToken(), poiInfo.get(), poiId));
            if (!poiInfo.isPresent() || !poiInfo.get().getResult().equals("OK")) {
//            participatoryBudgetingService.
                UI.getCurrent().getPage().executeJs("swalError($0);", "Eroare salvare DMSWS!");
                return;
            } else {
                UI.getCurrent().getPage().executeJs("swalInfo($0);", "Propunerea a fost modificata cu succes!");
            }
        } else {
            FileData fileData = new FileData();
            fileData.setId_document(poiDocumentTypeId);
            fileId = ps4Service.createDummyFileIncepereSolicitare(fileData);
            poiInfo.get().setIdFisier(fileId);
            poiId = Optional.ofNullable(participatoryBudgetingService.addNewPOI(SecurityUtils.getToken(), poiInfo.get())).get().getId();
            if (!poiInfo.isPresent() || !poiInfo.get().getResult().equals("OK")) {
//            participatoryBudgetingService.
                UI.getCurrent().getPage().executeJs("swalError($0);", "Eroare salvare DMSWS!");
                return;
            } else {
                UI.getCurrent().getPage().executeJs("swalInfo($0);", "Propunerea a fost incarcata cu succes! Aceasta va fi publicata dupa aprobarea unui administrator!");
            }
        }

        Integer finalFileId = fileId;

        try {
            if (getView().getMultiFileMemoryBuffer().getFiles().size() > 0) {
                getLogger().info("consultation form has:\t" + getView().getMultiFileMemoryBuffer().getFiles().size() + " uploaded files");
                getView().getMultiFileMemoryBuffer().getFiles().forEach(fileName -> uploadAttachment(finalFileId, fileName, poiId));
            }
        } catch (Throwable t) {
            getLogger().error("Eroare la upload fisier!", t);
        }
        try {
            if (getView().getMultiFileMemoryBufferPhotos().getFiles().size() > 0) {
                getLogger().info("consultation form has:\t" + getView().getMultiFileMemoryBufferPhotos().getFiles().size() + " uploaded photos");
                getView().getMultiFileMemoryBufferPhotos().getFiles().forEach(fileName -> uploadAttachmentPhotos(finalFileId, fileName, poiId));
            }
        } catch (Throwable t) {
            getLogger().error("Eroare la upload poza!", t);
        }
        try {
            if (getView().getMultiFileMemoryBufferVideos().getFiles().size() > 0) {
                getLogger().info("consultation form has:\t" + getView().getMultiFileMemoryBufferVideos().getFiles().size() + " uploaded videos");
                getView().getMultiFileMemoryBufferVideos().getFiles().forEach(fileName -> uploadAttachmentVideos(finalFileId, fileName, poiId));
            }
        } catch (Throwable t) {
            getLogger().error("Eroare la upload video!", t);
        }
        markOnMap = false;
        UI.getCurrent().getPage().executeJs("clearMapMarker();");
        loadBusinessPois();
        getView().configNewMarkerPlacementConsultationForm();
        poiInfo = Optional.empty();
        poiId = null;
    }

    private void uploadAttachment(Integer parentFileId, String fileName, Integer poiId) {
        if(
                !fileName.contains(".jpg")&&
                        !fileName.contains(".jpeg")&&
                        !fileName.contains(".png")&&
                        !fileName.contains(".bmp")&&
                        !fileName.contains(".tiff")&&
                        !fileName.contains(".dng")&&
                        !fileName.contains(".psd")&&
                        !fileName.contains(".raw")&&
                        !fileName.contains(".gif") &&
                        !fileName.contains(".mp4")&&
                        !fileName.contains(".mov")&&
                        !fileName.contains(".avi")&&
                        !fileName.contains(".mkv")&&
                        !fileName.contains(".flv")&&
                        !fileName.contains(".wmw")&&
                        !fileName.contains(".mpeg")) {
            try {
                String token = SecurityUtils.getToken();
                String filenameWithoutExt= fileName.substring(0,fileName.lastIndexOf("."));
                CreateTipDocFileResponse response = fileService.uploadFisierTipDocCode(token, poiAttachmentTypeCode, SecurityUtils.getUserId(), fileName, fileName,
                        IOUtils.toByteArray(getView().getMultiFileMemoryBuffer().getInputStream(fileName)));
                fileService.attachFile(token, parentFileId, Integer.parseInt(response.getFileId()));
                ProjectFile poiFile = participatoryBudgetingService.addPOIFile(token, poiId, Integer.parseInt(response.getFileId()));
                if (poiFile.getResult().equals("OK")) {
                    getLogger().info("Fisierul cu id-ul:\t" + Integer.parseInt(response.getFileId()) + "\ta fost atribuit la poiId:\t" + poiId);
                } else {
                    getLogger().error("Fisierul cu id-ul:\t" + Integer.parseInt(response.getFileId()) + "\tnu a fost atribuit la poiId:\t" + poiId + "\t!!!");
                }
            } catch (Throwable e) {
                getLogger().error("Eroare la upload fisier!", e);
            }
        }
    }
    private void uploadAttachmentPhotos(Integer parentFileId, String fileName, Integer poiId) {
        if(
                fileName.contains(".jpg")||
                        fileName.contains(".jpeg")||
                        fileName.contains(".png")||
                        fileName.contains(".bmp")||
                        fileName.contains(".tiff")||
                        fileName.contains(".dng")||
                        fileName.contains(".psd")||
                        fileName.contains(".raw")||
                        fileName.contains(".gif")){
            try {
                String token = SecurityUtils.getToken();
                CreateTipDocFileResponse response = fileService.uploadFisierTipDocCode(token, poiAttachmentPhotosTypeCode, SecurityUtils.getUserId(), fileName, fileName,
                        IOUtils.toByteArray(getView().getMultiFileMemoryBufferPhotos().getInputStream(fileName)));
                fileService.attachFile(token, parentFileId, Integer.parseInt(response.getFileId()));
                ProjectFile poiFile = participatoryBudgetingService.addPOIFile(token, poiId, Integer.parseInt(response.getFileId()));
                if (poiFile.getResult().equals("OK")) {
                    getLogger().info("Poza cu id-ul:\t" + Integer.parseInt(response.getFileId()) + "\ta fost atribuita la poiId:\t" + poiId);
                } else {
                    getLogger().error("Poza cu id-ul:\t" + Integer.parseInt(response.getFileId()) + "\tnu a fost atribuita la poiId:\t" + poiId + "\t!!!");
                }
            } catch (Throwable e) {
                getLogger().error("Eroare la upload poza!", e);
            }
        }

    }
    private void uploadAttachmentVideos(Integer parentFileId, String fileName, Integer poiId) {
        if(
                fileName.contains(".mp4")||
                        fileName.contains(".mov")||
                        fileName.contains(".avi")||
                        fileName.contains(".mkv")||
                        fileName.contains(".flv")||
                        fileName.contains(".wmw")||
                        fileName.contains(".mpeg")) {
            try {
                String token = SecurityUtils.getToken();
                CreateTipDocFileResponse response = fileService.uploadFisierTipDocCode(token, poiAttachmentVideosTypeCode, SecurityUtils.getUserId(), fileName, fileName,
                        IOUtils.toByteArray(getView().getMultiFileMemoryBufferVideos().getInputStream(fileName)));
                fileService.attachFile(token, parentFileId, Integer.parseInt(response.getFileId()));
                ProjectFile poiFile = participatoryBudgetingService.addPOIFile(token, poiId, Integer.parseInt(response.getFileId()));
                if (poiFile.getResult().equals("OK")) {
                    getLogger().info("Videoul cu id-ul:\t" + Integer.parseInt(response.getFileId()) + "\ta fost atribuit la poiId:\t" + poiId);
                } else {
                    getLogger().error("Videoul cu id-ul:\t" + Integer.parseInt(response.getFileId()) + "\tnu a fost atribuit la poiId:\t" + poiId + "\t!!!");
                }
            } catch (Throwable e) {
                getLogger().error("Eroare la upload video!", e);
            }
        }
    }
    @ClickEventPresenterMethod(viewProperty = "consultationMarkerBtn")
    public void onConsultationFormMarked(ClickEvent<ClickNotifierAnchor> clickEvent) {
        getLogger().info("mark on consultation form");
        markOnMap = true;
        getView().configNewConsultationForm(Optional.ofNullable(dmswsUtilizatorService.getPersoanaFizicaJuridica(SecurityUtils.getToken(), SecurityUtils.getUserId().intValue())));
    }
    @ClickEventPresenterMethod(viewProperty = "buttonAddress")
    public void onButtonAddressClick(ClickEvent<ClickNotifierAnchor> clickEvent) {
        getLogger().info("mark on consultation form");
        markOnMap = true;
        UI.getCurrent().getPage().executeJs("swalInfo($0);", "Plasati un indicator pe harta!");

        //getView().configNewConsultationForm(Optional.ofNullable(dmswsUtilizatorService.getPersoanaFizicaJuridica(SecurityUtils.getToken(), SecurityUtils.getUserId().intValue())));
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
            UI.getCurrent().getPage().executeJs(" swalInfo($0,$1);", "Pentru a putea vota un proiect trebuie sa fii autentificat!",getView());

            return;
        }
        BaseModel checkForUserVote=participatoryBudgetingService.checkForUserVote(SecurityUtils.getToken(), poiId);
        if(checkForUserVote.getInfo().equals("true")){
            UI.getCurrent().getPage().executeJs(" swalInfo($0);", "Ati exprimat votul dumnavoastra pe acest proiect: "+checkForUserVote.getExtendedInfo()+" la data de "+checkForUserVote.getExtendedInfo2()+"!");
            return;
        }
        Boolean datesAreBetweenCurrent= getView().checkDatesBetweenCurrent(poiInfo.get().getDataStartVote(),poiInfo.get().getDataEndVote());
        SimpleDateFormat sdf= new SimpleDateFormat("dd-mm-yyyy");
        if (!datesAreBetweenCurrent) {
            if(getView().checkDateBefore(poiInfo.get().getDataEndVote(), sdf.format(new Date()))){
                UI.getCurrent().getPage().executeJs(" swalInfo($0);",
                        "Perioada de votare a acestui proiect inca nu a inceput! <p> ("
                                +poiInfo.get().getDataStartVote()+" -> "
                                +poiInfo.get().getDataEndVote()+")</p>");

            }
            else{
                UI.getCurrent().getPage().executeJs(" swalInfo($0);",
                        "Perioada de votare a acestui proiect s-a incheiat! <p> ("
                                +poiInfo.get().getDataStartVote()+" -> "
                                +poiInfo.get().getDataEndVote()+")</p>");

            }

            return;
        }
        participatoryBudgetingService.POIVote(SecurityUtils.getToken(), poiId, 1);
        getView().configNewBudgetaryTab();
        UI.getCurrent().getPage().executeJs("clearMapPopup(); swalInfo($1);",  "Votul a fost inregistrat!");
    }

    @ClickEventPresenterMethod(viewProperty = "downVoteProjectBtn")
    public void onDownVoteProject(ClickEvent<NativeButton> clickEvent) {
        getLogger().info("downvote project poiId:\t" + poiId);
        if (!hasAuthenticateUser()) {
            getLogger().error("Security alert! No logged user wants to vote");
            UI.getCurrent().getPage().executeJs(" swalInfo($0,$1);", "Pentru a putea vota un proiect trebuie sa fii autentificat!",getView());

            return;
        }
        BaseModel checkForUserVote=participatoryBudgetingService.checkForUserVote(SecurityUtils.getToken(), poiId);
        if(checkForUserVote.getInfo().equals("true")){
            UI.getCurrent().getPage().executeJs(" swalInfo($0);", "Ati exprimat votul dumnavoastra pe acest proiect: "+checkForUserVote.getExtendedInfo()+" la data de "+checkForUserVote.getExtendedInfo2()+"!");
            return;
        }
        Boolean datesAreBetweenCurrent= getView().checkDatesBetweenCurrent(poiInfo.get().getDataStartVote(),poiInfo.get().getDataEndVote());
        SimpleDateFormat sdf= new SimpleDateFormat("dd-mm-yyyy");
        if (!datesAreBetweenCurrent) {
            if(getView().checkDateBefore(poiInfo.get().getDataEndVote(), sdf.format(new Date()))){
                UI.getCurrent().getPage().executeJs(" swalInfo($0);",
                        "Perioada de votare a acestui proiect inca nu a inceput! <p> ("
                                +poiInfo.get().getDataStartVote()+" -> "
                                +poiInfo.get().getDataEndVote()+")</p>");

            }
            else{
                UI.getCurrent().getPage().executeJs(" swalInfo($0);",
                        "Perioada de votare a acestui proiect s-a incheiat! <p> ("
                                +poiInfo.get().getDataStartVote()+" -> "
                                +poiInfo.get().getDataEndVote()+")</p>");

            }

            return;
        }
        participatoryBudgetingService.POIVote(SecurityUtils.getToken(), poiId, 0);
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
            String codCategoriePoi="default";
            try{
                codCategoriePoi =  getView().getCategory().get().getCod();
            }catch ( Exception e){

            }
            getView().setAddress(address);
            getView().configNewConsultationForm(pfj);
            UI.getCurrent().getPage().executeJs("addMapMarker($0, $1,$2);", lat, lng,codCategoriePoi.toLowerCase()+"_yellow.png");
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
