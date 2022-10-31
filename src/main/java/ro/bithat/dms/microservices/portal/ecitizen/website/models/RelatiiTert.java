package ro.bithat.dms.microservices.portal.ecitizen.website.models;

public class RelatiiTert {
    int id;
    int id_denumire_institutie;
    int id_tip_relatie;
    String denumire_institutie;
    String tip_relatie;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_denumire_institutie() {
        return id_denumire_institutie;
    }

    public void setId_denumire_institutie(int id_denumire_institutie) {
        this.id_denumire_institutie = id_denumire_institutie;
    }

    public int getId_tip_relatie() {
        return id_tip_relatie;
    }

    public void setId_tip_relatie(int id_tip_relatie) {
        this.id_tip_relatie = id_tip_relatie;
    }

    public String getDenumire_institutie() {
        return denumire_institutie;
    }

    public void setDenumire_institutie(String denumire_institutie) {
        this.denumire_institutie = denumire_institutie;
    }

    public String getTip_relatie() {
        return tip_relatie;
    }

    public void setTip_relatie(String tip_relatie) {
        this.tip_relatie = tip_relatie;
    }
}
