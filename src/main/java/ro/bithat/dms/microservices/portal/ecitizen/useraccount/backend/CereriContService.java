package ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ServerErrorException;
import org.springframework.web.server.ServerWebInputException;
import ro.bithat.dms.microservices.dmsws.DmswsRestService;
import ro.bithat.dms.microservices.dmsws.colaboration.Utilizator;
import ro.bithat.dms.microservices.dmsws.file.BaseModel;
import ro.bithat.dms.microservices.dmsws.file.DmswsFileService;
import ro.bithat.dms.microservices.dmsws.metadata.LovList;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.CreateTipDocFileResponse;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat.*;
import ro.bithat.dms.security.SecurityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

@Service
public class CereriContService extends DmswsRestService{

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private DmswsFileService fileService;

	@Value("${dmsws.anonymous.userid}")
	private Long idUtilizatorAnonimus;



	public TipOrdonatorList getListOperatorTipCredite(@PathVariable String token) {
		return get(TipOrdonatorList.class, checkBaseModel(), getDmswsUrl()+"/cerericont/{token}/getListOperatorTipCredite",
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token);
	}
	public InstiutiiList getListDenumireCif(@PathVariable String token) {
		return get(InstiutiiList.class, checkBaseModel(), getDmswsUrl()+"/cerericont/{token}/getListDenumireCif",
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token);
	}
	public JudetList getListJudete(@PathVariable String token) {
		return get(JudetList.class, checkBaseModel(), getDmswsUrl()+"/cerericont/{token}/getListJudete",
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token);
	}
	public LocalitateList getListLocalitate(@PathVariable String token, @PathVariable String idJudet) {
		return get(LocalitateList.class, checkBaseModel(), getDmswsUrl()+"/cerericont/{token}/getListLocalitate/{idJudet}",
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, idJudet);
	}


	public Integer addUtilizatorAcOe(String token,UtilizatorAcOe utilizatorAcOe,
										  String mdFilename, byte[] mdFileData, byte[] pdfData, String urlPath) throws ServerWebInputException {
		logger.info("adding user {}", utilizatorAcOe.getEmail_rp());
		if ( mdFilename == null){
			logger.info("canceled {}", utilizatorAcOe.getEmail_rp());
			throw new ServerWebInputException("Incarcati mandat!");
		}

		UtilizatorAcOeResponse result = post(utilizatorAcOe, UtilizatorAcOeResponse.class, checkBaseModelWithExtendedInfo(), getDmswsUrl()+"/cerericont/{token}/addUtilizatorAcOe", token);
		Integer idFisierDummy = result.getIdFisier();
		Integer idCerere =  result.getIdCerere();
		try {
			fileService.uploadToReplaceExistingFile2(SecurityUtils.getToken(), new Long(idFisierDummy), idUtilizatorAnonimus, "cont.pdf", pdfData);
		} catch (ServerErrorException | IOException e) {
			logger.error(e.getMessage(), e);
			throw new ServerWebInputException(e.getMessage());
		}
		if (mdFilename != null && !mdFilename.isEmpty()) {
			CreateTipDocFileResponse biResp = fileService.uploadFisierTipDocId(SecurityUtils.getToken(), result.getIdMandat(), idUtilizatorAnonimus,
					mdFilename, mdFilename, mdFileData, Optional.empty());
			fileService.attachFile(SecurityUtils.getToken(), new Integer(biResp.getFileId()), idFisierDummy);
		}

		logger.info("user added with succes", utilizatorAcOe.getEmail_rp());
		return idCerere;
	}


//
//
//	public void addPersoanaFizicaJuridica(String token,PersoanaFizicaJuridica persoanaFizicaJuridica,
//										   String biFilename, byte[] biFileData, String cuiFilename, byte[] cuiFileData,String imputernicireFilename, byte[] imputernicireFileData, byte[] pdfData, String urlPath) throws ServerWebInputException {
//		logger.info("adding user {}", persoanaFizicaJuridica.getEmail());
//		/*if ("1".equals(persoanaFizicaJuridica.getEstePersoanaFizica()) && biFilename == null){
//			logger.info("canceled {}", persoanaFizicaJuridica.getEmail());
//			throw new ServerWebInputException("Incarcati copie act identitate");
//		}*/
//		if ("0".equals(persoanaFizicaJuridica.getEstePersoanaFizica()) && cuiFilename == null){
//			logger.info("canceled {}", persoanaFizicaJuridica.getEmail());
//			throw new ServerWebInputException("Incarcati copie CUI societate");
//		}
//		PersoanaFizicaJuridicaResponse result = post(persoanaFizicaJuridica, PersoanaFizicaJuridicaResponse.class, checkBaseModelWithExtendedInfo(), getDmswsUrl()+"/utilizator/{token}/addPersoanaFizicaJuridica", token);
//		Integer idFisierDummy = result.getIdFisier();
//		Integer idUtilizator = result.getIdUtilizator();
//		try {
//			fileService.uploadToReplaceExistingFile2(SecurityUtils.getToken(), new Long(idFisierDummy), new Long(idUtilizator), "cont.pdf", pdfData);
//		} catch (ServerErrorException | IOException e) {
//			logger.error(e.getMessage(), e);
//			throw new ServerWebInputException(e.getMessage());
//		}
//		if (biFilename != null && !biFilename.isEmpty()) {
//			CreateTipDocFileResponse biResp = fileService.uploadFisierTipDocId(SecurityUtils.getToken(), result.getIdDocumentCI(), new Long(result.getIdUtilizator()),
//					biFilename, biFilename, biFileData,Optional.empty());
//			fileService.attachFile(SecurityUtils.getToken(), new Integer(biResp.getFileId()), idFisierDummy);
//		}
//		if (cuiFilename != null && !cuiFilename.isEmpty()) {
//			CreateTipDocFileResponse cuiResp = fileService.uploadFisierTipDocId(SecurityUtils.getToken(), result.getIdDocumentCUI(), new Long(result.getIdUtilizator()),
//					cuiFilename, cuiFilename, cuiFileData,Optional.empty());
//			fileService.attachFile(SecurityUtils.getToken(), new Integer(cuiResp.getFileId()), idFisierDummy);
//		}
//		if (imputernicireFilename != null && !imputernicireFilename.isEmpty()) {
//			CreateTipDocFileResponse imputernicireResp = fileService.uploadFisierTipDocId(SecurityUtils.getToken(), result.getIdDocumentImputernicire(), new Long(result.getIdUtilizator()),
//					imputernicireFilename, imputernicireFilename, imputernicireFileData, Optional.empty());
//			fileService.attachFile(SecurityUtils.getToken(), new Integer(imputernicireResp.getFileId()), idFisierDummy);
//		}
//
//		//Transmiterea pe flux trebuie sa se faca dupa validarea mail-ului
////		flowService.sendFluxRequestByIdFisier(SecurityUtils.getToken(), new Long(idFisierDummy));
//
//		HashMap<String,String> replaceMap = new HashMap<>();
//		replaceMap.put("{PORTAL_URL}",portalUrl);
//		replaceMap.put("{ID_FISIER_CONT}",idFisierDummy.toString());
//		emailService.sendEmailFromTemplateReplaceAll2(replaceMap
//				, persoanaFizicaJuridica.getEmail(),	"Confirmare email!",
//				"static/website/confirmare-email.html"
//				);
//
//		logger.info("user added with succes", persoanaFizicaJuridica.getEmail());
//	}
//
//	public BaseModel addPsihologExtension(String token, Psiholog psiholog){
//		return post(psiholog, MediaType.APPLICATION_XML, MediaType.APPLICATION_XML, BaseModel.class, checkBaseModel(),
//				getDmswsUrl()+"/utilizator/{token}/addPsihologExtension", token);
//	}
}
