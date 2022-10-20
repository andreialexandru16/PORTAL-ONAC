package ro.bithat.dms.microservices.portal.ecitizen.project.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ro.bithat.dms.microservices.portal.ecitizen.project.backend.ProjectService;
import ro.bithat.dms.security.SecurityUtils;
import ro.bithat.dms.service.URLUtil;


@RestController
public class UtilController {

    @Value("${app.version:PORTAL_EXT.22.1}")
    private String appVersion;
    @Autowired
    private URLUtil urlUtil;

    public static final String ERROR_TEXT = "Error Status Code ";

    @Autowired
    private ProjectService service;

    @GetMapping("/util/open/dms/{targetPage}")
    public String getProiecte(@PathVariable String targetPage) {
        String link = urlUtil.getDmsUrl() + "/go_get.jsp?ws_token=" + SecurityUtils.getToken()  + "&targetPage=" + targetPage;
        return link;
    }
    @GetMapping("/version")
    public String version() {
        return appVersion;
    }

}
