package ro.bithat.dms.microservices.portal.ecitizen.cache.gui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import ro.bithat.dms.microservices.portal.ecitizen.cache.backend.CacheService;
import ro.bithat.dms.microservices.portal.ecitizen.gui.BreadcrumbView;
import ro.bithat.dms.microservices.portal.ecitizen.gui.template.Ps4ECitizenPortalRoute;
import ro.bithat.dms.passiveview.FlowComponent;


@Route(value = "administrare")
@PageTitle("Administrare")

public class CacheRoute extends Ps4ECitizenPortalRoute {

	@Autowired
	private CacheService service;
	
    @FlowComponent
    private BreadcrumbView breadcrumbView;

    @Override
    protected boolean buildPortalRoute() {
        String[] classNames = {"breadcrumbs_container"};
        addContentHeaderContainer("", classNames, breadcrumbView);
        
        addContentContainer(buildContent());
        return true;
    }

    private Div buildContent() {
    	Div div = new Div();
    	Button clearCache = new Button("Goleste Cache");
    	clearCache.addClickListener(l->{
    		service.clearCaches();
    		Notification.show("Cacheul a fost golit!");
    	});
    	div.add(clearCache);
    	return div;
    }
}
