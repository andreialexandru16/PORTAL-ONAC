package ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.bithat.dms.microservices.dmsws.DmswsRestService;
import ro.bithat.dms.microservices.dmsws.email.Email;

import java.util.List;

/**
 * Created by Infrasoft34 on 4/13/2020.
 */
@Service
public class InboxService extends DmswsRestService {

    @Autowired
    private DmswsInboxService dmswsInboxService;


    public List<Email> getSysEmailsByUser(String token, String nrRows, String searchStr) {
        return dmswsInboxService.getSysEmailsByUser(token,nrRows,searchStr).getEmailList();
    }



}
