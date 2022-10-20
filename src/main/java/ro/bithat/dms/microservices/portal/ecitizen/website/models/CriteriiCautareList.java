package ro.bithat.dms.microservices.portal.ecitizen.website.models;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Modeleaza un o lista de attribute link.
 */
@XmlRootElement
public class CriteriiCautareList extends BaseModel {
    private List<CriteriiCautare> criteriiCautare;

    public List<CriteriiCautare> getCriteriiCautare() {
        return criteriiCautare;
    }

    public void setCriteriiCautare(List<CriteriiCautare> criteriiCautare) {
        this.criteriiCautare = criteriiCautare;
    }
}
