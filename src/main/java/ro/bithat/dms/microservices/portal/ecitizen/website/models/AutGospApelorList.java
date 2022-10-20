package ro.bithat.dms.microservices.portal.ecitizen.website.models;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class AutGospApelorList extends BaseModel {
    private List<AutGospApelor> autGospApelorList;

    public List<AutGospApelor> getAutGospApelorList() {
        return autGospApelorList;
    }

    public void setAutGospApelorList(List<AutGospApelor> autGospApelorList) {
        this.autGospApelorList = autGospApelorList;
    }
}