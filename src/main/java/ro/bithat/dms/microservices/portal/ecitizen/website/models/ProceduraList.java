
package ro.bithat.dms.microservices.portal.ecitizen.website.models;


import ro.bithat.dms.microservices.dmsws.flow.StandardResponse;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class ProceduraList extends StandardResponse {
    private List<Procedura> proceduraList;

    public List<Procedura> getProceduraList() {
        return proceduraList;
    }

    public void setProceduraList(List<Procedura> proceduraList) {
        this.proceduraList = proceduraList;
    }
}
