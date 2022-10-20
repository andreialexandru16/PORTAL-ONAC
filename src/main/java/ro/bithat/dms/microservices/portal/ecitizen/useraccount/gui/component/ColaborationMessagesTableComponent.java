package ro.bithat.dms.microservices.portal.ecitizen.useraccount.gui.component;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import ro.bithat.dms.microservices.dmsws.colaboration.InfoMesaje;
import ro.bithat.dms.microservices.portal.ecitizen.gui.component.FlowViewDivContainer;
import ro.bithat.dms.microservices.portal.ecitizen.gui.component.MobTableContainerDiv;
import ro.bithat.dms.microservices.portal.ecitizen.gui.component.TableContainerDiv;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.gui.Ps4ECitizenColaborationMessagesRoute;
import ro.bithat.dms.passiveview.QueryParameterUtil;
import ro.bithat.dms.passiveview.VaadinClientUrlUtil;
import ro.bithat.dms.passiveview.component.html.ClickNotifierAnchor;
import ro.bithat.dms.passiveview.component.html.Strong;
import ro.bithat.dms.passiveview.mvp.FlowView;

import java.util.*;

public class ColaborationMessagesTableComponent extends FlowViewDivContainer {


    private TableContainerDiv serviceColaborationMessagesTable =
            new TableContainerDiv(
                    "myaccount.colaboration.messages.number.label",
                    "myaccount.colaboration.messages.doctypename.label",
                    "myaccount.colaboration.messages.documentname.label",
                    "myaccount.colaboration.messages.datastart.label",
                    "myaccount.colaboration.messages.documentstatus.label"

            );
    private Div serviceColaborationMessagesTableContainer = new Div(serviceColaborationMessagesTable);
    private Div headerTitle = new Div();
    private Div serviceColaborationMessagesHeader = new Div(headerTitle);
    private Div serviceColaborationMessagesContainer = new Div(serviceColaborationMessagesHeader,serviceColaborationMessagesTableContainer);
    private MobTableContainerDiv mobServiceDocumentsTable = new MobTableContainerDiv();
    private Div formContainer = new Div(serviceColaborationMessagesContainer,mobServiceDocumentsTable);

    public void displayForMobile() {
           mobServiceDocumentsTable.getStyle().remove("display");
        serviceColaborationMessagesTableContainer.getStyle().set("display", "none");
    }

    public void displayForDesktop() {
        if(serviceColaborationMessagesTableContainer.getStyle().has("display")) {
            serviceColaborationMessagesTableContainer.getStyle().remove("display");
        }
        mobServiceDocumentsTable.getStyle().set("display", "none");
    }
    public ColaborationMessagesTableComponent(FlowView view) {
        super(view);
        setStyleColaborationMessagesContainer();

        add(formContainer);
        //Needed Document table

    }
    private void setStyleColaborationMessagesContainer() {
        serviceColaborationMessagesContainer.addClassName("services_list_container");
        serviceColaborationMessagesHeader.addClassName("services_header");
        mobServiceDocumentsTable.addClassName("table_mob_2col_5");

        headerTitle.add(new H2("myaccount.colaboration.messages.table.header"));
        headerTitle.addClassName("service_title");
        serviceColaborationMessagesTableContainer.addClassNames("table-responsive", "table-icons", "table-blue-line", "mt-3", "mb-5");
        serviceColaborationMessagesTable.setTableClassNames("table mb-0");
    }
    public void setColaborationMessagesTable(List<InfoMesaje> myMessages) {
        myMessages.stream().forEach(message -> setColaborationMessagesTableRow(message));
    }

    private void setColaborationMessagesTableRow(InfoMesaje messagesInfo) {


        HtmlContainer iconDocType = new HtmlContainer("i");
        if(messagesInfo.getTipDocument()!=null){
            iconDocType.addClassNames("fas", "fa-file");
        }
        Span docTypeSpan=new Span();
        docTypeSpan.setText(messagesInfo.getTipDocument());
        Div docTypeDetail=new Div(iconDocType,docTypeSpan);

        HtmlContainer iconDocument = new HtmlContainer("i");
        if(messagesInfo.getNumeFisier()!=null){
            iconDocument.addClassNames("fas", "fa-info-circle");
        }
        ClickNotifierAnchor documentLink=new ClickNotifierAnchor();
        documentLink.setText(messagesInfo.getNumeFisier());
        Div documentDetail=new Div(iconDocument,documentLink);

        if(Optional.ofNullable(messagesInfo.getIdFisier()).isPresent()) {
            Map<String, Object> filterPageParameters = new HashMap<>();
            filterPageParameters.put("fileId", messagesInfo.getIdFisier());

            documentLink.getStyle().set("cursor", "pointer");
            documentLink.addClickListener(e
                    -> VaadinClientUrlUtil.setLocation(QueryParameterUtil.getRelativePathWithQueryParameters(filterPageParameters, Ps4ECitizenColaborationMessagesRoute.class)));
        }

        HtmlContainer iconSendAt = new HtmlContainer("i");
        if(messagesInfo.getCreatLa()!=null){
            iconSendAt.addClassNames("fas", "fa-calendar-alt");
        }
        Span sendAtSpan=new Span();
        sendAtSpan.setText(messagesInfo.getTrimisLa());
        Div sendAtDetail=new Div(iconSendAt,sendAtSpan);

        HtmlContainer iconWorkflowStatus = new HtmlContainer("i");
        if(messagesInfo.getWorkflowStatus()!=null){
            if(messagesInfo.getWorkflowStatus().toLowerCase().contains("respins")){
                iconWorkflowStatus.addClassNames("fas", "fa-exclamation-circle","text-danger");
            }
            else{
                iconWorkflowStatus.addClassNames("fas", "fa-check","text-secondary");
            }
        }

        Span workflowStatusSpan=new Span();
        workflowStatusSpan.setText(messagesInfo.getWorkflowStatus());
        Div workflowStatusDetail=new Div(iconWorkflowStatus,workflowStatusSpan);

        HtmlContainer iconNoMessages = new HtmlContainer("i");
        iconNoMessages.addClassNames("fas", "fa-envelope");
        iconNoMessages.getStyle().set("color","red");

        Strong noMessagesSpan= new Strong();
        noMessagesSpan.setText(messagesInfo.getNrMesaje().toString());
        Div noMessagesDetail=new Div(iconNoMessages,noMessagesSpan);

        if(Optional.ofNullable(messagesInfo.getIdFisier()).isPresent()) {
            Map<String, Object> filterPageParameters = new HashMap<>();
            filterPageParameters.put("fileId", messagesInfo.getIdFisier());

            docTypeDetail.getStyle().set("cursor", "pointer");
            documentLink.getStyle().set("cursor", "pointer");
            sendAtDetail.getStyle().set("cursor", "pointer");
            workflowStatusDetail.getStyle().set("cursor", "pointer");
            noMessagesDetail.getStyle().set("cursor", "pointer");
            docTypeDetail.addClickListener(e
                    -> VaadinClientUrlUtil.setLocationToMessages(QueryParameterUtil.getRelativePathWithQueryParameters(filterPageParameters, Ps4ECitizenColaborationMessagesRoute.class)));
            documentDetail.addClickListener(e
                    -> VaadinClientUrlUtil.setLocationToMessages(QueryParameterUtil.getRelativePathWithQueryParameters(filterPageParameters, Ps4ECitizenColaborationMessagesRoute.class)));
            sendAtDetail.addClickListener(e
                    -> VaadinClientUrlUtil.setLocationToMessages(QueryParameterUtil.getRelativePathWithQueryParameters(filterPageParameters, Ps4ECitizenColaborationMessagesRoute.class)));
            workflowStatusDetail.addClickListener(e
                    -> VaadinClientUrlUtil.setLocationToMessages(QueryParameterUtil.getRelativePathWithQueryParameters(filterPageParameters, Ps4ECitizenColaborationMessagesRoute.class)));
            noMessagesDetail.addClickListener(e
                    -> VaadinClientUrlUtil.setLocationToMessages(QueryParameterUtil.getRelativePathWithQueryParameters(filterPageParameters, Ps4ECitizenColaborationMessagesRoute.class)));

        }
        serviceColaborationMessagesTable.addRow(
                noMessagesDetail,
                docTypeDetail,
                documentDetail,
                sendAtDetail,
                workflowStatusDetail
        );
        HtmlContainer iconDocTypeMob = new HtmlContainer("i");
        if(messagesInfo.getTipDocument()!=null){
            iconDocTypeMob.addClassNames("fas", "fa-file");
        }
        Span docTypeSpanMob=new Span();
        docTypeSpanMob.setText(messagesInfo.getTipDocument());
        Div docTypeDetailMob=new Div(iconDocTypeMob,docTypeSpanMob);

        HtmlContainer iconDocumentMob = new HtmlContainer("i");
        if(messagesInfo.getNumeFisier()!=null){
            iconDocumentMob.addClassNames("fas", "fa-info-circle");
        }
        ClickNotifierAnchor documentLinkMob=new ClickNotifierAnchor();
        documentLinkMob.setText(messagesInfo.getNumeFisier());
        Div documentDetailMob=new Div(iconDocumentMob,documentLinkMob);

        if(Optional.ofNullable(messagesInfo.getIdFisier()).isPresent()) {
            Map<String, Object> filterPageParameters = new HashMap<>();
            filterPageParameters.put("fileId", messagesInfo.getIdFisier());

            documentLinkMob.getStyle().set("cursor", "pointer");
            documentLinkMob.addClickListener(e
                    -> VaadinClientUrlUtil.setLocation(QueryParameterUtil.getRelativePathWithQueryParameters(filterPageParameters, Ps4ECitizenColaborationMessagesRoute.class)));
        }

        HtmlContainer iconSendAtMob = new HtmlContainer("i");
        if(messagesInfo.getCreatLa()!=null){
            iconSendAtMob.addClassNames("fas", "fa-calendar-alt");
        }
        Span sendAtSpanMob=new Span();
        sendAtSpanMob.setText(messagesInfo.getTrimisLa());
        Div sendAtDetailMob=new Div(iconSendAtMob,sendAtSpanMob);

        HtmlContainer iconWorkflowStatusMob = new HtmlContainer("i");
        if(messagesInfo.getWorkflowStatus()!=null){
            if(messagesInfo.getWorkflowStatus().toLowerCase().contains("respins")){
                iconWorkflowStatusMob.addClassNames("fas", "fa-exclamation-circle","text-danger");
            }
            else{
                iconWorkflowStatusMob.addClassNames("fas", "fa-check","text-secondary");
            }
        }

        Span workflowStatusSpanMob=new Span();
        workflowStatusSpanMob.setText(messagesInfo.getWorkflowStatus());
        Div workflowStatusDetailMob=new Div(iconWorkflowStatusMob,workflowStatusSpanMob);

        HtmlContainer iconNoMessagesMob = new HtmlContainer("i");
        iconNoMessagesMob.addClassNames("fas", "fa-envelope");
        iconNoMessagesMob.getStyle().set("color","red");

        Strong noMessagesSpanMob= new Strong();
        noMessagesSpanMob.setText(messagesInfo.getNrMesaje().toString());
        Div noMessagesDetailMob=new Div(iconNoMessagesMob,noMessagesSpanMob);

        if(Optional.ofNullable(messagesInfo.getIdFisier()).isPresent()) {
            Map<String, Object> filterPageParameters = new HashMap<>();
            filterPageParameters.put("fileId", messagesInfo.getIdFisier());

            docTypeDetailMob.getStyle().set("cursor", "pointer");
            documentLinkMob.getStyle().set("cursor", "pointer");
            sendAtDetailMob.getStyle().set("cursor", "pointer");
            workflowStatusDetailMob.getStyle().set("cursor", "pointer");
            noMessagesDetailMob.getStyle().set("cursor", "pointer");
            docTypeDetailMob.addClickListener(e
                    -> VaadinClientUrlUtil.setLocation(QueryParameterUtil.getRelativePathWithQueryParameters(filterPageParameters, Ps4ECitizenColaborationMessagesRoute.class)));
            documentDetailMob.addClickListener(e
                    -> VaadinClientUrlUtil.setLocation(QueryParameterUtil.getRelativePathWithQueryParameters(filterPageParameters, Ps4ECitizenColaborationMessagesRoute.class)));
            sendAtDetailMob.addClickListener(e
                    -> VaadinClientUrlUtil.setLocation(QueryParameterUtil.getRelativePathWithQueryParameters(filterPageParameters, Ps4ECitizenColaborationMessagesRoute.class)));
            workflowStatusDetailMob.addClickListener(e
                    -> VaadinClientUrlUtil.setLocation(QueryParameterUtil.getRelativePathWithQueryParameters(filterPageParameters, Ps4ECitizenColaborationMessagesRoute.class)));
            noMessagesDetailMob.addClickListener(e
                    -> VaadinClientUrlUtil.setLocation(QueryParameterUtil.getRelativePathWithQueryParameters(filterPageParameters, Ps4ECitizenColaborationMessagesRoute.class)));

        }


        Map<String, Component> mobileRowMap = new LinkedHashMap<>();

        mobileRowMap.put("myaccount.colaboration.messages.number.label", noMessagesDetailMob);
        mobileRowMap.put("myaccount.colaboration.messages.doctypename.label", docTypeDetailMob);
        mobileRowMap.put("myaccount.colaboration.messages.documentname.label", documentDetailMob);
        mobileRowMap.put("myaccount.colaboration.messages.datastart.label", sendAtDetailMob);
        mobileRowMap.put("myaccount.colaboration.messages.documentstatus.label", workflowStatusDetailMob);
        mobServiceDocumentsTable.addRow(mobileRowMap);
    }

}
