package ro.bithat.dms.microservices.portal.ecitizen.project.backend.bithat;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Modeleaza persoana din v_persoana
 */
@XmlRootElement
public class Persoana extends BaseModel {
    private Integer id;
    private Integer cnp;
    private String nume;
    private String contact;
    private Integer idDepartament;
    private Integer idFunctie;
    private Integer idUtilizator;
    private Integer idUnitate;
    private Integer idTipAct;
    private String serieAct;
    private String nrAct;
    private String prenume;
    private String email;
    private String utilizator;
    private String persoana;
    private Integer status;
    private String numeComplet;
    private String usernameLDAP;
    private String departament;
    private String functie;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFunctie() {
        return functie;
    }

    public void setFunctie(String functie) {
        this.functie = functie;
    }

    public Integer getCnp() {
        return cnp;
    }

    public void setCnp(Integer cnp) {
        this.cnp = cnp;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Integer getIdDepartament() {
        return idDepartament;
    }

    public void setIdDepartament(Integer idDepartament) {
        this.idDepartament = idDepartament;
    }

    public Integer getIdFunctie() {
        return idFunctie;
    }

    public void setIdFunctie(Integer idFunctie) {
        this.idFunctie = idFunctie;
    }

    public Integer getIdUtilizator() {
        return idUtilizator;
    }

    public void setIdUtilizator(Integer idUtilizator) {
        this.idUtilizator = idUtilizator;
    }

    public Integer getIdUnitate() {
        return idUnitate;
    }

    public void setIdUnitate(Integer idUnitate) {
        this.idUnitate = idUnitate;
    }

    public Integer getIdTipAct() {
        return idTipAct;
    }

    public void setIdTipAct(Integer idTipAct) {
        this.idTipAct = idTipAct;
    }

    public String getSerieAct() {
        return serieAct;
    }

    public void setSerieAct(String serieAct) {
        this.serieAct = serieAct;
    }

    public String getNrAct() {
        return nrAct;
    }

    public void setNrAct(String nrAct) {
        this.nrAct = nrAct;
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

    public String getUtilizator() {
        return utilizator;
    }

    public void setUtilizator(String utilizator) {
        this.utilizator = utilizator;
    }

    public String getPersoana() {
        return persoana;
    }

    public void setPersoana(String persoana) {
        this.persoana = persoana;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getNumeComplet() {
        return numeComplet;
    }

    public void setNumeComplet(String numeComplet) {
        this.numeComplet = numeComplet;
    }

    public String getUsernameLDAP() {
        return usernameLDAP;
    }

    public void setUsernameLDAP(String usernameLDAP) {
        this.usernameLDAP = usernameLDAP;
    }

    public String getDepartament() {
        return departament;
    }

    public void setDepartament(String departament) {
        this.departament = departament;
    }
}
