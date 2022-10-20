package ro.bithat.dms.microservices.portal.ecitizen.portalfile.gui;

import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Label;
import ro.bithat.dms.microservices.dmsws.file.PortalFile;
import ro.bithat.dms.microservices.portal.ecitizen.gui.component.TableContainerDiv;
import ro.bithat.dms.microservices.portal.ecitizen.portalfile.gui.component.SearchFilesContainer;
import ro.bithat.dms.passiveview.StreamResourceUtil;
import ro.bithat.dms.passiveview.boot.I18NProviderStatic;
import ro.bithat.dms.passiveview.component.view.DivFlowViewBuilder;

import java.util.List;

public class Ps4ECitizenCouncilDecisionsView extends DivFlowViewBuilder<Ps4ECitizenCouncilDecisionsPresenter> {

    private SearchFilesContainer searchFormContainerFiles = new SearchFilesContainer(this);

    private TableContainerDiv serviceDocumentsTable =
            new TableContainerDiv();


    @Override
    public void buildView() {

        setTableHeaders();
        add(searchFormContainerFiles,serviceDocumentsTable);
        addClassName("hcl_container");
        addTableClassNames("table-responsive", "table tbl_blue_head_divider");

    }

    public SearchFilesContainer getSearchFormContainerFiles() {
        return searchFormContainerFiles;
    }
    public void i18nInboxContainer(){
        searchFormContainerFiles.i18nInboxContainer();
    }

    public void setPortalFileTable(List<PortalFile> myDocuments) {
        serviceDocumentsTable.clearContent();
        myDocuments.stream().forEach(document -> setPortalFileTableRow(document));
    }

    private void setPortalFileTableRow(PortalFile document) {

        Label number = new Label(document.getNrInreg());
        number.getElement().setAttribute("td-class", "text-center");

        Label meetingDate = new Label(document.getDataInreg());
        meetingDate.getElement().setAttribute("td-class", "text-center");

        Label description = new Label(document.getDescriere());

        Anchor downloadLink = new Anchor();
        downloadLink.getElement().setAttribute("td-class", "text-center");


        HtmlContainer iconDownload = new HtmlContainer("i");
        if (document.getDownloadLink() != null) {
            iconDownload.addClassNames("fas", "fa-file-pdf");

            downloadLink.addClassNames("btn", "btn-maincolor");
            downloadLink.setHref(StreamResourceUtil.getStreamResource(document.getNume(), document.getDownloadLink()));
            downloadLink.setTarget("_blank");
            downloadLink.add(iconDownload, new Text(I18NProviderStatic.getTranslation("portalfile.view.provisions.download.label")));

        }

        Anchor downloadLinkFisierRef = new Anchor();
        downloadLinkFisierRef.getElement().setAttribute("td-class", "text-center");


        HtmlContainer iconDownloadFisierRef = new HtmlContainer("i");
        if (document.getDownloadLinkFisierReferinta() != null) {
            iconDownloadFisierRef.addClassNames("fas", "fa-file-pdf");

            downloadLinkFisierRef.addClassNames("btn", "btn-maincolor");
            downloadLinkFisierRef.setHref(StreamResourceUtil.getStreamResource(document.getNumeFisierReferinta(), document.getDownloadLinkFisierReferinta()));
            downloadLinkFisierRef.setTarget("_blank");
            downloadLinkFisierRef.add(iconDownloadFisierRef, new Text(I18NProviderStatic.getTranslation("portalfile.view.councildecisions.download.file.ref.label")));

        }
        serviceDocumentsTable.addRow(
                new Label(document.getNumeBaza()),
                description,
                number,
                meetingDate,
                downloadLink,
                downloadLinkFisierRef

        );

    }

    public void addTableClassNames(String classNameResponsiveTable, String classNamesTable) {
        serviceDocumentsTable.addClassNames(classNameResponsiveTable.split(" "));
        serviceDocumentsTable.setTableClassNames(classNamesTable);
    }

    public void setTableHeaders() {
        serviceDocumentsTable.addHeader("portalfile.view.file.name.label", "text-nowrap");
        serviceDocumentsTable.addHeader("portalfile.view.file.description.label", "text-nowrap");
        serviceDocumentsTable.addHeader("portalfile.view.file.number.label", "text-nowrap text-center");
        serviceDocumentsTable.addHeader("portalfile.view.file.meeting.from.date.label", "text-nowrap hcl_date text-center");
        serviceDocumentsTable.addHeader("portalfile.view.provisions.download.label", "text-nowrap hcl_download text-center");
        serviceDocumentsTable.addHeader("portalfile.view.councildecisions.download.file.ref.label", "text-nowrap text-center");

    }



}
