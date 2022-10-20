package ro.bithat.dms.microservices.portal.ecitizen.useraccount.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerErrorException;
import org.springframework.web.server.ServerWebInputException;
import ro.bithat.dms.microservices.dmsws.colaboration.AmenziResponse;
import ro.bithat.dms.microservices.dmsws.colaboration.Utilizator;
import ro.bithat.dms.microservices.dmsws.file.BaseModel;
import ro.bithat.dms.microservices.dmsws.file.WsAndUserInfo;
import ro.bithat.dms.microservices.dmsws.flow.StandardResponse;
import ro.bithat.dms.microservices.dmsws.ps4.PS4Service;
import ro.bithat.dms.microservices.portal.ecitizen.project.backend.bithat.UtilizatorList;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.DmswsNotifAmenziService;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.DmswsUtilizatorService;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat.ChangePassReq;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat.PersoanaFizicaJuridica;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat.PsihologList;
import ro.bithat.dms.security.SecurityUtils;
import ro.bithat.dms.security.UserWithUserToken;

import java.io.IOException;

@RestController
public class DmswsNotifAmenzi {

	@Autowired
	private DmswsNotifAmenziService service;




	@Value("${notif.amenzi:false}")
	private boolean notifAmenzi;



	@GetMapping("dmsws/notifAmenzi/getNotifAmenzi")
	public boolean getNotifAmenzi()  {
		return notifAmenzi;
	}

	@GetMapping("dmsws/notifAmenzi/getAmenzi")
	public AmenziResponse getAmenzi()  {
		return service.getAmenzi(SecurityUtils.getToken());
	}

}
