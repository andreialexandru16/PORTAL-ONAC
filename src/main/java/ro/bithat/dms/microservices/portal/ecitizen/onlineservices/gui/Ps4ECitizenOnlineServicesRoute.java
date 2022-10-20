package ro.bithat.dms.microservices.portal.ecitizen.onlineservices.gui;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteConfiguration;
import ro.bithat.dms.microservices.portal.ecitizen.gui.BreadcrumbView;
import ro.bithat.dms.microservices.portal.ecitizen.gui.template.Ps4ECitizenPortalRoute;
import ro.bithat.dms.microservices.portal.ecitizen.gui.Ps4ECitizenAnonymousHomeRoute;
import ro.bithat.dms.passiveview.FlowComponent;
import ro.bithat.dms.passiveview.boot.I18NProviderStatic;


@Route(value = "servicii-online")
@PageTitle(" Servicii Online")

public class Ps4ECitizenOnlineServicesRoute extends Ps4ECitizenPortalRoute {

    @FlowComponent
    private BreadcrumbView breadcrumbView;

    @FlowComponent
    private Ps4ECitizenOnlineServiceView onlineServiceView;


    @Override
    protected boolean buildPortalRoute() {
        String[] classNames = {"breadcrumbs_container"};
        addContentHeaderContainer("", classNames, breadcrumbView);
        breadcrumbView.clearCrumbs();
        breadcrumbView.addCrumb("ps4.ecetatean.breadcrumb.home.page.title", RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenAnonymousHomeRoute.class));
        breadcrumbView.setCurrentPageTitle(I18NProviderStatic.getTranslation("ps4.ecetatean.breadcrumb.document.type.title"));
        addContentContainer(onlineServiceView);
        return true;
    }


}
