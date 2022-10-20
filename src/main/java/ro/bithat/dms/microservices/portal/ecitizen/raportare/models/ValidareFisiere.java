package ro.bithat.dms.microservices.portal.ecitizen.raportare.models;


import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ValidareFisiere extends BaseModel {
    private String mesajEroare;
    private String formulaCalculata;
    private Boolean valid;

    public String getFormulaCalculata() {
        return formulaCalculata;
    }

    public void setFormulaCalculata(String formulaCalculata) {
        this.formulaCalculata = formulaCalculata;
    }

    public String getMesajEroare() {
        return mesajEroare;
    }

    public void setMesajEroare(String mesajEroare) {
        this.mesajEroare = mesajEroare;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }
}
