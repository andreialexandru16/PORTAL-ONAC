package ro.bithat.dms.microservices.dmsws.flow;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.conf.HystrixPropertiesManager;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import ro.bithat.dms.security.SecurityUtils;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DmswsFlowServiceOld {

	private static Logger logger = LoggerFactory.getLogger(DmswsFlowServiceOld.class);
	
	@Value("${dmsws.url}")
	private String url;

	public RestTemplate getRestTemplate(){
	    SSLContext sslContext;
		try {
			sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, new TrustAllStrategy()).build();
		    SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
		    CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
		    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		    requestFactory.setHttpClient(httpClient);
		    RestTemplate restTemplate = new RestTemplate(requestFactory);
		    return restTemplate;
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new RestTemplate();
	}

	@HystrixCommand(commandKey = "dmsws-addAction", commandProperties = {
			@HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS, value = "15000"),
	})
	public StandardResponse addAction(String token, PortalFlowActionRequest request) {
		logger.info(url + "/portalflow/" + token + "/addAction");
		return getRestTemplate().postForEntity(url + "/portalflow/" + token + "/addAction", request, StandardResponse.class).getBody();
	}
	
	@HystrixCommand(commandKey = "dmsws-getActionSteps", commandProperties = {
			@HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS, value = "15000"),
	})
    public PortalFlowPasiActiuniList getActionSteps(Long fileId) {
		//////////////////////
		//ORACLE BUG ON DMSWS////
		//////////////////////////
		logger.info(url + "/portalflow/" + SecurityUtils.getToken() + "/getPasiActiuni?idFisier="+fileId);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		List<MediaType> accept = new ArrayList<MediaType>();
		accept.add(MediaType.APPLICATION_JSON);
		headers.setAccept(accept);
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		return getRestTemplate().exchange(url + "/portalflow/" + SecurityUtils.getToken() + "/getPasiActiuni?idFisier="+fileId, HttpMethod.GET, entity, PortalFlowPasiActiuniList.class).getBody();
	}
	
	@RequestMapping(value = "/getConversatie", method = RequestMethod.GET, consumes = {"application/xml", "application/json"}, produces = {"application/xml", "application/json"})
    @ResponseBody
    
	@HystrixCommand(commandKey = "dmsws-getConversatie", commandProperties = {
			@HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS, value = "15000"),
	})
    public PortalFlowConversatieList getConversation(Long flowId) {
		logger.info(url + "/portalflow/" + SecurityUtils.getToken() + "/getConversatie?&idFlux="+flowId);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		List<MediaType> accept = new ArrayList<MediaType>();
		accept.add(MediaType.APPLICATION_JSON);
		headers.setAccept(accept);
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		return getRestTemplate().exchange(url + "/portalflow/" + SecurityUtils.getToken() + "/getConversatie?&idFlux="+flowId, HttpMethod.GET, entity, PortalFlowConversatieList.class).getBody();
	}
	
	@HystrixCommand(commandKey = "dmsws-getFlows", commandProperties = {
			@HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS, value = "15000"),
	})
	public String getFlows(String token, String flowStatus, String stepStatus, String stepType) {
		logger.info(url + "/flow/" + token + "/get/" + flowStatus + "/" + stepStatus + "/" + stepType);
		return getRestTemplate().getForEntity(url + "/flow/" + token + "/get/" + flowStatus + "/" + stepStatus + "/" + stepType, String.class).getBody();
	}
	
	@HystrixCommand(commandKey = "dmsws-getFlowsWaitingForMyAction", commandProperties = {
			@HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS, value = "15000"),
	})
	public String getFlowsWaitingForMyAction(String token) {
		logger.info(url + "/portalflow/" + token + "/getFlowInstances");
		return getRestTemplate().getForEntity(url + "/portalflow/" + token + "/getFlowInstances", String.class).getBody();
	}
	
	@HystrixCommand(commandKey = "dmsws-getFlowsWaitingForMyAction", commandProperties = {
			@HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS, value = "20000"),
	})
	public PortalFlowList getFlowsWaitingForMyAction(String token, Long flowId) {
		logger.info(url + "/portalflow/" + token + "/getFlowInstances/?&idFluxDef=" + flowId);
		RestTemplate restTemplate = getRestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		List<MediaType> accept = new ArrayList<MediaType>();
		accept.add(MediaType.APPLICATION_JSON);
		headers.setAccept(accept);

		HttpEntity<String> entity = new HttpEntity<String>("{\"idFluxDef\":"+ flowId +"}" ,headers);
		
		PortalFlowList result =  restTemplate.postForEntity(url + "/portalflow/" + token + "/getFlowInstances/?&idFluxDef=" + flowId, entity , PortalFlowList.class).getBody();
		return result;
	}
	
	@HystrixCommand(commandKey = "dmsws-getFilesSentByMeOnFlows", commandProperties = {
			@HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS, value = "20000"),
	})
	public PortalFlowList getFilesSentByMeOnFlows(String token, Long flowId) {
		logger.info(url + "/portalflow/" + token + "/getFilesSentByMeOnFlows/?&idFluxDef=" + flowId);
		RestTemplate restTemplate = getRestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		List<MediaType> accept = new ArrayList<MediaType>();
		accept.add(MediaType.APPLICATION_JSON);
		headers.setAccept(accept);

		HttpEntity<String> entity = new HttpEntity<String>("{\"idFluxDef\":"+ flowId +"}" ,headers);
		
		PortalFlowList result =  restTemplate.postForEntity(url + "/portalflow/" + token + "/getFilesSentByMeOnFlows?&idFluxDef=" + flowId, entity , PortalFlowList.class).getBody();
		return result;
	}
	

	@HystrixCommand(commandKey = "dmsws-getFilesActiveOnFlows", commandProperties = {
			@HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS, value = "20000"),
	})
	public PortalFlowList getFilesActiveOnFlows(String token, Long flowId) {
		logger.info(url + "/portalflow/" + token + "/getFilesActiveOnFlows?&idFluxDef=" + flowId);
		RestTemplate restTemplate = getRestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		List<MediaType> accept = new ArrayList<MediaType>();
		accept.add(MediaType.APPLICATION_JSON);
		headers.setAccept(accept);

		HttpEntity<String> entity = new HttpEntity<String>("{\"idFluxDef\":"+ flowId +"}" ,headers);
		
		PortalFlowList result =  restTemplate.postForEntity(url + "/portalflow/" + token + "/getFilesActiveOnFlows?&idFluxDef=" + flowId, entity , PortalFlowList.class).getBody();
		return result;
	}

	@HystrixCommand(commandKey = "dmsws-getFilesFinalizedOnFlows", commandProperties = {
			@HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS, value = "20000"),
	})
	public PortalFlowList getFilesFinalizedOnFlows(String token, Long flowId) {
		logger.info(url + "/portalflow/" + token + "/getFilesFinalizedOnFlows?&idFluxDef=" + flowId);
		RestTemplate restTemplate = getRestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		List<MediaType> accept = new ArrayList<MediaType>();
		accept.add(MediaType.APPLICATION_JSON);
		headers.setAccept(accept);

		HttpEntity<String> entity = new HttpEntity<String>("{\"idFluxDef\":"+ flowId +"}" ,headers);
		
		PortalFlowList result =  restTemplate.postForEntity(url + "/portalflow/" + token + "/getFilesFinalizedOnFlows?&idFluxDef=" + flowId, entity , PortalFlowList.class).getBody();
		return result;
	}
	
	//	FlowController > setAction for Current User with Comments for Step in
//	Workflow
//	http://<URL>/api/v1/flow/{token}/get/{fileId}/{flowId}/{comment}/{action}/{pas}/{idUser}
//	Approve / Reject file on workflow with comments
}