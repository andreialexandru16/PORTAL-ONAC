package ro.bithat.dms.microservices.portal.ecitizen.project.backend.bithat;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Modeleaza impact tichet
 */
@XmlRootElement
public class Impact {
    private Integer id;
    private String denumire;
    private String cod;
    private Integer pozitie;
    private String idUnitate;

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

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public Integer getPozitie() {
        return pozitie;
    }

    public void setPozitie(Integer pozitie) {
        this.pozitie = pozitie;
    }

    public String getIdUnitate() {
        return idUnitate;
    }

    public void setIdUnitate(String idUnitate) {
        this.idUnitate = idUnitate;
    }
}
