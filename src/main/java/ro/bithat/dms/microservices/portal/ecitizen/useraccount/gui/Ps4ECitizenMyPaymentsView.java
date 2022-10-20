package ro.bithat.dms.microservices.portal.ecitizen.useraccount.gui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import ro.bithat.dms.microservices.dmsws.ps4.paymentintegration.imported.PlataResponse;
import ro.bithat.dms.microservices.portal.ecitizen.gui.component.TableContainerDiv;
import ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui.Ps4ECitizenServiceRequestReviewRoute;
import ro.bithat.dms.microservices.portal.ecitizen.gui.ContentContainerView;
import ro.bithat.dms.microservices.portal.ecitizen.gui.component.MobTableContainerDiv;
import ro.bithat.dms.passiveview.QueryParameterUtil;
import ro.bithat.dms.passiveview.VaadinClientUrlUtil;
import ro.bithat.dms.passiveview.boot.I18NProviderStatic;
import ro.bithat.dms.passiveview.component.html.ClickNotifierAnchor;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Ps4ECitizenMyPaymentsView extends ContentContainerView<Ps4ECitizenMyPaymentsPresenter> {

    private TableContainerDiv serviceDocumentsTable =
            new TableContainerDiv("myaccount.my.payments.view.service.index.label",
                    "myaccount.my.payments.view.service.classname.label",
                    "myaccount.my.payments.view.service.documentname.label",
                    "myaccount.my.payments.view.service.datastart.label",
                    "myaccount.my.payments.view.service.documentstatus.label",
                    "myaccount.my.payments.view.service.paymentvalue.label"
            );
    private Div serviceDocumentsTableContainer = new Div(serviceDocumentsTable);
    private MobTableContainerDiv mobServiceDocumentsTable = new MobTableContainerDiv();
    private Div formContainer = new Div(serviceDocumentsTableContainer, mobServiceDocumentsTable);
    private ClickNotifierAnchor anchorContainer = new ClickNotifierAnchor();
    private Div buttonContainer = new Div(anchorContainer);


    @Override
    public void beforeBinding() {
        setContentPageTile(I18NProviderStatic.getTranslation("ps4.ecetatean.breadcrumb.myaccount.mypayments.page.title"));
        setServicesListHeaderIcon("/icons/document.png");
        formContainer.addClassNames("my_documents");

        styleNewRequestButton();

        Div clearFix = new Div();
        clearFix.addClassNames("clearfix", "gap_20");
        getServiceListContainer().add(formContainer, clearFix, buttonContainer);
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

    public void setMyPaymentsTable(List<PlataResponse> myPayments) {
        AtomicInteger index = new AtomicInteger(1);
        myPayments.stream().forEach(payment -> setPaymentTableRow(payment, index));
    }
    private void setPaymentTableRow(PlataResponse payment, AtomicInteger index) {

        ClickNotifierAnchor categoryLink = constructClickNotifierAnchor(payment.getCategorie(), "inherit-text-color");
        ClickNotifierAnchor mobCategoryLink = constructClickNotifierAnchor(payment.getCategorie(), "inherit-text-color");
        Span categoryIcon= constructSpan("pictograma");
        Span mobCategoryIcon= constructSpan("pictograma");
        Div categoryDetail=new Div(categoryIcon,categoryLink);
        Div mobCategoryDetail=new Div(mobCategoryIcon,mobCategoryLink);
        //TODO check this !!!!! should be class convention name lowercase and space underline
        if(payment.getCategorie()!=null){
            categoryDetail.addClassNames("picto_parent", "urbanism");
            mobCategoryDetail.addClassNames("picto_parent", "urbanism");
        }

        HtmlContainer iconDocument = payment.getNumeFisier()!=null ?
                constructIcon("fas", "fa-info-circle") : constructIcon();
        HtmlContainer mobIconDocument = payment.getNumeFisier()!=null ?
                constructIcon("fas", "fa-info-circle") : constructIcon();
        ClickNotifierAnchor documentLink= new ClickNotifierAnchor();
        ClickNotifierAnchor mobDocumentLink= new ClickNotifierAnchor();
        documentLink.setText(payment.getNumeFisier());
        mobDocumentLink.setText(payment.getNumeFisier());
        Div documentDetail=new Div(iconDocument,documentLink);
        Div mobDocumentDetail=new Div(mobIconDocument,mobDocumentLink);

        if(Optional.ofNullable(payment.getIdDocument()).isPresent()
                && Optional.ofNullable(payment.getIdClasaDocument()).isPresent()
                && Optional.ofNullable(payment.getId_fisier()).isPresent()) {
            Map<String, Object> filterPageParameters = new HashMap<>();
            filterPageParameters.put("tipDocument", payment.getIdClasaDocument());
            filterPageParameters.put("document", payment.getIdDocument());
            filterPageParameters.put("request", payment.getId_fisier());

            categoryLink.getStyle().set("cursor", "pointer");
            mobCategoryLink.getStyle().set("cursor", "pointer");
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
        }

        HtmlContainer iconSendAt = payment.getData_plata_str()!=null ?
                constructIcon("fas", "fa-calendar-alt") : constructIcon();
        HtmlContainer mobIconSendAt = payment.getData_plata_str()!=null ?
                constructIcon("fas", "fa-calendar-alt") : constructIcon();
        Span sendAtSpan=new Span();
        Span mobSendAtSpan=new Span();
        sendAtSpan.setText(payment.getData_plata_str());
        mobSendAtSpan.setText(payment.getData_plata_str());
        Div sendAtDetail=new Div(iconSendAt,sendAtSpan);
        Div mobSendAtDetail=new Div(mobIconSendAt,mobSendAtSpan);

        HtmlContainer iconWorkflowStatus = payment.getStatusFlux()!=null ?
                (payment.getStatusFlux().toLowerCase().contains("respins") ?
                        constructIcon("fas", "fa-exclamation-circle","text-danger")
                        : constructIcon("fas", "fa-check","text-secondary")) : constructIcon();
        HtmlContainer mobIconWorkflowStatus = payment.getStatusFlux()!=null ?
                (payment.getStatusFlux().toLowerCase().contains("respins") ?
                        constructIcon("fas", "fa-exclamation-circle","text-danger")
                        : constructIcon("fas", "fa-check","text-secondary")) : constructIcon();
        Span workflowStatusSpan=new Span();
        Span mobWorkflowStatusSpan=new Span();
        workflowStatusSpan.setText(payment.getStatusFlux());
        mobWorkflowStatusSpan.setText(payment.getStatusFlux());
        Div workflowStatusDetail=new Div(iconWorkflowStatus,workflowStatusSpan);
        Div mobWorkflowStatusDetail=new Div(mobIconWorkflowStatus,mobWorkflowStatusSpan);

        HtmlContainer iconPaymentValue = payment.getSuma()!=null ?
                constructIcon("fas", "fa-coins") : constructIcon();
        HtmlContainer mobIconPaymentValue = payment.getSuma()!=null ?
                constructIcon("fas", "fa-coins") : constructIcon();
        Span paymentValueSpan=new Span();
        Span mobPaymentValueSpan=new Span();
        /*if(document.getValoarePlatita()!=null && document.getValoarePlatita()!=0.0){

        }*/
        paymentValueSpan.setText(payment.getSuma().toString());
        mobPaymentValueSpan.setText(payment.getSuma().toString());
        Div paymentValueDetail=new Div(iconPaymentValue,paymentValueSpan);
        Div mobPaymentValueDetail=new Div(mobIconPaymentValue,mobPaymentValueSpan);

        int rowIndex = index.getAndIncrement();
        serviceDocumentsTable.addRow(new Label(rowIndex + ""),
                categoryDetail,
                documentDetail,
                sendAtDetail,
                workflowStatusDetail,
                paymentValueDetail
        );


        Map<String, Component> mobileRowMap = new LinkedHashMap<>();
        mobileRowMap.put("myaccount.my.payments.view.service.index.label", new Label(rowIndex + ""));
        mobileRowMap.put("myaccount.my.payments.view.service.classname.label", mobCategoryDetail);
        mobileRowMap.put("myaccount.my.payments.view.service.documentname.label", mobDocumentDetail);
        mobileRowMap.put("myaccount.my.payments.view.service.datastart.label", mobSendAtDetail);
        mobileRowMap.put("myaccount.my.payments.view.service.documentstatus.label", mobWorkflowStatusDetail);
        mobileRowMap.put("myaccount.my.payments.view.service.paymentvalue.label", mobPaymentValueDetail);
        mobServiceDocumentsTable.addRow(mobileRowMap);

    }
    private void styleMyRequestsTable() {
        mobServiceDocumentsTable.addClassNames("table_mob_2col_6", "table_anre_responsive");
        serviceDocumentsTableContainer.addClassNames("table-responsive", "table-icons", "table-blue-line", "table_anre", "mb-5");
        serviceDocumentsTable.setTableClassNames("table mb-0");
    }
    private void styleNewRequestButton() {
        /*buttonContainer.addClassNames("new_request");
        anchorContainer.setHref("javascript:void(0);");
        anchorContainer.addClassNames("btn","btn_green","min_width250");
        anchorContainer.add(new Text("myaccount.my.payments.view.service.home.button"));*/
        /*HtmlContainer iconArrowNext= new HtmlContainer("i");
        iconArrowNext.addClassNames("fas","fa-arrow-alt-circle-right");
        anchorContainer.add(iconArrowNext);*/
    }
}
