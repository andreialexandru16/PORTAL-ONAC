package ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat;
import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Modeleaza un o lista de localitati.
 */
@XmlRootElement
public class TipOrdonatorList extends BaseModel {
    private List<TipOrdonator> tipOrdonatorList;

    public List<TipOrdonator> getTipOrdonatorList() {
        return tipOrdonatorList;
    }

    public void setTipOrdonatorList(List<TipOrdonator> tipOrdonatorList) {
        this.tipOrdonatorList = tipOrdonatorList;
    }
}
