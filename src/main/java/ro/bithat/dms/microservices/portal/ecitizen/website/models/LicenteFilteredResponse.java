package ro.bithat.dms.microservices.portal.ecitizen.website.models;

import ro.bithat.dms.microservices.dmsws.flow.StandardResponse;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement

public class LicenteFilteredResponse extends StandardResponse {
    private List<Licenta> licentaList;
    private String query;
    private List<Object> params;

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

    public List<Licenta> getLicentaList() {
        return licentaList;
    }

    public void setLicentaList(List<Licenta> licentaList) {
        this.licentaList = licentaList;
    }
}

