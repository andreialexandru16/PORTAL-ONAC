package ro.bithat.dms.microservices.portal.ecitizen.project.backend.bithat;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TaskInfo extends BaseModel {

    //please, when you create an Entity -
    //the filed must be even in Roanian, even in English
    //not RO-EN :)
    private Integer nrCrt;
    private Integer id;
    private String issue;
    private String description;
    private Integer issueStatusId;
    private Integer responsibleId;
    private String date;
    private String createdAt;
    private String createdBy;
    private String updatedAt;
    private String updatedBy;
    private String issueStatus;
    private String responsibleName;
    private Integer idProiectZonaLucru;
    private String rezolvare;
    private String statusColor;
    private String statusTr;
    private Integer idImpact;
    private String impact;
    private String priorityColor;
    private String priorityTr;
    private Integer idProiect;
    private Double estimated;

    public Integer getIdProiect() {
        return idProiect;
    }

    public void setIdProiect(Integer idProiect) {
        this.idProiect = idProiect;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIssueStatusId() {
        return issueStatusId;
    }

    public void setIssueStatusId(Integer issueStatusId) {
        this.issueStatusId = issueStatusId;
    }

    public Integer getResponsibleId() {
        return responsibleId;
    }

    public void setResponsibleId(Integer responsibleId) {
        this.responsibleId = responsibleId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getIssueStatus() {
        return issueStatus;
    }

    public void setIssueStatus(String issueStatus) {
        this.issueStatus = issueStatus;
    }

    public String getResponsibleName() {
        return responsibleName;
    }

    public void setResponsibleName(String responsibleName) {
        this.responsibleName = responsibleName;
    }

    public Integer getIdProiectZonaLucru() {
        return idProiectZonaLucru;
    }

    public void setIdProiectZonaLucru(Integer idProiectZonaLucru) {
        this.idProiectZonaLucru = idProiectZonaLucru;
    }

    public Integer getNrCrt() {
        return nrCrt;
    }

    public void setNrCrt(Integer nrCrt) {
        this.nrCrt = nrCrt;
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

    public Integer getIdImpact() {
        return idImpact;
    }

    public void setIdImpact(Integer idImpact) {
        this.idImpact = idImpact;
    }

    public String getImpact() {
        return impact;
    }

    public void setImpact(String impact) {
        this.impact = impact;
    }

    public String getPriorityColor() {
        return priorityColor;
    }

    public void setPriorityColor(String priorityColor) {
        this.priorityColor = priorityColor;
    }

    public String getPriorityTr() {
        return priorityTr;
    }

    public void setPriorityTr(String priorityTr) {
        this.priorityTr = priorityTr;
    }

    public Double getEstimated() {
        return estimated;
    }

    public void setEstimated(Double estimated) {
        this.estimated = estimated;
    }
}
