package ro.bithat.dms.microservices.portal.ecitizen.portalfile.gui;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteConfiguration;
import ro.bithat.dms.microservices.portal.ecitizen.gui.BreadcrumbView;
import ro.bithat.dms.microservices.portal.ecitizen.gui.Ps4ECitizenAnonymousHomeRoute;
import ro.bithat.dms.microservices.portal.ecitizen.gui.template.Ps4ECitizenPortalRoute;
import ro.bithat.dms.passiveview.FlowComponent;

@Route(value = "hotarari-consiliu")
@PageTitle(" Hotărâri Consiliu")

public class Ps4ECitizenCouncilDecisionsRoute extends Ps4ECitizenPortalRoute {


    @FlowComponent
    private BreadcrumbView breadcrumbView;

    @FlowComponent
    private Ps4ECitizenCouncilDecisionsView portalFileView;

    private final static Integer documentTypeId=41; // documenta dms database setup
    @Override
    protected boolean buildPortalRoute() {
        String[] classNames = {"breadcrumbs_container"};
        addContentHeaderContainer("", classNames, breadcrumbView);
        breadcrumbView.clearCrumbs();
        breadcrumbView.addCrumb("ps4.ecetatean.breadcrumb.home.page.title", RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenAnonymousHomeRoute.class));
        breadcrumbView.setCurrentPageTitle("ps4.ecetatean.breadcrumb.portalfile.council.decisions.page.title");

        portalFileView.getPresenter().setDocumentTypeId(documentTypeId);
        addContentContainer(portalFileView);
        return true;
    }

}
