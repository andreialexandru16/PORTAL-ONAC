package ro.bithat.dms.microservices.dmsws.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ServerErrorException;
import org.springframework.web.server.ServerWebInputException;
import ro.bithat.dms.microservices.portal.ecitizen.website.models.DirLinkFoldersAndFiles;
import ro.bithat.dms.security.SecurityUtils;

import java.io.IOException;

@RestController("/dmsws/folder")
public class DmswsFileController {

	@Autowired
	private DmswsFileService service;;
	
	@GetMapping("/root")
	public RootFolder getRootFolder(@RequestParam String token){
		return service.getRootFolder(token);
	}

	@GetMapping("/{dirId}/files")
	public FileList getFileListForFolder(@RequestParam String token, @PathVariable String dirId){
		return service.getFileListForFolder(token, dirId);
	}

	@GetMapping("/getSubfoldersAndFiles/{dirId}")
	public DirLinkFoldersAndFiles getSubfoldersAndFiles(@PathVariable String dirId, @RequestParam(name = "searchVal", required = false) String searchVal) {
		return service.getSubfoldersAndFiles(SecurityUtils.getToken(), dirId, searchVal);
	}

	@GetMapping("/{dirId}/subfolders")
	public DirLinkList getSubFolders(@RequestParam String token, @PathVariable String dirId){
		return service.getSubFolders(token, dirId);
	}

	@GetMapping("/{fileId}/metadata")
	public Metadata getFileMetadata(@RequestParam String token, @PathVariable String fileId){
		return service.getFileMetadata(token, fileId);
	}
	
	@RequestMapping(value = "/{fileId}/metadata", method = RequestMethod.PUT, 
			consumes = {"application/xml", "application/json"}, produces = {"application/xml", "application/json"})
    @ResponseBody
    public BaseModel setDocAttributes(@PathVariable String token, @PathVariable Integer fileId, @RequestBody DocAttrLinkList docAttrLinkList){
		return service.setDocAttributes(token, fileId, docAttrLinkList);
	}
	
	@GetMapping("/{fileId}/properties")
	public Metadata getFileProperties(@RequestParam String token, @PathVariable String fileId){
		return service.getFileProperties(token, fileId);
	}
	
	@GetMapping("/{fileId}/link")
	public FileLink getFileLink(@RequestParam String token, @PathVariable String fileId){
		if(fileId!=null){
			return service.getFileLink(token, fileId);

		}else{
			return null;
		}
	}
	
	@GetMapping("/{fileId}/urlpdfpreview")
	public String getUrlPreviewPdf(@RequestParam String token, @PathVariable String fileId){
		return service.getUrlPreviewPdf(token, fileId);
	}
	
	@GetMapping(value = "/{fileId}/pdf")
	public byte[] getFileAsPdf(@RequestParam String token, @PathVariable String fileId){
		return service.getFileAsPdf(token, fileId);
	}
	
	//not tested
	@GetMapping("/{fileId}/checkOut/{userId}")
	public String checkOutFile(@RequestParam String token, @PathVariable String fileId, @PathVariable String userId) {
		return service.checkOutFile(token, fileId, userId);
	}
	
	@PostMapping(value = "/upload/{userId}/{dirId}", consumes = "multipart/form-data", produces = {"application/xml", "application/json"})
    @ResponseBody
	public FileLink upload(@RequestParam String token,@PathVariable Long dirId,@PathVariable Long userId,
			@RequestParam("fileData") MultipartFile fileData) throws ServerErrorException, ServerWebInputException, IOException {
		return service.upload(token, dirId, userId, fileData.getOriginalFilename(), fileData.getBytes());
	}
	
	@GetMapping("/{parentFileId}/attach/{fileId}")
	public BaseModel attachFile(@RequestParam String token, @PathVariable Integer fileId,  @PathVariable Integer parentFileId){
		return service.attachFile(token, fileId, parentFileId);
	}
	@GetMapping("/checkSecurityByFileId/{fileId}")
	public BaseModel checkSecurityByFileId(@PathVariable Integer fileId){
		return service.checkSecurityByFileId(SecurityUtils.getToken(), fileId);
	}

//	@PostMapping("/{fileId}/checkIn/{userId}")
////	@RequestMapping(value = "/api/v1/file/checkIn/", method = RequestMethod.POST, consumes = "multipart/form-data", produces = {"application/xml", "application/json"})
////    @ResponseBody
//    public FileLink checkIn(HttpServletRequest request, @RequestParam("finishEdit") Integer finishEdit, @RequestParam("fileId") Integer fileId,
//                            @RequestParam("token") String token, @RequestParam("userId") Integer userId, @RequestParam("comment") String comment) throws BadRequestException, IOException, ServerErrorException {
//		logger.info(url + "/portalflow/" + token + "/getFlowInstances/?&idFluxDef=" + flowId);
//		RestTemplate restTemplate = getRestTemplate();
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		List<MediaType> accept = new ArrayList<MediaType>();
//		accept.add(MediaType.APPLICATION_JSON);
//		headers.setAccept(accept);
//
//		HttpEntity<String> entity = new HttpEntity<String>("{\"idFluxDef\":"+ flowId +"}" ,headers);
//		
//		PortalFlowList result =  restTemplate.postForEntity(url + "/portalflow/" + token + "/getFlowInstances/?&idFluxDef=" + flowId, entity , PortalFlowList.class).getBody();
//
//	}
}
