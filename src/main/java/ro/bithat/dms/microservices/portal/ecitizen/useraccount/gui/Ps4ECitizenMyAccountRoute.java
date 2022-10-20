package ro.bithat.dms.microservices.portal.ecitizen.useraccount.gui;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteConfiguration;
import ro.bithat.dms.microservices.portal.ecitizen.gui.BreadcrumbView;
import ro.bithat.dms.microservices.portal.ecitizen.gui.template.Ps4ECitizenPortalRoute;
import ro.bithat.dms.microservices.portal.ecitizen.gui.Ps4ECitizenAnonymousHomeRoute;
import ro.bithat.dms.passiveview.FlowComponent;
import ro.bithat.dms.passiveview.QueryParameterUtil;
import ro.bithat.dms.security.SecurityUtils;

import java.util.Optional;

@Route(value = "contul-meu")
@PageTitle(" Contul meu")

public class Ps4ECitizenMyAccountRoute extends Ps4ECitizenPortalRoute {


    @FlowComponent
    private BreadcrumbView breadcrumbView;

    @FlowComponent
    private Ps4ECitizenMyAccountView myAccountView = new Ps4ECitizenMyAccountView();
    @Override
    protected boolean buildPortalRoute() {
        String[] classNames = {"breadcrumbs_container"};
        addContentHeaderContainer("", classNames, breadcrumbView);
        breadcrumbView.clearCrumbs();
/*
        breadcrumbView.addCrumb("ps4.ecetatean.breadcrumb.home.page.title", RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenAnonymousHomeRoute.class));
*/
        breadcrumbView.setCurrentPageTitle("ps4.ecetatean.breadcrumb.myaccount.page.title");
        Optional<Long> idUser = QueryParameterUtil.getQueryParameter("idUser", Long.class);
        Optional<String> token = QueryParameterUtil.getQueryParameter("token", String.class);
        /*if(idUser.isPresent()){
            SecurityUtils.setUserId(idUser.get());

        }
        if(token.isPresent()){
            SecurityUtils.setToken(token.get());
        }*/
        addContentContainer(myAccountView);

        return true;
    }

}
