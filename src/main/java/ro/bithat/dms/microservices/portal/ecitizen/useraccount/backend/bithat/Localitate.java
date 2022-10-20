package ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat;


import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Localitate extends BaseModel {

    private Integer id;
    private String denumire;
    private Integer id_judet;
    private String creatDe;
    private String creatLa;
    private String modificatDe;
    private String modificatLa;
    private String cod;
    private Integer activ;
    private Integer id_tara;
    private String nume_tara;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getActiv() {
        return activ;
    }

    public void setActiv(Integer activ) {
        this.activ = activ;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public Integer getId_judet() {
        return id_judet;
    }

    public void setId_judet(Integer id_judet) {
        this.id_judet = id_judet;
    }

    public String getCreatDe() {
        return creatDe;
    }

    public void setCreatDe(String creatDe) {
        this.creatDe = creatDe;
    }

    public String getCreatLa() {
        return creatLa;
    }

    public void setCreatLa(String creatLa) {
        this.creatLa = creatLa;
    }

    public String getModificatDe() {
        return modificatDe;
    }

    public void setModificatDe(String modificatDe) {
        this.modificatDe = modificatDe;
    }

    public String getModificatLa() {
        return modificatLa;
    }

    public void setModificatLa(String modificatLa) {
        this.modificatLa = modificatLa;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public Integer getId_tara() {
        return id_tara;
    }

    public void setId_tara(Integer id_tara) {
        this.id_tara = id_tara;
    }

    public String getNume_tara() {
        return nume_tara;
    }

    public void setNume_tara(String nume_tara) {
        this.nume_tara = nume_tara;
    }
}
