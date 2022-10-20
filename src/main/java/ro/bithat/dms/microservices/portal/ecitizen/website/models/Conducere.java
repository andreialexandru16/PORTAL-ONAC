package ro.bithat.dms.microservices.portal.ecitizen.website.models;


import ro.bithat.dms.microservices.dmsws.file.BaseModel;

public class Conducere {

    int id;
    int id_tert;
    String nume;
    String prenume;
    String functie;
    String reprez_legal;
    String studii;
    String an_absolvire;
    String termen_expirare_mandat;
    String vechime;
    String nr_adresa_cv;
    String data_adresa_cv;
    String manager;
    String resp_tehnic;

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

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getFunctie() {
        return functie;
    }

    public void setFunctie(String functie) {
        this.functie = functie;
    }

    public String getReprez_legal() {
        return reprez_legal;
    }

    public void setReprez_legal(String reprez_legal) {
        this.reprez_legal = reprez_legal;
    }

    public String getStudii() {
        return studii;
    }

    public void setStudii(String studii) {
        this.studii = studii;
    }

    public String getAn_absolvire() {
        return an_absolvire;
    }

    public void setAn_absolvire(String an_absolvire) {
        this.an_absolvire = an_absolvire;
    }

    public String getTermen_expirare_mandat() {
        return termen_expirare_mandat;
    }

    public void setTermen_expirare_mandat(String termen_expirare_mandat) {
        this.termen_expirare_mandat = termen_expirare_mandat;
    }

    public String getVechime() {
        return vechime;
    }

    public void setVechime(String vechime) {
        this.vechime = vechime;
    }

    public String getNr_adresa_cv() {
        return nr_adresa_cv;
    }

    public void setNr_adresa_cv(String nr_adresa_cv) {
        this.nr_adresa_cv = nr_adresa_cv;
    }

    public String getData_adresa_cv() {
        return data_adresa_cv;
    }

    public void setData_adresa_cv(String data_adresa_cv) {
        this.data_adresa_cv = data_adresa_cv;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getResp_tehnic() {
        return resp_tehnic;
    }

    public void setResp_tehnic(String resp_tehnic) {
        this.resp_tehnic = resp_tehnic;
    }
}
