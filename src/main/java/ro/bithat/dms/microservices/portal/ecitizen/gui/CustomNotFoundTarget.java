package ro.bithat.dms.microservices.portal.ecitizen.gui;

import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.ErrorParameter;
import com.vaadin.flow.router.NotFoundException;
import com.vaadin.flow.router.RouteNotFoundError;
import ro.bithat.dms.service.StreamToStringUtil;

import javax.servlet.http.HttpServletResponse;

public class CustomNotFoundTarget extends RouteNotFoundError {

    @Override
    public int setErrorParameter(BeforeEnterEvent event,
            ErrorParameter<NotFoundException> parameter) {
    	
		getElement().setProperty("innerHTML", 
					StreamToStringUtil.fileToString("static/PORTAL/page2-404.html"));
		
//		UI.getCurrent().getPage().setLocation(RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenHomeRoute.class));
        return HttpServletResponse.SC_NOT_FOUND;
    }
}