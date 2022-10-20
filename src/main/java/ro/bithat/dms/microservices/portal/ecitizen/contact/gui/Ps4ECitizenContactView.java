package ro.bithat.dms.microservices.portal.ecitizen.contact.gui;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import ro.bithat.dms.passiveview.component.view.DivFlowViewBuilder;
import ro.bithat.dms.service.StreamToStringUtil;

import java.util.UUID;

public class Ps4ECitizenContactView extends DivFlowViewBuilder<Ps4ECitizenContactPresenter> {

//    private ClickNotifierAnchor anchorContainer = new ClickNotifierAnchor();
    private Div buttonContainer = new Div();

    private TextField fullName = new TextField();

    private TextField senderEmail = new TextField();

    private TextField phone = new TextField();

    private TextField subject = new TextField();

    private TextArea message = new TextArea();

    private Div formContainer = new Div();

    private Div contactMap = new Div();

    @Override
    protected void buildView() {
        addClassName("contact_page");
        removeClassName("container");
        add(contactMap, formContainer);
        contactMap.setId("contact-map");
        contactMap.getElement().setProperty("innerHTML",
                StreamToStringUtil.fileToString("static/PORTAL/google_map.html"));
        formContainer.addClassNames("container", "bold_label");
        buildFormGroupFor(fullName, "ps4.ecetatean.contact.form.fullname");
        buildFormGroupFor(senderEmail, "ps4.ecetatean.contact.form.senderemail");
        buildFormGroupFor(phone, "ps4.ecetatean.contact.form.phone");
        buildFormGroupFor(subject, "ps4.ecetatean.contact.form.subject");
        buildFormGroupFor(message, "ps4.ecetatean.contact.form.message");
        styleContactButton();
        formContainer.add(buttonContainer);
        UI.getCurrent().getPage().addJavaScript("frontend/js/gmap.js");
        UI.getCurrent().getPage().executeJs("loadAfterMapElement($0)", this.getElement());
    }

    @ClientCallable
    public void loadGmapApi() {
        UI.getCurrent().getPage().addJavaScript("https://maps.googleapis.com/maps/api/js?key=" + getPresenter().getGoogleMapsApiKey() + "&language=ro&region=RO&callback=initMap");
    }

    private void buildFormGroupFor(Component inputComponent, String i18nLabelValue)  {
        Label inputLabel = new Label(i18nLabelValue);
        inputComponent.setId(UUID.randomUUID().toString());
        ((HasValue)inputComponent).addValueChangeListener( e -> getPresenter().validateContactForm());
        ((HasStyle)inputComponent).addClassNames("vaadin-ps4-theme");
        if(TextField.class.isAssignableFrom(inputComponent.getClass())) {
            ((TextField) inputComponent).addThemeVariants(TextFieldVariant.LUMO_SMALL);
        }
        ((HasSize)inputComponent).setWidthFull();
        HorizontalLayout formControl = new HorizontalLayout(inputComponent);
        formControl.addClassName("form-control");
        inputLabel.setFor(inputComponent);
        Div formGroup = new Div(inputLabel, formControl);
        formGroup.addClassName("form-group");
        formContainer.add(formGroup);
    }

    private void styleContactButton() {
        buttonContainer.addClassNames("new_request");

        buttonContainer.addClassNames("btn","btn-secondary","btn-block","font-weight-bold");
        buttonContainer.add(new Text("ps4.ecetatean.contact.form.button.label"));
        HtmlContainer iconArrowNext= new HtmlContainer("i");
        iconArrowNext.addClassNames("fas","fa-arrow-alt-circle-right");
        buttonContainer.add(iconArrowNext);



    }


    public TextField getFullName() {
        return fullName;
    }

    public TextField getSenderEmail() {
        return senderEmail;
    }

    public TextField getPhone() {
        return phone;
    }

    public TextField getSubject() {
        return subject;
    }

    public TextArea getMessage() {
        return message;
    }
}
