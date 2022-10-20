package ro.bithat.dms.microservices.portal.ecitizen.website.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.bithat.dms.microservices.dmsws.payment.OrderStatusResponse;
import ro.bithat.dms.microservices.dmsws.payment.PaymentService;
import ro.bithat.dms.microservices.dmsws.ps4.paymentintegration.DmswsBankingService;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.SolicitareService;
import ro.bithat.dms.service.URLUtil;

import javax.servlet.http.HttpServletRequest;


@RestController
public class BTController {

	@Autowired
	private PaymentService paymentService;

	@GetMapping(value="/dmsws/bt/getStatusOrderApi/{orderId}", produces="application/json")
	public OrderStatusResponse getStatusOrderApi(@PathVariable String orderId) {
		return paymentService.getStatusOrderApi(orderId);

	}

	@GetMapping("/dmsws/bt/test")
	public OrderStatusResponse test() {
		return new OrderStatusResponse();

	}


}
