package ro.bithat.dms.microservices.dmsws.ps4.documents.imported;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Info despre un file item.
 */
@XmlRootElement
public class ActNormativ extends BaseModel {
    private String id;
    private String denumire;
    private String descriere;
    private String idTipDocument;
    private Integer nrCrt;
    private String linkFisier;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public String getIdTipDocument() {
        return idTipDocument;
    }

    public void setIdTipDocument(String idTipDocument) {
        this.idTipDocument = idTipDocument;
    }

    public Integer getNrCrt() {
        return nrCrt;
    }

    public void setNrCrt(Integer nrCrt) {
        this.nrCrt = nrCrt;
    }

    public String getLinkFisier() {
        return linkFisier;
    }

    public void setLinkFisier(String linkFisier) {
        this.linkFisier = linkFisier;
    }
}
