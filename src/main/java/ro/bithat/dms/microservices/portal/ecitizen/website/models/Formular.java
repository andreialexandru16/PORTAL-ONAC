
package ro.bithat.dms.microservices.portal.ecitizen.website.models;


import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
public class Formular {
    private Integer id;
    private String nume;
    private String denumireDocument;
    private Date creatLa;
    private boolean perioadaActiva;
    private Integer idDocument;
    private Integer idTipDocument;
    private Integer idFisier;
    private boolean add;
    private boolean edit;
    private String dataTransmitereStr;
    private Date dataPublicare;
    private Date dataFinalizare;
    private Date dataTransmitere;

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

    public String getDenumireDocument() {
        return denumireDocument;
    }

    public void setDenumireDocument(String denumireDocument) {
        this.denumireDocument = denumireDocument;
    }

    public Date getCreatLa() {
        return creatLa;
    }

    public void setCreatLa(Date creatLa) {
        this.creatLa = creatLa;
    }

    public boolean isPerioadaActiva() {
        return perioadaActiva;
    }

    public void setPerioadaActiva(boolean perioadaActiva) {
        this.perioadaActiva = perioadaActiva;
    }

    public Integer getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(Integer idDocument) {
        this.idDocument = idDocument;
    }

    public Integer getIdTipDocument() {
        return idTipDocument;
    }

    public void setIdTipDocument(Integer idTipDocument) {
        this.idTipDocument = idTipDocument;
    }

    public Integer getIdFisier() {
        return idFisier;
    }

    public void setIdFisier(Integer idFisier) {
        this.idFisier = idFisier;
    }

    public boolean isAdd() {
        return add;
    }

    public void setAdd(boolean add) {
        this.add = add;
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    public String getDataTransmitereStr() {
        return dataTransmitereStr;
    }

    public void setDataTransmitereStr(String dataTransmitereStr) {
        this.dataTransmitereStr = dataTransmitereStr;
    }

    public Date getDataPublicare() {
        return dataPublicare;
    }

    public void setDataPublicare(Date dataPublicare) {
        this.dataPublicare = dataPublicare;
    }

    public Date getDataFinalizare() {
        return dataFinalizare;
    }

    public void setDataFinalizare(Date dataFinalizare) {
        this.dataFinalizare = dataFinalizare;
    }

    public Date getDataTransmitere() {
        return dataTransmitere;
    }

    public void setDataTransmitere(Date dataTransmitere) {
        this.dataTransmitere = dataTransmitere;
    }
}
