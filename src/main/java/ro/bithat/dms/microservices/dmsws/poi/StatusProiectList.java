package ro.bithat.dms.microservices.dmsws.poi;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Modeleaza un o lista de attribute link.
 */
@XmlRootElement
public class StatusProiectList extends BaseModel {
    private List<StatusProiect> statusProiectList;

    public List<StatusProiect> getStatusProiectList() {
        return statusProiectList;
    }

    public void setStatusProiectList(List<StatusProiect> statusProiectList) {
        this.statusProiectList = statusProiectList;
    }
}
