package ro.bithat.dms.microservices.portal.ecitizen.documenttype.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import ro.bithat.dms.microservices.dmsws.DmswsRestService;
import ro.bithat.dms.microservices.dmsws.colaboration.ColaborareResponse;
import ro.bithat.dms.microservices.dmsws.colaboration.DetaliiDocument;
import ro.bithat.dms.microservices.dmsws.flow.StandardResponse;
import ro.bithat.dms.microservices.dmsws.ps4.DmswsPS4Service;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.AddFileToPetitieReq;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.CorespondentaControlList;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.CorespondentaControlReq;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.CorespondentaPetitii;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.CorespondentaPetitiiList;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.CorespondentaPetitiiReq;

@Service
public class DmswsPetitiiService extends DmswsRestService {

    @Autowired
    private DmswsPS4Service service;

    public CorespondentaPetitiiList getCorespondentaPetitii(String token, Integer idUtilizator) {
        String url = getDmswsUrl() + "/petitii/{token}/getCorespondentaPetitiiByUserId";

        CorespondentaPetitiiReq corespondentaPetitiiReq = new CorespondentaPetitiiReq();
        corespondentaPetitiiReq.setIdUtilizator(idUtilizator);

        return post(corespondentaPetitiiReq, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, CorespondentaPetitiiList.class, checkBaseModel(),
                url, token);
    }

    public CorespondentaPetitiiList getCorespondentaPetitiiTrimise(String token, Integer idUtilizator, Integer nrTrimise) {
        String url = getDmswsUrl() + "/petitii/{token}/getCorespondentaPetitiiTrimiseByUserId?nrTrimise="+nrTrimise;

        CorespondentaPetitiiReq corespondentaPetitiiReq = new CorespondentaPetitiiReq();
        corespondentaPetitiiReq.setIdUtilizator(idUtilizator);

        return post(corespondentaPetitiiReq, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, CorespondentaPetitiiList.class, checkBaseModel(),
                url, token);
    }

    public StandardResponse addNewFileToPetitie(String token, String base64File, String fileName, Integer idCorespondenta) {
        AddFileToPetitieReq req= new AddFileToPetitieReq();
        req.setIdCorespondenta(idCorespondenta);
        req.setBase64File(base64File);
        req.setFileName(fileName);

        return post(req, StandardResponse.class, checkStandardResponse(),
                getDmswsUrl()+"/petitii/{token}/addNewFileToPetitie",
                token);
    }
}
