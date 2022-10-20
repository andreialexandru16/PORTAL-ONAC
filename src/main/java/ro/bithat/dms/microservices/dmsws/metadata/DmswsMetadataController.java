package ro.bithat.dms.microservices.dmsws.metadata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.bithat.dms.microservices.dmsws.file.BaseModel;
import ro.bithat.dms.microservices.dmsws.file.DocAttrLinkList;
import ro.bithat.dms.microservices.dmsws.file.Metadata;

@RestController("/dmsws/metadata")
public class DmswsMetadataController {

	@Autowired
	private DmswsMetadataService service;;
	
	@RequestMapping(value = "/file_meta/{fileId}", method = RequestMethod.PUT, 
			consumes = {"application/xml", "application/json"}, produces = {"application/xml", "application/json"})
    @ResponseBody
    public BaseModel setFileMetadata(@RequestParam String token, @PathVariable Integer fileId, @RequestBody DocAttrLinkList docAttrLinkList){
		return service.setFileMetadata(token, fileId, docAttrLinkList);
	}
	
	@RequestMapping(value = "/file_meta/{fileId}/metadata_even_if_missing", method = RequestMethod.PUT, 
			consumes = {"application/xml", "application/json"}, produces = {"application/xml", "application/json"})
	@ResponseBody
    public BaseModel setFileMetadataEvenIfMissing(@RequestParam String token, @PathVariable Integer fileId, @RequestBody DocAttrLinkList docAttrLinkList){
		return service.setFileMetadataEvenIfMissing(token, fileId, docAttrLinkList);
	}
	
	@GetMapping("/file_meta/{fileId}")
	public Metadata getFileMetadata(@RequestParam String token, @PathVariable Integer fileId)  {
		return service.getFileMetadata(token, fileId+"");
	}
	
	@GetMapping("/document_meta/{documentId}")
    public AttributeLinkList getMetadataByDocumentId(@RequestParam String token, @PathVariable Integer documentId){
    	return service.getMetadataByDocumentId(token, documentId);
    }
    
	@GetMapping("/lovs/{lovId}/search")
    public LovList getLovListFiltered(@RequestParam String token, @PathVariable Integer lovId,@RequestParam String p_search){
    	return service.getLovListFiltered(token, lovId, p_search);
    }
	
	@GetMapping("/lovs/{lovId}")
    public LovList getLovList(@RequestParam String token, @PathVariable Integer lovId){
    	return service.getLovList(token, lovId);
    }
}
