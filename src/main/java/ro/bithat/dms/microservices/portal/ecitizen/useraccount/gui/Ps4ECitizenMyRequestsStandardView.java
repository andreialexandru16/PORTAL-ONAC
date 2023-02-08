package ro.bithat.dms.microservices.portal.ecitizen.useraccount.gui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import ro.bithat.dms.microservices.dmsws.file.PortalFile;
import ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui.Ps4ECitizenServiceRequestReviewRoute;
import ro.bithat.dms.microservices.portal.ecitizen.gui.ContentContainerView;
import ro.bithat.dms.microservices.portal.ecitizen.gui.component.MobTableContainerDiv;
import ro.bithat.dms.microservices.portal.ecitizen.gui.component.TableContainerDiv;
import ro.bithat.dms.microservices.portal.ecitizen.onlineservices.gui.component.SearchContainer;
import ro.bithat.dms.passiveview.QueryParameterUtil;
import ro.bithat.dms.passiveview.VaadinClientUrlUtil;
import ro.bithat.dms.passiveview.boot.I18NProviderStatic;
import ro.bithat.dms.passiveview.component.html.ClickNotifierAnchor;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Ps4ECitizenMyRequestsStandardView extends ContentContainerView<Ps4ECitizenMyRequestsStandardPresenter> {
    private SearchContainer searchFormContainer = new SearchContainer(this);

    private Div filterContainer = new Div(searchFormContainer);

    private TableContainerDiv serviceDocumentsTable =
            new TableContainerDiv(

                    "myaccount.my.requests.view.service.index.label",
                    "myaccount.my.requests.view.service.registrationnumber.label",
                    "myaccount.my.requests.view.service.registrationdate.label",
                    "myaccount.my.requests.view.service.classname.label",
                    "myaccount.my.requests.view.service.doctypename.label",
                    "myaccount.my.requests.view.service.documentname.label",
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
        addComponentAsFirst(filterContainer);
        setContentPageTile(I18NProviderStatic.getTranslation("ps4.ecetatean.breadcrumb.myaccount.myrequestsStandard.page.title"));
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

        HtmlContainer iconDocument = document.getNumeBaza() != null ?
                constructIcon("fas", "fa-info-circle") : constructIcon();
        HtmlContainer mobIconDocument = document.getNumeBaza() != null ?
                constructIcon("fas", "fa-info-circle") : constructIcon();
        ClickNotifierAnchor documentLink=new ClickNotifierAnchor();
        ClickNotifierAnchor mobDocumentLink=new ClickNotifierAnchor();
        documentLink.setText(document.getNumeBaza());
        mobDocumentLink.setText(document.getNumeBaza());
        Div documentDetail=new Div(iconDocument,documentLink);
        Div mobDocumentDetail=new Div(mobIconDocument,mobDocumentLink);

        if(Optional.ofNullable(document.getIdDocument()).isPresent()
                && Optional.ofNullable(document.getIdClasaDocument()).isPresent()
                && Optional.ofNullable(document.getId()).isPresent()) {
            Map<String, Object> filterPageParameters = new HashMap<>();
            filterPageParameters.put("tipDocument", document.getIdClasaDocument());
            filterPageParameters.put("document", document.getIdDocument());
            filterPageParameters.put("request", document.getId());

            categoryLink.getStyle().set("cursor", "pointer");
            mobCategoryLink.getElement().getStyle().set("cursor", "pointer");
            categoryLink.addClickListener(e
                    -> VaadinClientUrlUtil.setLocation(QueryParameterUtil.getRelativePathWithQueryParameters(filterPageParameters, Ps4ECitizenServiceRequestReviewRoute.class)));
            mobCategoryLink.addClickListener(e
                    -> VaadinClientUrlUtil.setLocation(QueryParameterUtil.getRelativePathWithQueryParameters(filterPageParameters, Ps4ECitizenServiceRequestReviewRoute.class)));
            documentLink.getStyle().set("cursor", "pointer");
            mobDocumentLink.getStyle().set("cursor", "pointer");
            documentLink.addClickListener(e
                    -> VaadinClientUrlUtil.setLocation(QueryParameterUtil.getRelativePathWithQueryParameters(filterPageParameters, Ps4ECitizenServiceRequestReviewRoute.class)));
            mobDocumentLink.addClickListener(e
                    -> VaadinClientUrlUtil.setLocation(QueryParameterUtil.getRelativePathWithQueryParameters(filterPageParameters, Ps4ECitizenServiceRequestReviewRoute.class)));
//            categoryLink.setHref(QueryParameterUtil.getRelativePathWithQueryParameters(filterPageParameters, Ps4ECitizenServiceRequestReviewRoute.class));
//            documentLink.setHref(QueryParameterUtil.getRelativePathWithQueryParameters(filterPageParameters, Ps4ECitizenServiceRequestReviewRoute.class));
        }

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
                    new Label(document.getNrInreg()),
                    registrationDateDetail,
                    categoryDetail,
                    docTypeDetail,
                    documentDetail,
                    sendAtDetail,
                    workflowStatusDetail,
                    paymentValueDetail
            );
        }else{
            serviceDocumentsTable.addRow(new Label( rowIndex + ""),
                    new Label(document.getNrInreg()),
                    registrationDateDetail,
                    categoryDetail,
                    docTypeDetail,
                    documentDetail,
                    sendAtDetail,
                    workflowStatusDetail
            );
        }



        Map<String, Component> mobileRowMap = new LinkedHashMap<>();
        mobileRowMap.put("myaccount.my.requests.view.service.index.label", new Label(rowIndex + ""));
        mobileRowMap.put("myaccount.my.requests.view.service.registrationnumber.label", new Label(document.getNrInreg()));
        mobileRowMap.put("myaccount.my.requests.view.service.registrationdate.label", mobRegistrationDateDetail);
        mobileRowMap.put("myaccount.my.requests.view.service.classname.label", mobCategoryDetail);
        mobileRowMap.put("myaccount.my.requests.view.service.doctypename.label", mobDocTypeDetail);
        mobileRowMap.put("myaccount.my.requests.view.service.documentname.label", mobDocumentDetail);
        mobileRowMap.put("myaccount.my.requests.view.service.datastart.label", monSendAtDetail);
        mobileRowMap.put("myaccount.my.requests.view.service.documentstatus.label", mobWorkflowStatusDetail);
        if(getPresenter().getShowPaymentCol()!=null && getPresenter().getShowPaymentCol().equals("true") ){
            mobileRowMap.put("myaccount.my.requests.view.service.paymentvalue.label", mobPaymentValueDetail);

        }
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
