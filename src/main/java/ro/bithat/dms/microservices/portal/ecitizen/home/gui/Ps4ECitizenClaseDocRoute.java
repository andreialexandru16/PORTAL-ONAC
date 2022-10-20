package ro.bithat.dms.microservices.portal.ecitizen.home.gui;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteConfiguration;
import ro.bithat.dms.microservices.portal.ecitizen.gui.Ps4ECitizenAnonymousHomeRoute;
import ro.bithat.dms.microservices.portal.ecitizen.gui.template.Ps4ECitizenPortalRoute;
import ro.bithat.dms.passiveview.FlowComponent;
import ro.bithat.dms.passiveview.QueryParameterUtil;
import ro.bithat.dms.passiveview.VaadinClientUrlUtil;

import java.util.Optional;

@Route(value = "clase-doc")
public class Ps4ECitizenClaseDocRoute extends Ps4ECitizenPortalRoute {


    @FlowComponent
    private Ps4ECitizenHomeClaseDocByTagView docByTagView;



    @Override
    protected boolean buildPortalRoute() {
        addContentContainer(docByTagView);

        return true;
    }
}
