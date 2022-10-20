package ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Modeleaza un o lista de judete.
 */
@XmlRootElement
public class JudetList extends BaseModel {
    private List<Judet> judetList;

    public List<Judet> getJudetList() {
        return judetList;
    }

    public void setJudetList(List<Judet> judetList) {
        this.judetList = judetList;
    }
}
