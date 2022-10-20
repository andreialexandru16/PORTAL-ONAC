package ro.bithat.dms.microservices.dmsws.poi;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Modeleaza un o lista de durata proiect
 */
@XmlRootElement
public class DurataProiectList extends BaseModel {
    private List<DurataProiect> durataProiectList;

    public List<DurataProiect> getDurataProiectList() {
        return durataProiectList;
    }

    public void setDurataProiectList(List<DurataProiect> durataProiectList) {
        this.durataProiectList = durataProiectList;
    }
}
