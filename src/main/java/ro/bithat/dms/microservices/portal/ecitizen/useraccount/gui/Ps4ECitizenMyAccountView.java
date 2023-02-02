package ro.bithat.dms.microservices.portal.ecitizen.useraccount.gui;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.page.BrowserWindowResizeEvent;
import com.vaadin.flow.component.page.ExtendedClientDetails;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ro.bithat.dms.boot.BeanUtil;
import ro.bithat.dms.microservices.dmsws.colaboration.InfoMesaje;
import ro.bithat.dms.microservices.dmsws.email.Email;
import ro.bithat.dms.microservices.dmsws.file.PortalFile;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.CorespondentaControl;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.CorespondentaLinieControl;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.CorespondentaLiniePetitii;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.CorespondentaPetitii;
import ro.bithat.dms.microservices.dmsws.ps4.paymentintegration.imported.PlataResponse;
import ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui.Ps4ECitizenServiceRequestReviewRoute;
import ro.bithat.dms.microservices.portal.ecitizen.gui.template.ClientDetailsObserver;
import ro.bithat.dms.microservices.portal.ecitizen.gui.template.WindowResizeObserver;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat.PersoanaFizicaJuridica;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.gui.component.ColaborationMessagesTableComponent;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.gui.component.EmailInboxComponent;
import ro.bithat.dms.microservices.portal.ecitizen.website.models.FisierDraftExtended;
import ro.bithat.dms.passiveview.QueryParameterUtil;
import ro.bithat.dms.passiveview.StreamResourceUtil;
import ro.bithat.dms.passiveview.VaadinClientUrlUtil;
import ro.bithat.dms.passiveview.boot.I18NProviderStatic;
import ro.bithat.dms.passiveview.component.captcha.ReCaptcha;
import ro.bithat.dms.passiveview.component.html.ClickNotifierAnchor;
import ro.bithat.dms.passiveview.component.html.Strong;
import ro.bithat.dms.passiveview.component.view.DivFlowViewBuilder;
import ro.bithat.dms.security.AnonymousAuthenticationHelper;
import ro.bithat.dms.security.SecurityUtils;
import ro.bithat.dms.security.UserWithUserToken;

import java.text.SimpleDateFormat;
import java.util.*;

public class Ps4ECitizenMyAccountView extends DivFlowViewBuilder<Ps4ECitizenMyAccountPresenter> implements ClientDetailsObserver, WindowResizeObserver {


    private ClickNotifierAnchor anchorDocumentsBtn = new ClickNotifierAnchor();
    private ClickNotifierAnchor anchorEditBtn = new ClickNotifierAnchor();
    private ClickNotifierAnchor schimbaParolaBtn = new ClickNotifierAnchor();
    private ClickNotifierAnchor anchorButtonAllRequests = new ClickNotifierAnchor();
    private ClickNotifierAnchor anchorButtonAllInvoices = new ClickNotifierAnchor();
    private ClickNotifierAnchor anchorButtonAllDocuments = new ClickNotifierAnchor();
    private ClickNotifierAnchor anchorButtonAllDrafts = new ClickNotifierAnchor();
    private ClickNotifierAnchor studiiPage = new ClickNotifierAnchor();
    private ClickNotifierAnchor contactePage = new ClickNotifierAnchor();


    private ClickNotifierAnchor anchorAllMessages = new ClickNotifierAnchor();



    private Div myRequestsContainer = new Div();

    private Div myDocumentsContainer = new Div();

    private Div myInvoicesContainer = new Div();

    private Div myDraftsContainer = new Div();


    private Div myAccountDetailsContainerSidebar = new Div( myRequestsContainer,myInvoicesContainer, myDocumentsContainer, myDraftsContainer);

    private ColaborationMessagesTableComponent colaborationMessagesTableComponent = new ColaborationMessagesTableComponent(this);

    private Div gap50 = new Div();

    private EmailInboxComponent inboxComponent = new EmailInboxComponent(this);

    private Div accountDataContent = new Div();

    private Div accountDataContentProfileImage = new Div();

    private Div accountDataContentProfileContent = new Div();

    private Div accountData = new Div();

    private Div myAccountDetailsContainerProfile = new Div(accountData, inboxComponent, gap50, colaborationMessagesTableComponent);


    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
    //todo ce cauta model in view ???!?!?!?!?!
    private PersoanaFizicaJuridica user = new PersoanaFizicaJuridica();

    @Override
    protected void buildView() {
        addClassNames("profile_container", "row", "no-gutters");
        setStylesForMyAccountDetailsContainerProfile();
        setStylesForMyAccountDetailsContainerSidebar();
        gap50.addClassName("gap-50");

        add(myAccountDetailsContainerProfile, myAccountDetailsContainerSidebar);
        getPresenter().getAmenzi();
    }

    public void setColaborationMessagesTable(List<InfoMesaje> myMessages) {
        if (myMessages.size() != 0) {
            colaborationMessagesTableComponent.setColaborationMessagesTable(myMessages);
        } else {
            myAccountDetailsContainerProfile.remove(colaborationMessagesTableComponent);
        }
    }




    public void setMyInvoicesContainer(List<PortalFile> myRequests) {

        Div myInvoicesHeader = new Div();
        Div myInvoicesHeaderImage = new Div();
        Div myInvoicesHeaderTitle = new Div();
        Div myInvoicesHeaderNo = new Div();
       /*HEADER*/
        myInvoicesHeader.addClassName("header");
        myInvoicesHeaderImage.addClassName("image");
        myInvoicesHeaderTitle.addClassName("title");
        myInvoicesHeaderNo.addClassName("no");
        HtmlContainer iconHeaderImage = new HtmlContainer("i");
        iconHeaderImage.addClassNames("far", "fa-file-alt");

        myInvoicesHeaderImage.add(iconHeaderImage);
        myInvoicesHeaderTitle.add(new Text("ps4.ecetatean.breadcrumb.myaccount.myinvoices.page.title"));
        myInvoicesHeaderNo.add(new Text(String.valueOf(myRequests.size())));

        myInvoicesHeader.add(myInvoicesHeaderImage, myInvoicesHeaderTitle, myInvoicesHeaderNo);
        /*REQUESTS*/
        UnorderedList ulMyRequestsWidgetElements = new UnorderedList();
        ulMyRequestsWidgetElements.addClassName("widget_elements");
        for (PortalFile request : myRequests) {
            ListItem liMyRequests = createListItemForInvoices(request);
            ulMyRequestsWidgetElements.add(liMyRequests);
        }

        /*BUTTON ALL REQUESTS*/

        Div allMyInvoicesButton = new Div();

        allMyInvoicesButton.addClassName("all_elements");
        anchorButtonAllInvoices.add(new Text("ps4.ecetatean.breadcrumb.myaccount.page.myinvoices.button.all"));
        anchorButtonAllInvoices.setHref("javascript:void(0);");
        anchorButtonAllInvoices.addClassNames("btn", "btn_green", "min_width250", "btn-common");
        /*HtmlContainer iconElementButtonGoToAllRequests = new HtmlContainer("i");
        iconElementButtonGoToAllRequests.addClassNames("fas","fa-arrow-alt-circle-right");
        anchorButtonAllInvoices.add(iconElementButtonGoToAllRequests);*/

        myInvoicesContainer.add(myInvoicesHeader, ulMyRequestsWidgetElements, anchorButtonAllInvoices);

    }

    public void setMyDraftsContainer(List<FisierDraftExtended> myDrafts) {

        Div myDraftsHeader = new Div();
        Div myDraftsHeaderImage = new Div();
        Div myDraftsHeaderTitle = new Div();
        Div myDraftsHeaderNo = new Div();
       /*HEADER*/
        myDraftsHeader.addClassName("header");
        myDraftsHeaderImage.addClassName("image");
        myDraftsHeaderTitle.addClassName("title");
        myDraftsHeaderNo.addClassName("no");
        HtmlContainer iconHeaderImage = new HtmlContainer("i");
        iconHeaderImage.addClassNames("far", "fa-file-alt");

        myDraftsHeaderImage.add(iconHeaderImage);
        myDraftsHeaderTitle.add(new Text("ps4.ecetatean.breadcrumb.myaccount.mydrafts.page.title"));
        myDraftsHeaderNo.add(new Text(String.valueOf(myDrafts.size())));

        myDraftsHeader.add(myDraftsHeaderImage, myDraftsHeaderTitle, myDraftsHeaderNo);
        /*REQUESTS*/
        UnorderedList ulMyRequestsWidgetElements = new UnorderedList();
        ulMyRequestsWidgetElements.addClassName("widget_elements");
        for (FisierDraftExtended request : myDrafts) {
            ListItem liMyRequests = createListItemForDrafts(request);
            ulMyRequestsWidgetElements.add(liMyRequests);
        }

        /*BUTTON ALL REQUESTS*/

        Div allMyDraftsButton = new Div();

        allMyDraftsButton.addClassName("all_elements");
        anchorButtonAllDrafts.add(new Text("ps4.ecetatean.breadcrumb.myaccount.page.mydrafts.button.all"));
        anchorButtonAllDrafts.setHref("javascript:void(0);");
        anchorButtonAllDrafts.addClassNames("btn", "btn_green", "min_width250", "btn-common");


        myDraftsContainer.add(myDraftsHeader, ulMyRequestsWidgetElements, anchorButtonAllDrafts);

    }


    public void setMyRequestsTable(List<PortalFile> myRequests) {

        Div myRequestsHeader = new Div();
        Div myRequestsHeaderImage = new Div();
        Div myRequestsHeaderTitle = new Div();
        Div myRequestsHeaderNo = new Div();
       /*HEADER*/
        myRequestsHeader.addClassName("header");
        myRequestsHeaderImage.addClassName("image");
        myRequestsHeaderTitle.addClassName("title");
        myRequestsHeaderNo.addClassName("no");
        HtmlContainer iconHeaderImage = new HtmlContainer("i");
        iconHeaderImage.addClassNames("far", "fa-file-alt");

        myRequestsHeaderImage.add(iconHeaderImage);
        myRequestsHeaderTitle.add(new Text("ps4.ecetatean.breadcrumb.myaccount.myrequests.page.title"));
        myRequestsHeaderNo.add(new Text(String.valueOf(myRequests.size())));

        myRequestsHeader.add(myRequestsHeaderImage, myRequestsHeaderTitle, myRequestsHeaderNo);
        /*REQUESTS*/
        UnorderedList ulMyRequestsWidgetElements = new UnorderedList();
        ulMyRequestsWidgetElements.addClassName("widget_elements");
        for (PortalFile request : myRequests) {
            ListItem liMyRequests = createListItemForRequests(request);
            ulMyRequestsWidgetElements.add(liMyRequests);
        }

        /*BUTTON ALL REQUESTS*/

        Div allMyRequestsButton = new Div();

        allMyRequestsButton.addClassName("all_elements");
        anchorButtonAllRequests.add(new Text("ps4.ecetatean.breadcrumb.myaccount.page.myrequests.button.all"));
        anchorButtonAllRequests.setHref("javascript:void(0);");
        anchorButtonAllRequests.addClassNames("btn", "btn_green", "min_width250", "btn-common");
        /*HtmlContainer iconElementButtonGoToAllRequests = new HtmlContainer("i");
        iconElementButtonGoToAllRequests.addClassNames("fas","fa-arrow-alt-circle-right");
        anchorButtonAllRequests.add(iconElementButtonGoToAllRequests);*/

        myRequestsContainer.add(myRequestsHeader, ulMyRequestsWidgetElements, anchorButtonAllRequests);

    }

    public void setMyDocumentsTable(List<PortalFile> myDocuments) {
        Div myDocumentsHeader = new Div();
        Div myDocumentsHeaderImage = new Div();
        Div myDocumentsHeaderTitle = new Div();
        Div myDocumentsHeaderNo = new Div();
       /*HEADER*/
        myDocumentsHeader.addClassName("header");
        myDocumentsHeaderImage.addClassName("image");
        myDocumentsHeaderTitle.addClassName("title");
        myDocumentsHeaderNo.addClassName("no");
        HtmlContainer iconHeaderImage = new HtmlContainer("i");
        iconHeaderImage.addClassNames("far", "fa-file-alt");

        myDocumentsHeaderImage.add(iconHeaderImage);
        myDocumentsHeaderTitle.add((new Text("ps4.ecetatean.breadcrumb.myaccount.mydocuments.page.title")));
        myDocumentsHeaderNo.add(new Text(String.valueOf(myDocuments.size())));

        myDocumentsHeader.add(myDocumentsHeaderImage, myDocumentsHeaderTitle, myDocumentsHeaderNo);
        /*DOCUMENTS*/
        UnorderedList ulMyDocumentsWidgetElements = new UnorderedList();
        ulMyDocumentsWidgetElements.addClassName("widget_elements");
        for (PortalFile document : myDocuments) {
            ListItem liMyDocuments = createListItemForDocuments(document);
            ulMyDocumentsWidgetElements.add(liMyDocuments);
        }

        /*BUTTON ALL DOCUMENTS*/

        Div allMyDocumentsButton = new Div();

        allMyDocumentsButton.addClassName("all_elements");
        anchorButtonAllDocuments.add(new Text("ps4.ecetatean.breadcrumb.myaccount.page.mydocuments.button.all"));
        anchorButtonAllDocuments.setHref("javascript:void(0);");
        anchorButtonAllDocuments.addClassNames("btn", "btn_green", "min_width", "btn-common");
        myDocumentsContainer.add(myDocumentsHeader, ulMyDocumentsWidgetElements, anchorButtonAllDocuments);

    }


    public void i18nInboxContainer() {
        inboxComponent.i18nInboxContainer();
    }

    public void setInboxTable(List<Email> myMessages) {
        inboxComponent.setInboxTable(myMessages);

        anchorAllMessages.removeAll();
        anchorAllMessages.add(new Text(("ps4.ecetatean.breadcrumb.myaccount.page.inbox.message.btn.all")));
        anchorAllMessages.addClassNames("btn", "btn_green", "min_width250", "btn-common", "all_messages");
        anchorAllMessages.setHref("javascript:void(0);");
        /*HtmlContainer iconAllMessages = new HtmlContainer("i");
        iconAllMessages.addClassNames("fas","fa-arrow-alt-circle-right");
        anchorAllMessages.add(iconAllMessages);*/
        inboxComponent.add(anchorAllMessages);

    }

    public void setFormDetails(PersoanaFizicaJuridica userAccount) {
        user = userAccount;
        String fullName = userAccount.getNume() + " " + userAccount.getPrenume();
        String email = userAccount.getEmail();
        String phone = userAccount.getTelefon();
        Date today = new Date();
        String todayStr = simpleDateFormat.format(today);
        String welcomeMessage = I18NProviderStatic.getTranslation("ps4.ecetatean.breadcrumb.myaccount.page.welcome", fullName);

        Div profileFieldWelcome = new Div();
        Div profileFieldDate = new Div();
        Div gap20 = new Div();
        Div profileFieldName = new Div();
        Div profileFieldEmail = new Div();
        Div profileFieldPhone = new Div();
        Div editProfileBtn = new Div();

        //set classes
        profileFieldWelcome.addClassNames("profile_field", "welcome");
        profileFieldDate.addClassNames("profile_field", "profile_date");
        gap20.addClassName("gap-20");
        profileFieldName.addClassNames("profile_field", "pf_details");
        profileFieldEmail.addClassNames("profile_field", "pf_details");
        profileFieldPhone.addClassNames("profile_field", "pf_details");
        editProfileBtn.addClassNames("edit_profile_btn");

        //set content
        profileFieldWelcome.add(new Strong(welcomeMessage));
        Span spanprofileFieldDate = new Span(todayStr);
        profileFieldDate.add(spanprofileFieldDate);

        Strong strongName = new Strong();
        strongName.addClassName("same_width");
        strongName.add(new Text("ps4.ecetatean.breadcrumb.myaccount.page.name"));
        Span spanName = new Span(fullName);
        profileFieldName.add(strongName, spanName);

        Strong strongEmail = new Strong();
        strongEmail.addClassName("same_width");
        strongEmail.add(new Text("ps4.ecetatean.breadcrumb.myaccount.page.email"));
        Span spanEmail = new Span(email);
        profileFieldEmail.add(strongEmail, spanEmail);

        Strong strongPhone = new Strong();
        strongPhone.addClassName("same_width");
        strongPhone.add(new Text("ps4.ecetatean.breadcrumb.myaccount.page.phone"));
        Span spanPhone = new Span(phone);
        profileFieldPhone.add(strongPhone, spanPhone);

        //BTN SCHIMBA PAROLA
        schimbaParolaBtn.setHref("javascript:void(0);");
        schimbaParolaBtn.addClassNames("btn", "btn_cogs", "btn_green");
        schimbaParolaBtn.add(new Text("ps4.ecetatean.breadcrumb.myaccount.page.schimba.button"));
        HtmlContainer iconArrowNextPass = new HtmlContainer("i");
        iconArrowNextPass.addClassNames("fas", "fa-unlock-alt");
        schimbaParolaBtn.add(iconArrowNextPass);
        editProfileBtn.add(schimbaParolaBtn);
        //END BTN SCHIMBA PAROLA

        anchorEditBtn.setHref("javascript:void(0);");
        anchorEditBtn.getStyle().set("margin-right", "0px");
        anchorEditBtn.addClassNames("btn", "btn_cogs", "btn_green");
        anchorEditBtn.add(new Text("ps4.ecetatean.breadcrumb.myaccount.page.edit.button"));
        HtmlContainer iconArrowNext = new HtmlContainer("i");
        iconArrowNext.addClassNames("fas", "fa-cogs");
        anchorEditBtn.add(iconArrowNext);

        editProfileBtn.add(anchorEditBtn);

//        anchorDocumentsBtn.setHref("javascript:void(0);");
//        anchorDocumentsBtn.addClassNames("btn", "btn_cogs", "btn_green");
//        anchorDocumentsBtn.add(new Text("ps4.ecetatean.breadcrumb.myaccount.page.documents.button"));
//        HtmlContainer iconArrowNextDoc = new HtmlContainer("i");
//        iconArrowNextDoc.addClassNames("fas", "fa-file");
//        anchorDocumentsBtn.add(iconArrowNextDoc);
//        editProfileBtn.add(anchorDocumentsBtn);
        accountDataContentProfileContent.add(profileFieldWelcome, profileFieldDate, profileFieldName, profileFieldEmail, profileFieldPhone, editProfileBtn);

        //BTN STUDII
        studiiPage.setHref("javascript:void(0);");
        studiiPage.addClassNames("btn", "btn_cogs", "btn_green");
        studiiPage.add(new Text("ps4.ecetatean.breadcrumb.myaccount.detail.table.studii"));
        HtmlContainer iconArrowNextStudii = new HtmlContainer("i");
        iconArrowNextStudii.addClassNames("fas", "fa-university");
        studiiPage.add(iconArrowNextStudii);

        //END BTN STUDII


        //BTN CONTACTE
//        contactePage.setHref("javascript:void(0);");
//        contactePage.addClassNames("btn", "btn_cogs", "btn_green");
//        contactePage.add(new Text("ps4.ecetatean.breadcrumb.myaccount.detail.table.contactePage"));
//        HtmlContainer iconArrowNextContacte = new HtmlContainer("i");
//        iconArrowNextContacte.addClassNames("fas", "fa-cogs");
//        contactePage.add(iconArrowNextContacte);


        //END BTN CONTACTE
        if (SecurityUtils.getContCurentPortalE().getUserCurent() != null
                && (SecurityUtils.getContCurentPortalE().getUserCurent().getEstePersoanaFizica() != null
                && SecurityUtils.getContCurentPortalE().getUserCurent().getEstePersoanaFizica().equals("1"))) {
            if (SecurityUtils.getContCurentPortalE().getTertParinteUserCurent() != null) {
                editProfileBtn.add(contactePage, studiiPage);

            } else {
                editProfileBtn.add(studiiPage);
            }
        } else
        {
            if (SecurityUtils.getContCurentPortalE().getTertParinteUserCurent() != null
                    && (SecurityUtils.getContCurentPortalE().getUserCurent().getEstePersoanaFizica() != null
                    && SecurityUtils.getContCurentPortalE().getUserCurent().getEstePersoanaFizica().equals("0"))) {
            editProfileBtn.add(contactePage);

        }
    }

    //PN - 22.02.2022 - Claudiu Alecu - daca parametrul showEditButtons e setat pe false sa ascunda butoanele(default true)
        // {my.account.show.edit.buttons}
        if(!getPresenter().isShowEditButtons()){
            schimbaParolaBtn.getStyle().set("display","none");
            editProfileBtn.getStyle().set("display","none");
        }

    }

    private Div logoutBtn() {
        Div logoutBtn = new Div();
        logoutBtn.addClassNames("edit_profile_btn");

        ClickNotifierAnchor anchorLogoutBtn = new ClickNotifierAnchor();
        anchorLogoutBtn.addClickListener(e -> UI.getCurrent().getPage().setLocation("/logout"));
        anchorLogoutBtn.addClassName("profile_edit");
        anchorLogoutBtn.add(new Label("ps4.ecetatean.breadcrumb.myaccount.page.logout.button"));
        HtmlContainer iconArrowNext = new HtmlContainer("i");
        iconArrowNext.addClassNames("fas", "fa-arrow-alt-circle-right");
        anchorLogoutBtn.add(iconArrowNext);

        logoutBtn.add(anchorLogoutBtn);
        return logoutBtn;
    }


    private ListItem createListItemForInvoices(PortalFile file) {
        ListItem liMyRequests = new ListItem();
            /*ELEMENT CONTENT*/
        Div divElementContent = new Div();
        divElementContent.addClassName("element_content");
        HtmlContainer iconElementContent = new HtmlContainer("i");
        iconElementContent.addClassNames("fas", "fa-file-alt");
        Strong strongElementContent = new Strong();
        strongElementContent.setText("Factura " + " " + file.getNumarFactura() + " (" + file.getDataFactura() + ")"
                + " - " + file.getSerieFactura());

        divElementContent.add(iconElementContent, strongElementContent);
            /*ELEMENT BUTTONS*/
        Div divElementButtons = new Div();
        divElementButtons.addClassName("element_buttons");

        if (Optional.ofNullable(file.getDownloadLink()).isPresent() &&
                !file.getDownloadLink().isEmpty()) {
            ClickNotifierAnchor anchorRequestPreviewFile = new ClickNotifierAnchor();
            anchorRequestPreviewFile.setHref(
                    StreamResourceUtil.getStreamResource(file.getNume(), file.getDownloadLink()));
            anchorRequestPreviewFile.setTarget("_blank");
            HtmlContainer iconElementButtonPreview = new HtmlContainer("i");
            iconElementButtonPreview.addClassNames("fas", "fa-file-pdf");
            anchorRequestPreviewFile.add(iconElementButtonPreview);
            divElementButtons.add(anchorRequestPreviewFile);
        }


//        ClickNotifierAnchor anchorRequestGoToFile = new ClickNotifierAnchor();
        //TODO redirect to page: solicitare-noua-revizie-finala
        //anchorRequestPreviewFile.setHref();
//        HtmlContainer iconElementButtonGoTo = new HtmlContainer("i");
//        iconElementButtonGoTo.addClassNames("fas", "fa-eye");
//        anchorRequestGoToFile.add(iconElementButtonGoTo);
//        anchorRequestGoToFile.getElement().getStyle().set("cursor", "pointer");
//        if (Optional.ofNullable(file.getIdDocument()).isPresent()
//                && Optional.ofNullable(file.getIdClasaDocument()).isPresent()
//                && Optional.ofNullable(file.getId()).isPresent()) {
//            Map<String, Object> filterPageParameters = new HashMap<>();
//            filterPageParameters.put("tipDocument", file.getIdClasaDocument());
//            filterPageParameters.put("document", file.getIdDocument());
//            filterPageParameters.put("request", file.getId());
//
//            anchorRequestGoToFile.getStyle().set("cursor", "pointer");
//            anchorRequestGoToFile.addClickListener(e
//                    -> VaadinClientUrlUtil.setLocation(QueryParameterUtil.getRelativePathWithQueryParameters(filterPageParameters, Ps4ECitizenServiceRequestReviewRoute.class)));
//        }

//        divElementButtons.add(anchorRequestGoToFile);

        liMyRequests.add(divElementContent, divElementButtons);
        return liMyRequests;
    }

    private ListItem createListItemForDrafts(FisierDraftExtended file) {
        ListItem liMyRequests = new ListItem();
            /*ELEMENT CONTENT*/
        Div divElementContent = new Div();
        divElementContent.addClassName("element_content");
        HtmlContainer iconElementContent = new HtmlContainer("i");
        iconElementContent.addClassNames("fas", "fa-file-alt");
        Strong strongElementContent = new Strong();
        strongElementContent.setText(file.getNume());

        divElementContent.add(iconElementContent, strongElementContent);
            /*ELEMENT BUTTONS*/
        Div divElementButtons = new Div();
        divElementButtons.addClassName("element_buttons");

        liMyRequests.add(divElementContent, divElementButtons);
        return liMyRequests;
    }



    private ListItem createListItemForRequests(PortalFile file) {
        ListItem liMyRequests = new ListItem();
            /*ELEMENT CONTENT*/
        Div divElementContent = new Div();
        divElementContent.addClassName("element_content");
        HtmlContainer iconElementContent = new HtmlContainer("i");
        iconElementContent.addClassNames("fas", "fa-file-alt");
        Strong strongElementContent = new Strong();
        strongElementContent.setText(file.getDenumireDocument() + " " + file.getNume() + " (" + file.getTrimisLa() + ")"
                + " - " + file.getDenumireWorkflowStatus());

        divElementContent.add(iconElementContent, strongElementContent);
            /*ELEMENT BUTTONS*/
        Div divElementButtons = new Div();
        divElementButtons.addClassName("element_buttons");

        if (Optional.ofNullable(file.getDownloadLink()).isPresent() &&
                !file.getDownloadLink().isEmpty()) {
            ClickNotifierAnchor anchorRequestPreviewFile = new ClickNotifierAnchor();
            anchorRequestPreviewFile.setHref(
                    StreamResourceUtil.getStreamResource(file.getNume(), file.getDownloadLink()));
            anchorRequestPreviewFile.setTarget("_blank");
            HtmlContainer iconElementButtonPreview = new HtmlContainer("i");
            iconElementButtonPreview.addClassNames("fas", "fa-file-pdf");
            anchorRequestPreviewFile.add(iconElementButtonPreview);
            divElementButtons.add(anchorRequestPreviewFile);
        }


        ClickNotifierAnchor anchorRequestGoToFile = new ClickNotifierAnchor();
        //TODO redirect to page: solicitare-noua-revizie-finala
        //anchorRequestPreviewFile.setHref();
        HtmlContainer iconElementButtonGoTo = new HtmlContainer("i");
        iconElementButtonGoTo.addClassNames("fas", "fa-eye");
        anchorRequestGoToFile.add(iconElementButtonGoTo);
        anchorRequestGoToFile.getElement().getStyle().set("cursor", "pointer");
        if (Optional.ofNullable(file.getIdDocument()).isPresent()
                && Optional.ofNullable(file.getIdClasaDocument()).isPresent()
                && Optional.ofNullable(file.getId()).isPresent()) {
            Map<String, Object> filterPageParameters = new HashMap<>();
            filterPageParameters.put("tipDocument", file.getIdClasaDocument());
            filterPageParameters.put("document", file.getIdDocument());
            filterPageParameters.put("request", file.getId());

            anchorRequestGoToFile.getStyle().set("cursor", "pointer");
            anchorRequestGoToFile.addClickListener(e
                    -> VaadinClientUrlUtil.setLocation(QueryParameterUtil.getRelativePathWithQueryParameters(filterPageParameters, Ps4ECitizenServiceRequestReviewRoute.class)));
        }

        divElementButtons.add(anchorRequestGoToFile);

        liMyRequests.add(divElementContent, divElementButtons);
        return liMyRequests;
    }







    private ListItem createListItemForDocuments(PortalFile file) {
        ListItem liMyDocuments = new ListItem();
            /*ELEMENT CONTENT*/
        Div divElementContent = new Div();
        divElementContent.addClassName("element_content");
        HtmlContainer iconElementContent = new HtmlContainer("i");
        iconElementContent.addClassNames("fas", "fa-paperclip");
        Strong strongElementContent = new Strong();
        strongElementContent.setText(file.getDenumireDocument() + " (" + file.getCreatLaStr() + ")");
        divElementContent.add(iconElementContent, strongElementContent);
            /*ELEMENT BUTTONS*/
        Div divElementButtons = new Div();
        divElementButtons.addClassName("element_buttons");

        if (Optional.ofNullable(file.getDownloadLink()).isPresent() &&
                !file.getDownloadLink().isEmpty()) {
            ClickNotifierAnchor anchorDocumentPreviewFile = new ClickNotifierAnchor();
            anchorDocumentPreviewFile.setHref(
                    StreamResourceUtil.getStreamResource(file.getNume(), file.getDownloadLink()));
            anchorDocumentPreviewFile.setTarget("_blank");
            HtmlContainer iconElementButtonPreview = new HtmlContainer("i");
            iconElementButtonPreview.addClassNames("fas", "fa-file-pdf");
            anchorDocumentPreviewFile.add(iconElementButtonPreview);
            divElementButtons.add(anchorDocumentPreviewFile);
        }


//        ClickNotifierAnchor anchorDocumentGoToFile= new ClickNotifierAnchor();
//        //anchorDocumentPreviewFile.setHref();
//        HtmlContainer iconElementButtonGoTo = new HtmlContainer("i");
//        iconElementButtonGoTo.addClassNames("fas","fa-eye");
//        anchorDocumentGoToFile.add(iconElementButtonGoTo);
//
//        divElementButtons.add(anchorDocumentGoToFile);

        liMyDocuments.add(divElementContent, divElementButtons);
        return liMyDocuments;
    }


    private void setStylesForMyAccountDetailsContainerProfile() {
        myAccountDetailsContainerProfile.addClassNames("col-md-12", "col-xl-8", "profile");

        Div boxTitle = new Div();

        accountData.addClassName("account_data");
        boxTitle.addClassName("box_title");
        boxTitle.add(new H2(("ps4.ecetatean.breadcrumb.myaccount.page.account.info")));

        accountDataContent.addClassNames("account_data_content", "clearfix");

        accountDataContentProfileImage.addClassName("profile_image");
        accountDataContentProfileImage.getStyle().set("background-image", "url(frontend/ps4/ecitizen/assets/images/contul_meu.png)");

        Image personImage = new Image();
        personImage.addClassName("sr-only");

        accountDataContentProfileImage.add(personImage);

        accountDataContentProfileContent.addClassName("profile_content");
        accountDataContent.add(accountDataContentProfileImage, accountDataContentProfileContent);

        accountData.add(boxTitle, accountDataContent);

    }

    private void setStylesForMyAccountDetailsContainerSidebar() {
        myAccountDetailsContainerSidebar.addClassNames("cold-md-12", "col-xl-4", "sidebar");
//        myPetitiiContainer.addClassNames("sidebar_widget", "my_petitii");
        myRequestsContainer.addClassNames("sidebar_widget", "my_requests");
        myInvoicesContainer.addClassNames("sidebar_widget", "my_requests");
        myDocumentsContainer.addClassNames("sidebar_widget", "my_documents");
        myDraftsContainer.addClassNames("sidebar_widget", "my_requests");


    }

    public String getSearchTextValue() {
        return inboxComponent.getSearchTextValue();
    }

    protected void displayForWidth(int width) {
        if (width <= 700) {
            colaborationMessagesTableComponent.displayForMobile();

        } else {
            colaborationMessagesTableComponent.displayForDesktop();

        }
    }

    @Override
    public void receiveClientDetails(ExtendedClientDetails extendedClientDetails) {
        displayForWidth(extendedClientDetails.getBodyClientWidth());
    }

    @Override
    public void browserWindowResized(BrowserWindowResizeEvent event) {

        displayForWidth(event.getWidth());
    }

    public void hideNonstandardButtons(){
        studiiPage.setVisible(false);
        contactePage.setVisible(false);
    }
}
