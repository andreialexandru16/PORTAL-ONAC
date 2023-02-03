
package ro.bithat.dms.microservices.portal.ecitizen.website.models;


import ro.bithat.dms.microservices.dmsws.flow.StandardResponse;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class FormularList extends StandardResponse {
    private List<Formular> formularList;

    public List<Formular> getFormularList() {
        return formularList;
    }

    public void setFormularList(List<Formular> formularList) {
        this.formularList = formularList;
    }
}
