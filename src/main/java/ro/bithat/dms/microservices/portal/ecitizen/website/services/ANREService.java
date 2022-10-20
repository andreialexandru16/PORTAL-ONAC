package ro.bithat.dms.microservices.portal.ecitizen.website.services;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ro.bithat.dms.microservices.dmsws.DmswsRestService;
import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import ro.bithat.dms.microservices.dmsws.flow.ListaPublica;
import ro.bithat.dms.microservices.dmsws.flow.StandardResponse;
import ro.bithat.dms.microservices.dmsws.metadata.LovList;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.Completari;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.DocumentList;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.InboxInfo;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.TipDocumentList;
import ro.bithat.dms.microservices.portal.ecitizen.project.backend.bithat.CreateTipDocFileResponse;
import ro.bithat.dms.microservices.portal.ecitizen.website.models.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import ro.bithat.dms.microservices.portal.ecitizen.website.models.CertificatConstatator;
import ro.bithat.dms.microservices.portal.ecitizen.raportare.models.Tert;
import ro.bithat.dms.microservices.portal.ecitizen.website.models.PersoaneContact;
import ro.bithat.dms.microservices.portal.ecitizen.website.models.PersoaneContactList;


@Service
public class ANREService extends DmswsRestService {

    public BaseModel trimiteCompletari(String token, Completari completari){
        return post(completari,MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, BaseModel.class, checkBaseModel(),getDmswsUrl()+"/anre/{token}/trimiteCompletari/",
                token);
    }
    public BaseModel exportXlsx(String token, ExportXlsxReq req) {
        String url =  getDmswsUrl()+"/anre/{token}/exportXlsx";

        return post(req,MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, BaseModel.class, checkBaseModel(),url,
                token);
    }
    public ConducereList getInregistrariConducere(String token, Integer idTert) {
        String url =  getDmswsUrl()+"/anre/{token}/getInregistrariConducere/{idTert}";

        return get(ConducereList.class, checkBaseModel(),url,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, idTert);
    }

    public SucursalaList getInregistrariSucursala(String token, Integer idTert) {
        String url =  getDmswsUrl()+"/anre/{token}/getInregistrariSucursala/{idTert}";

        return get(SucursalaList.class, checkBaseModel(),url,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, idTert);
    }

    public AutMediuList getInregistrariAutMediu(String token, Integer idTert) {
        String url =  getDmswsUrl()+"/anre/{token}/getInregistrariAutMediu/{idTert}";

        return get(AutMediuList.class, checkBaseModel(),url,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, idTert);
    }

    public LegitimatieList getExameneInstalatori(String token, Legitimatie legitimatie, Optional<Integer> indexStart, Optional<Integer> indexEnd) {
        String url =  getDmswsUrl()+"/anre/{token}/getExameneInstalatori";
        if(indexStart.isPresent()){
            if(!url.contains("?")){
                url+="?";
            }
            url+="&indexStart="+indexStart.get();
        }
        if(indexEnd.isPresent()){
            if(!url.contains("?")){
                url+="?";
            }
            url+="&indexEnd="+indexEnd.get();
        }
        return post(legitimatie,MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON,LegitimatieList.class, checkBaseModel(),url,
                token);
    }
    public LegitimatieList getExameneElectricieni(String token, Legitimatie legitimatie, Optional<Integer> indexStart, Optional<Integer> indexEnd) {
        String url =  getDmswsUrl()+"/anre/{token}/getExameneElectricieni";
        if(indexStart.isPresent()){
            if(!url.contains("?")){
                url+="?";
            }
            url+="&indexStart="+indexStart.get();
        }
        if(indexEnd.isPresent()){
            if(!url.contains("?")){
                url+="?";
            }
            url+="&indexEnd="+indexEnd.get();
        }
        return post(legitimatie,MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON,LegitimatieList.class, checkBaseModel(),url,
                token);
    }
    public LegitimatieList getAutorizatiiInstalatori(String token, Legitimatie legitimatie, Optional<Integer> indexStart, Optional<Integer> indexEnd) {
        String url =  getDmswsUrl()+"/anre/{token}/getAutorizatiiInstalatori";
        if(indexStart.isPresent()){
            if(!url.contains("?")){
                url+="?";
            }
            url+="&indexStart="+indexStart.get();
        }
        if(indexEnd.isPresent()){
            if(!url.contains("?")){
                url+="?";
            }
            url+="&indexEnd="+indexEnd.get();
        }
        return post(legitimatie,MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON,LegitimatieList.class, checkBaseModel(),url,
                 token);
    }

    public BaseModel getAutorizatiiInstalatoriCount(String token, Legitimatie legitimatie) {
        String url =  getDmswsUrl()+"/anre/{token}/getAutorizatiiInstalatoriCount/";

        return post(legitimatie,MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, BaseModel.class, checkBaseModel(),url,
                 token);
    }

    public LegitimatieList getAutorizatiiElectricieni(String token, Legitimatie legitimatie, Optional<Integer> indexStart, Optional<Integer> indexEnd) {
        String url =  getDmswsUrl()+"/anre/{token}/getAutorizatiiElectricieni";
        if(indexStart.isPresent()){
            if(!url.contains("?")){
                url+="?";
            }
            url+="&indexStart="+indexStart.get();
        }
        if(indexEnd.isPresent()){
            if(!url.contains("?")){
                url+="?";
            }
            url+="&indexEnd="+indexEnd.get();
        }
        return post(legitimatie,MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON,LegitimatieList.class, checkBaseModel(),url,
                token);
    }

    public BaseModel getAutorizatiiElectricieniCount(String token, Legitimatie legitimatie) {
        String url =  getDmswsUrl()+"/anre/{token}/getAutorizatiiElectricieniCount/";

        return post(legitimatie,MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, BaseModel.class, checkBaseModel(),url,
                token);
    }

    public BaseModel getExameneInstalatoriCount(String token, Legitimatie legitimatie) {
        String url =  getDmswsUrl()+"/anre/{token}/getExameneInstalatoriCount/";

        return post(legitimatie,MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, BaseModel.class, checkBaseModel(),url,
                token);
    }
    public BaseModel getExameneElectricieniCount(String token, Legitimatie legitimatie) {
        String url =  getDmswsUrl()+"/anre/{token}/getExameneElectricieniCount/";

        return post(legitimatie,MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, BaseModel.class, checkBaseModel(),url,
                token);
    }

    public LicenteFilteredResponse getAtestate(String token,String domeniu, LicenteFilteredRequest licenta, Optional<Integer> indexStart, Optional<Integer> indexEnd) {
        String url =  getDmswsUrl()+"/anre/{token}/getAtestate/{domeniu}";
        if(indexStart.isPresent()){
            if(!url.contains("?")){
                url+="?";
            }
            url+="&indexStart="+indexStart.get();
        }
        if(indexEnd.isPresent()){
            if(!url.contains("?")){
                url+="?";
            }
            url+="&indexEnd="+indexEnd.get();
        }
        return post(licenta,MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON,LicenteFilteredResponse.class, checkStandardResponse(),url,
                token,domeniu);
    }

    public BaseModel getAtestateCount(String token,String domeniu, LicenteFilteredRequest licenta) {
        String url =  getDmswsUrl()+"/anre/{token}/getAtestateCount/{domeniu}";

        return post(licenta,MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, BaseModel.class, checkBaseModel(),url,
                token,domeniu);
    }

    public AutGospApelorList getInregistrariAutGospApelor(String token, Integer idTert) {
        String url =  getDmswsUrl()+"/anre/{token}/getInregistrariAutGospApelor/{idTert}";

        return get(AutGospApelorList.class, checkBaseModel(),url,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, idTert);
    }

    public StudiiList getInregistrariStudii(String token, Integer idTert) {
        String url =  getDmswsUrl()+"/anre/{token}/getInregistrariStudii/{idTert}";

        return get(StudiiList.class, checkBaseModel(),url,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, idTert);
    }

    public AdeverinteList getInregistrariAdeverinte(String token, Integer idTert) {
        String url =  getDmswsUrl()+"/anre/{token}/getInregistrariAdeverinte/{idTert}";

        return get(AdeverinteList.class, checkBaseModel(),url,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, idTert);
    }

    public CursuriList getInregistrariCursuri(String token, Integer idTert) {
        String url =  getDmswsUrl()+"/anre/{token}/getInregistrariCursuri/{idTert}";

        return get(CursuriList.class, checkBaseModel(),url,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, idTert);
    }

    public DocumentList getInregistrariDocumenteCerere(String token, Integer idTert, Integer idFisierCerere) {
        String url =  getDmswsUrl()+"/anre/{token}/getInregistrariDocumenteCerere/{idTert}/{idFisierCerere}";

        return get(DocumentList.class, checkBaseModel(),url,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, idTert, idFisierCerere);
    }


    public ExperientaManagerialaList getExperientaManageriala(String token, String idPers) {
        String url =  getDmswsUrl()+"/anre/{token}/getExperientaManageriala/{idPers}";

        return get(ExperientaManagerialaList.class, checkBaseModel(),url,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, idPers);
    }


    public BaseModel adaugaConducere(String token, Conducere conducere, Integer idTert) {

        String url =  getDmswsUrl()+"/anre/{token}/adaugaConducere/{idTert}";


        return post(conducere,   MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON,
                BaseModel.class, checkBaseModel(),
                url, token,idTert);
    }

    public BaseModel adaugaSucursala(String token, Sucursala sucursala, Integer idTert) {

        String url =  getDmswsUrl()+"/anre/{token}/adaugaSucursala/{idTert}";


        return post(sucursala,   MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON,
                BaseModel.class, checkBaseModel(),
                url, token,idTert);
    }

    public BaseModel adaugaAutMediu(String token, AutMediu autMediu, Integer idTert) {

        String url =  getDmswsUrl()+"/anre/{token}/adaugaAutMediu/{idTert}";


        return post(autMediu,   MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON,
                BaseModel.class, checkBaseModel(),
                url, token,idTert);
    }

    public BaseModel adaugaAutGospApelor(String token, AutGospApelor autGospApelor, Integer idTert) {

        String url =  getDmswsUrl()+"/anre/{token}/adaugaAutGospApelor/{idTert}";


        return post(autGospApelor,   MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON,
                BaseModel.class, checkBaseModel(),
                url, token,idTert);
    }

    public BaseModel adaugaStudii(String token, Studii studii, Integer idTert) {

        String url =  getDmswsUrl()+"/anre/{token}/adaugaStudii/{idTert}";


        return post(studii,   MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON,
                BaseModel.class, checkBaseModel(),
                url, token,idTert);
    }

    public BaseModel updateConducere(String token, @RequestBody Conducere conducere, String idPersoana) {

        String url =  getDmswsUrl()+"/anre/{token}/editeazaConducere/{idPersoana}";

        return put(conducere, BaseModel.class, checkBaseModel(),  url, token, idPersoana);


    }

    public BaseModel editeazaSucursala(String token, @RequestBody Sucursala sucursala, String id) {

        String url =  getDmswsUrl()+"/anre/{token}/editeazaSucursala/{id}";

        return put(sucursala, BaseModel.class, checkBaseModel(),  url, token, id);


    }

    public BaseModel editeazaAutMediu(String token, @RequestBody AutMediu autMediu, String id) {

        String url =  getDmswsUrl()+"/anre/{token}/editeazaAutMediu/{id}";

        return put(autMediu, BaseModel.class, checkBaseModel(),  url, token, id);
    }

    public BaseModel editeazaAutGospApelor(String token, @RequestBody AutGospApelor autGospApelor, String id) {

        String url =  getDmswsUrl()+"/anre/{token}/editeazaAutGospApelor/{id}";

        return put(autGospApelor, BaseModel.class, checkBaseModel(),  url, token, id);
    }

    public BaseModel updateStudii(String token, @RequestBody Studii studii, String id) {

        String url =  getDmswsUrl()+"/anre/{token}/editeazaStudii/{id}";

        return put(studii, BaseModel.class, checkBaseModel(),  url, token, id);


    }

    public BaseModel updateExperienta(String token, @RequestBody ExperientaManageriala experientaManageriala, String id) {

        String url =  getDmswsUrl()+"/anre/{token}/editeazaExperienta/{id}";

        return put(experientaManageriala, BaseModel.class, checkBaseModel(),  url, token, id);


    }

    public BaseModel adaugaExperientaManageriala(String token, ExperientaManageriala experientaManageriala, String idPers) {

        String url =  getDmswsUrl()+"/anre/{token}/adaugaExperientaManageriala/{idPers}";


        return post(experientaManageriala,   MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON,
                BaseModel.class, checkBaseModel(),
                url, token,idPers);
    }

    public BaseModel stergeConducere(String token, String idPers) {

        String url =  getDmswsUrl()+"/anre/{token}/stergeConducere/{idPers}";


        return get(BaseModel.class, checkBaseModel(),url,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, idPers);
    }

    public BaseModel stergeSucursala(String token, String id) {

        String url =  getDmswsUrl()+"/anre/{token}/stergeSucursala/{id}";


        return get(BaseModel.class, checkBaseModel(),url,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, id);
    }

    public BaseModel stergeAutMediu(String token, String id) {
        String url =  getDmswsUrl()+"/anre/{token}/stergeAutMediu/{id}";

        return get(BaseModel.class, checkBaseModel(),url,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, id);
    }

    public BaseModel stergeAutGospApelor(String token, String id) {
        String url =  getDmswsUrl()+"/anre/{token}/stergeAutGospApelor/{id}";

        return get(BaseModel.class, checkBaseModel(),url,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, id);
    }

    public BaseModel stergeFisierAutMediu(String token, String id) {
        String url =  getDmswsUrl()+"/anre/{token}/stergeFisierAutMediu/{id}";

        return get(BaseModel.class, checkBaseModel(),url,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, id);
    }

    public BaseModel stergeFisierAutGospApelor(String token, String id) {
        String url =  getDmswsUrl()+"/anre/{token}/stergeFisierAutGospApelor/{id}";

        return get(BaseModel.class, checkBaseModel(),url,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, id);
    }

    public BaseModel updateFisierAutMediu(String token, String id, UpdateFisierName u) {
        String url =  getDmswsUrl()+"/anre/{token}/updateFisierAutMediu/{id}";

        return post(u, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON,
                BaseModel.class, checkBaseModel(),
                url, token, id);
    }

    public BaseModel updateFisierAutGospApelor(String token, String id, UpdateFisierName u) {
        String url =  getDmswsUrl()+"/anre/{token}/updateFisierAutGospApelor/{id}";

        return post(u, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON,
                BaseModel.class, checkBaseModel(),
                url, token, id);
    }

    public BaseModel stergeStudii(String token, String id) {

        String url =  getDmswsUrl()+"/anre/{token}/stergeStudii/{id}";


        return get(BaseModel.class, checkBaseModel(),url,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, id);
    }

    public BaseModel stergeCursuri(String token, String id) {

        String url =  getDmswsUrl()+"/anre/{token}/stergeCursuri/{id}";


        return get(BaseModel.class, checkBaseModel(),url,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, id);
    }
    public BaseModel stergeAdeverinte(String token, String id) {

        String url =  getDmswsUrl()+"/anre/{token}/stergeAdeverinte/{id}";


        return get(BaseModel.class, checkBaseModel(),url,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, id);
    }


    public LovList getTipAdeverintaByIdGrad (@PathVariable String token, Integer idGrad) {
        return get(LovList.class, checkBaseModel(), getDmswsUrl()+"/anre/{token}/getTipAdeverintaByIdGrad/{idGrad}",
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token,idGrad);
    }
    public BaseModel stergeExperienta(String token, String id) {

        String url =  getDmswsUrl()+"/anre/{token}/stergeExperienta/{id}";


        return get(BaseModel.class, checkBaseModel(),url,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, id);
    }


    public BaseModel stergeContact(String token, String id) {

        String url =  getDmswsUrl()+"/anre/{token}/stergeContact/{id}";


        return get(BaseModel.class, checkBaseModel(),url,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, id);
    }
    public BaseModel stergeSediu(String token, String id) {

        String url =  getDmswsUrl()+"/anre/{token}/stergeSediu/{id}";


        return get(BaseModel.class, checkBaseModel(),url,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, id);
    }

    public BaseModel stergeActionar(String token, String id) {

        String url =  getDmswsUrl()+"/anre/{token}/stergeActionar/{id}";


        return get(BaseModel.class, checkBaseModel(),url,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, id);
    }

    public ConducereList getConducere(String token, String idPers) {
        String url =  getDmswsUrl()+"/anre/{token}/getConducere/{idPers}";

        return get(ConducereList.class, checkBaseModel(),url,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, idPers);
    }

    public SucursalaList getSucursala(String token, String id) {
        String url =  getDmswsUrl()+"/anre/{token}/getSucursala/{id}";

        return get(SucursalaList.class, checkBaseModel(),url,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, id);
    }

    public AutMediuList getAutMediu(String token, String id) {
        String url =  getDmswsUrl()+"/anre/{token}/getAutMediu/{id}";

        return get(AutMediuList.class, checkBaseModel(),url,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, id);
    }

    public AutGospApelorList getAutGospApelor(String token, String id) {
        String url =  getDmswsUrl()+"/anre/{token}/getAutGospApelor/{id}";

        return get(AutGospApelorList.class, checkBaseModel(),url,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, id);
    }

    public ExperientaManagerialaList getExperienta(String token, String id) {
        String url =  getDmswsUrl()+"/anre/{token}/getExperienta/{id}";

        return get(ExperientaManagerialaList.class, checkBaseModel(),url,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, id);
    }

    public StudiiList getStudii(String token, String id) {
        String url =  getDmswsUrl()+"/anre/{token}/getStudii/{id}";

        return get(StudiiList.class, checkBaseModel(),url,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, id);
    }

    public PersoaneContactList getListaPersoaneContact(String token, Integer idTert) {
       String url =  getDmswsUrl()+"/anre/{token}/getListaPersoaneContact/{idTert}";

        return get(PersoaneContactList.class, checkBaseModel(),url,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, idTert);
    }


    public ActionariList getListaActionari(String token, Integer idTert) {
       String url =  getDmswsUrl()+"/anre/{token}/getListaActionari/{idTert}";

        return get(ActionariList.class, checkBaseModel(),url,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, idTert);
    }
    public SediuList getInregistrariSedii(String token, Integer idTert) {
       String url =  getDmswsUrl()+"/anre/{token}/getInregistrariSedii/{idTert}";

        return get(SediuList.class, checkBaseModel(),url,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, idTert);
    }


    public PersoaneContactList getPersoanaContactById(String token, String idTert) {
       String url =  getDmswsUrl()+"/anre/{token}/getPersoanaContactById/{idTert}";

        return get(PersoaneContactList.class, checkBaseModel(),url,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, idTert);
    }
    public ActionariList getActionarById(String token, String idTert) {
       String url =  getDmswsUrl()+"/anre/{token}/getActionarById/{idTert}";

        return get(ActionariList.class, checkBaseModel(),url,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, idTert);
    }

    public SediuList getSediuInfo(String token, String idTert) {
       String url =  getDmswsUrl()+"/anre/{token}/getSediuInfo/{idTert}";

        return get(SediuList.class, checkBaseModel(),url,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, idTert);
    }

    public BaseModel adaugaPersoanaContact(String token, PersoaneContact persoanaContact, Integer idTert) {

        String url =  getDmswsUrl()+"/anre/{token}/adaugaPersoanaContact/{idTert}";


        return post(persoanaContact,   MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON,
                BaseModel.class, checkBaseModel(),
                url, token,idTert);
    }
 public BaseModel adaugaActionar(String token, Actionari actionari, Integer idTert) {

        String url =  getDmswsUrl()+"/anre/{token}/adaugaActionar/{idTert}";


        return post(actionari,   MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON,
                BaseModel.class, checkBaseModel(),
                url, token,idTert);
    }


    public Tert getTertInfoById(String token, Integer idTert) {
        String url =  getDmswsUrl()+"/anre/{token}/getTertInfoById/{idTert}";

        return get(Tert.class, checkBaseModel(),url,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, idTert);
    }

    public BaseModel updateCertificatConstatator(String token, CertificatConstatator certificat, Integer idTert) {

        String url =  getDmswsUrl()+"/anre/{token}/updateCertificatConstatator/{idTert}";


        return post(certificat, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON,
                BaseModel.class, checkBaseModel(),
                url, token,idTert);
    }
    public BaseModel updateInfoContact(String token, PersoaneContact PersoaneContact) {

        String url =  getDmswsUrl()+"/anre/{token}/updateInfoContact";


        return post(PersoaneContact, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON,
                BaseModel.class, checkBaseModel(),
                url, token);
    }
    public BaseModel updateInfoActionar(String token, Actionari actionari) {

        String url =  getDmswsUrl()+"/anre/{token}/updateInfoActionar";


        return post(actionari, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON,
                BaseModel.class, checkBaseModel(),
                url, token);
    }

    public BaseModel editeazaSediu(String token, Sediu sediu) {
        String url =  getDmswsUrl()+"/anre/{token}/editeazaSediu";

        return post(sediu, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON,
                BaseModel.class, checkBaseModel(),
                url, token);
    }

    public BaseModel adaugaSediu(String token, Sediu sediu) {
        String url =  getDmswsUrl()+"/anre/{token}/adaugaSediu";
        return post(sediu, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON,
                BaseModel.class, checkBaseModel(),
                url, token);
    }
    public CreateTipDocFileResponse uploadFisierToStudii(String token, String filename, byte[] someByteArray, Map<String, String> paramMap) {
        String path = getDmswsUrl()+ "/anre/"+token+"/uploadFisierToStudii";

        for(String key: paramMap.keySet()){
            if(!path.contains("?")){
                path+="?";
            }
            path+="&"+key+"="+paramMap.get(key);
        }
        CreateTipDocFileResponse result = upload(CreateTipDocFileResponse.class, checkStandardResponse(), filename != null ? filename : "empty.txt", someByteArray, path);
        return result;
    }

    public CreateTipDocFileResponse uploadFisierToAdeverinta(String token, String filename, byte[] someByteArray, Map<String, String> paramMap) {
        String path = getDmswsUrl()+ "/anre/"+token+"/uploadFisierToAdeverinte";

        for(String key: paramMap.keySet()){
            if(!path.contains("?")){
                path+="?";
            }
            path+="&"+key+"="+paramMap.get(key);
        }
        CreateTipDocFileResponse result = upload(CreateTipDocFileResponse.class, checkStandardResponse(), filename, someByteArray, path);
        return result;
    }

    public CreateTipDocFileResponse uploadFileToDocAnexat(String token, String filename, byte[] someByteArray, Map<String, String> paramMap) {
        String path = getDmswsUrl()+ "/anre/"+token+"/uploadFileToDocAnexat";

        for(String key: paramMap.keySet()){
            if(!path.contains("?")){
                path+="?";
            }
            path+="&"+key+"="+paramMap.get(key);
        }
        CreateTipDocFileResponse result = upload(CreateTipDocFileResponse.class, checkStandardResponse(), filename, someByteArray, path);
        return result;
    }

    public CreateTipDocFileResponse uploadFisierToCursuri(String token, String filename, byte[] someByteArray, Map<String, String> paramMap) {
        String path = getDmswsUrl()+ "/anre/"+token+"/uploadFisierToCursuri";

        for(String key: paramMap.keySet()){
            if(!path.contains("?")){
                path+="?";
            }
            path+="&"+key+"="+paramMap.get(key);
        }
        CreateTipDocFileResponse result = upload(CreateTipDocFileResponse.class, checkStandardResponse(), filename, someByteArray, path);
        return result;
    }

    public TipDocumentList getTipDocumentList(String token) {
        return get(TipDocumentList.class, checkBaseModel(), getDmswsUrl()+"/registratura_portal/{token}/getListaTipDoc",
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token);
    }

    public InboxInfo getInboxInfo(String token, String username) {
        return get(InboxInfo.class, checkBaseModel(), getDmswsUrl()+"/anre/{token}/getInboxInfo/"+username+"/",
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token);
    }

    public BaseModel getLicenteFilteredCount(String token, Integer idDomeniu, LicenteFilteredRequest licenteFilteredRequest) {
        String url =  getDmswsUrl()+"/anre_licente/{token}/getLicenteFilteredCount/{idDomeniu}";

        return post(licenteFilteredRequest,   MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON,
                BaseModel.class, checkBaseModel(),
                url, token,idDomeniu);
    }

    public LicenteFilteredResponse getLicenteFiltered(String token, Integer idDomeniu, LicenteFilteredRequest licenteFilteredRequest ,Optional<Integer> indexStart, Optional<Integer> indexEnd) {
        String url =  getDmswsUrl()+"/anre_licente/{token}/getLicenteFiltered/{idDomeniu}";
        if(indexStart.isPresent()){
            if(!url.contains("?")){
                url+="?";
            }
            url+="&indexStart="+indexStart.get();
        }
        if(indexEnd.isPresent()){
            if(!url.contains("?")){
                url+="?";
            }
            url+="&indexEnd="+indexEnd.get();
        }
        return post(licenteFilteredRequest,   MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON,
                LicenteFilteredResponse.class, checkStandardResponse(),
                url, token,idDomeniu);

    }


}