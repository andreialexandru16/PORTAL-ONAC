package ro.bithat.dms.microservices.portal.ecitizen.website.models;

public class PersoaneContact {
    int id;
    String nume;
    String descriere;
    String email;
    String telefon;
    String fax;
    Integer reprezentant;
    Integer imputernicit;
    String functie;
    String departament;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
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

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public Integer getReprezentant() {
        return reprezentant;
    }

    public void setReprezentant(Integer reprezentant) {
        this.reprezentant = reprezentant;
    }

    public Integer getImputernicit() {
        return imputernicit;
    }

    public void setImputernicit(Integer imputernicit) {
        this.imputernicit = imputernicit;
    }

    public String getFunctie() {
        return functie;
    }

    public void setFunctie(String functie) {
        this.functie = functie;
    }

    public String getDepartament() {
        return departament;
    }

    public void setDepartament(String departament) {
        this.departament = departament;
    }
}
