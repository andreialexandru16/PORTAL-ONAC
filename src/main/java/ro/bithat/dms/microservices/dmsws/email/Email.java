package ro.bithat.dms.microservices.dmsws.email;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Info despre un email.
 */
@XmlRootElement
public class Email extends BaseModel {
    private Integer id;
    private Integer citit;
    private Integer idFluxPas;
    private Integer idNotificariFluxuriDetail;
    private String catre;
    private String catreCC;
    private String titlu;
    private String mesaj;
    private String dataTransmisie;
    private String zona;
    private String procedura;
    private String info;
    private String creatDe;
    private String creatLa;
    private String modificatDe;
    private String modificatLa;
    private String status;
    private String denumireFisier;
    private Integer idFisier;
    private Integer idClasaDocument;
    private Integer idDocument;
    private String creatDeEmail;
    private String idFilesList;

    public String getIdFilesList() {
        return idFilesList;
    }

    public void setIdFilesList(String idFilesList) {
        this.idFilesList = idFilesList;
    }

    public String getCreatDeEmail() {
        return creatDeEmail;
    }

    public void setCreatDeEmail(String creatDeEmail) {
        this.creatDeEmail = creatDeEmail;
    }
    public Integer getIdClasaDocument() {
        return idClasaDocument;
    }

    public void setIdClasaDocument(Integer idClasaDocument) {
        this.idClasaDocument = idClasaDocument;
    }

    public Integer getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(Integer idDocument) {
        this.idDocument = idDocument;
    }

    public String getDenumireFisier() {
        return denumireFisier;
    }

    public void setDenumireFisier(String denumireFisier) {
        this.denumireFisier = denumireFisier;
    }

    public Integer getIdFisier() {
        return idFisier;
    }

    public void setIdFisier(Integer idFisier) {
        this.idFisier = idFisier;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCitit() {
        return citit;
    }

    public void setCitit(Integer citit) {
        this.citit = citit;
    }

    public Integer getIdFluxPas() {
        return idFluxPas;
    }

    public void setIdFluxPas(Integer idFluxPas) {
        this.idFluxPas = idFluxPas;
    }

    public Integer getIdNotificariFluxuriDetail() {
        return idNotificariFluxuriDetail;
    }

    public void setIdNotificariFluxuriDetail(Integer idNotificariFluxuriDetail) {
        this.idNotificariFluxuriDetail = idNotificariFluxuriDetail;
    }

    public String getCatre() {
        return catre;
    }

    public void setCatre(String catre) {
        this.catre = catre;
    }

    public String getCatreCC() {
        return catreCC;
    }

    public void setCatreCC(String catreCC) {
        this.catreCC = catreCC;
    }

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public String getMesaj() {
        return mesaj;
    }

    public void setMesaj(String mesaj) {
        this.mesaj = mesaj;
    }

    public String getDataTransmisie() {
        return dataTransmisie;
    }

    public void setDataTransmisie(String dataTransmisie) {
        this.dataTransmisie = dataTransmisie;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getProcedura() {
        return procedura;
    }

    public void setProcedura(String procedura) {
        this.procedura = procedura;
    }

    @Override
    public String getInfo() {
        return info;
    }

    @Override
    public void setInfo(String info) {
        this.info = info;
    }

    public String getCreatDe() {
        return creatDe;
    }

    public void setCreatDe(String creatDe) {
        this.creatDe = creatDe;
    }

    public String getCreatLa() {
        return creatLa;
    }

    public void setCreatLa(String creatLa) {
        this.creatLa = creatLa;
    }

    public String getModificatDe() {
        return modificatDe;
    }

    public void setModificatDe(String modificatDe) {
        this.modificatDe = modificatDe;
    }

    public String getModificatLa() {
        return modificatLa;
    }

    public void setModificatLa(String modificatLa) {
        this.modificatLa = modificatLa;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
