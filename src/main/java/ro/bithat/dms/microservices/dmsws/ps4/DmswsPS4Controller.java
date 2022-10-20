package ro.bithat.dms.microservices.dmsws.ps4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.bithat.dms.microservices.dmsws.file.MultiFilterPortalFileList;
import ro.bithat.dms.microservices.dmsws.file.PortalFileList;
import ro.bithat.dms.microservices.dmsws.ps4.detaliiserviciu.ElectronicService;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.ActNormativList;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.DocObligatoriiResp;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.TipDocumentList;
import ro.bithat.dms.security.SecurityUtils;

import java.util.Optional;


@RestController("/dmsws")
public class DmswsPS4Controller {
	
	@Autowired
	private DmswsPS4Service service;
	
	@GetMapping("/ps4/tip_documente")
	public TipDocumentList getTipDocumentList(){
		return service.getTipDocumentList(SecurityUtils.getToken());
	}
	
	@GetMapping("/ps4/detalii_serviciu")
	public ElectronicService getDetaliiServiciu(@RequestParam Long docTypeId) {
		return service.getDetaliiServiciu(SecurityUtils.getToken(), docTypeId);
	}

	@GetMapping("/ps4/documente_obligatorii_serviciu")
	public DocObligatoriiResp getDocumenteObligatoriiServiciu(@RequestParam Long docTypeId, @RequestParam(required = false) Integer parentFileId) {
		return service.getDocumenteObligatoriiServiciu(SecurityUtils.getToken(), docTypeId, Optional.ofNullable(parentFileId));
	}
	
	@GetMapping("/ps4/acte_normative")
	public ActNormativList getActeNormativeByDocTypeId(@RequestParam Long docTypeId) {
		return service.getActeNormativeByDocTypeId(SecurityUtils.getToken(), docTypeId);
	}
	
	@GetMapping("/ps4/documente_sablon_link_download")
	public String getDocumenteSablonLinkDownload(@RequestParam String fileId) {
		return service.getDocumenteSablonLinkDownload(SecurityUtils.getToken(), fileId);
	}
	
	@GetMapping("/ps4/cererile_mele")
	public MultiFilterPortalFileList getFilesOnWorkflowByUser() {
		return service.getFilesOnWorkflowByUser(SecurityUtils.getToken());
	}

	@GetMapping("/ps4/documente_raspuns/{fileId}")
	public PortalFileList getDocRaspunsByFileId(@RequestParam String token, @PathVariable Integer fileId) {
    	return service.getDocRaspunsByFileId(SecurityUtils.getToken(), fileId);
	}	
}
