package ro.bithat.dms.microservices.portal.ecitizen.project.backend.bithat;

import org.joda.time.DateTime;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Modeleaza un wbs.
 */
@XmlRootElement
public class Wbs {
    private Integer id;
    private Integer id_unitate;
    private Integer id_proiect;
    private String cod;
    private String denumire;
    private String descriere;
    private Integer buget;
    private String moneda;
    private Integer efort;
    private Double realizat_buget;
    private Double realizat_efort;
    private String unit_masura;
    private Double durata_in_minute;
    private DateTime de_la;
    private DateTime pana_la;
    private String de_la_str;
    private String pana_la_str;
    private String creat_de;
    private DateTime creat_la;
    private String creat_la_str;
    private String modificat_de;
    private DateTime modificat_la;
    private String modificat_la_str;
    private Integer pozitie;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_unitate() {
        return id_unitate;
    }

    public void setId_unitate(Integer id_unitate) {
        this.id_unitate = id_unitate;
    }

    public Integer getId_proiect() {
        return id_proiect;
    }

    public void setId_proiect(Integer id_proiect) {
        this.id_proiect = id_proiect;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public Integer getBuget() {
        return buget;
    }

    public void setBuget(Integer buget) {
        this.buget = buget;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public Integer getEfort() {
        return efort;
    }

    public void setEfort(Integer efort) {
        this.efort = efort;
    }

    public Double getRealizat_buget() {
        return realizat_buget;
    }

    public void setRealizat_buget(Double realizat_buget) {
        this.realizat_buget = realizat_buget;
    }

    public Double getRealizat_efort() {
        return realizat_efort;
    }

    public void setRealizat_efort(Double realizat_efort) {
        this.realizat_efort = realizat_efort;
    }

    public String getUnit_masura() {
        return unit_masura;
    }

    public void setUnit_masura(String unit_masura) {
        this.unit_masura = unit_masura;
    }

    public DateTime getDe_la() {
        return de_la;
    }

    public void setDe_la(DateTime de_la) {
        this.de_la = de_la;
    }

    public DateTime getPana_la() {
        return pana_la;
    }

    public void setPana_la(DateTime pana_la) {
        this.pana_la = pana_la;
    }

    public String getDe_la_str() {
        return de_la_str;
    }

    public void setDe_la_str(String de_la_str) {
        this.de_la_str = de_la_str;
    }

    public String getPana_la_str() {
        return pana_la_str;
    }

    public void setPana_la_str(String pana_la_str) {
        this.pana_la_str = pana_la_str;
    }

    public String getCreat_de() {
        return creat_de;
    }

    public void setCreat_de(String creat_de) {
        this.creat_de = creat_de;
    }

    public DateTime getCreat_la() {
        return creat_la;
    }

    public void setCreat_la(DateTime creat_la) {
        this.creat_la = creat_la;
    }

    public String getCreat_la_str() {
        return creat_la_str;
    }

    public void setCreat_la_str(String creat_la_str) {
        this.creat_la_str = creat_la_str;
    }

    public String getModificat_de() {
        return modificat_de;
    }

    public void setModificat_de(String modificat_de) {
        this.modificat_de = modificat_de;
    }

    public DateTime getModificat_la() {
        return modificat_la;
    }

    public void setModificat_la(DateTime modificat_la) {
        this.modificat_la = modificat_la;
    }

    public Double getDurata_in_minute() {
        return durata_in_minute;
    }

    public void setDurata_in_minute(Double durata_in_minute) {
        this.durata_in_minute = durata_in_minute;
    }

    public String getModificat_la_str() {
        return modificat_la_str;
    }

    public void setModificat_la_str(String modificat_la_str) {
        this.modificat_la_str = modificat_la_str;
    }

    public Integer getPozitie() {
        return pozitie;
    }

    public void setPozitie(Integer pozitie) {
        this.pozitie = pozitie;
    }
}
