package ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.page.BrowserWindowResizeEvent;
import com.vaadin.flow.component.page.ExtendedClientDetails;
import org.springframework.beans.factory.annotation.Value;
import ro.bithat.dms.microservices.dmsws.file.PortalFile;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.Corespondenta;
import ro.bithat.dms.microservices.portal.ecitizen.gui.component.TableContainerDiv;
import ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui.Ps4ECitizenServiceRequestReviewRoute;
import ro.bithat.dms.microservices.portal.ecitizen.gui.ContentContainerView;
import ro.bithat.dms.microservices.portal.ecitizen.gui.component.MobTableContainerDiv;
import ro.bithat.dms.microservices.portal.ecitizen.gui.template.ClientDetailsObserver;
import ro.bithat.dms.microservices.portal.ecitizen.gui.template.WindowResizeObserver;
import ro.bithat.dms.microservices.portal.ecitizen.onlineservices.gui.component.SearchContainer;
import ro.bithat.dms.passiveview.QueryParameterUtil;
import ro.bithat.dms.passiveview.StreamResourceUtil;
import ro.bithat.dms.passiveview.VaadinClientUrlUtil;
import ro.bithat.dms.passiveview.boot.I18NProviderStatic;
import ro.bithat.dms.passiveview.component.html.ClickNotifierAnchor;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Ps4ECorespondentaFisiereView extends ContentContainerView<Ps4ECorespondentaFisierePresenter>   {
    private SearchContainer searchFormContainer = new SearchContainer(this);

    private Div filterContainer = new Div(searchFormContainer);

    private TableContainerDiv serviceDocumentsTable =
            new TableContainerDiv(

                    "Sens",
                    "Document",
                    "Numar",
                    "Nr. inreg",
                    "Data inreg."
            );
    private Div serviceDocumentsTableContainer = new Div(serviceDocumentsTable);

    private MobTableContainerDiv mobServiceDocumentsTable = new MobTableContainerDiv();

    private Div formContainer = new Div(serviceDocumentsTableContainer);
    private ClickNotifierAnchor anchorContainer = new ClickNotifierAnchor();
    private Div buttonContainer = new Div(anchorContainer);



    @Override
    public void beforeBinding() {
        UI.getCurrent().getPage().executeJavaScript("window.parent.parent.scrollTo(0,0);");
        //13.07.2021 - NG - ANRE - adaugare camp de cautare
        setContentPageTile(I18NProviderStatic.getTranslation("ps4.ecetatean.breadcrumb.corespondenta.title"));
        setServicesListHeaderIcon("/icons/document.png");
        formContainer.addClassNames("my_documents");

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
    public void setMyRequestsTable(List<Corespondenta> myDocuments) {
        serviceDocumentsTable.clearContent();
        AtomicInteger index = new AtomicInteger(1);
        myDocuments.stream().forEach(document -> setDocumentTableRow(document, index));
    /*    if(Optional.ofNullable(getClientWidth()).isPresent()) {
            displayForWidth(getClientWidth());
        }*/
    }

    private void setDocumentTableRow(Corespondenta document, AtomicInteger index) {

        ClickNotifierAnchor anchorDocumentPreviewFile= new ClickNotifierAnchor();

        if(Optional.ofNullable(document.getDownloadLink()).isPresent() &&
                !document.getDownloadLink().isEmpty()) {
            anchorDocumentPreviewFile.setHref(
                    StreamResourceUtil.getStreamResource(document.getDocument(), document.getDownloadLink()));
            anchorDocumentPreviewFile.setTarget("_blank");
            HtmlContainer iconElementButtonPreview = new HtmlContainer("i");
            iconElementButtonPreview.addClassNames("fas","fa-file-pdf");
            anchorDocumentPreviewFile.add(iconElementButtonPreview);
         }
         anchorDocumentPreviewFile.setText(document.getDocument());
       // if(getPresenter().getShowPaymentCol()!=null && getPresenter().getShowPaymentCol().equals("true") ){
            serviceDocumentsTable.addRow(
                    new Label(document.getSens()),
                    anchorDocumentPreviewFile,
                    new Label(document.getVersiune().toString()),
                    new Label(document.getNrInreg()),
                    new Label(document.getDataInreg())

            );




        Map<String, Component> mobileRowMap = new LinkedHashMap<>();
        mobileRowMap.put("Sens",new Label(document.getSens()));
        mobileRowMap.put("Document",  new Label(document.getDocument()));
        mobileRowMap.put("Numar", new Label(document.getVersiune().toString()));
        mobileRowMap.put("Nr. inreg", new Label(document.getNrInreg()));
        mobileRowMap.put("Data inreg", new Label(document.getDataInreg()));


        mobServiceDocumentsTable.addRow(mobileRowMap);

    }


    private void styleMyRequestsTable() {
        mobServiceDocumentsTable.addClassName("table_mob_2col_9");
        serviceDocumentsTableContainer.addClassNames("table-responsive", "table-icons", "table-blue-line", "mt-3", "mb-5");
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
