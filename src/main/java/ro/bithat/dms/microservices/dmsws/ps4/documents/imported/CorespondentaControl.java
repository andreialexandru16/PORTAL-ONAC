package ro.bithat.dms.microservices.dmsws.ps4.documents.imported;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class CorespondentaControl extends BaseModel {

    private Integer id;
    private String sens;
    private String document;
    private Integer versiune;
    private String nrInreg;
    private String data;
    private String dataInreg;
    private String userIntern;
    private String userExtern;
    private String creatLa;
    private String downloadLink;
    private String status;
    private int idCerere;
    private int idFisier;
    private int idRaspunsLa;
    private List<CorespondentaLinieControl> corespondentaLinieControlList;

    public List<CorespondentaLinieControl> getCorespondentaLinieControlList() {
        return corespondentaLinieControlList;
    }

    public void setCorespondentaLinieControlList(List<CorespondentaLinieControl> corespondentaLinieControlList) {
        this.corespondentaLinieControlList = corespondentaLinieControlList;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSens() {
        return sens;
    }

    public void setSens(String sens) {
        this.sens = sens;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public Integer getVersiune() {
        return versiune;
    }

    public void setVersiune(Integer versiune) {
        this.versiune = versiune;
    }

    public String getNrInreg() {
        return nrInreg;
    }

    public void setNrInreg(String nrInreg) {
        this.nrInreg = nrInreg;
    }

    public String getDataInreg() {
        return dataInreg;
    }

    public void setDataInreg(String dataInreg) {
        this.dataInreg = dataInreg;
    }

    public String getUserIntern() {
        return userIntern;
    }

    public void setUserIntern(String userIntern) {
        this.userIntern = userIntern;
    }

    public String getUserExtern() {
        return userExtern;
    }

    public void setUserExtern(String userExtern) {
        this.userExtern = userExtern;
    }

    public String getCreatLa() {
        return creatLa;
    }

    public void setCreatLa(String creatLa) {
        this.creatLa = creatLa;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setIdCerere(int idCerere) {
        this.idCerere = idCerere;
    }

    public int getIdCerere() {
        return idCerere;
    }

    public void setIdFisier(int idFisier) {
        this.idFisier = idFisier;
    }

    public int getIdFisier() {
        return idFisier;
    }

    public void setIdRaspunsLa(int idRaspunsLa) {
        this.idRaspunsLa = idRaspunsLa;
    }

    public int getIdRaspunsLa() {
        return idRaspunsLa;
    }
}
