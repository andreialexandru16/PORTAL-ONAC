package ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.RouteConfiguration;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.CorespondentaLiniePetitii;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.CorespondentaPetitii;
import ro.bithat.dms.microservices.portal.ecitizen.gui.ContentContainerView;
import ro.bithat.dms.microservices.portal.ecitizen.gui.component.MobTableContainerDiv;
import ro.bithat.dms.microservices.portal.ecitizen.gui.component.TableContainerDiv;
import ro.bithat.dms.microservices.portal.ecitizen.home.gui.Ps4ECitizenHomeRoute;
import ro.bithat.dms.microservices.portal.ecitizen.onlineservices.gui.component.SearchContainer;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.gui.Ps4ECitizenMyAccountRoute;
import ro.bithat.dms.passiveview.StreamResourceUtil;
import ro.bithat.dms.passiveview.VaadinClientUrlUtil;
import ro.bithat.dms.passiveview.boot.I18NProviderStatic;
import ro.bithat.dms.passiveview.component.html.ClickNotifierAnchor;
import ro.bithat.dms.passiveview.component.html.Strong;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class Ps4ECorespondentaPetitiiView extends ContentContainerView<Ps4ECorespondentaPetitiiPresenter>   {
    private SearchContainer searchFormContainer = new SearchContainer(this);

    private Div filterContainer = new Div(searchFormContainer);

    private TableContainerDiv serviceDocumentsTable =
            new TableContainerDiv(
                    "Status",
                    "Sens",
                    "Document",
                    "Numar",
                    "Nr. inreg",
                    "Data inreg.",
                    "Creat la",
                    "Termen raspuns",
                    ""
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
        setContentPageTile(I18NProviderStatic.getTranslation("ps4.ecetatean.breadcrumb.corespondentaPetitii.title"));
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

    public void setMyRequestsTable(List<CorespondentaPetitii> myDocuments) {
        serviceDocumentsTable.clearContent();
        AtomicInteger index = new AtomicInteger(1);
        myDocuments.stream().forEach(document -> setDocumentTableRow(document, index));
    /*    if(Optional.ofNullable(getClientWidth()).isPresent()) {
            displayForWidth(getClientWidth());
        }*/
    }

    private void setDocumentTableRow(CorespondentaPetitii document, AtomicInteger index) {
        Strong strongElementContent= new Strong();

        if (document.getCorespondentaLiniePetitiiList() != null){
            for (CorespondentaLiniePetitii cl: document.getCorespondentaLiniePetitiiList()){
                ClickNotifierAnchor anchorRequestPreviewFile= new ClickNotifierAnchor();
                anchorRequestPreviewFile.setHref(
                        StreamResourceUtil.getStreamResource(cl.getDocument(), cl.getDownloadLink()));
                anchorRequestPreviewFile.setTarget("_blank");
                anchorRequestPreviewFile.setText(cl.getDocument());
                anchorRequestPreviewFile.getStyle().set("margin-right","10px");

                strongElementContent.add(anchorRequestPreviewFile);
            }
        }

        if(Optional.ofNullable(document.getDownloadLink()).isPresent() &&
                !document.getDownloadLink().isEmpty()) {
            ClickNotifierAnchor anchorRequestPreviewFile= new ClickNotifierAnchor();
            anchorRequestPreviewFile.setHref(
                    StreamResourceUtil.getStreamResource(document.getDocument(), document.getDownloadLink()));
            anchorRequestPreviewFile.setTarget("_blank");
            anchorRequestPreviewFile.setText(document.getDocument());
            anchorRequestPreviewFile.getStyle().set("margin-right","10px");

            strongElementContent.add(anchorRequestPreviewFile);
        }

        NativeButton btnAttachements = new NativeButton();

        ClickNotifierAnchor uploadBtn = new ClickNotifierAnchor();
        uploadBtn.addClassNames("btn", "btn-primary", "btn-secondary-hover", "btn-xsm");
        uploadBtn.setHref("javascript:void(0)");
        HtmlContainer uploadIcon = new HtmlContainer("i");
        uploadIcon.addClassNames("custom-icon", "icon-up");
        uploadBtn.add(uploadIcon, new Text("document.type.service.request.view.neededfiles.service.option.upload.label"));

        MemoryBuffer fileBuffer = new MemoryBuffer();
        Upload upload = new Upload(fileBuffer);
        upload.setId("upload-for-"+document.getId());
        upload.addClassName("upload-vaadin-no-file-list");

        upload.setUploadButton(btnAttachements);
        upload.setDropAllowed(false);
        upload.setMaxFiles(1);
        upload.addClassName("upload-no-info");

        Div uploadL = new Div(upload);
        uploadL.getStyle().set("display", "inline-block");

        upload.addSucceededListener(event -> {
            getPresenter().uploadFile(event,fileBuffer, document.getId());

        });

        Div btnAttachementsDiv = new Div(upload);

        btnAttachementsDiv.addClassName("btn_attachements_cp");

        Div mainUploadDiv = new Div(btnAttachementsDiv);


        serviceDocumentsTable.addRow(
                new Label(document.getStatus()),
                new Label(document.getSens()),
                strongElementContent,
                new Label(document.getVersiune().toString()),
                new Label(document.getNrInreg()),
                new Label(document.getDataInreg()),
                new Label(document.getCreatLa()),
                new Label(document.getData()),
                mainUploadDiv
        );

        Map<String, Component> mobileRowMap = new LinkedHashMap<>();
        mobileRowMap.put("Status",new Label(document.getStatus()));
        mobileRowMap.put("Sens",new Label(document.getSens()));
        mobileRowMap.put("Document",  new Label(document.getDocument()));
        mobileRowMap.put("Numar", new Label(document.getVersiune().toString()));
        mobileRowMap.put("Nr. inreg", new Label(document.getNrInreg()));
        mobileRowMap.put("Data inreg", new Label(document.getDataInreg()));
        mobileRowMap.put("Creat la", new Label(document.getCreatLa()));
        mobileRowMap.put("Termen raspuns", new Label(document.getData()));

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

    @ClientCallable
    public void swalInfoAck() {
        UI.getCurrent().getPage().reload();
    }


    @ClientCallable
    public void swalErrorAck() {
        UI.getCurrent().getPage().reload();
    }

}
