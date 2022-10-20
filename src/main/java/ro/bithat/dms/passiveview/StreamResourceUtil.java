package ro.bithat.dms.passiveview;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.server.StreamResource;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import ro.bithat.dms.microservices.portal.ecitizen.website.models.ExportXlsxReq;

import javax.net.ssl.SSLContext;
import java.io.ByteArrayInputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class StreamResourceUtil {

    private static Logger logger = LoggerFactory.getLogger(StreamResourceUtil.class);
    private static RestTemplate getRestTemplate(){
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

    public static byte[] downloadFile(String url)  {
        logger.info("start downloading file from DMSWS. dmsws requesting over downloadFile via GET to "+url);
        RestTemplate restTemplate = getRestTemplate();

        restTemplate.getMessageConverters().add(
                new ByteArrayHttpMessageConverter());

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
        //	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_PDF));
        //
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<byte[]> response = null;
        try {
            response = restTemplate.exchange(
                    url, HttpMethod.GET, entity, byte[].class);

            if (response.getStatusCode() == HttpStatus.OK) {
                logger.info("completed downloading file from DMSWS. dmsws completed for GET to "+url);
                return response.getBody();
            }
            logger.error("completed downloading file from DMSWS. dmsws completed with error "+response.getStatusCode()+" for GET to "+url);
            Notification.show("Eroare server DMSWS! Repetati operatia.");
            return null;
        }catch (Throwable e) {
            logger.error("completed downloading file from DMSWS. dmsws completed with error "+e.getMessage()+" for GET to "+url, e);
            return null;
        }

    }
    public static byte[] exportXlsx(String url, ExportXlsxReq req)  {
        logger.info("start exporting file using "+url);

        RestTemplate restTemplate = getRestTemplate();

        restTemplate.getMessageConverters().add(
                new ByteArrayHttpMessageConverter());

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));

        HttpEntity<ExportXlsxReq> request = new HttpEntity<ExportXlsxReq>(req, headers);

//        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<byte[]> response = null;
        try {
            response = restTemplate.exchange(
                    url, HttpMethod.POST, request, byte[].class);

            if (response.getStatusCode() == HttpStatus.OK) {
                logger.info("export completed");
                return response.getBody();
            }
            logger.error("export completed with error "+response.getStatusCode()+" for "+url);
            Notification.show("Eroare server DMSWS! Repetati operatia.");
            return null;
        }catch (Throwable e) {
            logger.error("export completed with error "+e.getMessage()+" for "+url, e);
            return null;
        }

    }

    public static StreamResource getStreamResource(String fileName, String url) {
        fileName=fileName.replaceAll("\\+","-");
        fileName=fileName.replaceAll("\\[","-");
        fileName=fileName.replaceAll("\\]","-");
        return new StreamResource(fileName, () -> {
            return new ByteArrayInputStream(downloadFile(url));
        });
    }
}
