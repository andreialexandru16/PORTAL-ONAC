package ro.bithat.dms.microservices.portal.ecitizen.anre.backend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ro.bithat.dms.microservices.dmsws.DmswsRestService;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.CreateTipDocFileResponse;

import java.net.URLEncoder;

@Service
public class AnreService extends DmswsRestService {

    @Value("${dmsws.url}")
    private String url;

    public CreateTipDocFileResponse processExcel(String token, String filename, byte[] someByteArray, String docTypeId, String perioadaId) {
        String path = url + "/file/"+token+"/processExcel/" + docTypeId + "/perioada/" + perioadaId;
        return upload(CreateTipDocFileResponse.class, checkStandardResponse(), filename, someByteArray, path);
    }
}
