package ro.bithat.dms.microservices.dmsws.file;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class InfoUtilizator extends BaseModel {

    private Integer id;
    private Integer primeAsigBrute;
    private Integer asigurariGenerale;
    private Integer asigurariViata;
    private Integer areRaportare;
    private Integer contribDatFga;
    private Integer asigurareGeneralaContrib;
    private Integer asigurareViataContrib;
    private Integer dobanziPenalitati;
    private Integer sumaVirataTotal;
    private Integer afContrib;
    private Integer asigGeneralAfc;
    private Integer asigViataAfc;
    private Integer aferentaDbPen;
    private Integer existaRaportareLunaSelectata;
    private Float cotaGen;
    private Float cotaViata;
    private String numeSocietate;
    private Integer idLocalitate;
    private String strada;
    private String nr_strada;
    private String bloc;
    private String scara;
    private String etaj;
    private String apartament;
    private String pers_contact;
    private String telefon;
    private String email;

    public Integer getIdLocalitate() {
        return idLocalitate;
    }

    public void setIdLocalitate(Integer idLocalitate) {
        this.idLocalitate = idLocalitate;
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

    public String getEtaj() {
        return etaj;
    }

    public void setEtaj(String etaj) {
        this.etaj = etaj;
    }

    public String getApartament() {
        return apartament;
    }

    public void setApartament(String apartament) {
        this.apartament = apartament;
    }

    public String getPers_contact() {
        return pers_contact;
    }

    public void setPers_contact(String pers_contact) {
        this.pers_contact = pers_contact;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumeSocietate() {
        return numeSocietate;
    }

    public void setNumeSocietate(String numeSocietate) {
        this.numeSocietate = numeSocietate;
    }

    public Float getCotaGen() {
        return cotaGen;
    }

    public void setCotaGen(Float cotaGen) {
        this.cotaGen = cotaGen;
    }

    public Float getCotaViata() {
        return cotaViata;
    }

    public void setCotaViata(Float cotaViata) {
        this.cotaViata = cotaViata;
    }

    public Integer getExistaRaportareLunaSelectata() {
        return existaRaportareLunaSelectata;
    }

    public void setExistaRaportareLunaSelectata(Integer existaRaportareLunaSelectata) {
        this.existaRaportareLunaSelectata = existaRaportareLunaSelectata;
    }

    public Integer getAferentaDbPen() {
        return aferentaDbPen;
    }

    public void setAferentaDbPen(Integer aferentaDbPen) {
        this.aferentaDbPen = aferentaDbPen;
    }

    public Integer getAsigViataAfc() {
        return asigViataAfc;
    }

    public void setAsigViataAfc(Integer asigViataAfc) {
        this.asigViataAfc = asigViataAfc;
    }

    public Integer getAsigGeneralAfc() {
        return asigGeneralAfc;
    }

    public void setAsigGeneralAfc(Integer asigGeneralAfc) {
        this.asigGeneralAfc = asigGeneralAfc;
    }

    public Integer getAfContrib() {
        return afContrib;
    }

    public void setAfContrib(Integer afContrib) {
        this.afContrib = afContrib;
    }

    public Integer getSumaVirataTotal() {
        return sumaVirataTotal;
    }

    public void setSumaVirataTotal(Integer sumaVirataTotal) {
        this.sumaVirataTotal = sumaVirataTotal;
    }

    public Integer getDobanziPenalitati() {
        return dobanziPenalitati;
    }

    public void setDobanziPenalitati(Integer dobanziPenalitati) {
        this.dobanziPenalitati = dobanziPenalitati;
    }

    public Integer getAsigurareViataContrib() {
        return asigurareViataContrib;
    }

    public void setAsigurareViataContrib(Integer asigurareViataContrib) {
        this.asigurareViataContrib = asigurareViataContrib;
    }

    public Integer getAsigurareGeneralaContrib() {
        return asigurareGeneralaContrib;
    }

    public void setAsigurareGeneralaContrib(Integer asigurareGeneralaContrib) {
        this.asigurareGeneralaContrib = asigurareGeneralaContrib;
    }

    public Integer getContribDatFga() {
        return contribDatFga;
    }

    public void setContribDatFga(Integer contribDatFga) {
        this.contribDatFga = contribDatFga;
    }

    public Integer getAreRaportare() {
        return areRaportare;
    }

    public void setAreRaportare(Integer areRaportare) {
        this.areRaportare = areRaportare;
    }

    public Integer getAsigurariViata() {
        return asigurariViata;
    }

    public void setAsigurariViata(Integer asigurariViata) {
        this.asigurariViata = asigurariViata;
    }

    public Integer getAsigurariGenerale() {
        return asigurariGenerale;
    }

    public void setAsigurariGenerale(Integer asigurariGenerale) {
        this.asigurariGenerale = asigurariGenerale;
    }

    public Integer getPrimeAsigBrute() {
        return primeAsigBrute;
    }

    public void setPrimeAsigBrute(Integer primeAsigBrute) {
        this.primeAsigBrute = primeAsigBrute;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}


