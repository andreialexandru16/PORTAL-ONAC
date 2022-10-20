package ro.bithat.dms.microservices.portal.ecitizen.participatorybudgeting.backend;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import ro.bithat.dms.microservices.dmsws.DmswsRestService;
import ro.bithat.dms.microservices.dmsws.file.BaseModel;
import ro.bithat.dms.microservices.dmsws.poi.*;

import java.util.Optional;

@Service
public class DmswsParticipatoryBudgetingService extends DmswsRestService {

    public ProjectsList getProiecte(String token) {
        return get(ProjectsList.class, checkBaseModel(), getDmswsUrl()+"/project/{token}/getProiecte",
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token);
    }
    public CategorieSesizareList getCategoriiSesizari(String token) {
        return get(CategorieSesizareList.class, checkBaseModel(), getDmswsUrl()+"/buget_sesizari/{token}/getCategoriiSesizari",
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token);
    }
    public CategoriePOIList getCategoriiPOI(String token) {
        return get(CategoriePOIList.class, checkBaseModel(), getDmswsUrl()+"/buget_sesizari/{token}/getCategoriiPOI",
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token);
    }
    public DurataProiectList getDurataProiectList(String token) {
        return get(DurataProiectList.class, checkBaseModel(), getDmswsUrl()+"/buget_sesizari/{token}/getDurataProiectList",
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token);
    }
    public StatusProiectList getListaStatusProiect(String token) {
        return get(StatusProiectList.class, checkBaseModel(), getDmswsUrl()+"/project/{token}/getListaStatusProiect",
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token);
    }

    public ProjectInfoList getPOIs(String token,Optional<String> deLaData, Optional<String> panaLaData, Optional<String> stadiu, Optional<String> idTipProiect,Optional<String> idCategorie) {

        String url="/buget_sesizari/{token}/getProjects";
        if(deLaData.isPresent() && panaLaData.isPresent()){
            url+="?deLaData="+deLaData.get()+"+&panaLaData="+panaLaData.get();
        }

        if(stadiu.isPresent()){
            if(url.contains("?")){
                url+="&stadiu="+stadiu.get();
            }
            else{
                url+="?stadiu="+stadiu.get();
            }

        }
        if(idTipProiect.isPresent()){
            if(url.contains("?")){
                url+="&idTpStr="+idTipProiect.get();
            }
            else{
                url+="?idTpStr="+idTipProiect.get();
            }

        }
        if(idCategorie.isPresent()){
            if(url.contains("?")){
                url+="&idCategorie="+idCategorie.get();
            }
            else{
                url+="?idCategorie="+idCategorie.get();
            }

        }

        return get(ProjectInfoList.class, checkBaseModel(), getDmswsUrl()+url,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token);
    }

    public ProjectInfoList getPOIsFiltered(String token,Optional<String> deLaData, Optional<String> panaLaData, Optional<String> stadiu, Optional<String> idTipProiect,Optional<String> idCategorie,Optional<String> searchStr) {

        String url="/buget_sesizari/{token}/getProjects";
        if(deLaData.isPresent() && panaLaData.isPresent()){
            url+="?deLaData="+deLaData.get()+"+&panaLaData="+panaLaData.get();
        }

        if(stadiu.isPresent()){
            if(url.contains("?")){
                url+="&stadiu="+stadiu.get();
            }
            else{
                url+="?stadiu="+stadiu.get();
            }

        }
        if(idTipProiect.isPresent()){
            if(url.contains("?")){
                url+="&idTpStr="+idTipProiect.get();
            }
            else{
                url+="?idTpStr="+idTipProiect.get();
            }

        }
        if(idCategorie.isPresent()){
            if(url.contains("?")){
                url+="&idCategorie="+idCategorie.get();
            }
            else{
                url+="?idCategorie="+idCategorie.get();
            }

        }
        if(searchStr.isPresent()){
            if(url.contains("?")){
                url+="&searchStr="+searchStr.get();
            }
            else{
                url+="?searchStr="+searchStr.get();
            }

        }
        return get(ProjectInfoList.class, checkBaseModel(), getDmswsUrl()+url,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token);
    }
    public ProjectInfo getInfoPOI(String token, Integer idPOI) {
        return get(ProjectInfo.class, checkBaseModel(), getDmswsUrl()+"/buget_sesizari/{token}/getInfoProject/{idPOI}",
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token,idPOI);
    }
    public ProjectInfo addNewPOI(String token, ProjectInfo poiInfoReq){
      /*  if (poiInfoReq.getDataStart() == null) {
            poiInfoReq.setDataStart("01-01-2000");
        }
        if (poiInfoReq.getDataEnd() == null) {
            poiInfoReq.setDataEnd("01-01-2000");
        }*/
        return post(poiInfoReq, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, ProjectInfo.class, checkBaseModel(),
                getDmswsUrl()+"/buget_sesizari/{token}/addNewProject", token);
    }
    public ProjectInfo updatePOI(String token, ProjectInfo poiInfoReq, Integer idPOI){
     /*   if (poiInfoReq.getDataStart() == null) {
            poiInfoReq.setDataStart("01-01-2000");
        }
        if (poiInfoReq.getDataEnd() == null) {
            poiInfoReq.setDataEnd("01-01-2000");
        }*/
    	return post(poiInfoReq, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, ProjectInfo.class, checkBaseModel(),
                getDmswsUrl()+"/buget_sesizari/{token}/updateProject/{idPOI}", token,idPOI);
    }

    public ProjectFile addPOIFile(String token, Integer idPOI, Integer fileId){
        ProjectFile poiFileReq = new ProjectFile();
        poiFileReq.setIdFisier(fileId);
        poiFileReq.setIdProiect(idPOI);
        return post(poiFileReq, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, ProjectFile.class, checkBaseModel(),
                getDmswsUrl()+"/buget_sesizari/{token}/addProjectFile/{idPOI}", token,idPOI);
    }

    public ProjectFilesList getAlteFisierePOI(String token, Integer idPOI, Optional<String> codTipDocument) {
        String url="/buget_sesizari/{token}/getAlteFisiereProject/{idPOI}";
        if(codTipDocument.isPresent()){
            url+="?codTipDocument="+codTipDocument.get();
        }
        return get(ProjectFilesList.class, checkBaseModel(), getDmswsUrl()+url,
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token,idPOI);
    }

    public BaseModel POIVote(String token, Integer idPOI,Integer vot){

        return post(null, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, BaseModel.class, checkBaseModel(),
                getDmswsUrl()+"/buget_sesizari/{token}/projectVote/{idPOI}/{vot}", token,idPOI,vot);
    }
    public BaseModel checkIfHasRole(String token, String roleCode) {
        //return true pe info daca are rol
        //return false pe info daca nu are rol
        return get(BaseModel.class, checkBaseModel(), getDmswsUrl()+"/buget_sesizari/{token}/checkIfHasRole/{roleCode}",
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token,roleCode);
    }

    public BaseModel checkForUserVote(String token, Integer idPoi) {
        //return true pe info daca a mai votat
        //return false pe info daca nu a mai votat
        return get(BaseModel.class, checkBaseModel(), getDmswsUrl()+"/buget_sesizari/{token}/checkForUserVote/{idPoi}",
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token,idPoi);
    }

}
