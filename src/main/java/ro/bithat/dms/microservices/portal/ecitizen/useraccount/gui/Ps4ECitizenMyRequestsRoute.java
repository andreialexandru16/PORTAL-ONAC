package ro.bithat.dms.microservices.portal.ecitizen.useraccount.gui;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteConfiguration;
import ro.bithat.dms.microservices.portal.ecitizen.gui.BreadcrumbView;
import ro.bithat.dms.microservices.portal.ecitizen.gui.template.Ps4ECitizenPortalRoute;
import ro.bithat.dms.passiveview.FlowComponent;

@Route(value = "contul-meu-cererile-mele")
@PageTitle(" Cererile mele")

public class Ps4ECitizenMyRequestsRoute extends Ps4ECitizenPortalRoute {


    @FlowComponent
    private BreadcrumbView breadcrumbView;

    @FlowComponent
    private Ps4ECitizenMyRequestsView myRequestsView;

    @Override
    protected boolean buildPortalRoute() {
        String[] classNames = {"breadcrumbs_container"};
        addContentHeaderContainer("", classNames, breadcrumbView);
        breadcrumbView.clearCrumbs();
/*
        breadcrumbView.addCrumb("ps4.ecetatean.breadcrumb.home.page.title", RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenAnonymousHomeRoute.class));
*/
        breadcrumbView.addCrumb("ps4.ecetatean.breadcrumb.myaccount.page.title", RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenMyAccountRoute.class));
        breadcrumbView.setCurrentPageTitle("ps4.ecetatean.breadcrumb.myaccount.myrequestsStandard.page.title");
        addContentContainer(myRequestsView);
        return true;
    }

}
