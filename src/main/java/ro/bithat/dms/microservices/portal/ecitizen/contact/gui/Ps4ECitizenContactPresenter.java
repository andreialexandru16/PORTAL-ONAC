package ro.bithat.dms.microservices.portal.ecitizen.contact.gui;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.validator.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import ro.bithat.dms.microservices.portal.ecitizen.contact.backend.ContactService;
import ro.bithat.dms.microservices.portal.ecitizen.contact.backend.ContactForm;
import ro.bithat.dms.passiveview.ClickEventPresenterMethod;
import ro.bithat.dms.passiveview.boot.I18NProviderStatic;
import ro.bithat.dms.passiveview.component.html.ClickNotifierAnchor;
import ro.bithat.dms.passiveview.component.presenter.PrepareModelFlowPresenter;
import ro.bithat.dms.security.SecurityUtils;
import ro.bithat.dms.smartform.gui.attribute.binder.RomanianPhoneNumberValidator;

public class Ps4ECitizenContactPresenter extends PrepareModelFlowPresenter<Ps4ECitizenContactView> {


    @Value("${google.maps.api.key}")
    private String googleMapsApiKey;

    @Value("${ps4.ecitizen.contact.email}")
    private String contactToAddress;

    @Autowired
    private ContactService contactService;

    private BeanValidationBinder<ContactForm> contactFormBinder = new BeanValidationBinder<>(ContactForm.class);

    private ContactForm contactForm = new ContactForm();

    public String getGoogleMapsApiKey() {
        return googleMapsApiKey;
    }

    @Override
    public void prepareModel(String state) {
        contactFormBinder.setBean(contactForm);
        contactFormBinder.forField(getView().getFullName())
                .bind(ContactForm::getFullName, ContactForm::setFullName);
        contactFormBinder.forField(getView().getSenderEmail())
                .withValidator(new EmailValidator("Email invalid"))
                .bind(ContactForm::getSenderEmail, ContactForm::setSenderEmail);
        contactFormBinder.forField(getView().getPhone())
                .withValidator(new RomanianPhoneNumberValidator("Numar de telefon invalid"))
                .bind(ContactForm::getPhone, ContactForm::setPhone);
        contactFormBinder.forField(getView().getSubject())
                .bind(ContactForm::getSubject, ContactForm::setSubject);
        contactFormBinder.forField(getView().getMessage())
                .bind(ContactForm::getMessage, ContactForm::setMessage);

    }


    @ClickEventPresenterMethod(viewProperty = "buttonContainer")
    public void onSendContactForm(ClickEvent<ClickNotifierAnchor> clickEvent) {
        getLogger().info("send mail from:\t "+ contactForm.getSenderEmail() + "\twith subject:\t"+contactForm.getSubject());
        if(contactFormBinder.validate().isOk()) {
            contactService.sendEmail(SecurityUtils.getToken(), contactToAddress, contactForm.getSenderEmail(),
                    contactForm.getSubject(),
                            "Nume și prenume:\t" + contactForm.getFullName()
                                    + "\r\nTelefon:\t" + contactForm.getPhone()
                                    + "\r\nEmail:\t" + contactForm.getSenderEmail()
                                    + "\r\nMesaj:\r\n" + contactForm.getMessage());
            UI.getCurrent().getPage().executeJs("swalInfo($0)",
                    I18NProviderStatic.getTranslation("ps4.ecetatean.contact.form.send.swal.info"));
            contactForm = new ContactForm();
            contactFormBinder.setBean(contactForm);
            return;
        }
        UI.getCurrent().getPage().executeJs("swalError($0)", I18NProviderStatic.getTranslation("ps4.ecetatean.form.swal.error"));
    }

    public void validateContactForm() {
        contactFormBinder.validate();
    }
}
