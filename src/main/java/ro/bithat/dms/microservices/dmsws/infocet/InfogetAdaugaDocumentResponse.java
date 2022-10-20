package ro.bithat.dms.microservices.dmsws.infocet;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Info despre un file item.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class InfogetAdaugaDocumentResponse extends BaseModel {
    private Integer fileId;
    private String status;
    private String idWorkflowStatus;
    private String nrInreg;
    private String dataInreg;


    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdWorkflowStatus() {
        return idWorkflowStatus;
    }

    public void setIdWorkflowStatus(String idWorkflowStatus) {
        this.idWorkflowStatus = idWorkflowStatus;
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
}
