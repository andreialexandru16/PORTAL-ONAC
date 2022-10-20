package ro.bithat.dms.microservices.dmsws.poi;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Modeleaza un poi.
 */
@XmlRootElement
public class PoiInfo extends BaseModel {
    private Integer id;
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
    private Integer idStrada;
    private String strada;
    private String numarStrada;
    private String bloc;
    private String executant;
    private String propusDeNume;
    private String propusDePrenume;
    private String propusDeEmail;
    private String propusDeTelefon;
    private Integer propusIdStadiuPoi;
    private String propusStadiuPoi;
    private Integer idProiect;
    private String numeProiect;
    private String dataStart;
    private String dataEnd;
    private Integer nrVoturiPro;
    private Integer nrVoturiContra;

    public Integer getNrVoturiPro() {
        return nrVoturiPro;
    }

    public void setNrVoturiPro(Integer nrVoturiPro) {
        this.nrVoturiPro = nrVoturiPro;
    }

    public Integer getNrVoturiContra() {
        return nrVoturiContra;
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

    public Integer getIdStrada() {
        return idStrada;
    }

    public void setIdStrada(Integer idStrada) {
        this.idStrada = idStrada;
    }

    public String getStrada() {
        return strada;
    }

    public void setStrada(String strada) {
        this.strada = strada;
    }

    public String getNumarStrada() {
        return numarStrada;
    }

    public void setNumarStrada(String numarStrada) {
        this.numarStrada = numarStrada;
    }

    public String getBloc() {
        return bloc;
    }

    public void setBloc(String bloc) {
        this.bloc = bloc;
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

    public Integer getPropusIdStadiuPoi() {
        return propusIdStadiuPoi;
    }

    public void setPropusIdStadiuPoi(Integer propusIdStadiuPoi) {
        this.propusIdStadiuPoi = propusIdStadiuPoi;
    }

    public Integer getIdProiect() {
        return idProiect;
    }

    public void setIdProiect(Integer idProiect) {
        this.idProiect = idProiect;
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

    public String getNumeFisier() {
        return numeFisier;
    }

    public void setNumeFisier(String numeFisier) {
        this.numeFisier = numeFisier;
    }

    public String getNumeProiect() {
        return numeProiect;
    }

    public void setNumeProiect(String numeProiect) {
        this.numeProiect = numeProiect;
    }

    public String getPropusStadiuPoi() {
        return propusStadiuPoi;
    }

    public void setPropusStadiuPoi(String propusStadiuPoi) {
        this.propusStadiuPoi = propusStadiuPoi;
    }
}
