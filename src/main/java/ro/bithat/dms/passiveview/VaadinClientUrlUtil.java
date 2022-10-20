package ro.bithat.dms.passiveview;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.Location;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public final class VaadinClientUrlUtil {

    private final static Logger logger = LoggerFactory.getLogger(VaadinClientUrlUtil.class);

    public static final String getProtocol() {
        return (((VaadinServletRequest) VaadinService.getCurrentRequest()).getProtocol().toLowerCase().contains("https") ? "https" : "http");
    }

    public static int getPort() {
        return ((VaadinServletRequest) VaadinService.getCurrentRequest()).getServerPort();
    }

    public static final String getDomainPath() {
        return getProtocol()+"//"+((VaadinServletRequest) VaadinService.getCurrentRequest()).getServerName() +
                (getPort() == 80 ? "" : ":" + ((VaadinServletRequest) VaadinService.getCurrentRequest()).getServerPort());
    }

    public static final String getRoutePath() {
        return getDomainPath() + "/" + getRouteLocation().getPathWithQueryParameters();
    }

    public static final String getRouteRelativePath() {
        return getRouteLocation().getPathWithQueryParameters();
    }

    public static final QueryParameters getQueryParameters() {
        return getRouteLocation().getQueryParameters();
    }

    public static final Location getRouteLocation() {
        return Optional.ofNullable(UI.getCurrent().getInternals().getLastHandledLocation()).isPresent() ?
            UI.getCurrent().getInternals().getLastHandledLocation() : UI.getCurrent().getInternals().getActiveViewLocation();
    }

    public static final void setLocation(String url) {
        logger.info("Application change location url:\t"+url);
        //TODO
        //fails
//        if(url.contains("?")) {
//            String urlNoParams = url.substring(0, url.indexOf("?"));
//            UI.getCurrent().navigate(urlNoParams, QueryParameterUtil.getQueryParameters(url));
//            return;
//        }
//        UI.getCurrent().navigate(url);
//
//        UI.getCurrent().getPage().setLocation(url);
        //fails after print to pdf add a /ps4
//

        //
        if(url.isEmpty()) {
            url += "/";
        }
        UI.getCurrent().getPage().executeJs("window.location=$0;", url);
    }
    public static final void setTopLocation(String url) {
        logger.info("Application change location url:\t"+url);
        //TODO
        //fails
//        if(url.contains("?")) {
//            String urlNoParams = url.substring(0, url.indexOf("?"));
//            UI.getCurrent().navigate(urlNoParams, QueryParameterUtil.getQueryParameters(url));
//            return;
//        }
//        UI.getCurrent().navigate(url);
//
//        UI.getCurrent().getPage().setLocation(url);
        //fails after print to pdf add a /ps4
//

        //
        if(url.isEmpty()) {
            url += "/";
        }
        UI.getCurrent().getPage().executeJs("window.top.location.href=$0;", url);
    }
    public static final void setLocationToMessages(String url){
        if(url.contains("?")) {
            String urlNoParams = url.substring(0, url.indexOf("?"));
            UI.getCurrent().navigate(urlNoParams, QueryParameterUtil.getQueryParameters(url));
            return;
        }
        UI.getCurrent().navigate(url);
        UI.getCurrent().getPage().setLocation(url);
    }
    public static final void setLocationNewTab(String url){

          UI.getCurrent().getPage().executeJs("window.open(\""+url+"\", \"_blank\", \"\");");
    }
}
