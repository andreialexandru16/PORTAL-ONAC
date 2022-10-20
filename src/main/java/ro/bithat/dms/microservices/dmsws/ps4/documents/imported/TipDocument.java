package ro.bithat.dms.microservices.dmsws.ps4.documents.imported;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class TipDocument extends BaseModel {
    private Integer id;
    private String denumire;
    private String cod;
    private String iconFilenamePath;
    private List<Document> documentList;
    private List<Column> atribute;
    private String linkUrl;

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }
    public TipDocument() {
    }

    public TipDocument(Integer id, String denumire) {
        this.id = id;
        this.denumire = denumire;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getIconFilenamePath() {
        return iconFilenamePath;
    }

    public void setIconFilenamePath(String iconFilenamePath) {
        this.iconFilenamePath = iconFilenamePath;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public List<Column> getAtribute() {
        return atribute;
    }

    public void setAtribute(List<Column> atribute) {
        this.atribute = atribute;
    }

    public List<Document> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(List<Document> documentList) {
        this.documentList = documentList;
    }
}
