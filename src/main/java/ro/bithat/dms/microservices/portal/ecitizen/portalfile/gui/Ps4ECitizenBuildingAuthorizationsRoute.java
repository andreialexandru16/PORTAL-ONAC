package ro.bithat.dms.microservices.portal.ecitizen.portalfile.gui;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import ro.bithat.dms.microservices.dmsws.ps4.PS4Service;
import ro.bithat.dms.microservices.portal.ecitizen.gui.BreadcrumbView;
import ro.bithat.dms.microservices.portal.ecitizen.gui.template.Ps4ECitizenPortalRoute;
import ro.bithat.dms.microservices.portal.ecitizen.gui.Ps4ECitizenAnonymousHomeRoute;
import ro.bithat.dms.passiveview.FlowComponent;
import ro.bithat.dms.passiveview.QueryParameterUtil;

import java.util.Optional;

@Route(value = "autorizatii-construire")
@PageTitle("PORTAL - Autorizatii de construire")

public class Ps4ECitizenBuildingAuthorizationsRoute extends Ps4ECitizenPortalRoute {


    @FlowComponent
    private BreadcrumbView breadcrumbView;

    @FlowComponent
    private Ps4ECitizenBuildingAuthorizationsView portalFileView;

    @Autowired
    PS4Service ps4Service;
    @Override
    protected boolean buildPortalRoute() {
        String[] classNames = {"breadcrumbs_container"};
        addContentHeaderContainer("", classNames, breadcrumbView);
        Optional<Integer> documentTypeId = QueryParameterUtil.getQueryParameter("tipDocument", Integer.class);

        breadcrumbView.clearCrumbs();
        breadcrumbView.addCrumb("ps4.ecetatean.breadcrumb.home.page.title", RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenAnonymousHomeRoute.class));

        breadcrumbView.setCurrentPageTitle("ps4.ecetatean.breadcrumb.portalfile.building.authorizations.page.title");

        portalFileView.getPresenter().setDocumentTypeId(documentTypeId.get());
        addContentContainer(portalFileView);
        return true;
    }

}
