package ro.bithat.dms.microservices.portal.ecitizen.participatorybudgeting.gui;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import ro.bithat.dms.microservices.dmsws.poi.ProjectInfo;
import ro.bithat.dms.microservices.portal.ecitizen.gui.BreadcrumbView;
import ro.bithat.dms.microservices.portal.ecitizen.gui.Ps4ECitizenAnonymousHomeRoute;
import ro.bithat.dms.microservices.portal.ecitizen.gui.template.Ps4ECitizenPortalRoute;
import ro.bithat.dms.microservices.portal.ecitizen.participatorybudgeting.backend.DmswsParticipatoryBudgetingService;
import ro.bithat.dms.passiveview.FlowComponent;
import ro.bithat.dms.passiveview.QueryParameterUtil;
import ro.bithat.dms.security.SecurityUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Route(value = "detaliu-proiect")
@PageTitle(" Detalii Proiect")

public class Ps4ECitizenProjectDetailRoute extends Ps4ECitizenPortalRoute {

    @FlowComponent
    private Ps4ECitizenProjectDetailView ps4ECitizenProjectDetailView;
    @FlowComponent
    private BreadcrumbView breadcrumbView;

    @Autowired
    private DmswsParticipatoryBudgetingService budgetingService;
    @Override
    protected boolean buildPortalRoute() {
        String[] classNames = {"breadcrumbs_container"};
        //addContentHeaderContainer("", classNames,breadcrumbView);
        addContentHeaderContainer("", classNames);
        breadcrumbView.clearCrumbs();
        breadcrumbView.addCrumb("ps4.ecetatean.breadcrumb.home.page.title",
                RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenAnonymousHomeRoute.class));
        Optional<String> fromBugetare = QueryParameterUtil.getQueryParameter("fromBugetare", String.class);

        if(fromBugetare.isPresent() && fromBugetare.get().equals("true")){
            breadcrumbView.addCrumb("ps4.ecetatean.breadcrumb.view.projects.title",
                    RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenProjectsRoute.class));
        }
        else{
            breadcrumbView.addCrumb("ps4.ecetatean.breadcrumb.view.consultation.projects.title",
                    RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenConsultationProjectsRoute.class));
        }
        Optional<Integer> idProiect = QueryParameterUtil.getQueryParameter("idProiect", Integer.class);
        if(idProiect.isPresent()) {
            ProjectInfo proiectInfo = budgetingService.getInfoPOI(SecurityUtils.getToken(),idProiect.get());
            if(proiectInfo!=null) {
                Map<String, Object> breadcrumbParameters = new HashMap<>();
                breadcrumbView.setCurrentPageTitle(proiectInfo.getDenumire());
                addContent(ps4ECitizenProjectDetailView);
            }

        }
        return true;
    }


}
