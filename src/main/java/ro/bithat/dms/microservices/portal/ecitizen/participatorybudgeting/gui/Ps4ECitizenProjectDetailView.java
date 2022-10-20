package ro.bithat.dms.microservices.portal.ecitizen.participatorybudgeting.gui;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.server.StreamResource;
import ro.bithat.dms.microservices.dmsws.poi.ProjectFile;
import ro.bithat.dms.microservices.portal.ecitizen.gui.ContentContainerView;
import ro.bithat.dms.microservices.portal.ecitizen.gui.component.MobTableContainerDiv;
import ro.bithat.dms.microservices.portal.ecitizen.gui.component.TableContainerDiv;
import ro.bithat.dms.passiveview.StreamResourceUtil;
import ro.bithat.dms.passiveview.i18n.MobTableFormI18n;
import ro.bithat.dms.passiveview.i18n.TableFormI18n;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Ps4ECitizenProjectDetailView extends ContentContainerView<Ps4ECitizenProjectDetailPresenter> {

    private H2 headerGalerieFoto = new H2("document.type.project.view.photos.title.label");

    private Div galerieFotoContent = new Div();

    private Div divGalerieFoto = new Div(galerieFotoContent);

    private H2 headerGalerieVideo = new H2("document.type.project.view.videos.title.label");

    private Div galerieVideoContent = new Div();

    private Div divGalerieVideo = new Div(galerieVideoContent);

    private NativeButton downVoteProjectBtn = new NativeButton();

    private NativeButton upVoteProjectBtn = new NativeButton();

    private Div divVotes = new Div(downVoteProjectBtn, upVoteProjectBtn);

    private Div votesContainer = new Div(divVotes);

    private TableContainerDiv projectDocumentsTable =
            new TableContainerDiv("document.type.service.request.view.service.index.label",
                    "document.type.service.request.view.service.documentname.label",
                    "document.type.service.request.view.service.option.label");

    private MobTableContainerDiv mobProjecctDocumentsTable = new MobTableContainerDiv();


    private H2 projectDocumentsTitle = new H2("document.type.project.view.files.title.label");

    //Needed Document table

    private Label projectType = new Label();

    private Label projectName = new Label();

    private Label projectDescription = new Label();

    private Label projectCategory = new Label();

    private Label projectAddress = new Label();

    private Label projectBeneficii = new Label();

    private Label projectBeneficiari = new Label();

    private Label projectVoturiPro = new Label();

    private Label projectVoturiContra = new Label();

    private Label projectValue = new Label();

    private Label projectDurata = new Label();

    private Label projectDateStartVote = new Label();

    private Label projectDateEndVote = new Label();

    private Label votesContainerMessage = new Label();

    ////////////////////////////////////////////////////////////

    private TableFormI18n documentTypeServiceRequestForm = new TableFormI18n();

    private Div tableResponsive = new Div(documentTypeServiceRequestForm);

    private MobTableFormI18n mobDocumentTypeServiceRequestForm = new MobTableFormI18n();


    @Override
    public void beforeBinding() {
        addServiceListContent();
        addClassName("container");
        removeServiceListContent();
        getServiceListContainer().addClassNames("detaliu-serviciu", "solicitare-document");
        documentTypeServiceRequestForm.getElement().setAttribute("class", "table border-dark large-whitespace catalog_su");
        setContentPageTile("Detalii Proiect");
        galerieFotoContent.addClassNames("content-galerie-foto");
        galerieVideoContent.addClassNames("content-galerie-video");
        divGalerieFoto.addClassNames("main-galerie-foto");
        divGalerieVideo.addClassNames("main-galerie-video");
        UI.getCurrent().getPage().executeJs("initCarouselSliderFoto();");

        ///Btn footer container
/*
        btnFooterContainer.registerPresenterNextStepMethod("document.type.service.request.view.confirm.action.label", "","-");
*/
        ///Btn footer container
        tableResponsive.addClassName("table-responsive");
        mobDocumentTypeServiceRequestForm.addClassName("table_mob_1col");
        Div clearFix = new Div();
        clearFix.addClassNames("clearfix");
        Div clearFixFoto = new Div();
        clearFixFoto.addClassNames("clearfix");
       // styleVotesContnainer();


        getServiceListContainer().add(
                headerGalerieFoto,
                divGalerieFoto,
                clearFixFoto,
                headerGalerieVideo,
                divGalerieVideo,
                clearFix,
                tableResponsive, mobDocumentTypeServiceRequestForm ,
               // votesContainer,
                projectDocumentsTitle, projectDocumentsTable, mobProjecctDocumentsTable
               );

        buildDocumentTypeServiceRequestForm();
        styleTables();
    }




    private void buildDocumentTypeServiceRequestForm() {


       // buildDocumentTypeServiceRequestForm(projectType, "document.type.service.project.view.form.projecttype.label");
        buildDocumentTypeServiceRequestForm(projectCategory, "document.type.service.project.view.form.service.category.label");
        buildDocumentTypeServiceRequestForm(projectName, "document.type.service.project.view.form.service.name.label");
        buildDocumentTypeServiceRequestForm(projectDescription, "document.type.service.project.view.form.service.description.label");
        buildDocumentTypeServiceRequestForm(projectAddress, "document.type.service.request.view.form.service.address.label");
        buildDocumentTypeServiceRequestForm(projectValue, "document.type.service.request.view.form.service.value.label");
        buildDocumentTypeServiceRequestForm(projectDurata, "document.type.service.request.view.form.service.durata.label");
        buildDocumentTypeServiceRequestForm(projectBeneficii, "document.type.service.request.view.form.service.beneficii.label");
        buildDocumentTypeServiceRequestForm(projectBeneficiari, "document.type.service.request.view.form.service.beneficiari.label");
        //buildDocumentTypeServiceRequestForm(projectDateStartVote, "document.type.service.request.view.form.service.start.vote.label");
        //buildDocumentTypeServiceRequestForm(projectDateEndVote, "document.type.service.request.view.form.service.end.vote.label");
        buildDocumentTypeServiceRequestForm(projectVoturiPro, "document.type.service.request.view.form.service.votes.pro.label");
        buildDocumentTypeServiceRequestForm(projectVoturiContra, "document.type.service.request.view.form.service.votes.contra.label");


    }

    private void buildDocumentTypeServiceRequestForm(Component component, String label) {
        documentTypeServiceRequestForm.addFormRow(component, label);
        mobDocumentTypeServiceRequestForm.addFormRow(component, label);
    }

    private void styleTables() {
        mobProjecctDocumentsTable.addClassName("table_mob_2col");
        projectDocumentsTable.addClassName("table-responsive");
        projectDocumentsTable.setTableClassNames("table dark-head has-buttons");
        projectDocumentsTable.setTableHeaderClassNames("thead-dark");

    }


    public void setPersonTypeValue(String personTypeValue) {
        projectType.setText(personTypeValue);
    }

    public void setProjectNameValue(String projectNameValue) {
        projectName.setText(projectNameValue);
    }

    public void setProjectDescriptionValue(String projectDescriptionValue) {
        projectDescription.setText(projectDescriptionValue);
    }

    public void setServiceProjectTypeValue(String projectTypeValue) {
        if(projectTypeValue!=null){
            projectType.setText(projectTypeValue);
        }
    }

    public void setServiceProjectCategoryValue(String projectCategoryValue) {
        projectCategory.setText(projectCategoryValue);
    }

    public void setServiceProjectDurataValue(String projectDurataValue) {
        if(projectDurataValue!=null){
            projectDurata.setText(projectDurataValue);
        }
    }

    public void setServiceProjectAddressValue(String projectAddressValue) {
        projectAddress.setText(projectAddressValue);
    }

    public void setServiceProjectValueValue(String projectValueValue) {
        if(projectValueValue!=null){
            projectValue.setText(projectValueValue);

        }
    }

    public void setServiceProjectBeneficiiValue(String projectBeneficiiValue) {
        if(projectBeneficiiValue!=null){
            projectBeneficii.setText(projectBeneficiiValue);

        }
    }

    public void setServiceProjectBeneficiariValue(String projectBeneficiariValue) {
        if(projectBeneficiariValue!=null){
            projectBeneficiari.setText(projectBeneficiariValue);

        }
    }
    public void setServiceProjectStartVoteValue(String projectStartVoteValue) {
        projectDateStartVote.setText(projectStartVoteValue);
    }
    public void setServiceProjectEndVoteValue(String projectEndVoteValue) {
        projectDateEndVote.setText(projectEndVoteValue);
    }
    public void setServiceProjectVoturiProValue(String voturiProValue) {
        projectVoturiPro.setText(voturiProValue);
    }
    public void setServiceProjectVoturiContraValue(String voturiContraValue) {
        projectVoturiContra.setText(voturiContraValue);
    }
    public void setProjectDocumentsTable(List<ProjectFile> projectsFileList) {
        AtomicInteger index = new AtomicInteger(1);
        projectsFileList.stream().forEach(projectFile -> setProjectDocumentTableRow(projectFile, index));
    }


    public void setProjectPhotosTable(List<ProjectFile> projectsFileList) {
        if(projectsFileList!=null && projectsFileList.size()!=0){
            constructImagesSlider(projectsFileList
                    .stream().collect(Collectors.toList()));
        }
        else{
            headerGalerieFoto.getStyle().set("display","none");
        }

    }
    public void setProjectVideosTable(List<ProjectFile> projectsFileList) {
        if(projectsFileList!=null && projectsFileList.size()!=0){
            constructVideoSlider(projectsFileList
                    .stream().collect(Collectors.toList()));
        }
        else{
            headerGalerieVideo.getStyle().set("display","none");
        }
    }


    private void constructImagesSlider(List<ProjectFile> poiFiles) {
        galerieFotoContent.removeAll();
        Div sliderMap = new Div();
        sliderMap.addClassName("foto-items");
        galerieFotoContent.add(sliderMap);
        poiFiles.stream().forEach(poiFile -> constructImageSlider(sliderMap, poiFile));
    }

    private void constructImageSlider(Div sliderMap, ProjectFile poiFile) {
        StreamResource poiFileStreamResource = StreamResourceUtil.getStreamResource(poiFile.getNumeFisier(), poiFile.getDownloadLink());
        Image poiFileImage = new Image(poiFileStreamResource, "alt");

        Anchor poiFileLink = new Anchor();

        poiFileLink.add(poiFileImage);
        poiFileLink.setHref(poiFileStreamResource);
        poiFileLink.getElement().setAttribute("data-fancybox", "gallery");
        //poiFileLink.getElement().setAttribute("rel", "ligthbox");
        Div poiFileLayout = new Div(poiFileLink);
        poiFileLayout.addClassName("foto-item");

        sliderMap.add(poiFileLayout);
    }
    private void constructVideoSlider(List<ProjectFile> poiFiles) {
        galerieVideoContent.removeAll();
        Div sliderMap = new Div();
        sliderMap.addClassName("video-items");
        galerieVideoContent.add(sliderMap);
        poiFiles.stream().forEach(poiFile -> constructVideoSlider(sliderMap, poiFile));
    }

    private void constructVideoSlider(Div sliderMap, ProjectFile poiFile) {
        StreamResource poiFileStreamResource = StreamResourceUtil.getStreamResource(poiFile.getNumeFisier(), poiFile.getDownloadLink());
        HtmlContainer htmlVideo = new HtmlContainer("video");
        //Image poiFileImage = new Image(poiFileStreamResource, "alt");
        //htmlVideo.getStyle().set("max-height", "250px");
        //htmlVideo.getStyle().set("max-width", "300px");
       /* poiFileImage.setMaxHeight("100px");
        poiFileImage.setMaxWidth("100px");*/
        Anchor poiFileLink = new Anchor();
        poiFileLink.add(htmlVideo);
        poiFileLink.setHref(poiFileStreamResource);
        poiFileLink.getElement().setAttribute("data-fancybox", "gallery");

        htmlVideo.getElement().setAttribute("href", poiFileLink.getHref());
        htmlVideo.getElement().setProperty("controls","#");
        poiFileLink.getElement().setAttribute("data-fancybox", "-");
        poiFileLink.getElement().setAttribute("data-height", "360");
        poiFileLink.getElement().setAttribute("data-width", "640");
        //poiFileLink.getElement().setAttribute("rel", "ligthbox");
        Div poiFileLayout = new Div(poiFileLink);
        poiFileLayout.addClassName("video-item");
        sliderMap.add(poiFileLayout);
    }
    private void setProjectDocumentTableRow(ProjectFile projectFile, AtomicInteger index) {
        Anchor visualisation = new Anchor();
        visualisation.addClassNames("btn", "btn-primary", "btn-secondary-hover", "btn-sm");
        HtmlContainer visualisationIcon = new HtmlContainer("i");
        visualisationIcon.addClassNames("fas", "fa-eye");
        visualisation.add(visualisationIcon, new Text("document.type.service.request.view.legislationfiles.service.option.label"));
        if(Optional.ofNullable(projectFile.getDownloadLink()).isPresent() &&
                !projectFile.getDownloadLink().isEmpty()) {
            visualisation.setHref(projectFile.getDownloadLink());
            visualisation.setTarget("_blank");
        }
        Div  optionsLayout = new Div(visualisation);
        projectDocumentsTable.addRow(new Label(index.getAndIncrement() + ""),
                new Label(projectFile.getNumeFisier()),
                optionsLayout);
        Map<String, Component> mobileRowMap = new LinkedHashMap<>();
        mobileRowMap.put("document.type.service.request.view.service.index.label", new Label(index.getAndIncrement() + ""));
        mobileRowMap.put("document.type.service.request.view.service.documentname.label", new Label(projectFile.getNumeFisier()));
        Anchor mobVisualisation = new Anchor();
        mobVisualisation.addClassNames("btn", "btn-primary", "btn-secondary-hover", "btn-sm");
        HtmlContainer mobVisualisationIcon = new HtmlContainer("i");
        mobVisualisationIcon.addClassNames("fas", "fa-eye");
        mobVisualisation.add(mobVisualisationIcon, new Text("document.type.service.request.view.legislationfiles.service.option.label"));
        if(Optional.ofNullable(projectFile.getDownloadLink()).isPresent() &&
                !projectFile.getDownloadLink().isEmpty()) {
            mobVisualisation.setHref(projectFile.getDownloadLink());
            mobVisualisation.setTarget("_blank");
        }
        Div  mobOptionsLayout = new Div(mobVisualisation);

        mobileRowMap.put("document.type.service.request.view.service.option.label", mobOptionsLayout);
        mobProjecctDocumentsTable.addRow(mobileRowMap);

    }

    protected void displayForWidth(int width) {
        if(width <= 700) {
            mobDocumentTypeServiceRequestForm.getStyle().remove("display");
            tableResponsive.getStyle().set("display", "none");
            mobDocumentTypeServiceRequestForm.buildTableBody();
            mobProjecctDocumentsTable.getStyle().remove("display");
            projectDocumentsTable.getStyle().set("display", "none");
        } else {
            if(tableResponsive.getStyle().has("display")) {
                tableResponsive.getStyle().remove("display");
            }
            mobDocumentTypeServiceRequestForm.getStyle().set("display", "none");
            documentTypeServiceRequestForm.buildTableBody();
            if(projectDocumentsTable.getStyle().has("display")) {
                projectDocumentsTable.getStyle().remove("display");
            }
            mobProjecctDocumentsTable.getStyle().set("display", "none");
        }
    }

    public void hideVotesContainer(){
       votesContainer.setVisible(false);
       votesContainer.getStyle().set("display","none");
    }
    public void styleVotesContainerToVote() {

        votesContainer.addClassNames("votes_container");
        divVotes.addClassNames("actions", "clearfix");
        downVoteProjectBtn.addClassNames("thumb_action", "downvote");
        HtmlContainer downVoteIcon = new HtmlContainer("i");
        downVoteIcon.addClassNames("fas", "fa-thumbs-down");
        downVoteProjectBtn.add(new Text("Dezacord "), downVoteIcon);

        upVoteProjectBtn.addClassNames("thumb_action", "upvote");
        HtmlContainer upVoteIcon = new HtmlContainer("i");
        upVoteIcon.addClassNames("fas", "fa-thumbs-up");
        upVoteProjectBtn.add(new Text("Aprob "), upVoteIcon);

        buildDocumentTypeServiceRequestForm(votesContainer, "document.type.service.request.view.form.service.vote.label");

    }
    public void styleVotesContainerMessage(String message) {

        buildDocumentTypeServiceRequestForm(votesContainerMessage, "document.type.service.request.view.form.service.vote.label");
        votesContainerMessage.setText(message);

    }
    @ClientCallable
    public void swalInfoAck() {
        UI.getCurrent().getPage().reload();
        //VaadinClientUrlUtil.setLocation(RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenMyAccountRoute.class));
    }
}


