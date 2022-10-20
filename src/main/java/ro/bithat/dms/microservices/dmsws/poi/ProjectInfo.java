package ro.bithat.dms.microservices.dmsws.poi;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Modeleaza un proiect.
 */
@XmlRootElement
public class ProjectInfo extends BaseModel {
    private Integer id;
    private String cod;
    private String scop;
    private String denumire;
    private String descriere;
    private String address;
    private Integer idCategoriePoi;
    private String categoriePoi;
    private Double latitudine;
    private Double longitudine;
    private Double valoare;
    private Integer idFisier;
    private String numeFisier;
    private String icon;
    private Integer idLocalitate;
    private String localitate;
    private Integer idJudet;
    private String judet;
    private String executant;
    private String propusDeNume;
    private String propusDePrenume;
    private String propusDeEmail;
    private String propusDeTelefon;
    private Integer idStatusProiect;
    private String statusProiect;
    private String dataStart;
    private String dataEnd;
    private String dataStartVote;
    private String dataEndVote;
    private Integer nrVoturiPro;
    private Integer nrVoturiContra;
    private Integer publicat;
    private String beneficii;
    private String beneficiari;
    private Integer idTipProiect;
    private Double buget;
    private String tipProiect;
    private String moneda;
    private String startdate;
    private String enddate;
    private String creatLa;
    private String propusDurataProiect;
    private String codCategoriePoi;
    private String tipLucrare;

    public String getTipLucrare() {
        return tipLucrare;
    }

    public void setTipLucrare(String tipLucrare) {
        this.tipLucrare = tipLucrare;
    }

    public String getCodCategoriePoi() {
        return codCategoriePoi;
    }

    public void setCodCategoriePoi(String codCategoriePoi) {
        this.codCategoriePoi = codCategoriePoi;
    }

    //pozitie
    private String info1;

    public String getInfo1() {
        return info1;
    }

    public void setInfo1(String info1) {
        this.info1 = info1;
    }
    public String getPropusDurataProiect() {
        return propusDurataProiect;
    }

    public void setPropusDurataProiect(String propusDurataProiect) {
        this.propusDurataProiect = propusDurataProiect;
    }

    public String getCreatLa() {
        return creatLa;
    }

    public void setCreatLa(String creatLa) {
        this.creatLa = creatLa;
    }

    public String getDataStartVote() {
        return dataStartVote;
    }

    public void setDataStartVote(String dataStartVote) {
        this.dataStartVote = dataStartVote;
    }

    public String getDataEndVote() {
        return dataEndVote;
    }

    public void setDataEndVote(String dataEndVote) {
        this.dataEndVote = dataEndVote;
    }


    public String getBeneficii() {
        return beneficii;
    }

    public void setBeneficii(String beneficii) {
        this.beneficii = beneficii;
    }

    public String getBeneficiari() {
        return beneficiari;
    }

    public void setBeneficiari(String beneficiari) {
        this.beneficiari = beneficiari;
    }

    public Integer getPublicat() {
        return publicat;
    }

    public void setPublicat(Integer publicat) {
        this.publicat = publicat;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getIdCategoriePoi() {
        return idCategoriePoi;
    }

    public void setIdCategoriePoi(Integer idCategoriePoi) {
        this.idCategoriePoi = idCategoriePoi;
    }

    public String getCategoriePoi() {
        return categoriePoi;
    }

    public void setCategoriePoi(String categoriePoi) {
        this.categoriePoi = categoriePoi;
    }

    public Double getLatitudine() {
        return latitudine;
    }

    public void setLatitudine(Double latitudine) {
        this.latitudine = latitudine;
    }

    public Double getLongitudine() {
        return longitudine;
    }

    public void setLongitudine(Double longitudine) {
        this.longitudine = longitudine;
    }

    public Double getValoare() {
        return valoare;
    }

    public void setValoare(Double valoare) {
        this.valoare = valoare;
    }

    public Integer getIdFisier() {
        return idFisier;
    }

    public void setIdFisier(Integer idFisier) {
        this.idFisier = idFisier;
    }

    public String getNumeFisier() {
        return numeFisier;
    }

    public void setNumeFisier(String numeFisier) {
        this.numeFisier = numeFisier;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getIdLocalitate() {
        return idLocalitate;
    }

    public void setIdLocalitate(Integer idLocalitate) {
        this.idLocalitate = idLocalitate;
    }

    public String getLocalitate() {
        return localitate;
    }

    public void setLocalitate(String localitate) {
        this.localitate = localitate;
    }

    public Integer getIdJudet() {
        return idJudet;
    }

    public void setIdJudet(Integer idJudet) {
        this.idJudet = idJudet;
    }

    public String getJudet() {
        return judet;
    }

    public void setJudet(String judet) {
        this.judet = judet;
    }

    public String getExecutant() {
        return executant;
    }

    public void setExecutant(String executant) {
        this.executant = executant;
    }

    public String getPropusDeNume() {
        return propusDeNume;
    }

    public void setPropusDeNume(String propusDeNume) {
        this.propusDeNume = propusDeNume;
    }

    public String getPropusDePrenume() {
        return propusDePrenume;
    }

    public void setPropusDePrenume(String propusDePrenume) {
        this.propusDePrenume = propusDePrenume;
    }

    public String getPropusDeEmail() {
        return propusDeEmail;
    }

    public void setPropusDeEmail(String propusDeEmail) {
        this.propusDeEmail = propusDeEmail;
    }

    public String getPropusDeTelefon() {
        return propusDeTelefon;
    }

    public void setPropusDeTelefon(String propusDeTelefon) {
        this.propusDeTelefon = propusDeTelefon;
    }

    public Integer getIdStatusProiect() {
        return idStatusProiect;
    }

    public void setIdStatusProiect(Integer idStatusProiect) {
        this.idStatusProiect = idStatusProiect;
    }

    public String getStatusProiect() {
        return statusProiect;
    }

    public void setStatusProiect(String statusProiect) {
        this.statusProiect = statusProiect;
    }

    public String getDataStart() {
        return dataStart;
    }

    public void setDataStart(String dataStart) {
        this.dataStart = dataStart;
    }

    public String getDataEnd() {
        return dataEnd;
    }

    public void setDataEnd(String dataEnd) {
        this.dataEnd = dataEnd;
    }

    public Integer getNrVoturiPro() {
        return nrVoturiPro;
    }

    public void setNrVoturiPro(Integer nrVoturiPro) {
        this.nrVoturiPro = nrVoturiPro;
    }

    public Integer getNrVoturiContra() {
        return nrVoturiContra;
    }

    public void setNrVoturiContra(Integer nrVoturiContra) {
        this.nrVoturiContra = nrVoturiContra;
    }

    public Integer getIdTipProiect() {
        return idTipProiect;
    }

    public void setIdTipProiect(Integer idTipProiect) {
        this.idTipProiect = idTipProiect;
    }

    public String getTipProiect() {
        return tipProiect;
    }

    public void setTipProiect(String tipProiect) {
        this.tipProiect = tipProiect;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getScop() {
        return scop;
    }

    public void setScop(String scop) {
        this.scop = scop;
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

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }
    public String getDenumireSiAdresa() {
        return denumire+"("+address+")";
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }
}
