package ro.bithat.dms.microservices.portal.ecitizen.website.models;

import ro.bithat.dms.microservices.dmsws.file.BaseModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class LegitimatieList extends BaseModel {




    private List<Legitimatie> legitimatieList;
    private String query;
    private List<Object> params;

    public List<Legitimatie> getLegitimatieList() {
        return legitimatieList;
    }

    public void setLegitimatieList(List<Legitimatie> legitimatieList) {
        this.legitimatieList = legitimatieList;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List<Object> getParams() {
        return params;
    }

    public void setParams(List<Object> params) {
        this.params = params;
    }
}
