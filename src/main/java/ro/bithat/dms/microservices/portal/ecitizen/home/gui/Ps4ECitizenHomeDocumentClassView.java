package ro.bithat.dms.microservices.portal.ecitizen.home.gui;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.RouteConfiguration;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.LinkUtil;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.TipDocument;
import ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui.Ps4ECitizenServiceRoute;
import ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui.component.Ps4ECitizenProcedureFormsRoute;
import ro.bithat.dms.microservices.portal.ecitizen.onlineservices.gui.component.SearchContainer;
import ro.bithat.dms.passiveview.VaadinClientUrlUtil;
import ro.bithat.dms.passiveview.boot.I18NProviderStatic;
import ro.bithat.dms.passiveview.component.html.ClickNotifierAnchor;
import ro.bithat.dms.passiveview.component.view.DivFlowViewBuilder;
import ro.bithat.dms.security.SecurityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Ps4ECitizenHomeDocumentClassView extends DivFlowViewBuilder<Ps4ECitizenHomeDocumentClassPresenter> {


//    private SearchContainer searchFormContainer = new SearchContainer(this);
//    private HtmlContainer filterButton = new HtmlContainer(Tag.INPUT);

//    private Div actionContainerFilter = new Div(filterButton);


    private ComboBox<TipDocument> serviceType = new ComboBox<>("");

    private Div serviceTypeContainerFilter = new Div(serviceType);


//    private Div filterContainer = new Div(searchFormContainer);



    private Div extraServices= new Div();

    private Div categoriesContainer= new Div();

    private Div mainDiv= new Div(categoriesContainer,extraServices);

    @Override
    protected void buildView() {

        add(mainDiv);
        categoriesContainer.addClassNames("categorii_homepage", "row");
//        searchFormContainer.addClassName("form_lista_serv");

        serviceType.setItemLabelGenerator(TipDocument::getDenumire);
        serviceType.setAllowCustomValue(false);
        serviceType.addClassNames("vaadin-ps4-theme", "form-control");
        serviceType.getStyle().set("display", "flex");
        serviceType.getStyle().set("background-color", "transparent");
        serviceTypeContainerFilter.addClassNames("col-md-6", "col-lg-3");
        UI.getCurrent().getPage().executeJs("addThemeSmall($0);", serviceType.getElement());

    }

//    public SearchContainer getSearchFormContainer() {
//        return searchFormContainer;
//    }
    public void buildServiceType(List<TipDocument> documentTypes) {
        if(documentTypes != null) {
            serviceType.setItems(documentTypes);
        }

    }
    public void buildDocumentClassRows() {
        categoriesContainer.removeAll();

        int chunkSize = 6;

        AtomicInteger counter = new AtomicInteger();
        List<TipDocument> documentsClasses = new ArrayList<>();
        if(!SecurityUtils.getToken().equalsIgnoreCase(getPresenter().getAnonymousToken())) {
            TipDocument forms = new TipDocument();
            forms.setDenumire(getTranslation("home.menu.forms"));
            forms.setCod(getTranslation("FORMS"));
            forms.setLinkUrl(RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenProcedureFormsRoute.class));
            documentsClasses.add(forms);
        }
        if(!SecurityUtils.getToken().equalsIgnoreCase(getPresenter().getAnonymousToken())) {
            TipDocument frameworkAgreement = new TipDocument();
            frameworkAgreement.setDenumire(getTranslation("home.menu.framework.agreement"));
            frameworkAgreement.setCod(getTranslation("FRAMEWORK-AGREEMENT"));
            frameworkAgreement.setLinkUrl(getPresenter().getDmsUrl() + "go_get.jsp?ws_token=" + SecurityUtils.getToken() + "&targetPage=acord_cadru_filtrare.jsp?from=CORE");
            documentsClasses.add(frameworkAgreement);
        }



        if(!SecurityUtils.getToken().equalsIgnoreCase(getPresenter().getAnonymousToken())) {

            if(getPresenter().getRequestsClassId() != null && !getPresenter().getRequestsClassId().isEmpty() && SecurityUtils.getAllDocumentTypes().stream().anyMatch(e -> e.getId().toString().equals(getPresenter().getRequestsClassId()))) {
                TipDocument requests = new TipDocument();
                requests.setDenumire(getTranslation("home.menu.requests"));
                requests.setCod(getTranslation("REQUESTS"));
                requests.setLinkUrl(RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenServiceRoute.class) + "?tipDocument=" + getPresenter().getRequestsClassId());
                documentsClasses.add(requests);
            }
        }
        documentsClasses.stream()
                .collect(Collectors.groupingBy(it -> counter.getAndIncrement() / chunkSize))
                .forEach(this::buildDocumentClassRow);
    }

    private void buildDocumentClassRow(Integer row, List<TipDocument> tipDocuments) {

        tipDocuments.forEach(documentClass -> buildDocumentClassRow(documentClass));
    }


    private void buildDocumentClassRow(TipDocument documentClass) {
        Div singleCategoryItem = new Div();
        singleCategoryItem.addClassNames("col-xl-2", "col-md-4", "col-sm-6");
        categoriesContainer.add(singleCategoryItem);
        ClickNotifierAnchor documentClassLink = new ClickNotifierAnchor();
        documentClassLink.addClassName("cat_link");
        String url = documentClass.getLinkUrl();

        documentClassLink.getStyle().set("cursor", "pointer");
        documentClassLink.addClickListener(e ->
                VaadinClientUrlUtil.setLocation(url)

        //showMessageAndRedirect(url)
        );
        HtmlContainer showMoreIcon = new HtmlContainer("i");
        showMoreIcon.addClassNames("fas", "fa-long-arrow-alt-right");
        Div showMore = new Div(showMoreIcon);
        showMore.addClassNames("show_more","text-right");
        Div hcTitle = new Div(showMore);
        hcTitle.addClassNames("hc_title", "text-center");
        hcTitle.setText(documentClass.getDenumire());
        Div pictograma = new Div();
        pictograma.addClassNames("pictograma");
        Div sgCategory = new Div(pictograma, hcTitle, showMore, documentClassLink);
        sgCategory.addClassNames("sg_category", documentClass.getCod().toLowerCase().replace(" ","_"));
        singleCategoryItem.add(sgCategory);


    }
    public void showMessageAndRedirect(String url){
        UI.getCurrent().getPage().executeJs("swalInfoParam($0,$1,$2);", "ATENTIE! Serviciu disponibil incepand cu data de 01.07.2020",getElement(),url);

    }
    @ClientCallable
    public void swalInfoAck(String url) {

        VaadinClientUrlUtil.setLocation(url);
    }
/*    @ClientCallable
    public void swalPaymentInfoConfirmation() {
        String url = RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenServiceRoute.class) + "?tipDocument="+documentClass.getId();
        VaadinClientUrlUtil.setLocation(url);

    }*/


    public void buildExtraServicesRows(List<LinkUtil> linkUtils) {
        extraServices.removeAll();
        //add(extraServices);
        extraServices.addClassName("box_servicii_aditionale");
        int chunkSize = 6;

        AtomicInteger counter = new AtomicInteger();
        linkUtils.stream()
                .collect(Collectors.groupingBy(it -> counter.getAndIncrement() / chunkSize))
                .forEach(this::buildExtraServiceRow);
    }

    private void buildExtraServiceRow(Integer row, List<LinkUtil> linkUtils) {
        Div extraServicesRow= new Div();

        extraServices.add(extraServicesRow);
        extraServicesRow.addClassNames("categorii_homepage", "row");
        linkUtils.forEach(linkUtil -> buildExtraServiceRowContent(linkUtil,extraServicesRow));
    }


    private void buildExtraServiceRowContent(LinkUtil linkUtil, Div extraServicesRow) {
        Div singleCategoryItem = new Div();
        singleCategoryItem.addClassNames("col-xl-2", "col-md-4", "col-sm-6");
        extraServicesRow.add(singleCategoryItem);
        Anchor documentClassLink = new Anchor(linkUtil.getLink());
        documentClassLink.addClassName("cat_link");

        documentClassLink.getStyle().set("cursor", "pointer");
        documentClassLink.setTarget("_blank");
        HtmlContainer showMoreIcon = new HtmlContainer("i");
        showMoreIcon.addClassNames("fas", "fa-long-arrow-alt-right");
        Div showMore = new Div(showMoreIcon);
        showMore.addClassNames("show_more","text-right");
        Div hcTitle = new Div(showMore);
        hcTitle.addClassNames("hc_title", "text-center");
        hcTitle.setText(linkUtil.getDenumire());
        Div pictograma = new Div();
        pictograma.addClassNames("pictograma");
        Div sgCategory = new Div(pictograma, hcTitle, showMore, documentClassLink);
        sgCategory.addClassNames("sg_category",linkUtil.getDenumire().toLowerCase().replace(" ","_"));
        singleCategoryItem.add(sgCategory);
    }

//    public void addSearchFormContainer() {
//        addComponentAsFirst(filterContainer);
//
//    }
}
