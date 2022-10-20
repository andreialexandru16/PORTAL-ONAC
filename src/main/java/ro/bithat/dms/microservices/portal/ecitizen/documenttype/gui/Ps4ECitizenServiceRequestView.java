package ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.RouteConfiguration;
import ro.bithat.dms.microservices.dmsws.metadata.AttributeLink;
import ro.bithat.dms.microservices.dmsws.metadata.AttributeLinkList;
import ro.bithat.dms.microservices.dmsws.ps4.documents.DocObligatoriuExtra;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.ActNormativ;
import ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui.component.DocObligatoriuExtraTableContainer;
import ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui.component.ServiceRequestStepsFooterButtonController;
import ro.bithat.dms.microservices.portal.ecitizen.gui.BreadcrumbWizardRequestView;
import ro.bithat.dms.microservices.portal.ecitizen.gui.component.MobTableContainerDiv;
import ro.bithat.dms.microservices.portal.ecitizen.gui.component.TableContainerDiv;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.gui.Ps4ECitizenMyAccountRoute;
import ro.bithat.dms.passiveview.FlowComponent;
import ro.bithat.dms.passiveview.QueryParameterUtil;
import ro.bithat.dms.passiveview.VaadinClientUrlUtil;
import ro.bithat.dms.passiveview.boot.I18NProviderStatic;
import ro.bithat.dms.passiveview.i18n.MobTableFormI18n;
import ro.bithat.dms.passiveview.i18n.TableFormI18n;
import ro.bithat.dms.service.SpelParserUtil;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Ps4ECitizenServiceRequestView extends DocumentTypeView<Ps4ECitizenServiceRequestPresenter>   {


    @FlowComponent("request-wizard-breadcrumb")
    private BreadcrumbWizardRequestView breadcrumbRequestWizardView;

    private ServiceRequestStepsFooterButtonController btnFooterContainer = new ServiceRequestStepsFooterButtonController(this);

    private TableContainerDiv legislationDocumentsTable =
            new TableContainerDiv("document.type.service.request.view.service.index.label", "document.type.service.request.view.service.documentname.label", "document.type.service.request.view.service.documentdescription.label", "document.type.service.request.view.service.option.label");

    private MobTableContainerDiv mobLegislationDocumentsTable = new MobTableContainerDiv();


    private H2 legislationDocumentsTitle = new H2("document.type.service.request.view.legislationfiles.title.label");
    private Div divLegislationDocumentsTitle= new Div(legislationDocumentsTitle);
    private Div divLegislationDocumentsTitle2 = new Div(divLegislationDocumentsTitle);

    //Needed Document table

    private H2 serviceNeededDocumentsTitle = new H2("document.type.service.request.view.neededfiles.title.label");
    private Div divServiceNeededDocumentsTitle = new Div(serviceNeededDocumentsTitle);
    private Div divServiceNeededDocumentsTitle2 = new Div(divServiceNeededDocumentsTitle);


    private DocObligatoriuExtraTableContainer serviceNeededDocumentsContainer = new DocObligatoriuExtraTableContainer(this);

    //Needed Document table

    private Label personType = new Label();

    private Label serviceName = new Label();

    private Label serviceDescription = new Label();

    private Label serviceResponsible = new Label();

    private Label serviceAnswerTime = new Label();

    private Label serviceArchiveTime = new Label();

    private Label serviceWaitingTime = new Label();

    private Label serviceContact = new Label();

    private Label serviceWorkingProgram = new Label();

    private Label serviceCost = new Label();
    
    ////////////////////////////////////////////////////////////
    
//    private HtmlContainer costButton = new HtmlContainer(Tag.INPUT);
    
    private NativeButton costButton = new NativeButton("document.type.service.request.view.form.service.cost.calcul.text");
    
    private String serviceFormulaCostExpression = "";
    
    private Label areaLabel= new Label("document.type.service.request.view.form.service.area.text");

    private Label mainAreaLabel= new Label("document.type.service.request.view.form.service.cost.calcul.label");

    private TextField area = new TextField();

    private TextField areaAfisareCost = new TextField();

    private TextArea areaTestTextArea = new TextArea();

    private ComboBox<String> areaTestSelect = new ComboBox<>("", "50", "100");

    private DatePicker areaTestDatePicker = new DatePicker();

    private Div areaDiv = new Div(areaLabel, area);

    private Div areaLayout=  new Div(mainAreaLabel,areaDiv);

    private Div costButtonLayout = new Div(costButton);
    private HorizontalLayout hl = new HorizontalLayout(area);

    //private Div costDiv = new Div(areaLayout, costButtonLayout, areaAfisareCost, areaTestTextArea,areaTestSelect,areaTestDatePicker);
    //private Div costDiv = new Div(areaLayout, costButtonLayout);
    private Div costDiv = new Div(area, costButtonLayout);
    /////////////////////////////////////////////////////////////


    private TableFormI18n documentTypeServiceRequestForm = new TableFormI18n();

    private Div tableResponsive = new Div(documentTypeServiceRequestForm);

    private MobTableFormI18n mobDocumentTypeServiceRequestForm = new MobTableFormI18n();

    private AttributeLinkList propList = new AttributeLinkList();

    @Override
    public void beforeBinding() {
        getServiceListContainer().addClassNames("detaliu-serviciu", "solicitare-document");
        documentTypeServiceRequestForm.getElement().setAttribute("class", "table border-dark large-whitespace catalog_su");

        ///Btn footer container
        btnFooterContainer.registerPresenterNextStepMethod("document.type.service.request.view.confirm.action.label", "","-");
        ///Btn footer container
        tableResponsive.addClassNames("table-responsive", "table_date_contact");
        mobDocumentTypeServiceRequestForm.addClassNames("table_mob_1col", "table_mob_title_blue");
        Div clearFix = new Div();
        clearFix.addClassNames("clearfix", "gap-20");
        divServiceNeededDocumentsTitle.addClassName("service_title");
        divServiceNeededDocumentsTitle2.addClassNames("services_header", "float_left_width");
        divLegislationDocumentsTitle.addClassName("service_title");
        divLegislationDocumentsTitle2.addClassNames("services_header", "mt-3", "float_left_width");
        getServiceListContainer().add(tableResponsive, mobDocumentTypeServiceRequestForm,costDiv,divServiceNeededDocumentsTitle2,
                serviceNeededDocumentsContainer, divLegislationDocumentsTitle2, legislationDocumentsTable, mobLegislationDocumentsTable, clearFix, btnFooterContainer);

        buildDocumentTypeServiceRequestForm();
        

        styleTables();
    }


    public void buildCost() {

        //costDiv.addClassName("row");
        areaLayout.addClassNames("col-md-6", "col-lg-3");
        //costButtonLayout.addClassNames("col-md-6", "col-lg-3", "md-nom");
        costButtonLayout.addClassNames("btn-calcul", "md-nom");
        costButton.addClassNames("btn", "btn-secondary", "btn-block");

        area.setPlaceholder(I18NProviderStatic.getTranslation("document.type.service.request.view.form.service.area.text"));
        area.addClassNames("vaadin-ps4-theme", "form-control");
        area.getStyle().set("display", "flex");

        hl.addClassName("smart-form-control");
        hl.setWidthFull();
        areaDiv.addClassNames("input", "div_label");

        area.getElement().addEventListener("keyup" , e -> {
            if(!e.getEventData().getString("element.value").isEmpty()) {
                if(!areaDiv.hasClassName("show_label")) {
                	areaDiv.addClassNames("show_label");
                }
            } else {
                if(areaDiv.hasClassName("show_label")) {
                	areaDiv.removeClassNames("show_label");
                }
            }

        }).addEventData("element.value");

        costButton.addClickListener(e->{
        	String areaS = area.getValue();
        	if (areaS.contains(",") && !areaS.contains(".")) {
        		areaS = areaS.replace(",", ".");
        	}
        	Map<String, String> map = new HashMap<>();
        	map.put("30826", areaS);
        	map.put("SUPRAFATA_IMOBIL", areaS);
        	String expression = "new Double(context['30826'])<=150?5d:(new Double(context['30826'])<=250?6d:(new Double(context['30826'])<=500?8d:(new Double(context['30826'])<=750?10d:(new Double(context['30826'])<=1000?12d: (14d+(new Double(context['30826'])-1000d)*0.01d)))))";
        	if (serviceFormulaCostExpression == null) {
        		expression = serviceFormulaCostExpression;
        	}
        	Double cost = (Double)SpelParserUtil.parseSpel(map, expression);
        	if (cost == null) {
        		UI.getCurrent().getPage().executeJs("swalError($0)", "Valoarea suprafetei imobilului este invalida!");
        	}else {
	        	String result = String.format("%.2f", cost);
	        	UI.getCurrent().getPage().executeJs("swalInfo($0)",
	                    "Costul total va fi de " + result+" lei.");
	        	}
        
        });
    }
    
    public void setServiceNameAndRegisterPreviousStep(String serviceName) {
        btnFooterContainer.registerPresenterPreviousStepMethod("document.type.service.request.view.back.action.label", "document.type.service.request.previous.step.title", serviceName);
    }

    private void buildDocumentTypeServiceRequestForm() {
        getPresenter().getProp();
        if (propList.getAttributeLink() != null && propList.getAttributeLink().size() > 0) {
            for (AttributeLink a : propList.getAttributeLink()) {
                Label value = new Label();
                value.setText(a.getValue());
                buildDocumentTypeServiceRequestForm(value, a.getName());
            }
        } else {
            buildDocumentTypeServiceRequestForm(personType, "document.type.service.request.view.form.persontype.label");
            buildDocumentTypeServiceRequestForm(serviceName, "document.type.service.request.view.form.service.name.label");
            buildDocumentTypeServiceRequestForm(serviceDescription, "document.type.service.request.view.form.service.description.label");
            buildDocumentTypeServiceRequestForm(serviceResponsible, "document.type.service.request.view.form.service.responsible.label");
            buildDocumentTypeServiceRequestForm(serviceAnswerTime, "document.type.service.request.view.form.service.answertime.label");
            //buildDocumentTypeServiceRequestForm(serviceArchiveTime, "document.type.service.request.view.form.service.archivetime.label");
            buildDocumentTypeServiceRequestForm(serviceWaitingTime, "document.type.service.request.view.form.service.waitingtime.label");
            buildDocumentTypeServiceRequestForm(serviceContact, "document.type.service.request.view.form.service.contact.label");
            buildDocumentTypeServiceRequestForm(serviceWorkingProgram, "document.type.service.request.view.form.service.workingprogram.label");
            buildDocumentTypeServiceRequestForm(serviceCost, "document.type.service.request.view.form.service.cost.label");

//        buildDocumentTypeServiceRequestForm(costDiv, "document.type.service.request.view.form.service.cost.calcul.label");
        }
    }

    private void buildDocumentTypeServiceRequestForm(Component component, String label) {
        documentTypeServiceRequestForm.addFormRow(component, label);
        mobDocumentTypeServiceRequestForm.addFormRow(component, label);
    }

    private void styleTables() {
        mobLegislationDocumentsTable.addClassNames("table_mob_2col", "table_date_contact", "first_td_bg");
        legislationDocumentsTable.addClassNames("table-responsive", "table_anre");
        legislationDocumentsTable.setTableClassNames("table has-buttons");
        legislationDocumentsTable.setTableHeaderClassNames("last_th_100");

    }


    public void setPersonTypeValue(String personTypeValue) {
        personType.setText(personTypeValue);
    }

    public void setServiceNameValue(String serviceNameValue) {
        serviceName.setText(serviceNameValue);
    }

    public void setServiceDescriptionValue(String serviceDescriptionValue) {
        serviceDescription.setText(serviceDescriptionValue);
    }

    public void setServiceResponsibleValue(String serviceResponsibleValue) {
        serviceResponsible.setText(serviceResponsibleValue);
    }

    public void setServiceAnswerTimeValue(String serviceAnswerTimeValue) {
        serviceAnswerTime.setText(serviceAnswerTimeValue);
    }

    public void setServiceArchiveTimeValue(String serviceArchiveTimeValue) {
        serviceArchiveTime.setText(serviceArchiveTimeValue);
    }

    public void setServiceWaitingTimeValue(String serviceWaitingTimeValue) {
        serviceWaitingTime.setText(serviceWaitingTimeValue);
    }

    public void setServiceContactValue(String serviceContactValue) {
        serviceContact.setText(serviceContactValue);
    }

    public void setServiceWorkingProgramValue(String serviceWorkingProgramValue) {
        serviceWorkingProgram.setText(serviceWorkingProgramValue);
    }

    public void setServiceCostValue(String serviceCostValue) {
        serviceCost.setText(serviceCostValue);
    }

    public void setNeededDocumentsTable(List<DocObligatoriuExtra> documenteObligatoriiServiciu) {
        serviceNeededDocumentsContainer.setNeededDocumentsTable(documenteObligatoriiServiciu, false);
    }

    public void hideNeededDocuments(){
        getServiceListContainer().remove(serviceNeededDocumentsContainer);
        divServiceNeededDocumentsTitle.setVisible(false);
    }
    public void setLegislationDocumentsTable(List<ActNormativ> acteNormativeByDocTypeId) {
        AtomicInteger index = new AtomicInteger(1);
        acteNormativeByDocTypeId.stream().forEach(legislationDocument -> setLegislationDocumentTableRow(legislationDocument, index));
    }

    private void setLegislationDocumentTableRow(ActNormativ actNormativ, AtomicInteger index) {
        Anchor visualisation = new Anchor();
        visualisation.addClassNames("btn", "btn-primary", "btn-secondary-hover", "btn-xsm-s", "btn_tooltip", "tooltip_chat");
        HtmlContainer visualisationIcon = new HtmlContainer("i");
        visualisationIcon.addClassNames("fas", "fa-eye");
        Span spanVisualisationIcon = new Span("document.type.service.request.view.legislationfiles.service.option.label");
        spanVisualisationIcon.addClassNames("tooltiptext", "width110");
        visualisationIcon.add(spanVisualisationIcon);
        visualisation.add(visualisationIcon);
        // visualisation.add(visualisationIcon, new Text("document.type.service.request.view.legislationfiles.service.option.label"));
        if(Optional.ofNullable(actNormativ.getLinkFisier()).isPresent() &&
                !actNormativ.getLinkFisier().isEmpty()) {
            visualisation.setHref(actNormativ.getLinkFisier());
            visualisation.setTarget("_blank");
        }
        else{
            visualisation.setHref("javascript:void(0);");

        }
        Div  optionsLayout = new Div(visualisation);
        Integer indexInt= index.getAndIncrement();
        legislationDocumentsTable.addRow(new Label(indexInt + ""),
                new Label(actNormativ.getDenumire()),
                new Label(actNormativ.getDescriere()),
                optionsLayout);
        Map<String, Component> mobileRowMap = new LinkedHashMap<>();
        mobileRowMap.put("document.type.service.request.view.service.index.label", new Label(indexInt+ ""));
        mobileRowMap.put("document.type.service.request.view.service.documentname.label", new Label(actNormativ.getDenumire()));
        mobileRowMap.put("document.type.service.request.view.service.documentdescription.label", new Label(actNormativ.getDescriere()));
        Anchor mobVisualisation = new Anchor();
        mobVisualisation.addClassNames("btn", "btn-primary", "btn-secondary-hover", "btn-sm");
        HtmlContainer mobVisualisationIcon = new HtmlContainer("i");
        mobVisualisationIcon.addClassNames("fas", "fa-eye");
        mobVisualisation.add(mobVisualisationIcon, new Text("document.type.service.request.view.legislationfiles.service.option.label"));
        if(Optional.ofNullable(actNormativ.getLinkFisier()).isPresent() &&
                !actNormativ.getLinkFisier().isEmpty()) {
            mobVisualisation.setHref(actNormativ.getLinkFisier());
            mobVisualisation.setTarget("_blank");
        }
        Div  mobOptionsLayout = new Div(mobVisualisation);

        mobileRowMap.put("document.type.service.request.view.service.option.label", mobOptionsLayout);
        mobLegislationDocumentsTable.addRow(mobileRowMap);

    }

    protected void displayForWidth(int width) {
        if(width <= 700) {
            mobDocumentTypeServiceRequestForm.getStyle().remove("display");
            tableResponsive.getStyle().set("display", "none");
            mobDocumentTypeServiceRequestForm.buildTableBody();
            mobLegislationDocumentsTable.getStyle().remove("display");
            legislationDocumentsTable.getStyle().set("display", "none");
            serviceNeededDocumentsContainer.displayForMobile();
        } else {
            if(tableResponsive.getStyle().has("display")) {
                tableResponsive.getStyle().remove("display");
            }
            mobDocumentTypeServiceRequestForm.getStyle().set("display", "none");
            documentTypeServiceRequestForm.buildTableBody();
            if(legislationDocumentsTable.getStyle().has("display")) {
                legislationDocumentsTable.getStyle().remove("display");
            }
            mobLegislationDocumentsTable.getStyle().set("display", "none");
            serviceNeededDocumentsContainer.displayForDesktop();
        }
    }


	public void setServiceFormulaCost(String serviceFormulaCost) {
		//DACA ESTE SETAT FORMULA PE TIP DE DOCUMENT
        if (serviceFormulaCost != null && !serviceFormulaCost.isEmpty() && serviceFormulaCost.contains("SUPRAFATA_IMOBIL")) {
			serviceFormulaCostExpression = serviceFormulaCost;
			//buildDocumentTypeServiceRequestForm(costDiv, "document.type.service.request.view.form.service.cost.calcul.label");

                buildCost();

		}
		//ALTFEL -> ASCUNDEM BUTONUL DE CALCUL
		else{
            getServiceListContainer().remove(costDiv);
        }
		
	}

	public void setNotesTable(){

            serviceNeededDocumentsContainer.addNotes("Nota: Completarea parțială a documentelor de mai sus atrage după sine respingerea solicitării.");
    }

    public void setPropList(AttributeLinkList proprietati){

        propList.setAttributeLink(proprietati.getAttributeLink());
    }
    public void buildBreadcrumbsWizard() {
        getServiceListContainer().getStyle().set("margin-top","150px");
        breadcrumbRequestWizardView.setCurrentPageActive( RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenServiceRequestRoute.class));
        addComponentAsFirst(breadcrumbRequestWizardView);
    }

    @ClientCallable
    public void confirmInfoAck() {
        Map<String, Object> filterPageParameters = new HashMap<>();
        filterPageParameters.put("tipDocument", getPresenter().getDocumentType());
        filterPageParameters.put("document", getPresenter().getDocumentId().get());
        VaadinClientUrlUtil.setLocation(QueryParameterUtil
                .getRelativePathWithQueryParameters(filterPageParameters, Ps4ECitizenServiceNewRequestRoute.class));
    }
    @ClientCallable
    public void rejectInfoAck() {
        if(getPresenter().getDocument().isPresent() && getPresenter().getDocument().get().getPortalRedirectNoButton()!=null && !getPresenter().getDocument().get().getPortalRedirectNoButton().isEmpty()){
            VaadinClientUrlUtil.setLocation(getPresenter().getDocument().get().getPortalRedirectNoButton());
        }else{
            VaadinClientUrlUtil.setLocation(RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenMyAccountRoute.class));
        }
    }
}


