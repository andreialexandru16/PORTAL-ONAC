package ro.bithat.dms.microservices.portal.ecitizen.participatorybudgeting.gui;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteConfiguration;
import ro.bithat.dms.microservices.portal.ecitizen.gui.BreadcrumbView;
import ro.bithat.dms.microservices.portal.ecitizen.gui.template.Ps4ECitizenPortalRoute;
import ro.bithat.dms.microservices.portal.ecitizen.gui.Ps4ECitizenAnonymousHomeRoute;
import ro.bithat.dms.passiveview.FlowComponent;

@Route(value = "bugetare-participativa")
@PageTitle(" Bugetare Participativă")
public class Ps4ECitizenParticipatoryBudgetingRoute extends Ps4ECitizenPortalRoute {


    @FlowComponent
    private BreadcrumbView breadcrumbView;


    @FlowComponent
    private Ps4ECitizenParticipatoryBudgetingView participatoryBudgetingView;


    @Override
    protected boolean buildPortalRoute() {
        String[] classNames = {"breadcrumbs_container"};
        addContentHeaderContainer("", classNames, breadcrumbView);
        breadcrumbView.clearCrumbs();
        breadcrumbView.addCrumb("ps4.ecetatean.breadcrumb.home.page.title", RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenAnonymousHomeRoute.class));
        breadcrumbView.addCrumb("ps4.ecetatean.breadcrumb.view.projects.title", RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenProjectsRoute.class));
        breadcrumbView.setCurrentPageTitle("ps4.ecetatean.breadcrumb.portalfile.participatorybudgeting.page.title");
        addContent(participatoryBudgetingView);
        return true;
    }

}
