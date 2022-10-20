package ro.bithat.dms.microservices.dmsws.file;

public class Metadata extends BaseModel{

	private DocAttrLink[] docAttrLink;

	public DocAttrLink[] getDocAttrLink() {
		return docAttrLink;
	}

	public void setDocAttrLink(DocAttrLink[] docAttrLink) {
		this.docAttrLink = docAttrLink;
	}

}