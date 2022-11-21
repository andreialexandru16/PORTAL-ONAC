package ro.bithat.dms.microservices.portal.ecitizen.useraccount.gui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import org.springframework.beans.factory.annotation.Value;
import ro.bithat.dms.microservices.dmsws.file.PortalFile;
import ro.bithat.dms.microservices.portal.ecitizen.gui.component.TableContainerDiv;
import ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui.Ps4ECitizenServiceRequestReviewRoute;
import ro.bithat.dms.microservices.portal.ecitizen.gui.ContentContainerView;
import ro.bithat.dms.microservices.portal.ecitizen.gui.component.MobTableContainerDiv;
import ro.bithat.dms.microservices.portal.ecitizen.onlineservices.gui.component.SearchContainer;
import ro.bithat.dms.passiveview.QueryParameterUtil;
import ro.bithat.dms.passiveview.VaadinClientUrlUtil;
import ro.bithat.dms.passiveview.boot.I18NProviderStatic;
import ro.bithat.dms.passiveview.component.html.ClickNotifierAnchor;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Ps4ECitizenMyRequestsView extends ContentContainerView<Ps4ECitizenMyRequestsPresenter> {
    private SearchContainer searchFormContainer = new SearchContainer(this);


    private TableContainerDiv serviceDocumentsTable =
            new TableContainerDiv(
                    "myaccount.my.requests.view.service.index.label",
                    "myaccount.my.requests.view.service.documentType.label",
                    "myaccount.my.requests.view.service.documentname.label",
                    "myaccount.my.requests.view.service.rl_denumire.label",
                    "myaccount.my.requests.view.service.rl_functie.label",
                    "myaccount.my.requests.view.service.rl_email.label",
                    "myaccount.my.requests.view.service.c1_denumire.label",
                    "myaccount.my.requests.view.service.c1_email.label",
                    "myaccount.my.requests.view.service.c1_tel.label",
                    "myaccount.my.requests.view.service.c2_denumire.label",
                    "myaccount.my.requests.view.service.c2_email.label",
                    "myaccount.my.requests.view.service.c2_telefon.label",
                    "myaccount.my.requests.view.service.datastart.label",
                    "myaccount.my.requests.view.service.documentstatus.label"
            );
    private Div serviceDocumentsTableContainer = new Div(serviceDocumentsTable);

    private MobTableContainerDiv mobServiceDocumentsTable = new MobTableContainerDiv();

    private Div formContainer = new Div(serviceDocumentsTableContainer, mobServiceDocumentsTable);
    private ClickNotifierAnchor anchorContainer = new ClickNotifierAnchor();
    private Div buttonContainer = new Div(anchorContainer);



    @Override
    public void beforeBinding() {
        if(getPresenter().getShowPaymentCol()!=null && getPresenter().getShowPaymentCol().equals("true") ){
            serviceDocumentsTable.addHeader( "myaccount.my.requests.view.service.paymentvalue.label");
        }
        //13.07.2021 - NG - ANRE - adaugare camp de cautare
        setContentPageTile(I18NProviderStatic.getTranslation("ps4.ecetatean.breadcrumb.myaccount.myrequests.page.title"));
        setServicesListHeaderIcon("/icons/document.png");
        formContainer.addClassNames("my_documents", "table_scroll");

        styleNewRequestButton();

        Div clearFix = new Div();
        clearFix.addClassNames("clearfix", "gap_20");
       // getServiceListContainer().add(formContainer, clearFix, buttonContainer);
        getServiceListContainer().add(formContainer, clearFix);
        styleMyRequestsTable();

    }

    @Override
    protected void displayForWidth(int width) {
        if(width <= 700) {
            mobServiceDocumentsTable.getStyle().remove("display");
            serviceDocumentsTableContainer.getStyle().set("display", "none");
        } else {
            if(serviceDocumentsTableContainer.getStyle().has("display")) {
                serviceDocumentsTableContainer.getStyle().remove("display");
            }
            mobServiceDocumentsTable.getStyle().set("display", "none");
        }
    }
    public SearchContainer getSearchFormContainer() {
        return searchFormContainer;
    }

    public void setMyRequestsTable(List<PortalFile> myDocuments) {
        serviceDocumentsTable.clearContent();
        AtomicInteger index = new AtomicInteger(1);
        myDocuments.stream().forEach(document -> setDocumentTableRow(document, index));
    }

    private void setDocumentTableRow(PortalFile document, AtomicInteger index) {


        ClickNotifierAnchor categoryLink = constructClickNotifierAnchor(document.getClasaDocument(), "inherit-text-color");
        ClickNotifierAnchor mobCategoryLink = constructClickNotifierAnchor(document.getClasaDocument(), "inherit-text-color");

       /* Span categoryIcon= constructSpan("pictograma");*/
        Span mobCategoryIcon= constructSpan("pictograma");

        /*Div categoryDetail=new Div(categoryIcon,categoryLink);*/
        Div categoryDetail=new Div(categoryLink);

        Div mobCategoryDetail=new Div(mobCategoryIcon,mobCategoryLink);

        //TODO check this !!!!! should be class convention name lowercase and space underline
        if(document.getClasaDocument()!=null){
            categoryDetail.addClassNames("picto_parent", "urbanism");
            mobCategoryDetail.addClassNames("picto_parent", "urbanism");
        }

        HtmlContainer iconDocType = document.getDenumireDocument() != null ?
                constructIcon("fas", "fa-file") : constructIcon();
        HtmlContainer mobIconDocType = document.getDenumireDocument() != null ?
                constructIcon("fas", "fa-file") : constructIcon();
        Span docTypeSpan=new Span();
        Span mobDocTypeSpan=new Span();
        docTypeSpan.setText(document.getDenumireDocument());
        mobDocTypeSpan.setText(document.getDenumireDocument());
        Div docTypeDetail=new Div(iconDocType,docTypeSpan);
        Div mobDocTypeDetail=new Div(mobIconDocType,mobDocTypeSpan);

        HtmlContainer iconDocument = document.getNume() != null ?
                constructIcon("fas", "fa-info-circle") : constructIcon();
        HtmlContainer mobIconDocument = document.getNume() != null ?
                constructIcon("fas", "fa-info-circle") : constructIcon();

        ClickNotifierAnchor documentLink=new ClickNotifierAnchor();
        ClickNotifierAnchor mobDocumentLink=new ClickNotifierAnchor();
        documentLink.setText(document.getNume());
        mobDocumentLink.setText(document.getNume());
        Div documentDetail=new Div(iconDocument,documentLink);
        Div mobDocumentDetail=new Div(mobIconDocument,mobDocumentLink);

        HtmlContainer iconSendAt = document.getTrimisLa() != null ?
                constructIcon("fas", "fa-calendar-alt") : constructIcon();
        HtmlContainer mobIconSendAt = document.getTrimisLa() != null ?
                constructIcon("fas", "fa-calendar-alt") : constructIcon();
        Span sendAtSpan=new Span();
        Span mobSendAtSpan=new Span();
        sendAtSpan.setText(document.getTrimisLa());
        mobSendAtSpan.setText(document.getTrimisLa());
        Div sendAtDetail=new Div(iconSendAt,sendAtSpan);
        sendAtDetail.addClassName("width_calendar");
        Div monSendAtDetail=new Div(mobIconSendAt,mobSendAtSpan);

        HtmlContainer iconWorkflowStatus = document.getDenumireWorkflowStatus()!=null ?
                (document.getDenumireWorkflowStatus().toLowerCase().contains("respins") ?
                        constructIcon("fas", "fa-exclamation-circle","text-danger")
                        : constructIcon("fas", "fa-check","text-secondary")) : constructIcon();
        HtmlContainer mobIconWorkflowStatus = document.getDenumireWorkflowStatus()!=null ?
                (document.getDenumireWorkflowStatus().toLowerCase().contains("respins") ?
                        constructIcon("fas", "fa-exclamation-circle","text-danger")
                        : constructIcon("fas", "fa-check","text-secondary")) : constructIcon();
        Span workflowStatusSpan=new Span();
        Span mobWorkflowStatusSpan=new Span();
        workflowStatusSpan.setText(document.getDenumireWorkflowStatus());
        mobWorkflowStatusSpan.setText(document.getDenumireWorkflowStatus());
        Div workflowStatusDetail=new Div(iconWorkflowStatus,workflowStatusSpan);
        Div mobWorkflowStatusDetail=new Div(mobIconWorkflowStatus,mobWorkflowStatusSpan);

        Span documentType=new Span();
        Span mobDocumentType=new Span();
        documentType.setText(document.getDenumireDocument());
        mobDocumentType.setText(document.getDenumireDocument());
        Div documentTypeDetail=new Div(iconDocument,documentType);
        Div mobDocumentTypeDetail=new Div(mobIconDocument,mobDocumentType);

        Span rl_denumire=new Span();
        Span mobRl_denumire=new Span();
        rl_denumire.setText(document.getPersoanaReprezentantLegal());
        mobRl_denumire.setText(document.getPersoanaReprezentantLegal());
        Div rl_denumireDetail=new Div(iconDocument,rl_denumire);
        Div mobRl_denumireDetail=new Div(mobIconDocument,mobRl_denumire);

        Span rl_email=new Span();
        Span mobRl_email=new Span();
        rl_email.setText(document.getPersoanaReprezentantLegalEmail());
        mobRl_email.setText(document.getPersoanaReprezentantLegalEmail());
        Div rl_emailDetail=new Div(iconDocument,rl_email);
        Div mobRl_emailDetail=new Div(mobIconDocument,mobRl_email);

        Span rl_functie=new Span();
        Span mobRl_functie=new Span();
        rl_functie.setText(document.getPersoanaReprezentantLegalFunctie());
        mobRl_functie.setText(document.getPersoanaReprezentantLegalFunctie());
        Div rl_functieDetail=new Div(iconDocument,rl_functie);
        Div mobRl_functieDetail=new Div(mobIconDocument,mobRl_functie);

        Span c1_denumire=new Span();
        Span mobc1_denumire=new Span();
        c1_denumire.setText(document.getPersoanaContact1());
        mobc1_denumire.setText(document.getPersoanaContact1());
        Div c1_denumireDetail=new Div(iconDocument,c1_denumire);
        Div mobc1_denumireDetail=new Div(mobIconDocument,mobc1_denumire);

        Span c1_telefon=new Span();
        Span mobc1_telefon=new Span();
        c1_telefon.setText(document.getPersoanaContact1Telefon());
        mobc1_telefon.setText(document.getPersoanaContact1Telefon());
        Div c1_telefonDetail=new Div(iconDocument,c1_telefon);
        Div mobc1_telefonDetail=new Div(mobIconDocument,mobc1_telefon);

        Span c1_email=new Span();
        Span mobc1_email=new Span();
        c1_email.setText(document.getPersoanaContact1Telefon());
        mobc1_email.setText(document.getPersoanaContact1Telefon());
        Div c1_emailDetail=new Div(iconDocument,c1_email);
        Div mobc1_emailDetail=new Div(mobIconDocument,mobc1_email);


        Span c2_denumire=new Span();
        Span mobc2_denumire=new Span();
        c2_denumire.setText(document.getPersoanaContact2());
        mobc2_denumire.setText(document.getPersoanaContact2());
        Div c2_denumireDetail=new Div(iconDocument,c2_denumire);
        Div mobc2_denumireDetail=new Div(mobIconDocument,mobc2_denumire);

        Span c2_email=new Span();
        Span mobc2_email=new Span();
        c2_email.setText(document.getPersoanaContact2Email());
        mobc2_email.setText(document.getPersoanaContact2Email());
        Div c2_emailDetail=new Div(iconDocument,c2_email);
        Div mobc2_emailDetail=new Div(mobIconDocument,mobc2_email);

        Span c2_tel=new Span();
        Span mobc2_tel=new Span();
        c2_tel.setText(document.getPersoanaContact2Telefon());
        mobc2_tel.setText(document.getPersoanaContact2Telefon());
        Div c2_telDetail=new Div(iconDocument,c2_tel);
        Div mobc2_telDetail=new Div(mobIconDocument,mobc2_tel);






        HtmlContainer iconRegistrationDate = document.getDataInreg()!=null ?
                constructIcon("fas", "fa-calendar-alt") : constructIcon();
        HtmlContainer mobIconRegistrationDate = document.getDataInreg()!=null ?
                constructIcon("fas", "fa-calendar-alt") : constructIcon();
        Span registrationDateSpan=new Span();
        Span mobRegistrationDateSpan=new Span();
        registrationDateSpan.setText(document.getDataInreg());
        mobRegistrationDateSpan.setText(document.getDataInreg());
        Div registrationDateDetail=new Div(iconRegistrationDate,registrationDateSpan);
        registrationDateDetail.addClassName("width_calendar");
        Div mobRegistrationDateDetail=new Div(mobIconRegistrationDate,mobRegistrationDateSpan);

        HtmlContainer iconPaymentValue = document.getCostDocument()!=null ?
                constructIcon("fas", "fa-coins") : constructIcon();
        HtmlContainer mobIconPaymentValue = document.getCostDocument()!=null ?
                constructIcon("fas", "fa-coins") : constructIcon();
        Span paymentValueSpan=new Span();
        Span mobPaymentValueSpan=new Span();
        if(document.getValoarePlatita()!=null){
            paymentValueSpan.setText(document.getValoarePlatita().toString());
            mobPaymentValueSpan.setText(document.getValoarePlatita().toString());
        }

        Div paymentValueDetail=new Div(iconPaymentValue,paymentValueSpan);
        Div mobPaymentValueDetail=new Div(iconPaymentValue,mobPaymentValueSpan);
        int rowIndex = index.getAndIncrement();

        if(getPresenter().getShowPaymentCol()!=null && getPresenter().getShowPaymentCol().equals("true") ){
            serviceDocumentsTable.addRow(new Label( rowIndex + ""),
                    documentDetail,
                    documentTypeDetail,
                    rl_denumireDetail,
                    rl_functieDetail,
                    rl_emailDetail,
                    c1_denumireDetail,
                    c1_emailDetail,
                    c1_telefonDetail,
                    c2_denumireDetail,
                    c2_emailDetail,
                    c2_telDetail,
                    sendAtDetail,
                    workflowStatusDetail
            );
        }else{
            serviceDocumentsTable.addRow(new Label( rowIndex + ""),
                    documentDetail,
                    documentTypeDetail,
                    rl_denumireDetail,
                    rl_functieDetail,
                    rl_emailDetail,
                    c1_denumireDetail,
                    c1_emailDetail,
                    c1_telefonDetail,
                    c2_denumireDetail,
                    c2_emailDetail,
                    c2_telDetail,
                    sendAtDetail,
                    workflowStatusDetail
            );
        }



        Map<String, Component> mobileRowMap = new LinkedHashMap<>();
        mobileRowMap.put("myaccount.my.requests.view.service.index.label", new Label(rowIndex + ""));
        mobileRowMap.put("myaccount.my.requests.view.service.documentname.label", mobDocumentDetail);
        mobileRowMap.put("myaccount.my.requests.view.service.documentType.label", mobDocumentTypeDetail);
        mobileRowMap.put("myaccount.my.requests.view.service.rl_denumire.label", mobRl_denumireDetail);
        mobileRowMap.put("myaccount.my.requests.view.service.rl_functie.label", mobRl_functieDetail);
        mobileRowMap.put("myaccount.my.requests.view.service.rl_email.label", mobRl_emailDetail);
        mobileRowMap.put("myaccount.my.requests.view.service.c1_denumire.label", mobc1_denumireDetail);
        mobileRowMap.put("myaccount.my.requests.view.service.c1_email.label", mobc1_emailDetail);
        mobileRowMap.put("myaccount.my.requests.view.service.c1_tel.label", mobc1_telefonDetail);
        mobileRowMap.put("myaccount.my.requests.view.service.c2_denumire.label", mobc2_denumireDetail);
        mobileRowMap.put("myaccount.my.requests.view.service.c2_email.label", mobc2_emailDetail);
        mobileRowMap.put("myaccount.my.requests.view.service.c2_telefon.label", mobc2_telDetail);
        mobileRowMap.put("myaccount.my.requests.view.service.datastart.label", monSendAtDetail);
        mobileRowMap.put("myaccount.my.requests.view.service.documentstatus.label", mobWorkflowStatusDetail);

        mobServiceDocumentsTable.addRow(mobileRowMap);

    }


    private void styleMyRequestsTable() {
        mobServiceDocumentsTable.addClassNames("table_mob_2col_9", "table_anre_responsive");
        serviceDocumentsTableContainer.addClassNames("table-responsive", "table-icons", "table-blue-line", "mb-5", "table_anre");
        serviceDocumentsTable.setTableClassNames("table mb-0");
    }
    private void styleNewRequestButton() {
        buttonContainer.addClassNames("new_request");
        anchorContainer.setHref("javascript:void(0);");
        anchorContainer.addClassNames("btn","btn-secondary","btn-block","font-weight-bold");
        anchorContainer.add(new Text("myaccount.my.requests.view.service.newrequest.button"));
        HtmlContainer iconArrowNext= new HtmlContainer("i");
        iconArrowNext.addClassNames("fas","fa-arrow-alt-circle-right");
        anchorContainer.add(iconArrowNext);
    }




}
