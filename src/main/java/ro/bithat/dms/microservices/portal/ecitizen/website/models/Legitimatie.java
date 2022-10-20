package ro.bithat.dms.microservices.portal.ecitizen.website.models;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Legitimatie extends BaseModel {


    private Integer id;
    private String nume_complet;
    private String grad;
    private String localitate;
    private String judet ;
    private String nr_legitimatie;
    private String data_emitere;
    private String data_expirare;
    private String stare_tiparire;
    private String societate;
    private String stare_dosar;
    private String sesiune;
    private String centru;
    private String observatii;
    private String localitate_centru;
    private String sala;
    private String data_ora_examen;
    private String rezultat;
    private String firma;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNume_complet() {
        return nume_complet;
    }

    public void setNume_complet(String nume_complet) {
        this.nume_complet = nume_complet;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
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

    public String getNr_legitimatie() {
        return nr_legitimatie;
    }

    public void setNr_legitimatie(String nr_legitimatie) {
        this.nr_legitimatie = nr_legitimatie;
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

    public String getStare_tiparire() {
        return stare_tiparire;
    }

    public void setStare_tiparire(String stare_tiparire) {
        this.stare_tiparire = stare_tiparire;
    }

    public String getSocietate() {
        return societate;
    }

    public void setSocietate(String societate) {
        this.societate = societate;
    }

    public String getStare_dosar() {
        return stare_dosar;
    }

    public void setStare_dosar(String stare_dosar) {
        this.stare_dosar = stare_dosar;
    }

    public String getSesiune() {
        return sesiune;
    }

    public void setSesiune(String sesiune) {
        this.sesiune = sesiune;
    }

    public String getCentru() {
        return centru;
    }

    public void setCentru(String centru) {
        this.centru = centru;
    }

    public String getObservatii() {
        return observatii;
    }

    public void setObservatii(String observatii) {
        this.observatii = observatii;
    }

    public String getLocalitate_centru() {
        return localitate_centru;
    }

    public void setLocalitate_centru(String localitate_centru) {
        this.localitate_centru = localitate_centru;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public String getData_ora_examen() {
        return data_ora_examen;
    }

    public void setData_ora_examen(String data_ora_examen) {
        this.data_ora_examen = data_ora_examen;
    }

    public String getRezultat() {
        return rezultat;
    }

    public void setRezultat(String rezultat) {
        this.rezultat = rezultat;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }
}
