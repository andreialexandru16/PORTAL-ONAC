package ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat;

import javax.xml.bind.annotation.XmlRootElement;
import ro.bithat.dms.microservices.dmsws.file.BaseModel;

/**
 * Modeleaza persoana din v_persoana
 */
@XmlRootElement
public class UtilizatorAcOe extends  BaseModel{

    private Integer id;
    private Integer id_tip_utilizator;
    private Integer id_institutie_solicitanta;
    private Integer id_tip_ordonator_credite;
    private String website;
    private String tl_sediu;
    private String fax;
    private String nume_rp;
    private String prenume_rp;
    private String functie_rp;
    private String email_rp;
    private String nume_c1;
    private String prenume_c1;
    private String email_c1;
    private String telefon_c1;
    private String nume_c2;
    private String prenume_c2;
    private String email_c2;
    private String telefon_c2;
    private Integer id_judet;
    private Integer id_localitate;
    private String strada;
    private String nr_strada;
    private String bloc;
    private String scara;
    private String apartament;
    private String etaj;
    private String cod;
    private String parola;
    private Integer id_unitate;

    public Integer getId_unitate() {
        return id_unitate;
    }

    public void setId_unitate(Integer id_unitate) {
        this.id_unitate = id_unitate;
    }

    public Integer getId_institutie_solicitanta() {
        return id_institutie_solicitanta;
    }

    public void setId_institutie_solicitanta(Integer id_institutie_solicitanta) {
        this.id_institutie_solicitanta = id_institutie_solicitanta;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_tip_utilizator() {
        return id_tip_utilizator;
    }

    public void setId_tip_utilizator(Integer id_tip_utilizator) {
        this.id_tip_utilizator = id_tip_utilizator;
    }

    public Integer getId_tip_ordonator_credite() {
        return id_tip_ordonator_credite;
    }

    public void setId_tip_ordonator_credite(Integer id_tip_ordonator_credite) {
        this.id_tip_ordonator_credite = id_tip_ordonator_credite;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getTl_sediu() {
        return tl_sediu;
    }

    public void setTl_sediu(String tl_sediu) {
        this.tl_sediu = tl_sediu;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getNume_rp() {
        return nume_rp;
    }

    public void setNume_rp(String nume_rp) {
        this.nume_rp = nume_rp;
    }

    public String getPrenume_rp() {
        return prenume_rp;
    }

    public void setPrenume_rp(String prenume_rp) {
        this.prenume_rp = prenume_rp;
    }

    public String getFunctie_rp() {
        return functie_rp;
    }

    public void setFunctie_rp(String functie_rp) {
        this.functie_rp = functie_rp;
    }

    public String getEmail_rp() {
        return email_rp;
    }

    public void setEmail_rp(String email_rp) {
        this.email_rp = email_rp;
    }

    public String getNume_c1() {
        return nume_c1;
    }

    public void setNume_c1(String nume_c1) {
        this.nume_c1 = nume_c1;
    }

    public String getPrenume_c1() {
        return prenume_c1;
    }

    public void setPrenume_c1(String prenume_c1) {
        this.prenume_c1 = prenume_c1;
    }

    public String getEmail_c1() {
        return email_c1;
    }

    public void setEmail_c1(String email_c1) {
        this.email_c1 = email_c1;
    }

    public String getTelefon_c1() {
        return telefon_c1;
    }

    public void setTelefon_c1(String telefon_c1) {
        this.telefon_c1 = telefon_c1;
    }

    public String getNume_c2() {
        return nume_c2;
    }

    public void setNume_c2(String nume_c2) {
        this.nume_c2 = nume_c2;
    }

    public String getPrenume_c2() {
        return prenume_c2;
    }

    public void setPrenume_c2(String prenume_c2) {
        this.prenume_c2 = prenume_c2;
    }

    public String getEmail_c2() {
        return email_c2;
    }

    public void setEmail_c2(String email_c2) {
        this.email_c2 = email_c2;
    }

    public String getTelefon_c2() {
        return telefon_c2;
    }

    public void setTelefon_c2(String telefon_c2) {
        this.telefon_c2 = telefon_c2;
    }

    public Integer getId_judet() {
        return id_judet;
    }

    public void setId_judet(Integer id_judet) {
        this.id_judet = id_judet;
    }

    public Integer getId_localitate() {
        return id_localitate;
    }

    public void setId_localitate(Integer id_localitate) {
        this.id_localitate = id_localitate;
    }

    public String getStrada() {
        return strada;
    }

    public void setStrada(String strada) {
        this.strada = strada;
    }

    public String getNr_strada() {
        return nr_strada;
    }

    public void setNr_strada(String nr_strada) {
        this.nr_strada = nr_strada;
    }

    public String getBloc() {
        return bloc;
    }

    public void setBloc(String bloc) {
        this.bloc = bloc;
    }

    public String getScara() {
        return scara;
    }

    public void setScara(String scara) {
        this.scara = scara;
    }

    public String getApartament() {
        return apartament;
    }

    public void setApartament(String apartament) {
        this.apartament = apartament;
    }

    public String getEtaj() {
        return etaj;
    }

    public void setEtaj(String etaj) {
        this.etaj = etaj;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }
}
