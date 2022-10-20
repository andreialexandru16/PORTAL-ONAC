package ro.bithat.dms.microservices.portal.ecitizen.useraccount.gui;

import ro.bithat.dms.microservices.dmsws.email.Email;
import ro.bithat.dms.microservices.portal.ecitizen.gui.ContentContainerView;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.gui.component.EmailInboxComponent;
import ro.bithat.dms.passiveview.boot.I18NProviderStatic;

import java.util.List;

public class Ps4ECitizenEmailInboxView extends ContentContainerView<Ps4ECitizenEmailInboxPresenter> {

    private EmailInboxComponent inboxComponent= new EmailInboxComponent(this);
//    private Div searchContainer= new Div();
//
//    Input inputSearch= new Input();
//    NativeButton buttonSearch=new NativeButton();

    @Override
    public void beforeBinding() {
        setContentPageTile(I18NProviderStatic.getTranslation("ps4.ecetatean.breadcrumb.myaccount.page.inbox.title"));
        setServicesListHeaderIcon("/icons/document.png");
        inboxComponent.addClassName("profile_inbox");
//        searchContainer.addClassName("search_form");
//        setSearchContainer();
//        addServiceListContent();
        getServiceListContainer().addClassName("profile_container");
        getServiceListContainer().add(inboxComponent);

    }

    public void i18nInboxContainer(){
        inboxComponent.i18nInboxContainer();
    }



    public void setInboxTable(List<Email> myMessages) {
        inboxComponent.setInboxTable(myMessages);
// /*TITLE*/
//        inboxContainer.removeAll();
//        inboxContainer.add(searchContainer);
//        Div inboxTitle = new Div();
//        inboxTitle.addClassName("box_title");
//        H2 hInboxTitle= new H2("ps4.ecetatean.breadcrumb.myaccount.page.inbox.title");
//        //hInboxTitle.add(new Text("ps4.ecetatean.breadcrumb.myaccount.page.inbox.title"));
//
//        inboxTitle.add(hInboxTitle);
//        inboxContainer.add(inboxTitle);
//
//        Integer index=0;
//        for (Email message: myMessages) {
//            Div messageContainer = new Div();
//            Div messageContainerEnvelope = new Div();
//            Div messageContainerHeader = new Div();
//            Div messageContainerSubject = new Div();
//            Div messageContainerHeaderName = new Div();
//            Div messageContainerHeaderRightItems = new Div();
//            messageContainer.addClassName("message");
//
//            if(message.getCitit()==null || message.getCitit().toString().equals("0")) {
//                messageContainer.addClassName("unread");
//            }
//            messageContainerEnvelope.addClassName("envelope");
//            HtmlContainer iconMessageEnvelope = new HtmlContainer("i");
//            messageContainerEnvelope.add(iconMessageEnvelope);
//            messageContainer.add(messageContainerEnvelope);
//
//            if(message.getCitit()==null || message.getCitit().toString().equals("0")) {
//                messageContainer.addClassName("unread");
//                iconMessageEnvelope.addClassNames("fas","fa-envelope");
//
//            }
//            else{
//                iconMessageEnvelope.addClassNames("fas","fa-envelope-open");
//
//            }
//
//            messageContainerHeader.addClassNames("header","clearfix");
//            messageContainerHeaderName.addClassName("name");
//            messageContainerHeaderName.add(new Text("ps4.ecetatean.breadcrumb.myaccount.page.inbox.message.sendby"));
//            messageContainerHeaderName.add(new Text(message.getCreatDe()));
//
//            messageContainerHeaderRightItems.addClassName("right_items");
//
//            ClickNotifierAnchor anchorReply= new ClickNotifierAnchor();
//            HtmlContainer iconMessageReply = new HtmlContainer("i");
//            iconMessageReply.addClassNames("fas","fa-reply");
//            anchorReply.add(iconMessageReply);
//
//            ClickNotifierAnchor anchorReplyAll= new ClickNotifierAnchor();
//            HtmlContainer iconMessageReplyAll = new HtmlContainer("i");
//            iconMessageReplyAll.addClassNames("fas","fa-reply-all");
//            anchorReplyAll.add(iconMessageReplyAll);
//
//            Div messageContainerHeaderRightItemsDate= new Div();
//            messageContainerHeaderRightItemsDate.addClassName("date");
//            messageContainerHeaderRightItemsDate.setText(message.getCreatLa());
//            messageContainerHeaderRightItems.add(anchorReply,anchorReplyAll,messageContainerHeaderRightItemsDate);
//
//            messageContainerHeader.add(messageContainerHeaderName,messageContainerHeaderRightItems);
//            messageContainerSubject.addClassName("subject");
//            messageContainerSubject.add(new Text("ps4.ecetatean.breadcrumb.myaccount.page.inbox.message.subject"));
//            messageContainerSubject.add(new Text(message.getTitlu()));
//            if(message.getDenumireFisier()!=null && !message.getDenumireFisier().isEmpty()){
//                HtmlContainer iconDocument = new HtmlContainer("i");
//                Anchor requestLink=new Anchor();
//                iconDocument.addClassNames("fas", "fa-eye");
//                Div documentDetail=new Div(iconDocument,requestLink);
//                requestLink.setText(I18NProviderStatic.getTranslation("ps4.ecetatean.breadcrumb.myaccount.page.inbox.message.request", message.getDenumireFisier()));
//                if(Optional.ofNullable(message.getIdDocument()).isPresent()
//                        && Optional.ofNullable(message.getIdClasaDocument()).isPresent()
//                        && Optional.ofNullable(message.getIdFisier()).isPresent()) {
//                    Map<String, Object> filterPageParameters = new HashMap<>();
//                    filterPageParameters.put("tipDocument", message.getIdClasaDocument());
//                    filterPageParameters.put("document", message.getIdDocument());
//                    filterPageParameters.put("request", message.getIdFisier());
//
//                    requestLink.setHref(QueryParameterUtil.getRelativePathWithQueryParameters(filterPageParameters, Ps4ECitizenServiceRequestReviewRoute.class));
//                }
//                messageContainerSubject.add(documentDetail);
//            }
//            messageContainer.add(messageContainerHeader,messageContainerSubject);
//
//            Div messageContainerEnvelopeContent = new Div();
//            messageContainerEnvelopeContent.addClassName("envelope_content");
//
//            Div messageContainerSeparator = new Div();
//            messageContainerSeparator.addClassName("separator");
//
//            /*REPLY TO MESSAGE*/
//            Div messageFullContentToSendContainer=new Div();
//            messageFullContentToSendContainer.addClassName("message_full_content");
//            Div messageFullContentToSend= new Div();
//            messageFullContentToSend.addClassName("message_reply");
//            Div messageToSendContainer= new Div();
//            messageToSendContainer.addClassName("form-group");
//            TextArea textAreaMessage=new TextArea();
//            textAreaMessage.addClassName("reply_message");
//            textAreaMessage.setSizeFull();
//
//
//            Input inputFileMessage= new Input();
//            inputFileMessage.setType("file"); inputFileMessage.addClassNames("form-control","file-control");
//            inputFileMessage.setPlaceholder(("ps4.ecetatean.breadcrumb.myaccount.page.inbox.message.upload.file"));
//            HtmlContainer iconUploadFile = new HtmlContainer("i");
//            iconUploadFile.addClassNames("fas","fa-upload");
//            Div divUploadFile= new Div(new Span(("ps4.ecetatean.breadcrumb.myaccount.page.inbox.message.upload.file")));
//            divUploadFile.addClassNames("cbnp_upload_file","form-control");
//            divUploadFile.add(iconUploadFile);
//            NativeButton buttonSendRespons= new NativeButton();
//            buttonSendRespons.add(new Text(("ps4.ecetatean.breadcrumb.myaccount.page.inbox.message.respond")));
//            buttonSendRespons.addClassNames("btn","btn-secondary");
//            HtmlContainer iconSendResponse = new HtmlContainer("i");
//            iconSendResponse.addClassNames("fas","fa-arrow-alt-circle-right");
//            buttonSendRespons.add(iconSendResponse);
//
//            Div messageToReplyContent= new Div();
//            messageToReplyContent.addClassName("msg_content");
//            Span messageToReplyContentDate= new Span();
//            messageToReplyContentDate.addClassName("date");
//            messageToReplyContentDate.add(new Text("ps4.ecetatean.breadcrumb.myaccount.page.inbox.message.sendat"));
//            messageToReplyContentDate.add(new Text(message.getCreatLa()));
//
//            /*Paragraph pReplyRequest= new Paragraph(I18NProviderStatic.getTranslation("ps4.ecetatean.breadcrumb.myaccount.page.inbox.message.request", message.getNumeBaza()));
//            Paragraph pReplyCategory= new Paragraph(I18NProviderStatic.getTranslation("ps4.ecetatean.breadcrumb.myaccount.page.inbox.message.category", message.getClasaDocument()));
//            Paragraph pReplyDocType= new Paragraph(I18NProviderStatic.getTranslation("ps4.ecetatean.breadcrumb.myaccount.page.inbox.message.document", message.getDenumireDocument()));
//
//            messageToReplyContent.add(messageToReplyContentDate,pReplyRequest,pReplyCategory,pReplyDocType);
//*/
//            messageToSendContainer.add(textAreaMessage,inputFileMessage,divUploadFile);
//            messageFullContentToSend.add(messageToSendContainer,buttonSendRespons);
//            messageFullContentToSendContainer.add(messageFullContentToSend,messageToReplyContent);
//
//            messageContainer.add(messageContainerEnvelopeContent,messageContainerSeparator,messageFullContentToSendContainer);
//            inboxContainer.add(messageContainer);
//
//        }
    }

//    private  void setSearchContainer(){
//
//        Div divSearchForm=new Div();
//        divSearchForm.addClassNames("row","no-gutters");
//        Div divClassSearchForm=new Div();
//        divClassSearchForm.addClassName("col-12");
//        inputSearch.setType("text");
//        inputSearch.addClassName("form-control");
//        inputSearch.setPlaceholder("Cauta");
//
//        HtmlContainer iconButtonSearch = new HtmlContainer("i");
//        iconButtonSearch.addClassNames("fas","fa-search");
//        buttonSearch.add(iconButtonSearch);
//
//        divClassSearchForm.add(inputSearch,buttonSearch);
//        divSearchForm.add(divClassSearchForm);
//        searchContainer.add(divSearchForm);
//
//    }

    public String getSearchTextValue() {
        return inboxComponent.getSearchTextValue();
    }

}
