package ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.Document;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.TipDocument;
import ro.bithat.dms.passiveview.FlowComponent;
import ro.bithat.dms.passiveview.QueryParameterUtil;
import ro.bithat.dms.passiveview.boot.I18NProviderStatic;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Route(value = "solicitare-noua-atasamente-lipsa")
@PageTitle(" Atașamente Solicitare")
public class Ps4ECitizenServiceAttacheMissingFileRoute extends DocumentTypeRoute {


    @FlowComponent
    private Ps4ECitizenServiceAttacheMissingFileView serviceAttacheFileView;

    @Override
    protected boolean buildPortalRoute() {
        String[] classNames = {"breadcrumbs_container"};
        addContentHeaderContainer("", classNames, getBreadcrumbView());
        getBreadcrumbView().clearCrumbs();
        /*getBreadcrumbView().addCrumb("ps4.ecetatean.breadcrumb.home.page.title",
                RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenAnonymousHomeRoute.class));*/

        Optional<Integer> fileId = QueryParameterUtil.getQueryParameter("request", Integer.class);
        addContentContainer(serviceAttacheFileView);


        return true;
    }


}
