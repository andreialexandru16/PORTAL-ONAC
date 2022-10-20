package ro.bithat.dms.microservices.portal.ecitizen.website.models;

import ro.bithat.dms.microservices.dmsws.flow.StandardResponse;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement

public class Licenta {
    private Integer id;
    private String societate;
    private String sediu;
    private String localitate;
    private String judet;
    private String telefon_fax;
    private String nr_licenta;
    private String data_emitere;
    private String data_expirare;
    private String stare;
    private String tip_licenta;
    private String tip_activitate;
    private String comentariu;
    private String nr_decizie;
    private String tip_repartitoare_contoare;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSocietate() {
        return societate;
    }

    public void setSocietate(String societate) {
        this.societate = societate;
    }

    public String getSediu() {
        return sediu;
    }

    public void setSediu(String sediu) {
        this.sediu = sediu;
    }

    public String getLocalitate() {
        return localitate;
    }

    public void setLocalitate(String localitate) {
        this.localitate = localitate;
    }

    public String getJudet() {
        return judet;
    }

    public void setJudet(String judet) {
        this.judet = judet;
    }

    public String getTelefon_fax() {
        return telefon_fax;
    }

    public void setTelefon_fax(String telefon_fax) {
        this.telefon_fax = telefon_fax;
    }

    public String getNr_licenta() {
        return nr_licenta;
    }

    public void setNr_licenta(String nr_licenta) {
        this.nr_licenta = nr_licenta;
    }

    public String getData_emitere() {
        return data_emitere;
    }

    public void setData_emitere(String data_emitere) {
        this.data_emitere = data_emitere;
    }

    public String getData_expirare() {
        return data_expirare;
    }

    public void setData_expirare(String data_expirare) {
        this.data_expirare = data_expirare;
    }

    public String getStare() {
        return stare;
    }

    public void setStare(String stare) {
        this.stare = stare;
    }

    public String getTip_licenta() {
        return tip_licenta;
    }

    public void setTip_licenta(String tip_licenta) {
        this.tip_licenta = tip_licenta;
    }

    public String getTip_activitate() {
        return tip_activitate;
    }

    public void setTip_activitate(String tip_activitate) {
        this.tip_activitate = tip_activitate;
    }

    public String getComentariu() {
        return comentariu;
    }

    public void setComentariu(String comentariu) {
        this.comentariu = comentariu;
    }

    public String getNr_decizie() {
        return nr_decizie;
    }

    public void setNr_decizie(String nr_decizie) {
        this.nr_decizie = nr_decizie;
    }

    public String getTip_repartitoare_contoare() {
        return tip_repartitoare_contoare;
    }

    public void setTip_repartitoare_contoare(String tip_repartitoare_contoare) {
        this.tip_repartitoare_contoare = tip_repartitoare_contoare;
    }
}
