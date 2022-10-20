package ro.bithat.dms.microservices.portal.ecitizen.portalfile.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import ro.bithat.dms.microservices.dmsws.DmswsRestService;
import ro.bithat.dms.microservices.dmsws.colaboration.Utilizator;
import ro.bithat.dms.microservices.dmsws.file.BaseModel;
import ro.bithat.dms.microservices.dmsws.flow.StandardResponse;
import ro.bithat.dms.microservices.dmsws.ps4.DmswsPS4Service;
import ro.bithat.dms.microservices.portal.ecitizen.raportare.models.FormularRaportareList;
import ro.bithat.dms.microservices.portal.ecitizen.raportare.models.PerioadaInfoResponse;
import ro.bithat.dms.microservices.portal.ecitizen.raportare.models.TertList;
import ro.bithat.dms.microservices.portal.ecitizen.raportare.models.ValidareFisiereList;
import ro.bithat.dms.security.SecurityUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Tip Document is used for clasa document!
 * Document is used for tip document!
 *
 * @author george
 */
@Service
public class FormularCustomService extends DmswsRestService {


    @Autowired
    private DmswsPS4Service service;

    private String getToken() {
        return SecurityUtils.getToken();
    }

    public BaseModel adaugaFormular(String token, String tableName,Integer idFisier, Map<String, String> campuriCerere) {
        return post(campuriCerere, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON,BaseModel.class, checkBaseModel(),
                getDmswsUrl()+"/formular_custom/{token}/adaugaFormular/{tableName}/{idFisier}",token, tableName,idFisier);
    }


    public BaseModel adaugaTabel(String token, String tableName, Integer idFisier, List<Map<String, String>> randuriTabel) {
        return post(randuriTabel, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON,BaseModel.class, checkBaseModel(),
                getDmswsUrl()+"/formular_custom/{token}/adaugaTabel/{tableName}/{idFisier}",token, tableName,idFisier);
    }

    public BaseModel adaugaTabelRectificare(String token, String tableName, Integer idFisier,Integer idPerioada, List<Map<String, String>> randuriTabel,
                                            String codTipDocInitial) {
        return post(randuriTabel, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON,BaseModel.class, checkBaseModel(),
                getDmswsUrl()+"/formular_custom/{token}/adaugaTabelRectificare/{tableName}/{idFisier}/{idPerioada}?&codTipDocInitial={codTipDocInitial}",token, tableName,idFisier,idPerioada,codTipDocInitial);
    }
}
