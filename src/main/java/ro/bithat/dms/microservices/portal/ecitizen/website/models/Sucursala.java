package ro.bithat.dms.microservices.portal.ecitizen.website.models;


public class Sucursala {
    int id;
    int id_tert;
    String nume;
    int id_localitate;
    String localitate;
    String adresa;
    String adresa_corespondenta;
    private Integer id_domeniu;
    private String domeniu;

    public String getDomeniu() {
        return domeniu;
    }

    public void setDomeniu(String domeniu) {
        this.domeniu = domeniu;
    }

    public Integer getId_domeniu() {
        return id_domeniu;
    }

    public void setId_domeniu(Integer id_domeniu) {
        this.id_domeniu = id_domeniu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_tert() {
        return id_tert;
    }

    public void setId_tert(int id_tert) {
        this.id_tert = id_tert;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getId_localitate() {
        return id_localitate;
    }

    public void setId_localitate(int id_localitate) {
        this.id_localitate = id_localitate;
    }

    public String getLocalitate() {
        return localitate;
    }

    public void setLocalitate(String localitate) {
        this.localitate = localitate;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getAdresa_corespondenta() {
        return adresa_corespondenta;
    }

    public void setAdresa_corespondenta(String adresa_corespondenta) {
        this.adresa_corespondenta = adresa_corespondenta;
    }
}