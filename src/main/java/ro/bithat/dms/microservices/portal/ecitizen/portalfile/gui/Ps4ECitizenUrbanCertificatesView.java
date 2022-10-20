package ro.bithat.dms.microservices.portal.ecitizen.portalfile.gui;

import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Input;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.router.RouteConfiguration;
import ro.bithat.dms.microservices.dmsws.poi.ProjectInfo;
import ro.bithat.dms.microservices.portal.ecitizen.gui.component.TableContainerDiv;
import ro.bithat.dms.microservices.portal.ecitizen.portalfile.gui.component.AddProjectlDialogComponent;
import ro.bithat.dms.microservices.portal.ecitizen.portalfile.gui.component.SearchFilesContainer;
import ro.bithat.dms.passiveview.VaadinClientUrlUtil;
import ro.bithat.dms.passiveview.boot.I18NProviderStatic;
import ro.bithat.dms.passiveview.component.html.ClickNotifierAnchor;
import ro.bithat.dms.passiveview.component.view.DivFlowViewBuilder;

import java.util.List;

public class Ps4ECitizenUrbanCertificatesView extends DivFlowViewBuilder<Ps4ECitizenUrbanCertificatesPresenter> {

    private SearchFilesContainer searchFormContainerFiles = new SearchFilesContainer(this);

    private TableContainerDiv serviceDocumentsTable =
            new TableContainerDiv();

    private Input newProjectBtn= new Input();

    private Div btnContainer= new Div(newProjectBtn);

    AddProjectlDialogComponent addProjectlDialogComponent = new AddProjectlDialogComponent(this);

    @Override
    public void buildView() {

        removeAll();
        UI.getCurrent().getPage().addJavaScript("frontend/js/gmapAddr.js");
        UI.getCurrent().getPage().addJavaScript("https://maps.googleapis.com/maps/api/js?key=" + getPresenter().getGoogleMapsApiKey() + "&language=ro&region=RO");

        setTableHeaders();
        add(btnContainer,searchFormContainerFiles,serviceDocumentsTable);
        addClassName("hcl_container");
        addTableClassNames("table-responsive", "table tbl_blue_head_divider");
        newProjectBtn.setValue(I18NProviderStatic.getTranslation("ps4.ecetatean.breadcrumb.portalfile.urban.certificates.add.btn"));
        newProjectBtn.setType("button");
        newProjectBtn.addClassNames("btn", "btn-primary", "col-sm-4", "col-md-3", "col-lg-2");
        btnContainer.addClassNames("form-group","row","no-gutters");

        addProjectlDialogComponent.setId("addProjectModal");
        add(addProjectlDialogComponent);
        //addProjectlDialogComponent.setClickEventAddProject();
        newProjectBtn.getElement().setAttribute("data-toggle", "modal");
        newProjectBtn.getElement().setAttribute("data-target", "#addProjectModal");

    }

    public void setAddProjectDialog(){
        addProjectlDialogComponent.setClickEventAddProject();
    }
    public SearchFilesContainer getSearchFormContainerFiles() {
        return searchFormContainerFiles;
    }
    public void i18nInboxContainer(){
        searchFormContainerFiles.i18nInboxContainer();
    }

    public void setPortalFileTable(List<ProjectInfo> myDocuments) {
        serviceDocumentsTable.clearContent();
        myDocuments.stream().forEach(document -> setPortalFileTableRow(document));
    }

    private void setPortalFileTableRow(ProjectInfo document) {

        Label number = new Label(document.getDenumire());

        Label adresaImobil = new Label(document.getAddress());
        Label tipLucrare = new Label(document.getTipLucrare());

        Anchor downloadLink = new Anchor();
        downloadLink.getElement().setAttribute("td-class", "text-center");


        ClickNotifierAnchor goToMap = new ClickNotifierAnchor();
        goToMap.setHref("javascript:void(0)");
        goToMap.addClickListener(e
                -> VaadinClientUrlUtil
                .setLocation(RouteConfiguration.forApplicationScope()
                        .getUrl(Ps4ECitizenUrbanDocumentsRoute.class) + "?fromDepunere=false&document="+ document.getId()));
        HtmlContainer iconMap = new HtmlContainer("i");
        iconMap.addClassNames("fas", "fa-map");
        goToMap.getElement().setAttribute("td-class", "text-center");
        goToMap.add(iconMap);

        serviceDocumentsTable.addRow(
                number,
                adresaImobil,
                new Label(document.getPropusDeNume() + " "+ document.getPropusDePrenume()),
                tipLucrare,
                goToMap

        );

    }

    public void addTableClassNames(String classNameResponsiveTable, String classNamesTable) {
        serviceDocumentsTable.addClassNames(classNameResponsiveTable.split(" "));
        serviceDocumentsTable.setTableClassNames(classNamesTable);
    }

    public void setTableHeaders() {
        serviceDocumentsTable.addHeader("ps4.ecetatean.breadcrumb.portalfile.urban.certificates.nr", "text-nowrap");
        serviceDocumentsTable.addHeader("ps4.ecetatean.breadcrumb.portalfile.urban.certificates.address", "text-nowrap");
        serviceDocumentsTable.addHeader("ps4.ecetatean.breadcrumb.portalfile.urban.certificates.requester", "text-nowrap");
        serviceDocumentsTable.addHeader("ps4.ecetatean.breadcrumb.portalfile.urban.certificates.type", "text-nowrap");
        serviceDocumentsTable.addHeader("ps4.ecetatean.breadcrumb.portalfile.urban.certificates.map", "text-nowrap");

    }

    public void hideAddNewDocumentBtn() {
        remove(btnContainer);
    }

}
