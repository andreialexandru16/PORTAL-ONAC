package ro.bithat.dms.microservices.dmsws.file;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Modeleaza un o lista de doc attr link.
 */
@XmlRootElement
public class DocAttrLinkList extends BaseModel {
    private List<DocAttrLink> docAttrLink;
    private String descriere;
    private String dataExpirare;

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public List<DocAttrLink> getDocAttrLink() {
        return docAttrLink;
    }

    public void setDocAttrLink(List<DocAttrLink> docAttrLink) {
        this.docAttrLink = docAttrLink;
    }
}
