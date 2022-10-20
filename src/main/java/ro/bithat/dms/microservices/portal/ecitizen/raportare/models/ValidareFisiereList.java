package ro.bithat.dms.microservices.portal.ecitizen.raportare.models;


import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class ValidareFisiereList extends BaseModel {
    private List<ValidareFisiere> validareFisiereList;
    private Boolean validTotal;
    private String mesajFinal;

    public Boolean getValidTotal() {
        return validTotal;
    }

    public void setValidTotal(Boolean validTotal) {
        this.validTotal = validTotal;
    }

    public String getMesajFinal() {
        return mesajFinal;
    }

    public void setMesajFinal(String mesajFinal) {
        this.mesajFinal = mesajFinal;
    }

    public List<ValidareFisiere> getValidareFisiereList() {
        return validareFisiereList;
    }

    public void setValidareFisiereList(List<ValidareFisiere> validareFisiereList) {
        this.validareFisiereList = validareFisiereList;
    }
}
