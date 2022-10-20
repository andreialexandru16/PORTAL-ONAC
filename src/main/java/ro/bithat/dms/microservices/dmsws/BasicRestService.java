package ro.bithat.dms.microservices.dmsws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.function.Predicate;

public class BasicRestService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private HystrixRestService service;
	
	public <REQUEST, RESPONSE> RESPONSE put(REQUEST request, Class<RESPONSE> classResp, String urlWithParams, Object... params) {
		return put(request, classResp, null, urlWithParams, params);
	}
	public <REQUEST, RESPONSE> RESPONSE put(REQUEST request, MediaType contentType, MediaType acceptsType, Class<RESPONSE> classResp, String urlWithParams, Object... params) {
		return put(request, classResp, null, urlWithParams, params);
	}
	public <REQUEST, RESPONSE> RESPONSE post(REQUEST request, Class<RESPONSE> classResp, String urlWithParams, Object... params) {
		return post(request, classResp, null, urlWithParams, params);
	}
	
	public <RESPONSE> RESPONSE get(Class<RESPONSE> classResp, String urlWithParams, MediaType contentType, MediaType acceptsType, Object... params) {
		return get(classResp, null, urlWithParams, contentType, acceptsType, params);
	}
	
	public <RESPONSE> RESPONSE get(Class<RESPONSE> classResp, String urlWithParams, Object... params) {
		return get(classResp, null, urlWithParams, params);
	}

	public <RESPONSE> RESPONSE upload(Class<RESPONSE> classResp, String filename, byte[] someByteArray, String url) {
		return upload(classResp, null, filename, someByteArray, url);
	}
	
	public <REQUEST, RESPONSE> RESPONSE put(REQUEST request, Class<RESPONSE> classResp, Predicate<RESPONSE> checkResult, String urlWithParams, Object... params) {
		return service.put(logger, request, classResp, checkResult, urlWithParams, params);
	}
	public <REQUEST, RESPONSE> RESPONSE put(REQUEST request,MediaType contentType, MediaType acceptsType, Class<RESPONSE> classResp, Predicate<RESPONSE> checkResult, String urlWithParams, Object... params) {

		return service.put(logger, request, contentType, acceptsType, classResp, checkResult, urlWithParams, params);
	}
	public <REQUEST, RESPONSE> RESPONSE post(REQUEST request, Class<RESPONSE> classResp, Predicate<RESPONSE> checkResult, String urlWithParams, Object... params) {
		return service.post(logger, request, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, classResp, checkResult, urlWithParams, params);
	}
	
	public <REQUEST, RESPONSE> RESPONSE post(REQUEST request, MediaType contentType, MediaType acceptsType, Class<RESPONSE> classResp, Predicate<RESPONSE> checkResult, String urlWithParams, Object... params) {
		return service.post(logger, request, contentType, acceptsType, classResp, checkResult, urlWithParams, params);
	}
	
	public <RESPONSE> RESPONSE get(Class<RESPONSE> classResp, Predicate<RESPONSE> checkResult, String urlWithParams, MediaType contentType, MediaType acceptsType, Object... params) {
		return service.get(logger, classResp, checkResult, urlWithParams, contentType, acceptsType, params);
	}
	
	public <RESPONSE> RESPONSE get(Class<RESPONSE> classResp, Predicate<RESPONSE> checkResult, String urlWithParams, Object... params) {
		return service.get(logger, classResp, checkResult, urlWithParams, params);
	}
	
	public <RESPONSE> RESPONSE upload(Class<RESPONSE> classResp, Predicate<RESPONSE> predicate, String filename, byte[] someByteArray, String url) {
		return service.upload(logger, classResp, predicate, filename, someByteArray, url);
	}
}
