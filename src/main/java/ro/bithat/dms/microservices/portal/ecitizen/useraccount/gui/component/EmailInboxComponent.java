package ro.bithat.dms.microservices.portal.ecitizen.useraccount.gui.component;

import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import ro.bithat.dms.microservices.dmsws.email.Email;
import ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui.Ps4ECitizenServiceRequestReviewRoute;
import ro.bithat.dms.microservices.portal.ecitizen.gui.component.FlowViewDivContainer;
import ro.bithat.dms.passiveview.QueryParameterUtil;
import ro.bithat.dms.passiveview.VaadinClientUrlUtil;
import ro.bithat.dms.passiveview.boot.I18NProviderStatic;
import ro.bithat.dms.passiveview.component.html.ClickNotifierAnchor;
import ro.bithat.dms.passiveview.i18n.flow.InternationalizeViewEngine;
import ro.bithat.dms.passiveview.mvp.FlowView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class EmailInboxComponent extends FlowViewDivContainer {


    private final static String PRESENTER_SEARCH_BTN_ACTION = "onInboxSearchBtnClick";

    private final static String PRESENTER_SEARCH_TEXT_ACTION = "onInboxSearchTextChanged";

    private final static String PRESENTER_RESPOND_BTN_ACTION = "onRespondBtnClick";

    private final static String PRESENTER_SHOW_EMAIL_ACTION = "onShowEmailBtnClick";

    private Div divContainerBifeTip = new Div();
    private Div divContainerBifaPrimit = new Div();
    private Div divContainerBifaTrimis = new Div();
    private Div divContainerBifaToate = new Div();
    private Boolean enableSearch = true;

//    private EmailDialogComponent emailDialogComponent;

    private Div searchContainer= new Div();

    TextField inputSearch= new TextField();
    NativeButton buttonSearch=new NativeButton();


    public EmailInboxComponent(FlowView view) {
        super(view);
//        emailDialogComponent = new EmailDialogComponent(view);
        addClassName("profile_inbox");
        searchContainer.addClassName("search_form");
        setSearchContainer();


    }

    private void setTitleContainer() {

        Div inboxTitle = new Div();
        inboxTitle.addClassName("box_title");
        H2 hInboxTitle = new H2("ps4.ecetatean.breadcrumb.myaccount.page.inbox.title");
        inboxTitle.add(hInboxTitle);
       // setBifeContainer();
        //inboxTitle.add(divContainerBifeTip);
        add(inboxTitle);

    }

    private void setBifeContainer() {
        divContainerBifeTip.addClassName("box_messages");
        divContainerBifeTip.add(divContainerBifaToate,divContainerBifaPrimit,divContainerBifaTrimis);
        setContainerBifa(divContainerBifaPrimit, "inbox.bifa.primit","primit");
        setContainerBifa(divContainerBifaToate, "inbox.bifa.toate","toate");
        setContainerBifa(divContainerBifaTrimis, "inbox.bifa.trimis","trimis");

    }

    private void setContainerBifa(Div divContainerBifa, String label, String id) {
    divContainerBifa.addClassNames("float_left");
    Label labelComp= new Label(label);
    Checkbox component = new Checkbox();
    component.setId("bifa_"+id);
    Div divIcon= new Div();
    divIcon.addClassNames("icn_checkbox");
    divContainerBifa.add(labelComp, component,divIcon);
    component.addClickListener(e -> onCheckBifaTip(component));
    }

    private void onCheckBifaTip(Checkbox component) {

    }


    public void setEnableSearch(Boolean enableSearch) {
        this.enableSearch = enableSearch;
    }

    public void i18nInboxContainer(){
        InternationalizeViewEngine.internationalize(this);
    }


    public void setInboxTable(List<Email> myMessages) {

 /*TITLE*/
        removeAll();
        if(enableSearch) {
            add(searchContainer);
            registerClickEvent(PRESENTER_SEARCH_BTN_ACTION, buttonSearch);
            registerComponentValueChangeEventEvent(PRESENTER_SEARCH_TEXT_ACTION, inputSearch);
        }
        if(myMessages.size() > 0) {
            setTitleContainer();

            Integer index = 0;
            for (Email message : myMessages) {
                index++;
                Div messageContainer = new Div();
                Div messageContainerEnvelope = new Div();
                Div messageContainerHeader = new Div();
                Div messageContainerSubject = new Div();
                Div messageContainerHeaderName = new Div();
                Div messageContainerHeaderRightItems = new Div();
                messageContainer.addClassName("message");

                if (message.getCitit() == null || message.getCitit().toString().equals("0")) {
                    messageContainer.addClassName("unread");
                }
                messageContainerEnvelope.addClassName("envelope");
                HtmlContainer iconMessageEnvelope = new HtmlContainer("i");
                messageContainerEnvelope.add(iconMessageEnvelope);
                messageContainer.add(messageContainerEnvelope);
                messageContainerEnvelope.getStyle().set("cursor", "pointer");
                EmailDialogComponent emailDialogComponent = new EmailDialogComponent(getView());
                emailDialogComponent.setEmail(message);
                emailDialogComponent.setId("emailModal"+ index);
                messageContainerEnvelope.getElement().setAttribute("data-toggle", "modal");
                messageContainerEnvelope.getElement().setAttribute("data-target", "#emailModal" + index);
                registerClickEvent(PRESENTER_SHOW_EMAIL_ACTION, messageContainerEnvelope,iconMessageEnvelope, message,messageContainer);

//                messageContainerEnvelope.addClickListener(e
//                        -> emailDialogComponent.show(message, messageContainer));

                if (message.getCitit() == null || message.getCitit().toString().equals("0")) {
                    messageContainer.addClassName("unread");
                    iconMessageEnvelope.addClassNames("fas", "fa-envelope");

                } else {
                    iconMessageEnvelope.addClassNames("fas", "fa-envelope-open");

                }

                messageContainerHeader.addClassNames("header", "clearfix");
                messageContainerHeaderName.addClassNames("name", "btn_modal");
                messageContainerHeaderName.add(new Span("ps4.ecetatean.breadcrumb.myaccount.page.inbox.message.sendby"));
                messageContainerHeaderName.add(new Text(message.getCreatDe()));
                messageContainerHeaderName.getStyle().set("cursor", "pointer");
                messageContainerHeaderName.getElement().setAttribute("data-toggle", "modal");
                messageContainerHeaderName.getElement().setAttribute("data-target", "#emailModal" + index);
                registerClickEvent(PRESENTER_SHOW_EMAIL_ACTION, messageContainerHeaderName, iconMessageEnvelope,message,messageContainer);

//                messageContainerHeaderName.addClickListener(e
//                        -> emailDialogComponent.show(message, messageContainer));

                messageContainerHeaderRightItems.addClassName("right_items");

                ClickNotifierAnchor anchorReply = new ClickNotifierAnchor();
                HtmlContainer iconMessageReply = new HtmlContainer("i");
                iconMessageReply.addClassNames("fas", "fa-reply");
                anchorReply.add(iconMessageReply);
                anchorReply.addClickListener(e -> addOrRemoveReplyClass(messageContainer));

/*

                ClickNotifierAnchor anchorReplyAll = new ClickNotifierAnchor();
                HtmlContainer iconMessageReplyAll = new HtmlContainer("i");
                iconMessageReplyAll.addClassNames("fas", "fa-reply-all");
                anchorReplyAll.add(iconMessageReplyAll);
*/

                Div messageContainerHeaderRightItemsDate = new Div();
                messageContainerHeaderRightItemsDate.addClassName("date");
                messageContainerHeaderRightItemsDate.setText(message.getCreatLa());
                messageContainerHeaderRightItems.add(anchorReply, messageContainerHeaderRightItemsDate);

                messageContainerHeader.add(messageContainerSubject, messageContainerHeaderRightItems);
                messageContainerSubject.addClassName("subject");
                messageContainerSubject.add(new Text("ps4.ecetatean.breadcrumb.myaccount.page.inbox.message.subject"));
                messageContainerSubject.add(new Text(message.getTitlu()));
                if (message.getDenumireFisier() != null && !message.getDenumireFisier().isEmpty()) {
                    HtmlContainer iconDocument = new HtmlContainer("i");
                    ClickNotifierAnchor requestLink = new ClickNotifierAnchor();
                    iconDocument.addClassNames("fas", "fa-eye");
                    Div documentDetail = new Div(iconDocument, requestLink);
                    requestLink.setText(I18NProviderStatic.getTranslation("ps4.ecetatean.breadcrumb.myaccount.page.inbox.message.request", message.getDenumireFisier()));
                    if (Optional.ofNullable(message.getIdDocument()).isPresent() && Optional.ofNullable(message.getIdClasaDocument()).isPresent() && Optional.ofNullable(message.getIdFisier()).isPresent()) {
                        Map<String, Object> filterPageParameters = new HashMap<>();
                        filterPageParameters.put("tipDocument", message.getIdClasaDocument());
                        filterPageParameters.put("document", message.getIdDocument());
                        filterPageParameters.put("request", message.getIdFisier());
                        requestLink.getStyle().set("cursor", "pointer");
                        requestLink.addClickListener(e
                                -> VaadinClientUrlUtil.setLocation(QueryParameterUtil.getRelativePathWithQueryParameters(filterPageParameters, Ps4ECitizenServiceRequestReviewRoute.class)));
                    }
                    messageContainerSubject.add(documentDetail);
                }

                /* LINK SUBJECT */
                messageContainerSubject.addClassNames("name", "btn_modal");
                messageContainerSubject.getStyle().set("cursor", "pointer");
                messageContainerSubject.getElement().setAttribute("data-toggle", "modal");
                messageContainerSubject.getElement().setAttribute("data-target", "#emailModal" + index);
                registerClickEvent(PRESENTER_SHOW_EMAIL_ACTION, messageContainerSubject, iconMessageEnvelope,message,messageContainer);

                messageContainer.add(messageContainerHeader, messageContainerHeaderName);

                Div messageContainerEnvelopeContent = new Div();
                messageContainerEnvelopeContent.addClassName("envelope_content");

                Div messageContainerSeparator = new Div();
                messageContainerSeparator.addClassName("separator");

            /*REPLY TO MESSAGE*/
                Div messageFullContentToSendContainer = new Div();
                messageFullContentToSendContainer.addClassName("message_full_content");
                Div messageFullContentToSend = new Div();
                messageFullContentToSend.addClassName("message_reply");
                Div messageToSendContainer = new Div();
                messageToSendContainer.addClassName("form-group");
                TextArea textAreaMessage = new TextArea();
                textAreaMessage.addClassName("reply_message");
                textAreaMessage.setSizeFull();


               /* Input inputFileMessage = new Input();
                inputFileMessage.setType("file");
                inputFileMessage.addClassNames("form-control", "file-control");
                inputFileMessage.setPlaceholder(("ps4.ecetatean.breadcrumb.myaccount.page.inbox.message.upload.file"));
                HtmlContainer iconUploadFile = new HtmlContainer("i");
                iconUploadFile.addClassNames("fas", "fa-upload");
                Div divUploadFile = new Div(new Span(("ps4.ecetatean.breadcrumb.myaccount.page.inbox.message.upload.file")));
                divUploadFile.addClassNames("cbnp_upload_file", "form-control");
                divUploadFile.add(iconUploadFile);*/

                NativeButton buttonSendResponse = new NativeButton();

                buttonSendResponse.add(new Text(("ps4.ecetatean.breadcrumb.myaccount.page.inbox.message.respond")));
                buttonSendResponse.addClassNames("btn", "btn_green");
//                HtmlContainer iconSendResponse = new HtmlContainer("i");
//                iconSendResponse.addClassNames("fas", "fa-arrow-alt-circle-right");
//                buttonSendResponse.add(iconSendResponse);

                Div messageToReplyContent = new Div();
                messageToReplyContent.addClassName("msg_content");
                Span messageToReplyContentDate = new Span();
                messageToReplyContentDate.addClassName("date");
                messageToReplyContentDate.add(new Text("ps4.ecetatean.breadcrumb.myaccount.page.inbox.message.sendat"));
                messageToReplyContentDate.add(new Text(message.getCreatLa()));

            /*Paragraph pReplyRequest= new Paragraph(I18NProviderStatic.getTranslation("ps4.ecetatean.breadcrumb.myaccount.page.inbox.message.request", message.getNumeBaza()));
            Paragraph pReplyCategory= new Paragraph(I18NProviderStatic.getTranslation("ps4.ecetatean.breadcrumb.myaccount.page.inbox.message.category", message.getClasaDocument()));
            Paragraph pReplyDocType= new Paragraph(I18NProviderStatic.getTranslation("ps4.ecetatean.breadcrumb.myaccount.page.inbox.message.document", message.getDenumireDocument()));

            messageToReplyContent.add(messageToReplyContentDate,pReplyRequest,pReplyCategory,pReplyDocType);
*/
                messageToSendContainer.add(textAreaMessage);
                messageFullContentToSend.add(messageToSendContainer, buttonSendResponse);
                registerClickEvent(PRESENTER_RESPOND_BTN_ACTION, buttonSendResponse, message,textAreaMessage,messageContainer);

                messageFullContentToSendContainer.add(messageFullContentToSend, messageToReplyContent);

                messageContainer.add(messageContainerEnvelopeContent, messageContainerSeparator, messageFullContentToSendContainer, emailDialogComponent);
                add(messageContainer);
            }
        }

    }

    private void addOrRemoveReplyClass( Div messageContainer) {
         if( messageContainer.hasClassName("reply")){
             messageContainer.removeClassNames("reply");
        }
        else{
             messageContainer.addClassName("reply");
         }
    }

    private  void setSearchContainer(){

        Div divSearchForm=new Div();
        divSearchForm.addClassNames("row","no-gutters");
        Div divClassSearchForm=new Div();
        divClassSearchForm.addClassName("col-12");
//        inputSearch.setType("text");
        inputSearch.setPlaceholder("Caută");
        inputSearch.setWidthFull();
        HorizontalLayout hl = new HorizontalLayout(inputSearch);
        hl.addClassName("smart-form-control");
        hl.setWidthFull();
        HtmlContainer iconButtonSearch = new HtmlContainer("i");
        iconButtonSearch.addClassNames("fas","fa-search");
        buttonSearch.add(iconButtonSearch);

        divClassSearchForm.add(hl,buttonSearch);
        divSearchForm.add(divClassSearchForm);
        searchContainer.add(divSearchForm);

    }

    public String getSearchTextValue() {
        return inputSearch.getValue();
    }



}
