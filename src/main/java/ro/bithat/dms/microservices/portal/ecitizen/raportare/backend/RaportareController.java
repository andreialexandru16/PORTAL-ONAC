package ro.bithat.dms.microservices.portal.ecitizen.raportare.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerErrorException;
import org.springframework.web.server.ServerWebInputException;
import ro.bithat.dms.microservices.dmsws.colaboration.Utilizator;
import ro.bithat.dms.microservices.dmsws.file.BaseModel;
import ro.bithat.dms.microservices.dmsws.flow.StandardResponse;
import ro.bithat.dms.microservices.dmsws.ps4.DmswsPS4Service;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.TipDocumentList;
import ro.bithat.dms.microservices.portal.ecitizen.project.backend.bithat.UtilizatorList;
import ro.bithat.dms.microservices.portal.ecitizen.raportare.models.FormularRaportareList;
import ro.bithat.dms.microservices.portal.ecitizen.raportare.models.PerioadaInfoResponse;
import ro.bithat.dms.microservices.portal.ecitizen.raportare.models.TertList;
import ro.bithat.dms.microservices.portal.ecitizen.raportare.models.ValidareFisiereList;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.DmswsUtilizatorService;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat.PersoanaFizicaJuridica;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat.PsihologList;
import ro.bithat.dms.security.SecurityUtils;

import java.io.IOException;
import java.util.Optional;

@RestController
public class RaportareController {

	@Autowired
	private RaportareService service;

	@Autowired
	private DmswsPS4Service dmswsPS4Service;

	@GetMapping(value = "/dmsws/raportare/validareInterFisiere")
	public ValidareFisiereList validareInterFisiere(@RequestParam String idsFiles, @RequestParam String transmitereDate) {
		return service.validareInterFisiere(idsFiles,transmitereDate);

	}
	@GetMapping(value = "/dmsws/raportare/getDocumentListByTertPerioada")
	public FormularRaportareList getDocumentListByTertPerioada(@RequestParam Integer idClasaDoc, @RequestParam Integer idPerioada, @RequestParam(name = "idTert",required = false) Integer idTert) {
		return service.getDocumentListByTertPerioada(idClasaDoc,idPerioada,Optional.ofNullable(idTert));

	}

	@GetMapping(value = "/dmsws/raportare/getPerioadaPrecedentaList")
	public PerioadaInfoResponse getPerioadaPrecedentaList() {
		return service.getPerioadaPrecedentaList();

	}


	@GetMapping(value = "/dmsws/raportare/checkUserGroupMemberByCode")
	public Utilizator checkUserGroupMemberByCode(@RequestParam String codGrup) {
		return service.checkUserGroupMemberByCode(codGrup);

	}
	@GetMapping(value = "/dmsws/raportare/getTertRaportareList")
	public TertList getTertRaportareList(@RequestParam Optional<Integer> idClasaDoc,@RequestParam Optional<String> searchStr ,@RequestParam Optional<String> saved_last_tert_id,@RequestParam Optional<String> putere_min_10  ) {

		return service.getTertRaportareList(idClasaDoc, searchStr, saved_last_tert_id,putere_min_10);

	}
	@GetMapping(value = "/dmsws/raportare/getTertRaportareListShort")
	public TertList getTertRaportareList(@RequestParam Optional<String> searchStr ,@RequestParam Optional<String> saved_last_tert_id ,@RequestParam Optional<String> putere_min_10 ) {

		return service.getTertRaportareList( Optional.empty(),searchStr, saved_last_tert_id,putere_min_10);

	}

	@GetMapping(value = "/dmsws/document/getListaClasaDocByTag")
	public TipDocumentList getListaClasaDocByTag(@RequestParam String tagStr) {
		return dmswsPS4Service.getTipDocumentListByTag(SecurityUtils.getToken(),tagStr);

	}

}
