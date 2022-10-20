package ro.bithat.dms.microservices.dmsws.ps4.documents.imported;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CorespondentaLinieControl {

    private Integer id;
    private String document;
    private String downloadLink;
    private Integer idFisier;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    public Integer getIdFisier() {
        return idFisier;
    }

    public void setIdFisier(Integer idFisier) {
        this.idFisier = idFisier;
    }
}
