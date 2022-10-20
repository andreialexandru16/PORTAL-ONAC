package ro.bithat.dms.microservices.dmsws.file;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerErrorException;
import org.springframework.web.server.ServerWebInputException;
import ro.bithat.dms.microservices.dmsws.DmswsRestService;
import ro.bithat.dms.microservices.dmsws.flow.StandardResponse;
import ro.bithat.dms.microservices.dmsws.metadata.AttributeLink;
import ro.bithat.dms.microservices.dmsws.metadata.AttributeLinkList;
import ro.bithat.dms.microservices.dmsws.metadata.AttributeLinkListOfLists;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.CreateTipDocFileResponse;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.CreateTipDocFileResponseXml;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.DocObligatoriiResp;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.StandardUploadResponse;
import ro.bithat.dms.microservices.portal.ecitizen.website.models.DirLinkFoldersAndFiles;
import ro.bithat.dms.microservices.portal.ecitizen.website.models.RunJasperByTipDocAndSaveReq;
import ro.bithat.dms.microservices.portal.ecitizen.website.models.RunJasperByTipDocAndSaveResp;
import ro.bithat.dms.security.SecurityUtils;

import javax.net.ssl.SSLContext;
import javax.sound.sampled.Port;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class DmswsFileService extends DmswsRestService{


	private static Logger logger = LoggerFactory.getLogger(DmswsFileService.class);
	
	@Value("${dmsws.url}")
	private String url;

	public RestTemplate getRestTemplate(){
	    SSLContext sslContext;
		try {
			sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, new TrustAllStrategy()).build();
		    SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
		    CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
		    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		    requestFactory.setHttpClient(httpClient);
		    RestTemplate restTemplate = new RestTemplate(requestFactory);
		    return restTemplate;
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new RestTemplate();
	}
	
//	@HystrixCommand(commandKey = "dmsws-getRootFolder", commandProperties = {
//            @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS, value = "10000"),
//    })
	public RootFolder getRootFolder(String token)  {
		logger.info(url + "/dir/" + token + "/get_root");
		return getRestTemplate().getForEntity(url + "/dir/" + token + "/get_root", RootFolder.class).getBody();
	}
	public DirLinkFoldersAndFiles getSubfoldersAndFiles(String token, String dirId, String searchVal)  {
		String url = getDmswsUrl()+"/dir/{token}/get_subfolders3_and_link_by_id2/{dirId}";
		if (searchVal != null && !searchVal.trim().isEmpty()){
			url+="?searchVal="+searchVal;
		}
		return get(DirLinkFoldersAndFiles.class, checkBaseModelWithExtendedInfo(), url,
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, dirId);
	}


//	@HystrixCommand(commandKey = "dmsws-getFileListForFolder", commandProperties = {
//            @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS, value = "15000"),
//    })
	public FileList getFileListForFolder(String token, String dirId)  {
		logger.info(url + "/dir/" + token + "/link_by_id/" + dirId);
		return getRestTemplate().getForEntity(url + "/dir/" + token + "/link_by_id/" + dirId, FileList.class).getBody();
	}

//	@HystrixCommand(commandKey = "dmsws-getSubFolders", commandProperties = {
//            @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS, value = "15000"),
//    })
	public DirLinkList getSubFolders(String token, String dirId)  {
		logger.info(url + "/dir/" + token + "/get_subfolders/" + dirId);
		return getRestTemplate().getForEntity(url + "/dir/" + token + "/get_subfolders/" + dirId, DirLinkList.class).getBody();
	}

//	@HystrixCommand(commandKey = "dmsws-getFileListForFolder-str", commandProperties = {
//            @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS, value = "15000"),
//    })
	public String getFileListForFolderAsString(String token, String dirId)  {
		logger.info(url + "/dir/" + token + "/link_by_id/" + dirId);
		return getRestTemplate().getForEntity(url + "/dir/" + token + "/link_by_id/" + dirId, String.class).getBody();
	}

//	@HystrixCommand(commandKey = "dmsws-getSubFolders-str", commandProperties = {
//            @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS, value = "15000"),
//    })
	public String getSubFoldersAsString(String token, String dirId)  {
		logger.info(url + "/dir/" + token + "/get_subfolders/" + dirId);
		return getRestTemplate().getForEntity(url + "/dir/" + token + "/get_subfolders/" + dirId, String.class).getBody();
	}

//	@HystrixCommand(commandKey = "dmsws-getFileMetadata", commandProperties = {
//            @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS, value = "15000"),
//    })
	public Metadata getFileMetadata(String token, String fileId)  {
		logger.info(url + "/attribute/" + token + "/file_attr_list_by_id/" + fileId);
		return getRestTemplate().getForEntity(url + "/attribute/" + token + "/file_attr_list_by_id/" + fileId, Metadata.class).getBody();
	}
	
//	@HystrixCommand(commandKey = "dmsws-setDocAttributes", commandProperties = {
//            @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS, value = "15000"),
//    })
    public BaseModel setDocAttributes(@PathVariable String token, @PathVariable Integer fileId, @RequestBody DocAttrLinkList docAttrLinkList) {
    	return put(docAttrLinkList, BaseModel.class, checkBaseModel(), getDmswsUrl() + "/attribute/{token}/set_attr_list/{fileId}", token, fileId);
	}
    
    public BaseModel setDocAttributesEvenIfMissing(@PathVariable String token, @PathVariable Integer fileId, @RequestBody DocAttrLinkList docAttrLinkList) {
    	//extra logging for big values
    	docAttrLinkList.getDocAttrLink().stream().filter(d->d != null && d.getValue() != null).filter(d -> d.getValue().length() > 4000).forEach(
    			d -> logger.info("A big DocAttrLink will be set " + d.getAttributeCode() + " " + d.getAttributeCode() +" size=" + d.getValue().length())
    			);
    	return put(docAttrLinkList,MediaType.APPLICATION_XML, MediaType.APPLICATION_XML, BaseModel.class, checkBaseModel(), getDmswsUrl() + "/attribute/{token}/add_set_attr_list/{fileId}", token, fileId);
	}

    public BaseModel createProiectFromCerere(@PathVariable String token, @PathVariable Integer fileId, @RequestBody FileOb fileOb) {
    	return put(fileOb, BaseModel.class, checkBaseModel(), getDmswsUrl() + "/projectsdms/{token}/create_pr_from_c_reab/{fileId}", token, fileId);
	}
    
	
//	@HystrixCommand(commandKey = "dmsws-getFileProperties", commandProperties = {
//            @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS, value = "15000"),
//    })
	public Metadata getFileProperties(String token, String fileId)  {
		logger.info(url + "/attribute/" + token + "/file_attr_general_by_id/" + fileId);
		return getRestTemplate().getForEntity(url + "/attribute/" + token + "/file_attr_general_by_id/" + fileId, Metadata.class).getBody();
	}

//	@HystrixCommand(commandKey = "dmsws-getFileLink", commandProperties = {
//            @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS, value = "15000"),
//    })
	public FileLink getFileLink(String token, String fileId)  {

		logger.info(url + "/file/" + token + "/link_by_id/" + fileId);
		return getRestTemplate().getForEntity(url + "/file/" + token + "/link_by_id/" + fileId, FileLink.class).getBody();
	}

//	@RequestMapping(value = "/api/v1/file/{token}/getFileBinary/{fileId}", method = RequestMethod.GET, consumes = "application/json")
	
//	@HystrixCommand(commandKey = "dmsws-getFileBinary", commandProperties = {
//            @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS, value = "15000"),
//    })
//	public File getFileBinary(String token, String fileId)  {
//		logger.info(url + "/file/" + token + "/getFileBinary/" + fileId);
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		
//		List<MediaType> accept = new ArrayList<MediaType>();
//		accept.add(MediaType.MULTIPART_FORM_DATA);
//		headers.setAccept(accept);
//	
//		HttpEntity<File> entity = new HttpEntity<File>(headers);
//		
//		return getRestTemplate().exchange(url + "/file/" + token + "/getFileBinary/" + fileId, 
//				HttpMethod.GET, entity, File.class).getBody();
//	}

//	@HystrixCommand(commandKey = "dmsws-getFileBinary", commandProperties = {
//    @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS, value = "15000"),
//	})
	public byte[] getFileBinary(String token, String fileDownloadLink) throws IOException  {
		return copyURLToByteArray(fileDownloadLink, 10000, 10000); 
	}
	
	static byte[] copyURLToByteArray(final String urlStr,
	        final int connectionTimeout, final int readTimeout) 
	                throws IOException {
	    final URL url = new URL(urlStr);
	    final URLConnection connection = url.openConnection();
	    connection.setConnectTimeout(connectionTimeout);
	    connection.setReadTimeout(readTimeout);
	    try (InputStream input = connection.getInputStream();
	            ByteArrayOutputStream output = new ByteArrayOutputStream()) {
	        final byte[] buffer = new byte[8192];
	        for (int count; (count = input.read(buffer)) > 0;) {
	            output.write(buffer, 0, count);
	        }
	        return output.toByteArray();
	    }
	}
	
	public String getUrlPreviewPdf(String token, String fileId) {
		//sa avem versiunea curenta in link
		return url + "/file/" + token + "/pdf_by_id/" + fileId;
	}
	
//	@HystrixCommand(commandKey = "dmsws-getFileAsPdf", commandProperties = {
//            @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS, value = "15000"),
//    })
	public byte[] getFileAsPdf(String token, String fileId)  {
		logger.info(url + "/file/" + token + "/pdf_by_id/" + fileId);
		
		RestTemplate restTemplate = getRestTemplate();
		
		restTemplate.getMessageConverters().add(
	            new ByteArrayHttpMessageConverter());

	    HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
//	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_PDF));
//
	    HttpEntity<String> entity = new HttpEntity<String>(headers);

	    ResponseEntity<byte[]> response = restTemplate.exchange(
	    		url + "/file/" + token + "/pdf_by_id/" + fileId,
	            HttpMethod.GET, entity, byte[].class);

	    if (response.getStatusCode() == HttpStatus.OK) {
	    	return response.getBody();
	    }
	    return null;
	}

//	@HystrixCommand(commandKey = "dmsws-checkOutFile", commandProperties = {
//            @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS, value = "15000"),
//    })
	public String checkOutFile(String token, String fileId, String userId)  {
		logger.info(url + "/file/" + token + "/checkOut/" + fileId +"/" + userId);
		return getRestTemplate().getForEntity(url + "/file/" + token + "/checkOut/" + fileId +"/" + userId, String.class).getBody();
	}
	
//	@HystrixCommand(commandKey = "dmsws-checkInFile", commandProperties = {
//            @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS, value = "15000"),
//    })
    public FileLink checkInFile(String token, Integer finishEdit, Integer fileId,
                            Integer userId, String comment, String filename, byte[] someByteArray) throws ServerWebInputException, IOException, ServerErrorException {
		String path = url + "/file/checkIn/?finishEdit=" + finishEdit +"&fileId=" + fileId + "&token=" + token +
				"&userId="+userId+"&comment="+comment;
    	logger.info(path);
		RestTemplate restTemplate = getRestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		
		List<MediaType> accept = new ArrayList<MediaType>();
		accept.add(MediaType.APPLICATION_JSON);
		headers.setAccept(accept);
		
		
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        File file = new File(filename);
        FileUtils.writeByteArrayToFile(file, someByteArray);
        body.add("fileData", new FileSystemResource(file));


		HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);
		
		FileLink result = restTemplate
				.postForEntity(path, entity, FileLink.class/* , finishEdit, fileId, token, userId, comment */).getBody();
		try {
        	file.delete();        	
        }catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}

//    @RequestMapping(value = "/api/v1/file/{token}/uploadFisierTipDocId", 
//    method = RequestMethod.POST, consumes = "multipart/form-data", produces = {"application/xml", "application/json"})
//    @ResponseBody
//    public CreateTipDocFileResponse uploadFisierTipDocId(@PathVariable String token,
//                                                         @RequestParam("fileData") MultipartFile fileData,
//                                                         HttpServletRequest request, HttpServletResponse httpServletResponse) {
    
//    final String[] nume = {request.getParameter("nume")};
//    String descriere = request.getParameter("descriere");
//    String id_document_str = request.getParameter("id_document");

	public CreateTipDocFileResponse uploadFisierTipDocId(String token, Integer idDocument, Long userId, String descriere, String filename, byte[] someByteArray, Optional<String> cuProcesare) {
		String filenameWithoutExtension= filename.substring(0,filename.lastIndexOf("."));

		String path = url + "/file/"+token+"/uploadFisierTipDocId/?id_document=" + idDocument + "&userId=" + userId
				 + "&nume=" + URLEncoder.encode(filenameWithoutExtension)
				 + "&descriere=" + URLEncoder.encode(descriere);
		if(cuProcesare.isPresent()){
			path+=  "&cu_procesare=" +cuProcesare.get();
		}
		CreateTipDocFileResponse result = upload(CreateTipDocFileResponse.class, checkStandardResponse(), filename, someByteArray, path);
		return result;
	}

	public CreateTipDocFileResponseXml uploadFisierTipDocIdXml(String token, Integer idDocument, Long userId, Integer tertId,Integer idPerioada, String descriere, String filename, byte[] someByteArray) {
		String filenameWithoutExtension= filename.substring(0,filename.lastIndexOf("."));

		String path = url + "/file/"+token+"/uploadFisierTipDocIdXml/?id_document=" + idDocument + "&userId=" + userId + "&idPerioada=" + idPerioada;
		if (tertId != null){
			path += "&tertId=" + tertId;
		}

		path += "&nume=" + URLEncoder.encode(filenameWithoutExtension)
		+ "&descriere=" + URLEncoder.encode(descriere);

		CreateTipDocFileResponseXml result = upload(CreateTipDocFileResponseXml.class, checkStandardResponseErrStringOnly(), filename, someByteArray, path);
		return result;
	}

	public CreateTipDocFileResponse uploadDocumentCont(String token, Integer idDocument, Long userId, String descriere, String filename, byte[] someByteArray,
													   Optional<String> valabilDeLa, Optional<String> valabilPanaLa
			, Optional<String> nrAnre, Optional<String> dataAnre
			, Optional<String> nrDocument, Optional<String> dataDocument
			, Optional<String> zona) {
		String filenameWithoutExtension= filename.substring(0,filename.lastIndexOf("."));
			String path = url + "/file/" + token + "/uploadDocumentCont/?id_document=" + idDocument + "&userId=" + userId
					+ "&nume=" + URLEncoder.encode(filenameWithoutExtension)
					+ "&descriere=" + URLEncoder.encode(descriere);

			if (valabilDeLa.isPresent()) {
				path += "&valabil_de_la=" + URLEncoder.encode(valabilDeLa.get());
			}
			if (valabilPanaLa.isPresent()) {
				path += "&valabil_pana_la=" + URLEncoder.encode(valabilPanaLa.get());
			}
			if (zona.isPresent()) {
				path += "&zona=" + URLEncoder.encode(zona.get());
			}
			if (nrAnre.isPresent()) {
				path += "&nr_anre=" + URLEncoder.encode(nrAnre.get());
			}
			if (dataAnre.isPresent()) {
				path += "&data_anre=" + URLEncoder.encode(dataAnre.get());
			}
			if (nrDocument.isPresent()) {
				path += "&nr_document=" + URLEncoder.encode(nrDocument.get());
			}
			if (dataDocument.isPresent()) {
				path += "&data_document=" + URLEncoder.encode(dataDocument.get());
			}

			CreateTipDocFileResponse result = upload(CreateTipDocFileResponse.class, checkStandardResponse(), filename, someByteArray, path);
			return result;

	}

	public CreateTipDocFileResponse uploadDocumentContEdit(String token, Integer idDocument, Long userId, String descriere, String filename, byte[] someByteArray,
													   Optional<String> valabilDeLa, Optional<String> valabilPanaLa
			, Optional<String> nrAnre, Optional<String> dataAnre
			, Optional<String> nrDocument, Optional<String> dataDocument
			, Optional<String> zona, String id) {
		String filenameWithoutExtension=null;
		if(filename!=null){
			filenameWithoutExtension=filename.substring(0,filename.lastIndexOf("."));
		}

		String path = url + "/file/"+token+"/uploadDocumentContEdit/?id_document=" + idDocument + "&userId=" + userId
				+ "&nume=" + URLEncoder.encode(filenameWithoutExtension==null?"":filenameWithoutExtension)
				+ "&descriere=" + URLEncoder.encode(descriere==null?"":descriere)
				+ "&id=" + URLEncoder.encode(id);

		if(valabilDeLa.isPresent()){
			path	+= "&valabil_de_la=" + URLEncoder.encode(valabilDeLa.get());

		}
		if(valabilPanaLa.isPresent()){
			path	+= "&valabil_pana_la=" + URLEncoder.encode(valabilPanaLa.get());

		}
		if(zona.isPresent()){
			path	+= "&zona=" + URLEncoder.encode(zona.get());
		}
		if(nrAnre.isPresent()){
			path	+= "&nr_anre=" + URLEncoder.encode(nrAnre.get());
		}
		if(dataAnre.isPresent()){
			path	+= "&data_anre=" + URLEncoder.encode(dataAnre.get());
		}
		if(nrDocument.isPresent()){
			path	+= "&nr_document=" + URLEncoder.encode(nrDocument.get());
		}
		if(dataDocument.isPresent()){
			path	+= "&data_document=" + URLEncoder.encode(dataDocument.get());
		}
		if(someByteArray==null){
			someByteArray= " ".getBytes();
		}
		CreateTipDocFileResponse result = upload(CreateTipDocFileResponse.class, checkStandardResponse(), filename==null?"empty.txt":filename, someByteArray, path);
		return result;
	}
	public StandardResponse uploadDocumentAutGospApelor(String token, Integer id, String filename, byte[] someByteArray) {
		String path = url + "/anre/"+token+"/uploadDocumentAutGospApelor"
			+ "?id=" + id;
		StandardResponse result = upload(StandardResponse.class, checkStandardResponse(), filename, someByteArray, path);
		return result;
	}

	public StandardResponse uploadDocumentAutMediu(String token, Integer id, String filename, byte[] someByteArray) {
		String path = url + "/anre/"+token+"/uploadDocumentAutMediu"
			+ "?id=" + id;
		StandardResponse result = upload(StandardResponse.class, checkStandardResponse(), filename, someByteArray, path);
		return result;
	}

	public CreateTipDocFileResponse uploadFisierTipDocCode(String token, String codTipDoc, Long userId, String descriere, String filename, byte[] someByteArray) {
		String filenameWithoutExtension= filename.substring(0,filename.lastIndexOf("."));
		String path = url + "/file/"+token+"/uploadFisierTipDocId/?cod_tip_document=" + codTipDoc + "&userId=" + userId
				+ "&nume=" + URLEncoder.encode(filenameWithoutExtension)
				+ "&descriere=" + URLEncoder.encode(descriere);
		CreateTipDocFileResponse result = upload(CreateTipDocFileResponse.class, checkStandardResponse(), filename, someByteArray, path);
		return result;
	}
    
//    public FileLink upload(byte[] uploadData, HttpServletRequest request, @RequestParam("token") String token, @RequestParam("dirId") Integer dirId
//    , @RequestParam("userId") Integer userId, @RequestParam("fileData") MultipartFile fileData) throws BadRequestException, IOException, ServerErrorException {
	public FileLink upload(String token, Long dirId, Long userId, String filename, byte[] someByteArray) throws ServerWebInputException, IOException, ServerErrorException {
		String path = url + "/file/upload/?dirId=" + dirId + "&token=" + token + "&userId=" + userId;
		FileLink result = upload(FileLink.class, filename, someByteArray, path);
		return result;
	}

	public FileData createDummyFile(String token, FileData fileData) {
		try {
		return post(fileData,MediaType.APPLICATION_XML, MediaType.APPLICATION_XML,  FileData.class, checkBaseModel(),
				getDmswsUrl()+"/file/{token}/create_dummy_file?&extension=pdf", SecurityUtils.getToken());
		}catch(Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}

	public FileData createDummyFileSolicitare(String token, FileData fileData) {
		try {
			return post(fileData, MediaType.APPLICATION_XML, MediaType.APPLICATION_XML, FileData.class, checkBaseModel(),
					getDmswsUrl()+"/file/{token}/create_dummy_file?extension=pdf&insertIntoRegistratura=0", SecurityUtils.getToken());
		}catch(Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
	public FileData createDummyFileSolicitareRegister(String token, FileData fileData) {
		try {
			return post(fileData, MediaType.APPLICATION_XML, MediaType.APPLICATION_XML, FileData.class, checkBaseModel(),
					getDmswsUrl()+"/file/{token}/create_dummy_file_autoregister?extension=pdf&insertIntoRegistratura=1", SecurityUtils.getToken());
		}catch(Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
    public BaseModel attachFile(String token, Integer fileId,  Integer parentFileId){
    	return get(DocObligatoriiResp.class, checkBaseModel(), getDmswsUrl()+"/file/{token}/attach_file/{fileId}/{parentFileId}", 
    			MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, fileId, parentFileId);
	}
	public BaseModel attachFileDocRaspuns(String token, Integer fileId,  Integer parentFileId,Integer requestFileId){
		return get(DocObligatoriiResp.class, checkBaseModel(), getDmswsUrl()+"/file/{token}/attach_file_doc_raspuns/{fileId}/{parentFileId}/{requestFileId}",
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, fileId, parentFileId,requestFileId);
	}
//    @RequestMapping(value = "/uploadFileToReplace/{type}", method = RequestMethod.POST, consumes = "multipart/form-data", produces = {"application/xml", "application/json"})
//    @ResponseBody
//    public CreateTipDocFileResponse uploadFileToReplace(@PathVariable String token,
//    @PathVariable String type, MultipartHttpServletRequest request, HttpServletResponse httpServletResponse) {
//    String id_fisier = request.getParameter("id_fisier");
public BaseModel deleteAttachment(@PathVariable String token, @PathVariable Integer fileId, @PathVariable Integer parentFileId) {
	return put( BaseModel.class,BaseModel.class, checkBaseModel(), getDmswsUrl() + "/file/{token}/deleteAttachment/{fileId}/{parentFileId}", token, fileId,parentFileId);
}
	public BaseModel deleteFile(@PathVariable String token, @PathVariable Integer fileId) {
		return put( BaseModel.class,BaseModel.class, checkBaseModel(), getDmswsUrl() + "/file/{token}/delFile/{fileId}", token, fileId);
	}
	public BaseModel deleteAttachmentOnFlow(@PathVariable String token, @PathVariable Integer fileId, @PathVariable Integer parentFileId) {
		return put( BaseModel.class,BaseModel.class, checkBaseModel(), getDmswsUrl() + "/file/{token}/deleteAttachmentOnFlow/{fileId}/{parentFileId}", token, fileId,parentFileId);
	}
	public CreateTipDocFileResponse uploadToReplaceExistingFile(String token, Long fileIdToBeReplaced, String filename, byte[] someByteArray) throws ServerWebInputException, IOException, ServerErrorException {
		String path = url + "/sidepanel/"+token+"/uploadFileToReplace/1/?&id_fisier=" + fileIdToBeReplaced;
		CreateTipDocFileResponse result = upload(CreateTipDocFileResponse.class, checkStandardResponse(), filename, someByteArray, path);
		return result;
	}
	public CreateTipDocFileResponse uploadToReplaceExistingFile(String token, Long fileIdToBeReplaced, String filename, byte[] someByteArray, Integer resetDescriere) throws ServerWebInputException, IOException, ServerErrorException {
		String path = url + "/sidepanel/"+token+"/uploadFileToReplace/1/?&id_fisier=" + fileIdToBeReplaced + "&resetDescriere=" + resetDescriere;
		CreateTipDocFileResponse result = upload(CreateTipDocFileResponse.class, checkStandardResponse(), filename, someByteArray, path);
		return result;
	}
	public CreateTipDocFileResponse uploadToReplaceExistingFileAndUpdateName(String token, Long fileIdToBeReplaced, String filename, byte[] someByteArray) throws ServerWebInputException, IOException, ServerErrorException {
		String path = url + "/sidepanel/"+token+"/uploadFileToReplace/1/?&id_fisier=" + fileIdToBeReplaced + "&updateNume=1";
		CreateTipDocFileResponse result = upload(CreateTipDocFileResponse.class, checkStandardResponse(), filename, someByteArray, path);
		return result;
	}
	public CreateTipDocFileResponse uploadToReplaceExistingFile2(String token, Long fileIdToBeReplaced, Long idUtilizator, String filename, byte[] someByteArray) throws ServerWebInputException, IOException, ServerErrorException {
		String path = url + "/sidepanel/"+token+"/uploadFileToReplace/1/?&id_fisier=" + fileIdToBeReplaced + "&id_utilizator=" + idUtilizator;
		CreateTipDocFileResponse result = upload(CreateTipDocFileResponse.class, checkStandardResponse(), filename, someByteArray, path);
		return result;
	}
	public BaseModel checkSecurityByFileId(String token, Integer fileId){
		return get(BaseModel.class, checkBaseModel(), getDmswsUrl()+"/file/{token}/checkSecurityFile/{fileId}",
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, fileId);
	}

	public BaseModel saveDescriptionFile(@PathVariable String token, @RequestBody PortalFile file ) {

		return put( file,MediaType.APPLICATION_XML, MediaType.APPLICATION_XML,BaseModel.class, checkBaseModel(), getDmswsUrl() + "/file/{token}/saveDescriptionFile", token);
	}

	public String exportXls(@PathVariable String token, @RequestParam Integer idDocument){
		 return getDmswsUrl()+"/file/"+token+"/exportXls/"+idDocument;

	}

	public AttributeLinkListOfLists importXls(@PathVariable String token, @RequestBody byte[] fisier, @PathVariable Integer idDocument){
		return  upload(AttributeLinkListOfLists.class, checkBaseModel(), "template", fisier, getDmswsUrl() + "/file/"+token+"/importXlsComplex/"+idDocument);
		//return put(fisier,MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON,AttributeLinkListOfLists.class, checkBaseModel(), getDmswsUrl() + "/file/{token}/importXlsComplex/{idDocument}", token, idDocument);

	}
	public CreateTipDocFileResponseXml importXml(@PathVariable String token, @RequestBody byte[] fisier, @PathVariable Integer idDocument, String filename){

		String filenameWithoutExtension= filename.substring(0,filename.lastIndexOf("."));

		String path = url + "/file/"+token+"/importXmlComplex/?id_document=" + idDocument ;

		path += "&nume=" + URLEncoder.encode(filenameWithoutExtension)
				+ "&descriere=" + URLEncoder.encode(filenameWithoutExtension);

		CreateTipDocFileResponseXml result = upload(CreateTipDocFileResponseXml.class, checkStandardResponseErrStringOnly(), filename, fisier, path);

		return result;
		//return  upload(AttributeLinkListOfLists.class, checkBaseModel(), fileName, fisier, getDmswsUrl() + "/file/"+token+"/importXmlComplex/"+idDocument);
		//return put(fisier,MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON,AttributeLinkListOfLists.class, checkBaseModel(), getDmswsUrl() + "/file/{token}/importXlsComplex/{idDocument}", token, idDocument);

	}

	public RunJasperByTipDocAndSaveResp getIdFisierJasper(@RequestBody RunJasperByTipDocAndSaveReq runJasperByTipDocAndSaveReq){
		try {
			return post(runJasperByTipDocAndSaveReq, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, RunJasperByTipDocAndSaveResp.class, checkStandardResponse(),
					getDmswsUrl()+"/jasper/{token}/runJusperByTipDocAndSave", SecurityUtils.getToken());
		}catch(Throwable e) {
			e.printStackTrace();
			return new RunJasperByTipDocAndSaveResp();
		}
	}

	public BaseModel getInvoiceJasperFileLink(@PathVariable String token, @RequestParam Integer fileId){
		return get(BaseModel.class, checkBaseModel(), getDmswsUrl()+"/file/{token}/getInvoiceJasperFileLink/{fileId}",
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, token, fileId);

	}
}
