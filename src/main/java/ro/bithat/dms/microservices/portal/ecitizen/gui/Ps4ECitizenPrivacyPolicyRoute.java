package ro.bithat.dms.microservices.portal.ecitizen.gui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteConfiguration;
import ro.bithat.dms.microservices.portal.ecitizen.gui.template.Ps4ECitizenPortalRoute;
import ro.bithat.dms.passiveview.FlowComponent;
import ro.bithat.dms.service.StreamToStringUtil;

@Route(value = "politica-de-confidentialitate")
public class Ps4ECitizenPrivacyPolicyRoute extends Ps4ECitizenPortalRoute {

    @FlowComponent
    private BreadcrumbView breadcrumbView;


    @Override
    protected boolean buildPortalRoute() {
        String[] classNames = {"breadcrumbs_container"};
        addContentHeaderContainer("", classNames, breadcrumbView);
        breadcrumbView.clearCrumbs();
        breadcrumbView.addCrumb("ps4.ecetatean.breadcrumb.home.page.title", RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenAnonymousHomeRoute.class));
        breadcrumbView.setCurrentPageTitle("ps4.ecetatean.breadcrumb.privacypolicy.detail.title");
        getContent().getElement().setProperty("innerHTML",
                StreamToStringUtil.fileToString("static/PORTAL/privacy_policy_" + UI.getCurrent().getLocale().getCountry().toLowerCase()+".html"));
        return true;
    }


}