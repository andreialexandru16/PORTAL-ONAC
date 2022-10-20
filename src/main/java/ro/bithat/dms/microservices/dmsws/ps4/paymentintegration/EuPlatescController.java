package ro.bithat.dms.microservices.dmsws.ps4.paymentintegration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ro.bithat.dms.microservices.dmsws.ps4.paymentintegration.imported.SalveazaPlataRequest;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.SolicitareService;
import ro.bithat.dms.service.URLUtil;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Optional;


@RestController
public class EuPlatescController {
	
	@Autowired
	private URLUtil urlUtil;
	
	@Autowired
	private DmswsBankingService service;
	
	@Autowired
	private SolicitareService solictiareService;
	
	private static Logger logger = LoggerFactory.getLogger(EuPlatescController.class);

	@PostMapping("/euplatesc/action")
	public ResponseEntity actionResponsePost(@RequestBody String data, HttpServletRequest request) {
		return actionResponse(data, request);
	}

	public ResponseEntity actionResponse(@RequestBody String data, HttpServletRequest request) {

		logger.info("Registering payment for " + request.getParameterMap().toString());
		
		String curr = request.getParameter("curr");
		String amount = request.getParameter("amount");
		String invoiceId = request.getParameter("invoice_id");
		String epId = request.getParameter("ep_id");
		String merchId = request.getParameter("merch_id");
		String action = request.getParameter("action");
		String message = request.getParameter("message");
		String approval = request.getParameter("approval");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String fp_hash = request.getParameter("fp_hash");
		String ep_id = request.getParameter("ep_id");
		String token = request.getParameter("ExtraData[token]");
		String fileId = request.getParameter("ExtraData[idFisier]");
		String idUser = request.getParameter("ExtraData[idUser]");
		String order_desc = request.getParameter("ExtraData[orderdesc]");
		String email = request.getParameter("ExtraData[emaill]");
//		String succesurlafterprocessing = request.getParameter("ExtraData[succesurlafterprocessing]");
//		String failedurlafterprocessing = request.getParameter("ExtraData[failedurlafterprocessing]");


//		String fpHashCurrent = EuPlatescRo.fpHash(EuPlatescRo.getKey(), amount, curr, invoiceId, order_desc, merchId, timestamp, nonce);
//		
//		if (!fp_hash.equals(fpHashCurrent)) {
//			logger.error("SECURITY ISSUE: wrong identity of caller " + request.getParameterMap().toString());
//			throw new IllegalArgumentException();
//		}
		
		//String calc_hash = euPlatescRo.fp_hash(response, key);
		SalveazaPlataRequest salveazaRequest = new SalveazaPlataRequest();
//		YYYYMMDDHHmmSS
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		/*
		 * try { salveazaRequest.setData_plata(sdf.parse(timestamp)); } catch
		 * (ParseException e) { e.printStackTrace(); salveazaRequest.setData_plata(new
		 * Date()); }//timestamp
		 */		
		salveazaRequest.setErr_code(message);
		salveazaRequest.setErr_value(message);
		salveazaRequest.setHash(fp_hash);
		salveazaRequest.setId_fisier(new Integer(fileId));
		salveazaRequest.setSuma(new Double(amount));
		salveazaRequest.setStatus(action.equals("0")?1:2);
		salveazaRequest.setReferinta_externa(ep_id);
		salveazaRequest.setInfo1(approval);
		salveazaRequest.setInfo2(fp_hash);
 		salveazaRequest.setInfo3("");
 		
 		if (action.equals("0")) {
			//a platit cu succes
			logger.info("The payment was successful for " + salveazaRequest.toString());
		} else {
			//failed
			logger.info("The payment failed for " + salveazaRequest.toString());
		}
 		
 		logger.info("Registering payment for request " + salveazaRequest.toString());
 		
 		try {
			service.salveazaPlata(token, salveazaRequest);
			if (action.equals("0")) {
				//a platit cu succes
				logger.info("successful registered successful payment for request and sending to flux " + salveazaRequest.toString());
				solictiareService.send(token, new Long(fileId), email, urlUtil.getPath(request),true, Optional.ofNullable(amount),Optional.ofNullable(amount),Optional.empty());
	//			return ResponseEntity.status(HttpStatus.MOVED_TEMPORARILY).header(HttpHeaders.LOCATION, succesurlafterprocessing).build();
				return ResponseEntity.status(HttpStatus.OK).build();
			} else {
				//failed
				logger.info("successful registered failed payment for request " + salveazaRequest.toString());
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	//			return ResponseEntity.status(HttpStatus.MOVED_TEMPORARILY).header(HttpHeaders.LOCATION, failedurlafterprocessing).build();
			}
 		}catch(Exception e) {
 			logger.info("failed to registered payment for request " + salveazaRequest.toString());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
}
