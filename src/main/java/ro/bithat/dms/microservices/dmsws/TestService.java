package ro.bithat.dms.microservices.dmsws;

import org.springframework.stereotype.Service;
import ro.bithat.dms.microservices.dmsws.flow.PortalFlowActionRequest;
import ro.bithat.dms.microservices.dmsws.flow.StandardResponse;

@Service
public class TestService extends DmswsRestService{

	public StandardResponse addAction(String token, PortalFlowActionRequest request) {
		return post(request, StandardResponse.class, getDmswsUrl() + "/portalflow/{token}/addAction", token);
	}
}
