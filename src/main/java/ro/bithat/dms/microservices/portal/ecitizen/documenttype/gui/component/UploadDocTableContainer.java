package ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui.component;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.bithat.dms.microservices.dmsws.ps4.documents.DocObligatoriuExtra;
import ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui.Ps4ECitizenServiceNewRequestRoute;
import ro.bithat.dms.microservices.portal.ecitizen.gui.component.FlowViewDivContainer;
import ro.bithat.dms.microservices.portal.ecitizen.gui.component.LoadingSpinner;
import ro.bithat.dms.microservices.portal.ecitizen.gui.component.TableContainerDiv;
import ro.bithat.dms.passiveview.QueryParameterUtil;
import ro.bithat.dms.passiveview.StreamResourceUtil;
import ro.bithat.dms.passiveview.VaadinClientUrlUtil;
import ro.bithat.dms.passiveview.boot.I18NProviderStatic;
import ro.bithat.dms.passiveview.component.html.ClickNotifierAnchor;
import ro.bithat.dms.passiveview.component.html.Strong;
import ro.bithat.dms.passiveview.i18n.flow.InternationalizeViewEngine;
import ro.bithat.dms.passiveview.mvp.FlowView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class UploadDocTableContainer extends FlowViewDivContainer {


    private static Logger logger = LoggerFactory.getLogger(UploadDocTableContainer.class);

    private LoadingSpinner loadingSpinner = new LoadingSpinner();


    private List<Div> optionLayouts = new ArrayList<>();

    private List<List<Component>> optionsForLayouts = new ArrayList<>();

    private List<List<Component>> mobileOptionsForLayouts = new ArrayList<>();
    private List<TextArea> textAreas = new ArrayList<>();


    private Div divNotes = new Div();

    private TableContainerDiv serviceNeededDocumentsTable =
            new TableContainerDiv("document.type.service.request.view.service.index.label",
                    "document.type.service.request.view.service.documentname.label",
                    "document.type.service.request.view.service.documentdescription.label",
                    "document.type.service.request.view.service.nota.lipsuri.label",
                    "document.type.service.request.view.service.option.label"
                    );

    public UploadDocTableContainer(FlowView view) {
        super(view);

        //Needed Document table

        serviceNeededDocumentsTable.addClassName("table-responsive");
        serviceNeededDocumentsTable.setTableClassNames("table dark-head has-buttons");
        serviceNeededDocumentsTable.setTableHeaderClassNames("thead-dark");
        addClassNames("table-responsive", "mt-3");
        add(serviceNeededDocumentsTable);

        loadingSpinner.show();
        loadingSpinner.setId("loading-spinner");
        loadingSpinner.getStyle().set("display", "none");
        //Needed Document table

    }

    public void displayForMobile() {
        if(serviceNeededDocumentsTable.getClassNames().contains("table-responsive")) {


            HtmlContainer indexHeader = serviceNeededDocumentsTable.getTableHeadersMap().get("document.type.service.request.view.service.index.label");
            indexHeader.removeAll();
            serviceNeededDocumentsTable.removeClassName("table-responsive");
            serviceNeededDocumentsTable.addClassNames("table_mob_4col", "nr_crt", "table_scroll");
            serviceNeededDocumentsTable.removeTableClassNames();
            serviceNeededDocumentsTable.removeTableHeaderClassNames();
            removeClassNames("table-responsive", "mt-3");

        }
        AtomicInteger index = new AtomicInteger(0);
        optionLayouts.stream()
                .forEach(optionLayout -> displayMobileOptions(optionLayout, index.getAndIncrement()));
    }

    private void displayMobileOptions(Div optionLayout, Integer index) {
        optionLayout.removeAll();
        optionLayout.addClassName("btns_table_mobile");
        mobileOptionsForLayouts.get(index)
                .stream().forEach(c -> optionLayout.add(c));

    }

    private void displayDesktopOptions(Div optionLayout, Integer index) {
        optionLayout.removeAll();
        if(optionLayout.getClassNames().contains("btns_table_mobile")) {
            optionLayout.addClassName("btns_table_mobile");
        }
        optionsForLayouts.get(index)
                .stream().forEach(c -> optionLayout.add(c));

    }

    public void displayForDesktop() {
        if(serviceNeededDocumentsTable.getClassNames().contains("table_mob_4col")) {
            HtmlContainer indexHeader = serviceNeededDocumentsTable.getTableHeadersMap().get("document.type.service.request.view.service.index.label");
            indexHeader.add(new Label(I18NProviderStatic.getTranslation("document.type.service.request.view.service.index.label")));
            serviceNeededDocumentsTable.removeClassNames("table_mob_4col", "nr_crt");
            serviceNeededDocumentsTable.addClassName("table-responsive");
            serviceNeededDocumentsTable.setTableClassNames("table dark-head has-buttons");
            serviceNeededDocumentsTable.setTableHeaderClassNames("thead-dark");
            addClassNames("table-responsive", "mt-3");
        }
        AtomicInteger index = new AtomicInteger(0);
        optionLayouts.stream()
                .forEach(optionLayout -> displayDesktopOptions(optionLayout, index.getAndIncrement()));
    }


    //Needed Document table

    public void setNeededDocumentsTable(List<DocObligatoriuExtra> documenteObligatoriiServiciu, boolean enableDocumentAction) {
        textAreas=new ArrayList<>();
        logger.info("loaded files size:\t" + documenteObligatoriiServiciu.size());
        optionLayouts = new ArrayList<>();
        optionsForLayouts = new ArrayList<>();
        mobileOptionsForLayouts = new ArrayList<>();
        AtomicInteger index = new AtomicInteger(1);
        serviceNeededDocumentsTable.clearContent();
        documenteObligatoriiServiciu.stream().forEach(neededDocument -> setNeededDocumentTableRow(neededDocument, index, enableDocumentAction));
        InternationalizeViewEngine.internationalize(serviceNeededDocumentsTable);
        UI.getCurrent().getPage().executeJs("hideUploadFileList();\r\n", "v-system-error", "none");

    }

    private void setNeededDocumentTableRow(DocObligatoriuExtra docObligatoriuExtra, AtomicInteger index, boolean enableDocumentAction) {
        Div optionsLayout = new Div();
        List<Component> options = new ArrayList<>();
        List<Component> mobileOptions = new ArrayList<>();

        if(enableDocumentAction) {

            ClickNotifierAnchor uploadBtn = new ClickNotifierAnchor();
            uploadBtn.addClassNames("btn", "btn-primary", "btn-secondary-hover", "btn-xsm");
            uploadBtn.setHref("javascript:void(0)");
            HtmlContainer uploadIcon = new HtmlContainer("i");
            uploadIcon.addClassNames("custom-icon", "icon-up");
            uploadBtn.add(uploadIcon, new Text("document.type.service.request.view.neededfiles.service.option.upload.label"));

            MemoryBuffer fileBuffer = new MemoryBuffer();
            Upload upload = new Upload(fileBuffer);
            upload.setId("upload-for-"+docObligatoriuExtra.getUploadedFileId());
            upload.addClassName("upload-vaadin-no-file-list");
            upload.setUploadButton(uploadBtn);
            upload.setDropAllowed(false);
            upload.setMaxFiles(1);
            upload.setAcceptedFileTypes(".png",".jpg", ".jpeg", ".png", ".bmp", ".tiff", ".gif", ".raw", ".pdf",".txt",".docx");
            upload.addClassName("upload-no-info");


            Div uploadL = new Div(upload);
            uploadL.getStyle().set("display", "inline-block");

            upload.addFailedListener(e -> {
                UI.getCurrent().getPage().executeJs("swalError($0);", e.getReason().getMessage());

            });
            upload.addFileRejectedListener(e -> {
                UI.getCurrent().getPage().executeJs("swalError($0);", e.getErrorMessage().equals("Incorrect File Type.")?"Sunt acceptate doar fisiere cu extensia: "+
                        upload.getAcceptedFileTypes().toString():e.getErrorMessage());

            });
            upload.addProgressListener(e -> {
//                loadingSpinner.show();
//                loadingSpinner.getStyle().remove("display");
                UI.getCurrent().getPage().executeJs("displayLoadingSpinner();\r\n toggleDisplayState($0,$1);", "v-system-error", "none");
            });
            upload.addFinishedListener(e -> {
//                loadingSpinner.getStyle().set("display", "none");
                UI.getCurrent().getPage().executeJs("hideLoadingSpinner(); toggleDisplayState($0,$1);", "v-system-error", "none");
            });
            registerUploadSucceededEvent("uploadFile", upload, fileBuffer, docObligatoriuExtra);
            optionsLayout.add(uploadL);
            options.add(uploadL);

            ClickNotifierAnchor mobileUploadBtn = new ClickNotifierAnchor();
            mobileUploadBtn.addClassNames("btn_upload");
            mobileUploadBtn.setHref("javascript:void(0)");

            MemoryBuffer mobileFileBuffer = new MemoryBuffer();
            Upload mobileUpload = new Upload(mobileFileBuffer);
            mobileUpload.setId("mob-upload-for-"+docObligatoriuExtra.getUploadedFileId());
            mobileUpload.addClassName("upload-vaadin-no-file-list");
            mobileUpload.setUploadButton(mobileUploadBtn);
            mobileUpload.setDropAllowed(false);
            mobileUpload.setMaxFiles(1);
            mobileUpload.addClassName("upload-no-info");

            Div mobUploadL = new Div(mobileUpload);
            mobUploadL.getStyle().set("display", "inline-block");
            mobileUpload.addProgressListener(e -> {
                //                loadingSpinner.show();
                //                loadingSpinner.getStyle().remove("display");
                UI.getCurrent().getPage().executeJs("displayLoadingSpinner();\r\n toggleDisplayState($0,$1);", "v-system-error", "none");
            });
            mobileUpload.addFinishedListener(e -> {
                //                loadingSpinner.getStyle().set("display", "none");
                UI.getCurrent().getPage().executeJs("hideLoadingSpinner(); toggleDisplayState($0,$1);", "v-system-error", "none");
            });
            registerUploadSucceededEvent("uploadFile", mobileUpload, mobileFileBuffer, docObligatoriuExtra);
            mobileOptions.add(mobUploadL);


            HtmlContainer statusIcon = new HtmlContainer("i");
            //
            if (Optional.ofNullable(docObligatoriuExtra.getDocObligatoriu().getLinkFisierAnexat()).isPresent() && !docObligatoriuExtra.getDocObligatoriu().getLinkFisierAnexat().isEmpty()) {
                logger.info("has download attachment:\t" + docObligatoriuExtra.getDocObligatoriu().getDenumire());
                Anchor download = new Anchor();

                download.addClassNames("btn", "btn-primary", "btn-secondary-hover", "btn-xsm");
                HtmlContainer downloadIcon = new HtmlContainer("i");
                downloadIcon.addClassNames("custom-icon", "icon-down");
                download.add(downloadIcon, new Text("document.type.service.request.view.neededfiles.service.option.download.label"));
                if(docObligatoriuExtra.getDocObligatoriu().getDenumireFisierAnexat().contains("generata de sistem")){
                    registerClickEvent("alertaAdresaGenerataSistem", download, docObligatoriuExtra);

                    download.setHref("javascript:void(0)");

                }else{
                    download.setHref(StreamResourceUtil.getStreamResource(
                            docObligatoriuExtra.getDocObligatoriu().getDenumireFisierAnexat(),
                            docObligatoriuExtra.getDocObligatoriu().getLinkFisierAnexat()));
                    download.setTarget("_blank");
                }

                Anchor delete = new Anchor();
                delete.addClassNames("btn", "btn-primary", "btn-secondary-hover", "btn-xsm");
                delete.setHref("javascript:void(0)");
                HtmlContainer deleteIcon = new HtmlContainer("i");
                deleteIcon.addClassNames("custom-icon", "icon-delete");
                delete.add(deleteIcon, new Text("document.type.service.request.view.neededfiles.service.option.delete.label"));
                registerClickEvent("deleteFile", delete, docObligatoriuExtra);



                statusIcon.addClassNames("fas", "fa-check");
                optionsLayout.add(download, delete);
                options.add(download);
                options.add(delete);


            } else {
                if(docObligatoriuExtra.getDocObligatoriu().getObligatoriu()) {
                     statusIcon.addClassNames("fas", "fa-asterisk");
                    statusIcon.getElement().setAttribute("title", "Document obligatoriu");
                    statusIcon.getStyle().set("color", "red");
                    statusIcon.getStyle().set("font-size", "18px");
                }
            }

            optionsLayout.add(statusIcon);
            options.add(statusIcon);


            HtmlContainer mobStatusIcon = new HtmlContainer("i");
            Div mobStatus = new Div(mobStatusIcon);
            //
            if (Optional.ofNullable(docObligatoriuExtra.getDocObligatoriu().getLinkFisierAnexat()).isPresent() && !docObligatoriuExtra.getDocObligatoriu().getLinkFisierAnexat().isEmpty()) {
                logger.info("has download attachment:\t" + docObligatoriuExtra.getDocObligatoriu().getDenumire());
                Anchor mobDownload = new Anchor();
                mobDownload.addClassNames("btn_download");
                mobDownload.setHref(StreamResourceUtil.getStreamResource(
                        docObligatoriuExtra.getDocObligatoriu().getDenumireFisierAnexat(),
                        docObligatoriuExtra.getDocObligatoriu().getLinkFisierAnexat()));
                mobDownload.setTarget("_blank");
                Anchor mobDelete = new Anchor();
                mobDelete.addClassNames("btn_delete");
                mobDelete.setHref("javascript:void(0)");
                registerClickEvent("deleteFile", mobDelete, docObligatoriuExtra);
                mobStatus.addClassName("icon_check");
                mobStatusIcon.addClassNames("fas", "fa-check");
                mobileOptions.add(mobDelete);
                mobileOptions.add(mobDownload);
            } else {
                if(docObligatoriuExtra.getDocObligatoriu().getObligatoriu()) {
                    mobStatus.addClassName("icon_warning");
                    mobStatusIcon.addClassNames("fas", "fa-asterisk");
                    statusIcon.getElement().setAttribute("title", "Document obligatoriu");

                    mobStatusIcon.getStyle().set("color", "red");
                }
            }

            mobileOptions.add(mobStatus);


        } else {
            if (Optional.ofNullable(docObligatoriuExtra.getDocObligatoriu().getLinkFisierAnexat()).isPresent() && !docObligatoriuExtra.getDocObligatoriu().getLinkFisierAnexat().isEmpty()) {
                logger.info("has download attachment:\t" + docObligatoriuExtra.getDocObligatoriu().getDenumire());
                Anchor download = new Anchor();
                download.addClassNames("btn", "btn-primary", "btn-secondary-hover", "btn-xsm");
                HtmlContainer downloadIcon = new HtmlContainer("i");
                downloadIcon.addClassNames("custom-icon", "icon-down");
                download.add(downloadIcon, new Text("document.type.service.request.view.neededfiles.service.option.download.label"));
                if(docObligatoriuExtra.getDocObligatoriu().getDenumireFisierAnexat().contains("generata de sistem")){
                    registerClickEvent("alertaAdresaGenerataSistem", download, docObligatoriuExtra);

                    download.setHref("javascript:void(0)");

                }else{
                    download.setHref(StreamResourceUtil.getStreamResource(
                            docObligatoriuExtra.getDocObligatoriu().getDenumireFisierAnexat(),
                            docObligatoriuExtra.getDocObligatoriu().getLinkFisierAnexat()));
                    download.setTarget("_blank");
                }

                optionsLayout.add(download);
                options.add(download);

                Anchor mobDownload = new Anchor();
                mobDownload.addClassNames("btn_download");
                mobDownload.setHref(StreamResourceUtil.getStreamResource(
                        docObligatoriuExtra.getDocObligatoriu().getDenumireFisierAnexat(),
                        docObligatoriuExtra.getDocObligatoriu().getLinkFisierAnexat()));
                mobDownload.setTarget("_blank");
                mobileOptions.add(mobDownload);
            }
        }

        optionsForLayouts.add(options);
        mobileOptionsForLayouts.add(mobileOptions);
        optionLayouts.add(optionsLayout);


        serviceNeededDocumentsTable.addRow(new Label(index.getAndIncrement() + ""),
                new Label(docObligatoriuExtra.getDocObligatoriu().getDenumire()),
                new Label(docObligatoriuExtra.getDocObligatoriu().getDescriere()),
                new Label(docObligatoriuExtra.getDocObligatoriu().getNota()),
                optionsLayout);
    }


    //Needed Document table


    public void removeNotes(){
        remove(divNotes);
    }
    public void addNotes(String text){
        Strong noteText= new Strong(text);
        noteText.getElement().getStyle().set("font-size","20px");
        divNotes.add(noteText);
        add(divNotes);
    }

    public List<TextArea> getTextAreas() {
        return textAreas;
    }

    public void setTextAreas(List<TextArea> textAreas) {
        this.textAreas = textAreas;
    }
}
