package ro.bithat.dms.microservices.dmsws.ps4.documents;

import ro.bithat.dms.microservices.dmsws.flow.StandardResponse;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.DocObligatoriu;

public class DocObligatoriuExtra{

	private DocObligatoriu docObligatoriu;
	
	private String idTextareDescription;
	private Long uploadedFileId;

	private String downloadLinkForUploadedFile;
	
	private String uploadedFileName;

	public String getIdTextareDescription() {
		return idTextareDescription;
	}

	public void setIdTextareDescription(String idTextareDescription) {
		this.idTextareDescription = idTextareDescription;
	}

	public DocObligatoriuExtra(DocObligatoriu docObligatoriu) {
		super();
		this.docObligatoriu = docObligatoriu;
	}

	public DocObligatoriu getDocObligatoriu() {
		return docObligatoriu;
	}

	public String getLinkTemplateFile() {
		return docObligatoriu.getLinkTemplateFile();
	}
	
	public void setDocObligatoriu(DocObligatoriu docObligatoriu) {
		this.docObligatoriu = docObligatoriu;
	}

	public Long getUploadedFileId() {
		return uploadedFileId;
	}

	public void setUploadedFileId(Long uploadedFileId) {
		this.uploadedFileId = uploadedFileId;
	}

	public String getDownloadLinkForUploadedFile() {
		return downloadLinkForUploadedFile;
	}

	public void setDownloadLinkForUploadedFile(String downloadLinkForUpoadedFile) {
		this.downloadLinkForUploadedFile = downloadLinkForUpoadedFile;
	}

	public String getUploadedFileName() {
		return uploadedFileName;
	}

	public void setUploadedFileName(String uploadedFileName) {
		this.uploadedFileName = uploadedFileName;
	}
	
	
}
