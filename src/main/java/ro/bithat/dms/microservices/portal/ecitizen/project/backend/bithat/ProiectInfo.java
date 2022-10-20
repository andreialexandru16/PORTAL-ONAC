package ro.bithat.dms.microservices.portal.ecitizen.project.backend.bithat;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Modeleaza un lov.
 */
@XmlRootElement
public class ProiectInfo extends BaseModel {
    private Integer id;
    private Integer idStatus;
    private Integer idDurataProiect;
    private Integer idDirectorImplicit;
    private String nume;
    private String descriere;
    private String imageLink;
    private String durataProiect;
    private String cod_proiect;
    private String nume_manager;
    private String status;
    private String startdate;
    private String enddate;
    private Double nrZileRamase;
    private Double timpDerulare;
    private Double indFizici;
    private Double indValorici;
    private Integer nrResurse;
    private Integer nrDocumente;
    private Integer procentUAT;
    private Integer procentFD;
    private Integer nrTicheteDeschise;
    private Integer nrFluxuriActive;
    private Double buget;
    private String docObl1;
    private String docObl2;
    private String docObl3;
    private String sefBirou;
    private String moneda;
    private Double valoareRealizata;
    private Double dimensiuneMaximaGb;
    private Double dimensiuneActualaGb;

    public Integer getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(Integer idStatus) {
        this.idStatus = idStatus;
    }

    public Integer getIdDurataProiect() {
        return idDurataProiect;
    }

    public void setIdDurataProiect(Integer idDurataProiect) {
        this.idDurataProiect = idDurataProiect;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getDurataProiect() {
        return durataProiect;
    }

    public void setDurataProiect(String durataProiect) {
        this.durataProiect = durataProiect;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getCod_proiect() {
        return cod_proiect;
    }

    public void setCod_proiect(String cod_proiect) {
        this.cod_proiect = cod_proiect;
    }

    public String getNume_manager() {
        return nume_manager;
    }

    public void setNume_manager(String nume_manager) {
        this.nume_manager = nume_manager;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public Double getNrZileRamase() {
        return nrZileRamase;
    }

    public void setNrZileRamase(Double nrZileRamase) {
        this.nrZileRamase = nrZileRamase;
    }

    public Double getTimpDerulare() {
        return timpDerulare;
    }

    public void setTimpDerulare(Double timpDerulare) {
        this.timpDerulare = timpDerulare;
    }

    public Double getIndValorici() {
        return indValorici;
    }

    public void setIndValorici(Double indValorici) {
        this.indValorici = indValorici;
    }

    public Double getIndFizici() {

        return indFizici;
    }

    public void setIndFizici(Double indFizici) {
        this.indFizici = indFizici;
    }

    public Integer getNrResurse() {
        return nrResurse;
    }

    public void setNrResurse(Integer nrResurse) {
        this.nrResurse = nrResurse;
    }

    public Integer getNrDocumente() {
        return nrDocumente;
    }

    public void setNrDocumente(Integer nrDocumente) {
        this.nrDocumente = nrDocumente;
    }

    public Integer getProcentUAT() {
        return procentUAT;
    }

    public void setProcentUAT(Integer procentUAT) {
        this.procentUAT = procentUAT;
    }

    public Integer getProcentFD() {
        return procentFD;
    }

    public void setProcentFD(Integer procentFD) {
        this.procentFD = procentFD;
    }

    public Integer getNrTicheteDeschise() {
        return nrTicheteDeschise;
    }

    public void setNrTicheteDeschise(Integer nrTicheteDeschise) {
        this.nrTicheteDeschise = nrTicheteDeschise;
    }

    public Integer getNrFluxuriActive() {
        return nrFluxuriActive;
    }

    public void setNrFluxuriActive(Integer nrFluxuriActive) {
        this.nrFluxuriActive = nrFluxuriActive;
    }

    public String getDocObl1() {
        return docObl1;
    }

    public void setDocObl1(String docObl1) {
        this.docObl1 = docObl1;
    }

    public String getDocObl2() {
        return docObl2;
    }

    public void setDocObl2(String docObl2) {
        this.docObl2 = docObl2;
    }

    public String getDocObl3() {
        return docObl3;
    }

    public void setDocObl3(String docObl3) {
        this.docObl3 = docObl3;
    }

    public String getSefBirou() {
        return sefBirou;
    }

    public void setSefBirou(String sefBirou) {
        this.sefBirou = sefBirou;
    }

    public Double getBuget() {
        return buget;
    }

    public void setBuget(Double buget) {
        this.buget = buget;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public Double getValoareRealizata() {
        return valoareRealizata;
    }

    public void setValoareRealizata(Double valoareRealizata) {
        this.valoareRealizata = valoareRealizata;
    }

    public Integer getIdDirectorImplicit() {
        return idDirectorImplicit;
    }

    public void setIdDirectorImplicit(Integer idDirectorImplicit) {
        this.idDirectorImplicit = idDirectorImplicit;
    }

    public Double getDimensiuneMaximaGb() {
        return dimensiuneMaximaGb;
    }

    public void setDimensiuneMaximaGb(Double dimensiuneMaximaGb) {
        this.dimensiuneMaximaGb = dimensiuneMaximaGb;
    }

    public Double getDimensiuneActualaGb() {
        return dimensiuneActualaGb;
    }

    public void setDimensiuneActualaGb(Double dimensiuneActualaGb) {
        this.dimensiuneActualaGb = dimensiuneActualaGb;
    }
}
