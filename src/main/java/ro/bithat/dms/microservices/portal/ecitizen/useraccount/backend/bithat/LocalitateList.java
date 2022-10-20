package ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Modeleaza o lista de localitati.
 */
@XmlRootElement
public class LocalitateList extends BaseModel {
    private List<Localitate> localitateList;

    public List<Localitate> getLocalitateList() {
        return localitateList;
    }

    public void setLocalitateList(List<Localitate> localitateList) {
        this.localitateList = localitateList;
    }
}
