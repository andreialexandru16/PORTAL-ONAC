package ro.bithat.dms.microservices.portal.ecitizen.website.models;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Modeleaza un dir link.
 */
@XmlRootElement
public class DirLink_BM {
    private Integer id;
    private List<FileLink_BM> fileLinks;
    private String path;
    private String name;
    private String serverPath;

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

    public List<FileLink_BM> getFileLinks() {
        return fileLinks;
    }

    public void setFileLinks(List<FileLink_BM> fileLinks) {
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
}
