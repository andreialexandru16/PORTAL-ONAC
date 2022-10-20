package ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import ro.bithat.dms.microservices.dmsws.DmswsRestService;
import ro.bithat.dms.microservices.dmsws.email.Email;
import ro.bithat.dms.microservices.dmsws.email.EmailList;
import ro.bithat.dms.microservices.dmsws.file.BaseModel;
import ro.bithat.dms.security.SecurityUtils;

/**
 * Created by Infrasoft34 on 4/13/2020.
 */
@Service
public class DmswsInboxService extends DmswsRestService {


    public EmailList getSysEmailsByUser(String token, String nrRows, String searchStr) {
        String url = getDmswsUrl()+"/email/{token}/getSysEmailsByUser?nrRows={nrRows}&searchStr={searchStr}";

        return get(EmailList.class, checkBaseModel(), url, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token,nrRows,searchStr);
    }
    
    public Email getSysEmailById(String token,Integer id){
        String url = getDmswsUrl()+"/email/{token}/getSysEmailById/{id}";

        return get(Email.class, checkBaseModel(), url, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token,id);
    }


    public EmailList getSysEmailsByFisier(String token,Integer fileId){
        String url = getDmswsUrl()+"/email/{token}/getSysEmailsByFisier/{fileId}";

        return get(EmailList.class, checkBaseModel(), url, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, fileId);
    }

    public BaseModel setEmailReaded(String token,Integer emailId){

        return post(null,  MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, BaseModel.class, checkBaseModel(),
                getDmswsUrl()+"/email/{token}/setEmailCitit/{idEmail}", token,emailId);    }

    public BaseModel sendEmail(String token, String to, String from, String title, String message) {
        Email email = new Email();
        email.setCatre(to);
        email.setTitlu(title);
        email.setMesaj(message);
        email.setCreatDe(from);
        email.setCreatDeEmail(SecurityUtils.getEmail());
        return post(email, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, Email.class, checkBaseModel(),
                getDmswsUrl()+"/email/{token}/sendNewEmail", token);
    }
}
