package ro.bithat.dms.microservices.portal.ecitizen.documenttype.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import ro.bithat.dms.microservices.dmsws.DmswsRestService;
import ro.bithat.dms.microservices.dmsws.flow.StandardResponse;
import ro.bithat.dms.microservices.dmsws.ps4.DmswsPS4Service;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.AddFileToPetitieReq;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.CorespondentaControlList;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.CorespondentaControlReq;

@Service
public class DmswsControlService extends DmswsRestService {

    @Autowired
    private DmswsPS4Service service;

    public CorespondentaControlList getCorespondentaControl(String token, Integer idUtilizator) {
        String url = getDmswsUrl() + "/control/{token}/getCorespondentaControlByUserId";

        CorespondentaControlReq corespondentaControlReq = new CorespondentaControlReq();
        corespondentaControlReq.setIdUtilizator(idUtilizator);

        return post(corespondentaControlReq, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, CorespondentaControlList.class, checkBaseModel(),
                url, token);
    }

    public CorespondentaControlList getCorespondentaControlTrimise(String token, Integer idUtilizator, Integer nrTrimise) {
        String url = getDmswsUrl() + "/control/{token}/getCorespondentaControlTrimiseByUserId?nrTrimise="+nrTrimise;

        CorespondentaControlReq corespondentaControlReq = new CorespondentaControlReq();
        corespondentaControlReq.setIdUtilizator(idUtilizator);

        return post(corespondentaControlReq, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, CorespondentaControlList.class, checkBaseModel(),
                url, token);
    }

    public StandardResponse addNewFileToControl(String token, String base64File, String fileName, Integer idCorespondenta) {
        AddFileToPetitieReq req= new AddFileToPetitieReq();
        req.setIdCorespondenta(idCorespondenta);
        req.setBase64File(base64File);
        req.setFileName(fileName);

        return post(req, StandardResponse.class, checkStandardResponse(),
                getDmswsUrl()+"/control/{token}/addNewFileToControl",
                token);
    }
}
