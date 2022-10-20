package ro.bithat.dms.microservices.dmsws.ps4.documents.imported;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Modeleaza un o lista de attribute link.
 */
@XmlRootElement
public class CorespondentaList extends BaseModel {
    private List<Corespondenta> corespondentaList;

    public List<Corespondenta> getCorespondentaList() {
        return corespondentaList;
    }

    public void setCorespondentaList(List<Corespondenta> corespondentaList) {
        this.corespondentaList = corespondentaList;
    }
}
