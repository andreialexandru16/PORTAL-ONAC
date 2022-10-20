package ro.bithat.dms.microservices.dmsws.ps4.documents.imported;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class CorespondentaControlList extends BaseModel {
    private List<CorespondentaControl> corespondentaControlList;

    public List<CorespondentaControl> getCorespondentaControlList() {
        return corespondentaControlList;
    }

    public void setCorespondentaControlList(List<CorespondentaControl> corespondentaControlList) {
        this.corespondentaControlList = corespondentaControlList;
    }
}
