package ro.bithat.dms.microservices.portal.ecitizen.participatorybudgeting.gui;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteConfiguration;
import ro.bithat.dms.microservices.portal.ecitizen.gui.BreadcrumbView;
import ro.bithat.dms.microservices.portal.ecitizen.gui.template.Ps4ECitizenPortalRoute;
import ro.bithat.dms.microservices.portal.ecitizen.gui.Ps4ECitizenAnonymousHomeRoute;
import ro.bithat.dms.passiveview.FlowComponent;

@Route(value = "consultare-cetateni")
@PageTitle(" Proiecte Prezentate")
public class Ps4ECitizenConsultationRoute extends Ps4ECitizenPortalRoute {


    @FlowComponent
    private BreadcrumbView breadcrumbView;


    @FlowComponent
    private Ps4ECitizenConsultationView consultationView;


    @Override
    protected boolean buildPortalRoute() {
        String[] classNames = {"breadcrumbs_container"};
        addContentHeaderContainer("", classNames, breadcrumbView);
        breadcrumbView.clearCrumbs();
        breadcrumbView.addCrumb("ps4.ecetatean.breadcrumb.home.page.title", RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenAnonymousHomeRoute.class));
        breadcrumbView.addCrumb("ps4.ecetatean.breadcrumb.view.consultation.projects.title", RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenConsultationProjectsRoute.class));
        breadcrumbView.setCurrentPageTitle("ps4.ecetatean.breadcrumb.portalfile.consultation.page.title");
        addContent(consultationView);
        return true;
    }

}
