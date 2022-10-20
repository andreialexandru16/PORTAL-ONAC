package ro.bithat.dms.microservices.portal.ecitizen.raportare.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerErrorException;
import org.springframework.web.server.ServerWebInputException;
import ro.bithat.dms.microservices.dmsws.DmswsRestService;
import ro.bithat.dms.microservices.dmsws.colaboration.Utilizator;
import ro.bithat.dms.microservices.dmsws.file.*;
import ro.bithat.dms.microservices.dmsws.metadata.*;
import ro.bithat.dms.microservices.dmsws.ps4.DmswsPS4Service;
import ro.bithat.dms.microservices.dmsws.ps4.detaliiserviciu.ElectronicService;
import ro.bithat.dms.microservices.dmsws.ps4.documents.DocObligatoriuExtra;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.*;
import ro.bithat.dms.microservices.portal.ecitizen.raportare.models.*;
import ro.bithat.dms.microservices.portal.ecitizen.rehabilitationreq.backend.DmswsRehabilitationService;
import ro.bithat.dms.security.SecurityUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Tip Document is used for clasa document!
 * Document is used for tip document!
 *
 * @author george
 */
@Service
public class RaportareService extends DmswsRestService {


    @Autowired
    private DmswsPS4Service service;

    private String getToken() {
        return SecurityUtils.getToken();
    }


    public FormularRaportareList getDocumentListByTertPerioada(Integer idClasaDoc, Integer idPerioada, Optional<Integer> idTert) {
        String url=getDmswsUrl()+"/raportare/{token}/getDocumentListByTertPerioada?serviciuElectronic=1&idClasaDoc={idClasaDoc}&idPerioada={idPerioada}";

        if(idTert.isPresent()){
            url+="&idTert="+idTert.get();
        }
        FormularRaportareList tipDocList =   get(FormularRaportareList.class, checkBaseModel(),url ,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, getToken(),idClasaDoc,idPerioada);
        return  tipDocList;
    }
    public PerioadaInfoResponse getPerioadaPrecedentaList() {
        return   get(PerioadaInfoResponse.class, checkStandardResponse(), getDmswsUrl()+"/raportare/{token}/getPerioadaPrecedentaList",
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, getToken());

    }

    public TertList getTertRaportareList(Optional<Integer> idClasaDoc, Optional<String> searchStr, Optional<String> saved_last_tert_id,Optional<String> putere_min_10) {
        String url= getDmswsUrl()+"/raportare/{token}/getTertRaportareList";
        if(idClasaDoc.isPresent() ){
            if(!url.contains("?")){
                url+="?";
            }
            url+="&idClasaDoc="+idClasaDoc.get();
        }
        if(searchStr.isPresent() ){
            if(!url.contains("?")){
                url+="?";
            }
            url+="&searchStr="+searchStr.get();
        }
        if(saved_last_tert_id.isPresent() ){
            if(!url.contains("?")){
                url+="?";
            }
            url+="&saved_last_tert_id="+saved_last_tert_id.get();
        }
        if(putere_min_10.isPresent() ){
            if(!url.contains("?")){
                url+="?";
            }
            url+="&putere_min_10="+putere_min_10.get();
        }
        return   get(TertList.class, checkBaseModel(), url,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, getToken());

    }


    public Utilizator checkUserGroupMemberByCode(String codGrup) {
        return   get(Utilizator.class, checkBaseModel(), getDmswsUrl()+"/raportare/{token}/checkUserGroupMemberByCode/{codGrup}",
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, getToken(),codGrup);

    }

    public ValidareFisiereList validareInterFisiere( String idsFiles,String transmitereDate) {
        String url=getDmswsUrl()+"/raportare/{token}/validareInterFisiere?idsFiles={idsFiles}&transmitereDate={transmitereDate}";

        ValidareFisiereList responseList =   get(ValidareFisiereList.class, checkBaseModel(),url ,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, getToken(),idsFiles,transmitereDate);
        return  responseList;
    }
}
