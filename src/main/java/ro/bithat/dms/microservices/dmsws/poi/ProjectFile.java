package ro.bithat.dms.microservices.dmsws.poi;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
public class ProjectFile extends BaseModel {
    private Integer id;
    private String downloadLink;
    private String name;
    private String creatDe;
    private Date creatLa;
    private String modificatDe;
    private Date modificatLa;
    private String creatLaStr;
    private String modificatLaStr;
    private Integer idFisier;
    private Integer idProiect;
    private String numeFisier;

    public Integer getIdFisier() {
        return idFisier;
    }

    public void setIdFisier(Integer idFisier) {
        this.idFisier = idFisier;
    }

    public Integer getIdProiect() {
        return idProiect;
    }

    public void setIdProiect(Integer idProiect) {
        this.idProiect = idProiect;
    }

    public String getNumeFisier() {
        return numeFisier;
    }

    public void setNumeFisier(String numeFisier) {
        this.numeFisier = numeFisier;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatDe() {
        return creatDe;
    }

    public void setCreatDe(String creatDe) {
        this.creatDe = creatDe;
    }

    public Date getCreatLa() {
        return creatLa;
    }

    public void setCreatLa(Date creatLa) {
        this.creatLa = creatLa;
    }

    public String getModificatDe() {
        return modificatDe;
    }

    public void setModificatDe(String modificatDe) {
        this.modificatDe = modificatDe;
    }

    public Date getModificatLa() {
        return modificatLa;
    }

    public void setModificatLa(Date modificatLa) {
        this.modificatLa = modificatLa;
    }

    public String getCreatLaStr() {
        return creatLaStr;
    }

    public void setCreatLaStr(String creatLaStr) {
        this.creatLaStr = creatLaStr;
    }

    public String getModificatLaStr() {
        return modificatLaStr;
    }

    public void setModificatLaStr(String modificatLaStr) {
        this.modificatLaStr = modificatLaStr;
    }
}
