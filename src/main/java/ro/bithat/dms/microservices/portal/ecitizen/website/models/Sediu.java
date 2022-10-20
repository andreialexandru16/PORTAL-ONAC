package ro.bithat.dms.microservices.portal.ecitizen.website.models;

public class Sediu {
    int id;
    int id_tert;
    String denumire;
    String codCaen;
    String cod_caen;
    String numeTert;
    String nr_reg_com;
    String euid;
    String adresa;
    String localitate_mica;
    Integer id_localitate_mica;
    Integer id_localitate_mare;
    String localitate_mare;
    Integer id_judet;
    String judet;
    Integer cod_postal;

    public String getCod_caen() {
        return cod_caen;
    }

    public void setCod_caen(String cod_caen) {
        this.cod_caen = cod_caen;
    }

    public String getNumeTert() {
        return numeTert;
    }

    public void setNumeTert(String numeTert) {
        this.numeTert = numeTert;
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

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public String getCodCaen() {
        return codCaen;
    }

    public void setCodCaen(String codCaen) {
        this.codCaen = codCaen;
    }

    public String getNr_reg_com() {
        return nr_reg_com;
    }

    public void setNr_reg_com(String nr_reg_com) {
        this.nr_reg_com = nr_reg_com;
    }

    public String getEuid() {
        return euid;
    }

    public void setEuid(String euid) {
        this.euid = euid;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getLocalitate_mica() {
        return localitate_mica;
    }

    public void setLocalitate_mica(String localitate_mica) {
        this.localitate_mica = localitate_mica;
    }

    public Integer getId_localitate_mica() {
        return id_localitate_mica;
    }

    public void setId_localitate_mica(Integer id_localitate_mica) {
        this.id_localitate_mica = id_localitate_mica;
    }

    public Integer getId_localitate_mare() {
        return id_localitate_mare;
    }

    public void setId_localitate_mare(Integer id_localitate_mare) {
        this.id_localitate_mare = id_localitate_mare;
    }

    public String getLocalitate_mare() {
        return localitate_mare;
    }

    public void setLocalitate_mare(String localitate_mare) {
        this.localitate_mare = localitate_mare;
    }

    public Integer getId_judet() {
        return id_judet;
    }

    public void setId_judet(Integer id_judet) {
        this.id_judet = id_judet;
    }

    public String getJudet() {
        return judet;
    }

    public void setJudet(String judet) {
        this.judet = judet;
    }

    public Integer getCod_postal() {
        return cod_postal;
    }

    public void setCod_postal(Integer cod_postal) {
        this.cod_postal = cod_postal;
    }
}