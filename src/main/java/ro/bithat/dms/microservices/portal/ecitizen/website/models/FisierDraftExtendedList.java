
package ro.bithat.dms.microservices.portal.ecitizen.website.models;

import ro.bithat.dms.microservices.dmsws.flow.StandardResponse;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class FisierDraftExtendedList extends StandardResponse {
    private List<FisierDraftExtended> fisierDraftList;

    public List<FisierDraftExtended> getFisierDraftList() {
        return fisierDraftList;
    }

    public void setFisierDraftList(List<FisierDraftExtended> fisierDraftList) {
        this.fisierDraftList = fisierDraftList;
    }
}
