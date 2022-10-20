package ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Psiholog extends BaseModel {
    private Integer id;
    private String codRup;
    private String cnp;
    private String filiala;
    private Integer idFiliala;
    private String localitateFiliala;
    private Integer idLocalitateFiliala;
    private String judetFiliala;
    private Integer idJudetFiliala;
    private String nume;
    private String prenume;
    private String adresa;
    private String localitate;
    private Integer idLocalitate;
    private String judet;
    private Integer idJudet;
    private String strada;
    private String nr;
    private String bloc;
    private String scara;
    private String etaj;
    private String apartament;
    private String telefon;
    private String email;
    private String locMunca;
    private String localitateMunca;
    private Integer idLocalitateMunca;
    private String judetMunca;
    private Integer idJudetMunca;
    private String stradaMunca;
    private String nrMunca;
    private String blocMunca;
    private String scaraMunca;
    private String etajMunca;
    private String apartamentMunca;
    private String universitateLicenta;
    private String facultateaLicenta;
    private String specializareLicenta;
    private String anObtinereLicenta;
    private String universitateMaster;
    private String facultateaMaster;
    private String denumireMaster;
    private String anObtinereDisertatie;
    private String furnizorFormare;
    private String specialitateFormare;
    private String specializareFormare;
    private String serieFormare;
    private String dataEmitereFormare;
    private String treaptaFormare;
    private String regimFormare;
    private String universitateDoctorat;
    private String domeniuDoctorat;
    private String denumireTemaDoctorat;
    private String anObtinereDoctorat;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodRup() {
        return codRup;
    }

    public void setCodRup(String codRup) {
        this.codRup = codRup;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getFiliala() {
        return filiala;
    }

    public void setFiliala(String filiala) {
        this.filiala = filiala;
    }

    public Integer getIdFiliala() {
        return idFiliala;
    }

    public void setIdFiliala(Integer idFiliala) {
        this.idFiliala = idFiliala;
    }

    public String getLocalitateFiliala() {
        return localitateFiliala;
    }

    public void setLocalitateFiliala(String localitateFiliala) {
        this.localitateFiliala = localitateFiliala;
    }

    public Integer getIdLocalitateFiliala() {
        return idLocalitateFiliala;
    }

    public void setIdLocalitateFiliala(Integer idLocalitateFiliala) {
        this.idLocalitateFiliala = idLocalitateFiliala;
    }

    public String getJudetFiliala() {
        return judetFiliala;
    }

    public void setJudetFiliala(String judetFiliala) {
        this.judetFiliala = judetFiliala;
    }

    public Integer getIdJudetFiliala() {
        return idJudetFiliala;
    }

    public void setIdJudetFiliala(Integer idJudetFiliala) {
        this.idJudetFiliala = idJudetFiliala;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getLocalitate() {
        return localitate;
    }

    public void setLocalitate(String localitate) {
        this.localitate = localitate;
    }

    public Integer getIdLocalitate() {
        return idLocalitate;
    }

    public void setIdLocalitate(Integer idLocalitate) {
        this.idLocalitate = idLocalitate;
    }

    public String getJudet() {
        return judet;
    }

    public void setJudet(String judet) {
        this.judet = judet;
    }

    public Integer getIdJudet() {
        return idJudet;
    }

    public void setIdJudet(Integer idJudet) {
        this.idJudet = idJudet;
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

    public String getLocMunca() {
        return locMunca;
    }

    public void setLocMunca(String locMunca) {
        this.locMunca = locMunca;
    }

    public String getLocalitateMunca() {
        return localitateMunca;
    }

    public void setLocalitateMunca(String localitateMunca) {
        this.localitateMunca = localitateMunca;
    }

    public Integer getIdLocalitateMunca() {
        return idLocalitateMunca;
    }

    public void setIdLocalitateMunca(Integer idLocalitateMunca) {
        this.idLocalitateMunca = idLocalitateMunca;
    }

    public String getJudetMunca() {
        return judetMunca;
    }

    public void setJudetMunca(String judetMunca) {
        this.judetMunca = judetMunca;
    }

    public Integer getIdJudetMunca() {
        return idJudetMunca;
    }

    public void setIdJudetMunca(Integer idJudetMunca) {
        this.idJudetMunca = idJudetMunca;
    }

    public String getStrada() {
        return strada;
    }

    public void setStrada(String strada) {
        this.strada = strada;
    }

    public String getNr() {
        return nr;
    }

    public void setNr(String nr) {
        this.nr = nr;
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

    public String getStradaMunca() {
        return stradaMunca;
    }

    public void setStradaMunca(String stradaMunca) {
        this.stradaMunca = stradaMunca;
    }

    public String getNrMunca() {
        return nrMunca;
    }

    public void setNrMunca(String nrMunca) {
        this.nrMunca = nrMunca;
    }

    public String getBlocMunca() {
        return blocMunca;
    }

    public void setBlocMunca(String blocMunca) {
        this.blocMunca = blocMunca;
    }

    public String getScaraMunca() {
        return scaraMunca;
    }

    public void setScaraMunca(String scaraMunca) {
        this.scaraMunca = scaraMunca;
    }

    public String getEtajMunca() {
        return etajMunca;
    }

    public void setEtajMunca(String etajMunca) {
        this.etajMunca = etajMunca;
    }

    public String getApartamentMunca() {
        return apartamentMunca;
    }

    public void setApartamentMunca(String apartamentMunca) {
        this.apartamentMunca = apartamentMunca;
    }

    public String getUniversitateLicenta() {
        return universitateLicenta;
    }

    public void setUniversitateLicenta(String universitateLicenta) {
        this.universitateLicenta = universitateLicenta;
    }

    public String getFacultateaLicenta() {
        return facultateaLicenta;
    }

    public void setFacultateaLicenta(String facultateaLicenta) {
        this.facultateaLicenta = facultateaLicenta;
    }

    public String getSpecializareLicenta() {
        return specializareLicenta;
    }

    public void setSpecializareLicenta(String specializareLicenta) {
        this.specializareLicenta = specializareLicenta;
    }

    public String getUniversitateMaster() {
        return universitateMaster;
    }

    public void setUniversitateMaster(String universitateMaster) {
        this.universitateMaster = universitateMaster;
    }

    public String getFacultateaMaster() {
        return facultateaMaster;
    }

    public void setFacultateaMaster(String facultateaMaster) {
        this.facultateaMaster = facultateaMaster;
    }

    public String getDenumireMaster() {
        return denumireMaster;
    }

    public void setDenumireMaster(String denumireMaster) {
        this.denumireMaster = denumireMaster;
    }

    public String getFurnizorFormare() {
        return furnizorFormare;
    }

    public void setFurnizorFormare(String furnizorFormare) {
        this.furnizorFormare = furnizorFormare;
    }

    public String getSpecialitateFormare() {
        return specialitateFormare;
    }

    public void setSpecialitateFormare(String specialitateFormare) {
        this.specialitateFormare = specialitateFormare;
    }

    public String getSpecializareFormare() {
        return specializareFormare;
    }

    public void setSpecializareFormare(String specializareFormare) {
        this.specializareFormare = specializareFormare;
    }

    public String getSerieFormare() {
        return serieFormare;
    }

    public void setSerieFormare(String serieFormare) {
        this.serieFormare = serieFormare;
    }

    public String getDataEmitereFormare() {
        return dataEmitereFormare;
    }

    public void setDataEmitereFormare(String dataEmitereFormare) {
        this.dataEmitereFormare = dataEmitereFormare;
    }

    public String getTreaptaFormare() {
        return treaptaFormare;
    }

    public void setTreaptaFormare(String treaptaFormare) {
        this.treaptaFormare = treaptaFormare;
    }

    public String getRegimFormare() {
        return regimFormare;
    }

    public void setRegimFormare(String regimFormare) {
        this.regimFormare = regimFormare;
    }

    public String getUniversitateDoctorat() {
        return universitateDoctorat;
    }

    public void setUniversitateDoctorat(String universitateDoctorat) {
        this.universitateDoctorat = universitateDoctorat;
    }

    public String getDomeniuDoctorat() {
        return domeniuDoctorat;
    }

    public void setDomeniuDoctorat(String domeniuDoctorat) {
        this.domeniuDoctorat = domeniuDoctorat;
    }

    public String getDenumireTemaDoctorat() {
        return denumireTemaDoctorat;
    }

    public void setDenumireTemaDoctorat(String denumireTemaDoctorat) {
        this.denumireTemaDoctorat = denumireTemaDoctorat;
    }

    public String getAnObtinereLicenta() {
        return anObtinereLicenta;
    }

    public void setAnObtinereLicenta(String anObtinereLicenta) {
        this.anObtinereLicenta = anObtinereLicenta;
    }

    public String getAnObtinereDisertatie() {
        return anObtinereDisertatie;
    }

    public void setAnObtinereDisertatie(String anObtinereDisertatie) {
        this.anObtinereDisertatie = anObtinereDisertatie;
    }

    public String getAnObtinereDoctorat() {
        return anObtinereDoctorat;
    }

    public void setAnObtinereDoctorat(String anObtinereDoctorat) {
        this.anObtinereDoctorat = anObtinereDoctorat;
    }
}



