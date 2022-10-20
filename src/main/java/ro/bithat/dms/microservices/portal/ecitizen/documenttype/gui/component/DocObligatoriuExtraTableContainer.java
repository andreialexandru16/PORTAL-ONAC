package ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui.component;
//14.07.2021 - NG - ANRE - modificare style coloana descriere fisier

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.tags.form.TextareaTag;
import ro.bithat.dms.microservices.dmsws.ps4.documents.DocObligatoriuExtra;
import ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui.Ps4ECitizenServiceAttacheFilePresenter;
import ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui.Ps4ECitizenServiceAttacheFileView;
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
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class DocObligatoriuExtraTableContainer extends FlowViewDivContainer {


    private static Logger logger = LoggerFactory.getLogger(DocObligatoriuExtraTableContainer.class);

    private LoadingSpinner loadingSpinner = new LoadingSpinner();

    private boolean showButtonDownloadInvoice = false;

    private List<Div> optionLayouts = new ArrayList<>();

    private List<List<Component>> optionsForLayouts = new ArrayList<>();

    private List<List<Component>> mobileOptionsForLayouts = new ArrayList<>();
    private List<TextArea> textAreas = new ArrayList<>();
    private Optional<String> requestFileId = QueryParameterUtil.getQueryParameter("request", String.class);

    private Div divNotes = new Div();

    //Mesaj de informare
    private Label infoMessage = new Label("document.type.service.request.view.neededfiles.infomessage.label");
    private Label textInfoMessage = new Label("document.type.service.request.view.neededfiles.textinfomessage.label");
    private Div divInfoMessage = new Div(infoMessage);
    private Div divTextInfoMessage = new Div(divInfoMessage, textInfoMessage);


    private TableContainerDiv serviceNeededDocumentsTable;

    public DocObligatoriuExtraTableContainer(FlowView view) {
        super(view);


        //Needed Document table
        if (requestFileId.isPresent()) {
            if (VaadinClientUrlUtil.getRouteLocation().getFirstSegment().equals("solicitare-revizie-finala")) {
                serviceNeededDocumentsTable = new TableContainerDiv("document.type.service.request.view.service.index.label",
                        "document.type.service.request.view.service.documentname.label",
                        "document.type.service.request.view.service.filedescription.label",
                        "document.type.service.request.view.service.attach.label"
                );
            } else {
                add(divTextInfoMessage);
                serviceNeededDocumentsTable = new TableContainerDiv("document.type.service.request.view.service.index.label",
                        "document.type.service.request.view.service.documentname.label",
                        "document.type.service.request.view.service.documentdescription.label",
                        "document.type.service.request.view.service.filedescription.label",
                        "document.type.service.request.view.service.option.label"
                );

            }

        } else {
            serviceNeededDocumentsTable = new TableContainerDiv("document.type.service.request.view.service.index.label",
                    "document.type.service.request.view.service.documentname.label",
                    "document.type.service.request.view.service.documentdescription.label",
                    "document.type.service.request.view.service.mandatory.label",
                    "document.type.service.request.view.service.model.label"
            );
        }
        serviceNeededDocumentsTable.addClassNames("table-responsive", "table_anre");
        serviceNeededDocumentsTable.setTableClassNames("table has-buttons");
        serviceNeededDocumentsTable.setTableHeaderClassNames("");
        addClassName("table-responsive");
        divTextInfoMessage.addClassName("info_message");
        add(serviceNeededDocumentsTable);

        loadingSpinner.show();
        loadingSpinner.setId("loading-spinner");
        loadingSpinner.getStyle().set("display", "none");
        //Needed Document table

    }

    public void displayForMobile() {
        if (serviceNeededDocumentsTable.getClassNames().contains("table-responsive")) {


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
        if (optionLayout.getClassNames().contains("btns_table_mobile")) {
            optionLayout.addClassName("btns_table_mobile");
        }
        optionsForLayouts.get(index)
                .stream().forEach(c -> optionLayout.add(c));

    }

    public void displayForDesktop() {
        if (serviceNeededDocumentsTable.getClassNames().contains("table_mob_4col")) {
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
        textAreas = new ArrayList<>();
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
        optionsLayout.getStyle().set("width", "200px");
        List<Component> options = new ArrayList<>();
        List<Component> mobileOptions = new ArrayList<>();
        if (Optional.ofNullable(docObligatoriuExtra.getLinkTemplateFile()).isPresent() &&
                !docObligatoriuExtra.getLinkTemplateFile().isEmpty() && !VaadinClientUrlUtil.getRouteLocation().getFirstSegment().equals("solicitare-revizie-finala")) {
            Anchor templateDownload = new Anchor();
            templateDownload.addClassNames("btn", "btn-primary", "btn-secondary-hover", "btn-xsm", "btn-xsm-s");
            templateDownload.setHref("javascript:void(0)");
            HtmlContainer templateDownloadIcon = new HtmlContainer("i");
            Span spanTemplateDownloadIcon = new Span("document.type.service.request.view.neededfiles.service.option.template.download.label");
            spanTemplateDownloadIcon.addClassName("tooltiptext");
            templateDownloadIcon.addClassNames("custom-icon", "icon-down", "tooltip_chat");
            templateDownloadIcon.add(spanTemplateDownloadIcon);
            templateDownload.add(templateDownloadIcon, new Text(""));
            templateDownload.setHref(
                    StreamResourceUtil.getStreamResource(docObligatoriuExtra.getDocObligatoriu().getDenumireTemplateFile(),
                            docObligatoriuExtra.getLinkTemplateFile()));

            templateDownload.setTarget("_blank");
            optionsLayout.add(templateDownload);
            options.add(templateDownload);

            Anchor mobileTemplateDownload = new Anchor();
            mobileTemplateDownload.addClassName("btn_download_sablon");
            mobileTemplateDownload.setHref("javascript:void(0)");
            mobileTemplateDownload.setHref(
                    StreamResourceUtil.getStreamResource(docObligatoriuExtra.getDocObligatoriu().getDenumireTemplateFile(),
                            docObligatoriuExtra.getLinkTemplateFile()));

            mobileTemplateDownload.setTarget("_blank");
            mobileOptions.add(mobileTemplateDownload);
        }


        if (enableDocumentAction) {

            ClickNotifierAnchor uploadBtn = new ClickNotifierAnchor();
            uploadBtn.addClassNames("btn", "btn-primary", "btn-secondary-hover", "btn-xsm", "btn-xsm-s");
            uploadBtn.setHref("javascript:void(0)");
            HtmlContainer uploadIcon = new HtmlContainer("i");
            uploadIcon.addClassNames("custom-icon", "icon-up", "tooltip_chat");
            uploadBtn.add(uploadIcon, new Text(""));
            Span spanuploadIcon = new Span("document.type.service.request.view.neededfiles.service.option.upload.label");
            spanuploadIcon.addClassName("tooltiptext");
            uploadIcon.add(spanuploadIcon);
            MemoryBuffer fileBuffer = new MemoryBuffer();
            Upload upload = new Upload(fileBuffer);
            upload.setId("upload-for-" + docObligatoriuExtra.getUploadedFileId());
            upload.addClassName("upload-vaadin-no-file-list");
            upload.setUploadButton(uploadBtn);
            upload.setDropAllowed(false);
            upload.setMaxFiles(1);
            upload.setAcceptedFileTypes(".png",".jpg", ".jpeg", ".png", ".bmp", ".tiff", ".gif", ".raw", ".pdf",".txt",".docx",".zip",".rar",".7zip",
                    ".xml",".csv",".xsd",".doc",".xls",".xlsx",".tif");
            upload.addClassName("upload-no-info");

            Div uploadL = new Div(upload);
            uploadL.getStyle().set("display", "inline-block");
            upload.addFileRejectedListener(e -> {
                String err = e.getErrorMessage().replaceAll(" ", "");
                UI.getCurrent().getPage().executeJs("swalError($0)",
                        I18NProviderStatic.getTranslation(err));

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
            mobileUpload.setId("mob-upload-for-" + docObligatoriuExtra.getUploadedFileId());
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

                download.addClassNames("btn", "btn-primary", "btn-secondary-hover", "btn-xsm", "btn-xsm-s");
                HtmlContainer downloadIcon = new HtmlContainer("i");
                downloadIcon.addClassNames("custom-icon", "icon-down", "tooltip_chat");
                download.add(downloadIcon, new Text(""));
                Span spandownloadIcon = new Span("document.type.service.request.view.neededfiles.service.option.download.label");
                spandownloadIcon.addClassName("tooltiptext");
                downloadIcon.add(spandownloadIcon);


                download.setHref(StreamResourceUtil.getStreamResource(
                        docObligatoriuExtra.getDocObligatoriu().getDenumireFisierAnexat(),
                        docObligatoriuExtra.getDocObligatoriu().getLinkFisierAnexat()));
                download.setTarget("_blank");
                Anchor delete = new Anchor();
                delete.addClassNames("btn", "btn-primary", "btn-secondary-hover", "btn-xsm", "btn-xsm-s");
                delete.setHref("javascript:void(0)");
                HtmlContainer deleteIcon = new HtmlContainer("i");
                deleteIcon.addClassNames("custom-icon", "icon-delete", "tooltip_chat");
                delete.add(deleteIcon, new Text(""));
                Span spandeleteIcon = new Span("document.type.service.request.view.neededfiles.service.option.delete.label");
                spandeleteIcon.addClassName("tooltiptext");
                deleteIcon.add(spandeleteIcon);
                registerClickEvent("deleteFile", delete, docObligatoriuExtra);


                statusIcon.addClassNames("fas", "fa-check");
                optionsLayout.add(download, delete);
                options.add(download);
                options.add(delete);


                Anchor saveDescription = new Anchor();
                saveDescription.addClassNames("btn", "btn-primary", "btn-secondary-hover", "btn-xsm", "btn-xsm-s");
                saveDescription.setHref("javascript:void(0)");
                HtmlContainer saveDescriptionIcon = new HtmlContainer("i");
                saveDescriptionIcon.addClassNames("custom-icon", "icon-save", "tooltip_chat");
                saveDescription.add(saveDescriptionIcon, new Text(""));
                Span spansaveDescriptionIcon = new Span("Salveaza descriere fisier");
                spansaveDescriptionIcon.addClassName("tooltiptext");
                saveDescriptionIcon.add(spansaveDescriptionIcon);

                registerClickEvent("saveDescriptionFile", saveDescription, docObligatoriuExtra);
                options.add(saveDescription);
                optionsLayout.add(saveDescription);

            } else {
                if (docObligatoriuExtra.getDocObligatoriu().getObligatoriu()) {
                    statusIcon.addClassNames("fas", "fa-asterisk");
                    statusIcon.getElement().setAttribute("title", "Document obligatoriu");
                    statusIcon.getStyle().set("color", "red");
                    statusIcon.getStyle().set("font-size", "18px");
                }
            }
            if (getView().getPresenter().getClass().isAssignableFrom(Ps4ECitizenServiceAttacheFilePresenter.class)) {
                showButtonDownloadInvoice = ((Ps4ECitizenServiceAttacheFilePresenter) getView().getPresenter()).isShowButtonDownloadInvoice();

                if (showButtonDownloadInvoice && Optional.ofNullable(docObligatoriuExtra.getDocObligatoriu().getDocumentPlata()).isPresent() && Optional.ofNullable(docObligatoriuExtra.getDocObligatoriu().getDocumentPlata()).get().equals(1)) {
                    Anchor downloadProforma = new Anchor();
                    downloadProforma.addClassNames("btn", "btn-primary", "btn-secondary-hover", "btn-xsm", "btn-xsm-s");
                    downloadProforma.setHref(((Ps4ECitizenServiceAttacheFilePresenter) getView().getPresenter()).getInvoiceJasperFileLink());
                    downloadProforma.getStyle().set("width", "30px");
                    HtmlContainer downloadProformaIcon = new HtmlContainer("i");
                    downloadProformaIcon.addClassNames("fas", "fa-file-invoice", "tooltip_chat");
                    downloadProformaIcon.getStyle().set("font-size", "20px");
                    downloadProformaIcon.getStyle().set("width", "20px");

                    downloadProforma.add(downloadProformaIcon, new Text(""));
                    Span spanDownloadProformaIcon = new Span("document.type.service.request.view.neededfiles.service.option.download.invoice.label");
                    spanDownloadProformaIcon.addClassName("tooltiptext");
                    downloadProformaIcon.add(spanDownloadProformaIcon);

                    options.add(downloadProforma);
                    optionsLayout.add(downloadProforma);


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
                if (docObligatoriuExtra.getDocObligatoriu().getObligatoriu()) {
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
                download.setHref(StreamResourceUtil.getStreamResource(
                        docObligatoriuExtra.getDocObligatoriu().getDenumireFisierAnexat(),
                        docObligatoriuExtra.getDocObligatoriu().getLinkFisierAnexat()));
                download.setTarget("_blank");
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
        //daca exista fisier creat se afiseaza text area pentru descriere fisier, altfel nu se afiseaza
        if (requestFileId.isPresent()) {
            if (VaadinClientUrlUtil.getRouteLocation().getFirstSegment().equals("solicitare-revizie-finala")) {
                serviceNeededDocumentsTable.addRow(new Label(index.getAndIncrement() + ""),
                        new Label(docObligatoriuExtra.getDocObligatoriu().getDenumire()),
                        new Label(docObligatoriuExtra.getDocObligatoriu().getDescriereFisier()),
                        optionsLayout);
            } else {
                TextArea textAreaDescriereFisier = new TextArea();
                textAreaDescriereFisier.setId("textarea_desc_" + index.get());
                textAreaDescriereFisier.getElement().getStyle().set("background-color", "white");
                // 14.07.2021 - NG - ANRE - modificare style coloana descriere fisier
                textAreaDescriereFisier.addClassNames("smart-form-control", "vaadin-ps4-theme");
                if (docObligatoriuExtra.getDocObligatoriu().getDescriereFisier() != null) {
                    textAreaDescriereFisier.setValue(docObligatoriuExtra.getDocObligatoriu().getDescriereFisier());
                }
                docObligatoriuExtra.setIdTextareDescription(textAreaDescriereFisier.getId().get());
                getTextAreas().add(textAreaDescriereFisier);
                serviceNeededDocumentsTable.addRow(new Label(index.getAndIncrement() + ""),
                        new Label(docObligatoriuExtra.getDocObligatoriu().getDenumire()),
                        new Label(docObligatoriuExtra.getDocObligatoriu().getDescriere()),
                        textAreaDescriereFisier,
                        optionsLayout);
            }
        } else {
            serviceNeededDocumentsTable.addRow(new Label(index.getAndIncrement() + ""),
                    new Label(docObligatoriuExtra.getDocObligatoriu().getDenumire()),
                    new Label(docObligatoriuExtra.getDocObligatoriu().getDescriere()),
                    new Label(docObligatoriuExtra.getDocObligatoriu().getObligatoriu() ? "Document obligatoriu" : "Document opțional"),
                    optionsLayout);
        }


    }


    //Needed Document table


    public void removeNotes() {
        remove(divNotes);
    }

    public void addNotes(String text) {
        Strong noteText = new Strong(text);
        noteText.getElement().getStyle().set("font-size", "20px");
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
