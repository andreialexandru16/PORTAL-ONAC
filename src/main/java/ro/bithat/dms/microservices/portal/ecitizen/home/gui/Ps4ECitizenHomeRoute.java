package ro.bithat.dms.microservices.portal.ecitizen.home.gui;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteConfiguration;
import ro.bithat.dms.microservices.portal.ecitizen.gui.Ps4ECitizenAnonymousHomeRoute;
import ro.bithat.dms.passiveview.VaadinClientUrlUtil;

@Route(value = "index")
public class Ps4ECitizenHomeRoute extends Div implements BeforeEnterObserver {


    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        VaadinClientUrlUtil.setLocation(RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenAnonymousHomeRoute.class));
    }



}
