package ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.bithat.dms.microservices.dmsws.file.PortalFile;
import ro.bithat.dms.microservices.dmsws.ps4.DmswsPS4Service;
import ro.bithat.dms.security.SecurityUtils;

import java.util.List;

/**
 * Created by Infrasoft34 on 4/13/2020.
 */
@Service
public class DmswsMyRequestsService {

    @Autowired
    private DmswsPS4Service service;

    public List<PortalFile> getFilesOnWorkflowByUser(){
        return service.getFilesOnWorkflowByUser(SecurityUtils.getToken()).getPortalFileList();
    }
    public List<PortalFile> getLimitedFilesOnWorkflowByUser(String nrRows){
        return service.getLimitedFilesOnWorkflowByUser(SecurityUtils.getToken(),nrRows).getPortalFileList();
    }

}
