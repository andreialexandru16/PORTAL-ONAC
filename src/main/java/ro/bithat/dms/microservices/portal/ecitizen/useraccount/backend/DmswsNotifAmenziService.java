package ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import ro.bithat.dms.microservices.dmsws.DmswsRestService;
import ro.bithat.dms.microservices.dmsws.colaboration.AmenziResponse;
import ro.bithat.dms.microservices.dmsws.colaboration.Utilizator;
import ro.bithat.dms.microservices.dmsws.file.BaseModel;
import ro.bithat.dms.microservices.dmsws.flow.StandardResponse;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.LinkUtilList;
import ro.bithat.dms.microservices.portal.ecitizen.project.backend.bithat.UtilizatorList;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat.ChangePassReq;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat.ContCurentPortalE;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat.PersoanaFizicaJuridica;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat.PsihologList;

@Service
public class DmswsNotifAmenziService extends DmswsRestService{


	private static Logger logger = LoggerFactory.getLogger(DmswsNotifAmenziService.class);



	public AmenziResponse getAmenzi(String token) {
		StringBuilder sb = new StringBuilder();
		sb.append(getDmswsUrl()).append("/taxe_pn/{token}/getAmenzi");

		return get(AmenziResponse.class, checkBaseModel(), sb.toString(),
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token);
	}



}
