package ro.bithat.dms.microservices.portal.ecitizen.website.models;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class RelatiiTertList extends BaseModel {
    private List<RelatiiTert> relatiiTertList;

    public List<RelatiiTert> getRelatiiTertList() {
        return relatiiTertList;
    }

    public void setRelatiiTertList(List<RelatiiTert> relatiiTertList) {
        this.relatiiTertList = relatiiTertList;
    }
}
