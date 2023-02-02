
package ro.bithat.dms.microservices.portal.ecitizen.website.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FisierDraftExtended extends FisierDraft {
   private String nume;
   private String denumireDocument;
   private String creatLa;
   private boolean perioadaActiva;
    private Integer idDocument;
    private Integer idTipDocument;

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getDenumireDocument() {
        return denumireDocument;
    }

    public void setDenumireDocument(String denumireDocument) {
        this.denumireDocument = denumireDocument;
    }

    public String getCreatLa() {
        return creatLa;
    }

    public void setCreatLa(String creatLa) {
        this.creatLa = creatLa;
    }

    public boolean isPerioadaActiva() {
        return perioadaActiva;
    }

    public void setPerioadaActiva(boolean perioadaActiva) {
        this.perioadaActiva = perioadaActiva;
    }

    public Integer getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(Integer idDocument) {
        this.idDocument = idDocument;
    }

    public Integer getIdTipDocument() {
        return idTipDocument;
    }

    public void setIdTipDocument(Integer idTipDocument) {
        this.idTipDocument = idTipDocument;
    }
}
