package ro.bithat.dms.microservices.dmsws;

import org.apache.commons.io.FileUtils;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebInputException;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@Service
class HystrixRestService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
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

//	@HystrixCommand(threadPoolKey = "dmsws_post", commandKey = "dmsws_post")
	public <REQUEST, RESPONSE> RESPONSE post(Logger logger, REQUEST request, MediaType contentType, MediaType acceptsType, Class<RESPONSE> classResp, Predicate<RESPONSE> predicate, String urlWithParams, Object... params) {
		String loggingPart;
		long startTime = System.currentTimeMillis();
		logger.info("dmsws requesting over "+ (loggingPart = " POST to "+replaceUriVariablesAndParams(urlWithParams, params)));
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(contentType);
		List<MediaType> accept = new ArrayList<MediaType>();
		if (acceptsType != null) {
			accept.add(acceptsType);	
		}
		headers.setAccept(accept);
//		HttpEntity<String> entity = new HttpEntity<String>(request, headers);
		
//		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
//		map.add("request", request);
		
		// build the request
		HttpEntity<REQUEST> httpEntity = new HttpEntity<REQUEST>(request, headers);
		
//		HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<MultiValueMap<String, Object>>(map, headers);
		
		try {
			if (params.length == 0) {
//				return checkResponse(logger, "dmsws completed for " + loggingPart, classResp, predicate,
//						getRestTemplate().postForEntity(urlWithParams, request, classResp).getBody());
				
				
				
				return checkResponse(logger, "dmsws completed in "+ (System.currentTimeMillis()-startTime)+"ms for " + loggingPart, classResp, predicate,
						getRestTemplate().exchange(urlWithParams, HttpMethod.POST, httpEntity, classResp).getBody());
			}

			return checkResponse(logger, "dmsws completed in "+ (System.currentTimeMillis()-startTime)+"ms for " + loggingPart, classResp, predicate,
					getRestTemplate().exchange(urlWithParams, HttpMethod.POST, httpEntity, classResp, getUriVariablesAndParams(urlWithParams, params)).getBody());
//			return checkResponse(logger, "dmsws completed for " + loggingPart, classResp, predicate,
//					getRestTemplate().postForEntity(urlWithParams, request, classResp, getUriVariablesAndParams(urlWithParams, params)).getBody());
		}catch(HttpClientErrorException ee) {
			logger.error("dmsws completed in "+ (System.currentTimeMillis()-startTime)+"ms with error {"+((HttpClientErrorException)ee).getResponseBodyAsString()+"} for " + loggingPart);
			throw ee;
		}catch(Throwable e) {
			logger.error("dmsws completed in "+ (System.currentTimeMillis()-startTime)+"ms with error {"+e.getMessage()+"} for " + loggingPart);
			throw e;
		}
	}

//	@HystrixCommand(threadPoolKey = "dmsws_put", commandKey = "dmsws_put")
	public <REQUEST, RESPONSE> RESPONSE put(Logger logger, REQUEST request, MediaType contentType, MediaType acceptsType,  Class<RESPONSE> classResp, Predicate<RESPONSE> predicate, String urlWithParams, Object... params) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType( contentType);
		List<MediaType> accept = new ArrayList<MediaType>();
		accept.add(acceptsType);
		headers.setAccept(accept);
		String loggingPart;
		logger.info("dmsws requesting over "+ (loggingPart = " PUT to "+replaceUriVariablesAndParams(urlWithParams, params)));
		long startTime = System.currentTimeMillis();
		try {
			HttpEntity<REQUEST> entity = new HttpEntity<REQUEST>(request,headers);
			if (params.length == 0) {
				return checkResponse(logger, "dmsws completed in "+ (System.currentTimeMillis()-startTime)+"ms for " + loggingPart, classResp, predicate,
						getRestTemplate().exchange(urlWithParams, HttpMethod.PUT, entity, classResp).getBody());
			}
			return checkResponse(logger, "dmsws completed in "+ (System.currentTimeMillis()-startTime)+"ms for " + loggingPart, classResp, predicate,
					getRestTemplate().exchange(urlWithParams, HttpMethod.PUT, entity, classResp, getUriVariablesAndParams(urlWithParams, params)).getBody());
		}catch(Throwable e) {
			logger.error("dmsws completed in "+ (System.currentTimeMillis()-startTime)+"ms with error {"+e.getMessage()+"} for " + loggingPart);
			throw e;
		}
	}


	//	@HystrixCommand(threadPoolKey = "dmsws_put", commandKey = "dmsws_put")
	public <REQUEST, RESPONSE> RESPONSE put(Logger logger, REQUEST request, Class<RESPONSE> classResp, Predicate<RESPONSE> predicate, String urlWithParams, Object... params) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType( MediaType.APPLICATION_JSON);
		List<MediaType> accept = new ArrayList<MediaType>();
		accept.add(MediaType.APPLICATION_JSON);
		headers.setAccept(accept);
		String loggingPart;
		logger.info("dmsws requesting over "+ (loggingPart = " PUT to "+replaceUriVariablesAndParams(urlWithParams, params)));
		long startTime = System.currentTimeMillis();
		try {
			HttpEntity<REQUEST> entity = new HttpEntity<REQUEST>(request,headers);
			if (params.length == 0) {
				return checkResponse(logger, "dmsws completed in "+ (System.currentTimeMillis()-startTime)+"ms for " + loggingPart, classResp, predicate,
						getRestTemplate().exchange(urlWithParams, HttpMethod.PUT, entity, classResp).getBody());
			}
			return checkResponse(logger, "dmsws completed in "+ (System.currentTimeMillis()-startTime)+"ms for " + loggingPart, classResp, predicate,
					getRestTemplate().exchange(urlWithParams, HttpMethod.PUT, entity, classResp, getUriVariablesAndParams(urlWithParams, params)).getBody());
		}catch(Throwable e) {
			logger.error("dmsws completed in "+ (System.currentTimeMillis()-startTime)+"ms with error {"+e.getMessage()+"} for " + loggingPart);
			throw e;
		}
	}
//	@HystrixCommand(threadPoolKey = "dmsws_get", commandKey = "dmsws_get")
	public <RESPONSE> RESPONSE get(Logger logger, Class<RESPONSE> classResp, Predicate<RESPONSE> predicate, String urlWithParams, Object... params) {
		String loggingPart;
		logger.info("dmsws requesting over "+ (loggingPart = " GET to "+replaceUriVariablesAndParams(urlWithParams, params)));
		long startTime = System.currentTimeMillis();
		try {
			if (params.length == 0) {
				return checkResponse(logger, "dmsws completed in "+ (System.currentTimeMillis()-startTime)+"ms for " + loggingPart, classResp, predicate,
						getRestTemplate().getForEntity(urlWithParams, classResp).getBody());
			}
			return checkResponse(logger, "dmsws completed in "+ (System.currentTimeMillis()-startTime)+"ms for " + loggingPart, classResp, predicate,
					getRestTemplate().getForEntity(urlWithParams, classResp, getUriVariablesAndParams(urlWithParams, params)).getBody());
		}catch(Throwable e) {
			logger.error("dmsws completed in "+ (System.currentTimeMillis()-startTime)+"ms with error {"+e.getMessage()+"} for " + loggingPart);
			throw e;
		}
	}
	
//	@HystrixCommand(threadPoolKey = "dmsws_get", commandKey = "dmsws_get")
	public <RESPONSE> RESPONSE get(Logger logger, Class<RESPONSE> classResp, Predicate<RESPONSE> predicate, String urlWithParams, MediaType contentType, MediaType acceptsType, Object... params) {
		String loggingPart;		
		logger.info("dmsws requesting over "+ (loggingPart = " GET to "+replaceUriVariablesAndParams(urlWithParams, params)));
		long startTime = System.currentTimeMillis();
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(contentType);
			List<MediaType> accept = new ArrayList<MediaType>();
			accept.add(acceptsType);
			headers.setAccept(accept);
			HttpEntity<String> entity = new HttpEntity<String>(headers);
			return checkResponse(logger, "dmsws completed in "+ (System.currentTimeMillis()-startTime)+"ms for " + loggingPart, classResp, predicate,
					getRestTemplate().exchange(urlWithParams, HttpMethod.GET, entity, classResp, params).getBody());
		}catch(Throwable e) {
			logger.error("dmsws completed in "+ (System.currentTimeMillis()-startTime)+"ms with error {"+e.getMessage()+"} for " + loggingPart);
			throw e;
		}
	}
	
	public <RESPONSE> RESPONSE checkResponse(Logger logger, String logDetails, Class<RESPONSE> classResp, Predicate<RESPONSE> predicate, RESPONSE response) {
		if (predicate != null) {
			if ( !predicate.test(response)) {
				logger.error(logDetails + " with error!");
				throw new ServerWebInputException("Internal server error!");
			}else {
				logger.info(logDetails);
			}
		}
		return response;
	}
	
	public <RESPONSE> void checkResponse(RESPONSE response) {
	}
	
	private static String replaceUriVariablesAndParams(String uri, Object... params){
		if (params.length == 0) {
			return uri;
		}
		List<String> uriVariables = getUriVariables(uri);
		if (uriVariables.size() != params.length) {
			throw new IllegalArgumentException();
		}
		for (int i=0; i< params.length; i++) {
			uri = uri.replace("{"+ uriVariables.get(i) +"}", params[i]+"");
		}
		return uri;
	}
	
	private static Map<String, String> getUriVariablesAndParams(String uri, Object... params){
		Map<String, String> result = new HashMap<>();
		if (params.length == 0) {
			return result; 
		}
		List<String> keys = getUriVariables(uri);
		if (keys.size() != params.length) {
			throw new IllegalArgumentException();
		}
		for (int i = 0; i < keys.size(); i++) {
			result.put(keys.get(i), params[i]+"");
		}
		return result;
	}
	
	private static List<String> getUriVariables(String uri){
		List<String> result = new ArrayList<String>();
		String parts[] = uri.split("\\{");
		if (parts.length<=1) {
			result.add(uri);
			return result;
		}
		for (int i=1; i < parts.length; i++) {
			int index = parts[i].indexOf("}");
			if (index <=0 ) {
				throw new IllegalArgumentException();
			}
			result.add(parts[i].substring(0, index));
		}
		return result;
	}
	
//	@HystrixCommand(threadPoolKey = "dmsws_upload", commandKey = "dmsws_upload")
	public <RESPONSE> RESPONSE upload(Logger logger, Class<RESPONSE> classResp, Predicate<RESPONSE> predicate, String filename, byte[] someByteArray, String url) {
		String loggingPart;
		logger.info("dmsws requesting over "+ (loggingPart = " UPLOAD via POST to "+url));
		long startTime = System.currentTimeMillis();
		try {
			RestTemplate restTemplate = getRestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.MULTIPART_FORM_DATA);
	
			List<MediaType> accept = new ArrayList<MediaType>();
			accept.add(MediaType.APPLICATION_JSON);
			headers.setAccept(accept);
	
			MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
			File file = new File(filename);
			try {
				FileUtils.writeByteArrayToFile(file, someByteArray);
			} catch (IOException e1) {
				logger.error(e1.getMessage(), e1);
			}
			body.add("fileData", new FileSystemResource(file));
	
			HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);
	
			RESPONSE result = restTemplate
					.postForEntity(url, entity, classResp/* , finishEdit, fileId, token, userId, comment */)
					.getBody();
			try {
				file.delete();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
	//TODO: handle exception
			}
			checkResponse(logger, "dmsws completed in "+ (System.currentTimeMillis()-startTime)+"ms for " + loggingPart, classResp, predicate,result);
			return result;
		}catch(Throwable e) {
			logger.error("dmsws completed in "+ (System.currentTimeMillis()-startTime)+"ms with error {"+e.getMessage()+"} for " + loggingPart);
			throw e;
		}	
	}

	public <RESPONSE> RESPONSE delete(Logger logger, Class<RESPONSE> classResp, Predicate<RESPONSE> predicate, String urlWithParams, MediaType contentType, MediaType acceptsType, Object... params) {
		String loggingPart;
		logger.info("dmsws requesting over "+ (loggingPart = " DELETE to "+replaceUriVariablesAndParams(urlWithParams, params)));
		long startTime = System.currentTimeMillis();
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(contentType);
			List<MediaType> accept = new ArrayList<MediaType>();
			accept.add(acceptsType);
			headers.setAccept(accept);
			HttpEntity<String> entity = new HttpEntity<String>(headers);
			return checkResponse(logger, "dmsws completed in "+ (System.currentTimeMillis()-startTime)+"ms for " + loggingPart, classResp, predicate,
					getRestTemplate().exchange(urlWithParams, HttpMethod.DELETE, entity, classResp, params).getBody());
		}catch(Throwable e) {
			logger.error("dmsws completed in "+ (System.currentTimeMillis()-startTime)+"ms with error {"+e.getMessage()+"} for " + loggingPart);
			throw e;
		}
	}
	
	public void handleError(Throwable t) {
		logger.error(t.getMessage(), t);
	}
}