package ro.bithat.dms.microservices.portal.ecitizen.useraccount.api;

import org.atmosphere.config.service.Get;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ro.bithat.dms.microservices.dmsws.file.BaseModel;
import ro.bithat.dms.microservices.dmsws.metadata.LovList;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.DmswsNomenclatorService;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat.FilialaList;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat.JudetList;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat.TaraList;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat.LocalitateList;


@RestController
public class DmswsNomenclatorController {
	
	@Value("${dmsws.anonymous.token}")
	private String anonymousToken;
	
	@Autowired
	private DmswsNomenclatorService service;

	@GetMapping("/dmsws/nomenclator/country/{idCountry}/cities")
    public LocalitateList getCities(@PathVariable String idCountry) {
    	return service.getInfoLocalitate(anonymousToken, idCountry);
    }
	@GetMapping("/dmsws/nomenclator/country/{idRegion}/cities_by_region")
	public LocalitateList getCitiesByRegion(@PathVariable String idRegion) {
		return service.getLocalitatiJudet(anonymousToken, idRegion);
	}
	@GetMapping("/dmsws/nomenclator/country/{idCountry}/regions")
    public JudetList getRegions(@PathVariable String idCountry) {
		return service.getInfoJudet(anonymousToken, idCountry);
    }
    
	@GetMapping("/dmsws/nomenclator/countries")
    public TaraList getInfoTari() {
		return service.getInfoTara(anonymousToken);
    }

	@GetMapping("/dmsws/nomenclator/country/{idCountry}/prefix")
	public BaseModel getPrefix(@PathVariable String idCountry) {
		return service.getPrefix(anonymousToken, idCountry);
	}

	@GetMapping("/dmsws/nomenclator/filiala")
	public FilialaList getFilialaList() {
		return service.getFilialaList(anonymousToken);
	}

	@GetMapping("/dmsws/nomenclator/regions/{idCountry}")
	public LovList getRegionsByCountry(@PathVariable Integer idCountry) {return service.getJudeteByIdTara(anonymousToken, idCountry);}

	@GetMapping("/dmsws/nomenclator/tarife_ee_n3")
	public LovList getTarifeElectricieniNivel3() {return service.getTarifeElectricieniNivel3(anonymousToken);}

	@GetMapping("/dmsws/nomenclator/operatori_economici")
	public LovList getOperatoriEconomici() {return service.getOperatoriEconomici(anonymousToken);}

	@GetMapping("/dmsws/nomenclator/status_dosar")
	public LovList getStatusDosar() {return service.getStatusDosar(anonymousToken);}

	@GetMapping("/dmsws/nomenclator/sesiune_examen/{zona}")
	public LovList getSesiuniByZona(@PathVariable String zona) {return service.getSesiuniByZona(anonymousToken, zona);}

	@GetMapping("/dmsws/nomenclator/centru_examen")
	public LovList getCentruExamen() {return service.getCentruExamen(anonymousToken);}
}
