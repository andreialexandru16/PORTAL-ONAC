package ro.bithat.dms.microservices.portal.ecitizen.gui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteConfiguration;
import ro.bithat.dms.microservices.portal.ecitizen.gui.template.Ps4ECitizenPortalRoute;
import ro.bithat.dms.passiveview.FlowComponent;
import ro.bithat.dms.service.StreamToStringUtil;

@Route(value = "termeni-si-conditii")
public class Ps4ECitizenTermsAndConditionsRoute extends Ps4ECitizenPortalRoute {

    @FlowComponent
    private BreadcrumbView breadcrumbView = new BreadcrumbView();


    @Override
    protected boolean buildPortalRoute() {
        String[] classNames = {"breadcrumbs_container"};
        addContentHeaderContainer("", classNames, breadcrumbView);
        breadcrumbView.clearCrumbs();
        breadcrumbView.addCrumb("ps4.ecetatean.breadcrumb.home.page.title", RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenAnonymousHomeRoute.class));
        breadcrumbView.setCurrentPageTitle("ps4.ecetatean.breadcrumb.terms.detail.title");
        getContent().getElement().setProperty("innerHTML",
                StreamToStringUtil.fileToString("static/PORTAL/terms_and_conditions_" + UI.getCurrent().getLocale().getCountry().toLowerCase()+".html"));
        return true;
    }


}