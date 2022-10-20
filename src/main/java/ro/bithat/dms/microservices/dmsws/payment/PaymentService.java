package ro.bithat.dms.microservices.dmsws.payment;


import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import ro.bithat.dms.microservices.dmsws.DmswsRestService;
import ro.bithat.dms.microservices.dmsws.file.BaseModel;
import ro.bithat.dms.microservices.dmsws.file.DocAttrLink;
import ro.bithat.dms.microservices.dmsws.ps4.PS4Service;
import ro.bithat.dms.microservices.dmsws.ps4.paymentintegration.imported.ListaPlatiResponse;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.SolicitareService;
import ro.bithat.dms.security.SecurityUtils;
import ro.bithat.dms.service.URLUtil;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService extends DmswsRestService{


    private static Logger logger = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    private SolicitareService solictiareService;

    @Autowired
    private PS4Service service;

    @Value("${dmsws.url}")
    private String url;

    @Value("${btepos.api.url:https://ecclients.btrl.ro:5443/payment/rest/}")
    private String apiUrl;

    @Value("${btepos.api.username:test_iPay9_api}")
    private String userName;

    @Value("${btepos.api.password:test_iPay9_api1}")
    private String password;

    @Value("${btepos.api.currency:946}")
    private String currency;

    @Value("${btepos.api.returnUrl:http://localhost:8081/PORTAL/payment-redirect.html}")
    private String returnUrl;

    @Autowired
    private URLUtil urlUtil;

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



    public BTPaymentResponse getBTPaymentUrl(@PathVariable String token, @RequestParam Long fileId, @RequestParam String amount, @RequestParam String description){

        BTPaymentRequest requestBody = new BTPaymentRequest();

        requestBody.setApiUrl(apiUrl);
        requestBody.setCurrency(currency);
        requestBody.setOrderNumber(String.valueOf(fileId));
        requestBody.setReturnUrl(returnUrl);
        requestBody.setUserName(userName);
        requestBody.setPassword(password);
        requestBody.setAmount(amount);
        requestBody.setDescription(description);

        return post(requestBody,MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON,  BTPaymentResponse.class, checkBaseModel(),
                getDmswsUrl()+"/bt/{token}/getPaymentUrl/", SecurityUtils.getToken());

    }

    public OrderStatusResponse getStatusOrderApi(String orderId){
        String redirectUri="";
        BTOrderStatusRequest request = new BTOrderStatusRequest();
        request.setApiUrl(apiUrl);
        request.setOrderId(orderId);
        request.setUserName(userName);
        request.setPassword(password);
        OrderStatusResponse order = post(request,MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON,  OrderStatusResponse.class, checkBaseModel(),
                getDmswsUrl()+"/bt/{token}/getOrderStatus/", SecurityUtils.getToken());
        if(order.getActionCode()==0){
            List<DocAttrLink> listaAtribute = service.getFileMetaData(Long.parseLong(order.getOrderNumber()));
            solictiareService.send(SecurityUtils.getToken(), new Long(order.getOrderNumber()), SecurityUtils.getEmail(), null,false, Optional.ofNullable(String.valueOf(order.getAmount())),Optional.ofNullable(String.valueOf(order.getAmount())),Optional.ofNullable(listaAtribute));
        }

        return order;

    }
}
