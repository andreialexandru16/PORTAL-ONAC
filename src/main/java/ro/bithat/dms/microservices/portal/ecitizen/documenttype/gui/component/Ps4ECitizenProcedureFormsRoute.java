package ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui.component;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteConfiguration;
import ro.bithat.dms.microservices.portal.ecitizen.gui.BreadcrumbView;
import ro.bithat.dms.microservices.portal.ecitizen.gui.Ps4ECitizenAnonymousHomeRoute;
import ro.bithat.dms.microservices.portal.ecitizen.gui.template.Ps4ECitizenPortalRoute;
import ro.bithat.dms.passiveview.FlowComponent;
import ro.bithat.dms.passiveview.boot.I18NProviderStatic;


@Route(value = "formulare")
@PageTitle(" Servicii Online")

public class Ps4ECitizenProcedureFormsRoute extends Ps4ECitizenPortalRoute {

    @FlowComponent
    private BreadcrumbView breadcrumbView;

    @FlowComponent
    private Ps4ECitizenProcedureFormsView onlineServiceView;


    @Override
    protected boolean buildPortalRoute() {
        String[] classNames = {"breadcrumbs_container"};
        addContentHeaderContainer("", classNames, breadcrumbView);
        breadcrumbView.clearCrumbs();
        breadcrumbView.addCrumb("ps4.ecetatean.breadcrumb.home.page.title", RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenAnonymousHomeRoute.class));
        breadcrumbView.setCurrentPageTitle(I18NProviderStatic.getTranslation("forms"));
        addContentContainer(onlineServiceView);
        return true;
    }


}
