package ro.bithat.dms.microservices.portal.ecitizen.website.models;

import org.joda.time.DateTime;
import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Modeleaza attribute link.
 */
@XmlRootElement
public class CertificatConstatator {

    private String numarCertificatConstatator;
    private String dataEmitereCertificatConstatator;
    private Integer idJudetCertificatConstatator;
    private Integer idStareaFirmei;
    private String durataDeConstituire;
    private Double capitalulSocial;
    private String codCaenStr;
    private String info;

    public Integer getIdStareaFirmei() {
        return idStareaFirmei;
    }

    public void setIdStareaFirmei(Integer idStareaFirmei) {
        this.idStareaFirmei = idStareaFirmei;
    }

    public String getDurataDeConstituire() {
        return durataDeConstituire;
    }

    public void setDurataDeConstituire(String durataDeConstituire) {
        this.durataDeConstituire = durataDeConstituire;
    }

    public Double getCapitalulSocial() {
        return capitalulSocial;
    }

    public void setCapitalulSocial(Double capitalulSocial) {
        this.capitalulSocial = capitalulSocial;
    }

    public String getCodCaenStr() {
        return codCaenStr;
    }

    public void setCodCaenStr(String codCaenStr) {
        this.codCaenStr = codCaenStr;
    }

    public String getNumarCertificatConstatator() {
        return numarCertificatConstatator;
    }

    public void setNumarCertificatConstatator(String numarCertificatConstatator) {
        this.numarCertificatConstatator = numarCertificatConstatator;
    }

    public String getDataEmitereCertificatConstatator() {
        return dataEmitereCertificatConstatator;
    }

    public void setDataEmitereCertificatConstatator(String dataEmitereCertificatConstatator) {
        this.dataEmitereCertificatConstatator = dataEmitereCertificatConstatator;
    }

    public Integer getIdJudetCertificatConstatator() {
        return idJudetCertificatConstatator;
    }

    public void setIdJudetCertificatConstatator(Integer idJudetCertificatConstatator) {
        this.idJudetCertificatConstatator = idJudetCertificatConstatator;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
