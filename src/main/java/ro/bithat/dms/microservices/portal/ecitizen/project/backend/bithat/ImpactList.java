package ro.bithat.dms.microservices.portal.ecitizen.project.backend.bithat;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class ImpactList extends BaseModel {

    List<Impact> impactList;

    public List<Impact> getImpactList() {
        return impactList;
    }

    public void setImpactList(List<Impact> impactList) {
        this.impactList = impactList;
    }
}
