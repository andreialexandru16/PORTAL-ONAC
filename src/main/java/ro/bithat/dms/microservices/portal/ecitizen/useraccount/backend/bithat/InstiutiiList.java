package ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.bithat;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Modeleaza un o lista de localitati.
 */
@XmlRootElement
public class InstiutiiList extends BaseModel {
    private List<Institutie> institutieList;

    public List<Institutie> getInstitutieList() {
        return institutieList;
    }

    public void setInstitutieList(List<Institutie> institutieList) {
        this.institutieList = institutieList;
    }
}
