package ro.bithat.dms.microservices.portal.ecitizen.portalfile.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.bithat.dms.microservices.dmsws.file.PortalFile;
import ro.bithat.dms.microservices.dmsws.ps4.DmswsPS4Service;
import ro.bithat.dms.security.SecurityUtils;

import java.util.List;

/**
 * Created by Infrasoft34 on 4/21/2020.
 */
@Service
public class Ps4ECitizenPortalFileService {
    @Autowired
    private DmswsPS4Service service;
    public List<PortalFile> getPortalFileByIdDocType(Integer documentId) {
        return service.getPortalFileByIdDocType(SecurityUtils.getToken(),documentId).getPortalFileList();
    }
    public PortalFile getPortalFileByFileId(Integer fileId) {
        return service.getPortalFileByFileId(SecurityUtils.getToken(),fileId);
    }
}
