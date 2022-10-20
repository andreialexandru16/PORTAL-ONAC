package ro.bithat.dms.microservices.portal.ecitizen.website.models;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class ExperientaManagerialaList extends BaseModel {
    private List<ExperientaManageriala> experientaManagerialaList;

    public List<ExperientaManageriala> getExperientaManagerialaList() {
        return experientaManagerialaList;
    }

    public void setExperientaManagerialaList(List<ExperientaManageriala> experientaManagerialaList) {
        this.experientaManagerialaList = experientaManagerialaList;
    }
}
