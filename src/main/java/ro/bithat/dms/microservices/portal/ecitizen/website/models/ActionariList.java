package ro.bithat.dms.microservices.portal.ecitizen.website.models;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class ActionariList extends BaseModel {
    private List<Actionari> actionariList;

    public List<Actionari> getActionariList() {
        return actionariList;
    }

    public void setActionariList(List<Actionari> actionariList) {
        this.actionariList = actionariList;
    }
}
