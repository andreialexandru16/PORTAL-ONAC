package ro.bithat.dms.microservices;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/health")
public class HealthController {

	@RequestMapping(value = "", method = RequestMethod.GET, produces = {"application/json"})
	public String health() {
		return "{\"status\":\"UP\"}";
	}
}
