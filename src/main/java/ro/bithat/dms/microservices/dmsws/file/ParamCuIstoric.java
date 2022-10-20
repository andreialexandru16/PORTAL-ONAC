package ro.bithat.dms.microservices.dmsws.file;


import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ParamCuIstoric extends BaseModel {


    private Integer id;
    private String cod;
    private String descriere;
    private Double valoare;
    private Integer activ;
    private String deLaData;
    private String panaLaData;
    private String creatDe;
    private String creatLa;
    private String modificatDe;
    private String modificatLa;
    private Integer idUnitate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public Double getValoare() {
        return valoare;
    }

    public void setValoare(Double valoare) {
        this.valoare = valoare;
    }

    public Integer getActiv() {
        return activ;
    }

    public void setActiv(Integer activ) {
        this.activ = activ;
    }

    public String getDeLaData() {
        return deLaData;
    }

    public void setDeLaData(String deLaData) {
        this.deLaData = deLaData;
    }

    public String getPanaLaData() {
        return panaLaData;
    }

    public void setPanaLaData(String panaLaData) {
        this.panaLaData = panaLaData;
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

    public Integer getIdUnitate() {
        return idUnitate;
    }

    public void setIdUnitate(Integer idUnitate) {
        this.idUnitate = idUnitate;
    }
}
