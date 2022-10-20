package ro.bithat.dms.microservices.portal.ecitizen.project.backend.bithat;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Modeleaza un o lista de attribute link.
 */
@XmlRootElement
public class UtilizatorList extends BaseModel {
    private List<Utilizator> listaUtilizator;

    public List<Utilizator> getListaUtilizator() {
        return listaUtilizator;
    }

    public void setListaUtilizator(List<Utilizator> listaUtilizator) {
        this.listaUtilizator = listaUtilizator;
    }
}
