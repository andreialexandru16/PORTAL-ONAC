package ro.bithat.dms.microservices.portal.ecitizen.participatorybudgeting.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ro.bithat.dms.microservices.dmsws.poi.PoiFile;
import ro.bithat.dms.microservices.dmsws.poi.ProjectInfo;
import ro.bithat.dms.microservices.portal.ecitizen.participatorybudgeting.backend.DmswsParticipatoryBudgetingService;
import ro.bithat.dms.security.SecurityUtils;

@RestController("/bugetare")
public class DmswsParticipatoryBudgetingController {

	@Autowired
	private DmswsParticipatoryBudgetingService service; 
	
	@PostMapping("/participativa")
    public Object addNewPOI(@RequestBody ProjectInfo poiInfoReq){
    	return service.addNewPOI(SecurityUtils.getToken(), poiInfoReq);
    }


	@PostMapping(path="/participativa/{idPoi}/poi", consumes = {"application/xml", "application/json"} )
    public Object addNewPOI(@PathVariable Integer idPoi, @RequestBody PoiFile poiInfoReq){
    	return service.addPOIFile(SecurityUtils.getToken(), idPoi, poiInfoReq.getIdFisier());
    }
}
