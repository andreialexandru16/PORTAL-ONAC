package ro.bithat.dms.microservices.dmsws.ps4.paymentintegration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ro.bithat.dms.security.SecurityUtils;
import ro.bithat.dms.service.URLUtil;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;

/**
 * Created by Silviu Iancu on 5/5/2020.
 */
@Component
public class EuPlatescRo {

	private	Logger logger = LoggerFactory.getLogger(EuPlatescRo.class);
	
	@Value("${experimental.demo.profile:true}")
	private boolean experimentalDemoProfile;
	
	@Autowired
	private URLUtil urlUtil;
	
	public String redirectToPayment(String suma,String nume,String prenume,String email,
                                           String idFisier,String idDocument,String idTipDocument){


//        String backtosite = URLEncoder.encode(VaadinClientUrlUtil.getRoutePath());
//        //        String failedurlafterprocessing = backtosite;
//
//        String succesurl = URLEncoder.encode(VaadinClientUrlUtil.getDomainPath()+ "/" + RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenMyAccountRoute.class));


        String backtosite = URLEncoder.encode(urlUtil.getPathIfVaadin()+"solicitare-revizie-finala?request="+idFisier+"&document="+idDocument+"&tipDocument="+idTipDocument);
//        String failedurlafterprocessing = backtosite;

        String succesurl = URLEncoder.encode(urlUtil.getPathIfVaadin()+"contul-meu-cererile-mele");

        String failedurl = "https://secure.euplatesc.ro/tdsprocess/replyd.php";
//        String failedurl = backtosite;

        String silenturl = URLEncoder.encode(urlUtil.getPathIfVaadin()+ "euplatesc/action");

        if (experimentalDemoProfile) {
        	logger.error("Ëxperimental euplatesc redirect used!!!!!!!!!!!!!!!!1111");
        	//silenturl = URLEncoder.encode("https://c4a0d8cc4c70.ngrok.io/euplatesc/action");
        	silenturl = URLEncoder.encode("http://localhost:8081/euplatesc/action");
        	//succesurl = URLEncoder.encode("https://c4a0d8cc4c70.ngrok.io/contul-meu-cererile-mele");
        	succesurl = URLEncoder.encode("http://localhost:8081/contul-meu-cererile-mele");
        	backtosite = "http://localhost:8081/solicitare-revizie-finala?request="+idFisier+"&document="+idDocument+"&tipDocument="+idTipDocument;
        	//backtosite = "https://c4a0d8cc4c70.ngrok.io/"+"solicitare-revizie-finala?request="+idFisier+"&document="+idDocument+"&tipDocument="+idTipDocument;
       	failedurl = "https://secure.euplatesc.ro/tdsprocess/replyd.php";
        }
        
//        if(forceHttps) {
//            succesurl = succesurl.replace("http%3A%2F%2F30", "https%3A%2F%2F30").replace("%3A80%2F", "%2F");
//            backtosite = backtosite.replace("http%3A%2F%2F30", "https%3A%2F%2F30").replace("%3A80%2F", "%2F");
//            silenturl = silenturl.replace("http%3A%2F%2F30", "https%3A%2F%2F30").replace("%3A80%2F", "%2F");
//        }

        String getMethod = "get";
        String postMethod= "post";

        String mid= getMid();
        String key="fe5bf04a231ca7795aee7ec2e43b144a2bfc8215";

        Date date = new Date();
        String dateString = new SimpleDateFormat("yyyyMMddHHmmss").format(date);
        String invoiceId = Long.toString(date.getTime());//idFisier + Long.toString(date.getTime());//stupid!
        String orderDescription ="Order " + Long.toString(date.getTime());//3
        String nonce = nonceGen(32);
        String fpHash = fpHash(key, suma, "RON", invoiceId, orderDescription, mid, dateString, nonce);

        String ep_url="https://secure.euplatesc.ro/tdsprocess/tranzactd.php?amount="+suma+"&curr="+"RON"+"&invoice_id="+invoiceId
                +"&order_desc="+orderDescription+"&merch_id="+mid+"&timestamp="+dateString+"&nonce="+nonce+"&fp_hash="+fpHash
                +"&ExtraData[backtosite]="+backtosite+"&ExtraData[successurl]="+succesurl+"&ExtraData[failedurl]="+failedurl
                +"&ExtraData[backtosite_method]="+postMethod+"&ExtraData[silenturl]="+silenturl+
//              "&ExtraData[ep_method]="+postMethod+
                "&ExtraData[idFisier]="+idFisier+
                "&ExtraData[token]="+SecurityUtils.getToken()+
                "&ExtraData[idUser]="+SecurityUtils.getUserId()+
                "&ExtraData[orderdesc]="+orderDescription+
//                "&ExtraData[succesurlafterprocessing]="+succesurlafterprocessing+
//                "&ExtraData[failedurlafterprocessing]="+failedurlafterprocessing+
                "&ExtraData[emaill]="+email+
                "&fname="+prenume+"&lname="+nume+"&email="+email;

        logger.info("Payment subbmitted to: " + ep_url);
        return ep_url;
    }

	public static String getMid() {
		return "44840991314";
	}
	
	public static String getKey() {
		return "fe5bf04a231ca7795aee7ec2e43b144a2bfc8215";
	}
	
	public static String fpHash(String key, String suma, String currency, String invoiceId, String orderId, 
			String mid, String dateString, String nonce){
		ArrayList<String> s=new ArrayList<String>();
        s.add(suma);//0
        s.add(currency);//1
        s.add(invoiceId);//2
        s.add(orderId);
        s.add(mid);//4
        s.add(dateString);//5
        s.add(nonce);
        return fp_hash(s, key);
	}

    public static String nonceGen(int len){
        String AlphaNumericString = "ABCDEF0123456789";
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            int index = (int)(AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }

    public static byte[] hex2byte(String key){
        int len = key.length();
        byte[] bkey = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            bkey[i / 2] = (byte) ((Character.digit(key.charAt(i), 16) << 4) + Character.digit(key.charAt(i+1), 16));
        }
        return bkey;
    }

    //RFC2104HMAC
    private static String fp_hash(ArrayList<String> s, String key){
        StringBuffer ret = new StringBuffer();
        Formatter formatter = new Formatter();
        String t;
        Integer l;
        for(int i = 0; i < s.size(); i++)
        {
            t = s.get(i).trim();
            if(t.length() == 0)
                ret.append("-");
            else
            {
                l = t.length();
                ret.append(l.toString());
                ret.append(t.toString());
            }
        }
        String data=ret.toString();
        try
        {
            SecretKeySpec secretKeySpec = new SecretKeySpec(hex2byte(key), "HmacMD5");
            Mac mac = Mac.getInstance("HmacMD5");
            mac.init(secretKeySpec);
            for (byte b : mac.doFinal(data.getBytes())) {
                formatter.format("%02x", b);
            }
        }
        catch(InvalidKeyException e){}
        catch (NoSuchAlgorithmException e) {}

        return formatter.toString().toUpperCase();
    }
}
