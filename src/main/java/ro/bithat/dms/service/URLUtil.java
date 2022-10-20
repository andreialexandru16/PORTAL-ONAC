package ro.bithat.dms.service;

import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class URLUtil {

	@Value("${ps4.ecitizen.force.https}")
    private boolean forceHttps;

	@Value("${dms.url}")
	private String dmsUrl;

	public String getDmsUrl() {
		return dmsUrl;
	}

	public String getPath(HttpServletRequest  request) {
		String scheme = request.getScheme();
        String serverName = request.getServerName();
        int portNumber = request.getServerPort();
        if (forceHttps) {
        	scheme = "https";
        }
        
        if (portNumber == 80) {
        	return scheme+"://"+serverName+"/";
        }
        return scheme+"://"+serverName+":"+portNumber+"/";
	}
	
	public String getPathIfVaadin() {
	    VaadinServletRequest request = (VaadinServletRequest) VaadinService.getCurrentRequest();	    
	    return getPath(request);
	}
}
