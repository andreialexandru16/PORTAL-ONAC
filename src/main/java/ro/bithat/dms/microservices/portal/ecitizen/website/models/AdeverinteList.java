package ro.bithat.dms.microservices.portal.ecitizen.website.models;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class AdeverinteList extends BaseModel {
    private List<Adeverinte> adeverinteList;

    public List<Adeverinte> getAdeverinteList() {
        return adeverinteList;
    }

    public void setAdeverinteList(List<Adeverinte> adeverinteList) {
        this.adeverinteList = adeverinteList;
    }
}
