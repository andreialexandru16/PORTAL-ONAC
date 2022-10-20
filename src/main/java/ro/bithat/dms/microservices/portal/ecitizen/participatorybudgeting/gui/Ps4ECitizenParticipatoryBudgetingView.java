package ro.bithat.dms.microservices.portal.ecitizen.participatorybudgeting.gui;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.UploadI18N;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.RouteConfiguration;
import elemental.json.Json;
import elemental.json.JsonObject;
import elemental.json.impl.JreJsonNull;
import ro.bithat.dms.microservices.dmsws.poi.CategoriePOI;
import ro.bithat.dms.microservices.dmsws.poi.ProjectInfo;
import ro.bithat.dms.microservices.dmsws.poi.StatusProiect;
import ro.bithat.dms.microservices.portal.ecitizen.gui.Ps4ECitizenTermsAndConditionsRoute;
import ro.bithat.dms.microservices.portal.ecitizen.participatorybudgeting.gui.component.PoiPopup;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat.PersoanaFizicaJuridica;
import ro.bithat.dms.passiveview.VaadinClientUrlUtil;
import ro.bithat.dms.passiveview.component.html.ClickNotifierAnchor;
import ro.bithat.dms.passiveview.component.html.Strong;
import ro.bithat.dms.passiveview.component.view.DivFlowViewBuilder;
import ro.bithat.dms.passiveview.i18n.DatePickerI18N;
import ro.bithat.dms.service.StreamToStringUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class Ps4ECitizenParticipatoryBudgetingView extends DivFlowViewBuilder<Ps4ECitizenParticipatoryBudgetingPresenter> {


    private ComboBox<ProjectInfo> budgetaryFormProjects = new ComboBox<>();

    private Div budgetaryFormRowAdminProjects = new Div(budgetaryFormProjects);

    private ComboBox<ProjectInfo> consultationFormProjects = new ComboBox<>();

    private Div consultationFormRowAdminProjects = new Div(consultationFormProjects);

    private Label publishProjectLabel = new Label("Publica proiect");

    private Checkbox publishProject = new Checkbox();

    private Div publishProjectLayout= new Div(publishProjectLabel,publishProject);

    private Label consultationFormApprovedLabel = new Label();

    private Checkbox consultationFormApproved = new Checkbox();

    private Label consultationFormDraftLabel = new Label();

    private Checkbox consultationFormDraft = new Checkbox();

    private Div consultationFormCustomControlFiltersPublished = new Div(consultationFormApprovedLabel, consultationFormApproved);
    private Div consultationFormCustomControlFiltersUnpublished = new Div(consultationFormDraftLabel,consultationFormDraft);

    private NativeButton downVoteProjectBtn = new NativeButton();

    private NativeButton upVoteProjectBtn = new NativeButton();

    private Div budgetaryActions = new Div(downVoteProjectBtn, upVoteProjectBtn);

    private Text budgetaryProjectAddress = new Text("");

    private Div budgetaryProjectAddressLayout = new Div(new Strong("Adresa:"), budgetaryProjectAddress);

    private Text budgetaryProjectValue = new Text("");

    private Div budgetaryProjectValueLayout = new Div(new Strong("Valoare proiect:"), budgetaryProjectValue);

    private Paragraph budgetaryProjectDescription = new Paragraph();

    private Div budgetaryProjectDescriptionLayout = new Div(new Strong("Descriere proiect:"), budgetaryProjectDescription);

    private Paragraph budgetaryProjectDetails = new Paragraph();

    private Div budgetaryProjectDetailsLayout = new Div(new Strong("Denumire proiect:"), budgetaryProjectDetails);

    private Paragraph budgetaryProjectBeneficiari = new Paragraph();

    private Div budgetaryProjectBeneficiariLayout = new Div(new Strong("Beneficiari:"), budgetaryProjectBeneficiari);

    private Paragraph budgetaryProjectBeneficii = new Paragraph();

    private Div budgetaryProjectBeneficiiLayout = new Div(new Strong("Beneficii:"), budgetaryProjectBeneficii);

    private Paragraph budgetaryProjectDurata = new Paragraph();

    private Div budgetaryProjectDurataLayout = new Div(new Strong("Durata proiect:"), budgetaryProjectDurata);

    private Text budgetaryVoteDueDate = new Text("");

    private Div budgetaryVoteDueDateLayout = new Div(new Strong("Data finalizare vot:"), budgetaryVoteDueDate);

    private Text budgetaryStartVoteDate = new Text("");

    private Div budgetaryStartVoteDateLayout = new Div(new Strong("Data incepere vot:"), budgetaryStartVoteDate);

    private Text budgetaryState = new Text("");

    private Div budgetaryStateLayout = new Div(new Strong("Status:"), budgetaryState);

    private Text budgetaryCategory = new Text("");

    private Div budgetaryCategoryLayout = new Div(new Strong("Categorie:"), budgetaryCategory);

    private Div budgetaryDetails = new Div( budgetaryCategoryLayout, budgetaryStateLayout, budgetaryStartVoteDateLayout,
            budgetaryVoteDueDateLayout, budgetaryProjectDetailsLayout,budgetaryProjectDescriptionLayout,
            budgetaryProjectDurataLayout,budgetaryProjectBeneficiiLayout,
            budgetaryProjectBeneficiariLayout, budgetaryProjectValueLayout, budgetaryProjectAddressLayout
    );

    private H3 budgetaryTabHeader = new H3("Propunere");

    private Text budgetaryInfoPhone2No = new Text(" - 021 123 1234");

    private Strong budgetaryInfoPhone2Title = new Strong("Telefonul primarului");

    private Span budgetaryInfoPhone2 = new Span(budgetaryInfoPhone2Title, budgetaryInfoPhone2No);

    private Text budgetaryInfoPhone1No = new Text(" -  021 123 1234");

    private Strong budgetaryInfoPhone1Title = new Strong("Dispeceratul poliției locale");

    private Span budgetaryInfoPhone1 = new Span(budgetaryInfoPhone1Title, budgetaryInfoPhone1No);

    private HtmlContainer budgetaryInfoPhoneIcon = new HtmlContainer("i");

    private Div budgetaryInfoPhoneLayout = new Div(budgetaryInfoPhoneIcon, budgetaryInfoPhone1, budgetaryInfoPhone2);

    private Paragraph budgetaryTabInfoParagraph = new Paragraph("Pentru intervenția operativă a Poliției Locale vă rugăm să apelați numerele de telefon:");

    private Div budgetaryTab = new Div(budgetaryTabInfoParagraph, budgetaryInfoPhoneLayout, budgetaryFormRowAdminProjects,budgetaryTabHeader, budgetaryDetails, budgetaryActions);

    private ClickNotifierAnchor consultationSendBtn = new ClickNotifierAnchor();

    private ClickNotifierAnchor consultationMarkerBtn = new ClickNotifierAnchor();

    private ClickNotifierAnchor consultationCancelBtn = new ClickNotifierAnchor();

    private Div consultationFormButtonsLayout = new Div(consultationCancelBtn, consultationMarkerBtn, consultationSendBtn);

    private Div consultationFormButtons = new Div(consultationFormButtonsLayout);

    private Label consultationFormLikeVotesLabel = new Label();

    private Label consultationFormDislikeVotesLabel = new Label();

    private Div consultationFormVotesContainer = new Div(consultationFormLikeVotesLabel, consultationFormDislikeVotesLabel);

    private Label consultationFormTermsAgreementLabel = new Label();

    private Checkbox consultationFormTermsAgreement = new Checkbox();

    private Div consultationFormCustomControl = new Div(consultationFormTermsAgreement, consultationFormTermsAgreementLabel);

    private MultiFileMemoryBuffer multiFileMemoryBufferPhotos = new MultiFileMemoryBuffer();

    private Upload chunkUploadPhotos = new Upload(multiFileMemoryBufferPhotos);

    private Div consultationFormRowPhotos = new Div(chunkUploadPhotos);

    private MultiFileMemoryBuffer multiFileMemoryBufferVideos = new MultiFileMemoryBuffer();

    private Upload chunkUploadVideos = new Upload(multiFileMemoryBufferVideos);

    private Div consultationFormRowVideos = new Div(chunkUploadVideos);

    private MultiFileMemoryBuffer multiFileMemoryBuffer = new MultiFileMemoryBuffer();

    private Upload chunkUpload = new Upload(multiFileMemoryBuffer);

    private Div consultationFormRow6 = new Div(chunkUpload);

    private TextField consultationFormValue = new TextField();

    private Label consultationFormValueLabel = new Label("Valoarea");

    private Div consultationFormValueLayout = new Div(consultationFormValueLabel, consultationFormValue);

    private Div consultationFormRowAdmin4 = new Div(consultationFormValueLayout);

    private TextField consultationFormDurataValue = new TextField();

    private Label consultationFormDurataLabel = new Label("Durata proiect");

    private Div consultationFormRowDurataLayout = new Div(consultationFormDurataLabel,consultationFormDurataValue);

    private Div consultationFormRowDurata= new Div(consultationFormRowDurataLayout);

    private TextArea consultationFormBeneficii = new TextArea();

    private Label consultationFormBeneficiiLabel = new Label("Beneficii");

    private Div consultationFormBeneficiiLayout = new Div(consultationFormBeneficiiLabel, consultationFormBeneficii);

    private Div consultationFormRowBeneficii= new Div(consultationFormBeneficiiLayout);

    private TextArea consultationFormBeneficiari = new TextArea();

    private Label consultationFormBeneficiariLabel = new Label("Beneficiari");

    private Div consultationFormBeneficiariLayout = new Div(consultationFormBeneficiariLabel, consultationFormBeneficiari);

    private Div consultationFormRowBeneficiari= new Div(consultationFormBeneficiariLayout);

    private DatePickerI18N consultationFormDueDate = new DatePickerI18N();

    private Label consultationFormDueDateLabel = new Label("Data finalizare vot");

    private Div consultationFormDueDateLayout = new Div(consultationFormDueDateLabel, consultationFormDueDate);

    private Div consultationFormRowAdmin3 = new Div(consultationFormDueDateLayout);

    private TextField consultationFormPosition = new TextField();

    private Label consultationFormPositionLabel = new Label("Pozitie afisare");

    private Div consultationFormPositionLayout = new Div(consultationFormPositionLabel, consultationFormPosition);

    private Div consultationFormRowPosition = new Div(consultationFormPositionLayout);

    private DatePickerI18N consultationFormStartDate = new DatePickerI18N();

    private Label consultationFormStartDateLabel = new Label("Data incepere vot");

    private Div consultationFormStartDateLayout = new Div(consultationFormStartDateLabel, consultationFormStartDate);

    private Div consultationFormRowAdmin2 = new Div(consultationFormStartDateLayout);



    private ComboBox<StatusProiect> consultationFormState = new ComboBox<>();

    private Div consultationFormRowAdmin1 = new Div(consultationFormState);

    private TextField consultationFormMessage = new TextField();

    private Label consultationFormMessageLabel = new Label("Denumire proiect*");

    private Div consultationFormMessageLayout = new Div(consultationFormMessageLabel, consultationFormMessage);

    private TextArea consultationFormDescription = new TextArea();

    private Label consultationFormDescriptionLabel = new Label("Descriere proiect*");

    private Div consultationFormDescriptionLayout = new Div(consultationFormDescriptionLabel, consultationFormDescription);

    private Div consultationFormRowDescription = new Div(consultationFormDescriptionLayout);

    private Div consultationFormRow5 = new Div(consultationFormMessageLayout);

    private TextField consultationFormAddress = new TextField();

    private Label consultationFormAddressLabel = new Label("Adresa");

    HtmlContainer iconMap = new HtmlContainer("i");

    private Button buttonAddress= new Button(iconMap);

    HorizontalLayout horizontalLayoutAddress= new HorizontalLayout(consultationFormAddressLabel,consultationFormAddress,buttonAddress);

    private Div consultationFormAddressLayout = new Div(horizontalLayoutAddress);

    private Div consultationFormRow4 = new Div(consultationFormAddressLayout);

    private ComboBox<CategoriePOI> consultationFormCategory = new ComboBox<>();

    private Div consultationFormRow3 = new Div(consultationFormCategory);

    private TextField consultationFormPhone = new TextField();

    private Label consultationFormPhoneLabel = new Label("Telefon*");

    private Div consultationFormPhoneLayout = new Div(consultationFormPhoneLabel, consultationFormPhone);

    private Div consultationFormRow2Col2 = new Div(consultationFormPhoneLayout);

    private TextField consultationFormEmail = new TextField();

    private Label consultationFormEmailLabel = new Label("Email*");

    private Div consultationFormEmailLayout = new Div(consultationFormEmailLabel, consultationFormEmail);

    private Div consultationFormRow2Col1 = new Div(consultationFormEmailLayout);

    private Div consultationFormRow2 = new Div(consultationFormRow2Col1, consultationFormRow2Col2);

    private TextField consultationFormName = new TextField();

    private Label consultationFormNameLabel = new Label("Prenume");

    private Div consultationFormNameLayout = new Div(consultationFormNameLabel, consultationFormName);

    private Div consultationFormRow1Col2 = new Div(consultationFormNameLayout);

    private TextField consultationFormSurname = new TextField();

    private Label consultationFormSurnameLabel = new Label("Nume");

    private Div consultationFormSurnameLayout = new Div(consultationFormSurnameLabel, consultationFormSurname);

    private Div consultationFormRow1Col1 = new Div(consultationFormSurnameLayout);

    private Div consultationFormRow1 = new Div(consultationFormRow1Col1, consultationFormRow1Col2);

    private Div consultationForm = new Div(consultationFormRowAdminProjects,consultationFormRow1, consultationFormRow2, consultationFormRow3,
            consultationFormRow4, consultationFormRow5, consultationFormRowDescription,
            consultationFormRowAdmin2,
            consultationFormRowAdmin3, consultationFormRowAdmin4,
            consultationFormRowDurata, consultationFormRowBeneficii, consultationFormRowBeneficiari,
            consultationFormRowPosition,
            consultationFormRow6,consultationFormRowPhotos,consultationFormRowVideos,
            publishProjectLayout, consultationFormCustomControl, consultationFormVotesContainer, consultationFormButtons);

    private Div consultationInfoGap20 = new Div();

    private Div consultationInfo2Gap20 = new Div();

    private Text consultationInfoPhone2No = new Text(" - 021 123 1234");

    private Strong consultationInfoPhone2Title = new Strong("Asistenta Cetateni");

    private Span consultationInfoPhone2 = new Span(consultationInfoPhone2Title, consultationInfoPhone2No);

    private Text consultationInfoPhone1No = new Text(" -  021 123 1234");

    private Strong consultationInfoPhone1Title = new Strong("Telefonul Primarului");

    private Span consultationInfoPhone1 = new Span(consultationInfoPhone1Title, consultationInfoPhone1No);

    private HtmlContainer consultationInfoPhoneIcon = new HtmlContainer("i");

    private Div consultationInfoPhoneLayout = new Div(consultationInfoPhoneIcon, consultationInfoPhone1, consultationInfoPhone2);

    private Paragraph consultationTabInfoParagraph = new Paragraph("Apreciem propunerile dvs - pentru noi propuneri de proiect. Completati mai jos:");

    private H3 consultationTabHeader = new H3("Adaugă o propunere nouă");

    private Div consultationTab = new Div(consultationTabHeader, consultationTabInfoParagraph, consultationInfoPhoneLayout, consultationInfo2Gap20, consultationFormCustomControlFiltersPublished,consultationFormCustomControlFiltersUnpublished, consultationInfoGap20, consultationForm);

    private Div panelContainer = new Div(consultationTab, budgetaryTab);

    private NativeButton budgetaryTabBtn = new NativeButton();

    private NativeButton consultationTabBtn = new NativeButton();

    private Div panelHeader = new Div(budgetaryTabBtn, consultationTabBtn);

    private Div toggleSideBar = new Div();

    private Div leftPanel = new Div(toggleSideBar, panelHeader, panelContainer);

    private Div gap30 = new Div();

    private Div budgetMap = new Div();

    private Div mapContainer = new Div(leftPanel, gap30, budgetMap);

    private PoiPopup poiPopup = new PoiPopup(this);

    public void setSurname(String surname) {
        consultationFormSurname.setValue(surname);
        consultationFormSurnameLayout.addClassName("show_label");
    }

    public String getSurname() {
        return consultationFormSurname.getValue();
    }

    public void setName(String name) {
        consultationFormName.setValue(name);
        consultationFormNameLayout.addClassName("show_label");
    }

    public String getName() {
        return consultationFormName.getValue();
    }

    public void setEmail(String email) {
        consultationFormEmail.setValue(email);
        consultationFormEmailLayout.addClassName("show_label");
    }

    public String getEmail() {
        return consultationFormEmail.getValue();
    }

    public void setPhone(String phone) {
        consultationFormPhone.setValue(phone);
        consultationFormPhoneLayout.addClassName("show_label");
    }

    public String getPhone() {
        return consultationFormPhone.getValue();
    }

    public void setCategories(List<CategoriePOI> categories) {
        consultationFormCategory.setItems(categories);
    }

    public Optional<CategoriePOI> getCategory() {
        return Optional.ofNullable(consultationFormCategory.getValue());
    }

    public void setStates(List<StatusProiect> states) {
        consultationFormState.setItems(states);
    }
    public void setProjectsConsultation(List<ProjectInfo> projects) {
        try{
            consultationFormProjects.setItems(projects);
        }catch (Exception e){

        }
    }

    public Optional<ProjectInfo> getProjectConsultation() {
        return Optional.ofNullable(consultationFormProjects.getValue());
    }

    public void setProjectsBudgetary(List<ProjectInfo> projects) {
        try{
            budgetaryFormProjects.setItems(projects);
        }catch (Exception e){

        }
    }

    public Optional<ProjectInfo> getProjectBudgetary() {
        return Optional.ofNullable(budgetaryFormProjects.getValue());
    }

    public Optional<StatusProiect> getState() {
        return Optional.ofNullable(consultationFormState.getValue());
    }

    public void setAddress(String address) {
        consultationFormAddress.setValue(address);
    }

    public String getAddress() {
        return consultationFormAddress.getValue();
    }

    public String getMessage() {
        return consultationFormMessage.getValue();
    }
    public String getDescription() {
        return consultationFormDescription.getValue();
    }

    public MultiFileMemoryBuffer getMultiFileMemoryBuffer() {
        return multiFileMemoryBuffer;
    }
    public MultiFileMemoryBuffer getMultiFileMemoryBufferPhotos() {
        return multiFileMemoryBufferPhotos;
    }
    public MultiFileMemoryBuffer getMultiFileMemoryBufferVideos() {
        return multiFileMemoryBufferVideos;
    }

    public Boolean hasTermsAgreement() {
        return consultationFormTermsAgreement.getValue();
    }

    public String getStartDate() {
        return consultationFormStartDate.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }
    public String getPosition() {
        return consultationFormPosition.getValue();
    }
    public String getDueDate() {
        return consultationFormDueDate.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }
    public Integer getPublicat() {
        return publishProject.getValue()?1:0;
    }

    public Boolean getConsultationFormApprovedValue() {
        Boolean value = false;
        try {
            value = consultationFormApproved.getOptionalValue().get();
        } catch (Exception e) {

        }
        return value;
    }

    public Boolean getConsultationFormDraftValue() {
        Boolean value = false;
        try {
            value = consultationFormDraft.getOptionalValue().get();
        } catch (Exception e) {

        }
        return value;
    }

    public String getValue() {
        return consultationFormValue.getValue();
    }
    public String getDurata() {
        return consultationFormDurataValue.getValue();
    }

    public String getBeneficii() {
        return consultationFormBeneficii.getValue();
    }


    public String getBeneficiari() {
        return consultationFormBeneficiari.getValue();
    }


    @Override
    protected void buildView() {
        gap30.addClassName("gap-30");
        configLeftPanel();
        mapContainer.addClassName("map_container");
        add(mapContainer);
        budgetMap.setId("map");
//        budgetMap.getStyle().set("min-height", "600px");
        budgetMap.getElement().setProperty("innerHTML",
                StreamToStringUtil.fileToString("static/PORTAL/google_map.html"));
        UI.getCurrent().getPage().addJavaScript("frontend/js/gmap.js");
        UI.getCurrent().getPage().addJavaScript("PORTAL/assets/js/participatory-budgeting.js");
        UI.getCurrent().getPage().executeJs("loadAfterMapElement($0)", this.getElement());
        poiPopup.hidePopup();
        poiPopup.hideStateProject();
        configBudgetaryFormRowAdminProjects();
        add(poiPopup);
    }

    @ClientCallable
    public void loadGmapApi() {
        UI.getCurrent().getPage().addJavaScript("https://maps.googleapis.com/maps/api/js?libraries=geometry&key=" + getPresenter().getGoogleMapsApiKey() + "&language=ro&region=RO&callback=initMap");
        getPresenter().afterLoadingGmaps();
    }

    @ClientCallable
    public void clickMarker(JsonObject markerInfo) {
        getLogger().info("marker Info:\t" + markerInfo.toJson());
        if (getPresenter().getParticipatoryBudgetingActionForm() == Ps4ECitizenParticipatoryBudgetingPresenter.ParticipatoryBudgetingActionForm.BUDGETING) {
            configBudgetaryTabInfo(markerInfo);
        }

        int poiId = (int) markerInfo.getNumber("id");
        poiPopup.showPopUp(poiId, markerInfo.getString("categoriePoi"),
                JreJsonNull.class.isAssignableFrom(markerInfo.get("statusProiect").getClass()) ? "" : markerInfo.getString("statusProiect"),
                markerInfo.getString("creatLa"), markerInfo.getString("descriere"));
        UI.getCurrent().getPage().executeJs("showPopupOnMap($0, $1);", markerInfo, poiPopup.getElement());

        upVoteProjectBtn.setVisible(true);
        downVoteProjectBtn.setVisible(true);
        getPresenter().setPoiId(poiId);
        if (getPresenter().getParticipatoryBudgetingActionForm() == Ps4ECitizenParticipatoryBudgetingPresenter.ParticipatoryBudgetingActionForm.ADMIN) {
            getPresenter().serviceAdminForm();
        }

    }

    public void hideVote() {
        upVoteProjectBtn.setVisible(false);
        downVoteProjectBtn.setVisible(false);
    }


    @ClientCallable
    public void clickMap(JsonObject mapInfo) {
        getLogger().info("mapInfo:\t" + mapInfo.toJson());
        configNewBudgetaryTab();
        getPresenter().onMapLocation(mapInfo.getString("address"), mapInfo.getNumber("lat"), mapInfo.getNumber("lng"));

    }

    @ClientCallable
    public void hideMapPopup() {
        poiPopup.hidePopup();
    }

    public void configNewConsultationForm(Optional<PersoanaFizicaJuridica> persoanaFizicaJuridica) {
        if (persoanaFizicaJuridica.isPresent()) {
            setSurname(persoanaFizicaJuridica.get().getNume());
            setName(persoanaFizicaJuridica.get().getPrenume());
            setEmail(persoanaFizicaJuridica.get().getEmail());
            if (persoanaFizicaJuridica.get().getTelefon() != null) {
                setPhone(persoanaFizicaJuridica.get().getTelefon());
            }

        }
        if (getPresenter().getParticipatoryBudgetingActionForm() == Ps4ECitizenParticipatoryBudgetingPresenter.ParticipatoryBudgetingActionForm.ADMIN) {
            consultationFormTermsAgreement.setValue(true);
            consultationFormCategory.setValue(null);
            consultationFormMessage.setValue("");
            consultationFormDescription.setValue("");
            consultationFormState.setValue(null);
            consultationFormStartDate.setValue(null);
            consultationFormDueDate.setValue(null);
            consultationFormValue.setValue("");
            consultationFormPosition.setValue("");
            consultationFormDurataValue.setValue("");
            consultationFormBeneficii.setValue("");
            consultationFormBeneficiari.setValue("");
        } else {
            consultationFormTermsAgreement.setValue(false);
        }
        consultationFormSetVisible(true);
        consultationCancelBtn.setVisible(true);
        consultationMarkerBtn.setVisible(true);
        consultationSendBtn.setVisible(true);

    }

    public void configNewMarkerPlacementConsultationForm() {
        multiFileMemoryBuffer = new MultiFileMemoryBuffer();
        chunkUpload.setReceiver(multiFileMemoryBuffer);
        chunkUpload.getElement().setPropertyJson("files", Json.createArray());


        multiFileMemoryBufferPhotos = new MultiFileMemoryBuffer();
        chunkUploadPhotos.setReceiver(multiFileMemoryBufferPhotos);
        chunkUploadPhotos.getElement().setPropertyJson("files", Json.createArray());


        multiFileMemoryBufferVideos = new MultiFileMemoryBuffer();
        chunkUploadVideos.setReceiver(multiFileMemoryBufferVideos);
        chunkUploadVideos.getElement().setPropertyJson("files", Json.createArray());

        consultationFormSetVisible(false);
        consultationCancelBtn.setVisible(false);
        consultationMarkerBtn.setVisible(true);
        consultationSendBtn.setVisible(false);
        consultationFormCategory.setValue(null);
        consultationFormAddress.setValue("");
        consultationFormMessage.setValue("");
        consultationFormDescription.setValue("");
        consultationFormState.setValue(null);
        consultationFormStartDate.setValue(null);
        consultationFormDueDate.setValue(null);
        consultationFormValue.setValue("");
        consultationFormPosition.setValue("");
        consultationFormDurataValue.setValue("");
        consultationFormBeneficii.setValue("");
        consultationFormBeneficiari.setValue("");
        if (getPresenter().getParticipatoryBudgetingActionForm() == Ps4ECitizenParticipatoryBudgetingPresenter.ParticipatoryBudgetingActionForm.ADMIN) {
            consultationFormTermsAgreement.setValue(true);
        } else {
            consultationFormTermsAgreement.setValue(false);
        }
    }

    private void configLeftPanel() {
        leftPanel.getStyle().set("min-height", "600px");
        leftPanel.getStyle().set("margin-top", "100px");
        leftPanel.addClassName("left_panel");
        toggleSideBar.addClassName("toggle_sidebar");
//        toggleSideBar.addClickListener(e -> )
        panelHeader.addClassNames("panel_header", "clearfix");
        panelHeader.getStyle().set("text-align","center");
        panelContainer.addClassName("panel_container");
        budgetaryTabBtn.addClickListener(e -> displayBudgetaryTab());
        configPanelHeaderTabButton(budgetaryTabBtn, "consultare", "fa-user-friends", false, "Voteaza proiect");
        configBudgetaryTab();
        consultationTabBtn.addClickListener(e -> displayConsultationTab());
        configPanelHeaderTabButton(consultationTabBtn, "bugetare", "fa-coins", true, "Bugetare Participativa");
        configConsultationTab();
    }

    protected void displayConsultationTab() {
        if (!consultationTabBtn.hasClassName("active")) {
            consultationTabBtn.addClassName("active");
            consultationTab.addClassName("show");
            consultationTabBtn.getElement().setAttribute("status", "active");
            budgetaryTabBtn.removeClassName("active");
            budgetaryTab.removeClassName("show");
            budgetaryTabBtn.getElement().setAttribute("status", "false");
            UI.getCurrent().getPage().executeJs("clearMapPopup();");
        }
        if (getPresenter().getParticipatoryBudgetingActionForm() == Ps4ECitizenParticipatoryBudgetingPresenter.ParticipatoryBudgetingActionForm.ADMIN) {
            return;
        }
        getPresenter().setParticipatoryBudgetingActionForm(Ps4ECitizenParticipatoryBudgetingPresenter.ParticipatoryBudgetingActionForm.CONSULTATION);

    }

    public void hideBudgetary(){
        budgetaryTab.setVisible(false);
        budgetaryTabBtn.setVisible(false);
    }
    public void hideConsultation(){
        consultationTab.setVisible(false);
        consultationTabBtn.setVisible(false);
    }
    public void displayPoiAdmin() {
        displayConsultationTab();
        budgetaryTab.setVisible(false);
        budgetaryTabBtn.setVisible(false);
        //displayBudgetaryTab();
        //consultationTabBtn.getStyle().set("display", "none");
       /* budgetaryTabBtn.addClassName("active");
        budgetaryTabBtn.getStyle().remove("display");
       */
       consultationFormRowAdmin1.getStyle().remove("display");
        consultationFormRowAdmin2.getStyle().remove("display");
        consultationFormRowPosition.getStyle().remove("display");
        consultationFormRowAdmin3.getStyle().remove("display");
        publishProjectLayout.getStyle().remove("display");
        consultationFormCustomControlFiltersPublished.getStyle().remove("display");
        consultationFormCustomControlFiltersUnpublished.getStyle().remove("display");
        consultationFormDraft.setValue(true);
        //getPresenter().loadDraftPois(SecurityUtils.getToken());

        configConsultationFormCustomControlFilters();

        //consultationFormRowAdmin4.getStyle().remove("display");
        getPresenter().setParticipatoryBudgetingActionForm(Ps4ECitizenParticipatoryBudgetingPresenter.ParticipatoryBudgetingActionForm.ADMIN);
    }

    protected void displayBudgetaryTab() {
        budgetaryFormRowAdminProjects.addClassName("form-group");
        if (!budgetaryTabBtn.hasClassName("active")) {
            budgetaryTabBtn.addClassName("active");
            budgetaryTab.addClassName("show");
            budgetaryTabBtn.getElement().setAttribute("status", "active");
            consultationTabBtn.removeClassName("active");
            consultationTab.removeClassName("show");
            consultationTabBtn.getElement().setAttribute("status", "false");
            configNewBudgetaryTab();
            UI.getCurrent().getPage().executeJs("clearMapPopup();");
        }
        if (getPresenter().getParticipatoryBudgetingActionForm() == Ps4ECitizenParticipatoryBudgetingPresenter.ParticipatoryBudgetingActionForm.ADMIN) {

            return;
        }
        getPresenter().setParticipatoryBudgetingActionForm(Ps4ECitizenParticipatoryBudgetingPresenter.ParticipatoryBudgetingActionForm.BUDGETING);
    }

    private void configPanelHeaderTabButton(NativeButton tabBtn, String tab, String fasIco, Boolean active, String label) {
        tabBtn.addClassNames("btn", "map_tab");
        tabBtn.getElement().setAttribute("map-tab", tab);
        tabBtn.getElement().setAttribute("status", active ? "active" : "false");
        if (active) {
            tabBtn.addClassName("active");
        }
        HtmlContainer icon = new HtmlContainer("i");
        icon.addClassNames("fas", fasIco);
        Span span = new Span(label);
        tabBtn.add(icon, span);
    }

    private void configConsultationTab() {
        consultationTab.setId("consultare");
        consultationTabBtn.getStyle().set("float","none");
        consultationTab.addClassName("panel_tab_content");
        consultationTabHeader.addClassNames("underline", "mt-0");
        consultationInfoPhoneLayout.addClassName("phone");
        consultationInfoPhoneIcon.addClassNames("fas", "fa-phone-volume", "text-secondary");
        consultationInfoGap20.addClassName("gap-20");
        consultationInfo2Gap20.addClassName("gap-20");
        consultationForm.addClassName("sm-spacing");
        consultationFormRow1.addClassNames("form-group", "form-row", "no-gutters");
        consultationFormRow2.addClassNames("form-group", "form-row", "no-gutters");
        consultationFormRow3.addClassName("form-group");
        consultationFormRow4.addClassName("form-group");
        consultationFormRow5.addClassName("form-group");
        consultationFormRowDescription.addClassName("form-group");
        consultationFormRowAdminProjects.addClassName("form-group");


        consultationFormRowAdmin1.addClassName("form-group");
        consultationFormRowAdmin1.getStyle().set("display", "none");
        consultationFormRowAdmin2.addClassName("form-group");
        consultationFormRowAdmin2.getStyle().set("display", "none");
        consultationFormRowPosition.addClassName("form-group");
        consultationFormRowPosition.getStyle().set("display", "none");
        publishProjectLayout.getStyle().set("display", "none");
        consultationFormCustomControlFiltersPublished.getStyle().set("display", "none");
        consultationFormCustomControlFiltersUnpublished.getStyle().set("display", "none");
        consultationFormRowAdmin3.addClassName("form-group");
        consultationFormRowAdmin3.getStyle().set("display", "none");
        consultationFormRowAdmin4.addClassName("form-group");
        consultationFormRowDurata.addClassName("form-group");
        consultationFormRowBeneficiari.addClassName("form-group");
        consultationFormRowBeneficii.addClassName("form-group");
        //consultationFormRowAdmin4.getStyle().set("display", "none");
        consultationFormRow6.addClassNames("form-group", "box_upload_more");
        consultationFormRowVideos.addClassNames("form-group", "box_upload_more");
        consultationFormRowPhotos.addClassNames("form-group", "box_upload_more");
        consultationFormCustomControl.addClassNames("custom-control", "custom-checkbox");
        consultationFormButtons.addClassName("form-buttons");
        consultationFormSetVisible(false);
        configConsultationFormRow1();
        configConsultationFormRow2();
        configConsultationFormRow3();
        configConsultationFormRow4();
        configConsultationFormRow5();
        configConsultationFormRowDescription();
        configConsultationFormRowAdmin1();
        configConsultationFormRowAdminProjects();
        configConsultationFormRowAdmin2();
        configConsultationFormRowPosition();
        configConsultationFormRowAdmin3();
        configConsultationFormRowAdmin4();
        configConsultationFormRowDurata();
        configConsultationFormRowBeneficiari();
        configConsultationFormRowBeneficii();
        configConsultationFormRow6();
        configConsultationFormRowPhotos();
        configConsultationFormRowVideos();
        configConsultationFormCustomControl();

        configConsultationFormActionControls();
        if (getPresenter().hasAuthenticateUser()) {
            configConsultationTabForUser();
        } else {
            configConsultationTabForNoUser();
        }
    }

    private void consultationFormSetVisible(Boolean visible) {
        consultationFormRow1.setVisible(visible);
        consultationFormRow2.setVisible(visible);
        consultationFormRow3.setVisible(visible);
        consultationFormRow4.setVisible(visible);
        consultationFormRow5.setVisible(visible);
        consultationFormRowDescription.setVisible(visible);
        consultationFormRow6.setVisible(visible);
        consultationFormRowPhotos.setVisible(visible);
        consultationFormRowVideos.setVisible(visible);
        if (getPresenter().getParticipatoryBudgetingActionForm() == Ps4ECitizenParticipatoryBudgetingPresenter.ParticipatoryBudgetingActionForm.ADMIN) {
            consultationFormCustomControl.setVisible(false);
        } else {
            consultationFormCustomControl.setVisible(visible);
        }
        consultationFormRowAdmin1.setVisible(visible);
        consultationFormRowAdmin2.setVisible(visible);
        consultationFormRowPosition.setVisible(visible);
        consultationFormRowAdmin3.setVisible(visible);
        consultationFormRowAdmin4.setVisible(visible);
        consultationFormRowDurata.setVisible(visible);
        consultationFormRowBeneficiari.setVisible(visible);
        consultationFormRowBeneficii.setVisible(visible);
        publishProjectLayout.setVisible(visible);
        consultationFormCustomControlFiltersUnpublished.setVisible(visible);
        consultationFormCustomControlFiltersPublished.setVisible(visible);
    }

    private HtmlContainer constructConsultationFormActionBtnIco() {
        HtmlContainer icon = new HtmlContainer("i");
        icon.addClassNames("fas", "fa-arrow-alt-circle-right");
        return icon;
    }
    private HtmlContainer constructConsultationFormActionBtnIcoCancel() {
        HtmlContainer icon = new HtmlContainer("i");
        icon.addClassNames("fas", "fa-arrow-alt-circle-left");
        return icon;
    }
    private void configConsultationFormActionControls() {
        consultationFormButtonsLayout.addClassName("custom_3_buttons");
        consultationCancelBtn.addClassNames("btn", "btn-sm", "gray");
        consultationCancelBtn.add(constructConsultationFormActionBtnIcoCancel(),new Span("Renunță"));
        consultationCancelBtn.setVisible(false);
        consultationCancelBtn.setHref("javascript:void(0);");
        consultationMarkerBtn.addClassNames("btn", "btn-sm", "blue");
        consultationMarkerBtn.add(new Span("Plasează indicatorul"), constructConsultationFormActionBtnIco());
        consultationMarkerBtn.setHref("javascript:void(0);");
        consultationSendBtn.addClassNames("btn", "btn-sm", "green");
        consultationSendBtn.add(new Span("Salveaza"), constructConsultationFormActionBtnIco());
        consultationSendBtn.setVisible(false);
        consultationSendBtn.setHref("javascript:void(0);");
    }

    private void configConsultationFormCustomControl() {
        ClickNotifierAnchor termsAndConditionPagesLink = new ClickNotifierAnchor();
        termsAndConditionPagesLink.setText("termenii si conditiile");
        termsAndConditionPagesLink.setHref("javascript:void(0);");
        termsAndConditionPagesLink.addClickListener(e
                -> VaadinClientUrlUtil
                .setLocation(RouteConfiguration.forApplicationScope()
                        .getUrl(Ps4ECitizenTermsAndConditionsRoute.class)));
//        consultationFormTermsAgreementLabel.addClassName("custom-control-label");
        consultationFormTermsAgreementLabel.add(new Text("Sunt de acord cu "), termsAndConditionPagesLink);
//        consultationFormTermsAgreement.s
        // consultationFormTermsAgreement.addClassName("smart-form-control");
    }

    private void configConsultationFormCustomControlFilters() {
        configConsultationFormApproved();
        configConsultationFormDraft();

    }

    private void configConsultationFormApproved() {

        consultationFormApprovedLabel.add(new Text("Vezi proiecte publicate"));
        //    consultationFormApproved.addClassName("smart-form-control");

    }

    private void configConsultationFormDraft() {

        consultationFormDraftLabel.add(new Text("Vezi proiecte nepublicate"));
        //    consultationFormDraft.addClassName("smart-form-control");

    }

    private void configConsultationFormRowPhotos(){
        Div chunkUploadButton = new Div();
        chunkUploadButton.addClassName("tooltip_chat");
        NativeButton button = new NativeButton();
        button.addClassName("upload_more");
        chunkUploadButton.add(button);
        HtmlContainer icon = new HtmlContainer("i");
        icon.addClassNames("fas", "fa-plus");
        button.add(icon);
        chunkUploadPhotos.setI18n(initI18N("photos"));
        chunkUploadPhotos.setDropAllowed(true);
        chunkUploadPhotos.setUploadButton(chunkUploadButton);

        chunkUploadPhotos.addFileRejectedListener(myFileRejectedListener -> {
            UI.getCurrent().getPage().executeJs(" swalError($0);", "Tip fisier incorect! <p>Fisierele acceptate sunt: jpg, jpeg, png, bmp, tiff, gif, raw</p>");
        });
        chunkUploadPhotos.setAcceptedFileTypes(".png",".jpg", ".jpeg", ".png", ".bmp", ".tiff", ".gif", ".raw");
    }


    private void configConsultationFormRowVideos(){
        Div chunkUploadButton = new Div();
        chunkUploadButton.addClassName("tooltip_chat");
        NativeButton button = new NativeButton();
        button.addClassName("upload_more");
        chunkUploadButton.add(button);
        HtmlContainer icon = new HtmlContainer("i");
        icon.addClassNames("fas", "fa-plus");
        button.add(icon);
        chunkUploadVideos.setI18n(initI18N("videos"));
        chunkUploadVideos.setDropAllowed(true);
        chunkUploadVideos.setUploadButton(chunkUploadButton);
        chunkUploadVideos.addFileRejectedListener(myFileRejectedListener -> {
            UI.getCurrent().getPage().executeJs(" swalError($0);", "Tip fisier incorect! <p>Fisierele acceptate sunt: mp4, mov , avi, mkv, flv, wmw, mpeg</p>");
        });
        chunkUploadVideos.setAcceptedFileTypes(".mp4",".mov" , ".avi", ".mkv", ".flv", ".wmw", ".mpeg");

    }
    private void configConsultationFormRow6() {
        Div chunkUploadButton = new Div();
        chunkUploadButton.addClassName("tooltip_chat");
        NativeButton button = new NativeButton();
        button.addClassName("upload_more");
        chunkUploadButton.add(button);
        HtmlContainer icon = new HtmlContainer("i");
        icon.addClassNames("fas", "fa-plus");
        button.add(icon);
        chunkUpload.setI18n(initI18N("files"));
        chunkUpload.setDropAllowed(true);
        chunkUpload.setUploadButton(chunkUploadButton);
        chunkUpload.setAcceptedFileTypes(".doc",".docx",".ppt",".pptx",".xls",".xlsx",".html",".txt",".pdf");
        chunkUpload.addFileRejectedListener(myFileRejectedListener -> {
            UI.getCurrent().getPage().executeJs(" swalError($0);", "Tip fisier incorect! <p>Fisierele acceptate sunt: doc, docx, ppt, pptx, xls, xlsx, html, txt, pdf</p>");
        });
//        DomEventViewSupport.registerUploadSucceededEvent(this, "uploadFile", chunkUpload, );
    }

    private void configConsultationFormRowAdmin4() {
        consultationFormValueLayout.addClassNames("textarea", "div_label");
        consultationFormValueLabel.addClassName("label");
        consultationFormValue.addClassName("smart-form-control");
        consultationFormValue.setPlaceholder("Valoare proiect (lei)");
        consultationFormValue.setWidthFull();
        consultationFormValue.addInputListener(e -> {
            String uiValue = consultationFormValue.getValue();
            if(uiValue!=null && !uiValue.isEmpty()){
                try {

                    Double convertedValue = Double.valueOf(consultationFormValue.getValue());

                } catch (Exception ex) {
                    Notification.show(
                            "Introduceti o valoare numerica pentru valoarea proiectului!");
                }
            }

        })  ;
        displayInputLabelOnValue(consultationFormValue, consultationFormValueLayout);
    }
    private void configConsultationFormRowDurata() {
        consultationFormRowDurataLayout.addClassNames("textarea", "div_label");
        consultationFormDurataLabel.addClassName("label");
        consultationFormDurataValue.addClassName("smart-form-control");
        consultationFormDurataValue.setPlaceholder("Durata proiect ");
        consultationFormDurataValue.setWidthFull();
        displayInputLabelOnValue(consultationFormDurataValue, consultationFormRowDurataLayout);
    }
    private void configConsultationFormRowBeneficiari() {
        consultationFormBeneficiariLayout.addClassNames("textarea", "div_label");
        consultationFormBeneficiariLabel.addClassName("label");
        consultationFormBeneficiari.addClassName("smart-form-control");
        consultationFormBeneficiari.setPlaceholder("Beneficiari");
        consultationFormBeneficiari.setWidthFull();
        displayInputLabelOnValue(consultationFormBeneficiari, consultationFormBeneficiariLayout);
    }


    private void configConsultationFormRowBeneficii() {
        consultationFormBeneficiiLayout.addClassNames("textarea", "div_label");
        consultationFormBeneficiiLabel.addClassName("label");
        consultationFormBeneficii.addClassName("smart-form-control");
        consultationFormBeneficii.setPlaceholder("Beneficii");
        consultationFormBeneficii.setWidthFull();
        displayInputLabelOnValue(consultationFormBeneficii, consultationFormBeneficiiLayout);
    }

    private void configConsultationFormRowAdmin3() {
        consultationFormDueDateLayout.addClassNames("textarea", "div_label");
        consultationFormDueDateLabel.addClassName("label");
        consultationFormDueDate.addClassName("smart-form-control");
        consultationFormDueDate.setPlaceholder("Data finalizare vot");
        consultationFormDueDate.setWidthFull();
        //        displayInputLabelOnValue(consultationFormMessage, consultationFormMessageLayout);
    }

    private void configConsultationFormRowAdmin2() {
        consultationFormStartDateLayout.addClassNames("textarea", "div_label");
        consultationFormStartDateLabel.addClassName("label");
        consultationFormStartDate.addClassName("smart-form-control");
        consultationFormStartDate.setPlaceholder("Data incepere vot");
        consultationFormStartDate.setWidthFull();
//        displayInputLabelOnValue(consultationFormMessage, consultationFormMessageLayout);
    }
    private void configConsultationFormRowPosition() {
        consultationFormPositionLayout.addClassNames("textarea", "div_label");
        consultationFormPositionLabel.addClassName("label");
        consultationFormPosition.addClassName("smart-form-control");
        consultationFormPosition.setPlaceholder("Pozitie afisare");
        consultationFormPosition.setWidthFull();

        consultationFormPosition.setValueChangeMode(ValueChangeMode.EAGER);

        consultationFormPosition.addInputListener(e -> {
            String uiValue = consultationFormPosition.getValue();
            if(uiValue!=null && !uiValue.isEmpty()){
                try {

                    Double convertedValue = Double.valueOf(consultationFormPosition.getValue());

                } catch (Exception ex) {
                    Notification.show(
                            "Introduceti o valoare numerica pentru pozitia de afisare!");
                }
            }

        })  ;
    }
    private void configConsultationFormRowAdmin1() {
        consultationFormState.setAllowCustomValue(false);
        consultationFormState.setItemLabelGenerator(StatusProiect::getDenumire);
        consultationFormState.setPlaceholder("Status proiect");

        consultationFormState.addClassNames("smart-form-control", "chosen-container", "chosen-container-single", "chosen-container-single-nosearch");
        UI.getCurrent().getPage().executeJs("addThemeSmall($0);", consultationFormState.getElement());

    }
    private void configConsultationFormRowAdminProjects() {
        consultationFormRowAdminProjects.addClassName("underline");
        consultationFormRowAdminProjects.getStyle().set("margin-top","10px");
        consultationFormProjects.setAllowCustomValue(false);
        consultationFormProjects.setItemLabelGenerator(ProjectInfo::getDenumireSiAdresa);
        consultationFormProjects.setPlaceholder("Alege proiect");

        consultationFormProjects.addClassNames("smart-form-control", "chosen-container", "chosen-container-single", "chosen-container-single-nosearch");
        UI.getCurrent().getPage().executeJs("addThemeSmall($0);", consultationFormProjects.getElement());
        consultationFormProjects.addValueChangeListener(e ->{
            UI.getCurrent().getPage().executeJs("triggerClickMarker($0,$1);", consultationFormProjects.getValue().getId(),this);

        });

    }
    private void configBudgetaryFormRowAdminProjects() {
        budgetaryFormRowAdminProjects.addClassName("underline");
        budgetaryFormRowAdminProjects.getStyle().set("margin-top","10px");
        budgetaryFormProjects.setAllowCustomValue(false);
        budgetaryFormProjects.setItemLabelGenerator(ProjectInfo::getDenumireSiAdresa);
        budgetaryFormProjects.setPlaceholder("Alege proiect");

        budgetaryFormProjects.addClassNames("smart-form-control", "chosen-container", "chosen-container-single", "chosen-container-single-nosearch");
        UI.getCurrent().getPage().executeJs("addThemeSmall($0);", budgetaryFormProjects.getElement());
        budgetaryFormProjects.addValueChangeListener(e ->{
            UI.getCurrent().getPage().executeJs("triggerClickMarker($0,$1);", budgetaryFormProjects.getValue().getId(),this);

        });

    }
    private void configConsultationFormRow5() {
        consultationFormMessageLayout.addClassNames("textarea", "div_label");
        consultationFormMessageLabel.addClassName("label");
        consultationFormMessage.addClassName("smart-form-control");
        consultationFormMessage.setPlaceholder("Denumire proiect*");
        consultationFormMessage.setWidthFull();
        displayInputLabelOnValue(consultationFormMessage, consultationFormMessageLayout);
    }
    private void configConsultationFormRowDescription() {
        consultationFormDescriptionLayout.addClassNames("textarea", "div_label");
        consultationFormDescriptionLabel.addClassName("label");
        consultationFormDescription.addClassName("smart-form-control");
        consultationFormDescription.setPlaceholder("Descriere proiect*");
        consultationFormDescription.setWidthFull();
        displayInputLabelOnValue(consultationFormDescription, consultationFormDescriptionLayout);
    }
    private void configConsultationFormRow4() {

        horizontalLayoutAddress.getStyle().set("align-items","center");
        consultationFormAddressLayout.addClassNames("input", "div_label","smart-form-control");
        consultationFormAddressLabel.addClassName("label");
        consultationFormAddress.addClassName("vaadin-ps4-theme");
        consultationFormAddress.setPlaceholder("Adresa");
        consultationFormAddress.setWidthFull();
        iconMap.addClassNames("fa","fa-map");

        displayInputLabelOnValue(consultationFormAddress, consultationFormAddressLayout);
    }

    private void configConsultationFormRow3() {
        consultationFormCategory.setAllowCustomValue(false);
        consultationFormCategory.setItemLabelGenerator(CategoriePOI::getDenumire);
        consultationFormCategory.addClassNames("smart-form-control", "chosen-container", "chosen-container-single", "chosen-container-single-nosearch");
        UI.getCurrent().getPage().executeJs("addThemeSmall($0);", consultationFormCategory.getElement());
        consultationFormCategory.setPlaceholder("Categorie proiect");
    }

    private void configConsultationFormRow2() {
        consultationFormRow2Col1.addClassName("col-6");
        consultationFormRow2Col2.addClassName("col-6");
        consultationFormEmailLayout.addClassNames("input", "div_label");
        consultationFormEmailLabel.addClassName("label");
        consultationFormEmail.addClassName("smart-form-control");
        consultationFormEmail.setPlaceholder("Email*");
        displayInputLabelOnValue(consultationFormEmail, consultationFormEmailLayout);
        consultationFormPhoneLayout.addClassNames("input", "div_label");
        consultationFormPhoneLabel.addClassName("label");
        consultationFormPhone.addClassName("smart-form-control");
        consultationFormPhone.setPlaceholder("Telefon*");
        displayInputLabelOnValue(consultationFormPhone, consultationFormPhoneLayout);
    }

    private void configConsultationFormRow1() {
        consultationFormRow1Col1.addClassName("col-6");
        consultationFormRow1Col2.addClassName("col-6");
        consultationFormSurnameLayout.addClassNames("input", "div_label");
        consultationFormSurnameLabel.addClassName("label");
        consultationFormSurname.addClassName("smart-form-control");
        consultationFormSurname.setPlaceholder("Nume");
        displayInputLabelOnValue(consultationFormSurname, consultationFormSurnameLayout);
        consultationFormNameLayout.addClassNames("input", "div_label");
        consultationFormNameLabel.addClassName("label");
        consultationFormName.addClassName("smart-form-control");
        consultationFormName.setPlaceholder("Prenume");
        displayInputLabelOnValue(consultationFormName, consultationFormNameLayout);
    }

    private void configConsultationTabForNoUser() {
        consultationTabBtn.getStyle().set("display", "none");
//        consultationTabHeader.getStyle().set("display", "none");
//        consultationForm.getStyle().set("display", "none");
//        consultationTabInfoParagraph.setText("Pentru intervenția operativă a Poliției Locale vă rugăm să apelați numerele de telefon:");
//        consultationInfoPhone1Title.setText("Dispeceratul poliției locale");
//        consultationInfoPhone2Title.setText("Telefonul primarului");
    }

    private void configConsultationTabForUser() {
        consultationTabBtn.getStyle().remove("display");
//        consultationTabHeader.getStyle().remove("display");
//        consultationForm.getStyle().remove("display");
//        consultationTabInfoParagraph.setText("PORTAL apreciaza propunerile Dvs pentru noi propuneri de proiect. Completati mai jos:");
//        consultationInfoPhone1Title.setText("Telefonul Primarului");
//        consultationInfoPhone2Title.setText("Asistenta Cetateni");
    }

    private void configBudgetaryTab() {
        budgetaryTab.setId("bugetare");
        budgetaryTab.addClassNames("panel_tab_content", "show");
        budgetaryTabBtn.getStyle().set("float","none");
        budgetaryInfoPhoneLayout.addClassName("phone");
        budgetaryInfoPhoneIcon.addClassNames("fas", "fa-phone-volume", "text-secondary");
        budgetaryTabHeader.addClassName("underline");
        budgetaryDetails.addClassName("details");
        budgetaryActions.addClassNames("actions", "clearfix");
        upVoteProjectBtn.addClassNames("thumb_action", "upvote");
        HtmlContainer upVoteIcon = new HtmlContainer("i");
        upVoteIcon.addClassNames("fas", "fa-thumbs-up");
        upVoteProjectBtn.add(new Text("Îmi place "), upVoteIcon);
        downVoteProjectBtn.addClassNames("thumb_action", "downvote");
        HtmlContainer downVoteIcon = new HtmlContainer("i");
        downVoteIcon.addClassNames("fas", "fa-thumbs-down");
        downVoteProjectBtn.add(new Text("Nu-mi place "), downVoteIcon);
        configNewBudgetaryTab();
//        if(getPresenter().hasAuthenticateUser()) {
//            configBudgetaryTabForUser();
//        } else {
//            configBudgetaryTabForNoUser();
//        }
    }


    public void configNewBudgetaryTab() {
        budgetaryTabHeader.getStyle().set("display", "none");
        budgetaryDetails.getStyle().set("display", "none");
        budgetaryActions.getStyle().set("display", "none");
//        budgetaryTabBtn.getStyle().set("display", "none");
    }

    private void configBudgetaryTabInfo(JsonObject poiInfo) {
//        budgetaryTabBtn.getStyle().remove("display");
        budgetaryTabHeader.getStyle().remove("display");
        budgetaryDetails.getStyle().remove("display");
        budgetaryActions.getStyle().remove("display");

        budgetaryCategory.setText(poiInfo.getString("categoriePoi"));


        budgetaryState.setText(JreJsonNull.class.isAssignableFrom(poiInfo.get("statusProiect").getClass()) ? "" : poiInfo.getString("statusProiect"));
        if(poiInfo.getString("denumire")!=null){
            budgetaryProjectDetails.setText(poiInfo.getString("denumire"));
        }
        try{
            budgetaryVoteDueDate.setText(poiInfo.getString("dataEndVote"));
        }catch (Exception e) {

        }
        try{
            budgetaryStartVoteDate.setText(poiInfo.getString("dataStartVote"));
        }catch (Exception e) {

        }

        try{
            budgetaryProjectBeneficii.setText(poiInfo.getString("beneficii"));
        }catch (Exception e){

        }
        try{
            budgetaryProjectDurata.setText(poiInfo.getString("propusDurataProiect"));
        }catch (Exception e){

        }
        try{
            budgetaryProjectBeneficiari.setText(poiInfo.getString("beneficiari"));
        }catch (Exception e) {

        }
        try{
            budgetaryProjectDescription.setText(poiInfo.getString("descriere"));
        }catch (Exception e) {

        }
        try{
            budgetaryProjectValue.setText(poiInfo.getNumber("valoare") + " LEI");
        }catch (Exception e) {

        }

        Double publicat= poiInfo.getNumber("publicat");
        if(publicat!=null && publicat.equals(1)){
            publishProject.setValue(true);
        }else{
            publishProject.setValue(false);
        }
        try{
            budgetaryProjectAddress.setText(poiInfo.getString("address") );
        }catch (Exception e) {

        }
    }

    public void configAdminForm(ProjectInfo poiInfo, List<CategoriePOI> categories, List<StatusProiect> stages) {
        setSurname(poiInfo.getPropusDeNume());
        setName(poiInfo.getPropusDePrenume());
        setEmail(poiInfo.getPropusDeEmail());
        if (poiInfo.getPropusDeTelefon() != null) {
            setPhone(poiInfo.getPropusDeTelefon());
        }
        if (Optional.ofNullable(poiInfo.getIdCategoriePoi()).isPresent()) {
            consultationFormCategory.setValue(categories.stream().filter(categoriePOI -> categoriePOI.getId() == poiInfo.getIdCategoriePoi()).findFirst().get());
        }

        setAddress(Optional.ofNullable(poiInfo.getAddress()).orElse(""));
        consultationFormMessage.setValue(poiInfo.getDenumire());
        consultationFormDescription.setValue(poiInfo.getDescriere());
        if (Optional.ofNullable(poiInfo.getBeneficii()).isPresent()) {
            consultationFormBeneficii.setValue(poiInfo.getBeneficii());
        }
        if (Optional.ofNullable(poiInfo.getBeneficiari()).isPresent()) {
            consultationFormBeneficiari.setValue(poiInfo.getBeneficiari());
        }
        if (poiInfo.getIdStatusProiect()!=null && poiInfo.getIdStatusProiect()!=0) {
            consultationFormState.setValue(stages.stream().filter(statusProiect -> statusProiect.getId() == poiInfo.getIdStatusProiect()).findFirst().get());
        }

        if (Optional.ofNullable(poiInfo.getDataStartVote()).isPresent()) {
            consultationFormStartDate.setValue(LocalDate.parse(poiInfo.getDataStartVote(), DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        }
        if (Optional.ofNullable(poiInfo.getDataEndVote()).isPresent()) {
            consultationFormDueDate.setValue(LocalDate.parse(poiInfo.getDataEndVote(), DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        }
        if (Optional.ofNullable(poiInfo.getValoare()).isPresent()) {
            consultationFormValue.setValue(poiInfo.getValoare() + "");
        }
        if (Optional.ofNullable(poiInfo.getInfo1()).isPresent()) {
            consultationFormPosition.setValue(poiInfo.getInfo1());
        }
        if (Optional.ofNullable(poiInfo.getPropusDurataProiect()).isPresent()) {
            consultationFormDurataValue.setValue(poiInfo.getPropusDurataProiect());
        }
        if (Optional.ofNullable(poiInfo.getNrVoturiPro()).isPresent()) {
            consultationFormLikeVotesLabel.setText("Numar voturi pro: " + poiInfo.getNrVoturiPro());
        }
        if (Optional.ofNullable(poiInfo.getNrVoturiContra()).isPresent()) {
            consultationFormDislikeVotesLabel.setText("Numar voturi contra: " + poiInfo.getNrVoturiContra());
            consultationFormDislikeVotesLabel.getStyle().set("float", "right");
        }
        Integer publicat= poiInfo.getPublicat();
        if(publicat!=null && publicat.equals(1)){
            publishProject.setValue(true);
        }else{
            publishProject.setValue(false);
        }
    }


    private void displayInputLabelOnValue(TextField inputComponent, Div layout) {
        inputComponent.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        inputComponent.getElement().addEventListener("keyup", e -> {
            if (!e.getEventData().getString("element.value").isEmpty()) {
                if (!layout.hasClassName("show_label")) {
                    layout.addClassNames("show_label");
                }
            } else {
                if (layout.hasClassName("show_label")) {
                    layout.removeClassNames("show_label");
                }
            }
        }).addEventData("element.value");

    }

    private void displayInputLabelOnValue(TextArea inputComponent, Div layout) {
        inputComponent.getElement().addEventListener("keyup", e -> {
            if (!e.getEventData().getString("element.value").isEmpty()) {
                if (!layout.hasClassName("show_label")) {
                    layout.addClassNames("show_label");
                }
            } else {
                if (layout.hasClassName("show_label")) {
                    layout.removeClassNames("show_label");
                }
            }
        }).addEventData("element.value");

    }


    protected UploadI18N initI18N(String typeOfFiles) {
        UploadI18N i18n = new UploadI18N();
        i18n.setAddFiles(new UploadI18N.AddFiles()
                .setMany(getTranslation("component.upload.addfiles.many."+typeOfFiles))
                .setOne(getTranslation("component.upload.addfiles.one."+typeOfFiles))
        );

        i18n.setCancel(getTranslation("component.upload.cancel"));

        i18n.setDropFiles(new UploadI18N.DropFiles()
                .setMany(getTranslation("component.upload.dropfiles.many."+typeOfFiles))
                .setOne(getTranslation("component.upload.dropfiles.one."+typeOfFiles))
        );

        i18n.setUploading(new UploadI18N.Uploading()
                .setStatus(new UploadI18N.Uploading.Status()
                        .setConnecting(getTranslation("component.upload.uploading.status.connecting"))
                        .setHeld(getTranslation("component.upload.uploading.status.held"))
                        .setProcessing(getTranslation("component.upload.uploading.status.processing"))
                        .setStalled(getTranslation("component.upload.uploading.status.stalled"))
                )
                .setRemainingTime(new UploadI18N.Uploading.RemainingTime()
                        .setPrefix(getTranslation("component.upload.uploading.remainingtime.prefix"))
                        .setUnknown(getTranslation("component.upload.uploading.remainingtime.unknon"))
                )
                .setError(new UploadI18N.Uploading.Error()
                        .setForbidden(getTranslation("component.upload.uploading.error.forbidden"))
                        .setServerUnavailable(getTranslation("component.upload.uploading.error.serverunavailable"))
                        .setUnexpectedServerError(getTranslation("component.upload.uploading.error.unexpectedservererror"))
                )
        );

        i18n.setError(new UploadI18N.Error()
                .setFileIsTooBig(getTranslation("component.upload.error.fileistoobig"))
                .setIncorrectFileType(getTranslation("component.upload.error.incorrectfiletype"))
                .setTooManyFiles(getTranslation("component.upload.error.toomanyfiles"))
        );

        return i18n;
    }

    public static Boolean checkDatesBetweenCurrent(String startDateVote, String endDateVote) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date startDate = new Date();
        if (startDateVote != null) {
            try {
                startDate = simpleDateFormat.parse(startDateVote);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        Date endDate = new Date();
        if (endDateVote != null) {
            try {
                endDate = simpleDateFormat.parse(endDateVote);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        Date currentDate = new Date();
        return startDate.before(currentDate) && currentDate.before(endDate);
    }
    public static Boolean checkDateBefore(String date1, String date2) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date startDate = new Date();
        if (date1 != null) {
            try {
                startDate = simpleDateFormat.parse(date1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        Date endDate = new Date();
        if (date2 != null) {
            try {
                endDate = simpleDateFormat.parse(date2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return startDate.before(endDate);
    }
    @ClientCallable
    public void swalInfoAck() {

        VaadinClientUrlUtil.setLocation("/logout");
    }
}
