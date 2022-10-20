package ro.bithat.dms.microservices.dmsws.ps4.documents.imported;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class LinkUtilList extends BaseModel {
    private List<LinkUtil> linkUtilList;

    public List<LinkUtil> getLinkUtilList() {
        return linkUtilList;
    }

    public void setLinkUtilList(List<LinkUtil> linkUtilList) {
        this.linkUtilList = linkUtilList;
    }
}
