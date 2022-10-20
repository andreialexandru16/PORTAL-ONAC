package ro.bithat.dms.microservices.dmsws.ps4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerErrorException;
import org.springframework.web.server.ServerWebInputException;
import ro.bithat.dms.microservices.dmsws.file.DocAttrLink;
import ro.bithat.dms.microservices.dmsws.file.DocAttrLinkList;
import ro.bithat.dms.microservices.dmsws.file.FileData;
import ro.bithat.dms.microservices.dmsws.file.WsAndUserInfo;
import ro.bithat.dms.microservices.dmsws.metadata.AttributeLinkList;
import ro.bithat.dms.microservices.dmsws.metadata.LovList;
import ro.bithat.dms.microservices.dmsws.ps4.detaliiserviciu.ElectronicService;
import ro.bithat.dms.microservices.dmsws.ps4.documents.DocObligatoriuExtra;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.ActNormativ;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.Document;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.TipDocument;
import ro.bithat.dms.security.SecurityUtils;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@RestController("/ps4")
public class PS4Controller {
	
	@Autowired
	private PS4Service service;
	
	@GetMapping("/tip_documente")
	public List<TipDocument> getAllDocumentTypess() {
		return SecurityUtils.getAllDocumentTypes();
	}
	
	@GetMapping("/tip_document/{id_tip_document}")
	public TipDocument getDocumentType(@PathVariable("id_tip_document") Integer docTypeId) {
		Optional<TipDocument> res = service.getDocumentType(docTypeId);
		if (res.isPresent()) { return res.get();}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND); 
	}
	
	@GetMapping("/tip_document/{id_tip_document}/documents")
	public List<Document> getDocuments(@PathVariable("id_tip_document") Integer tipDocument ) {
		return service.getDocuments(tipDocument);
	}
	
	@GetMapping("/tip_document/documents")
	public List<Document> getDocuments(@RequestParam String filter) {
		return service.getDocuments(filter);
	}
	
	@GetMapping("/tip_document/{id_tip_document}/documents/{filter}")
	public List<Document> getDocuments(@PathVariable("id_tip_document") Integer tipDocument,@PathVariable String filter,
			@RequestParam  String tipServiciu, @RequestParam String tipPersoana ) {
		return service.getDocuments(tipDocument, filter, tipServiciu, tipPersoana);
	}
	
	@GetMapping("/document/{id}/externallink")
	public String getExternalLink(@PathVariable Integer id) {
		Optional<Document> doc = getDocuments("").stream().filter(d->d.getId().equals(id)).findFirst();
		if (!doc.isPresent()) {throw new ResponseStatusException(HttpStatus.NOT_FOUND);} 
		if (doc.get().getJspPage() == null) {return null;}
		return doc.get().getJspPage();
	}
	
	@GetMapping("/document/{id}/detalii_serviciu")
	public ElectronicService getDetaliiServiciu(@PathVariable Long id) {
		return service.getDetaliiServiciu(id);
	}
	
	@GetMapping("/document/{id}/acte_normative")
	public List<ActNormativ> getActeNormativeByDocTypeId(@PathVariable Long id) {
		return service.getActeNormativeByDocTypeId(id);
	}
	
	@GetMapping("/document/{id}/documente_obligatorii_serviciu")
	public List<DocObligatoriuExtra> getDocumenteObligatoriiServiciu(@PathVariable Long id, @RequestParam(required = false) Integer parentFileId) {
		return service.getDocumenteObligatoriiServiciu(id, Optional.ofNullable(parentFileId));
	}
	
	@PutMapping("file/{parentFileId}/upload_and_attach")
	public DocObligatoriuExtra uploadFileAndAttach(@PathVariable Long parentFileId, @RequestBody DocObligatoriuExtra docObligatoriuExtra, @RequestParam("fileData") MultipartFile fileData) throws ServerErrorException, ServerWebInputException, IOException {
		return service.uploadFileAndAttach(parentFileId, docObligatoriuExtra, fileData.getOriginalFilename(), fileData.getBytes());
	}
	
	@GetMapping("/file/{fileId}/metadata")
	public List<DocAttrLink> getFileMetaData(@PathVariable Long fileId) {
		return service.getFileMetaData(fileId);
	}
	
	@PostMapping("/file/create_dummy_file_incepere_solicitare")
	public Integer createDummyFileIncepereSolicitare(@RequestBody FileData fileData) {
		return service.createDummyFileIncepereSolicitare(fileData);
	}
	@GetMapping("/document/getDocumentById/{idDocument}/")
	public Document getDocumentById(@PathVariable Integer idDocument) {
		return service.getDocumentById2(idDocument);
	}
	@PostMapping("/file/{fileIdToBeReplaced}/replace_existing_file_and_set_metadata")
	public Long replaceExistingFileAndSetMetadata(@PathVariable Long fileIdToBeReplaced,@RequestBody DocAttrLinkList metaData) throws ServerErrorException, ServerWebInputException, IOException {
		return service.replaceExistingFileAndSetMetadata(fileIdToBeReplaced, metaData);
	}
	
	////////////////////////////metadata and LOV////////////////////////////////
	@GetMapping("/document/{id}/metadata")
    public AttributeLinkList getMetadataByDocumentId(@PathVariable Integer id){
    	return service.getMetadataByDocumentId(id);
    }
    
	@GetMapping("/lovs/{lovId}/filtered")
    public LovList getLovListFiltered(@PathVariable Integer lovId,@RequestParam String p_search){
    	return service.getLovListFiltered(lovId, p_search);
    }
	
	@GetMapping("/lovs/{lovId}/all")
    public LovList getLovList(@PathVariable Integer lovId){
    	return service.getLovList(lovId);
    }
}
