package ro.bithat.dms.microservices.dmsws.ps4.documents.imported;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;
import ro.bithat.dms.microservices.dmsws.flow.StandardResponse;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class Completari extends BaseModel{
    private Integer idFisierCerere;
    private List<String> fisiereIncarcate;
    private String error;
    private String idChangeRequire;
    private String idChangeComplete;

    public Integer getIdFisierCerere() {
        return idFisierCerere;
    }

    public void setIdFisierCerere(Integer idFisierCerere) {
        this.idFisierCerere = idFisierCerere;
    }

    public List<String> getFisiereIncarcate() {
        return fisiereIncarcate;
    }

    public void setFisiereIncarcate(List<String> fisiereIncarcate) {
        this.fisiereIncarcate = fisiereIncarcate;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getIdChangeRequire() {
        return idChangeRequire;
    }

    public void setIdChangeRequire(String idChangeRequire) {
        this.idChangeRequire = idChangeRequire;
    }

    public String getIdChangeComplete() {
        return idChangeComplete;
    }

    public void setIdChangeComplete(String idChangeComplete) {
        this.idChangeComplete = idChangeComplete;
    }
}
