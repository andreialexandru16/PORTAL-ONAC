package ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Modeleaza  o lista de filiale
 */
@XmlRootElement
public class FilialaList extends BaseModel {
    private List<Filiala> filialaList;

    public List<Filiala> getFilialaList() {
        return filialaList;
    }

    public void setFilialaList(List<Filiala> filialaList) {
        this.filialaList = filialaList;
    }
}
