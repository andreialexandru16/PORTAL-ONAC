package ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.bithat.dms.microservices.dmsws.DmswsRestService;
import ro.bithat.dms.microservices.dmsws.colaboration.InfoMesaje;
import ro.bithat.dms.security.SecurityUtils;

import java.util.List;

/**
 * Created by Infrasoft34 on 4/13/2020.
 */
@Service
public class ColaborationService extends DmswsRestService {

    @Autowired
    private DmswsColaborationService dmswsColaborationService;


    public List<InfoMesaje> getLastColaborationMessagesByUser() {
        return dmswsColaborationService.getLastColaborationMessagesByUser(SecurityUtils.getToken()).getInfoMesajeList();
    }
    public List<InfoMesaje> getLastColaborationMessagesByFile(Integer fileId) {
        return dmswsColaborationService.getLastColaborationMessagesByFile(SecurityUtils.getToken(),fileId).getInfoMesajeList();
    }


}
