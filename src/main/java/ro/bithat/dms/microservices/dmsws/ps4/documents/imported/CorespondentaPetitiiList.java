package ro.bithat.dms.microservices.dmsws.ps4.documents.imported;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Modeleaza un o lista de attribute link.
 */
@XmlRootElement
public class CorespondentaPetitiiList extends BaseModel {
    private List<CorespondentaPetitii> corespondentaPetitiiList;

    public List<CorespondentaPetitii> getCorespondentaPetitiiList() {
        return corespondentaPetitiiList;
    }

    public void setCorespondentaPetitiiList(List<CorespondentaPetitii> corespondentaPetitiiList) {
        this.corespondentaPetitiiList = corespondentaPetitiiList;
    }
}
