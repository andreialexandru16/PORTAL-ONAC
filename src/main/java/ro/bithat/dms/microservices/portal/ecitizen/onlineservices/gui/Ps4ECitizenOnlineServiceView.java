package ro.bithat.dms.microservices.portal.ecitizen.onlineservices.gui;

import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Paragraph;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.Document;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.TipDocument;
import ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui.Ps4ECitizenServiceRoute;
import ro.bithat.dms.microservices.portal.ecitizen.onlineservices.gui.component.SearchContainer;
import ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui.Ps4ECitizenServiceRequestRoute;
import ro.bithat.dms.microservices.portal.ecitizen.gui.ContentContainerView;
import ro.bithat.dms.passiveview.QueryParameterUtil;
import ro.bithat.dms.passiveview.VaadinClientUrlUtil;
import ro.bithat.dms.passiveview.boot.I18NProviderStatic;
import ro.bithat.dms.passiveview.component.html.ClickNotifierAnchor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Ps4ECitizenOnlineServiceView extends ContentContainerView<Ps4ECitizenOnlineServicePresenter> {


    private SearchContainer searchFormContainer = new SearchContainer(this);

    private HtmlContainer filterButton = new HtmlContainer(Tag.INPUT);

    private Div actionContainerFilter = new Div(filterButton);

    private ComboBox<String> personType = new ComboBox<>("", "Persoană fizică", "Persoană juridică");

    private Div personTypeContainerFilter = new Div(personType);

    private ComboBox<TipDocument> serviceType = new ComboBox<>("");

    private Div serviceTypeContainerFilter = new Div(serviceType);

    private Div searchParent = new Div(searchFormContainer);

    private Div filterContainerRow = new Div(searchParent,serviceTypeContainerFilter, /*personTypeContainerFilter,*/ actionContainerFilter);

    private Div filterContainer = new Div(filterContainerRow);


    @Override
    public void beforeBinding() {
        setServicesListHeaderIcon("/icons/document.png");
        setContentPageTile(I18NProviderStatic.getTranslation("ps4.ecetatean.breadcrumb.document.type.title"));
        searchParent.addClassNames("col-md-6", "col-lg-5");
        searchFormContainer.addClassName("form_lista_serv");
        filterContainer.addClassName("filter_container");
        filterContainerRow.addClassName("row");
        filterButton.getElement().setAttribute("type", "button");
        filterButton.addClassNames("btn", "btn_green", "full_width");
        filterButton.getElement().setAttribute("value", I18NProviderStatic.getTranslation("document.type.service.view.filter.action.text"));
        actionContainerFilter.addClassNames("col-md-6", "col-lg-2", "md-nom");
        personType.setValue("Tip persoana");
        personType.setAllowCustomValue(false);
        personType.addClassNames("vaadin-ps4-theme", "form-control");
        personType.getStyle().set("display", "flex");
        personTypeContainerFilter.addClassNames("col-md-6", "col-lg-3", "md-nom");
        serviceType.setItemLabelGenerator(TipDocument::getDenumire);
        serviceType.setAllowCustomValue(false);
        serviceType.setPlaceholder("Domeniu");
        serviceType.addClassNames("vaadin-ps4-theme", "form-control");
        serviceType.getStyle().set("display", "flex");
        serviceType.getStyle().set("background-color", "transparent");
        serviceTypeContainerFilter.addClassNames("col-md-6", "col-lg-5");
        UI.getCurrent().getPage().executeJs("addThemeSmall($0);", personType.getElement());
        UI.getCurrent().getPage().executeJs("addThemeSmall($0);", serviceType.getElement());

        addComponentAsFirst(filterContainer);
    }

    public SearchContainer getSearchFormContainer() {
        return searchFormContainer;
    }

    public void setServiceTypeValue(TipDocument serviceTypeValue) {
        serviceType.setValue(serviceTypeValue);
    }

    public void setPersonTypeValue(String personTypeValue) {
        personType.setValue(personTypeValue);
    }

    public TipDocument getServiceTypeValue() {
        return serviceType.getValue();
    }

    public String getPersonTypeValue() {
        return personType.getValue();
    }

    public void buildDocumentPageBodyAndPagination(List<Document> documents, int page, int totalPages) {
        buildDocumentPageBody(documents);
        addServiceListContent();
        addServiceListPagination();
        buildPagination(page, totalPages);
    }

    private void buildDocumentPageBody(List<Document> documents) {
        getServiceListContentContainer().removeAll();
        if(documents != null) {
            documents.stream().forEach(document -> buildDocumentListItem(document));
        }

    }
    public void buildServiceType(List<TipDocument> documentTypes) {
        if(documentTypes != null) {
            serviceType.setItems(documentTypes);
        }

    }
    private void buildDocumentListItem(Document document) {
        ClickNotifierAnchor documentItemTitleLink = new ClickNotifierAnchor();
        documentItemTitleLink.setText(document.getDenumire());
        if(Optional.ofNullable(document.getJspPage()).isPresent() && document.getJspPage().trim().length() > 0) {

            documentItemTitleLink.addClickListener(e -> UI.getCurrent().getPage().setLocation(document.getJspPage()));
        } else {
            Map<String, Object> documentRequestParameters = new HashMap<>();
            documentRequestParameters.put("tipDocument", document.getIdDocumentType());
            documentRequestParameters.put("document", document.getId());
            documentItemTitleLink.getStyle().set("cursor", "pointer");
            documentItemTitleLink.addClickListener(e
                    -> VaadinClientUrlUtil.setLocation(QueryParameterUtil.getRelativePathWithQueryParameters(documentRequestParameters, Ps4ECitizenServiceRequestRoute.class)));
        }
        ClickNotifierAnchor documentCategoryTitleLink = new ClickNotifierAnchor();
        documentCategoryTitleLink.setText(document.getCategorieDoc());
        Map<String, Object> documentRequestParameters = new HashMap<>();
        documentRequestParameters.put("tipDocument", document.getIdDocumentType());
        documentCategoryTitleLink.setHref(QueryParameterUtil.getRelativePathWithQueryParameters(documentRequestParameters, Ps4ECitizenServiceRoute.class));
        documentCategoryTitleLink.addClassName("categ_serv");
        Div documentItemTitleIcon = new Div();
        documentItemTitleIcon.addClassName("service_item_icon");
        documentItemTitleIcon.getStyle().set("background-image", "url('PORTAL/assets/images/icons/icon_categ.png')");
        /*documentItemTitleIcon.getStyle().set("background-image", "url('PORTAL/assets/images/icons/documentcategory/" +
                document.getCodCategorieDoc().toLowerCase().replace(" ", "_") + "_black.png')");*/

        Div divCategoryTitleLink = new Div(documentCategoryTitleLink);
        divCategoryTitleLink.addClassName("box_categ_serv_anre");

        Div documentItemTitle = new Div(divCategoryTitleLink, documentItemTitleIcon, documentItemTitleLink);
        documentItemTitle.addClassName("service-item-title");
        Div documentItemContent = new Div(new Paragraph(document.getDescriere()));
        documentItemContent.addClassName("service-item-content");
        ListItem documentListItem = new ListItem(documentItemTitle, documentItemContent);
        documentListItem.addClassName("service-single");
        getServiceListContentContainer().add(documentListItem);

    }

}
