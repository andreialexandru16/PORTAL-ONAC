package ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebInputException;
import ro.bithat.dms.microservices.dmsws.DmswsRestService;
import ro.bithat.dms.microservices.dmsws.file.BaseModel;
import ro.bithat.dms.security.SecurityUtils;
import ro.bithat.dms.service.StreamToStringUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class EmailHelperService extends DmswsRestService{

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private DmswsEmailService emailService;

	@Value("${dmsws.anonymous.token}")
	private String anonymousToken;
	
	public BaseModel sendEmailFromTemplate(String replaceWith, String to, String title, String messageFilePath, String whatToReplace) throws ServerWebInputException {
		return emailService.sendEmail(SecurityUtils.getToken(), 
				to, title, getEmailMsg(replaceWith, messageFilePath, whatToReplace));
    }
	public BaseModel sendEmailFromTemplateBySystem(String replaceWith, String to, String title, String messageFilePath, String whatToReplace) throws ServerWebInputException {
		return emailService.sendEmailBySystem(SecurityUtils.getToken(),
				to, title, getEmailMsg(replaceWith, messageFilePath, whatToReplace));
	}
	public BaseModel sendEmailFromTemplateReplaceAll(HashMap<String,String> replaceMap, String to, String title, String messageFilePath) throws ServerWebInputException {
		return emailService.sendEmail(SecurityUtils.getToken(),
				to, title, getEmailMsgReplaceAll(replaceMap, messageFilePath));
	}

	public BaseModel sendEmailFromTemplateReplaceAll2(HashMap<String,String> replaceMap, String to, String title, String messageFilePath) throws ServerWebInputException {
		return emailService.sendEmail(anonymousToken,
				to, title, getEmailMsgReplaceAll(replaceMap, messageFilePath));
	}
	private String getEmailMsg(String replaceWith, String messageFilePath, String whatToReplace) {
		AtomicReference<String> message = new AtomicReference<>("");
			
			message.set(StreamToStringUtil.fileToString(messageFilePath));
			Arrays.asList(whatToReplace).stream().forEach(s->message.set(message.get().replace(s, replaceWith)));
		return message.get();
	}
	private String getEmailMsgReplaceAll(HashMap<String,String> replaceMap, String messageFilePath) {
		AtomicReference<String> message = new AtomicReference<>("");

		message.set(StreamToStringUtil.fileToString(messageFilePath));
		String messageStr= message.get();
		for (String whatToReplace: replaceMap.keySet()
			 ) {
			messageStr =messageStr.replace(whatToReplace,replaceMap.get(whatToReplace));
		}

		//Arrays.asList(whatToReplace).stream().forEach(s->message.set(message.get().replace(s, replaceWith)));
		message.set(messageStr);
		return message.get();
	}

	public BaseModel getHtmlTemplate(String code) throws ServerWebInputException {
		return emailService.getHtmlTemplate(SecurityUtils.getToken(),code);
	}

	public BaseModel sendEmailTemplate(String to, String title, String message) throws ServerWebInputException {
		return emailService.sendEmail(SecurityUtils.getToken(),
				to, title, message);
	}
	public BaseModel sendEmailTemplateBySystem(String to, String title, String message) throws ServerWebInputException {
		return emailService.sendEmailBySystem(SecurityUtils.getToken(),
				to, title, message);
	}
}
