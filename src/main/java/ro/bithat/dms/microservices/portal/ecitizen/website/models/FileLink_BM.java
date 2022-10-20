package ro.bithat.dms.microservices.portal.ecitizen.website.models;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Modeleaza un file link.
 */
@XmlRootElement
public class FileLink_BM extends BaseModel {

    private Integer id;
    private String downloadLink;
    private String path;
    private String name;
    private String nameClear;
    private String extension;
    private String nameOnly;
    private String extensionName1;
    private String version;
    private Integer idDocument;
    private String creatDe;
    private Date creatLa;
    private String modificatDe;
    private Date modificatLa;
    private Integer dimensiune;
    private Double dimensiuneInTip;
    private String numeDimensiune1;
    private String creatLaStr;
    private String modificatLaStr;
    private FileLinkFluxData fluxData;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public Integer getDimensiune() {
        return dimensiune;
    }

    public void setDimensiune(Integer dimensiune) {
        this.dimensiune = dimensiune;
    }

    public Date getModificatLa() {
        return modificatLa;
    }

    public void setModificatLa(Date modificatLa) {
        this.modificatLa = modificatLa;
    }

    public String getModificatDe() {
        return modificatDe;
    }

    public void setModificatDe(String modificatDe) {
        this.modificatDe = modificatDe;
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

    public Integer getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(Integer idDocument) {
        this.idDocument = idDocument;
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

    public String getNameClear() {
        return nameClear;
    }

    public void setNameClear(String nameClear) {
        this.nameClear = nameClear;
    }

    public FileLinkFluxData getFluxData() {
        return fluxData;
    }

    public void setFluxData(FileLinkFluxData fluxData) {
        this.fluxData = fluxData;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getNameOnly() {
        return nameOnly;
    }

    public void setNameOnly(String nameOnly) {
        this.nameOnly = nameOnly;
    }

    public String getExtensionName1() {
        return extensionName1;
    }

    public void setExtensionName1(String extensionName1) {
        this.extensionName1 = extensionName1;
    }

    public Double getDimensiuneInTip() {
        return dimensiuneInTip;
    }

    public void setDimensiuneInTip(Double dimensiuneInTip) {
        this.dimensiuneInTip = dimensiuneInTip;
    }

    public String getNumeDimensiune1() {
        return numeDimensiune1;
    }

    public void setNumeDimensiune1(String numeDimensiune1) {
        this.numeDimensiune1 = numeDimensiune1;
    }
}
