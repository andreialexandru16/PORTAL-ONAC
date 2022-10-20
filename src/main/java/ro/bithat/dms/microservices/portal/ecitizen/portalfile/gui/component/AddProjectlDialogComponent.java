package ro.bithat.dms.microservices.portal.ecitizen.portalfile.gui.component;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import ro.bithat.dms.microservices.portal.ecitizen.gui.component.FlowViewDivContainer;
import ro.bithat.dms.passiveview.boot.I18NProviderStatic;
import ro.bithat.dms.passiveview.mvp.FlowView;
import ro.bithat.dms.smartform.gui.attribute.component.AddrMapComponent;

import java.util.HashMap;
import java.util.Map;

public class AddProjectlDialogComponent extends FlowViewDivContainer {

    private final static String PRESENTER_ADD_PROJECT_ACTION = "onAddProjectBtnClick";

    private TextField textFieldNr = new TextField();

    private HorizontalLayout layoutNr = new HorizontalLayout(textFieldNr);

    private Div layoutContainerNr = new Div(layoutNr);

    private Label labelNr = new Label("ps4.ecetatean.breadcrumb.portalfile.urban.documents.nr");

    private Div containerNr= new Div(labelNr, layoutContainerNr);

    private Map<String, com.vaadin.flow.component.Component> componentMap = new HashMap<String, com.vaadin.flow.component.Component>();

    private TextField textFieldAddress = new TextField();

    HtmlContainer iconMap = new HtmlContainer("i");

    private com.vaadin.flow.component.button.Button buttonAddress= new com.vaadin.flow.component.button.Button(iconMap);

    private HorizontalLayout layoutAddress = new HorizontalLayout();

    private Div layoutContainerAddress = new Div(layoutAddress);

    private Label labelAddress = new Label("ps4.ecetatean.breadcrumb.portalfile.urban.certificates.address");


    private Div containerAddress= new Div(labelAddress, layoutContainerAddress);

    private TextField textFieldRequesterFirstName = new TextField();

    private HorizontalLayout layoutRequesterFirstName = new HorizontalLayout(textFieldRequesterFirstName);

    private Div layoutContainerRequesterFirstName = new Div(layoutRequesterFirstName);

    private Label labelRequesterFirstName = new Label("ps4.ecetatean.breadcrumb.portalfile.urban.certificates.requester.firstname");

    private Div containerRequesterFirstName= new Div(labelRequesterFirstName, layoutContainerRequesterFirstName);

    private TextField textFieldRequesterLastName = new TextField();

    private HorizontalLayout layoutRequesterLastName = new HorizontalLayout(textFieldRequesterLastName);

    private Div layoutContainerRequesterLastName = new Div(layoutRequesterLastName);

    private Label labelRequesterLastName = new Label("ps4.ecetatean.breadcrumb.portalfile.urban.certificates.requester.lastname");

    private Div containerRequesterLastName= new Div(labelRequesterLastName, layoutContainerRequesterLastName);

    private TextField textFieldType = new TextField();

    private HorizontalLayout layoutType = new HorizontalLayout(textFieldType);

    private Div layoutContainerType = new Div(layoutType);

    private Label labelType = new Label("ps4.ecetatean.breadcrumb.portalfile.urban.certificates.type");

    private Div containerType= new Div(labelType, layoutContainerType);


    private Div modalBody = new Div(containerNr,containerAddress,containerRequesterFirstName,containerRequesterLastName,containerType);

    private Div nameTop = new Div();

    private Div modalName = new Div(nameTop);

    private Div modalTop = new Div(modalName);

    private NativeButton closeBtn = new NativeButton();

    private Div closeModal = new Div(closeBtn);

    private Div modalHeader = new Div(closeModal, modalTop);

    private NativeButton addProjectBtn= new NativeButton();

    private Div btnContainer= new Div(addProjectBtn);

    private Div modalFooter= new Div(btnContainer);

    private Div modalContent = new Div(modalHeader, modalBody,modalFooter);

    private Div modalDialog = new Div(modalContent);



    public AddProjectlDialogComponent(FlowView view) {
        super(view);
        addClassNames("modal", "fade");
        getElement().setAttribute("tabindex", "-1");
        getElement().setAttribute("role", "dialog");
        getElement().setAttribute("aria-hidden", "true");
        getElement().setAttribute("aria-labelledby", "exampleModalLabel");
//        getStyle().set("display", "block");
        add(modalDialog);

        modalDialog.addClassName("modal-dialog");
        modalDialog.getElement().setAttribute("role", "document");
        modalContent.addClassName("modal-content");
        modalFooter.addClassName("modal-footer");

        modalHeader.addClassName("modal-header");
        modalBody.addClassName("modal-body");
        closeModal.addClassName("close_modal");
        modalTop.addClassName("modal_top");
        modalTop.getStyle().set("padding","0");
        closeBtn.addClassName("close");
        closeBtn.getElement().setAttribute("data-dismiss", "modal");
        closeBtn.getElement().setAttribute("aria-label", "Close");
        closeBtn.setId("closeBtn");
        Span closeBtnSpan = new Span();
        closeBtnSpan.getElement().setProperty("innerHTML", "&times;");
        closeBtn.add(closeBtnSpan);

        modalName.addClassName("modal_name");
        nameTop.addClassName("name");
        nameTop.setText("ps4.ecetatean.breadcrumb.portalfile.urban.documents.add.title");

        addProjectBtn.setText(I18NProviderStatic.getTranslation("ps4.ecetatean.breadcrumb.portalfile.urban.documents.add"));
        //addProjectBtn.setType("button");
        addProjectBtn.addClassNames("btn", "btn-secondary", "col-sm-12", "col-md-12", "col-lg-12");


        btnContainer.addClassNames("form-group","row");


        String addrBoxId = "addrMapAddrBox";
        String addrModalId = "addrMapModal";

        textFieldAddress.setId(addrBoxId);

        buttonAddress.getElement().setAttribute("data-toggle", "modal");
        buttonAddress.getElement().setAttribute("data-target", "#" + addrModalId);

        AddrMapComponent addrMapComponent = new AddrMapComponent();
        addrMapComponent.setId("addrMapModal");
        componentMap.put(addrBoxId, textFieldAddress);
        UI.getCurrent().getPage().executeJs("initBootstrapOnShowFunction('" + addrBoxId + "','" + addrModalId + "', $0, $1)", this, textFieldAddress);

        add(addrMapComponent);
        layoutAddress.add(textFieldAddress,buttonAddress);

        styleRows();
    }

    private void styleRows() {

        containerNr.addClassNames("form-group","row","no-gutters");
        labelNr.addClassNames("col-sm-6","col-md-5","col-lg-4");
        labelNr.getStyle().set("font-weight","600");
        layoutContainerNr.addClassNames("col-sm-6","col-md-7","col-lg-8","search_container");
        layoutNr.addClassName("form-control");
        layoutNr.getStyle().set("justify-content","center");
        layoutNr.getStyle().set("align-items","center");
        textFieldNr.addClassName("vaadin-ps4-theme");
        textFieldNr.getStyle().set("width","100%");

        containerAddress.addClassNames("form-group","row","no-gutters");
        labelAddress.addClassNames("col-sm-6","col-md-5","col-lg-4");
        labelAddress.getStyle().set("font-weight","600");
        layoutContainerAddress.addClassNames("col-sm-6","col-md-7","col-lg-8","search_container");
        layoutAddress.addClassName("form-control");
        layoutAddress.getStyle().set("justify-content","center");
        layoutAddress.getStyle().set("align-items","center");
        textFieldAddress.addClassName("vaadin-ps4-theme");
        textFieldAddress.getStyle().set("min-width","70%");
        textFieldAddress.getElement().setAttribute("readonly"," ");
        iconMap.addClassNames("fa","fa-map");
        buttonAddress.getStyle().set("float","right");

        containerRequesterLastName.addClassNames("form-group","row","no-gutters");
        labelRequesterLastName.addClassNames("col-sm-6","col-md-5","col-lg-4");
        labelRequesterLastName.getStyle().set("font-weight","600");
        layoutContainerRequesterLastName.addClassNames("col-sm-6","col-md-7","col-lg-8","search_container");
        layoutRequesterLastName.addClassName("form-control");
        layoutRequesterLastName.getStyle().set("justify-content","center");
        layoutRequesterLastName.getStyle().set("align-items","center");
        textFieldRequesterLastName.addClassName("vaadin-ps4-theme");
        textFieldRequesterLastName.getStyle().set("width","100%");


        containerRequesterFirstName.addClassNames("form-group","row","no-gutters");
        labelRequesterFirstName.addClassNames("col-sm-6","col-md-5","col-lg-4");
        labelRequesterFirstName.getStyle().set("font-weight","600");
        layoutContainerRequesterFirstName.addClassNames("col-sm-6","col-md-7","col-lg-8","search_container");
        layoutRequesterFirstName.addClassName("form-control");
        layoutRequesterFirstName.getStyle().set("justify-content","center");
        layoutRequesterFirstName.getStyle().set("align-items","center");
        textFieldRequesterFirstName.addClassName("vaadin-ps4-theme");
        textFieldRequesterFirstName.getStyle().set("width","100%");

        containerType.addClassNames("form-group","row","no-gutters");
        labelType.addClassNames("col-sm-6","col-md-5","col-lg-4");
        labelType.getStyle().set("font-weight","600");
        layoutContainerType.addClassNames("col-sm-6","col-md-7","col-lg-8","search_container");
        layoutType.addClassName("form-control");
        layoutType.getStyle().set("justify-content","center");
        layoutType.getStyle().set("align-items","center");
        textFieldType.addClassName("vaadin-ps4-theme");
        textFieldType.getStyle().set("width","100%");



    }
    @ClientCallable
    public void saveAdresa(String id, String adresa) {
        com.vaadin.flow.component.Component component = componentMap.get(id);
        if (component != null) {
            if (TextField.class.isAssignableFrom(component.getClass())) {
                ((TextField) component).setValue(adresa);
            }
        }
    }
    public void setClickEventAddProject(){
        registerClickEvent(PRESENTER_ADD_PROJECT_ACTION, addProjectBtn, textFieldNr,textFieldAddress,textFieldRequesterLastName,textFieldRequesterFirstName,textFieldType,this);

    }
    public void closeBtnClick(){
        UI.getCurrent().getPage().executeJs("$('#closeBtn').click()");


    }
}
