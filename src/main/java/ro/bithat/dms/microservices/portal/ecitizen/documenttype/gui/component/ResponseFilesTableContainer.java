package ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui.component;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import ro.bithat.dms.microservices.dmsws.file.PortalFile;
import ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui.Ps4ECitizenServiceAttacheDocRaspunsRoute;
import ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui.Ps4ECitizenServiceAttacheFileRoute;
import ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui.Ps4ECitizenServiceRequestReviewRoute;
import ro.bithat.dms.microservices.portal.ecitizen.gui.component.FlowViewDivContainer;
import ro.bithat.dms.microservices.portal.ecitizen.gui.component.TableContainerDiv;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.gui.Ps4ECitizenColaborationMessagesRoute;
import ro.bithat.dms.passiveview.QueryParameterUtil;
import ro.bithat.dms.passiveview.StreamResourceUtil;
import ro.bithat.dms.passiveview.VaadinClientUrlUtil;
import ro.bithat.dms.passiveview.boot.I18NProviderStatic;
import ro.bithat.dms.passiveview.i18n.flow.InternationalizeViewEngine;
import ro.bithat.dms.passiveview.mvp.FlowView;
import ro.bithat.dms.service.URLUtil;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ResponseFilesTableContainer extends FlowViewDivContainer {


    private List<Div> optionLayouts = new ArrayList<>();

    private List<List<Component>> optionsForLayouts = new ArrayList<>();

    private List<List<Component>> mobileOptionsForLayouts = new ArrayList<>();

    private TableContainerDiv responseFilesTable =
            new TableContainerDiv();

    public ResponseFilesTableContainer(FlowView view) {
        super(view);


        //Response Files table

        responseFilesTable.addClassName("table-responsive");
        responseFilesTable.setTableClassNames("table dark-head has-buttons");
        responseFilesTable.setTableHeaderClassNames("thead-dark");
        addClassNames("table-responsive", "mt-3");
        add(responseFilesTable);

        setTableHeaders();
        //Response Files table

    }


    public void displayForMobile() {
        if(responseFilesTable.getClassNames().contains("table-responsive")) {
            HtmlContainer indexHeader = responseFilesTable.getTableHeadersMap().get("document.type.service.request.view.responsefiles.index.label");
            indexHeader.removeAll();
            responseFilesTable.removeClassName("table-responsive");
            responseFilesTable.addClassNames("table_mob_4col", "nr_crt", "table_scroll");
            responseFilesTable.removeTableClassNames();
            responseFilesTable.removeTableHeaderClassNames();
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
        if(responseFilesTable.getClassNames().contains("table_mob_4col")) {
            HtmlContainer indexHeader = responseFilesTable.getTableHeadersMap().get("document.type.service.request.view.responsefiles.index.label");
            indexHeader.add(new Label(I18NProviderStatic.getTranslation("document.type.service.request.view.responsefiles.index.label")));
            responseFilesTable.removeClassNames("table_mob_4col", "nr_crt");
            responseFilesTable.addClassName("table-responsive");
            responseFilesTable.setTableClassNames("table dark-head has-buttons");
            responseFilesTable.setTableHeaderClassNames("thead-dark");
            addClassNames("table-responsive", "mt-3");
        }
        AtomicInteger index = new AtomicInteger(0);
        optionLayouts.stream()
                .forEach(optionLayout -> displayDesktopOptions(optionLayout, index.getAndIncrement()));
    }

    //Response Files table

    public void setResponseFilesTable(List<PortalFile> responseFiles, boolean enableDocumentAction,Integer document, Integer documentType) {
        optionLayouts = new ArrayList<>();
        optionsForLayouts = new ArrayList<>();
        mobileOptionsForLayouts = new ArrayList<>();
        AtomicInteger index = new AtomicInteger(1);
        responseFilesTable.clearContent();
        responseFiles.stream().forEach(responseFile -> setResponseFileTableRow(responseFile, index, enableDocumentAction,document,documentType));
        InternationalizeViewEngine.internationalize(responseFilesTable);
    }

//    public void setDisplayImageUrl(String fileName, String url) {
//        try {
//            URL connUrl = new URL(url);
//            URLConnection conn = connUrl.openConnection();
//            conn.setConnectTimeout(5000);
//            conn.setReadTimeout(5000);
//            conn.connect();
//
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            IOUtils.copy(conn.getInputStream(), baos);
//            image.setSrc(new StreamResource(fileName, () -> new ByteArrayInputStream(baos.toByteArray())));
//        } catch (IOException e) {
//            logger.error("fileName [ " + fileName+  "] inputStream error from url: \t"+ url, e);
//        }
//    }

    private void setResponseFileTableRow(PortalFile responseFile, AtomicInteger index, boolean enableDocumentAction, Integer document, Integer documentType) {

        Div optionsLayout = new Div();
        List<Component> options = new ArrayList<>();
        List<Component> mobileOptions = new ArrayList<>();

        if (enableDocumentAction &&
                Optional.ofNullable(responseFile.getDownloadLink()).isPresent() && !responseFile.getDownloadLink().isEmpty()) {
            Anchor download = new Anchor();
            download.addClassNames("btn", "btn-primary", "btn-secondary-hover", "btn-xsm");
            HtmlContainer downloadIcon = new HtmlContainer("i");
            downloadIcon.addClassNames("custom-icon", "icon-down");
            download.add(downloadIcon, new Text("document.type.service.request.view.neededfiles.service.option.download.label"));
            download.setHref(StreamResourceUtil.getStreamResource(responseFile.getNume(), responseFile.getDownloadLink()));
            
            download.setTarget("_blank");
            optionsLayout.add(download);
            options.add(download);
            Anchor mobDownload = new Anchor();
            mobDownload.addClassNames("btn_download");
            mobDownload.setHref(StreamResourceUtil.getStreamResource(responseFile.getNume(), responseFile.getDownloadLink()));
            mobDownload.setTarget("_blank");
            mobileOptions.add(mobDownload);
        }
        if (enableDocumentAction &&
                Optional.ofNullable(responseFile.getAllowComments()).isPresent() && responseFile.getAllowComments().equals(1)) {
            Map<String, Object> filterPageParameters = new HashMap<>();
            filterPageParameters.put("document", document);
            filterPageParameters.put("tipDocument", documentType);
            filterPageParameters.put("request", responseFile.getIdFisierReferinta());
            filterPageParameters.put("docRaspunsId", responseFile.getId());


            Anchor uploadDocumente = new Anchor();
            uploadDocumente.addClassNames("btn", "btn-primary", "btn-secondary-hover", "btn-xsm");
            HtmlContainer downloadIcon = new HtmlContainer("i");
            downloadIcon.addClassNames("custom-icon", "icon-up");
            uploadDocumente.add(downloadIcon, new Text("document.type.service.request.view.neededfiles.service.option.upload.response.label"));
            uploadDocumente.setHref(QueryParameterUtil.getRelativePathWithQueryParameters(filterPageParameters, Ps4ECitizenServiceAttacheDocRaspunsRoute.class));

            optionsLayout.add(uploadDocumente);
            options.add(uploadDocumente);
            Anchor mobUploadDocumente = new Anchor();
            mobUploadDocumente.addClassNames("btn_upload");

            mobUploadDocumente.setHref(QueryParameterUtil.getRelativePathWithQueryParameters(filterPageParameters, Ps4ECitizenServiceAttacheDocRaspunsRoute.class));

            mobUploadDocumente.getStyle().set("cursor", "pointer");

            mobileOptions.add(mobUploadDocumente);
        }
        optionsForLayouts.add(options);
        mobileOptionsForLayouts.add(mobileOptions);
        optionLayouts.add(optionsLayout);

        String fileNameDescription = responseFile.getNume().substring(0, responseFile.getNume().lastIndexOf("."));

        fileNameDescription = fileNameDescription.substring(0,1).toUpperCase() + fileNameDescription.substring(1);

        responseFilesTable.addRow(new Label(index.getAndIncrement() + ""),
                new Label(fileNameDescription.replaceAll("_", " ")),
                new Label(responseFile.getDenumireDocument()),
                optionsLayout);
    }


    public void setTableHeaders() {
        responseFilesTable.addHeader("document.type.service.request.view.responsefiles.index.label");
        responseFilesTable.addHeader("document.type.service.request.view.responsefiles.documentname.label");
        responseFilesTable.addHeader("document.type.service.request.view.responsefiles.documentdescription.label");
        responseFilesTable.addHeader("document.type.service.request.view.responsefiles.option.label");
    }


}
