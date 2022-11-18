package ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Modeleaza persoana din v_persoana
 */
@XmlRootElement
public class UtilizatorContact extends  BaseModel{

    private Integer id;
    private String nume;
    private String prenume;
    private String email;
    private String telefon;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }
}
