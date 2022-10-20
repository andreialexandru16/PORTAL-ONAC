package ro.bithat.dms.microservices.portal.ecitizen.website.models;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class StudiiList extends BaseModel {
    private List<Studii> studiiList;

    public List<Studii> getStudiiList() {
        return studiiList;
    }

    public void setStudiiList(List<Studii> studiiList) {
        this.studiiList = studiiList;
    }
}
