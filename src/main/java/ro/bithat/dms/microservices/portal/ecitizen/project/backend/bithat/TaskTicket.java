package ro.bithat.dms.microservices.portal.ecitizen.project.backend.bithat;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TaskTicket extends BaseModel {

    private Integer nrCrt;
    private Integer id;
    private String title;
    private String description;
    private Integer statusId;
    private String taskStatus;
    private Integer responsibleId;
    private String responsibleName;
    private String createdAt;
    private String createdBy;
    private String updatedAt;
    private String updatedBy;
    private Integer idTicket;
    private String rezolvare;
    private String statusColor;
    private String statusTr;
    private String dataTask;
    private Double alocated;
    private Double effective;
    private String createdByImg;

    public String getCreatedByImg() {
        return createdByImg;
    }

    public void setCreatedByImg(String createdByImg) {
        this.createdByImg = createdByImg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDataTask() {
        return dataTask;
    }

    public void setDataTask(String dataTask) {
        this.dataTask = dataTask;
    }

    public Integer getNrCrt() {
        return nrCrt;
    }

    public void setNrCrt(Integer nrCrt) {
        this.nrCrt = nrCrt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Integer getResponsibleId() {
        return responsibleId;
    }

    public void setResponsibleId(Integer responsibleId) {
        this.responsibleId = responsibleId;
    }

    public String getResponsibleName() {
        return responsibleName;
    }

    public void setResponsibleName(String responsibleName) {
        this.responsibleName = responsibleName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Integer getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(Integer idTicket) {
        this.idTicket = idTicket;
    }

    public String getRezolvare() {
        return rezolvare;
    }

    public void setRezolvare(String rezolvare) {
        this.rezolvare = rezolvare;
    }

    public String getStatusColor() {
        return statusColor;
    }

    public void setStatusColor(String statusColor) {
        this.statusColor = statusColor;
    }

    public String getStatusTr() {
        return statusTr;
    }

    public void setStatusTr(String statusTr) {
        this.statusTr = statusTr;
    }

    public Double getAlocated() {
        return alocated;
    }

    public void setAlocated(Double alocated) {
        this.alocated = alocated;
    }

    public Double getEffective() {
        return effective;
    }

    public void setEffective(Double effective) {
        this.effective = effective;
    }
}
