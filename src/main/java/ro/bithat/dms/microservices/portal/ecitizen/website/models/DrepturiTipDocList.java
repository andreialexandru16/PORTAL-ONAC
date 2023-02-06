
package ro.bithat.dms.microservices.portal.ecitizen.website.models;


import ro.bithat.dms.microservices.dmsws.flow.StandardResponse;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class DrepturiTipDocList extends StandardResponse {
    private List<DrepturiTipDoc> drepturiTipDoc;

    public List<DrepturiTipDoc> getDrepturiTipDoc() {
        return drepturiTipDoc;
    }

    public void setDrepturiTipDoc(List<DrepturiTipDoc> drepturiTipDoc) {
        this.drepturiTipDoc = drepturiTipDoc;
    }
}
