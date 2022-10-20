package ro.bithat.dms.microservices.portal.ecitizen.website.models;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class PersoaneContactList extends BaseModel {
    private List<PersoaneContact> persoaneContactList;

    public List<PersoaneContact> getPersoaneContactList() {
        return persoaneContactList;
    }

    public void setPersoaneContactList(List<PersoaneContact> persoaneContactList) {
        this.persoaneContactList = persoaneContactList;
    }
}
