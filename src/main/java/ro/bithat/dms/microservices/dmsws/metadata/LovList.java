package ro.bithat.dms.microservices.dmsws.metadata;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Modeleaza o lista de valori
 */
@XmlRootElement
public class LovList extends BaseModel {
    List<Lov> lov;
    public LovList() {

    }

    public LovList(List<Lov> lov) {
        this.lov = lov;
    }

    public List<Lov> getLov() {
        return lov;
    }

    public void setLov(List<Lov> lov) {
        this.lov = lov;
    }
}
