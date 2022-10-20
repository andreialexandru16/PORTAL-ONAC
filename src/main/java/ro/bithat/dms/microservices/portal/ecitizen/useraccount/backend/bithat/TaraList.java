package ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Modeleaza un o lista de tari
 */
@XmlRootElement
public class TaraList extends BaseModel {
    private List<Tara> taraList;

    public List<Tara> getTaraList() {
        return taraList;
    }

    public void setTaraList(List<Tara> taraList) {
        this.taraList = taraList;
    }
}
