package ro.bithat.dms.microservices.portal.ecitizen.contact.backend;

import org.springframework.stereotype.Service;
import ro.bithat.dms.microservices.dmsws.DmswsRestService;
import ro.bithat.dms.microservices.dmsws.email.Email;
import ro.bithat.dms.microservices.dmsws.file.BaseModel;

@Service
public class ContactService extends DmswsRestService {

    public BaseModel sendEmail(String token, String to, String from, String title, String mesage) {
        Email email = new Email();
        email.setCatre(to);
        email.setTitlu(title);
        email.setMesaj(mesage);
        email.setCreatDe(from);
        return post(email, /* MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, */Email.class, checkBaseModel(),
                getDmswsUrl()+"/email/{token}/sendNewEmail", token);
    }

}
