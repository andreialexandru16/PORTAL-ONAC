package ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebInputException;
import ro.bithat.dms.microservices.dmsws.DmswsRestService;
import ro.bithat.dms.microservices.dmsws.file.DmswsFileService;
import ro.bithat.dms.microservices.dmsws.file.DocAttrLink;
import ro.bithat.dms.microservices.dmsws.file.DocAttrLinkList;
import ro.bithat.dms.microservices.dmsws.flow.DmswsFlowService;
import ro.bithat.dms.microservices.dmsws.metadata.AttributeLink;
import ro.bithat.dms.microservices.dmsws.ps4.DmswsPS4Service;
import ro.bithat.dms.microservices.portal.ecitizen.website.models.PpnInfoRegistraturaReq;
import ro.bithat.dms.microservices.portal.ecitizen.website.models.PpnInfoRegistraturaResp;
import ro.bithat.dms.microservices.portal.ecitizen.website.models.RegistraturaComplete;
import ro.bithat.dms.security.SecurityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SolicitareService extends DmswsRestService{

	@Value("${neamt.registration.number.active:false}")
	private Boolean getRegistrationNumberNeamtActive;

	@Value("${ps4.ecitizen.registration.number.era.active}")
	private Boolean getRegistrationNumberFromERAActive;

	@Value("${infocet.registration.active}")
	private Boolean infocetActive;

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private DmswsFlowService flowService;
	
	@Autowired
	private EmailHelperService emailService;
	
	@Autowired
	private DmswsPS4Service dmswsPs4Service;


	@Autowired
	private DmswsFileService dmswsFileService;
	
	public void send(String token, Long idFisierDummy, String emailTo, String urlPath, Boolean setERARegistrationNumber, Optional<String> sumaDePlata, Optional<String> sumaPlatita, Optional<List<DocAttrLink>> listaAtribute) throws ServerWebInputException {
		logger.info("solicitare sending for ", emailTo);
		if(infocetActive){
			dmswsPs4Service.adaugaDocument(SecurityUtils.getToken(), idFisierDummy.intValue());
		}
		if(getRegistrationNumberNeamtActive ){
			PpnInfoRegistraturaReq ppnInfoRegistraturaReq= new PpnInfoRegistraturaReq();

			Optional<String> valNumeAdresant= Optional.ofNullable("USER|ID_TERT"); //valoare efectiva se va citi in DMSWS
			Optional<String> valJudetAdresant= Optional.ofNullable("USER|ID_JUDET"); //valoare efectiva se va citi in DMSWS
			//Optional<String> valOrasAdresant= Optional.ofNullable("USER|ID_LOCALITATE"); //valoare efectiva se va citi in DMSWS
			//Optional<DocAttrLink> valNumeAdresant= listaAtribute.get().stream().filter( a -> a.getAttributeCode().equals("USER|ID_TERT")).findFirst();
			//Optional<DocAttrLink> valJudetAdresant=listaAtribute.get().stream().filter( a -> a.getAttributeCode().equals("USER|ID_JUDET")).findFirst();
			Optional<DocAttrLink> valOrasAdresant=listaAtribute.get().stream().filter( a -> a.getAttributeCode().equals("USER|ID_LOCALITATE")).findFirst();

			Optional<DocAttrLink> valAdresaAdresant=listaAtribute.get().stream().filter( a -> a.getAttributeCode().equals("USER|STRADA")).findFirst();
			Optional<DocAttrLink> valCnpcuiAdresant=listaAtribute.get().stream().filter( a -> a.getAttributeCode().equals("CNP_CUI_SUBSEMNAT")).findFirst();
			Optional<DocAttrLink> valEmailAdresant=listaAtribute.get().stream().filter( a -> a.getAttributeCode().equals("USER|EMAIL")).findFirst();
			Optional<DocAttrLink> valNumarTelefonAdresant=listaAtribute.get().stream().filter( a -> a.getAttributeCode().equals("USER|TELEFON")).findFirst();

			ppnInfoRegistraturaReq.setNumeAdresant(valNumeAdresant.isPresent()?valNumeAdresant.get():"-");
			ppnInfoRegistraturaReq.setAdresaAdresant(valAdresaAdresant.isPresent()?valAdresaAdresant.get().getValue():"-");
			ppnInfoRegistraturaReq.setCnpcuiAdresant(valCnpcuiAdresant.isPresent()?valCnpcuiAdresant.get().getValue():"-");
			ppnInfoRegistraturaReq.setCuprins("test.dms");
			ppnInfoRegistraturaReq.setEmailAdresant(valEmailAdresant.isPresent()?valEmailAdresant.get().getValue():"-");
			ppnInfoRegistraturaReq.setJudetAdresant(valJudetAdresant.isPresent()?valJudetAdresant.get():"-");
			ppnInfoRegistraturaReq.setNumarTelefonAdresant(valNumarTelefonAdresant.isPresent()?valNumarTelefonAdresant.get().getValue():"-");
			ppnInfoRegistraturaReq.setOrasAdresant(valOrasAdresant.isPresent()?valOrasAdresant.get().getValue():"-");
			PpnInfoRegistraturaResp resp = dmswsPs4Service.setRegistrationNumberNeamt(SecurityUtils.getToken(), idFisierDummy.intValue(),ppnInfoRegistraturaReq);
			if(!resp.getResult().equals("OK")){
				return;
			}
		}
		flowService.sendFluxRequestByIdFisier(SecurityUtils.getToken(), new Long(idFisierDummy));
		//setDocAttributesEvenIfMissing
		DocAttrLinkList docAttrLinkList= new DocAttrLinkList();
		List<DocAttrLink> docAttrLinkList1= new ArrayList<>();
		if(sumaPlatita.isPresent() && !sumaPlatita.get().isEmpty()){
			docAttrLinkList1.add(new DocAttrLink("PLATA_EFECTUATA",sumaPlatita.get()));

		}
		if(sumaDePlata.isPresent() && !sumaDePlata.get().isEmpty()){
			docAttrLinkList1.add(new DocAttrLink("SUMA_PLATA",sumaDePlata.get()));

		}
		docAttrLinkList.setDocAttrLink(docAttrLinkList1);
		if(docAttrLinkList!=null && docAttrLinkList.getDocAttrLink().size()!=0){
			dmswsFileService.setDocAttributesEvenIfMissing(SecurityUtils.getToken(),idFisierDummy.intValue(),docAttrLinkList);
		}

		if(getRegistrationNumberFromERAActive && setERARegistrationNumber){
			dmswsPs4Service.getEraRegistrationNumber(SecurityUtils.getToken(), idFisierDummy.intValue());
		}


		if(dmswsPs4Service.getSysParam(SecurityUtils.getToken(),"NOTIFICARE_DIN_TEMPLATE_SOLICITARE").getDescriere()!=null && dmswsPs4Service.getSysParam(SecurityUtils.getToken(),"NOTIFICARE_DIN_TEMPLATE_SOLICITARE").getDescriere().equals("1")){
			String template=emailService.getHtmlTemplate("NOTIFICARE_SOLICITARE").getExtendedInfo2();
			RegistraturaComplete inreg= dmswsPs4Service.getInregistrareByIdFisier(SecurityUtils.getToken(),idFisierDummy.toString());
			template=template.replaceAll("TERT",inreg.getCreat_de());
			template=template.replaceAll("DATA_INREGISTRARE",inreg.getData());
			template=template.replaceAll("NR_GENERAT",inreg.getNr_generat());
			emailService.sendEmailTemplateBySystem(emailTo,"Solicitare noua",template);
		}
		else{
		emailService.sendEmailFromTemplateBySystem(
				urlPath+ "static/PORTAL/assets/images/logos/logo.png", emailTo,	"Solicitare noua",
                "static/PORTAL/email_solictiare_noua.html",
				"assets/images/logos/logo.png");
		logger.info("solicitare sent with succes", emailTo);
    }}

	public void setWorflowStatus( Long idFisier, Integer idStatus) throws ServerWebInputException {
		logger.info("changing workflow status to: "+idStatus+" for file id: "+idFisier);
		flowService.setWorkflowStatus(SecurityUtils.getToken(), new Long(idFisier),idStatus);

	}

}
