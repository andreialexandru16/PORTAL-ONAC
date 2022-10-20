package ro.bithat.dms.microservices.dmsws.poi;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Modeleaza un o lista de stadiu poi
 */
@XmlRootElement
public class StadiuPOIList extends BaseModel {
    private List<StadiuPOI> stadiuPOIList;

    public List<StadiuPOI> getStadiuPOIList() {
        return stadiuPOIList;
    }

    public void setStadiuPOIList(List<StadiuPOI> stadiuPOIList) {
        this.stadiuPOIList = stadiuPOIList;
    }
}
