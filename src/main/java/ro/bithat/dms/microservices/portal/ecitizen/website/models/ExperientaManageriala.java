package ro.bithat.dms.microservices.portal.ecitizen.website.models;

public class ExperientaManageriala {
    int id;
    int id_persoana_conducere;
    String firma;
    String functie;
    String de_la;
    String pana_la;
    String activitati_desfasurate;
    int prezent;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_persoana_conducere() {
        return id_persoana_conducere;
    }

    public void setId_persoana_conducere(int id_persoana_conducere) {
        this.id_persoana_conducere = id_persoana_conducere;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public String getFunctie() {
        return functie;
    }

    public void setFunctie(String functie) {
        this.functie = functie;
    }

    public String getDe_la() {
        return de_la;
    }

    public void setDe_la(String de_la) {
        this.de_la = de_la;
    }

    public String getPana_la() {
        return pana_la;
    }

    public void setPana_la(String pana_la) {
        this.pana_la = pana_la;
    }

    public String getActivitati_desfasurate() {
        return activitati_desfasurate;
    }

    public void setActivitati_desfasurate(String activitati_desfasurate) {
        this.activitati_desfasurate = activitati_desfasurate;
    }

    public int getPrezent() {
        return prezent;
    }

    public void setPrezent(int prezent) {
        this.prezent = prezent;
    }
}
