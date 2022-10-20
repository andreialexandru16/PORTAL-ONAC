package ro.bithat.dms.microservices.portal.ecitizen.website.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PpnInfoRegistraturaReq {
    private String adresaAdresant;
    private String cnpcuiAdresant;
    private String cuprins;
    private String emailAdresant;
    private String judetAdresant;
    private String numarTelefonAdresant;
    private String numeAdresant;
    private String orasAdresant;
    private String taraAdresant;

    @Override
    public String toString() {
        return "PpnInfoRegistraturaReq{" +
                "adresaAdresant=" + adresaAdresant +
                ", cnpcuiAdresant='" + cnpcuiAdresant + '\'' +
                ", cuprins='" + cuprins + '\'' +
                ", emailAdresant='" + emailAdresant + '\'' +
                ", judetAdresant='" + judetAdresant + '\'' +
                ", numarTelefonAdresant='" + numarTelefonAdresant + '\'' +
                ", numeAdresant='" + numeAdresant + '\'' +
                ", orasAdresant='" + orasAdresant + '\'' +
                ", taraAdresant='" + taraAdresant + '\''+
                '}';
    }

    public String getAdresaAdresant() {
        return adresaAdresant;
    }

    public void setAdresaAdresant(String adresaAdresant) {
        this.adresaAdresant = adresaAdresant;
    }

    public String getCnpcuiAdresant() {
        return cnpcuiAdresant;
    }

    public void setCnpcuiAdresant(String cnpcuiAdresant) {
        this.cnpcuiAdresant = cnpcuiAdresant;
    }

    public String getCuprins() {
        return cuprins;
    }

    public void setCuprins(String cuprins) {
        this.cuprins = cuprins;
    }

    public String getEmailAdresant() {
        return emailAdresant;
    }

    public void setEmailAdresant(String emailAdresant) {
        this.emailAdresant = emailAdresant;
    }

    public String getJudetAdresant() {
        return judetAdresant;
    }

    public void setJudetAdresant(String judetAdresant) {
        this.judetAdresant = judetAdresant;
    }

    public String getNumarTelefonAdresant() {
        return numarTelefonAdresant;
    }

    public void setNumarTelefonAdresant(String numarTelefonAdresant) {
        this.numarTelefonAdresant = numarTelefonAdresant;
    }

    public String getNumeAdresant() {
        return numeAdresant;
    }

    public void setNumeAdresant(String numeAdresant) {
        this.numeAdresant = numeAdresant;
    }

    public String getOrasAdresant() {
        return orasAdresant;
    }

    public void setOrasAdresant(String orasAdresant) {
        this.orasAdresant = orasAdresant;
    }

    public String getTaraAdresant() {
        return taraAdresant;
    }

    public void setTaraAdresant(String taraAdresant) {
        this.taraAdresant = taraAdresant;
    }
}
