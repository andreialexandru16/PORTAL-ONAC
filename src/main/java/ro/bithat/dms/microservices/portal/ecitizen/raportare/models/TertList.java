package ro.bithat.dms.microservices.portal.ecitizen.raportare.models;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Modeleaza un o lista de attribute link.
 */
@XmlRootElement
public class TertList extends BaseModel {
    private List<Tert> tertList;


    public List<Tert> getTertList() {
        return tertList;
    }

    public void setTertList(List<Tert> tertList) {
        this.tertList = tertList;
    }
}
