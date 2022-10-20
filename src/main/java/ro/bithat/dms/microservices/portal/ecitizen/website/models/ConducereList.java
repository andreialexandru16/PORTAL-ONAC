package ro.bithat.dms.microservices.portal.ecitizen.website.models;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class ConducereList extends BaseModel {
    private List<Conducere> conducereList;

    public List<Conducere> getConducereList() {
        return conducereList;
    }

    public void setConducereList(List<Conducere> conducereList) {
        this.conducereList = conducereList;
    }
}
