package ro.bithat.dms.microservices.dmsws.login;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ro.bithat.dms.microservices.dmsws.colaboration.Utilizator;
import ro.bithat.dms.microservices.dmsws.file.User;
import ro.bithat.dms.microservices.dmsws.file.UserToken;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat.PersoanaFizicaJuridicaResponse;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DmswsLoginService {

	private static Logger logger = LoggerFactory.getLogger(DmswsLoginService.class);
	
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
	
//	@HystrixCommand( commandKey = "dmsws-login", commandProperties = {
//            @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS, value = "10000"),
//    })
	public UserToken login(String userName, String password) {
		logger.info("login attempting for " + url + "/login/" + userName);
		RestTemplate restTemplate = getRestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		List<MediaType> accept = new ArrayList<MediaType>();
		accept.add(MediaType.APPLICATION_JSON);
		headers.setAccept(accept);
		
		User user = new User();
		user.setUsername(userName);
		user.setPassword(password);
		HttpEntity<User> request = new HttpEntity<User>(user, headers);
		
		ResponseEntity<UserToken> response = restTemplate.exchange(url + "/login/", HttpMethod.PUT, request, UserToken.class);
		logger.info("login succesfull " + url + "/login/" + userName);
		return response.getBody();
	}

	public UserToken loginOidc(String oidcUserName) {
		logger.info("login attempting for " + url + "/login/loginOidc/" + oidcUserName);
		RestTemplate restTemplate = getRestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		List<MediaType> accept = new ArrayList<MediaType>();
		accept.add(MediaType.APPLICATION_JSON);
		headers.setAccept(accept);

		User user = new User();
		user.setUsername(oidcUserName);
		HttpEntity<User> request = new HttpEntity<User>(user, headers);

		ResponseEntity<UserToken> response = restTemplate.exchange(url + "/login/loginOidc", HttpMethod.PUT, request, UserToken.class);
		logger.info("login succesfull " + url + "/login/loginOidc/" + oidcUserName);
		return response.getBody();
	}
	
//	@HystrixCommand( commandKey = "dmsws-login", commandProperties = {
//            @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS, value = "10000"),
//    })
	public String loginForToken( String userName, String password) {
		RestTemplate restTemplate = getRestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		User user = new User();
		user.setUsername(userName);
		user.setPassword(password);
		HttpEntity<User> request = new HttpEntity<User>(user, headers);
		
		ResponseEntity<UserToken> response = restTemplate.exchange(url + "/login/", HttpMethod.PUT, request, UserToken.class);
		return response.getBody().getToken();
	}
	
//	@HystrixCommand( commandKey = "dmsws-checkTokenValidity", commandProperties = {
//            @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS, value = "10000"),
//    })
	public boolean checkTokenValidity(String token) {
		ResponseEntity<UserToken> response = getRestTemplate().getForEntity(url + "/login/" + token, UserToken.class);
		return new Boolean(response.getBody().getValid());
	}



}
