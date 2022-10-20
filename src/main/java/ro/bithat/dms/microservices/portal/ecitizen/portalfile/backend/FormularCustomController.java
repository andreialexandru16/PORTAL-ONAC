package ro.bithat.dms.microservices.portal.ecitizen.portalfile.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.bithat.dms.microservices.dmsws.colaboration.Utilizator;
import ro.bithat.dms.microservices.dmsws.file.BaseModel;
import ro.bithat.dms.microservices.dmsws.file.FileData;
import ro.bithat.dms.microservices.dmsws.file.RaportSocietati;
import ro.bithat.dms.microservices.dmsws.flow.StandardResponse;
import ro.bithat.dms.microservices.dmsws.metadata.LovList;
import ro.bithat.dms.microservices.dmsws.ps4.DmswsPS4Service;
import ro.bithat.dms.microservices.dmsws.ps4.PS4Service;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.TipDocumentList;
import ro.bithat.dms.microservices.portal.ecitizen.raportare.backend.RaportareService;
import ro.bithat.dms.microservices.portal.ecitizen.raportare.models.FormularRaportareList;
import ro.bithat.dms.microservices.portal.ecitizen.raportare.models.PerioadaInfoResponse;
import ro.bithat.dms.microservices.portal.ecitizen.raportare.models.TertList;
import ro.bithat.dms.microservices.portal.ecitizen.raportare.models.ValidareFisiereList;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat.ContCurentPortalE;
import ro.bithat.dms.microservices.portal.ecitizen.website.services.PortalService;
import ro.bithat.dms.security.SecurityUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class FormularCustomController {

	@Autowired
	private FormularCustomService service;
	@Autowired
	private PS4Service ps4Service;
	@Autowired
	private PortalService portalService;
	@PostMapping("/dmsws/formular_custom/adaugaFormular/{tableName}/{idFisier}/")
	public BaseModel adaugaFormular(@PathVariable String tableName,@PathVariable Integer idFisier, @RequestBody Map<String,String> campuriCerere) {
		return service.adaugaFormular(SecurityUtils.getToken(),tableName,idFisier,campuriCerere);
	}





	@PostMapping("/dmsws/formular_custom/adaugaTabel/{tableName}/{idFisier}/")
	public BaseModel adaugaTabel(@PathVariable String tableName,@PathVariable Integer idFisier, @RequestBody List<Map<String,String>> randuriTabel) {
		return service.adaugaTabel(SecurityUtils.getToken(),tableName,idFisier,randuriTabel);
	}
	@PostMapping("/dmsws/formular_custom/adaugaTabelRectificare/{tableName}/{idFisier}/{idPerioada}")
	public BaseModel adaugaTabelRectificare(@PathVariable String tableName,@PathVariable Integer idFisier,@PathVariable Integer idPerioada, @RequestBody List<Map<String,String>> randuriTabel,
	@RequestParam(value = "codTipDocInitial", required = false) String codTipDocInitial) {
		return service.adaugaTabelRectificare(SecurityUtils.getToken(),tableName,idFisier,idPerioada,randuriTabel,codTipDocInitial);
	}




	@PostMapping("/dmsws/formular_custom/create_dummy_file/{idTipDocument}")
	public FileData adaugaFormular(@PathVariable Integer idTipDocument) {
		FileData fileData = new FileData();
		fileData.setId_document(idTipDocument);
		Integer idFisier=  ps4Service.createDummyFileIncepereSolicitareRegistratura(fileData);
		fileData.setId(idFisier);
		return fileData;
	}

	@PostMapping("/dmsws/formular_custom/create_dummy_file_base64/{idTipDocument}")
	public FileData adaugaFormular(@PathVariable Integer idTipDocument, @RequestBody FileData base64File) {
		FileData fileData = new FileData();
		fileData.setId_document(idTipDocument);
		fileData.setBase64File(base64File.getBase64File());
		Integer idFisier=  ps4Service.createDummyFileIncepereSolicitareRegistratura(fileData);
		String  downloadLink=  ps4Service.createDummyFileIncepereSolicitareRegistraturaStr(fileData);
		fileData.setId(idFisier);
		fileData.setDownloadLinkFile(downloadLink);
		//fileData.setResult("OK");
		return fileData;
	}

	@GetMapping(value = "/dmsws/formular_custom/lov/get_values_by_code/{lovCode}")
	public LovList getValuesLovByCode(@PathVariable String lovCode) {
		return portalService.getValuesLovByCode(SecurityUtils.getToken(),lovCode);

	}

	@GetMapping(value = "/dmsws/formular_custom/getInfoTabelRaport")
	public RaportSocietati getInfoTabelRaport(
			@RequestParam(value = "codTipDoc", required = true) String codTipDoc,
			@RequestParam(value = "idPerioada", required = false) Integer idPerioada,
												@RequestParam(value = "idFisier", required = false) Integer idFisier) {
		return portalService.getInfoTabelRaport(SecurityUtils.getToken(), Optional.ofNullable(idPerioada), Optional.ofNullable(idFisier),codTipDoc);

	}

	@GetMapping(value = "/dmsws/formular_custom/getInfoUtilizator")
	public ContCurentPortalE getInfoUtilizator() {

		return SecurityUtils.getContCurentPortalE();
		//return portalService.getInfoUtilizator(SecurityUtils.getToken());

	}

	@PostMapping("dmsws/formular_custom/sendRaportare/{id}")
	public BaseModel sendRaportare(@PathVariable String id ) {
		return portalService.sendRaportare(SecurityUtils.getToken(),id);
	}

	@GetMapping(value = "/dmsws/formular_custom/checkPerioadaRaportare/{idPerioada}")
	public RaportSocietati checkPerioadaRaportare(@PathVariable String idPerioada) {
		return portalService.checkPerioadaRaportare(SecurityUtils.getToken(),idPerioada);

	}
	@GetMapping(value = "/dmsws/formular_custom/checkPerioadaRaportareRez/{idPerioada}")
	public RaportSocietati checkPerioadaRaportareRez(@PathVariable String idPerioada) {
		return portalService.checkPerioadaRaportareRez(SecurityUtils.getToken(),idPerioada);

	}

	@GetMapping(value = "/dmsws/formular_custom/getInfoRaportRectificare/{id}")
	public RaportSocietati getInfoRaportRectificare(@PathVariable String id) {
		return portalService.getInfoRaportRectificare(SecurityUtils.getToken(),id);

	}
	@GetMapping(value = "/dmsws/formular_custom/getInfoRaportRectificareRez/{id}")
	public RaportSocietati getInfoRaportRectificareRez(@PathVariable String id) {
		return portalService.getInfoRaportRectificareRez(SecurityUtils.getToken(),id);

	}

	@GetMapping(value = "/dmsws/formular_custom/lov/get_values_by_code_search/{lovCode}")
	public LovList getValuesLovByCodeSearch(@PathVariable String lovCode,@RequestParam String searchStr) {
		return portalService.getValuesLovByCodeSearch(SecurityUtils.getToken(),lovCode,searchStr);

	}
}
