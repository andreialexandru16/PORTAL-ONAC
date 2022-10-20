package ro.bithat.dms.microservices.dmsws.file;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class MultiFilterPortalFileList extends BaseModel {
    private List<PortalFile> portalFileList;

    public List<PortalFile> getPortalFileList() {
        return portalFileList;
    }

    public void setPortalFileList(List<PortalFile> portalFileList) {
        this.portalFileList = portalFileList;
    }
}
