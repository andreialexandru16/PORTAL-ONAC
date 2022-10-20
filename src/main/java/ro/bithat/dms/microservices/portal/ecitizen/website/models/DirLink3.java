package ro.bithat.dms.microservices.portal.ecitizen.website.models;

import ro.bithat.dms.microservices.dmsws.file.FileLink;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.List;

/**
 * Modeleaza un dir link.
 */
@XmlRootElement
public class DirLink3 {
    private Integer id;
    private Integer parentId;
    private List<FileLink> fileLinks;
    private String path;
    private String name;
    private String description;
    private String serverPath;
    private String createdby;
    private String modifiedby;
    private Boolean hasEdit;
    private Date modifiedat;
    private String createdAtStr;
    private String modifiedAtStr;

    public Date getModifiedat() {
        return modifiedat;
    }

    public void setModifiedat(Date modifiedat) {
        this.modifiedat = modifiedat;
    }

    public Date getCreatedat() {
        return createdat;
    }

    public void setCreatedat(Date createdat) {
        this.createdat = createdat;
    }

    private Date createdat;



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

    public List<FileLink> getFileLinks() {
        return fileLinks;
    }

    public void setFileLinks(List<FileLink> fileLinks) {
        this.fileLinks = fileLinks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServerPath() {
        return serverPath;
    }

    public void setServerPath(String serverPath) {
        this.serverPath = serverPath;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public String getModifiedby() {
        return modifiedby;
    }

    public void setModifiedby(String modifiedby) {
        this.modifiedby = modifiedby;
    }

    public Boolean getHasEdit() {
        return hasEdit;
    }

    public void setHasEdit(Boolean hasEdit) {
        this.hasEdit = hasEdit;
    }

    public String getCreatedAtStr() {
        return createdAtStr;
    }

    public void setCreatedAtStr(String createdAtStr) {
        this.createdAtStr = createdAtStr;
    }

    public String getModifiedAtStr() {
        return modifiedAtStr;
    }

    public void setModifiedAtStr(String modifiedAtStr) {
        this.modifiedAtStr = modifiedAtStr;
    }
}
