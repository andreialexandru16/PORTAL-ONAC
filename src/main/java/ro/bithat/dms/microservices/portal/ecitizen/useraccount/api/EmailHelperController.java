package ro.bithat.dms.microservices.portal.ecitizen.useraccount.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.bithat.dms.microservices.dmsws.file.BaseModel;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.EmailHelperService;
import ro.bithat.dms.service.URLUtil;

import javax.servlet.http.HttpServletRequest;

@RestController
public class EmailHelperController{

	@Autowired
	private EmailHelperService service;
	
	@Autowired
	private URLUtil urlUtil;
	
	@PostMapping(value = "/dmsws/email/send", produces = "application/json")
	public BaseModel sendEmailFromTemplate(@RequestParam String to, @RequestParam String title, @RequestParam String messageFilePath, @RequestParam String whatToPrefix, HttpServletRequest httpServletRequest) {
		return service.sendEmailFromTemplate(urlUtil.getPath(httpServletRequest), to, title, messageFilePath, whatToPrefix);
	}
}
