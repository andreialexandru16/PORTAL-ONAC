package ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui.component;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.*;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.Document;
import ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui.Ps4ECitizenServiceNewRequestRoute;
import ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui.Ps4ECitizenServiceRequestRoute;
import ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui.Ps4ECitizenServiceRoute;
import ro.bithat.dms.microservices.portal.ecitizen.gui.ContentContainerView;
import ro.bithat.dms.microservices.portal.ecitizen.gui.component.MobTableContainerDiv;
import ro.bithat.dms.microservices.portal.ecitizen.gui.component.TableContainerDiv;
import ro.bithat.dms.microservices.portal.ecitizen.website.models.FisierDraftExtended;
import ro.bithat.dms.microservices.portal.ecitizen.website.models.Formular;
import ro.bithat.dms.microservices.portal.ecitizen.website.models.Procedura;
import ro.bithat.dms.microservices.portal.ecitizen.website.models.ProceduraList;
import ro.bithat.dms.passiveview.QueryParameterUtil;
import ro.bithat.dms.passiveview.VaadinClientUrlUtil;
import ro.bithat.dms.passiveview.boot.I18NProviderStatic;
import ro.bithat.dms.passiveview.component.html.ClickNotifierAnchor;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Ps4ECitizenProcedureFormsView extends ContentContainerView<Ps4ECitizenProcedureFormsPresenter> {


    private ComboBox<Procedura> serviceType = new ComboBox<>("");

    private Div serviceTypeContainerFilter = new Div(serviceType);

    private Div filterContainerRow = new Div(serviceTypeContainerFilter);

    private Div filterContainer = new Div(filterContainerRow);

    private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    private TableContainerDiv serviceDocumentsTable =
            new TableContainerDiv(
                    "document.type.service.request.view.service.index.label",
                    "my.draft.table.headers.nume",
                    "my.draft.table.headers.denumire.document",
                    "my.draft.table.headers.creat.la",
                    "my.draft.table.headers.transmis.la",
                    "my.draft.table.headers.actiuni"
            );
    private Div serviceDocumentsTableContainer = new Div(serviceDocumentsTable);

    private MobTableContainerDiv mobServiceDocumentsTable = new MobTableContainerDiv();

    private Div formContainer = new Div(serviceDocumentsTableContainer, mobServiceDocumentsTable);
    private ClickNotifierAnchor anchorContainer = new ClickNotifierAnchor();
    private Div buttonContainer = new Div(anchorContainer);

    @Override
    public void beforeBinding() {
        setServicesListHeaderIcon("/icons/document.png");
        setContentPageTile(I18NProviderStatic.getTranslation("forms"));
        filterContainer.addClassName("filter_container");
        filterContainerRow.addClassName("row");

        serviceType.setItemLabelGenerator(Procedura::getNume);
        serviceType.setAllowCustomValue(false);
        serviceType.setPlaceholder(getTranslation("procedura"));
        serviceType.addClassNames("vaadin-ps4-theme", "form-control");
        serviceType.getStyle().set("display", "flex");
        serviceType.getStyle().set("background-color", "transparent");
        serviceTypeContainerFilter.addClassNames("col-md-6", "col-lg-5");
        UI.getCurrent().getPage().executeJs("addThemeSmall($0);", serviceType.getElement());

        addComponentAsFirst(filterContainer);
        Div clearFix = new Div();
        clearFix.addClassNames("clearfix", "gap_20");
        styleMyRequestsTable();
        getServiceListContainer().add(formContainer, clearFix);
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

    public void setMyRequestsTable(List<Formular> myDocuments) {
        serviceDocumentsTable.clearContent();
        AtomicInteger index = new AtomicInteger(1);
        myDocuments.stream().forEach(document -> setDocumentTableRow(document, index));
    }
    public void setServiceTypeValue(Procedura serviceTypeValue) {
        serviceType.setValue(serviceTypeValue);
    }

    public Procedura getServiceTypeValue() {
        return serviceType.getValue();
    }

    public void buildServiceType(ProceduraList proceduri) {
        if(proceduri != null) {
            serviceType.setItems(proceduri.getProceduraList());
        }

    }
    private void setDocumentTableRow(Formular document, AtomicInteger index) {

        HtmlContainer iconDocument = document.getDenumireDocument() != null ?
                constructIcon("fas", "fa-info-circle") : constructIcon();
        HtmlContainer mobIconDocument = document.getDenumireDocument() != null ?
                constructIcon("fas", "fa-info-circle") : constructIcon();


        HtmlContainer iconCreatedAt = document.getCreatLa() != null ?
                constructIcon("fas", "fa-calendar-alt") : constructIcon();
        HtmlContainer mobIconCreatedAt = document.getCreatLa() != null ?
                constructIcon("fas", "fa-calendar-alt") : constructIcon();
        Span createdAtSpan=new Span();
        Span mobCreatedAtSpan=new Span();
        String creatLaStr = null;
        if(document.getCreatLa() !=null){
            creatLaStr = sdf.format(document.getCreatLa());
        }
        createdAtSpan.setText(creatLaStr);
        mobCreatedAtSpan.setText(creatLaStr);
        Div createdAtDetail=new Div(iconCreatedAt,createdAtSpan);
        createdAtDetail.addClassName("width_calendar");
        Div mobCreatedAtDetail=new Div(mobIconCreatedAt,mobCreatedAtSpan);

        HtmlContainer iconSendAt = document.getDataTransmitere() != null ?
                constructIcon("fas", "fa-calendar-alt") : constructIcon();
        HtmlContainer mobIconSendAt = document.getDataTransmitere() != null ?
                constructIcon("fas", "fa-calendar-alt") : constructIcon();
        Span sendAtSpan=new Span();
        Span mobSendAtSpan=new Span();
        String sendAtStr = null;
        if(document.getDataTransmitere() !=null){
            sendAtStr = sdf.format(document.getDataTransmitere());
        }
        sendAtSpan.setText(sendAtStr);
        mobSendAtSpan.setText(sendAtStr);
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


        HtmlContainer iconEdit = document.isEdit() ? constructIcon("fas", "fa-edit") : constructIcon();
        HtmlContainer mobIconEdit = document.isEdit() ? constructIcon("fas", "fa-edit") : constructIcon();
        HtmlContainer iconAdd = document.isAdd() ? constructIcon("fas", "fa-plus") : constructIcon();
        HtmlContainer mobIconAdd = document.isAdd() ? constructIcon("fas", "fa-plus") : constructIcon();

        ClickNotifierAnchor editLink =new ClickNotifierAnchor();
        editLink.add(iconEdit);
        if(document.isEdit()) {
            editLink.addClickListener(e -> {
                Map<String, Object> filterPageParameters = new HashMap<>();
                filterPageParameters.put("request", document.getIdFisier());
                filterPageParameters.put("tipDocument", document.getIdTipDocument());
                filterPageParameters.put("document", document.getIdDocument());

                VaadinClientUrlUtil.setLocation(QueryParameterUtil.getRelativePathWithQueryParameters(filterPageParameters, Ps4ECitizenServiceNewRequestRoute.class));

            });
        }

        ClickNotifierAnchor addLink =new ClickNotifierAnchor();
        addLink.add(iconAdd);
        if(document.isAdd()) {
            addLink.addClickListener(e -> {
                Map<String, Object> filterPageParameters = new HashMap<>();
                filterPageParameters.put("tipDocument", document.getIdTipDocument());
                filterPageParameters.put("document", document.getIdDocument());

                VaadinClientUrlUtil.setLocation(QueryParameterUtil.getRelativePathWithQueryParameters(filterPageParameters, Ps4ECitizenServiceNewRequestRoute.class));

            });
        }

        ClickNotifierAnchor mobEditLink =new ClickNotifierAnchor();
        mobEditLink.add(mobIconEdit);
        editLink.getElement().setAttribute("title", getTranslation("edit.draft"));
        if(document.isEdit()) {
            mobEditLink.addClickListener(e -> {
                Map<String, Object> filterPageParameters = new HashMap<>();
                filterPageParameters.put("fileId", document.getIdFisier());
                filterPageParameters.put("tipDocument", document.getIdTipDocument());
                filterPageParameters.put("document", document.getIdDocument());

                VaadinClientUrlUtil.setLocation(QueryParameterUtil.getRelativePathWithQueryParameters(filterPageParameters, Ps4ECitizenServiceNewRequestRoute.class));

            });
        }


        ClickNotifierAnchor mobAddLink =new ClickNotifierAnchor();
        mobAddLink.add(mobIconAdd);
        if(document.isAdd()) {
            mobAddLink.addClickListener(e -> {
                Map<String, Object> filterPageParameters = new HashMap<>();
                filterPageParameters.put("tipDocument", document.getIdTipDocument());
                filterPageParameters.put("document", document.getIdDocument());

                VaadinClientUrlUtil.setLocation(QueryParameterUtil.getRelativePathWithQueryParameters(filterPageParameters, Ps4ECitizenServiceNewRequestRoute.class));

            });
        }

        Div actionDetail=new Div(addLink, editLink);
        Div mobActionDetail=new Div(mobAddLink, mobEditLink);
        serviceDocumentsTable.addRow(new Label( rowIndex + ""),
                numeDetail,
                documentTypeDetail,
                createdAtDetail,
                sendAtDetail,
                actionDetail);



        Map<String, Component> mobileRowMap = new LinkedHashMap<>();
        mobileRowMap.put("document.type.service.request.view.service.index.label", new Label(rowIndex + ""));
        mobileRowMap.put("my.draft.table.headers.nume", mobNumeDetail);
        mobileRowMap.put("my.draft.table.headers.denumire.document", mobDocumentTypeDetail);
        mobileRowMap.put("my.draft.table.headers.creat.la", mobCreatedAtDetail);
        mobileRowMap.put("my.draft.table.headers.transmis.la", mobSendAtDetail);
        mobileRowMap.put("my.draft.table.headers.actiuni", mobActionDetail);


        mobServiceDocumentsTable.addRow(mobileRowMap);

    }

    private void styleMyRequestsTable() {
        mobServiceDocumentsTable.addClassNames("table_mob_2col_9", "table_anre_responsive");
        serviceDocumentsTableContainer.addClassNames("table-responsive", "table-icons", "table-blue-line", "mb-5", "table_anre");
        serviceDocumentsTable.setTableClassNames("table mb-0");
    }

}
