package ro.bithat.dms.microservices.portal.ecitizen.useraccount.gui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui.Ps4ECitizenServiceNewRequestRoute;
import ro.bithat.dms.microservices.portal.ecitizen.gui.ContentContainerView;
import ro.bithat.dms.microservices.portal.ecitizen.gui.component.MobTableContainerDiv;
import ro.bithat.dms.microservices.portal.ecitizen.gui.component.TableContainerDiv;
import ro.bithat.dms.microservices.portal.ecitizen.onlineservices.gui.component.SearchContainer;
import ro.bithat.dms.microservices.portal.ecitizen.website.models.FisierDraftExtended;
import ro.bithat.dms.passiveview.DomEventViewSupport;
import ro.bithat.dms.passiveview.QueryParameterUtil;
import ro.bithat.dms.passiveview.VaadinClientUrlUtil;
import ro.bithat.dms.passiveview.boot.I18NProviderStatic;
import ro.bithat.dms.passiveview.component.html.ClickNotifierAnchor;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Ps4ECitizenMyDraftRequestsView extends ContentContainerView<Ps4ECitizenMyDraftRequestsPresenter> {
    private SearchContainer searchFormContainer = new SearchContainer(this);


    private TableContainerDiv serviceDocumentsTable =
            new TableContainerDiv(
                    "document.type.service.request.view.service.index.label",
                    "my.draft.table.headers.nume",
                    "my.draft.table.headers.denumire.document",
                    "my.draft.table.headers.creat.la",
                    "my.draft.table.headers.actiuni"
            );
    private Div serviceDocumentsTableContainer = new Div(serviceDocumentsTable);

    private MobTableContainerDiv mobServiceDocumentsTable = new MobTableContainerDiv();

    private Div formContainer = new Div(serviceDocumentsTableContainer, mobServiceDocumentsTable);
    private ClickNotifierAnchor anchorContainer = new ClickNotifierAnchor();
    private Div buttonContainer = new Div(anchorContainer);



    @Override
    public void beforeBinding() {

        setContentPageTile(I18NProviderStatic.getTranslation("ps4.ecetatean.breadcrumb.myaccount.mydraftrequests.page.title"));
        setServicesListHeaderIcon("/icons/document.png");
        formContainer.addClassNames("my_documents", "table_scroll");

        styleNewRequestButton();

        Div clearFix = new Div();
        clearFix.addClassNames("clearfix", "gap_20");
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

    public void setMyRequestsTable(List<FisierDraftExtended> myDocuments) {
        serviceDocumentsTable.clearContent();
        AtomicInteger index = new AtomicInteger(1);
        myDocuments.stream().forEach(document -> setDocumentTableRow(document, index));
    }

    private void setDocumentTableRow(FisierDraftExtended document, AtomicInteger index) {

        HtmlContainer iconDocument = document.getNume() != null ?
                constructIcon("fas", "fa-info-circle") : constructIcon();
        HtmlContainer mobIconDocument = document.getNume() != null ?
                constructIcon("fas", "fa-info-circle") : constructIcon();


        HtmlContainer iconSendAt = document.getCreatLa() != null ?
                constructIcon("fas", "fa-calendar-alt") : constructIcon();
        HtmlContainer mobIconSendAt = document.getCreatLa() != null ?
                constructIcon("fas", "fa-calendar-alt") : constructIcon();
        Span sendAtSpan=new Span();
        Span mobSendAtSpan=new Span();
        sendAtSpan.setText(document.getCreatLa());
        mobSendAtSpan.setText(document.getCreatLa());
        Div sendAtDetail=new Div(iconSendAt,sendAtSpan);
        sendAtDetail.addClassName("width_calendar");
        Div mobSendAtDetail=new Div(mobIconSendAt,mobSendAtSpan);



        Span nume=new Span();
        Span mobNume=new Span();
        nume.setText(document.getNume());
        mobNume.setText(document.getNume());
        Div numeDetail=new Div(nume);
        Div mobNumeDetail=new Div(mobNume);

        Span documentType=new Span();
        Span mobDocumentType=new Span();
        documentType.setText(document.getDenumireDocument());
        mobDocumentType.setText(document.getDenumireDocument());
        Div documentTypeDetail=new Div(iconDocument,documentType);
        Div mobDocumentTypeDetail=new Div(mobIconDocument,mobDocumentType);


        int rowIndex = index.getAndIncrement();


        HtmlContainer iconEdit = document.isPerioadaActiva() ? constructIcon("fas", "fa-edit") : constructIcon("");
        HtmlContainer mobIconEdit = document.isPerioadaActiva() ? constructIcon("fas", "fa-edit") : constructIcon("");
        ClickNotifierAnchor editLink =new ClickNotifierAnchor();
        editLink.add(iconEdit);
        if(document.isPerioadaActiva()) {
            editLink.addClickListener(e -> {
                Map<String, Object> filterPageParameters = new HashMap<>();
                filterPageParameters.put("request", document.getIdFisier());
                filterPageParameters.put("tipDocument", document.getIdTipDocument());
                filterPageParameters.put("document", document.getIdDocument());

                VaadinClientUrlUtil.setLocation(QueryParameterUtil.getRelativePathWithQueryParameters(filterPageParameters, Ps4ECitizenServiceNewRequestRoute.class));

            });
        }
        ClickNotifierAnchor mobEditLink =new ClickNotifierAnchor();
        mobEditLink.add(mobIconEdit);
        editLink.getElement().setAttribute("title", getTranslation("edit.draft"));
        Div actionDetail=new Div(editLink);
        Div mobActionDetail=new Div(mobEditLink);
        if(document.isPerioadaActiva()) {
            mobEditLink.addClickListener(e -> {
                Map<String, Object> filterPageParameters = new HashMap<>();
                filterPageParameters.put("fileId", document.getIdFisier());
                filterPageParameters.put("tipDocument", document.getIdTipDocument());
                filterPageParameters.put("document", document.getIdDocument());

                VaadinClientUrlUtil.setLocation(QueryParameterUtil.getRelativePathWithQueryParameters(filterPageParameters, Ps4ECitizenServiceNewRequestRoute.class));

            });
        }
        serviceDocumentsTable.addRow(new Label( rowIndex + ""),
                    numeDetail,
                    documentTypeDetail,
                    sendAtDetail,
                    actionDetail);



        Map<String, Component> mobileRowMap = new LinkedHashMap<>();
        mobileRowMap.put("document.type.service.request.view.service.index.label", new Label(rowIndex + ""));
        mobileRowMap.put("my.draft.table.headers.nume", mobNumeDetail);
        mobileRowMap.put("my.draft.table.headers.denumire.document", mobDocumentTypeDetail);
        mobileRowMap.put("my.draft.table.headers.creat.la", mobSendAtDetail);
        mobileRowMap.put("my.draft.table.headers.actiuni", mobActionDetail);


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
