package ro.bithat.dms.microservices.portal.ecitizen.useraccount.gui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import ro.bithat.dms.microservices.dmsws.file.PortalFile;
import ro.bithat.dms.microservices.portal.ecitizen.gui.component.TableContainerDiv;
import ro.bithat.dms.microservices.portal.ecitizen.gui.ContentContainerView;
import ro.bithat.dms.microservices.portal.ecitizen.gui.component.MobTableContainerDiv;
import ro.bithat.dms.passiveview.boot.I18NProviderStatic;
import ro.bithat.dms.passiveview.component.html.ClickNotifierAnchor;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Ps4ECitizenMyDocumentsView extends ContentContainerView<Ps4ECitizenMyDocumentsPresenter> {


    private TableContainerDiv serviceDocumentsTable =
            new TableContainerDiv("myaccount.my.documents.view.service.documentname.label",
                    "myaccount.my.documents.view.service.doctypename.label",
                    "myaccount.my.documents.view.service.paymentdate.label",
                    "myaccount.my.documents.view.service.duedate.label"
            );
    private Div serviceDocumentsTableContainer = new Div(serviceDocumentsTable);
    private MobTableContainerDiv mobServiceDocumentsTable = new MobTableContainerDiv();
    private Div formContainer = new Div(serviceDocumentsTableContainer, mobServiceDocumentsTable);
   // private ClickNotifierAnchor anchorContainer = new ClickNotifierAnchor();
  //  private Div buttonContainer = new Div(anchorContainer);


    @Override
    public void beforeBinding() {
        setContentPageTile(I18NProviderStatic.getTranslation("ps4.ecetatean.breadcrumb.myaccount.mydocuments.page.title"));
        setServicesListHeaderIcon("/icons/document.png");
        formContainer.addClassNames("my_requests", "table_scroll");

       // styleNewRequestButton();
        Div clearFix = new Div();
        clearFix.addClassNames("clearfix", "gap-20");
        getServiceListContainer().add(formContainer, clearFix);
        styleMyDocumentsTable();

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

    private void styleMyDocumentsTable() {
        mobServiceDocumentsTable.addClassNames("table_mob_2col", "table_anre_responsive");
        serviceDocumentsTableContainer.addClassNames("table-responsive", "table-icons", "table-blue-line", "table_anre", "mb-5");
        serviceDocumentsTable.setTableClassNames("table mb-0");
    }
//    private void styleNewRequestButton() {
//        buttonContainer.addClassNames("new_request");
//        anchorContainer.setHref("javascript:void(0);");
//
//        anchorContainer.addClassNames("btn","btn-secondary","btn-block","font-weight-bold");
//        anchorContainer.add(new Text("myaccount.my.documents.view.service.newrequest.button"));
//        HtmlContainer iconArrowNext= new HtmlContainer("i");
//        iconArrowNext.addClassNames("fas","fa-arrow-alt-circle-right");
//        anchorContainer.add(iconArrowNext);
//    }

    public void setMyDocumentsTable(List<PortalFile> myDocuments) {
        AtomicInteger index = new AtomicInteger(1);
        myDocuments.stream().forEach(document -> setDocumentTableRow(document, index));
    }


    private void setDocumentTableRow(PortalFile document, AtomicInteger index) {

        HtmlContainer iconDocName = new HtmlContainer("i");
        if(document.getNumeBaza()!=null){
            iconDocName.addClassNames("fas", "fa-file");
        }
        Span docNameSpan=new Span();
        docNameSpan.setText(document.getNumeBaza());
        Div docNameDetail=new Div(iconDocName,docNameSpan);


        HtmlContainer iconDocType = new HtmlContainer("i");
        if(document.getDenumireDocument()!=null){
            iconDocType.addClassNames("fas", "fa-info-circle");
        }
        Span docTypeSpan=new Span();
        docTypeSpan.setText(document.getDenumireDocument());
        Div docTypeDetail=new Div(iconDocType,docTypeSpan);


        HtmlContainer iconPaymentDate = new HtmlContainer("i");
        if(document.getCreatLaStr()!=null){
            iconPaymentDate.addClassNames("fas", "fa-calendar-alt");
        }
        Span paymentDateSpan=new Span();
        paymentDateSpan.setText(document.getCreatLaStr());
        Div paymentDateDetail=new Div(iconPaymentDate,paymentDateSpan);

        HtmlContainer iconDueDate = new HtmlContainer("i");
        if(document.getDataExpirare()!=null){
            iconDueDate.addClassNames("fas", "fa-calendar-alt");
        }
        Span dueDateSpan=new Span();
        dueDateSpan.setText(document.getDataExpirare());
        Div dueDateDetail=new Div(iconDueDate,dueDateSpan);

        serviceDocumentsTable.addRow(
                docNameDetail,
                docTypeDetail,
                paymentDateDetail,
                dueDateDetail
        );

        //mobile
        HtmlContainer mobIconDocName = new HtmlContainer("i");
        if(document.getNumeBaza()!=null){
            mobIconDocName.addClassNames("fas", "fa-file");
        }
        Span mobDocNameSpan=new Span();
        mobDocNameSpan.setText(document.getNumeBaza());
        Div mobDocNameDetail=new Div(mobIconDocName,mobDocNameSpan);

        HtmlContainer mobIconDocType = new HtmlContainer("i");
        if(document.getDenumireDocument()!=null){
            mobIconDocType.addClassNames("fas", "fa-info-circle");
        }
        Span mobDocTypeSpan=new Span();
        mobDocTypeSpan.setText(document.getDenumireDocument());
        Div mobDocTypeDetail=new Div(mobIconDocType,mobDocTypeSpan);

        HtmlContainer mobIconPaymentDate = new HtmlContainer("i");
        if(document.getCreatLaStr()!=null){
            mobIconPaymentDate.addClassNames("fas", "fa-calendar-alt");
        }
        Span mobPaymentDateSpan=new Span();
        mobPaymentDateSpan.setText(document.getCreatLaStr());
        Div mobPaymentDateDetail=new Div(mobIconPaymentDate,mobPaymentDateSpan);

        HtmlContainer mobIconDueDate = new HtmlContainer("i");
        if(document.getDataExpirare()!=null){
            mobIconDueDate.addClassNames("fas", "fa-calendar-alt");
        }
        Span mobDueDateSpan=new Span();
        mobDueDateSpan.setText(document.getDataExpirare());
        Div mobDueDateDetail=new Div(mobIconDueDate,mobDueDateSpan);

        Map<String, Component> mobileRowMap = new LinkedHashMap<>();
        mobileRowMap.put("myaccount.my.documents.view.service.documentname.label", mobDocNameDetail);
        mobileRowMap.put("myaccount.my.documents.view.service.doctypename.label", mobDocTypeDetail);
        mobileRowMap.put("myaccount.my.documents.view.service.paymentdate.label", mobPaymentDateDetail);
        mobileRowMap.put("myaccount.my.documents.view.service.duedate.label", mobDueDateDetail);
        mobServiceDocumentsTable.addRow(mobileRowMap);
      //mobile
    }
}
