package ro.bithat.dms.microservices.dmsws.colaboration;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class InfoMesaje extends BaseModel {


    private Integer id;
    private Integer idFisier;
    private Integer idTipDocument;
    private Integer idClasaDocument;
    private Integer idWorkflowStatus;
    private Integer nrMesaje;
    private String numeFisier;
    private String tipDocument;
    private String workflowStatus;
    private String creatLa;
    private String trimisLa;

    public String getTrimisLa() {
        return trimisLa;
    }

    public void setTrimisLa(String trimisLa) {
        this.trimisLa = trimisLa;
    }
    public Integer getIdClasaDocument() {
        return idClasaDocument;
    }

    public void setIdClasaDocument(Integer idClasaDocument) {
        this.idClasaDocument = idClasaDocument;
    }

    public String getCreatLa() {
        return creatLa;
    }

    public void setCreatLa(String creatLa) {
        this.creatLa = creatLa;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdFisier() {
        return idFisier;
    }

    public void setIdFisier(Integer idFisier) {
        this.idFisier = idFisier;
    }

    public Integer getIdTipDocument() {
        return idTipDocument;
    }

    public void setIdTipDocument(Integer idTipDocument) {
        this.idTipDocument = idTipDocument;
    }

    public Integer getIdWorkflowStatus() {
        return idWorkflowStatus;
    }

    public void setIdWorkflowStatus(Integer idWorkflowStatus) {
        this.idWorkflowStatus = idWorkflowStatus;
    }

    public Integer getNrMesaje() {
        return nrMesaje;
    }

    public void setNrMesaje(Integer nrMesaje) {
        this.nrMesaje = nrMesaje;
    }

    public String getNumeFisier() {
        return numeFisier;
    }

    public void setNumeFisier(String numeFisier) {
        this.numeFisier = numeFisier;
    }

    public String getTipDocument() {
        return tipDocument;
    }

    public void setTipDocument(String tipDocument) {
        this.tipDocument = tipDocument;
    }

    public String getWorkflowStatus() {
        return workflowStatus;
    }

    public void setWorkflowStatus(String workflowStatus) {
        this.workflowStatus = workflowStatus;
    }
}
