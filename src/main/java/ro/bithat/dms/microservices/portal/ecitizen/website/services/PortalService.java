package ro.bithat.dms.microservices.portal.ecitizen.website.services;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import ro.bithat.dms.microservices.dmsws.DmswsRestService;
import ro.bithat.dms.microservices.dmsws.file.BaseModel;
import ro.bithat.dms.microservices.dmsws.file.RaportSocietati;
import ro.bithat.dms.microservices.dmsws.file.RaportSocietatiList;
import ro.bithat.dms.microservices.dmsws.metadata.LovList;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.CorespondentaControlList;
import ro.bithat.dms.microservices.portal.ecitizen.website.models.MenuRights;

import java.util.Map;
import java.util.Optional;

@Service
public class PortalService extends DmswsRestService {

    public MenuRights getAllMeniuWebElectronic(@PathVariable String token) {
        return get(MenuRights.class, checkBaseModel(), getDmswsUrl()+"/meniu_web/{token}/getMeniuWebElectronic",
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token);
    }

    public LovList getValuesLovByCode (@PathVariable String token, String lovCode) {
        return get(LovList.class, checkBaseModel(), getDmswsUrl()+"/lov/{token}/values_by_code/{lovCode}",
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token,lovCode);
    }

    public RaportSocietati getInfoTabelRaport(@PathVariable String token, Optional<Integer> idPerioada, Optional<Integer> idFisier, String codTipDoc) {
        String url= getDmswsUrl()+"/formular_custom/{token}/getInfoTabelRaport";
        url+="?&codTipDoc="+codTipDoc;
        if(idPerioada.isPresent()){
            url+="&idPerioada="+idPerioada.get();
        }
        if(idFisier.isPresent()){

            url+="&idFisier="+idFisier.get();

        }
        return get(RaportSocietati.class, checkBaseModel(), url,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token);
    }

    public RaportSocietati getInfoUtilizator (@PathVariable String token) {
        return get(RaportSocietati.class, checkBaseModel(), getDmswsUrl()+"/formular_custom/{token}/getInfoUtilizator",
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token);
    }

    public BaseModel sendRaportare(String token, String id){
        //  return put(persoanaFizicaJuridica, PersoanaFizicaJuridica.class, checkBaseModel(), getDmswsUrl()+"/utilizator/{token}/updatePersoanaFizicaJuridica/{idUser}", token, idUser);

        return post(null,   MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON,
                BaseModel.class, checkBaseModel(),
                getDmswsUrl()+"/formular_custom/{token}/sendRaportare/{id}", token,id);

    }

    public RaportSocietati checkPerioadaRaportare (@PathVariable String token,String idPerioada) {
        return get(RaportSocietati.class, checkBaseModel(), getDmswsUrl()+"/formular_custom/{token}/checkPerioadaRaportare/{idPerioada}",
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token,idPerioada);
    }

    public RaportSocietati checkPerioadaRaportareRez (@PathVariable String token,String idPerioada) {
        return get(RaportSocietati.class, checkBaseModel(), getDmswsUrl()+"/formular_custom/{token}/checkPerioadaRaportareRez/{idPerioada}",
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token,idPerioada);
    }

    public RaportSocietati getInfoRaportRectificare (@PathVariable String token,String id) {
        return get(RaportSocietati.class, checkBaseModel(), getDmswsUrl()+"/formular_custom/{token}/getInfoRaportRectificare/{id}",
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token,id);
    }

    public RaportSocietati getInfoRaportRectificareRez (@PathVariable String token,String id) {
        return get(RaportSocietati.class, checkBaseModel(), getDmswsUrl()+"/formular_custom/{token}/getInfoRaportRectificareRez/{id}",
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token,id);
    }

    public LovList getValuesLovByCodeWithDep (@PathVariable String token, String lovCode, Map<String,String> dep) {
        return post(dep, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, LovList.class, checkBaseModel(),
                getDmswsUrl()+"/lov/{token}/values_by_code_with_dep/{lovCode}", token, lovCode);
    }

    public LovList getValuesLovByCodeSearch (@PathVariable String token, String lovCode, String searchStr) {
        return get(LovList.class, checkBaseModel(), getDmswsUrl()+"/lov/{token}/values_by_code_search/{lovCode}?&searchStr={searchStr}",
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token,lovCode,searchStr);
    }
}