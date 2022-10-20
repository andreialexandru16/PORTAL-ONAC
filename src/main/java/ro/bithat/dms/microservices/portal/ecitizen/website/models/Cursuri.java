package ro.bithat.dms.microservices.portal.ecitizen.website.models;


public class Cursuri {

    int id;
    int id_tert;
    String firma;
    int id_grad;
    String grad;
    String data_certificat_curs;
    String numar_certificat_curs;
    String tip_curs;
    String linkDownload;
    String fisier;

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

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public int getId_grad() {
        return id_grad;
    }

    public void setId_grad(int id_grad) {
        this.id_grad = id_grad;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public String getData_certificat_curs() {
        return data_certificat_curs;
    }

    public void setData_certificat_curs(String data_certificat_curs) {
        this.data_certificat_curs = data_certificat_curs;
    }

    public String getNumar_certificat_curs() {
        return numar_certificat_curs;
    }

    public void setNumar_certificat_curs(String numar_certificat_curs) {
        this.numar_certificat_curs = numar_certificat_curs;
    }

    public String getTip_curs() {
        return tip_curs;
    }

    public void setTip_curs(String tip_curs) {
        this.tip_curs = tip_curs;
    }

    public String getLinkDownload() {
        return linkDownload;
    }

    public void setLinkDownload(String linkDownload) {
        this.linkDownload = linkDownload;
    }

    public String getFisier() {
        return fisier;
    }

    public void setFisier(String fisier) {
        this.fisier = fisier;
    }
}
