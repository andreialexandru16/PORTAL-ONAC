package ro.bithat.dms.microservices.portal.ecitizen.website.models;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class SediuList extends BaseModel {
    private List<Sediu> sediuList;

    public List<Sediu> getSediuList() {
        return sediuList;
    }

    public void setSediuList(List<Sediu> sediuList) {
        this.sediuList = sediuList;
    }
}
