package ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui;

import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.RouteConfiguration;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.Document;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.TipDocument;
import ro.bithat.dms.microservices.portal.ecitizen.rehabilitationreq.gui.Ps4ECitizenRehabilitationRoute;
import ro.bithat.dms.passiveview.QueryParameterUtil;
import ro.bithat.dms.passiveview.VaadinClientUrlUtil;
import ro.bithat.dms.passiveview.boot.I18NProviderStatic;
import ro.bithat.dms.passiveview.component.html.ClickNotifierAnchor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Ps4ECitizenServiceView extends PageableDocumentTypeView<Ps4ECitizenServicePresenter> {

    private HtmlContainer filterButton = new HtmlContainer(Tag.INPUT);

    private Div actionContainerFilter = new Div(filterButton);

    private ComboBox<String> personType = new ComboBox<>("", "Persoană fizică", "Persoană juridică");

    private Div personTypeContainerFilter = new Div(personType);

    private ComboBox<TipDocument> serviceType = new ComboBox<>("");

    private Div serviceTypeContainerFilter = new Div(serviceType);

    private TextField keyword = new TextField();

    private Label keywordLabel= new Label("document.type.service.view.filter.keyword.placeholder");

    private Div keywordDiv = new Div(keywordLabel, keyword);

    private Div keywordContainerFilter = new Div(keywordDiv);

    private Div filterContainerRow = new Div(keywordContainerFilter, serviceTypeContainerFilter, personTypeContainerFilter, actionContainerFilter);

 //   private Div filterContainer = new Div(filterContainerRow);

    @Override
    public void beforeBinding() {
  //      filterContainer.addClassName("filter_container");
        filterContainerRow.addClassName("row");
        filterButton.getElement().setAttribute("type", "button");
        filterButton.addClassNames("btn", "btn-secondary", "btn-block");
        filterButton.getElement().setAttribute("value", I18NProviderStatic.getTranslation("document.type.service.view.filter.action.text"));
        actionContainerFilter.addClassNames("col-md-6", "col-lg-3", "md-nom");
        personType.setValue("Tip persoana");
        personType.setAllowCustomValue(false);
        personType.addClassNames("vaadin-ps4-theme", "form-control");
        personType.getStyle().set("display", "flex");
        personTypeContainerFilter.addClassNames("col-md-6", "col-lg-3", "md-nom");
        serviceType.setPlaceholder("Selecteaza categorie");
        serviceType.setItemLabelGenerator(TipDocument::getDenumire);
        serviceType.setAllowCustomValue(false);
        serviceType.addClassNames("vaadin-ps4-theme", "form-control");
        serviceType.getStyle().set("display", "flex");
        serviceType.getStyle().set("background-color", "transparent");
        serviceTypeContainerFilter.addClassNames("col-md-6", "col-lg-3");
        keyword.setPlaceholder(I18NProviderStatic.getTranslation("document.type.service.view.filter.keyword.placeholder"));
        keyword.addClassNames("vaadin-ps4-theme", "form-control");
        keyword.getStyle().set("display", "flex");
        keywordDiv.addClassNames("input", "div_label");
        UI.getCurrent().getPage().executeJs("addThemeSmall($0);", personType.getElement());
        UI.getCurrent().getPage().executeJs("addThemeSmall($0);", serviceType.getElement());

        //Use orignila delivered script
//        UI.getCurrent().getPage().executeJs("checkForInput($0)", keyword.getElement());

        keyword.getElement().addEventListener("keyup" , e -> {
            if(!e.getEventData().getString("element.value").isEmpty()) {
                if(!keywordDiv.hasClassName("show_label")) {
                    keywordDiv.addClassNames("show_label");
                }
            } else {
                if(keywordDiv.hasClassName("show_label")) {
                    keywordDiv.removeClassNames("show_label");
                }
            }

        }).addEventData("element.value");
//        keyword.addInputListener(e -> {
//        });
        keywordContainerFilter.addClassNames("col-md-6", "col-lg-3");
 //       addComponentAsFirst(filterContainer);
    }

    public void setKeywordValue(String keywordValue) {
        keyword.setValue(keywordValue);
    }

    public void setServiceTypeValue(TipDocument serviceTypeValue) {
        serviceType.setValue(serviceTypeValue);
    }

    public void setPersonTypeValue(String personTypeValue) {
        personType.setValue(personTypeValue);
    }


    public String getKeywordValue() {
        return keyword.getValue();
    }

    public TipDocument getServiceTypeValue() {
        return serviceType.getValue();
    }

    public String getPersonTypeValue() {
        return personType.getValue();
    }

    public void buildDocumentPageBodyAndPagination(TipDocument tipDocument, List<Document> documents, Integer page, Integer totalPages) {
        buildDocumentPageBody(tipDocument, documents);
        addServiceListContent();
        addServiceListPagination();
        buildPagination(page, totalPages);
    }

    public void buildDocumentPageBody(TipDocument tipDocument, List<Document> documents) {
        getServiceListContentContainer().removeAll();
        if(documents != null) {
            documents.stream().forEach(document -> buildDocumentListItem(tipDocument, document));
        }

        // daca e cerere de reabilitare
        if (tipDocument != null && tipDocument.getId() != null && tipDocument.getId().equals(getPresenter().getClassReabilitareId())){
            buildVizualizareCerereReabilitare();
        }

    }

    public void buildServiceTypeCombobox(List<TipDocument> documents) {
       serviceType.setItems(documents);
    }
    private void buildDocumentListItem(TipDocument tipDocument, Document document) {
        ClickNotifierAnchor documentItemTitleLink = new ClickNotifierAnchor();
        documentItemTitleLink.setText(document.getDenumire());
        if(Optional.ofNullable(document.getJspPage()).isPresent() && document.getJspPage().trim().length() > 0) {
            documentItemTitleLink.addClickListener(e -> UI.getCurrent().getPage().setLocation(document.getJspPage()));
        } else {
            Map<String, Object> documentRequestParameters = new HashMap<>();
            documentRequestParameters.put("tipDocument", tipDocument.getId());
            documentRequestParameters.put("document", document.getId());
            documentItemTitleLink.getStyle().set("cursor", "pointer");
            if(document.getSkipIntro()!=null&&document.getSkipIntro()){
                documentItemTitleLink.addClickListener(e
                        -> VaadinClientUrlUtil.setLocation(QueryParameterUtil.getRelativePathWithQueryParameters(documentRequestParameters, Ps4ECitizenServiceNewRequestRoute.class)));
            }else {
                documentItemTitleLink.addClickListener(e
                        -> VaadinClientUrlUtil.setLocation(QueryParameterUtil.getRelativePathWithQueryParameters(documentRequestParameters, Ps4ECitizenServiceRequestRoute.class)));
            }
//                    setHref(QueryParameterUtil.getRelativePathWithQueryParameters(documentRequestParameters, Ps4ECitizenServiceRequestRoute.class));
        }
        Div documentItemTitleIcon = new Div();
        documentItemTitleIcon.addClassName("service_item_icon");
        documentItemTitleIcon.getStyle().set("background-image", "url('PORTAL/assets/images/icons/shield.png')");
        Div documentItemTitle = new Div(documentItemTitleIcon, documentItemTitleLink);
        documentItemTitle.addClassName("service-item-title");
        documentItemTitle.getStyle().set("cursor", "pointer");

        Div documentItemContent = new Div(new Paragraph(document.getDescriere()));
        documentItemContent.addClassName("service-item-content");
        ListItem documentListItem = new ListItem(documentItemTitle, documentItemContent);
        documentListItem.addClassName("service-single");
        getServiceListContentContainer().add(documentListItem);
    }

    private void buildVizualizareCerereReabilitare() {
        ClickNotifierAnchor documentItemTitleLink = new ClickNotifierAnchor();
        documentItemTitleLink.setText(I18NProviderStatic.getTranslation("document.type.service.new.request.reabilitare.view"));
        documentItemTitleLink.getStyle().set("cursor", "pointer");

        Map<String, Object> documentRequestParameters = new HashMap<>();
        documentRequestParameters.put("fromDepunere", false);
        documentItemTitleLink.addClickListener(e
                -> VaadinClientUrlUtil.setLocation(QueryParameterUtil.getRelativePathWithQueryParameters(documentRequestParameters, Ps4ECitizenRehabilitationRoute.class)));

        Div documentItemTitleIcon = new Div();
        documentItemTitleIcon.addClassName("service_item_icon");
        documentItemTitleIcon.getStyle().set("background-image", "url('PORTAL/assets/images/icons/shield.png')");
        Div documentItemTitle = new Div(documentItemTitleIcon, documentItemTitleLink);
        documentItemTitle.addClassName("service-item-title");
        Div documentItemContent = new Div(new Paragraph(I18NProviderStatic.getTranslation("document.type.service.new.request.reabilitare.view")));
        documentItemContent.addClassName("service-item-content");
        ListItem documentListItem = new ListItem(documentItemTitle, documentItemContent);
        documentListItem.addClassName("service-single");
        getServiceListContentContainer().add(documentListItem);
    }
    public void buildVizualizareAdmAudiente() {
        //Administrare calendar
        ClickNotifierAnchor documentItemTitleLink = new ClickNotifierAnchor();
        documentItemTitleLink.setText(I18NProviderStatic.getTranslation("document.type.service.adm.calendar"));
        documentItemTitleLink.getStyle().set("cursor", "pointer");

        documentItemTitleLink.addClickListener(e
                -> VaadinClientUrlUtil.setLocation("website/administrare-calendar.html"));

        Div documentItemTitleIcon = new Div();
        documentItemTitleIcon.addClassName("service_item_icon");
        documentItemTitleIcon.getStyle().set("background-image", "url('PORTAL/assets/images/icons/dp.png')");
        Div documentItemTitle = new Div(documentItemTitleIcon, documentItemTitleLink);
        documentItemTitle.addClassName("service-item-title");
        Div documentItemContent = new Div(new Paragraph(I18NProviderStatic.getTranslation("document.type.service.adm.calendar.descriere")));
        documentItemContent.addClassName("service-item-content");
        ListItem documentListItem = new ListItem(documentItemTitle, documentItemContent);
        documentListItem.addClassName("service-single");
        getServiceListContentContainer().add(documentListItem);

        //Administrare rezervari
        ClickNotifierAnchor documentItemTitleLinkRezervari = new ClickNotifierAnchor();
        documentItemTitleLinkRezervari.setText(I18NProviderStatic.getTranslation("document.type.service.adm.rezervari"));
        documentItemTitleLinkRezervari.getStyle().set("cursor", "pointer");

        documentItemTitleLinkRezervari.addClickListener(e
                -> VaadinClientUrlUtil.setLocation("website/administrare-rezervari.html"));

        Div documentItemTitleIconRezervari = new Div();
        documentItemTitleIconRezervari.addClassName("service_item_icon");
        documentItemTitleIconRezervari.getStyle().set("background-image", "url('PORTAL/assets/images/icons/arrow-select.png')");

        Div documentItemTitleRezervari = new Div(documentItemTitleIconRezervari, documentItemTitleLinkRezervari);
        documentItemTitleRezervari.addClassName("service-item-title");
        Div documentItemContentRezervari = new Div(new Paragraph(I18NProviderStatic.getTranslation("document.type.service.adm.rezervari.descriere")));
        documentItemContentRezervari.addClassName("service-item-content");
        ListItem documentListItemRezervari = new ListItem(documentItemTitleRezervari, documentItemContentRezervari);
        documentListItemRezervari.addClassName("service-single");
        getServiceListContentContainer().add(documentListItemRezervari);
    }

}
