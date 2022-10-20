package ro.bithat.dms.microservices.portal.ecitizen.gui;

import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Nav;
import com.vaadin.flow.component.html.OrderedList;
import ro.bithat.dms.passiveview.VaadinClientUrlUtil;
import ro.bithat.dms.passiveview.component.html.ClickNotifierAnchor;
import ro.bithat.dms.passiveview.component.view.DivFlowViewBuilder;

public class BreadcrumbView extends DivFlowViewBuilder<BreadcrumbPresenter> {

    private OrderedList breadcrumbs = new OrderedList();

    private Nav breadcrumbNav = new Nav(breadcrumbs);

    @Override
    protected void buildView() {
        addClassName("container");
        breadcrumbNav.getElement().setAttribute("aria-label", "breadcrumb");
        breadcrumbs.addClassName("breadcrumb");
        add(breadcrumbNav);
    }

    public void clearCrumbs() {
        breadcrumbs.removeAll();
    }

    public void setCurrentPageTitle(String currentPageTitle) {
        ListItem breadcrumb = new ListItem();
        breadcrumb.getElement().setAttribute("aria-current", "page");
        breadcrumb.addClassNames("breadcrumb-item", "active");
        breadcrumb.setText(currentPageTitle);
        breadcrumbs.addComponentAtIndex((int)breadcrumbs.getChildren().count(), breadcrumb);
    }

    public void addCrumb(String pageTitle, String url) {
        ClickNotifierAnchor breadcrumbLink = new ClickNotifierAnchor();
        breadcrumbLink.getStyle().set("cursor", "pointer");
        breadcrumbLink.setText(pageTitle);
        ListItem breadcrumb = new ListItem(breadcrumbLink);
        breadcrumb.addClassName("breadcrumb-item");
        breadcrumb.addClickListener(e
                -> VaadinClientUrlUtil.setLocation(url));
        breadcrumbs.add(breadcrumb);
    }
}
