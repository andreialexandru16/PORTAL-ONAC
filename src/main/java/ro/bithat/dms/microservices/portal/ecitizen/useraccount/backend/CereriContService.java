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
import ro.bithat.dms.microservices.dmsws.file.MultiFilterPortalFileList;
import ro.bithat.dms.microservices.dmsws.flow.StandardResponse;
import ro.bithat.dms.microservices.dmsws.metadata.LovList;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.CreateTipDocFileResponse;
import ro.bithat.dms.microservices.portal.ecitizen.project.backend.bithat.UtilizatorList;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat.*;
import ro.bithat.dms.microservices.portal.ecitizen.website.models.*;
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

	@Autowired
	private EmailHelperService emailService;

	@Value("${portal.url}")
	private String portalUrl;


	public MultiFilterPortalFileList getFilesOnWorkflowByUser(String token) {
		return get(MultiFilterPortalFileList.class, checkBaseModel(), getDmswsUrl()+"/cerericont/{token}/getWorkflowsByUser",
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token);
	}
	public MultiFilterPortalFileList getInvoicesByUser(String token) {
		return get(MultiFilterPortalFileList.class, checkBaseModel(), getDmswsUrl()+"/cerericont/{token}/getInvoicesByUser",
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token);
	}


	public MultiFilterPortalFileList getLimitedFilesOnWorkflowByUser(String token, String nrRows) {
		return get(MultiFilterPortalFileList.class, checkBaseModel(), getDmswsUrl()+"/cerericont/{token}/getWorkflowsByUser?nrRows="+nrRows,
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token);
	}
	public MultiFilterPortalFileList getLimitedInvoicesByUser(String token, String nrRows) {
		return get(MultiFilterPortalFileList.class, checkBaseModel(), getDmswsUrl()+"/cerericont/{token}/getInvoicesByUser?nrRows="+nrRows,
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token);
	}



	public Utilizator inactivareCont(String token, String id) {
		return post(null,
				Utilizator.class,
				checkBaseModelWithExtendedInfo(),
				getDmswsUrl()+"/cerericont/{token}/inactivareCont/{id}/",token,id);

	}
	public Utilizator activareCont(String token, String id) {
		return post(null,
				Utilizator.class,
				checkBaseModelWithExtendedInfo(),
				getDmswsUrl()+"/cerericont/{token}/activareCont/{id}/",token,id);

	}

	public StandardResponse validareEmail(String token, Long idFisier) {
		return post(null, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, StandardResponse.class, checkStandardResponse(),
				getDmswsUrl() + "/cerericont/{token}/validareEmail/{idFisier}", token, idFisier);
	}


	public BaseModel checkFileOnFlow(String token, String idFisier) {
		String url=getDmswsUrl()+"/cerericont/{token}/checkFileOnFlow?&idFisier="+idFisier;
		return get(BaseModel.class, checkBaseModel(),url ,
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token);
	}

	public TipOrdonatorList getListOperatorTipCredite(@PathVariable String token) {
		return get(TipOrdonatorList.class, checkBaseModel(), getDmswsUrl()+"/cerericont/{token}/getListOperatorTipCredite",
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token);
	}
	public InstiutiiList getListDenumireCif(@PathVariable String token) {
		return get(InstiutiiList.class, checkBaseModel(), getDmswsUrl()+"/cerericont/{token}/getListDenumireCif",
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token);
	}
	public UtilizatorDocument getDownloadSablon(@PathVariable String token) {
		return get(UtilizatorDocument.class, checkBaseModel(), getDmswsUrl()+"/cerericont/{token}/getDownloadSablon",
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token);
	}

	public UtilizatorList getSubconturi(String token) {
		return get(UtilizatorList.class, checkBaseModel(), getDmswsUrl()+"/cerericont/{token}/getSubconturi",
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

	public Integer addContact(String token,UtilizatorAcOe utilizatorContact,
									 String mdFilename, byte[] mdFileData) throws ServerWebInputException {
		logger.info("adding user {}", utilizatorContact.getEmail());
		if ( mdFilename == null){
			logger.info("canceled {}", utilizatorContact.getEmail());
			throw new ServerWebInputException("Incarcati mandat!");
		}
		Integer returnResult = null;

		UtilizatorAcOeResponse result = post(utilizatorContact, UtilizatorAcOeResponse.class, checkBaseModelWithExtendedInfo(), getDmswsUrl()+"/cerericont/{token}/addUtilizatorCt", token);

		returnResult = result.getVerificatNrConturi();

		if (mdFilename != null && !mdFilename.isEmpty() && returnResult == 0) {
			CreateTipDocFileResponse biResp = fileService.uploadFisierTipDocId(SecurityUtils.getToken(), result.getIdMandat(), Long.parseLong(String.valueOf(result.getIdUtilizator())),
					mdFilename, mdFilename, mdFileData, Optional.empty());
		}
		if(returnResult == 0) {
			HashMap<String, String> replaceMap = new HashMap<>();
			replaceMap.put("{PORTAL_URL}", portalUrl);

			emailService.sendEmailFromTemplateReplaceAll2(replaceMap
					, utilizatorContact.getEmail(), "Confirmare email!",
					"static/website/creare-cont-utilizator-contact.html"
			);

			logger.info("contact added with succes", utilizatorContact.getEmail());
		}

		return returnResult;

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
		String emailInstitutie = result.getEmailInstitutie();
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
		HashMap<String,String> replaceMap = new HashMap<>();
		replaceMap.put("{PORTAL_URL}",portalUrl);
		replaceMap.put("{ID_FISIER_CONT}",idFisierDummy.toString());

		emailService.sendEmailFromTemplateReplaceAll2(replaceMap
				, emailInstitutie,	"Confirmare email!",
				"static/website/confirmare-email.html"
		);

		logger.info("user added with succes", utilizatorAcOe.getEmail_rp());
		return idCerere;
	}
	public RelatiiTertList getListaRelatii(String token,String idCerere) {
		String url =  getDmswsUrl()+"/cerericont/{token}/getListaRelatii/{idCerere}";

		return get(RelatiiTertList.class, checkBaseModel(),url,
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token,idCerere);
	}
	public BaseModel adaugaRelatie(String token, RelatiiTert relatie) {

		String url =  getDmswsUrl()+"/cerericont/{token}/adaugaRelatie/";


		return post(relatie, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON,
				BaseModel.class, checkBaseModel(),
				url, token);
	}
	public BaseModel stergeRelatie(String token, String id) {

		String url =  getDmswsUrl()+"/cerericont/{token}/stergeRelatie/{id}";


		return get(BaseModel.class, checkBaseModel(),url,
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, id);
	}

	public RelatiiTertList getRelatieInfo(String token, String idRelatie) {
		String url =  getDmswsUrl()+"/cerericont/{token}/getRelatieInfo/{idRelatie}";

		return get(RelatiiTertList.class, checkBaseModel(),url,
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, idRelatie);
	}
	public BaseModel updateInfoRelatie(String token, RelatiiTert relatiiTert) {

		String url =  getDmswsUrl()+"/cerericont/{token}/updateInfoRelatie";


		return post(relatiiTert, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON,
				BaseModel.class, checkBaseModel(),
				url, token);
	}

	public BaseModel trimSolicitare(String token, RelatiiTert relatiiTert) {

		String url =  getDmswsUrl()+"/cerericont/{token}/trimSolicitare/";


		return post(relatiiTert,   MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON,
				BaseModel.class, checkBaseModel(),
				url, token);
	}

}
