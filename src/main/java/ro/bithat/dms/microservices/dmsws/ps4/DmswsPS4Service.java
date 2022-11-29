package ro.bithat.dms.microservices.dmsws.ps4;

import com.fasterxml.jackson.databind.ser.Serializers;
import feign.Param;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import ro.bithat.dms.microservices.dmsws.DmswsRestService;
import ro.bithat.dms.microservices.dmsws.file.*;
import ro.bithat.dms.microservices.dmsws.infocet.InfogetAdaugaDocumentResponse;
import ro.bithat.dms.microservices.dmsws.metadata.AttributeLinkList;
import ro.bithat.dms.microservices.dmsws.ps4.detaliiserviciu.ElectronicService;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.*;
import ro.bithat.dms.microservices.dmsws.ps4.eraintegration.ERARegistrationNumberResponse;
import ro.bithat.dms.microservices.portal.ecitizen.website.models.PpnInfoRegistraturaReq;
import ro.bithat.dms.microservices.portal.ecitizen.website.models.PpnInfoRegistraturaResp;
import ro.bithat.dms.microservices.portal.ecitizen.website.models.RegistraturaComplete;
import ro.bithat.dms.security.SecurityUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.Optional;

@Service
public class DmswsPS4Service extends DmswsRestService{

	@Value("${zuulproxy.url:}")
	private String zuulproxyUrl;

	@Value("${zuulproxy.upload.url:}")
	private String zuulproxyUploadUrl;

	@Value("${dmsws.url}")
	private String dmswsUrl;

	@Value("${portal.url}")
	private String portalUrl;

	@Override
	public String getDmswsUrl() {
		return dmswsUrl;
	}

	public void setDmswsUrl(String dmswsUrl) {
		this.dmswsUrl = dmswsUrl;
	}

	public String getPortalUrl() {
		return portalUrl;
	}

	public void setPortalUrl(String portalUrl) {
		this.portalUrl = portalUrl;
	}

	//@Cacheable(value = "dmswsPS4Service_tipDocumentList", key = "1")
    public TipDocumentList getTipDocumentList(String token) {
    	return get(TipDocumentList.class, checkBaseModel(), getDmswsUrl()+"/registratura_portal/{token}/getListaTipDoc?serviciuElectronic=1",
    			MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token);
	}
	public TipDocument getDocumentTypeById(Integer documentId) {
		return get(TipDocument.class, checkBaseModel(), getDmswsUrl()+"/document/{token}/getClasaDocumentById/{documentId}",
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, SecurityUtils.getToken(), documentId);
	}
	public TipDocument getClasaDocumentAndDocumentListById(Integer documentId) {
		return get(TipDocument.class, checkBaseModel(), getDmswsUrl()+"/document/{token}/getClasaDocumentAndDocumentListById/{documentId}",
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, SecurityUtils.getToken(), documentId);
	}
	//@Cacheable(value = "dmswsPS4Service_tipDocumentList", key = "1")
	public Document getDocumentById(Integer documentId) {
		return get(Document.class, checkBaseModel(), getDmswsUrl()+"/document/{token}/getDocumentById/{documentId}",
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, SecurityUtils.getToken(), documentId);
	}
    public ERARegistrationNumberResponse getEraRegistrationNumber(String token, Integer fileId) {
    	return get(ERARegistrationNumberResponse.class, checkBaseModel(), getDmswsUrl()+"/ps4/{token}/setERARegistrationNumber/{fileId}",
				//"/?" +
						//"entitate={entitate}" +
				//"&tipDocument={tipDocument}"+
				//"tipRegistru={tipRegistru}",
    			MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token,fileId
				/*
				"SERVICIUL RELATII CU PUBLICUL",
				"CU - Cerere Certificat de urbanism",*/
				//"intrare"
				);
    }
	public PpnInfoRegistraturaResp setRegistrationNumberNeamt(String token, Integer fileId, PpnInfoRegistraturaReq ppnInfoRegistraturaReq) {

		return post(ppnInfoRegistraturaReq, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON,
				PpnInfoRegistraturaResp.class, checkBaseModel(),
				getDmswsUrl()+"/ppn_registratura/{token}/getNumarInregistrare/{fileId}", token, fileId);

	}

	public InfogetAdaugaDocumentResponse adaugaDocument(String token, Integer fileId) {
		return get(InfogetAdaugaDocumentResponse.class, checkBaseModel(), getDmswsUrl()+"/infoget/{token}/adaugaDocument/{fileId}",

				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token,fileId

		);
	}
    	
   //@Cacheable(value = "detaliiServiciu", key = "#docTypeId")
	public ElectronicService getDetaliiServiciu(String token, Long docTypeId) {
    	return get(ElectronicService.class, checkBaseModel(), getDmswsUrl()+"/achizitii/{token}/getElectronicServiceByDocTypeId/{docTypeId}", 
    			MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, docTypeId);
	}

	public AttributeLinkList getDetaliiServicii(String token, Long docTypeId) {
		return get(AttributeLinkList.class, checkBaseModel(), getDmswsUrl()+"/portal_e/{token}/getElectronicServiceByDocTypeId/{docTypeId}",
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, docTypeId);
	}
	
    //@Cacheable(value = "documenteObligatoriiServiciu", key = "#docTypeId", condition="#parentFileId.isPresent()==false")
	public DocObligatoriiResp getDocumenteObligatoriiServiciu(String token, Long docTypeId, Optional<Integer> parentFileId) {
    	String url = parentFileId.isPresent() ?
				getDmswsUrl()+"/document/{token}/getDocObligatoriiByDocTypeId/{docTypeId}" + "?parentFileId="+parentFileId.get() :
				getDmswsUrl()+"/document/{token}/getDocObligatoriiByDocTypeId/{docTypeId}";
    	return get(DocObligatoriiResp.class, checkBaseModel(), url,
    			MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, docTypeId);
	}

	public DocObligatoriiResp getDocumenteLipsaServiciu(String token, Long docTypeId, Optional<Integer> parentFileId) {
		String url = getDmswsUrl()+"/document/{token}/getDocLipsaByFileId/{parentFileId}";
		return get(DocObligatoriiResp.class, checkBaseModel(), url,
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, parentFileId.get());
	}
	
	public DocObligatoriiResp getDocumenteObligatoriiServiciuDePlatit(String token, Long docTypeId, Optional<Integer> parentFileId) {
		String url = getDmswsUrl()+"/document/{token}/getDocObligatoriiByDocTypeIdToPay/{docTypeId}" + "?parentFileId="+parentFileId.get();
		return get(DocObligatoriiResp.class, checkBaseModel(), url,
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, docTypeId);
	}
	
	public String getDocumenteSablonLinkDownload(String token, String fileId) {
		return get(BaseModel.class, checkBaseModel(), getDmswsUrl()+"/file/{token}/getFileExtendedInfo/{idFisier}/{type}", 
    			MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, fileId, "FILE").getExtendedInfo();
	}
	
	public PortalFileList getDocRaspunsByFileId(String token, Integer fileId) {
    	return get(PortalFileList.class, checkBaseModel(), getDmswsUrl()+"/document/{token}/getDocRaspunsByFileId/{fileId}",
    			MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, fileId);
	}
	
	//@Cacheable(value = "acteNormativeByDocTypeId", key = "#docTypeId")
	public ActNormativList getActeNormativeByDocTypeId(String token, Long docTypeId) {
    	return get(ActNormativList.class, checkBaseModel(), getDmswsUrl()+"/document/{token}/getActeNormativeByDocTypeId/{docTypeId}", 
    			MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, docTypeId);
	}
	
	public MultiFilterPortalFileList getFilesOnWorkflowByUser(String token) {
		return get(MultiFilterPortalFileList.class, checkBaseModel(), getDmswsUrl()+"/file/{token}/getWorkflowsByUser",
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token);
	}
	
	public MultiFilterPortalFileList getLimitedFilesOnWorkflowByUser(String token,String nrRows) {
		return get(MultiFilterPortalFileList.class, checkBaseModel(), getDmswsUrl()+"/file/{token}/getWorkflowsByUser?nrRows="+nrRows,
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token);
	}

	public MultiFilterPortalFileList getDocumentsByUser(String token) {
		return get(MultiFilterPortalFileList.class, checkBaseModel(), getDmswsUrl()+"/file/{token}/getFilesByUser",
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token);
	}
	
	public MultiFilterPortalFileList getLimitedDocumentsByUser(String token,String nrRows) {
		return get(MultiFilterPortalFileList.class, checkBaseModel(), getDmswsUrl()+"/file/{token}/getFilesByUser?nrRows="+nrRows,
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token);
	}
	
	public MultiFilterPortalFileList getPortalFileByIdDocType(String token, Integer docTypeId) {
		return get(MultiFilterPortalFileList.class, checkBaseModel(), getDmswsUrl()+"/file/{token}/getFilesByTipDoc/{docTypeId}",
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token,docTypeId);
	}
	public MultiFilterPortalFileList getPortalFileByIdDocTypeFiltered(String token, Integer docTypeId, String searchStr) {
		return get(MultiFilterPortalFileList.class, checkBaseModel(), getDmswsUrl()+"/file/{token}/getFilesByTipDoc/{docTypeId}?searchStr={searchStr}",
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token,docTypeId,searchStr);
	}
	public PortalFile getPortalFileByFileId(String token, Integer fileId) {
		return get(PortalFile.class, checkBaseModel(), getDmswsUrl()+"/file/{token}/getWorkflowByFileId/{fileId}",
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token,fileId);
	}
	public ParamCuIstoric getSysParam(String token, String cod) {
		return get(ParamCuIstoric.class, checkBaseModel(), getDmswsUrl()+"/portal_e/{token}/getSysParam/{cod}",
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token,cod);
	}
	public RegistraturaComplete getInregistrareByIdFisier(String token, String idFisier) {
		return get(RegistraturaComplete.class, checkBaseModel(), getDmswsUrl()+"/registratura_portal/{token}/getInregistrareByIdFisier/{idFisier}",
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token,idFisier);
	}

	public DocObligatoriiResp getCriteriiScrisoareLipsuri(String token, Optional<Integer> fileId, long documentId, Optional<String>  afiseazaTipDocRaspunsScrisoare) {
		String url =   getDmswsUrl()+"/document/{token}/getCriteriiScrisoareLipsuriPortal/{documentId}/?&scrisoareId="+fileId.get() ;
		if(afiseazaTipDocRaspunsScrisoare.isPresent()){
			url+="&afiseazaTipDocRaspunsScrisoare="+afiseazaTipDocRaspunsScrisoare.get();
		}

		return get(DocObligatoriiResp.class, checkBaseModel(), url,
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token,documentId);
	}
	public TipDocumentList getTipDocumentListByTag(String token,String tagStr) {
		return get(TipDocumentList.class, checkBaseModel(), getDmswsUrl()+"/document/{token}/getListaClasaDocByTag?serviciuElectronic=1&tagStr={tagStr}",
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token,tagStr);
	}


	public BaseModel getSqlResult(String valoareImplicita) {
		String valoareImplicitaB64 = Base64.getEncoder().encodeToString(valoareImplicita.getBytes());
		try {
			valoareImplicitaB64 = URLEncoder.encode(valoareImplicitaB64, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return get(BaseModel.class, checkBaseModel(), getDmswsUrl()+"/attribute/{token}/getSqlResult/?sql={valoareImplicita}",
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, SecurityUtils.getToken(),valoareImplicitaB64);
	}

	public DmsTert getTertDataByUserId(String token) {
		return get(DmsTert.class, checkBaseModel(), getDmswsUrl()+"/anre_contributii/{token}/getTertDataByUserId",
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token);
	}

	public String getZuulproxyUrl() {
		return zuulproxyUrl;
	}

	public void setZuulproxyUrl(String zuulproxyUrl) {
		this.zuulproxyUrl = zuulproxyUrl;
	}

	public String getZuulproxyUploadUrl() {
		return zuulproxyUploadUrl;
	}

	public void setZuulproxyUploadUrl(String zuulproxyUploadUrl) {
		this.zuulproxyUploadUrl = zuulproxyUploadUrl;
	}
}
