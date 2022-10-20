package ro.bithat.dms.microservices.dmsws.poi;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Modeleaza un poi file.
 */
@XmlRootElement
public class PoiFile extends BaseModel {
    private Integer idFisier;
    private Integer idPoi;
    private String numeFisier;
    private String downloadLink;

    public Integer getIdFisier() {
        return idFisier;
    }

    public void setIdFisier(Integer idFisier) {
        this.idFisier = idFisier;
    }

    public Integer getIdPoi() {
        return idPoi;
    }

    public void setIdPoi(Integer idPoi) {
        this.idPoi = idPoi;
    }

    public String getNumeFisier() {
        return numeFisier;
    }

    public void setNumeFisier(String numeFisier) {
        this.numeFisier = numeFisier;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }
}
