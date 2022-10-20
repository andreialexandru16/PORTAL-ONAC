package ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Filiala extends BaseModel {

    private Integer id;
    private String denumire;
    private String presedinte;
    private Integer activ;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public String getPresedinte() {
        return presedinte;
    }

    public void setPresedinte(String presedinte) {
        this.presedinte = presedinte;
    }

    public Integer getActiv() {
        return activ;
    }

    public void setActiv(Integer activ) {
        this.activ = activ;
    }
}