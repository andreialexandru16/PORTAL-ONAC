package ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import ro.bithat.dms.microservices.dmsws.DmswsRestService;
import ro.bithat.dms.microservices.dmsws.file.BaseModel;
import ro.bithat.dms.microservices.dmsws.metadata.LovList;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat.FilialaList;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat.JudetList;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat.TaraList;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat.LocalitateList;

@Service
public class DmswsNomenclatorService extends DmswsRestService{
	
	@Cacheable(value = "infoLocalitate", key = "1")
	public LocalitateList getInfoLocalitate(@PathVariable String token, @PathVariable String idTara) {
    	return get(LocalitateList.class, checkBaseModel(), getDmswsUrl()+"/nomenc/{token}/getInfoLocalitate/{idTara}", 
    			MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, idTara);
    }
	public LocalitateList getLocalitatiJudet(@PathVariable String token, @PathVariable String idJudet) {
		return get(LocalitateList.class, checkBaseModel(), getDmswsUrl()+"/nomenc/{token}/getLocalitatiJudet/{idJudet}",
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, idJudet);
	}

    public JudetList getInfoJudet(@PathVariable String token, @PathVariable String idTara) {
    	return get(JudetList.class, checkBaseModel(), getDmswsUrl()+"/nomenc/{token}/getInfoJudet/{idTara}", 
    			MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, idTara);
    }
    
	@Cacheable(value = "infoTara", key = "1")
    public TaraList getInfoTara(@PathVariable String token) {
    	return get(TaraList.class, checkBaseModel(), getDmswsUrl()+"/nomenc/{token}/getInfoTara/",
    			MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token);
    }

	public FilialaList getFilialaList(@PathVariable String token) {
		return get(FilialaList.class, checkBaseModel(), getDmswsUrl()+"/nomenc/{token}/getFilialaList",
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token);
	}

	public BaseModel getPrefix(@PathVariable String token,@PathVariable String idTara) {
		return get(BaseModel.class, checkBaseModel(), getDmswsUrl()+"/nomenc/{token}/getPrefixTara/{idTara}",
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token,idTara);
	}

	public LovList getJudeteByIdTara(String token, Integer idTara){
		return get(LovList.class, getDmswsUrl() + "/nomenc/{token}/getJudeteByIdTara/{idTara}", token,idTara);
	}

	public LovList getTarifeElectricieniNivel3(String token) {
		return get(LovList.class, getDmswsUrl() + "/nomenc/{token}/getTarifeElectricieniNivel3", token);
	}

	public LovList getOperatoriEconomici(String token){
		return get(LovList.class, getDmswsUrl() + "/nomenc/{token}/getOperatoriEconomici", token);
	}

	public LovList getStatusDosar(String token){
		return get(LovList.class, getDmswsUrl() + "/nomenc/{token}/getStatusDosar", token);
	}

	public LovList getSesiuniByZona(String token, String zona){
		return get(LovList.class, getDmswsUrl() + "/nomenc/{token}/getSesiuniByZona/{zona}", token,zona);
	}

	public LovList getCentruExamen(String token){
		return get(LovList.class, getDmswsUrl() + "/nomenc/{token}/getCentruExamen", token);
	}
}
