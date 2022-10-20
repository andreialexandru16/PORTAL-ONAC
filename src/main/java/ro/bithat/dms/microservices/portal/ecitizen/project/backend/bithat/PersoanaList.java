package ro.bithat.dms.microservices.portal.ecitizen.project.backend.bithat;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Modeleaza o lista de persoane din v_persoana.
 */
@XmlRootElement
public class PersoanaList extends BaseModel {
    private List<Persoana> listaPersoane;

    public List<Persoana> getListaPersoane() {
        return listaPersoane;
    }

    public void setListaPersoane(List<Persoana> listaPersoane) {
        this.listaPersoane = listaPersoane;
    }
}
