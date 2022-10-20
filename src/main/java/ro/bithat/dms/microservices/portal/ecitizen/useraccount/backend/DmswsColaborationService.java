package ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import ro.bithat.dms.microservices.dmsws.DmswsRestService;
import ro.bithat.dms.microservices.dmsws.colaboration.ColaborareResponse;
import ro.bithat.dms.microservices.dmsws.colaboration.DetaliiDocument;
import ro.bithat.dms.microservices.dmsws.colaboration.InfoMesajeList;
import ro.bithat.dms.microservices.dmsws.colaboration.Mesaj;

/**
 * Created by Infrasoft34 on 4/13/2020.
 */
@Service
public class DmswsColaborationService extends DmswsRestService {

    public InfoMesajeList getLastColaborationMessagesByUser(String token) {

        return get(InfoMesajeList.class, checkBaseModel(),  getDmswsUrl()+"/sidepanel/{token}/getNrLastMesaje",
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token);
    }
    public InfoMesajeList getLastColaborationMessagesByFile(String token, Integer fileId) {

        return get(InfoMesajeList.class, checkBaseModel(),  getDmswsUrl()+"/sidepanel/{token}/getNrLastMesaje?fileId={fileId}",
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token,fileId);
    }
    public ColaborareResponse getColaborationByFileId(String token,Integer fileId) {

        return get(ColaborareResponse.class, checkStandardResponse(),  getDmswsUrl()+"/sidepanel/{token}/getColaborare/{fileId}?getJustContribuitorsList={getJustContribuitorsList}",
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token,fileId,"true");
    }

    public ColaborareResponse addNewMessageToColaborare(String token, String messageToSend, Integer fileId) {
        Mesaj message= new Mesaj();
        message.setMesaj(messageToSend);

        return put(message, ColaborareResponse.class, checkStandardResponse(),
                getDmswsUrl()+"/sidepanel/{token}/addNewMessageToColaborare/{fileId}",
                token,fileId);
    }
    public ColaborareResponse addNewFileToColaborare(String token, String base64File,String fileName, Integer fileId) {
        DetaliiDocument documentDetails= new DetaliiDocument();
        documentDetails.setDocument(base64File);
        documentDetails.setDocumentName(fileName);

        return put(documentDetails, ColaborareResponse.class, checkStandardResponse(),
                getDmswsUrl()+"/sidepanel/{token}/addFileToColaborare/{fileId}",
                token,fileId);
    }
}
