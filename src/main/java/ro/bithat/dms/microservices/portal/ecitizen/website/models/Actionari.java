package ro.bithat.dms.microservices.portal.ecitizen.website.models;

public class Actionari {
    int id;
    String actionar;
    String tip_capital;
    Float procente;
    int nr_actiuni;
    float valoare;
    String moneda;

    int id_moneda;
    int id_tip_capital;


    public int getId_moneda() {
        return id_moneda;
    }

    public void setId_moneda(int id_moneda) {
        this.id_moneda = id_moneda;
    }

    public int getId_tip_capital() {
        return id_tip_capital;
    }

    public void setId_tip_capital(int id_tip_capital) {
        this.id_tip_capital = id_tip_capital;
    }

    public String getActionar() {
        return actionar;
    }

    public void setActionar(String actionar) {
        this.actionar = actionar;
    }

    public String getTip_capital() {
        return tip_capital;
    }

    public void setTip_capital(String tip_capital) {
        this.tip_capital = tip_capital;
    }

    public Float getProcente() {
        return procente;
    }

    public void setProcente(Float procente) {
        this.procente = procente;
    }

    public int getNr_actiuni() {
        return nr_actiuni;
    }

    public void setNr_actiuni(int nr_actiuni) {
        this.nr_actiuni = nr_actiuni;
    }

    public float getValoare() {
        return valoare;
    }

    public void setValoare(float valoare) {
        this.valoare = valoare;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
