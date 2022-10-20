package ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import ro.bithat.dms.microservices.dmsws.DmswsRestService;
import ro.bithat.dms.microservices.dmsws.email.Email;
import ro.bithat.dms.microservices.dmsws.file.BaseModel;

@Service
public class DmswsEmailService extends DmswsRestService{

	public BaseModel sendEmail(String token, String to, String title, String mesage) {
		Email email = new Email();
		email.setCatre(to);
		email.setTitlu(title);
		email.setMesaj(mesage);
		return post(email,  MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, Email.class, checkBaseModel(),
				getDmswsUrl()+"/email/{token}/sendNewEmail", token);
	}
	public BaseModel sendEmailBySystem(String token, String to, String title, String mesage) {
		Email email = new Email();
		email.setCatre(to);
		email.setTitlu(title);
		email.setMesaj(mesage);
		email.setCreatDe("SYSTEM");
		return post(email,  MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, Email.class, checkBaseModel(),
				getDmswsUrl()+"/email/{token}/sendNewEmail", token);
	}
	public BaseModel getNotificationsNumber(String token) {
		String url = getDmswsUrl()+"/email/{token}/getNotificationsNumber";

		return get(BaseModel.class, checkBaseModel(), url, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token);

	}

	public BaseModel getHtmlTemplate(String token,String code) {
		String url = getDmswsUrl()+"/portal_e/{token}/getHtmlTemplate/{code}";

		return get(BaseModel.class, checkBaseModel(), url, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token,code);

	}
}

