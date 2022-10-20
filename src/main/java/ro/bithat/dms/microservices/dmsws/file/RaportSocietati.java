package ro.bithat.dms.microservices.dmsws.file;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RaportSocietati extends BaseModel {

    private Integer id;
    private Float primeAsigBrute;
    private Float asigurariGenerale;
    private Float asigurariViata;
    private Integer areRaportare;
    private Float contribDatFga;
    private Float asigurareGeneralaContrib;
    private Float asigurareViataContrib;
    private Float dobanziPenalitati;
    private Float sumaVirataTotal;
    private Float afContrib;
    private Float asigGeneralAfc;
    private Float asigViataAfc;
    private Float aferentaDbPen;
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
    private Float primeAsigBruteLuna;
    private Float asigurariGeneraleLuna;
    private Float asigurariViataLuna;
    private Float contribDatFgaLuna;
    private Float asigurareGeneralaContribLuna;
    private Float asigurareViataContribLuna;
    private Float dobanziPenalitatiLuna;
    private Float sumaVirataTotalLuna;
    private Float afContribLuna;
    private Float asigGeneralAfcLuna;
    private Float asigViataAfcLuna;
    private Float aferentaDbPenLuna;


    private String primeAsigBruteDoc;
    private String asigurariGeneraleDoc;
    private String asigurariViataDoc;
    private Integer areRaportareDoc;
    private String contribDatFgaDoc;
    private String asigurareGeneralaContribDoc;
    private String asigurareViataContribDoc;
    private String dobanziPenalitatiDoc;
    private String sumaVirataTotalDoc;
    private String afContribDoc;
    private String asigGeneralAfcDoc;
    private String asigViataAfcDoc;
    private String aferentaDbPenDoc;
    private String subsemnatul;
    private String inCalitateDe;

    public String getInCalitateDe() {
        return inCalitateDe;
    }

    public void setInCalitateDe(String inCalitateDe) {
        this.inCalitateDe = inCalitateDe;
    }

    public String getSubsemnatul() {
        return subsemnatul;
    }

    public void setSubsemnatul(String subsemnatul) {
        this.subsemnatul = subsemnatul;
    }
    public String getPrimeAsigBruteDoc() {
        return primeAsigBruteDoc;
    }

    public void setPrimeAsigBruteDoc(String primeAsigBruteDoc) {
        this.primeAsigBruteDoc = primeAsigBruteDoc;
    }

    public String getAsigurariGeneraleDoc() {
        return asigurariGeneraleDoc;
    }

    public void setAsigurariGeneraleDoc(String asigurariGeneraleDoc) {
        this.asigurariGeneraleDoc = asigurariGeneraleDoc;
    }

    public String getAsigurariViataDoc() {
        return asigurariViataDoc;
    }

    public void setAsigurariViataDoc(String asigurariViataDoc) {
        this.asigurariViataDoc = asigurariViataDoc;
    }

    public Integer getAreRaportareDoc() {
        return areRaportareDoc;
    }

    public void setAreRaportareDoc(Integer areRaportareDoc) {
        this.areRaportareDoc = areRaportareDoc;
    }

    public String getContribDatFgaDoc() {
        return contribDatFgaDoc;
    }

    public void setContribDatFgaDoc(String contribDatFgaDoc) {
        this.contribDatFgaDoc = contribDatFgaDoc;
    }

    public String getAsigurareGeneralaContribDoc() {
        return asigurareGeneralaContribDoc;
    }

    public void setAsigurareGeneralaContribDoc(String asigurareGeneralaContribDoc) {
        this.asigurareGeneralaContribDoc = asigurareGeneralaContribDoc;
    }

    public String getAsigurareViataContribDoc() {
        return asigurareViataContribDoc;
    }

    public void setAsigurareViataContribDoc(String asigurareViataContribDoc) {
        this.asigurareViataContribDoc = asigurareViataContribDoc;
    }

    public String getDobanziPenalitatiDoc() {
        return dobanziPenalitatiDoc;
    }

    public void setDobanziPenalitatiDoc(String dobanziPenalitatiDoc) {
        this.dobanziPenalitatiDoc = dobanziPenalitatiDoc;
    }

    public String getSumaVirataTotalDoc() {
        return sumaVirataTotalDoc;
    }

    public void setSumaVirataTotalDoc(String sumaVirataTotalDoc) {
        this.sumaVirataTotalDoc = sumaVirataTotalDoc;
    }

    public String getAfContribDoc() {
        return afContribDoc;
    }

    public void setAfContribDoc(String afContribDoc) {
        this.afContribDoc = afContribDoc;
    }

    public String getAsigGeneralAfcDoc() {
        return asigGeneralAfcDoc;
    }

    public void setAsigGeneralAfcDoc(String asigGeneralAfcDoc) {
        this.asigGeneralAfcDoc = asigGeneralAfcDoc;
    }

    public String getAsigViataAfcDoc() {
        return asigViataAfcDoc;
    }

    public void setAsigViataAfcDoc(String asigViataAfcDoc) {
        this.asigViataAfcDoc = asigViataAfcDoc;
    }

    public String getAferentaDbPenDoc() {
        return aferentaDbPenDoc;
    }

    public void setAferentaDbPenDoc(String aferentaDbPenDoc) {
        this.aferentaDbPenDoc = aferentaDbPenDoc;
    }

    public Float getPrimeAsigBruteLuna() {
        return primeAsigBruteLuna;
    }

    public void setPrimeAsigBruteLuna(Float primeAsigBruteLuna) {
        this.primeAsigBruteLuna = primeAsigBruteLuna;
    }

    public Float getAsigurariGeneraleLuna() {
        return asigurariGeneraleLuna;
    }

    public void setAsigurariGeneraleLuna(Float asigurariGeneraleLuna) {
        this.asigurariGeneraleLuna = asigurariGeneraleLuna;
    }

    public Float getAsigurariViataLuna() {
        return asigurariViataLuna;
    }

    public void setAsigurariViataLuna(Float asigurariViataLuna) {
        this.asigurariViataLuna = asigurariViataLuna;
    }


    public Float getContribDatFgaLuna() {
        return contribDatFgaLuna;
    }

    public void setContribDatFgaLuna(Float contribDatFgaLuna) {
        this.contribDatFgaLuna = contribDatFgaLuna;
    }

    public Float getAsigurareGeneralaContribLuna() {
        return asigurareGeneralaContribLuna;
    }

    public void setAsigurareGeneralaContribLuna(Float asigurareGeneralaContribLuna) {
        this.asigurareGeneralaContribLuna = asigurareGeneralaContribLuna;
    }

    public Float getAsigurareViataContribLuna() {
        return asigurareViataContribLuna;
    }

    public void setAsigurareViataContribLuna(Float asigurareViataContribLuna) {
        this.asigurareViataContribLuna = asigurareViataContribLuna;
    }

    public Float getDobanziPenalitatiLuna() {
        return dobanziPenalitatiLuna;
    }

    public void setDobanziPenalitatiLuna(Float dobanziPenalitatiLuna) {
        this.dobanziPenalitatiLuna = dobanziPenalitatiLuna;
    }

    public Float getSumaVirataTotalLuna() {
        return sumaVirataTotalLuna;
    }

    public void setSumaVirataTotalLuna(Float sumaVirataTotalLuna) {
        this.sumaVirataTotalLuna = sumaVirataTotalLuna;
    }

    public Float getAfContribLuna() {
        return afContribLuna;
    }

    public void setAfContribLuna(Float afContribLuna) {
        this.afContribLuna = afContribLuna;
    }

    public Float getAsigGeneralAfcLuna() {
        return asigGeneralAfcLuna;
    }

    public void setAsigGeneralAfcLuna(Float asigGeneralAfcLuna) {
        this.asigGeneralAfcLuna = asigGeneralAfcLuna;
    }

    public Float getAsigViataAfcLuna() {
        return asigViataAfcLuna;
    }

    public void setAsigViataAfcLuna(Float asigViataAfcLuna) {
        this.asigViataAfcLuna = asigViataAfcLuna;
    }

    public Float getAferentaDbPenLuna() {
        return aferentaDbPenLuna;
    }

    public void setAferentaDbPenLuna(Float aferentaDbPenLuna) {
        this.aferentaDbPenLuna = aferentaDbPenLuna;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getPrimeAsigBrute() {
        return primeAsigBrute;
    }

    public void setPrimeAsigBrute(Float primeAsigBrute) {
        this.primeAsigBrute = primeAsigBrute;
    }

    public Float getAsigurariGenerale() {
        return asigurariGenerale;
    }

    public void setAsigurariGenerale(Float asigurariGenerale) {
        this.asigurariGenerale = asigurariGenerale;
    }

    public Float getAsigurariViata() {
        return asigurariViata;
    }

    public void setAsigurariViata(Float asigurariViata) {
        this.asigurariViata = asigurariViata;
    }

    public Integer getAreRaportare() {
        return areRaportare;
    }

    public void setAreRaportare(Integer areRaportare) {
        this.areRaportare = areRaportare;
    }

    public Float getContribDatFga() {
        return contribDatFga;
    }

    public void setContribDatFga(Float contribDatFga) {
        this.contribDatFga = contribDatFga;
    }

    public Float getAsigurareGeneralaContrib() {
        return asigurareGeneralaContrib;
    }

    public void setAsigurareGeneralaContrib(Float asigurareGeneralaContrib) {
        this.asigurareGeneralaContrib = asigurareGeneralaContrib;
    }

    public Float getAsigurareViataContrib() {
        return asigurareViataContrib;
    }

    public void setAsigurareViataContrib(Float asigurareViataContrib) {
        this.asigurareViataContrib = asigurareViataContrib;
    }

    public Float getDobanziPenalitati() {
        return dobanziPenalitati;
    }

    public void setDobanziPenalitati(Float dobanziPenalitati) {
        this.dobanziPenalitati = dobanziPenalitati;
    }

    public Float getSumaVirataTotal() {
        return sumaVirataTotal;
    }

    public void setSumaVirataTotal(Float sumaVirataTotal) {
        this.sumaVirataTotal = sumaVirataTotal;
    }

    public Float getAfContrib() {
        return afContrib;
    }

    public void setAfContrib(Float afContrib) {
        this.afContrib = afContrib;
    }

    public Float getAsigGeneralAfc() {
        return asigGeneralAfc;
    }

    public void setAsigGeneralAfc(Float asigGeneralAfc) {
        this.asigGeneralAfc = asigGeneralAfc;
    }

    public Float getAsigViataAfc() {
        return asigViataAfc;
    }

    public void setAsigViataAfc(Float asigViataAfc) {
        this.asigViataAfc = asigViataAfc;
    }

    public Float getAferentaDbPen() {
        return aferentaDbPen;
    }

    public void setAferentaDbPen(Float aferentaDbPen) {
        this.aferentaDbPen = aferentaDbPen;
    }

    public Integer getExistaRaportareLunaSelectata() {
        return existaRaportareLunaSelectata;
    }

    public void setExistaRaportareLunaSelectata(Integer existaRaportareLunaSelectata) {
        this.existaRaportareLunaSelectata = existaRaportareLunaSelectata;
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

    public String getNumeSocietate() {
        return numeSocietate;
    }

    public void setNumeSocietate(String numeSocietate) {
        this.numeSocietate = numeSocietate;
    }

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
}


