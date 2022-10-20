package ro.bithat.dms.microservices.dmsws.flow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/dmsws/flows")
public class DmswsFlowController {

	@Autowired
	private DmswsFlowService service;
	
	@GetMapping("/all")
	public String getFlows(@RequestParam String token, @RequestParam String flowStatus, @RequestParam String stepStatus, @RequestParam String stepType){
		return service.getFlows(token, flowStatus, stepStatus, stepType);
	}

//	@GetMapping("/waiting")
//	public String getFlows(@RequestParam String token){
//		return service.getFlowsWaitingForMyAction(token);
//	}
//	
//	@GetMapping("/waiting/{flowId}")
//	public PortalFlowList getFlows(@RequestParam String token, @PathVariable Long flowId){
//		return service.getFlowsWaitingForMyAction(token, flowId);
//	}
}
