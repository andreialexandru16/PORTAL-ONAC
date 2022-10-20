package ro.bithat.dms.microservices.portal.ecitizen.website.models;

/**
 * Created by Infrasoft34 on 1/10/2022.
 */
public class PpnAdresant {
    private String adresa;
    private String cnpcui;
    private String email;
    private String judet;
    private String telefon;
    private String nume;
    private String oras;
    private String tara;

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getCnpcui() {
        return cnpcui;
    }

    public void setCnpcui(String cnpcui) {
        this.cnpcui = cnpcui;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJudet() {
        return judet;
    }

    public void setJudet(String judet) {
        this.judet = judet;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getOras() {
        return oras;
    }

    public void setOras(String oras) {
        this.oras = oras;
    }

    public String getTara() {
        return tara;
    }

    public void setTara(String tara) {
        this.tara = tara;
    }
}
