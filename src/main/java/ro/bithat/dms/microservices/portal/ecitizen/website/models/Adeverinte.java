package ro.bithat.dms.microservices.portal.ecitizen.website.models;


public class Adeverinte {

    int id;
    int id_tert;
    String numar_adeverinte;
    String data_adeverinta;
    int id_grad_vechime;
    String grad_vechime;
    int id_tip_adeverinta;
    String tip_adeverinta;
    String data_inceput;
    String data_sfarsit;
    int id_firma;
    String firma;
    int status;
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

    public String getNumar_adeverinte() {
        return numar_adeverinte;
    }

    public void setNumar_adeverinte(String numar_adeverinte) {
        this.numar_adeverinte = numar_adeverinte;
    }

    public String getData_adeverinta() {
        return data_adeverinta;
    }

    public void setData_adeverinta(String data_adeverinta) {
        this.data_adeverinta = data_adeverinta;
    }

    public int getId_grad_vechime() {
        return id_grad_vechime;
    }

    public void setId_grad_vechime(int id_grad_vechime) {
        this.id_grad_vechime = id_grad_vechime;
    }

    public String getGrad_vechime() {
        return grad_vechime;
    }

    public void setGrad_vechime(String grad_vechime) {
        this.grad_vechime = grad_vechime;
    }

    public int getId_tip_adeverinta() {
        return id_tip_adeverinta;
    }

    public void setId_tip_adeverinta(int id_tip_adeverinta) {
        this.id_tip_adeverinta = id_tip_adeverinta;
    }

    public String getTip_adeverinta() {
        return tip_adeverinta;
    }

    public void setTip_adeverinta(String tip_adeverinta) {
        this.tip_adeverinta = tip_adeverinta;
    }

    public String getData_inceput() {
        return data_inceput;
    }

    public void setData_inceput(String data_inceput) {
        this.data_inceput = data_inceput;
    }

    public String getData_sfarsit() {
        return data_sfarsit;
    }

    public void setData_sfarsit(String data_sfarsit) {
        this.data_sfarsit = data_sfarsit;
    }

    public int getId_firma() {
        return id_firma;
    }

    public void setId_firma(int id_firma) {
        this.id_firma = id_firma;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
