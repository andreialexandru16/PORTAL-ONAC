package ro.bithat.dms.microservices.portal.ecitizen.project.backend;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import ro.bithat.dms.microservices.dmsws.DmswsRestService;
import ro.bithat.dms.microservices.dmsws.file.BaseModel;
import ro.bithat.dms.microservices.dmsws.poi.ProjectInfo;
import ro.bithat.dms.microservices.portal.ecitizen.project.backend.bithat.*;

import java.util.Optional;

@Service
public class ProjectService extends DmswsRestService {
    //    public LocalitateList getInfoLocalitate(@PathVariable String token, @PathVariable String idTara) {
//        return get(LocalitateList.class, checkBaseModel(), getDmswsUrl()+"/nomenc/{token}/getInfoLocalitate/{idTara}",
//                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, idTara);
//    }
    public TaskTicketList getTaskuriTicket(@PathVariable String token, @PathVariable String idTicket) {
        return get(TaskTicketList.class, checkBaseModel(), getDmswsUrl()+"/wbs_uat/{token}/getTaskuriTicket/{idTicket}",
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, idTicket);
    }

    public ProjectsList getProiecte(@PathVariable String token) {
        return get(ProjectsList.class, checkBaseModel(), getDmswsUrl()+"/project/{token}/getProiecte",
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token);
    }
    public ProjectsList getProiecteExterne(@PathVariable String token, Optional<String> idtipStr) {
        String url="/project/{token}/getProiecteExterne";
        if(idtipStr.isPresent()){
            url+="?idTpStr="+idtipStr.get();
        }
        return get(ProjectsList.class, checkBaseModel(), getDmswsUrl()+url,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token);
    }
    public ProjectsList getProiecteExterneShort(@PathVariable String token, Optional<String> idtipStr) {
        String url="/project/{token}/getProiecteExterneShort";
        if(idtipStr.isPresent()){
            url+="?idTpStr="+idtipStr.get();
        }
        return get(ProjectsList.class, checkBaseModel(), getDmswsUrl()+url,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token);
    }
    public ProjectsList getAllProiecteExterne(@PathVariable String token, Optional<String> idtipStr) {
        String url="/project/{token}/getProiecteExterne?publicat=all";
        if(idtipStr.isPresent()){
            url+="?idTpStr="+idtipStr.get();
        }
        return get(ProjectsList.class, checkBaseModel(), getDmswsUrl()+url,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token);
    }

    public ProjectsList getProiecteLimited(@PathVariable String token, Integer nrRows) {
        return get(ProjectsList.class, checkBaseModel(), getDmswsUrl()+"/project/{token}/getProiecte?nrRows={nrRows}",
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, nrRows);
    }

    public MembriiEchipaList getProjectTeam(String token, String projectId){
        return get(MembriiEchipaList.class, checkBaseModel(), getDmswsUrl()+"/project/{token}/getEchipaProiect/{idProiect}",
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, projectId);

    }

    public ProjectInfo getInfoProiect(String token, String projectId){
        return get(ProjectInfo.class, checkBaseModelWithExtendedInfo(), getDmswsUrl()+"/project/{token}/getInfoProject/{idProiect}",
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, projectId);

    }

    public WbsResponse2 getWbs2(String token, String projectId){
        return get(WbsResponse2.class, checkStandardResponse(), getDmswsUrl()+"/wbs_uat/{token}/getWbs2/{idProiect}",
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, projectId);

    }

    public TaskInfoList getTaskuriWbs(String token, String idWbs){
        return get(TaskInfoList.class, checkBaseModelWithExtendedInfo(), getDmswsUrl()+"/wbs_uat/{token}/getTaskuriWbs/{idWbs}",
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, idWbs);

    }
    public TaskInfo addTask(String token, String wbsId, TaskInfo task){
        return post(task, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, TaskInfo.class, checkBaseModel(),
                getDmswsUrl()+"/wbs_uat/{token}/addTask/{wbsId}", token, wbsId);
    }

    public WbsUpdateResponse addWbs(String token, String projectId, Wbs2 wbs){
        return post(wbs, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, WbsUpdateResponse.class, checkStandardResponse(),
                getDmswsUrl()+"/wbs_uat/{token}/addWbs/{projectId}", token, projectId);
    }
    public WbsUpdateResponse updateWbsET(String token, String id, Wbs2 wbs){
        return post(wbs, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, WbsUpdateResponse.class, checkStandardResponse(),
                getDmswsUrl()+"/wbs_uat/{token}/updateWbsET/{id}", token, id);
    }

    public WbsUpdateResponse updateWbs2(String token, String wbsId, Wbs2 wbs){
        return post(wbs, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, WbsUpdateResponse.class, checkStandardResponse(),
                getDmswsUrl()+"/wbs_uat/{token}/updateWbs2/{idWbs}", token, wbsId);
    }
    public CreateTipDocFileResponse uploadFisierToProject(String token, Integer idProiect, Integer idDirector,String filename, byte[] someByteArray) {
        String path = getDmswsUrl()+ "/file/"+token+"/uploadFisierToProject/" +
                "?id_proiect=" + idProiect
                + "&id_director=" +idDirector
                ;
        CreateTipDocFileResponse result = upload(CreateTipDocFileResponse.class, checkStandardResponse(), filename, someByteArray, path);
        return result;
    }
    public Director addDirector(String token, String parentId, Director director){
        return post(director, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, Director.class, checkBaseModel(),
                getDmswsUrl()+"/dir/{token}/addFolder/{parentId}", token, parentId);
    }

    public Wbs2 getInfoWbsById(String token, String Id){
        return get(Wbs2.class, checkBaseModel(), getDmswsUrl()+"/wbs_uat/{token}/getInfoWbsById/{id}",
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, Id);
    }

    public UtilizatorList getListaResponsabili(String token){
        return get(UtilizatorList.class, checkBaseModel(), getDmswsUrl()+"/project/{token}/getListaUtilizatori",
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token);

    }
    public BaseModel deleteEtapa(String token, String id){
        //  return put(persoanaFizicaJuridica, PersoanaFizicaJuridica.class, checkBaseModel(), getDmswsUrl()+"/utilizator/{token}/updatePersoanaFizicaJuridica/{idUser}", token, idUser);

        return post(null,   MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON,
                BaseModel.class, checkBaseModel(),
                getDmswsUrl()+"/wbs_uat/{token}/deleteEtapa/{id}", token,id);

    }


    public ImpactList getListaPrioritatiTichet(String token){
        return get(ImpactList.class, checkBaseModel(), getDmswsUrl()+"/project/{token}/getListaPrioritatiTichet",
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token);

    }

    public StatusList getListaStatusEtapa(String token){
        return get(StatusList.class, checkBaseModel(), getDmswsUrl()+"/wbs_uat/{token}/getListaStatusEtapa",
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token);

    }
    public UnitateMasuraList getListaUnitateMasura(String token){
        return get(UnitateMasuraList.class, checkBaseModel(), getDmswsUrl()+"/wbs_uat/{token}/getListaUnitateMasura",
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token);

    }

    public BaseModel getPerioadaDepunere(@PathVariable String token) {
        return get(BaseModel.class, checkBaseModel(), getDmswsUrl()+"/project/{token}/getPerioadaDepunere",
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token);
    }
    public ProjectsList getProiecteExterneShortPaginatCount(@PathVariable String token, Optional<String> idtipStr) {
        String url="/project/{token}/getProiecteExterneShortPaginatCount";
        if(idtipStr.isPresent()){
            url+="?idTpStr="+idtipStr.get();
        }
        return get(ProjectsList.class, checkBaseModel(), getDmswsUrl()+url,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token);
    }

    public ProjectsList getProiecteExterneShortPaginat(@PathVariable String token, Optional<String> idtipStr, Optional<Integer> indexStart,Optional<Integer> indexEnd) {
        String url=getDmswsUrl()+"/project/{token}/getProiecteExterneShortPaginat";
        if(idtipStr.isPresent()){
            url+="?idTpStr="+idtipStr.get();
        }
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
        return get(ProjectsList.class, checkBaseModel(), url,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token);
    }
}