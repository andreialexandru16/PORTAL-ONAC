package ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Modeleaza un o lista de tari
 */
@XmlRootElement
public class PsihologList extends BaseModel{

    private List<Psiholog> psihologList;

    private Boolean exists;

    public List<Psiholog> getPsihologList() {
        return psihologList;
    }

    public void setPsihologList(List<Psiholog> psihologList) {
        this.psihologList = psihologList;
    }

    public Boolean getExists() {
        return exists;
    }

    public void setExists(Boolean exists) {
        this.exists = exists;
    }
}
