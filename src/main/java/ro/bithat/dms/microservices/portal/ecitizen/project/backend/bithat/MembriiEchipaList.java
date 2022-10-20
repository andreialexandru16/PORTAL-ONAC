package ro.bithat.dms.microservices.portal.ecitizen.project.backend.bithat;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Modeleaza un o lista de attribute link.
 */
@XmlRootElement
public class MembriiEchipaList extends BaseModel {
    private List<MembriiEchipa> membriiEchipa;

    public List<MembriiEchipa> getMembriiEchipa() {
        return membriiEchipa;
    }

    public void setMembriiEchipa(List<MembriiEchipa> membriiEchipa) {
        this.membriiEchipa = membriiEchipa;
    }
}
