package ro.bithat.dms.microservices.dmsws.file;

import org.apache.commons.io.FileUtils;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerErrorException;
import org.springframework.web.server.ServerWebInputException;
import ro.bithat.dms.microservices.dmsws.DmswsRestService;
import ro.bithat.dms.microservices.dmsws.flow.StandardResponse;
import ro.bithat.dms.microservices.dmsws.metadata.AttributeLinkListOfLists;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.CreateTipDocFileResponse;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.CreateTipDocFileResponseXml;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.DocObligatoriiResp;
import ro.bithat.dms.microservices.portal.ecitizen.website.models.*;
import ro.bithat.dms.security.SecurityUtils;

import javax.net.ssl.SSLContext;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class FormulareService extends DmswsRestService{


	private static Logger logger = LoggerFactory.getLogger(FormulareService.class);
	
	@Value("${dmsws.url}")
	private String url;

	private String getToken() {
		return SecurityUtils.getToken();
	}

	public ProceduraList getProceduri(String token){
		return get(ProceduraList.class, checkStandardResponse(), getDmswsUrl()+"/formulare/{token}/proceduri",
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token);
	}

	public FormularList getFormulare(String token, Integer idProcedura){
		return get(FormularList.class, checkStandardResponse(), getDmswsUrl()+"/formulare/{token}/get/{idProcedura}",
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, idProcedura);
	}

	public DrepturiTipDocList getDrepturi(String token){
		return get(DrepturiTipDocList.class, checkStandardResponse(), getDmswsUrl()+"/formulare/{token}/get_tip_doc",
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token);
	}
}
