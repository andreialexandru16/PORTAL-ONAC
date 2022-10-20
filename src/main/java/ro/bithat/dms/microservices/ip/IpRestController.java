package ro.bithat.dms.microservices.ip;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.bithat.dms.security.SecurityUtils;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/ip")
public class IpRestController {
	
	@RequestMapping(value = "", method = RequestMethod.GET)
    public String getHexavalentNetwork(HttpServletRequest request) {
		
		String r = request.getRemoteAddr()+"||||||"+request.getRemoteHost()+"||||||"+request.getRequestURI()+"\n";;
		return "<html><body>Username="+SecurityUtils.getUsername()+"<br>"+r+new NetIdentity().getDescription().replace("\n", "<br>")+"</body></html>";
	}
}
