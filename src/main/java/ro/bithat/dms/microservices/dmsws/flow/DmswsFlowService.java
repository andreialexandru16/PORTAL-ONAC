package ro.bithat.dms.microservices.dmsws.flow;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import ro.bithat.dms.microservices.dmsws.DmswsRestService;
import ro.bithat.dms.microservices.dmsws.file.BaseModel;
import ro.bithat.dms.security.SecurityUtils;

@Service
public class DmswsFlowService extends DmswsRestService{

	public StandardResponse addAction(String token, PortalFlowActionRequest request) {
		return post(request, StandardResponse.class, checkStandardResponse(), getDmswsUrl() + "/portalflow/{token}/addAction", token);
	}
	
    public PortalFlowPasiActiuniList getActionSteps(Long fileId) {
		//////////////////////
		//ORACLE BUG ON DMSWS////
		//////////////////////////
		return get(PortalFlowPasiActiuniList.class, checkStandardResponse(), 
				getDmswsUrl() + "/portalflow/{token}/getPasiActiuni?idFisier="+fileId,
				MediaType.APPLICATION_JSON,	MediaType.APPLICATION_JSON, SecurityUtils.getToken());
	}
	
    public PortalFlowConversatieList getConversation(Long flowId) {
		return get(PortalFlowConversatieList.class, checkStandardResponse(), 
				getDmswsUrl() + "/portalflow/{token}/getConversatie?idFlux="+flowId,
				MediaType.APPLICATION_JSON,	MediaType.APPLICATION_JSON, SecurityUtils.getToken());
//		return getRestTemplate().exchange(url + "/portalflow/" + SecurityUtils.getToken() + "/getConversatie?&idFlux="+flowId, HttpMethod.GET, entity, PortalFlowConversatieList.class).getBody();
	}
	
	public String getFlows(String token, String flowStatus, String stepStatus, String stepType) {
		return get(String.class, getDmswsUrl() + "/flow/{token}/get/{flowStatus}/{stepStatus}/{stepType}", token, flowStatus, stepStatus, stepType);
//		return getRestTemplate().getForEntity(url + "/flow/" + token + "/get/" + flowStatus + "/" + stepStatus + "/" + stepType, String.class).getBody();
	}
	
    public StandardResponse sendFluxByIdFisier(String token, Long idFisier) {
        return post(null, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, StandardResponse.class, checkStandardResponse(), 
        		getDmswsUrl() + "/portalflow/{token}/sendFluxByIdFisier/{idFisier}", token, idFisier);    	
	}
	public StandardResponse sendFluxRequestByIdFisier(String token, Long idFisier) {
		return post(null, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, StandardResponse.class, checkStandardResponse(),
				getDmswsUrl() + "/portalflow/{token}/sendFluxRequestByIdFisier/{idFisier}", token, idFisier);
	}
	public BaseModel setWorkflowStatus(String token, Long idFisier, Integer idStatus) {
		return post(null, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, BaseModel.class, checkBaseModel(),
				getDmswsUrl() + "/file/{token}/setWorkflowStatus/{idFisier}/{idStatus}", token, idFisier, idStatus);
	}
}