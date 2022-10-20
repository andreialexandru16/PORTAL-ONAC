package ro.bithat.dms.microservices.portal.ecitizen.website.models;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;
import ro.bithat.dms.microservices.dmsws.file.DirLink;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class DirLinkList_BM extends BaseModel {
    private List<DirLink> dirLink;

    public List<DirLink> getDirLink() {
        return dirLink;
    }

    public void setDirLink(List<DirLink> dirLink) {
        this.dirLink = dirLink;
    }
}

