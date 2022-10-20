package ro.bithat.dms.microservices.dmsws.colaboration;


import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Mesaj extends BaseModel {


    private Integer id;
    private String mesaj;
    private String divMesaj;
    private String creatDe;
    private String creatLa;
    private String versiune;
    private String idComunicare;
    private String pagina;
    private String sectiune;
    private String downloadFileLink;
    private String filename;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMesaj() {
        return mesaj;
    }

    public void setMesaj(String mesaj) {
        this.mesaj = mesaj;
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

    public String getVersiune() {
        return versiune;
    }

    public void setVersiune(String versiune) {
        this.versiune = versiune;
    }

    public String getIdComunicare() {
        return idComunicare;
    }

    public void setIdComunicare(String idComunicare) {
        this.idComunicare = idComunicare;
    }

    public String getPagina() {
        return pagina;
    }

    public void setPagina(String pagina) {
        this.pagina = pagina;
    }

    public String getSectiune() {
        return sectiune;
    }

    public void setSectiune(String sectiune) {
        this.sectiune = sectiune;
    }

    public String getDivMesaj() {
        return divMesaj;
    }

    public void setDivMesaj(String divMesaj) {
        this.divMesaj = divMesaj;
    }

    public String getDownloadFileLink() {
        return downloadFileLink;
    }

    public void setDownloadFileLink(String downloadFileLink) {
        this.downloadFileLink = downloadFileLink;
    }
}
