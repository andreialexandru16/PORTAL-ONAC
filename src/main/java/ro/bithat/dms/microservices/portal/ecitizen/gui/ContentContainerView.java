package ro.bithat.dms.microservices.portal.ecitizen.gui;

import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.page.BrowserWindowResizeEvent;
import com.vaadin.flow.component.page.ExtendedClientDetails;
import ro.bithat.dms.microservices.portal.ecitizen.gui.component.LoadingSpinner;
import ro.bithat.dms.microservices.portal.ecitizen.gui.template.ClientDetailsObserver;
import ro.bithat.dms.microservices.portal.ecitizen.gui.template.WindowResizeObserver;
import ro.bithat.dms.passiveview.QueryParameterUtil;
import ro.bithat.dms.passiveview.VaadinClientUrlUtil;
import ro.bithat.dms.passiveview.component.html.ClickNotifierAnchor;
import ro.bithat.dms.passiveview.component.view.DivFlowViewBuilder;
import ro.bithat.dms.passiveview.mvp.FlowPresenter;
import ro.bithat.dms.passiveview.mvp.observer.FlowViewBeforeBindingObserver;

import java.util.Optional;

public abstract class ContentContainerView<P extends FlowPresenter> extends DivFlowViewBuilder<P> implements FlowViewBeforeBindingObserver, ClientDetailsObserver, WindowResizeObserver {

    private UnorderedList paginationContainer = new UnorderedList();

    private Nav servicePagination = new Nav(paginationContainer);

    private Div serviceListPagination = new Div(servicePagination);

    private UnorderedList serviceListContentContainer = new UnorderedList();

    private Div serviceListContent = new Div(serviceListContentContainer);

    private H2 servicesHeaderTitleText = new H2();

    private Integer clientWidth;

    private Div servicesListHeaderTitle = new Div(servicesHeaderTitleText);

    private Div servicesListHeaderIcon = new Div();

    private Div serviceListHeader = new Div(servicesListHeaderIcon, servicesListHeaderTitle);

    private Div serviceListContainer = new Div(serviceListHeader);

    private LoadingSpinner loadingSpinner = new LoadingSpinner();

    @Override
    protected void buildView() {
        serviceListContent.addClassName("service_body");
        servicesListHeaderTitle.addClassName("service_title");
        servicesListHeaderIcon.addClassName("service_pictogram");
        serviceListHeader.addClassName("services_header");
        serviceListContainer.addClassName("services_list_container");
        paginationContainer.addClassNames("pagination", "justify-content-end");
        servicePagination.getElement().setAttribute("aria-label", "Services Pagination");
        serviceListPagination.addClassName("pagination_container");
        add(new Hr(), serviceListContainer);
    }

    protected Div getServiceListHeader() {
        return serviceListHeader;
    }

    protected Div getServiceListContent() {
        return serviceListContent;
    }

    public Div getServiceListContainer() {
        return serviceListContainer;
    }

    protected UnorderedList getServiceListContentContainer() {
        return serviceListContentContainer;
    }

    protected void setServicesListHeaderIcon(String iconRelativePath) {
        /*servicesListHeaderIcon.getStyle().set("background-image", "url('PORTAL/assets/images" + iconRelativePath + "')");*/
        servicesListHeaderIcon.getStyle().set("background-image", "url('PORTAL/assets/images/icons/document.png')");
    }

    protected void setServicesListHeaderFontAwesomeIcon(String... classNames) {
        HtmlContainer icon = new HtmlContainer("i");
        icon.addClassNames(classNames);
        servicesListHeaderIcon.add(icon);
    }

    public void setContentPageTile(String titleTextBundleLabel) {
        servicesHeaderTitleText.setText(titleTextBundleLabel);
    }

    public Integer getClientWidth() {
        return clientWidth;
    }

    public void setClientWidth(Integer clientWidth) {
        this.clientWidth = clientWidth;
    }

    protected void removeServiceListHeader() {
        serviceListContainer.remove(serviceListHeader);
    }

    public void removeServiceListContent() {
        serviceListContainer.remove(serviceListContent);
    }

    protected void addServiceListHeader() {
        serviceListContainer.add(serviceListHeader);
    }

    protected void addServiceListContent() {
        serviceListContainer.add(serviceListContent);
    }

    protected void addServiceListPagination() {
        serviceListContainer.add(serviceListPagination);
    }

    public Div getServiceListPagination() {
        return serviceListPagination;
    }

    public void setServiceListPagination(Div serviceListPagination) {
        this.serviceListPagination = serviceListPagination;
    }

    public void showLoading() {
        loadingSpinner.show();
    }

    public void closeLoading() {
        loadingSpinner.close();
    }

    protected void buildPagination(Integer page, Integer totalPages) {
        if(paginationContainer.getChildren().count() > 0) {
            paginationContainer.removeAll();
        }
        if (page != 0) {
            HtmlContainer iconPrev = new HtmlContainer("i");
            iconPrev.addClassNames("fas", "fa-chevron-left");
            buildPager(page - 1, "prev", Optional.of(iconPrev));
        }
        for(int i = 0; i < totalPages; i++) {
            buildPager(i, i == page ? "active" : "", Optional.empty());
        }
        if (page < totalPages - 1) {
            HtmlContainer iconPrev = new HtmlContainer("i");
            iconPrev.addClassNames("fas", "fa-chevron-right");
            buildPager(page + 1, "next", Optional.of(iconPrev));
        }
    }

    private void buildPager(Integer page, String classType, Optional<HtmlContainer> icon) {
        ClickNotifierAnchor pageLink = new ClickNotifierAnchor();
        pageLink.getStyle().set("cursor", "pointer");
        pageLink.addClassName("page-link");
//        pageLink.setHref("javas");
        pageLink.addClickListener(e
                -> VaadinClientUrlUtil.setLocation(QueryParameterUtil.getRelativePathWithQueryParameter("pagina", page)));

        if(icon.isPresent()){
            pageLink.add(icon.get());
        } else  {
            pageLink.setText((page + 1)+ "");
        }
        ListItem pageListItem = new ListItem(pageLink);
        if(classType!=null && classType.trim().length() > 0) {
            pageListItem.addClassNames("page-item", classType);
        } else {
            pageListItem.addClassName("page-item");
        }
        paginationContainer.add(pageListItem);
    }

    @Override
    public void receiveClientDetails(ExtendedClientDetails extendedClientDetails) {
        displayForWidth(extendedClientDetails.getBodyClientWidth() );
        setClientWidth(extendedClientDetails.getBodyClientWidth());
    }

    @Override
    public void browserWindowResized(BrowserWindowResizeEvent event) {
        if(Optional.ofNullable(getClientWidth()).isPresent() && Math.abs(getClientWidth() - event.getWidth()) > 300) {
            UI.getCurrent().getPage().reload();
        } else {
            displayForWidth(event.getWidth());
        }
        setClientWidth(event.getWidth());
    }

    protected void displayForWidth(int width) {

    }

    protected ClickNotifierAnchor constructClickNotifierAnchor(String text, String className) {
        ClickNotifierAnchor clickAnchor=new ClickNotifierAnchor();
        clickAnchor.addClassName(className);
        clickAnchor.setText(text);
        return clickAnchor;
    }

    protected Span constructSpan(String className) {
        Span span=new Span();
        if(Optional.ofNullable(className).isPresent()) {
            span.addClassName(className);
        }
        return span;
    }

    protected HtmlContainer constructIcon(String... classNames) {
        HtmlContainer icon = new HtmlContainer("i");
        if(classNames != null && classNames.length > 0) {
            icon.addClassNames(classNames);
        }
        return icon;
    }


}
