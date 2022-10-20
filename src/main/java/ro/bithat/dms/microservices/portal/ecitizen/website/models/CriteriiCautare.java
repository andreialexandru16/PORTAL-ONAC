package ro.bithat.dms.microservices.portal.ecitizen.website.models;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Modeleaza attribute link.
 */
@XmlRootElement
public class CriteriiCautare extends BaseModel {

    private Integer id;
    private String cod_criteriu;
    private String denumire_criteriu;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCod_criteriu() {
        return cod_criteriu;
    }

    public void setCod_criteriu(String cod_criteriu) {
        this.cod_criteriu = cod_criteriu;
    }

    public String getDenumire_criteriu() {
        return denumire_criteriu;
    }

    public void setDenumire_criteriu(String denumire_criteriu) {
        this.denumire_criteriu = denumire_criteriu;
    }
}
