package ro.bithat.dms.microservices.portal.ecitizen.website.models;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class AutMediuList extends BaseModel {
    private List<AutMediu> autMediuList;

    public List<AutMediu> getAutMediuList() {
        return autMediuList;
    }

    public void setAutMediuList(List<AutMediu> autMediuList) {
        this.autMediuList = autMediuList;
    }
}